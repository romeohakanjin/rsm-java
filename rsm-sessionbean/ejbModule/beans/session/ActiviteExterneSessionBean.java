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

import beans.entity.ActiviteExterne;
import beans.entity.Annonce;

/**
 * 
 * @author SLI
 *
 */
@Stateful
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class ActiviteExterneSessionBean {

	@PersistenceContext(unitName = "RsmProjectService")
	EntityManager entityManager;

	@Resource
	UserTransaction userTransaction;

	/**
	 * Create a external activity
	 * 
	 * @param activity : instance of ActiviteExterne
	 * @return true/false : if the request has been executed
	 */
	public Boolean createExternalActivity(ActiviteExterne activity) {
		try {
			userTransaction.begin();
			entityManager.persist(activity);
			userTransaction.commit();
			return true;
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * get all the external activities
	 * @return List<ActiviteExterne>  : list of external activities
	 */
	public List<ActiviteExterne> getAllActiviteesExterne() {
		String queryString = "FROM ActiviteExterne";
		Query query = entityManager.createQuery(queryString);
		List<ActiviteExterne> listExternalActivities = (List<ActiviteExterne>) query.getResultList();
		
		return listExternalActivities;
	}
	
	/**
	 * get a external activity
	 * @param idExternalActivity
	 * @return ActiviteExterne : the external activity
	 */
	public ActiviteExterne getActiviteExterne(int idExternalActivity) {
		String queryString = "FROM ActiviteExterne AS a " + "WHERE a.id_activite_externe = '" + idExternalActivity + "'";
		Query query = entityManager.createQuery(queryString);
		ActiviteExterne announcement = (ActiviteExterne) query.getResultList().get(0);
		
		return announcement;
	}
	
	/**
	 * delete a external activity
	 * @param idExternalActivity
	 */
	public void deleteActiviteExterne(int idExternalActivity) {
		try {
			userTransaction.begin();
			String query =	"DELETE FROM ActiviteExterne "
					+ "WHERE id_activite_externe = '" + idExternalActivity + "' ";
			Query result = entityManager.createQuery(query);
			result.executeUpdate();
			userTransaction.commit();
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
		}
		
	}
}
