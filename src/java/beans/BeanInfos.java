package beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cdi315
 * Création 24/07/2018, milieu après-midi :
*/
public class BeanInfos implements Serializable {

    private HashMap<String, String> map = null;

    //Constructeur par défaut :
    public BeanInfos() {
        this.map = new HashMap();
        //TODO : ici ????? lire toutes les informations dans la table Infos et les
        //inscrire dans "map" via appel(s) à la méthode add
    }

    public void add(String clef, String valeur) {
        String chaine;
        if (map.containsKey(clef)) {
            chaine = map.get(clef);
            map.replace(clef, chaine, valeur);
        } else {
            map.put(clef, valeur);
        }
    }

    public void del(String clef) {
        map.remove(clef);
    }

    public void clean() {
        map.clear();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public int size() {
        return map.size();
    }

    public String get(String clef) {
        String chaine = null;
        if (map.containsKey(clef))
            chaine = map.get(clef);
        return chaine;
    }
    
    public Collection<String> getList() {
        return map.values();
    }

    //Alimenter la HashMap avec les informations lues dans la table Infos :
    public void lireInformations(Connection connexion) throws Exception {
        String requeteLectureInfos = "SELECT * FROM Infos";

        try {
            Statement stmt = connexion.createStatement();

            ResultSet rs = stmt.executeQuery(requeteLectureInfos);

            while (rs.next()) {
                this.add(rs.getString("infLibelle"),rs.getString("infInfo"));
            }

            rs.close();
            stmt.close();
        } catch (SQLException sqlEx) {
            throw new Exception("Erreur SQL lors de la lecture des " +
                    "informations :<br />" + sqlEx.getErrorCode() + " " +
                    sqlEx.getMessage());
        }

        try {
            connexion.close();
        } catch (SQLException sqlEx) {
            throw new Exception("Erreur SQL lors de la fermeture de la " +
                    "connexion servant à lire les informations :<br />" +
                    sqlEx.getErrorCode() + " " + sqlEx.getMessage());
        }
    }
        
        
    
}

/*Réserve :
TODO :
infLibelle
"Adresse"
"Courriel service clients"
"Fax service clients"
"Forme juridique"
"Horaire fermé"
"Horaire Lundi-Vendredi"
"Horaire Samedi"
"Nom"
"SIREN"
"SIRET"
"Téléphone service clients"

*/