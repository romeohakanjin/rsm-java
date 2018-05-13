<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>HomePage</title>
<meta http-equiv="Content-Type" content="text/html">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="css/main.css" />
<link rel="stylesheet" href="css/style.css" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body class="homepage">
	<div id="page-wrapper">
		<div id="header-wrapper">
			<header id="header" class="container">
				<div id="logo">
					<h1>
						<a href="Home.jsp">RSM</a>
					</h1>
				</div>
				<nav id="nav">
					<ul>
						<li id="home" class="current"><a href="Home.jsp">Accueil</a></li>
						<li id="standard"><a href="AnnoncesServlet">Annonces</a>
							<ul>
								<li id="standard"><a href="AnnoncesServlet">Liste Annonces</a></li>
							</ul>
						</li>
						<%
							if (session.getAttribute("session-admin") != null) {
						%>
						<li id="gestionAdmin"><a href="DashboardServlet">Gestion
								Admin</a>
							<ul>
								<li class="current" id="dashboard"><a
									href="DashboardServlet">Tableau de bord</a></li>
								<li id="userManagement"><a href="UserManagementServlet">Gestion
										des utilisateurs</a></li>
								<li id="announcementManagement"><a
									href="AnnouncementManagementServlet">Gestion des annonces</a></li>
								<li id="externAnnouncementManagement"><a
									href="ExternAnnouncementManagementServlet">Gestion des
										annonces d'activités externes</a></li>
								<li id="commentManagement"><a href="CommentManagementServlet">
								Gestion des commentaires signalés</a></li>
								<li id="payementManagement"><a href="PayementManagementServlet">
								Gestion des paiements</a></li>
							</ul>
						</li>
						<%
							} else if (session.getAttribute("session-hotelier") != null) {
						%>
						<li id="gestionHotelier"><a href="HotelierBordServlet">Gestion
								Hotelier</a>
							<ul>
								<li id="hotelierListServlet"><a
									href="HotelierAnnonceListServlet">Annonces</a></li>
								<li id="hotelierListServlet"><a
									href="HotelierReservationListServlet">Réservations</a></li>
							</ul>
						</li>
						<li id="updateUser"><a href="HotelierProfilServlet">Informations Hotelier</a></li>
						<%
							} else if (session.getAttribute("session-standard") != null){ %>
								<li id="standard">
									<a href="StandardServlet">Informations personnelles</a>
									<ul>
										<li><a href="ReservationListServlet?action=list">Réservation en cours</a></li>
										<li><a href="UpdateUserServlet?action=modifierUtilisateur" class="modifier">Modifier informations</a></li>
										<li><a href="DeactivateUserAccount?action=deactivateUser">Désactiver le compte</a></li>
									</ul>
								</li>							
							<% }
						%>
						<%
							if (session.getAttribute("login") != null) {
						%>
						<li id="deconnexion"><a href="Deconnection">Déconnexion</a></li>
						<%
							} else {
						%>
						<li id="connexion"><a href="Connexion.jsp">Connexion</a></li>
						<li id="inscription"><a href="Inscription.jsp">Inscription</a></li>
						<%
							}
						%>
					</ul>
				</nav>
			</header>
		</div>
		
		<!-- Contenu partiel -->
		<div class="content">
			<%
				if (request.getAttribute("alert-success") != null) {
			%>
				<div class="alert alert-success" role="alert">
				  <strong>Well done!</strong><p><%= request.getAttribute("alert-success") %></p>
				</div>
			<%
				request.removeAttribute("alert-success");
			
				} else if (request.getAttribute("alert-info") != null) {
			%>
				<div class="alert alert-info" role="alert">
				  <strong>Heads up!</strong><p><%= request.getAttribute("alert-info") %></p>
				</div>
			<%
				request.removeAttribute("alert-info");
			
				} else if (request.getAttribute("alert-warning") != null){
			%>
				<div class="alert alert-warning" role="alert">
				  <strong>Warning!</strong><p><%= request.getAttribute("alert-warning") %></p>
				</div>							
			<% 
				request.removeAttribute("alert-warning");
			
			} else if (request.getAttribute("alert-danger") != null){ 
			%>
				<div class="alert alert-danger" role="alert">
				  <strong>Oh snap!</strong><p><%= request.getAttribute("alert-danger") %></p>
				</div>							
			<% 
				request.removeAttribute("alert-danger");
			} %>
			