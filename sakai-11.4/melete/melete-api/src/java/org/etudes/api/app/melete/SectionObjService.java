/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.1/melete-api/src/java/org/etudes/api/app/melete/SectionObjService.java $
 * $Id: SectionObjService.java 80314 2012-06-12 22:15:39Z rashmi@etudes.org $  
 ***********************************************************************************
 *
 * Copyright (c) 2008,2009,2010,2011 Etudes, Inc.
 *
 * Portions completed before September 1, 2008 Copyright (c) 2004, 2005, 2006, 2007, 2008 Foothill College, ETUDES Project
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

import java.io.Serializable;
import java.util.Date;

public interface SectionObjService extends Serializable
{
	/**
	 * {@inheritDoc}
	 */
	public abstract boolean equals(Object other);

	/**
	 * Get section content type like noType, typeEditor, typeUpload, typeLink or typeLTI
	 * 
	 * @return
	 */
	public abstract String getContentType();

	/**
	 * Get section creation date
	 * 
	 * @return
	 */
	public abstract Date getCreationDate();

	/**
	 * Get section instructions
	 * 
	 * @return
	 */
	public abstract String getInstr();

	/**
	 * Get section's modification date
	 * 
	 * @return
	 */
	public abstract Date getModificationDate();

	/**
	 * Get modified by userid
	 * 
	 * @return
	 */
	public abstract String getModifyUserId();
	
	/**
	 * Get Module.
	 * 
	 * @return
	 */
	public abstract org.etudes.api.app.melete.ModuleObjService getModule();

	/**
	 * Get Module Id
	 * 
	 * @return
	 */
	public abstract int getModuleId();

	/**
	 * Get section Id
	 * 
	 * @return
	 */
	public Integer getSectionId();

	/**
	 * Get section resource
	 * 
	 * @return
	 */
	public abstract org.etudes.api.app.melete.SectionResourceService getSectionResource();

	/**
	 * Get Section title
	 * 
	 * @return
	 */
	public abstract String getTitle();

	/**
	 * Get created by userid
	 * 
	 * @return
	 */
	public abstract String getUserId();
	
	/**
	 * 
	 * @return
	 */
	public abstract int getVersion();

	/**
	 * {@inheritDoc}
	 */
	public abstract int hashCode();

	/**
	 * Does content have audio
	 * 
	 * @return
	 */
	public abstract boolean isAudioContent();

	/**
	 * Checks if section is marked as deleted
	 * 
	 * @return
	 */
	public abstract boolean isDeleteFlag();

	/**
	 * Checks to open content in a new window/tab
	 * 
	 * @return
	 */
	public abstract boolean isOpenWindow();

	/**
	 * Does content have textual content
	 * 
	 * @return
	 */
	public abstract boolean isTextualContent();

	/**
	 * Does content have video
	 * 
	 * @return
	 */
	public abstract boolean isVideoContent();

	/**
	 * set if content has audio
	 * 
	 * @param audioContent
	 */
	public abstract void setAudioContent(boolean audioContent);

	/**
	 * Set section content type
	 * 
	 * @param contentType
	 */
	public abstract void setContentType(String contentType);

	/**
	 * Set section's creation date
	 * 
	 * @param creationDate
	 */
	public abstract void setCreationDate(Date creationDate);

	/**
	 * Mark section as deleted.
	 * 
	 * @param deleteFlag
	 */
	public abstract void setDeleteFlag(boolean deleteFlag);

	/**
	 * Set section instructions
	 * 
	 * @param instr
	 */
	public abstract void setInstr(String instr);

	/**
	 * set section's modification date
	 * 
	 * @param modificationDate
	 */
	public abstract void setModificationDate(Date modificationDate);

	/**
	 * @param modifyUserId
	 *        user_id of modifying author
	 */
	public abstract void setModifyUserId(String modifyUserId);
	
	/**
	 * Set Module
	 * 
	 * @param module
	 */
	public abstract void setModule(org.etudes.api.app.melete.ModuleObjService module);

	/**
	 * Set Module Id
	 * 
	 * @param moduleId
	 */
	public abstract void setModuleId(int moduleId);

	/**
	 * Set to open content in new window/tab
	 * 
	 * @param openWindow
	 */
	public abstract void setOpenWindow(boolean openWindow);

	/**
	 * Set Section id
	 * 
	 * @param sectionId
	 */
	public void setSectionId(Integer sectionId);

	/**
	 * Set section resource
	 * 
	 * @param sectionResource
	 */
	public abstract void setSectionResource(org.etudes.api.app.melete.SectionResourceService sectionResource);

	/**
	 * Set if content has text
	 * 
	 * @param textualContent
	 */
	public abstract void setTextualContent(boolean textualContent);

	/**
	 * Set section title
	 * 
	 * @param title
	 */
	public abstract void setTitle(String title);

	/**
	 * @param userId
	 *        user_id of creator
	 */
	public abstract void setUserId(String userId);
	
	/**
	 * 
	 * @param version
	 */
	public abstract void setVersion(int version);

	/**
	 * Set if content has video
	 * 
	 * @param videoContent
	 */
	public abstract void setVideoContent(boolean videoContent);

	/**
	 * {@inheritDoc}
	 */
	public abstract String toString();
}
