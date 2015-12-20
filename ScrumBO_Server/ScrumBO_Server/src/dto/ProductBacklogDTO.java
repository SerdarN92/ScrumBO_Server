package dto;

import java.io.Serializable;
import java.util.List;

public class ProductBacklogDTO implements Serializable {
	
	private Integer				id;
	private List<UserStoryDTO>	userstory;
								
	public ProductBacklogDTO() {
	
	}
	
	public ProductBacklogDTO(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public List<UserStoryDTO> getUserstory() {
		return userstory;
	}
	
	public void setUserstory(List<UserStoryDTO> userstory) {
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
		ProductBacklogDTO other = (ProductBacklogDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
