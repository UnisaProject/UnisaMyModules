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
@Table(name = "LIBNEWSTITLE", uniqueConstraints = {
		@UniqueConstraint(columnNames = "NewsTitle")})

public class Newsletter extends BaseObject implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4182866094230457682L;
	private Long newsTitleID;
	private String newsTitle;
	private boolean enabled;
	//private  Eresource eresource;
	
	
	public Newsletter() {
	}

	
	public Newsletter(Long newsTitleID, String newsTitle, boolean isEnabled) {
		super();
		this.newsTitleID = newsTitleID;
		this.newsTitle = newsTitle;
		this.enabled = isEnabled;
	}


	public Newsletter(Long newsTitleID, String newsTitle, boolean isEnabled,
			Eresource eresource) {
		super();
		this.newsTitleID = newsTitleID;
		this.newsTitle = newsTitle;
		this.enabled = isEnabled;
		//this.eresource = eresource;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "NewsId", nullable = false)
	public Long getNewsTitleID() {
		return newsTitleID;
	}


	public void setNewsTitleID(Long newsTitleID) {
		this.newsTitleID = newsTitleID;
	}

	@Column(name = "newsTitle", nullable = true, length = 50)
	public String getNewsTitle() {
		return newsTitle;
	}


	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	@Column(name = "Enabled", unique = false, nullable = false, length =1)
	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean isEnabled) {
		this.enabled = isEnabled;
	}

	/*@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
		Newsletter other = (Newsletter) obj;
		/*if (eresource == null) {
			if (other.eresource != null)
				return false;
		} else if (!eresource.equals(other.eresource))
			return false;*/
		if (enabled != other.enabled)
			return false;
		if (newsTitle == null) {
			if (other.newsTitle != null)
				return false;
		} else if (!newsTitle.equals(other.newsTitle))
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
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result
				+ ((newsTitle == null) ? 0 : newsTitle.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Newsletter [newsTitle=" + newsTitle + ", isEnabled="
				+ enabled +/* ", eresource=" + eresource +*/ "]";
	}
	

}
