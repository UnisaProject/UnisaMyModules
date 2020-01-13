/**
 * 
 */
package za.ac.unisa.lms.tool.eresources.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.appfuse.model.BaseObject;

/**
 * @author TMasibm
 *
 */
@Entity
@Table(name = "LIBHIGHLIGHTNOTE")

public class HighlightNote extends BaseObject implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4596892794724506319L;
	private Long highlightNotesID;
	private String highlightNote;
	private boolean enabled;
	//private  Eresource eresource;
	
	public HighlightNote() {
	
	}

	public HighlightNote(Long highlightNotesID, String highlightNote,
			boolean isEnabled) {
		super();
		this.highlightNotesID = highlightNotesID;
		this.highlightNote = highlightNote;
		this.enabled = isEnabled;
	}

	public HighlightNote(Long highlightNotesID, String highlightNote,
			boolean isEnabled, Eresource eresource) {
		super();
		this.highlightNotesID = highlightNotesID;
		this.highlightNote = highlightNote;
		this.enabled = isEnabled;
	//	this.eresource = eresource;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "highlightNoteId", nullable = false)
	public Long getHighlightNotesID() {
		return highlightNotesID;
	}

	public void setHighlightNotesID(Long highlightNotesID) {
		this.highlightNotesID = highlightNotesID;
	}

	@Column(name = "HighlightNote", unique = true, nullable = false, length =50)
	public String getHighlightNote() {
		return highlightNote;
	}

	public void setHighlightNote(String highlightNote) {
		this.highlightNote = highlightNote;
	}

	@Column(name = "Enabled", unique = false, nullable = false, length =1)
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.enabled = isEnabled;
	}


	/*@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
>>>>>>> .r19163
	@PrimaryKeyJoinColumn
	public Eresource getEresource() {
		return eresource;
	}

	public void setEresource(Eresource eresource) {
		this.eresource = eresource;
	}
*/
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
		HighlightNote other = (HighlightNote) obj;
		/*if (eresource == null) {
			if (other.eresource != null)
				return false;
		} else if (!eresource.equals(other.eresource))
			return false;*/
		if (highlightNote == null) {
			if (other.highlightNote != null)
				return false;
		} else if (!highlightNote.equals(other.highlightNote))
			return false;
		if (enabled != other.enabled)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				/*+ ((eresource == null) ? 0 : eresource.hashCode())*/;
		result = prime * result
				+ ((highlightNote == null) ? 0 : highlightNote.hashCode());
		result = prime * result + (enabled ? 1231 : 1237);
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HighlightNote [highlightNote=" + highlightNote
				+ ", isEnabled=" + enabled + "]";
	}
	
}
