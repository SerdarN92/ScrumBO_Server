package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BENUTZERROLLE")
public class Benutzerrolle {
	
	@Id
	@GeneratedValue
	@Column(name = "benutzerrollen_id", unique = true, nullable = false)
	private Integer benutzerrollenid;
	
	@Column(name = "bezeichnung", unique = true, nullable = false)
	private String bezeichnung;
	
	// @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
	// CascadeType.REFRESH }, fetch = FetchType.EAGER, mappedBy =
	// "benutzerrolle")
	// @Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE,
	// org.hibernate.annotations.CascadeType.DELETE,
	// org.hibernate.annotations.CascadeType.MERGE,
	// org.hibernate.annotations.CascadeType.PERSIST,
	// org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
	// @JsonSerialize
	// @JsonDeserialize
	// private List<Benutzer> benutzer;
	
	public Benutzerrolle() {
	
	}
	
	public Benutzerrolle(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	public Integer getBenutzerrollenid() {
		return benutzerrollenid;
	}
	
	public void setBenutzerrollenid(Integer benutzerrollenid) {
		this.benutzerrollenid = benutzerrollenid;
	}
	
	public String getBezeichnung() {
		return bezeichnung;
	}
	
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	// public List<Benutzer> getBenutzer() {
	// return benutzer;
	// }
	//
	// public void setBenutzer(List<Benutzer> benutzer) {
	// this.benutzer = benutzer;
	// }
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((benutzerrollenid == null) ? 0 : benutzerrollenid.hashCode());
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
		Benutzerrolle other = (Benutzerrolle) obj;
		if (benutzerrollenid == null) {
			if (other.benutzerrollenid != null)
				return false;
		} else if (!benutzerrollenid.equals(other.benutzerrollenid))
			return false;
		return true;
	}
	
}
