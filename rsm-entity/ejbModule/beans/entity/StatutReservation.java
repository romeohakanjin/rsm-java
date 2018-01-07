package beans.entity;

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
@Table(name = "statut_reservation")
public class StatutReservation {
	private Integer id_statut_reservation;
	private String libelle;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId_statut_reservation() {
		return id_statut_reservation;
	}

	public void setId_statut_reservation(Integer id_statut_reservation) {
		this.id_statut_reservation = id_statut_reservation;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

}
