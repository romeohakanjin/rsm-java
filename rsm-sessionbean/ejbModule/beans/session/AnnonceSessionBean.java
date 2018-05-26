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
	 * Cr�er une annonce
	 * 
	 * @param Annonce
	 * @return
	 */
	public Boolean creerAnnonce(Annonce annonce) {
		try {
			userTransaction.begin();
			entityManager.persist(annonce);
			userTransaction.commit();
			return true;
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * get the number of announce for a hotelier
	 * 
	 * @return
	 */
	public long getNumberOfAnnounceForHotelier(int idUser) {
		long numberOfAnnounce = 0;

		String query = "SELECT COUNT(id_annonce) FROM Annonce WHERE id_utilisateur = '" + idUser + "' AND actif = true";
		Query query2 = entityManager.createQuery(query);

		List listAnnonce = query2.getResultList();

		for (int i = 0; i < listAnnonce.size(); i++) {
			numberOfAnnounce = (long) listAnnonce.get(i);
		}
		return numberOfAnnounce;
	}

	/**
	 * get the number of announce for each etat_reservation
	 * 
	 * @param idUser
	 *            : id of the user
	 */
	public List<Object[]> getNumberOfAnnouncePerReservationStatus(int idUser) {
		String query = "SELECT COUNT(er.id_etat_reservation), er.libelle "
				+ "FROM Annonce AS a, Reservation AS r, EtatReservation AS er " + "WHERE a.id_annonce = r.id_annonce "
				+ "AND r.id_etat_reservation = er.id_etat_reservation " + "AND a.id_utilisateur = '" + idUser + "'"
				+ " AND actif = true "
				+ "GROUP BY er.libelle";
		Query query2 = entityManager.createQuery(query);

		List<Object[]> numberOfAnnounce = query2.getResultList();

		return numberOfAnnounce;
	}

	/**
	 * R�cup�re toutes les annonces
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Annonce> getAllAnnonce() {
		String queryString = "FROM Annonce WHERE actif = TRUE";
		Query query = entityManager.createQuery(queryString);
		return (List<Annonce>) query.getResultList();
	}
	
	/**
	 * R�cup�re l'annonce associ� � l'id
	 * @param idAnnonce
	 * @return
	 */
	public Annonce getAnnonceById(Integer idAnnonce) {
		String queryString = "FROM Annonce WHERE actif = true AND id_annonce = "+idAnnonce;
		Query query = entityManager.createQuery(queryString);
		return (Annonce) query.getResultList().get(0);
	}
	
	/**
	 * R�cup�re les annonces en fonction des champs saisis
	 * par l'utilisateur
	 * @param destination
	 * @return
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
	 * R�cup�re l'id de la d'un utilisateur
	 * 
	 * @param identifiant
	 *            : email
	 * @return int : id of the user
	 */
	public int getIdUtilisateur(String identifiant) {
		int idUtilisateur = 0;

		String query = "SELECT u.id_utilisateur FROM Utilisateur AS u " + "WHERE u.mail = '" + identifiant + "'";
		Query query2 = entityManager.createQuery(query);

		List listAnnonce = query2.getResultList();

		for (int i = 0; i < listAnnonce.size(); i++) {
			idUtilisateur = (int) listAnnonce.get(i);
		}
		return idUtilisateur;
	}
	
	public Annonce getAnnonce(int idAnnonce) {
		Annonce annonce = new Annonce();
		String query = "FROM Annonce AS a " + "WHERE a.id_annonce = '" + idAnnonce + "'";
		Query query2 = entityManager.createQuery(query);
		List listAnnonce = query2.getResultList();

		for (int i = 0; i < listAnnonce.size(); i++) {
			annonce = (Annonce) listAnnonce.get(0);
		}
		return annonce;
	}
	
	/**
	 * R�cup�re les annonces actives post�es pas un utilisateur
	 * @param idUser
	 * @return
	 */
	public List<Annonce> getAllAnnonceUtilisateur(int idUser) {	
		String query = "FROM Annonce " + "WHERE id_utilisateur = '"+ idUser + "' AND actif = true";
		Query query2 = entityManager.createQuery(query);
		List<Annonce> listAnnonce = query2.getResultList();
		return listAnnonce;
	}

	/**
	 * Check if the id_utilisateur of the annonce correspond to the actual userId
	 * 
	 * @param idUser
	 * @param idAnnonce
	 * @return true if the id_utilisateur match
	 */
	public boolean isMatchingIdUserAndIdUserAnnonce(int idUser, int idAnnonce) {
		boolean isMatchingId = false;

		String query = "SELECT id_annonce FROM Annonce WHERE id_utilisateur = '" + idUser
				+ "' AND id_annonce = '" + idAnnonce + "' AND actif = true ";
		Query query2 = entityManager.createQuery(query);

		List annonces = query2.getResultList();

		if (annonces.size() != 0) {
			isMatchingId = true;
		}

		return isMatchingId;
	}

	/**
	 * Update a annonce
	 * 
	 * @param annonce
	 *            : The object Annonce representing a annonce to edit
	 * @throws SQLException 
	 */
	public void updateAnnonce(Annonce annonce){
		int id_annonce = annonce.getId_annonce();
		String titre = annonce.getTitre();
		String description = annonce.getDescription();
		int capacite_max = annonce.getCapacite_max();
		try {
			Connection connection = dataSource.getConnection();
			String sql = "UPDATE Annonce AS a SET titre = ?, description = ?, capacite_max = ?, WHERE a.id_annonce = ?"; 
			PreparedStatement statement = connection.prepareStatement(sql); 
			//en spécifiant bien les types SQL cibles 
			statement.setString(1, titre); 
			statement.setString(2, description); 
			statement.setInt(3, capacite_max); 
			statement.setInt(3, id_annonce);
			statement.executeUpdate(); 
//			
//			String query = "UPDATE Annonce AS a " + "SET titre = '" + titre + "', " + "description = '" + description
//					+ "', " + "capacite_max = '" + capacite_max + "' " + "WHERE a.id_annonce = '" + id_annonce + "' ";
//
//			Query query1 = entityManager.createQuery(query);
//			query1.executeUpdate();
			connection.commit();
		} catch (SecurityException | IllegalStateException | SQLException exception) {

			exception.printStackTrace();
		}
	}

	/**
	 * Delete a annonce
	 * 
	 * @param idAnnonce
	 */
	public void deleteAnnonce(int idAnnonce) {
		try {
			userTransaction.begin();

			String query = "UPDATE Annonce AS a SET actif = false WHERE id_annonce = '" + idAnnonce + "' ";

			Query query1 = entityManager.createQuery(query);
			query1.executeUpdate();
			userTransaction.commit();
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException exception) {

			exception.printStackTrace();
		}

	}
	
	/**
	 * R�cup�re le nombre d'annonce
	 * @return
	 */
	public List<Object> getNbAnnounce() {
		String queryString = "SELECT COUNT(*) FROM Annonce";
		Query query = entityManager.createQuery(queryString);

		return query.getResultList();
	}

	/**
	 * get all the comments from a annonce
	 * 
	 * @param idAnnonce
	 * @return
	 * @return
	 */
	public List<Commentaire> getCommentsFromAnnonce(int idAnnonce) {
		String queryString = "SELECT c.id_commentaire, c.commentaire, c.id_reservation, c.id_etat_commentaire "
				+ "FROM Commentaire AS c " + "JOIN Reservation AS r ON c.id_reservation = r.id_reservation "
				+ "JOIN Annonce as a ON r.id_annonce = a.id_annonce " + "WHERE a.id_annonce = '" + idAnnonce + "' ";
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
	 * @param id_utilisateur
	 * @param commentId
	 * @return true if the id of the comment match the list of annonce
	 */
	public boolean isMatchingIdUserAndIdComment(int id_utilisateur, int commentId) {
		boolean isMatchingId = false;
		String queryString = "SELECT c.id_commentaire " + "FROM Commentaire AS c "
				+ "JOIN Reservation AS r ON c.id_reservation = r.id_reservation "
				+ "JOIN Annonce as a ON r.id_annonce = a.id_annonce " + "WHERE a.id_utilisateur = '" + id_utilisateur
				+ "' " + "AND c.id_commentaire = '" + commentId + "' ";
		Query query = entityManager.createQuery(queryString);
		List<Commentaire> commentaire = query.getResultList();

		if (commentaire.size() != 0) {
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
	 * @param annonceIdInt
	 *            : ad id
	 * @return true/false : if the ad is active
	 */
	//TODO: pas de traitement dans le session bean
	public boolean isActiveAd(int annonceIdInt) {
		boolean isActive = false;

		String queryString = "SELECT a.actif " + "FROM Annonce AS a " + "WHERE a.actif = true";
		Query query = entityManager.createQuery(queryString);
		List<Annonce> annonce = query.getResultList();

		if (annonce.size() != 0) {
			isActive = true;
		}

		return isActive;
	}
	
	/**
	 * R�cup�re les annonces actives d'un utilisateur
	 * pour lesquels il existe des r�servations
	 * � l'�tat Valid� et au statut autre que Termin�
	 * @param idUser
	 * @return
	 */
	public List<Annonce> getAnnonceByUserWithReservationValide(Integer idUser){
		String queryString = "SELECT a.actif, a.capacite_max, a.date_creation, a.date_modification, "
				+ "a.description, a.id_annonce, a.id_utilisateur, a.prix_nuit, a.titre "
				+ "FROM Annonce a "
				+ "JOIN Reservation r ON a.id_annonce = r.id_annonce "
				+ "WHERE a.actif = true "
				+ "AND r.id_etat_reservation = 3 "
				+ "AND r.id_statut_reservation <> 4 "
				+ "AND a.id_utilisateur = "+idUser;
		Query query = entityManager.createQuery(queryString);
		return query.getResultList();
	}

	/**
	 * Get the price for one night of a ad by his id
	 * @param annonceIdInt2
	 * @return double: pricePerNight
	 */
	public double getPricePerNightAd(int annonceIdInt) {
		String queryString = "SELECT prix_nuit FROM Annonce WHERE id_annonce = '"+annonceIdInt+"' ";
		Query query = entityManager.createQuery(queryString);
		Double prix =  (Double) query.getSingleResult();
		return prix;
	}
}