package beans.entity;

import java.sql.Timestamp;

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
@Table(name = "proposition_modification_annonce")
public class PropositionModificationAnnonce {
	private Integer id_proposition_modif_annonce;
	private Integer id_annonce;
	private Integer id_utilisateur;
	private String modification;
	private Timestamp date_proposition;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId_proposition_modif_annonce() {
		return id_proposition_modif_annonce;
	}
	
	public void setId_proposition_modif_annonce(Integer id_proposition_modif_annonce) {
		this.id_proposition_modif_annonce = id_proposition_modif_annonce;
	}
	
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
	
	public String getModification() {
		return modification;
	}
	
	public void setModification(String modification) {
		this.modification = modification;
	}
	
	public Timestamp getDate_proposition() {
		return date_proposition;
	}
	
	public void setDate_proposition(Timestamp date_proposition) {
		this.date_proposition = date_proposition;
	}	
}
