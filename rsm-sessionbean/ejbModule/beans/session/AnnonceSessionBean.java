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

/**
 * @author SLI
 */
@Stateful
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class AnnonceSessionBean {

	@PersistenceContext(unitName = "RsmProjectService")
	EntityManager entityManager;

	@Resource
	UserTransaction userTransaction;

	/**
	 * Créer une annonce
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
	 * Récupère toutes les annonces
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Annonce> getAllAnnonce() {
		String queryString = "FROM Annonce";
		Query query = entityManager.createQuery(queryString);
		return (List<Annonce>) query.getResultList();
	}

	/**
	 * Récupère l'id de la d'un utilisateur
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
	
	public List<Annonce> getAllAnnonceUtilisateur(int idUtilisateur) {
		String query = "FROM Annonce AS a " + "WHERE a.id_utilisateur = '" + idUtilisateur + "'";
		Query query2 = entityManager.createQuery(query);

		List listAnnonce = query2.getResultList();
		return listAnnonce;
	}

	public boolean isMatchingIdUserAndIdUserAnnonce(int idUser, int idAnnonce) {
		boolean isMatchingId = false;
		
		String query = "SELECT a.id_annonce FROM Annonce AS a WHERE a.id_utilisateur = '" + idUser + "' AND a.id_annonce = '"+idAnnonce+"' ";
		Query query2 = entityManager.createQuery(query);

		List annonces = query2.getResultList();

		if (annonces.size() != 0) {
			isMatchingId = true;
		}
		
		return isMatchingId;
	}
	
	/**
	 * Update a annonce
	 * @param annonce : The object Annonce representing a annonce to edit
	 */
	public void updateAnnonce(Annonce annonce) {
		int id_annonce = annonce.getId_annonce();
		String titre = annonce.getTitre();
		String description = annonce.getDescription();
		int capacite_max = annonce.getCapacite_max();
		try {
			userTransaction.begin();
			
			String query =	"UPDATE Annonce AS a "
					+ "SET titre = '" + titre + "', "
					+ "description = '" + description + "', "
					+ "capacite_max = '" + capacite_max + "' "
					+ "WHERE a.id_annonce = '" + id_annonce + "' ";
			
			Query query1 = entityManager.createQuery(query);
			query1.executeUpdate();
			userTransaction.commit();
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException exception) {
			
			exception.printStackTrace();
		}
	}
	
	/**
	 * Delete a annonce
	 * @param idAnnonce
	 */
	public void deleteAnnonce(int idAnnonce) {
		//Annonce annonce = entityManager.find(Annonce.class, idAnnonce);
		try {
			userTransaction.begin();
			String query =	"DELETE FROM Annonce "
					+ "WHERE id_annonce = '" + idAnnonce + "' ";
			Query result = entityManager.createQuery(query);
			result.executeUpdate();
			userTransaction.commit();
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
		}
		
	}

}