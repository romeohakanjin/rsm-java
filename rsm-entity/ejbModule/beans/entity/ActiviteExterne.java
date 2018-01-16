package beans.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.GenerationType;

/**
 * 
 * @author SLI
 *
 */
public class ActiviteExterne {
	private Integer id_activite;
	private String titre;
	private String description;
	private String ville;
	private Integer id_type_activite;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId_activite() {
		return id_activite;
	}
	public void setId_activite(Integer id_activite) {
		this.id_activite = id_activite;
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
	public Integer getId_type_activite() {
		return id_type_activite;
	}
	public void setId_type_activite(Integer id_type_activite) {
		this.id_type_activite = id_type_activite;
	}   
}
