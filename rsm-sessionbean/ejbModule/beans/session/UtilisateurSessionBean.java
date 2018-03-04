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
	 * Créer un utilisateur
	 * 
	 * @param libelle
	 * @return
	 */
	public Boolean creerUtilisateur(Utilisateur utilisateur) {
		try {
			userTransaction.begin();
			entityManager.persist(utilisateur);
			userTransaction.commit();
			return true;
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
			return false;
		}
	}
	
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
	 * Récupère tous les utilisateurs
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Utilisateur> getAllUtilisateur() {
		String queryString = "FROM Utilisateur";
		Query query = entityManager.createQuery(queryString);
		return (List<Utilisateur>) query.getResultList();
	}
	
	/**
	 * Récupère tous les utilisateurs avec leurs types d'utilisateurs
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getAllUtilisateurWithUserType() {
		String queryString = "FROM Utilisateur AS u "
				+ "JOIN TypeUtilisateur AS tu ON u.id_type_utilisateur = tu.id_type_utilisateur";
		Query query = entityManager.createQuery(queryString);
		return (List<Object>) query.getResultList();
	}

	/**
	 * Vérifie les identifiants de la connexion d'un utilisateur
	 * 
	 * @param mail
	 *            :
	 * @param motDePasse
	 *            :
	 * 
	 * @return boolean :
	 */
	public boolean isIdentificationValid(String mail, String motDePasse) {
		boolean isIdentificationValid = false;

		String query = "SELECT u.mail FROM Utilisateur u WHERE mail = '" + mail + "' AND motDePasse='" + motDePasse
				+ "' AND actif = true";
		Query query2 = entityManager.createQuery(query);

		List listUser = query2.getResultList();

		if (listUser.size() != 0) {
			isIdentificationValid = true;
		}

		return isIdentificationValid;
	}

	/**
	 * Vérifie l'email d'un utilisateur
	 * 
	 * @param mail
	 *            :
	 * 
	 * @return boolean :
	 */
	public boolean isExitingUser(String mail) {
		boolean isExitingUser = false;

		String query = "SELECT u.mail FROM Utilisateur u WHERE mail = '" + mail + "'";
		Query query2 = entityManager.createQuery(query);

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

		String query = "SELECT h.id_hotel FROM Hotel AS h WHERE id_hotel= h.id_hotel AND h.nom = '" + nomHotel + "'";
		Query query2 = entityManager.createQuery(query);

		List listHotel = query2.getResultList();

		if (listHotel.size() != 0) {
			isExistingHotel = true;
		}

		return isExistingHotel;
	}

	public int getIdHotel(String nomHotel) {
		int idHotel = 0;
		String query = "SELECT h.id_hotel FROM Hotel AS h WHERE id_hotel= h.id_hotel AND h.nom = '" + nomHotel + "'";
		Query query2 = entityManager.createQuery(query);

		List listHotel = query2.getResultList();

		for (int i = 0; i < listHotel.size(); i++) {
			idHotel = (int) listHotel.get(i);
		}
		return idHotel;
	}

	public int createHotel(String nomHotel, String adresseHotel, String codePostalHotel, String villeHotel) {
		int idHotel = 0;

		Hotel hotel = new Hotel();
		hotel.setNom(nomHotel);
		hotel.setAdresse(adresseHotel);
		hotel.setCode_postal(codePostalHotel);
		hotel.setVille(villeHotel);

		try {
			userTransaction.begin();
			entityManager.persist(hotel);
			userTransaction.commit();
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
		}

		String query = "SELECT h.id_hotel FROM Hotel AS h WHERE id_hotel= h.id_hotel AND h.nom = '" + nomHotel + "'";
		Query query2 = entityManager.createQuery(query);

		List listInfoHotel = query2.getResultList();

		for (int i = 0; i < listInfoHotel.size(); i++) {
			idHotel = (int) listInfoHotel.get(i);
		}

		return idHotel;
	}
	
	/**
	 * Get the id_type_utilisateur from a user
	 * @param identifiant
	 * @param motDePasse
	 * @return
	 */
	public int getIdTypeUtilisateur(String identifiant, String motDePasse) {
		int idTypeUtilisateur = 3;
		String query = "SELECT u.id_type_utilisateur FROM Utilisateur u WHERE mail = '" + identifiant + "' AND motDePasse='" + motDePasse
				+ "'";
		Query query2 = entityManager.createQuery(query);

		List listUser = query2.getResultList();

		for (int i = 0; i < listUser.size(); i++) {
			idTypeUtilisateur = (int) listUser.get(i);
		}

		return idTypeUtilisateur;
	}
	
	/**
	 * Récupère le nombre d'utilisateur
	 * regroupé par type d'utilisateur
	 * @return
	 */
	public List<Object[]> getNbUserGroupByUserType() {
		String queryString = "SELECT COUNT(*), tu.libelle "
				+ "FROM Utilisateur AS u "
				+ "JOIN TypeUtilisateur AS tu ON u.id_type_utilisateur = tu.id_type_utilisateur "
				+ "GROUP BY tu.libelle";
		Query query = entityManager.createQuery(queryString);
		return query.getResultList();
	}
	
	/**
	 * Récupère un utilisateur avec son id
	 * @param userId
	 * @return
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
}
