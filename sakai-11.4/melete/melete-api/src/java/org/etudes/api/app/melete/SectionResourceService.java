/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-api/src/java/org/etudes/api/app/melete/SectionResourceService.java $
 * $Id: SectionResourceService.java 73460 2011-03-30 21:29:45Z rashmi@etudes.org $  
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

public interface SectionResourceService
{
	/**
	 * @return Returns the resource.
	 */
	public abstract org.etudes.api.app.melete.MeleteResourceService getResource();

	/**
	 * @return Returns the section.
	 */
	public abstract org.etudes.api.app.melete.SectionObjService getSection();

	/**
	 * @return Returns the sectionId.
	 */
	public abstract Integer getSectionId();

	/**
	 * @param resource
	 *        The resource to set.
	 */
	public abstract void setResource(org.etudes.api.app.melete.MeleteResourceService resource);

	/**
	 * @param section
	 *        The section to set.
	 */
	public abstract void setSection(org.etudes.api.app.melete.SectionObjService section);

	/**
	 * @param sectionId
	 *        The sectionId to set.
	 */
	public abstract void setSectionId(Integer sectionId);

	public abstract String toString();
}
