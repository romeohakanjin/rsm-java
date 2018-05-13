package admin;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.entity.Annonce;
import beans.entity.Reservation;
import beans.session.AnnonceSessionBean;
import beans.session.ReservationSessionBean;

/**
 * Servlet implementation class AnnouncementManagementServlet
 */
@WebServlet("/AnnouncementManagementServlet")
public class AnnouncementManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ANNOUNCEMENT_LIST = "AdminAnnouncementManagement";
	private static final String ANNONCE_VIEW = "HotelierAnnonce";
	private static final String DELETE_ANNOUNCEMENT = "Delete";
	private static final String EDIT_ANNOUNCEMENT = "Edit";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String action;
	private String annonceId;

	@EJB
	AnnonceSessionBean annonceSessionBean;
	@EJB
	ReservationSessionBean reservationSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;

		initialiser();

		switch (this.action) {
		case DELETE_ANNOUNCEMENT:
			if (annonceId != null || !annonceId.equals("")) {
				Integer idAnnonce = Integer.valueOf(annonceId);
				Boolean isDeleted = deleteAnnouncementById(idAnnonce);
				System.out.println(isDeleted);
				getAllAnnouncement();
				setVariableToView("alert-success", "Annonce suprimée");
				redirectionToView(ANNOUNCEMENT_LIST);
			}
			break;
		case EDIT_ANNOUNCEMENT:
			if (annonceId != null || !annonceId.equals("")) {
				int idAnnonce = Integer.valueOf(annonceId);
				Annonce annonce = annonceSessionBean.getAnnonce(idAnnonce);
				request.setAttribute("annonceEdited", annonce);
				redirectionToView(ANNONCE_VIEW);
			}
			break;
		default:
			getAllAnnouncement();
			redirectionToView(ANNOUNCEMENT_LIST);
			break;
		}
	}

	/**
	 * Itinaliser les variables
	 * 
	 * @throws IOException
	 */
	private void initialiser() throws IOException {
		this.response.setContentType("text/html");
		this.action = request.getParameter("action");
		if (this.action == null) {
			this.action = "";
		} else {
			this.annonceId = request.getParameter("annonceId");
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
	private void redirectionToView(String view) throws ServletException, IOException {
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

	/**
	 * Récupère toutes les annonces
	 */
	private void getAllAnnouncement() {
		List<Annonce> annonces = annonceSessionBean.getAllAnnonce();
		this.request.setAttribute("annonces", annonces);
	}

	/**
	 * Supprime l'annonce passée en paramètre
	 */
	private Boolean deleteAnnouncementById(Integer idAnnonce) {
		Boolean isDeleted = false;
		Annonce annonceToDelete = annonceSessionBean.getAnnonce(idAnnonce);
		List<Reservation> reservationList = reservationSessionBean
				.getReservationByAnnonceId(annonceToDelete.getId_annonce());
		if (reservationList.size() != 0) {
			isDeleted = false;
		} else {
			annonceSessionBean.deleteAnnonce(idAnnonce);
			isDeleted = true;
		}
		return isDeleted;
	}
}
