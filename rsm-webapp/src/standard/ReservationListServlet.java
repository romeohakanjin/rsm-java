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

import beans.entity.Reservation;
import beans.session.AnnonceSessionBean;
import beans.session.ReservationSessionBean;

/**
 * Servlet implementation class ReservationList
 */
@WebServlet("/ReservationListServlet")
public class ReservationListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String LIST_RESERVATION = "ReservationList";
	private static final String HOME_PAGE = "Home";
	private static final String RESERVATION_LIST_ACTION = "list";
	private static final String RESERVATION_CANCEL_ACTION = "cancel";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private String action;
	private String reservationId;

	@EJB
	ReservationSessionBean reservationSessionBean;
	@EJB
	AnnonceSessionBean annonceSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;

		initialize();

		String identifiant = (String) this.session.getAttribute("login");
		int idUtilisateur = annonceSessionBean.getUserId(identifiant);

		switch (action) {
		case RESERVATION_LIST_ACTION:
			reservationList(idUtilisateur);
			break;
		case RESERVATION_CANCEL_ACTION:
			// cancel if the state id is 'waiting for approval'
			cancelReservation(idUtilisateur);
			break;
		default:
			redirectionToView(HOME_PAGE);
			break;
		}

	}

	/**
	 * Cancel a reservation that is waiting for the approval of a hotelier
	 * 
	 * @param idUtilisateur
	 *            : id of the current user
	 * @throws IOException
	 * @throws ServletException
	 */
	private void cancelReservation(int idUtilisateur) throws ServletException, IOException {
		// v�rification id de l'annonce qui est bien en 1 ou 2
		boolean isOkReservation = reservationSessionBean.isReservationPending(idUtilisateur, reservationId);

		if (isOkReservation) {
			// changer �tat de la r�servation
			reservationSessionBean.deleteReservation(reservationId);
			setVariableToView("alert-success", "Cette r�servation vient d'�tre supprim�e");
			redirectionToView(HOME_PAGE);
		} else {
			setVariableToView("alert-warning", "Num�ro d'annonce incorrect");
			redirectionToView(HOME_PAGE);
		}

	}

	/**
	 * Show all the reservation for a user
	 * 
	 * @param idUtilisateur
	 *            : user id
	 * @throws IOException
	 * @throws ServletException
	 */
	private void reservationList(int idUtilisateur) throws ServletException, IOException {
		List<Reservation> reservationsPending = reservationSessionBean.getPendingReservationByUserId(idUtilisateur);
		this.request.setAttribute("reservationsPending", reservationsPending);
		
		List<Reservation> reservationsFinish = reservationSessionBean.getFinishedReservationByUserId(idUtilisateur);
		this.request.setAttribute("reservationsFinish", reservationsFinish);
		
		redirectionToView(LIST_RESERVATION);

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
		this.reservationId = request.getParameter("reservationId");
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
	private void setVariableToView(String variable, String message) {
		request.setAttribute(variable, message);
	}
}
