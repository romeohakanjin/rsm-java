package admin;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.session.AnnonceSessionBean;
import beans.session.ReservationSessionBean;
import beans.session.UtilisateurSessionBean;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DASHBOARD = "AdminBoard";
	private HttpServletRequest request;
	private HttpServletResponse response;

	@EJB
	AnnonceSessionBean annonceSessionBean;
	@EJB
	UtilisateurSessionBean utilisateurSessionBean;
	@EJB
	ReservationSessionBean reservationSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;

		initialize();
		getDataForDashboard();
	}

	/**
	 * get the number of announcement, the number of user group by they user type,
	 * the number of reservation group by the state of the reservation
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	private void getDataForDashboard() throws ServletException, IOException {
		List<Object> nbAnnonceList = annonceSessionBean.getNumberOfAnnouncement();
		List<Object[]> nbUserList = utilisateurSessionBean.getUserNumberGroupByUserType();
		List<Object[]> nbReservationList = reservationSessionBean.getNumberOfReservationGroupByReservationState();
		Integer nbAnnonce = Integer.valueOf(nbAnnonceList.get(0).toString());
		this.request.setAttribute("nbAnnonce", nbAnnonce);
		this.request.setAttribute("nbUserList", nbUserList);
		this.request.setAttribute("nbReservationList", nbReservationList);
		redirectionToView(DASHBOARD);
	}

	private void initialize() {
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
		RequestDispatcher dispatcher = request.getRequestDispatcher(view + ".jsp");
		dispatcher.include(request, response);
	}

}
