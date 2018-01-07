package beans.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author RHA
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "utilisateur")
public class Utilisateur implements Serializable {
	private Integer id_utilisateur;
	private Integer id_type_utilisateur;
	private Integer id_hotel;
	private String nom;
	private String prenom;
	private String mail;
	private String mobile;
	private String adresse;
	private String code_postal;
	private String ville;
	private Integer point_bonus;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId_utilisateur() {
		return id_utilisateur;
	}

	public void setId_utilisateur(Integer id_utilisateur) {
		this.id_utilisateur = id_utilisateur;
	}

	public Integer getId_type_utilisateur() {
		return id_type_utilisateur;
	}

	public void setId_type_utilisateur(Integer id_type_utilisateur) {
		this.id_type_utilisateur = id_type_utilisateur;
	}

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

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public Integer getPoint_bonus() {
		return point_bonus;
	}

	public void setPoint_bonus(Integer point_bonus) {
		this.point_bonus = point_bonus;
	}
}
