/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import classes.MotDePasseSQL;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author cdi315
 * Création 24/07/2018, milieu après-midi :
 */
public class BeanClient implements Serializable {

    /*============================== Attributs ==============================*/
    private Long cliNumClient;             //Pour identifiant unique.
    private String cliCivilite;            //UTILISÉ
    private String cliNom;                 //UTILISÉ
    private String cliPrenom;              //UTILISÉ
    private Date cliDateNaiss;             //UTILISÉ
    private String cliEmail;               //UTILISÉ
    private String cliTelDomicile;         //UTILISÉ
    private String cliTelMobile;           //UTILISÉ
    private Short cliStatut;               //TODO : Seuls les "1" peuvent se
                                           //connecter. Les autres auront le
                                           //message "user/pwd invalide ..."
    private String cliMdp;                 //UTILISÉ
    private String cliMdpCompl;            //UTILISÉ
    //Champ auquel le client n'a pas accès : cliCommentaireInterne
    
    
    

    /*============================ Constructeurs ============================*/
    
    /**
     * Constructeur par défaut
     */
    public BeanClient(){
    }

    public BeanClient(Long cliNumClient, String cliCivilite, String cliNom, 
            String cliPrenom, Date cliDateNaiss, String cliEmail, 
            String cliTelDomicile, String cliTelMobile, Short cliStatut) {
        
        this.cliNumClient = cliNumClient;
        this.cliCivilite = cliCivilite;
        this.cliNom = cliNom;
        this.cliPrenom = cliPrenom;
        this.cliDateNaiss = cliDateNaiss;
        this.cliEmail = cliEmail;
        this.cliTelDomicile = cliTelDomicile;
        this.cliTelMobile = cliTelMobile;
        this.cliStatut = cliStatut;
    }
    
    /*============================== Accesseurs ==============================*/

    public Long getCliNumClient() {
        return cliNumClient;
    }

    public void setCliNumClient(Long cliNumClient) {
        this.cliNumClient = cliNumClient;
    }

    public String getCliCivilite() {
        return cliCivilite;
    }

    public void setCliCivilite(String cliCivilite) {
        this.cliCivilite = cliCivilite;
    }

    public String getCliNom() {
        return cliNom;
    }

    public void setCliNom(String cliNom) {
        this.cliNom = cliNom;
    }

    public String getCliPrenom() {
        return cliPrenom;
    }

    public void setCliPrenom(String cliPrenom) {
        this.cliPrenom = cliPrenom;
    }

    public Date getCliDateNaiss() {
        return cliDateNaiss;
    }

    public void setCliDateNaiss(Date cliDateNaiss) {
        this.cliDateNaiss = cliDateNaiss;
    }

    public String getCliEmail() {
        return cliEmail;
    }

    public void setCliEmail(String cliEmail) {
        this.cliEmail = cliEmail;
    }

    public String getCliTelDomicile() {
        return cliTelDomicile;
    }

    public void setCliTelDomicile(String cliTelDomicile) {
        this.cliTelDomicile = cliTelDomicile;
    }

    public String getCliTelMobile() {
        return cliTelMobile;
    }

    public void setCliTelMobile(String cliTelMobile) {
        this.cliTelMobile = cliTelMobile;
    }

    public Short getCliStatut() {
        return cliStatut;
    }

    public void setCliStatut(Short cliStatut) {
        this.cliStatut = cliStatut;
    }

    public String getCliMdp() {
        return cliMdp;
    }

    public void setCliMdp(String cliMdp) {
        this.cliMdp = cliMdp;
    }

    public String getCliMpdCompl() {
        return cliMdpCompl;
    }

    public void setCliMpdCompl(String cliMpdCompl) {
        this.cliMdpCompl = cliMpdCompl;
    }

    @Override
    public String toString() {
        return cliCivilite + " " + cliPrenom + " " + cliNom.toUpperCase();
    }
    
    /*======================== Méthode(s) publique(s) ========================*/

    /*
     * Pour la création d'un nouveau client :
     * TODO : Cependant, si se n'est pas déjà fait, il faudra penser à gueuler en cas de mail déjà existant
     * Ou bien est-ce qu'on est passé à côté parce qu'on n'a pas réfléchi aux contraintes ?
     * Et voir si on peut intercepter avec un try catch approprié ...
     * @param connexion
     * @param civiliteClient
     * @param nomClient
     * @param prenomClient
     * @param eMailClient
     * @param motDePasseSaisi
     * @param dateNaissClient
     * @param telDomicileClient
     * @param telMobileClient
     * @throws java.lang.Exception
     */ 
    
