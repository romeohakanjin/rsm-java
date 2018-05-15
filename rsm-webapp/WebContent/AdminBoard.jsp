<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<core:import url="Header.jsp" />
<%@ page import="java.util.List" %>
<!-- Contenu partiel -->
		<div class="content">
			<div id="main-wrapper">
				<div class="container">
					<div class="content">
						<h2>Nombre d'utilisateur</h2>
						<%
						List<Object[]> nbUserList = (List<Object[]>) request.getAttribute( "nbUserList" );

						if (nbUserList.size() == 0) {
						%>
						<p>Il n'y a aucun utilisateur inscrit</p>
						<%
						} else {
						%>
						<table class="table table-striped">
							<thead>
								<tr>
									<th scope="col">Nombre</th>
									<th scope="col">Type d'utilisateur</th>
								</tr>
							</thead>
							<tbody>
								<%

								for (int i = 0; i < nbUserList.size(); i++) {
								%>
								<tr>
									<th scope="row"><%= Integer.valueOf(nbUserList.get(i)[0].toString()) %></th>
									<td><%= nbUserList.get(i)[1].toString() %></td>
								</tr>
								<%
							}
							%>
						</tbody>
						</table>
						<%
						}
						%>

						<h2>Nombre d'annonce</h2>
						<%
						Integer nbAnnonce = (Integer) request.getAttribute( "nbAnnonce" );

						if (nbAnnonce == 0) {
						%>
						<p>Il n'y a aucun annonce publiée</p>
						<%
						} else {
						%>
						<table class="table table-striped">
							<thead>
								<tr>
									<th scope="col">Nombre</th>
								</tr>
							</thead>
							<tbody>

								<tr>
									<th scope="row"><%= nbAnnonce %></th>
								</tr>
							</tbody>
						</table>
						<%
						}
						%>

						<h2>Nombre de réservation</h2>
						<%
						List<Object[]> nbReservationList = (List<Object[]>) request.getAttribute( "nbReservationList" );

						if (nbReservationList.size() == 0) {
						%>
						<p>Il n'y a aucune réservation</p>
						<%
						} else {
						%>
						<table class="table table-striped">
							<thead>
								<tr>
									<th scope="col">Nombre</th>
									<th scope="col">Etat de la réservation</th>
								</tr>
							</thead>
							<tbody>
								<%

								for (int i = 0; i < nbReservationList.size(); i++) {
								%>
								<tr>
									<th scope="row"><%= Integer.valueOf(nbReservationList.get(i)[0].toString()) %></th>
									<td><%= nbReservationList.get(i)[1].toString() %></td>
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