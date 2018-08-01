<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Themes</h1>
<c:forEach var="t" items="${themes}">
    <a href="Controller?section=accueil&themeSelectionne=${t}">${t}</a><br>
    <c:if test="${t.nom eq themeSelectionneAfficher}">
        <c:forEach var="s" items="${sousThemes}">
            <a href="Controller?section=livresSousTheme&themeSelectionne=${t}&sousThemeSelectionne=${s}">${s}</a><br>
        </c:forEach>
    </c:if>
</c:forEach>
