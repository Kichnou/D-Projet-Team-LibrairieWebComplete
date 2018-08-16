<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="./style.css">
        <title>Le Panier</title>
    </head>
    <body>
        <jsp:include page="../Header.jsp" flush="true" />
        <div class="row">
            <jsp:include page="../catalogue/Themes.jsp" flush="true" />
            <div class="col-xs-9">
                <h1>Votre Panier</h1> <br/>

                <c:if test="${list.size() == 0}">
                    <p>Le Panier est vide !!</p>
                </c:if>

                <c:forEach var="lig" items="${list}">

                    <c:if test="${lig.leLivre.image != null}">

                        <div style="vertical-align:middle; clear: both;">

                            <img src="${lig.leLivre.image}" height="100" width="70" 
                                 style="vertical-align:middle; float:left">

                            <p>
                                Titre : ${lig.leLivre.titre} <br/>
                                Quantité : ${lig.quantite} <br/>
                                Prix : ${lig.leLivre.prixTtc * lig.quantite} € <br/>
                            <form action="Controller" method="GET">
                                <input type="hidden" name="section" value="panier"/>
                                <input type="hidden" name="prixCom" value="${prixCom += lig.leLivre.prixTtc * lig.quantite}"<br/>
                                <input type="hidden" name="isbn" value="${lig.leLivre.isbn}"<br/>
                                <input type="submit" name="add" value="+"/>
                                <input type="submit" name="dec" value="-"/>
                                <input type="submit" name="del" value="X"/>
                            </form>
                            </p>
                        </div><br/>
                    </c:if>
                </c:forEach>

                <div>
                    <form action="Controller" method="GET">
                        <input type="hidden" name="section" value="panier"/>
                        <c:if test="${list.size() != 0}">
                            <input type="submit" name="clean" value="Vider le Panier"/>
                            <br/>
                            PRIX TOTAL : ${prixCom} €
                            <br/>
                            <input type="submit" name="valider" 
                                   value="Passer La Commande"/>
                        </c:if>
                    </form> 
                </div>
            </div>
        </div>
    </body>
</html>
