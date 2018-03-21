package hotelier;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.entity.Annonce;
import beans.session.AnnonceSessionBean;

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
	private static final String ANNONCE_VIEW = "HotelierAnnonce";
	private static final String ANNONCE_LISTE_SERVLET = "HotelierAnnonceListServlet";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private String titre;
	private String description;
	private String capaciteMax;
	private int capaciteMaxValue;
	private Date dateCreation;
	private String parametre;
	private String annonceId;
	// private String parametreAnnonce;

	@EJB
	AnnonceSessionBean annonceSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;

		initialiser();

		// Afficher ou supprimer une annonce
		switch (this.parametre) {
		case PARAMETER_ACTION_ADD:
			ajouterAnnonceActionPerformed();
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

		default:
			redirectionToServlet(ANNONCE_LISTE_SERVLET);
			break;
		}

	}

	/**
	 * @throws IOException
	 * @throws ServletException
	 * 
	 */
	private void ajouterAnnonceModifierActionPerformed() throws ServletException, IOException {
		// Vérifier si l'id de l'annonce (id utilisateur) match l'id de l'id annonce

		String identifiant = (String) this.session.getAttribute("login");
		int id_utilisateur = annonceSessionBean.getIdUtilisateur(identifiant);
		boolean isFormOk = verificationFormulaire();

		if (isFormOk) {
			try {
				int idAnnonce = (int) session.getAttribute("sessionAnnonceId");
				this.session.removeAttribute("sessionAnnonceId");
				boolean matchingIdUser = annonceSessionBean.isMatchingIdUserAndIdUserAnnonce(id_utilisateur, idAnnonce);

				if (matchingIdUser) {
					// Récupérer l'id de l'annonce modifié et le set dans annonce
					Annonce annonce = new Annonce();
					annonce.setId_annonce(idAnnonce);
					annonce.setId_utilisateur(id_utilisateur);
					annonce.setTitre(titre);
					annonce.setDescription(description);
					annonce.setCapacite_max(this.capaciteMaxValue);
					annonceSessionBean.updateAnnonce(annonce);

					this.request.removeAttribute("annonceEdited");
				}
			} catch (NumberFormatException exception) {
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
			annonce.setTitre(titre);
			annonce.setDescription(description);
			annonce.setCapacite_max(capaciteMaxValue);

			String identifiant = (String) this.session.getAttribute("login");
			int id_utilisateur = annonceSessionBean.getIdUtilisateur(identifiant);
			annonce.setId_utilisateur(id_utilisateur);
			annonce.setDate_creation(new Timestamp(dateCreation.getTime()));
			annonceSessionBean.creerAnnonce(annonce);

			request.removeAttribute("error-hotelier-annonce-form");
			System.out.println("gribouti");
			redirectionToServlet(ANNONCE_LISTE_SERVLET);
		} else {
			setVariableToView("error-hotelier-annonce-form", "Informations incorrectes ou manquantes");
			System.out.println("yata");
			redirectionToView(ANNONCE_VIEW);
		}
	}

	/**
	 * Edit a annonce action
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
				redirectionToView(ANNONCE_VIEW);
			} else {
				redirectionToServlet(ANNONCE_LISTE_SERVLET);
			}

		} catch (NumberFormatException exception) {
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

			redirectionToServlet(ANNONCE_LISTE_SERVLET);
		} catch (NumberFormatException exception) {
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
		this.annonceId = "";
		this.parametre = "";

		this.parametre = request.getParameter("action");
		if (this.parametre == null) {
			this.parametre = request.getParameter("submitButtonHotelierForm");
		}

		this.parametre = request.getParameter("submitButtonHotelierForm");
		if (this.parametre == null) {
			this.parametre = request.getParameter("action");
		}

		this.titre = request.getParameter("titre");
		this.description = request.getParameter("description");
		this.capaciteMax = request.getParameter("capaciteMax");
		this.annonceId = request.getParameter("annonceId");
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