    public void ajouterClient(Connection connexion, String civiliteClient,
            String nomClient, String prenomClient, String eMailClient,
            String motDePasseSaisi, String dateNaissClient, String telDomicileClient, 
            String telMobileClient) throws Exception {
        
        try {
            
            //Commencer à construire la requête d'insertion avec la liste des
            //champs obligatoires :
            String requeteInsertion = "INSERT INTO Client (cliCivilite, " +
                    "cliNom, cliPrenom, cliEmail, cliStatut, cliMdp, cliMdpCompl";
            //TODEL String parenthèseQuery = ")";
            //TODEL String nullItemQuery = "";

            //Si une valeur a été saisie par l'utilisateur pour un champ non
            //obligatoire, alors ajouter le nom du champs à la liste des champs :
            if(!dateNaissClient.isEmpty() || 
                    !dateNaissClient.trim().equalsIgnoreCase("")){
                requeteInsertion += ", cliDateNaiss";
            }
            if(!telDomicileClient.isEmpty() || 
                    !telDomicileClient.trim().equalsIgnoreCase("")){
                requeteInsertion += ", cliTelDomicile";
            }
            if(!telMobileClient.isEmpty() || 
                    !telMobileClient.trim().equalsIgnoreCase("")){
                requeteInsertion += ", cliTelMobile";
            }

            //Parenthèse fermante de la liste des champs de la requête INSERT
            requeteInsertion += ")";
            
            String valeurs = " VALUES (?, ?, ?, ?, ?, ?, ?";

            //Si une valeur a été saisie par l'utilisateur pour un champ non
            //obligatoire, alors ajouter un point d'interrogation à la liste des
            //valeurs :
            if(!dateNaissClient.isEmpty() || 
                    !dateNaissClient.trim().equalsIgnoreCase("")){
                valeurs += " , ? ";
            }
            if(!telDomicileClient.isEmpty() || 
                    !telDomicileClient.trim().equalsIgnoreCase("")){
                valeurs += " ,? ";
            }
            if(!telMobileClient.isEmpty() || 
                    !telMobileClient.trim().equalsIgnoreCase("")){
                valeurs += ", ?";
            }

            //Parenthèse fermante de la liste des valeurs de la requête INSERT :
            requeteInsertion += valeurs + ")";

            System.out.println("dbg requête ajout client :\n" + requeteInsertion);
            
            
            PreparedStatement prepstmt = connexion.prepareStatement(requeteInsertion);
            
            //Assignation des valeurs qui vont remplacer les points
            //d'interrogation :
            
            //1°) Champs obligatoires :
            
            //Civilité :
            int i = 1;
            prepstmt.setString(i, civiliteClient);

            //Nom de famille :
            i++;
            prepstmt.setString(i, nomClient);

            //Prénom :
            i++;
            prepstmt.setString(i, prenomClient);

            //E-mail :
            i++;
            prepstmt.setString(i, eMailClient);

            //Statut : (non saisi par l'utilisateur)
            i++;
            prepstmt.setShort(i, (short) 1); //todo hmap(Actif) TODOPB : valeur en dur => est-ce licite ?
            
            //"Hacher" le mot de passe saisi en assaisonnant avec une pointe de sel :
            MotDePasseSQL monMDPSQL = new MotDePasseSQL(connexion,
                    motDePasseSaisi);
            
            //Mot de passe (après hachage) :
            i++;
            prepstmt.setString(i, monMDPSQL.getMdpApresHachage());
            
            //Grain de sel :
            i++;
            prepstmt.setString(i, monMDPSQL.getMotDePasseCompl());
            
            //2°) Champs facultatifs :
            
            //Date de naissance
            if(!dateNaissClient.isEmpty() || 
                    !dateNaissClient.trim().equalsIgnoreCase("")){
                i++;
                prepstmt.setDate(i, java.sql.Date.valueOf(dateNaissClient));
            }
            
            //Téléphone du domicile :
            if(!telDomicileClient.isEmpty() || 
                    !telDomicileClient.trim().equalsIgnoreCase("")){
                i++;
                prepstmt.setString(i, telDomicileClient);
            }
            
            //Téléphone mobile :
            if(!telMobileClient.isEmpty() || 
                    !telMobileClient.trim().equalsIgnoreCase("")){
                prepstmt.setString(i, telMobileClient);
            }

            //Exécuter la requête d'insertion :
            prepstmt.execute();
            

            
            try {
                prepstmt.close();
            } catch(Exception ex) {
                throw new Exception("Fermeture de l'instruction lors de " +
                        "l'insertion du nouveau client :<br />" + ex.getMessage());
            }
        } catch (SQLException sqlEx) {
            throw new Exception("Erreur SQL lors de l'insertion du nouveau " +
                    "client :<br />" + sqlEx.getErrorCode() + " " +
                    sqlEx.getMessage());
        } catch (Exception ex) {
            throw new Exception("Exception lors de l'insertion du nouveau " +
                    "client :<br />" + ex.getMessage());
        }

        try {
            connexion.close();
        } catch (SQLException sqlEx) {
            throw new Exception("Erreur SQL lors de la fermeture de la " +
                    "connexion servant lors de l'insertion du nouveau " +
                    "client :<br />" + sqlEx.getErrorCode() + " " +
                    sqlEx.getMessage());
        }
    }
    
