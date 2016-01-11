package dto;

import java.io.Serializable;

public class UserStoryTaskDTO implements Serializable {
	
	private Integer			id;
	private String			description;
	private TaskstatusDTO	taskstatus;
	private Integer			effortInHours;
	private UserDTO			user;
	private UserStoryDTO	userstory;
							
	public UserStoryTaskDTO() {
	
	}
	
	public UserStoryTaskDTO(Integer id, String description, Integer effortInHours) {
		this.id = id;
		this.description = description;
		this.effortInHours = effortInHours;
	}
	
	public UserStoryTaskDTO(Integer id, String description, Integer effortInHours, TaskstatusDTO taskstatus,
			UserDTO user, UserStoryDTO userstory) {
		this.id = id;
		this.description = description;
		this.effortInHours = effortInHours;
		this.taskstatus = taskstatus;
		this.user = user;
		this.userstory = userstory;
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
	
	public TaskstatusDTO getTaskstatus() {
		return taskstatus;
	}
	
	public void setTaskstatus(TaskstatusDTO taskstatus) {
		this.taskstatus = taskstatus;
	}
	
	public Integer getEffortInHours() {
		return effortInHours;
	}
	
	public void setEffortInHours(Integer effortInHours) {
		this.effortInHours = effortInHours;
	}
	
	public UserDTO getUser() {
		return user;
	}
	
	public void setUser(UserDTO user) {
		this.user = user;
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
		UserStoryTaskDTO other = (UserStoryTaskDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
