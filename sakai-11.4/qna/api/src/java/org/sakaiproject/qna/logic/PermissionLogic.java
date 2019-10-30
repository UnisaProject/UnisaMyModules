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

public interface PermissionLogic {
	
	/**
	 * Check if a specified user has QNA update rights in specific location
	 * @param locationId a unique id which represents the current location of the user (entity reference)
	 * @param userId the internal user id (not username)
	 * @return true if user can update, false if not
	 */
	public boolean canUpdate(String locationId, String userId);
	
	/**
	 * Check if a specified user can add new questions in specific location
	 * @param locationId a unique id which represents the current location of the user (entity reference)
	 * @param userId the internal user id (not username)
	 * @return true if user can add new question, false if not
	 */
	public boolean canAddNewQuestion(String locationId, String userId);
	
	/**
	 * Check if a specified user can add new category in specific location
	 * @param locationId a unique id which represents the current location of the user (entity reference)
	 * @param userId the internal user id (not username)
	 * @return true if user can add new category, false if not
	 */
	public boolean canAddNewCategory(String locationId, String userId);
	
	/**
	 * Check if a specified user can add new answer in specific location
	 * @param locationId a unique id which represents the current location of the user (entity reference)
	 * @param userId the internal user id (not username)
	 * @return true if user can add new answer, false if not
	 */
	public boolean canAddNewAnswer(String locationId, String userId);
	
	/**
	 * Check if a user has the read permission in a location
	 * @param location reference
	 * @param userId can be null
	 * @return
	 */
	public boolean canRead(String locationId, String userId);
}
