package dto;

public class Benutzer_Benutzerrolle_ScrumprojektDTO {
	
	private Integer	benutzerId;
	private Integer	benutzerrollenId;
	private Integer	scrumprojektId;
	
	public Benutzer_Benutzerrolle_ScrumprojektDTO() {
	
	}
	
	public Benutzer_Benutzerrolle_ScrumprojektDTO(Integer benutzerId, Integer benutzerrollenId,
			Integer scrumprojektId) {
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
		Benutzer_Benutzerrolle_ScrumprojektDTO other = (Benutzer_Benutzerrolle_ScrumprojektDTO) obj;
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
