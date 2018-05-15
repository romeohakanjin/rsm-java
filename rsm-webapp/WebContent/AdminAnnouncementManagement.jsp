<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<core:import url="Header.jsp" />
<%@ page import="java.util.List" %>
<%@ page import="beans.entity.Annonce" %>
<!-- Contenu partiel -->
<div class="content">
	<div id="main-wrapper">
		<div class="container">
			<div id="content">
				<%
						List<Annonce> annonces = (List<Annonce>) request.getAttribute( "annonces" );
						
						if (annonces.size() == 0) {
						%>
				<p>Vous n'avez pas d'annonce publiée</p>
				<%
						} else {
						%>
				<h2>Liste des annonces de chambre d'hotel de l'application</h2>
				<table class="table table-striped">
					<thead>
						<tr>
							<th scope="col">N°</th>
							<th scope="col">Titre</th>
							<th scope="col">Description</th>
							<th scope="col">Capacité maximum</th>
							<th scope="col">Date d'ajout</th>
							<th scope="col">Supprimer</th>
						</tr>
					</thead>
					<tbody>
						<%
						int cpt= 1;
			            for(Annonce annonce : annonces){
			            	%>
						<tr>
							<th scope="row"><%= annonce.getId_annonce() %></th>
							<td><%= annonce.getTitre() %></td>
							<td><%= annonce.getDescription() %></td>
							<td><%= annonce.getCapacite_max() %></td>
							<td><%= annonce.getDate_creation() %></td>
							<td><a
								href='AnnouncementManagementServlet?action=Delete&annonceId=<%= annonce.getId_annonce() %>'><img
									alt="delete_icon" src="images/icon_suppression.png"></a></td>
						</tr>
						<%
			            }
			            %>
					</tbody>
				</table>
				<%
						}
						%>
			</div>
		</div>
	</div>
</div>

<core:import url="Footer.jsp" />