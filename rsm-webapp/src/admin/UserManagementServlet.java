package admin;

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
 * Servlet implementation class Admin
 */
@WebServlet("/UserManagementServlet")
public class UserManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String USER_MANAGEMENT = "AdminUserManagement";
	private static final String USER_RECORD = "AdminUserRecord";
	//private static final String USER_MANAGEMENT_SERVLET = "UserManagementServlet";
	private static final String DELETE_USER = "DeleteUser";
	private static final String DISPLAY_USER = "Display";
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
		getAllUser();

		redirectionToView(USER_MANAGEMENT);
		switch(this.action) {
		case DELETE_USER :
			if(userId != null || !userId.equals("")) {
				Integer idUser = Integer.valueOf(userId);
				Utilisateur userToDelete = utilisateurSessionBean.getUserById(idUser);
				if(userToDelete != null) {
					Boolean isDeleted = deleteUserById(idUser, userToDelete.getId_type_utilisateur());
					if(isDeleted) {
						redirectionToView(USER_MANAGEMENT);
					}else {
						setVariableToView("erreurDelete", "La suppression n'a pas pu être effectué");
					}
				}
			}
			break;
		case DISPLAY_USER :
			if(userId != null || !userId.equals("")) {
				Integer idUser = Integer.valueOf(userId);
				Utilisateur userToDisplay = utilisateurSessionBean.getUserById(idUser);
				request.setAttribute("user", userToDisplay);
				redirectionToView(USER_RECORD);
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
	
	/**
	 * Récupère tous les utilisateurs
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void getAllUser() throws ServletException, IOException {
		List<Utilisateur> userList = utilisateurSessionBean.getAllUtilisateur();
		this.request.setAttribute("userList", userList);
	}
	
	/**
	 * Supprime un utilisateur à l'aide de son id
	 * @param userId
	 * @param userTypeId
	 * @return
	 */
	private boolean deleteUserById(Integer userId, Integer userTypeId) {
		Boolean isDeleted = false;
		List<Annonce> annonceList = new ArrayList<Annonce>();
		List<Reservation> reservationList = new ArrayList<Reservation>();
		Utilisateur user = utilisateurSessionBean.getUserById(userId);
		
		switch(userTypeId) {
		case 1: 
			isDeleted = utilisateurSessionBean.deleteUser(user);
			break;
		case 2:
			annonceList = annonceSessionBean.getAllAnnonceUtilisateur(userId);
			if(annonceList.size() != 0) {
				for(Annonce annonce : annonceList) {
					reservationList = reservationSessionBean.getReservationByAnnonceId(annonce.getId_annonce());
					if(reservationList.size() != 0) {
						isDeleted = false;
					}
				}
			}else {
				isDeleted = utilisateurSessionBean.deleteUser(user);
			}
			break;
		case 3:
			reservationList = reservationSessionBean.getReservationByUserId(userId);
			if(reservationList.size() != 0) {
				isDeleted = false;
			}else {
				isDeleted = utilisateurSessionBean.deleteUser(user);
			}
			break;
		}
		return isDeleted;
	}
}
 