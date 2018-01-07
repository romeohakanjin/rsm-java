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
@Table(name = "hotel")
public class Hotel {
	private Integer id_hotel;
	private String nom;
	private String adresse;
	private String code_postal;
	private String ville;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId_hotel() {
		return id_hotel;
	}

	public void setId_hotel(Integer id_hotel) {
		this.id_hotel = id_hotel;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getCode_postal() {
		return code_postal;
	}

	public void setCode_postal(String code_postal) {
		this.code_postal = code_postal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}
}
