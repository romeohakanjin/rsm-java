<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<core:import url="Header.jsp" />

<div id="main-wrapper">
	<div class="container">
		<div id="content">
		<%
		if (request.getParameter("annonceId") != null) {
		%>
			<form method="get" action="AnnoncesDetailsServlet">
				<input type="hidden" name="annonceId" value="<%= request.getParameter("annonceId") %>">
				<h3>Proposition de modification</h3>
				<label>Nom du service</label>
				<input type='text' name='nom' required />
				<label>Quantité</label> 
				<input type='text' name='quantite' required />
				<input type="submit" name="action" value="Ajouter" />
			</form>
		<%
		}
		%>
		</div>
	</div>
</div>

<core:import url="Footer.jsp" />