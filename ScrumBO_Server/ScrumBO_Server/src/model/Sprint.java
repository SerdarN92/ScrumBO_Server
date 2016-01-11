package model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

/*
 * Entität für die Tabelle "Sprint".
 */

@Entity
@Table(name = "SPRINT")
public class Sprint {
	
	@Id
	@GeneratedValue
	@Column(name = "sprint_id", unique = true, nullable = false)
	private Integer			id;
							
	@Column(name = "sprint_number", nullable = false)
	private Integer			sprintnumber;
							
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "project_id", nullable = false)
	private Project			project;
							
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private SprintBacklog	sprintbacklog;
							
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE,
			org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST,
			org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
	private BurndownChart	burndownChart;
							
	@Column(name = "status", nullable = false)
	private boolean			status;
							
	public Sprint() {
	
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
	
	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	public SprintBacklog getSprintbacklog() {
		return sprintbacklog;
	}
	
	public void setSprintbacklog(SprintBacklog sprintbacklog) {
		this.sprintbacklog = sprintbacklog;
	}
	
	public BurndownChart getBurndownChart() {
		return burndownChart;
	}
	
	public void setBurndownChart(BurndownChart burndownChart) {
		this.burndownChart = burndownChart;
	}
	
	public boolean getStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
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
