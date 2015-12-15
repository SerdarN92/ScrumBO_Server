package model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "BENUTZER")
public class Benutzer {
	
	@Id
	@GeneratedValue
	@Column(name = "benutzer_id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "vorname", nullable = false)
	private String vorname;
	
	@Column(name = "nachname", nullable = false)
	private String nachname;
	
	@Column(name = "passwort", nullable = false)
	private String passwort;
	
	@Column(name = "email", unique = true, nullable = false)
	private String email;
	
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.EAGER, mappedBy = "benutzer")
	@Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE,
			org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST,
			org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
	@JsonSerialize
	@JsonDeserialize
	private List<UserStoryTask> userstorytask;
	
	public Benutzer() {
	
	}
	
	public Benutzer(String vorname, String nachname, String passwort, String email) {
		this.vorname = vorname;
		this.nachname = nachname;
		this.passwort = passwort;
		this.email = email;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getVorname() {
		return vorname;
	}
	
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	
	public String getNachname() {
		return nachname;
	}
	
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	
	public String getPasswort() {
		return passwort;
	}
	
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	// public Benutzerrolle getBenutzerrolle() {
	// return benutzerrolle;
	// }
	//
	// public void setBenutzerrolle(Benutzerrolle benutzerrolle) {
	// this.benutzerrolle = benutzerrolle;
	// }
	
	// public List<Scrumprojekt> getScrumprojekte() {
	// return scrumprojekte;
	// }
	//
	// public void setScrumprojekte(List<Scrumprojekt> scrumprojekte) {
	// this.scrumprojekte = scrumprojekte;
	// }
	
	public List<UserStoryTask> getUserstorytask() {
		return userstorytask;
	}
	
	public void setUserstorytask(List<UserStoryTask> userstorytask) {
		this.userstorytask = userstorytask;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Benutzer other = (Benutzer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
