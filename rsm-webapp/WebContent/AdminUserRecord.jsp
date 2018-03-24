<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<core:import url="Header.jsp" />
<%@ page import="java.util.List"%>
<%@ page import="beans.entity.Utilisateur"%>
<!-- Contenu partiel -->
<div class="content">
	<div id="main-wrapper">
		<div class="container">
			<div id="content">
				<h2>Fiche utilisateur</h2>
				<%
					Utilisateur user = (Utilisateur) request.getAttribute("user");

					if (user != null) {
				%>
				<h2>
					Profil de
					<%=user.getPrenom()%>
					<%=user.getNom()%></h2>
				<table>
					<tr>
						<td><label> Nom </label></td>
						<td><%=user.getNom()%></td>
					</tr>
					<tr>
						<td><label> Prénom </label></td>
						<td><%=user.getPrenom()%></td>
					</tr>
					<tr>
						<td><label> Adresse mail </label></td>
						<td><%=user.getMail()%></td>
					</tr>
					<tr>
						<td><label> Téléphone </label></td>
						<td><%=user.getMobile()%></td>
					</tr>
					<tr>
						<td><label> Adresse </label></td>
						<td><%=user.getAdresse()%></td>
					</tr>
					<tr>
						<td><label> Code Postal </label></td>
						<td><%=user.getCode_postal()%></td>
					</tr>
					<tr>
						<td><label> Ville </label></td>
						<td><%=user.getVille()%></td>
					</tr>
				</table>

				<%
					}
				%>
			</div>
		</div>
	</div>
</div>

<core:import url="Footer.jsp" />