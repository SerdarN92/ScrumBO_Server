package model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/*
 * Entität für die Tabelle "Sprint".
 */

@Entity
@Table(name = "SPRINT")
public class Sprint {
	
	@Id
	@GeneratedValue
	@Column(name = "sprint_id", unique = true, nullable = false)
	private Integer			id;
							
	@Column(name = "sprint_nummer", nullable = false)
	private Integer			sprintnummer;
							
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "scrumprojekt_id", nullable = false)
	private Scrumprojekt	scrumprojekt;
							
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private SprintBacklog	sprintbacklog;
							
	public Sprint() {
	
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getSprintnummer() {
		return sprintnummer;
	}
	
	public void setSprintnummer(Integer sprintnummer) {
		this.sprintnummer = sprintnummer;
	}
	
	public Scrumprojekt getScrumprojekt() {
		return scrumprojekt;
	}
	
	public void setScrumprojekt(Scrumprojekt scrumprojekt) {
		this.scrumprojekt = scrumprojekt;
	}
	
	public SprintBacklog getSprintbacklog() {
		return sprintbacklog;
	}
	
	public void setSprintbacklog(SprintBacklog sprintbacklog) {
		this.sprintbacklog = sprintbacklog;
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
		Sprint other = (Sprint) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
