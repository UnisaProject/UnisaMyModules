/**
 * 
 */
package za.ac.unisa.lms.tool.eresources.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.appfuse.model.BaseObject;

/**
 * @author TMasibm
 *
 */

@Entity
@Table(name = "LIBSUBJ",uniqueConstraints = {
		@UniqueConstraint(columnNames = "Subject")})

public class Subject extends BaseObject implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8572763081748255499L;
	private Long subjectId;
	private String subjectName;
	private boolean enabled;
	private Set<Eresource> eresources = new HashSet<Eresource>(0);
	
	/**
	 * @param eresourceSubject the eresourceSubject to set
	 */
	public Subject() {
	
	}

	public Subject(Long subjectId, String subjectName, boolean isEnabled) {
		super();
		this.subjectId = subjectId;
		this.subjectName = subjectName;
		this.enabled = isEnabled;
	}


	/**
	 * @param subjectId
	 * @param subjectName
	 * @param isEnabled
	 * @param eresources
	 */
	public Subject(Long subjectId, String subjectName, boolean isEnabled,
			Set<Eresource> eresources) {
		this.subjectId = subjectId;
		this.subjectName = subjectName;
		this.enabled = isEnabled;
		this.eresources = eresources;
	}

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "subject")
	public Set<Eresource> getEresources() {
		return eresources;
	}

	public void setEresources(Set<Eresource> eresources) {
		this.eresources = eresources;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SubjId", nullable = false)
	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	@Column(name = "Subject", nullable = false, length = 250)
	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	@Column(name = "Enabled", unique = false, nullable = false, length = 3)
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.enabled = isEnabled;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Subject [subjectId=" + subjectId + ", subjectName="
				+ subjectName + ", isEnabled=" + enabled + ", eresources="
				+ eresources + "]";
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((eresources == null) ? 0 : eresources.hashCode());
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result
				+ ((subjectId == null) ? 0 : subjectId.hashCode());
		result = prime * result
				+ ((subjectName == null) ? 0 : subjectName.hashCode());
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
		if (!(obj instanceof Subject)) {
			return false;
		}
		Subject other = (Subject) obj;
		if (eresources == null) {
			if (other.eresources != null) {
				return false;
			}
		} else if (!eresources.equals(other.eresources)) {
			return false;
		}
		if (enabled != other.enabled) {
			return false;
		}
		if (subjectId == null) {
			if (other.subjectId != null) {
				return false;
			}
		} else if (!subjectId.equals(other.subjectId)) {
			return false;
		}
		if (subjectName == null) {
			if (other.subjectName != null) {
				return false;
			}
		} else if (!subjectName.equals(other.subjectName)) {
			return false;
		}
		return true;
	}
	
}
