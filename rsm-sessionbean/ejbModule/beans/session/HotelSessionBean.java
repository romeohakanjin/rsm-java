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

/**
 * @author RHA
 */
@Stateful
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class HotelSessionBean {

	@PersistenceContext(unitName = "RsmProjectService")
	EntityManager entityManager;

	@Resource
	UserTransaction userTransaction;
	
	/**
	 * Crate a hotel
	 * @param hotel : intance of Hotel
	 * @return true/false : if the request has been executed
	 */
	public Boolean createHotel(Hotel hotel) {
		try {
			userTransaction.begin();
			entityManager.persist(hotel);
			userTransaction.commit();
			return true;
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * get all the hotels
	 * @return List<Hotel>
	 */
	@SuppressWarnings("unchecked")
	public List<Hotel> getAllHotels(){
		String queryString = "FROM Hotel";
		Query query = entityManager.createQuery(queryString);
		return (List<Hotel>) query.getResultList();
	}
	
	/**
	 * get a hotel
	 * @param idHotel
	 * @return Hotel
	 */
	public Hotel getHotelById(Integer idHotel) {
		String queryString = "FROM Hotel h WHERE h.id_hotel = "+idHotel;
		Query query = entityManager.createQuery(queryString);
		return (Hotel) query.getResultList().get(0);
	}
}
