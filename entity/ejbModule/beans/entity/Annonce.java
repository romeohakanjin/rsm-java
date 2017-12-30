package beans.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
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
@Table(name = "annonce")
public class Annonce {
	private Integer id_annonce;
	private Integer id_utilisateur;
	private String titre;
	private String description;
	private Integer capacite_max;
	private Timestamp date_creation;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCapacite_max() {
		return capacite_max;
	}

	public void setCapacite_max(Integer capacite_max) {
		this.capacite_max = capacite_max;
	}

	public Timestamp getDate_creation() {
		return date_creation;
	}

	public void setDate_creation(Timestamp date_creation) {
		this.date_creation = date_creation;
	}
}
