<jsp:directive.page contentType="text/html;charset=UTF-8" />
<!DOCTYPE HTML>
<html>
<head>
	<title>HomePage</title>
	<meta http-equiv="Content-Type" content="text/html">
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<!--[if lte IE 8]><script src="js/ie/html5shiv.js"></script><![endif]-->
	<link rel="stylesheet" href="css/main.css" />
	<!--[if lte IE 8]><link rel="stylesheet" href="css/ie8.css" /><![endif]-->
</head>
<body class="homepage">
	<div id="page-wrapper">

		<!-- Header -->
		<div id="header-wrapper">
			<header id="header" class="container">

				<!-- Logo -->
				<div id="logo">
					<h1>
						<a href="Home.jsp">RSM</a>
					</h1>
				</div>

				<!-- Nav -->
				<nav id="nav">
					<ul>
						<li class="current"><a href="Home.jsp">Accueil</a></li>
						<% 	if (session.getAttribute("login") != null){%>
								<li><a href="Deconnection">Déconnexion</a></li>
						<% } else{
							%> 	<li><a href="Connexion.jsp">Connexion</a></li>
							 	<li><a href="Inscription.jsp">Inscription</a></li> <%
						}
						%>
					</ul>
				</nav>

			</header>
		</div>

		<div id="banner-wrapper">
			<div id="banner" class="box container">
				<div class="row">
					<div class="7u 12u(medium)">
						<h2>Hi. This is Verti.</h2>
						<p>It's a free responsive site template by HTML5 UP</p>
					</div>
					<div class="5u 12u(medium)">
						<ul>
							<li><a href="#"
								class="button big icon fa-arrow-circle-right">Ok let's go</a></li>
							<li><a href="#"
								class="button alt big icon fa-question-circle">More info</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>

		<!-- Features -->
		<div id="features-wrapper">
			<div class="container">
				<div class="row">
					<div class="4u 12u(medium)">

						<!-- Box -->
						<section class="box feature">
							<a href="#" class="image featured"><img
								src="images/pic01.jpg" alt="" /></a>
							<div class="inner">
								<header>
									<h2>Put something here</h2>
									<p>Maybe here as well I think</p>
								</header>
								<p>Phasellus quam turpis, feugiat sit amet in, hendrerit in
									lectus. Praesent sed semper amet bibendum tristique fringilla.</p>
							</div>
						</section>

					</div>
					<div class="4u 12u(medium)">

						<!-- Box -->
						<section class="box feature">
							<a href="#" class="image featured"><img
								src="images/pic02.jpg" alt="" /></a>
							<div class="inner">
								<header>
									<h2>An interesting title</h2>
									<p>This is also an interesting subtitle</p>
								</header>
								<p>Phasellus quam turpis, feugiat sit amet in, hendrerit in
									lectus. Praesent sed semper amet bibendum tristique fringilla.</p>
							</div>
						</section>

					</div>
					<div class="4u 12u(medium)">

						<!-- Box -->
						<section class="box feature">
							<a href="#" class="image featured"><img
								src="images/pic03.jpg" alt="" /></a>
							<div class="inner">
								<header>
									<h2>Oh, and finally ...</h2>
									<p>Here's another intriguing subtitle</p>
								</header>
								<p>Phasellus quam turpis, feugiat sit amet in, hendrerit in
									lectus. Praesent sed semper amet bibendum tristique fringilla.</p>
							</div>
						</section>

					</div>
				</div>
			</div>
		</div>

		<!-- Main -->
		<div id="main-wrapper">
			<div class="container">
				<div class="row 200%">
					<div class="4u 12u(medium)">

						<!-- Sidebar -->
						<div id="sidebar">
							<section class="widget thumbnails">
								<h3>Interesting stuff</h3>
								<div class="grid">
									<div class="row 50%">
										<div class="6u">
											<a href="#" class="image fit"><img src="images/pic04.jpg"
												alt="" /></a>
										</div>
										<div class="6u">
											<a href="#" class="image fit"><img src="images/pic05.jpg"
												alt="" /></a>
										</div>
										<div class="6u">
											<a href="#" class="image fit"><img src="images/pic06.jpg"
												alt="" /></a>
										</div>
										<div class="6u">
											<a href="#" class="image fit"><img src="images/pic07.jpg"
												alt="" /></a>
										</div>
									</div>
								</div>
								<a href="#" class="button icon fa-file-text-o">More</a>
							</section>
						</div>

					</div>
					<div class="8u 12u(medium) important(medium)">

						<!-- Content -->
						<div id="content">
							<section class="last">
								<h2>So what's this all about?</h2>
								<p>
									This is <strong>Verti</strong>, a free and fully responsive
									HTML5 site template by <a href="http://html5up.net">HTML5
										UP</a>. Verti is released under the <a
										href="http://html5up.net/license">Creative Commons
										Attribution license</a>, so feel free to use it for any personal
									or commercial project you might have going on (just don't
									forget to credit us for the design!)
								</p>
								<p>Phasellus quam turpis, feugiat sit amet ornare in,
									hendrerit in lectus. Praesent semper bibendum ipsum, et
									tristique augue fringilla eu. Vivamus id risus vel dolor auctor
									euismod quis eget mi. Etiam eu ante risus. Aliquam erat
									volutpat. Aliquam luctus mattis lectus sit amet phasellus quam
									turpis.</p>
								<a href="#" class="button icon fa-arrow-circle-right">Continue
									Reading</a>
							</section>
						</div>

					</div>
				</div>
			</div>
		</div>

		<!-- Footer -->
		<div id="footer-wrapper">
			<footer id="footer" class="container">
				<div class="row">
					<div class="3u 6u(medium) 12u$(small)">

						<!-- Links -->
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

						<!-- Links -->
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

						<!-- Links -->
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

						<!-- Contact -->
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

	<!-- Scripts -->

	<script src="js/jquery.min.js"></script>
	<script src="js/jquery.dropotron.min.js"></script>
	<script src="js/skel.min.js"></script>
	<script src="js/util.js"></script>
	<!--[if lte IE 8]><script src="js/ie/respond.min.js"></script><![endif]-->
	<script src="js/main.js"></script>

</body>
</html>