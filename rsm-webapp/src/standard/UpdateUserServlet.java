package standard;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.entity.Utilisateur;
import beans.session.UtilisateurSessionBean;

/**
 * @author MDI
 */
@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PARAMETER_ACTION_EDIT = "modifier";
	private static final String PARAMETER_ACTION_EDIT_USER = "modifierUtilisateur";
	private static final String EDIT_USER_INFORMATION_VIEW = "Standards";
	private static final String USER_EDITED_SERVLET = "StandardServlet";

	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private String lastName;
	private String firstName;
	private String mail;
	private String mobile;
	private String address;
	private String town;
	private String postalCode;
	private String parameter;

	@EJB
	UtilisateurSessionBean userSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;

		initialize();
		switch (this.parameter) {
		case PARAMETER_ACTION_EDIT_USER:
			EditUserActionPerformed();
			break;
		case PARAMETER_ACTION_EDIT:
			addUserEditedActionPerformed();
			break;
		default:
			redirectionToServlet(USER_EDITED_SERVLET);
			break;
		}
	}

	/**
	 * @throws ServletException
	 * @throws IOException
	 * 
	 *             Cette fonction permet d'�diter un utilisateur si son id existe
	 */
	private void EditUserActionPerformed() throws ServletException, IOException {

		String identifiant = (String) this.session.getAttribute("login");
		int id_utilisateur = userSessionBean.getIdUtilisateur(identifiant);
		try {
			boolean isExistingUserId = userSessionBean.isExistingUserId(id_utilisateur);
			System.out.println(isExistingUserId);
			if (isExistingUserId) {
				Utilisateur utilisateur = userSessionBean.getActiveUsers(id_utilisateur);
				request.setAttribute("userInformations", utilisateur);
				redirectionToView(EDIT_USER_INFORMATION_VIEW);
			} else {
				redirectionToView(USER_EDITED_SERVLET);
			}
		} catch (NumberFormatException exception) {
			redirectionToServlet(USER_EDITED_SERVLET);
		}
	}

	/**
	 * update a user if is id exists
	 * @throws ServletException
	 * @throws IOException
	 */
	private void addUserEditedActionPerformed() throws ServletException, IOException {

		String identifiant = (String) this.session.getAttribute("login");
		int id_utilisateur = userSessionBean.getIdUtilisateur(identifiant);
		boolean isExistingUserId = userSessionBean.isExistingUserId(id_utilisateur);
		if (isExistingUserId) {

			Utilisateur utilisateur = new Utilisateur();
			utilisateur.setId_utilisateur(id_utilisateur);
			utilisateur.setNom(lastName);
			utilisateur.setPrenom(firstName);
			utilisateur.setMail(mail);
			utilisateur.setMobile(mobile);
			utilisateur.setAdresse(address);
			utilisateur.setVille(town);
			utilisateur.setCode_postal(postalCode);
			userSessionBean.updateUser(utilisateur);
		}
		redirectionToServlet(USER_EDITED_SERVLET);
	}

	/**
	 * Initialize the values
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		this.session = request.getSession();
		this.response.setContentType("text/html");

		this.lastName = "";
		this.firstName = "";
		this.address = "";
		this.town = "";
		this.postalCode = "";
		this.mail = "";
		this.mobile = "";
		this.parameter = "";
		this.parameter = request.getParameter("action");
		if (this.parameter == null) {
			this.parameter = request.getParameter("submitButtonUtilisateurForm");
		}
		this.parameter = request.getParameter("submitButtonUtilisateurForm");
		if (this.parameter == null) {
			this.parameter = request.getParameter("action");
		}
		this.lastName = request.getParameter("nom");
		this.firstName = request.getParameter("prenom");
		this.mobile = request.getParameter("mobile");
		this.mail = request.getParameter("mail");
		this.address = request.getParameter("adresse");
		this.town = request.getParameter("ville");
		this.postalCode = request.getParameter("codePostal");
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
