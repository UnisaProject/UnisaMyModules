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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.authz.api.SecurityService;
import org.sakaiproject.entitybroker.EntityReference;
import org.sakaiproject.entitybroker.entityprovider.CoreEntityProvider;
import org.sakaiproject.entitybroker.entityprovider.capabilities.RESTful;
import org.sakaiproject.entitybroker.entityprovider.capabilities.Statisticable;
import org.sakaiproject.entitybroker.entityprovider.extension.Formats;
import org.sakaiproject.entitybroker.entityprovider.search.Search;
import org.sakaiproject.entitybroker.util.AbstractEntityProvider;
import org.sakaiproject.qna.logic.AnswerLogic;
import org.sakaiproject.qna.logic.ExternalLogic;
import org.sakaiproject.qna.model.QnaAnswer;

/**
 * Entity provider for answers  
 */
public class AnswerEntityProvider extends AbstractEntityProvider implements CoreEntityProvider, RESTful, Statisticable {
	public final static String ENTITY_PREFIX = "qna-answer";

	private static Log log = LogFactory.getLog(AnswerEntityProvider.class);
	/**
	 * Injected services
	 */
	private AnswerLogic answerLogic;
	
	public void setAnswerLogic(AnswerLogic answerLogic) {
		this.answerLogic = answerLogic;
	}
	
	private SecurityService securityService;
	
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	

	public boolean entityExists(String id) {
		log.debug("exists: " + id);
		if (id == null) {
            return false;
        }
        if ("".equals(id)) {
            return true;
        }
        
        QnaAnswer answer = answerLogic.getAnswerById(Long.valueOf(id));
        boolean exists = (answer != null);
        return exists;
    }

	public String getEntityPrefix() {
		return ENTITY_PREFIX;
	}

	public String createEntity(EntityReference arg0, Object arg1,
			Map<String, Object> arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getSampleEntity() {
		return new QnaAnswer();
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
		
		QnaAnswer answer = answerLogic.getAnswerById(Long.valueOf(id));
		if (answer == null) {
			throw new IllegalArgumentException("No qnaAnswer found for the given reference: " + ref);
		}
		String currentUserId = developerHelperService.getCurrentUserId();
		
		if (! developerHelperService.isEntityRequestInternal(ref+"")) {
            // not an internal request so we require user to be logged in
            if (currentUserId == null) {
                throw new SecurityException("User must be logged in in order to access qna data: " + ref);
            } else {
                String userReference = developerHelperService.getCurrentUserReference();
                boolean allowRead = developerHelperService.isUserAllowedInEntityReference(userReference, ExternalLogic.QNA_READ, answer.getQuestion().getLocation());
                if (!allowRead) {
                	throw new SecurityException("User ("+userReference+") not allowed to access qna data: " + ref);
                }
                
            }
		}
		
		
		return answer;
		
	}

	public void deleteEntity(EntityReference arg0, Map<String, Object> arg1) {
		// TODO Auto-generated method stub
		
	}

	public List<?> getEntities(EntityReference arg0, Search arg1) {
		// TODO Auto-generated method stub
		List<QnaAnswer> ret = new ArrayList<QnaAnswer>(); //answerLogic.getAllAnswers(null);
		return ret;
	}

	public String[] getHandledOutputFormats() {
		return new String[] {Formats.XML, Formats.JSON};
	}

	public String[] getHandledInputFormats() {
		return new String[] {Formats.XML, Formats.JSON};
	}
	
	
	private boolean canRead(QnaAnswer answer) {
		if (securityService.isSuperUser())
			return true;
		
		Long id = answer.getId();
		QnaAnswer a = answerLogic.getAnswerById(id);
		if (a != null) {
			if (securityService.unlock(ExternalLogic.QNA_READ, a.getQuestion().getLocation()))
				return true;
				
		}
		
		return false;
	}


	public String getAssociatedToolId() {
		return "sakai.qna";
	}


	public String[] getEventKeys() {
		String[] ret = new String[]{
									"qna.answer.created",
									"qna.answer.updated"};
		
		return ret;
	}


	public Map<String, String> getEventNames(Locale arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
