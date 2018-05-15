<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<core:import url="Header.jsp" />

<div id="main-wrapper">
	<div class="container">
		<div id="content">
			<h2>Inscription</h2>
			<form method='get' action='Inscription'>
				<select name='selectTypeUtilisateur'>
					<option value="utilisateur">Utilisateur</option>
					<option value="hotelier">Hôtelier</option>
				</select> <label>Identifiant</label> 
				<input type='text' name='identifiant'/>
				<label>Mot de passe</label>
				<input type='text' name='motDePasse'/>
				<label>Nom</label>
				<input type='text' name='nom'/> 
				<label>Prenom</label>
				<input type='text' name='prenom'/>
				<label>Téléphone</label> 
				<input type='text' name='telephone'/> 
				<label>Adresse</label> 
				<input type='text' name='adresse'/> 
				<label>Code postal</label> 
				<input type='text' name='codePostal'/> 
				<label>Ville</label> 
				<input type='text' name='ville'/> 
				<label>Nom hôtel</label>
				<input type='text' name='nomHotel'/>
				<input type='submit' name='submit' value="submit">
			</form>
		</div>
	</div>
</div>

<core:import url="Footer.jsp" />