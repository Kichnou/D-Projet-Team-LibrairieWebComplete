/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes.commande;

/**
 *
 * @author danie
 */
public class Tva {
    
    //******************************** Attributs *******************************
    private String codeTva;
    private Float tauxTva;
    
    //****************************** Constructeur ******************************

    public Tva() {
    }
    
    //******************************* Accesseurs *******************************

    public String getCodeTva() {
        return codeTva;
    }

    public void setCodeTva(String codeTva) {
        this.codeTva = codeTva;
    }

    public Float getTauxTva() {
        return tauxTva;
    }

    public void setTauxTva(Float tauxTva) {
        this.tauxTva = tauxTva;
    }
    
    //***************************** Autres Methodes ****************************
}
