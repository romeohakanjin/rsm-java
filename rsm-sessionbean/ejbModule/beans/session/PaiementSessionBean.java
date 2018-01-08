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

    /**
     * Récupère les paiements
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<Paiement> getAllPaiements(){
    	String queryString = "FROM Paiement";
    	Query query = entityManager.createQuery(queryString);
    	return (List<Paiement>) query.getResultList();
    }
}
