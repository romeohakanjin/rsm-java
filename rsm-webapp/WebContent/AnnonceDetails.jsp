<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<%@ page import="beans.entity.Annonce"%>
<%@ page import="java.util.List"%>
<%@ page import="beans.entity.Commentaire"%>
<%@ page import="beans.entity.ServiceChambre"%>
<core:import url="Header.jsp" />

<div id="main-wrapper">
	<div class="container">
		<div id="content">
			<%
				if (request.getAttribute("annonceDetails") != null) {
					Annonce annonce = (Annonce) request.getAttribute("annonceDetails");
			%>
			<form method="get" action="AnnoncesDetailsServlet">
			<input type="hidden" name="annonceId" value="<%= annonce.getId_annonce() %>" readonly>
			<input type="hidden" name="action" value="reserver" readonly>
			<h3>Vue de détail</h3>
			<label>Titre</label>
			<p><%=annonce.getTitre()%></p>
			<label>Description</label>
			<p><%=annonce.getDescription()%></p>
			<label>Capacite maximum</label>
			<p><%=annonce.getCapacite_max()%></p>
			<label>Prix / nuit</label>
			<p><%=annonce.getPrix_nuit()%></p>
			
			<div>
				<p>Dates de réservation</p>
				<p>
					Date début: <input type="text" name="dateDebut" id="datepickerDebut"
						required />
				</p>
				<p>
					Date fin: <input type="text" name="dateFin" id="datepickerFin"
						required />
				</p>
			</div>
				<%
					if (session.getAttribute("session-admin") == null && session.getAttribute("session-hotelier") == null) {
				%>
				<a><input type="submit" name="submit" value="Réserver"></a>

			<%
				}
				}
			%>
			
			<h2>Service</h2>
				<%
					List<ServiceChambre> roomServices = (List<ServiceChambre>) request.getAttribute( "roomServices" );
					
					if (roomServices.size() != 0) {
						int cpt= 1;
						%>	 
					    <ul>
					    <%
			            for(ServiceChambre roomService : roomServices){
			            	if(roomService.getQuantite() == 1){
			            	%>	 
						      <li><%= roomService.getNom() %></li>
						    <%
			            	} else{
						    	%>	 
							      <li><%= roomService.getNom() %> (<%= roomService.getQuantite() %>)</li>
							    <%
						    }
			            }
					    %>	 
					    </ul>
					    <%
					}
					%>
			</form>
		</div>
	</div>
</div>

<core:import url="Footer.jsp" />

<script>
	$(function() {
		$("#datepickerDebut").datepicker();
	});
	$(function() {
		$("#datepickerFin").datepicker();
	});
</script>
