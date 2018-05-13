package admin;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.entity.Commentaire;
import beans.session.CommentaireSessionBean;

/**
 * @author SLI
 */
@WebServlet("/CommentManagementServlet")
public class CommentManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String COMMENT_MANAGEMENT = "AdminCommentManagement";
	private static final String REFUSE_COMMENT = "RefuseComment";
	private static final String VALIDATE_COMMENT = "ValidateComment";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private String action;
	private String commentId;

	@EJB
	CommentaireSessionBean commentaireSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;

		initialize();

		switch (this.action) {
		case REFUSE_COMMENT:
			if (commentId != null || !commentId.equals("")) {
				Integer idComment = Integer.valueOf(commentId);
				Commentaire commentToRefuse = commentaireSessionBean.getCommentById(idComment);
				if (commentToRefuse != null) {
					Boolean isRefuse = refuseCommentById(idComment);
					if (isRefuse) {
						getAllComment();
						setVariableToView("alert-success", "Commentaire supprimé");
						redirectionToView(COMMENT_MANAGEMENT);
					} else {
						setVariableToView("alert-danger", "La suppression de ce commentaire n'a pas pu être effectuée");
					}
				}
			}
			break;
		case VALIDATE_COMMENT:
			if (commentId != null || !commentId.equals("")) {
				Integer idComment = Integer.valueOf(commentId);
				Commentaire commentToValidate = commentaireSessionBean.getCommentById(idComment);
				if (commentToValidate != null) {
					Boolean isValidate = validateCommentById(idComment);
					if (isValidate) {
						getAllComment();
						setVariableToView("alert-success", "Commentaire validé");
						redirectionToView(COMMENT_MANAGEMENT);
					} else {
						setVariableToView("alert-warning", "La validation de ce commentaire n'a pas pu être effectuée");
					}
				}
			}
			break;
		default:
			getAllComment();
			redirectionToView(COMMENT_MANAGEMENT);
			break;
		}
	}

	/**
	 * Initialise les variables
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		this.session = request.getSession();
		this.response.setContentType("text/html");
		this.action = request.getParameter("action");
		if (this.action == null) {
			this.action = "";
		} else {
			this.commentId = request.getParameter("commentId");
		}
	}

	/**
	 * Feed request attribute
	 * 
	 * @param variable
	 * @param message
	 */
	private void setVariableToView(String variable, String message) {
		request.setAttribute(variable, message);
	}

	/**
	 * Set the httpSession
	 * 
	 * @param login
	 * @param password
	 */
	protected void httpSession(String login, String password) {
		session.setAttribute("login", login);
		session.setAttribute("password", password);
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

	/**
	 * Récupère tous les utilisateurs
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	private void getAllComment() throws ServletException, IOException {
		List<Object[]> commentList = commentaireSessionBean.getSignaledComment();
		this.request.setAttribute("commentList", commentList);
	}

	/**
	 * Passe un commentaire signalé à l'état réfusé
	 * 
	 * @param commentId
	 */
	private Boolean refuseCommentById(Integer commentId) {
		return commentaireSessionBean.refuseComment(commentId);
	}

	/**
	 * Passe un commentaire signalé à l'état validé
	 * 
	 * @param commentId
	 */
	private Boolean validateCommentById(Integer commentId) {
		return commentaireSessionBean.validateComment(commentId);
	}
}
