<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<%@ page import="beans.entity.Annonce" %>
<%@ page import="java.util.List"%>
<%@ page import="beans.entity.ServiceChambre"%>
<core:import url="Header.jsp" />
<div id="main-wrapper">
	<div class="container">
		<div id="content">
			<h2>Annonce hotelier</h2>
			<%
				if (request.getAttribute("annonceEdited") != null) {
					Annonce annonce = (Annonce) request.getAttribute("annonceEdited");
						session.setAttribute("sessionAnnonceId", annonce.getId_annonce());
			%>
					<!-- if the user modify a ad -->
					<form method='get' action='HotelierAnnonceServlet'>
						<label>Titre</label>
						<input type='text' name='titre' value="<%= annonce.getTitre() %>" required />
						<label>Description</label>
						<textarea rows="10" cols="50" name="description" maxlength="1000" required><%= annonce.getDescription() %></textarea>
						<label>Capacite maximum</label>
						<input type='text' name='capaciteMax' value="<%= annonce.getCapacite_max() %>" required />
						<label>Prix par nuit</label>
						<input type='text' name='prixNuit' value="<%= annonce.getPrix_nuit() %>" required />
						<br />
						<input type='submit' name='submitButtonHotelierForm' value="Modifier">
					</form>
					<br/><br/>
					<form>
					<h3>Service</h3>
						<label>Nom</label>
						<input type='text' name='serviceName' required />
						<label>Quantité</label>
						<input type='text' name='serviceQuantity' required />
						<input type="hidden" name="idAnnonce" value="<%= annonce.getId_annonce() %>">

						<input type='submit' name='submitServiceButtonHotelierForm' value="Ajouter Service">
						
						<br />
						<h4>Liste des services : (Cliquez sur un service pour le supprimer)</h4>
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
								      <li><a href="HotelierAnnonceServlet?action=deleteService&annonceId=<%= annonce.getId_annonce() %>&serviceId=<%= roomService.getId_service_chambre() %>"><%= roomService.getNom() %></a></li>
								    <%
					            	} else{
								    	%>	 
									      <li><a href="HotelierAnnonceServlet?action=deleteService&annonceId=<%= annonce.getId_annonce() %>&serviceId=<%= roomService.getId_service_chambre() %>"><%= roomService.getNom() %> (<%= roomService.getQuantite() %>)</a></li>
									    <%
								    }
					            }
							    %>	 
							    </ul>
							    <%
							} else{
								%>	
								<p>Vous n'avez aucun service<p>
								<%
							}
							%>
					</form>
		
					<%
						} else {
					%>	<!-- if the user dosn't modify a ad -->
						<form method='get' action='HotelierAnnonceServlet'>
						<label>Titre</label>
						<input type='text' name='titre' required />
						<label>Description</label>
						<textarea rows="10" cols="50" name="description" maxlength="1000" required>
						</textarea>
						<label>Capacite maximum</label>
						<input type='text' name='capaciteMax' required />
						<label>Prix par nuit</label>
						<input type='text' name='prixNuit' required />
						
						<input type='submit' name='submitButtonHotelierForm' value="Ajouter">
					</form>
					<%
						}
					%>
		</div>
	</div>
</div>
<core:import url="Footer.jsp" />