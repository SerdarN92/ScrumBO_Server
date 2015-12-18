package scrumbo.de.entity;

import java.io.Serializable;

public class Sprint implements Serializable {
	
	private Integer			id;
	private Integer			sprintnummer;
	private Scrumprojekt	scrumprojekt;
	private SprintBacklog	sprintbacklog;
							
	public Sprint() {
	
	}
	
	public Sprint(Integer id, Integer sprintnummer) {
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
	
	public Scrumprojekt getScrumprojekt() {
		return scrumprojekt;
	}
	
	public void setScrumprojekt(Scrumprojekt scrumprojekt) {
		this.scrumprojekt = scrumprojekt;
	}
	
	public SprintBacklog getSprintbacklog() {
		return sprintbacklog;
	}
	
	public void setSprintbacklog(SprintBacklog sprintbacklog) {
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
		Sprint other = (Sprint) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
