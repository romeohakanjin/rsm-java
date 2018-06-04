package beans.session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import beans.entity.Annonce;
import beans.entity.Commentaire;

/**
 * @author SLI
 */
@Stateful
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class AnnonceSessionBean {
	
	@Resource(lookup="java:jboss/datasources/rsmProject")
    DataSource dataSource;
	
	@PersistenceContext(unitName = "RsmProjectService")
	EntityManager entityManager;

	@Resource
	UserTransaction userTransaction;

	/**
	 * Create a announcement
	 * 
	 * @param announcement : instance of Annonce
	 * @return true/false : if the request has been executed
	 */
	public Boolean createAnnouncement(Annonce announcement) {
		try {
			userTransaction.begin();
			entityManager.persist(announcement);
			userTransaction.commit();
			return true;
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * get the number of announcement for a hotelier
	 * @param idUser
	 * @return long : numberOfAnnounce
	 */
	public long getNumberOfAnnouncementForHotelier(int idUser) {
		long numberOfAnnouncement = 0;

		String query = "SELECT COUNT(id_annonce) FROM Annonce WHERE id_utilisateur = '" + idUser + "' AND actif = true";
		Query query2 = entityManager.createQuery(query);

		List announcementList = query2.getResultList();

		for (int i = 0; i < announcementList.size(); i++) {
			numberOfAnnouncement = (long) announcementList.get(i);
		}
		return numberOfAnnouncement;
	}

	/**
	 * get the number of announce for each etat_reservation
	 * 
	 * @param idUser : id of the user
	 * @return List<Object[]>
	 */
public List<Object[]> getNumberOfAnnouncementPerReservationStatus(int idUser) {
		String query = "SELECT COUNT(er.id_etat_reservation), er.libelle "
				+ "FROM Annonce AS a, Reservation AS r, EtatReservation AS er " + "WHERE a.id_annonce = r.id_annonce "
				+ "AND r.id_etat_reservation = er.id_etat_reservation " + "AND a.id_utilisateur = '" + idUser + "'"
				+ " AND actif = true "
				+ "GROUP BY er.libelle";
		Query query2 = entityManager.createQuery(query);

		List<Object[]> numberOfAnnouncement = query2.getResultList();

		return numberOfAnnouncement;
	}

	/**
	 * get all the announcement
	 * 
	 * @return List<Annonce>: list of Announcement
	 */
	@SuppressWarnings("unchecked")
	public List<Annonce> getAllAnnouncement() {
		String queryString = "FROM Annonce WHERE actif = TRUE";
		Query query = entityManager.createQuery(queryString);
		List<Annonce> announcementList = (List<Annonce>) query.getResultList();
		
		return announcementList;
	}
	
	/**
	 * get a announcement by is id
	 * @param announcementId
	 * @return Annonce : the announcement
	 */
	public Annonce getAnnouncementById(Integer announcementId) {
		String queryString = "FROM Annonce WHERE actif = true AND id_annonce = "+announcementId;
		Query query = entityManager.createQuery(queryString);
		Annonce announcement = (Annonce) query.getResultList().get(0);
		
		return announcement;
	}
	
	/**
	 * Get all the announcement that have the keywords
	 * @param destination
	 * @keywords
	 * @keywordsList
	 * @return List<Integer>
	 */
	public List<Integer> getAnnouncementSearched(String destination, String keywords, String[] keywordsList){
		String queryString = "SELECT a.id_annonce "
				+ "FROM Annonce as a "
				+ "JOIN Utilisateur as u ON a.id_utilisateur = u.id_utilisateur "
				+ "WHERE a.actif = TRUE "
				+ "AND u.ville LIKE '%"+ destination +"%' "
				+ "AND (a.description LIKE '%"+keywords+"%' ";
		if(keywordsList != null) {
			for(int i=0; i<keywordsList.length; i++) {
				queryString = queryString+"OR a.description LIKE '%"+keywordsList[i]+"%' ";
			}
		}
		queryString = queryString+")";			
		Query query = entityManager.createQuery(queryString);
		return (List<Integer>) query.getResultList();
	}

	/**
	 * get the id of a user
	 * 
	 * @param identifiant : email
	 * @return int : id of the user
	 */
	public int getUserId(String identifiant) {
		int idUser = 0;

		String query = "SELECT u.id_utilisateur FROM Utilisateur AS u " + "WHERE u.mail = '" + identifiant + "'";
		Query query2 = entityManager.createQuery(query);

		List announcementList = query2.getResultList();

		for (int i = 0; i < announcementList.size(); i++) {
			idUser = (int) announcementList.get(i);
		}
		return idUser;
	}
	
	/**
	 * get a announcement
	 * @param announcemenId
	 * @return Annonce : the announcement
	 */
	public Annonce getAnnouncement(int announcemenId) {
		Annonce announcement = new Annonce();
		String query = "FROM Annonce AS a " + "WHERE a.id_annonce = '" + announcemenId + "'";
		Query query2 = entityManager.createQuery(query);
		List announcementList = query2.getResultList();

		for (int i = 0; i < announcementList.size(); i++) {
			announcement = (Annonce) announcementList.get(0);
		}
		return announcement;
	}
	
	/**
	 * Get active reservation from a user
	 * @param idUser
	 * @return List<Annonce>
	 */
	public List<Annonce> getAllUserAnnouncement(int idUser) {	
		String query = "FROM Annonce " + "WHERE id_utilisateur = '"+ idUser + "' AND actif = true";
		Query query2 = entityManager.createQuery(query);
		List<Annonce> announcementList = query2.getResultList();
		return announcementList;
	}

	/**
	 * Check if the id_utilisateur of the announcement correspond to the actual userId
	 * 
	 * @param idUser
	 * @param idAnnonce
	 * @return true if the id_utilisateur match
	 */
	public boolean isMatchingUserIdAndAnnouncementId(int idUser, int idAnnonce) {
		boolean isMatchingId = false;

		String query = "SELECT id_annonce FROM Annonce WHERE id_utilisateur = '" + idUser
				+ "' AND id_annonce = '" + idAnnonce + "' AND actif = true ";
		Query query2 = entityManager.createQuery(query);

		List announcementList = query2.getResultList();

		if (announcementList.size() != 0) {
			isMatchingId = true;
		}

		return isMatchingId;
	}

	/**
	 * Update a announcement
	 * 
	 * @param announcement
	 *            : The object Annonce representing a announcement to edit
	 */
	public void updateAnnouncement(Annonce announcement){
		int announcementId = announcement.getId_annonce();
		String title = announcement.getTitre();
		String description = announcement.getDescription();
		int maxCapacity = announcement.getCapacite_max();
		try {
			userTransaction.begin();
//			Connection connection = dataSource.getConnection();
//			String sql = "UPDATE Annonce AS a SET titre = ?, description = ?, capacite_max = ?, WHERE a.id_annonce = ?"; 
//			PreparedStatement statement = connection.prepareStatement(sql); 
//			//en spécifiant bien les types SQL cibles 
//			statement.setString(1, title); 
//			statement.setString(2, description); 
//			statement.setInt(3, maxCapacity); 
//			statement.setInt(3, announcementId);
//			statement.executeUpdate(); 
			
			String query = "UPDATE Annonce AS a " + "SET titre = '" + title + "', " + "description = '" + description
					+ "', " + "capacite_max = '" + maxCapacity + "' " + "WHERE a.id_annonce = '" + announcementId + "' ";

			Query query1 = entityManager.createQuery(query);
			query1.executeUpdate();
			userTransaction.commit();
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException exception) {

			exception.printStackTrace();
		}
	}

	/**
	 * Delete a announcement
	 * 
	 * @param announcementId
	 */
	public void deleteAnnouncement(int announcementId) {
		try {
			userTransaction.begin();

			String query = "UPDATE Annonce AS a SET actif = false WHERE id_annonce = '" + announcementId + "' ";

			Query query1 = entityManager.createQuery(query);
			query1.executeUpdate();
			userTransaction.commit();
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException exception) {

			exception.printStackTrace();
		}

	}
	
	/**
	 * get the number of announcement
	 * @return List<Object>
	 */
	public List<Object> getNumberOfAnnouncement() {
		String queryString = "SELECT COUNT(*) FROM Annonce";
		Query query = entityManager.createQuery(queryString);
		List<Object> announcementList = query.getResultList();
		
		return announcementList;
	}

	/**
	 * get all the comments from a announcement
	 * 
	 * @param announcementId
	 * @return List<Commentaire> : comments of a announcement
	 */
	public List<Commentaire> getAnnouncementComments(int announcementId) {
		String queryString = "SELECT c.id_commentaire, c.commentaire, c.id_reservation, c.id_etat_commentaire "
				+ "FROM Commentaire AS c " + "JOIN Reservation AS r ON c.id_reservation = r.id_reservation "
				+ "JOIN Annonce as a ON r.id_annonce = a.id_annonce " + "WHERE a.id_annonce = '" + announcementId + "' ";
		Query query = entityManager.createQuery(queryString);
		List<Object[]> res = query.getResultList();
		List<Commentaire> comments = new ArrayList<Commentaire>();

		Iterator<Object[]> it = res.iterator();
		while (it.hasNext()) {
			Object[] line = it.next();
			Commentaire comment = new Commentaire();
			comment.setId_commentaire((int) line[0]);
			comment.setCommentaire((String) line[1]);
			comment.setId_reservation((int) line[2]);
			comment.setId_etat_commentaire((int) line[3]);
			comments.add(comment);
		}

		return comments;
	}

	/**
	 * Check if the id_utilisateur of the comment correspond to the actual commentId
	 * 
	 * @param userId
	 * @param commentId
	 * @return true/false : if the id of the comment match the list of announcement
	 */
	public boolean isMatchingUserIdAndCommentId(int userId, int commentId) {
		boolean isMatchingId = false;
		String queryString = "SELECT c.id_commentaire " + "FROM Commentaire AS c "
				+ "JOIN Reservation AS r ON c.id_reservation = r.id_reservation "
				+ "JOIN Annonce as a ON r.id_annonce = a.id_annonce " + "WHERE a.id_utilisateur = '" + userId
				+ "' " + "AND c.id_commentaire = '" + commentId + "' ";
		Query query = entityManager.createQuery(queryString);
		List<Commentaire> commentList = query.getResultList();

		if (commentList.size() != 0) {
			isMatchingId = true;
		}

		return isMatchingId;
	}

	/**
	 * report a comment
	 * 
	 * @param commentId
	 *            : the id_commentaire of the comment
	 */
	public void reportComment(int commentId) {
		try {
			userTransaction.begin();

			String query = "UPDATE Commentaire AS c " + "SET c.signaler = true " + "WHERE c.id_commentaire = '"
					+ commentId + "' ";

			Query query1 = entityManager.createQuery(query);
			query1.executeUpdate();

			userTransaction.commit();
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * ignore a comment
	 * 
	 * @param commentId
	 *            : the id_commentaire of the comment
	 */
	public void ignoreComment(int commentId) {
		try {
			userTransaction.begin();

			String query = "UPDATE Commentaire AS c " + "SET c.signaler = false " + "WHERE c.id_commentaire = '"
					+ commentId + "' ";

			Query query1 = entityManager.createQuery(query);
			query1.executeUpdate();

			userTransaction.commit();
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException exception) {
			exception.printStackTrace();
		}
	}
	
	/**
	 * if the ad is active or not
	 * 
	 * @param announcementId
	 * @return true/false : if the announcement is active
	 */
	public boolean isAnnouncementActivated(int announcementId) {
		boolean isActive = false;

		String queryString = "SELECT a.actif " + "FROM Annonce AS a " + "WHERE a.actif = true";
		Query query = entityManager.createQuery(queryString);
		List<Annonce> announcement = query.getResultList();

		if (announcement.size() != 0) {
			isActive = true;
		}

		return isActive;
	}
	
	/**
	 * get the active announcement of a user where the reservation have the validate state 
	 * 		and other than terminated status
	 * @param idUser
	 * @return List<Annonce>
	 */
	public List<Annonce> getAnnouncementByUserIdAndValidatedReservation(Integer idUser){
		String queryString = "SELECT a.actif, a.capacite_max, a.date_creation, a.date_modification, "
				+ "a.description, a.id_annonce, a.id_utilisateur, a.prix_nuit, a.titre "
				+ "FROM Annonce a "
				+ "JOIN Reservation r ON a.id_annonce = r.id_annonce "
				+ "WHERE a.actif = true "
				+ "AND r.id_etat_reservation = 3 "
				+ "AND r.id_statut_reservation <> 4 "
				+ "AND a.id_utilisateur = "+idUser;
		Query query = entityManager.createQuery(queryString);
		List<Annonce> announcementList = query.getResultList();
		return announcementList;
	}

	/**
	 * Get the price for one night of a announcement by his id
	 * @param annoncementId
	 * @return double: pricePerNight
	 */
	public double getAnnouncementPricePerNight(int annoncementId) {
		String queryString = "SELECT prix_nuit FROM Annonce WHERE id_annonce = '"+annoncementId+"' ";
		Query query = entityManager.createQuery(queryString);
		Double price =  (Double) query.getSingleResult();
		return price;
	}
}