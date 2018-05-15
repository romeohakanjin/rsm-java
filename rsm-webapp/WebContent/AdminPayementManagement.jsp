<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<core:import url="Header.jsp" />
<%@ page import="java.util.List"%>
<!-- Contenu partiel -->
<div class="content">
	<div id="main-wrapper">
		<div class="container">
			<div id="content">
					<h2>Liste des paiements</h2>
					<%
					List<Object[]> paiementList = (List<Object[]>) request.getAttribute( "paiementList" );

					if (paiementList.size() == 0) {
					%>
					<p>Il n'y a aucun paiement</p>
					<%
					} else {
					%>
					<table class="table table-striped">
						<thead>
							<tr>
								<th scope="col">Utilisateur</th>
								<th scope="col">N° Réservation</th>
								<th scope="col">Prix</th>
								<th scope="col">Date Paiement</th>
								<th scope="col">Commentaire</th>
							</tr>
						</thead>
						<tbody>
							<%
							for (int i = 0; i < paiementList.size(); i++){
							%>
								<tr>
							      <td><%= paiementList.get(i)[0] %> <%= paiementList.get(i)[1] %></td>
							      <td><%= paiementList.get(i)[3] %></td>
							      <td><%= paiementList.get(i)[4] %></td>
							      <td><%= paiementList.get(i)[5] %></td>
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