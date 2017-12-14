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

import beans.entity.Utilisateur;

/**
 * @author Sindy
 */
@Stateful
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class UtilisateurSessionBean {

	@PersistenceContext(unitName = "AnnonceService")
	EntityManager entityManager;

	@Resource
	UserTransaction userTransaction;

	/**
	 * Créer un utilisateur
	 * @param libelle
	 * @return
	 */
	public Boolean creerUtilisateur(Utilisateur utilisateur) {
		try {
			userTransaction.begin();
			entityManager.persist(utilisateur);
			userTransaction.commit();
			return true;
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Récupère tous les utilisateurs
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Utilisateur> getAllUtilisateur(){
		String queryString = "FROM Utilisateur";
		Query query = entityManager.createQuery(queryString);
		return (List<Utilisateur>) query.getResultList();
	}
}
