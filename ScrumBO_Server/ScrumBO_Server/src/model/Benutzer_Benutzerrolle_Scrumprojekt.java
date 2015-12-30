package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/*
 * Entität für die Tabelle "benutzer_benutzerrolle_scrumprojekt".
 * Sie dient zur Zuweisung von Benutzern zu Benutzerrollen in Scrumprojekten.
 *
 * Ein Benutzer kann verschiedene Rollen in verschiedenen Projekten haben.
 */

@Entity
@Table(name = "benutzer_benutzerrolle_scrumprojekt")
public class Benutzer_Benutzerrolle_Scrumprojekt {
	
	@Embeddable
	public static class Pk implements Serializable {
		@Column(nullable = true, updatable = true)
		private Integer	benutzerId;
						
		@Column(nullable = true, updatable = true)
		private Integer	benutzerrollenId;
						
		@Column(nullable = true, updatable = true)
		private Integer	scrumprojektId;
						
		public Pk() {
		}
		
		public Pk(Integer benutzerId, Integer benutzerrollenId, Integer scrumprojektId) {
			this.benutzerId = benutzerId;
			this.benutzerrollenId = benutzerrollenId;
			this.scrumprojektId = scrumprojektId;
		}
		
		public Integer getBenutzerId() {
			return benutzerId;
		}
		
		public void setBenutzerId(Integer benutzerId) {
			this.benutzerId = benutzerId;
		}
		
		public Integer getBenutzerrollenId() {
			return benutzerrollenId;
		}
		
		public void setBenutzerrollenId(Integer benutzerrollenId) {
			this.benutzerrollenId = benutzerrollenId;
		}
		
		public Integer getScrumprojektId() {
			return scrumprojektId;
		}
		
		public void setScrumprojektId(Integer scrumprojektId) {
			this.scrumprojektId = scrumprojektId;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((benutzerId == null) ? 0 : benutzerId.hashCode());
			result = prime * result + ((benutzerrollenId == null) ? 0 : benutzerrollenId.hashCode());
			result = prime * result + ((scrumprojektId == null) ? 0 : scrumprojektId.hashCode());
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
			Pk other = (Pk) obj;
			if (benutzerId == null) {
				if (other.benutzerId != null)
					return false;
			} else if (!benutzerId.equals(other.benutzerId))
				return false;
			if (benutzerrollenId == null) {
				if (other.benutzerrollenId != null)
					return false;
			} else if (!benutzerrollenId.equals(other.benutzerrollenId))
				return false;
			if (scrumprojektId == null) {
				if (other.scrumprojektId != null)
					return false;
			} else if (!scrumprojektId.equals(other.scrumprojektId))
				return false;
			return true;
		}
		
	}
	
	@EmbeddedId
	private Pk pk;
	
	public Pk getPk() {
		return pk;
	}
	
	public void setPk(Pk pk) {
		this.pk = pk;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pk == null) ? 0 : pk.hashCode());
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
		Benutzer_Benutzerrolle_Scrumprojekt other = (Benutzer_Benutzerrolle_Scrumprojekt) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}
	
}
