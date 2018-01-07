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

import beans.entity.Annonce;
import beans.session.AnnonceSessionBean;

/**
 * @author SLI
 */
@WebServlet("/AnnonceServlet")
public class AnnonceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public String titreParameter;
	public String descriptionParameter;
	public String capaciteParameter;
	public Integer capaciteMax;
	public Boolean creation;
	public PrintWriter out;
	public HttpServletRequest request;
	public HttpServletResponse response;

	@EJB
    AnnonceSessionBean annonceSessionBean;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initialiser(request, response);
		formulaireCreation();
		
		List<Annonce> annonces = annonceSessionBean.getAllAnnonce();
		for (Annonce annonce : annonces) {
			out.println(annonce.getTitre());
			out.println(annonce.getDescription());
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
	 * Affiche le formulaire de création
	 * Récupère les informations saisis
	 */
	public void formulaireCreation() {
		out.println("<h2>Créer une annonce</h2>");
		out.print("<form method='get' action=''>");
		out.println("Titre : <input type='text' name='titre'/> <br>");
		out.println("Description <br>");
		out.println("<textarea name='description'></textarea> <br>");
		out.println("Capacité maximale : <input type='text' name='capacite'/> <br>");
		out.print("<input type='submit' name='submit'><br>");
		out.print("</form>");
		titreParameter = request.getParameter("titre");
		descriptionParameter = request.getParameter("description");
		capaciteParameter = request.getParameter("capacite");
	}

}