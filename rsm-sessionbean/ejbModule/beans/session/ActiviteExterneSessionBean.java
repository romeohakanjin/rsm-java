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
	 * Créer une activité externe
	 * 
	 * @param libelle
	 * @return
	 */
	public Boolean creerActiviteExterne(ActiviteExterne activite) {
		try {
			userTransaction.begin();
			entityManager.persist(activite);
			userTransaction.commit();
			return true;
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Récupère toutes les activitées externes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ActiviteExterne> getAllActiviteesExterne() {
		String queryString = "FROM ActiviteExterne";
		Query query = entityManager.createQuery(queryString);
		return (List<ActiviteExterne>) query.getResultList();
	}
	
	/**
	 * Récupère une activité externe
	 * @param idAnnonce
	 * @return
	 */
	public ActiviteExterne getActiviteExterne(int idActiviteExterne) {
		Annonce annonce = new Annonce();

		String queryString = "FROM ActiviteExterne AS a " + "WHERE a.id_activite_externe = '" + idActiviteExterne + "'";
		Query query = entityManager.createQuery(queryString);

		return (ActiviteExterne) query.getResultList().get(0);
	}
	
	/**
	 * Supprime une activité externe
	 * @param idAnnonce
	 */
	public void deleteActiviteExterne(int idActiviteExterne) {
		try {
			userTransaction.begin();
			String query =	"DELETE FROM ActiviteExterne "
					+ "WHERE id_activite_externe = '" + idActiviteExterne + "' ";
			Query result = entityManager.createQuery(query);
			result.executeUpdate();
			userTransaction.commit();
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
		}
		
	}
}
