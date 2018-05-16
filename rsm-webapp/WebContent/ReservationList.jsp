<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<%@ page import="java.util.List" %>
<%@ page import="beans.entity.Reservation" %>
<core:import url="Header.jsp" />
			
		<div id="main-wrapper">
			<div class="container">
				<div id="content">	
					<%
					List<Reservation> reservationsPending = (List<Reservation>) request.getAttribute( "reservationsPending" );
					List<Reservation> reservationsFinish = (List<Reservation>) request.getAttribute( "reservationsFinish" );
					
					if((reservationsPending.size() == 0) && (reservationsFinish.size()) == 0){
					%>
						<p>Vous n'avez pas effectué de réservation</p>
					<%
					} else {
					
					if (reservationsPending.size() != 0) {
						%>
						<h2>Réservations en cours</h2>
						<table class="table table-striped">
						  <thead>
						    <tr>
						      <th scope="col">N°</th>
						      <th scope="col">Date début</th>
						      <th scope="col">Date fin</th>
						      <th scope="col">Nombre de jour</th>
						      <th scope="col">Prix</th>
						      <th scope="col">Supprimer</th>
						    </tr>
						  </thead>
						  <tbody>
						<%
			            for(Reservation reservation : reservationsPending){
			            	%>	
						    <tr>
						      <th scope="row"><%= reservation.getId_reservation() %></th>
						      <td><%= reservation.getDate_debut_sejour() %></td>
						      <td><%= reservation.getDate_fin_sejour() %></td>
						      <td><%= reservation.getDuree_sejour() %></td>
						      <td><%= reservation.getPrix() %></td>
						     <td><a href='ReservationListServlet?action=cancel&reservationId=<%= reservation.getId_reservation() %>'><img alt="delete_icon"
						     src="images/icon_suppression.png"></a></td>
						    </tr>
						    <%
			            %>	
						  </tbody>
						</table>
						<%
						}
						}
					
						if (reservationsFinish.size() != 0) {
						%>
						<h2>Réservations terminées</h2>
						<table class="table table-striped">
						  <thead>
						    <tr>
						      <th scope="col">N°</th>
						      <th scope="col">Date début</th>
						      <th scope="col">Date fin</th>
						      <th scope="col">Nombre de jour</th>
						      <th scope="col">Prix</th>
						      <th>lien annonce</th>
						    </tr>
						  </thead>
						  <tbody>
						<%
			            for(Reservation reservation : reservationsFinish){
			            	%>	
						    <tr>
						      <th scope="row"><%= reservation.getId_reservation() %></th>
						      <td><%= reservation.getDate_debut_sejour() %></td>
						      <td><%= reservation.getDate_fin_sejour() %></td>
						      <td><%= reservation.getDuree_sejour() %></td>
						      <td><%= reservation.getPrix() %></td>
						      <td><a href="AnnoncesDetailsServlet?annonceId=<%= reservation.getId_annonce() %>">lien</a></td>
						    </tr>
						    <%
			            }
			            %>	
						  </tbody>
						</table>
						<%
						}}
					%>
				</div>
			</div>
		</div>
		
<core:import url="Footer.jsp" />