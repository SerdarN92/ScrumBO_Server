package scrumbo.de.entity;

import java.io.Serializable;

public class BurndownChartPoint implements Serializable {
	
	private Integer			id;
	private Integer			x;
	private Integer			y;
	private BurndownChart	burndownChart;
							
	public BurndownChartPoint() {
	
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getX() {
		return x;
	}
	
	public void setX(Integer x) {
		this.x = x;
	}
	
	public Integer getY() {
		return y;
	}
	
	public void setY(Integer y) {
		this.y = y;
	}
	
	public BurndownChart getBurndownChart() {
		return burndownChart;
	}
	
	public void setBurndownChart(BurndownChart burndownChart) {
		this.burndownChart = burndownChart;
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
		BurndownChartPoint other = (BurndownChartPoint) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
