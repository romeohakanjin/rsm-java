package beans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author SLI
 *
 */
@Entity
@Table(name = "etat_commentaire")
public class EtatCommentaire {
	private Integer id_etat_commentaire;
	private String libelle;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId_etat_commentaire() {
		return id_etat_commentaire;
	}

	public void setId_etat_commentaire(Integer id_etat_commentaire) {
		this.id_etat_commentaire = id_etat_commentaire;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
}
