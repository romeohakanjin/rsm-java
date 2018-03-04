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
import javax.servlet.http.HttpSession;

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
	private HttpSession session;

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
	 * Récupère le nombre d'annonce, le nombre d'utilisateur regroupé par type
	 * d'utilisateur, le nombre de réservation regroupé par état de réservation
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	private void getDataForDashboard() throws ServletException, IOException {
		List<Object> nbAnnonceList = annonceSessionBean.getNbAnnounce();
		List<Object[]> nbUserList = utilisateurSessionBean.getNbUserGroupByUserType();
		List<Object[]> nbReservationList = reservationSessionBean.getNbReservationGroupByReservationState();
		Integer nbAnnonce = Integer.valueOf(nbAnnonceList.get(0).toString());
		this.request.setAttribute("nbAnnonce", nbAnnonce);
		this.request.setAttribute("nbUserList", nbUserList);
		this.request.setAttribute("nbReservationList", nbReservationList);
		redirectionToView(DASHBOARD);
	}

	private void initialize() {
		this.session = request.getSession();
		this.response.setContentType("text/html");
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
		RequestDispatcher dispatcher = request.getRequestDispatcher(view + ".jsp");
		dispatcher.include(request, response);
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
