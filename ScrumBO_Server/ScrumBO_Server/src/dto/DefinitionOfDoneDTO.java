package dto;

import java.io.Serializable;

public class DefinitionOfDoneDTO implements Serializable {
	
	private Integer			id;
	private String			criteria;
	private boolean			status;
	private UserStoryDTO	userstory;
							
	public DefinitionOfDoneDTO() {
	
	}
	
	public DefinitionOfDoneDTO(Integer id, String criteria, boolean status) {
		this.id = id;
		this.criteria = criteria;
		this.status = status;
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
	
	public UserStoryDTO getUserstory() {
		return userstory;
	}
	
	public void setUserstory(UserStoryDTO userstory) {
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
		DefinitionOfDoneDTO other = (DefinitionOfDoneDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
