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
        //Retourner le résultat :
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

            //Fermeture du résultat :
            rs.close();
            
            //Fermeture de l'instruction :
            stmt.close();
            
            //Fermeture de la connexion :
            connexion.close();
            
        } catch (SQLException sqlEx) {
            throw new Exception("Erreur SQL lors de la lecture des " +
                    "informations :<br />" + sqlEx.getErrorCode() + " " +
                    sqlEx.getMessage());
        }

    }
        
}