

package beans.commande;

import classes.catalogue.LigneDeCommande;
import classes.catalogue.Livre;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    //****************************** Constructeur ******************************
    public BeanPanier() {
        this.panier = new HashMap<>();
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
    
    //***************************** Autres Methodes ****************************

//    public Float prixCommande(){
//        Float prixTtc = 0.0F;
//        
//        panier.forEach(null);
//        for(LigneDeCommande laLigneDeCommande : this.panier){
//            prixTtc += laLigneDeCommande.prixTtcLigCom();
//        }
//        return prixTtc;
//    }
    
    public Collection<LigneDeCommande> getlist(){
        return panier.values();
    }
    
    public void add(String isbn, int quantite){
        LigneDeCommande lig = null;
        
        if(panier.containsKey(isbn)){
            lig = panier.get(isbn);
            lig.change(quantite);
        }else{
            lig = new LigneDeCommande();
            panier.put(lig.getLeLivre().getIsbn(), lig);
        }
    }
    
    public void add(Livre leLivre, String quantite){
        try{
            this.leLivre = leLivre;
            this.quantite = Integer.parseInt(quantite);
            this.add(this.leLivre, this.quantite);
        }catch(NumberFormatException ex){
            System.out.println("Erreur de conversion : "+ ex.getMessage());
        }
    }
    
    public void add(Livre leLivre){
        this.leLivre = leLivre;
        this.add(this.leLivre, 1);
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
    
    public void dec(String isbn){
        add(isbn, -1);
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
