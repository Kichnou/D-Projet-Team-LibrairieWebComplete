<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" href="./style.css">
        <title>Recherche avancée</title>
    </head>
    <body>        
        <jsp:include page="../Header.jsp" flush="true"/>
        <div class="row">
            <jsp:include page="Themes.jsp" flush="true"/>
            <div class="col-xs-9">
                <div class="rechercheAvancee">
                    <form action="Controller" method="get">
                        <input type="hidden" name="section" value="resultatRechercheAvancee">
                        <input type="text" name="motRecherche">
                        <input type="submit" name="doIt" value="Ok"><br>
                        Critères de recherche<br>
                        <input type="checkbox" name="critereRecherche" value="auteur" checked>
                        Auteur
                        <input type="checkbox" name="critereRecherche" value="isbn" checked>
                        ISBN
                        <input type="checkbox" name="critereRecherche" value="titre" checked>
                        Titre
                        <input type="checkbox" name="critereRecherche" value="MotCle" checked>
                        Mot clé
                    </form>
                </div>
            </div>
        </div>
        <jsp:include page="../bottom.jsp" flush="true" />            
    </body>
</html>
