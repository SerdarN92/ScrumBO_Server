/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumbo.de.entity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Serdar
 */
public class Scrumprojekt implements Serializable {
	
	private Integer					id;
	private String					projektname;
	private String					passwort;
	private List<Benutzer>			benutzer;
	private List<Sprint>			sprint			= new LinkedList<Sprint>();
	private List<Impediment>		impediment		= new LinkedList<Impediment>();
	private List<ProductBacklog>	productbacklog	= new LinkedList<ProductBacklog>();
	
	public Scrumprojekt() {
	}
	
	public Scrumprojekt(Integer id) {
		this.id = id;
	}
	
	public Scrumprojekt(Integer id, String projektname) {
		this.id = id;
		this.projektname = projektname;
	}
	
	public Integer getScrumProjektID() {
		return id;
	}
	
	public void setScrumProjektID(Integer id) {
		this.id = id;
	}
	
	public String getProjektname() {
		return projektname;
	}
	
	public void setProjektname(String projektname) {
		this.projektname = projektname;
	}
	
	public String getPasswort() {
		return passwort;
	}
	
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}
	
	public List<Benutzer> getBenutzer() {
		return benutzer;
	}
	
	public void setBenutzer(List<Benutzer> benutzer) {
		this.benutzer = benutzer;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public List<Sprint> getSprint() {
		return sprint;
	}
	
	public void setSprint(List<Sprint> sprint) {
		this.sprint = sprint;
	}
	
	public List<Impediment> getImpediment() {
		return impediment;
	}
	
	public void setImpediment(List<Impediment> impediment) {
		this.impediment = impediment;
	}
	
	public List<ProductBacklog> getProductbacklog() {
		return productbacklog;
	}
	
	public void setProductbacklog(List<ProductBacklog> productbacklog) {
		this.productbacklog = productbacklog;
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
		Scrumprojekt other = (Scrumprojekt) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
