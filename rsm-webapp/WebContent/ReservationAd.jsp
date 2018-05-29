<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<%@ page import="beans.entity.Annonce"%>
<%@ page import="java.util.List"%>
<%@ page import="beans.entity.Commentaire"%>
<core:import url="Header.jsp" />

<div id="main-wrapper">
	<div class="container">
		<div id="content">
			<%
				// 				if (request.getAttribute("annonceDetails") != null) {
				// 					Annonce annonce = (Annonce) request.getAttribute("annonceDetails");
			%>
			
			<% 
			if(((int) request.getAttribute("userPoints")) >= 100 && ((int) session.getAttribute("reservationPrice")) <= 80 ){
				%>
				<a href="ReservationConfirmationServlet?action=pointPaiement">Vous bénéficiez de <%= request.getAttribute("userPoints") %>, cliquez-ici pour payer cette annonce avec vos points</a>
				<br />
				<%
			}
			%>
			
			<form method="get" action="ReservationConfirmationServlet">
				<h3>Confirmer votre réservation</h3>
				<label>Date début</label>
				<p><%=session.getAttribute("reservationDateDebut")%></p>
				<label>Date fin</label>
				<p><%=session.getAttribute("reservationDateFin")%></p>
				<label>Nombre de jour</label>
				<p><%=session.getAttribute("reservationNumberOfDays")%></p>
				<label>Prix</label>
				<p><%=session.getAttribute("reservationPrice")%></p>

				<%
					// if (session.getAttribute("session-admin") == null && session.getAttribute("session-hotelier") == null) {
				%>
				<input type="submit" name="submitButtonReservationValidation"
					value="Confirmer" />
				<%
					// 					}
					// 					}
				%>
			</form>
		</div>
	</div>
</div>

<core:import url="Footer.jsp" />