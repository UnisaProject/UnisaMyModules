/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.1/melete-hbm/src/java/org/etudes/component/app/melete/Module.java $
 * $Id: Module.java 80314 2012-06-12 22:15:39Z rashmi@etudes.org $  
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
package org.etudes.component.app.melete;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.etudes.api.app.melete.ModuleObjService;
import org.etudes.api.app.melete.SectionObjService;

public class Module implements Serializable, ModuleObjService
{
	/** nullable persistent field */
	private org.etudes.component.app.melete.CourseModule coursemodule;

	/** persistent field */
	private Date creationDate;

	private Map deletedSections;

	/** nullable persistent field */
	private String description;

	/** persistent field */
	private String keywords;

	/** nullable persistent field */
	private Date modificationDate;

	/** persistent field */
	private String modifyUserId;
	
	/** identifier field */
	private Integer moduleId;
	
	protected String moduleIdStr;
	

	/** nullable persistent field */
	private org.etudes.component.app.melete.ModuleShdates moduleshdate;

	/** persistent field */
	private Map<Integer, SectionObjService> sections;

	/** nullable persistent field */
	private String seqXml;

	/** persistent field */
	private String title;

	/** persistent field */
	private String userId;

	/** nullable persistent field */
	private int version;

	/** nullable persistent field */
	private String whatsNext;

	/** default constructor */
	public Module()
	{
	}

	/** Copy constructor */
	public Module(Module oldModule)
	{
		this.title = oldModule.getTitle();
		this.description = oldModule.getDescription();
		this.keywords = oldModule.getKeywords();
		this.whatsNext = oldModule.getWhatsNext();
		this.seqXml = null;
		this.moduleshdate = null;
		this.coursemodule = null;
		this.sections = null;
		this.deletedSections = null;
		this.userId = oldModule.userId;
		this.modifyUserId = oldModule.modifyUserId;
	}

	/** minimal constructor */
	public Module(String title, String keywords, String userId, Date creationDate, Map<Integer, SectionObjService> sections,
			Map deletedSections)
	{
		this.title = title;
		this.keywords = keywords;
		this.userId = userId;
		this.creationDate = creationDate;
		this.sections = sections;
		this.deletedSections = deletedSections;
	}

	/* Custom constructor */
	public Module(String title, String description, String keywords, String userId, String modifyUserId, String whatsNext, Date creationDate,
			Date modificationDate, String seqXml)
	{
		this.title = title;
		this.description = description;
		this.keywords = keywords;
		this.userId = userId;
		this.modifyUserId = modifyUserId;
		this.whatsNext = whatsNext;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.seqXml = seqXml;

	}

	/** full constructor */

	public Module(String title, String learnObj, String description, String keywords, String createdByFname, String createdByLname, String userId,
			String modifiedByFname, String modifiedByLname, String modifyUserId, String institute, String whatsNext, Date creationDate, Date modificationDate,
			String seqXml, int version, org.etudes.component.app.melete.ModuleShdates moduleshdate,
			org.etudes.component.app.melete.CourseModule coursemodule, Map<Integer, SectionObjService> sections, Map deletedSections)
	{
		this.title = title;
		this.description = description;
		this.keywords = keywords;
		this.userId = userId;
		this.whatsNext = whatsNext;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.modifyUserId = modifyUserId;
		this.seqXml = seqXml;
		this.version = version;
		this.moduleshdate = moduleshdate;
		this.coursemodule = coursemodule;
		this.sections = sections;
		this.deletedSections = deletedSections;
	}

	@Override
	public boolean equals(Object obj)
	{
	if (this == obj) return true;
	if ((obj == null) || (obj.getClass() != this.getClass())) return false;
	if ((this.moduleId == null) || (((Module) obj).moduleId == null)) return false;
	return this.moduleId.equals(((Module) obj).moduleId);
	}

	/**
	 * {@inheritDoc}
	 */
	public org.etudes.api.app.melete.CourseModuleService getCoursemodule()
	{
		return this.coursemodule;
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
	public Map getDeletedSections()
	{
		return this.deletedSections;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getDescription()
	{
		return this.description;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getKeywords()
	{
		return this.keywords;
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
	public Integer getModuleId()
	{
		return this.moduleId;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getModuleIdStr() {
		return Integer.toString(this.moduleId);
	}

	/**
	 * {@inheritDoc}
	 */
	public org.etudes.api.app.melete.ModuleShdatesService getModuleshdate()
	{
		return this.moduleshdate;
	}

	/**
	 * {@inheritDoc}
	 */
	public Map<Integer,SectionObjService> getSections()
	{
		return this.sections;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getSeqXml()
	{
		return this.seqXml;
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
		return this.userId;
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
	public String getWhatsNext()
	{
		return this.whatsNext;
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder().append(getModuleId()).toHashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	public void setCoursemodule(org.etudes.api.app.melete.CourseModuleService coursemodule)
	{
		this.coursemodule = (CourseModule) coursemodule;
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
	public void setDeletedSections(Map deletedSections)
	{
		this.deletedSections = deletedSections;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setKeywords(String keywords)
	{
		this.keywords = keywords;
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
	public void setModuleId(Integer moduleId)
	{
		this.moduleId = moduleId;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setModuleshdate(org.etudes.api.app.melete.ModuleShdatesService moduleshdate)
	{
		this.moduleshdate = (ModuleShdates) moduleshdate;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setSections(Map<Integer, SectionObjService> sections)
	{
		this.sections = sections;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setSeqXml(String seqXml)
	{
		this.seqXml = seqXml;
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
	public void setWhatsNext(String whatsNext)
	{
		this.whatsNext = whatsNext;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString()
	{
		return new ToStringBuilder(this).append("moduleId", getModuleId()).toString();
	}
}
