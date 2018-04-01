package standard;

import java.io.IOException;
import java.util.ArrayList;
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
	private static final String SEARCH_ANNOUNCEMENT = "Search";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String action;
	private String destinationParameter;
	private String keywordParameter;
	private String[] keywords;
	private List<Annonce> annonces;

	@EJB
	AnnonceSessionBean annonceSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;
		initialize();
		
		switch(action) {
		case SEARCH_ANNOUNCEMENT:
			if(!"".equals(keywordParameter)) {
				keywords = keywordParameter.split(" ");
			}
			List<Integer> annonceObj = annonceSessionBean.getAnnouncementSearched(destinationParameter, keywordParameter, keywords);
			for(int i=0; i<annonceObj.size(); i++) {
				Annonce annonce = annonceSessionBean.getAnnonceById(annonceObj.get(i));
				if(annonce != null) {
					annonces.add(annonce);
				}
			}
			break;
		default: 
			annonces = annonceSessionBean.getAllAnnonce();
			break;
		}
		
		this.request.setAttribute("annonces", annonces);
		redirectionToView(ANNONCES_PAGE);	
	}

	/**
	 * Initialise les paramètres
	 */
	private void initialize() {
		this.response.setContentType("text/html");
		this.annonces = new ArrayList<Annonce>();
		
		if(request.getParameter("action") != null && !"".equals(request.getParameter("action"))) {
			this.action = request.getParameter("action");
		}else {
			this.action = "";
		}
		
		if(request.getParameter("destination") != null && !"".equals(request.getParameter("destination"))) {
			this.destinationParameter = request.getParameter("destination").trim();
		}else {
			this.destinationParameter = "";
		}
		
		if(request.getParameter("keyWord") != null && !"".equals(request.getParameter("keyWord"))) {
			this.keywordParameter = request.getParameter("keyWord").trim();
		}else {
			this.keywordParameter = "";
		}
	}

	/**
	 * Redirection to a view
	 * @param String : the view name
	 * @throws ServletException
	 * @throws IOException
	 */
	private void redirectionToView(String view) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(view + ".jsp");
		dispatcher.include(request, response);
	}
}