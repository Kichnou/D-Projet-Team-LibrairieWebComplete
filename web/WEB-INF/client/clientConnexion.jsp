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
        <title>Connexion au compte client</title>
    </head>
    <body>
        <h3>Connexion au compte client :</h3>
        <form action='Controller' method='post'>
            <input type="hidden" name="section" value="clientConnexion" />
            <font color='red'>Les champs indiqués par une (*) doivent être remplis.</font><br>
            (*) E-mail : <input type='email' name='email' value='${cliEmail}' required /><br>
            (*) Mot de passe : <input type='password' name='password' required /><br>
            <input type='submit' name='connecterClient' value='Connexion' />
        </form>
        <font color='red'>${messageErreurConnexionClient}</font>
        <hr>
        <h3>Nouveau client :</h3>
        <form action='Controller' method='post'>
            <input type="hidden" name="section" value="clientCreerCompte" />
            Si vous êtes un nouveau client de la librairie en ligne, alors vous devriez<br>
            <input type='submit' name='creerCompteClient' value='Créer votre compte' />
        </form>
        <!-- Ici, il ne peut pas y avoir d'erreur. -->
    </body>
</html>




