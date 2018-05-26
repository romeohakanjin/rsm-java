package standard;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.entity.Annonce;
import beans.entity.PropositionModificationAnnonce;
import beans.entity.ServiceChambre;
import beans.session.AnnonceSessionBean;
import beans.session.PropositionModificationSessionBean;
import beans.session.ReservationSessionBean;
import beans.session.ServiceChambreSessionBean;

/**
 * Servlet implementation class AnnoncesDetailsServlet
 */
@WebServlet("/AnnoncesDetailsServlet")
public class AnnoncesDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONNECTION_VIEW = "Connexion";
	private static final String ANNONCE_ADD_SERVICE_VIEW = "ModificationProposition";
	private static final String ANNONCE_DETAILS_VIEW = "AnnonceDetails";
	private static final String ANNONCE_DETAILS_PROP_MODIFICATION_ACTION = "Ajouter";
	private static final String ANNONCES_LISTE_SERVLET = "AnnoncesServlet";
	private static final String RESERVATION_ACTION = "reserver";
	private static final String RESERVATION_VALIDATE_SERVLET = "ReservationConfirmationServlet";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private String annonceId;
	private int annonceIdInt;
	private int reservationId;
	private String action;
	private String dateDebut;
	private String dateFin;
	private double price;
	private double pricePerNight;
	private long numberOfDays;
	private Timestamp timestampBegining;
	private Timestamp timestampEnd;
	private String nameService;
	private String quantityService;

	@EJB
	AnnonceSessionBean annonceSessionBean;

	@EJB
	ReservationSessionBean reservationSessionBean;

	@EJB
	ServiceChambreSessionBean serviceChambreSessionBean;
	
	@EJB
	PropositionModificationSessionBean propositionModificationSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;

		initialiser();

		try {
			if (annonceId != null) {
				annonceIdInt = Integer.valueOf(annonceId);
			}

			switch (action) {
			case RESERVATION_ACTION:
				reservationActionPerformed();
				break;
			case ANNONCE_DETAILS_PROP_MODIFICATION_ACTION:
				propositionModificationActionPerformed();
				break;
			default:
				Annonce annonce = annonceSessionBean.getAnnonce(annonceIdInt);
				request.setAttribute("annonceDetails", annonce);

				List<ServiceChambre> roomServices = serviceChambreSessionBean.getRoomServices(annonceIdInt);
				request.setAttribute("roomServices", roomServices);

				redirectionToView(ANNONCE_DETAILS_VIEW);
				break;
			}
		} catch (NumberFormatException exception) {
			redirectionToServlet(ANNONCES_LISTE_SERVLET);
		}

	}

	/**
	 * Action for a proposition of modification
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	private void propositionModificationActionPerformed() throws ServletException, IOException {
		System.out.println(annonceId);
		System.out.println(nameService);
		System.out.println(quantityService);
		try {
			annonceIdInt = Integer.valueOf(annonceId);
			int quantity = Integer.valueOf(quantityService);
			String identifiant = (String) this.session.getAttribute("login");
			int id_utilisateur = annonceSessionBean.getIdUtilisateur(identifiant);
			boolean isMatchingId = reservationSessionBean.isMatchingIdUserReservationAndIdAnnouncement(id_utilisateur, annonceIdInt);
			
			if (isMatchingId) {
				PropositionModificationAnnonce proposition = new PropositionModificationAnnonce();
				proposition.setId_annonce(annonceIdInt);
				proposition.setId_utilisateur(id_utilisateur);
				proposition.setNom(nameService);
				proposition.setQuantite(quantity);
				proposition.setDate_proposition(new Timestamp(System.currentTimeMillis()));
				
				propositionModificationSessionBean.createModificationProposition(proposition);
				
				setVariableToView("alert-success", "Votre proposition vient d'être prise en compte");
				redirectionToServlet(ANNONCES_LISTE_SERVLET);
			} else {
				setVariableToView("alert-danger", "Cette réservation n'est pas encore passer");
				redirectionToServlet(ANNONCES_LISTE_SERVLET);
			}
		
		} catch (NumberFormatException exception) {
			setVariableToView("alert-danger", "Numéro d'annonce ou quantité du service incorrect");
			redirectionToServlet(ANNONCES_LISTE_SERVLET);
		}
	}

	/**
	 * Action for the Reservation button for a announce
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	private void reservationActionPerformed() throws ServletException, IOException {
		if (session.getAttribute("session-standard") != null) {
			final boolean isOkForm = verificationFormulaire();

			if (isOkForm) {
				// V�rifier si l'id de l'utilisateur est bien celui d'un utilisateurs standard
				String identifiant = (String) this.session.getAttribute("login");
				int id_utilisateur = annonceSessionBean.getIdUtilisateur(identifiant);

				boolean isActiveAd = annonceSessionBean.isActiveAd(annonceIdInt);

				if (isActiveAd) {
					boolean isAlreadyReservedAdForThisDate = reservationSessionBean.isOkDateForReservation(annonceIdInt,
							timestampBegining, timestampEnd);
					if (!isAlreadyReservedAdForThisDate) {
						// alors send sur la page de r�sumer de r�servation
						session.setAttribute("reservationToValidate", annonceIdInt);
						session.setAttribute("reservationIdUser", id_utilisateur);
						session.setAttribute("reservationDateDebut", timestampBegining);
						session.setAttribute("reservationDateFin", timestampEnd);
						session.setAttribute("reservationNumberOfDays", numberOfDays);
						session.setAttribute("reservationPrice", price);
						redirectionToServlet(RESERVATION_VALIDATE_SERVLET);
					}

				} else {
					setVariableToView("alert-danger", "Num�ro d'annonce incorrect");
					redirectionToServlet(ANNONCES_LISTE_SERVLET);
				}
			} else {
				setVariableToView("alert-danger", "Date incorect");
				redirectionToServlet(ANNONCES_LISTE_SERVLET);
			}
		} else {
			setVariableToView("alert-danger", "Vous devez �tre connect� pour acc�der � cette page");
			redirectionToView(CONNECTION_VIEW);
		}
	}

	/**
	 * Veirfication des champs saisies
	 * 
	 * @return boolean isOkForm
	 * @throws ServletException
	 * @throws ParseException
	 */
	private boolean verificationFormulaire() throws ServletException {
		boolean isOkForm = true;

		if (dateDebut == null || "".equals(dateDebut) && dateFin == null || "".equals(dateFin)) {
			isOkForm = false;
		} else {
			boolean reservationForActualDate = false;
			Date dateBegining = new Date(dateDebut);
			Date dateEnd = new Date(dateFin);

			if (dateEnd.getTime() < dateBegining.getTime()) {
				isOkForm = false;
			}

			// verifier si les dates sont pas avant la date actuelle
			// TODO : la date actuelle est avec une heure, tandis que quand on cr�er le
			// timestamp on donne pas l'heuredonc ne compare pas correctement pour les
			// reservation le jour j
			// TODO: FIXE THAT

			Date actualDate = new Date();
			if ((dateEnd.getTime() < actualDate.getTime()) && (dateBegining.getTime() < actualDate.getTime())) {
				isOkForm = false;
			} else {
				// if it is a reservation for the actual date
				if (dateBegining.getTime() == dateEnd.getTime()) {
					reservationForActualDate = true;
					this.numberOfDays = 1;
					pricePerNight = annonceSessionBean.getPricePerNightAd(annonceIdInt);
					this.price = pricePerNight;
				}
			}

			timestampBegining = new java.sql.Timestamp(dateBegining.getTime());
			timestampEnd = new java.sql.Timestamp(dateEnd.getTime());

			if (!reservationForActualDate) {
				// TODO : contr�le sur la date
				// V�rifier si le mois est pas sup�rieur � 12 ou inf�rieur � 1
				// V�rifier que les jours c'est pas sup�rieur � 31 et inf�rieur � 1
				// V�rifier que en fevrier on d�passe aps 28 jours
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
					Date firstDate = sdf.parse(dateDebut);
					Date secondDate = sdf.parse(dateFin);

					long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
					this.numberOfDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

					pricePerNight = annonceSessionBean.getPricePerNightAd(annonceIdInt);
					this.price = pricePerNight * numberOfDays;
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}

		if (annonceId == null || "".equals(annonceId)) {
			isOkForm = false;
		} else {
			try {
				this.annonceIdInt = Integer.parseInt(annonceId);
			} catch (NumberFormatException exception) {
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
	private void initialiser() throws IOException {
		this.session = request.getSession();
		this.response.setContentType("text/html");
		this.dateDebut = request.getParameter("dateDebut");
		this.dateFin = request.getParameter("dateFin");
		this.annonceId = request.getParameter("annonceId");
		this.nameService = request.getParameter("nom");
		this.quantityService = request.getParameter("quantite");
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
