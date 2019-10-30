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
package org.sakaiproject.qna.logic;

import java.util.List;

import org.sakaiproject.qna.model.QnaCategory;
import org.sakaiproject.qna.model.QnaQuestion;

/**
 *	QNA API for Category logic 
 *
 */
public interface CategoryLogic {

	/**
	 * Get a category with a specific id
	 *
	 * @param categoryId
	 *            unique id of a {@link QnaCategory}
	 * @return {@link QnaCategory} object or null
	 */
	public QnaCategory getCategoryById(String categoryId);

	/**
	 * Check if a category exists
	 *
	 * @param questionid
	 * 			unique id of a {@link QnaCategory}
	 * @return	boolean
	 * 			true if it exists, false if it doesn't
	 */
	public boolean existsCategory(String categoryId);

	/**
	 * Saves a category
	 *
	 * @param category
	 *            {@link QnaCategory} object to be saved
	 * @param locationId unique id for location
	 */
	public void saveCategory(QnaCategory category, String locationId);

	/**
	 * Removes a category
	 *
	 * @param categoryId
	 *            {@link QnaCategory} object id to be removed
	 * @param locationId unique id for location
	 */
	public void removeCategory(String categoryId, String locationId);

	/**
	 * Get questions for as specific category
	 *
	 * @param categoryId 	id of a {@link QnaCategory}
	 * @return {@link List} of {@link QnaQuestion} with all questions for a category
	 */
	public List<QnaQuestion> getQuestionsForCategory(String categoryId);

	/**
	 * Get a list of categories for a location
	 *
	 * @param locationId unique id for location
	 * @return {@link List} of {@link QnaCategory} for location
	 */
	public List<QnaCategory> getCategoriesForLocation(String locationId);

	/**
	 * Creates a default category
	 *
	 * @param locationId 	unique id for location
	 * @param ownerId 		id of sakai user
	 * @param categoryText 	text for category
	 * @return QnaCategory 	new {@link QnaCategory} created
	 */
	public QnaCategory createDefaultCategory(String locationId, String ownerId, String categoryText);
	
	/**
	 * Sets default values for a new {@link QnaCategory} object
	 * 
	 * @param qnaCategory 	{@link QnaCategory} object to set values to
	 * @param locationId 	unique id for location
	 * @param ownerId		id of sakai user
	 */
	public void setNewCategoryDefaults(QnaCategory qnaCategory,String locationId, String ownerId);
	
	/**
	 * Get default category for location (first one)
	 * 
	 * @param locationId	unique id for location
	 * @return QnaCategory 	default {@link QnaCategory} for location
	 */
	public QnaCategory getDefaultCategory(String locationId);
}
