package session;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.session.UtilisateurSessionBean;

/**
 * Servlet implementation class Connexion
 */
@WebServlet("/Connection")
public class Connection extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String HOME_PAGE = "Home";
	private static final String CONNECTION_PAGE = "Connexion";
	private static final String ADMIN_HOME_PAGE = "DashboardServlet";
	private static final int ID_TYPE_UTILISATEUR_STANDARD = 3;
	private static final int ID_TYPE_UTILISATEUR_HOTELIER = 2;
	private static final int ID_TYPE_UTILISATEUR_ADMIN = 1;
	private String id;
	private String password;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;

	@EJB
	UtilisateurSessionBean userSessionBean;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initialize(request, response);
		boolean isOkForm = verificationFormulaire();

		if (isOkForm) {
			boolean existingUser = userSessionBean.isIdentificationValid(id, password);
			if (existingUser) {
				int id_type_utilisateur = userSessionBean.getIdTypeUtilisateur(id, password);

				switch (id_type_utilisateur) {
				case ID_TYPE_UTILISATEUR_ADMIN:
					session.setAttribute("session-admin", "admin");
					break;
				case ID_TYPE_UTILISATEUR_HOTELIER:
					session.setAttribute("session-hotelier", "hotelier");
					break;
				case ID_TYPE_UTILISATEUR_STANDARD:
					session.setAttribute("session-standard", "standard");
					break;
				default:
					break;
				}

				httpSession(id, password);
				if(id_type_utilisateur == ID_TYPE_UTILISATEUR_ADMIN) {
					redirectionToServlet(ADMIN_HOME_PAGE);
				}else {
					redirectionToView(HOME_PAGE);
				}
			} else {
				setVariableToView("alert-danger", "Identifiants incorrect");
				redirectionToView(CONNECTION_PAGE);
			}
		} else {
			setVariableToView("alert-danger", "Veuillez saisir tous les champs");
			redirectionToView(CONNECTION_PAGE);
		}

	}
	
	/**
	 * Check the form values
	 * @return true  false : if the form values are OK
	 */
	private boolean verificationFormulaire() {
		boolean isOkForm = true;

		if (id == null || "".equals(id)) {
			isOkForm = false;
		}

		if (password == null || "".equals(password)) {
			isOkForm = false;
		}

		return isOkForm;
	}

	/**
	 * Initialize the values
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void initialize(HttpServletRequest request, HttpServletResponse response) throws IOException {
		this.request = request;
		this.response = response;
		this.session = request.getSession();

		id = request.getParameter("identifiant");
		password = request.getParameter("motDePasse");
		response.setContentType("text/html");
	}

	/**
	 * Set the httpSession
	 * 
	 * @param login
	 * @param password
	 */
	protected void httpSession(String login, String password) {
		session.setAttribute("login", login);
		session.setAttribute("password", password);
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
		RequestDispatcher dispatcher = request.getRequestDispatcher(view + ".jsp");
		dispatcher.include(request, response);
	}
	
	/**
	 * Redirection to a servlet
	 * 
	 * @param String
	 *            : the servlet name
	 * @throws ServletException
	 * @throws IOException
	 */
	private void redirectionToServlet(String sevlet) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(sevlet);
		dispatcher.include(request, response);
	}
	
}