
package beans;

import java.io.Serializable;
import java.util.Date;

public class BeanRubrique implements Serializable {
    private Long rubriqueId;
    private String nom;
    private String type;
    private Date dateDebut;
    private Date dateFin; 
    private String description;
    private String image;
    private Float pourcentagePromo;
    private String commentaireInterne; 

    public BeanRubrique() {
    }

    public Long getRubriqueId() {
        return rubriqueId;
    }

    public void setRubriqueId(Long rubriqueId) {
        this.rubriqueId = rubriqueId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Float getPourcentagePromo() {
        return pourcentagePromo;
    }

    public void setPourcentagePromo(Float pourcentagePromo) {
        this.pourcentagePromo = pourcentagePromo;
    }

    public String getCommentaireInterne() {
        return commentaireInterne;
    }

    public void setCommentaireInterne(String commentaireInterne) {
        this.commentaireInterne = commentaireInterne;
    }
    
    
}
