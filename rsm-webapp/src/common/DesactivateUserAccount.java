package common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.entity.Annonce;
import beans.entity.Reservation;
import beans.entity.Utilisateur;
import beans.session.AnnonceSessionBean;
import beans.session.ReservationSessionBean;
import beans.session.TypeUtilisateurSessionBean;
import beans.session.UtilisateurSessionBean;

/**
 * @author SLI
 */
@WebServlet("/DesactivateUserAccount")
public class DesactivateUserAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DEACTIVATE_USER = "deactivateUser";
	private static final String PROFIL_PAGE_HOTELIER = "HotelierProfilServlet";
	private static final String PROFIL_PAGE_STANDARD = "StandardServlet";
	private static final String DECONNEXION = "Deconnection";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private String action;

	@EJB
	UtilisateurSessionBean utilisateurSessionBean;
	@EJB
	TypeUtilisateurSessionBean typeUtilisateurSessionBean;
	@EJB
	AnnonceSessionBean annonceSessionBean;
	@EJB
	ReservationSessionBean reservationSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;

		initialize();

		switch (this.action) {
		case DEACTIVATE_USER:
			String identifiant = (String) this.session.getAttribute("login");
			int idUser = annonceSessionBean.getUserId(identifiant);
			Utilisateur userToDelete = utilisateurSessionBean.getUserById(idUser);
			if (userToDelete != null) {
				Boolean isDeleted = this.deleteUserById(userToDelete);
				if (!isDeleted) {
					request.setAttribute("alert-danger",
							"Le compte n\'a pas pu �tre supprim� car vous avez des r�servations en cours");
					switch (userToDelete.getId_type_utilisateur()) {
					case 2:
						redirectionToServlet(PROFIL_PAGE_HOTELIER);
						break;
					case 3:
						redirectionToServlet(PROFIL_PAGE_STANDARD);
						break;
					}
				} else {
					redirectionToServlet(DECONNEXION);
				}

			}
			break;
		}
	}

	/**
	 * Initialize the values
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		this.session = request.getSession();
		this.response.setContentType("text/html");
		this.action = request.getParameter("action");
		if (this.action == null) {
			this.action = "";
		}
	}

	/**
	 * delete a user with is id et user type
	 * 
	 * @param userId
	 * @param userTypeId
	 * @return true / false : if the user has been delete
	 */
	private boolean deleteUserById(Utilisateur userToDelete) {
		Boolean isDeleted = false;
		List<Annonce> annonceList = new ArrayList<Annonce>();
		List<Reservation> reservationList = new ArrayList<Reservation>();

		switch (userToDelete.getId_type_utilisateur()) {
		case 2:
			annonceList = annonceSessionBean.getAnnouncementByUserIdAndValidatedReservation(userToDelete.getId_utilisateur());
			if (annonceList.size() == 0) {
				isDeleted = utilisateurSessionBean.deleteUser(userToDelete);
			} else {
				isDeleted = false;
			}
			break;
		case 3:
			reservationList = reservationSessionBean.getReservationNonPasseeByUserId(userToDelete.getId_utilisateur());
			if (reservationList.size() == 0) {
				isDeleted = utilisateurSessionBean.deleteUser(userToDelete);
			} else {
				isDeleted = false;
			}
			break;
		}
		return isDeleted;
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
