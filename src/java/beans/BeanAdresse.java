/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;

/**
 *
 * @author cdi315
 * Création 24/07/2018, milieu après-midi :
 */
public class BeanAdresse implements Serializable {
    
    /*============================== Attributs ==============================*/
    private Long adrId;
    private String adrNumVoie;
    private String adrTypeVoie;
    private String adrNomVoie;
    private String adrComplement;
    private String adrCodePostal;
    private String adrVille;
    private String adrPays;
    private String adrInfosSup;
    private String adrNom;
    private String adrPrenom;
    private String adrCommentaireInterne;
    private Byte adrStatut;
    
    /*============================ Constructeurs ============================*/

    /**
     * Constructeur par défaut
     */
    public BeanAdresse() {
    }
    
    /*============================== Accesseurs ==============================*/

    public Long getAdrId() {
        return adrId;
    }

    public void setAdrId(Long adrId) {
        this.adrId = adrId;
    }

    public String getAdrNumVoie() {
        return adrNumVoie;
    }

    public void setAdrNumVoie(String adrNumVoie) {
        this.adrNumVoie = adrNumVoie;
    }

    public String getAdrTypeVoie() {
        return adrTypeVoie;
    }

    public void setAdrTypeVoie(String adrTypeVoie) {
        this.adrTypeVoie = adrTypeVoie;
    }

    public String getAdrNomVoie() {
        return adrNomVoie;
    }

    public void setAdrNomVoie(String adrNomVoie) {
        this.adrNomVoie = adrNomVoie;
    }

    public String getAdrComplement() {
        return adrComplement;
    }

    public void setAdrComplement(String adrComplement) {
        this.adrComplement = adrComplement;
    }

    public String getAdrCodePostal() {
        return adrCodePostal;
    }

    public void setAdrCodePostal(String adrCodePostal) {
        this.adrCodePostal = adrCodePostal;
    }

    public String getAdrVille() {
        return adrVille;
    }

    public void setAdrVille(String adrVille) {
        this.adrVille = adrVille;
    }

    public String getAdrPays() {
        return adrPays;
    }

    public void setAdrPays(String adrPays) {
        this.adrPays = adrPays;
    }

    public String getAdrInfosSup() {
        return adrInfosSup;
    }

    public void setAdrInfosSup(String adrInfosSup) {
        this.adrInfosSup = adrInfosSup;
    }

    public String getAdrNom() {
        return adrNom;
    }

    public void setAdrNom(String adrNom) {
        this.adrNom = adrNom;
    }

    public String getAdrPrenom() {
        return adrPrenom;
    }

    public void setAdrPrenom(String adrPrenom) {
        this.adrPrenom = adrPrenom;
    }

    public String getAdrCommentaireInterne() {
        return adrCommentaireInterne;
    }

    public void setAdrCommentaireInterne(String adrCommentaireInterne) {
        this.adrCommentaireInterne = adrCommentaireInterne;
    }

    public Byte getAdrStatut() {
        return adrStatut;
    }

    public void setAdrStatut(Byte adrStatut) {
        this.adrStatut = adrStatut;
    }
    
    @Override
    public String toString() {
        return "Adresse{" + "adrId=" + adrId + ", adrNumVoie=" + adrNumVoie + 
                ", adrTypeVoie=" + adrTypeVoie + ", adrNomVoie=" + adrNomVoie + 
                ", adrComplement=" + adrComplement + ", adrCodePostal=" + 
                adrCodePostal + ", adrVille=" + adrVille + ", adrPays=" + 
                adrPays + ", adrInfosSup=" + adrInfosSup + ", adrNom=" + 
                adrNom + ", adrPrenom=" + adrPrenom + ", "
                + "adrCommentaireInterne=" + adrCommentaireInterne + ", "
                + "adrStatut=" + adrStatut + '}';
    }

    /*======================== Méthode(s) publique(s) ========================*/
    
    
}
