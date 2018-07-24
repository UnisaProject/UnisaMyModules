/**********************************************************************************
 *
 * $URL: https://source.etudes.org/svn/apps/melete/tags/2.9.1forSakai/melete-api/src/java/org/etudes/api/app/melete/SpecialAccessObjService.java $
 *
 ***********************************************************************************
 * Copyright (c) 2010, 2011 Etudes, Inc.
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

import java.util.Date;

public interface SpecialAccessObjService
{

	/**
	 * @return the accessId
	 */
	public abstract int getAccessId();

	/**
	 * @return allowUntilDate end date to set
	 */
	public abstract Date getAllowUntilDate();

	/**
	 * @return endDate end date to set
	 */
	public abstract Date getEndDate();

	/**
	 * @return the module
	 */
	public abstract org.etudes.api.app.melete.ModuleObjService getModule();

	/**
	 * @return the moduleId
	 */
	public abstract int getModuleId();

	/**
	 * @return startDate the start date
	 */
	public abstract Date getStartDate();

	/**
	 * @return the user names
	 */
	public abstract String getUserNames();

	/*
	 * @return the users
	 */
	public abstract String getUsers();

	/**
	 * @return overrideAllowUntil
	 */
	public abstract boolean isOverrideAllowUntil();

	/**
	 * @return overrideEnd
	 */
	public abstract boolean isOverrideEnd();

	/**
	 * @return overrideStart
	 */
	public abstract boolean isOverrideStart();

	/**
	 * @param accessId
	 *        the accessId to set
	 */
	public abstract void setAccessId(int accessId);

	/**
	 * @param allowUntilDate
	 *        allow until date to set
	 */
	public abstract void setAllowUntilDate(Date allowUntilDate);

	/**
	 * @param endDate
	 *        end date to set
	 */
	public abstract void setEndDate(Date endDate);

	/**
	 * @param module
	 *        the module to set
	 */
	public abstract void setModule(org.etudes.api.app.melete.ModuleObjService module);

	/**
	 * @param moduleId
	 *        the module id to set
	 */
	public abstract void setModuleId(int moduleId);

	/**
	 * @param overrideAllowUntil
	 *        overrideAllowUntil to set
	 */
	public abstract void setOverrideAllowUntil(boolean overrideAllowUntil);

	/**
	 * @param overrideEnd
	 *        overrideEnd to set
	 */
	public abstract void setOverrideEnd(boolean overrideEnd);

	/**
	 * @param overrideStart
	 *        overrideStart to set
	 */
	public abstract void setOverrideStart(boolean overrideStart);

	/**
	 * @param startDate
	 *        start date to set
	 */
	public abstract void setStartDate(Date startDate);

	/**
	 * @param userNames
	 *        the userNames to set
	 */
	public abstract void setUserNames(String userNames);

	/**
	 * @param users
	 *        the users to set
	 */
	public abstract void setUsers(String users);

}
