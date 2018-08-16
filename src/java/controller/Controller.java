package controller;

import beanRubrique.BeanRubrique;
import beans.BeanClient;
import beans.BeanConnect;
import beans.BeanInfos;
import beans.BeanParticipant;
import beans.catalogue.BeanCatalogue;
import beans.commande.BeanPanier;
import classes.catalogue.Evaluations;
import classes.catalogue.Livre;
import classes.catalogue.SousTheme;
import classes.catalogue.Theme;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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

        //NOTA : pour ne pas être emm… par les caractères accentués (àçéèô …)
        request.setCharacterEncoding("UTF-8");

        //Par défaut, URL pointant vers la page d'accueil du site :
        String url = "/WEB-INF/Accueil.jsp";

        //Récupérer la section courante :
        String section = request.getParameter("section");

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
        //TODO : avertissement de compilation : "variable non utilisée ..."
        //TODO : voir comment je me suis débarrassé de la variable
        //messageErreurConnexionClient
        String messageErreurChangementMotDePasse = "";

        HttpSession session = request.getSession();
        ServletContext application = this.getServletContext();

        BeanConnect connect = (BeanConnect) application.getAttribute("connection");
        if (connect == null) {
            connect = new BeanConnect();
            application.setAttribute("connection", connect);
        }

        BeanCatalogue catalogue = (BeanCatalogue) application.getAttribute("catalogue");
        if (catalogue == null) {
            catalogue = new BeanCatalogue();
            application.setAttribute("catalogue", catalogue);
        }

        BeanPanier lePanier = (BeanPanier) session
                .getAttribute("beanPanier");
        if (lePanier == null) {
            lePanier = new BeanPanier();
            session.setAttribute("beanPanier", lePanier);
        }

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
                session.setAttribute("infos", infos);
            }

            //Alimenter la HashMap avec les informations lues dans la table
            //Infos :
            infos.lireInformations(connect.getInstance());

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
        
        //BeanClient :
        BeanClient leClient = (BeanClient) session.getAttribute("client");
        if (leClient == null) {
            leClient = new BeanClient();
            session.setAttribute("client", leClient);
        }
        
        BeanParticipant participant = (BeanParticipant) session.getAttribute("participant");
        if (participant == null) {
            participant = new BeanParticipant();
            session.setAttribute("participant", participant);
        }
        
        BeanRubrique rubrique = (BeanRubrique) application.getAttribute("rubrique");
        if (rubrique == null){
            rubrique = new BeanRubrique();
            application.setAttribute("rubrique", rubrique);
        }
        
        if (section == null || "accueil".equals(section)) {
            url = "/WEB-INF/Accueil.jsp";
            
            if (catalogue.getMonCatalogue().isEmpty()) {
                catalogue.remplirCatalogue(connect.getInstance());
            }
            
            session.setAttribute("liste", catalogue.getMonCatalogue());
        }

        /*
         Si l'utilisateur a cliqué sur le bouton "Valider"
         dans "clientChangementMotDePasse.jsp" :
         */
        if ("clientChangementMotDePasse".equals(section)) {
            System.out.println("dbg section clientChangementMotDePasse : IN");
            url = "/WEB-INF/client/clientChangementMotDePasse.jsp";

            /*
            TODOUUU...
            */
            request.setAttribute("cliCivilite", leClient.getCliCivilite());
            request.setAttribute("cliNom", leClient.getCliNom());
            request.setAttribute("cliPrenom", leClient.getCliPrenom());
            request.setAttribute("cliEmail", leClient.getCliEmail());

            //Si l'utilisateur a cliqué sur le bouton nommé
            //"validerNouveauMotDePasse" :
            if (request.getParameter("validerNouveauMotDePasse") != null) {
                /*
                 todoUUU : créer un MotDePasseSQL
                 todoUUU : call UPDATE        
                 Donc, à un moment, il y aura un prepstmt.executeUpdate();
                 todoUUU : cf Employe.        
                 */
            }

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

                /*
                TODOUUUDEV : code à ajouter ici, ou pas ...
                */

                //Lire l'adresse e-mail et le mot de passe saisis :
                eMailClient = request.getParameter("email");
                motDePasseSaisi = request.getParameter("password");

                try {
                    //En déduire les informations relatives au client :
                    if (! leClient.getClientFromEMailClient(
                            connect.getInstance(), eMailClient)) {
                        //Le message d'erreur suivant sera affiché en rouge dans
                        //la page clientConnexion.jsp :
                        request.setAttribute("messageErreurConnexionClient",
                                "Le client ayant pour adresse mail " +
                                eMailClient + " est introuvable !");
                    }
                } catch(Exception ex) {
                    //Le message de l'exception sera affiché en rouge dans la
                    //page clientConnexion.jsp :
                    request.setAttribute("messageErreurConnexionClient",
                            ex.getMessage());
                }
                
                //Par défaut, le mot de passe saisi par le client n'est pas
                //correct :
                boolean motDePasseOk = false;

                //Par défaut, aucun message d'erreur en rouge dans la page
                //clientConnexion.jsp :
                request.setAttribute("messageErreurConnexionClient", "");

                //Lire le nombre de tentatives déjà faites dans le cookie :
                cookieNbTentatives = getCookie(request.getCookies(), "essai" +
                        numeroClient);
                if (cookieNbTentatives == null) {
                    nombreTentativesDejaFaites = 0;
                } else {
                    nombreTentativesDejaFaites = cookieNbTentatives.getValue().
                            length();
                }
                
                try {
                    motDePasseOk = leClient.motDePasseEstCorrect(
                            connect.getInstance(),eMailClient,motDePasseSaisi,
                            nombreTentativesDejaFaites);
                } catch (Exception ex) {
                    //Le message de l'exception sera affiché en rouge dans la
                    //page clientConnexion.jsp :
                    request.setAttribute("messageErreurConnexionClient",
                            ex.getMessage());
                }

                if (motDePasseOk) {
                    //L'utilisateur a réussi à se connecter. Le contrôleur lui
                    //donne donc accès à la page clientConsultation.jsp :
                    url = "/WEB-INF/client/clientConsultation.jsp";

                    /*
                     Réinitialiser le nombre de tentatives (infructueuses) de
                     saisie du mot de passe à 0 :
                     */
                    request.setAttribute("welcome", eMailClient);
                    cookieClientConnecte = new Cookie("connecte" + numeroClient,
                            eMailClient);
                    response.addCookie(cookieClientConnecte);

                    cookieNbTentatives = new Cookie("essai" + numeroClient, "");
                    cookieNbTentatives.setMaxAge(0);
                    response.addCookie(cookieNbTentatives);

                    //Alimenter les variables nécessaires pour la page :
                    request.setAttribute("cliCivilite", leClient.getCliCivilite());
                    //TODO QQ : exécutera OK ou pas ? Date !!!!
                    request.setAttribute("cliDateNaiss", leClient.getCliDateNaiss());
                    request.setAttribute("cliEmail", leClient.getCliEmail());
                    request.setAttribute("cliNom", leClient.getCliNom());
                    request.setAttribute("cliPrenom", leClient.getCliPrenom());
                    request.setAttribute("cliTelDomicile", leClient.getCliTelDomicile());
                    request.setAttribute("cliTelMobile", leClient.getCliTelMobile());
                } else {
                    //L'utilisateur n'a PAS réussi à se connecter.
                    //TODO : PBPB : faut-il (ou pas) ????
                    //request.setAttribute("cliEmail", eMailClient);
                    //L'utilisateur DOIT donc rester sur la page de départ :
                    url = "/WEB-INF/client/clientConnexion.jsp";

                    cookieNbTentatives = getCookie(request.getCookies(),
                            "essai" + numeroClient);
                    if (cookieNbTentatives == null) {
                        cookieNbTentatives = new Cookie("essai" + numeroClient,
                                "*");
                    } else {
                        cookieNbTentatives = new Cookie("essai" + numeroClient,
                                cookieNbTentatives.getValue() + "*");
                    }
                    if (cookieNbTentatives.getValue().length() > 3) {
                        cookieNbTentatives.setMaxAge(20 * 60); //20 minutes
                    }
                    response.addCookie(cookieNbTentatives);

                }
            }

            System.out.println("dbg section clientConnexion : OUT");

        }

        //Section clientConsultation :
        if ("clientConsultation".equals(section)) {
            System.out.println("dbg section clientConsultation : IN");

            url = "/WEB-INF/client/clientConsultation.jsp";

            /*
            TODOUUU...
            */
            request.setAttribute("cliCivilite", leClient.getCliCivilite());
            request.setAttribute("cliNom", leClient.getCliNom());
            request.setAttribute("cliPrenom", leClient.getCliPrenom());
            request.setAttribute("cliDateNaiss", leClient.getCliDateNaiss());
            request.setAttribute("cliEmail", leClient.getCliEmail());
            request.setAttribute("cliTelDomicile", leClient.getCliTelDomicile());
            request.setAttribute("cliTelMobile", leClient.getCliTelMobile());
            System.out.println("dbg section clientConsultation : OUT");            
            
            
        }

        /*
         Si l'utilisateur a cliqué sur le bouton "Valider" dans
         "clientCreationCompte.jsp", alors :
         */
        if ("clientCreationCompte".equals(section)) {
            System.out.println("dbg section clientCreationCompte : IN");

            url = "/WEB-INF/client/clientCreationCompte.jsp";

            /*
            TODOUUU...
            */
            request.setAttribute("cliNom", leClient.getCliNom());
            request.setAttribute("cliPrenom", leClient.getCliPrenom());
            request.setAttribute("cliEmail", leClient.getCliEmail());
            
            //Si l'utilisateur a cliqué sur le bouton nommé
            //"validerCreationCompte" :
            if (request.getParameter("validerCreationCompte") != null) {

                civiliteClient = request.getParameter("groupeCivilite");
                dateNaissClient = "";
                eMailClient = request.getParameter("email");
                motDePasseSaisi = request.getParameter("password");
                nomClient = request.getParameter("nom");
                prenomClient = request.getParameter("prenom");
                telDomicileClient = "";
                telMobileClient = "";

                try {
                    leClient.ajouterClient(connect.getInstance(), civiliteClient,
                            nomClient, prenomClient, eMailClient,
                            motDePasseSaisi, dateNaissClient, telDomicileClient,
                            telMobileClient);
                    //TODOUUUU : url ????!!!!!
                } catch (Exception ex) {
                    //Le message de l'exception sera affiché en rouge dans la
                    //page clientCreationCompte.jsp :
                    request.setAttribute("messageErreurCreationCompteClient",
                            ex.getMessage());
                }
            }

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
                url = "/WEB-INF/client/clientCreationCompte.jsp";
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

            //Si l'utilisateur a cliqué sur le bouton nommé
            //"deconnecterClient" :
            if (request.getParameter("deconnecterClient") != null) {
                url = "/WEB-INF/Accueil.jsp";
                //Récuperer l'e-mail du client :
                eMailClient = request.getParameter("email");

                try {
                    //En déduire les informations relatives au client :
                    if (! leClient.getClientFromEMailClient(
                            connect.getInstance(), eMailClient)) {
                        //Le message d'erreur suivant sera affiché en rouge dans
                        //la page clientConnexion.jsp :
                        request.setAttribute("messageErreurConnexionClient",
                                "Le client ayant pour adresse mail " +
                                eMailClient + " est introuvable !");
                    }
                } catch(Exception ex) {
                    //Le message de l'exception sera affiché en rouge dans la
                    //page clientConnexion.jsp :
                    request.setAttribute("messageErreurConnexionClient",
                            ex.getMessage());
                }
                
                //Chercher le cookie "connecte" :
                cookieClientConnecte = getCookie(request.getCookies(),
                        "connecte" + numeroClient);
                if (cookieClientConnecte == null) {
                    //TODOQ/PB : anomalie ???
                } else {
                    //"Supprimer" le cookie du navigateur du poste client en
                    //affectant la valeur 0 à son MaxAge :
                    cookieClientConnecte.setMaxAge(0);
                    response.addCookie(cookieClientConnecte);
                }
            }

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
            if (request.getParameter("connecterClient") != null) //Appeler la page clientConnexion.jsp :
            {
                url = "/WEB-INF/client/clientConnexion.jsp";
            }

            //Si l'utilisateur a cliqué sur le bouton nommé
            //"creerCompteClient" :
            if (request.getParameter("creerCompteClient") != null) //Appeler la page clientCreationCompte.jsp :
            {
                url = "/WEB-INF/client/clientCreationCompte.jsp";
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
            if (!resultatRecherche.values().isEmpty()) {
                session.setAttribute("liste", resultatRecherche.values());
            }
            if (request.getParameter("doIt") != null) {
                lePanier.add(connect.getInstance(), request.getParameter("livIsbn"));
                session.setAttribute("beanPanier", lePanier);
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

            session.setAttribute("liste", resultatRecherche.values());
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

            session.setAttribute("liste", leSousTheme.getListeLivre());
        }
        
        if ("detailLivre".equals(section)) {
            url = "/WEB-INF/catalogue/DetailLivre.jsp";
            
            ArrayList<Livre> monLivre = catalogue.resultatRechercheIsbn(request.getParameter("livIsbn"), connect.getInstance());
            
            Livre leLivre = null;
            
            for (Livre l : monLivre) {
                leLivre = l;
            }
            
            request.setAttribute("livre", leLivre);
        }

        if ("commentaires".equals(section)) {
            url = "/WEB-INF/catalogue/Evaluations.jsp";

            ArrayList<Evaluations> listeEval = catalogue
                    .remplirEvaluations(request.getParameter("livreSelectionne"), connect.getInstance());

            request.setAttribute("listeEval", listeEval);
        }

        if ("panier".equals(section)) {
            url = "/WEB-INF/commande/jspPanier.jsp";
            
//            if(request.getParameter("add") != null){
//                lePanier.add(connect.getInstance(), request.getParameter("add"));
//            }
//            if(request.getParameter("dec") != null){
//                lePanier.dec(connect.getInstance(), request.getParameter("dec"));
//            }
//            if(request.getParameter("del") != null){
//                lePanier.del(request.getParameter("del"));
//            }
//            if(request.getParameter("clean") != null){
//                lePanier.clean();
//            }
            
            if(request.getParameter("add") != null){
                lePanier.add(connect.getInstance(), request.getParameter("isbn"));
//                lePanier.prixCommande();
                session.setAttribute("beanPanier", lePanier);
            }
            if(request.getParameter("dec") != null){
                lePanier.dec(connect.getInstance(), request.getParameter("isbn"));
//                lePanier.prixCommande();
                session.setAttribute("beanPanier", lePanier);
            }
            if(request.getParameter("del") != null){
                lePanier.del(request.getParameter("isbn"));
                session.setAttribute("beanPanier", lePanier);
            }
            if(request.getParameter("clean") != null){
                lePanier.clean();
            }
            
            if(!lePanier.getPanier().isEmpty()){
                lePanier.prixCommande();
            }
            
            if(request.getParameter("valider") != null){
                url = "/WEB-INF/commande/jspCommande.jsp";
                
                lePanier.calculNbreArticles();
                System.out.println("nbreArticles : "+ lePanier.getNbrArticles());
                session.setAttribute("beanPanier", lePanier);
                
//                cookieClientConnecte = this.getCookie(request.getCookies(),
//                        "connecte" + numeroClient);
//                if(cookieClientConnecte == null){
//                    url = "/WEB-INF/client/clientConnexion.jsp";
//                }else{
//                    url = "jspCommande.jsp";
//                }
            }
            
            if(request.getParameter("acheter") != null){
                url = "/WEB-INF/commande/jspPaiement.jsp";
            }
            
            if(request.getParameter("validerCommande") != null){
                System.out.println("je suis dans ze if");
                
                int payer = 3;
                lePanier.getStatutPaiement(connect.getInstance(), payer);
                lePanier.saveCommande(connect.getInstance());
                lePanier.saveLigneDeCommande(connect.getInstance());
                
                url = "/WEB-INF/Acceuil.jsp";
            }
            
            request.setAttribute("prixDeLiv", lePanier.getPrixDeLiv());
            request.setAttribute("nbreArticles", lePanier.getNbrArticles());
            request.setAttribute("prixCom", lePanier.getPrixTtc());
            request.setAttribute("list", lePanier.getPanier().values());
        }

        if ("participant".equals(request.getParameter("section"))) {
            url = "/WEB-INF/rubrique/participant.jsp";
            
            if (request.getParameter("doit") != null) {
                participant.setNom(request.getParameter("nom"));
                participant.setPrenom(request.getParameter("prenom"));
                participant.seteMail(request.getParameter("email"));
                participant.insertParticipant(connect.getInstance());
                request.setAttribute("participationOK", "Votre participation a été enregistrée.");
            }

        }
        
        if ("evenement".equals(request.getParameter("section"))) {
           url = "/WEB-INF/rubrique/evenement.jsp";
           if(request.getParameter("evenement") != null){
               
            
        }
        }
        
        if ("coupDeCoeur".equals(section)) {
            System.out.println("Dans Coup de coeur");
            
            url = "/WEB-INF/rubrique/coupDeCoeur.jsp";
           
           if (request.getParameter("coupDeCoeur") != null){
               
               rubrique.getPromoCoupsDeCoeur(connect.getInstance(), 
                       request.getParameter("coupDeCoeur"));
               request.setAttribute("mesCoupsDeCoeur", rubrique.getMesCoupDeCoeurs());
           }
        }
        
        if("promo".equals(request.getParameter("section"))){
            url = "/WEB-INF/rubrique/promo.jsp";
            
            if(request.getParameter("promo")!= null){
                
                rubrique.getPromoCoupsDeCoeur(connect.getInstance(), 
                        request.getParameter("promo"));
                
                request.setAttribute("mesPromos", rubrique.getMesPromo());
             //   System.out.println("Size"+rubrique.getMesPromo().size());
            }
        }    
        try {
            connect.getInstance().close();
        } catch (SQLException ex) {
            System.out.println("Connection problem to close"+ex.getMessage()
                    +ex.getErrorCode());
        }
        

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
