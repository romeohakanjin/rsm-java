package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.entity.Annonce;
import beans.entity.Hotel;
import beans.session.HotelSessionBean;

/**
 * @author RHA
 */
@WebServlet("/HotelServlet")
public class HotelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PrintWriter out;
	private HttpServletRequest request;
	private HttpServletResponse response;
	public String nom;
	public String adresse;
	public String codePostal;
	public String ville;
	
	@EJB
	HotelSessionBean hotelSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initialiser(request, response);
		formulaireCreation();
		
		List<Hotel> hotels = hotelSessionBean.getAllHotels();
		for (Hotel hotel : hotels) {
			out.println(hotel.getNom());
			out.println(hotel.getAdresse());
			out.println(hotel.getVille());
			out.println(hotel.getCode_postal());
		}
	}

	/**
	 * Initialiser
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void initialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		this.request = request;
		this.response = response;
		out = response.getWriter();
		response.setContentType("text/html");
	}
	
	/**
	 * Affiche le formulaire de création
	 * Récupère les informations saisis par l'utilisateur
	 */
	public void formulaireCreation() {
		out.println("<h2>Créer une annonce</h2>");
		out.print("<form method='get' action=''>");
		out.println("Nom de l'hôtel : <input type='text' name='nom'/> <br>");
		out.println("Adresse : <input type='text' name='adresse'/> <br>");
		out.println("Code Postal : <input type='text' name='codePostal'/> <br>");
		out.println("Ville : <input type='text' name='ville'/> <br>");
		out.print("<input type='submit' name='submit'><br>");
		out.print("</form>");
		nom = request.getParameter("nom");
		adresse = request.getParameter("adresse");
		codePostal = request.getParameter("codePostal");
		ville = request.getParameter("ville");
	}

}