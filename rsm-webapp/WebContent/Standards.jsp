<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<core:import url="Header.jsp" />
<%@ page import="beans.entity.Utilisateur"%>

<div id="main-wrapper">
	<div class="gestion-utilisateur">

		<h2 class="annonce">Editer vos informations :</h2>
		<div class="modification">
			<%
				if (request.getAttribute("userInformations") != null) {
					Utilisateur utilisateur = (Utilisateur) request.getAttribute("userInformations");
					session.setAttribute("userInformations", utilisateur.getId_utilisateur());
			%>
			<form method='get' action='UpdateUserServlet'>
				<h3>
					<label>Nom</label>
				</h3>
				<input type='text' name='nom' value="<%=utilisateur.getNom()%>" /><br />
				<h3>
					<label>Prenom</label>
				</h3>
				<input type='text' name='prenom'
					value="<%=utilisateur.getPrenom()%>" /><br />
				<h3>
					<label>E-mail</label>
				</h3>
				<input type='text' name='mail' value="<%=utilisateur.getMail()%>" /><br />
				<h3>
					<label>Telephone</label>
				</h3>
				<input type='text' name='mobile'
					value="<%=utilisateur.getMobile()%>" /><br />
				<h3>
					<label>Adresse</label>
				</h3>
				<textarea rows="10" cols="30" name="adresse" maxlength="1000"><%=utilisateur.getAdresse()%></textarea>
				<br />
				<h3>
					<label>Ville</label>
				</h3>
				<input type='text' name='ville'
					value="<%=utilisateur.getVille()%>" /><br />
				<h3>
					<label>Code postal</label>
				</h3>
				<input type='text' name='codePostal'
					value="<%=utilisateur.getCode_postal()%>" /><br /> <input
					class="submit-utilisateur" type='submit'
					name='submitButtonUtilisateurForm' value="modifier">
				<%
					}
				%>
			</form>
		</div>
	</div>
</div>

<core:import url="Footer.jsp" />