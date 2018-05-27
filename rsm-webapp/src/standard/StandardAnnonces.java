package standard;

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
public class StandardAnnonces extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String LISTE_ANNONCES = "StandardAnnonces";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;

	@EJB
	AnnonceSessionBean annoncementSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;

		initialize();

		String identifiant = (String) this.session.getAttribute("login");
		int idUtilisateur = annoncementSessionBean.getUserId(identifiant);

		List<Annonce> annonces = annoncementSessionBean.getAllUserAnnouncement(idUtilisateur);
		this.request.setAttribute("annonces", annonces);
		redirectionToView(LISTE_ANNONCES);
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
