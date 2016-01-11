package dto;

import java.io.Serializable;

public class SprintDTO implements Serializable {
	
	private Integer				id;
	private Integer				sprintnumber;
	private ProjectDTO			project;
	private SprintBacklogDTO	sprintbacklog;
	private BurndownChartDTO	burndownChart;
	private boolean				status;
								
	public SprintDTO() {
	
	}
	
	public SprintDTO(Integer id, Integer sprintnumber, boolean status) {
		this.id = id;
		this.sprintnumber = sprintnumber;
		this.status = status;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getSprintnumber() {
		return sprintnumber;
	}
	
	public void setSprintnumber(Integer sprintnumber) {
		this.sprintnumber = sprintnumber;
	}
	
	public ProjectDTO getProject() {
		return project;
	}
	
	public void setProject(ProjectDTO project) {
		this.project = project;
	}
	
	public SprintBacklogDTO getSprintbacklog() {
		return sprintbacklog;
	}
	
	public void setSprintbacklog(SprintBacklogDTO sprintbacklog) {
		this.sprintbacklog = sprintbacklog;
	}
	
	public boolean isStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public BurndownChartDTO getBurndownChart() {
		return burndownChart;
	}
	
	public void setBurndownChart(BurndownChartDTO burndownChart) {
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
		SprintDTO other = (SprintDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
