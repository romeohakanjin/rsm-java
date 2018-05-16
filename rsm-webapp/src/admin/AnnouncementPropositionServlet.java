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

import beans.entity.PropositionModificationAnnonce;
import beans.session.PropositionModificationSessionBean;

/**
 * @author RHA
 */
@WebServlet("/AnnouncementPropositionServlet")
public class AnnouncementPropositionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String LIST_RESERVATION = "AdminModificationPropositionList";
	private static final String HOME_PAGE = "Home";
	private static final String PROPOSITION_MODIFICATION_LIST_ACTION = "list";
	private static final String PROPOSITION_MODIFICATION_IGNORE_ACTION = "ignore";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private String action;
	private String modificationPropositionId;
	
	@EJB
	PropositionModificationSessionBean propositionModificationSessionBean;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.request = request;
		this.response = response;

		initialize();

		switch (action) {
		case PROPOSITION_MODIFICATION_LIST_ACTION:
			propositionModificationList();
			break;
		case PROPOSITION_MODIFICATION_IGNORE_ACTION:
			// cancel si id statut etc est en attente de confirmation
			ignorePropositionReservation(modificationPropositionId);
			break;
		default:
			redirectionToView(HOME_PAGE);
			break;
		}
		
	}
	
	/**
	 * Show all the modifications propositions
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	private void propositionModificationList() throws ServletException, IOException {
		List<PropositionModificationAnnonce> propositionsModifications = propositionModificationSessionBean.getModificationsPorpositions();
		this.request.setAttribute("propositionsModifications", propositionsModifications);
		
		redirectionToView(LIST_RESERVATION);

	}
	
	/**
	 * ignore a modification proposition
	 * 
	 * @param modificationPropositionId
	 *            : id of the modification propositions
	 * @throws IOException
	 * @throws ServletException
	 */
	private void ignorePropositionReservation(String modificationPropositionId) throws ServletException, IOException {
		boolean isOkReservation = propositionModificationSessionBean.isExistingModificationProposition(modificationPropositionId);

		if (isOkReservation) {
			// ignorer la proposition de modification
			propositionModificationSessionBean.deleteModificationPrposition(modificationPropositionId);
			setVariableToView("alert-success", "Cette proposition vient d'être ignorée");
			redirectionToView(HOME_PAGE);
		} else {
			setVariableToView("alert-warning", "Numéro de proposition incorrect");
			redirectionToView(HOME_PAGE);
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
			this.action = "";
		}
		this.modificationPropositionId = request.getParameter("modificationPropositionId");
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
