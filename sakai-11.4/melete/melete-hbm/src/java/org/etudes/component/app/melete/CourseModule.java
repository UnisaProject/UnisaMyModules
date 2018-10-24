/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-hbm/src/java/org/etudes/component/app/melete/CourseModule.java $
 * $Id: CourseModule.java 73420 2011-03-29 18:45:36Z rashmi@etudes.org $  
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
package org.etudes.component.app.melete;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.etudes.api.app.melete.CourseModuleService;

public class CourseModule implements Serializable, CourseModuleService
{
	/** nullable persistent field */
	private boolean archvFlag;

	/** nullable persistent field */
	private String courseId;

	/** nullable persistent field */
	private Date dateArchived;

	/** nullable persistent field */
	private boolean deleteFlag;

	/** identifier field */
	private org.etudes.component.app.melete.Module module;

	/** identifier field */
	private Integer moduleId;

	/** persistent field */
	private int seqNo;

	/** default constructor */
	public CourseModule()
	{
	}

	/** minimal constructor */
	public CourseModule(int seqNo)
	{
		this.seqNo = seqNo;
	}

	/** full constructor */
	public CourseModule(String courseId, int seqNo, boolean archvFlag, Date dateArchived, boolean deleteFlag,
			org.etudes.component.app.melete.Module module)
	{
		this.courseId = courseId;
		this.seqNo = seqNo;
		this.archvFlag = archvFlag;
		this.dateArchived = dateArchived;
		this.deleteFlag = deleteFlag;
		this.module = module;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getCourseId()
	{
		return this.courseId;
	}

	/**
	 * {@inheritDoc}
	 */
	public Date getDateArchived()
	{
		return this.dateArchived;
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
	public Integer getModuleId()
	{
		return this.moduleId;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getSeqNo()
	{
		return this.seqNo;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isArchvFlag()
	{
		return this.archvFlag;
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
	public void setArchvFlag(boolean archvFlag)
	{
		this.archvFlag = archvFlag;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setCourseId(String courseId)
	{
		this.courseId = courseId;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDateArchived(Date dateArchived)
	{
		this.dateArchived = dateArchived;
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
	public void setModule(org.etudes.api.app.melete.ModuleObjService module)
	{
		this.module = (Module) module;
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
	public void setSeqNo(int seqNo)
	{
		this.seqNo = seqNo;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString()
	{
		return new ToStringBuilder(this).append("moduleId", getModuleId()).append("courseId", getCourseId()).toString();
	}

}
