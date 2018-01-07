package beans.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author RHA
 *
 */
@Entity
@Table(name = "paiement")
public class Paiement {
	private Integer id_paiement;
	private Integer id_reservation;
	private Timestamp date_paiement;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId_paiement() {
		return id_paiement;
	}

	public void setId_paiement(Integer id_paiement) {
		this.id_paiement = id_paiement;
	}

	public Integer getId_reservation() {
		return id_reservation;
	}

	public void setId_reservation(Integer id_reservation) {
		this.id_reservation = id_reservation;
	}

	public Timestamp getDate_paiement() {
		return date_paiement;
	}

	public void setDate_paiement(Timestamp date_paiement) {
		this.date_paiement = date_paiement;
	}
}
