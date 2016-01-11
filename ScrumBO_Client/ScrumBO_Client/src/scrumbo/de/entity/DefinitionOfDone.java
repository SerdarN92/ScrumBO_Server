package scrumbo.de.entity;

import java.io.Serializable;

public class DefinitionOfDone implements Serializable {
	
	private Integer		id;
	private String		criteria;
	private boolean		status;
	private UserStory	userstory	= null;
	
	public DefinitionOfDone() {
	
	}
	
	public DefinitionOfDone(Integer id, String kriterium, boolean status) {
		this.id = id;
		this.criteria = kriterium;
		this.status = status;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getKriterium() {
		return criteria;
	}
	
	public void setKriterium(String kriterium) {
		this.criteria = kriterium;
	}
	
	public boolean isStatus() {
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
