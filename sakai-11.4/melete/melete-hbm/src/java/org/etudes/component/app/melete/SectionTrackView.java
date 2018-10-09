/**********************************************************************************
 *
 * $URL: https://source.etudes.org/svn/apps/melete/tags/2.9.9/melete-hbm/src/java/org/etudes/component/app/melete/SectionTrackView.java $
 * $Id: SectionTrackView.java 3641 2012-12-02 21:43:44Z ggolden $  
 ***********************************************************************************
 *
 * Copyright (c) 2010, 2011 Etudes, Inc.
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
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.etudes.api.app.melete.SectionTrackViewObjService;


public class SectionTrackView implements Serializable, SectionTrackViewObjService
{

	private org.etudes.component.app.melete.Section section;

	/** nullable persistent field */
	private int sectionId;

	private String userId;

	private Date firstViewDate;
	
	private Date viewDate;

	public SectionTrackView()
	{
		this.sectionId = 0;
		this.userId = null;
		this.viewDate = null;
		this.section = null;
		this.firstViewDate = null;
	}

	/*
	 * {@inheritDoc}
	 */
	public boolean equals(Object other)
	{
		if ((this == other)) return true;
		if (!(other instanceof SectionTrackView)) return false;
		SectionTrackView castOther = (SectionTrackView) other;
		return new EqualsBuilder().append(this.getSectionId(), castOther.getSectionId()).append(this.getUserId(), castOther.getUserId()).append(
				this.getViewDate(), castOther.getViewDate()).isEquals();
	}

	/*
	 * {@inheritDoc}
	 */
	public org.etudes.api.app.melete.SectionObjService getSection()
	{
		return section;
	}

	/*
	 * {@inheritDoc}
	 */
	public int getSectionId()
	{
		return this.sectionId;
	}

	/*
	 * {@inheritDoc}
	 */
	public String getUserId()
	{
		return userId;
	}

	/*
	 * {@inheritDoc}
	 */
	public Date getFirstViewDate()
	{
		return firstViewDate;
	}
	
	/*
	 * {@inheritDoc}
	 */
	public Date getViewDate()
	{
		return this.viewDate;
	}
	
	/*
	 * {@inheritDoc}
	 */
	public int hashCode()
	{
		return new HashCodeBuilder().append(getSectionId()).append(getUserId()).append(getViewDate()).toHashCode();
	}

	/*
	 * {@inheritDoc}
	 */
	public void setSection(org.etudes.api.app.melete.SectionObjService section)
	{
		this.section = (Section) section;
	}

	/*
	 * {@inheritDoc}
	 */
	public void setSectionId(int sectionId)
	{
		this.sectionId = sectionId;
	}

	/*
	 * {@inheritDoc}
	 */
	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	/*
	 * {@inheritDoc}
	 */
	public void setFirstViewDate(Date firstViewDate)
	{
		this.firstViewDate = firstViewDate;
	}
	
	/*
	 * {@inheritDoc}
	 */
	public void setViewDate(Date viewDate)
	{
		this.viewDate = viewDate;
	}
}
