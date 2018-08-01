
package beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alex
 */
public class BeanParticipant {
    private String nom;
    private String  prenom;
    private String eMail;
    
    
    //CONSTRUCTEUR PAR DEFAUT ET SURCHARGES

    public BeanParticipant() {
    }

    public BeanParticipant(String nom, String prenom, String eMail) {
        this.nom = nom;
        this.prenom = prenom;
        this.eMail = eMail;
    }
  // ACCESSEURS

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
    
    //AUTRES METHODES

    @Override
    public String toString() {
        return "" + "nom=" + nom + ", prenom=" + prenom + ", eMail=" + eMail;
    }

    public void insertParticipant(Connection connect){
       
        try {
            String query = "insert into Participant (parNom, parPrenom, parEmail)"
               + "values(?, ?, ?)"; 
            PreparedStatement pstmt = connect.prepareStatement(query);
            pstmt.setString(1,this.nom);
            pstmt.setString(2,this.prenom);
            pstmt.setString(3,this.eMail);
            pstmt.execute();
            pstmt.close();
            connect.close();
        } catch (SQLException ex) {
            System.out.println("Erreur d'insertion"+ex.getErrorCode()
                    +" / "+ ex.getMessage());
        }
    
    }
}
