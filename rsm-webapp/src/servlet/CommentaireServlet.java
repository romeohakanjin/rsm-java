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

import beans.entity.Commentaire;
import beans.session.CommentaireSessionBean;

/**
 * @author Sindy
 */
@WebServlet("/CommentaireServlet")
public class CommentaireServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public String commentaireParameter;
	public Boolean creation;
	public PrintWriter out;
	public HttpServletRequest request;
	public HttpServletResponse response;

	@EJB
	CommentaireSessionBean commentaireSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initialiser(request, response);
		formulaireCreation();

		if (commentaireParameter != null) {
			if (!"".equals(commentaireParameter)) {
				Commentaire commentaire = new Commentaire();
				commentaire.setCommentaire(commentaireParameter);
				creation = commentaireSessionBean.creerCommentaire(commentaire);
				if (creation) {
					List<Commentaire> commentaires = commentaireSessionBean.getAllCommentaire();
					out.println("<table border='1'>");
					for (Commentaire comment : commentaires) {
						out.println("<tr><td>" + comment.getId_commentaire() + "</td><td>" + comment.getCommentaire() + "</td></tr>");
					}
					out.println("</table>");
				} else {
					out.print("L'état n'a pas pu être créé <br>");
				}
			}
		}
	}

	/**
	 * Initiliser
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
	 * Affichage du formulaire de création
	 * Récupèration des élèments saisis par l'utilisateur
	 */
	public void formulaireCreation() {
		out.println("<h2>Créer un commentaire</h2>");
		out.print("<form method='get' action=''>");
		out.println("Commentaire <br>");
		out.println("<textarea name='commentaire'></textarea>");
		out.print("<input type='submit' name='submit'><br>");
		out.print("</form>");
		commentaireParameter = request.getParameter("commentaire");
	}

}