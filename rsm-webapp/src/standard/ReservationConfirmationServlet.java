package standard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;

		initialiser();

		switch (this.action) {
		case CONFIRMATION_RESERVATION:
			reservationValidate();
			break;

		default:
			System.out.println("non non pas confirmé");
			redirectionToView(CONFIRMATION_RESERVATION_VIEW);
			break;
		}

	}

	/**
	 * validate the reservation and pay it
	 */
	private void reservationValidate() {
		// TODO : paiement
		System.out.println(session.getAttribute("reservationToValidate"));
		System.out.println(session.getAttribute("reservationIdUser"));
		System.out.println(session.getAttribute("reservationDateDebut"));
		System.out.println(session.getAttribute("reservationDateFin"));
	}

	/**
	 * Itinaliser les variables
	 * 
	 * @throws IOException
	 */
	private void initialiser() throws IOException {
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
