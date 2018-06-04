	package beans.session;

import java.sql.Timestamp;
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

import beans.entity.Reservation;

/**
 * @author SLI
 */
@Stateful
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class ReservationSessionBean {

	@PersistenceContext(unitName = "RsmProjectService")
	EntityManager entityManager;

	@Resource
	UserTransaction userTransaction;

	/**
	 * Create a reservation
	 * 
	 * @param reservation : instance of Reservation
	 * @return true/false : if the request has been executed
	 */
	public Boolean createReservation(Reservation reservation) {
		try {
			userTransaction.begin();
			entityManager.persist(reservation);
			userTransaction.commit();
			return true;
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * récupère l'id de réservation en fonction de l'annonce
	 * 
	 * @param annonceId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public int getReservationId(Integer annonceId) {
		
		int reservationId = 0;
		String queryString = "Select id_reservation FROM Reservation WHERE id_annonce = '" + annonceId + "' "
				+ "AND etat_reservation = true";
		Query query = entityManager.createQuery(queryString);
		List listReservation = query.getResultList();
		reservationId = (int) listReservation.get(0);
		return reservationId;
	}

	/**
	 * get all reservations
	 * 
	 * @return List<Reservation> 
	 */
	@SuppressWarnings("unchecked")
	public List<Reservation> getAllReservation() {
		String queryString = "FROM Reservation";
		Query query = entityManager.createQuery(queryString);
		return (List<Reservation>) query.getResultList();
	}

	/**
	 * get the number of reservation group by the state of the reservation
	 * 
	 * @return List<Object[]>
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getNumberOfReservationGroupByReservationState() {
		String queryString = "SELECT COUNT(*), er.libelle " + "FROM Reservation AS r "
				+ "JOIN EtatReservation AS er ON er.id_etat_reservation = r.id_etat_reservation"
				+ " WHERE etat_reservation = true "
				+ "GROUP BY er.libelle";
		Query query = entityManager.createQuery(queryString);
		return query.getResultList();
	}

	/**
	 * get all the reservation with the state incoming or pending of a announcement
	 * 
	 * @return List<Reservation>
	 */
	public List<Reservation> getReservationByAnnonceId(Integer annonceId) {
		String queryString = "FROM Reservation " + "WHERE id_annonce = '" + annonceId + "' "
				+ "AND etat_reservation = true";
		Query query = entityManager.createQuery(queryString);
		return query.getResultList();
	}

	/**
	 * get the reservations with waiting, incoming, pending
	 * 
	 * @param userId
	 * @return  List<Reservation>
	 */
	public List<Reservation> getReservationNotFinishedByUserId(Integer userId) {
		String queryString = "FROM Reservation " + "WHERE id_utilisateur = '" + userId + "' "
				+ "AND etat_reservation = true "
				+ "AND id_etat_reservation <> 3";
		Query query = entityManager.createQuery(queryString);
		return query.getResultList();
	}

	/**
	 * get reservation with finished state
	 * 
	 * @param userId
	 * @return
	 */
	public List<Reservation> getFinishedReservationByUserId(Integer userId) {
		String queryString = "FROM Reservation " + "WHERE id_utilisateur = '" + userId + "' "
				+ "AND etat_reservation = true "
				+ "AND id_etat_reservation = 3";
		Query query = entityManager.createQuery(queryString);
		return query.getResultList();
	}

	/**
	 * get all the reservations for a user with a state 1 or 2
	 * 
	 * @param userId
	 * @return List<Reservation>
	 */
	public List<Reservation> getPendingReservationByUserId(Integer userId) {
		String queryString = "FROM Reservation " + "WHERE id_utilisateur = '" + userId + "' "
				+ "AND etat_reservation = true "
				+ "AND id_etat_reservation = 1 OR id_etat_reservation = 2";
		Query query = entityManager.createQuery(queryString);
		return query.getResultList();
	}

	/**
	 * get all the reservations of a user
	 * 
	 * @param userId
	 * @return List<Object[]> : list of reservations
	 */
	public List<Object[]> getReservationsHotelByUserId(int userId) {
		String queryString = "FROM Reservation as r " + "JOIN Annonce AS a ON a.id_annonce = r.id_annonce "
				+ "JOIN EtatReservation AS er ON er.id_etat_reservation = r.id_etat_reservation "
				+ "WHERE a.id_utilisateur = '" + userId + "' "
				+ "AND etat_reservation = true";
		Query query = entityManager.createQuery(queryString);

		return query.getResultList();
	}

	/**
	 * check if the the id user correspond to the id user of the annouce
	 * 
	 * @param userId
	 *            : id of the current user
	 * @param reservationId
	 *            : id of the reservation
	 * @return boolean : true if the id correspond or false
	 */
	public boolean isMatchingIdUserReservationAndIdUserAnnonce(int userId, int reservationId) {
		boolean isMatchingId = false;

		String query = "FROM Reservation as r, Annonce as a " + "WHERE a.id_annonce = r.id_annonce "
				+ "AND etat_reservation = true "
				+ "AND a.id_utilisateur = '" + userId + "' " + "AND r.id_reservation = '" + reservationId + "' ";
		Query query2 = entityManager.createQuery(query);

		List annonces = query2.getResultList();

		if (annonces.size() != 0) {
			isMatchingId = true;
		}

		return isMatchingId;
	}

	/**
	 * Get the reservation state id
	 * 
	 * @return int : state (id) of the reservation
	 */
	public int getReservationStateId(int reservationId) {
		int idStateReservation = 0;

		String queryString = "SELECT r.id_etat_reservation " 
				+ "FROM Reservation as r " + "WHERE r.id_reservation = '"+ reservationId + "' "
				+ "AND etat_reservation = true";
		Query query2 = entityManager.createQuery(queryString);

		List listStateReservation = query2.getResultList();

		for (int i = 0; i < listStateReservation.size(); i++) {
			idStateReservation = (int) listStateReservation.get(i);
		}

		return idStateReservation;
	}

	/**
	 * Validate a reservation in a hotel
	 * 
	 * @param reservationId
	 *            : id of the reservation
	 * @param reservationStateValidationHotelier
	 *            : id of the validation state
	 */
	public void validationReservationHotelier(int reservationId, int reservationStateValidationHotelier,
			int incomingReservationStatusId) {
		try {
			userTransaction.begin();
			String queryString = "UPDATE Reservation AS r " + "SET r.id_etat_reservation = '"
					+ reservationStateValidationHotelier + "'" + "WHERE r.id_reservation = '" + reservationId + "' ";
			Query query = entityManager.createQuery(queryString);
			query.executeUpdate();

			String queryStringUpdate = "UPDATE Reservation AS r " + "SET r.id_statut_reservation = '"
					+ reservationStateValidationHotelier + "'" + "WHERE r.id_reservation = '" + reservationId + "' ";
			Query queryUpdate = entityManager.createQuery(queryStringUpdate);
			queryUpdate.executeUpdate();

			userTransaction.commit();
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
		}
	}

	/**
	 * refuse a reservation in a hotel
	 * 
	 * @param reservationId
	 *            : id of the reservation
	 * @param reservationStateRefusingHotelier
	 *            : : id of the refusing state
	 */
	public void refusingReservationHotelier(int reservationId, int reservationStateRefusingHotelier,
			int finishedReservationStatusId) {
		try {
			userTransaction.begin();
			String queryString = "UPDATE Reservation AS r " + "SET r.id_etat_reservation = '"
					+ reservationStateRefusingHotelier + "' WHERE r.id_reservation = '" + reservationId + "' ";
			Query query = entityManager.createQuery(queryString);
			query.executeUpdate();

			String queryStringUpdate = "UPDATE Reservation AS r " + "SET r.id_statut_reservation = '"
					+ finishedReservationStatusId + "'" + "WHERE r.id_reservation = '" + reservationId + "' ";
			Query queryUpdate = entityManager.createQuery(queryStringUpdate);
			queryUpdate.executeUpdate();

			userTransaction.commit();
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
		}
	}

	/**
	 * If there is any reservation already coming for this ad
	 * 
	 * @param announcementId
	 * @param timestampBegining
	 * @param timestampEnd
	 * @param true / false : if the date is ok
	 */
	public boolean isOkDateForReservation(int announcementId, Timestamp timestampBegining, Timestamp timestampEnd) {
		boolean isOkDate = false;

		String query = "SELECT date_debut_sejour, date_fin_sejour" + " FROM Reservation "
				+ " WHERE date_debut_sejour >= '" + timestampBegining + "' " + " AND date_fin_sejour <= '"
				+ timestampEnd + "' " + " AND id_annonce = '" + announcementId + "' "
				+ "AND etat_reservation = true";
		Query query2 = entityManager.createQuery(query);
		List reservation = query2.getResultList();

		if (reservation.size() != 0) {
			isOkDate = true;
		}

		return isOkDate;
	}

	/**
	 * get all the reservations that are not passed yet
	 * 
	 * @param userId
	 * @return List<Reservation>
	 */
	public List<Reservation> getReservationNonPasseeByUserId(Integer userId) {
		String queryString = "FROM Reservation r " + "WHERE r.id_etat_reservation = 3 "
				+ "AND etat_reservation = true "
				+ "AND r.id_statut_reservation <> 4 " + "ANd r.id_utilisateur = " + userId;
		Query query = entityManager.createQuery(queryString);
		return query.getResultList();
	}
	
	/**
	 * check if the reservation is waiting for approval for this user
	 * @param userId 
	 * @param reservationId
	 * @return true / false if the reservation is OK
	 */
	public boolean isReservationPending(int userId, String reservationId) {
		boolean isOkReservation = false;
		
		String queryString = "FROM Reservation " + "WHERE id_utilisateur = '" + userId + "' "
				+ "AND etat_reservation = true "
				+ "AND id_reservation = '"+reservationId+"' "
				+ "AND id_etat_reservation = 1 OR id_etat_reservation = 2";
		Query query = entityManager.createQuery(queryString);
		List reservation = query.getResultList();
		
		if (reservation.size() != 0) {
			isOkReservation =  true;
		}
		return isOkReservation;
	}
	
	/**
	 * change the state of a reservation to false
	 * @param reservationId
	 */
	public void deleteReservation(String reservationId) {
		try {
			userTransaction.begin();
			String queryString = "UPDATE Reservation AS r"
					+ " SET etat_reservation = false "
					+ "WHERE r.id_reservation = '" + reservationId + "' ";
			Query query = entityManager.createQuery(queryString);
			query.executeUpdate();

			userTransaction.commit();
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * check if the id user correspond to the id user on the reservation and the state s finished
	 * 
	 * @param userId
	 *            : id of the current user
	 * @param announcementId
	 *            : id of the announcement
	 * @return boolean : true if the id correspond or false
	 */
	public boolean isMatchingIdUserReservationAndIdAnnouncement(int userId, int announcementId) {
		boolean isMatchingId = false;

		String query = "FROM Reservation"
				+ " WHERE id_utilisateur = '"+userId+"'"
				+ " AND id_annonce = '"+announcementId+"'"
				+ " AND id_statut_reservation = 4";
		Query query2 = entityManager.createQuery(query);

		List reservation = query2.getResultList();

		if (reservation.size() != 0) {
			isMatchingId = true;
		}

		return isMatchingId;
	}
}