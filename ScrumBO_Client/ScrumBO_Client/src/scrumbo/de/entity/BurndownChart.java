package scrumbo.de.entity;

import java.io.Serializable;
import java.util.List;

public class BurndownChart implements Serializable {
	
	private Integer						id;
	private Integer						days;
	private List<BurndownChartPoint>	burndownChartPoint;
										
	public BurndownChart() {
	
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getDays() {
		return days;
	}
	
	public void setDays(Integer days) {
		this.days = days;
	}
	
	public List<BurndownChartPoint> getBurndownChartPoint() {
		return burndownChartPoint;
	}
	
	public void setBurndownChartPoint(List<BurndownChartPoint> burndownChartPoint) {
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
		BurndownChart other = (BurndownChart) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
