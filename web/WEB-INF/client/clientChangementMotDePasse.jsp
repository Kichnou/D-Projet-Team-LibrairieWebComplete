<%-- 
    Document   : clientChangementMotDePasse
    Created on : 24 juil. 2018, 10:35:24
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
        <title>Changement de votre mot de passe</title>
    </head>
    <body>
        <jsp:include page="../Header.jsp" flush="true"/>
        <jsp:include page="../catalogue/Themes.jsp" flush="true"/>
        
        <h3>Changement de votre mot de passe :</h3>
        <form action='Controller' method='post'>
            <input type="hidden" name="section" value="clientChangementMotDePasse" />
            Bonjour, ${cliCivilite} ${cliNom} ${cliPrenom}.<br>
            <p>Votre e-mail actuel : ${cliEmail}</p>
            <p class="obligatoire">Les champs indiqués par une étoile (*) doivent être remplis.</p>
            (*) Nouveau mot de passe : <input type='password' name='motdepasse' required /><br>
            (*) Confirmation de votre nouveau mot de passe : <input type='password' name='confirmationmotdepasse' onforminput="setCustomValidity(value != motdepasse.value ? 'Les deux mots de passe sont différents !' : '')" required /><br>
            <input type='submit' name='validerNouveauMotDePasse' value='Valider' />
        </form>
        <p class="erreur">${messageErreurChangementMotDePasse}</p>
        <jsp:include page="../bottom.jsp" flush="true" />
    </body>
</html>
