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

import java.util.Locale;

/**
 *  Shamelessly stolen from Assignment2
 *  Used to get messages from outside Tool layer
 */
public interface QnaBundleLogic {

	/** Path to bundle messages */
	public static final String QNA_BUNDLE = "messages";
	
	/**
	 * Get message with key
	 * 
	 * @param key specific key of message to retrieve
	 * @return message as {@link String}
	 */
	public String getString(String key);
	
	/**
	 * Get message with parameters
	 * 
	 * @param key specific key of message to retrieve
	 * @param parameters array of parameters to format message
	 * @return message as {@link String}
	 */
	public String getFormattedMessage(String key, Object[] parameters);
	
	/**
	 * @return user's preferred locale
	 */
	public Locale getLocale();
}
