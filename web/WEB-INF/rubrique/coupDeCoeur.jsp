<%-- 
    Document   : coupDeCoeur
    Created on : 15 août 2018, 22:51:06
    Author     : pc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="./style.css">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="../Header.jsp" flush="true" />
        <div class="row">
            <jsp:include page="../catalogue/Themes.jsp" flush="true" />
            <div class="col-xs-9">
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
            </div>
        </div>
    </body>
</html>
