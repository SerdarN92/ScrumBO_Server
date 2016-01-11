package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/*
 * Entität für die Tabelle "Impediment"
 */

@Entity
@Table(name = "IMPEDIMENT")
public class Impediment {
	
	@Id
	@GeneratedValue
	@Column(name = "impediment_id", unique = true, nullable = false)
	private Integer	id;
					
	@Column(name = "priority", nullable = false)
	private Integer	priority;
					
	@Column(name = "employee", nullable = false)
	private String	employee;
					
	@Column(name = "description", nullable = false)
	private String	description;
					
	@Column(name = "dateOfOccurrence", nullable = false)
	private String	dateOfOccurrence;
					
	@Column(name = "dateOfRectify", nullable = true)
	private String	dateOfRectify;
					
	@Column(name = "comment", nullable = true)
	private String	comment;
					
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", nullable = true)
	private Project	project;
					
	public Impediment() {
	
	}
	
	public Impediment(Integer priority, String employee, String description, String dateOfOccurrence) {
		this.priority = priority;
		this.employee = employee;
		this.description = description;
		this.dateOfOccurrence = dateOfOccurrence;
		
	}
	
	public Impediment(Integer id, Integer priority, String employee, String description, String dateOfOccurrence,
			String dateOfRectify, String comment) {
		this.id = id;
		this.priority = priority;
		this.employee = employee;
		this.description = description;
		this.dateOfOccurrence = dateOfOccurrence;
		this.dateOfRectify = dateOfRectify;
		this.comment = comment;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getPriority() {
		return priority;
	}
	
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	public String getEmployee() {
		return employee;
	}
	
	public void setEmployee(String employee) {
		this.employee = employee;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDateOfOccurrence() {
		return dateOfOccurrence;
	}
	
	public void setDateOfOccurrence(String dateOfOccurrence) {
		this.dateOfOccurrence = dateOfOccurrence;
	}
	
	public String getDateOfRectify() {
		return dateOfRectify;
	}
	
	public void setDateOfRectify(String dateOfRectify) {
		this.dateOfRectify = dateOfRectify;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		this.project = project;
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
		Impediment other = (Impediment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
