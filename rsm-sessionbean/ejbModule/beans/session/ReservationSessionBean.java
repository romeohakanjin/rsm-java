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
	 * Créer une réservation
	 * 
	 * @param reservation
	 * @return
	 */
	public Boolean creerReservation(Reservation reservation) {
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
	 * Récupère toutes les réservations
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Reservation> getAllReservation() {
		String queryString = "FROM Reservation";
		Query query = entityManager.createQuery(queryString);
		return (List<Reservation>) query.getResultList();
	}

	/**
	 * Récupère le nombre de réservation regroupé par état de réservation
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getNbReservationGroupByReservationState() {
		String queryString = "SELECT COUNT(*), er.libelle " + "FROM Reservation AS r "
				+ "JOIN EtatReservation AS er ON er.id_etat_reservation = r.id_etat_reservation "
				+ "GROUP BY er.libelle";
		Query query = entityManager.createQuery(queryString);
		return query.getResultList();
	}

	/**
	 * Récupère les réservations ayant pour statut A venir ou En cours liées à une
	 * annonce
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Reservation> getReservationByAnnonceId(Integer annonceId) {
		String queryString = "FROM Reservation " + "WHERE id_annonce = '" + annonceId + "' ";
		Query query = entityManager.createQuery(queryString);
		return query.getResultList();
	}

	/**
	 * Récupère les réservations faites par un utilisateur ayant pour statut En
	 * attente, A venir ou En cours
	 * 
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Reservation> getReservationByUserId(Integer userId) {
		String queryString = "FROM Reservation " + "WHERE id_utilisateur = '" + userId + "' "
				+ "AND id_etat_reservation <> 3";
		Query query = entityManager.createQuery(queryString);
		return query.getResultList();
	}

	/**
	 * Récupère les réservations faites par un utilisateur ayant pour Terminée
	 * 
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Reservation> getFinishedReservationByUserId(Integer userId) {
		String queryString = "FROM Reservation " + "WHERE id_utilisateur = '" + userId + "' "
				+ "AND id_etat_reservation = 3";
		Query query = entityManager.createQuery(queryString);
		return query.getResultList();
	}

	/**
	 * get all the reservations of a user
	 * 
	 * @param idUtilisateur
	 *            : id of a user
	 * @return List<Reservation> : list of reservations
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getReservationsHotelByUserId(int userId) {
		String queryString = "FROM Reservation as r " + "JOIN Annonce AS a ON a.id_annonce = r.id_annonce "
				+ "JOIN EtatReservation AS er ON er.id_etat_reservation = r.id_etat_reservation "
				+ "WHERE a.id_utilisateur = '" + userId + "'";
		Query query = entityManager.createQuery(queryString);

		return query.getResultList();
	}

	/**
	 * check if the the id user correspond to the id user of the annouce
	 * 
	 * @param id_utilisateur
	 *            : id of the current user
	 * @param idReservation
	 *            : id of the reservation
	 * @return boolean : true if the id correspond or false
	 */
	public boolean isMatchingIdUserReservationAndIdUserAnnonce(int idUser, int idReservation) {
		boolean isMatchingId = false;

		String query = "FROM Reservation as r, Annonce as a " + "WHERE a.id_annonce = r.id_annonce "
				+ "AND a.id_utilisateur = '" + idUser + "' " + "AND r.id_reservation = '" + idReservation + "' ";
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

		String queryString = "SELECT r.id_etat_reservation " + "FROM Reservation as r " + "WHERE r.id_reservation = '"
				+ reservationId + "'";
		Query query2 = entityManager.createQuery(queryString);

		List listStateReservation = query2.getResultList();

		for (int i = 0; i < listStateReservation.size(); i++) {
			idStateReservation = (int) listStateReservation.get(i);
		}

		return idStateReservation;
	}

	/**
	 * Valdiate a reservation in a hotel
	 * 
	 * @param idReservation
	 *            : id of the reservation
	 * @param reservationStateValidationHotelier
	 *            : id of the validation state
	 */
	public void validationReservationHotelier(int idReservation, int reservationStateValidationHotelier,
			int incomingReservationStatusId) {
		try {
			userTransaction.begin();
			String queryString = "UPDATE Reservation AS r " + "SET r.id_etat_reservation = '"
					+ reservationStateValidationHotelier + "'" + "WHERE r.id_reservation = '" + idReservation + "' ";
			Query query = entityManager.createQuery(queryString);
			query.executeUpdate();

			String queryStringUpdate = "UPDATE Reservation AS r " + "SET r.id_statut_reservation = '"
					+ reservationStateValidationHotelier + "'" + "WHERE r.id_reservation = '" + idReservation + "' ";
			Query queryUpdate = entityManager.createQuery(queryStringUpdate);
			queryUpdate.executeUpdate();

			userTransaction.commit();
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
		}
	}

	/**
	 * refusing a reservation in a hotel
	 * 
	 * @param idReservation
	 *            : id of the reservation
	 * @param reservationStateRefusingHotelier
	 *            : : id of the refusing state
	 */
	public void resufingReservationHotelier(int idReservation, int reservationStateRefusingHotelier,
			int finishedReservationStatusId) {
		try {
			userTransaction.begin();
			String queryString = "UPDATE Reservation AS r " + "SET r.id_etat_reservation = '"
					+ reservationStateRefusingHotelier + "' WHERE r.id_reservation = '" + idReservation + "' ";
			Query query = entityManager.createQuery(queryString);
			query.executeUpdate();

			String queryStringUpdate = "UPDATE Reservation AS r " + "SET r.id_statut_reservation = '"
					+ finishedReservationStatusId + "'" + "WHERE r.id_reservation = '" + idReservation + "' ";
			Query queryUpdate = entityManager.createQuery(queryStringUpdate);
			queryUpdate.executeUpdate();

			userTransaction.commit();
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
		}
	}
}