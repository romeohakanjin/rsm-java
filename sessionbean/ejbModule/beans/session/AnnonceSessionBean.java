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

import beans.entity.Annonce;
/**
 * @author Sindy
 */
@Stateful
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class AnnonceSessionBean {

	@PersistenceContext(unitName = "AnnonceService")
	EntityManager entityManager;

	@Resource
	UserTransaction userTransaction;
	
	/**
	 * Créer une annonce
	 * @param Annonce
	 * @return
	 */
	public Boolean creerAnnonce(Annonce annonce) {
		try {
			userTransaction.begin();
			entityManager.persist(annonce);
			userTransaction.commit();
			return true;
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Récupère toutes les annonces
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Annonce> getAllAnnonce(){
		String queryString = "FROM Annonce";
		Query query = entityManager.createQuery(queryString);
		return (List<Annonce>) query.getResultList();
	}

}
