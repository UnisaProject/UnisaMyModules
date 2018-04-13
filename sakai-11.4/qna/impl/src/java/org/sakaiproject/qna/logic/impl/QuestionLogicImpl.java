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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.authz.api.AuthzGroupService;
import org.sakaiproject.entity.api.EntityManager;
import org.sakaiproject.entity.api.Reference;
import org.sakaiproject.entitybroker.DeveloperHelperService;
import org.sakaiproject.genericdao.api.finders.ByPropsFinder;
import org.sakaiproject.genericdao.api.search.Order;
import org.sakaiproject.genericdao.api.search.Restriction;
import org.sakaiproject.genericdao.api.search.Search;
import org.sakaiproject.qna.dao.QnaDao;
import org.sakaiproject.qna.logic.AttachmentLogic;
import org.sakaiproject.qna.logic.CategoryLogic;
import org.sakaiproject.qna.logic.ExternalEventLogic;
import org.sakaiproject.qna.logic.ExternalLogic;
import org.sakaiproject.qna.logic.NotificationLogic;
import org.sakaiproject.qna.logic.OptionsLogic;
import org.sakaiproject.qna.logic.PermissionLogic;
import org.sakaiproject.qna.logic.QuestionLogic;
import org.sakaiproject.qna.logic.exceptions.AttachmentException;
import org.sakaiproject.qna.logic.exceptions.QnaConfigurationException;
import org.sakaiproject.qna.model.QnaAttachment;
import org.sakaiproject.qna.model.QnaCategory;
import org.sakaiproject.qna.model.QnaOptions;
import org.sakaiproject.qna.model.QnaQuestion;
import org.sakaiproject.site.api.SiteService;

public class QuestionLogicImpl implements QuestionLogic {

	private static Log log = LogFactory.getLog(QuestionLogicImpl.class);
	public static final String QUESTION_ID= "questionid";
	
	private PermissionLogic permissionLogic;
	private OptionsLogic optionsLogic;
	private ExternalLogic externalLogic;
	private CategoryLogic categoryLogic;
	private AttachmentLogic attachmentLogic;
	private NotificationLogic notificationLogic;
	private ExternalEventLogic externalEventLogic;
	private DeveloperHelperService developerHelperService;
	private AuthzGroupService authzGroupService;
	private QnaDao dao;
	private EntityManager entityManager;
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void setAuthzGroupService(AuthzGroupService authzGroupService) {
		this.authzGroupService = authzGroupService;
	}

	public void setPermissionLogic(PermissionLogic permissionLogic) {
		this.permissionLogic = permissionLogic;
	}

	public void setOptionsLogic(OptionsLogic optionsLogic) {
		this.optionsLogic = optionsLogic;
	}

	public void setExternalLogic(ExternalLogic externalLogic) {
		this.externalLogic = externalLogic;
	}

	public void setCategoryLogic(CategoryLogic categoryLogic) {
		this.categoryLogic = categoryLogic;
	}
	
	public void setAttachmentLogic(AttachmentLogic attachmentLogic) {
		this.attachmentLogic = attachmentLogic;
	}
	
	public void setNotificationLogic(NotificationLogic notificationLogic) {
		this.notificationLogic = notificationLogic;
	}

	public void setExternalEventLogic(ExternalEventLogic externalEventLogic) {
		this.externalEventLogic = externalEventLogic;
	}
	
	public void setDeveloperHelperService(DeveloperHelperService developerHelperService) {
		this.developerHelperService = developerHelperService;
	}
	
	public void setDao(QnaDao dao) {
		this.dao = dao;
	}
	
	/**
	 * @see QuestionLogic#getNewQuestions(String)
	 */
	public List<QnaQuestion> getNewQuestions(String locationId) {
		return dao.getNewQuestions(locationId);
	}
	
