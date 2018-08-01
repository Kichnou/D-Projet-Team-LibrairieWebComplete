package classes.catalogue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SousTheme {
    
    //ATTRIBUTS
    ///////////////////////////////////////////////////////////
    private int id;
    private String nom;
    private ArrayList<Livre> listeLivre = new ArrayList();

    //CONSTRUCTEUR
    ///////////////////////////////////////////////////////////
    public SousTheme() {
    }

    public SousTheme(int id, String nom) {
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

    public ArrayList<Livre> getListeLivre() {
        return listeLivre;
    }

    public void setListeLivre(ArrayList<Livre> listeLivre) {
        this.listeLivre = listeLivre;
    }

    //TOSTRING
    ////////////////////////////////////////////////////////////
    @Override
    public String toString() {
        return nom;
    }

    //AUTRES METHODES
    ///////////////////////////////////////////////////////////////
    public void remplirSousThemes(Connection connexion) {

        String query = "select sou.souId, livNumIsbn from Traitement tra\n"
                + "join SousTheme sou\n"
                + "on tra.souId = sou.souId";

        try {
            Statement stmtSousTheme = connexion.createStatement();
            ResultSet rsSousTheme = stmtSousTheme.executeQuery(query);
            Statement stmtCreationLivre = connexion.createStatement();
            ResultSet rsCreationLivre = null;
            
            while (rsSousTheme.next()) {
                if (rsSousTheme.getInt("souId") == this.getId()) {
                    query = "select * from creationLivre where livNumIsbn = " + rsSousTheme.getString("livNumIsbn");
                    rsCreationLivre = stmtCreationLivre.executeQuery(query);

                    String lastIsbn = "";

                    while (rsCreationLivre.next()) {
                        if (lastIsbn.equalsIgnoreCase(rsCreationLivre.getString("livNumIsbn"))) {
                            listeLivre.get(listeLivre.size() - 1).getAuteurs().add(new Auteur(rsCreationLivre.getInt("autId"), rsCreationLivre.getString("autPrenom"), rsCreationLivre.getString("autNom")));
                        }

                        /*if (lastIsbn == null) {
                            Livre leLivre = new Livre();

                            leLivre.valoriserLivre(rsCreationLivre);

                            this.getListeLivre().add(leLivre);

                            lastIsbn = rsCreationLivre.getString("livNumIsbn");
                        }*/

                        if (!lastIsbn.equalsIgnoreCase(rsCreationLivre.getString("livNumIsbn"))) {
                            Livre leLivre = new Livre();

                            leLivre.valoriserLivre(rsCreationLivre);

                            this.getListeLivre().add(leLivre);

                            lastIsbn = rsCreationLivre.getString("livNumIsbn");
                        }
                    }
                    rsCreationLivre.close();
                }                
            }

            

            rsSousTheme.close();
            stmtSousTheme.close();
            stmtCreationLivre.close();
        } catch (SQLException ex) {
            Logger.getLogger(SousTheme.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
