package standard;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.entity.Paiement;
import beans.entity.Reservation;
import beans.session.PaiementSessionBean;
import beans.session.AnnonceSessionBean;
import beans.session.ReservationSessionBean;
import beans.session.UtilisateurSessionBean;

/**
 * Servlet implementation class ReservationConfirmation
 */
@WebServlet("/ReservationConfirmationServlet")
public class ReservationConfirmationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ANNONCES_LISTE_SERVLET = "AnnoncesServlet";
	private static final String CONFIRMATION_RESERVATION_VIEW = "ReservationAd";
	private static final String CONFIRMATION_RESERVATION_ACTION = "Confirmer";
	private static final String POINT_PAYMENT_RESERVATION_ACTION = "pointPaiement";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private String action;

	@EJB
	ReservationSessionBean reservationSessionBean;
	
	@EJB
	PaiementSessionBean paiementSessionBean;

	@EJB
	AnnonceSessionBean announcementSessionBean;

	@EJB
	UtilisateurSessionBean utilisateurSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;

		initialize();

		switch (this.action) {
		case CONFIRMATION_RESERVATION_ACTION:
			reservationValidate();
			break;
		case POINT_PAYMENT_RESERVATION_ACTION:
			paymentReservationWithPoints();
			break;
		default:
			redirectionToView(CONFIRMATION_RESERVATION_VIEW);
			break;
		}
	}

	/**
	 * pay a reservation with point
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void paymentReservationWithPoints() throws ServletException, IOException {
		try {
			String identifiant = (String) this.session.getAttribute("login");
			int id_utilisateur = announcementSessionBean.getUserId(identifiant);
			int userPoints = utilisateurSessionBean.getUserPoints(id_utilisateur);
			
			if (userPoints >= 100) {
				utilisateurSessionBean.payReservationWithPoints(id_utilisateur);
				
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

				setVariableToView("alert-success", "Réservation prise en compte");
				redirectionToServlet(ANNONCES_LISTE_SERVLET);
			} else {
				setVariableToView("alert-danger", "Points insuffisant");
				redirectionToServlet(ANNONCES_LISTE_SERVLET);
			}
		} catch (Exception exception) {
			setVariableToView("alert-danger", "Réservation non prise en compte");
			redirectionToServlet(ANNONCES_LISTE_SERVLET);
		}

	}	
	/*
	 * create the paiement
	 */
	public void paiement() {
		
		int annonceId = (int) session.getAttribute("reservationToValidate");
		int reservationId = reservationSessionBean.getReservationId(annonceId);
		
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		Timestamp date_paiement = new Timestamp(now.getTime());
		
		Paiement paiement = new Paiement();
		paiement.setId_reservation(reservationId);
		paiement.setDate_paiement(date_paiement);
		
		paiementSessionBean.createPaiement(paiement);
	}
		
	 /**
	  *  @throws ServletException
	 */
	private void reservationValidate() throws ServletException, IOException {
		try {

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

			setVariableToView("alert-success", "Réservation prise en compte");
			redirectionToServlet(ANNONCES_LISTE_SERVLET);
		} catch (Exception exception) {
			setVariableToView("alert-danger", "Réservation non prise en compte");
			redirectionToServlet(ANNONCES_LISTE_SERVLET);
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
			this.action = request.getParameter("submitButtonReservationValidation");
			if (this.action == null) {
				this.action = "";
			}
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