	/**
	 * @see QuestionLogic#getPublishedQuestions(String)
	 */
	@SuppressWarnings("unchecked")
	public List<QnaQuestion> getPublishedQuestions(String locationId) {
		List<QnaQuestion> l = dao.findByProperties(QnaQuestion.class,
				new String[] { "location", "published" }, new Object[] {
						locationId, true }, new int[] { ByPropsFinder.EQUALS,
						ByPropsFinder.EQUALS });
		return l;
	}

	/**
	 * @see QuestionLogic#getAllQuestions(String)
	 */
	@SuppressWarnings("unchecked")
	public List<QnaQuestion> getAllQuestions(String locationId) {
		List<QnaQuestion> l = dao.findByProperties(QnaQuestion.class,
				new String[] { "location" }, new Object[] {
						locationId}, new int[] { ByPropsFinder.EQUALS});
		return l;
	}

	/**
	 * @see QuestionLogic#getQuestionById(Long, String, boolean)
	 */
	public QnaQuestion getQuestionById(Long questionId, String userId, boolean ignorePermission) {
		QnaQuestion q = dao.findById(QnaQuestion.class, questionId);
		
		//the question may not exist
		if (q == null)
			return null;
		
		if (!ignorePermission) {
			//does the user have rights to read the question?
	 		boolean allowRead = permissionLogic.canRead(q.getLocation(), userId);
	        if (!allowRead) {
	        	throw new SecurityException("User ("+ userId +") not allowed to access qna data: " + questionId);
	        }
		}
	
		return q;
	}	
	
	/**
	 * @see QuestionLogic#getQuestionById(Long)
	 */
	public QnaQuestion getQuestionById(Long questionId) {
		return getQuestionById(questionId, developerHelperService.getCurrentUserId(), false);
	}
	

	/**
	 * @see QuestionLogic#getQuestionsWithPrivateReplies(String)
	 */
	public List<QnaQuestion> getQuestionsWithPrivateReplies(String locationId) {
		List<QnaQuestion> questions = getAllQuestions(locationId);
		List<QnaQuestion> questionsWithPrivateReplies = new ArrayList<QnaQuestion>();
		
		for (QnaQuestion qnaQuestion : questions) {
			if (qnaQuestion.hasPrivateReplies()) {
				questionsWithPrivateReplies.add(qnaQuestion);
			}
		}
		return questionsWithPrivateReplies;
	}

	/**
	 * @see QuestionLogic#incrementView(Long)
	 */
	public void incrementView(Long questionId) {
		if (!permissionLogic.canUpdate(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId())) {
			QnaQuestion question = getQuestionById(questionId);	
			
			// Don't increment if user is owner of question
			if (!question.getOwnerId().equals(externalLogic.getCurrentUserId())) { 
				question.setViews(question.getViews() + 1);
				dao.save(question);
			}
		}
	}

	/**
	 * @see QuestionLogic#publishQuestion(Long, String)
	 */
	public void publishQuestion(Long questionId, String locationId) {
		String userId = externalLogic.getCurrentUserId();
		if (permissionLogic.canUpdate(locationId, userId)) {
			QnaQuestion question = getQuestionById(questionId);
			
			if (question.getCategory() == null) {
				throw new QnaConfigurationException("A question can not be published without a category");
			}
			
			question.setPublished(true);
			saveQuestion(question, locationId);
		} else {
			throw new SecurityException(
					"Current user cannot save question for " + locationId
							+ " because they do not have permission");
		}
	}

