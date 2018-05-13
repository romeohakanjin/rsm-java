<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<%@ page import="java.util.List" %>
<%@ page import="beans.entity.Reservation" %>
<core:import url="Header.jsp" />
			
		<div id="main-wrapper">
			<div class="container">
				<div id="content">	
					<%
					List<Reservation> reservations = (List<Reservation>) request.getAttribute( "reservations" );
					
					if (reservations.size() == 0) {
					%>
					<p>Vous n'avez pas effectu� de r�servation</p>
					<%
					} else {
					%>
					<h2>R�servations</h2>
					<table class="table table-striped">
					  <thead>
					    <tr>
					      <th scope="col">N�</th>
					      <th scope="col">Date d�but</th>
					      <th scope="col">Date fin</th>
					      <th scope="col">Nombre de jour</th>
					      <th scope="col">Prix</th>
					      <th scope="col">Supprimer</th>
					    </tr>
					  </thead>
					  <tbody>
					<%
		            for(Reservation reservation : reservations){
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
		
<core:import url="Footer.jsp" />