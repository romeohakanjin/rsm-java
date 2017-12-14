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
 * @author Sindy
 */
@Stateful
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class CommentaireSessionBean {

	@PersistenceContext(unitName = "AnnonceService")
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
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
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
}
