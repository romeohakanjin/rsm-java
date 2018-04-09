<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<core:import url="Header.jsp" />
<%@ page import="java.util.List" %>
<%@ page import="beans.entity.EtatCommentaire" %>
<%@ page import="beans.entity.Commentaire" %>
<!-- Contenu partiel -->
	<div class="content">
		<div id="main-wrapper">
			<div class="container">
				<div id="content">
					<h2>Liste des avis signal�s</h2>
					<%
					List<Object[]> commentList = (List<Object[]>) request.getAttribute( "commentList" );

					if (commentList.size() == 0) {
					%>
					<p>Il n'y a aucun commentaire signalé</p>
					<%
					} else {
					%>
					<table class="table table-striped">
						<thead>
							<tr>
								<th scope="col">Id</th>
								<th scope="col">N° Réservation</th>
								<th scope="col">Auteur</th>
								<th scope="col">Date création</th>
								<th scope="col">Commentaire</th>
								<th scope="col">Valider</th>
								<th scope="col">Supprimer</th>
							</tr>
						</thead>
						<tbody>
							<%
							for (int i = 0; i < commentList.size(); i++){
							%>
								<tr>
							      <td><%= commentList.get(i)[0] %></td>
							      <td><%= commentList.get(i)[1] %></td>
							      <td><%= commentList.get(i)[2] %> <%= commentList.get(i)[3] %></td>
							      <td><%= commentList.get(i)[4] %></td>
							      <td><%= commentList.get(i)[5] %></td>
							      <td><a href='CommentManagementServlet?action=ValidateComment&commentId=<%= commentList.get(i)[0] %>'><img alt="delete_icon" src="images/icon_valid.png"></a></td>
							      <td><a href='CommentManagementServlet?action=RefuseComment&commentId=<%= commentList.get(i)[0] %>'><img alt="delete_icon" src="images/icon_suppression.png"></a></td>
							    </tr>
							<%
							}%>
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