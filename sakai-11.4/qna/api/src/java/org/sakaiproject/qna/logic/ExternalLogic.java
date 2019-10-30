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

public interface ExternalLogic {

	public final static String NO_LOCATION = "noLocationAvailable";
	public static final String QNA_TOOL_ID = "sakai.qna";

	// permissions for QNA

	public final static String QNA_READ = "qna.read";

	public final static String QNA_NEW_QUESTION = "qna.new.question";

	public final static String QNA_NEW_ANSWER = "qna.new.answer";

	public final static String QNA_NEW_CATEGORY = "qna.new.category";

	public final static String QNA_UPDATE = "qna.update";

	/**
	 * @return the current sakai user id (not username)
	 */
	public String getCurrentUserId();

	/**
	 * Get the display name for a user by their unique id
	 * 
	 * @param userId
	 *            the current sakai user id (not username)
	 * @return display name (probably firstname lastname) or "----------" (10
	 *         hyphens) if none found
	 */
	public String getUserDisplayName(String userId);

	/**
	 * @return the current location id of the current user
	 */
	public String getCurrentLocationId();

	/**
	 * Returns URL to viewId pass in
	 * 
	 * @param viewId
	 *            of view to build path to
	 * @return a url path to the view
	 */
	public String getQuestionViewUrl(String viewId);

	/**
	 * Get title of a given location
	 * 
	 * @param locationId
	 *            a unique id which represents the current location of the user
	 *            (entity reference)
	 * @return the title for the context or "--------" (8 hyphens) if none found
	 */
	public String getLocationTitle(String locationId);

	/**
	 * Get site ID from entity reference
	 * 
	 * @param reference
	 *            enitity reference
	 * @return
	 */
	public String getSiteIdFromRef(String reference);

	/**
	 * Check if this user has super admin access
	 * 
	 * @param userId
	 *            the internal user id (not username)
	 * @return true if the user has admin access, false otherwise
	 */
	public boolean isUserAdmin(String userId);

	/**
	 * Check if user has maintain role
	 * 
	 * @param userId
	 *            the internal user id (not username)
	 * @return true if user has maintain role, false otherwise
	 */
	public boolean hasMaintainRole(String userId);

	/**
	 * Check if a user has a specified permission within a context, primarily a
	 * convenience method and passthrough
	 * 
	 * @param userId
	 *            the internal user id (not username)
	 * @param permission
	 *            a permission string constant
	 * @param locationId
	 *            a unique id which represents the current location of the user
	 *            (entity reference)
	 * @return true if allowed, false otherwise
	 */
	public boolean isUserAllowedInLocation(String userId, String permission,
			String locationId);

	/**
	 * Name of site contact for Location
	 * 
	 * @param locationId
	 *            a unique id which represents the current location of the user
	 *            (entity reference)
	 * @return Name of site contant
	 */
	public String getSiteContactName(String locationId);

	/**
	 * E-mail address of site contact
	 * 
	 * @param locationId
	 *            a unique id which represents the current location of the user
	 *            (entity reference)
	 * @return e-mail address of site contact
	 */
	public String getSiteContactEmail(String locationId);

	/**
	 * E-mail address for user
	 * 
	 * @param userId
	 *            the internal user id (not username)
	 * @return e-mailaddress of user
	 */
	public String getUserEmail(String userId);

	/**
	 * Retrieves all Users of site with specific permission
	 * 
	 * @param locationId
	 *            a unique id which represents the current location of the user
	 *            (entity reference)
	 * @param permission
	 *            permission to check
	 * @return {@link Set} of user id's with specific permission for location
	 */
	public Set<String> getSiteUsersWithPermission(String locationId, String permission);

	/**
	 * Send e-mails to users ids
	 * 
	 * @param from
	 *            from address to be used
	 * @param toUserIds
	 *            array of sakai user ids
	 * @param subject
	 *            subject of e-mail
	 * @param message
	 *            message of e-mail
	 * @return an array of email addresses that this message was sent to
	 */
	public String[] sendEmailsToUsers(String from, String[] toUserIds,
			String subject, String message);

	/**
	 * Send e-mail to array of e-mail addresses
	 * 
	 * @param from
	 *            from address to be used
	 * @param toEmails
	 *            array of e-mail addresses
	 * @param subject
	 *            subject of e-mail
	 * @param message
	 *            message of e-mail
	 * @return an array of email addresses that this message was sent to
	 */
	public String[] sendEmails(String from, String[] emails, String subject,
			String message);

	/**
	 * Send SMS to array of mobile numbers
	 * 
	 * @param mobileNrs
	 *            array of mobile numbers to send to
	 * @param fromId
	 *            user id to set as sender
	 * @param siteId
	 *            site id
	 * @param toolId
	 *            tool id
	 * @param message
	 *            message to send
	 * @return an array of userIds SMS was sent to
	 */
	public String[] sendSms(String[] mobileNrs, String fromId, String siteId,
			String toolId, String message);

	/**
	 * Return current tool display name
	 * 
	 * @return display name of tool
	 */
	public String getCurrentToolDisplayName();

	/**
	 * Return current tool id
	 * 
	 * @return id of current tool
	 */
	public String getCurrentToolId();
	
	/**
	 * Get incoming SMS number
	 * @return SMS number (e.g. shortcode) or null if not enabled / unset.
	 */
	public String getSmsNumber();
	
	/**
	 * Get service name
	 */
	public String getServiceName();
}
