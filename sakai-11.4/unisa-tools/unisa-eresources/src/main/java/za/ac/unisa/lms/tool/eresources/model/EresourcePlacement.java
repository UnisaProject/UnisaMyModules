package za.ac.unisa.lms.tool.eresources.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.appfuse.model.BaseObject;

@Entity
@Table(name = "LIBRESPLA")
@AssociationOverrides({
		@AssociationOverride(name = "pk.placement", 
			joinColumns = @JoinColumn(name = "placementId")),
		@AssociationOverride(name = "pk.eresource", 
			joinColumns = @JoinColumn(name = "eresourceId")) })

public class EresourcePlacement extends BaseObject implements java.io.Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1917871187571957157L;

	private EresourcePlacementId pk = new EresourcePlacementId();
	private Date startDate;
	private Date endDate;
	
	public EresourcePlacement() {
	}
 
	@EmbeddedId
	public EresourcePlacementId getPk() {
		return pk;
	}
 
	public void setPk(EresourcePlacementId pk) {
		this.pk = pk;
	}
 
	@Transient
	public Placement getPlacement() {
		return getPk().getPlacement();
	}
 
	public void setPlacement(Placement placement) {
		getPk().setPlacement(placement);
	}
 
	@Transient
	public Eresource getEresource() {
		return getPk().getEresource();
	}
 
	public void setEresource(Eresource eresource) {
		getPk().setEresource(eresource);
	}
 
	@Temporal(TemporalType.DATE)
	@Column(name = "STARTDATE", nullable = false, length = 10)
	public Date getStartDate() {
		return this.startDate;
	}
 
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
 
	@Temporal(TemporalType.DATE)
	@Column(name = "ENDDATE", nullable = false, length = 10)
	public Date getEndDate() {
		return this.endDate;
	}
 
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
 
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
 
		EresourcePlacement that = (EresourcePlacement) o;
 
		if (getPk() != null ? !getPk().equals(that.getPk())
				: that.getPk() != null)
			return false;
 
		return true;
	}
 
	public int hashCode() {
		return (getPk() != null ? getPk().hashCode() : 0);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EresourcePlacement [pk=" + pk + ", startDate=" + startDate
				+ ", endDate=" + endDate + "]";
	}

}
