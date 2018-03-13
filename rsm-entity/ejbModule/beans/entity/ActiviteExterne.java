package beans.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;

/**
 * 
 * @author SLI
 *
 */
@Entity
@Table(name = "activite_externe")
public class ActiviteExterne {
	private Integer id_activite_externe;
	private String titre;
	private String description;
	private String ville;
	private Timestamp date_creation;
	private Integer id_type_activite;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId_activite_externe() {
		return id_activite_externe;
	}
	
	public void setId_activite_externe(Integer id_activite_externe) {
		this.id_activite_externe = id_activite_externe;
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
	
	public String getVille() {
		return ville;
	}
	
	public void setVille(String ville) {
		this.ville = ville;
	}
	
	public Timestamp getDate_creation() {
		return date_creation;
	}
	
	public void setDate_creation(Timestamp date_creation) {
		this.date_creation = date_creation;
	}
	
	public Integer getId_type_activite() {
		return id_type_activite;
	}
	
	public void setId_type_activite(Integer id_type_activite) {
		this.id_type_activite = id_type_activite;
	}	
}
