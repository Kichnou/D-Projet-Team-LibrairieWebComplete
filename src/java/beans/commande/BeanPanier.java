

package beans.commande;

import beans.BeanConnect;
import classes.commande.LigneDeCommande;
import classes.catalogue.Livre;
import com.sun.corba.se.spi.presentation.rmi.StubAdapter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

public class BeanPanier implements Serializable{
    
    //******************************** Attributs *******************************
    private Long numCommande;
    private Long idAdresseFact;
    private Long idAdresseLiv;
    private Long numClient;
    private Date dateCommande;
    private String observations;
    private Float prixDeLiv;
    private String statutPayement;
    private String adresseIp;
    private HashMap<String, LigneDeCommande> panier;
    private Float prixTtc;
    
    //****************************** Constructeur ******************************
    public BeanPanier() {
        this.panier = new HashMap<>();
        this.prixTtc = 0F;
    }
    
    //******************************* Accesseurs *******************************
    public Long getNumCommande() {
        return numCommande;
    }

    public void setNumCommande(Long numCommande) {
        this.numCommande = numCommande;
    }

    public Long getIdAdresseFact() {
        return idAdresseFact;
    }

    public void setIdAdresseFact(Long idAdresseFact) {
        this.idAdresseFact = idAdresseFact;
    }

    public Long getIdAdresseLiv() {
        return idAdresseLiv;
    }

    public void setIdAdresseLiv(Long idAdresseLiv) {
        this.idAdresseLiv = idAdresseLiv;
    }

    public Long getNumClient() {
        return numClient;
    }

    public void setNumClient(Long numClient) {
        this.numClient = numClient;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Float getPrixDeLiv() {
        return prixDeLiv;
    }

    public void setPrixDeLiv(Float prixDeLiv) {
        this.prixDeLiv = prixDeLiv;
    }

    public String getStatutPayement() {
        return statutPayement;
    }

    public void setStatutPayement(String statutPayement) {
        this.statutPayement = statutPayement;
    }

    public String getAdresseIp() {
        return adresseIp;
    }

    public void setAdresseIp(String adresseIp) {
        this.adresseIp = adresseIp;
    }

    public HashMap<String, LigneDeCommande> getPanier() {
        return panier;
    }

    public void setPanier(HashMap<String, LigneDeCommande> panier) {
        this.panier = panier;
    }

    public Float getPrixTtc() {
        return prixTtc;
    }

    public void setPrixTtc(Float prixTtc) {
        this.prixTtc = prixTtc;
    }
    
    //***************************** Autres Methodes ****************************

    public void prixCommande(){
        
        for(Map.Entry<String, LigneDeCommande> Collection : this.panier.entrySet()){
            this.prixTtc += (Collection.getValue().getQuantite() * 
                    Collection.getValue().getLeLivre().getPrixTtc());
        }
    }
    
    public Collection<LigneDeCommande> getlist(){
        return panier.values();
    }
    
    public void add(Connection connexion, String isbn){
        this.add(connexion, isbn, 1);
    }
    
    public void add(Connection connexion, String isbn, String quantite){
        try{
            int qte = Integer.parseInt(quantite);
            this.add(connexion, isbn, qte);
        }catch(NumberFormatException ex){
            System.out.println("Erreur de conversion : "+ ex.getMessage());
        }
    }
    
    public void add(Connection connexion, String isbn, int quantite){
        LigneDeCommande lig = null;
        
        if(panier.get(isbn) != null){
            lig = panier.get(isbn);
            lig.change(quantite);
            panier.put(isbn, lig);
        }else{
            lig = new LigneDeCommande();
            ResultSet rs = this.getLivre(connexion, isbn);
            Livre leLivre = new Livre();
            try {
                while(rs.next()){
                    leLivre.valoriserLivre(rs);
                }
            } catch (SQLException ex) {
                Logger.getLogger(BeanPanier.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            lig.setLeLivre(leLivre);
            lig.setQuantite(1);
            
            panier.put(lig.getLeLivre().getIsbn(), lig);
        }
        
        if(lig.getQuantite() < 1){
            this.del(isbn);
        }
    }  
    
    public int size(){
        return panier.size();
    }
    
    public Boolean isEmpty(){
        return panier.isEmpty();
    }
    
    public void clean(){
        panier.clear();
    }
    
    public void del(String isbn){
        panier.remove(isbn);
    }
    
    public void dec(Connection connexion, String isbn){
        add(connexion, isbn, -1);
    }
    
    public ResultSet getLivre(Connection connexion, String isbn){
        
        
        
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM creationLivre WHERE livNumIsbn = ?";
            PreparedStatement pstmt = connexion.prepareStatement(query);
            pstmt.setString(1, isbn);
            rs = pstmt.executeQuery();
            
        } catch (SQLException ex) {
            System.out.println("Erreur SQL getLivre()"
                    + ex.getErrorCode()+" / "+ ex.getMessage());
        }
        
        return rs;
    }
    
    
    public void saveCommande(Connection connect){
        
        try {
            String querry = "INSERT INTO (adrIdFacturation, adrIdLivraison, "
                + "cliNumClient, comDateBC, comObservations, comPrixLivraison, "
                + "comAdrIp, comDateLiv) "
                + "VALUES (?,?,?,?,?,?,?,?)";
            
            PreparedStatement pstmt = connect.prepareStatement(querry);
            
        } catch (SQLException ex) {
            Logger.getLogger(BeanPanier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
