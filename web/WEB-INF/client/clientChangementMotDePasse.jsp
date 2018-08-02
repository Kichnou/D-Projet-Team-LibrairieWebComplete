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
        <title>Changement de votre mot de passe</title>
    </head>
    <body>
        <jsp:include page="../Header.jsp" flush="true"/>
        <jsp:include page="../catalogue/Themes.jsp" flush="true"/>
        
        <h3>Changement de votre mot de passe :</h3>
        <form action='Controller' method='post'>
            <input type="hidden" name="section" value="clientChangementMotDePasse" />
            Bonjour, ${cliCivilite} ${cliNom} ${cliPrenom}.<br>
            Votre e-mail actuel : ${cliEmail}<br>
            <font color='red'>Les champs indiqués par une (*) doivent être remplis.</font><br>
            (*) Nouveau mot de passe : <input type='password' name='password' required /><br>
            (*) Confirmation de votre nouveau mot de passe : <input type='password' name='confirmationpassword' onforminput="setCustomValidity(value != password.value ? 'Les deux mots de passe sont différents !' : '')" required /><br>
            <input type='submit' name='validerNouveauMotDePasse' value='Valider' />
        </form>
        <font color='red'>${messageErreurChangementMotDePasse}</font>
	<!-- TODO : cf JFrameChangementMotDePasseEmploye.java -->
        <jsp:include page="../bottom.jsp" flush="true" />
    </body>
</html>
