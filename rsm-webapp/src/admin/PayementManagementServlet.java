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

import beans.session.PaiementSessionBean;
import beans.session.ReservationSessionBean;
import beans.session.UtilisateurSessionBean;

/**
 * @author SLI
 */
@WebServlet("/PayementManagementServlet")
public class PayementManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PAYEMENT_MANAGEMENT = "AdminPayementManagement";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private String action;
	
	@EJB
	PaiementSessionBean paiementSessionBean;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.request = request;
		this.response = response;

		initialize();
		getAllPaiement();
		redirectionToView(PAYEMENT_MANAGEMENT);
	}
	
	/**
	 * Initialise les variables
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		this.session = request.getSession();
		this.response.setContentType("text/html");
		this.action = request.getParameter("action");
		if (this.action == null) {
			this.action = "";
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
	 * Set the httpSession
	 * 
	 * @param login
	 * @param password
	 */
	protected void httpSession(String login, String password) {
		session.setAttribute("login", login);
		session.setAttribute("password", password);
	}

	/**
	 * Redirection to a view
	 * 
	 * @param String : the view name
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
	 * @param String : the servlet name
	 * @throws ServletException
	 * @throws IOException
	 */
	private void redirectionToServlet(String sevlet) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(sevlet);
		dispatcher.include(request, response);
	}
	
	/**
	 * Récupère tous les paiements
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void getAllPaiement() throws ServletException, IOException {
		List<Object[]> paiementList = paiementSessionBean.getHistoriquePaiement();
		this.request.setAttribute("paiementList", paiementList);
	}
}
