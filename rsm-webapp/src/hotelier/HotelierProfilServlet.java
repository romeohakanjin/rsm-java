package hotelier;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.entity.Hotel;
import beans.entity.Utilisateur;
import beans.session.HotelSessionBean;
import beans.session.UtilisateurSessionBean;

/**
 * @author SLI
 */
@WebServlet("/HotelierProfilServlet")
public class HotelierProfilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String INFOS_HOTELIER = "HotelierProfilPage";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;

	@EJB
	UtilisateurSessionBean utilisateurSessionBean;
	@EJB
	HotelSessionBean hotelSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;

		initialize();
		showUserInformations();
	}

	/**
	 * Initialize the values
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		this.session = request.getSession();
		this.response.setContentType("text/html");
	}
	
	/**
	 * display user informations
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showUserInformations() throws ServletException, IOException {
		String identifiant = (String) this.session.getAttribute("login");
		int idUtilisateur = utilisateurSessionBean.getIdUtilisateur(identifiant); 
		Utilisateur utilisateur = utilisateurSessionBean.getUser(idUtilisateur);
		Hotel hotel = hotelSessionBean.getHotelById(utilisateur.getId_hotel());
		this.request.setAttribute("userInformations", utilisateur);
		this.request.setAttribute("hotelInformations", hotel);
		redirectionToView(INFOS_HOTELIER);
	}

	/**
	 * Redirection to a view
	 * 
	 * @param String
	 *            : the view name
	 * @throws ServletException
	 * @throws IOException
	 */
	private void redirectionToView(String view) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/" + view + ".jsp").forward(request, response);
	}	
}