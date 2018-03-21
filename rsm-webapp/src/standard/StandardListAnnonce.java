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
import beans.session.UtilisateurSessionBean;

@WebServlet("/StandardListAnnonce")
public class StandardListAnnonce extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String LISTE_ANNONCES = "StandardListAnnonce";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;

	@EJB
	UtilisateurSessionBean utilisateurSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		

		initialiser(request, response);
		showListAnnonces();
	}

	/**
	 * Itinaliser les variables
	 * 
	 * @throws IOException
	 */
	private void initialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		this.request = request;
		this.response = response;
		this.session = request.getSession();
		this.response.setContentType("text/html");
	}
	
	/**
	 * Shows all the Annonces available
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showListAnnonces() throws ServletException, IOException {
		
		List<Annonce> annonces = utilisateurSessionBean.getAllAnnonceUtilisateur();
		this.request.setAttribute("annonces", annonces);
		redirectionToView(LISTE_ANNONCES);
	}

	/**
	 * Redirection to a view
	 * 
	 * @param String
	 * @throws ServletException
	 * @throws IOException
	 */
	private void redirectionToView(String view) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/" + view + ".jsp").forward(request, response);
	}
}
