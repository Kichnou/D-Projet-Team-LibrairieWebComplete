
package classesRubrique;

import java.util.Date;

public class Evenement {
    private Date dateDebut;
    private Date datefin;
    private String image;
    private String nom;

    public Evenement() {
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDatefin() {
        return datefin;
    }

    public void setDatefin(Date datefin) {
        this.datefin = datefin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Evenement{" + "dateDebut=" + dateDebut + ", datefin=" + 
                datefin + ", image=" + image + ", nom=" + nom + '}';
    }

    
}
