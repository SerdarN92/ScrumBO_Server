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

@Entity
@Table(name = "PRODUCTBACKLOG")
public class ProductBacklog {
	
	@Id
	@GeneratedValue
	@Column(name = "productbacklog_id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "version", nullable = false)
	private Integer version;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "scrumprojekt_id", nullable = true)
	private Scrumprojekt scrumprojekt;
	
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.EAGER, mappedBy = "productbacklog")
	@Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE,
			org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST,
			org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
	private List<UserStory> userstory;
	
	public ProductBacklog() {
	
	}
	
	public ProductBacklog(Integer version) {
		this.version = version;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getVersion() {
		return version;
	}
	
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public Scrumprojekt getScrumprojekt() {
		return scrumprojekt;
	}
	
	public void setScrumprojekt(Scrumprojekt scrumprojekt) {
		this.scrumprojekt = scrumprojekt;
	}
	
	public List<UserStory> getUserstory() {
		return userstory;
	}
	
	public void setUserstory(List<UserStory> userstory) {
		this.userstory = userstory;
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
		ProductBacklog other = (ProductBacklog) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
