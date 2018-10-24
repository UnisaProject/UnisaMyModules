/**********************************************************************************
 *
 * $URL: https://source.etudes.org/svn/apps/melete/tags/2.9.9/melete-impl/src/java/org/etudes/component/app/melete/BookmarkServiceImpl.java $
 * $Id: BookmarkServiceImpl.java 3641 2012-12-02 21:43:44Z ggolden $
 ***********************************************************************************
 *
 * Copyright (c) 2008, 2011 Etudes, Inc.
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
import java.util.List;
import java.util.Iterator;
import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.etudes.api.app.melete.BookmarkService;
import org.etudes.api.app.melete.BookmarkObjService;
import org.etudes.api.app.melete.exception.MeleteException;
import org.etudes.component.app.melete.MeleteUtil;

public class BookmarkServiceImpl implements Serializable, BookmarkService
{
	private BookmarkDB bookmarkDb;
	private Log logger = LogFactory.getLog(BookmarkServiceImpl.class);
	protected MeleteUtil meleteUtil = new MeleteUtil();

	/*
	 * {@inheritDoc}
	 */
	public void createFile(List<? extends BookmarkObjService> bmList, String fileName) throws Exception
	{
		StringBuffer bmStrbuf = new StringBuffer();
		for (Iterator<?> iter = bmList.iterator(); iter.hasNext();)
		{
			Bookmark bm = (Bookmark) iter.next();
			bmStrbuf.append("\nTitle: " + bm.getTitle());
			bmStrbuf.append("\nNotes: " + bm.getNotes());
			bmStrbuf.append("\n");
			bmStrbuf.append("\n------------------------------------------------------------------");
		}
		meleteUtil.createFileFromContent(bmStrbuf.toString().getBytes(), fileName);
	}

	/*
	 * {@inheritDoc}
	 */
	public void deleteBookmark(int bookmarkId) throws Exception
	{
		try
		{
			bookmarkDb.deleteBookmark(bookmarkId);
		}
		catch (Exception e)
		{
			logger.debug("melete bookmark business -- delete bookmark failed");
			throw new MeleteException("delete_bookmark_fail");
		}
	}

	/*
	 * {@inheritDoc}
	 */
	public void deleteFiles(File delfile)
	{

		meleteUtil.deleteFiles(delfile);
	}

	/*
	 * {@inheritDoc}
	 */
	public BookmarkObjService getBookmark(String userId, String siteId, int sectionId)
	{
		BookmarkObjService bObj = null;
		try
		{
			bObj = bookmarkDb.getBookmark(userId, siteId, sectionId);
		}
		catch (Exception e)
		{
			logger.debug("melete bookmark business --get bookmark failed");
		}
		return bObj;
	}

	/**
	 * @return Returns the bookmarkDb
	 */
	public BookmarkDB getBookmarkDb()
	{
		return bookmarkDb;
	}

	/*
	 * {@inheritDoc}
	 */
	public List<BookmarkObjService> getBookmarks(String userId, String siteId)
	{
		List<BookmarkObjService> mbList = null;
		try
		{
			mbList = bookmarkDb.getBookmarks(userId, siteId);
		}
		catch (Exception e)
		{
			logger.debug("melete bookmark business --get bookmarks failed");
		}
		return mbList;
	}

	/*
	 * {@inheritDoc}
	 */
	public int getLastVisitSectionId(boolean isAuthor, String userId, String siteId)
	{
		int sectionId = 0;
		try
		{
			sectionId = bookmarkDb.getLastVisitSectionId(isAuthor, userId, siteId);
		}
		catch (Exception e)
		{
			// It is ok to not have a last visited section
		}
		return sectionId;
	}

	/*
	 * {@inheritDoc}
	 */
	public void insertBookmark(BookmarkObjService mb) throws Exception
	{
		try
		{
			bookmarkDb.setBookmark((Bookmark) mb);
		}
		catch (Exception e)
		{
			logger.debug("melete bookmark business --add bookmark failed");
			throw new MeleteException("add_bookmark_fail");

		}
		return;
	}

	/**
	 * @param bookmarkDb
	 *        The bookmarkDb to set.
	 */
	public void setBookmarkDb(BookmarkDB bookmarkDb)
	{
		this.bookmarkDb = bookmarkDb;
	}

}
