/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumbo.de.entity;

import java.io.Serializable;

/**
 *
 * @author Serdar
 */
public class User implements Serializable {
	
	private Integer	id;
	private String	prename;
	private String	lastname;
	private String	password;
	private String	email;
	// private Benutzerrolle benutzerrolle;
	
	public User() {
	}
	
	public User(Integer id, String vorname, String nachname, String passwort, String email) {
		this.id = id;
		this.prename = vorname;
		this.lastname = nachname;
		this.password = passwort;
		this.email = email;
	}
	
	// public Benutzer(Integer id, String vorname, String nachname, String
	// passwort, String email,
	// Benutzerrolle benutzerrolle) {
	// this.id = id;
	// this.vorname = vorname;
	// this.nachname = nachname;
	// this.passwort = passwort;
	// this.email = email;
	// // this.benutzerrolle = benutzerrolle;
	// }
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
		
	}
	
	public String getVorname() {
		return prename;
		
	}
	
	public void setVorname(String vorname) {
		this.prename = vorname;
		
	}
	
	public String getNachname() {
		return lastname;
		
	}
	
	public void setNachname(String nachname) {
		this.lastname = nachname;
	}
	
	public String getPasswort() {
		return password;
	}
	
	public void setPasswort(String passwort) {
		this.password = passwort;
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
