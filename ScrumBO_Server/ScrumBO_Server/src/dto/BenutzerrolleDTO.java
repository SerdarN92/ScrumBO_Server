package dto;

import java.io.Serializable;

public class BenutzerrolleDTO implements Serializable {
	
	private Integer	id;
	private String	bezeichnung;
	
	public BenutzerrolleDTO() {
	
	}
	
	public BenutzerrolleDTO(Integer id, String bezeichnung) {
		this.id = id;
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
		BenutzerrolleDTO other = (BenutzerrolleDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
