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
									
	@Column(name = "prioritaet", nullable = false)
	private Integer					prioritaet;
									
	@Column(name = "thema", nullable = false)
	private String					thema;
									
	@Column(name = "beschreibung", nullable = false)
	private String					beschreibung;
									
	@Column(name = "aufwandintagen", nullable = false)
	private Integer					aufwandintagen;
									
	@Column(name = "akzeptanzkriterien", nullable = false)
	private String					akzeptanzkriterien;
									
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
	
	public UserStory(Integer priortaet, String thema, String beschreibung, Integer aufwandintagen,
			String akzeptanzkriterien, Integer status) {
		this.prioritaet = priortaet;
		this.thema = thema;
		this.beschreibung = beschreibung;
		this.aufwandintagen = aufwandintagen;
		this.akzeptanzkriterien = akzeptanzkriterien;
		this.status = status;
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
