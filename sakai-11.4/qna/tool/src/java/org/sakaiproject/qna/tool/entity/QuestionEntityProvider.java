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
package org.sakaiproject.qna.tool.entity;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.entitybroker.EntityReference;
import org.sakaiproject.entitybroker.entityprovider.CoreEntityProvider;
import org.sakaiproject.entitybroker.entityprovider.capabilities.CollectionResolvable;
import org.sakaiproject.entitybroker.entityprovider.capabilities.RESTful;
import org.sakaiproject.entitybroker.entityprovider.capabilities.RedirectDefinable;
import org.sakaiproject.entitybroker.entityprovider.capabilities.Statisticable;
import org.sakaiproject.entitybroker.entityprovider.extension.Formats;
import org.sakaiproject.entitybroker.entityprovider.extension.TemplateMap;
import org.sakaiproject.entitybroker.entityprovider.search.Restriction;
import org.sakaiproject.entitybroker.entityprovider.search.Search;
import org.sakaiproject.entitybroker.util.AbstractEntityProvider;
import org.sakaiproject.qna.logic.ExternalLogic;
import org.sakaiproject.qna.logic.QuestionLogic;
import org.sakaiproject.qna.logic.exceptions.AttachmentException;
import org.sakaiproject.qna.model.QnaAnswer;
import org.sakaiproject.qna.model.QnaQuestion;

/**
 * Entity provider for questions
 */
