package scrumbo.de.entity;

import java.io.Serializable;
import java.util.List;

public class ProductBacklog implements Serializable {
	
	private Integer			id;
	private List<UserStory>	userstory;
							
	public ProductBacklog() {
	
	}
	
	public ProductBacklog(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
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
