/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.1/melete-hbm/src/java/org/etudes/component/app/melete/SectionResource.java $
 *
 ***********************************************************************************
 *
 * Copyright (c) 2008, 2009, 2010, 2011 Etudes, Inc.
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
package org.etudes.component.app.melete;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.etudes.api.app.melete.SectionResourceService;

public class SectionResource implements Serializable, SectionResourceService
{

	private org.etudes.component.app.melete.MeleteResource resource;

	private org.etudes.component.app.melete.Section section;

	/** identifier field */
	private Integer sectionId;

	/** default constructor */
	public SectionResource()
	{
	}

	/** full constructor */
	public SectionResource(org.etudes.component.app.melete.Section section, org.etudes.component.app.melete.MeleteResource resource)
	{

		this.section = section;
		this.resource = resource;
	}

	/**
	 * @return Returns the resource.
	 */
	public org.etudes.api.app.melete.MeleteResourceService getResource()
	{
		return resource;
	}

	/**
	 * @return Returns the section.
	 */
	public org.etudes.api.app.melete.SectionObjService getSection()
	{
		return section;
	}

	/**
	 * @return Returns the sectionId.
	 */
	public Integer getSectionId()
	{
		return sectionId;
	}

	/**
	 * @param resource
	 *        The resource to set.
	 */
	public void setResource(org.etudes.api.app.melete.MeleteResourceService resource)
	{
		this.resource = (MeleteResource) resource;
	}

	/**
	 * @param section
	 *        The section to set.
	 */
	public void setSection(org.etudes.api.app.melete.SectionObjService section)
	{
		this.section = (Section) section;
	}

	/**
	 * @param sectionId
	 *        The sectionId to set.
	 */
	public void setSectionId(Integer sectionId)
	{
		this.sectionId = sectionId;
	}

	public String toString()
	{
		return new ToStringBuilder(this).append("sectionId", getSectionId()).toString();
	}
}
