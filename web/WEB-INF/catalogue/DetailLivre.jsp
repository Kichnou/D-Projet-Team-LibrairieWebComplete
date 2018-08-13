<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" href="./style.css">
        <title>Informations détaillées</title>
    </head>
    <body>
        <jsp:include page="../Header.jsp" flush="true"/>
        <div class="row">
            <jsp:include page="Themes.jsp" flush="true"/>

            <div class="col-xs-9">
                <h1>Détail du livre</h1>

                <div class="row">
                    <div class="col-xs-3">
                        <c:if test="${livre.image != null}">
                            <img src="${livre.image}" height="100" width="70">                
                        </c:if>
                    </div>

                    <div class="col-xs-9">
                        <div class="row">
                            titre : <h3 class="titreLivre">${livre.titre}<br></h3>
                        </div>

                        Sous-Titre : 
                        <c:if test="${livre.sousTitre != null}">
                            ${livre.sousTitre}
                        </c:if>
                        <br>

                        Auteur(s) : 
                        <c:forEach var="a" items="${livre.auteurs}">
                            ${a.prenom} ${a.nom}
                        </c:forEach>
                        <br>

                        Editeur : 
                        ${livre.editeur}
                        <br>

                        N° ISBN : 
                        ${livre.isbn}
                        <br>

                        Prix : 
                        ${livre.prixTtc}€<br>


                        Résumé : <br>
                        <div class="resume">
                            ${livre.resume}<br>
                        </div>

                        <a href="Controller?section=commentaires&livreSelectionne=${livre.isbn}">Commentaires</a>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="../bottom.jsp" flush="true" />
    </body>
</html>
