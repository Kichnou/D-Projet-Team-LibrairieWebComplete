
package beanRubrique;

import classesRubrique.CoupDeCoeur;
import classesRubrique.Evenement;
import classesRubrique.Promo;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BeanRubrique implements Serializable {
    
    private ArrayList <Promo> mesPromo = new ArrayList();
    private ArrayList <Evenement> mesEvenements = new ArrayList();
    private ArrayList <CoupDeCoeur> mesCoupDeCoeurs = new ArrayList();
    
    
    
    public BeanRubrique() {
        this.mesEvenements = new ArrayList();
        this.mesPromo = new ArrayList();
        this.mesCoupDeCoeurs = new ArrayList();
    }

   
    public ArrayList<Promo> getMesPromo() {
        return mesPromo;
    }

    public void setMespromo(ArrayList<Promo> mesPromo) {
        this.mesPromo = mesPromo;
    }

    public ArrayList<Evenement> getMesEvenements() {
        return mesEvenements;
    }

    public void setMesEvenements(ArrayList<Evenement> mesEvenements) {
        this.mesEvenements = mesEvenements;
    }

    public ArrayList<CoupDeCoeur> getMesCoupDeCoeurs() {
        return mesCoupDeCoeurs;
    }

 public void setMesCoupDeCoeurs(ArrayList<CoupDeCoeur> mesCoupDeCoeurs) {
       this.mesCoupDeCoeurs = mesCoupDeCoeurs;
  }
       
//    public ResultSet getEvent(Connection connect, String event){
//        this.mesEvenements.clear();
//        ResultSet rs = null;
//          
//        try {
//            String rubNom = null;
//            
//            if(event.equals("evenement")){
//                rubNom = "evenement";
//            }
//            
//            String query = "select * from Rubrique where rubNom = ?";
//            PreparedStatement pstmt = connect.prepareStatement(query);
//            pstmt.setString(1, event);
//            rs = pstmt.executeQuery();
//        
//            if(event.equals("evenement")){
//                while (rs.next()) {
//                   Evenement e1 = new Evenement();
//                   
//                   e1.setNom(rs.getString("rubNom"));
//                   e1.setDateDebut(rs.getDate("rubDateDebut"));
//                   e1.setDatefin(rs.getDate("rubDateFin"));
//                   e1.setImage(rs.getString("rubImage"));
//                   
//                   this.mesEvenements.add(e1);
//                }
//            }
//                        
//        } catch (SQLException ex) {
//            System.out.println("Error getEvent: "+ex.getErrorCode()+" / "+
//                    ex.getMessage());
//        }
//        return rs;
//    } 
//    //  Get Promos From The DB 
    public void getPromoCoupsDeCoeur(Connection connect, String event){
            this.mesPromo.clear();
            this.mesCoupDeCoeurs.clear();
            
            
            ResultSet rs = null;
        try {
            String rubNom = null;
            String query = "select * from RubriqueLivre where rubNom =?";
            
            if(event.equals("promo")){
                rubNom = "Promo";
            }
            else if(event.equals("coupDeCoeur")){
                rubNom = "coupDeCoeur";
            }
            
            PreparedStatement pstmt = connect.prepareStatement(query);
            pstmt.setString(1, rubNom);
                      
            
            rs = pstmt.executeQuery();
            
            if (event.equals("promo")){
                while (rs.next()){

                Promo maPromo = new Promo();
                    maPromo.setDateDebut(rs.getDate("rubDateDebut"));
                    maPromo.setDateFin(rs.getDate("rubDateFin"));
                    maPromo.setImage(rs.getString("livImage"));
                    maPromo.setLivreDatePubli(rs.getDate("livDatePubli"));
                    maPromo.setLivreImage(rs.getString("livImage"));
                    maPromo.setLivreNumIsbn(rs.getString("LivNumIsbn"));
                    maPromo.setLivrePrix(rs.getFloat("livPrix"));
                    maPromo.setLivreResume(rs.getString("livResume"));
                    maPromo.setLivreSousTitre(rs.getString("livSousTitre"));
                    maPromo.setLivreTitre(rs.getString("livTitre"));
                    maPromo.setLivreQuantite(rs.getInt("livQuantite"));
                    maPromo.setPourcentagePromo(rs.getFloat("rubPourcentagePromotion"));

                    this.mesPromo.add(maPromo);
                }
            }
            
            if(event.equals("coupDeCoeur")){
                
                while(rs.next()){
                    CoupDeCoeur monCoupDeCoeur = new CoupDeCoeur();
                    monCoupDeCoeur.setDateDebut(rs.getDate("rubDateDebut"));
                    monCoupDeCoeur.setDateFin(rs.getDate("rubDateFin"));
                    monCoupDeCoeur.setLivreDatePubli(rs.getDate("livDatePubli"));
                    monCoupDeCoeur.setLivreImage(rs.getString("livImage"));
                    monCoupDeCoeur.setLivreNumIsbn(rs.getString("livNumIsbn"));
                    monCoupDeCoeur.setLivreResume(rs.getString("livResume"));
                    monCoupDeCoeur.setLivreSousTitre(rs.getString("livSousTitre"));
                    monCoupDeCoeur.setLivreTitre(rs.getString("livTitre"));
                    
                    this.mesCoupDeCoeurs.add(monCoupDeCoeur);
                }
            }
            
            
        } catch (SQLException ex) {
            System.out.println("Problem reaching RubriqueLivre View"+ex.getErrorCode()
                    +ex.getMessage());
        }
      
    }

    
    

//        public void assigningValuesToPromo(Connection connect, String Event){
//            this.mesPromo.clear();
//             ResultSet rs = null;
//            String query;
//            PreparedStatement pstmt;
//        try {
//            query = "select * from RubriqueLivre where rubNom = ?";
//            pstmt = connect.prepareStatement(query);
//            (1, )
//            rs = pstmt.executeQuery();
//            Promo maPromo = new Promo();
//        try {
//            maPromo.setDateDebut(rs.getDate("rubDateDebut"));
//            maPromo.setDateFin(rs.getDate("rubDateFin"));
//            maPromo.setImage(rs.getString("livImage"));
//            maPromo.setLivreDatePubli(rs.getDate("livDatePubli"));
//            maPromo.setLivreImage(rs.getString("livImage"));
//            maPromo.setLivreNumIsbn(rs.getString("LivNumIsbn"));
//            maPromo.setLivrePrix(rs.getFloat("livPrix"));
//            maPromo.setLivreResume(rs.getString("livResume"));
//            maPromo.setLivreSousTitre(rs.getString("livSousTitre"));
//            maPromo.setLivreTitre(rs.getString("livTitre"));
//            maPromo.setLivreQuantite(rs.getInt("livQuantite"));
//            maPromo.setPourcentagePromo(rs.getFloat("rubPourcentagePromotion"));
//
//                    this.mesPromo.add(maPromo);
//                    
//                    
//        } catch (SQLException ex) {
//            System.out.println("Problems Assigning Values to Promo"+ex.getMessage()
//                    +ex.getErrorCode());
//        } 
//            
//        } catch (SQLException ex) {
//            System.out.println("Error In Promo"+ex.getMessage()+ex.getErrorCode());
//        }
        
        }
//         public void assigningValuesToCoupsDeCoeur(Connection connect, String event){
//          this.mesCoupDeCoeurs.clear();
//          
//          String query = "select * from "
//  }
//      
//         



                



