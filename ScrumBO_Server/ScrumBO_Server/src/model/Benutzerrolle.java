package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * Entität für die Tabelle "Benutzerrolle".
 */

@Entity
@Table(name = "BENUTZERROLLE")
public class Benutzerrolle {
	
	@Id
	@GeneratedValue
	@Column(name = "benutzerrollen_id", unique = true, nullable = false)
	private Integer	id;
					
	@Column(name = "bezeichnung", unique = true, nullable = false)
	private String	bezeichnung;
					
	public Benutzerrolle() {
	
	}
	
	public Benutzerrolle(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getBezeichnung() {
		return bezeichnung;
	}
	
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
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
		Benutzerrolle other = (Benutzerrolle) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
