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

import beans.entity.PropositionModificationAnnonce;

/**
 * 
 * @author RHA
 *
 */
@Stateful
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class PropositionModificationSessionBean {

	@PersistenceContext(unitName = "RsmProjectService")
	EntityManager entityManager;

	@Resource
	UserTransaction userTransaction;

	/**
	 * Create a modification proposition for an ad
	 * 
	 * @param proposition
	 * @return
	 */
	public Boolean createModificationProposition(PropositionModificationAnnonce proposition) {
		try {
			userTransaction.begin();
			entityManager.persist(proposition);
			userTransaction.commit();
			return true;
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * get all the modifications propositions
	 * 
	 * @return List<PropositionModificationAnnonce>
	 */
	public List<PropositionModificationAnnonce> getModificationsPorpositions() {
		String queryString = "FROM PropositionModificationAnnonce";
		Query query = entityManager.createQuery(queryString);
		return (List<PropositionModificationAnnonce>) query.getResultList();
	}

	/**
	 * get a modification proposition by is id
	 * 
	 * @param propositionId
	 * @return
	 */
	public PropositionModificationAnnonce getModificationsPropositionById(int propositionId) {
		PropositionModificationAnnonce annonce = new PropositionModificationAnnonce();

		String queryString = "FROM PropositionModificationAnnonce " + "WHERE id_proposition_modif_annonce = '"
				+ propositionId + "'";
		Query query = entityManager.createQuery(queryString);

		return (PropositionModificationAnnonce) query.getResultList().get(0);
	}

	/**
	 * get a modification proposition by ad id
	 * 
	 * @param userId
	 * @return List<PropositionModificationAnnonce>
	 */
	public List<PropositionModificationAnnonce> getModificationsPropositionByAdId(int adId) {
		String queryString = "FROM PropositionModificationAnnonce WHERE id_annonce = '"+adId+"' ";
		Query query = entityManager.createQuery(queryString);

		return (List<PropositionModificationAnnonce>) query.getResultList().get(0);
	}

	/**
	 * get a modification proposition by user id
	 * 
	 * @param userId
	 * @return List<PropositionModificationAnnonce>
	 */
	public List<PropositionModificationAnnonce> getModificationsPropositionByUserId(int userId) {
		String queryString = "FROM PropositionModificationAnnonce WHERE id_utilisateur = '"+userId+"' ";
		Query query = entityManager.createQuery(queryString);

		return (List<PropositionModificationAnnonce>) query.getResultList().get(0);
	}

	/**
	 * delete a modification proposition
	 * 
	 * @param modificationPropositionId
	 */
	public void deleteModificationProposition(String modificationPropositionId) {
		try {
			userTransaction.begin();
			String query = "DELETE FROM PropositionModificationAnnonce " + "WHERE id_proposition_modif_annonce = '" + modificationPropositionId + "' ";
			Query result = entityManager.createQuery(query);
			result.executeUpdate();
			userTransaction.commit();
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
		}

	}

	/**
	 * check if the modification proposition is existing
	 * @param modificationPropositionId
	 * @return true / false if the proposition exist
	 */
	public boolean isExistingModificationProposition(String modificationPropositionId) {
		boolean isOkModificationProposition = false;
		
		String queryString = "FROM PropositionModificationAnnonce " + "WHERE id_proposition_modif_annonce = '" + modificationPropositionId + "' ";
		Query query = entityManager.createQuery(queryString);
		List propositionModification = query.getResultList();
		
		if (propositionModification.size() != 0) {
			isOkModificationProposition =  true;
		}
		return isOkModificationProposition;
	}
}
