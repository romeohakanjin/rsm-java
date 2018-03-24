<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<core:import url="Header.jsp" />
<%@ page import="java.util.List"%>
<%@ page import="beans.entity.ActiviteExterne"%>
<!-- Contenu partiel -->
<div class="content">
	<div id="main-wrapper">
		<div class="container">
			<div id="content">
				<%
					List<ActiviteExterne> activiteExternes = (List<ActiviteExterne>) request.getAttribute("activiteExternes");

					if (activiteExternes.size() == 0) {
				%>
				<p>Vous n'avez pas d'activiteExterne publiée</p>
				<%
					} else {
				%>
				<h2>Liste des activités externes</h2>
				<table class="table table-striped">
					<thead>
						<tr>
							<th scope="col">N°</th>
							<th scope="col">Titre</th>
							<th scope="col">Description</th>
							<th scope="col">Ville</th>
							<th scope="col">Type d'activité</th>
						</tr>
					</thead>
					<tbody>
						<%
							int cpt = 1;
								for (ActiviteExterne activiteExterne : activiteExternes) {
									String libelle = "";
									switch (activiteExterne.getId_type_activite()) {
									case 1:
										libelle = "Restauration";
										break;
									case 2:
										libelle = "Musée";
										break;
									case 3:
										libelle = "Parc d'attractions";
										break;
									case 4:
										libelle = "Zoo";
										break;
									case 5:
										libelle = "Evénements";
										break;
									}
						%>
						<tr>
							<th scope="row"><%=activiteExterne.getId_activite_externe()%></th>
							<td><%=activiteExterne.getTitre()%></td>
							<td><%=activiteExterne.getDescription()%></td>
							<td><%=activiteExterne.getVille()%></td>
							<td><%=libelle%></td>
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

<core:import url="Footer.jsp" />