/**********************************************************************************
 * $URL$
 * $Id$
 ***********************************************************************************
 *
 * Copyright (c) 2003, 2004, 2005, 2006, 2007, 2008, 2009 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.opensource.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **********************************************************************************/

package org.sakaiproject.discussionforums.impl;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.discussionforums.api.DiscussionForumsService;
import org.sakaiproject.discussionforums.api.model.Forum;
import org.sakaiproject.discussionforums.api.model.ForumTopicDetails;
import org.sakaiproject.discussionforums.api.model.ForumMessage;
import org.sakaiproject.tool.api.Session; 
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.UserDirectoryService;
import org.w3c.dom.Element;

/**
 * <p>
 * Implements the DiscussionForumsService, all but a Storage model.
 * </p>
 */
public abstract class BaseDiscussionForumsService implements DiscussionForumsService 
{
	/** Our log (commons). */
	private static Logger M_log = LoggerFactory.getLogger(BaseDiscussionForumsService.class);
	protected Storage m_storage = null;
	protected abstract Storage newStorage();



	/**********************************************************************************************************************************************************************************************************************************************************
	 * Constructors, Dependencies and their setter methods
	 *********************************************************************************************************************************************************************************************************************************************************/

	/** Dependency: SessionManager */
	protected SessionManager m_sessionManager = null;

	/**
	 * Dependency: SessionManager.
	 * 
	 * @param service
	 *        The SessionManager.
	 */
	public void setSessionManager(SessionManager service)
	{
		m_sessionManager = service;
	}

	/** Dependency: UsageSessionService */
	protected UsageSessionService m_usageSessionService = null;

	/**
	 * Dependency: UsageSessionService.
	 * 
	 * @param service
	 *        The UsageSessionService.
	 */
	public void setUsageSessionService(UsageSessionService service)
	{
		m_usageSessionService = service;
	}

	/** Dependency: UserDirectoryService */
	protected UserDirectoryService m_userDirectoryService = null;

	/**
	 * Dependency: UserDirectoryService.
	 * 
	 * @param service
	 *        The UserDirectoryService.
	 */
	public void setUserDirectoryService(UserDirectoryService service)
	{	
		m_userDirectoryService = service;
	}

	/** Dependency: EventTrackingService */
	protected EventTrackingService m_eventTrackingService = null;

	/**
	 * Dependency: EventTrackingService.
	 * 
	 * @param service
	 *        The EventTrackingService.
	 */
	public void setEventTrackingService(EventTrackingService service)
	{
		m_eventTrackingService = service;
	}

	/////// Forum Details Start ///////
	public List getForumList(String siteId, String sortby, String sortorder)
	{
		
		List forums = m_storage.getForumList(siteId, sortby, sortorder);

		return forums;

	}
	
	public void insertForum(Forum forum)
	{
		 m_storage.insertForum(forum);
	}
	
	public void updateForum(String forumName, String forumDescription, Integer forumId)
	{
		m_storage.updateForum(forumName, forumDescription, forumId);
	}
	
	public void deleteForum(String siteId, Integer forumId)
	{
		m_storage.deleteForum(siteId, forumId);
	}
	
	public int getForumCount(String forumName, String siteId)
	{
		int returnCount = m_storage.getForumCount(forumName, siteId);
		return returnCount;
	}
	
	public String getForumsPerSiteCounter(String siteId)
	{
		String totalForumsForSite = m_storage.getForumsPerSiteCounter(siteId);

		return totalForumsForSite;
	}
	
	public List getForumContent(Integer forumId)
	{
		List forum = m_storage.getForumContent(forumId);

		return forum;
	}
	
	public String getForumByName(String forumName, String siteId)
	{
		String returnForumName = m_storage.getForumByName(forumName, siteId);

		return returnForumName;
	}
	/////// Forum Details End ///////
	
