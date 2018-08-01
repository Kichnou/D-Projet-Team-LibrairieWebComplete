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
        <title>Création d'un nouveau compte client</title>
    </head>
    <body>
        <h3>Création d'un nouveau compte client :</h3>
        <form action='Controller' method='post'>
            <input type="hidden" name="section" value="clientCreationCompte" />
            <font color='red'>Les champs indiqués par une (*) doivent être remplis.</font><br>
            (*) Civilité :
            <input type="radio" name="groupeCivilite" value="Mr" id="monsieur" checked required />
            <label for="monsieur">Monsieur</label>
            <input type="radio" name="groupeCivilite" value="Mme" id="madame" />
            <label for="madame">Madame</label><br>
            (*) Prénom : <input type='text' name='prenom' value='${cliPrenom}' required /><br>
            (*) Nom : <input type='text' name='nom' value='${cliNom}' required /><br>
            (*) E-mail : <input type='email' name='email' value='${cliEmail}' required /><br>
            (*) Confirmation de votre e-mail : <input type='email' name='confirmationmail' value='${cliEmailConfirmation}' onforminput="setCustomValidity(value != email.value ? 'Les deux adresses e-mail sont différentes !' : '')" required /><br>
            (*) Mot de passe : <input type='password' name='password' required /><br>
            (*) Confirmation de votre mot de passe : <input type='password' name='confirmationpassword' onforminput="setCustomValidity(value != password.value ? 'Les deux mots de passe sont différents !' : '')" required /><br>
            <input type='submit' name='validerCreationCompte' value='Valider' />
        </form>
        <font color='red'>${messageErreurCreationCompteClient}</font>
    </body>
</html>
