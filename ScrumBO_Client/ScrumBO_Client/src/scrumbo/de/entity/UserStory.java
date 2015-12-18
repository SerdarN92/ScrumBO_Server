package scrumbo.de.entity;

import java.io.Serializable;
import java.util.List;

public class UserStory implements Serializable {
	
	private Integer					id;
	private Integer					prioritaet;
	private String					thema;
	private String					beschreibung;
	private Integer					aufwandintagen;
	private String					akzeptanzkriterien;
	private Integer					platzierung;
									
	private ProductBacklog			productbacklog;
	private Sprint					sprint;
	private List<UserStoryTask>		userstorytask;
	private List<DefinitionOfDone>	definitionOfDone;
									
	public UserStory() {
	
	}
	
	public UserStory(Integer id, Integer prioritaet, String thema, String beschreibung, Integer aufwandintagen,
			String akzeptanzkriterien, Integer platzierung) {
		this.id = id;
		this.prioritaet = prioritaet;
		this.thema = thema;
		this.beschreibung = beschreibung;
		this.aufwandintagen = aufwandintagen;
		this.akzeptanzkriterien = akzeptanzkriterien;
		this.platzierung = platzierung;
	}
	
	public UserStory(Integer prioritaet, String thema, String beschreibung, String akzeptanzkriterien,
			Integer aufwandintagen) {
		this.prioritaet = prioritaet;
		this.thema = thema;
		this.beschreibung = beschreibung;
		this.aufwandintagen = aufwandintagen;
		this.akzeptanzkriterien = akzeptanzkriterien;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getPrioritaet() {
		return prioritaet;
	}
	
	public void setPrioritaet(Integer prioritaet) {
		this.prioritaet = prioritaet;
	}
	
	public String getThema() {
		return thema;
	}
	
	public void setThema(String thema) {
		this.thema = thema;
	}
	
	public String getBeschreibung() {
		return beschreibung;
	}
	
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
	
	public Integer getAufwandintagen() {
		return aufwandintagen;
	}
	
	public void setAufwandintagen(Integer aufwandintagen) {
		this.aufwandintagen = aufwandintagen;
	}
	
	public String getAkzeptanzkriterien() {
		return akzeptanzkriterien;
	}
	
	public void setAkzeptanzkriterien(String akzeptanzkriterien) {
		this.akzeptanzkriterien = akzeptanzkriterien;
	}
	
	public Integer getPlatzierung() {
		return platzierung;
	}
	
	public void setPlatzierung(Integer platzierung) {
		this.platzierung = platzierung;
	}
	
	public ProductBacklog getProductbacklog() {
		return productbacklog;
	}
	
	public void setProductbacklog(ProductBacklog productbacklog) {
		this.productbacklog = productbacklog;
	}
	
	public Sprint getSprint() {
		return sprint;
	}
	
	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}
	
	public List<UserStoryTask> getUserstorytask() {
		return userstorytask;
	}
	
	public void setUserstorytask(List<UserStoryTask> userstorytask) {
		this.userstorytask = userstorytask;
	}
	
	public List<DefinitionOfDone> getDefinitionOfDone() {
		return definitionOfDone;
	}
	
	public void setDefinitionOfDone(List<DefinitionOfDone> definitionOfDone) {
		this.definitionOfDone = definitionOfDone;
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
		UserStory other = (UserStory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
