package classes.catalogue;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Livre {

    //ATTRIBUTS
    ////////////////////////////////////////////////////////////////
    private String titre;
    private String sousTitre;
    private ArrayList<Auteur> auteurs = new ArrayList();
    private String editeur;
    private String isbn;
    private String image;
    private Float prix;
    //private String couverture;
    private String resume;
    private Date datePubli;
    private int quantite;
    private float tva;
    private float prixTtc;

    //CONSTRUCTEURS
    //////////////////////////////////////////////////////////////
    public Livre() {

    }

    //ACCESSEURS
    //////////////////////////////////////////////////////////////////
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getSousTitre() {
        return sousTitre;
    }

    public void setSousTitre(String sousTitre) {
        this.sousTitre = sousTitre;
    }

    public String getEditeur() {
        return editeur;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    /*public String getCouverture() {
     return couverture;
     }

     public void setCouverture(String couverture) {
     this.couverture = couverture;
     }*/
    public String getImage() {
        return image;
    }

    public void setImage(String Image) {
        this.image = Image;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public Date getDatePubli() {
        return datePubli;
    }

    public void setDatePubli(Date datePubli) {
        this.datePubli = datePubli;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public ArrayList<Auteur> getAuteurs() {        
        return auteurs;
    }

    public void setAuteurs(ArrayList<Auteur> auteurs) {
        this.auteurs = auteurs;
    }

    public float getTva() {
        return tva;
    }

    public void setTva(float tva) {
        this.tva = tva;
    }

    public void setPrixTtc(float prixTtc) {
        this.prixTtc = prixTtc;
    }

    public float getPrixTtc() {
        return prixTtc;
    }

    //TOSTRING
    ////////////////////////////////////////////////////////////
    @Override
    public String toString() {
        return titre;
    }

    //AUTRES METHODES
    ////////////////////////////////////////////////////////////    
    public void valoriserLivre(ResultSet rs) {
        try {
            this.setImage(rs.getString("livImage"));
            this.setIsbn(rs.getString("livNumIsbn"));
            this.setPrix(rs.getFloat("livPrix"));
            this.setSousTitre(rs.getString("livSousTitre"));
            this.setTitre(rs.getString("livTitre"));
            this.setEditeur(rs.getString("ediNom"));
            this.setImage(rs.getString("livImage"));
            this.setResume(rs.getString("livResume"));
            this.setDatePubli(rs.getDate("livDatePubli"));
            this.setQuantite(rs.getInt("livQuantite"));
            this.setTva(rs.getFloat("tvaTaux"));
            this.getAuteurs().add(new Auteur(rs.getInt("autId"), rs.getString("autPrenom"), rs.getString("autNom")));
            this.setPrixTtc(this.calculPrixTtc());
        } catch (SQLException ex) {
            Logger.getLogger(Livre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enregistrerLivre(Connection connexion) {
        String query = "UPDATE Livre "
                + "SET livTitre = ?, "
                + "livSousTitre = ?, "
                + "livDatePubli = ?, "
                + "livPrix = ?, "
                + "livQuantite = ?, "
                + "livResume = ?, "
                + "livImage = ?, "
                + "livNumIsbn = ? "
                + "WHERE livNumIsbn = ?";

        try {
            PreparedStatement pstmt = connexion.prepareStatement(query);

            pstmt.setString(1, this.getTitre());
            pstmt.setString(2, this.getSousTitre());
            pstmt.setDate(3, this.getDatePubli());
            pstmt.setFloat(4, this.getPrix());
            pstmt.setInt(5, this.getQuantite());
            pstmt.setString(6, this.getResume());
            pstmt.setString(7, this.getImage());
            pstmt.setString(8, this.getIsbn());
            pstmt.setString(9, this.getIsbn());

            pstmt.executeUpdate();
            query = "select * from Editeur where ediNom like ?";

            pstmt = connexion.prepareStatement(query);

            pstmt.setString(1, this.getEditeur());

            ResultSet rs = pstmt.executeQuery();

            int ediId = -1;

            while (rs.next()) {
                ediId = rs.getInt("ediId");
            }

            if (ediId != -1) {
                query = "update Livre set ediId = ? where livNumIsbn = ?";
                pstmt = connexion.prepareStatement(query);

                pstmt.setInt(1, ediId);
                pstmt.setString(2, this.getIsbn());

                pstmt.executeUpdate();
            }

            rs.close();
            pstmt.close();

        } catch (SQLException ex) {
            System.out.println("erreur:SQL: " + ex.getMessage());
        }

        try {
            connexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(Livre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ajouterLivre(Connection connexion) {
        String query = "INSERT INTO Livre "
                + "(livNumIsbn, livTitre, livSousTitre, livImage, livPrix, livResume, livDatePubli, livQuantite, livStatut, tvaCode, ediId) "
                + "VALUES (?,?,?,?,?,?,?,?,1,?,?)";

        try {
            PreparedStatement pstmt = connexion.prepareStatement(query);

            pstmt.setString(1, isbn);
            pstmt.setString(2, titre);
            pstmt.setString(3, sousTitre);
            pstmt.setString(4, image);
            pstmt.setFloat(5, prix);
            pstmt.setString(6, resume);
            pstmt.setDate(7, datePubli);
            pstmt.setInt(8, quantite);

            query = "select * from Tva where tvaTaux = 5.50";

            Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            String tva = "";

            while (rs.next()) {
                tva = rs.getString("tvaCode");
            }

            pstmt.setString(9, tva);

            query = "select * from Editeur where ediNom = '" + editeur + "'";

            rs = stmt.executeQuery(query);

            int ediId = -1;

            while (rs.next()) {
                ediId = rs.getInt("ediId");
            }

            pstmt.setInt(10, ediId);

            pstmt.executeUpdate();

            for (Auteur a : auteurs) {
                query = "INSERT INTO Redaction VALUES (?,?)";

                pstmt = connexion.prepareStatement(query);

                pstmt.setInt(1, a.getId());
                pstmt.setString(2, isbn);

                pstmt.executeUpdate();
            }

            stmt.close();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Livre.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            connexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(Livre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean auteurEmpty() {
        if (this.auteurs.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public float calculPrixTtc() {
        float prixTtc = ((int) ((this.prix + (prix * tva / 100)) * 100)) / 100;

        return prixTtc;
    }
}
