package dto;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ProjectDTO implements Serializable {
	
	private Integer				id;
	private String				projectname;
	private String				password;
	private List<SprintDTO>		sprint			= new LinkedList<SprintDTO>();
	private List<ImpedimentDTO>	impediment		= new LinkedList<ImpedimentDTO>();
	private ProductBacklogDTO	productbacklog	= new ProductBacklogDTO();
												
	public ProjectDTO() {
	
	}
	
	public ProjectDTO(Integer id, String projectname, String password) {
		this.id = id;
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
	
	public List<SprintDTO> getSprint() {
		return sprint;
	}
	
	public void setSprint(List<SprintDTO> sprint) {
		this.sprint = sprint;
	}
	
	public List<ImpedimentDTO> getImpediment() {
		return impediment;
	}
	
	public void setImpediment(List<ImpedimentDTO> impediment) {
		this.impediment = impediment;
	}
	
	public ProductBacklogDTO getProductbacklog() {
		return productbacklog;
	}
	
	public void setProductbacklog(ProductBacklogDTO productbacklog) {
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
		ProjectDTO other = (ProjectDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
