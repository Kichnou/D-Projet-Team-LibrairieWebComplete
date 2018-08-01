package beans.catalogue;

import classes.catalogue.Auteur;
import classes.catalogue.Evaluations;
import classes.catalogue.Livre;
import classes.catalogue.Theme;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BeanCatalogue {

    //ATTRIBUTS
    ////////////////////////////////////////////////////////////////
    private Vector<Livre> monCatalogue = new Vector();
    private ArrayList<Theme> mesThemes = new ArrayList();

    //CONSTRUCTEURS
    ///////////////////////////////////////////////////////////////
    public BeanCatalogue() {
    }

    //ACCESSEURS
    ////////////////////////////////////////////////////////////////
    public Vector<Livre> getMonCatalogue() {
        return monCatalogue;
    }

    public void setMonCatalogue(Vector<Livre> monCatalogue) {
        this.monCatalogue = monCatalogue;
    }

    public ArrayList<Theme> getMesThemes() {
        return mesThemes;
    }

    public void setMesThemes(ArrayList<Theme> mesThemes) {
        this.mesThemes = mesThemes;
    }

    //TOSTRING
    /////////////////////////////////////////////////////////////////
    @Override
    public String toString() {
        return "Catalogue{" + "monCatalogue=" + monCatalogue + '}';
    }

    //AUTRES METHODES
    //////////////////////////////////////////////////////////////////
    /*
     *Methode pour mettre tous les livres de la base dans un arrayList
     */
    public void remplirCatalogue(Connection connexion) {
        String query = "select * from creationLivre order by livNumIsbn";

        try {
            Statement stmt = connexion.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            String lastIsbn = "";

            while (rs.next()) {

                if (lastIsbn.equalsIgnoreCase(rs.getString("livNumIsbn"))) {
                    monCatalogue.get(monCatalogue.size() - 1).getAuteurs().add(new Auteur(rs.getInt("autId"), rs.getString("autPrenom"), rs.getString("autNom")));
                }

                if (!lastIsbn.equalsIgnoreCase(rs.getString("livNumIsbn"))) {
                    Livre leLivre = new Livre();

                    leLivre.valoriserLivre(rs);

                    monCatalogue.add(leLivre);

                    lastIsbn = rs.getString("livNumIsbn");
                }

            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(BeanCatalogue.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            connexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(BeanCatalogue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     *Remplit un Vector pour remplir jListRecherche
     */
    public ArrayList resultatRechercheTitre(String recherche, Connection connexion) {
        ArrayList<Livre> resultatRecherche = new ArrayList();

        String query = "select * from Livre where livTitre like '%" + recherche + "%'";
        ArrayList<String> listeIsbn = new ArrayList();
        String queryView = "";

        try {
            Statement stmt = connexion.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                listeIsbn.add(rs.getString("livNumIsbn"));
            }

            ResultSet rsView = null;

            if (!listeIsbn.isEmpty()) {
                for (String s : listeIsbn) {
                    queryView = "select * from creationLivre where livNumIsbn = '" + s + "' order by livNumIsbn";
                    rsView = stmt.executeQuery(queryView);
                    Livre l = new Livre();
                    String lastIsbn = "";
                    while (rsView.next()) {
                        if (!lastIsbn.equals(l.getIsbn())) {
                            l = new Livre();
                            l.valoriserLivre(rsView);
                            resultatRecherche.add(l);
                            lastIsbn = l.getIsbn();
                        } else {
                            ((Livre) resultatRecherche.get(resultatRecherche.size() - 1)).getAuteurs()
                                    .add(new Auteur(rsView.getInt("autId"), rsView.getString("autPrenom"), rsView.getString("autNom")));
                            lastIsbn = rsView.getString("livNumIsbn");
                        }
                    }
                }
                rsView.close();
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(BeanCatalogue.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            connexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(BeanCatalogue.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultatRecherche;
    }

    public ArrayList resultatRechercheIsbn(String recherche, Connection connexion) {
        ArrayList<Livre> resultatRecherche = new ArrayList();

        String query = "select * from Livre where livNumIsbn like '%" + recherche + "%'";
        ArrayList<String> listeIsbn = new ArrayList();
        String queryView = "";

        try {
            Statement stmt = connexion.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                listeIsbn.add(rs.getString("livNumIsbn"));
            }

            ResultSet rsView = null;

            if (!listeIsbn.isEmpty()) {
                for (String s : listeIsbn) {
                    queryView = "select * from creationLivre where livNumIsbn = '" + s + "' order by livNumIsbn";
                    rsView = stmt.executeQuery(queryView);
                    Livre l = new Livre();
                    String lastIsbn = "";
                    while (rsView.next()) {
                        if (!lastIsbn.equals(l.getIsbn())) {
                            l = new Livre();
                            l.valoriserLivre(rsView);
                            resultatRecherche.add(l);
                            lastIsbn = l.getIsbn();
                        } else {
                            ((Livre) resultatRecherche.get(resultatRecherche.size() - 1)).getAuteurs()
                                    .add(new Auteur(rsView.getInt("autId"), rsView.getString("autPrenom"), rsView.getString("autNom")));
                            lastIsbn = rsView.getString("livNumIsbn");
                        }
                    }
                }
                rsView.close();
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(BeanCatalogue.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            connexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(BeanCatalogue.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultatRecherche;
    }

    public ArrayList resultatRechercheAuteur(String recherche, Connection connexion) {
        ArrayList<Livre> resultatRecherche = new ArrayList();

        String query = "select * from Auteur where autPrenom + ' ' + autNom like '%" + recherche + "%'";
        ArrayList<String> listeIsbn = new ArrayList();
        ArrayList<String> listeAutId = new ArrayList();
        String queryView = "";

        try {
            Statement stmt = connexion.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                listeAutId.add("" + rs.getInt("autId"));
            }

            for (String s : listeAutId) {
                query = "select * from rechercheAuteur where autId = " + s;
                rs = stmt.executeQuery(query);
                while (rs.next()) {
                    listeIsbn.add(rs.getString("livNumIsbn"));
                }
            }

            ResultSet rsView = null;

            if (!listeIsbn.isEmpty()) {
                for (String s : listeIsbn) {
                    queryView = "select * from creationLivre where livNumIsbn = '" + s + "' order by livNumIsbn";
                    rsView = stmt.executeQuery(queryView);
                    Livre l = new Livre();
                    String lastIsbn = "";
                    while (rsView.next()) {
                        if (!lastIsbn.equals(l.getIsbn())) {
                            l = new Livre();
                            l.valoriserLivre(rsView);
                            resultatRecherche.add(l);
                            lastIsbn = l.getIsbn();
                        } else {
                            ((Livre) resultatRecherche.get(resultatRecherche.size() - 1)).getAuteurs()
                                    .add(new Auteur(rsView.getInt("autId"), rsView.getString("autPrenom"), rsView.getString("autNom")));
                            lastIsbn = rsView.getString("livNumIsbn");
                        }
                    }
                }
                rsView.close();
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(BeanCatalogue.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            connexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(BeanCatalogue.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultatRecherche;
    }

    public ArrayList resultatRechercheMotCle(String recherche, Connection connexion) {
        ArrayList<Livre> resultatRecherche = new ArrayList();

        String query = "select * from MotsCles where motMot like '%" + recherche + "%'";
        ArrayList<String> listeIsbn = new ArrayList();
        ArrayList<String> listeMotId = new ArrayList();
        String queryView = "";

        try {
            Statement stmt = connexion.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                listeMotId.add("" + rs.getInt("motId"));
            }

            for (String s : listeMotId) {
                query = "select * from rechercheMotsCles where motId = " + s;
                rs = stmt.executeQuery(query);
                while (rs.next()) {
                    listeIsbn.add(rs.getString("livNumIsbn"));
                }
            }

            ResultSet rsView = null;

            if (!listeIsbn.isEmpty()) {
                for (String s : listeIsbn) {
                    queryView = "select * from creationLivre where livNumIsbn = '" + s + "' order by livNumIsbn";
                    rsView = stmt.executeQuery(queryView);
                    Livre l = new Livre();
                    String lastIsbn = "";
                    while (rsView.next()) {
                        if (!lastIsbn.equals(l.getIsbn())) {
                            l = new Livre();
                            l.valoriserLivre(rsView);
                            resultatRecherche.add(l);
                            lastIsbn = l.getIsbn();
                        } else {
                            ((Livre) resultatRecherche.get(resultatRecherche.size() - 1)).getAuteurs()
                                    .add(new Auteur(rsView.getInt("autId"), rsView.getString("autPrenom"), rsView.getString("autNom")));                           
                            lastIsbn = rsView.getString("livNumIsbn");
                        }
                    }
                }
                rsView.close();
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(BeanCatalogue.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            connexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(BeanCatalogue.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultatRecherche;
    }

    public void remplirThemesCatalogue(Connection connexion) {

        String query = "select * from Theme";

        try {
            Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Theme t = new Theme(rs.getInt("theId"), rs.getString("theNom"));
                t.remplirThemes(connexion);
                this.getMesThemes().add(t);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BeanCatalogue.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            connexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(BeanCatalogue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Evaluations> remplirEvaluations(String isbn, Connection connexion) {
        ArrayList<Evaluations> listeEval = new ArrayList();
        
        String query = "select evaNote, evaCommentaire from Evaluation where livNumIsbn = ?";
        
        try {
            PreparedStatement pstmt = connexion.prepareStatement(query);
            
            pstmt.setString(1, isbn);
            
            ResultSet rs = pstmt.executeQuery();
            
            int note;
            
            while (rs.next()) {
                if (rs.getString("evaNote") == null) {
                    note = -1;
                } else {
                    note = rs.getInt("evaNote");
                }
                listeEval.add(new Evaluations(note, rs.getString("evaCommentaire")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BeanCatalogue.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listeEval;
    }
}
