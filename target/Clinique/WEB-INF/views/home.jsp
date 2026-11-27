<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Clinique - Accueil</title>
</head>
<body>
<%@ include file="_menu.jspf" %>
<main>
    <%
        Object total = request.getAttribute("total");
        String erreur = (String) request.getAttribute("erreur");
    %>
    <h1>Gestion de la clinique</h1>
    <p class="meta">Application web construite avec le framework maison et Spring JDBC.</p>

    <% if (erreur != null) { %>
        <div class="alerte alerte-erreur"><%= com.projet.util.Html.escape(erreur) %></div>
    <% } %>

    <div class="carte">
        <h2>Patients</h2>
        <p>
            <% if (total != null) { %>
                <strong><%= total %></strong> patient(s) enregistre(s) dans la base.
            <% } else { %>
                Nombre de patients indisponible.
            <% } %>
        </p>
        <div class="actions">
            <a class="btn" href="<%= request.getContextPath() %>/app/patients">Voir la liste</a>
            <a class="btn btn-secondaire" href="<%= request.getContextPath() %>/app/patients/new">Ajouter un patient</a>
        </div>
    </div>
</main>
</body>
</html>
