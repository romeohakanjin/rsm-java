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
import beans.entity.Reservation;
import beans.session.AnnonceSessionBean;
import beans.session.ReservationSessionBean;

/**
 * @author Sindy
 */
@WebServlet("/ReservationServlet")
public class ReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public String libelle;
	public Integer idAnnonce;
	public String annonceParameter;
	public Boolean creation;
	public PrintWriter out;
	public HttpServletRequest request;
	public HttpServletResponse response;

	@EJB
	ReservationSessionBean reservationSessionBean;
	@EJB
	AnnonceSessionBean annonceSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initialiser(request, response);
		formulaireCreation();

		if (annonceParameter != null) {
			if (!"".equals(annonceParameter)) {
				idAnnonce = Integer.parseInt(annonceParameter);
				Reservation reservation = new Reservation();
				reservation.setId_annonce(idAnnonce);
				creation = reservationSessionBean.creerReservation(reservation);
				if (creation) {
					List<Reservation> reservations = reservationSessionBean.getAllReservation();
					out.println("<table border='1'>");
					for (Reservation reserv : reservations) {
						out.println("<tr><td>" + reserv.getId_annonce() + "</td><td>" + reserv.getCapatice_max() + "</td></tr>");
					}
					out.println("</table>");
				} else {
					out.print("L'état n'a pas pu être créé <br>");
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
	 * Affiche le formulaire de création
	 * Récupère les informations saisis par l'utilisateur
	 */
	public void formulaireCreation() {
		List<Annonce> annonces = annonceSessionBean.getAllAnnonce();
		out.println("<h2>Créer une réservation</h2>");
		out.print("<form method='get' action=''>");
		out.println("<select name='annonce'>");
		for (Annonce annonce : annonces) {
			out.println("<option value=" + annonce.getId_annonce() + ">" + annonce.getTitre() + "</option>");
		}
		out.println("</select>");
		out.println("Prix : <input type='text' name='prix'/> <br>");
		out.print("<input type='submit' name='submit'><br>");
		out.print("</form>");
		annonceParameter = request.getParameter("annonce");
	}

}


