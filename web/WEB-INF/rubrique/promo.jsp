<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : rubrique
    Created on : Aug 5, 2018, 7:20:18 PM
    Author     : Lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Event</title>
    </head>
    <body>
    <center>  <h1>PROMOTIONS</h1></center>
    <center> <h2>促销</h2></center>
        <c:forEach var="maPromo" items="${mesPromos}">
        outin the for each
            
        hhuhuhuuuhuh
        <c:if test="${maPromo.image != null}">
                <div style="vertical-align:middle; clear: both;">
                    <img src="${maPromo.image}"height="100"width="70"
                    style ="vertical-align: middle;float:left">
                    
                    <p>
                         ${maPromo.livreTitre}<br>
                        Prix: ${maPromo.livrePrix} €<br>
                        Promotion: ${maPromo.pourcentagePromo} %<br>
                        ${maPromo.calculateSumSaved()}
                        Prix Après Promo:${maPromo.prixEconomise} €<br>
                        Promo Valable jusqu'au: ${maPromo.dateFin}<br>
                        Nombre Encore Disponible: ${maPromo.livreQuantite}
                    </p>
                </div>
            </c:if>
        </c:forEach>
        
    </body>
</html>
