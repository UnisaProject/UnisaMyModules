/**********************************************************************************
 *
 * $URL: https://source.etudes.org/svn/apps/melete/tags/2.9.1forSakai/melete-api/src/java/org/etudes/api/app/melete/BookmarkObjService.java $
 *
 ***********************************************************************************
 * Copyright (c) 2010, 2011 Etudes, Inc.
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
 ****************************************************************************************/
package org.etudes.api.app.melete;

public interface BookmarkObjService
{

	/**
	 * @return the bookmarkId
	 */
	public abstract int getBookmarkId();

	/**
	 * @return the lastVisited
	 */
	public abstract Boolean getLastVisited();

	/**
	 * @return the notes
	 */
	public abstract String getNotes();

	/**
	 * @return the section
	 */
	public abstract org.etudes.api.app.melete.SectionObjService getSection();

	/**
	 * @return the section id
	 */
	public abstract int getSectionId();

	/**
	 * @return the siteId
	 */
	public abstract String getSiteId();

	/*
	 * @return the title
	 */
	public abstract String getTitle();

	/**
	 * @return the userId
	 */
	public abstract String getUserId();

	/**
	 * @return the sectionVisibleFlag
	 */
	public abstract boolean isSectionVisibleFlag();

	/**
	 * @param bookmarkId
	 *        the bookmarkId to set
	 */
	public abstract void setBookmarkId(int bookmarkId);

	/**
	 * @param lastVisited
	 *        the lastVisited to set
	 */
	public abstract void setLastVisited(Boolean lastVisited);

	/**
	 * @param notes
	 *        the notes to set
	 */
	public abstract void setNotes(String notes);

	/**
	 * @param section
	 *        the section to set
	 */
	public abstract void setSection(org.etudes.api.app.melete.SectionObjService section);

	/**
	 * @param sectionId
	 *        the section id to set
	 */
	public abstract void setSectionId(int sectionId);

	/**
	 * @param sectionVisibleFlag
	 *        the sectionVisibleFlag to set
	 */
	public abstract void setSectionVisibleFlag(boolean sectionVisibleFlag);

	/**
	 * @param siteId
	 *        the siteId to set
	 */
	public abstract void setSiteId(String siteId);

	/**
	 * @param title
	 *        the title to set
	 */
	public abstract void setTitle(String title);

	/**
	 * @param userId
	 *        the userId to set
	 */
	public abstract void setUserId(String userId);

}
