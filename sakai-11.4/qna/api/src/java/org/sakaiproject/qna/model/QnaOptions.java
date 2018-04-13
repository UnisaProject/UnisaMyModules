/**
 * Copyright (c) 2007-2009 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sakaiproject.qna.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.sakaiproject.qna.model.constants.QnaConstants;

/**
 * This is a the options table entity
 *
 */
public class QnaOptions {

    private String id;

//  The list of custom email addresses associated with this option
    private Set<QnaCustomEmail> customEmails = new HashSet<QnaCustomEmail>();

//	The user (sakai userid) that changed this options
    private String ownerId;

//	Sakai entity reference
    private String location;

//	The date these options was last modified by someone
    private Date dateLastModified;

//	The date these options was created
    private Date dateCreated;

//  Can participants post questions anonymously
    private Boolean anonymousAllowed;

//  Are the questions moderated on this site
    private Boolean moderated;

//  Should email notifications be sent when new questions are asked
    private Boolean emailNotification;

//  The type of email notification
    private String emailNotificationType;

//  The default view presented to students
    private String defaultStudentView;

//  This is the comma separated string used by the front-end to create the custom emails    
    private String commaSeparated;
    
//	Allow new questions/answers from unknown mobile numbers
    private Boolean allowUnknownMobile;

//  Number of answers returned by mobile command 
    private Integer mobileAnswersNr;

//	Should SMS Notification be sent? Only used for new answers currently    
    private Boolean smsNotification;
    
	/**
	 *	Empty constructor
	 */
	public QnaOptions() {
	}

	/**
	 * @param ownerId
	 * @param location
	 * @param dateLastModified
	 * @param dateCreated
	 * @param anonymousAllowed
	 * @param moderated
	 * @param emailNotification
	 * @param emailNotificationType
	 * @param defaultStudentView
	 * @param allowUnknownMobileNr TODO
	 * @param id
	 */
	public QnaOptions(String ownerId, String location,
			Date dateLastModified, Date dateCreated, Boolean anonymousAllowed,
			Boolean moderated, Boolean emailNotification,
			String emailNotificationType, String defaultStudentView, Boolean allowUnknownMobile) {
		this.ownerId = ownerId;
		this.location = location;
		this.dateLastModified = dateLastModified;
		this.dateCreated = dateCreated;
		this.anonymousAllowed = anonymousAllowed;
		this.moderated = moderated;
		this.emailNotification = emailNotification;
		this.emailNotificationType = emailNotificationType;
		this.defaultStudentView = defaultStudentView;
		this.allowUnknownMobile = allowUnknownMobile;
		this.mobileAnswersNr = 1;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the ownerId
	 */
	public String getOwnerId() {
		return ownerId;
	}

	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the dateLastModified
	 */
	public Date getDateLastModified() {
		return dateLastModified;
	}

	/**
	 * @param dateLastModified the dateLastModified to set
	 */
	public void setDateLastModified(Date dateLastModified) {
		this.dateLastModified = dateLastModified;
	}

	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * @return the anonymousAllowed
	 */
	public Boolean getAnonymousAllowed() {
		return anonymousAllowed;
	}

	/**
	 * @param anonymousAllowed the anonymousAllowed to set
	 */
	public void setAnonymousAllowed(Boolean anonymousAllowed) {
		this.anonymousAllowed = anonymousAllowed;
	}

	/**
	 * @return the moderated
	 */
	public Boolean isModerated() {
		return moderated;
	}

	/**
	 * @param moderated the moderated to set
	 */
	public void setModerated(Boolean moderated) {
		this.moderated = moderated;
	}

	/**
	 * @return the emailNotification
	 */
	public Boolean getEmailNotification() {
		return emailNotification;
	}

	/**
	 * @param emailNotification the emailNotification to set
	 */
	public void setEmailNotification(Boolean emailNotification) {
		this.emailNotification = emailNotification;
	}

	/**
	 * @return the emailNotificationType
	 */
	public String getEmailNotificationType() {
		return emailNotificationType; 
	}

	/**
	 * @param emailNotificationType the emailNotificationType to set
	 */
	public void setEmailNotificationType(String emailNotificationType) {
		if (emailNotificationType != null) {
			
			if (emailNotificationType.equals(QnaConstants.CUSTOM_LIST) || 
				emailNotificationType.equals(QnaConstants.SITE_CONTACT) ||
				emailNotificationType.equals(QnaConstants.UPDATE_RIGHTS)) {
				this.emailNotificationType = emailNotificationType;
			} else {
				throw new IllegalArgumentException("Invalid notification type provided");
			}
		}
	}

	/**
	 * @return the defaultStudentView
	 */
	public String getDefaultStudentView() {
		return defaultStudentView;
	}

	/**
	 * @param defaultStudentView the defaultStudentView to set
	 */
	public void setDefaultStudentView(String defaultStudentView) {
		
		if (defaultStudentView.equals(QnaConstants.CATEGORY_VIEW) || defaultStudentView.equals(QnaConstants.MOST_POPULAR_VIEW)) {
			this.defaultStudentView = defaultStudentView;
		} else {
			throw new IllegalArgumentException("Invalid default student view option provided");
		}
	}

	/**
	 * @return the customEmails
	 */
	public Set<QnaCustomEmail> getCustomEmails() {		
		return customEmails;
	}

	/**
	 * @param customEmails the customEmails to set
	 */
	public void setCustomEmails(Set<QnaCustomEmail> customEmails) {
		this.customEmails = customEmails;
	}

	public void addCustomEmail(QnaCustomEmail customEmail) {
		customEmail.setOptions(this);
		customEmails.add(customEmail);
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
		QnaOptions other = (QnaOptions) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	// Used for the front-end
	public String getCustomEmailDisplay() {
		if (getCustomEmails().size() == 0) {
			return "";
		}
		
		StringBuilder displayed = new StringBuilder();
		for (QnaCustomEmail email : getCustomEmails()) {
			displayed.append(email.getEmail() + ",");
		}
		
		// Remove last comma
		displayed.replace(displayed.length()-1, displayed.length(), "");
		
		return displayed.toString();
	}
	
	public String getCommaSeparated() {
		return commaSeparated;
	}

	public void setCommaSeparated(String commaSeparated) {
		this.commaSeparated = commaSeparated;
	}

	public Boolean getAllowUnknownMobile() {
		return allowUnknownMobile;
	}

	public void setAllowUnknownMobile(Boolean allowUnknownMobile) {
		this.allowUnknownMobile = allowUnknownMobile;
	}

	public Integer getMobileAnswersNr() {
		if (mobileAnswersNr == null) {
			return 0;
		}
		return mobileAnswersNr;
	}

	public void setMobileAnswersNr(Integer mobileAnswersNr) {
		this.mobileAnswersNr = mobileAnswersNr;
	}

	/**
	 * Send question answers by SMS to the user who asked the question, if the question was submitted by SMS.
	 * @return
	 */
	public Boolean getSmsNotification() {
		return smsNotification;
	}

	/**
	 * Send question answers by SMS to the user who asked the question, if the question was submitted by SMS.
	 * @return
	 */
	public void setSmsNotification(Boolean smsNotification) {
		this.smsNotification = smsNotification;
	}
	
}
