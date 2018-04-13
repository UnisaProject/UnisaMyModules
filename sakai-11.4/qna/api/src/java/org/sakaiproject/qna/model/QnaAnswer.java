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
 * This is a the QnaAnswer table entity
 * 
 */
public class QnaAnswer {

	private Long id;

	// The question this answer is linked to
	private QnaQuestion question;

	// The user (sakai userid) that posted this question
	private String ownerId;

	// The mobile number used to post answer (in case of anonymous postings via mobile)
	private String ownerMobileNr;
	
	// The actual answer text
	private String answerText;

	// The user (sakai userid) that last modified this question
	private String lastModifierId;

	// The date this answer was last modified by someone
	private Date dateLastModified;

	// The date this answer was created
	private Date dateCreated;

	// Is this answer approved yet
	private Boolean approved;

	// Is this answer a private reply to the user that posted the question
	private Boolean privateReply;

	// Is this answer anonymous (TAKE NOTE: not used for anonymous postings from mobile)
	private Boolean anonymous;

	/**
	 * Default constructor
	 */
	public QnaAnswer() {
	}

	/**
	 * Full constructor
	 */
	public QnaAnswer(QnaQuestion question, String ownerId, String answerText,
			Boolean approved, Boolean privateReply, Boolean anonymous) {
		this.question = question;
		this.ownerId = ownerId;
		this.answerText = answerText;
		this.dateLastModified = new Date();
		this.dateCreated = new Date();
		this.approved = approved;
		this.privateReply = privateReply;
		this.anonymous = anonymous;
		this.lastModifierId = ownerId;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the question
	 */
	public QnaQuestion getQuestion() {
		return question;
	}

	/**
	 * @param question
	 *            the question to set
	 */
	public void setQuestion(QnaQuestion question) {
		this.question = question;
	}

	/**
	 * @return the ownerId
	 */
	public String getOwnerId() {
		return ownerId;
	}

	/**
	 * @param ownerId
	 *            the ownerId to set
	 */
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * @return the answerText
	 */
	public String getAnswerText() {
		return answerText;
	}

	/**
	 * @param answerText
	 *            the answerText to set
	 */
	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}

	/**
	 * @return the dateLastModified
	 */
	public Date getDateLastModified() {
		return dateLastModified;
	}

	/**
	 * @param dateLastModified
	 *            the dateLastModified to set
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
	 * @param dateCreated
	 *            the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * @return the approved
	 */
	public Boolean isApproved() {
		return approved;
	}

	/**
	 * @param approved
	 *            the approved to set
	 */
	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	/**
	 * @return the privateReply
	 */
	public Boolean isPrivateReply() {
		return privateReply;
	}

	/**
	 * @param privateReply
	 *            the privateReply to set
	 */
	public void setPrivateReply(Boolean privateReply) {
		this.privateReply = privateReply;
	}

	/**
	 * @return the anonymous
	 */
	public Boolean isAnonymous() {
		return anonymous;
	}

	/**
	 * @param anonymous
	 *            the anonymous to set
	 */
	public void setAnonymous(Boolean anonymous) {
		this.anonymous = anonymous;
	}

	public String getLastModifierId() {
		return lastModifierId;
	}

	public void setLastModifierId(String lastModifierId) {
		this.lastModifierId = lastModifierId;
	}
	
	
	public String getOwnerMobileNr() {
		return ownerMobileNr;
	}

	public void setOwnerMobileNr(String ownerMobileNr) {
		this.ownerMobileNr = ownerMobileNr;
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
		QnaAnswer other = (QnaAnswer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
