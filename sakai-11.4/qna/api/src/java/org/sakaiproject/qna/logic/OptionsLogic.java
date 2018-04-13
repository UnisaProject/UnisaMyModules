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

import java.util.Set;

import org.sakaiproject.qna.model.QnaOptions;

/**
 *	QNA API for manipulation options 
 */
public interface OptionsLogic {

	/**
	 * Checks if the options for a location has moderation switched on
	 *
	 * @param locationId
	 *            a unique id which represents the current location of the user
	 *            (entity reference)
	 * @return boolean true if moderation on, false if not
	 */
	public boolean isModerationOn(String locationId);

	/**
	 * Retrieves options for a location
	 *
	 * @param locationId
	 *            a unique id which represents the current location of the user
	 *            (entity reference)
	 * @return {@link QnaOptions} for location
	 */
	public QnaOptions getOptionsForLocation(String locationId);
	
	/**
	 * Get options by ID
	 * 
	 * @param id
	 * 			unique id that represents the options in the database
	 * @return {@link QnaOptions} object
	 */
	public QnaOptions getOptionsById(String id);

	/**
	 * Save/update options uses current location id
	 *
	 * @param options 		{@link QnaOptions} to be saved
	 * @param locationId 	 a unique id which represents the current location of the user
	 */
	public void saveOptions(QnaOptions options, String locationId);

	/**
	 * Creates options at locationId with default values
	 *
	 * @param locationId
	 *            a unique id which represents the current location of the user
	 *            (entity reference)
	 * @param {@link QnaOptions} created
	 */
	public QnaOptions createDefaultOptions(String locationId);

	/**
	 * Set custom mail list for location
	 *
	 * @param locationId
	 *            a unique id which represents the current location of the user
	 *            (entity reference)
	 * @param mailList
	 * 			  comma-separated mail list
	 * @return true if there were invalid e-mail addresses, false if all were valid
	 */
	public boolean setCustomMailList(String locationId, String mailList);

	/**
	 * Get e-mail notification list for location based on notification type
	 *
	 * @param locationId
	 *            a unique id which represents the current location of the user
	 *            (entity reference)
	 * @return set of e-mail address
	 */
	public Set<String> getNotificationSet(String locationId);

}
