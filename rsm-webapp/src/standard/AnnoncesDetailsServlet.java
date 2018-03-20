package standard;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.entity.Annonce;
import beans.session.AnnonceSessionBean;

/**
 * Servlet implementation class AnnoncesDetailsServlet
 */
@WebServlet("/AnnoncesDetailsServlet")
public class AnnoncesDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ANNONCE_DETAILS_VIEW = "AnnonceDetails";
	private static final String ANNONCES_LISTE_SERVLET = "AnnoncesServlet";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private String annonceId;
	private String action;

	@EJB
	AnnonceSessionBean annonceSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;

		initialiser();

		switch (action) {

		default:
			try {
				int idAnnonce = Integer.valueOf(annonceId);
				Annonce annonce = annonceSessionBean.getAnnonce(idAnnonce);

				request.setAttribute("annonceDetails", annonce);

				redirectionToView(ANNONCE_DETAILS_VIEW);
			} catch (NumberFormatException exception) {
				redirectionToServlet(ANNONCES_LISTE_SERVLET);
			}
			break;
		}
	}

	/**
	 * Itinaliser les variables
	 * 
	 * @throws IOException
	 */
	private void initialiser() throws IOException {
		this.session = request.getSession();
		this.response.setContentType("text/html");
		this.annonceId = request.getParameter("annonceId");
		this.action = request.getParameter("action");
		if (this.action == null) {
			this.action = "";
		}
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
