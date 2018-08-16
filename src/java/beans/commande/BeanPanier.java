

package beans.commande;

import classes.commande.LigneDeCommande;
import classes.catalogue.Livre;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BeanPanier implements Serializable{
    
    //******************************** Attributs *******************************
    private Long numCommande;
    private Long idAdresseFact;
    private Long idAdresseLiv;
    private Long numClient;
    private Timestamp dateCommande;
    private String observations;
    private Float prixDeLiv;
    private int statutPayement;
    private String adresseIp;
    private HashMap<String, LigneDeCommande> panier;
    private Float prixTtc;
    private int nbrArticles;
    private DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    
    //****************************** Constructeur ******************************
    public BeanPanier() {
        this.prixDeLiv = 5F;
        this.panier = new HashMap<>();
        this.prixTtc = 0F;
        this.nbrArticles = 0;
        //this.dateCommande = new Date();
        //dateCommande
        this.idAdresseFact = 1L;
        this.adresseIp = "92.196.20.45";
        this.idAdresseLiv = 3L;
        this.numClient = 1L;
//        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//        format.format(dateCommande);
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

    public Timestamp getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Timestamp dateCommande) {
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

    public int getStatutPayement() {
        return statutPayement;
    }

    public void setStatutPayement(int statutPayement) {
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

    public int getNbrArticles() {
        return nbrArticles;
    }

    public void setNbrArticles(int nbrArticles) {
        this.nbrArticles = nbrArticles;
    }
    
    //***************************** Autres Methodes ****************************
    public void calculNbreArticles(){
        this.nbrArticles = 0;
        
        for(Map.Entry<String, LigneDeCommande> Collection : this.panier.entrySet()){
            this.nbrArticles += Collection.getValue().getQuantite();
        }
    }
    
    public void prixCommande(){
        this.prixTtc = 0F;
        
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
    
    public Long getEvaId(Connection connect, String isbn){
        Long evaId = null;
        
        try {
            String query = "SELECT evaId FROM Evaluation WHERE livNumIsbn = ?";
            PreparedStatement pstmt = connect.prepareStatement(query);
            pstmt.setString(1, isbn);
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()){
                evaId = rs.getLong(1);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur requete getEvaId : "
                        + ex.getErrorCode() + " / "+ ex.getMessage());
        }
        
        return evaId;
    }
    
    public void saveLigneDeCommande(Connection connexion){
        String url = "INSERT INTO LigneDeCommande (livNumIsbn, comNumBc"
                + ", ligQteCommande, ligPrix, ligTva)"
                + "VALUES (?,?,?,?,?)";
        System.out.println("numCommande : "+ this.numCommande);
        for(Map.Entry<String, LigneDeCommande> Collection : this.panier.entrySet()){
            try {
            PreparedStatement pstmt = connexion.prepareStatement(url);
            
            pstmt.setString(1, Collection.getValue().getLeLivre().getIsbn());
            pstmt.setLong(2, this.numCommande);
//            pstmt.setLong(3, this.getEvaId(connexion
//                    , Collection.getValue().getLeLivre().getIsbn()));
            pstmt.setInt(3, Collection.getValue().getQuantite());
            pstmt.setFloat(4, Collection.getValue().getLeLivre().getPrix());
            pstmt.setFloat(5, Collection.getValue().getLeLivre().getTva());
            
            pstmt.execute();
            
            pstmt.close();
            
            
            } catch (SQLException ex) {
                System.out.println("Erreur requete insert ligne de commande : "
                        + ex.getErrorCode() + " / "+ ex.getMessage());
            }
        }
        
        try {
            connexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(BeanPanier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Long saveCommande(Connection connect){
        //this.getStatutPaiement(connect, "Payé");
        //this.statutPayement = 3;
        try {
            String querry = "INSERT INTO Commande (adrIdFacturation, "
                    + "adrIdLivraison, cliNumClient, comDateBC, comObservations, "
                    + "comPrixLivraison, comStatutPay, comAdrIp) "
                    + "VALUES (?,?,?,?,?,?,?,?)";
            
            PreparedStatement pstmt = connect.prepareStatement(querry, 
                    Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setLong(1, this.idAdresseFact);
            pstmt.setLong(2, this.idAdresseLiv);
            pstmt.setLong(3, this.numClient);
            pstmt.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
            pstmt.setString(5, this.observations);
            pstmt.setFloat(6, this.prixDeLiv);
            pstmt.setInt(7, this.statutPayement);
            pstmt.setString(8, this.adresseIp);
            
            pstmt.execute();
            ResultSet rs = pstmt.getGeneratedKeys();
            
            while(rs.next()){
                this.numCommande = rs.getLong(1);
            }
            
            pstmt.close();
            connect.close();
            
        } catch (SQLException ex) {
            System.out.println("erreur saveCommande : "
                    + ex.getErrorCode() + " / "+ ex.getMessage());
        }
        
        return this.numCommande;
        
        
    }
    
    public void getStatutPaiement(Connection connect, String i){
        
        try {
            String query = "SELECT staValeur WHERE staValeur = ?";
            PreparedStatement pstmt = connect.prepareStatement(query);
            pstmt.setString(1, i);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs != null && rs.first()){
                this.statutPayement = rs.getInt(1);
                //System.out.println("statut paiement : "+this.statutPayement);
               
            pstmt.close();
            connect.close();
            
            }
        } catch (SQLException ex) {
            Logger.getLogger(BeanPanier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
