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
    <input type="submit" name="doIt" value="Evenement">
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


<%-- SECTION RELATIVE AU CLIENT : --%>

<form action="Controller" method="post">
    <input type="hidden" name="section" value="headerClient">
    <%-- Bouton pour la connexion (d'un client déjà existant) : --%>
    <input type="submit" name="connecterClient" value="Connexion">
    <%-- Bouton pour la création de compte (d'un nouveau client) : --%>
    <input type="submit" name="creerCompteClient" value="Créer votre compte">
</form>