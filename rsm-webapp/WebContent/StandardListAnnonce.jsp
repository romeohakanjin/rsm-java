<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<core:import url="Header.jsp" />
<%@ page import="java.util.List" %>
<%@ page import="beans.entity.Annonce" %>

 <div class="header-container">
	<div>
		<ul>
			<li><a href="StandardServlet" class="modifier">Retour</a></li>
		</ul>
	</div>
	<div>
		<ul>
			<li><a href="StandardListAnnonce" class="annonces">Annonces</a></li>
		</ul>
	</div>
</div>
<div  class="gestion-utilisateur">
	<div class="container">
		<div id="content">
			<%	List<Annonce> annonces = (List<Annonce>) request.getAttribute("annonces"); %>
			
			<h2>Annonces</h2>
			<table class="table table-striped">
			  <thead>
			    <tr>
			      <th scope="col" style = "text-align: center">N°</th>
			      <th scope="col" style = "text-align: center">Titre</th>
			      <th scope="col" style = "text-align: center">Description</th>
			      <th scope="col" style = "text-align: center">Capacité maximum</th>
			      <th scope="col" style = "text-align: center">Date de création</th>
			    </tr>
			  </thead>
			  <tbody>
			<% for(Annonce annonce : annonces) { %>	
			    <tr>
			      <th scope="row" style = "text-align: center"><%= annonce.getId_annonce() %></th>
			      <td style = "text-align: center"><%= annonce.getTitre() %></td>
			      <td style = "text-align: center"><%= annonce.getDescription() %></td>
			      <td style = "text-align: center"><%= annonce.getCapacite_max() %></td>
			      <td style = "text-align: center"><%= annonce.getDate_creation() %></td>
			    </tr>
			<% } %>	
			  </tbody>
			</table>
		</div>            
	</div>
</div>
<core:import url="Footer.jsp" />
 