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
import beans.entity.ServiceChambre;
import beans.session.AnnonceSessionBean;
import beans.session.PropositionModificationSessionBean;
import beans.session.ServiceChambreSessionBean;

/**
 * @author RHA
 */
@WebServlet("/AnnouncementPropositionServlet")
public class AnnouncementPropositionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String LIST_RESERVATION = "ModificationPropositionList";
	private static final String HOME_PAGE = "Home";
	private static final String PROPOSITION_MODIFICATION_LIST_ACTION = "list";
	private static final String PROPOSITION_MODIFICATION_LIST_HOTELIER_ACTION = "hotelierList";
	private static final String PROPOSITION_MODIFICATION_ACCEPT = "propositionAccepte";
	private static final String PROPOSITION_MODIFICATION_IGNORE_ACTION = "ignore";
	private static final String ANNONCES_LISTE_SERVLET = "AnnoncesServlet";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private String action;
	private String modificationPropositionId;
	private String annonceId;

	@EJB
	PropositionModificationSessionBean propositionModificationSessionBean;

	@EJB
	ServiceChambreSessionBean serviceChambreSessionBean;

	@EJB
	AnnonceSessionBean annonceSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;

		initialize();

		switch (action) {
		case PROPOSITION_MODIFICATION_LIST_ACTION:
			propositionModificationList();
			break;
		case PROPOSITION_MODIFICATION_LIST_HOTELIER_ACTION:
			propositionModificationListHotelier();
			break;
		case PROPOSITION_MODIFICATION_IGNORE_ACTION:
			// cancel si id statut etc est en attente de confirmation
			ignorePropositionReservation(modificationPropositionId);
			break;
		case PROPOSITION_MODIFICATION_ACCEPT:
			acceptModificationProposition();
			break;
		default:
			redirectionToView(HOME_PAGE);
			break;
		}

	}

	/**
	 * Accept a modification proposition for a announcement
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	private void acceptModificationProposition() throws ServletException, IOException {
		String identifiant = (String) this.session.getAttribute("login");
		int id_utilisateur = annonceSessionBean.getIdUtilisateur(identifiant);

		try {
			int annonceIdInt = Integer.valueOf(annonceId);
			int idModificationProposition = Integer.valueOf(modificationPropositionId);
			boolean isMatchinIdUserAndIdAnnouncement = annonceSessionBean
					.isMatchingIdUserAndIdUserAnnonce(id_utilisateur, annonceIdInt);

			if (isMatchinIdUserAndIdAnnouncement) {
				boolean isMatchinId = propositionModificationSessionBean
						.isMatchinIdAnnoucementAndIdProposition(annonceIdInt, idModificationProposition);
				if (isMatchinId) {
					PropositionModificationAnnonce propositionModificationAnnonce = propositionModificationSessionBean
							.getModificationsPropositionById(idModificationProposition);
					boolean alreadyExistService = serviceChambreSessionBean
							.isExistingService(propositionModificationAnnonce.getNom(), annonceIdInt);

					if (alreadyExistService) {
						// si le service exist on l'update
						serviceChambreSessionBean.updateRoomService(propositionModificationAnnonce);
					} else {
						// si le service nexiste pas on lajoute
						ServiceChambre serviceChambre = new ServiceChambre();
						serviceChambre.setId_annonce(annonceIdInt);
						serviceChambre.setNom(propositionModificationAnnonce.getNom());
						serviceChambre.setQuantite(propositionModificationAnnonce.getQuantite());

						serviceChambreSessionBean.creerServiceChambre(serviceChambre);
					}

					// After adding the proposition the list of service we delete it from the
					// Modification Proposition table
					propositionModificationSessionBean.deleteModificationProposition(modificationPropositionId);

					setVariableToView("alert-success", "Cette proposition vient d'être ignorée");
					redirectionToServlet(ANNONCES_LISTE_SERVLET);
				} else {
					setVariableToView("alert-warning", "Numéro de proposition incorrect");
					redirectionToServlet(ANNONCES_LISTE_SERVLET);
				}
			} else {
				setVariableToView("alert-warning", "Numéro d'annonce incorrect");
				redirectionToServlet(ANNONCES_LISTE_SERVLET);
			}
		} catch (NumberFormatException exception) {
			setVariableToView("alert-warning", "Numéro d'annonce ou de proposition incorrect");
			redirectionToServlet(ANNONCES_LISTE_SERVLET);
		}
	}

	/**
	 * Show all the modifications propostitions for a hotelier
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	private void propositionModificationListHotelier() throws ServletException, IOException {
		String identifiant = (String) this.session.getAttribute("login");
		int id_utilisateur = annonceSessionBean.getIdUtilisateur(identifiant);

		List<PropositionModificationAnnonce> propositionsModifications = propositionModificationSessionBean
				.getModificationsPropositionByUserId(id_utilisateur);
		this.request.setAttribute("propositionsModifications", propositionsModifications);

		redirectionToView(LIST_RESERVATION);
	}

	/**
	 * Show all the modifications propositions
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	private void propositionModificationList() throws ServletException, IOException {
		List<PropositionModificationAnnonce> propositionsModifications = propositionModificationSessionBean
				.getModificationsPorpositions();
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
		boolean isOkReservation = propositionModificationSessionBean
				.isExistingModificationProposition(modificationPropositionId);

		if (isOkReservation) {
			// ignorer la proposition de modification
			propositionModificationSessionBean.deleteModificationProposition(modificationPropositionId);
			setVariableToView("alert-success", "Cette proposition vient d'être ignorée");
			redirectionToServlet(ANNONCES_LISTE_SERVLET);
		} else {
			setVariableToView("alert-warning", "Numéro de proposition incorrect");
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
		this.modificationPropositionId = request.getParameter("modificationPropositionId");
		this.annonceId = request.getParameter("annonceId");
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
