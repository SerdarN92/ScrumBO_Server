package dto;

import java.io.Serializable;

public class SprintDTO implements Serializable {
	
	private Integer				id;
	private Integer				sprintnummer;
	private ScrumprojektDTO		scrumprojekt;
	private SprintBacklogDTO	sprintbacklog;
								
	public SprintDTO() {
	
	}
	
	public SprintDTO(Integer id, Integer sprintnummer) {
		this.id = id;
		this.sprintnummer = sprintnummer;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getSprintnummer() {
		return sprintnummer;
	}
	
	public void setSprintnummer(Integer sprintnummer) {
		this.sprintnummer = sprintnummer;
	}
	
	public ScrumprojektDTO getScrumprojekt() {
		return scrumprojekt;
	}
	
	public void setScrumprojekt(ScrumprojektDTO scrumprojekt) {
		this.scrumprojekt = scrumprojekt;
	}
	
	public SprintBacklogDTO getSprintbacklog() {
		return sprintbacklog;
	}
	
	public void setSprintbacklog(SprintBacklogDTO sprintbacklog) {
		this.sprintbacklog = sprintbacklog;
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
		SprintDTO other = (SprintDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
