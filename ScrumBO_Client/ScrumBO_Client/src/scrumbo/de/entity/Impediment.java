package scrumbo.de.entity;

import java.io.Serializable;

public class Impediment implements Serializable {
	
	private Integer	id;
	private Integer	priority;
	private String	description;
	private String	employee;
	private String	dateOfOccurrence;
	private String	dateOfRectify;
	private String	comment;
	private Project	project;
					
	public Impediment() {
	
	}
	
	public Impediment(Integer id, Integer priorität, String beschreibung, String mitarbeiter, String datumDesAuftretens,
			String datumDerBehebung, String kommentar) {
		this.id = id;
		this.priority = priorität;
		this.description = beschreibung;
		this.employee = mitarbeiter;
		this.dateOfOccurrence = datumDesAuftretens;
		this.dateOfRectify = datumDerBehebung;
		this.comment = kommentar;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getPriorität() {
		return priority;
	}
	
	public void setPriorität(Integer priorität) {
		this.priority = priorität;
	}
	
	public String getMitarbeiter() {
		return employee;
	}
	
	public void setMitarbeiter(String mitarbeiter) {
		this.employee = mitarbeiter;
	}
	
	public String getKommentar() {
		return comment;
	}
	
	public void setKommentar(String kommentar) {
		this.comment = kommentar;
	}
	
	public String getBeschreibung() {
		return description;
	}
	
	public void setBeschreibung(String beschreibung) {
		this.description = beschreibung;
	}
	
	public String getDatumDesAuftretens() {
		return dateOfOccurrence;
	}
	
	public void setDatumDesAuftretens(String datumDesAuftretens) {
		this.dateOfOccurrence = datumDesAuftretens;
	}
	
	public String getDatumDerBehebung() {
		return dateOfRectify;
	}
	
	public void setDatumDerBehebung(String datumDerBehebung) {
		this.dateOfRectify = datumDerBehebung;
	}
	
	public Project getScrumprojekt() {
		return project;
	}
	
	public void setScrumprojekt(Project scrumprojekt) {
		this.project = scrumprojekt;
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
		Impediment other = (Impediment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
