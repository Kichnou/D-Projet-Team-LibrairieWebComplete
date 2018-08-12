<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" href="./style.css">
        <title>Accueil</title>
    </head>
    <body>
        <jsp:include page="Header.jsp" flush="true"/>          
        <div class="row">
            <jsp:include page="catalogue/Themes.jsp" flush="true"/>

            <div class="col-xs-9">
                <h1>Résultat recherche</h1>

                <c:forEach var="l" items="${liste}">
                    <div>
                        <a class="lienLivre" href="Controller?section=detailLivre&livIsbn=${l.isbn}">      
                            <div class="row">
                                <c:if test="${l.image != null}">
                                    <img class="couverture col-xs-3" src="${l.image}" height="100" width="70">                
                                </c:if>

                                <div class="col-xs-9 livreInformations">
                                    ${l.titre}

                                    <c:if test="${l.sousTitre != null}">
                                        ${l.sousTitre}
                                    </c:if>

                                    <br>

                                    <c:forEach var="a" items="${l.auteurs}">
                                        ${a.prenom} ${a.nom}
                                    </c:forEach>

                                    ${l.editeur}

                                    ${l.isbn}

                                    <br>

                                    ${l.prixTtc} €

                                    <a href="Controller?section=commentaires&livreSelectionne=${l.isbn}">Commentaires</a>
                                </div>
                            </div>
                        </a>

                        <div class="boutonAjouter">
                            <form action="Controller" method="post">
                                <input type="hidden" name="section" value="recherche">
                                <input type="hidden" name="livIsbn" value="${l.isbn}">
                                <input type="submit" name="doIt" value="Ajouter">
                            </form>
                        </div>
                        <br>
                    </div>

                </c:forEach>
            </div>
        </div>
        <jsp:include page="bottom.jsp" flush="true" />
    </body>
</html>
