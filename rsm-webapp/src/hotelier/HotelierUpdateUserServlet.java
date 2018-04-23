package hotelier;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.entity.Utilisateur;
import beans.session.UtilisateurSessionBean;

/**
 * @author SLI
 */
@WebServlet("/HotelierUpdateUserServlet")
public class HotelierUpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PARAMETER_ACTION_EDIT = "modifier";
	private static final String PARAMETER_ACTION_EDIT_USER = "modifierUtilisateur";
	private static final String EDIT_USER_INFORMATION_VIEW = "HotelierProfilEdit";
	private static final String USER_EDITED_SERVLET = "HotelierProfilServlet";

	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private String nom;
	private String prenom;
	private String mail;
	private String mobile;
	private String adresse;
	private String ville;
	private String codePostal;
	private String parametre;

	@EJB
	UtilisateurSessionBean utilisateurSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;

		initialiser();
		switch (this.parametre) {
		case PARAMETER_ACTION_EDIT_USER:
			displayEditPage();
			break;
		case PARAMETER_ACTION_EDIT:
			userEditInformations();
			break;
		default:
			redirectionToServlet(USER_EDITED_SERVLET);
			break;
		}
	}
	
	/**
	 * Affiche la page de modification du profil
	 * @throws ServletException
	 * @throws IOException
	 */
	private void displayEditPage() throws ServletException, IOException {
		
		String identifiant = (String) this.session.getAttribute("login");
		int id_utilisateur = utilisateurSessionBean.getIdUtilisateur(identifiant);		
		try {
			// Vérifier si l'id utilisateur match
			boolean isMathingId = utilisateurSessionBean.isMatchingIdUser(id_utilisateur);
			if (isMathingId) {
				Utilisateur utilisateur = utilisateurSessionBean.getUtilisateur(id_utilisateur);
				request.setAttribute("userInformations", utilisateur);
				redirectionToView(EDIT_USER_INFORMATION_VIEW);
			} else {
				redirectionToView(USER_EDITED_SERVLET);
			}
		}catch(NumberFormatException exception) {
			redirectionToServlet(USER_EDITED_SERVLET);
		}		
	}

	/**
	 * Modifie les informations du profil
	 * @throws ServletException
	 * @throws IOException
	 */
	private void userEditInformations() throws ServletException, IOException {	
		String identifiant = (String) this.session.getAttribute("login");
		int id_utilisateur = utilisateurSessionBean.getIdUtilisateur(identifiant);
		boolean isMathingId = utilisateurSessionBean.isMatchingIdUser(id_utilisateur);
		if (isMathingId) {
			Utilisateur utilisateur = new Utilisateur();
			utilisateur.setId_utilisateur(id_utilisateur);
			utilisateur.setNom(nom);
			utilisateur.setPrenom(prenom);
			utilisateur.setMail(mail);
			utilisateur.setMobile(mobile);
			utilisateur.setAdresse(adresse);
			utilisateur.setVille(ville);
			utilisateur.setCode_postal(codePostal);
			utilisateurSessionBean.updateUtilisateur(utilisateur);
		}
		redirectionToServlet(USER_EDITED_SERVLET);
	}
	
	/**
	 * 
	 * @throws IOException
	 */
	private void initialiser() throws IOException {
		this.session = request.getSession();
		this.response.setContentType("text/html");

		this.nom = "";
		this.prenom = "";
		this.adresse = "";
		this.ville = "";
		this.codePostal = "";
		this.mail = "";
		this.mobile = "";
		this.parametre = "";
		this.parametre = request.getParameter("action");
		if (this.parametre == null) {
			this.parametre = request.getParameter("submitButtonUtilisateurForm");
		}
		this.parametre = request.getParameter("submitButtonUtilisateurForm");
		if (this.parametre == null) {
			this.parametre = request.getParameter("action");
		}
		this.nom = request.getParameter("nom");
		this.prenom = request.getParameter("prenom");
		this.mobile = request.getParameter("mobile");
		this.mail = request.getParameter("mail");
		this.adresse = request.getParameter("adresse");
		this.ville = request.getParameter("ville");
		this.codePostal = request.getParameter("codePostal");
	}
	
	/**
	 * Redirection to a view
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
	 * @param String : the servlet name
	 * @throws ServletException
	 * @throws IOException
	 */
	private void redirectionToServlet(String sevlet) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(sevlet);
		dispatcher.include(request, response);
	}
}
