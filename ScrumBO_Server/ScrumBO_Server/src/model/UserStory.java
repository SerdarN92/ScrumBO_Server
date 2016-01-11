package model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

/*
 * Entität für die Tabelle "Userstory".
 */

@Entity
@Table(name = "USERSTORY")
public class UserStory {
	
	@Id
	@GeneratedValue
	@Column(name = "userstory_id", unique = true, nullable = false)
	private Integer					id;
									
	@Column(name = "priority", nullable = false)
	private Integer					priority;
									
	@Column(name = "theme", nullable = false)
	private String					theme;
									
	@Column(name = "description", nullable = false)
	private String					description;
									
	@Column(name = "effortInDays", nullable = false)
	private Integer					effortInDays;
									
	@Column(name = "acceptanceCriteria", nullable = false)
	private String					acceptanceCriteria;
									
	@Column(name = "status", unique = false, nullable = true)
	private Integer					status;
									
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productbacklog_id", nullable = true)
	private ProductBacklog			productbacklog;
									
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sprint_id", nullable = true)
	private Sprint					sprint;
									
	// @Column(name = "sprintnummer", unique = false, nullable = true)
	// private Integer sprintnummer;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "userstory")
	@Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE,
			org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST,
			org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
	private List<UserStoryTask>		userstorytask;
									
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.EAGER, mappedBy = "userstory")
	@Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE,
			org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST,
			org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
	private List<DefinitionOfDone>	definitionofdone;
									
	public UserStory() {
	
	}
	
	public UserStory(Integer priority, String theme, String description, Integer effortInDays,
			String acceptanceCriteria, Integer status) {
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
	
	public List<DefinitionOfDone> getDefinitionofdone() {
		return definitionofdone;
	}
	
	public void setDefinitionofdone(List<DefinitionOfDone> definitionofdone) {
		this.definitionofdone = definitionofdone;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
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
	
	public List<DefinitionOfDone> getDefinitinOfDone() {
		return definitionofdone;
	}
	
	public void setDefinitinOfDone(List<DefinitionOfDone> definitionofdone) {
		this.definitionofdone = definitionofdone;
	}
	
	// public Integer getSprintnummer() {
	// if (sprint != null)
	// return sprint.getSprintnummer();
	// return null;
	// }
	//
	// public void setSprintnummer(Integer sprintnummer) {
	// this.sprintnummer = sprintnummer;
	// }
	
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
