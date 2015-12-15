package scrumbo.de.entity;

import java.io.Serializable;

public class Impediment implements Serializable {
	
	private Integer			id;
	private Integer			priorität;
	private String			beschreibung;
	private String			mitarbeiter;
	private String			datumDesAuftretens;
	private String			datumDerBehebung;
	private String			kommentar;
	private Scrumprojekt	scrumprojekt;
	
	public Impediment() {
	
	}
	
	public Impediment(Integer id, Integer priorität, String beschreibung, String mitarbeiter, String datumDesAuftretens,
			String datumDerBehebung, String kommentar) {
		this.id = id;
		this.priorität = priorität;
		this.beschreibung = beschreibung;
		this.mitarbeiter = mitarbeiter;
		this.datumDesAuftretens = datumDesAuftretens;
		this.datumDerBehebung = datumDerBehebung;
		this.kommentar = kommentar;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getPriorität() {
		return priorität;
	}
	
	public void setPriorität(Integer priorität) {
		this.priorität = priorität;
	}
	
	public String getMitarbeiter() {
		return mitarbeiter;
	}
	
	public void setMitarbeiter(String mitarbeiter) {
		this.mitarbeiter = mitarbeiter;
	}
	
	public String getKommentar() {
		return kommentar;
	}
	
	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}
	
	public String getBeschreibung() {
		return beschreibung;
	}
	
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
	
	public String getDatumDesAuftretens() {
		return datumDesAuftretens;
	}
	
	public void setDatumDesAuftretens(String datumDesAuftretens) {
		this.datumDesAuftretens = datumDesAuftretens;
	}
	
	public String getDatumDerBehebung() {
		return datumDerBehebung;
	}
	
	public void setDatumDerBehebung(String datumDerBehebung) {
		this.datumDerBehebung = datumDerBehebung;
	}
	
	public Scrumprojekt getScrumprojekt() {
		return scrumprojekt;
	}
	
	public void setScrumprojekt(Scrumprojekt scrumprojekt) {
		this.scrumprojekt = scrumprojekt;
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
