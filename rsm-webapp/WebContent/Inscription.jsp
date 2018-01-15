<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Inscription</title>
	</head>
	<body>
		<h2>Inscription</h2>
		<form method='get' action='Inscription'>
			<select name='selectTypeUtilisateur'>
				<option value="utilisateur">Utilisateur</option>
				<option value="hotelier">Hôtelier</option>
			</select>
			
			<label>Identifiant</label>
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
			
			<br/>
			<input type='text' name='nomHotel'/>
			<label>Nom hôtel</label>
			
			<input type='submit' name='submit' value ="submit">
		</form>
	</body>
</html>