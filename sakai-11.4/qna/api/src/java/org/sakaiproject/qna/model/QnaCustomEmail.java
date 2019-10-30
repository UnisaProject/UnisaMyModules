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


/**
 * This is a the options table entity
 *
 */
public class QnaCustomEmail {

    private String id;

//  The options this custom email is linked to
    private QnaOptions options;

//	The user (sakai userid) that posted this question
	private String ownerId;

//	The email address to which notification should be sent
	private String email;

//	The date this answer was created
	private Date dateCreated;

	/**
	 *
	 */
	public QnaCustomEmail() {
	}


	/**
	 * @param ownerId
	 * @param email
	 * @param dateCreated
	 */
	public QnaCustomEmail(String ownerId, String email, Date dateCreated) {
		this.ownerId = ownerId;
		this.email = email;
		this.dateCreated = dateCreated;
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
	 * @return the options
	 */
	public QnaOptions getOptions() {
		return options;
	}


	/**
	 * @param options the options to set
	 */
	public void setOptions(QnaOptions options) {
		this.options = options;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof QnaCustomEmail){
			return ((QnaCustomEmail)obj).getId().equals(this.getId());
		}

		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return email.hashCode();
	}
}
