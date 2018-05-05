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
import session.Deconnection;

/**
 * @author SLI
 */
@WebServlet("/DeactivateUserAccount")
public class DeactivateUserAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DEACTIVATE_USER = "deactivateUser";
	private static final String PROFIL_PAGE_HOTELIER = "HotelierProfilServlet";
	private static final String PROFIL_PAGE_STANDARD = "StandardServlet";
	private static final String DECONNEXION = "Deconnection";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private String action;
	private String userId;
	
	@EJB
	UtilisateurSessionBean utilisateurSessionBean;
	@EJB
	TypeUtilisateurSessionBean typeUtilisateurSessionBean;
	@EJB
	AnnonceSessionBean annonceSessionBean;
	@EJB
	ReservationSessionBean reservationSessionBean;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.request = request;
		this.response = response;

		initialize();

		switch(this.action) {
		case DEACTIVATE_USER :
			if(userId != null || !userId.equals("")) {
				Integer idUser = Integer.valueOf(userId);
				Utilisateur userToDelete = utilisateurSessionBean.getUserById(idUser);
				if(userToDelete != null) {
					Boolean isDeleted = this.deleteUserById(userToDelete);
					if(!isDeleted) {
						request.setAttribute("erreurDelete", "Le compte n\'a pas pu être supprimé car vous avez des réservations en cours");
						switch(userToDelete.getId_type_utilisateur()) {
						case 2:
							redirectionToServlet(PROFIL_PAGE_HOTELIER);
							break;
						case  3:
							redirectionToServlet(PROFIL_PAGE_STANDARD);
							break;
						}
					}else {
						request.removeAttribute("error-hotelier-annonce-form");
						redirectionToServlet(DECONNEXION);
					}
				}
			}
			break;
		}
	}
	
	/**
	 * Initialise les variables
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		this.session = request.getSession();
		this.response.setContentType("text/html");
		this.action = request.getParameter("action");
		if (this.action == null) {
			this.action = "";
		}else {
			this.userId = request.getParameter("userId");
		}
	}
	
	/**
	 * Supprime un utilisateur à l'aide de son id et son type
	 * @param userId
	 * @param userTypeId
	 * @return
	 */
	private boolean deleteUserById(Utilisateur userToDelete) {
		Boolean isDeleted = false;
		List<Annonce> annonceList = new ArrayList<Annonce>();
		List<Reservation> reservationList = new ArrayList<Reservation>();
		
		switch(userToDelete.getId_type_utilisateur()) {
		case 2:
			annonceList = annonceSessionBean.getAnnonceByUserWithReservationValide(userToDelete.getId_utilisateur());
			if(annonceList.size() == 0) {
				isDeleted = utilisateurSessionBean.deleteUser(userToDelete);
			}else {
				isDeleted = false;
			}
			break;
		case 3:
			reservationList = reservationSessionBean.getReservationNonPasseeByUserId(userToDelete.getId_utilisateur());
			if(reservationList.size() == 0) {
				isDeleted = utilisateurSessionBean.deleteUser(userToDelete);
			}else {
				isDeleted = false;
			}
			break;
		}
		return isDeleted;
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
	 * Redirection to a view
	 * 
	 * @param String : the view name
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
	 * @param String : the servlet name
	 * @throws ServletException
	 * @throws IOException
	 */
	private void redirectionToServlet(String sevlet) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(sevlet);
		dispatcher.include(request, response);
	}
}