	/**
	 * @see QuestionLogic#existsQuestion(Long)
	 */
	public boolean existsQuestion(Long questionId) {
		if (questionId == null) {
			return false;
		} else {
			if (getQuestionById(questionId) != null) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * @see QuestionLogic#removeQuestion(Long, String)
	 */
	public void removeQuestion(Long questionId, String locationId) throws AttachmentException {
		QnaQuestion question = getQuestionById(questionId);
		String userId = externalLogic.getCurrentUserId();
		if (permissionLogic.canUpdate(locationId, userId)) {
			try {
				// delete attachments
				for (QnaAttachment attachment : question.getAttachments()) {
					attachmentLogic.deleteAttachment(attachment.getAttachmentId());
				}
				
			} finally {
				dao.delete(question);
				externalEventLogic.postEvent(ExternalEventLogic.EVENT_QUESTION_DELETE, question);
				log.info("Question deleted: " + question.getId());
			}
		
		} else {
			throw new SecurityException(
					"Current user cannot remove question for " + locationId
							+ " because they do not have permission");
		}
	}
	
	/**
	 * @see QuestionLogic#removeQuestion(String, String)
	 */
	public void removeQuestion(String questionId, String locationId) throws AttachmentException {
		removeQuestion(Long.parseLong(questionId), locationId);
	}
	
	/**
	 * @see QuestionLogic#saveQuestion(QnaQuestion, String)
	 */
	public void saveQuestion(QnaQuestion question, String locationId) {
		String userId = externalLogic.getCurrentUserId();
		saveQuestion(question, locationId, userId);
	}
	
	/**
	 * @see QuestionLogic#saveQuestion(QnaQuestion, String, String)
	 */
	public void saveQuestion(QnaQuestion question, String locationId, String userId) {
		
		if (existsQuestion(question.getId())) {
			if (permissionLogic.canUpdate(locationId, userId)) {
				question.setDateLastModified(new Date());
				question.setLastModifierId(userId);
				dao.save(question);
				externalEventLogic.postEvent(ExternalEventLogic.EVENT_QUESTION_UPDATE, question);
			} else {
				throw new SecurityException(
						"User " + userId +" cannot save question for "
								+ question.getLocation()
								+ " because they do not have permission");
			}
		} else {
			if (permissionLogic.canAddNewQuestion(locationId, userId) || (userId == null && question.getOwnerMobileNr() != null)) {
				QnaOptions options = optionsLogic.getOptionsForLocation(locationId);
				
				if (userId == null && !options.getAllowUnknownMobile()) {
					throw new SecurityException(
							"New questions cannot be saved anonymously via mobile for "
									+ locationId);
				}
				
				if (question.isAnonymous() == null) {
					question.setAnonymous(options.getAnonymousAllowed()); // default for location
				} else {
					if (question.isAnonymous()) {
						if (!options.getAnonymousAllowed()) {
							throw new QnaConfigurationException("Location: "
									+ locationId
									+ " does not allow anonymous questions");
						}
					}
				}

				Date now = new Date();
				question.setDateCreated(now);
				question.setDateLastModified(now);

				if (options.isModerated()) {
					question.setPublished(false);
				} else {
					question.setPublished(true);
				}

				question.setLocation(locationId);
				question.setOwnerId(userId);
				question.setLastModifierId(userId);
				question.setViews(0);
				question.setSortOrder(0);
				
				if (question.getNotify() == null) {
					question.setNotify(true);
				}
				
				if (question.getCategory() != null) {
					question.setHidden(question.getCategory().getHidden());
				} else {
					question.setHidden(false);
				}
				
				dao.save(question);
				externalEventLogic.postEvent(ExternalEventLogic.EVENT_QUESTION_CREATE, question);
				
				// Notification
				if (options.getEmailNotification()) {
					String[] emails = optionsLogic.getNotificationSet(locationId).toArray(new String[]{});
					if (options.getAnonymousAllowed() || question.getOwnerId() == null) {
						notificationLogic.sendNewQuestionNotification(emails, question);
					} else {
						notificationLogic.sendNewQuestionNotification(emails, question, question.getOwnerId());
					}
				}
			} else {
				throw new SecurityException(
						"User " + userId +  " cannot save new question for "
								+ locationId
								+ " because they do not have permission");
			}

		}
	}

	/**
	 * @see QuestionLogic#addQuestionToCategory(Long, String, String)
	 */
	public void addQuestionToCategory(Long questionId, String categoryId,
			String locationId) {
		
		QnaCategory category = categoryLogic.getCategoryById(categoryId);
		QnaQuestion question = getQuestionById(questionId);

		if (category == null) {
			throw new IllegalArgumentException("Category (" + categoryId
					+ ") does not exist");
		}

		if (question == null) {
			throw new IllegalArgumentException("Question (" + questionId
					+ ") does not exist");
		}

		if (!category.getLocation().equals(question.getLocation())) {
			throw new QnaConfigurationException(
					"The locations of the category (" + category.getLocation() + ") and the question (" + question.getLocation() + ") are not equal");
		} else if (!category.getLocation().equals(locationId)) {
			throw new QnaConfigurationException("Location supplied ("+locationId+") does not match location of category and question ("+question.getLocation()+")");
		}

		category.addQuestion(question);
		categoryLogic.saveCategory(category, locationId);
	}

	/**
	 * @see QuestionLogic#retrieveURL(QnaQuestion, String)
	 */
	public String retrieveURL(QnaQuestion question, String view) {
		Map<String,String> params = new HashMap<String, String>();
		params.put(QUESTION_ID,question.getId().toString());
		return developerHelperService.getToolViewURL(externalLogic.getCurrentToolId(), view, params, question.getLocation());
	}

	public List<QnaQuestion> findAllQuestionsForUserAndSitesAndPersmissions(
			String userId, String[] siteIds, String permissionConstant) {
		if (userId == null || permissionConstant == null) {
            throw new IllegalArgumentException("userId and permissionConstant must be set");
        }
		List<QnaQuestion> questions = null;
		// get all allowed sites for this user
        List<String> allowedSites = getSitesForUser(userId, permissionConstant);
        if (! allowedSites.isEmpty()) {
            if (siteIds != null) {
                if (siteIds.length > 0) {
                    // filter down to just the requested ones
                    for (int j = 0; j < allowedSites.size(); j++) {
                        String siteId = allowedSites.get(j);
                        boolean found = false;
                        for (int i = 0; i < siteIds.length; i++) {
                            if (siteId.equals(siteIds[i])) {
                                found = true;
                            }
                        }
                        if (!found) {
                            allowedSites.remove(j);
                        }
                    }
                } else {
                    // no sites to search so EXIT here
                    return new ArrayList<QnaQuestion>();
                }
            }
            String[] siteIdsToSearch = allowedSites.toArray(new String[allowedSites.size()]);
            Search search = new Search();
            if (siteIdsToSearch.length > 0) {
            	search.addRestriction(new Restriction("location", siteIdsToSearch));
            }
            search.addOrder(new Order("dateCreated", false));
            questions = dao.findBySearch(QnaQuestion.class, search);
           
        } 
           
		return questions;
	}
	
	
    private static final String SAKAI_SITE_TYPE = SiteService.SITE_SUBTYPE;
    @SuppressWarnings("unchecked")
    protected List<String> getSitesForUser(String userId, String permission) {
        log.debug("userId: " + userId + ", permission: " + permission);

        List<String> l = new ArrayList<String>();

        // get the groups from Sakai
        Set<String> authzGroupIds = 
           authzGroupService.getAuthzGroupsIsAllowed(userId, permission, null);
        Iterator<String> it = authzGroupIds.iterator();
        while (it.hasNext()) {
           String authzGroupId = it.next();
           Reference r = entityManager.newReference(authzGroupId);
           if (r.isKnownType()) {
              // check if this is a Sakai Site or Group
              if (r.getType().equals(SiteService.APPLICATION_ID)) {
                 String type = r.getSubType();
                 if (SAKAI_SITE_TYPE.equals(type)) {
                    // this is a Site
                	log.debug("adding " + authzGroupId);
                    l.add(authzGroupId);
                 }
              }
           }
        }

        if (l.isEmpty()) log.info("Empty list of siteIds for user:" + userId + ", permission: " + permission);
        return l;
     }
	
}
