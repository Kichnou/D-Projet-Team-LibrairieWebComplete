package classes.catalogue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Theme {
    
    //ATTRIBUTS
    ///////////////////////////////////////////////////////////
    private int id;
    private String nom;
    private ArrayList<SousTheme> listeSousThemes = new ArrayList();

    //CONSTRUCTEUR
    ///////////////////////////////////////////////////////////
    public Theme() {
    }

    public Theme(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    //ACCESSEURS
    ///////////////////////////////////////////////////////////
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<SousTheme> getListeSousThemes() {
        return listeSousThemes;
    }

    public void setListeSousThemes(ArrayList<SousTheme> listeSousThemes) {
        this.listeSousThemes = listeSousThemes;
    }
    
    

    //TOSTRING
    ////////////////////////////////////////////////////////////
    @Override
    public String toString() {
        return nom;
    }

    //AUTRES METHODES
    /////////////////////////////////////////////////////////////
    public void remplirThemes(Connection connexion) {

        String query = "select * from SousTheme\n"
                + "order by theId";        
        try {
            Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                if (rs.getInt("theId") == this.getId()) {
                    SousTheme s = new SousTheme(rs.getInt("souId"), rs.getString("souNom"));
                    s.remplirSousThemes(connexion);
                    this.getListeSousThemes().add(s);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Theme.class.getName()).log(Level.SEVERE, null, ex);
        }                
    }
}
