package hotelier;

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

import beans.entity.Annonce;
import beans.entity.Commentaire;
import beans.entity.ServiceChambre;
import beans.session.AnnonceSessionBean;
import beans.session.ServiceChambreSessionBean;

/**
 * Servlet implementation class HotelierAnnonceDetailsServlet
 */
@WebServlet("/HotelierAnnonceDetailsServlet")
public class HotelierAnnonceDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SIGNALER_COMMENT = "signaler";
	private static final String IGNORE_COMMENT = "ignore";
	private static final String LISTE_ANNONCES = "HotelierAnnoncesListe";
	private static final String ANNONCE_DETAILS_VIEW = "HotelierAnnonceDetail";
	private static final String ANNONCE_LISTE_SERVLET = "HotelierAnnonceListServlet";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private String announcementId;
	private String action;
	private String commentId;

	@EJB
	AnnonceSessionBean annonceSessionBean;
	
	@EJB
	ServiceChambreSessionBean serviceChambreSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;

		initialize();

		switch (action) {
		case SIGNALER_COMMENT:
			try {
				int commentId = Integer.valueOf(this.commentId);
				String identifiant = (String) this.session.getAttribute("login");
				int id_utilisateur = annonceSessionBean.getUserId(identifiant);

				boolean isMathingId = annonceSessionBean.isMatchingUserIdAndCommentId(id_utilisateur, commentId);

				if (isMathingId) {
					annonceSessionBean.reportComment(commentId);
					setVariableToView("alert-success", "Commentaire signalé");
					redirectionToServlet(ANNONCE_LISTE_SERVLET);
				}
			} catch (NumberFormatException exception) {
				setVariableToView("alert-danger", "Numéro de commentaire incorrect");
				redirectionToServlet(ANNONCE_LISTE_SERVLET);
			}
			break;
		case IGNORE_COMMENT:
			try {
				int commentId = Integer.valueOf(this.commentId);
				String identifiant = (String) this.session.getAttribute("login");
				int id_utilisateur = annonceSessionBean.getUserId(identifiant);

				boolean isMathingId = annonceSessionBean.isMatchingUserIdAndCommentId(id_utilisateur, commentId);

				if (isMathingId) {
					annonceSessionBean.ignoreComment(commentId);
					setVariableToView("alert-success", "Commentaire ignoré");
					redirectionToServlet(ANNONCE_LISTE_SERVLET);
				}
			} catch (NumberFormatException exception) {
				setVariableToView("alert-danger", "Numéro incorrect");
				redirectionToServlet(ANNONCE_LISTE_SERVLET);
			}
			break;
		default:
			try {
				int idAnnonce = Integer.valueOf(announcementId);
				String identifiant = (String) this.session.getAttribute("login");
				int id_utilisateur = annonceSessionBean.getUserId(identifiant);
				boolean isMathingId = annonceSessionBean.isMatchingUserIdAndAnnouncementId(id_utilisateur, idAnnonce);

				if (isMathingId) {
					Annonce annonce = annonceSessionBean.getAnnouncement(idAnnonce);
					List<Commentaire> commentaires = annonceSessionBean.getAnnouncementComments(idAnnonce);
					List<ServiceChambre> roomServices = serviceChambreSessionBean.getRoomServices(idAnnonce);
					request.setAttribute("roomServices", roomServices);
					
					request.setAttribute("annonceDetails", annonce);
					request.setAttribute("commentsList", commentaires);
					
					redirectionToView(ANNONCE_DETAILS_VIEW);
				} else {
					setVariableToView("alert-danger", "Numéro incorrect");
					redirectionToServlet(LISTE_ANNONCES);
				}

			} catch (NumberFormatException exception) {
				setVariableToView("alert-danger", "Numéro d'annonce incorrect");
				redirectionToServlet(LISTE_ANNONCES);
			}

			break;
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
		this.announcementId = request.getParameter("annonceId");
		this.action = request.getParameter("action");
		if (this.action == null) {
			this.action = "";
		}
		this.commentId = request.getParameter("commentaireId");
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
	
	/**
	 * Feed request attribute
	 * 
	 * @param variable
	 * @param message
	 */
	public void setVariableToView(String variable, String message) {
		request.setAttribute(variable, message);
	}
}
