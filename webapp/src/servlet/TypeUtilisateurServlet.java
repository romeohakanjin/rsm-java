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
import beans.session.TypeUtilisateurSessionBean;

/**
 * @author Sindy
 */
@WebServlet("/TypeUtilisateurServlet")
public class TypeUtilisateurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public String libelle;
	public Boolean creation;
	public PrintWriter out;
	public HttpServletRequest request;
	public HttpServletResponse response;

	@EJB
	TypeUtilisateurSessionBean typeUtilisateurSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initialiser(request, response);
		formulaireCreation();

		if (libelle != null) {
			if (!"".equals(libelle)) {
				creation = typeUtilisateurSessionBean.creerTypeUtilisateur(libelle);
				if (creation) {
					List<TypeUtilisateur> typeUtilisateurs = typeUtilisateurSessionBean.getAllTypeUtilisateur();
					out.println("<table border='1'>");
					for (TypeUtilisateur type : typeUtilisateurs) {
						out.println("<tr><td>" + type.getId_type() + "</td><td>" + type.getLibelle() + "</td></tr>");
					}
					out.println("</table>");
				} else {
					out.print("Le type n'a pas pu être créé <br>");
				}
			}
		}
	}

	public void initialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		this.request = request;
		this.response = response;
		out = response.getWriter();
		response.setContentType("text/html");
	}

	public void formulaireCreation() {
		out.println("<h2>Créer un type d'utilisateur</h2>");
		out.print("<form method='get' action=''>");
		out.println("Nom du type d'utilisateur : <input type='text' name='nomTypeUtilisateur'/> <br>");
		out.print("<input type='submit' name='submit'><br>");
		out.print("</form>");
		libelle = request.getParameter("nomTypeUtilisateur");
	}

}
