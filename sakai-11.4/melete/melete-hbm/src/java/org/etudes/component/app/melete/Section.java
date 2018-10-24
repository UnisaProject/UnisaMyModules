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

import org.etudes.api.app.melete.SectionObjService;

public class Section implements SectionObjService
{
	/** nullable persistent field */
	private boolean audioContent;

	/** persistent field */
	private String contentType;

	/** persistent field */
	private Date creationDate;

	/** nullable persistent field */
	private boolean deleteFlag;

	/** nullable persistent field */
	private String instr;

	/** persistent field */
	private Date modificationDate;

	/** persistent field */
	private String modifyUserId="";
	
	/** nullable persistent field */
	private org.etudes.component.app.melete.Module module;

	/** nullable persistent field */
	private int moduleId;

	/** nullable persistent field */
	private boolean openWindow;

	/** identifier field */
	private Integer sectionId;

	/** nullable persistent field */
	private org.etudes.component.app.melete.SectionResource sectionResource;

	/** nullable persistent field */
	private boolean textualContent;

	/** persistent field */
	private String title = "Untitled section";

	/** persistent field */
	private String userId="";
	
	/** nullable persistent field */
	private int version;

	/** nullable persistent field */
	private boolean videoContent;

	/** default constructor */
	public Section()
	{
	}

	/** full constructor */
	public Section(int moduleId, String userId, String modifyUserId, String title, String instr, String contentType, boolean audioContent, boolean videoContent, boolean textualContent,
			boolean openWindow, boolean deleteFlag, Date creationDate, Date modificationDate, int version,
			org.etudes.component.app.melete.Module module, org.etudes.component.app.melete.SectionResource sectionResource)
	{
		this.moduleId = moduleId;
		this.title = title;
		this.instr = instr;
		this.contentType = contentType;
		this.audioContent = audioContent;
		this.videoContent = videoContent;
		this.textualContent = textualContent;
		this.openWindow = openWindow;
		this.deleteFlag = deleteFlag;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.version = version;
		this.module = module;
		this.sectionResource = sectionResource;
		this.userId = userId;
		this.modifyUserId = modifyUserId;
	}

	/** copy constructor */
	public Section(Section oldSection)
	{
		this.title = oldSection.getTitle();
		this.instr = oldSection.getInstr();
		this.contentType = oldSection.getContentType();
		this.audioContent = oldSection.isAudioContent();
		this.videoContent = oldSection.isVideoContent();
		this.textualContent = oldSection.isTextualContent();
		this.openWindow = oldSection.isOpenWindow();
		this.deleteFlag = oldSection.isDeleteFlag();
		this.module = null;
		this.sectionResource = null;
		this.userId = oldSection.userId;
		this.modifyUserId = oldSection.modifyUserId;
	}

	/** minimal constructor */
	public Section(String title, String contentType, Date creationDate, Date modificationDate)
	{
		this.title = title;
		this.contentType = contentType;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
	}

	/** Custom constructor */
	public Section(String title, String userId, String modifyUserId, String instr,
			String contentType, boolean audioContent, boolean videoContent, boolean textualContent, boolean openWindow, boolean deleteFlag,
			Date creationDate, Date modificationDate)
	{
		this.title = title;
		this.userId = userId;
		this.modifyUserId = modifyUserId;
		this.instr = instr;
		this.contentType = contentType;
		this.audioContent = audioContent;
		this.videoContent = videoContent;
		this.textualContent = textualContent;
		this.openWindow = openWindow;
		this.deleteFlag = deleteFlag;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sectionId == null) ? 0 : sectionId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Section other = (Section) obj;
		if (sectionId == null)
		{
			if (other.sectionId != null) return false;
		}
		else if (!sectionId.equals(other.sectionId)) return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getContentType()
	{
		return this.contentType;
	}

	/**
	 * {@inheritDoc}
	 */
	public Date getCreationDate()
	{
		return this.creationDate;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getInstr()
	{
		return this.instr;
	}

	/**
	 * {@inheritDoc}
	 */
	public Date getModificationDate()
	{
		return this.modificationDate;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getModifyUserId()
	{
		return modifyUserId;
	}

	/**
	 * {@inheritDoc}
	 */
	public org.etudes.api.app.melete.ModuleObjService getModule()
	{
		return this.module;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getModuleId()
	{
		return this.moduleId;
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer getSectionId()
	{
		return this.sectionId;
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
	public String getTitle()
	{
		return this.title;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getUserId()
	{
		return userId;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int getVersion()
	{
		return this.version;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isAudioContent()
	{
		return this.audioContent;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isDeleteFlag()
	{
		return this.deleteFlag;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isOpenWindow()
	{
		return this.openWindow;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isTextualContent()
	{
		return this.textualContent;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isVideoContent()
	{
		return this.videoContent;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setAudioContent(boolean audioContent)
	{
		this.audioContent = audioContent;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setCreationDate(Date creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDeleteFlag(boolean deleteFlag)
	{
		this.deleteFlag = deleteFlag;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setInstr(String instr)
	{
		this.instr = instr;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setModificationDate(Date modificationDate)
	{
		this.modificationDate = modificationDate;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setModifyUserId(String modifyUserId)
	{
		this.modifyUserId = modifyUserId;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setModule(org.etudes.api.app.melete.ModuleObjService module)
	{
		this.module = (Module) module;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setModuleId(int moduleId)
	{
		this.moduleId = moduleId;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setOpenWindow(boolean openWindow)
	{
		this.openWindow = openWindow;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setSectionId(Integer sectionId)
	{
		this.sectionId = sectionId;
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
	public void setTextualContent(boolean textualContent)
	{
		this.textualContent = textualContent;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setVersion(int version)
	{
		this.version = version;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setVideoContent(boolean videoContent)
	{
		this.videoContent = videoContent;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString()
	{
		return new ToStringBuilder(this).append("sectionId", getSectionId()).toString();
	}

}
