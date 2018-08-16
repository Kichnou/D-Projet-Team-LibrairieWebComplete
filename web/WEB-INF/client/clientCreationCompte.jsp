<%-- 
    Document   : clientCreationCompte
    Created on : 24 juil. 2018, 10:35:04
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
        <title>Création d'un nouveau compte client</title>
    </head>
    <body>
        <jsp:include page="../Header.jsp" flush="true"/>
        <jsp:include page="../catalogue/Themes.jsp" flush="true"/>
        <h3>Création d'un nouveau compte client :</h3>
        <form action='Controller' method='post'>
            <input type="hidden" name="section" value="clientCreationCompte" />
            <p class="obligatoire">Les champs indiqués par une étoile (*) doivent être remplis.</p>
            (*) Civilité :
            <input type="radio" name="groupeCivilite" value="Mr" id="monsieur" checked required />
            <label for="monsieur">Monsieur</label>
            <input type="radio" name="groupeCivilite" value="Mme" id="madame" />
            <label for="madame">Madame</label><br>
            (*) Prénom : <input type='text' name='prenom' value='${cliPrenom}' required /><br>
            (*) Nom : <input type='text' name='nom' value='${cliNom}' required /><br>
            (*) E-mail : <input type='email' name='email' value='${cliEmail}' required /><br>
            (*) Confirmation de votre e-mail : <input type='email' name='confirmationmail' value='${cliEmailConfirmation}' onforminput="setCustomValidity(value != email.value ? 'Les deux adresses e-mail sont différentes !' : '')" required /><br>
            (*) Mot de passe : <input type='password' name='motdepasse' required /><br>
            (*) Confirmation de votre mot de passe : <input type='password' name='confirmationmotdepasse' onforminput="setCustomValidity(value != motdepasse.value ? 'Les deux mots de passe sont différents !' : '')" required /><br>
            <input type='submit' name='validerCreationCompte' value='Valider' />
        </form>
        <p class="erreur">${messageErreurCreationCompteClient}</p>
    </body>
</html>
