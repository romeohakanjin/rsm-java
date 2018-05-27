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
public class TypeActiviteExterneSessionBean {
	
	@PersistenceContext(unitName = "RsmProjectService")
	EntityManager entityManager;
	
	@Resource
	UserTransaction userTransaction;
	
	/**
	 * Create a external activity type
	 * @param name
	 * @return true/false : if the request has been executed
	 */
	public Boolean createExternalActivityState(String name) {
		try {
			userTransaction.begin();
			TypeActiviteExterne typeActivite = new TypeActiviteExterne();
			typeActivite.setLibelle(name);
			entityManager.persist(typeActivite);
			userTransaction.commit();
			return true;
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * get all the external activities states
	 * @return List<TypeUtilisateur>
	 */
	@SuppressWarnings("unchecked")
	public List<TypeUtilisateur> getTypeActiviteesExterne(){
		String queryString = "FROM TypeActiviteExterne";
		Query query = entityManager.createQuery(queryString);
		return (List<TypeUtilisateur>) query.getResultList();
	}
}
