<%-- 
    Document   : jspPaement
    Created on : 8 août 2018, 14:58:30
    Author     : danie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Paement</title>
    </head>
    <body>
        <h1>Paement</h1>
        <form action="Controller" method="GET">
            
            Nom du propriétaire de la carte <br/>
            <input type="text" name="nomPaiement"/> <br/>
            Numeros de la carte <br/>
            <input type="number" name="numCarte"/> <br/>
            Date d'expiratio de la carte <br/>
            <input type="date" name="dateExp"/> <br/>
            Cryptograme <br/>
            <input type="number" placeholder="Les trois derniers chiffres au dos
                   de la carte" name="crypto"/><br/>
            
            <input type="hidden" name="section" value="panier"/><br/>
            <input type="submit" name="validerCommande" value="Achat">
        </form>
    </body>
</html>
