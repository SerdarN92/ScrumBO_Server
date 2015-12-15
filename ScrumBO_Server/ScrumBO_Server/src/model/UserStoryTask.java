package model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USERSTORYTASK")
public class UserStoryTask {
	
	@Id
	@GeneratedValue
	@Column(name = "userstorytask_id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "beschreibung", nullable = false)
	private String beschreibung;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "taskstatus_id", nullable = true)
	private Taskstatus taskstatus;
	
	@Column(name = "aufwandinstunden", nullable = false)
	private Integer aufwandinstunden;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "benutzer_id", nullable = true, updatable = true)
	private Benutzer benutzer;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "userstory_id", nullable = true, updatable = true)
	private UserStory userstory;
	
	public UserStoryTask(String beschreibung, Taskstatus taskstatus, Integer aufwandinstunden) {
		this.beschreibung = beschreibung;
		this.taskstatus = taskstatus;
		this.aufwandinstunden = aufwandinstunden;
		
	}
	
	public UserStoryTask() {
	
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getBeschreibung() {
		return beschreibung;
	}
	
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
	
	public Taskstatus getTaskstatus() {
		return taskstatus;
	}
	
	public void setTaskstatus(Taskstatus taskstatus) {
		this.taskstatus = taskstatus;
	}
	
	public Integer getAufwandinstunden() {
		return aufwandinstunden;
	}
	
	public void setAufwandinstunden(Integer aufwandinstunden) {
		this.aufwandinstunden = aufwandinstunden;
	}
	
	public Benutzer getBenutzer() {
		return benutzer;
	}
	
	public void setBenutzer(Benutzer benutzer) {
		this.benutzer = benutzer;
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
