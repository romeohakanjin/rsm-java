package hotelier;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.session.AnnonceSessionBean;
import beans.session.ReservationSessionBean;

/**
 * @author RHA
 * 
 *         Servlet for action on hotelier reservation list (accept or refuse)
 */
@WebServlet("/HotelierReservationServlet")
public class HotelierReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String LIST_RESERVATIONS_SERVLET = "HotelierReservationListServlet";
	private static final String ACCEPT_RESERVATION = "ValiderReservation";
	private static final String REFUSE_RESERVATION = "RefuserReservation";
	private static final int FINISHED_RESERVATION_STATUS_ID = 4;
	/**
	 * status which correspond to the "waiting confirmation from hotelier" state
	 */
	private static final int RESERVATION_STATE_HOTELIER = 1;
	/**
	 * status which correspond to the "accepted" state
	 */
	private static final int RESERVATION_STATE_VALIDATION_HOTELIER = 3;
	/**
	 * status which correspond to "refusing"
	 */
	private static final int RESERVATION_STATE_REFUSING_HOTELIER = 4;
	/**
	 * status which correspond to "incoming"
	 */
	private static final int INCOMING_RESERVATION_STATUS_ID = 2;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private String action;
	private String reservationId;

	@EJB
	AnnonceSessionBean annonceSessionBean;

	@EJB
	ReservationSessionBean reservationSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;
		initialiser();

		switch (action) {
		case ACCEPT_RESERVATION:
			reservationValidation();
			break;
		case REFUSE_RESERVATION:
			reservationRefuse();
			break;
		default:
			redirectionToServlet(LIST_RESERVATIONS_SERVLET);
			break;
		}

	}

	/**
	 * Validate a reservation
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	private void reservationRefuse() throws ServletException, IOException {
		try {
			int idReservation = Integer.valueOf(reservationId);
			String identifiant = (String) this.session.getAttribute("login");
			int id_utilisateur = annonceSessionBean.getIdUtilisateur(identifiant);

			boolean isMathingId = reservationSessionBean.isMatchingIdUserReservationAndIdUserAnnonce(id_utilisateur,
					idReservation);
			if (isMathingId) {
				int reservationStateId = reservationSessionBean.getReservationStateId(idReservation);

				if (reservationStateId == RESERVATION_STATE_HOTELIER) {
					setVariableToView("alert-success", "Cette annonce vient d'être refusée");
					reservationSessionBean.validationReservationHotelier(idReservation,
							RESERVATION_STATE_VALIDATION_HOTELIER, INCOMING_RESERVATION_STATUS_ID);
				} else {
					setVariableToView("alert-warning", "Cette annonce n'est pas en attente d'une confirmation");
				}
			} else {
				setVariableToView("alert-danger", "Numéro d'annonce incorrect");
			}

			redirectionToServlet(LIST_RESERVATIONS_SERVLET);
		} catch (NumberFormatException exception) {
			setVariableToView("alert-danger", "Numéro d'annonce incorrect");
			redirectionToServlet(LIST_RESERVATIONS_SERVLET);
		}
	}

	/**
	 * Refuse a reservation
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	private void reservationValidation() throws ServletException, IOException {
		try {
			int idReservation = Integer.valueOf(reservationId);
			String identifiant = (String) this.session.getAttribute("login");
			int id_utilisateur = annonceSessionBean.getIdUtilisateur(identifiant);

			boolean isMathingId = reservationSessionBean.isMatchingIdUserReservationAndIdUserAnnonce(id_utilisateur,
					idReservation);
			if (isMathingId) {
				int reservationStateId = reservationSessionBean.getReservationStateId(idReservation);

				if (reservationStateId == RESERVATION_STATE_HOTELIER) {
					setVariableToView("alert-success", "Cette annonce vient d'être validée");
					reservationSessionBean.resufingReservationHotelier(idReservation,
							RESERVATION_STATE_REFUSING_HOTELIER, FINISHED_RESERVATION_STATUS_ID);
				} else {
					setVariableToView("alert-warning", "Cette annonce n'est pas en attente d'une confirmation");
				}
			} else {
				setVariableToView("alert-warning", "Numéro d'annonce incorrect");
			}

			redirectionToServlet(LIST_RESERVATIONS_SERVLET);
		} catch (NumberFormatException exception) {
			setVariableToView("alert-warning", "Numéro d'annonce incorrect");
			redirectionToServlet(LIST_RESERVATIONS_SERVLET);
		}
	}

	/**
	 * Itinaliser les variables
	 * 
	 * @throws IOException
	 */
	private void initialiser() throws IOException {
		this.session = request.getSession();
		this.response.setContentType("text/html");
		this.action = this.request.getParameter("action");
		if (this.action == null) {
			this.action = "";
		}
		this.reservationId = this.request.getParameter("reservationId");
		this.request.removeAttribute("ok-hotelier-reservations-list");
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
