<%-- 
    Document   : coupDeCoeur
    Created on : 15 août 2018, 22:51:06
    Author     : pc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
         <center>  <h1>Coups De Coeur</h1></center>
    <center> <h2>好书推荐</h2></center>
        <c:forEach var="monCoup" items="${mesCoupsDeCoeur}">
       
            
       
        <c:if test="${monCoup.image != null}">
                <div style="vertical-align:middle; clear: both;">
                    <img src="${monCoup.image}"height="100"width="70"
                    style ="vertical-align: middle;float:left">
                    
                    <p>
                         ${monCoup.livreTitre}<br>
                        Nombre Encore Disponible: ${monCoup.livreQuantite}
                    </p>
                </div>
            </c:if>
        </c:forEach>
        
    </body>
</html>
