package dto;

import java.io.Serializable;

public class BenutzerDTO implements Serializable {
	
	private Integer	id;
	private String	vorname;
	private String	nachname;
	private String	passwort;
	private String	email;
					
	public BenutzerDTO() {
	}
	
	public BenutzerDTO(Integer id, String vorname, String nachname, String passwort, String email) {
		this.id = id;
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
		BenutzerDTO other = (BenutzerDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}