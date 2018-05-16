package beans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author RHA
 *
 */
@Entity
@Table(name = "service_chambre")
public class ServiceChambre {
	private Integer id_service_chambre;
	private String nom;
	private Integer quantite;
	private Integer id_annonce;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId_service_chambre() {
		return id_service_chambre;
	}

	public void setId_service_chambre(Integer id_service_chambre) {
		this.id_service_chambre = id_service_chambre;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Integer getQuantite() {
		return quantite;
	}

	public void setQuantite(Integer quantite) {
		this.quantite = quantite;
	}

	public Integer getId_annonce() {
		return id_annonce;
	}

	public void setId_annonce(Integer id_annonce) {
		this.id_annonce = id_annonce;
	}

}
