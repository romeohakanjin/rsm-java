<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<core:import url="Header.jsp" />
<%@ page import="java.util.List" %>
<%@ page import="beans.entity.TypeUtilisateur" %>
<%@ page import="beans.entity.Utilisateur" %>
<!-- Contenu partiel -->
		<div class="content">
			<div id="main-wrapper">
				<div class="container">
					<div id="content">
						<h2>Liste des utilisateurs de l'application</h2>
						<%
						List<Utilisateur> userList = (List<Utilisateur>) request.getAttribute( "userList" );

						if (userList.size() == 0) {
						%>
						<p>Il n'y a aucun utilisateur inscrit</p>
						<%
						} else {
						%>
						<table class="table table-striped">
							<thead>
								<tr>
									<th scope="col">Prénom</th>
									<th scope="col">Nom</th>
									<th scope="col">Type d'utilisateur</th>
									<th scope="col">Supprimer</th>
								</tr>
							</thead>
							<tbody>
								<%
								for (Utilisateur user : userList) {
									String libelle = "";
									switch(user.getId_type_utilisateur()){
									case 1:	libelle = "Administrateur";
										break;
									case 2: libelle = "Hotelier";
										break;
									case 3: libelle = "Utilisateur Standard";
										break;
									}
								%>
								<tr>
									<td><a href='UserManagementServlet?action=Display&userId=<%= user.getId_utilisateur() %>' ><%= user.getPrenom() %></a></td>
									<td><a href='UserManagementServlet?action=Display&userId=<%= user.getId_utilisateur() %>' ><%= user.getNom() %></a></td>
									<td><a href='UserManagementServlet?action=Display&userId=<%= user.getId_utilisateur() %>' ><%= libelle %></a></td>
									<td><a href='UserManagementServlet?action=DeleteUser&userId=<%= user.getId_utilisateur() %>'><img alt="delete_icon" src="images/icon_suppression.png"></a></td>
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