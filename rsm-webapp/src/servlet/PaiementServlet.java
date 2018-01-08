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

import beans.entity.Paiement;
import beans.session.PaiementSessionBean;

/**
 * @author RHA
 */
@WebServlet("/PaiementServlet")
public class PaiementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private PrintWriter out;
    private HttpServletRequest request;
    private HttpServletResponse response;
    
    @EJB
    PaiementSessionBean paiementSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		initialiser(request, response);
		
		List<Paiement> paiements = paiementSessionBean.getAllPaiements();
		for(Paiement paiement : paiements) {
			out.println("Paiement : "+paiement.getId_paiement()+"<br>");
			out.println(paiement.getId_reservation());
			out.println(paiement.getDate_paiement());
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
}
