/**********************************************************************************
 *
 * $URL: https://source.etudes.org/svn/apps/melete/tags/2.9.1forSakai/melete-api/src/java/org/etudes/api/app/melete/SectionTrackViewObjService.java $
 *
 ***********************************************************************************
 * Copyright (c) 2010, 2011 Etudes, Inc.
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

import java.util.Date;

public interface SectionTrackViewObjService
{

	/**
	 * method to compare two section track view objects
	 * 
	 * @param other
	 * @return true if equal, false if not
	 */
	public abstract boolean equals(Object other);

	/**
	 * @return the section
	 */
	public abstract org.etudes.api.app.melete.SectionObjService getSection();

	/**
	 * @return section id
	 */
	public abstract int getSectionId();

	/**
	 * @return the userId
	 */
	public abstract String getUserId();

	/**
	 * @return get view date
	 */
	public abstract Date getFirstViewDate();

	/**
	 * @return get view date
	 */
	public abstract Date getViewDate();

	/**
	 * method to compare with hashcode
	 * 
	 * @return hashcode value
	 */
	public abstract int hashCode();

	/**
	 * @param section
	 *        the section to set
	 */
	public abstract void setSection(org.etudes.api.app.melete.SectionObjService section);

	/**
	 * @param set
	 *        section id
	 */
	public abstract void setSectionId(int sectionId);

	/**
	 * @param userId
	 *        the userId to set
	 */
	public abstract void setUserId(String userId);

	/**
	 * @param viewDate
	 *        set view date
	 */
	public abstract void setFirstViewDate(Date firstViewDate);
	
	/**
	 * @param viewDate
	 *        set view date
	 */
	public abstract void setViewDate(Date viewDate);
}
