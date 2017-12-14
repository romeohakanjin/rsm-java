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

import beans.entity.StatutReservation;

/**
 * @author Sindy
 */
@Stateful
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class StatutReservationSessionBean {

	@PersistenceContext(unitName = "AnnonceService")
	EntityManager entityManager;

	@Resource
	UserTransaction userTransaction;

	/**
	 * Cr�er un statut de r�servation
	 * @param libelle
	 * @return
	 */
	public Boolean creerStatutReservation(String libelle) {
		try {
			userTransaction.begin();
			StatutReservation statut = new StatutReservation();
			statut.setLibelle(libelle);
			entityManager.persist(statut);
			userTransaction.commit();
			return true;
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * R�cup�re tous les status de r�servation
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<StatutReservation> getAllStatutReservation(){
		String queryString = "FROM StatutReservation";
		Query query = entityManager.createQuery(queryString);
		return (List<StatutReservation>) query.getResultList();
	}
}
