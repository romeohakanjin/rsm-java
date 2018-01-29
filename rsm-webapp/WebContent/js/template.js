$(function(){
	redirect();
});

/**
 * Récupère le nom de la page à afficher
 * @param page
 * @returns
 */
function setCurrentPage(page){
	$("li.current").removeClass("current");
	$("#"+page).addClass("current");
	redirect();
}

/**
 * Charge le contenue de la page en fonction
 * du clique sur le menu
 * @returns
 */
function redirect(){
	var currentPage = $("li.current").attr("id");
	switch(currentPage){
		case "home":
			$("div.content").load("Home.jsp");
		break;
		case "connexion":
			$("div.content").load("Connexion.jsp");
		break;
		case "inscription":
			$("div.content").load("Inscription.jsp");
		break;
		case "deconnexion":
			$("div.content").load("Home.jsp");
		break;
		default:
			$("div.content").load("Home.jsp");
	}
}