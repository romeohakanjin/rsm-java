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

import beans.entity.StatutReservation;
import beans.session.StatutReservationSessionBean;

/**
 * @author Sindy
 */
@WebServlet("/StatutReservationServlet")
public class StatutReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public String libelle;
	public Boolean creation;
	public PrintWriter out;
	public HttpServletRequest request;
	public HttpServletResponse response;

	@EJB
	StatutReservationSessionBean statutReservationSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initialiser(request, response);
		formulaireCreation();

		if (libelle != null) {
			if (!"".equals(libelle)) {
				creation = statutReservationSessionBean.creerStatutReservation(libelle);
				if (creation) {
					List<StatutReservation> statutReservations = statutReservationSessionBean.getAllStatutReservation();
					out.println("<table border='1'>");
					for (StatutReservation statut : statutReservations) {
						out.println("<tr><td>" + statut.getId_statut_reservation() + "</td><td>" + statut.getLibelle() + "</td></tr>");
					}
					out.println("</table>");
				} else {
					out.print("Le statut n'a pas pu être créé <br>");
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
		out.println("<h2>Créer un statut de réservation</h2>");
		out.print("<form method='get' action=''>");
		out.println("Libellé du statut : <input type='text' name='libelleStatut'/> <br>");
		out.print("<input type='submit' name='submit'><br>");
		out.print("</form>");
		libelle = request.getParameter("libelleStatut");
	}

}