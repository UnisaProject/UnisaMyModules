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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This is a the options table entity
 * 
 */
public class QnaQuestion {

	private Long id;

	// The category this question falls in
	private QnaCategory category;

	// The list of answers associated with this question
	private List<QnaAnswer> answers = new ArrayList<QnaAnswer>();

	// The list of attachments linked to this question
	private List<QnaAttachment> attachments = new ArrayList<QnaAttachment>();

	// The user (sakai userid) that posted this question
	private String ownerId;
	
	// The mobile number used to post question (in case of anonymous postings via mobile)
	private String ownerMobileNr;
	
	// The user (sakai userid) that last modified this question
	private String lastModifierId;

	// Sakai entity reference
	private String location;

	// Text of the question text
	private String questionText;

	// How many times the question has been viewed
	private Integer views;

	// The date this question was last modified by someone
	private Date dateLastModified;

	// The date this question was created
	private Date dateCreated;

	// Order of the question in category view
	private Integer sortOrder;

	// If this question is asked anonymously (TAKE NOTE: not used for anonymous postings from mobile)
	private Boolean anonymous;

	// If the question is published
	private Boolean published;

	// If user needs to be notified of answer
	private Boolean notify;

	private Boolean hidden;

	// Category of id to be persisted. This is used by front-end and not
	// persisted itself. There must be a better way of doing this :/
	private String categoryId;

	/**
	 * Default constructor
	 */
	public QnaQuestion() {
	}

	/**
	 * @param ownerId
	 * @param location
	 * @param questionText
	 * @param views
	 * @param dateLastModified
	 * @param dateCreated
	 * @param order
	 * @param anonymous
	 * @param published
	 * @param hidden
	 * @param ownerMobileNr
	 */
	public QnaQuestion(QnaCategory category, String ownerId, String location,
			String questionText, Integer views, Date dateLastModified,
			Date dateCreated, Integer order, Boolean anonymous,
			Boolean published, Boolean hidden, String ownerMobileNr) {
		this.category = category;
		this.ownerId = ownerId;
		this.lastModifierId = ownerId;
		this.location = location;
		this.questionText = questionText;
		this.views = views;
		this.dateLastModified = dateLastModified;
		this.dateCreated = dateCreated;
		this.sortOrder = order;
		this.anonymous = anonymous;
		this.published = published;
		this.notify = false;
		this.hidden = false;
		this.ownerMobileNr = ownerMobileNr;
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
	 * @return the category
	 */
	public QnaCategory getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(QnaCategory category) {
		this.category = category;
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
	 * @return the location, viz. the site reference (/site/siteid)
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set in site reference format (/site/siteid)
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the questionText
	 */
	public String getQuestionText() {
		return questionText;
	}

	/**
	 * @param questionText
	 *            the questionText to set
	 */
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	/**
	 * @return the views
	 */
	public Integer getViews() {
		return views;
	}

	/**
	 * @param views
	 *            the views to set
	 */
	public void setViews(Integer views) {
		this.views = views;
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
	 * @return the order
	 */
	public Integer getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
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

	/**
	 * @return the published
	 */
	public Boolean isPublished() {
		return published;
	}

	/**
	 * @param published
	 *            the published to set
	 */
	public void setPublished(Boolean published) {
		this.published = published;
	}

	/**
	 * @return list of answers linked to this question
	 */
	public List<QnaAnswer> getAnswers() {
		return answers;
	}

	/**
	 * @param answers
	 *            set list of answers to this question
	 */
	public void setAnswers(List<QnaAnswer> answers) {
		this.answers = answers;
	}

	/**
	 * Add answer to this question
	 * 
	 * @param answer
	 *            {@link QnaAnswer} to be added
	 */
	public void addAnswer(QnaAnswer answer) {
		answer.setQuestion(this);
		answers.add(answer);
	}

	/**
	 * 
	 * @return list of attachments for question
	 */
	public List<QnaAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<QnaAttachment> attachments) {
		this.attachments = attachments;
	}

	/**
	 * Add attachment to this question
	 * 
	 * @param attachment
	 *            {@link QnaAttachment} to be added
	 */
	public void addAttachment(QnaAttachment attachment) {
		attachment.setQuestion(this);
		attachments.add(attachment);
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
		QnaQuestion other = (QnaQuestion) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getLastModifierId() {
		return lastModifierId;
	}

	public void setLastModifierId(String lastModifierId) {
		this.lastModifierId = lastModifierId;
	}

	public Boolean getNotify() {
		return notify;
	}

	public void setNotify(Boolean notify) {
		this.notify = notify;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryId() {
		return categoryId;
	}
	
	public String getOwnerMobileNr() {
		return ownerMobileNr;
	}

	public void setOwnerMobileNr(String ownerMobileNr) {
		this.ownerMobileNr = ownerMobileNr;
	}

	/**
	 * Helper function to check if question has private replies
	 * 
	 * @return true if question has private replies, false if otherwise
	 */
	public boolean hasPrivateReplies() {
		if (answers == null || answers.size() == 0) {
			return false;
		} else {
			for (QnaAnswer answer : answers) {
				if (answer.isPrivateReply()) {
					return true;
				}
			}
		}
		return false;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}
	
	
}
