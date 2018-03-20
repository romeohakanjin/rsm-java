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
							</ul></li>
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
							</ul></li>
						<%
							} else { %>
								<li id="standard"><a href="AnnoncesServlet">Annonces</a>
									<ul>
										<li id="standard"><a
										href="AnnoncesServlet">Liste Annonces</a></li>
									</ul>
								</li>							
							<% }
						
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