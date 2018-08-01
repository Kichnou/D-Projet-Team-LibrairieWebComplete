<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Evaluations</title>
    </head>
    <body>
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
