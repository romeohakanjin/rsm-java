package standard;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.entity.Annonce;
import beans.session.AnnonceSessionBean;

/**
 * Servlet implementation class AnnoncesServlet
 */
@WebServlet("/AnnoncesServlet")
public class AnnoncesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ANNONCES_PAGE = "Annonces";
	private HttpServletRequest request;
	private HttpServletResponse response;

	@EJB
	AnnonceSessionBean annonceSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;
		initialize();

		List<Annonce> annonces = annonceSessionBean.getAllAnnonce();
		this.request.setAttribute("annonces", annonces);

		redirectionToView(ANNONCES_PAGE);
	}

	private void initialize() {
		this.response.setContentType("text/html");
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
		RequestDispatcher dispatcher = request.getRequestDispatcher(view + ".jsp");
		dispatcher.include(request, response);
	}
}