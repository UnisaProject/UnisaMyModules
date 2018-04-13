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
public class QnaCategory {

	private String id;

	//  The list of questions associated with this option
    private List<QnaQuestion> questions = new ArrayList<QnaQuestion>();

	// The user (sakai userid) that created this category
	private String ownerId;

	// Sakai entity reference
	private String location;

	// Text of the category
	private String categoryText;

	// The date this category was last modified
	private Date dateLastModified;

	// The date this category was created
	private Date dateCreated;

	// Ordering of category in category view

	private Integer sortOrder;

	private Boolean hidden;

	public QnaCategory() {
	}

	/**
	 * @param ownerId
	 * @param location
	 * @param categoryText
	 * @param order
	 * @param hidden TODO
	 * @param dateLastModified
	 * @param dateCreated
	 */
	public QnaCategory(String ownerId, String location, String categoryText,
			Integer order, Boolean hidden) {
		this.ownerId = ownerId;
		this.location = location;
		this.categoryText = categoryText;
		this.sortOrder = order;
		Date now = new Date();
		dateCreated = now;
		dateLastModified = now;
		this.hidden = hidden;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
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
	 * @param ownerId
	 *            the ownerId to set
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
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the categoryText
	 */
	public String getCategoryText() {
		return categoryText;
	}

	/**
	 * @param categoryText
	 *            the categoryText to set
	 */
	public void setCategoryText(String categoryText) {
		this.categoryText = categoryText;
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
		QnaCategory other = (QnaCategory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * @return the questions
	 */
	public List<QnaQuestion> getQuestions() {
		return questions;
	}

	public List<QnaQuestion> getPublishedQuestions() {
		List<QnaQuestion> published = new ArrayList<QnaQuestion>();
		
		if (questions != null) {
			for (QnaQuestion question : questions) {
				if (question.isPublished()) {
					published.add(question);
				}
			}			
		}
		return published;
	}

	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(List<QnaQuestion> questions) {
		this.questions = questions;
	}

	public void addQuestion(QnaQuestion qnaQuestion) {
		qnaQuestion.setCategory(this);
		questions.add(qnaQuestion);
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

}
