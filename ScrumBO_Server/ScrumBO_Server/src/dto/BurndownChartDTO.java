package dto;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class BurndownChartDTO implements Serializable {
	
	private Integer						id;
	private Integer						tage;
	private List<BurndownChartPointDTO>	burndownChartPoint	= new LinkedList<BurndownChartPointDTO>();
															
	public BurndownChartDTO() {
	
	}
	
	public BurndownChartDTO(Integer id, Integer tage) {
		this.id = id;
		this.tage = tage;
	}
	
	public BurndownChartDTO(Integer id, Integer tage, List<BurndownChartPointDTO> burndownChartPoint) {
		this.id = id;
		this.tage = tage;
		this.burndownChartPoint = burndownChartPoint;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getTage() {
		return tage;
	}
	
	public void setTage(Integer tage) {
		this.tage = tage;
	}
	
	public List<BurndownChartPointDTO> getBurndownChartPoint() {
		return burndownChartPoint;
	}
	
	public void setBurndownChartPoint(List<BurndownChartPointDTO> burndownChartPoint) {
		this.burndownChartPoint = burndownChartPoint;
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
		BurndownChartDTO other = (BurndownChartDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
