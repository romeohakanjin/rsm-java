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

import beans.entity.EtatReservation;
import beans.session.EtatReservationSessionBean;

/**
 * @author Sindy
 */
@WebServlet("/EtatReservationServlet")
public class EtatReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public String libelle;
	public Boolean creation;
	public PrintWriter out;
	public HttpServletRequest request;
	public HttpServletResponse response;

	@EJB
	EtatReservationSessionBean etatReservationSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initialiser(request, response);
		formulaireCreation();

		if (libelle != null) {
			if (!"".equals(libelle)) {
				creation = etatReservationSessionBean.creerEtatReservation(libelle);
				if (creation) {
					List<EtatReservation> statutReservations = etatReservationSessionBean.getAllEtatReservation();
					out.println("<table border='1'>");
					for (EtatReservation etat : statutReservations) {
						out.println("<tr><td>" + etat.getId_etat() + "</td><td>" + etat.getLibelle() + "</td></tr>");
					}
					out.println("</table>");
				} else {
					out.print("L'état n'a pas pu être créé <br>");
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

	/**
	 * Affiche le formulaire de création
	 * Récupère les informations saisis par l'utilisateur
	 */
	public void formulaireCreation() {
		out.println("<h2>Créer un état de réservation</h2>");
		out.print("<form method='get' action=''>");
		out.println("Libellé de l'état : <input type='text' name='libelleEtat'/> <br>");
		out.print("<input type='submit' name='submit'><br>");
		out.print("</form>");
		libelle = request.getParameter("libelleEtat");
	}

}

