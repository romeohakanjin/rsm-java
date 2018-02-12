<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ page import="java.util.List" %>
<%@ page import="beans.entity.Annonce" %>
<!DOCTYPE HTML>
<html>
<head>
<title>HomePage</title>
<meta http-equiv="Content-Type" content="text/html">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="css/main.css" />
<link rel="stylesheet" href="css/style.css" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body class="homepage">
	<div id="page-wrapper">
		<div id="header-wrapper">
			<header id="header" class="container">
				<div id="logo">
					<h1>
						<a href="Template.jsp">RSM</a>
					</h1>
				</div>
				<nav id="nav">
					<ul>
						<li class="current"><a href="Template.jsp">Accueil</a></li>
						<%
							if (session.getAttribute("session-hotelier") != null) {
						%>
						<li><a
							href="TemplateHotelier.jsp">Gestion Hotelier</a></li>
						<%
							}
						%>
						<% 	if (session.getAttribute("login") != null){%>
						<li id="deconnexion"><a href="Deconnection">Déconnexion</a></li>
						<% } %>
					</ul>
				</nav>
			</header>
		</div>
		
		
			<!-- Gestion hotelier -->
			<%
				if (session.getAttribute("session-hotelier") != null) {
			%>
			<nav id="nav">
				<ul>
					<li><a
						href="HotelierAnnonceListServlet">Annonce</a></li>
					<li id="hotelier-chambre"><a
						href="javascript:setCurrentPage('hotelier-chambre');">Chambre</a></li>
				</ul>
			</nav>
			<%
				}
			%>
			<!-- Contenu partiel -->
		<div class="content">
		
		<div id="main-wrapper">
			<div class="container">
				<div id="content">
				<div class="img-add-annonce"><a href="HotelierAnnonce.jsp"><img alt="ajout_icon" src="images/icon_add.png"></a></div>
					
					<%
					List<Annonce> annonces = (List<Annonce>) request.getAttribute( "annonces" );
					
					if (annonces.size() == 0) {
					%>
					<p>Vous n'avez pas d'annonce publiée</p>
					<%
					} else {
					%>
					<h2>Annonces</h2>
					<table class="table table-striped">
					  <thead>
					    <tr>
					      <th scope="col">N°</th>
					      <th scope="col">Titre</th>
					      <th scope="col">Description</th>
					      <th scope="col">Capacité maximum</th>
					      <th scope="col">Date d'ajout</th>
					      <th scope="col">Modifier</th>
					      <th scope="col">Supprimer</th>
					    </tr>
					  </thead>
					  <tbody>
					<%
					int cpt= 1;
		            for(Annonce annonce : annonces){
		            	%>	
					    <tr>
					      <th scope="row"><%= annonce.getId_annonce() %></th>
					      <td><%= annonce.getTitre() %></td>
					      <td><%= annonce.getDescription() %></td>
					      <td><%= annonce.getCapacite_max() %></td>
					      <td><%= annonce.getDate_creation() %></td>
					      <td><a href='HotelierAnnonceServlet?action=ModifierAnnonce&annonceId=<%= annonce.getId_annonce() %>' ><img alt="modifier_icon" src="images/icon_modification.png"></a></td>
					      <td><a href='HotelierAnnonceServlet?action=SupprimerAnnonce&annonceId=<%= annonce.getId_annonce() %>'><img alt="delete_icon" src="images/icon_suppression.png"></a></td>
					    </tr>
					    <%
		            }
		            %>	
					  </tbody>
					</table>
					<%
					}
					%>
				</div>
			</div>
		</div>
		
		</div>
		
		<div id="footer-wrapper">
			<footer id="footer" class="container">
				<div class="row">
					<div class="3u 6u(medium) 12u$(small)">
						<section class="widget links">
							<h3>Random Stuff</h3>
							<ul class="style2">
								<li><a href="#">Etiam feugiat condimentum</a></li>
								<li><a href="#">Aliquam imperdiet suscipit odio</a></li>
								<li><a href="#">Sed porttitor cras in erat nec</a></li>
								<li><a href="#">Felis varius pellentesque potenti</a></li>
								<li><a href="#">Nullam scelerisque blandit leo</a></li>
							</ul>
						</section>
					</div>
					<div class="3u 6u$(medium) 12u$(small)">
						<section class="widget links">
							<h3>Random Stuff</h3>
							<ul class="style2">
								<li><a href="#">Etiam feugiat condimentum</a></li>
								<li><a href="#">Aliquam imperdiet suscipit odio</a></li>
								<li><a href="#">Sed porttitor cras in erat nec</a></li>
								<li><a href="#">Felis varius pellentesque potenti</a></li>
								<li><a href="#">Nullam scelerisque blandit leo</a></li>
							</ul>
						</section>
					</div>
					<div class="3u 6u(medium) 12u$(small)">
						<section class="widget links">
							<h3>Random Stuff</h3>
							<ul class="style2">
								<li><a href="#">Etiam feugiat condimentum</a></li>
								<li><a href="#">Aliquam imperdiet suscipit odio</a></li>
								<li><a href="#">Sed porttitor cras in erat nec</a></li>
								<li><a href="#">Felis varius pellentesque potenti</a></li>
								<li><a href="#">Nullam scelerisque blandit leo</a></li>
							</ul>
						</section>
					</div>
					<div class="3u 6u$(medium) 12u$(small)">
						<section class="widget contact last">
							<h3>Contact Us</h3>
							<ul>
								<li><a href="#" class="icon fa-twitter"><span
										class="label">Twitter</span></a></li>
								<li><a href="#" class="icon fa-facebook"><span
										class="label">Facebook</span></a></li>
								<li><a href="#" class="icon fa-instagram"><span
										class="label">Instagram</span></a></li>
								<li><a href="#" class="icon fa-dribbble"><span
										class="label">Dribbble</span></a></li>
								<li><a href="#" class="icon fa-pinterest"><span
										class="label">Pinterest</span></a></li>
							</ul>
							<p>
								1234 Fictional Road<br /> Nashville, TN 00000<br /> (800)
								555-0000
							</p>
						</section>

					</div>
				</div>
				<div class="row">
					<div class="12u">
						<div id="copyright">
							<ul class="menu">
								<li>&copy; Untitled. All rights reserved</li>
								<li>Design: <a href="http://html5up.net">HTML5 UP</a></li>
							</ul>
						</div>
					</div>
				</div>
			</footer>
		</div>
	</div>

	<!-- Script JS -->
	<script src="js/jquery.min.js"></script>
	<script src="js/jquery.dropotron.min.js"></script>
	<script src="js/skel.min.js"></script>
	<script src="js/util.js"></script>
	<script src="js/main.js"></script>
<!-- 	<script src="js/templateHotelier.js"></script> -->

</body>
</html>