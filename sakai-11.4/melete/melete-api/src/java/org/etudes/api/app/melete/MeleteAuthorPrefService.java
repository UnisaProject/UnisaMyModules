/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-api/src/java/org/etudes/api/app/melete/MeleteAuthorPrefService.java $
 * $Id: MeleteAuthorPrefService.java 73420 2011-03-29 18:45:36Z rashmi@etudes.org $  
 ***********************************************************************************
 *
 * Copyright (c) 2008,2009,2010,2011 Etudes, Inc.
 *
 *  Portions completed before September 1, 2008 Copyright (c) 2004, 2005, 2006, 2007, 2008 Foothill College, ETUDES Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
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

import org.etudes.api.app.melete.MeleteUserPreferenceService;

public interface MeleteAuthorPrefService
{
	/**
	 * Get user's preference at site level
	 * 
	 * @param site_id
	 *        The Site Id
	 * @return
	 */
	public MeleteSitePreferenceService getSiteChoice(String site_id);

	/**
	 * Get user's choice.
	 * 
	 * @param user_id
	 *        The user Id
	 * @return
	 */
	public MeleteUserPreferenceService getUserChoice(String user_id);

	/**
	 * Add user's preference for editor, expanded view, license choice etc
	 * 
	 * @param mup
	 *        MeleteUserPreferenceService Object
	 * @throws Exception
	 *         "add_editorchoice_fail" MeleteException
	 */
	public void insertUserChoice(MeleteUserPreferenceService mup) throws Exception;

	/**
	 * Add site level preference like printable, auto numbering of modules
	 * 
	 * @param msp
	 *        MeleteSitePreferenceService object
	 * @throws Exception
	 *         "add_editorchoice_fail" MeleteException
	 */
	public void insertUserSiteChoice(MeleteSitePreferenceService msp) throws Exception;
}
