package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.entity.TypeUtilisateur;
import beans.entity.Utilisateur;
import beans.session.TypeUtilisateurSessionBean;
import beans.session.UtilisateurSessionBean;

/**
 * @author Sindy
 */
@WebServlet("/UtilisateurServlet")
public class UtilisateurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public Integer idTypeUtilisateur;
	public String idTypeUtilisateurParameter;
	
	public String nomParameter;
	public String prenomParameter;
	public String mailParameter;
	public String mobileParameter;
	public String adresseParameter;
	public String codePostalParameter;
	public String villeParameter;

	public Boolean creation;
	public PrintWriter out;
	public HttpServletRequest request;
	public HttpServletResponse response;

	@EJB
	TypeUtilisateurSessionBean typeUtilisateurSessionBean;
	@EJB
	UtilisateurSessionBean utilisateurSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initialiser(request, response);
		formulaireCreation();

		if ((idTypeUtilisateurParameter != null) && (nomParameter != null) && (prenomParameter != null) && (mailParameter != null) 
				&& (mobileParameter != null) && (adresseParameter != null) && (codePostalParameter != null) && (villeParameter != null)) {
			if (!"".equals(idTypeUtilisateurParameter) && !"".equals(nomParameter) && !"".equals(prenomParameter) && !"".equals(mailParameter)
					&& !"".equals(mobileParameter) && !"".equals(adresseParameter) && !"".equals(codePostalParameter) && !"".equals(villeParameter)) {
				
				idTypeUtilisateur = Integer.parseInt(idTypeUtilisateurParameter);
				
				Utilisateur utilisateur = new Utilisateur();
				utilisateur.setId_utilisateur(idTypeUtilisateur);
				utilisateur.setNom(nomParameter);
				utilisateur.setPrenom(prenomParameter);
				utilisateur.setMail(mailParameter);
				utilisateur.setMobile(mobileParameter);
				utilisateur.setAdresse(adresseParameter);
				utilisateur.setCodePostal(codePostalParameter);
				utilisateur.setVille(villeParameter);
				
				creation = utilisateurSessionBean.creerUtilisateur(utilisateur);
				//TODO : AFFICHAGE A ENLEVER; ICI JUSTE POUR TESTER LA CREATION
				if (creation) {
					List<Utilisateur> utilisateurs = utilisateurSessionBean.getAllUtilisateur();
					out.println("<table border='1'>");
					for (Utilisateur user : utilisateurs) {
						out.println("<tr><td>" + user.getNom() + "</td><td>" + user.getPrenom() + "</td></tr>");
					}
					out.println("</table>");
				} else {
					out.print("L'utilisateur n'a pas pu être créé <br>");
				}
			}
		}
	}
	
	/**
	 * Initialiser
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void initialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		this.request = request;
		this.response = response;
		out = response.getWriter();
		response.setContentType("text/html");
	}

	/**
	 * Affiche le formulaire de création de l'utilisateur
	 * Récupération des élements saisis par l'utilisateur
	 */
	public void formulaireCreation() {

		List<TypeUtilisateur> typeUtilisateurs = typeUtilisateurSessionBean.getAllTypeUtilisateur();
		out.println("<h2>Créer un utilisateur</h2>");
		out.print("<form method='get' action=''>");
		out.println("<select name='typeUtilisateur'>");
		for (TypeUtilisateur type : typeUtilisateurs) {
			out.println("<option value=" + type.getId_type() + ">" + type.getLibelle() + "</option>");
		}
		out.println("</select>");
		out.println("Nom : <input type='text' name='nom'/> <br>");
		out.println("Prénom : <input type='text' name='prenom'/> <br>");
		out.println("Adresse mail : <input type='text' name='mail'/> <br>");
		out.println("Mobile : <input type='text' name='mobile'/> <br>");
		out.println("Adresse : <input type='text' name='adresse'/> <br>");
		out.println("Code postal : <input type='text' name='codePostal'/> <br>");
		out.println("Ville : <input type='text' name='ville'/> <br>");
		out.print("<input type='submit' name='submit'><br>");
		out.print("</form>");
		
		idTypeUtilisateurParameter = request.getParameter("typeUtilisateur");
		nomParameter = request.getParameter("nom");
		prenomParameter = request.getParameter("prenom");
		mailParameter = request.getParameter("mail");
		mobileParameter = request.getParameter("mobile");
		adresseParameter = request.getParameter("adresse");
		codePostalParameter = request.getParameter("codePostal");
		villeParameter = request.getParameter("ville");

	}

}
