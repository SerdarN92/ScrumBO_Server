package model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

/*
 * Entität für die Tabelle "Scrumprojekt".
 */

@Entity
@Table(name = "PROJECT")
public class Project {
	
	@Id
	@GeneratedValue
	@Column(name = "project_id", unique = true, nullable = false)
	private Integer				id;
								
	@Column(name = "projectname", unique = true, nullable = false)
	private String				projectname;
								
	@Column(name = "password", nullable = false)
	private String				password;
								
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.EAGER, mappedBy = "project")
	@Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE,
			org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST,
			org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
	private List<Sprint>		sprint	= new LinkedList<Sprint>();
										
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.EAGER, mappedBy = "project")
	@Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE,
			org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST,
			org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
	private List<Impediment>	impediment;
								
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private ProductBacklog		productbacklog;
								
	public Project() {
	
	}
	
	public Project(String projectname, String password) {
		this.projectname = projectname;
		this.password = password;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getProjectname() {
		return projectname;
	}
	
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
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
