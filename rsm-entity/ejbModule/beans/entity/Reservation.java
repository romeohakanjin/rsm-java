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
@Table(name = "reservation")
public class Reservation {
	private Integer id_reservation;
	private Integer id_annonce;
	private Integer id_utilisateur;
	private double prix;
	private Timestamp date_debut_sejour;
	private Timestamp date_fin_sejour;
	private Integer duree_sejour;
	private Integer id_statut_reservation;
	private Integer id_etat_reservation;
	private boolean etat_reservation;

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

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public Timestamp getDate_debut_sejour() {
		return date_debut_sejour;
	}

	public void setDate_debut_sejour(Timestamp date_debut_sejour) {
		this.date_debut_sejour = date_debut_sejour;
	}

	public Timestamp getDate_fin_sejour() {
		return date_fin_sejour;
	}

	public void setDate_fin_sejour(Timestamp date_fin_sejour) {
		this.date_fin_sejour = date_fin_sejour;
	}

	public Integer getDuree_sejour() {
		return duree_sejour;
	}

	public void setDuree_sejour(Integer duree_sejour) {
		this.duree_sejour = duree_sejour;
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

	public boolean isEtat_reservation() {
		return etat_reservation;
	}

	public void setEtat_reservation(boolean etat_reservation) {
		this.etat_reservation = etat_reservation;
	}

}
