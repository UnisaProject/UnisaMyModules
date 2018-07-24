/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.1/melete-hbm/src/java/org/etudes/component/app/melete/ViewSecBean.java $
 * $Id: ViewSecBean.java 75093 2011-06-22 20:58:10Z rashmi@etudes.org $  
 ***********************************************************************************
 *
 * Copyright (c) 2009, 2011 Etudes, Inc.
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
import org.etudes.api.app.melete.ViewSecBeanService;

public class ViewSecBean implements Serializable, ViewSecBeanService
{

	protected boolean selected;
	protected int sectionId;
	protected String contentType;
	protected String displayClass;
	protected String displaySequence;
	protected String title;
	private Date viewDate;

	/** default constructor */
	public ViewSecBean()
	{
	}

	/**
	 * @param selected
	 * @param sectionId
	 * @param contentType
	 * @param displaySequence
	 * @param title
	 */
	public ViewSecBean(boolean selected, int sectionId, String contentType, String displaySequence, String title, Date viewDate)
	{
		super();
		this.selected = selected;
		this.sectionId = sectionId;
		this.contentType = contentType;
		this.displaySequence = displaySequence;
		this.title = title;
		this.viewDate = viewDate;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getContentType()
	{
		return this.contentType;
	}

	public String getDisplayClass()
	{
		return displayClass;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getDisplaySequence()
	{
		return displaySequence;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getSectionId()
	{
		return this.sectionId;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getTitle()
	{
		return this.title;
	}

	/*
	 * {@inheritDoc}
	 */
	public Date getViewDate()
	{
		return this.viewDate;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isSelected()
	{
		return selected;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}

	public void setDisplayClass(String displayClass)
	{
		this.displayClass = displayClass;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDisplaySequence(String displaySequence)
	{
		this.displaySequence = displaySequence;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setSectionId(int sectionId)
	{
		this.sectionId = sectionId;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}	

	/**
	 * {@inheritDoc}
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/*
	 * {@inheritDoc}
	 */
	public void setViewDate(Date viewDate)
	{
		this.viewDate = viewDate;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString()
	{
		return new ToStringBuilder(this).toString();
	}

}
