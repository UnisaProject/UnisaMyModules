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
package org.sakaiproject.qna.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.sakaiproject.qna.dao.QnaDao;
import org.sakaiproject.qna.logic.AnswerLogic;
import org.sakaiproject.qna.logic.ExternalEventLogic;
import org.sakaiproject.qna.logic.ExternalLogic;
import org.sakaiproject.qna.logic.NotificationLogic;
import org.sakaiproject.qna.logic.OptionsLogic;
import org.sakaiproject.qna.logic.PermissionLogic;
import org.sakaiproject.qna.logic.QuestionLogic;
import org.sakaiproject.qna.logic.exceptions.QnaConfigurationException;
import org.sakaiproject.qna.model.QnaAnswer;
import org.sakaiproject.qna.model.QnaOptions;
import org.sakaiproject.qna.model.QnaQuestion;

public class AnswerLogicImpl implements AnswerLogic {

	private PermissionLogic permissionLogic;
	private OptionsLogic optionsLogic;
	private QuestionLogic questionLogic;
	private ExternalLogic externalLogic;
	private NotificationLogic notificationLogic;
	private ExternalEventLogic externalEventLogic;
	private QnaDao dao;
	
	public void setPermissionLogic(PermissionLogic permissionLogic) {
		this.permissionLogic = permissionLogic;
	}

	public void setOptionsLogic(OptionsLogic optionsLogic) {
		this.optionsLogic = optionsLogic;
	}

	public void setQuestionLogic(QuestionLogic questionLogic) {
		this.questionLogic = questionLogic;
	}

	public void setExternalLogic(ExternalLogic externalLogic) {
		this.externalLogic = externalLogic;
	}
	
	public void setNotificationLogic(NotificationLogic notificationLogic) {
		this.notificationLogic = notificationLogic;
	}

	public void setExternalEventLogic(ExternalEventLogic externalEventLogic) {
		this.externalEventLogic = externalEventLogic;
	}
	
	public void setDao(QnaDao dao) {
		this.dao = dao;
	}
	
