package hotelier;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
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
import beans.entity.ServiceChambre;
import beans.session.AnnonceSessionBean;
import beans.session.ServiceChambreSessionBean;

/**
 * Servlet implementation class HotelierAnnonceServlet
 */
@WebServlet("/HotelierAnnonceServlet")
public class HotelierAnnonceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PARAMETER_ACTION_EDIT_ANNONCE = "Modifier";
	private static final String PARAMETER_ACTION_EDIT = "ModifierAnnonce";
	private static final String PARAMETER_ACTION_DELETE = "SupprimerAnnonce";
	private static final String PARAMETER_ACTION_ADD = "Ajouter";
	private static final String PARAMETER_ACTION_ADD_SERVICE = "Ajouter Service";
	private static final String PARAMETER_ACTION_DELETE_SERVICE = "deleteService";
	private static final String ANNONCE_VIEW = "HotelierAnnonce";
	private static final String ANNONCE_LISTE_SERVLET = "HotelierAnnonceListServlet";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private String title;
	private String description;
	private String maxCapacity;
	private int maxCapacityValue;
	private String priceForANight;
	private Double pricePerNight;
	private Date creationDate;
	private String parameter;
	private String announcementId;
	private String serviceId;
	private String service;

	@EJB
	AnnonceSessionBean annonceSessionBean;

	@EJB
	ServiceChambreSessionBean serviceChambreSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;

		initialize();
		// Show, add, modify or delete a annoncement
		switch (this.parameter) {
		case PARAMETER_ACTION_ADD:
			addAnnouncementActionPerformed();
			break;
		case PARAMETER_ACTION_ADD_SERVICE:
			addServiceActionPerformed();
			break;
		case PARAMETER_ACTION_EDIT_ANNONCE:
			addAnnoncementAndModifyActionPerformed();
			break;
		case PARAMETER_ACTION_EDIT:
			modifyAnnoncementActionPerformed();
			break;
		case PARAMETER_ACTION_DELETE:
			deleteAnnoncementActionPerformed();
			break;
		case PARAMETER_ACTION_DELETE_SERVICE:
			deleteServiceActionPerformed();
			break;

		default:
			redirectionToServlet(ANNONCE_LISTE_SERVLET);
			break;
		}

	}

	/**
	 * Add a service
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	private void addServiceActionPerformed() throws ServletException, IOException {
		String name = request.getParameter("serviceName");
		String quantity = request.getParameter("serviceQuantity");
		String idAnnonce = request.getParameter("idAnnonce");
		String identifiant = (String) this.session.getAttribute("login");
		int id_utilisateur = annonceSessionBean.getUserId(identifiant);
		int idAnnonceInt = Integer.valueOf(idAnnonce);
		boolean matchingIdUser = annonceSessionBean.isMatchingUserIdAndAnnouncementId(id_utilisateur, idAnnonceInt);

		if ((name != null || !"".equals(name)) || (quantity != null || !"".equals(quantity))) {
			if (matchingIdUser) {
				try {
					int quantityInt = Integer.valueOf(quantity);
					ServiceChambre serviceChambre = new ServiceChambre();
					serviceChambre.setNom(name);
					serviceChambre.setQuantite(quantityInt);
					serviceChambre.setId_annonce(idAnnonceInt);
					serviceChambreSessionBean.createServiceChambre(serviceChambre);
					
					// Reload and redirection
					setVariableToView("alert-success", "Nouveau service pris en compte");
					redirectionToServlet(ANNONCE_LISTE_SERVLET);
				} catch (NumberFormatException exception) {
					setVariableToView("alert-danger", "Quantité du service incorrect");
					redirectionToServlet(ANNONCE_LISTE_SERVLET);
					exception.printStackTrace();
				}
			} else {
				setVariableToView("alert-danger", "Numéro incorrect");
				redirectionToServlet(ANNONCE_LISTE_SERVLET);
			}
		} else

		{
			setVariableToView("alert-danger", "Fomulaire d'ajout d'un service incorrect");
			redirectionToServlet(ANNONCE_LISTE_SERVLET);
		}
	}

	/**
	 * Delete a service from an announcement
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	private void deleteServiceActionPerformed() throws ServletException, IOException {
		// Looking if the ad id matches the id of the ad id from the service
		try {
			int idService = Integer.valueOf(serviceId);
			int idAnnonce = Integer.valueOf(announcementId);
			boolean matchingIdUser = serviceChambreSessionBean.isMatchingIdAdAndIdAdService(idAnnonce, idService);

			if (matchingIdUser) {
				serviceChambreSessionBean.deleteService(idService);

				// Reload and redirection
				setVariableToView("alert-success", "Suppression du service prise en compte");
				modifyAnnoncementActionPerformed();
			}
		} catch (NumberFormatException exception) {
			redirectionToServlet(ANNONCE_LISTE_SERVLET);
			setVariableToView("alert-danger", "Numéro du service ou de l'annonce incorrect");
			exception.printStackTrace();
		}

	}

	/**
	 * Modify a announcement
	 * @throws IOException
	 * @throws ServletException
	 * 
	 */
	private void addAnnoncementAndModifyActionPerformed() throws ServletException, IOException {
		// looking if the anouncement id (id_user) matches the id of the announcement

		String identifiant = (String) this.session.getAttribute("login");
		int id_utilisateur = annonceSessionBean.getUserId(identifiant);
		boolean isFormOk = verificationFormulaire();

		if (isFormOk) {
			try {
				int idAnnonce = (int) session.getAttribute("sessionAnnonceId");
				this.session.removeAttribute("sessionAnnonceId");
				boolean matchingIdUser = annonceSessionBean.isMatchingUserIdAndAnnouncementId(id_utilisateur, idAnnonce);

				if (matchingIdUser) {
					// get the id of the announcement and update the set in the announcement
					Annonce annonce = new Annonce();
					annonce.setId_annonce(idAnnonce);
					annonce.setId_utilisateur(id_utilisateur);
					annonce.setTitre(title);
					annonce.setDescription(description);
					annonce.setCapacite_max(this.maxCapacityValue);
					annonce.setPrix_nuit(pricePerNight);
					annonceSessionBean.updateAnnouncement(annonce);

					this.request.removeAttribute("annonceEdited");
					setVariableToView("alert-success", "Modifications prisent en compte");
				}
			} catch (NumberFormatException exception) {
				setVariableToView("alert-danger", "Numéro d'annonce incorrect");
				exception.printStackTrace();
			}
		}
		redirectionToServlet(ANNONCE_LISTE_SERVLET);
	}
	
	/**
	 * add a announcement
	 * @throws ServletException
	 * @throws IOException
	 */
	private void addAnnouncementActionPerformed() throws ServletException, IOException {
		final boolean isOkForm = verificationFormulaire();

		if (isOkForm) {
			creationDate = new Date(Calendar.getInstance().getTime().getTime());
			Annonce annonce = new Annonce();
			annonce.setTitre(title.trim());
			annonce.setDescription(description.trim());
			annonce.setCapacite_max(maxCapacityValue);
			annonce.setPrix_nuit(pricePerNight);
			annonce.setActif(true);

			String identifiant = (String) this.session.getAttribute("login");
			int id_utilisateur = annonceSessionBean.getUserId(identifiant);
			annonce.setId_utilisateur(id_utilisateur);
			annonce.setDate_creation(new Timestamp(creationDate.getTime()));
			annonceSessionBean.createAnnouncement(annonce);
			setVariableToView("alert-success", "Annonce ajoutée");
			redirectionToServlet(ANNONCE_LISTE_SERVLET);
		} else {
			setVariableToView("alert-danger", "Informations incorrectes ou manquantes");
			redirectionToView(ANNONCE_VIEW);
		}
	}

	/**
	 * Edit a announcement action
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	private void modifyAnnoncementActionPerformed() throws ServletException, IOException {
		try {
			int idAnnonce = Integer.valueOf(announcementId);
			String identifiant = (String) this.session.getAttribute("login");
			int id_utilisateur = annonceSessionBean.getUserId(identifiant);

			boolean isMathingId = annonceSessionBean.isMatchingUserIdAndAnnouncementId(id_utilisateur, idAnnonce);
			if (isMathingId) {
				Annonce annonce = annonceSessionBean.getAnnouncement(idAnnonce);
				request.setAttribute("annonceEdited", annonce);

				List<ServiceChambre> roomServices = serviceChambreSessionBean.getRoomServices(idAnnonce);
				request.setAttribute("roomServices", roomServices);
				redirectionToView(ANNONCE_VIEW);
			} else {
				setVariableToView("alert-warning", "Le numéro de l'annonce ne correspond pas");
				redirectionToServlet(ANNONCE_LISTE_SERVLET);
			}

		} catch (NumberFormatException exception) {
			setVariableToView("alert-danger", "Numéro d'annonce incorrect");
			redirectionToServlet(ANNONCE_LISTE_SERVLET);
		}
	}

	/**
	 * Delete a announcement action
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	private void deleteAnnoncementActionPerformed() throws ServletException, IOException {
		String identifiant = (String) this.session.getAttribute("login");
		int id_utilisateur = annonceSessionBean.getUserId(identifiant);
		try {
			int idAnnonce = Integer.valueOf(announcementId);
			boolean matchingIdUser = annonceSessionBean.isMatchingUserIdAndAnnouncementId(id_utilisateur, idAnnonce);
			if (matchingIdUser) {
				annonceSessionBean.deleteAnnouncement(idAnnonce);
			}

			setVariableToView("alert-success", "Suppression de l'annonce prise en compte");
			redirectionToServlet(ANNONCE_LISTE_SERVLET);
		} catch (NumberFormatException exception) {
			setVariableToView("alert-danger", "Numéro d'annonce incorrect");
			redirectionToServlet(ANNONCE_LISTE_SERVLET);
		}
	}

	/**
	 * Verification of the form values
	 * 
	 * @return true / false : if the form is OK
	 */
	private boolean verificationFormulaire() {
		boolean isOkForm = true;

		if (title == null || "".equals(title)) {
			isOkForm = false;
		}

		if (description == null || "".equals(description)) {
			isOkForm = false;
		}

		if (maxCapacity == null || "".equals(maxCapacity)) {
			isOkForm = false;
		} else {
			try {
				this.maxCapacityValue = Integer.parseInt(maxCapacity);
			} catch (NumberFormatException exception) {
				isOkForm = false;
			}
		}

		if (priceForANight == null || "".equals(priceForANight)) {
			isOkForm = false;
		} else {
			try {
				this.pricePerNight = Double.parseDouble(priceForANight.trim());
			} catch (NumberFormatException e) {
				isOkForm = false;
			}

		}

		return isOkForm;
	}

	/**
	 * Initialize the values
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		this.session = request.getSession();
		this.response.setContentType("text/html");

		this.title = "";
		this.description = "";
		this.maxCapacity = "";
		this.priceForANight = "";
		this.announcementId = "";
		this.parameter = "";

		this.parameter = request.getParameter("action");

		this.title = request.getParameter("titre");
		this.description = request.getParameter("description");
		this.maxCapacity = request.getParameter("capaciteMax");
		this.priceForANight = request.getParameter("prixNuit");
		this.announcementId = request.getParameter("annonceId");
		this.serviceId = request.getParameter("serviceId");
		this.service = request.getParameter("service");
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
