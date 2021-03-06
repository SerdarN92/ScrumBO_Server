package dto;

import java.io.Serializable;
import java.util.List;

public class TaskstatusDTO implements Serializable {
	
	private Integer					id;
	private String					description;
	private List<UserStoryTaskDTO>	userstorytask;
									
	public TaskstatusDTO() {
	
	}
	
	public TaskstatusDTO(Integer id, String description) {
		this.id = id;
		this.description = description;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<UserStoryTaskDTO> getUserstorytask() {
		return userstorytask;
	}
	
	public void setUserstorytask(List<UserStoryTaskDTO> userstorytask) {
		this.userstorytask = userstorytask;
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
		TaskstatusDTO other = (TaskstatusDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
