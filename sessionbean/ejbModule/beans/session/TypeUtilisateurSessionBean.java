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
	 * Créer un type d'utilisateur
	 * @param libelle
	 * @return
	 */
	public Boolean creerTypeUtilisateur(String libelle) {
		try {
			userTransaction.begin();
			TypeUtilisateur typeUtilisateur = new TypeUtilisateur();
			typeUtilisateur.setLibelle(libelle);
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
	 * Récupère tous les types d'utilisateurs
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TypeUtilisateur> getAllTypeUtilisateur(){
		String queryString = "FROM TypeUtilisateur";
		Query query = entityManager.createQuery(queryString);
		return (List<TypeUtilisateur>) query.getResultList();
	}
}