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
	private Integer id_type;
	private String libelle;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId_type() {
		return id_type;
	}

	public void setId_type(Integer id_type) {
		this.id_type = id_type;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
}
