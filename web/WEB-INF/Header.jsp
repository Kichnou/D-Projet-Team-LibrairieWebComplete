<%-- ENTETE --%>

<header>
    <h2 class="entete">
        <image src="images/icone header.jpg" class="image">
        <em class="titre">Dark Side Bookstore</em>
        <em class="sousTitre">Come to the dark side. We have cookies.</em>
    </h2>
</header>

<%-- RECHERCHE --%>

<nav>

    <div class="flex-container">

        <div>            
            <form action="Controller" method="post" class="menu">
                Rechercher :
                <input type="hidden" name="section" value="recherche">
                <input type="text" name="motRecherche">
                <input type="submit" name="doIt" value="Ok">
            </form>
        </div>


        <%-- RECHERCHE AVANCEE --%>

        <div>
            <form action="Controller" method="post">
                <input type="hidden" name="section" value="rechercheAvancee">
                <input type="submit" name="doIt" value="Recherche avancée">
            </form>
        </div>

        <%-- PANIER --%>

        <div>
            <form action="Controller" method="post">
                <input type="hidden" name="section" value="panier">
                <input type="submit" name="doIt" value="Panier">
            </form>
        </div>

        <%-- EVENEMENTS --%>

        <%-- bouton evenements --%>
        <div>
            <form action="Controller" method="post">
                <input type="hidden" name="section" value="evenements">
                <input type="submit" name="doIt" value="Événement">
            </form>
        </div>

        <%-- bouton Coup de coeur --%>
        <div>
            <form action="Controller" method="post">
                <input type="hidden" name="section" value="coupDeCoeur">
                <input type="submit" name="doIt" value="Coup de c&#339;ur">
            </form>
        </div>

        <%-- bouton promo --%>
        <div>
            <form action="Controller" method="post">
                <input type="hidden" name="section" value="promo">
                <input type="submit" name="doIt" value="Promotions">
            </form>
        </div>


        <%-- SECTION RELATIVE AU CLIENT : --%>

        <div>
            <form action="Controller" method="post">
                <input type="hidden" name="section" value="headerClient">
                <%-- Bouton pour la connexion (d'un client déjà existant) : --%>
                <input type="submit" name="connecterClient" value="Connexion">
                <%-- Bouton pour la création de compte (d'un nouveau client) : --%>
                <input type="submit" name="creerCompteClient" value="Créer votre compte">
            </form>
        </div>

    </div>

</nav>
