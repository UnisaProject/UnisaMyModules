/**
 * 
 */
package za.ac.unisa.lms.tool.eresources.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.appfuse.model.BaseObject;

/**
 * @author TMasibm
 *
 */
@Entity
@Table(name = "LIBVENDOR", uniqueConstraints = {
		@UniqueConstraint(columnNames = "Vendorname"),
		@UniqueConstraint(columnNames = "Offcampusurl"),
		@UniqueConstraint(columnNames = "Oncampusurl"),
		@UniqueConstraint(columnNames = "Logofile"),
		@UniqueConstraint(columnNames = "Logourl")})

public class Vendor extends BaseObject implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 629902671918694160L;
	private Long vendorId;
	private String vendorName;
	private String offCampusURL;
	private String onCampusURL;
	private String  logoFile; 
	private String logoURL; 
	private boolean enabled;
//	private Set<Eresource> eresources = new HashSet<Eresource>(0);

	
	public Vendor() {
		
	}

	public Vendor(Long vendorId, String vendorName, String offCampusURL,
			String onCampusURL, String logoFile, String logoURL,
			boolean isEnabled) {
		super();
		this.vendorId = vendorId;
		this.vendorName = vendorName;
		this.offCampusURL = offCampusURL;
		this.onCampusURL = onCampusURL;
		this.logoFile = logoFile;
		this.logoURL = logoURL;
		this.enabled = isEnabled;
	}

	public Vendor(Long vendorId, String vendorName, String offCampusURL,
			String onCampusURL, String logoFile, String logoURL,
			boolean isEnabled, Set<Eresource> eresources) {
		super();
		this.vendorId = vendorId;
		this.vendorName = vendorName;
		this.offCampusURL = offCampusURL;
		this.onCampusURL = onCampusURL;
		this.logoFile = logoFile;
		this.logoURL = logoURL;
		this.enabled = isEnabled;
	//	this.eresources = eresources;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "VendorId", nullable = false)
	public Long getVendorId() {
		return vendorId;
	
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	@Column(name = "Vendorname", nullable = false, length = 50)
	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Column(name = "Offcampusurl", length = 150)
	public String getOffCampusURL() {
		return offCampusURL;
	}

	public void setOffCampusURL(String offCampusURL) {
		this.offCampusURL = offCampusURL;
	}

	@Column(name = "Oncampusurl",  length = 150)
	public String getOnCampusURL() {
		return onCampusURL;
	}

	public void setOnCampusURL(String onCampusURL) {
		this.onCampusURL = onCampusURL;
	}

	@Column(name = "Logofile", length = 20)
	public String getLogoFile() {
		return logoFile;
	}

	public void setLogoFile(String logoFile) {
		this.logoFile = logoFile;
	}

	@Column(name = "Logourl", nullable = false, length = 20)
	public String getLogoURL() {
		return logoURL;
	}

	public void setLogoURL(String logoURL) {
		this.logoURL = logoURL;
	}

	@Column(name = "Enabled", unique = false, nullable = false, length = 3)
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.enabled = isEnabled;
	}

	/*@OneToMany(fetch = FetchType.EAGER, mappedBy = "vendor")
	public Set<Eresource> getEresources() {
		return this.eresources;
	}

	public void setEresources(Set<Eresource> eresources) {
		this.eresources = eresources;
	}
*/
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Vendor [vendorId=" + vendorId + ", vendorName=" + vendorName
				+ ", offCampusURL=" + offCampusURL + ", onCampusURL="
				+ onCampusURL + ", logoFile=" + logoFile + ", logoURL="
				+ logoURL + ", isEnabled=" + enabled/* + ", eresources="
				+ eresources */+ "]";
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result/*
				+ ((eresources == null) ? 0 : eresources.hashCode())*/;
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result
				+ ((logoFile == null) ? 0 : logoFile.hashCode());
		result = prime * result + ((logoURL == null) ? 0 : logoURL.hashCode());
		result = prime * result
				+ ((offCampusURL == null) ? 0 : offCampusURL.hashCode());
		result = prime * result
				+ ((onCampusURL == null) ? 0 : onCampusURL.hashCode());
		result = prime * result
				+ ((vendorId == null) ? 0 : vendorId.hashCode());
		result = prime * result
				+ ((vendorName == null) ? 0 : vendorName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Vendor)) {
			return false;
		}
		Vendor other = (Vendor) obj;
		/*if (eresources == null) {
			if (other.eresources != null) {
				return false;
			}
		} else if (!eresources.equals(other.eresources)) {
			return false;
		}*/
		if (enabled != other.enabled) {
			return false;
		}
		if (logoFile == null) {
			if (other.logoFile != null) {
				return false;
			}
		} else if (!logoFile.equals(other.logoFile)) {
			return false;
		}
		if (logoURL == null) {
			if (other.logoURL != null) {
				return false;
			}
		} else if (!logoURL.equals(other.logoURL)) {
			return false;
		}
		if (offCampusURL == null) {
			if (other.offCampusURL != null) {
				return false;
			}
		} else if (!offCampusURL.equals(other.offCampusURL)) {
			return false;
		}
		if (onCampusURL == null) {
			if (other.onCampusURL != null) {
				return false;
			}
		} else if (!onCampusURL.equals(other.onCampusURL)) {
			return false;
		}
		if (vendorId == null) {
			if (other.vendorId != null) {
				return false;
			}
		} else if (!vendorId.equals(other.vendorId)) {
			return false;
		}
		if (vendorName == null) {
			if (other.vendorName != null) {
				return false;
			}
		} else if (!vendorName.equals(other.vendorName)) {
			return false;
		}
		return true;
	}	

}
