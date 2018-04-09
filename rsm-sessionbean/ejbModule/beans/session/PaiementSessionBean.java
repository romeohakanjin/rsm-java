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

import beans.entity.Paiement;

/**
 * @author SLI
 */
@Stateful
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class PaiementSessionBean {

    @PersistenceContext(unitName = "RsmProjectService")
    EntityManager entityManager;
    
    @Resource
    UserTransaction userTransaction;
    
    /**
     * Créer un paiement
     * @param paiement
     * @return
     */
    public Boolean creerPaeiment(Paiement paiement) {
    	try {
    		userTransaction.begin();
    		entityManager.persist(paiement);
    		userTransaction.commit();
    		return true;
    	}catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
    @SuppressWarnings("unchecked")
	public List<Object[]> getHistoriquePaiement(){
    	String queryString = "SELECT u.nom, u.prenom, p.id_reservation, r.prix, p.date_paiement "
    			+ "FROM Paiement as p "
    			+ "JOIN Reservation as r ON r.id_reservation = p.id_reservation "
    			+ "JOIN Utilisateur as u ON u.id_utilisateur = r.id_utilisateur";
    	Query query = entityManager.createQuery(queryString);
    	return query.getResultList();
    }
}
