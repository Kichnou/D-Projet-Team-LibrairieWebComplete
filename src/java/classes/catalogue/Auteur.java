package classes.catalogue;

import java.io.Serializable;

public class Auteur implements Serializable {
    //ATTRIBUTS
    ////////////////////////////////////////////////////////////////////

    private int id;
    private String prenom;
    private String nom;

    //CONSTRUCTEUR
    /////////////////////////////////////////////////////////////////////
    public Auteur() {
    }

    public Auteur(int id, String prenom, String nom) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
    }

    //ACCESSEURS
    /////////////////////////////////////////////////////////////////////
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    //TOSTRING
    //////////////////////////////////////////////////////////////////////
    @Override
    public String toString() {
        return prenom + " " + nom + " ";
    }
}