public class QuestionEntityProvider extends AbstractEntityProvider implements CoreEntityProvider, RESTful, 
				Statisticable, RedirectDefinable {
	
	public final static String ENTITY_PREFIX = "qna-question";

	private static Log log = LogFactory.getLog(QuestionEntityProvider.class);
	
	/**
	 * Injected services
	 */
	private QuestionLogic questionLogic; 
	
	
	
	public void setQuestionLogic(QuestionLogic questionLogic) {
		this.questionLogic = questionLogic;
	}

	public boolean entityExists(String id) {
		log.debug("exists: " + id);
		if (id == null) {
            return false;
        }
        if ("".equals(id)) {
            return true;
        }
        
        QnaQuestion question = questionLogic.getQuestionById(Long.valueOf(id));
        boolean exists = (question != null);
        return exists;
	}

	public String getEntityPrefix() {
		return ENTITY_PREFIX;
	}

	public String createEntity(EntityReference ref, Object entity,
			Map<String, Object> params) {
		String userReference = developerHelperService.getCurrentUserReference();
        if (userReference == null) {
            throw new SecurityException("user must be logged in to create new question");
        }
        QnaQuestion question = (QnaQuestion) entity;
        
        //location must be specified
        if (question.getLocation() == null)
        	throw new IllegalArgumentException("Location must be set to create an question");
        
        //can the user add a question in this location
        Boolean isAllowed = developerHelperService.isUserAllowedInEntityReference(userReference, ExternalLogic.QNA_NEW_QUESTION, question.getLocation());
        if (!isAllowed) {
        	throw new SecurityException("user cannot create question in: " + question.getLocation());
        }
        
        question.setDateCreated(new Date());
        question.setDateLastModified(new Date());
        question.setOwnerId(developerHelperService.getCurrentUserId());
        
        questionLogic.saveQuestion(question, question.getLocation());
        
		return question.getId().toString();
	}

	public Object getSampleEntity() {
		return new QnaQuestion();
	}

	public void updateEntity(EntityReference arg0, Object arg1,
			Map<String, Object> arg2) {
		// TODO Auto-generated method stub
		
	}

	public Object getEntity(EntityReference ref) {
		log.debug("getEntity:" + ref.toString());
		String id = ref.getId();
		if (id == null) {
            return new QnaAnswer();
        }
		
		QnaQuestion question = questionLogic.getQuestionById(Long.valueOf(id));
		if (question == null) {
			throw new IllegalArgumentException("No qnaQuestion found for the given reference: " + ref);
		}
		String currentUserId = developerHelperService.getCurrentUserId();
		log.info("got userId: " + currentUserId);
		if (currentUserId == null) 
            throw new SecurityException("User must be logged in in order to access qna data: " + ref);

		if (! developerHelperService.isEntityRequestInternal(ref+"")) {
			// not an internal request so we require user to be logged in
			String userReference = developerHelperService.getCurrentUserReference();
			boolean allowRead = developerHelperService.isUserAllowedInEntityReference(userReference, ExternalLogic.QNA_READ, question.getLocation());
			if (!allowRead) {
				throw new SecurityException("User ("+userReference+") not allowed to access qna data: " + ref);

			}
		}
		
		
		return question;
	}

	public void deleteEntity(EntityReference ref, Map<String, Object> params) {
		    String id = ref.getId();
	        String userReference = developerHelperService.getCurrentUserReference();
	        if (userReference == null) {
	            throw new SecurityException("anonymous user cannot delete option: " + ref);
	        }
	        QnaQuestion q = questionLogic.getQuestionById(Long.valueOf(id));
	        if (q == null)
	        	throw new IllegalArgumentException("No question found to delete for the given reference: " + ref);
	        
	        
            boolean allowUpdate = developerHelperService.isUserAllowedInEntityReference(userReference, ExternalLogic.QNA_UPDATE, q.getLocation());
	        if (!allowUpdate)
	        	throw new SecurityException("user: " + userReference +" cannot delete option: " + ref);
	        	
	        try {
				questionLogic.removeQuestion(q.getId(), q.getLocation());
			} catch (AttachmentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public List<?> getEntities(EntityReference ref, Search search) {
		log.debug("getEntities: " + ref.toString() + " ," + search.toString());
		
        // get the setting which indicates if we are getting polls we can admin or polls we can take
        boolean adminControl = false;
        Restriction adminRes = search.getRestrictionByProperty("admin");
        if (adminRes != null) {
            adminControl = developerHelperService.convert(adminRes.getSingleValue(), boolean.class);
        }
        // get the location (if set)
        Restriction locRes = search.getRestrictionByProperty(CollectionResolvable.SEARCH_LOCATION_REFERENCE); //requestStorage.getStoredValueAsType(String.class, "siteId");
        String[] siteIds = null;
        if (locRes != null) {
            String siteId = developerHelperService.getLocationIdFromRef(locRes.getStringValue());
            siteIds = new String[] {siteId};
        }
        // get the user (if set)
        Restriction userRes = search.getRestrictionByProperty(CollectionResolvable.SEARCH_USER_REFERENCE);
        String userId = null;
        if (userRes != null) {
            String currentUser = developerHelperService.getCurrentUserReference();
            log.debug("current user: " + currentUser);
            String userReference = userRes.getStringValue(); 
            if (userReference == null) {
                throw new IllegalArgumentException("Invalid request: Cannot limit questions by user when the value is null");
            }
            if (userReference.equals(currentUser) || developerHelperService.isUserAdmin(currentUser)) {
                userId = developerHelperService.getUserIdFromRef(userReference); //requestStorage.getStoredValueAsType(String.class, "userId");
            } else {
                throw new SecurityException("Only the admin can get questions for other users, you requested polls for: " + userReference);
            }
        } else {
            userId = developerHelperService.getCurrentUserId();
            log.debug("current user: " + userId);
            if (userId == null) {
                throw new SecurityException("No user is currently logged in so no qna data can be retrieved");
            }
        }
        String perm = ExternalLogic.QNA_READ;
        if (adminControl) {
            perm = ExternalLogic.QNA_UPDATE;
        }
        log.debug("about to get questions");
        List<QnaQuestion> questions = questionLogic.findAllQuestionsForUserAndSitesAndPersmissions(userId, siteIds, perm);
        
        
        return questions;
	}

	public String[] getHandledOutputFormats() {
		return new String[] {Formats.XML, Formats.JSON};
	}

	public String[] getHandledInputFormats() {
		return new String[] {Formats.XML, Formats.JSON};
	}

	public String getAssociatedToolId() {
		return "sakai.qna";
	}

	public String[] getEventKeys() {
		String[] retval = new String[] { "qna.question.created",
										 "qna.question.deleted",
										 "qna.question.updated"};
		
		return retval;
	}

	public Map<String, String> getEventNames(Locale arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public TemplateMap[] defineURLMappings() {
		return new TemplateMap[] {
                new TemplateMap("/{prefix}/{questionId}/answer", AnswerEntityProvider.ENTITY_PREFIX + "{dot-extension}")
		};
		
	}
	
}
