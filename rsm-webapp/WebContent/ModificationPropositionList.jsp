<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<%@ page import="java.util.List" %>
<%@ page import="beans.entity.PropositionModificationAnnonce" %>
<core:import url="Header.jsp" />
		<div id="main-wrapper">
			<div class="container">
				<div id="content">	
					<%
					List<PropositionModificationAnnonce> propositionsModifications = (List<PropositionModificationAnnonce>) request.getAttribute( "propositionsModifications" );
					
					if(propositionsModifications.size() == 0){
					%>
						<p>Aucune proposition de modification</p>
					<%
					} else {
						%>
						<h2>Proposition de modification</h2>
						<table class="table table-striped">
						  <thead>
						    <tr>
						      <th scope="col">N°</th>
						      <th scope="col">Nom</th>
						      <th scope="col">Quantité</th>
						      <th scope="col">Date</th>
						      <th scope="col">Lien annonce</th>
						      
						      <% if(session.getAttribute("session-admin") != null){ %>
						      	<th scope="col">Ignorer</th>
						      
						      <% } else if(session.getAttribute("session-hotelier") != null){ %>
							    <th scope="col">Accepter</th>
							    <th scope="col">Ignorer</th>
						      <% } %>
						      
						    </tr>
						<%
			            for(PropositionModificationAnnonce propositionModification : propositionsModifications){
			            	%>	
						    <tr>
						      <th scope="row"><%= propositionModification.getId_proposition_modif_annonce() %></th>
						      <td><%= propositionModification.getNom() %></td>
						      <td><%= propositionModification.getQuantite() %></td>
						      <td><%= propositionModification.getDate_proposition() %></td>
						      <td><a href="AnnoncesDetailsServlet?annonceId=<%= propositionModification.getId_annonce() %>">lien</a></td>
						     
						     <% if(session.getAttribute("session-admin") != null){ %>
						     <td><a href='AnnouncementPropositionServlet?action=ignore&modificationPropositionId=<%= propositionModification.getId_proposition_modif_annonce() %>'><img alt="delete_icon"
						     src="images/icon_suppression.png"></a></td>
						     
						     <% } else if(session.getAttribute("session-hotelier") != null){ %>
						     <td><a href='AnnouncementPropositionServlet?action=propositionAccepte&modificationPropositionId=<%= propositionModification.getId_proposition_modif_annonce() %>&annonceId=<%= propositionModification.getId_annonce() %>'><img alt="delete_icon"
						     src="images/icon_valid.png"></a></td>
						     <td><a href='AnnouncementPropositionServlet?action=ignore&modificationPropositionId=<%= propositionModification.getId_proposition_modif_annonce() %>'><img alt="delete_icon"
						     src="images/icon_suppression.png"></a></td>
						     <% } %>
						     
						    </tr>
						<%
						}
						}
					%>
					</tbody>
					</table>
				</div>
			</div>
		</div>
<core:import url="Footer.jsp" />