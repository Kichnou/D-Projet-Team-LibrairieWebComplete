package classesRubrique;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Promo {

    private Date dateDebut;
    private Date dateFin;
    private String image;
    private String livreNumIsbn;
    private String livreTitre;
    private String livreSousTitre;
    private Date livreDatePubli;
    private Float livrePrix;
    private String livreImage;
    private String livreResume;
    private int livreQuantite;
    private Float pourcentagePromo;
    private Float prixEconomise;
    
    public Promo() {
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLivreNumIsbn() {
        return livreNumIsbn;
    }

    public void setLivreNumIsbn(String livreNumIsbn) {
        this.livreNumIsbn = livreNumIsbn;
    }

    public String getLivreTitre() {
        return livreTitre;
    }

    public void setLivreTitre(String livreTitre) {
        this.livreTitre = livreTitre;
    }

    public String getLivreSousTitre() {
        return livreSousTitre;
    }

    public void setLivreSousTitre(String livreSousTitre) {
        this.livreSousTitre = livreSousTitre;
    }

    public Date getLivreDatePubli() {
        return livreDatePubli;
    }

    public void setLivreDatePubli(Date livreDatePubli) {
        this.livreDatePubli = livreDatePubli;
    }

    public Float getLivrePrix() {
        return livrePrix;
    }

    public void setLivrePrix(Float livrePrix) {
        this.livrePrix = livrePrix;
    }

    public String getLivreImage() {
        return livreImage;
    }

    public void setLivreImage(String livreImage) {
        this.livreImage = livreImage;
    }

    public String getLivreResume() {
        return livreResume;
    }

    public void setLivreResume(String livreResume) {
        this.livreResume = livreResume;
    }

    public int getLivreQuantite() {
        return livreQuantite;
    }

    public void setLivreQuantite(int livreQuantite) {
        this.livreQuantite = livreQuantite;
    }

    public Float getPourcentagePromo() {
        return pourcentagePromo;
    }

    public void setPourcentagePromo(Float pourcentagePromo) {
        this.pourcentagePromo = pourcentagePromo;
    }

    public Float getPrixEconomise() {
        return prixEconomise;
    }

    public void setPrixEconomise(Float prixEconomise) {
        this.prixEconomise = prixEconomise;
    }
    public void calculateSumSaved(){
     
        this.prixEconomise = 0F;
        float prix = (livrePrix*pourcentagePromo)/100; 
        this.prixEconomise = livrePrix - prix ;
    }
    @Override
    public String toString() {
        return "Promo{" + "dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", image=" + image + ", livreNumIsbn=" + livreNumIsbn + ", livreTitre=" + livreTitre + ", livreSousTitre=" + livreSousTitre + ", livreDatePubli=" + livreDatePubli + ", livrePrix=" + livrePrix + ", livreImage=" + livreImage + ", livreResume=" + livreResume + '}';
    }
    
}        
    