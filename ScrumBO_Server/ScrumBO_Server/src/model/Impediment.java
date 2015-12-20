package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "IMPEDIMENT")
public class Impediment {
	
	@Id
	@GeneratedValue
	@Column(name = "impediment_id", unique = true, nullable = false)
	private Integer			id;
							
	@Column(name = "priorit�t", nullable = false)
	private Integer			priorit�t;
							
	@Column(name = "mitarbeiter", nullable = false)
	private String			mitarbeiter;
							
	@Column(name = "beschreibung", nullable = false)
	private String			beschreibung;
							
	@Column(name = "datumdesauftretens", nullable = false)
	private String			datumDesAuftretens;
							
	@Column(name = "datumderbehebung", nullable = true)
	private String			datumDerBehebung;
							
	@Column(name = "kommentar", nullable = true)
	private String			kommentar;
							
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "scrumprojekt_id", nullable = true)
	private Scrumprojekt	scrumprojekt;
							
	public Impediment() {
	
	}
	
	public Impediment(Integer prioritaet, String mitarbeiter, String beschreibung, String datumDesAuftretens) {
		this.priorit�t = prioritaet;
		this.mitarbeiter = mitarbeiter;
		this.beschreibung = beschreibung;
		this.datumDesAuftretens = datumDesAuftretens;
		
	}
	
	public Impediment(Integer id, Integer priorit�t, String mitarbeiter, String beschreibung, String datumDesAuftretens,
			String datumDerBehebung, String kommentar) {
		this.id = id;
		this.priorit�t = priorit�t;
		this.mitarbeiter = mitarbeiter;
		this.beschreibung = beschreibung;
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
	
	public Integer getPriorit�t() {
		return priorit�t;
	}
	
	public void setPriorit�t(Integer priorit�t) {
		this.priorit�t = priorit�t;
	}
	
	public String getBeschreibung() {
		return beschreibung;
	}
	
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
	
	public String getMitarbeiter() {
		return mitarbeiter;
	}
	
	public void setMitarbeiter(String mitarbeiter) {
		this.mitarbeiter = mitarbeiter;
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
	
	public String getKommentar() {
		return kommentar;
	}
	
	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
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
