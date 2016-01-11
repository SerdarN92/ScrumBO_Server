package scrumbo.de.entity;

import java.io.Serializable;
import java.util.List;

public class Taskstatus implements Serializable {
	
	private Integer					id;
	private String					description;
	private List<UserStoryTask>	userstorytask;
	
	public Taskstatus() {
	
	}
	
	public Taskstatus(Integer id, String beschreibung) {
		this.id = id;
		this.description = beschreibung;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getBeschreibung() {
		return description;
	}
	
	public void setBeschreibung(String beschreibung) {
		this.description = beschreibung;
	}
	
	public List<UserStoryTask> getUserstorytask() {
		return userstorytask;
	}
	
	public void setUserstorytask(List<UserStoryTask> userstorytask) {
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
		Taskstatus other = (Taskstatus) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
