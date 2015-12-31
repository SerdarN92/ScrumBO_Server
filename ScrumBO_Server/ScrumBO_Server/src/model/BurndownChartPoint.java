package model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/*
 * Entität für die Tabelle "BurndownChartPoint".
 */

@Entity
@Table(name = "BURNDOWNCHARTPOINT")
public class BurndownChartPoint {
	
	@Id
	@GeneratedValue
	@Column(name = "burndownchartpoint_id", unique = true, nullable = false)
	private Integer			id;
							
	@Column(name = "x", nullable = true)
	private Integer			x;
							
	@Column(name = "y", nullable = true)
	private Integer			y;
							
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "burndownchart_id", nullable = false)
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
