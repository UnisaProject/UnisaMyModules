/**********************************************************************************
 *
 * $URL: https://source.etudes.org/svn/apps/melete/tags/2.9.9/melete-hbm/src/java/org/etudes/component/app/melete/Bookmark.java $
 * $Id: Bookmark.java 3641 2012-12-02 21:43:44Z ggolden $  
 ***********************************************************************************
 *
 * Copyright (c) 2010, 2011 Etudes, Inc.
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

import org.etudes.api.app.melete.BookmarkObjService;

/** @author Hibernate CodeGenerator */
public class Bookmark implements Serializable, BookmarkObjService
{

	private int bookmarkId;

	private String briefNotes;

	private Boolean lastVisited;

	private String notes;

	private org.etudes.component.app.melete.Section section;

	/** nullable persistent field */
	private int sectionId;

	private boolean sectionVisibleFlag;

	private String siteId;

	private String title;

	private String userId;

	public Bookmark()
	{
		this.sectionId = 0;
		this.userId = null;
		this.siteId = null;
		this.section = null;
		this.title = null;
		this.notes = null;
		this.lastVisited = null;
	}

	/*
	 * {@inheritDoc}
	 */
	public int getBookmarkId()
	{
		return bookmarkId;
	}

	/*
	 * {@inheritDoc}
	 */
	public String getBriefNotes()
	{
		return briefNotes;
	}

	/*
	 * {@inheritDoc}
	 */
	public Boolean getLastVisited()
	{
		return lastVisited;
	}

	/*
	 * {@inheritDoc}
	 */
	public String getNotes()
	{
		return notes;
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
	public String getSiteId()
	{
		return siteId;
	}

	/*
	 * {@inheritDoc}
	 */
	public String getTitle()
	{
		return this.title;
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
	public boolean isSectionVisibleFlag()
	{
		return sectionVisibleFlag;
	}

	/*
	 * {@inheritDoc}
	 */
	public void setBookmarkId(int bookmarkId)
	{
		this.bookmarkId = bookmarkId;
	}

	/*
	 * {@inheritDoc}
	 */
	public void setBriefNotes(String briefNotes)
	{
		this.briefNotes = briefNotes;
	}

	/*
	 * {@inheritDoc}
	 */
	public void setLastVisited(Boolean lastVisited)
	{
		this.lastVisited = lastVisited;
	}

	/*
	 * {@inheritDoc}
	 */
	public void setNotes(String notes)
	{
		this.notes = notes;
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
	public void setSectionVisibleFlag(boolean sectionVisibleFlag)
	{
		this.sectionVisibleFlag = sectionVisibleFlag;
	}

	/*
	 * {@inheritDoc}
	 */
	public void setSiteId(String siteId)
	{
		this.siteId = siteId;
	}

	/*
	 * {@inheritDoc}
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/*
	 * {@inheritDoc}
	 */
	public void setUserId(String userId)
	{
		this.userId = userId;
	}

}
