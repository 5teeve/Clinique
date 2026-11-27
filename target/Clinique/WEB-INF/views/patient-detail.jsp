<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.projet.model.Patient" %>
<%@ page import="com.projet.util.Html" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Clinique - Detail patient</title>
</head>
<body>
<%@ include file="_menu.jspf" %>
<main>
    <%
        Patient patient = (Patient) request.getAttribute("patient");
    %>

    <% if (patient == null) { %>
        <div class="alerte alerte-erreur">Patient introuvable.</div>
        <a class="btn btn-secondaire" href="<%= request.getContextPath() %>/app/patients">Retour a la liste</a>
    <% } else { %>
        <h1>Patient #<%= patient.getId() %></h1>

        <div class="carte">
            <table>
                <tbody>
                <tr>
                    <th>Nom</th>
                    <td><%= Html.escape(patient.getNom()) %></td>
                </tr>
                <tr>
                    <th>Telephone</th>
                    <td><%= Html.escape(patient.getTelephone()) %></td>
                </tr>
                </tbody>
            </table>

            <div class="qr-code">
                <img src="http://cdn.local/patient/<%= patient.getId() %>/qrcode/<%= patient.getId() %>-qrcode.jpeg" alt="qr-code">
            </div>

            <div class="actions" style="margin-top: 1rem;">
                <a class="btn btn-secondaire" href="<%= request.getContextPath() %>/app/patients">Retour</a>
                <a class="btn"
                   href="<%= request.getContextPath() %>/app/patients/edit?id=<%= patient.getId() %>">Modifier</a>
                <form method="post"
                      action="<%= request.getContextPath() %>/app/patients/delete"
                      onsubmit="return confirm('Supprimer ce patient ?');">
                    <input type="hidden" name="id" value="<%= patient.getId() %>">
                    <button type="submit" class="danger">Supprimer</button>
                </form>
            </div>
        </div>
    <% } %>
</main>
</body>
</html>
