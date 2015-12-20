package dto;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ScrumprojektDTO implements Serializable {
	
	private Integer				id;
	private String				projektname;
	private String				passwort;
	// private List<BenutzerDTO> benutzer = new LinkedList<BenutzerDTO>();
	private List<SprintDTO>		sprint			= new LinkedList<SprintDTO>();
	private List<ImpedimentDTO>	impediment		= new LinkedList<ImpedimentDTO>();
	private ProductBacklogDTO	productbacklog	= new ProductBacklogDTO();
												
	public ScrumprojektDTO() {
	
	}
	
	public ScrumprojektDTO(Integer id, String projektname, String passwort) {
		this.id = id;
		this.projektname = projektname;
		this.passwort = passwort;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
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
	
	// public List<BenutzerDTO> getBenutzer() {
	// return benutzer;
	// }
	//
	// public void setBenutzer(List<BenutzerDTO> benutzer) {
	// this.benutzer = benutzer;
	// }
	
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
		ScrumprojektDTO other = (ScrumprojektDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
