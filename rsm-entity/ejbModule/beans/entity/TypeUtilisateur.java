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
@Table(name = "type_utilisateur")
public class TypeUtilisateur {
	private Integer id_type_utilisateur;
	private String libelle;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId_type_utilisateur() {
		return id_type_utilisateur;
	}

	public void setId_type_utilisateur(Integer id_type_utilisateur) {
		this.id_type_utilisateur = id_type_utilisateur;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
}
