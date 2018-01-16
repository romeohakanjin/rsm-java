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
import beans.entity.TypeActiviteExterne;
import beans.entity.TypeUtilisateur;

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
	public List<TypeActiviteExterne> getActiviteesExterne() {
		String queryString = "FROM ActiviteExterne";
		Query query = entityManager.createQuery(queryString);
		return (List<TypeActiviteExterne>) query.getResultList();
	}
}
