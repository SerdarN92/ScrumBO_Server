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
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

/*
 * Entität für die Tabelle "BurndownChart".
 */

@Entity
@Table(name = "BURNDOWNCHART")
public class BurndownChart {
	
	@Id
	@GeneratedValue
	@Column(name = "burndownchart_id", unique = true, nullable = false)
	private Integer						id;
										
	@Column(name = "days", nullable = true)
	private Integer						days;
										
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.EAGER, mappedBy = "burndownChart")
	@Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE,
			org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST,
			org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
	private List<BurndownChartPoint>	burndownChartPoint	= new LinkedList<BurndownChartPoint>();
															
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
