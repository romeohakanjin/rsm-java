<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<core:import url="Header.jsp" />

<div id="main-wrapper">
	<div class="container">
		<div id="content">
			<h2>Annnonces</h2>
			<form method='get' action='HotelierAnnonceServlet'>
				<label>Titre</label>
				<input type='text' name='titre' required />
				<label>Description</label>
				<textarea rows="10" cols="50" name="description" maxlength="1000" required>
				</textarea>
				<label>Capacite maximum</label>
				<input type='text' name='capaciteMax' required />
				
				<%
				if (request.getAttribute("annonce-hotelier-modification") != null) {
				%>
				<input type='submit' name='submit' value="submit">
				<%
					}
				%>
			</form>
		</div>
	</div>
</div>

<core:import url="Footer.jsp" />