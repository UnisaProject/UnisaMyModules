/**
 * 
 */
package za.ac.unisa.lms.tool.eresources.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.appfuse.model.BaseObject;

/**
 * @author TMasibm
 *
 */
@Entity
@Table(name = "LIBPLACEMENT", uniqueConstraints = {
		@UniqueConstraint(columnNames = "Placement"),
		@UniqueConstraint(columnNames = "DisplayOrder")})

public class Placement extends BaseObject implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6471179051642210822L;
	private Long placementId;
	private String placement;
	private int displayOrder;
	private boolean enabled;
	private Set<EresourcePlacement> eresourcePlacement = new HashSet<EresourcePlacement>(0);
	
	public Placement() {
	
	}

	public Placement(Long placementId, String placement, int displayOrder,
			boolean isEnabled) {
		super();
		this.placementId = placementId;
		this.placement = placement;
		this.displayOrder = displayOrder;
		this.enabled = isEnabled;
	}

	public Placement(Long placementId, String placement, int displayOrder,
			boolean isEnabled, Set<EresourcePlacement> eresourcePlacement) {
		super();
		this.placementId = placementId;
		this.placement = placement;
		this.displayOrder = displayOrder;
		this.enabled = isEnabled;
		this.eresourcePlacement = eresourcePlacement;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Id", nullable = false)
	public Long getPlacementId() {
		return placementId;
	}

	
	public void setPlacementId(Long placementId) {
		this.placementId = placementId;
	}

	@Column(name = "Placement", nullable = false, length = 50)
	public String getPlacement() {
		return placement;
	}

	public void setPlacement(String placement) {
		this.placement = placement;
	}

	@Column(name = "DisplayOrder", nullable = false, length = 3)
	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	@Column(name = "Enabled", unique = false, nullable = false, length = 3)
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.enabled = isEnabled;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "pk.placement", cascade=CascadeType.ALL)
	public Set<EresourcePlacement> getEresourcePlacement() {
		return this.eresourcePlacement;
	}

	public void setEresourcePlacement(Set<EresourcePlacement> eresourcePlacement) {
		this.eresourcePlacement = eresourcePlacement;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + displayOrder;
		result = prime
				* result
				+ ((eresourcePlacement == null) ? 0 : eresourcePlacement
						.hashCode());
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result
				+ ((placement == null) ? 0 : placement.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Placement other = (Placement) obj;
		if (displayOrder != other.displayOrder)
			return false;
		if (eresourcePlacement == null) {
			if (other.eresourcePlacement != null)
				return false;
		} else if (!eresourcePlacement.equals(other.eresourcePlacement))
			return false;
		if (enabled != other.enabled)
			return false;
		if (placement == null) {
			if (other.placement != null)
				return false;
		} else if (!placement.equals(other.placement))
			return false;
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Placement [placement=" + placement + ", displayOrder="
				+ displayOrder + ", isEnabled=" + enabled
				+ ", eresourcePlacement=" + eresourcePlacement + "]";
	}

	
}
