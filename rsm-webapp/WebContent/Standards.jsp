<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<core:import url="Header.jsp" />

<div id="main-wrapper">
	<div class="container">
		<div id="content">
			<h2>Utilisateur</h2>
			<form method='get' action='StandardServlet'>
				<label>Nom</label>
				<input type='text' name='nom' required /><br/>
				<label>Prénom</label>
				<input type='text' name='prenom' required /><br/>
				<label>Téléphone</label>
				<input type='text' name='telephone' required /><br/>
				<label>E-mail</label>
				<input type='text' name='e-mail' required /><br/>
				<label>Mot de passe</label>
				<input type='text' name='motdepasse' required /><br/>
				<label>Adresse</label>
				<textarea rows="10" cols="30" name="adresse" maxlength="1000" required></textarea><br/>
				<label>Ville</label>
				<input type='text' name='Ville' required /><br/>
				<label>Code postal</label>
				<input type='text' name='codepostal' required /><br/>
				<label>Point bonus</label>
				<input type='text' name='pointbonus' required /><br/>
				<input type='submit' name='submit' value="Créer">
				
				<%
				if (request.getAttribute("annonce-hotelier-modification") != null) {
				%>
				<input type='submit' name='submit' value="Creer">
				<%
					}
				%>
			</form>
		</div>
	</div>
</div>

<core:import url="Footer.jsp" />