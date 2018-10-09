/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-api/src/java/org/etudes/api/app/melete/ModuleShdatesService.java $
 * $Id: ModuleShdatesService.java 80314 2012-06-12 22:15:39Z rashmi@etudes.org $  
 ***********************************************************************************
 *
 * Copyright (c) 2008, 2009, 2010, 2011, 2012 Etudes, Inc.
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

import java.util.Date;

public interface ModuleShdatesService
{

	/**
	 * Method to compare moduleshdatesservice objects
	 * 
	 * @param other
	 * @return true if equal, false if not
	 */
	public abstract boolean equals(Object other);

	/**
	 * @return add to schedule boolean flag
	 */
	public abstract Boolean getAddtoSchedule();
	
	/**
	 * @return allow until date
	 */
	public abstract Date getAllowUntilDate();
	
	/**
	 * @return end date
	 */
	public abstract Date getEndDate();

	/**
	 * @return end event id
	 */
	public abstract String getEndEventId();

	/**
	 * @return module
	 */
	public abstract org.etudes.api.app.melete.ModuleObjService getModule();

	/**
	 * @return start date
	 */
	public abstract Date getStartDate();

	/**
	 * @return start event id string
	 */
	public abstract String getStartEventId();

	/**
	 * @return version
	 */
	public abstract int getVersion();

	/**
	 * Method to compare objects using hashCode
	 * 
	 * @return int value of hashCode
	 */
	public abstract int hashCode();
	
	/**
	 * @return true if dates are good(start date before end date or either/both dates are null), false otherwise
	 */
	public abstract boolean isDateFlag();
	

	/**
	 * @return true if start date year is less than or equal to 9999, false otherwise
	 */
	public boolean isStartDateValid();
	
	/**
	 * @return true if end date year is less than or equal to 9999, false otherwise
	 */
	public boolean isEndDateValid();
	
	/**
	 * @return true if start date year is less than or equal to 9999, false otherwise
	 */
	public boolean isAllowUntilDateValid();	
	
	/**
	 * @return visibleFlag value of visible flag
	 */
	public boolean isVisibleFlag();

	/**
	 * @param addtoSchedule
	 *        add to schedule boolean flag
	 */
	public abstract void setAddtoSchedule(Boolean addtoSchedule);

	/**
	 * @param endDate
	 *        end date
	 */
	public abstract void setEndDate(Date endDate);

	/**
	 * @param endEventId
	 *        end event id
	 */
	public abstract void setEndEventId(String endEventId);

	/**
	 * @param module
	 *        module object
	 */
	public abstract void setModule(org.etudes.api.app.melete.ModuleObjService module);

	/**
	 * @param allowUntilDate
	 *        the allow Until date
	 */
	public abstract void setAllowUntilDate(Date allowUntilDate);

	/**
	 * @param startDate
	 *        the start date
	 */
	public abstract void setStartDate(Date startDate);

	/**
	 * @param startEventId
	 *        the start event id
	 */
	public abstract void setStartEventId(String startEventId);

	/**
	 * @param version
	 *        the version
	 */
	public abstract void setVersion(int version);

	/**
	 * @return string value of moduleshdatesservice
	 */
	public abstract String toString();
}
