<%-- RECHERCHE --%>

Rechercher :
<form action="Controller" method="post">
    <input type="hidden" name="section" value="recherche">
    <input type="text" name="motRecherche">
    <input type="submit" name="doIt" value="Ok">
</form>


<%-- RECHERCHE AVANCEE --%>

<form action="Controller" method="post">
    <input type="hidden" name="section" value="rechercheAvancee">
    <input type="submit" name="doIt" value="Recherche Avancée">
</form>

<%-- PANIER --%>

<a href="Controller?section=panier">Panier</a>

<%-- EVENEMENTS --%>

<%-- bouton evenements --%>
<form action="Controller" method="post">
    <input type="hidden" name="section" value="evenements">
    <input type="submit" name="doIt">
</form>

<%-- bouton Coup de coeur --%>
<form action="Controller" method="post">
    <input type="hidden" name="section" value="coupDeCoeur">
    <input type="submit" name="doIt" value="Coup de coeur">
</form>

<%-- bouton promo --%>
<form action="Controller" method="post">
    <input type="hidden" name="section" value="promo">
    <input type="submit" name="doIt" value="Promotions">
</form>


<%-- CONNECTION UTILISATEUR --%>

<%-- connection --%>
<form action="Controller" method="post">
    login : <input type="text" name="login">
    Mot de passe : <input type="password" name="mdp">
    <input type="submit" name="doIt" value="Connexion"
</form>

<%-- création de compte --%>
<form action="Controller" method="post">
    <input type="submit" name="doIt" value="S'inscrire">
</form>