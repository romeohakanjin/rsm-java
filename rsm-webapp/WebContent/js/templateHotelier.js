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
		case "hotelier-chambre":
			$("div.content").load("HotelierChambre.jsp");
		break;
		case "hotelier-annonce":
			$("div.content").load("HotelierAnnonce.jsp");
		break;
		default:
			$("div.content").load("HotelierBord.jsp");
	}
}