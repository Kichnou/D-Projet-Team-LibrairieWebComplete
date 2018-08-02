<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" href="./style.css">
        <title>Evaluations</title>
    </head>
    <body>
        <jsp:include page="../Header.jsp" flush="true"/>
        <jsp:include page="Themes.jsp" flush="true"/>
        <h1>Evaluations</h1>
        <c:forEach var="e" items="${listeEval}">
            <c:if test="${e.note == -1}">
                N/A
            </c:if>
            
            <c:if test="${e.note != -1}">
                ${e.note}
            </c:if>
            
            ${e.commentaire}
            <br>
        </c:forEach>
    </body>
</html>
