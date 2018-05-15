<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<core:import url="Header.jsp" />
<core:import url="Search.jsp" />
<%@ page import="java.util.List" %>
<%@ page import="beans.entity.Annonce" %>
<!-- Contenu partiel -->
<div class="content">
	<div id="main-wrapper">
		<div class="container">
			<div id="content">
				<h2>Annonces</h2>
				<%
					List<Annonce> annonces = (List<Annonce>) request.getAttribute( "annonces" );
					
					if (annonces.size() != 0) {
					%>
					<table class="table table-striped">
					  <thead>
					    <tr>
					      	<th scope="col">Titre</th>
					      	<th scope="col">Description</th>
						     <th scope="col">Capacité maximum</th>
						     <th scope="col">Prix/nuit</th>
					    </tr>
					  </thead>
					  <tbody>
					<%
					int cpt= 1;
		            for(Annonce annonce : annonces){
		            	%>	 
					    <tr>
					      <td><a href='AnnoncesDetailsServlet?annonceId=<%= annonce.getId_annonce() %>'><%= annonce.getTitre() %></a></td>
					      <% if((annonce.getDescription()).length() >= 129){ %>
					      <td><%= annonce.getDescription().substring(0,129) %>...</td>
					      <% } else{ %>
					       <td><%= annonce.getDescription() %></td>
					      <% } %>
					      <td><%= annonce.getCapacite_max() %></td>
					      <td><%= annonce.getPrix_nuit() %></td>
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
</div>

<core:import url="Footer.jsp" />