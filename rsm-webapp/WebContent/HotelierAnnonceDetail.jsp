<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<%@ page import="beans.entity.Annonce" %>
<%@ page import="java.util.List" %>
<%@ page import="beans.entity.Commentaire" %>
<%@ page import="beans.entity.ServiceChambre"%>
<core:import url="Header.jsp" />

			<div id="main-wrapper">
				<div class="container">
					<div id="content">
							<%
							if (request.getAttribute("annonceDetails") != null) {
								Annonce annonce = (Annonce) request.getAttribute("annonceDetails");
							%>
								<h3>Annonce</h3>
								<label>Titre</label>
								<p><%= annonce.getTitre() %> </p>
								<label>Description</label>
								<p><%= annonce.getDescription() %></p>
								<label>Capacite maximum</label>
								<p><%= annonce.getCapacite_max() %></p>
								
								
								<form>
								<h3>Service</h3>
									<label>Nom</label>
									<input type='text' name='serviceName' required />
									<label>Quantité</label>
									<input type='text' name='serviceQuantity' required />
									<input type="hidden" name="idAnnonce" value="<%= annonce.getId_annonce() %>">
			
									<input type='submit' name='action' value="Ajouter Service">
									
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
									}
								%>
								
								<%
								List<Commentaire> comments = (List<Commentaire>) request.getAttribute( "commentsList" );
								
								if (comments.size() == 0) {
								%>
								<p>Aucun commentaire publié</p>
								<%
								} else {
								%>
								<h2>Commentaires</h2>
								<table class="table table-striped">
								  <thead>
								    <tr>
								      <th scope="col">N°</th>
								      <th scope="col">Commentaire</th>
								      <th scope="col">Signaler</th>
								    </tr>
								  </thead>
								  <tbody>
								<%
								int cpt= 1;
					            for(Commentaire comment : comments){
					            	%>	
								    <tr>
								      <th scope="row"><%= comment.getId_commentaire() %></th>
								      <td><%= comment.getCommentaire() %></td>
								      <%
										if(comment.getId_etat_commentaire() == 3){
										%>
								      		<td><a href='HotelierAnnonceDetailsServlet?action=ignore&commentaireId=<%= comment.getId_commentaire() %>'>
								      		<img alt="ignore_icon" src="images/ignore_icon.png"></a>
								      		</td>
								    	<%
										} else {
										%>
											<td><a href='HotelierAnnonceDetailsServlet?action=signaler&commentaireId=<%= comment.getId_commentaire() %>'>
								      		<img alt="ban_icon" src="images/icon_ban.png"></a>
								      		</td>
								    	<%
										}
										%>
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