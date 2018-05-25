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
	private String titre;
	private String description;
	private String capaciteMax;
	private int capaciteMaxValue;
	private String prixNuit;
	private Double prixParNuit;
	private Date dateCreation;
	private String parametre;
	private String annonceId;
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

		initialiser();
		// Afficher, ajouter, modifier ou supprimer une annonce
		switch (this.parametre) {
		case PARAMETER_ACTION_ADD:
			ajouterAnnonceActionPerformed();
			break;
		case PARAMETER_ACTION_ADD_SERVICE:
			addServiceActionPerformed();
			break;
		case PARAMETER_ACTION_EDIT_ANNONCE:
			ajouterAnnonceModifierActionPerformed();
			break;
		case PARAMETER_ACTION_EDIT:
			modifierAnnonceActionPerformed();
			break;
		case PARAMETER_ACTION_DELETE:
			deleteAnnonceActionPerformed();
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
		int id_utilisateur = annonceSessionBean.getIdUtilisateur(identifiant);
		int idAnnonceInt = Integer.valueOf(idAnnonce);
		boolean matchingIdUser = annonceSessionBean.isMatchingIdUserAndIdUserAnnonce(id_utilisateur, idAnnonceInt);

		if ((name != null || !"".equals(name)) || (quantity != null || !"".equals(quantity))) {
			if (matchingIdUser) {
				try {
					int quantityInt = Integer.valueOf(quantity);
					ServiceChambre serviceChambre = new ServiceChambre();
					serviceChambre.setNom(name);
					serviceChambre.setQuantite(quantityInt);
					serviceChambre.setId_annonce(idAnnonceInt);
					serviceChambreSessionBean.creerServiceChambre(serviceChambre);
					
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
			int idAnnonce = Integer.valueOf(annonceId);
			boolean matchingIdUser = serviceChambreSessionBean.isMatchingIdAdAndIdAdService(idAnnonce, idService);

			if (matchingIdUser) {
				serviceChambreSessionBean.deleteService(idService);

				// Reload and redirection
				setVariableToView("alert-success", "Suppression du service prise en compte");
				modifierAnnonceActionPerformed();
			}
		} catch (NumberFormatException exception) {
			redirectionToServlet(ANNONCE_LISTE_SERVLET);
			setVariableToView("alert-danger", "Numéro du service ou de l'annonce incorrect");
			exception.printStackTrace();
		}

	}

	/**
	 * @throws IOException
	 * @throws ServletException
	 * 
	 */
	private void ajouterAnnonceModifierActionPerformed() throws ServletException, IOException {
		// looking if the anouncement id (id_user) matches the id of the announcement

		String identifiant = (String) this.session.getAttribute("login");
		int id_utilisateur = annonceSessionBean.getIdUtilisateur(identifiant);
		boolean isFormOk = verificationFormulaire();

		if (isFormOk) {
			try {
				int idAnnonce = (int) session.getAttribute("sessionAnnonceId");
				this.session.removeAttribute("sessionAnnonceId");
				boolean matchingIdUser = annonceSessionBean.isMatchingIdUserAndIdUserAnnonce(id_utilisateur, idAnnonce);

				if (matchingIdUser) {
					// get the id of the announcement and update the set in the announcement
					Annonce annonce = new Annonce();
					annonce.setId_annonce(idAnnonce);
					annonce.setId_utilisateur(id_utilisateur);
					annonce.setTitre(titre);
					annonce.setDescription(description);
					annonce.setCapacite_max(this.capaciteMaxValue);
					annonce.setPrix_nuit(prixParNuit);
					annonceSessionBean.updateAnnonce(annonce);

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

	private void ajouterAnnonceActionPerformed() throws ServletException, IOException {
		final boolean isOkForm = verificationFormulaire();

		if (isOkForm) {
			dateCreation = new Date(Calendar.getInstance().getTime().getTime());
			Annonce annonce = new Annonce();
			annonce.setTitre(titre.trim());
			annonce.setDescription(description.trim());
			annonce.setCapacite_max(capaciteMaxValue);
			annonce.setPrix_nuit(prixParNuit);
			annonce.setActif(true);

			String identifiant = (String) this.session.getAttribute("login");
			int id_utilisateur = annonceSessionBean.getIdUtilisateur(identifiant);
			annonce.setId_utilisateur(id_utilisateur);
			annonce.setDate_creation(new Timestamp(dateCreation.getTime()));
			annonceSessionBean.creerAnnonce(annonce);
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
	private void modifierAnnonceActionPerformed() throws ServletException, IOException {
		try {
			int idAnnonce = Integer.valueOf(annonceId);
			String identifiant = (String) this.session.getAttribute("login");
			int id_utilisateur = annonceSessionBean.getIdUtilisateur(identifiant);

			boolean isMathingId = annonceSessionBean.isMatchingIdUserAndIdUserAnnonce(id_utilisateur, idAnnonce);
			if (isMathingId) {
				Annonce annonce = annonceSessionBean.getAnnonce(idAnnonce);
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
	 * Delete a annonce action
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	private void deleteAnnonceActionPerformed() throws ServletException, IOException {
		String identifiant = (String) this.session.getAttribute("login");
		int id_utilisateur = annonceSessionBean.getIdUtilisateur(identifiant);
		try {
			int idAnnonce = Integer.valueOf(annonceId);
			boolean matchingIdUser = annonceSessionBean.isMatchingIdUserAndIdUserAnnonce(id_utilisateur, idAnnonce);
			if (matchingIdUser) {
				annonceSessionBean.deleteAnnonce(idAnnonce);
			}

			setVariableToView("alert-success", "Suppression de l'annonce prise en compte");
			redirectionToServlet(ANNONCE_LISTE_SERVLET);
		} catch (NumberFormatException exception) {
			setVariableToView("alert-danger", "Numéro d'annonce incorrect");
			redirectionToServlet(ANNONCE_LISTE_SERVLET);
		}
	}

	/**
	 * Veirfication des champs saisies
	 * 
	 * @return boolean isOkForm
	 */
	private boolean verificationFormulaire() {
		boolean isOkForm = true;

		if (titre == null || "".equals(titre)) {
			isOkForm = false;
		}

		if (description == null || "".equals(description)) {
			isOkForm = false;
		}

		if (capaciteMax == null || "".equals(capaciteMax)) {
			isOkForm = false;
		} else {
			try {
				this.capaciteMaxValue = Integer.parseInt(capaciteMax);
			} catch (NumberFormatException exception) {
				isOkForm = false;
			}
		}

		if (prixNuit == null || "".equals(prixNuit)) {
			isOkForm = false;
		} else {
			try {
				this.prixParNuit = Double.parseDouble(prixNuit.trim());
			} catch (NumberFormatException e) {
				isOkForm = false;
			}

		}

		return isOkForm;
	}

	/**
	 * Itinaliser les variables
	 * 
	 * @throws IOException
	 */
	private void initialiser() throws IOException {
		this.session = request.getSession();
		this.response.setContentType("text/html");

		this.titre = "";
		this.description = "";
		this.capaciteMax = "";
		this.prixNuit = "";
		this.annonceId = "";
		this.parametre = "";

		this.parametre = request.getParameter("action");

		this.titre = request.getParameter("titre");
		this.description = request.getParameter("description");
		this.capaciteMax = request.getParameter("capaciteMax");
		this.prixNuit = request.getParameter("prixNuit");
		this.annonceId = request.getParameter("annonceId");
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
