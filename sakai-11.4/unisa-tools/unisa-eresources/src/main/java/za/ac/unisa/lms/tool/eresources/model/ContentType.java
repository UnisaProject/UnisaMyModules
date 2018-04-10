/**
 * This is a Content Type Model class which maps a one-to-one relationship
 * with the Eresources Classes, whereby one e-resource class can have zero or one cardinality with 
 * the Content Type class.
 */
package za.ac.unisa.lms.tool.eresources.model;

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
@Table(name = "LIBTXT", uniqueConstraints = {
		@UniqueConstraint(columnNames = "TEXTDESC")})

public class ContentType extends BaseObject implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6590358793100290179L;
	private Long libTxtID;
	private String fullTxtDescr;
	//private Eresource eresource;
	private boolean enabled;
 
	public ContentType() {
	}
 

	/*@OneToOne(fetch = FetchType.LAZY, mappedBy = "contentType", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	public Eresource getEresource() {
		return eresource;
	}


	public void setEresource(Eresource eresource) {
		this.eresource = eresource;
	}*/


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TXTID",  nullable = false)
	public Long getLibTxtID() {
		return libTxtID;
	}
	public void setLibTxtID(Long libTxtID) {
		this.libTxtID = libTxtID;
	}
	
	@Column(name = "TEXTDESC",  nullable = false, length = 50)
	public String getFullTxtDescr() {
		return fullTxtDescr;
	}
	
	@Column(name = "ENABLED", unique = false, nullable = false, length = 1)
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.enabled = isEnabled;
	}

	public void setFullTxtDescr(String fullTxtDescr) {
		this.fullTxtDescr = fullTxtDescr;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ContentType [fullTxtDescr=" + fullTxtDescr + ", isEnabled="
				+ enabled + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fullTxtDescr == null) ? 0 : fullTxtDescr.hashCode());
		result = prime * result + (enabled ? 1231 : 1237);
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
		if (!(obj instanceof ContentType)) {
			return false;
		}
		ContentType other = (ContentType) obj;
		if (fullTxtDescr == null) {
			if (other.fullTxtDescr != null) {
				return false;
			}
		} else if (!fullTxtDescr.equals(other.fullTxtDescr)) {
			return false;
		}
		if (enabled != other.enabled) {
			return false;
		}
		return true;
	}

		
}
