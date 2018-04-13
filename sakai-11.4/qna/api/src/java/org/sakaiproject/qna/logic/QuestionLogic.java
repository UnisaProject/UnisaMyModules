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

import org.sakaiproject.qna.logic.exceptions.AttachmentException;
import org.sakaiproject.qna.model.QnaCategory;
import org.sakaiproject.qna.model.QnaQuestion;

/**
 *	QNA API for questions 
 */
public interface QuestionLogic {
	
	
	/**
	 * Get a question with a specific id (specify user)
	 * @param userId
	 * 				unique id of user
	 * @param ignorePermission 
	 * 				boolean value to specify if read permission must be ignored
	 * 				(userd for anon answer posts from mobile)
	 * @param questionid
	 * 				unique id of a {@link QnaQuestion}
	 *
	 * @return a {@link QnaQuestion} or null
	 */
	public QnaQuestion getQuestionById(Long questionId, String userId, boolean ignorePermission);
	

	/**
	 * Get a question with a specific id (for current user)
	 *
	 * @param questionid
	 * 				unique id of a {@link QnaQuestion}
	 * @return a {@link QnaQuestion} or null
	 */
	public QnaQuestion getQuestionById(Long questionId);
	

	/**
	 * Check if a question exists
	 *
	 * @param questionid
	 * 			unique id of a {@link QnaQuestion}
	 * @return	boolean true if it exists, false otherwise
	 */
	public boolean existsQuestion(Long questionId);

	/**
	 * Saves a question. Default uses currentUserId as user
	 *
	 * @param question
	 * 				{@link QnaQuestion} object
	 * @param locationId
	 * 				a unique id which represents the current location of the user (entity reference)
	 */
	public void saveQuestion(QnaQuestion question, String locationId);
	
	/**
	 *  Saves a question. Specify a user
	 * 
	 * @param question
	 * 				{@link QnaQuestion} object
	 * @param locationId
	 * 				a unique id which represents the location (entity reference)
	 * @param userId
	 * 				the internal user id (not username)
	 */
	public void saveQuestion(QnaQuestion question, String locationId, String userId);
	
	/**
	 * Remove a question
	 *
	 * @param questionid
	 * 				{@link QnaQuestion} object
	 * @throws AttachmentException 
	 */
	public void removeQuestion(Long questionId, String locationId) throws AttachmentException;

	/**
	 * Remove a question
	 *
	 * @param questionid
	 * 				{@link QnaQuestion} object
	 * @throws AttachmentException 
	 */
	public void removeQuestion(String questionId, String locationId) throws AttachmentException;
	
	/**
	 * Get all (published and unpublished) questions for location
	 * 	
	 * @param locationId unique id which represents the current location of the user
	 * @return a list of {@link QnaQuestion}
	 */
	public List<QnaQuestion> getAllQuestions(String locationId);
	
	/**
	 * Get all published questions for a location
	 *
	 * @param locationId
	 * 			a unique id which represents the current location of the user (entity reference)
	 * @return a list of {@link QnaQuestion}
	 *
	 */
	public List<QnaQuestion> getPublishedQuestions(String locationId);

	/**
	 * Get all new(unpublished without private replies) questions
	 *
	 * @param locationId
	 * 				a unique id which represents the current location of the user (entity reference)
	 * @return	a list of {@link QnaQuestion}
	 */
	public List<QnaQuestion> getNewQuestions(String locationId);

	/**
	 * Publishes question
	 *
	 * @param questionid unique id of {@link QnaQuestion}
	 * @param locationId a unique id which represents the current location of the user (entity reference)
	 */
	public void publishQuestion(Long questionId, String locationId);

	/**
	 * Get all questions with private replies
	 *
	 * @param locationId
	 * 				a unique id which represents the current location of the user (entity reference)
	 * @return	a list of {@link QnaQuestion}
	 */
	public List<QnaQuestion> getQuestionsWithPrivateReplies(String locationId);

	/**
	 * Increment view of a question
	 *
	 * @param unique id of {@link QnaQuestion}
	 */
	public void incrementView(Long questionId);
	
	/**
	 * Add a {@link QnaQuestion} to a {@link QnaCategory}
	 * 
	 * @param questionid
	 *            unique id of {@link QnaQuestion}
	 * @param categoryId
	 *            unique id of {@link QnaCategory}
	 * @param locationId 
	 * 			  a unique id which represents the current location of the user
	 */
	public void addQuestionToCategory(Long questionId,
			String categoryId, String locationId);
	
	/**
	 * Retrieves URL for a question based on a link
	 * 
	 * @param question Question to retrieve URL for
	 * @param view Specific view for Question
	 * @return String with URL
	 */
	public String retrieveURL(QnaQuestion question, String view);
	
	
	/**
	 * Get all the questions for a user in a set of sites (can be one) given the permission,
     * will return only questions that can be update on if the permission is qna.update
     * 
     * @param userId a sakai internal user id (not eid)
     * @param siteIds an array of site ids (can be null or empty to get the questions for all without security check)
     * @param permissionConstant either the qna.update (for all questions a user can update on) or 
     * qna.read for all the polls the user can read
     * @return the list of all questions this user can access
	 */
	public List<QnaQuestion> findAllQuestionsForUserAndSitesAndPersmissions(String userId, String[] siteIds, String permissionConstant);
}
