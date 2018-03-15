<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<%@ page import="java.util.List" %>
<%@ page import="beans.entity.Annonce" %>
<%@ page import="beans.entity.Reservation" %>
<%@ page import="beans.entity.EtatReservation" %>
<core:import url="Header.jsp" />
			
		<div id="main-wrapper">
			<div class="container">
				<div id="content">
					<%
					if (request.getAttribute("error-hotelier-reservations-list") != null) {
					%>
					<p class="error-form"><%= request.getAttribute("error-hotelier-reservations-list") %></p>
					<%
						}
					%>
					<%
					List<Object[]> reservations = (List<Object[]>) request.getAttribute( "reservations" );
					
					if (reservations.size() == 0) {
					%>
					<p>Vous n'avez pas de réservation pour l'instant.</p>
					<%
					} else {
					%>
					<h2>Réservations</h2>
					<table class="table table-striped">
					  <thead>
					    <tr>
					    	<th scope="col">Titre</th>
					      <th scope="col">Prix</th>
					      <th scope="col">Capacité maximum</th>
					      <th scope="col">Date début séjour</th>
					      <th scope="col">Date fin séjour</th>
					      <th scope="col">Etat réservation</th>
					      <th scope="col">Accepter</th>
					      <th scope="col">Refuser</th>
					    </tr>
					  </thead>
					  <tbody>
					<%
					int cpt= 1;
					for(int i=0; i < reservations.size(); i++) {
						Reservation reservation = (Reservation) reservations.get(i)[0];
						Annonce annonce = (Annonce) reservations.get(i)[1];
						EtatReservation etatReservation = (EtatReservation) reservations.get(i)[2];
		            	%>	
					    <tr>
					      <td><%= annonce.getTitre() %></td>
					      <td><%= reservation.getPrix() %></td>
					      <td><%= annonce.getCapacite_max() %></td>
					      <td><%= reservation.getDate_sejour() %></td>
					      <td><%= reservation.getDate_sejour() %></td>
					      <td><%= etatReservation.getLibelle() %></td>
					      <td><a href='HotelierReservationServlet?action=ValiderReservation&reservationId=<%= reservation.getId_reservation() %>' ><img alt="validate_icon" src="images/icon_valid.png"></a></td>
					      <td><a href='HotelierReservationServlet?action=RefuserReservation&reservationId=<%= reservation.getId_reservation() %>'><img alt="refuse_icon" src="images/icon_ban.png"></a></td>
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