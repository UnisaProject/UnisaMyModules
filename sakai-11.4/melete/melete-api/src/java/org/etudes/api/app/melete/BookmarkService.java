/**********************************************************************************
 *
 * $URL: https://source.etudes.org/svn/apps/melete/tags/2.9.9/melete-api/src/java/org/etudes/api/app/melete/BookmarkService.java $
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
 ***************************************************************************************/
package org.etudes.api.app.melete;

import java.util.List;
import java.io.File;


public interface BookmarkService
{

	/**
	 * Invokes Melete util's create file method, used for exporting notes
	 * 
	 * @param bmList
	 *        List of bookmarks
	 * @param fileName
	 *        Filename to export to
	 * @throws Exception
	 */
	public void createFile(List<? extends BookmarkObjService> bmList, String fileName) throws Exception;

	/**
	 * Deletes a bookmark
	 * 
	 * @param bookmarkId
	 *        Bookmark id
	 * @throws Exception
	 */
	public void deleteBookmark(int bookmarkId) throws Exception;

	/**
	 * Invokes Melete util's delete file method, used for exporting notes
	 * 
	 * @param delfile
	 *        File object of file to be deleted
	 */
	public void deleteFiles(File delfile);

	/**
	 * Get bookmark for this user, site and section
	 * 
	 * @param userId
	 *        User id
	 * @param siteId
	 *        Site id
	 * @param sectionId
	 *        Section id
	 * @return Bookmark object or null if bookmark doesn't exist
	 */
	public BookmarkObjService getBookmark(String userId, String siteId, int sectionId);

	/**
	 * Get bookmarks for this user and site
	 * 
	 * @param userId
	 *        User id
	 * @param siteId
	 *        Site id
	 * @return List of bookmarks, empty list if none are found
	 */
	public List<BookmarkObjService> getBookmarks(String userId, String siteId);

	/**
	 * Returns the section bookmarked as the last visited. For students, checks to see if special access is defined. If restricted, returns 0.
	 * 
	 * @param isAuthor
	 *        true for author, false otherwise
	 * @param userId
	 *        user id
	 * @param siteId
	 *        site id
	 * @return
	 */
	public int getLastVisitSectionId(boolean isAuthor, String userId, String siteId);

	/**
	 * Insert a bookmark
	 * 
	 * @param mb
	 *        The bookmark object
	 * @throws Exception
	 */
	public void insertBookmark(BookmarkObjService mb) throws Exception;
}
