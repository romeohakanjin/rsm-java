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
@Table(name = "commentaire")
public class Commentaire {
	private Integer id_commentaire;
	private Integer id_reservation;
	private Integer id_utilisateur;
	private Timestamp date_creation;
	private String commentaire;
	private Integer id_etat_commentaire;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId_commentaire() {
		return id_commentaire;
	}

	public void setId_commentaire(Integer id_commentaire) {
		this.id_commentaire = id_commentaire;
	}

	public Integer getId_reservation() {
		return id_reservation;
	}

	public void setId_reservation(Integer id_reservation) {
		this.id_reservation = id_reservation;
	}

	public Integer getId_utilisateur() {
		return id_utilisateur;
	}

	public void setId_utilisateur(Integer id_utilisateur) {
		this.id_utilisateur = id_utilisateur;
	}

	public Timestamp getDate_creation() {
		return date_creation;
	}

	public void setDate_creation(Timestamp date_creation) {
		this.date_creation = date_creation;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public Integer getId_etat_commentaire() {
		return id_etat_commentaire;
	}

	public void setId_etat_commentaire(Integer id_etat_commentaire) {
		this.id_etat_commentaire = id_etat_commentaire;
	}

}
