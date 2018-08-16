/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classesRubrique;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class CoupDeCoeur {
   private Date dateDebut;
    private Date dateFin;
    private String livreNumIsbn;
    private String livreTitre;
    private String livreSousTitre;
    private Date livreDatePubli;
    private Float livrePrix;
    private String livreImage;
    private String livreResume;
   
   
    
    public CoupDeCoeur() {
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

    @Override
    public String toString() {
        return "CoupDeCoeur{" + "dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", livreNumIsbn=" + livreNumIsbn + ", livreTitre=" + livreTitre + ", livreSousTitre=" + livreSousTitre + ", livreDatePubli=" + livreDatePubli + ", livrePrix=" + livrePrix + ", livreImage=" + livreImage + ", livreResume=" + livreResume + '}';
    }   
}
