package controller;

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
                .getAttribute("BeanCommande");
        if (lePanier == null) {
            lePanier = new BeanPanier();
            session.setAttribute("BeanCommande", lePanier);
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

        if (section == null || "accueil".equals(section)) {
            url = "/WEB-INF/Accueil.jsp";

            try {
                BeanInfos infos = (BeanInfos) session.getAttribute("infos");
                if (infos == null) {
                    infos = new BeanInfos();
                    session.setAttribute("infos",infos);
                }
                
                /*
                TODODEV : code à ajouter ici, ou pas ...
                */
                
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
            
        }
        
        /*
          Si l'utilisateur a cliqué sur le bouton "Connexion" dans
          "accueil.jsp", alors clientConnexion.jsp est exécuté :
        */
        
        if ("accueilConnecter".equals(section)) {
            System.out.println("dbg section accueilConnecter : IN");            
            url = "/WEB-INF/client/clientConnexion.jsp";
            System.out.println("dbg section accueilConnecter : OUT");            
            //TODOTEST : OK, fonctionne.
        }
        
        /*
          Si l'utilisateur a cliqué sur le bouton "Valider"
          dans "clientChangementMotDePasse.jsp" :
        */
        if ("clientChangementMotDePasse".equals(section)) {
            System.out.println("dbg section clientChangementMotDePasse : IN");            
            url = "/WEB-INF/client/clientChangementMotDePasse.jsp";

            
            BeanClient client = (BeanClient) session.getAttribute("client");
            if (client == null) {
                client = new BeanClient();
                session.setAttribute("client", client);
            }
            /*
            TODO...
            */
            request.setAttribute("cliCivilite", client.getCliCivilite());
            request.setAttribute("cliNom", client.getCliNom());
            request.setAttribute("cliPrenom", client.getCliPrenom());
            request.setAttribute("cliEmail", client.getCliEmail());

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
                BeanClient client = (BeanClient) session.getAttribute("client");
                if (client == null) {
                    client = new BeanClient();
                    session.setAttribute("client", client);
                }

                /*
                TODODEV : code à ajouter ici, ou pas ...
                */

                //Lire l'adresse e-mail et le mot de passe saisis :
                eMailClient = request.getParameter("email");
                motDePasseSaisi = request.getParameter("password");

                //Par défaut, le mot de passe saisi par le client n'est pas
                //correct :
                boolean motDePasseOk = false;

                //Par défaut, aucun message d'erreur en rouge dans la page
                //clientConnexion.jsp :
                request.setAttribute("messageErreurConnexionClient", "");

                //Lire le nombre de tentatives déjà faites dans le cookie :
                cookieNbTentatives = getCookie(request.getCookies(), "essai" +
                        eMailClient);
                if (cookieNbTentatives == null) {
                    nombreTentativesDejaFaites = 0;
                } else {
                    nombreTentativesDejaFaites = cookieNbTentatives.getValue().
                            length();
                }

                try {
                    motDePasseOk = client.motDePasseEstCorrect(
                            connect.getInstance(),eMailClient,motDePasseSaisi,
                            nombreTentativesDejaFaites);
                } catch(Exception ex) {
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
                    cookieClientConnecte = new Cookie("connecte" + eMailClient,
                            eMailClient);
                    response.addCookie(cookieClientConnecte);

                    cookieNbTentatives = new Cookie("essai" + eMailClient, "");
                    cookieNbTentatives.setMaxAge(0);
                    response.addCookie(cookieNbTentatives);


                    //Alimenter les variables nécessaires pour la page :
                    request.setAttribute("cliCivilite", client.getCliCivilite());
                    //TODO QQ : exécutera OK ou pas ? Date !!!!
                    request.setAttribute("cliDateNaiss", client.getCliDateNaiss());
                    request.setAttribute("cliEmail", client.getCliEmail());
                    request.setAttribute("cliNom", client.getCliNom());
                    request.setAttribute("cliPrenom", client.getCliPrenom());
                    request.setAttribute("cliTelDomicile", client.getCliTelDomicile());
                    request.setAttribute("cliTelMobile", client.getCliTelMobile());
                } else {
                    //L'utilisateur n'a PAS réussi à se connecter.
                    //TODO : PBPB : faut-il (ou pas) ????
                    //request.setAttribute("cliEmail", eMailClient);
                    //L'utilisateur DOIT donc rester sur la page de départ :
                    url = "/WEB-INF/client/clientConnexion.jsp";


                    cookieNbTentatives = getCookie(request.getCookies(),
                            "essai" + eMailClient);
                    if (cookieNbTentatives == null) {
                        cookieNbTentatives = new Cookie("essai" + eMailClient,
                                "*");
                    } else {
                        cookieNbTentatives = new Cookie("essai" + eMailClient,
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

            BeanClient client = (BeanClient) session.getAttribute("client");
            if (client == null) {
                client = new BeanClient();
                session.setAttribute("client", client);
            }
            /*
            TODO...
            */
            request.setAttribute("cliCivilite", client.getCliCivilite());
            request.setAttribute("cliNom", client.getCliNom());
            request.setAttribute("cliPrenom", client.getCliPrenom());
            request.setAttribute("cliDateNaiss", client.getCliDateNaiss());
            request.setAttribute("cliEmail", client.getCliEmail());
            request.setAttribute("cliTelDomicile", client.getCliTelDomicile());
            request.setAttribute("cliTelMobile", client.getCliTelMobile());
            System.out.println("dbg section clientConsultation : OUT");            
            
            
        }

        /*
          Si l'utilisateur a cliqué sur le bouton "Valider" dans
          "clientCreationCompte.jsp", alors :
        */
        if ("clientCreationCompte".equals(section)) {
            System.out.println("dbg section clientCreationCompte : IN");
			
            url = "/WEB-INF/client/clientCreationCompte.jsp";

            BeanClient client = (BeanClient) session.getAttribute("client");
            if (client == null) {
                client = new BeanClient();
                session.setAttribute("client", client);
            }
            /*
            TODO...
            */
            request.setAttribute("cliNom", client.getCliNom());
            request.setAttribute("cliPrenom", client.getCliPrenom());
            request.setAttribute("cliEmail", client.getCliEmail());
            
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
                    client.ajouterClient(connect.getInstance(), civiliteClient,
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

                //Chercher le cookie "connecte" :
                cookieClientConnecte = getCookie(request.getCookies(),
                        "connecte" + eMailClient);
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

            ArrayList<Evaluations> listeEval = catalogue.remplirEvaluations(request.getParameter("livreSelectionne"), connect.getInstance());

            request.setAttribute("listeEval", listeEval);
        }
        
        if ("panier".equals(section)) {
            url = "/WEB-INF/commande/jspPanier.jsp";
            
            
            
//            HashMap<String, Livre> map = new HashMap<>();
//            Livre leLivre = new Livre();
//            
//            Connection connect = connexion.getInstance();
//            ResultSet rs = laLigneDeCommande.getLivre(connect, request
//                   .getParameter("livIsbn"));
//            
//            try {
//                
//                while (rs.next()){
//                    leLivre.valoriserLivre(rs);
//                }
//                
//            } catch (SQLException ex) {
//                System.out.println("Erreur resultSet "
//                        + ex.getErrorCode() +" / "+ ex.getMessage());
//            }
//            
//            if(!map.containsKey(leLivre.getIsbn())){
//                map.put(leLivre.getIsbn(), leLivre);
//            }
//            
//            request.setAttribute("listLig", map.values());
//            
//            System.out.println("titre : "+ map.get(leLivre.getIsbn()));
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