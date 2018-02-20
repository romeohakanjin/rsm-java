<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<%@ page import="java.util.List" %>
<%@ page import="beans.entity.Annonce" %>
<core:import url="Header.jsp" />
			
		<div id="main-wrapper">
			<div class="container">
				<div id="content">
				<div class="img-add-annonce"><a href="HotelierAnnonce.jsp"><img alt="ajout_icon" src="images/icon_add.png"></a></div>
					
					<%
					List<Annonce> annonces = (List<Annonce>) request.getAttribute( "annonces" );
					
					if (annonces.size() == 0) {
					%>
					<p>Vous n'avez pas d'annonce publiée</p>
					<%
					} else {
					%>
					<h2>Annonces</h2>
					<table class="table table-striped">
					  <thead>
					    <tr>
					      <th scope="col">N°</th>
					      <th scope="col">Titre</th>
					      <th scope="col">Description</th>
					      <th scope="col">Capacité maximum</th>
					      <th scope="col">Date d'ajout</th>
					      <th scope="col">Modifier</th>
					      <th scope="col">Supprimer</th>
					    </tr>
					  </thead>
					  <tbody>
					<%
					int cpt= 1;
		            for(Annonce annonce : annonces){
		            	%>	
					    <tr>
					      <th scope="row"><%= annonce.getId_annonce() %></th>
					      <td><a href='HotelierAnnonceDetailsServlet?annonceId=<%= annonce.getId_annonce() %>'><%= annonce.getTitre() %></a></td>
					      <td><%= annonce.getDescription() %></td>
					      <td><%= annonce.getCapacite_max() %></td>
					      <td><%= annonce.getDate_creation() %></td>
					      <td><a href='HotelierAnnonceServlet?action=ModifierAnnonce&annonceId=<%= annonce.getId_annonce() %>' ><img alt="modifier_icon" src="images/icon_modification.png"></a></td>
					      <td><a href='HotelierAnnonceServlet?action=SupprimerAnnonce&annonceId=<%= annonce.getId_annonce() %>'><img alt="delete_icon" src="images/icon_suppression.png"></a></td>
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