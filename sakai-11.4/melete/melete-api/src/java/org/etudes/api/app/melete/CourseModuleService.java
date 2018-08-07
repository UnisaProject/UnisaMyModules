/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.1/melete-api/src/java/org/etudes/api/app/melete/CourseModuleService.java $
 * $Id: CourseModuleService.java 77082 2011-10-24 18:38:10Z rashmi@etudes.org $  
 ***********************************************************************************
 *
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
 *
 **********************************************************************************/
package org.etudes.api.app.melete;

import java.util.Date;

public interface CourseModuleService
{
	/**
	 * 
	 * @param other
	 * @return
	 */
	public abstract boolean equals(Object other);

	/**
	 * Get the Site Id
	 * 
	 * @return
	 */
	public abstract String getCourseId();

	/**
	 * get the date when module was archived
	 * 
	 * @return
	 */
	public abstract Date getDateArchived();

	/**
	 * Get Module Object.
	 * 
	 * @return
	 */
	public abstract org.etudes.api.app.melete.ModuleObjService getModule();
	
	/**
	 * Get Module id
	 * 
	 * @return
	 */
	public abstract Integer getModuleId();

	/**
	 * Get the sequence number of the module
	 * 
	 * @return
	 */
	public abstract int getSeqNo();

	/**
	 * 
	 * @return
	 */
	public abstract int hashCode();

	/**
	 * Flag for archive. Module is archived if true.
	 * 
	 * @return
	 */
	public abstract boolean isArchvFlag();

	/**
	 * Flag for deleting the module. Module is deleted if flag is true. We deep delete module now.
	 * 
	 * @return
	 */
	public abstract boolean isDeleteFlag();

	/**
	 * Set the archive flag
	 * 
	 * @param archvFlag
	 */
	public abstract void setArchvFlag(boolean archvFlag);

	/**
	 * Sets the course Id
	 * 
	 * @param courseId
	 */
	public abstract void setCourseId(String courseId);

	/**
	 * Set Archive date
	 * 
	 * @param dateArchived
	 */
	public abstract void setDateArchived(Date dateArchived);

	/**
	 * Set delete flag
	 * 
	 * @param deleteFlag
	 */
	public abstract void setDeleteFlag(boolean deleteFlag);

	/**
	 * Set Module Object
	 * 
	 * @param module
	 */
	public abstract void setModule(org.etudes.api.app.melete.ModuleObjService module);

	/**
	 * Set the sequence number
	 * 
	 * @param seqNo
	 */
	public abstract void setSeqNo(int seqNo);

	/**
	 * 
	 * @return
	 */
	public abstract String toString();
}
