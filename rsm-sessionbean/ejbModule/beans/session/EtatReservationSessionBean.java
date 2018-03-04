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

import beans.entity.EtatReservation;

/**
 * @author SLI
 */
@Stateful
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class EtatReservationSessionBean {

	@PersistenceContext(unitName = "RsmProjectService")
	EntityManager entityManager;

	@Resource
	UserTransaction userTransaction;

	/**
	 * Créer un etat de réservation
	 * @param libelle
	 * @return
	 */
	public Boolean creerEtatReservation(String libelle) {
		try {
			userTransaction.begin();
			EtatReservation etat = new EtatReservation();
			etat.setLibelle(libelle);
			entityManager.persist(etat);
			userTransaction.commit();
			return true;
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Récupère tous les états de réservation
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EtatReservation> getAllEtatReservation(){
		String queryString = "FROM EtatReservation";
		Query query = entityManager.createQuery(queryString);
		return (List<EtatReservation>) query.getResultList();
	}
}