	/////// Topic Details Start ///////
	public void insertTopic(ForumTopicDetails forumTopicDetails)
	{
		 m_storage.insertTopic(forumTopicDetails);
	}
	
	public void updateTopic(String topicName, Integer topicId)
	{
		m_storage.updateTopic(topicName, topicId);
	}
	
	public void deleteTopic(Integer topicId)
	{
		m_storage.deleteTopic(topicId);
	}
	
	public List getTopicContent(Integer topicId)
	{
		List topic = m_storage.getTopicContent(topicId);

		return topic;
	}
	
	public List getTopics(Integer forumId, String sortby, String sortorder)
	{
		
		List topics = m_storage.getTopics(forumId, sortby, sortorder);

		return topics;

	}
	
	public int getTopicCount(String topicName, Integer forumId)
	{
		int returnCount = m_storage.getTopicCount(topicName, forumId);
		return returnCount;
	}
	/////// Topic Details End ///////
	
	/////// Message Details Start ///////
	public void insertMessage(ForumMessage forumMessage)
	{
		 m_storage.insertMessage(forumMessage);
	}
	
	public void deleteMessage(Integer messageId)
	{
		m_storage.deleteMessage(messageId);
	}
	
	public List getMessageList(Integer topicId)
	{
		
		List topics = m_storage.getMessageList(topicId);

		return topics;

	}
	
	public ForumMessage getTopicPosting(Integer topicId)
	{
		ForumMessage forumMessage = new ForumMessage();
		forumMessage = m_storage.getTopicPosting(topicId);

		return forumMessage;
	}
	
	public ForumMessage getMessageDetail(Integer messageId)
	{
		ForumMessage forumMessage = new ForumMessage();
		forumMessage = m_storage.getMessageDetail(messageId);

		return forumMessage;
	}
	/////// Message Details End ///////
	
	public String getCurrentUserName() 
	{
		return m_userDirectoryService.getCurrentUser().getDisplayName();
	}

	public String getSakaiUserId(String eid)
	{
		eid = m_userDirectoryService.getCurrentUser().getEid().toLowerCase();
		
		String userId = m_storage.getSakaiUserId(eid);

		return userId;

	} 

	/**********************************************************************************************************************************************************************************************************************************************************
	 * Init and Destroy
	 *********************************************************************************************************************************************************************************************************************************************************/

	/**
	 * Final initialization, once all dependencies are set.
	 */
	public void init()
	{
		try
		{
        
			m_storage = newStorage();

			M_log.info("init()");
			
		


		}
		catch (Exception t)
		{
			M_log.warn("init(): ", t);
		}
	}

	/**
	 * Returns to uninitialized state.
	 */
	public void destroy()
	{
	
		M_log.info("destroy()");
	}

	
	
	/**********************************************************************************************************************************************************************************************************************************************************
	 * Storage
	 *********************************************************************************************************************************************************************************************************************************************************/

	protected interface Storage
	{
		List getForumList(String siteId, String sortby, String sortorder);
		void insertForum(Forum forum);
		void updateForum(String forumName, String forumDescription, Integer forumId);
		void deleteForum(String siteId, Integer forumId);
		List getForumContent(Integer forumId);
		String getForumByName(String forumName, String siteId);
		int getForumCount(String forumName, String siteId);
		String getForumsPerSiteCounter(String siteId);
		String getSakaiUserId(String eid);
		
		void insertTopic(ForumTopicDetails forumTopicDetails);
		void updateTopic(String topicName, Integer topicId);
		void deleteTopic(Integer topicId);
		List getTopicContent(Integer topicId);
		List getTopics(Integer forumId, String sortby, String sortorder);
		int getTopicCount(String topicName, Integer forumId);
		
		void insertMessage(ForumMessage forumMessage);
		void deleteMessage(Integer topicId);
		List getMessageList(Integer topicId);
		ForumMessage getTopicPosting(Integer topicId);
		ForumMessage getMessageDetail(Integer messageId);
		
	}
	

}
