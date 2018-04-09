package beans.session;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import beans.entity.Commentaire;

/**
 * @author SLI
 */
@Stateful
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class CommentaireSessionBean {

	@PersistenceContext(unitName = "RsmProjectService")
	EntityManager entityManager;

	@Resource
	UserTransaction userTransaction;

	/**
	 * Créer un commentaire
	 * @param commentaire
	 * @return
	 */
	public Boolean creerCommentaire(Commentaire commentaire) {
		try {
			userTransaction.begin();
			entityManager.persist(commentaire);
			userTransaction.commit();
			return true;
		} catch (RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Récupère tous les commentaires
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Commentaire> getAllCommentaire(){
		String queryString = "FROM Commentaire";
		Query query = entityManager.createQuery(queryString);
		return (List<Commentaire>) query.getResultList();
	}
	
	/**
	 * Récupèrer tous les commentaires signalés
	 * @return
	 */
	public List<Object[]> getSignaledComment(){
		String queryString = "SELECT c.id_commentaire, c.id_reservation, u.nom, u.prenom, c.date_creation, c.commentaire "
				+ "FROM Commentaire as c "
				+ "JOIN Utilisateur as u ON c.id_utilisateur = u.id_utilisateur "
				+ "WHERE id_etat_commentaire = 3";
		Query query = entityManager.createQuery(queryString);
		return (List<Object[]>) query.getResultList();
	}
	
	/**
	 * Passe un commentaire signalé à l'état réfusé
	 * @param commentId
	 */
	public boolean refuseComment(Integer commentId) {
		try {
			userTransaction.begin();
			String queryString = "UPDATE Commentaire AS c "
					+ "SET id_etat_commentaire = 2 "
					+ "WHERE c.id_commentaire = "+commentId;
			Query query = entityManager.createQuery(queryString);
			query.executeUpdate();
			userTransaction.commit();
			return true;
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Passe un commentaire signalé à l'état validé
	 * @param commentId
	 */
	public boolean validateComment(Integer commentId) {
		try {
			userTransaction.begin();
			String queryString = "UPDATE Commentaire AS c "
					+ "SET id_etat_commentaire = 1 "
					+ "WHERE c.id_commentaire = "+commentId;
			Query query = entityManager.createQuery(queryString);
			query.executeUpdate();
			userTransaction.commit();
			return true;
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Récupère un commentaire par son id_commentaire
	 * @param commentId
	 * @return
	 */
	public Commentaire getCommentById(Integer commentId) {
		String queryString = "FROM Commentaire WHERE id_commentaire = "+commentId;
		Query query = entityManager.createQuery(queryString);
		return (Commentaire) query.getSingleResult();
	}
}
