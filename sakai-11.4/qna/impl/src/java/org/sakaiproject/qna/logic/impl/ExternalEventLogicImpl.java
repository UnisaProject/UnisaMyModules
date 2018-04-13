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

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.entitybroker.EntityReference;
import org.sakaiproject.event.api.Event;
import org.sakaiproject.event.api.NotificationService;
import org.sakaiproject.qna.logic.ExternalEventLogic;
import org.sakaiproject.qna.logic.entity.AnswerEntityProvider;
import org.sakaiproject.qna.logic.entity.CategoryEntityProvider;
import org.sakaiproject.qna.logic.entity.QuestionEntityProvider;
import org.sakaiproject.qna.model.QnaAnswer;
import org.sakaiproject.qna.model.QnaCategory;
import org.sakaiproject.qna.model.QnaOptions;
import org.sakaiproject.qna.model.QnaQuestion;

public class ExternalEventLogicImpl implements ExternalEventLogic {

    private org.sakaiproject.event.api.EventTrackingService eventTrackingService;
    
    private static Log log = LogFactory.getLog(ExternalEventLogicImpl.class);
    
    public void setEventTrackingService(org.sakaiproject.event.api.EventTrackingService eventTrackingService) {
    	this.eventTrackingService = eventTrackingService;
    }
    
    /**
     * @see ExternalEventLogic#postEvent(String, Object)
     */
    public void postEvent(String message, Object entity) {
		if (entity != null) {
			String reference = getEntityReference(entity);
			if (reference != null) {
				Event event = eventTrackingService.newEvent(message, reference, true, NotificationService.PREF_IMMEDIATE);
				eventTrackingService.post(event);
			}
		}
	}
	
    /**
     * Retrieves entity reference
     * 
     * @param entity Entity object
     * @return entity reference
     */
	@SuppressWarnings("unchecked")
	private String getEntityReference(Object entity) {
	      String id = null;
	      try {
	         Class elementClass = entity.getClass();
	         Method getIdMethod = elementClass.getMethod("getId", new Class[] {});
	         	         
	         id = getIdMethod.invoke(entity, (Object[]) null).toString();
	                  
	         return getEntityReference(elementClass, id);
	      } catch (Exception e) {
	         log.warn("Failed to get id from entity object", e);
	         return null;
	      }
	}
	
	/**
	 * Retrieves entity reference
	 * 
	 * @param refClass	Entity class
	 * @param entityId	Id of entity
	 * @return entity reference
	 */
	@SuppressWarnings("unchecked")
	private String getEntityReference(Class refClass, String entityId) {
		
		String prefix =  null;
		if (refClass == QnaQuestion.class) {
			prefix = QuestionEntityProvider.ENTITY_PREFIX;
		} else if (refClass == QnaAnswer.class) {
			prefix = AnswerEntityProvider.ENTITY_PREFIX;
		} else if (refClass == QnaCategory.class) {
			prefix = CategoryEntityProvider.ENTITY_PREFIX;
		} else if (refClass == QnaOptions.class) {
			prefix = "qna-options";
		} else {
			prefix = "qna:" + refClass.getName();
		}
		
		return new EntityReference(prefix, entityId).toString();
	}
	
	
}
