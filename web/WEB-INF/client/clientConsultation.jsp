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
        <title>Consultation du compte client</title>
    </head>
    <body>
        <h3>Bienvenue, ${cliCivilite} ${cliNom} ${cliPrenom} :</h3>
        <form action='Controller' method='post'>
            <input type="hidden" name="section" value="clientConsultation" />
            <font color='red'>Les champs indiqués par une (*) doivent être remplis.</font><br>
            Date de naissance : <input type='date' name='datenaiss' value='${cliDateNaiss}' /><br>
            (*) E-mail : <input type='email' name='email' value='${cliEmail}' required /><br>
            Téléphone domicile : <input type='text' name='teldomicile' value='${cliTelDomicile}' /><br>
            Téléphone mobile : <input type='text' name='telmobile' value='${cliTelMobile}' /><br>
            TODO : btn modif/màj/enregistrer les modifs ???? : cf ce qui est fait sur www (DOC où captures)
        </form>
        <!--
            TODO : a priori pas d'erreur en consultation, sauf en cas de modification.
            < font color='red' >${TODOmessage}< /font > TODO : ou bien appeler jspFatalError ?
        -->
        <hr>
        <h3>Changer votre mot de passe :</h3>
        <form action='Controller' method='post'>
            <input type="hidden" name="section" value="clientChangerMdp" />
            <input type='submit' name='changerMdp' value='Changer votre mot de passe' />
        </form>
        <!-- Ici, il ne peut pas y avoir d'erreur. -->
        <hr>
        <h3>Déconnexion :</h3>
        <form action='Controller' method='post'>
            <input type="hidden" name="section" value="clientDeconnexion" />
            <input type='submit' name='deconnecterClient' value='Vous déconnecter' />
        </form>
        <font color='red'>${messageErreurDeconnexionClient}</font>
    </body>
</html>
