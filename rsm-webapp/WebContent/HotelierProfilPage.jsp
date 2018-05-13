<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<core:import url="Header.jsp" />
<%@ page import="java.util.List" %>
<%@ page import="beans.entity.Utilisateur" %>
<%@ page import="beans.entity.Hotel" %>

<% Utilisateur utilisateur = (Utilisateur) request.getAttribute("userInformations"); %>
<% Hotel hotel = (Hotel) request.getAttribute("hotelInformations");%>
<div class="header-container">
	<div>
		<ul>
			<li><a href="UpdateUserServlet?action=modifierUtilisateur" class="modifier">Modifier</a></li>
			<li><a href="DeactivateUserAccount?action=deactivateUser&userId=<%= utilisateur.getId_utilisateur()%>">Désactiver le compte</a></li>
		</ul>
	</div>
	<div>
</div>
<div  class="gestion-utilisateur">
	<div class="container">
		<div id="content">
			<h2 class="info-perso">Informations personnelles</h2>
			<div id="content">
					<table class="table table-striped">
					  <thead>
					    <tr>
					      <th scope="col" style = "text-align: center">Nom Hotel</th>
					      <th scope="col" style = "text-align: center">Nom</th>
					      <th scope="col" style = "text-align: center">Prenom</th>
					      <th scope="col" style = "text-align: center">Telephone</th>
					      <th scope="col" style = "text-align: center">E-mail</th>
					      <th scope="col" style = "text-align: center">Adresse</th>
					      <th scope="col" style = "text-align: center">Ville</th>
					      <th scope="col" style = "text-align: center">Code Postal</th>
					    </tr>
					  </thead>
					  <tbody>
					    <tr>
					      <th scrope="row" style = "text-align: center"><%= hotel.getNom_hotel()%></th>
					      <td style = "text-align: center"><%= utilisateur.getNom() %></td>
					      <td style = "text-align: center"><%= utilisateur.getPrenom() %></td>
					      <td style = "text-align: center"><%= utilisateur.getMobile() %></td>
					      <td style = "text-align: center"><%= utilisateur.getMail() %></td>					     
					      <td style = "text-align: center"><%= utilisateur.getAdresse() %></td>
					      <td style = "text-align: center"><%= utilisateur.getVille() %></td>
					      <td style = "text-align: center"><%= utilisateur.getCode_postal() %></td>
					    </tr>
					  </tbody>
					</table>
					</div>            
		</div>
	</div>
</div>
<core:import url="Footer.jsp" />