	/**
	 * Check if answer exists
	 * 
	 * @param answerId id to check
	 * @return true if it exists, false otherwise
	 */
	public boolean existsAnswer(Long answerId) {
		if (answerId == null) {
			return false;
		} else {
			if (getAnswerById(answerId) != null) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * @see AnswerLogic#saveAnswer(QnaAnswer, String)
	 */
	public void saveAnswer(QnaAnswer answer, String locationId) {
		String userId = externalLogic.getCurrentUserId();
		saveAnswer(answer, locationId, userId);
	}
	
	/**
	 * @see AnswerLogic#saveAnswer(QnaAnswer, String, String)
	 */
	public void saveAnswer(QnaAnswer answer, String locationId, String userId) {
		
		if (!existsAnswer(answer.getId())) {
			if (permissionLogic.canAddNewAnswer(locationId, userId) || (userId == null && answer.getOwnerMobileNr() != null)) {
				QnaOptions options = optionsLogic.getOptionsForLocation(locationId);
				
				if (userId == null && !options.getAllowUnknownMobile()) {
					throw new SecurityException(
							"New answers cannot be saved anonymously via mobile for "
									+ locationId);
				}
				
				QnaQuestion question = questionLogic.getQuestionById(answer.getQuestion().getId(), userId, (userId == null ) ? true : false);
				if (question == null) {
					throw new QnaConfigurationException("Question with id "+answer.getQuestion().getId()+" does not exist");
				}
				
				if (question.getLocation().equals(locationId)) {
					if (answer.isAnonymous()) {
						if (!options.getAnonymousAllowed()) {
							throw new QnaConfigurationException("The location "
									+ locationId
									+ " does not allow anonymous replies");
						}
					}
					Date now = new Date();
					answer.setDateCreated(now);
					answer.setDateLastModified(now);
					answer.setOwnerId(userId);
					answer.setLastModifierId(userId);
					
					if (options.isModerated()) {
						// If user has update permission it is automatically approved
						if (permissionLogic.canUpdate(locationId, answer.getOwnerId())) {
							answer.setApproved(true);
						} else {
							answer.setApproved(false);
						}
					} else {
						answer.setApproved(false);
					}
					question.addAnswer(answer);
					dao.save(answer);
					
					externalEventLogic.postEvent(ExternalEventLogic.EVENT_ANSWER_CREATE, answer);
					
					// Notification emails
					if (answer.isPrivateReply() && (question.getOwnerId() != null)) {
						notificationLogic.sendPrivateReplyNotification(new String[]{question.getOwnerId()}, question, answer.getAnswerText());
					} else if (question.getNotify()) {
						if (question.getOwnerId() != null) {
							notificationLogic.sendNewAnswerNotification(new String[]{question.getOwnerId()}, question, answer.getAnswerText());	
						}
						
						if (question.getOwnerMobileNr() != null && options.getSmsNotification()) {
							notificationLogic.sendNewAnswerSmsNotification(new String[] {question.getOwnerMobileNr()}, question, answer.getAnswerText());
						}
					}
				} else {
					throw new QnaConfigurationException(
							"The location of the question ("
									+ question.getLocation()
									+ ") and location supplied (" + locationId
									+ ") does not match");
				}
			} else {
				throw new SecurityException("Current user cannot add question for "
						+ locationId + " because they do not have permission");
			}
		} else {
			if (permissionLogic.canUpdate(locationId, userId)) {
				answer.setDateLastModified(new Date());
				answer.setLastModifierId(userId);

				dao.save(answer);
				externalEventLogic.postEvent(ExternalEventLogic.EVENT_ANSWER_UPDATE, answer);
			} else {
				throw new SecurityException(
						"Current user cannot update answer for "
								+ locationId
								+ " because they do not have permission");
			}
		}
	}
	
	/**
	 * @see AnswerLogic#approveAnswer(Long, String)
	 */
	public void approveAnswer(Long answerId, String locationId) {
		String userId = externalLogic.getCurrentUserId();
		if (permissionLogic.canUpdate(locationId, userId)) {
			QnaAnswer answer = getAnswerById(answerId);
			answer.setApproved(true);
			answer.setDateLastModified(new Date());
			answer.setLastModifierId(userId);
			dao.save(answer);
			
			if (answer.getQuestion().getNotify()) {
				notificationLogic.sendNewAnswerNotification(new String[]{answer.getQuestion().getOwnerId()}, answer.getQuestion(), answer.getAnswerText());
			}
			
		} else {
			throw new SecurityException("Current user cannot approve answers for " + locationId + " because they do not have permission");
		}
	}
	
	/**
	 * @see AnswerLogic#getAnswerById(Long)
	 */
	public QnaAnswer getAnswerById(Long answerId) {
		return dao.findById(QnaAnswer.class, answerId);
	}
	
	/**
	 * @see AnswerLogic#removeAnswer(Long, String)
	 */
	public void removeAnswer(Long answerId, String locationId) {
		QnaAnswer answer = getAnswerById(answerId);
		removeAnswerFromQuestion(answerId,answer.getQuestion().getId(), locationId);
	}
	
	/**
	 * @see AnswerLogic#removeAnswerFromQuestion(Long, Long, String)
	 */
	public void removeAnswerFromQuestion(Long answerId, Long questionId, String locationId) {
		String userId = externalLogic.getCurrentUserId();
		if (permissionLogic.canUpdate(locationId, userId)) {
			QnaQuestion question = questionLogic.getQuestionById(questionId);
			
			QnaAnswer answer = getAnswerById(answerId);
			question.getAnswers().remove(answer);
			dao.delete(answer);
			externalEventLogic.postEvent(ExternalEventLogic.EVENT_ANSWER_DELETE, answer);
		} else {
			throw new SecurityException("Current user cannot delete answers for " + locationId + " because they do not have permission");
		}
	}
	
	/**
	 * @see AnswerLogic#withdrawApprovalAnswer(Long, String)
	 */
	public void withdrawApprovalAnswer(Long answerId, String locationId) {
		String userId = externalLogic.getCurrentUserId();
		if (permissionLogic.canUpdate(locationId, userId)) {
			QnaAnswer answer = getAnswerById(answerId);
			answer.setApproved(false);
			answer.setDateLastModified(new Date());
			answer.setLastModifierId(userId);
			dao.save(answer);
		} else {
			throw new SecurityException("Current user cannot withdraw approval of answers for " + locationId + " because they do not have permission");
		}
	}
	
	/**
	 * @see AnswerLogic#createDefaultAnswer(String)
	 */
	public QnaAnswer createDefaultAnswer(String locationId) {
		QnaAnswer answer = new QnaAnswer();
		QnaOptions options = optionsLogic.getOptionsForLocation(locationId);
		answer.setAnonymous(options.getAnonymousAllowed());
		answer.setPrivateReply(false);
		return answer;
	}

	public List<QnaAnswer> getAllAnswers(String context) {
		//TODO this should be done in one query
		List<QnaQuestion> qList = questionLogic.getAllQuestions(context);
		List<QnaAnswer> aList = new ArrayList<QnaAnswer>();
		for (int i = 0; i < qList.size(); i++) {
			QnaQuestion q = qList.get(i);
			List<QnaAnswer> a = q.getAnswers();
			aList.addAll(a);
		}
		
		return aList;
	}

}
