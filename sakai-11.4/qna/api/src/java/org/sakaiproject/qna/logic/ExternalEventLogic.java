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

/**
 * This is the implementation for logic which uses Sakai's event implementation
 * 
 */
public interface ExternalEventLogic {
	
	public static final String EVENT_OPTIONS_CREATE = "qna.options.created";
	public static final String EVENT_OPTIONS_UPDATE = "qna.options.updated";
	
	public static final String EVENT_QUESTION_CREATE = "qna.question.created";
	public static final String EVENT_QUESTION_UPDATE = "qna.question.updated";
	public static final String EVENT_QUESTION_DELETE = "qna.question.deleted";

	public static final String EVENT_ANSWER_CREATE = "qna.answer.created";
	public static final String EVENT_ANSWER_UPDATE = "qna.answer.updated";
	public static final String EVENT_ANSWER_DELETE = "qna.answer.deleted";

	public static final String EVENT_CATEGORY_CREATE = "qna.category.created";
	public static final String EVENT_CATEGORY_UPDATE = "qna.category.updated";
	public static final String EVENT_CATEGORY_DELETE = "qna.category.deleted";
	
    /**
	 * Post a sakai event
	 * 
	 * @param message	Message of event
	 * @param entity	Entity event pertains to 
	 */
	public void postEvent(String message, Object entity);
	
}
