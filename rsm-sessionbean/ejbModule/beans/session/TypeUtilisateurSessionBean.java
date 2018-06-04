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

import beans.entity.TypeUtilisateur;

/**
 * @author SLI
 */
@Stateful
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class TypeUtilisateurSessionBean {

	@PersistenceContext(unitName = "RsmProjectService")
	EntityManager entityManager;

	@Resource
	UserTransaction userTransaction;

	/**
	 * create a user type
	 * @param name
	 * @return true/false : if the request has been executed
	 */
	public Boolean createUserType(String name) {
		try {
			userTransaction.begin();
			TypeUtilisateur typeUtilisateur = new TypeUtilisateur();
			typeUtilisateur.setLibelle(name);
			entityManager.persist(typeUtilisateur);
			userTransaction.commit();
			return true;
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * get all user states
	 * @return List<TypeUtilisateur>
	 */
	@SuppressWarnings("unchecked")
	public List<TypeUtilisateur> getUserStates(){
		String queryString = "FROM TypeUtilisateur";
		Query query = entityManager.createQuery(queryString);
		return (List<TypeUtilisateur>) query.getResultList();
	}
	
	/**
	 * get the user state
	 * @param stateId
	 * @return ist<TypeUtilisateur>
	 */
	public List<TypeUtilisateur> getUserStateById(Integer stateId) {
		String queryString = "FROM TypeUtilisateur WHERE id_type_utilisateur = "+stateId;
		Query query = entityManager.createQuery(queryString);
		return (List<TypeUtilisateur>) query.getResultList();
	}
}