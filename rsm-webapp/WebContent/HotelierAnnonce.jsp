<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<%@ page import="beans.entity.Annonce" %>
<core:import url="Header.jsp" />

			<div id="main-wrapper">
				<div class="container">
					<div id="content">
						<%
							if (request.getAttribute("error-hotelier-annonce-form") != null) {
						%>
								<p class="error-form"><%= request.getAttribute("error-hotelier-annonce-form") %></p>
						<%
							}
						%>
						<h2>Annnonce hotelier</h2>
						<%
							if (request.getAttribute("annonceEdited") != null) {
								Annonce annonce = (Annonce) request.getAttribute("annonceEdited");
 								session.setAttribute("sessionAnnonceId", annonce.getId_annonce());
						%>
								<!-- if the user modify a annnonce -->
								<form method='get' action='HotelierAnnonceServlet'>
									<label>Titre</label>
									<input type='text' name='titre' value="<%= annonce.getTitre() %>" required />
									<label>Description</label>
									<textarea rows="10" cols="50" name="description" maxlength="1000" required><%= annonce.getDescription() %></textarea>
									<label>Capacite maximum</label>
									<input type='text' name='capaciteMax' value="<%= annonce.getCapacite_max() %>" required />
									
									<input type='submit' name='submitButtonHotelierForm' value="Modifier">
								</form>
								<%
									} else {
								%>	<!-- if the user dosn't modify a annnonce -->
									<form method='get' action='HotelierAnnonceServlet'>
									<label>Titre</label>
									<input type='text' name='titre' required />
									<label>Description</label>
									<textarea rows="10" cols="50" name="description" maxlength="1000" required>
									</textarea>
									<label>Capacite maximum</label>
									<input type='text' name='capaciteMax' required />
									
									<input type='submit' name='submitButtonHotelierForm' value="Ajouter">
								</form>
								<%
									}
								%>
					
					</div>
				</div>
			</div>
					
<core:import url="Footer.jsp" />