<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<%@ page import="java.util.List" %>
<%@ page import="beans.entity.Annonce" %>
<core:import url="Header.jsp" />
			
		<div id="main-wrapper">
			<div class="container">
				<div id="content">
				<div class="img-add-annonce"><a href="StandardPage.jsp"><img alt="ajout_icon" src="images/icon_add.png"></a></div>
					
					<%
					List<Annonce> annonces = (List<Annonce>) request.getAttribute( "annonces" );
					
					if (annonces.size() == 0) {
					%>
					<i><p>Vous n'avez pas d'Utilisateurs créées</p></i>
					<%
					} else {
					%>
					<h2>Utilisateurs</h2>
					<table class="table table-striped">
					  <thead>
					    <tr>
					      <th scope="col">Nom</th>
					      <th scope="col">Prénom</th>
					      <th scope="col">E-mail</th>
					      <th scope="col">Adresse</th>
					      <th scope="col">Ville</th>
					      <th scope="col">Code Postal</th>
					      <th scope="col">Mot de passe</th>
					      <th scope="col">Téléphone</th>
					      <th scope="col">Point bonus</th>
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