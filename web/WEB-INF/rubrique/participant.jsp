<%-- 
    Document   : participant
    Created on : Jul 31, 2018, 10:14:18 AM
    Author     : Lenovo
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
                <center><h1>DARK SIDE BOOK STORE</h1></center><br>
                <form action="Controller" method ="get">
                    <input type="hidden" name="section" value="participant"/>   
                    Nom: <input type="text" name="nom"><br>
                    Prenom:<input type="text" name="prenom"/><br> 
                    E-Mail:<input type="text" name ="email"/><br>
                    <input type="submit" value="ok" name="doit"/><br>
                    <font color='cyan'>${participationOK}</font<input type="hidden"
                                                                      name="section" value="participant"/>   
                    Nom: <input type="text" name="nom"><br>
                    Prenom:<input type="text" name="prenom"/><br> 
                    E-Mail:<input type="text" name ="email"/><br>
                    <input type="submit" value="ok" name="doit"/><br>
                    <font>
                </form>
            </div>
        </div>
    </body>
</html>
