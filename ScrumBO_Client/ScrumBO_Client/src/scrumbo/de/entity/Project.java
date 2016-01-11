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
public class Project implements Serializable {
	
	private Integer				id;
	private String				projectname;
	private String				password;
	private List<User>			user;
	private List<Sprint>		sprint			= new LinkedList<Sprint>();
	private List<Impediment>	impediment		= new LinkedList<Impediment>();
	private ProductBacklog		productbacklog	= new ProductBacklog();
												
	public Project() {
	}
	
	public Project(Integer id) {
		this.id = id;
	}
	
	public Project(Integer id, String projektname) {
		this.id = id;
		this.projectname = projektname;
	}
	
	public Integer getScrumProjektID() {
		return id;
	}
	
	public void setScrumProjektID(Integer id) {
		this.id = id;
	}
	
	public String getProjektname() {
		return projectname;
	}
	
	public void setProjektname(String projektname) {
		this.projectname = projektname;
	}
	
	public String getPasswort() {
		return password;
	}
	
	public void setPasswort(String passwort) {
		this.password = passwort;
	}
	
	public List<User> getBenutzer() {
		return user;
	}
	
	public void setBenutzer(List<User> benutzer) {
		this.user = benutzer;
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
	
	public ProductBacklog getProductbacklog() {
		return productbacklog;
	}
	
	public void setProductbacklog(ProductBacklog productbacklog) {
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
		Project other = (Project) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
