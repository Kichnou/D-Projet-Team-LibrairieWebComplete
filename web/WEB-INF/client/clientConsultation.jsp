<%-- 
    Document   : clientConsultation
    Created on : 24 juil. 2018, 10:37:31
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
        <title>Consultation du compte client</title>
    </head>
    <body>
        <jsp:include page="../Header.jsp" flush="true"/>
        <jsp:include page="../catalogue/Themes.jsp" flush="true"/>
        <h3>Bienvenue, ${cliCivilite} ${cliNom} ${cliPrenom} :</h3>
        <p>Votre e-mail actuel : ${cliEmail}</p>
        <form action='Controller' method='post'>
            <input type="hidden" name="section" value="clientConsultation" />
            <p class="obligatoire">Les champs indiqués par une étoile (*) doivent être remplis.</p>
<!--            
            ESSAI ESSAI TODEL ESSAI ESSAI<br>
            (*) Civilité :
            <input type="radio" name="groupeCivilite" value="Mr" id="monsieur" checked="value=${cliCivilite}" required />
            <label for="monsieur">Monsieur</label>
            <input type="radio" name="groupeCivilite" value="Mme" id="madame" />
            <label for="madame">Madame</label><br>
            ESSAI ESSAI TODEL ESSAI ESSAI<br>
-->
            (*) Prénom : <input type='text' name='prenom' value='${cliPrenom}' required /><br>
            (*) Nom : <input type='text' name='nom' value='${cliNom}' required /><br>
            Date de naissance : <input type='date' name='datenaiss' value='${cliDateNaiss}' /><br>
            Téléphone domicile : <input type='text' name='teldomicile' value='${cliTelDomicile}' /><br>
            Téléphone mobile : <input type='text' name='telmobile' value='${cliTelMobile}' /><br>
            <input type='submit' name='modifierCompteClient' value='Enregistrer' />
        </form>
        <!--
            TODO : a priori pas d'erreur en consultation, sauf en cas de modification.
            < font obsolète !!! color='red' >${TODOmessage}< /font > TODO : ou bien appeler jspFatalError ?
        -->
        <hr>
        <h3>Changer votre mot de passe :</h3>
        <form action='Controller' method='post'>
            <input type="hidden" name="section" value="clientChangerMdp" />
            <input type='submit' name='changerMdp' value='Changer votre mot de passe' />
        </form>
        <!-- Ici, il ne peut pas y avoir d'erreur. -->
        <p class="information">${messageMotDePasseChangeAvecSucces}</p>
        <hr>
        <h3>Déconnexion :</h3>
        <form action='Controller' method='post'>
            <input type="hidden" name="section" value="clientDeconnexion" />
            <input type='submit' name='deconnecterClient' value='Vous déconnecter' />
        </form>
        <p class="erreur">${messageErreurDeconnexionClient}</p>
    </body>
</html>
