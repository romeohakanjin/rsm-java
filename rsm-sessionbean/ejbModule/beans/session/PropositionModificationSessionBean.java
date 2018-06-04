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
	 * @param proposition : instance of PropositionModificationAnnonce
	 * @return true/false : if the request has been executed
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
	public List<PropositionModificationAnnonce> getModificationsPropositions() {
		String queryString = "FROM PropositionModificationAnnonce";
		Query query = entityManager.createQuery(queryString);
		return (List<PropositionModificationAnnonce>) query.getResultList();
	}

	/**
	 * get a modification proposition by is id
	 * 
	 * @param propositionId
	 * @return PropositionModificationAnnonce
	 */
	public PropositionModificationAnnonce getModificationsPropositionById(int propositionId) {
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
	public List<Object[]> getModificationsPropositionByUserId(int userId) {
		String queryString = "SELECT p.id_proposition_modif_annonce, p.id_annonce, p.id_utilisateur, p.nom, p.quantite, p.date_proposition "
				+ "FROM PropositionModificationAnnonce as p, Annonce as a "
				+ "WHERE p.id_annonce = a.id_annonce "
				+ "AND a.id_utilisateur = '"+userId+"' "
				+ "AND a.actif = true";
		Query query = entityManager.createQuery(queryString);

		return (List<Object[]>) query.getResultList();
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
	
	/**
	 * check if the id of the announcement and the proposition id match for a announcement
	 * @param annonceId : id of the announcement
	 * @param idModificationProposition
	 * @return
	 */
	public boolean isMatchinIdAnnoucementAndIdProposition(int annonceId, int idModificationProposition) {
		boolean isMatchingId = false;
		String query = "FROM PropositionModificationAnnonce "
				+ "WHERE id_annonce = '"+annonceId+"' "
				+ "AND id_proposition_modif_annonce =  '"+idModificationProposition+"' ";
		Query query2 = entityManager.createQuery(query);

		List annonces = query2.getResultList();

		if (annonces.size() != 0) {
			isMatchingId = true;
		}

		return isMatchingId;
	}
}
