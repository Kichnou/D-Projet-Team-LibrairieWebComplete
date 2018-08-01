<%-- 
    Document   : participant
    Created on : Jul 31, 2018, 10:14:18 AM
    Author     : Lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
       <%--<jsp:include page="Header.jsp" flush="true"/>--%>
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
    </body>
</html>
