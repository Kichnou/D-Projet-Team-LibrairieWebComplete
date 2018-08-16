<%-- 
    Document   : clientConnexion
    Created on : 24 juil. 2018, 10:38:34
    Author     : cdi315
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style type="text/css">
        <jsp:include page="../css/styleClient.css"/>
        </style>
        <title>Connexion au compte client</title>
    </head>
    <body>
        <jsp:include page="../Header.jsp" flush="true"/>
        <jsp:include page="../catalogue/Themes.jsp" flush="true"/>
        <h3>Connexion au compte client :</h3>
        <form action='Controller' method='post'>
            <input type="hidden" name="section" value="clientConnexion" />
            <p class="obligatoire">Les champs indiqués par une étoile (*) doivent être remplis.</p>
            (*) E-mail : <input type='email' name='email' value='${cliEmail}' required /><br>
            (*) Mot de passe : <input type='password' name='motdepasse' required /><br>
            <input type='submit' name='connecterClient' value='Connexion' />
        </form>
        <p class="erreur">${messageErreurConnexionClient}</p>
        <hr>
        <p class="information">${messageCompteClientCreeAvecSucces}</p>
        <h3>Nouveau client :</h3>
        <form action='Controller' method='post'>
            <input type="hidden" name="section" value="clientCreerCompte" />
            Si vous êtes un nouveau client de la librairie en ligne, alors vous devriez<br>
            <input type='submit' name='creerCompteClient' value='Créer votre compte' />
        </form>
        <!-- Ici, il ne peut pas y avoir d'erreur. -->
        <jsp:include page="../bottom.jsp" flush="true" />
    </body>
</html>




