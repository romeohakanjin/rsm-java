package standard;

import java.io.IOException;
import java.sql.Timestamp;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.entity.Reservation;
import beans.session.ReservationSessionBean;

/**
 * Servlet implementation class ReservationConfirmation
 */
@WebServlet("/ReservationConfirmationServlet")
public class ReservationConfirmationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONFIRMATION_RESERVATION_VIEW = "ReservationAd";
	private static final String CONFIRMATION_RESERVATION = "Confirmer";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private String action;

	@EJB
	ReservationSessionBean reservationSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;

		initialize();

		switch (this.action) {
		case CONFIRMATION_RESERVATION:
			reservationValidate();
			break;

		default:
			redirectionToView(CONFIRMATION_RESERVATION_VIEW);
			break;
		}

	}

	/**
	 * validate the reservation and pay it
	 */
	private void reservationValidate() {
		// TODO :faire appel � la m�thode pour paiement
		// paiement()

		int annonceId = (int) session.getAttribute("reservationToValidate");
		int userId = (int) session.getAttribute("reservationIdUser");
		Timestamp timestampBegining = (Timestamp) session.getAttribute("reservationDateDebut");
		Timestamp timestampEnd = (Timestamp) session.getAttribute("reservationDateFin");
		long numberOfDays = (long) session.getAttribute("reservationNumberOfDays");
		Double price = (Double) session.getAttribute("reservationPrice");

		Reservation reservation = new Reservation();
		reservation.setId_annonce(annonceId);
		reservation.setId_utilisateur(userId);
		reservation.setDate_debut_sejour(timestampBegining);
		reservation.setDate_fin_sejour(timestampEnd);
		reservation.setDuree_sejour((int) numberOfDays);
		reservation.setPrix(price);
		reservation.setId_etat_reservation(1);
		reservation.setId_statut_reservation(1);

		reservationSessionBean.createReservation(reservation);
	}

	/**
	 * Initialize the values
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		this.session = request.getSession();
		this.response.setContentType("text/html");
		this.action = request.getParameter("submitButtonReservationValidation");
		if (this.action == null) {
			this.action = "";
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
}
