<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<core:import url="Header.jsp" />
<%@ page import="java.util.List" %>
<!-- Gestion hotelier -->
<%
	if (session.getAttribute("session-hotelier") != null) {
%>

<%
	}
%>
<div id="main-wrapper">
	<div class="container">
		<div id="content">
			<h2>Tableau de bord</h2>
			<%
			long numberOfAnnounce = (Long) request.getAttribute( "numberOfAnnounceForHotelier" );
					
			if (numberOfAnnounce >= 0) {
			%>
			<p>Annonces publiées : <%= numberOfAnnounce %></p>
			<%
			}
            
            List<Object[]> numberOfAnnouncePerState = (List<Object[]>) request.getAttribute( "numberOfAnnouncePerState" );
		
            if (!numberOfAnnouncePerState.isEmpty()) {
            %>
           	<table class="table table-striped">
				<thead>
				    <tr>
				      <th scope="col">Nombre</th>
				      <th scope="col">Etat</th>
				    </tr>
				</thead>
				<tbody>
            	<%
				for (int i = 0; i < numberOfAnnouncePerState.size() ; i++) {
				%>
					    <tr>
					      <td><%= numberOfAnnouncePerState.get(i)[0] %></td>
					      <td><%= numberOfAnnouncePerState.get(i)[1] %></td>
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

<core:import url="Footer.jsp" />