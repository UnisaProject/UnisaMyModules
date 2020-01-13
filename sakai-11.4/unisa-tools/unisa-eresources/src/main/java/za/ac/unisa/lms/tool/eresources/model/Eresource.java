/**
 * 
 */
package za.ac.unisa.lms.tool.eresources.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.appfuse.model.BaseObject;

/**
 * @author TMasibm
 *
 */
@Entity
@Table(name = "LIBRESOURCE")
public class Eresource  extends BaseObject implements java.io.Serializable{

	
	private static final long serialVersionUID = -9092975737352354976L;
	/**
	 * 
	 */

	private long eresourceId; 
	private String resourceName;
	private String description;
	private String onCampusUrl;
	private String offCampusUrl;
	private String mobileUrl;
	private boolean mobileAccessAvailability;
	private String  mobileAccessInfo;
	
	private String	cdRomContactInfo;
	private String trainingMaterialURL;
	private String newsLetterUrl;
	
	private boolean logonExist;
	private String login;
	private String password;
	private boolean disabilityComply;
	private String persLibWebpageURL;
	private boolean resourceDiscSysIndicator;
	private boolean trialDatabase;
	private boolean enabled;
	private Date trialStartDate;
	private Date trialEndDate;
	
	private Newsletter newsletter;
	private ContentType contentType;
	private HighlightNote highlightNote;
	
	private Vendor vendor;
	private Set<EresourcePlacement> eresourcePlacement = new HashSet<EresourcePlacement>(0);
	private Set<Subject> subject = new HashSet<Subject>(0);
	
	
	//Transient Fields
	private String vendorId;
	private String contentTypeId;
	private String newsletterId;
	private String highlightNoteId;
	private Set<String> eresourcePlacementIds;
	private Set<String> eresourceSubjectIds;
	
	
	public Eresource() {
		super();
	}

	public Eresource(long eresourceId, String resourceName, String description,
			String onCampusUrl, String offCampusUrl, String mobileUrl,
			boolean mobileAccessAvailability, String mobileAccessInfo,
			String cdRomContactInfo, String trainingMaterialURL,
			String newsLetterUrl, boolean logonExist, String login,
			String password, boolean disabilityComply,
			String persLibWebpageURL, boolean resourceDiscSysIndicator,
			boolean trialDatabase, Date trialStartDate, Date trialEndDate,
			Newsletter newsletter, ContentType contentType,
			HighlightNote highlightNote, Vendor vendor) {
		super();
		this.eresourceId = eresourceId;
		this.resourceName = resourceName;
		this.description = description;
		this.onCampusUrl = onCampusUrl;
		this.offCampusUrl = offCampusUrl;
		this.mobileUrl = mobileUrl;
		this.mobileAccessAvailability = mobileAccessAvailability;
		this.mobileAccessInfo = mobileAccessInfo;
		this.cdRomContactInfo = cdRomContactInfo;
		this.trainingMaterialURL = trainingMaterialURL;
		this.newsLetterUrl = newsLetterUrl;
		this.logonExist = logonExist;
		this.login = login;
		this.password = password;
		this.disabilityComply = disabilityComply;
		this.persLibWebpageURL = persLibWebpageURL;
		this.resourceDiscSysIndicator = resourceDiscSysIndicator;
		this.trialDatabase = trialDatabase;
		this.trialStartDate = trialStartDate;
		this.trialEndDate = trialEndDate;
		this.newsletter = newsletter;
		this.contentType = contentType;
		this.highlightNote = highlightNote;
		this.vendor = vendor;
	}


	

		
	/**
	 * @param eresourceId
	 * @param resourceName
	 * @param description
	 * @param onCampusUrl
	 * @param offCampusUrl
	 * @param mobileUrl
	 * @param mobileAccessAvailability
	 * @param mobileAccessInfo
	 * @param cdRomContactInfo
	 * @param trainingMaterialURL
	 * @param newsLetterUrl
	 * @param logonExist
	 * @param login
	 * @param password
	 * @param disabilityComply
	 * @param persLibWebpageURL
	 * @param resourceDiscSysIndicator
	 * @param trialDatabase
	 * @param trialStartDate
	 * @param trialEndDate
	 * @param newsletter
	 * @param contentType
	 * @param highlightNote
	 * @param vendor
	 * @param eresourcePlacement
	 * @param subject
	 */
	public Eresource(long eresourceId, String resourceName, String description,
			String onCampusUrl, String offCampusUrl, String mobileUrl,
			boolean mobileAccessAvailability, String mobileAccessInfo,
			String cdRomContactInfo, String trainingMaterialURL,
			String newsLetterUrl, boolean logonExist, String login,
			String password, boolean disabilityComply,
			String persLibWebpageURL, boolean resourceDiscSysIndicator,
			boolean trialDatabase, Date trialStartDate, Date trialEndDate,
			Newsletter newsletter, ContentType contentType,
			HighlightNote highlightNote, Vendor vendor,
			Set<EresourcePlacement> eresourcePlacement, Set<Subject> subject) {
		this.eresourceId = eresourceId;
		this.resourceName = resourceName;
		this.description = description;
		this.onCampusUrl = onCampusUrl;
		this.offCampusUrl = offCampusUrl;
		this.mobileUrl = mobileUrl;
		this.mobileAccessAvailability = mobileAccessAvailability;
		this.mobileAccessInfo = mobileAccessInfo;
		this.cdRomContactInfo = cdRomContactInfo;
		this.trainingMaterialURL = trainingMaterialURL;
		this.newsLetterUrl = newsLetterUrl;
		this.logonExist = logonExist;
		this.login = login;
		this.password = password;
		this.disabilityComply = disabilityComply;
		this.persLibWebpageURL = persLibWebpageURL;
		this.resourceDiscSysIndicator = resourceDiscSysIndicator;
		this.trialDatabase = trialDatabase;
		this.trialStartDate = trialStartDate;
		this.trialEndDate = trialEndDate;
		this.newsletter = newsletter;
		this.contentType = contentType;
		this.highlightNote = highlightNote;
		this.vendor = vendor;
		this.eresourcePlacement = eresourcePlacement;
		this.subject = subject;
	}

