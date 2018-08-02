/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes.commande;

import classes.catalogue.*;
import classes.catalogue.Evaluations;
import classes.catalogue.Livre;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;

public class LigneDeCommande{
    
    //******************************** Attributs *******************************
    private Evaluations eval;
    private Long numLignDeCommande;
    private int quantite;
    private HashMap<String, Livre> lesLivres = null;
    private Livre leLivre;
    
    //****************************** Constructeur ******************************

    public LigneDeCommande() {
        this.lesLivres = new HashMap<>();
    }
    
    //******************************* Accesseurs *******************************
    public Long getNumLignDeCommande() {
        return numLignDeCommande;
    }

    public void setNumLignDeCommande(Long numLignDeCommande) {
        this.numLignDeCommande = numLignDeCommande;
    }
    
    public HashMap<String, Livre> getLesLivres() {
        return lesLivres;
    }

    public void setLesLivres(HashMap<String, Livre> lesLivres) {
        this.lesLivres = lesLivres;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    
    public HashMap<String, Livre> getMap() {
        return lesLivres;
    }

    public void setMap(HashMap<String, Livre> map) {
        this.lesLivres = map;
    }

    public Livre getLeLivre() {
        return leLivre;
    }

    public void setLeLivre(Livre leLivre) {
        this.leLivre = leLivre;
    }
    
    //***************************** Autres Methodes ****************************
    
//    public Collection<Livre> getlist(){
//        return lesLivres.values();
//    }
//    
//    public int size(){
//        return lesLivres.size();
//    }
//    
//    public Boolean isEmpty(){
//        return lesLivres.isEmpty();
//    }
//    
//    public void clean(){
//        lesLivres.clear();
//    }
//    

    public void saveLigneDeCommande(Long numCommande, Connection connexion){
        String url = "INSERT INTO LigneDeCommande (ligId, livNumIsbn, comNumBc"
                + ", evaId, ligQte, ligPrix, ligTva)"
                + "VALUES (?,?,?,?,?,?,?)";
        
        try {
            PreparedStatement pstmt = connexion.prepareStatement(url);
            
            pstmt.setLong(1, this.numLignDeCommande);
            pstmt.setString(2, this.leLivre.getIsbn());
            pstmt.setLong(3, numCommande);
            pstmt.setLong(4, this.eval.getEvaId());
            pstmt.setInt(5, this.quantite);
            pstmt.setFloat(6, this.leLivre.getPrix());
            pstmt.setFloat(7, this.leLivre.getTva());
            
            pstmt.execute();
            
            pstmt.close();
            connexion.close();
            
        } catch (SQLException ex) {
            System.out.println("Erreur requete insert ligne de commande : "
                    + ex.getErrorCode() + " / "+ ex.getMessage());
        }
    }
    
    public Float prixTtcLigCom(){
        Float prixTtc = 0.0F;
        
        prixTtc = this.leLivre.getPrixTtc() * this.quantite;
        
        return prixTtc;
    }
    
    
    
    
    public void change(int quantite){
        this.quantite += quantite;
    }
}
