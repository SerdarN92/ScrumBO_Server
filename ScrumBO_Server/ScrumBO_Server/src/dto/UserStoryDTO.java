package dto;

import java.io.Serializable;
import java.util.List;

public class UserStoryDTO implements Serializable {
	
	private Integer						id;
	private Integer						priority;
	private String						theme;
	private String						description;
	private Integer						effortInDays;
	private String						acceptanceCriteria;
	private Integer						status;
										
	private ProductBacklogDTO			productbacklog;
	private SprintDTO					sprint;
	private List<UserStoryTaskDTO>		userstorytask;
	private List<DefinitionOfDoneDTO>	definitionOfDone;
										
	public UserStoryDTO() {
	
	}
	
	public UserStoryDTO(Integer id, Integer priority, String theme, String description, Integer effortInDays,
			String acceptanceCriteria, Integer status) {
		this.id = id;
		this.priority = priority;
		this.theme = theme;
		this.description = description;
		this.effortInDays = effortInDays;
		this.acceptanceCriteria = acceptanceCriteria;
		this.status = status;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getPriority() {
		return priority;
	}
	
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	public String getTheme() {
		return theme;
	}
	
	public void setTheme(String theme) {
		this.theme = theme;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getEffortInDays() {
		return effortInDays;
	}
	
	public void setEffortInDays(Integer effortInDays) {
		this.effortInDays = effortInDays;
	}
	
	public String getAcceptanceCriteria() {
		return acceptanceCriteria;
	}
	
	public void setAcceptanceCriteria(String acceptanceCriteria) {
		this.acceptanceCriteria = acceptanceCriteria;
	}
	
	public void setSprint(SprintDTO sprint) {
		this.sprint = sprint;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer platziestatusrung) {
		this.status = status;
	}
	
	public ProductBacklogDTO getProductbacklog() {
		return productbacklog;
	}
	
	public void setProductbacklog(ProductBacklogDTO productbacklog) {
		this.productbacklog = productbacklog;
	}
	
	public SprintDTO getSprint() {
		return sprint;
	}
	
	public void setSprintbacklog(SprintDTO sprint) {
		this.sprint = sprint;
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
