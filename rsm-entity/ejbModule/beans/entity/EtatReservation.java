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
@Table(name = "etat_reservation")
public class EtatReservation {
	private Integer id_etat;
	private String libelle;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId_etat() {
		return id_etat;
	}

	public void setId_etat(Integer id_etat) {
		this.id_etat = id_etat;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
}
