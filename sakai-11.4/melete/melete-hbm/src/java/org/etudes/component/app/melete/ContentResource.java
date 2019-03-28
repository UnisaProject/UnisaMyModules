/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-hbm/src/java/org/etudes/component/app/melete/Section.java $
 * $Id: Section.java 80344 2012-06-14 22:20:56Z rashmi@etudes.org $  
 ***********************************************************************************
 *
 * Copyright (c) 2008,2009,2010,2011, 2012 Etudes, Inc.
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
package org.etudes.component.app.melete;

import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ContentResource
{
	/** persistent field */
	private String resourceId;

	/** nullable persistent field */
	private String resourceUuid;

	/** persistent field */
	private String inCollection;

	/** nullable persistent field */
	private String filePath;

	/** nullable persistent field */
	private Long xml;
	
	/** nullable persistent field */
	private String binaryEntity;

	/** persistent field */
	private int fileSize;

	/** persistent field */
	private String context;
	
	/** nullable persistent field */
	private String resourceTypeId;

	/** nullable persistent field */
	private org.etudes.component.app.melete.SectionResource sectionResource;

	/** default constructor */
	public ContentResource()
	{
	}

	/** full constructor */
	public ContentResource(String resourceId, String resourceUuid, String inCollection, String filePath, Long xml, String binaryEntity, int fileSize, String context, String resourceTypeId,
			org.etudes.component.app.melete.SectionResource sectionResource)
	{
		this.resourceId = resourceId;
		this.resourceUuid = resourceUuid;
		this.inCollection = inCollection;
		this.filePath = filePath;
		this.xml = xml;
		this.binaryEntity = binaryEntity;
		this.fileSize = fileSize;
		this.context = context;
		this.resourceTypeId = resourceTypeId;
		this.sectionResource = sectionResource;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resourceId == null) ? 0 : resourceId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		ContentResource other = (ContentResource) obj;
		if (resourceId == null)
		{
			if (other.resourceId != null) return false;
		}
		else if (!resourceId.equals(other.resourceId)) return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getResourceId()
	{
		return this.resourceId;
	}

	/**
	 * {@inheritDoc}
	 */
	public org.etudes.api.app.melete.SectionResourceService getSectionResource()
	{
		return this.sectionResource;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getInCollection()
	{
		return this.inCollection;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getResourceUuid()
	{
		return resourceUuid;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getFilePath()
	{
		return this.filePath;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Long getXml()
	{
		return this.xml;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int getFileSize()
	{
		return this.fileSize;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getBinaryEntity()
	{
		return this.binaryEntity;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getContext()
	{
		return this.context;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getResourceTypeId()
	{
		return this.resourceTypeId;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setResourceUuid(String resourceUuid)
	{
		this.resourceUuid = resourceUuid;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setInCollection(String inCollection)
	{
		this.inCollection = inCollection;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setXml(Long xml)
	{
		this.xml = xml;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setBinaryEntity(String binaryEntity)
	{
		this.binaryEntity = binaryEntity;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setContext(String context)
	{
		this.context = context;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setFileSize(int fileSize)
	{
		this.fileSize = fileSize;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setResourceTypeId(String resourceTypeId)
	{
		this.resourceTypeId = resourceTypeId;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setResourceId(String resourceId)
	{
		this.resourceId = resourceId;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setSectionResource(org.etudes.api.app.melete.SectionResourceService sectionResource)
	{
		this.sectionResource = (SectionResource) sectionResource;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString()
	{
		return new ToStringBuilder(this).append("resourceId", getResourceId()).toString();
	}

}
