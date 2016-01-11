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
 * Entität für die Tabelle "Definitionofdone".
 */

@Entity
@Table(name = "DEFINITIONOFDONE")
public class DefinitionOfDone {
	
	@Id
	@GeneratedValue
	@Column(name = "definitionofdone_id", unique = true, nullable = false)
	private Integer		id;
						
	@Column(name = "criteria", nullable = true)
	private String		criteria;
						
	@Column(name = "status")
	private boolean		status;
						
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userstory_id", nullable = true)
	private UserStory	userstory;
						
	public DefinitionOfDone() {
	
	}
	
	public DefinitionOfDone(Integer id, String criteria, boolean status) {
		this.id = id;
		this.criteria = criteria;
		this.status = status;
		
	}
	
	public DefinitionOfDone(String criteria, boolean status, UserStory userstory) {
		this.criteria = criteria;
		this.status = status;
		this.userstory = userstory;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCriteria() {
		return criteria;
	}
	
	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}
	
	public boolean getStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public UserStory getUserstory() {
		return userstory;
	}
	
	public void setUserstory(UserStory userstory) {
		this.userstory = userstory;
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
		DefinitionOfDone other = (DefinitionOfDone) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
