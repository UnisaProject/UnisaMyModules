/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.1/melete-api/src/java/org/etudes/api/app/melete/MeleteSecurityService.java $
 * $Id: MeleteSecurityService.java 73943 2011-04-27 21:54:12Z rashmi@etudes.org $  
 ***********************************************************************************
 *
 * Copyright (c) 2008,2009, 2010, 2011 Etudes, Inc.
 *
 * Portions completed before September 1, 2008 Copyright (c) 2004, 2005, 2006, 2007, 2008 Foothill College, ETUDES Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 **********************************************************************************/
package org.etudes.api.app.melete;

import java.util.Set;

/**
 * MeleteSecurityService provides the access permissions to the melete
 */
public interface MeleteSecurityService
{
	/**
	 * The type string for this application: should not change over time as it may be stored in various parts of persistent entities.
	 */
	public static final String APPLICATION_ID = "sakai:meleteDocs";

	/** This string starts the references to resources in this service. */
	public static final String REFERENCE_ROOT = "/meleteDocs/";

	/** Security function name for author. */
	public static final String SECURE_AUTHOR = "melete.author";

	/** Security function name for student. */
	public static final String SECURE_STUDENT = "melete.student";

	/**
	 * Check if the current user has permission as author for a particular site.
	 * 
	 * @param reference
	 *        The Site Id
	 * @return
	 * @throws Exception
	 */
	boolean allowAuthor(String reference) throws Exception;

	/**
	 * Check if the current user has permission as student for a particular site.
	 * 
	 * @param reference
	 *        The Site Id
	 * @return
	 * @throws Exception
	 */
	boolean allowStudent(String reference) throws Exception;

	/**
	 * Get all students and guests who are authorized to view the site content
	 * 
	 * @param context
	 *        The Site Id
	 * @return Set of user ids
	 */
	public Set<String> getUsersIsAllowed(String context);

	/**
	 * Check if the user has administrative rights
	 * 
	 * @param userId
	 *        The user Id
	 * @return true if user has rights
	 */
	public boolean isSuperUser(String userId);

	/**
	 * Remove security advisor.
	 */
	public void popAdvisor();

	/**
	 * Setup security advisor.
	 */
	public void pushAdvisor();
}
