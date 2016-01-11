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
 * Entität für die Tabelle "Userstorytask".
 */

@Entity
@Table(name = "USERSTORYTASK")
public class UserStoryTask {
	
	@Id
	@GeneratedValue
	@Column(name = "userstorytask_id", unique = true, nullable = false)
	private Integer		id;
						
	@Column(name = "description", nullable = false)
	private String		description;
						
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "taskstatus_id", nullable = true)
	private Taskstatus	taskstatus;
						
	@Column(name = "effortInHours", nullable = false)
	private Integer		effortInHours;
						
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = true, updatable = true)
	private User		user;
						
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userstory_id", nullable = true, updatable = true)
	private UserStory	userstory;
						
	public UserStoryTask(String description, Taskstatus taskstatus, Integer effortInHours) {
		this.description = description;
		this.taskstatus = taskstatus;
		this.effortInHours = effortInHours;
		
	}
	
	public UserStoryTask(String description, Taskstatus taskstatus, Integer effortInHours, User user,
			UserStory userstory) {
		this.description = description;
		this.taskstatus = taskstatus;
		this.effortInHours = effortInHours;
		this.user = user;
		this.userstory = userstory;
		
	}
	
	public UserStoryTask() {
	
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
	
	public Taskstatus getTaskstatus() {
		return taskstatus;
	}
	
	public void setTaskstatus(Taskstatus taskstatus) {
		this.taskstatus = taskstatus;
	}
	
	public Integer getEffortInHours() {
		return effortInHours;
	}
	
	public void setEffortInHours(Integer effortInHours) {
		this.effortInHours = effortInHours;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
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
		UserStoryTask other = (UserStoryTask) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
