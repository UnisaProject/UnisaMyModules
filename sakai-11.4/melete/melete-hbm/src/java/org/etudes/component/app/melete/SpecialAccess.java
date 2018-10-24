/**********************************************************************************
 *
 * $URL: https://source.etudes.org/svn/apps/melete/tags/2.9.9/melete-hbm/src/java/org/etudes/component/app/melete/SpecialAccess.java $
 * $Id: SpecialAccess.java 3641 2012-12-02 21:43:44Z ggolden $  
 ***********************************************************************************
 *
 * Copyright (c) 2010,2011 Etudes, Inc.
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

import java.util.Calendar;
import java.util.Date;
import org.etudes.api.app.melete.SpecialAccessObjService;

/** @author Hibernate CodeGenerator */
public class SpecialAccess implements Serializable, SpecialAccessObjService
{

	private int accessId;

	/** nullable persistent field */
	private Date allowUntilDate;

	/** nullable persistent field */
	private Date endDate;

	private org.etudes.component.app.melete.Module module;

	/** nullable persistent field */
	private int moduleId;

	/** nullable persistent field */
	private boolean overrideAllowUntil;

	private boolean overrideEnd;

	/** nullable persistent field */
	private boolean overrideStart;

	/** nullable persistent field */
	private Date startDate;

	private String userNames;

	private String users;

	private boolean valid;
	protected String accessIdStr;

	protected boolean selected;

	public SpecialAccess()
	{
		this.moduleId = 0;
		this.module = null;
		this.users = null;
		this.startDate = null;
		this.endDate = null;
		this.allowUntilDate = null;
		this.overrideStart = false;
		this.overrideEnd = false;
		this.overrideStart = false;
	}

	/*
	 * {@inheritDoc}
	 */
	public int getAccessId()
	{
		return accessId;
	}

	public String getAccessIdStr()
	{
		return Integer.toString(this.accessId);
	}

	/*
	 * {@inheritDoc}
	 */
	public Date getAllowUntilDate()
	{
		return this.allowUntilDate;
	}

	/*
	 * {@inheritDoc}
	 */
	public Date getEndDate()
	{
		return this.endDate;
	}

	/*
	 * {@inheritDoc}
	 */
	public org.etudes.api.app.melete.ModuleObjService getModule()
	{
		return module;
	}

	/*
	 * {@inheritDoc}
	 */
	public int getModuleId()
	{
		return this.moduleId;
	}

	/*
	 * {@inheritDoc}
	 */
	public Date getStartDate()
	{
		return this.startDate;
	}

	/*
	 * {@inheritDoc}
	 */
	public String getUserNames()
	{
		return this.userNames;
	}

	/*
	 * {@inheritDoc}
	 */
	public String getUsers()
	{
		return users;
	}

	/*
	 * {@inheritDoc}
	 */
	public boolean isOverrideAllowUntil()
	{
		return this.overrideAllowUntil;
	}

	/*
	 * {@inheritDoc}
	 */
	public boolean isOverrideEnd()
	{
		return this.overrideEnd;
	}

	/*
	 * {@inheritDoc}
	 */
	public boolean isOverrideStart()
	{
		return this.overrideStart;
	}

	/*
	 * {@inheritDoc}
	 */
	public boolean isSelected()
	{
		return selected;
	}

	/**
	 * Determines if special access entry is valid, checks against module and special access dates
	 * 
	 * @return true if special access is valid, false otherwise
	 */
	public boolean isValid()
	{
		Date actualStartDate, actualEndDate, actualAllowUntilDate;
		if (overrideStart)
			actualStartDate = getStartDate();
		else
			actualStartDate = getModule().getModuleshdate().getStartDate();
		if (overrideEnd)
			actualEndDate = getEndDate();
		else
			actualEndDate = getModule().getModuleshdate().getEndDate();
		if (overrideAllowUntil)
			actualAllowUntilDate = getAllowUntilDate();
		else
			actualAllowUntilDate = getModule().getModuleshdate().getAllowUntilDate();
		
		// start, if defined, must be before allowUntil and End, if defined
		if ((actualStartDate != null) && (actualEndDate != null) && (!actualStartDate.before(actualEndDate))) return Boolean.FALSE;
		if ((actualStartDate != null) && (actualAllowUntilDate != null) && (!actualStartDate.before(actualAllowUntilDate))) return Boolean.FALSE;

		// End, if defined, must be not after allowUntil, if defined
		if ((actualEndDate != null) && (actualAllowUntilDate != null) && (actualEndDate.after(actualAllowUntilDate))) return Boolean.FALSE;

		return true;
	}

	/*
	 * {@inheritDoc}
	 */
	public void setAccessId(int accessId)
	{
		this.accessId = accessId;
	}

	/*
	 * {@inheritDoc}
	 */
	public void setAllowUntilDate(Date allowUntilDate)
	{
		this.allowUntilDate = allowUntilDate;
	}

	/*
	 * {@inheritDoc}
	 */
	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	/*
	 * {@inheritDoc}
	 */
	public void setModule(org.etudes.api.app.melete.ModuleObjService module)
	{
		this.module = (Module) module;
	}

	/*
	 * {@inheritDoc}
	 */
	public void setModuleId(int moduleId)
	{
		this.moduleId = moduleId;
	}

	/*
	 * {@inheritDoc}
	 */
	public void setOverrideAllowUntil(boolean overrideAllowUntil)
	{
		this.overrideAllowUntil = overrideAllowUntil;
	}

	/*
	 * {@inheritDoc}
	 */
	public void setOverrideEnd(boolean overrideEnd)
	{
		this.overrideEnd = overrideEnd;
	}

	/*
	 * {@inheritDoc}
	 */
	public void setOverrideStart(boolean overrideStart)
	{
		this.overrideStart = overrideStart;
	}

	/*
	 * {@inheritDoc}
	 */
	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

	/*
	 * {@inheritDoc}
	 */
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	/*
	 * {@inheritDoc}
	 */
	public void setUserNames(String userNames)
	{
		this.userNames = userNames;
	}

	/*
	 * {@inheritDoc}
	 */
	public void setUsers(String users)
	{
		this.users = users;
	}
}