    public void modifierMotDePasseClient(Connection connexion,
            String nouveauMotDePasse) throws Exception {
        
        try {
            String requeteModificationMotDePasseClient = "UPDATE Client SET "
                + "cliMdp = ?, cliMdpCompl = ? WHERE cliNumClient = ?";
        
            System.out.println("dbg requête màj mdp client :\n" +
                    requeteModificationMotDePasseClient);
        
            //Créer un objet de la classe MotDePasseSQL :
            MotDePasseSQL monMDP = new MotDePasseSQL(connexion,
                    nouveauMotDePasse);
            
            
            PreparedStatement prepstmt = connexion.
                    prepareStatement(requeteModificationMotDePasseClient);
            
            prepstmt.setString(1, monMDP.getMdpApresHachage());
            prepstmt.setString(2, monMDP.getMotDePasseCompl());
            prepstmt.setLong(3, cliNumClient);
            
            prepstmt.executeUpdate();
            
            try {
                prepstmt.close();
            } catch(Exception ex) {
                throw new Exception("Fermeture de l'instruction lors de la " +
                        "modification du mot de passe du client :<br />" +
                        ex.getMessage());
            }
            
        } catch (SQLException sqlEx) {
            throw new Exception("Erreur SQL lors de la modification du mot " +
                    "de passe du client :<br />" + sqlEx.getErrorCode() + " " +
                    sqlEx.getMessage());
        } catch (Exception ex) {
            throw new Exception("Exception lors de la modification du mot " +
                    "de passe du client :<br />" + ex.getMessage());
        }

        try {
            connexion.close();
        } catch (SQLException sqlEx) {
            throw new Exception("Erreur SQL lors de la fermeture de la " +
                    "connexion servant lors de la modification du mot de " +
                    "passe du client :<br />" + sqlEx.getErrorCode() + " " +
                    sqlEx.getMessage());
        }
    }

    
    
