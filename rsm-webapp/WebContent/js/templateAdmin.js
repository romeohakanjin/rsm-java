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
		case "dashboard":
			$(windows).load("AdminBord.jsp");
		break;
		case "userManagement":
			$(windows).load("AdminUserManagement.jsp");
		break;
		case "userRecord":
			$(windows).load("AdminUserRecord.jsp");
		break;
		case "announcementManagement":
			$(windows).load("AdminAnnouncementManagement.jsp");
		break;
		case "externAnnouncementManagement":
			$(windows).load("AdminExternAnnouncementManagement.jsp");
		break;
		default:
			$("div.content").load("Home.jsp");
	}
}