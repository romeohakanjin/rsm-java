package session;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Deconnection")
public class Deconnection extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private static final String HOME_PAGE = "Home";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		initialize(request, response);
		deconnection();
	}
	
	/**
	 * Initialize the values
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 */
	private void initialize(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		session = request.getSession();
	}
	
	/**
	 * delete all param in http session from the user
	 * @throws ServletException
	 * @throws IOException
	 */
	private void deconnection() throws ServletException, IOException {
		//delete params in http session
		session.invalidate();
		//redirection home
		redirectionToView(HOME_PAGE);
	}
	
	/**
	 * Redirection to a view
	 * @throws ServletException
	 * @throws IOException
	 */
	private void redirectionToView(String view) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(view+".jsp");
		dispatcher.include(request, response);
	}
}