    public boolean getClientFromEMailClient(Connection connexion, String eMailSaisi) throws Exception {

        boolean loginIncorrect = false;        
        
        //Déterminer si le login (e-mail) du client est correct ou non :        
        try {
            
            /*
             * Récupérer le(s) client(s) qui sont ACTIFS et qui ont un login
             * (e-mail) identique au login (e-mail) qui a été saisi :
             */
            String requeteSelection = "SELECT * FROM Client WHERE cliEmail = " +
                    "? AND cliStatut = 1";
                    //todo hmap(Actif) TODOPB : valeur en dur => est-ce licite ?
            
            PreparedStatement prepstmt = connexion.prepareStatement(
                    requeteSelection,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
                
            prepstmt.setString(1,eMailSaisi);
            
            ResultSet rsClient = prepstmt.executeQuery();

            //Tentative de passer à l'enregistrement suivant :
            rsClient.next();
            //Détermination du nombre d'enregistrements dans le ResultSet :
            if (rsClient.getInt(1) == 0) {
                //Le login n'a pas été trouvé :
                System.out.println("dbg gNCFEMC login <<" + eMailSaisi + ">> non trouvé");                
                loginIncorrect = true;
            } else {
                //Le login a bien été trouvé.
                System.out.println("dbg gNCFEMC login <<" + eMailSaisi + ">> TROUVÉ !!!!!!");                
                
                cliNumClient = rsClient.getLong("cliNumClient");
                System.out.println("dbg gNCFEMC cliNumClient VAUT <<" + cliNumClient + ">>."); 
                
                cliCivilite = rsClient.getString("cliCivilite");
                System.out.println("dbg gNCFEMC cliCivilite VAUT <<" + cliCivilite + ">>."); 
                
                cliNom = rsClient.getString("cliNom");
                System.out.println("dbg gNCFEMC cliNom VAUT <<" + cliNom + ">>."); 
                
                cliPrenom = rsClient.getString("cliPrenom");
                System.out.println("dbg gNCFEMC cliPrenom VAUT <<" + cliPrenom + ">>."); 
                
                cliDateNaiss = rsClient.getDate("cliDateNaiss");
                System.out.println("dbg gNCFEMC cliDateNaiss VAUT <<" + cliDateNaiss + ">>."); 
                
                cliEmail = rsClient.getString("cliEmail");
                System.out.println("dbg gNCFEMC cliEmail VAUT <<" + cliEmail + ">>."); 

                cliTelDomicile = rsClient.getString("cliTelDomicile");
                System.out.println("dbg gNCFEMC cliTelDomicile VAUT <<" + cliTelDomicile + ">>."); 
                
                cliTelMobile = rsClient.getString("cliTelMobile");
                System.out.println("dbg gNCFEMC cliTelMobile VAUT <<" + cliTelMobile + ">>."); 
                
                cliStatut = rsClient.getShort("cliStatut");
                System.out.println("dbg gNCFEMC cliStatut VAUT <<" + cliStatut + ">>."); 
                
                cliMdp = rsClient.getString("cliMdp");
                System.out.println("dbg gNCFEMC cliMdp VAUT <<" + cliMdp + ">>."); 

                cliMdpCompl = rsClient.getString("cliMdpCompl");
                System.out.println("dbg gNCFEMC cliMdpCompl VAUT <<" + cliMdpCompl + ">>."); 
            }
            
            if (loginIncorrect)
                throw new Exception("L'e-mail est incorrect !");
            
            try {
                rsClient.close();
            } catch(Exception ex) {
                throw new Exception("Fermeture du résultat lors de la " +
                        "détermination du numéro du client :<br />" + ex.getMessage());
            }
            
            try {
                prepstmt.close();
            } catch(Exception ex) {
                throw new Exception("Fermeture de l'instruction lors " +
                        "de la détermination du numéro du client :<br />" +
                        ex.getMessage());
            }
        } catch (SQLException sqlEx) {
            throw new Exception("Erreur SQL lors de la détermination du " +
                    "numéro du client :<br />" + sqlEx.getErrorCode() + " " +
                    sqlEx.getMessage());
        } catch (Exception ex) {
            throw new Exception("Exception lors de la détermination du " +
                    "numéro du client :<br />" + ex.getMessage());
        }

        try {
            connexion.close();
        } catch (SQLException sqlEx) {
            throw new Exception("Erreur SQL lors de la fermeture de la " +
                    "connexion servant à déterminer le numéro du client :<br />" +
                    sqlEx.getErrorCode() + " " + sqlEx.getMessage());
        }
        
        return (! loginIncorrect);
    }
    
    
    
    public boolean motDePasseEstCorrect(Connection connexion, String eMailSaisi, String motDePasseSaisi, Integer nombreTentativesDejaFaites) throws Exception {

        boolean loginOuMDPIncorrect = false;        
        String motDePasseLu;
        String motDePasseCompl;
        boolean motDePasseValide = false;
        Integer nombreTentativesRestantes;
        
        //Déterminer si le login (e-mail) du client est correct ou non :        
        try {
            
            /*
            Déterminer le nombre de clients qui sont ACTIFS et qui ont un
            login (e-mail) identique au login (e-mail) qui a été saisi :
            */
            String requeteSelection = "SELECT COUNT(*) FROM Client WHERE " +
                    "cliEmail = ? AND cliStatut = 1";
                    //todo hmap(Actif) TODOPB : valeur en dur => est-ce licite ?
            
            PreparedStatement prepstmt = connexion.prepareStatement(
                    requeteSelection,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
                
            prepstmt.setString(1,eMailSaisi);
            
            ResultSet rsNombreDeLogin = prepstmt.executeQuery();

            //Tentative de passer à l'enregistrement suivant :
            rsNombreDeLogin.next();
            //Détermination du nombre d'enregistrements dans le ResultSet :
            if (rsNombreDeLogin.getInt(1) == 0) {
                //Le login n'a pas été trouvé :
                System.out.println("dbg mdpEstCorrect login <<" + eMailSaisi + ">> non trouvé");                
                loginOuMDPIncorrect = true;
            } else {
                //Le login a bien été trouvé.
                System.out.println("dbg mdpEstCorrect login <<" + eMailSaisi + ">> TROUVÉ !!!!!!");                
                
//TODEL                //Créer un objet de la classe Employe à partir du login saisi :
//TODEL                Employe unEmploye = new Employe(                          //FLC
//TODEL                        //FLC
//TODEL                        maFenetrePrincipale.getMaConnexionJdbc(), eMailSaisi);
                //Récupération du mot de passe du client :
                motDePasseLu = cliMdp;
                //C'est le "grain de sel"
                motDePasseCompl = cliMdpCompl;
                System.out.println("dbg mdpEstCorrect motDePasseLu affecté : <<" + motDePasseLu + ">>.");
                System.out.println("dbg mdpEstCorrect motDePasseCompl affecté : <<" + motDePasseCompl + ">>.");

///////////////////todel@end                try {
                //Créer un objet de la classe MotDePasseSQL :
                MotDePasseSQL monMDP = new MotDePasseSQL(connexion,
                        motDePasseSaisi,motDePasseLu,motDePasseCompl);
                motDePasseValide = monMDP.motDePasseEstValide();
                    
/*todel@end                    
                } catch (Exception ex) {
                    TODODEV : remplacer par un message d'erreur … ???!!!!
                    Logger.getLogger(JFrameLoginEmploye.class.getName()).log(Level.SEVERE, null, ex);
                }
*/                
                if ( ! motDePasseValide) {
                    //Si le mot de passe qui a été saisi est INVALIDE ! :
                    System.out.println("dbg mdpEstCorrect motDePasseValide = false.");
                    /*
                      Par conséquent, incrémenter le nombre de tentatives
                      (infructueuses) de saisie du mot de passe.
                    */
                    nombreTentativesDejaFaites++;
                    //Si le client a fait trois tentatives (ou plus …) de
                    //connexion infructueuses …
                    if (nombreTentativesDejaFaites >= 3) {
                        // "Verrouiller" le client :
                        System.out.println("dbg mdpEstCorrect tododev : le "+
                                "client va être verrouillé !!!!!!!");
                        //À la troisième tentative infructueuse …
                        /*
                            TODOQ : est-ce qu'on "bloque" le compte client ?
                        if (nombreTentativesDejaFaites == 3)
                            //… "brouiller" le mot de passe de l'employé.
                            unEmploye.brouillerMotDePasseSuiteTentativesInfructueuses();
                        */        
                        //Afficher un message d'erreur à destination de
                        //l'utilisateur :
                        throw new Exception("Vous avez effectué au moins " +
                                "trois tentatives de connexion " +
                                "infructueuses.<br />Aussi, votre compte a " +
                                "été bloqué.<br />Veuillez contacter un " +
                                "administrateur afin qu'il débloque votre " +
                                "compte utilisateur.");
                    } else {
                        nombreTentativesRestantes = 3 - nombreTentativesDejaFaites;
                        //Afficher un message d'avertissement à destination de
                        //l'utilisateur :
                        //NE PAS générer d'exception ici.
                        throw new Exception("Le mot de passe que vous avez "+
                                "saisi est invalide.<br />Veuillez tenter de " +
                                "le saisir à nouveau.<br />Il vous reste " +
                                ((nombreTentativesRestantes >= 2)?
                                nombreTentativesRestantes + " tentatives.":
                                "une toute dernière tentative !"));
                    }
                }
                
            }
            
            if (loginOuMDPIncorrect)
                throw new Exception("L'e-mail ou le mot de passe est incorrect !");
            
            try {
                rsNombreDeLogin.close();
            } catch(Exception ex) {
                throw new Exception("Fermeture du résultat lors du contrôle " +
                        "du mot de passe :<br />" + ex.getMessage());
            }
            
            try {
                prepstmt.close();
            } catch(Exception ex) {
                throw new Exception("Fermeture de l'instruction lors " +
                        "du contrôle du mot de passe :<br />" + ex.getMessage());
            }
        } catch (SQLException sqlEx) {
            throw new Exception("Erreur SQL lors du contrôle du mot de " +
                    "passe :<br />" + sqlEx.getErrorCode() + " " +
                    sqlEx.getMessage());
        } catch (Exception ex) {
            throw new Exception("Erreur lors du contrôle du mot de " +
                    "passe :<br />" + ex.getMessage());
        }

        try {
            connexion.close();
        } catch (SQLException sqlEx) {
            throw new Exception("Erreur SQL lors de la fermeture de la " +
                    "connexion servant à déterminer si le mot de passe est " +
                    "correct :<br />" + sqlEx.getErrorCode() + " " +
                    sqlEx.getMessage());
        }
        
        return motDePasseValide;
    }
            
            
//TODEL : réserve :    
/* TODEL TODEL TODEL TODEL : a priori non utilisé : recherche d'un client    
    public Vector initClientJList(JFormattedTextField champRecherche){
        
        Vector v = new Vector();
        
        Connection connexion = connecterJdbc();
        
        try {
            
            String whereQuery = " WHERE cliNom +' '+ cliPrenom LIKE '%"
                    + champRecherche.getText()+ "%'";
            
            String requete = "Select * from Client";
            
            if(!champRecherche.getText().isEmpty() && 
                !champRecherche.getText().trim().
                        equalsIgnoreCase("")){
                requete += whereQuery;
            }
            System.out.println("dbg reuête liste clients : " + requete);
            
            Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery(requete);
            
            while(rs.next()){
                BeanClient monClient = new BeanClient(
                rs.getLong("cliNumClient"), rs.getString("cliCivilite"),
                rs.getString("cliNom"), rs.getString("cliPrenom"),
                rs.getDate("cliDateNaiss"), rs.getString("cliEmail"),
                rs.getString("cliTelDomicile"), rs.getString("cliTelMobile"),
                rs.getShort("cliStatut"));
                TODO : cliMdp + cliMdpCompl
                
                v.add(monClient);
            }
            
            rs.close();
            stmt.close();
            
        } catch (SQLException sqlEx) {
    todo throw !!!!! 
            System.out.println("Erreur requête SQL : "
                    + sqlEx.getErrorCode() + " / "+ sqlEx.getMessage());
        }
        
        try {
            connexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(BeanCatalogue.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return v;
    }
*/
    
/* TODO : peut peut-être servir pour faire une requête UPDATE si une valeur d'un client est modifiée lors d'une consultation    
    public void modifierClient(JTextField numClient, JComboBox leComboBox,  
            JFormattedTextField nomClient, JFormattedTextField prenomClient, 
            JFormattedTextField dateNaissClient, 
            JFormattedTextField eMailClient, JFormattedTextField telDomicileClient, 
            JFormattedTextField telMobileClient, 
            JFormattedTextField StatutClient, 
            Statut leStatut){
        
        Connection connexion = connecterJdbc();
        
        
        try {
            String requeteModificationMotDePasseClient = "UPDATE Client SET "
                + "cliCivilite = ?, "
                + "cliNom = ?, "
                + "cliPrenom = ?, "
                + "cliDateNaiss = ?, "
                + "cliEmail = ?, "
                + "cliTelDomicile = ?, "
                + "cliTelMobile = ?, "
                + "cliStatut = ?, "
                TODO : cliMdp + cliMdpCompl
                + "WHERE cliNumClient = ?";
        
            System.out.println("dbg requête modif Mdp = " +
                    requeteModificationMotDePasseClient);
        
            PreparedStatement prepstmt = connexion.
                    prepareStatement(requeteModificationMotDePasseClient);
            
            prepstmt.setString(1, String.valueOf(leComboBox.getSelectedItem()));
            prepstmt.setString(2, nomClient.getText().toUpperCase());
            prepstmt.setString(3, prenomClient.getText());
            prepstmt.setDate(4, java.sql.Date.valueOf(dateNaissClient.getText()));
            prepstmt.setString(5, eMailClient.getText());
            prepstmt.setString(6, telDomicileClient.getText());
            prepstmt.setString(7, telMobileClient.getText());
            prepstmt.setShort(8, (short) leStatut.gestionStatut(StatutClient.getText()));
TODO : oups num séquence            prepstmt.setString(9, comInterne.getText());
            prepstmt.setLong(10, Long.valueOf(numClient.getText()));
                TODO : cliMdp + cliMdpCompl
            
            prepstmt.executeUpdate();
            
            prepstmt.close();
            
        } catch (SQLException sqlEx) {
    todo throw !!!!! 
            System.out.println("Erreur requête modification mot de passe : "
                    + ""+ sqlEx.getErrorCode() + " " + sqlEx.getMessage());
        }
        try {
            connexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(BeanCatalogue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
*/

            
    
    
    
}
