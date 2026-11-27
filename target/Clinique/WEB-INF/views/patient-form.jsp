<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.projet.model.Patient" %>
<%@ page import="com.projet.util.Html" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Clinique - Patient</title>
</head>
<body>
<%@ include file="_menu.jspf" %>
<main>
    <%
        Patient patient = (Patient) request.getAttribute("patient");
        if (patient == null) {
            patient = new Patient();
        }
        boolean edition = "edit".equals(request.getAttribute("mode"));
        String erreur = (String) request.getAttribute("erreur");
        String action = request.getContextPath() + (edition ? "/app/patients/update" : "/app/patients");
    %>

    <h1><%= edition ? "Modifier le patient #" + patient.getId() : "Nouveau patient" %></h1>

    <% if (erreur != null) { %>
        <div class="alerte alerte-erreur"><%= Html.escape(erreur) %></div>
    <% } %>

    <div class="carte">
        <form method="post" action="<%= action %>" class="champ">
            <% if (edition) { %>
                <input type="hidden" name="id" value="<%= patient.getId() %>">
            <% } %>

            <div class="ligne">
                <label for="nom">Nom complet</label>
                <input type="text" id="nom" name="nom" required maxlength="255"
                       value="<%= Html.escape(patient.getNom()) %>"
                       placeholder="Ex. Rakoto">
            </div>

            <div class="ligne">
                <label for="telephone">Telephone</label>
                <input type="tel" id="telephone" name="telephone" required maxlength="255"
                       value="<%= Html.escape(patient.getTelephone()) %>"
                       placeholder="Ex. 033 45 977 22">
            </div>

            <div class="actions">
                <button type="submit"><%= edition ? "Enregistrer les modifications" : "Creer le patient" %></button>
                <a class="btn btn-secondaire" href="<%= request.getContextPath() %>/app/patients">Annuler</a>
            </div>
        </form>
    </div>
</main>
</body>
</html>
