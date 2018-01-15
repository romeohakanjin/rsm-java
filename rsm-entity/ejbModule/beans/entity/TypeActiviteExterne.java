package beans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author SLI
 *
 */
@Entity
@Table(name = "type_activite")
public class TypeActiviteExterne {
	private Integer id_type_activite;
	private String libelle;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId_type_activite() {
		return id_type_activite;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public void setId_type_activite(Integer id_type_activite) {
		this.id_type_activite = id_type_activite;
	}
}
