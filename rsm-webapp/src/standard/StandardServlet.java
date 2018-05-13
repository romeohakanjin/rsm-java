package standard;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.entity.Utilisateur;
import beans.session.UtilisateurSessionBean;

/**
 * 
 * @author Massneymar
 *
 */
@WebServlet("/StandardServlet")
public class StandardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String INFOS_PERSONNELLES = "StandardPage";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;

	@EJB
	UtilisateurSessionBean utilisateurSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;

		initialiser();
		showUserInformations();
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
	 * Affiche les informations de l'utilisateur
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showUserInformations() throws ServletException, IOException {
		String identifiant = (String) this.session.getAttribute("login");

		int idUtilisateur = utilisateurSessionBean.getIdUtilisateur(identifiant);
		Utilisateur utilisateur = new Utilisateur();
		utilisateur = (Utilisateur) utilisateurSessionBean.getUserInformation(idUtilisateur);

		this.request.setAttribute("userInformations", utilisateur);
		redirectionToView(INFOS_PERSONNELLES);
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
