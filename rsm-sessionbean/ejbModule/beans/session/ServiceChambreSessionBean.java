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
import beans.entity.ServiceChambre;

/**
 * @author RHA
 */
@Stateful
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class ServiceChambreSessionBean {

	@PersistenceContext(unitName = "RsmProjectService")
	EntityManager entityManager;

	@Resource
	UserTransaction userTransaction;

	/**
	 * Create a service
	 * 
	 * @param serviceChambre : instance of ServiceChambre
	 * @return true/false : if the request has been executed
	 */
	public Boolean createServiceChambre(ServiceChambre serviceChambre) {
		try {
			userTransaction.begin();
			entityManager.persist(serviceChambre);
			userTransaction.commit();
			return true;
		} catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SystemException
				| NotSupportedException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Get all the rooms services
	 * 
	 * @return
	 */
	public List<ServiceChambre> getRoomsServices() {
		String queryString = "FROM ServiceChambre";
		Query query = entityManager.createQuery(queryString);
		return (List<ServiceChambre>) query.getResultList();
	}

	/**
	 * Get all the room services for one ad by is id
	 * 
	 * @param annonceId
	 * @return
	 */
	public List<ServiceChambre> getRoomServices(int annonceId) {
		String queryString = "FROM ServiceChambre" + " WHERE id_annonce = '" + annonceId + "' ";
		Query query = entityManager.createQuery(queryString);
		return (List<ServiceChambre>) query.getResultList();
	}

	/**
	 * get a room service by is id
	 * 
	 * @param serviceId
	 * @return
	 */
	public ServiceChambre getRoomService(Integer serviceId) {
		String queryString = "FROM ServiceChambre WHERE id_service_chambre = " + serviceId;
		Query query = entityManager.createQuery(queryString);
		return (ServiceChambre) query.getSingleResult();
	}
	
	/**
	 * Looking if the ad id matches the id of the ad id from the service
	 * @param idAnnouncement :  id ad
	 * @param idService : service id
	 * @return true/false : if the id user match the id user of the service
	 */
	public boolean isMatchingIdAdAndIdAdService(int idAnnouncement, int idService) {
		boolean isMatchingId = false;
		String queryString = "FROM ServiceChambre WHERE id_annonce = '"+idAnnouncement+"' "
				+ "AND id_service_chambre = '"+idService+"' ";
		Query query = entityManager.createQuery(queryString);
		List<ServiceChambre> serviceChambre = query.getResultList();

		if (serviceChambre.size() != 0) {
			isMatchingId = true;
		}

		return isMatchingId;
	}
	
	/**
	 * Delete a service
	 * @param idService
	 */
	public void deleteService(int idService) {
		try {
			userTransaction.begin();
			String query = "DELETE FROM ServiceChambre " 
			+ "WHERE id_service_chambre = '" + idService + "' ";
			Query result = entityManager.createQuery(query);
			result.executeUpdate();
			userTransaction.commit();
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
		}
	}

	/**
	 * check if the service is existing by is name for a announcement
	 * @param name : service name 
	 * @param announcementId : announcementId
	 * @return True if it exist or False if he dosn't
	 */
	public boolean isExistingService(String name, int announcementId) {
		boolean isExistingId = false;
		String queryString = "FROM ServiceChambre WHERE nom LIKE '%"+name+"%' "
				+ "AND id_annonce = '"+announcementId+"' ";
		Query query = entityManager.createQuery(queryString);
		List<ServiceChambre> serviceChambre = query.getResultList();

		if (serviceChambre.size() != 0) {
			isExistingId = true;
		}

		return isExistingId;
	}

	/**
	 * Update a existing service with the proposition modification that has been validate
	 * @param propositionModificationAnnonce
	 */
	public void updateRoomService(PropositionModificationAnnonce propositionModificationAnnonce) {
		try {
			userTransaction.begin();
			int adId = propositionModificationAnnonce.getId_annonce();
			String serviceName = propositionModificationAnnonce.getNom();
			int quantity = propositionModificationAnnonce.getQuantite();
			
			String query = "UPDATE ServiceChambre SET nom = '"+serviceName+"',  quantite = '"+quantity+"' "
					+ "WHERE id_annonce = '" + adId + "' ";

			Query query1 = entityManager.createQuery(query);
			query1.executeUpdate();
			userTransaction.commit();
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException exception) {

			exception.printStackTrace();
		}
	}
}
