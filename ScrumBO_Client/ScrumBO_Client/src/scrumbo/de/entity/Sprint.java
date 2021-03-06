package scrumbo.de.entity;

import java.io.Serializable;

public class Sprint implements Serializable {
	
	private Integer			id;
	private Integer			sprintnumber;
	private Project			project;
	private SprintBacklog	sprintbacklog;
	private BurndownChart	burndownChart;
	private boolean			status;
							
	public Sprint() {
	
	}
	
	public Sprint(Integer id, Integer sprintnummer) {
		this.id = id;
		this.sprintnumber = sprintnummer;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getSprintnummer() {
		return sprintnumber;
	}
	
	public void setSprintnummer(Integer sprintnummer) {
		this.sprintnumber = sprintnummer;
	}
	
	public Project getScrumprojekt() {
		return project;
	}
	
	public void setScrumprojekt(Project scrumprojekt) {
		this.project = scrumprojekt;
	}
	
	public SprintBacklog getSprintbacklog() {
		return sprintbacklog;
	}
	
	public void setSprintbacklog(SprintBacklog sprintbacklog) {
		this.sprintbacklog = sprintbacklog;
	}
	
	public boolean getStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public BurndownChart getBurndownChart() {
		return burndownChart;
	}
	
	public void setBurndownChart(BurndownChart burndownChart) {
		this.burndownChart = burndownChart;
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
