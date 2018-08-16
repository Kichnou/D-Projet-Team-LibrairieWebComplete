<%-- 
    Document   : jspPaement
    Created on : 8 août 2018, 14:58:30
    Author     : danie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="./style.css">
        <title>Paiement</title>
    </head>
    <body>
        <jsp:include page="../Header.jsp" flush="true" />
        <div class="row">
            <jsp:include page="../catalogue/Themes.jsp" flush="true" />
            <div class="col-xs-9">
                <h1>Paiement</h1>
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
            </div>
        </div>
    </body>
</html>
