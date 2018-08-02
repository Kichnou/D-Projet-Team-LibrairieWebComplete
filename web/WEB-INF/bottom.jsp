<%-- 
    Document   : bottom
    Created on : 25 juil. 2018, 11:30:31
    Author     : cdi315
--%>

<style type="text/css">
<jsp:include page="css/styleBottom.css"/>
</style>

<footer>

    <hr class="lignevertefoncee">
    
    <table class="tableau" cellspacing=0 cellpadding=8>
        <tbody>
            <tr>
                <th>Qui sommes-nous ?</th>
                <th class="bordgauchedoubleorange">Nos horaires :</th>
                <th class="bordgauchedoubleorange">Coordonnées de notre service clients :</th>
            </tr>
            <tr>
                <td>${formjur} ${nom}</td>
                <td class="bordgauchedoubleorange">${horlunven}</td>
                <td class="bordgauchedoubleorange">E-mail : ${krlsercli}</td>
            </tr>
            <tr>
                <td>${adresse}</td>
                <td class="bordgauchedoubleorange">${horsam}</td>
                <td class="bordgauchedoubleorange">Fax : ${faxsercli}</td>
            </tr>
            <tr>
                <td>N° SIREN : ${siren}</td>
                <td class="bordgauchedoubleorange">${horfermeture}</td>
                <td class="bordgauchedoubleorange">Téléphone : ${telsercli}</td>
            </tr>
            <tr>
                <td>N° SIRET : ${siret}</td>
                <td class="bordgauchedoubleorange"></td>
                <td class="bordgauchedoubleorange"></td>
        </tbody>
    </table>
            
    <hr class="ligneorange">
    <h4 id ="teamtitre">Site conçu et développé par la team :</h4>
    <p id="team">Alexandre, Edem, Florent, Kevin.</p>
    <p id="teammini">La team &#8660; AlexH4k3R &#8746; Dsnow &#8721; Kinou &#8747; SystemFlo</p>
</footer>