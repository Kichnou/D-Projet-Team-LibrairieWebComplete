<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Recherche avancée</title>
    </head>
    <body>
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
    </body>
</html>
