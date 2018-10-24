/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-api/src/java/org/etudes/api/app/melete/MeleteSitePreferenceService.java $
 *
 ***********************************************************************************
 * Copyright (c) 2008,2009,2010,2011 Etudes, Inc.
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
 ****************************************************************************************/
package org.etudes.api.app.melete;

public interface MeleteSitePreferenceService
{
	/**
	 * Gets the Site Id
	 * 
	 * @return
	 */
	public abstract String getPrefSiteId();

	/**
	 * Checks author's preference for auto-numbering of modules/sections on list page
	 * 
	 * @return true if auto-numbered
	 */
	public abstract boolean isAutonumber();

	/**
	 * Checks if author has allowed printing of material preference
	 * 
	 * @return true if allowed
	 */
	public abstract Boolean isPrintable();

	/**
	 * Sets auto-numbering preference
	 * 
	 * @param autonumber
	 *        The preferred option
	 */
	public abstract void setAutonumber(boolean autonumber);

	/**
	 * Sets the Site Id
	 * 
	 * @param prefSiteId
	 *        The Site Id
	 */
	public abstract void setPrefSiteId(String prefSiteId);

	/**
	 * Set allow printing of material preference
	 * 
	 * @param printable
	 *        The preferred option
	 */
	public abstract void setPrintable(Boolean printable);
}
