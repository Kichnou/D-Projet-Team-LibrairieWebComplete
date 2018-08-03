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

        <h1>Détail du livre</h1>

    <c:if test="${livre.image != null}">
        <img src="${livre.image}" height="100" width="70">                
    </c:if>

    ${livre.titre}

    <c:if test="${livre.sousTitre != null}">
        ${livre.sousTitre}
    </c:if>

    <c:forEach var="a" items="${livre.auteurs}">
        ${a.prenom} ${a.nom}
    </c:forEach>

    ${livre.editeur}

    ${livre.isbn}

    ${livre.prixTtc}
    
    ${livre.resume}

    <a href="Controller?section=commentaires&livreSelectionne=${livre.isbn}">Commentaires</a>

</body>
</html>
