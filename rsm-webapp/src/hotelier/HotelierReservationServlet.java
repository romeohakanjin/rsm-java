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
	private static final String ACCEPT_RESERVATION = "Valider";
	private static final String REFUSE_RESERVATION = "Refuser";
	/**
	 * status which correspond to the "waiting confirmation from hotelier" status
	 */
	private static final int RESERVATION_STATUS_HOTELIER_ID = 1;
	private static final int RESERVATION_STATE_VALIDATION_ID = 4;
	private static final int RESERVATION_STATE_REFUSE_ID = 0;
	private static final int RESERVATION_STATE_FINISH_ID = 3;
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
//				int reservationStatusId = etatReservationSessionBean.getReservationStatusId(idReservation);
//
//				if (reservationStatusId == RESERVATION_STATUS_HOTELIER_ID) {
//					reservationSessionBean.RefuseReservationHotelier(idReservation, RESERVATION_STATE_REFUSE_ID);
//				} else {
//					setVariableToView("error-hotelier-reservations-list", "Cette réservation est en attente de confirmation");
//				}
				// sinon on fait pas redirection + message d'erreur
			} else {
				setVariableToView("error-hotelier-reservations-list", "Numéro d'annonce incorrect");
			}

			redirectionToServlet(LIST_RESERVATIONS_SERVLET);
		} catch (NumberFormatException exception) {
			setVariableToView("error-hotelier-reservations-list", "Numéro d'annonce incorrect");
			redirectionToServlet(LIST_RESERVATIONS_SERVLET);
		}
	}

	/**
	 * Refuse a reservation
	 * @param RESERVATION_STATE_VALIDATION 
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
//				int reservationStatusId = etatReservationSessionBean.getReservationStatusId(idReservation);
//
//				if (reservationStatusId == RESERVATION_STATUS_HOTELIER_ID) {
//					reservationSessionBean.ValidateReservationHotelier(idReservation, RESERVATION_STATE_VALIDATION);
//				} else {
//					setVariableToView("error-hotelier-reservations-list", "Cette réservation est en attente de confirmation");
//				}
				// sinon on fait pas redirection + message d'erreur
			} else {
				setVariableToView("error-hotelier-reservations-list", "Numéro d'annonce incorrect");
			}

			redirectionToServlet(LIST_RESERVATIONS_SERVLET);
		} catch (NumberFormatException exception) {
			setVariableToView("error-hotelier-reservations-list", "Numéro d'annonce incorrect");
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
