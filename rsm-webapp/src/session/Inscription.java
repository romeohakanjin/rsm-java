package session;

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
import common.Utils;

/**
 * Servlet implementation class Inscription
 */
@WebServlet("/Inscription")
public class Inscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String HOME_PAGE = "Home";
	private static final String INSCRIPTION_PAGE = "Inscription";
	private static final int ID_TYPE_UTILISATEUR_STANDARD = 3;
	private static final int ID_TYPE_UTILISATEUR_HOTELIER = 2;
	private static final int ID_TYPE_UTILISATEUR_ADMIN = 1;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private Utils utils;

	private String userType;
	private String id;
	private String password;
	private String lastName;
	private String firstName;
	private String phoneNumber;
	private String address;
	private String postalCode;
	private String town;
	private String hotelName;
	private boolean isHotelier;

	@EJB
	UtilisateurSessionBean userSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;

		initialize();

		boolean isOkForm = verificationForm();

		if (isOkForm) {
			boolean existingUser = userSessionBean.isExistingUser(id);
			if (!existingUser) {
				Utilisateur utilisateur = new Utilisateur();
				utilisateur.setMail(id);
				utilisateur.setMot_de_passe(password);
				utilisateur.setNom(lastName);
				utilisateur.setPrenom(firstName);
				utilisateur.setMobile(phoneNumber);
				utilisateur.setAdresse(address);
				utilisateur.setCode_postal(postalCode);
				utilisateur.setVille(town);
				utilisateur.setActif(true);

				if (this.isHotelier) {
					utilisateur.setId_type_utilisateur(2);
					boolean existingHotel = userSessionBean.checkExistingHotel(hotelName);
					if (existingHotel) {
						// Hotel already registred
						int idHotel = userSessionBean.getIdHotel(hotelName);
						utilisateur.setId_hotel(idHotel);

						userSessionBean.createUser(utilisateur);
						httpSession(id, password);
						setSession(utilisateur.getId_type_utilisateur());
						setVariableToView("alert-sucess", "Cr�ation de compte prise en compte");
						redirectionToView(HOME_PAGE);
					} else if (!existingHotel) {
						// No hotel registred
						int idHotel = userSessionBean.createHotel(hotelName);
						utilisateur.setId_hotel(idHotel);

						userSessionBean.createUser(utilisateur);
						httpSession(id, password);
						setSession(utilisateur.getId_type_utilisateur());
						setVariableToView("alert-sucess", "Cr�ation de compte prise en compte");
						redirectionToView(HOME_PAGE);
					} else {
						setVariableToView("alert-danger", "H�tel inexistant");
						redirectionToView(INSCRIPTION_PAGE);
					}
				} else {
					utilisateur.setId_type_utilisateur(3);
					this.setSession(utilisateur.getId_type_utilisateur());
					userSessionBean.createUser(utilisateur);
					httpSession(id, password);
					setVariableToView("alert-sucess", "Cr�ation de compte prise en compte");
					redirectionToView(HOME_PAGE);
				}
			} else {
				setVariableToView("alert-danger", "Cette adresse e-mail est d�j� utilis�e");
				redirectionToView(INSCRIPTION_PAGE);
			}
		} else {
			setVariableToView("alert-danger", "Informations manquantes");
			redirectionToView(INSCRIPTION_PAGE);
		}

	}

	/**
	 * Verification of the parameters from the inscription form
	 * 
	 * @return true / false : true if the form is ok OK
	 */
	private boolean verificationForm() {
		boolean isOkForm = true;
		if (userType != null) {
			if (id == null || "".equals(id)) {
				isOkForm = false;
			}

			if (password == null || "".equals(password)) {
				isOkForm = false;
			}

			if (userType.equals("hotelier")) {
				this.isHotelier = true;

				if (hotelName == null || "".equals(hotelName)) {
					isOkForm = false;
				}
			}

			if (userType.equals("utilisateur")) {
				this.isHotelier = false;

				if (lastName == null || "".equals(lastName)) {
					isOkForm = false;
				}

				if (firstName == null || "".equals(firstName)) {
					isOkForm = false;
				}

				if (phoneNumber == null || "".equals(phoneNumber)) {
					isOkForm = false;
				}

				if (address == null || "".equals(address)) {
					isOkForm = false;
				}

				if (postalCode == null || "".equals(postalCode)) {
					isOkForm = false;
				}

				if (town == null || "".equals(town)) {
					isOkForm = false;
				}
			}
		}
		return isOkForm;
	}

	/**
	 * Initialize the values
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		this.session = request.getSession();
		this.isHotelier = false;
		this.response.setContentType("text/html");
		this.request.removeAttribute("error-form-inscription");

		this.userType = request.getParameter("selectTypeUtilisateur");
		this.id = request.getParameter("identifiant");
		this.password = request.getParameter("motDePasse");
		this.lastName = request.getParameter("nom");
		this.firstName = request.getParameter("prenom");
		this.phoneNumber = request.getParameter("telephone");
		this.address = request.getParameter("adresse");
		this.postalCode = request.getParameter("codePostal");
		this.town = request.getParameter("ville");

		this.hotelName = request.getParameter("nomHotel");
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
	 * Set the session type
	 * 
	 * @param userType
	 */
	protected void setSession(Integer userType) {
		switch (userType) {
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
	}

	/**
	 * Redirection to a view
	 * 
	 * @param String
	 *            : the view name
	 * @throws ServletException
	 * @throws IOException
	 */
	public void redirectionToView(String view) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/" + view + ".jsp").forward(request, response);
	}

	/**
	 * Feed request attribute
	 * 
	 * @param variable
	 * @param message
	 */
	public void setVariableToView(String variable, String message) {
		request.setAttribute(variable, message);
	}
}
