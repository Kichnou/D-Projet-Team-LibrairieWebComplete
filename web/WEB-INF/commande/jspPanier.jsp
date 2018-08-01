<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Le Panier</title>
    </head>
    <body>
        <jsp:include page="Header.jsp" flush="true"/>
        <h1>Votre Panier</h1> <br/>
        
        <c:forEach var="leLivre" items="${listLig}">
            
            <c:if test="${leLivre.image != null}">
                <img src="${leLivre.image}" height="100" width="70">
            </c:if>

            ${leLivre.titre}
            ${leLivre.prixTtc}
        
        </c:forEach>
                
        <form action="Controller" method="GET">
            <input type="hidden" name="section" value="panier"/>
        </form>
        
    </body>
</html>
