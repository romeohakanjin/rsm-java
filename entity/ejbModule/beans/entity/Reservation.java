package beans.entity;

import java.sql.Date;

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
@Table(name = "reservation")
public class Reservation {
	private Integer id_reservation;
	private Integer id_annonce;
	private Integer id_utilisateur;
	private Double prix;
	private Integer capatice_max;
	private Date date_sejour;
	private Integer id_statut_reservation;
	private Integer id_etat_reservation;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId_reservation() {
		return id_reservation;
	}

	public void setId_reservation(Integer id_reservation) {
		this.id_reservation = id_reservation;
	}

	public Integer getId_annonce() {
		return id_annonce;
	}

	public void setId_annonce(Integer id_annonce) {
		this.id_annonce = id_annonce;
	}

	public Integer getId_utilisateur() {
		return id_utilisateur;
	}

	public void setId_utilisateur(Integer id_utilisateur) {
		this.id_utilisateur = id_utilisateur;
	}

	public Double getPrix() {
		return prix;
	}

	public void setPrix(Double prix) {
		this.prix = prix;
	}

	public Integer getCapatice_max() {
		return capatice_max;
	}

	public void setCapatice_max(Integer capatice_max) {
		this.capatice_max = capatice_max;
	}

	public Date getDate_sejour() {
		return date_sejour;
	}

	public void setDate_sejour(Date date_sejour) {
		this.date_sejour = date_sejour;
	}

	public Integer getId_statut_reservation() {
		return id_statut_reservation;
	}

	public void setId_statut_reservation(Integer id_statut_reservation) {
		this.id_statut_reservation = id_statut_reservation;
	}

	public Integer getId_etat_reservation() {
		return id_etat_reservation;
	}

	public void setId_etat_reservation(Integer id_etat_reservation) {
		this.id_etat_reservation = id_etat_reservation;
	}
}
