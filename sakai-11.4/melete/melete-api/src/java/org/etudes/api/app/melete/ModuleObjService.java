/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.1/melete-api/src/java/org/etudes/api/app/melete/ModuleObjService.java $
 *
 ***********************************************************************************
 * Copyright (c) 2008, 2011 Etudes, Inc.
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
import java.util.Map;

public interface ModuleObjService
{

	/**
	 * Compare module objects using equals method
	 * 
	 * @param other
	 * @return true if objects are equal, false if not
	 */
	public abstract boolean equals(Object other);

	/**
	 * @return coursemoduleservice
	 */
	public abstract org.etudes.api.app.melete.CourseModuleService getCoursemodule();
	
	/**
	 * @return creation date
	 */
	public abstract Date getCreationDate();

	/**
	 * @return description
	 */
	public abstract String getDescription();

	/**
	 * @return keywords
	 */
	public abstract String getKeywords();

	/**
	 * @return
	 */
	public abstract Date getModificationDate();

	/**
	 * @return get modified by user id
	 */
	public abstract String getModifyUserId();

	/**
	 * @return module id
	 */
	public abstract Integer getModuleId();

	/**
	 * @return moduleshdatesservice
	 */
	public abstract org.etudes.api.app.melete.ModuleShdatesService getModuleshdate();

	/**
	 * @return map of sections
	 */
	public abstract Map<Integer,SectionObjService> getSections();

	/**
	 * @return seqXml string
	 */
	public abstract String getSeqXml();

	/**
	 * @return title
	 */
	public abstract String getTitle();

	/**
	 * @return get user id
	 */
	public abstract String getUserId();

	/**
	 * @return version
	 */
	public abstract int getVersion();

	/**
	 * @return whats next
	 */
	public abstract String getWhatsNext();

	/**
	 * Compare module objects using hashcode
	 * 
	 * @return hashcode int value of object
	 */
	public abstract int hashCode();

	/**
	 * @param coursemodule
	 *        coursemoduleservice to set
	 */
	public abstract void setCoursemodule(org.etudes.api.app.melete.CourseModuleService coursemodule);

	/**
	 * @param creationDate
	 *        creation date
	 */
	public abstract void setCreationDate(Date creationDate);

	/**
	 * @param description
	 */
	public abstract void setDescription(String description);

	/**
	 * @param keywords
	 */
	public abstract void setKeywords(String keywords);

	/**
	 * @param modificationDate
	 *        modification date
	 */
	public abstract void setModificationDate(Date modificationDate);

	/**
	 * @param modifyUserId
	 *        user_id of modifying author
	 */
	public abstract void setModifyUserId(String modifyUserId);
	
	/**
	 * @param moduleId
	 *        the module id
	 */
	public abstract void setModuleId(Integer moduleId);

	/**
	 * @param moduleshdate
	 *        moduleshdateservice to set
	 */
	public abstract void setModuleshdate(org.etudes.api.app.melete.ModuleShdatesService moduleshdate);

	/**
	 * @param sections
	 *        map of sections to set
	 */
	public abstract void setSections(Map<Integer,SectionObjService> sections);

	/**
	 * @param seqXml
	 *        sequence xml to set
	 */
	public abstract void setSeqXml(String seqXml);

	/**
	 * @param title
	 *        title of section
	 */
	public abstract void setTitle(String title);

	/**
	 * @param userId
	 *        user id
	 */
	public abstract void setUserId(String userId);

	/**
	 * @param version
	 *        version to set
	 */
	public abstract void setVersion(int version);

	/**
	 * @param whatsNext
	 */
	public abstract void setWhatsNext(String whatsNext);

	/**
	 * @return string value of module object
	 */
	public abstract String toString();
}
