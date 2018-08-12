<%-- 
    Document   : jspCommande
    Created on : 7 août 2018, 16:25:56
    Author     : danie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Validation Commande</title>
    </head>
    <body>
        <h1>Passer la commande (Nombre d'articles : ${nbreArticles}) </h1>
        <div>
            <div>
                <p>Récapitulatif de la commande: </p> <br/>
                Articles : Eur ${prixCom} <br/>
                Livraison : Eur ${prixDeLiv}
            </div>
            <div>
                Prix Total : Eur ${prixCom + prixDeLiv} <br/>
                Le total de la commande inclut la TVA.
                <form action="Controller" method="GET">
                    <input type="hidden" name="section" value="panier"/><br/>
                    <input type="submit" name="acheter" value="Acheter"/>
                </form>
            </div>
        </div>
    </body>
</html>
