package dto;

import java.io.Serializable;

public class UserStoryTaskDTO implements Serializable {
	
	private Integer			id;
	private String			beschreibung;
	private TaskstatusDTO	taskstatus;
	private Integer			aufwandinstunden;
	private BenutzerDTO		benutzer;
	private UserStoryDTO	userstory;
	
	public UserStoryTaskDTO() {
	
	}
	
	public UserStoryTaskDTO(Integer id, String beschreibung, Integer aufwandinstunden) {
		this.id = id;
		this.beschreibung = beschreibung;
		this.aufwandinstunden = aufwandinstunden;
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
	
	public TaskstatusDTO getTaskstatus() {
		return taskstatus;
	}
	
	public void setTaskstatus(TaskstatusDTO taskstatus) {
		this.taskstatus = taskstatus;
	}
	
	public Integer getAufwandinstunden() {
		return aufwandinstunden;
	}
	
	public void setAufwandinstunden(Integer aufwandinstunden) {
		this.aufwandinstunden = aufwandinstunden;
	}
	
	public BenutzerDTO getBenutzer() {
		return benutzer;
	}
	
	public void setBenutzer(BenutzerDTO benutzer) {
		this.benutzer = benutzer;
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
