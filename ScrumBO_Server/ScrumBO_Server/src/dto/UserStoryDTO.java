package dto;

import java.io.Serializable;
import java.util.List;

public class UserStoryDTO implements Serializable {
	
	private Integer	id;
	private Integer	prioritaet;
	private String	thema;
	private String	beschreibung;
	private Integer	aufwandintagen;
	private String	akzeptanzkriterien;
	private Integer	platzierung;
	
	private ProductBacklogDTO			productbacklog;
	private SprintBacklogDTO			sprintbacklog;
	private List<UserStoryTaskDTO>		userstorytask;
	private List<DefinitionOfDoneDTO>	definitionOfDone;
	
	public UserStoryDTO() {
	
	}
	
	public UserStoryDTO(Integer id, Integer prioritaet, String thema, String beschreibung, Integer aufwandintagen,
			String akzeptanzkriterien, Integer platzierung) {
		this.id = id;
		this.prioritaet = prioritaet;
		this.thema = thema;
		this.beschreibung = beschreibung;
		this.aufwandintagen = aufwandintagen;
		this.akzeptanzkriterien = akzeptanzkriterien;
		this.platzierung = platzierung;
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
	
	public ProductBacklogDTO getProductbacklog() {
		return productbacklog;
	}
	
	public void setProductbacklog(ProductBacklogDTO productbacklog) {
		this.productbacklog = productbacklog;
	}
	
	public SprintBacklogDTO getSprintbacklog() {
		return sprintbacklog;
	}
	
	public void setSprintbacklog(SprintBacklogDTO sprintbacklog) {
		this.sprintbacklog = sprintbacklog;
	}
	
	public List<UserStoryTaskDTO> getUserstorytask() {
		return userstorytask;
	}
	
	public void setUserstorytask(List<UserStoryTaskDTO> userstorytask) {
		this.userstorytask = userstorytask;
	}
	
	public List<DefinitionOfDoneDTO> getDefinitionOfDone() {
		return definitionOfDone;
	}
	
	public void setDefinitionOfDone(List<DefinitionOfDoneDTO> definitionOfDone) {
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
		UserStoryDTO other = (UserStoryDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
