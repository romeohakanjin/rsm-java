<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<%@ page import="beans.entity.Annonce" %>
<%@ page import="java.util.List" %>
<%@ page import="beans.entity.Commentaire" %>
<core:import url="Header.jsp" />

			<div id="main-wrapper">
				<div class="container">
					<div id="content">
							<%
							if (request.getAttribute("annonceDetails") != null) {
								Annonce annonce = (Annonce) request.getAttribute("annonceDetails");
							%>
							<h3>Vue de détail</h3>
								<label>Titre</label>
								<p><%= annonce.getTitre() %> </p>
								<label>Description</label>
								<p><%= annonce.getDescription() %></p>
								<label>Capacite maximum</label>
								<p><%= annonce.getCapacite_max() %></p>
								<label>Prix / nuit</label>
								<p><%= annonce.getPrix_nuit() %></p>
								<%
									}
								%>
								
					</div>
				</div>
			</div>

<core:import url="Footer.jsp" />