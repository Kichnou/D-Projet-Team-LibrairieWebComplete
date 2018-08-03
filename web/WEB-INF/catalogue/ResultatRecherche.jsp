<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" href="./style.css">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="../Header.jsp" flush="true"/>
        <jsp:include page="Themes.jsp" flush="true"/>

        <h1>Résultat recherche</h1>

        <c:forEach var="l" items="${liste}">
            <a href="Controller?section=detailLivre&livIsbn=${l.isbn}">
                <c:if test="${l.image != null}">
                    <img src="${l.image}" height="100" width="70">                
                </c:if>

                ${l.titre}

                <c:if test="${l.sousTitre != null}">
                    ${l.sousTitre}
                </c:if>

                <c:forEach var="a" items="${l.auteurs}">
                    ${a.prenom} ${a.nom}
                </c:forEach>

                ${l.editeur}

                ${l.isbn}

                ${l.prixTtc}

                <a href="Controller?section=commentaires&livreSelectionne=${l.isbn}">Commentaires</a>
            </a>

            <form action="Controller" method="post">
                <input type="hidden" name="section" value="recherche">
                <input type="hidden" name="livIsbn" value="${l.isbn}">
                <input type="submit" name="doIt" value="Ajouter">
            </form>
            <br>
        </c:forEach>
    </body>
</html>
