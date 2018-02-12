package hotelier;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.entity.Annonce;
import beans.session.AnnonceSessionBean;

/**
 * Servlet implementation class HotelierAnnonceListServlet
 */
@WebServlet("/HotelierAnnonceListServlet")
public class HotelierAnnonceListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String TABLEAU_BORD = "Template";
	private static final String LISTE_ANNONCES = "HotelierAnnoncesListe";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;

	@EJB
	AnnonceSessionBean annonceSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;

		initialiser();
		
		String identifiant = (String) this.session.getAttribute("login");
		int idUtilisateur = annonceSessionBean.getIdUtilisateur(identifiant);
		
		List<Annonce> annonces = annonceSessionBean.getAllAnnonceUtilisateur(idUtilisateur);
		this.request.setAttribute("annonces", annonces);
		redirectionToView(LISTE_ANNONCES);
	}

	/**
	 * Itinaliser les variables
	 * 
	 * @throws IOException
	 */
	private void initialiser() throws IOException {
		this.session = request.getSession();
		this.response.setContentType("text/html");

	}

	/**
	 * Feed request attribute
	 * 
	 * @param variable
	 * @param message
	 */
	private void setVariableToView(String variable, String message) {
		request.setAttribute(variable, message);
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
		this.getServletContext().getRequestDispatcher("/"+view + ".jsp").forward(request, response);

	}
}
