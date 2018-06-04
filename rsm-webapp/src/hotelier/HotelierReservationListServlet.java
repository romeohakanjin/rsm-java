package hotelier;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
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
 *         Servlet for all reservations of a hotelier
 */
@WebServlet("/HotelierReservationListServlet")
public class HotelierReservationListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String RESERVATION_LIST = "HotelierReservationsList";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;

	@EJB
	AnnonceSessionBean annonceSessionBean;

	@EJB
	ReservationSessionBean reservationSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;

		initialize();

		String identifiant = (String) this.session.getAttribute("login");
		int idUtilisateur = annonceSessionBean.getUserId(identifiant);

		List<Object[]> reservations = reservationSessionBean.getReservationsHotelByUserId(idUtilisateur);

		this.request.setAttribute("reservations", reservations);
		redirectionToView(RESERVATION_LIST);
	}

	/**
	 * Initialize the values
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		this.session = request.getSession();
		this.response.setContentType("text/html");

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