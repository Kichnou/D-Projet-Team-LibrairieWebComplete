package classes.catalogue;

import java.sql.Date;

public class Evaluations {
    //ATTRIBUTS
    /////////////////////////////////////////////////////////////////////
    
    private Long evaId;
    private int note;
    private String commentaire;
    private Long numClient;
    private String isbn;
    private Date dateCom;
    private String adrIp;
    private int statut;
    
    //CONSTRUCTEUR
    //////////////////////////////////////////////////////////////////////

    public Evaluations() {
    }

    public Evaluations(int note, String commentaire) {
        this.note = note;
        this.commentaire = commentaire;
    }
    
    //ACCESSEURS
    ////////////////////////////////////////////////////////////////////////

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Long getEvaId() {
        return evaId;
    }

    public void setEvaId(Long evaId) {
        this.evaId = evaId;
    }

    public Long getNumClient() {
        return numClient;
    }

    public void setNumClient(Long numClient) {
        this.numClient = numClient;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Date getDateCom() {
        return dateCom;
    }

    public void setDateCom(Date dateCom) {
        this.dateCom = dateCom;
    }

    public String getAdrIp() {
        return adrIp;
    }

    public void setAdrIp(String adrIp) {
        this.adrIp = adrIp;
    }

    public int getStatut() {
        return statut;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }
    
    
}
