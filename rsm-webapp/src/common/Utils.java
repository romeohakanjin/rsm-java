package common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public abstract class Utils {

	/**
	 * Redirection to a view
	 * 
	 * @param String
	 *            : the view name
	 * @throws ServletException
	 * @throws IOException
	 */
	public void redirectionToView(String view, ServletRequest request, ServletResponse response) throws ServletException, IOException {
		request.getServletContext().getRequestDispatcher("/" + view + ".jsp").forward(request, response);
	}

	/**
	 * Feed request attribute
	 * 
	 * @param variable
	 * @param message
	 */
	public void setVariableToView(String variable, String message, ServletRequest request) {
		request.setAttribute(variable, message);
	}
}
