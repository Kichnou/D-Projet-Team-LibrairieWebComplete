package controller;

import beans.BeanClient;
import beans.BeanConnect;
import beans.BeanInfos;
import beans.BeanParticipant;
import beans.catalogue.BeanCatalogue;
import beans.commande.BeanPanier;
import classes.catalogue.Evaluations;
import classes.commande.LigneDeCommande;
import classes.catalogue.Livre;
import classes.catalogue.SousTheme;
import classes.catalogue.Theme;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Controller", urlPatterns = {"/Controller"})
public class Controller extends HttpServlet {

    private Cookie getCookie(Cookie[] cookies, String name) {
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(name)) {
                    return c;
                }
            }
        }
        return null;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        System.out.println("dbg £45 atteint.");
        
        //NOTA : pour ne pas être emm… par les caractères accentués (àçéèô …)
        request.setCharacterEncoding("UTF-8");
        System.out.println("dbg £49 atteint.");

        //Par défaut, URL pointant vers la page d'accueil du site :
        String url = "/WEB-INF/Accueil.jsp";
        System.out.println("dbg £53 atteint.");

        //Récupérer la section courante :
        String section = request.getParameter("section");
        System.out.println("dbg £57 atteint.");
        
        if (section != null)
            System.out.println("dbg section courante en cours d'exécution : <<" + section + ">>.");            
        
        
        //Déclarer un certain nombre de variables :
        Cookie cookieClientConnecte;
        Cookie cookieNbTentatives;
        Integer nombreTentativesDejaFaites;
        Long numeroClient = 0L;
        String civiliteClient;
        String dateNaissClient;
        String eMailClient;
        String motDePasseSaisi;
        String nomClient;
        String prenomClient;
        String telDomicileClient;
        String telMobileClient;
        
        //Message en cas d'erreur lors du changement du mot de passe du client :
        String messageErreurChangementMotDePasse = "";
        //Message en cas d'erreur lors de la connexion du client :
        String messageErreurConnexionClient = "";
        //Message en cas d'erreur lors de la creation du compte client :
        String messageErreurCreationCompteClient = "";
        //Message en cas d'erreur lors de la deconnexion du client :
        String messageErreurDeconnexionClient = "";
        //Message lorsque le mot de passe a été changé avec succès :
        String messageMotDePasseChangeAvecSucces = "";
        //Message lorsque le compte client a été créé avec succès :
        String messageCompteClientCreeAvecSucces = "";

        HttpSession session = request.getSession();
        ServletContext application = this.getServletContext();

        BeanConnect connect = (BeanConnect) application.getAttribute("connection");
        if (connect == null) {
            connect = new BeanConnect();
            application.setAttribute("connection", connect);
        }

        System.out.println("dbg BeanConnect passé.");            
        
        BeanCatalogue catalogue = (BeanCatalogue) application.getAttribute("catalogue");
        if (catalogue == null) {
            catalogue = new BeanCatalogue();
            application.setAttribute("catalogue", catalogue);
        }
        
        System.out.println("dbg BeanCatalogue passé.");            

        BeanPanier lePanier = (BeanPanier) session
                .getAttribute("BeanCommande");
        if (lePanier == null) {
            lePanier = new BeanPanier();
            session.setAttribute("BeanCommande", lePanier);
        }

        System.out.println("dbg BeanPanier passé.");            

        if (catalogue.getMesThemes().isEmpty()) {
            catalogue.remplirThemesCatalogue(connect.getInstance());
        }

        request.setAttribute("themes", catalogue.getMesThemes());

        if (request.getParameter("themeSelectionne") != null) {
            request.setAttribute("themeSelectionneAfficher", request.getParameter("themeSelectionne"));
            Theme leTheme = null;
            for (Theme t : catalogue.getMesThemes()) {
                if (t.getNom().equals(request.getParameter("themeSelectionne"))) {
                    leTheme = t;
                }
            }

            request.setAttribute("sousThemes", leTheme.getListeSousThemes());
        }

        //Lire les informations dans la table de façon à ce qu'elles soient
        //affichées dans bottom.jsp :
        try {
            BeanInfos infos = (BeanInfos) session.getAttribute("infos");
            if (infos == null) {
                infos = new BeanInfos();
                /*
                 * Alimenter la HashMap avec les informations lues dans la
                 * table Infos :
                 */
                infos.lireInformations(connect.getInstance());
                session.setAttribute("infos",infos);
            }

            /*
             * Lire les informations individuelles qui seront affichées dans
             * bottom.jsp :
             */
            request.setAttribute("adresse", infos.get("Adresse"));
            request.setAttribute("krlsercli", infos.get("Courriel service clients"));
            request.setAttribute("faxsercli", infos.get("Fax service clients"));
            request.setAttribute("formjur", infos.get("Forme juridique"));
            request.setAttribute("horfermeture", infos.get("Horaire fermé"));
            request.setAttribute("horlunven", infos.get("Horaire Lundi-Vendredi"));
            request.setAttribute("horsam", infos.get("Horaire Samedi"));
            request.setAttribute("nom", infos.get("Nom"));
            request.setAttribute("siren", infos.get("SIREN"));
            request.setAttribute("siret", infos.get("SIRET"));
            request.setAttribute("telsercli", infos.get("Téléphone service clients"));
        } catch (Exception ex) {
            //En cas d'exception jetée par un bean ou une classe, afficher
            //la page d'erreur fatale :
            url = "/WEB-INF/jspFatalError.jsp";
            request.setAttribute("messageErreurFatale", ex.getMessage());
        }

        System.out.println("dbg BeanInfos passé.");            
        
        //BeanClient :
        BeanClient leClient = (BeanClient) session.getAttribute("client");
        if (leClient == null) {
            leClient = new BeanClient();
            System.out.println("dbg le bean client vient d'être créé.");
            session.setAttribute("client", leClient);
        }

        System.out.println("dbg BeanClient passé.");            
        if (leClient.getCliCivilite() == null) {
            System.out.println("dbg £180 BeanClient actuel == <<VIDE>>.");            
        } else {
            System.out.println("dbg £182 BeanClient actuel == <<"+leClient.toString()+">>.");            
        }
        
        if (section == null || "accueil".equals(section)) {
            url = "/WEB-INF/Accueil.jsp";
        }
       
        /*
          Si l'utilisateur a cliqué sur le bouton "Valider"
          dans "clientChangementMotDePasse.jsp" :
        */
        if ("clientChangementMotDePasse".equals(section)) {
            System.out.println("dbg section clientChangementMotDePasse : IN");            
            url = "/WEB-INF/client/clientChangementMotDePasse.jsp";
            System.out.println("dbg future url : clientChangementMotDePasse.jsp.");

            if (leClient.getCliCivilite() == null) {
                System.out.println("dbg £199 BeanClient actuel == <<VIDE>>.");            
            } else {
                System.out.println("dbg £201 BeanClient actuel == <<"+leClient.toString()+">>.");            
            }
            
            //Alimenter les variables nécessaires pour la page :
            request.setAttribute("cliCivilite", leClient.getCliCivilite());
            request.setAttribute("cliNom", leClient.getCliNom());
            request.setAttribute("cliPrenom", leClient.getCliPrenom());
            request.setAttribute("cliEmail", leClient.getCliEmail());

            //Si l'utilisateur a cliqué sur le bouton nommé
            //"validerNouveauMotDePasse" :
            if (request.getParameter("validerNouveauMotDePasse") != null) {
                System.out.println("dbg utilisateur a cliqué sur le bouton validerNouveauMotDePasse.");
                String nouveauMotDePasseSaisi = request.getParameter("motdepasse");
                System.out.println("dbg getParameter £205 nouveau mot de passe saisi : <<"+nouveauMotDePasseSaisi+">>.");
                try {
                    leClient.modifierMotDePasseClient(connect.getInstance(),
                            nouveauMotDePasseSaisi);
                    System.out.println("dbg le nouveau mot de passe saisi a été inscrit avec succès dans la table.");
                    //Retourner à clientConsultation.jsp :
                    url = "/WEB-INF/client/clientConsultation.jsp";
                    //Et y afficher un message en vert :
                    messageMotDePasseChangeAvecSucces = "Votre mot de passe " +
                            "a été changé avec succès.";
                } catch (Exception ex) {
                    System.out.println("dbg EXCEPTION lors du changement du mot de passe du client !");            
                    //Le message de l'exception sera affiché en rouge dans la
                    //page clientChangementMotDePasse.jsp :
                    messageErreurChangementMotDePasse = ex.getMessage();
                }
            }
        
            System.out.println("dbg l'éventuel message de succès lors du changement du mot de passe est : "+(messageMotDePasseChangeAvecSucces.equals("")?"<<VIDE>>":"<<"+messageMotDePasseChangeAvecSucces+">>")+".");            
            request.setAttribute("messageMotDePasseChangeAvecSucces",
                    messageMotDePasseChangeAvecSucces);
            
            System.out.println("dbg l'éventuel message d'erreur lors du changement du mot de passe est : "+(messageErreurChangementMotDePasse.equals("")?"<<VIDE>>":"<<"+messageErreurChangementMotDePasse+">>")+".");            
            request.setAttribute("messageErreurChangementMotDePasse",
                    messageErreurChangementMotDePasse);
            System.out.println("dbg section clientChangementMotDePasse : OUT");            
        
        
        }
        
        /*
          Si l'utilisateur a cliqué sur le bouton "Changer votre mot de passe"
          dans "clientConsultation.jsp", alors clientChangementMotDePasse.jsp
          est exécuté.
        */
        if ("clientChangerMdp".equals(section)) {
            System.out.println("dbg section clientChangerMdp : IN");            
            url = "/WEB-INF/client/clientChangementMotDePasse.jsp";
            System.out.println("dbg future url : clientChangementMotDePasse.jsp.");
            
            //Alimenter les variables nécessaires pour la future page :
            request.setAttribute("cliCivilite", leClient.getCliCivilite());
            request.setAttribute("cliNom", leClient.getCliNom());
            request.setAttribute("cliPrenom", leClient.getCliPrenom());
            request.setAttribute("cliEmail", leClient.getCliEmail());
            System.out.println("dbg £265 variables alimentées pour la future page <<"+url+">>.");
            
            System.out.println("dbg section clientChangerMdp : OUT");            
            //TODOTEST : verdict.
        }
        
        /*
          Si l'utilisateur a cliqué sur le bouton "Connexion" dans
          "clientConnexion.jsp", alors :
        */
        if ("clientConnexion".equals(section)) {
            System.out.println("dbg section clientConnexion : IN");            

            //Si l'utilisateur a cliqué sur le bouton nommé
            //"connecterClient" :
            if (request.getParameter("connecterClient") != null) {
                System.out.println("dbg utilisateur a cliqué sur le bouton connecterClient.");

                //Lire l'adresse e-mail et le mot de passe saisis :
                eMailClient = request.getParameter("email");
                System.out.println("dbg getParameter £284 eMailClient : <<"+eMailClient+">>.");
                motDePasseSaisi = request.getParameter("motdepasse");
                System.out.println("dbg getParameter £286 motDePasseSaisi : <<"+motDePasseSaisi+">>.");

                try {
                    //En déduire les informations relatives au client :
                    if (leClient.getClientFromEMailClient(
                            connect.getInstance(), eMailClient)) {
                        numeroClient = leClient.getCliNumClient();
                        System.out.println("dbg £283 le numéro du client qui en est déduit est : <<"+numeroClient+">>.");
                    }
                } catch(Exception ex) {
                    System.out.println("dbg EXCEPTION lors de la recherche du client par son e-mail (occ 1/3) !");            
                    /*
                     * Le message d'erreur suivant sera affiché en rouge dans la
                     * page clientConnexion.jsp :
                     */
                    messageErreurConnexionClient = "Le client ayant pour " +
                            "adresse mail " + eMailClient + " est " +
                            "introuvable !";
                }
                
                //Par défaut, le mot de passe saisi par le client n'est pas
                //correct :
                boolean motDePasseOk = false;
                
                if (numeroClient != 0L) {
                    //Lire le nombre de tentatives déjà faites dans le cookie :
                    System.out.println("dbg lecture du cookie du nombre de tentatives liées au client n° <<"+numeroClient+">>.");            
                    cookieNbTentatives = getCookie(request.getCookies(), "essai" +
                            numeroClient);
                    if (cookieNbTentatives == null) {
                        nombreTentativesDejaFaites = 0;
                        System.out.println("dbg le client n° <<"+numeroClient+">> n'a pas déjà tenté de se connecter.");            
                    } else {
                        nombreTentativesDejaFaites = cookieNbTentatives.getValue().
                                length();
                        System.out.println("dbg le client n° <<"+numeroClient+">> a déjà tenté de se connecter <<"+nombreTentativesDejaFaites+">> fois.");            
                    }

                    try {
                        System.out.println("dbg le mot de passe du client est-il correct ?");            
                        motDePasseOk = leClient.motDePasseEstCorrect(
                                connect.getInstance(),eMailClient,motDePasseSaisi,
                                nombreTentativesDejaFaites);
                        System.out.println("dbg réponse => <<"+motDePasseOk+">>.");            
                    } catch(Exception ex) {
                        //Le message de l'exception sera affiché en rouge dans la
                        //page clientConnexion.jsp :
                        System.out.println("dbg EXCEPTION lors de la vérification de la validité du mot de passe du client.");            
                        messageErreurConnexionClient = ex.getMessage();
                    }

                } else {
                    System.out.println("dbg £341 anomalie client n° = zéro !");
                }


                if (motDePasseOk) {
                    
////TODOANA : TODO OU PAS ?                    //Lire l'adresse e-mail et le mot de passe saisis :
////TODOANA : TODO OU PAS ?                    request.setParameter("email",eMailClient);
////TODOANA : TODO OU PAS ?                    "email"
                    
                    //L'utilisateur a réussi à se connecter. Le contrôleur lui
                    //donne donc accès à la page clientConsultation.jsp :
                    System.out.println("dbg le mot de passe du client est correct.");            
                    url = "/WEB-INF/client/clientConsultation.jsp";
                    System.out.println("dbg future url : clientConsultation.jsp.");
                    
                    if (numeroClient != 0L) {
                        /*
                          Réinitialiser le nombre de tentatives (infructueuses) de
                          saisie du mot de passe à 0 :
                        */
                        cookieNbTentatives = new Cookie("essai" + numeroClient, "");
                        cookieNbTentatives.setMaxAge(0);
                        response.addCookie(cookieNbTentatives);
                        System.out.println("dbg le nombre de tentatives a été réinitialisé à zéro.");

                        cookieClientConnecte = new Cookie("connecte" + numeroClient,
                                eMailClient);
                        response.addCookie(cookieClientConnecte);
                        System.out.println("dbg cookie client connecté créé.");
                    } else {
                        System.out.println("dbg £372 anomalie client n° = zéro !");
                    }

                    //Alimenter les variables nécessaires pour la future page :
                    request.setAttribute("cliCivilite", leClient.getCliCivilite());
                    //TODOUUUUU QQUUUUUU : exécutera OK ou pas ? Date !!!!
                    request.setAttribute("cliDateNaiss", leClient.getCliDateNaiss());
                    request.setAttribute("cliEmail", leClient.getCliEmail());
                    request.setAttribute("cliNom", leClient.getCliNom());
                    request.setAttribute("cliPrenom", leClient.getCliPrenom());
                    request.setAttribute("cliTelDomicile", leClient.getCliTelDomicile());
                    request.setAttribute("cliTelMobile", leClient.getCliTelMobile());
                    System.out.println("dbg £376 variables alimentées pour la future page <<"+url+">>.");
                } else {
                    //L'utilisateur n'a PAS réussi à se connecter.
                    System.out.println("dbg le mot de passe du client est incorrect !");            
                    //TODOUUUUUU : PBPB : faut-il (ou pas) ????
                    //request.setAttribute("cliEmail", eMailClient);
                    //L'utilisateur DOIT donc rester sur la page de départ :
                    url = "/WEB-INF/client/clientConnexion.jsp";
                    System.out.println("dbg future url : l'utilisateur DOIT rester sur clientConnexion.jsp.");

                    if (numeroClient != 0L) {
                        //Essayer de lire le cookie du nombre de tentatives :
                        cookieNbTentatives = getCookie(request.getCookies(),
                                "essai" + numeroClient);
                        if (cookieNbTentatives == null) {
                            //Le cookie du nombre de tentatives n'existe pas déjà :
                            System.out.println("dbg premier échec de connexion du client n° <<"+numeroClient+">>.");
                            cookieNbTentatives = new Cookie("essai" + numeroClient,
                                    "*");
                            System.out.println("dbg cookie cookieNbTentatives créé.");
                        } else {
                            //Le cookie du nombre de tentatives existe déjà :
                            System.out.println("dbg Nième échec de connexion du client n° <<"+numeroClient+">>.");
                            cookieNbTentatives = new Cookie("essai" + numeroClient,
                                    cookieNbTentatives.getValue() + "*");
                            System.out.println("dbg cookie cookieNbTentatives mis à jour.");
                        }
                        if (cookieNbTentatives.getValue().length() > 3) {
                            System.out.println("dbg 4 tentatives ou plus => cookieNbTentatives expirera dans 20 minutes.");
                            cookieNbTentatives.setMaxAge(20 * 60); //20 minutes
                        }
                        response.addCookie(cookieNbTentatives);
                    } else {
                        System.out.println("dbg £417 anomalie client n° = zéro !");
                    }

                }
            }        
        
            System.out.println("dbg l'éventuel message d'erreur lors de la connexion du client est : "+(messageErreurConnexionClient.equals("")?"<<VIDE>>":"<<"+messageErreurConnexionClient+">>")+".");            
        
            request.setAttribute("messageErreurConnexionClient",
                    messageErreurConnexionClient);
        
            System.out.println("dbg section clientConnexion : OUT");            
        
            
        }
        
        //Section clientConsultation :
        if ("clientConsultation".equals(section)) {
            System.out.println("dbg section clientConsultation : IN");            
            
            url = "/WEB-INF/client/clientConsultation.jsp";
            System.out.println("dbg future url : clientConsultation.jsp.");

            //Alimenter les variables nécessaires pour la future page :
            request.setAttribute("cliCivilite", leClient.getCliCivilite());
            request.setAttribute("cliNom", leClient.getCliNom());
            request.setAttribute("cliPrenom", leClient.getCliPrenom());
            //TODOUUUUU QQUUUUUU : exécutera OK ou pas ? Date !!!!
            request.setAttribute("cliDateNaiss", leClient.getCliDateNaiss());
            request.setAttribute("cliEmail", leClient.getCliEmail());
            request.setAttribute("cliTelDomicile", leClient.getCliTelDomicile());
            request.setAttribute("cliTelMobile", leClient.getCliTelMobile());
            System.out.println("dbg £441 variables alimentées pour la future page <<"+url+">>.");
            
            //Si l'utilisateur a cliqué sur le bouton nommé
            //"modifierCompteClient" :
            if (request.getParameter("modifierCompteClient") != null) {
                
                eMailClient = request.getParameter("cliEmail");
                System.out.println("dbg getParameter £454 eMailClient : <<"+eMailClient+">>.");

                prenomClient = request.getParameter("cliPrenom");
                System.out.println("dbg getParameter £457 prenomClient : <<"+prenomClient+">>.");

                nomClient = request.getParameter("cliNom");
                System.out.println("dbg getParameter £460 nomClient : <<"+nomClient+">>.");

                dateNaissClient = request.getParameter("cliDateNaiss");
                System.out.println("dbg getParameter £463 dateNaissClient : <<"+dateNaissClient+">>.");

                telDomicileClient = request.getParameter("cliTelDomicile");
                System.out.println("dbg getParameter £466 telDomicileClient : <<"+telDomicileClient+">>.");

                telMobileClient = request.getParameter("cliTelMobile");
                System.out.println("dbg getParameter £469 telMobileClient : <<"+telMobileClient+">>.");
                
                try {
                    leClient.modifierClient(connect.getInstance(),
                            leClient.getCliNumClient(), nomClient, prenomClient,
                            dateNaissClient, eMailClient, telDomicileClient,
                            telMobileClient);
                } catch (Exception ex) {
                    //TODO
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            
            
            
            System.out.println("dbg section clientConsultation : OUT");            
        }

        /*
          Si l'utilisateur a cliqué sur le bouton "Valider" dans
          "clientCreationCompte.jsp", alors :
        */
        if ("clientCreationCompte".equals(section)) {
            System.out.println("dbg section clientCreationCompte : IN");
			
            url = "/WEB-INF/client/clientCreationCompte.jsp";
            System.out.println("dbg future url : clientCreationCompte.jsp.");

            //Alimenter les variables nécessaires pour la future page :
            request.setAttribute("cliNom", leClient.getCliNom());
            request.setAttribute("cliPrenom", leClient.getCliPrenom());
            request.setAttribute("cliEmail", leClient.getCliEmail());
            System.out.println("dbg £458 variables alimentées pour la future page <<"+url+">>.");
            
            //Si l'utilisateur a cliqué sur le bouton nommé
            //"validerCreationCompte" :
            if (request.getParameter("validerCreationCompte") != null) {
                System.out.println("dbg l'utilisateur a cliqué sur le bouton validerCreationCompte.");
                
                civiliteClient = request.getParameter("groupeCivilite");
                System.out.println("dbg getParameter £436 civiliteClient : <<"+civiliteClient+">>.");
                dateNaissClient = "";
                eMailClient = request.getParameter("email");
                System.out.println("dbg getParameter £439 eMailClient : <<"+eMailClient+">>.");
                motDePasseSaisi = request.getParameter("motdepasse");
                System.out.println("dbg getParameter £441 motDePasseSaisi : <<"+motDePasseSaisi+">>.");
                nomClient = request.getParameter("nom");
                System.out.println("dbg getParameter £443 nomClient : <<"+nomClient+">>.");
                prenomClient = request.getParameter("prenom");
                System.out.println("dbg getParameter £445 prenomClient : <<"+prenomClient+">>.");
                telDomicileClient = "";
                telMobileClient = "";

                //ATTENTION : il FAUT empêcher l'ajout d'un client avec un
                //courriel déjà existant !
                try {
                    //En déduire les informations relatives au client :
                    if (leClient.getClientFromEMailClient(
                            connect.getInstance(), eMailClient)) {
                        System.out.println("dbg un client existe déjà avec l'adresse mail <<"+eMailClient+">>.");            
                        //Le message d'erreur suivant sera affiché en rouge dans
                        //la page clientConnexion.jsp :
                        messageErreurCreationCompteClient = "Il existe déjà " +
                                "un client avec cette adresse mail !";
                    }
                } catch(Exception ex) {
                    System.out.println("dbg EXCEPTION lors de la recherche du client par son e-mail (occ 3/3) !");            
                    //Le message de l'exception sera affiché en rouge dans la
                    //page clientConnexion.jsp :
                    messageErreurCreationCompteClient = ex.getMessage();
                }

                //Si le message d'erreur est encore vide :
                if( ! messageErreurCreationCompteClient.equals("")) {
                    //Tenter d'ajouter le nouveau client dans la table :
                    try {
                        leClient.ajouterClient(connect.getInstance(), civiliteClient,
                                nomClient, prenomClient, eMailClient,
                                motDePasseSaisi, dateNaissClient, telDomicileClient,
                                telMobileClient);
                        System.out.println("dbg le nouveau client a été ajouté dans la table avec succès.");

                        /*
                         * Le nouveau client ayant été ajouté dans la table
                         * avec succès, l'utilisateur se trouve redirigé vers
                         * la page de connexion au compte client :
                         */
                        url = "/WEB-INF/client/clientConnexion.jsp";
                        System.out.println("dbg future url : clientConnexion.jsp.");
                        //Et y afficher un message en vert :
                        messageCompteClientCreeAvecSucces = "Votre compte " +
                                "client a été créé avec succès.<br />Vous " +
                                "pouvez vous y connecter ci-dessus afin de " +
                                "fournir des informations complémentaires.";
                    } catch (Exception ex) {
                        System.out.println("dbg EXCEPTION lors de l'ajout du nouveau client dans la table.");
                        //Le message de l'exception sera affiché en rouge dans la
                        //page clientCreationCompte.jsp :
                        messageErreurCreationCompteClient = ex.getMessage();
                    }
                }
                
            }
            
            System.out.println("dbg l'éventuel message de succès lors de la création du compte client est : "+(messageCompteClientCreeAvecSucces.equals("")?"<<VIDE>>":"<<"+messageCompteClientCreeAvecSucces+">>")+".");            
            request.setAttribute("messageCompteClientCreeAvecSucces",
                    messageCompteClientCreeAvecSucces);
            
            System.out.println("dbg l'éventuel message d'erreur lors de la création du compte client est : "+(messageErreurCreationCompteClient.equals("")?"<<VIDE>>":"<<"+messageErreurCreationCompteClient+">>")+".");            
        
            request.setAttribute("messageErreurCreationCompteClient",
                    messageErreurConnexionClient);
            
            System.out.println("dbg section clientCreationCompte : OUT");            
        }

        /*
          Si l'utilisateur a cliqué sur le bouton "Créez votre compte" dans
          "clientConnexion.jsp", alors clientCreationCompte.jsp est exécuté :
        */
        if ("clientCreerCompte".equals(section)) {
            System.out.println("dbg section clientCreerCompte : IN");  
        
            //Si l'utilisateur a cliqué sur le bouton nommé
            //"creerCompteClient" :
            if (request.getParameter("creerCompteClient") != null) {
                System.out.println("dbg l'utilisateur a cliqué sur le bouton creerCompteClient.");
                url = "/WEB-INF/client/clientCreationCompte.jsp";
                System.out.println("dbg future url : clientCreationCompte.jsp.");
            }        
        
            System.out.println("dbg section clientCreerCompte : OUT");            
            //TODOTEST : TODOverdict.
        }
        
        /*
          Si l'utilisateur a cliqué sur le bouton "Vous déconnecter" dans
          "clientConsultation.jsp", alors on "tue" le cookie et on retourne à
          la page d'accueil :
        */
        if ("clientDeconnexion".equals(section)) {
            System.out.println("dbg section clientDeconnexion : IN"); 

            //Par défaut, on reste sur la page clientConsultation.jsp :
            url = "/WEB-INF/client/clientConsultation.jsp";

            //Si l'utilisateur a cliqué sur le bouton nommé
            //"deconnecterClient" :
            if (request.getParameter("deconnecterClient") != null) {
                System.out.println("dbg l'utilisateur a cliqué sur le bouton deconnecterClient.");
                //Récuperer l'e-mail du client :
                eMailClient = leClient.getCliEmail();
                System.out.println("dbg £549 eMailClient : <<"+eMailClient+">>.");

                try {
                    //En déduire les informations relatives au client :
                    if (! leClient.getClientFromEMailClient(
                            connect.getInstance(), eMailClient)) {
                        System.out.println("dbg ERREUR : client introuvable par son e-mail (occ 2/3) !");            
                        //Le message d'erreur suivant sera affiché en rouge dans
                        //la page clientConnexion.jsp :
                        messageErreurDeconnexionClient = "Le client ayant " +
                                "pour adresse mail " + eMailClient + " est " +
                                "introuvable !";
                    } else {
                        numeroClient = leClient.getCliNumClient();
                        System.out.println("dbg £577 le numéro du client qui en est déduit est : <<"+numeroClient+">>.");
                    }
                } catch(Exception ex) {
                    System.out.println("dbg EXCEPTION lors de la recherche du client par son e-mail (occ 2/3) !");            
                    //Le message de l'exception sera affiché en rouge dans la
                    //page clientConnexion.jsp :
                    messageErreurDeconnexionClient = ex.getMessage();
                }
                
                if (numeroClient != 0L) {
                    //Chercher le cookie "connecte" du client :
                    cookieClientConnecte = getCookie(request.getCookies(),
                            "connecte" + numeroClient);
                    if (cookieClientConnecte == null) {
                        /*
                         * Anomalie : le cookie de connexion du client n'a pas
                         * été trouvé.
                         */
                        messageErreurDeconnexionClient = "Anomalie : le cookie " +
                                "de connexion du client n° " + numeroClient +
                                " est introuvable !";
                        System.out.println("dbg ANOMALIE : le cookie cookieClientConnecte non trouvé pour le client n° <<"+numeroClient+">> !");            
                    } else {
                        System.out.println("dbg le cookie cookieClientConnecte a été bien trouvé pour le client n° <<"+numeroClient+">>.");            
                        /*
                         * "Supprimer" le cookie du navigateur du poste client
                         * en affectant la valeur 0 à son MaxAge :
                         */
                        cookieClientConnecte.setMaxAge(0);
                        response.addCookie(cookieClientConnecte);
                        System.out.println("dbg le cookie cookieClientConnecte pour le client n° <<"+numeroClient+">> vient d'être supprimé.");            
                    }
                } else {
                    System.out.println("dbg £634 anomalie client n° = zéro !");
                }
                
                //S'il n'y a pas de message d'erreur de déconnexion :
                if (messageErreurDeconnexionClient.equals("")) {
                    //L'utilisateur est redirigé vers la page d'accueil :
                    url = "/WEB-INF/Accueil.jsp";
                    System.out.println("dbg future url : Accueil.jsp.");
                }
            }

            System.out.println("dbg l'éventuel message d'erreur lors de la deconnexion du client est : "+(messageErreurDeconnexionClient.equals("")?"<<VIDE>>":"<<"+messageErreurDeconnexionClient+">>")+".");            

            request.setAttribute("messageErreurDeconnexionClient",
                    messageErreurDeconnexionClient);

            System.out.println("dbg section clientDeconnexion : OUT");            
            //TODOTEST : TODOverdict.
        }

        /*
          Si l'utilisateur a cliqué sur le bouton "Connexion" ou le bouton
          "Créer votre compte" dans "Header.jsp", alors :
        */
        if ("headerClient".equals(section)) {
            System.out.println("dbg section headerClient : IN");            

            //Si l'utilisateur a cliqué sur le bouton nommé
            //"connecterClient" :
            if (request.getParameter("connecterClient") != null) {
                //Appeler la page clientConnexion.jsp :
                System.out.println("dbg l'utilisateur a cliqué sur le bouton connecterClient.");
                url = "/WEB-INF/client/clientConnexion.jsp";
                System.out.println("dbg future url : clientConnexion.jsp.");
            }
            
            
            
            //Si l'utilisateur a cliqué sur le bouton nommé
            //"creerCompteClient" :
            if (request.getParameter("creerCompteClient") != null) {
                System.out.println("dbg l'utilisateur a cliqué sur le bouton creerCompteClient.");
                //Appeler la page clientCreationCompte.jsp :
                url = "/WEB-INF/client/clientCreationCompte.jsp";
                System.out.println("dbg future url : clientCreationCompte.jsp.");
            }

            System.out.println("dbg section headerClient : OUT");            
        }    
        
        
        if ("recherche".equals(section)) {
            url = "/WEB-INF/catalogue/ResultatRecherche.jsp";

            HashMap<String, Livre> resultatRecherche = new HashMap();
            

            ArrayList<Livre> temp = catalogue.resultatRechercheAuteur(request.getParameter("motRecherche"), connect.getInstance());

            for (Livre l : temp) {
                if (resultatRecherche.get(l.getIsbn()) == null) {
                    resultatRecherche.put(l.getIsbn(), l);
                }
            }

            temp = catalogue.resultatRechercheIsbn(request.getParameter("motRecherche"), connect.getInstance());

            for (Livre l : temp) {
                if (resultatRecherche.get(l.getIsbn()) == null) {
                    resultatRecherche.put(l.getIsbn(), l);
                }
            }

            temp = catalogue.resultatRechercheMotCle(request.getParameter("motRecherche"), connect.getInstance());

            for (Livre l : temp) {
                if (resultatRecherche.get(l.getIsbn()) == null) {
                    resultatRecherche.put(l.getIsbn(), l);
                }
            }

            temp = catalogue.resultatRechercheTitre(request.getParameter("motRecherche"), connect.getInstance());

            for (Livre l : temp) {
                if (resultatRecherche.get(l.getIsbn()) == null) {
                    resultatRecherche.put(l.getIsbn(), l);
                }
            }
            
            request.setAttribute("motRecherche", request.getParameter("motRecherche"));
            request.setAttribute("liste", resultatRecherche.values());
            if(request.getParameter("doIt") != null){
                lePanier.add(connect.getInstance(), request.getParameter("livIsbn"));
            }

        }

        if ("rechercheAvancee".equals(section)) {
            url = "/WEB-INF/catalogue/RechercheAvancee.jsp";
        }

        if ("resultatRechercheAvancee".equals(section)) {
            url = "/WEB-INF/catalogue/ResultatRecherche.jsp";

            HashMap<String, Livre> resultatRecherche = new HashMap();

            ArrayList<Livre> temp = null;

            String[] checkBox = request.getParameterValues("critereRecherche");


            if (checkBox != null) {
                for (int i = 0; i < checkBox.length; i++) {
                    if (checkBox[i].equals("auteur")) {
                        temp = catalogue.resultatRechercheAuteur(request.getParameter("motRecherche"), connect.getInstance());
                        for (Livre l : temp) {
                            if (resultatRecherche.get(l.getIsbn()) == null) {
                                resultatRecherche.put(l.getIsbn(), l);
                            }
                        }
                    }

                    if (checkBox[i].equals("isbn")) {
                        temp = catalogue.resultatRechercheIsbn(request.getParameter("motRecherche"), connect.getInstance());
                        for (Livre l : temp) {
                            if (resultatRecherche.get(l.getIsbn()) == null) {
                                resultatRecherche.put(l.getIsbn(), l);
                            }
                        }
                    }

                    if (checkBox[i].equals("motCle")) {
                        temp = catalogue.resultatRechercheMotCle(request.getParameter("motRecherche"), connect.getInstance());
                        for (Livre l : temp) {
                            if (resultatRecherche.get(l.getIsbn()) == null) {
                                resultatRecherche.put(l.getIsbn(), l);
                            }
                        }
                    }

                    if (checkBox[i].equals("titre")) {
                        temp = catalogue.resultatRechercheTitre(request.getParameter("motRecherche"), connect.getInstance());
                        for (Livre l : temp) {
                            if (resultatRecherche.get(l.getIsbn()) == null) {
                                resultatRecherche.put(l.getIsbn(), l);
                            }
                        }
                    }
                }
            }

            request.setAttribute("liste", resultatRecherche.values());
        }

        if ("livresSousTheme".equals(section)) {
            url = "/WEB-INF/catalogue/ResultatRecherche.jsp";

            Theme leTheme = null;
            SousTheme leSousTheme = null;

            if (catalogue.getMesThemes().isEmpty()) {
                catalogue.remplirThemesCatalogue(connect.getInstance());
            }

            for (Theme t : catalogue.getMesThemes()) {
                if (t.getNom().equals(request.getParameter("themeSelectionne"))) {
                    leTheme = t;
                }
            }

            for (SousTheme s : leTheme.getListeSousThemes()) {
                if (s.getNom().equals(request.getParameter("sousThemeSelectionne"))) {
                    leSousTheme = s;
                }
            }

            request.setAttribute("liste", leSousTheme.getListeLivre());
        }

        if ("commentaires".equals(section)) {
            url = "/WEB-INF/catalogue/Evaluations.jsp";

            ArrayList<Evaluations> listeEval = catalogue
                    .remplirEvaluations(request.getParameter("livreSelectionne")
                            , connect.getInstance());

            request.setAttribute("listeEval", listeEval);
        }
        
        if ("panier".equals(section)) {
            url = "/WEB-INF/commande/jspPanier.jsp";
        }
        
        if ("participant".equals(request.getParameter("section"))){
           url = "/WEB-INF/rubrique/participant.jsp";
            BeanParticipant participant = (BeanParticipant) session.getAttribute("participant");
            if (participant == null){
                participant = new BeanParticipant();
                session.setAttribute("participant", participant);
            }
            if(request.getParameter("doit") !=null){
                participant.setNom(request.getParameter("nom"));
                participant.setPrenom(request.getParameter("prenom"));
                participant.seteMail(request.getParameter("email"));
                participant.insertParticipant(connect.getInstance());
                request.setAttribute("participationOK","Votre participation a été enregistrée.");
            }
            
        }

        System.out.println("dbg LANCEMENT url : => <<"+url+">>.");
        
        request.getRequestDispatcher(url).include(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