	@ManyToMany//(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "LIBRESSUBJ", joinColumns = { 
			@JoinColumn(name = "eresourceId", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "SubjId", 
					nullable = false, updatable = false) })
	public Set<Subject> getSubject() {
		return this.subject;
	}

	public void setSubject(Set<Subject> subject) {
		this.subject = subject;
	}
	
	@OneToOne//(fetch = FetchType.LAZY)
	@JoinColumn(name = "TXTID", nullable = false)
	public ContentType getContentType() {
		return this.contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	@OneToOne//(fetch = FetchType.LAZY)
	@JoinColumn(name = "NewsId", nullable = false)
	public Newsletter getNewsletter() {
		return newsletter;
	}

	public void setNewsletter(Newsletter newsletter) {
		this.newsletter = newsletter;
	}

	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "highlightNoteId", nullable = false)
	public HighlightNote getHighlightNote() {
		return highlightNote;
	}

	public void setHighlightNote(HighlightNote highlightNote) {
		this.highlightNote = highlightNote;
	}

	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "VendorId", nullable = false)
	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	@OneToMany // removed Lazy
	public Set<EresourcePlacement> getEresourcePlacement() {
		return this.eresourcePlacement;
	}

	public void setEresourcePlacement(Set<EresourcePlacement> eresourcePlacement) {
		this.eresourcePlacement = eresourcePlacement;
	}


	/**
	 * @return the eresourceId
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "eresourceId",nullable = false)
	public long getEresourceId() {
		return this.eresourceId;
	}


	/**
	 * @param eresourceId the eresourceId to set
	 */
	public void setEresourceId(long eresourceId) {
		this.eresourceId = eresourceId;
	}


	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}


	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the onCampusUrl
	 */
	public String getOnCampusUrl() {
		return onCampusUrl;
	}


	/**
	 * @param onCampusUrl the onCampusUrl to set
	 */
	public void setOnCampusUrl(String onCampusUrl) {
		this.onCampusUrl = onCampusUrl;
	}


	/**
	 * @return the offCampusUrl
	 */
	public String getOffCampusUrl() {
		return offCampusUrl;
	}


	/**
	 * @param offCampusUrl the offCampusUrl to set
	 */
	public void setOffCampusUrl(String offCampusUrl) {
		this.offCampusUrl = offCampusUrl;
	}


	/**
	 * @return the mobileUrl
	 */
	public String getMobileUrl() {
		return mobileUrl;
	}


	/**
	 * @param mobileUrl the mobileUrl to set
	 */
	public void setMobileUrl(String mobileUrl) {
		this.mobileUrl = mobileUrl;
	}


	/**
	 * @return the mobileAccessAvailability
	 */
	@Basic
	@Column(name = "mobileAccessAvailability",  length = 1)
	public boolean isMobileAccessAvailability() {
		return mobileAccessAvailability;
	}


	/**
	 * @param mobileAccessAvailability the mobileAccessAvailability to set
	 */
	public void setMobileAccessAvailability(boolean mobileAccessAvailability) {
		this.mobileAccessAvailability = mobileAccessAvailability;
	}


	/**
	 * @return the mobileAccessInfo
	 */
	public String getMobileAccessInfo() {
		return mobileAccessInfo;
	}


	/**
	 * @param mobileAccessInfo the mobileAccessInfo to set
	 */
	public void setMobileAccessInfo(String mobileAccessInfo) {
		this.mobileAccessInfo = mobileAccessInfo;
	}


	/**
	 * @return the cdRomContactInfo
	 */
	public String getCdRomContactInfo() {
		return cdRomContactInfo;
	}


	/**
	 * @param cdRomContactInfo the cdRomContactInfo to set
	 */
	public void setCdRomContactInfo(String cdRomContactInfo) {
		this.cdRomContactInfo = cdRomContactInfo;
	}


	/**
	 * @return the trainingMaterialURL
	 */
	public String getTrainingMaterialURL() {
		return trainingMaterialURL;
	}


	/**
	 * @param trainingMaterialURL the trainingMaterialURL to set
	 */
	public void setTrainingMaterialURL(String trainingMaterialURL) {
		this.trainingMaterialURL = trainingMaterialURL;
	}


	/**
	 * @return the newsLetterUrl
	 */
	public String getNewsLetterUrl() {
		return newsLetterUrl;
	}


	/**
	 * @param newsLetterUrl the newsLetterUrl to set
	 */
	public void setNewsLetterUrl(String newsLetterUrl) {
		this.newsLetterUrl = newsLetterUrl;
	}


	/**
	 * @return the logonExist
	 */
	@Basic
	@Column(name = "logonExist",  length = 1)
	public boolean isLogonExist() {
		return logonExist;
	}


	/**
	 * @param logonExist the logonExist to set
	 */
	public void setLogonExist(boolean logonExist) {
		this.logonExist = logonExist;
	}


	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}


	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}


	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @return the disabilityComply
	 */
	@Basic
	@Column(name = "disabilityComply",  length = 1)
	public boolean isDisabilityComply() {
		return disabilityComply;
	}


	/**
	 * @param disabilityComply the disabilityComply to set
	 */
	public void setDisabilityComply(boolean disabilityComply) {
		this.disabilityComply = disabilityComply;
	}


	/**
	 * @return the persLibWebpageURL
	 */
	public String getPersLibWebpageURL() {
		return persLibWebpageURL;
	}


	/**
	 * @param persLibWebpageURL the persLibWebpageURL to set
	 */
	public void setPersLibWebpageURL(String persLibWebpageURL) {
		this.persLibWebpageURL = persLibWebpageURL;
	}


	/**
	 * @return the resourceDiscSysIndicator
	 */
	@Basic
	@Column(name = "resourceDiscSysIndicator", length = 1)
	public boolean isResourceDiscSysIndicator() {
		return resourceDiscSysIndicator;
	}


	/**
	 * @param resourceDiscSysIndicator the resourceDiscSysIndicator to set
	 */
	public void setResourceDiscSysIndicator(
			boolean resourceDiscSysIndicator) {
		this.resourceDiscSysIndicator = resourceDiscSysIndicator;
	}

	@Basic
	@Column(name = "Enabled",  length = 1)
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the trialDatabase
	 */
	@Basic
	@Column(name = "trialDatabase",  length = 1)
	public boolean isTrialDatabase() {
		return trialDatabase;
	}


	/**
	 * @param trialDatabase the trialDatabase to set
	 */
	public void setTrialDatabase(boolean trialDatabase) {
		this.trialDatabase = trialDatabase;
	}


	/**
	 * @return the trialStartDate
	 */
	public Date getTrialStartDate() {
		return trialStartDate;
	}


	/**
	 * @param trialStartDate the trialStartDate to set
	 */
	public void setTrialStartDate(Date trialStartDate) {
		this.trialStartDate = trialStartDate;
	}


	/**
	 * @return the trialEndDate
	 */
	public Date getTrialEndDate() {
		return trialEndDate;
	}


	/**
	 * @param trialEndDate the trialEndDate to set
	 */
	public void setTrialEndDate(Date trialEndDate) {
		this.trialEndDate = trialEndDate;
	}
	
	
	/**
	 * @return the vendorId
	 */
	@Transient
	public String getVendorId() {
		return vendorId;
	}

	/**
	 * @param vendorId the vendorId to set
	 */
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	
		
	/**
	 * @return the contentTypeId
	 */
	@Transient
	public String getContentTypeId() {
		return contentTypeId;
	}

	/**
	 * @param contentTypeId the contentTypeId to set
	 */
	public void setContentTypeId(String contentTypeId) {
		this.contentTypeId = contentTypeId;
	}


	/**
	 * @return the newsletterId
	 */
	@Transient
	public String getNewsletterId() {
		return newsletterId;
	}

	/**
	 * @param newsletterId the newsletterId to set
	 */
	public void setNewsletterId(String newsletterId) {
		this.newsletterId = newsletterId;
	}
	
	

	/**
	 * @return the highlightNoteId
	 */
	@Transient
	public String getHighlightNoteId() {
		return highlightNoteId;
	}

	/**
	 * @param highlightNoteId the highlightNoteId to set
	 */
	public void setHighlightNoteId(String highlightNoteId) {
		this.highlightNoteId = highlightNoteId;
	}
	
	
	/**
	 * @return the eresourcePlacementIds
	 */
	@Transient
	public Set<String> getEresourcePlacementIds() {
		return eresourcePlacementIds;
	}

	/**
	 * @param eresourcePlacementIds the eresourcePlacementIds to set
	 */
	public void setEresourcePlacementIds(Set<String> eresourcePlacementIds) {
		this.eresourcePlacementIds = eresourcePlacementIds;
	}

	@Transient
	public Set<String> getEresourceSubjectIds() {
		return eresourceSubjectIds;
	}

	public void setEresourceSubjectIds(Set<String> eresourceSubjectIds) {
		this.eresourceSubjectIds = eresourceSubjectIds;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Eresource [eresourceId=" + eresourceId + ", resourceName="
				+ resourceName + ", description=" + description
				+ ", onCampusUrl=" + onCampusUrl + ", offCampusUrl="
				+ offCampusUrl + ", mobileUrl=" + mobileUrl
				+ ", mobileAccessAvailability=" + mobileAccessAvailability
				+ ", mobileAccessInfo=" + mobileAccessInfo
				+ ", cdRomContactInfo=" + cdRomContactInfo
				+ ", trainingMaterialURL=" + trainingMaterialURL
				+ ", newsLetterUrl=" + newsLetterUrl + ", logonExist="
				+ logonExist + ", login=" + login + ", password=" + password
				+ ", disabilityComply=" + disabilityComply
				+ ", persLibWebpageURL=" + persLibWebpageURL
				+ ", resourceDiscSysIndicator="
				+ resourceDiscSysIndicator + ", trialDatabase="
				+ trialDatabase + ", enabled=" + enabled + ", trialStartDate="
				+ trialStartDate + ", trialEndDate=" + trialEndDate
				+ ", newsletter=" + newsletter + ", contentType=" + contentType
				+ ", highlightNote=" + highlightNote + ", vendor=" + vendor
				+ ", eresourcePlacement=" + eresourcePlacement + ", subject="
				+ subject + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((cdRomContactInfo == null) ? 0 : cdRomContactInfo.hashCode());
		result = prime * result
				+ ((contentType == null) ? 0 : contentType.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + (disabilityComply ? 1231 : 1237);
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + (int) (eresourceId ^ (eresourceId >>> 32));
		result = prime
				* result
				+ ((eresourcePlacement == null) ? 0 : eresourcePlacement
						.hashCode());
		result = prime * result
				+ ((highlightNote == null) ? 0 : highlightNote.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + (logonExist ? 1231 : 1237);
		result = prime * result + (mobileAccessAvailability ? 1231 : 1237);
		result = prime
				* result
				+ ((mobileAccessInfo == null) ? 0 : mobileAccessInfo.hashCode());
		result = prime * result
				+ ((mobileUrl == null) ? 0 : mobileUrl.hashCode());
		result = prime * result
				+ ((newsLetterUrl == null) ? 0 : newsLetterUrl.hashCode());
		result = prime * result
				+ ((newsletter == null) ? 0 : newsletter.hashCode());
		result = prime * result
				+ ((offCampusUrl == null) ? 0 : offCampusUrl.hashCode());
		result = prime * result
				+ ((onCampusUrl == null) ? 0 : onCampusUrl.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime
				* result
				+ ((persLibWebpageURL == null) ? 0 : persLibWebpageURL
						.hashCode());
		result = prime * result
				+ (resourceDiscSysIndicator ? 1231 : 1237);
		result = prime * result
				+ ((resourceName == null) ? 0 : resourceName.hashCode());
		result = prime * result
				+ ((subject == null) ? 0 : subject.hashCode());
		result = prime
				* result
				+ ((trainingMaterialURL == null) ? 0 : trainingMaterialURL
						.hashCode());
		result = prime * result + (trialDatabase ? 1231 : 1237);
		result = prime * result
				+ ((trialEndDate == null) ? 0 : trialEndDate.hashCode());
		result = prime * result
				+ ((trialStartDate == null) ? 0 : trialStartDate.hashCode());
		result = prime * result + ((vendor == null) ? 0 : vendor.hashCode());
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
		if (!(obj instanceof Eresource)) {
			return false;
		}
		Eresource other = (Eresource) obj;
		if (cdRomContactInfo == null) {
			if (other.cdRomContactInfo != null) {
				return false;
			}
		} else if (!cdRomContactInfo.equals(other.cdRomContactInfo)) {
			return false;
		}
		if (contentType == null) {
			if (other.contentType != null) {
				return false;
			}
		} else if (!contentType.equals(other.contentType)) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (disabilityComply != other.disabilityComply) {
			return false;
		}
		if (enabled != other.enabled) {
			return false;
		}
		if (eresourceId != other.eresourceId) {
			return false;
		}
		if (eresourcePlacement == null) {
			if (other.eresourcePlacement != null) {
				return false;
			}
		} else if (!eresourcePlacement.equals(other.eresourcePlacement)) {
			return false;
		}
		if (highlightNote == null) {
			if (other.highlightNote != null) {
				return false;
			}
		} else if (!highlightNote.equals(other.highlightNote)) {
			return false;
		}
		if (login == null) {
			if (other.login != null) {
				return false;
			}
		} else if (!login.equals(other.login)) {
			return false;
		}
		if (logonExist != other.logonExist) {
			return false;
		}
		if (mobileAccessAvailability != other.mobileAccessAvailability) {
			return false;
		}
		if (mobileAccessInfo == null) {
			if (other.mobileAccessInfo != null) {
				return false;
			}
		} else if (!mobileAccessInfo.equals(other.mobileAccessInfo)) {
			return false;
		}
		if (mobileUrl == null) {
			if (other.mobileUrl != null) {
				return false;
			}
		} else if (!mobileUrl.equals(other.mobileUrl)) {
			return false;
		}
		if (newsLetterUrl == null) {
			if (other.newsLetterUrl != null) {
				return false;
			}
		} else if (!newsLetterUrl.equals(other.newsLetterUrl)) {
			return false;
		}
		if (newsletter == null) {
			if (other.newsletter != null) {
				return false;
			}
		} else if (!newsletter.equals(other.newsletter)) {
			return false;
		}
		if (offCampusUrl == null) {
			if (other.offCampusUrl != null) {
				return false;
			}
		} else if (!offCampusUrl.equals(other.offCampusUrl)) {
			return false;
		}
		if (onCampusUrl == null) {
			if (other.onCampusUrl != null) {
				return false;
			}
		} else if (!onCampusUrl.equals(other.onCampusUrl)) {
			return false;
		}
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		if (persLibWebpageURL == null) {
			if (other.persLibWebpageURL != null) {
				return false;
			}
		} else if (!persLibWebpageURL.equals(other.persLibWebpageURL)) {
			return false;
		}
		if (resourceDiscSysIndicator != other.resourceDiscSysIndicator) {
			return false;
		}
		if (resourceName == null) {
			if (other.resourceName != null) {
				return false;
			}
		} else if (!resourceName.equals(other.resourceName)) {
			return false;
		}
		if (subject == null) {
			if (other.subject != null) {
				return false;
			}
		} else if (!subject.equals(other.subject)) {
			return false;
		}
		if (trainingMaterialURL == null) {
			if (other.trainingMaterialURL != null) {
				return false;
			}
		} else if (!trainingMaterialURL.equals(other.trainingMaterialURL)) {
			return false;
		}
		if (trialDatabase != other.trialDatabase) {
			return false;
		}
		if (trialEndDate == null) {
			if (other.trialEndDate != null) {
				return false;
			}
		} else if (!trialEndDate.equals(other.trialEndDate)) {
			return false;
		}
		if (trialStartDate == null) {
			if (other.trialStartDate != null) {
				return false;
			}
		} else if (!trialStartDate.equals(other.trialStartDate)) {
			return false;
		}
		if (vendor == null) {
			if (other.vendor != null) {
				return false;
			}
		} else if (!vendor.equals(other.vendor)) {
			return false;
		}
		return true;
	}


}
