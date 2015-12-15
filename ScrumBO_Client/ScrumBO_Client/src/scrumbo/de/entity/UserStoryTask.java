package scrumbo.de.entity;

import java.io.Serializable;

public class UserStoryTask implements Serializable {
	
	private Integer		id;
	private String		beschreibung;
	private Taskstatus	taskstatus;
	private Integer		aufwandinstunden;
	private Benutzer	benutzer;
	private UserStory	userstory;
	
	public UserStoryTask() {
	
	}
	
	public UserStoryTask(Integer id, String beschreibung, Integer aufwandinstunden) {
		this.id = id;
		this.beschreibung = beschreibung;
		this.aufwandinstunden = aufwandinstunden;
	}
	
	public UserStoryTask(Integer id, String beschreibung, Taskstatus taskstatus, Integer aufwandinstunden,
			Benutzer benutzer, UserStory userstory) {
		this.id = id;
		this.beschreibung = beschreibung;
		this.taskstatus = taskstatus;
		this.aufwandinstunden = aufwandinstunden;
		this.benutzer = benutzer;
		this.userstory = userstory;
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
