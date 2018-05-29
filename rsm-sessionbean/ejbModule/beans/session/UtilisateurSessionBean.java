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

import beans.entity.Annonce;
import beans.entity.Hotel;
import beans.entity.Utilisateur;

/**
 * @author SLI
 */
@Stateful
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class UtilisateurSessionBean {

	@PersistenceContext(unitName = "RsmProjectService")
	EntityManager entityManager;

	@Resource
	UserTransaction userTransaction;

	/**
	 * Create user
	 * 
	 * @param user : instance of Utilisateur
	 * @return true/false : if the request has been executed
	 */
	public Boolean createUser(Utilisateur user) {
		try {
			userTransaction.begin();
			entityManager.persist(user);
			userTransaction.commit();
			return true;
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * delete a user
	 * @param user : instance of Utilisateur
	 * @return true/false : if the request has been executed
	 */
	public Boolean deleteUser(Utilisateur user) {
		try {
			Integer userId = user.getId_utilisateur();
			userTransaction.begin();
			String queryString =	"UPDATE Utilisateur AS u "
					+ "SET actif = false "
					+ "WHERE u.id_utilisateur = '" + userId + "' ";
			Query query = entityManager.createQuery(queryString);
			query.executeUpdate();
			userTransaction.commit();
			return true;
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * get all users
	 * 
	 * @return List<Utilisateur> 
	 */
	@SuppressWarnings("unchecked")
	public List<Utilisateur> getUsers() {
		String queryString = "FROM Utilisateur WHERE actif = TRUE";
		Query query = entityManager.createQuery(queryString);
		return (List<Utilisateur>) query.getResultList();
	}
	
	/**
	 * get users and users types
	 * 
	 * @return List<Object>
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getUsersAndUsersTypes() {
		String queryString = "FROM Utilisateur AS u "
				+ "JOIN TypeUtilisateur AS tu ON u.id_type_utilisateur = tu.id_type_utilisateur "
				+ "WHERE u.actif = TRUE";
		Query query = entityManager.createQuery(queryString);
		return (List<Object>) query.getResultList();
	}

	/**
	 * check if the connection  id for a user
	 * 
	 * @param mail
	 *            :
	 * @param motDePasse
	 *            :
	 * 
	 * @return boolean :
	 */
	public boolean isIdentificationValid(String mail, String password) {
		
		boolean isIdentificationValid = false;
		String query = "SELECT u.mail FROM Utilisateur u WHERE mail = '" + mail + "' AND mot_de_passe='" + password
				+ "' AND actif = true";
		Query query2 = entityManager.createQuery(query);
		@SuppressWarnings("rawtypes")
		List listUser = query2.getResultList();

		if (listUser.size() != 0) {
			isIdentificationValid = true;
		}
		return isIdentificationValid;
	}

	/**
	 * check the mail of a user
	 * 
	 * @param mail
	 * @return true/false : if the mail exist
	 */
	public boolean isExistingUser(String mail) {
		
		boolean isExitingUser = false;

		String query = "SELECT u.mail FROM Utilisateur u WHERE mail = '" + mail + "' "
				+ "AND u.actif = TRUE ";
		Query query2 = entityManager.createQuery(query);
		@SuppressWarnings("rawtypes")
		List listUser = query2.getResultList();

		if (listUser.size() != 0) {
			isExitingUser = true;
		}
		return isExitingUser;
	}

	/**
	 * Check if the hotel already exist
	 * 
	 * @param nomHotel
	 */
	public boolean checkExistingHotel(String nomHotel) {
		boolean isExistingHotel = false;

		String query = "SELECT h.id_hotel FROM Hotel AS h WHERE id_hotel= h.id_hotel AND h.nom_hotel = '" + nomHotel + "'";
		Query query2 = entityManager.createQuery(query);

		@SuppressWarnings("rawtypes")
		List listHotel = query2.getResultList();

		if (listHotel.size() != 0) {
			isExistingHotel = true;
		}

		return isExistingHotel;
	}
	
	/**
	 * get id hotel by is name
	 * @param hotelName
	 * @return int : id of the hotel
	 */
	//TODO : deplacer dans hotelSessionBean
	public int getIdHotel(String hotelName) {
		int idHotel = 0;
		String query = "SELECT h.id_hotel FROM Hotel AS h WHERE id_hotel= h.id_hotel AND h.nom_hotel = '" + hotelName + "'";
		Query query2 = entityManager.createQuery(query);

		@SuppressWarnings("rawtypes")
		List listHotel = query2.getResultList();

		for (int i = 0; i < listHotel.size(); i++) {
			idHotel = (int) listHotel.get(i);
		}
		return idHotel;
	}

	/**
	 * Create a hotel and return is id
	 * @param hotelName
	 * @return int : id of the hotel created
	 */
	public int createHotel(String hotelName) {
		int idHotel = 0;
		Hotel hotel = new Hotel();
		hotel.setNom_hotel(hotelName);

		try {
			userTransaction.begin();
			entityManager.persist(hotel);
			userTransaction.commit();
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
		}

		String query = "SELECT h.id_hotel FROM Hotel AS h WHERE id_hotel= h.id_hotel AND h.nom_hotel = '" + hotelName + "'";
		Query query2 = entityManager.createQuery(query);

		@SuppressWarnings("rawtypes")
		List listInfoHotel = query2.getResultList();

		for (int i = 0; i < listInfoHotel.size(); i++) {
			idHotel = (int) listInfoHotel.get(i);
		}
		return idHotel;
	}
	
	/**
	 * Get the id_type_utilisateur from a user
	 * @param id
	 * @param password
	 * @return
	 */
	//TODO : deplacer dans typeUtilisateurSessionBean
	public int getIdTypeUtilisateur(String id, String password) {
		
		int idTypeUtilisateur = 3;
		String query = "SELECT u.id_type_utilisateur FROM Utilisateur u WHERE mail = '" + id + "' AND mot_de_passe='" + password
				+ "'";
		Query query2 = entityManager.createQuery(query);

		@SuppressWarnings("rawtypes")
		List listUser = query2.getResultList();

		for (int i = 0; i < listUser.size(); i++) {
			idTypeUtilisateur = (int) listUser.get(i);
		}
		return idTypeUtilisateur;
	}
	/**
	 * check if the user id exist
	 * @param userId
	 * @return true / fale : if the id exist
	 */
	public boolean isExistingUserId(int userId) {
		
		boolean isExistingUserId = false;
		String query = "SELECT id_utilisateur FROM Utilisateur WHERE id_utilisateur = '" + userId + "'";
		Query query2 = entityManager.createQuery(query);
		
		List utilisateurs = query2.getResultList();
		if (utilisateurs.size() != 0) {
			isExistingUserId = true;
		}
		return isExistingUserId;
	}
	/**
	 * Verification of the existence of a user id
	 * @param idUser
 	 * @return
 	 */
	public boolean isMatchingIdUser(int id_utilisateur) {
 			 		
		boolean isMatchingId = false;
 		String query = "SELECT id_utilisateur FROM Utilisateur WHERE id_utilisateur = '" + id_utilisateur + "'";
 		Query query2 = entityManager.createQuery(query);
 		
 		List utilisateurs = query2.getResultList();
 		if (utilisateurs.size() != 0) {
			isMatchingId = true;
 		}
		return isMatchingId;
 	}
	
	/**
	 * get a user
	 * @param idUtilisateur
	 * @return Utilisateur
	 */
	public Utilisateur getUser(int idUtilisateur){
		String queryString = "FROM Utilisateur AS a " + "WHERE a.id_utilisateur = '"+idUtilisateur+"' ";
		Query query = entityManager.createQuery(queryString);
		Utilisateur utilisateur = (Utilisateur) query.getSingleResult();
		return utilisateur;
	}
	
	/**
	 * get user id by is mail
	 * @param id
	 * @return int : user id
	 */
	public int getIdUtilisateur(String id) {
		int idUtilisateur = 0;
		String query = "SELECT u.id_utilisateur FROM Utilisateur AS u " + "WHERE u.mail = '" + id + "' AND actif = TRUE";
		Query query2 = entityManager.createQuery(query);
		List userList = query2.getResultList();
		idUtilisateur = (int) userList.get(0);
		return idUtilisateur;
	}
	
	/**
	 * get users
	 * 
	 * @param userId
	 * @return Utilisateur
	 */
	public Utilisateur getActiveUsers(int userId) {
		
		Utilisateur user = new Utilisateur();
		String query = "FROM Utilisateur AS u WHERE u.id_utilisateur = '" + userId + "' AND actif = TRUE";
		Query query2 = entityManager.createQuery(query);

		user = (Utilisateur) query2.getSingleResult();

		return user;
	}
	
	/**
	 * Update a user
	 * @param userId
	 */
	public void updateUser(Utilisateur user) {
		
		int userId = user.getId_utilisateur();
		String lastName = user.getNom();
		String firstName = user.getPrenom();
		String mail = user.getMail();
		String mobile = user.getMobile();
		String address = user.getAdresse();
		String town = user.getVille();
		String postalCode = user.getCode_postal();
		try {
			userTransaction.begin();
			String query = 	"UPDATE Utilisateur AS u " +
							"SET nom = '" + lastName + "', " + 
							"prenom = '" + firstName + "', " +
							"mail = '" + mail + "', " +
							"mobile = '" + mobile + "', " + 
							"adresse = '" + address + "', " +
							"ville = '" + town + "', " + 
							"code_postal = '" + postalCode + "' " +
							"WHERE u.id_utilisateur = '" + userId + "' ";

			Query query1 = entityManager.createQuery(query);
			query1.executeUpdate();
			userTransaction.commit();
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException exception) {
			exception.printStackTrace();
		}
	}
	
	/**
	 * get the number of user group by user types
	 * @return List<Object[]>
	 */
	public List<Object[]> getUserNumberGroupByUserType() {
		String queryString = "SELECT COUNT(*), tu.libelle "
				+ "FROM Utilisateur AS u "
				+ "JOIN TypeUtilisateur AS tu ON u.id_type_utilisateur = tu.id_type_utilisateur "
				+ "GROUP BY tu.libelle";
		Query query = entityManager.createQuery(queryString);
		return query.getResultList();
	}
	
	/**
	 * get user by is id
	 * @param userId
	 * @return Utilisateur
	 */
	public Utilisateur getUserById(Integer userId) {
		String queryString = "FROM Utilisateur AS a " + "WHERE a.id_utilisateur = '" + userId + "'";
		Query query = entityManager.createQuery(queryString);
		Utilisateur user = null;
		for (int i = 0; i < query.getResultList().size(); i++) {
			user = (Utilisateur) query.getResultList().get(i);
		}
		return user;
	}
	
	/**
	 * add point for a standard user
	 * @param userId : id of the user
	 */
	public void addPointForAUser(Integer userId) {
		try {
			userTransaction.begin();
			String query = "UPDATE Utilisateur AS u " +
							"SET point_bonus = point_bonus + 10 " + 
							"WHERE u.id_utilisateur = '" + userId + "' ";
			Query query1 = entityManager.createQuery(query);
			query1.executeUpdate();
			userTransaction.commit();
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException exception) {
			exception.printStackTrace();
		}
	}
	
	/**
	 * get user points
	 * @param idUser
	 * @return int : number of points
	 */
	public int getUserPoints(int idUser) {
		int numberOfPoints = 0;
		
		String queryString = "SELECT point_bonus "
				+ "FROM Utilisateur "
				+ "WHERE id_utilisateur = '"+idUser+"' ";
		Query query = entityManager.createQuery(queryString);
		numberOfPoints = (int) query.getSingleResult();
		
		return numberOfPoints;
	}
}
