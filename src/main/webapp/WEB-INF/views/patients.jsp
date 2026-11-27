<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.projet.model.Patient" %>
<%@ page import="com.projet.util.Html" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Clinique - Patients</title>
</head>
<body>
<%@ include file="_menu.jspf" %>
<main>
    <%
        @SuppressWarnings("unchecked")
        List<Patient> patients = (List<Patient>) request.getAttribute("patients");
        String message = (String) request.getAttribute("message");
        String erreur = (String) request.getAttribute("erreur");
    %>

    <div class="actions" style="justify-content: space-between; margin-bottom: 1rem;">
        <div>
            <h1 style="margin: 0;">Patients</h1>
            <% if (patients != null) { %>
                <p class="meta" style="margin: .25rem 0 0;"><%= patients.size() %> patient(s)</p>
            <% } %>
        </div>
        <a class="btn" href="<%= request.getContextPath() %>/app/patients/new">+ Nouveau patient</a>
    </div>

    <% if (message != null) { %>
        <div class="alerte alerte-succes"><%= Html.escape(message) %></div>
    <% } %>
    <% if (erreur != null) { %>
        <div class="alerte alerte-erreur"><%= Html.escape(erreur) %></div>
    <% } %>

    <% if (patients == null || patients.isEmpty()) { %>
        <div class="carte">
            <p class="vide">Aucun patient enregistre pour le moment.</p>
        </div>
    <% } else { %>
        <div class="carte" style="padding: 0; overflow-x: auto;">
            <table>
                <thead>
                <tr>
                    <th>#</th>
                    <th>Nom</th>
                    <th>Telephone</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <% for (Patient patient : patients) { %>
                    <tr>
                        <td><%= patient.getId() %></td>
                        <td><%= Html.escape(patient.getNom()) %></td>
                        <td><%= Html.escape(patient.getTelephone()) %></td>
                        <td>
                            <div class="actions">
                                <a class="btn btn-secondaire"
                                   href="<%= request.getContextPath() %>/app/patients/show?id=<%= patient.getId() %>">Detail</a>
                                <a class="btn btn-secondaire"
                                   href="<%= request.getContextPath() %>/app/patients/edit?id=<%= patient.getId() %>">Modifier</a>
                                <form method="post"
                                      action="<%= request.getContextPath() %>/app/patients/delete"
                                      onsubmit="return confirm('Supprimer ce patient ?');">
                                    <input type="hidden" name="id" value="<%= patient.getId() %>">
                                    <button type="submit" class="danger">Supprimer</button>
                                </form>
                            </div>
                        </td>
                    </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    <% } %>
</main>
</body>
</html>
