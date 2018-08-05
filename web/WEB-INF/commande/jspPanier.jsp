<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Le Panier</title>
    </head>
    <body>
        <%--jsp:include page="../Header.jsp" flush="true"/--%>
        <h1>Votre Panier</h1> <br/>
        
        <c:if test="${list.size() == 0}">
            <p>Le Panier est vide !!</p>
        </c:if>
        
        <c:forEach var="lig" items="${list}">
            
            <c:if test="${lig.leLivre.image != null}">
                
                <div style="vertical-align:middle; clear: both;">
                    
                    <img src="${lig.leLivre.image}" height="100" width="70" 
                         style="vertical-align:middle; float:left">
                    
                    <p>
                        
                        Titre : ${lig.leLivre.titre} <br/>
                        Quantité : ${lig.quantite} <br/>
                        Prix Unitaire : ${lig.leLivre.prixTtc}
                        Prix : ${lig.leLivre.prixTtc * lig.quantite} € <br/>
<!--                        <a href="Controller?section=panier&add=${lig.leLivre.isbn}">+</a>
                        <a href="Controller?section=panier&dec=${lig.leLivre.isbn}">-</a>
                        <a href="Controller?section=panier&del=${lig.leLivre.isbn}">X</a>-->
                        
                        <form action="Controller" method="GET">
                            <input type="hidden" name="section" value="panier"/>
                            <input type="hidden" name="isbn" value="${lig.leLivre.isbn}"<br/>
                            <input type="submit" name="add" value="+"/>
                            <input type="submit" name="dec" value="-"/>
                            <input type="submit" name="del" value="X"/>
                        </form>
                    </p>
                </div><br/>
            </c:if>
        </c:forEach>
        
        <div>
            <form action="Controller" method="GET">
                <input type="hidden" name="section" value="panier"/>
                <c:if test="${list.size() != 0}">
                    <input type="submit" name="clean" value="Vider le Panier"/>
                    <br/>
                    PRIX TOTAL : ${prixTtc} €
                </c:if>
            </form> 
        </div>
    </body>
</html>
