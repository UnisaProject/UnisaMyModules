/**********************************************************************************
 * $URL$
 * $Id$
 ***********************************************************************************
 *
 * Copyright (c) 2003, 2005, 2006, 2007, 2008 The Sakai Foundation
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

package org.sakaiproject.discussionforums.cover;

import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.user.api.User;
import org.sakaiproject.discussionforums.api.model.Forum;
import org.sakaiproject.discussionforums.api.model.ForumTopicDetails;
import org.sakaiproject.discussionforums.api.model.ForumMessage;
import java.util.List;

/**
 * <p>
 * DiscussionForumsService is a static Cover for the
 * {@link org.sakaiproject.discussionforums.api.DiscussionForumsService DiscussionForumsService}; see
 * that interface for usage details.
 * </p>
 */
public class DiscussionForumsService{

private static org.sakaiproject.discussionforums.api.DiscussionForumsService m_instance = null;


	/**
	 * Access the component instance: special cover only method.
	 * 
	 * @return the component instance.
	 */
	public static org.sakaiproject.discussionforums.api.DiscussionForumsService getInstance() {
		if (ComponentManager.CACHE_COMPONENTS) {
			if (m_instance == null)
				m_instance = (org.sakaiproject.discussionforums.api.DiscussionForumsService) ComponentManager
						.get(org.sakaiproject.discussionforums.api.DiscussionForumsService.class);
			return m_instance;
		} else {
			return (org.sakaiproject.discussionforums.api.DiscussionForumsService) ComponentManager
					.get(org.sakaiproject.discussionforums.api.DiscussionForumsService.class);
		}
	}
	
	/////// Forum Details Start ///////
	public static void insertForum(Forum forum) {
		org.sakaiproject.discussionforums.api.DiscussionForumsService service = getInstance();
		service.insertForum(forum);
	}
	
	public static void updateForum(String forumName, String forumDescription, Integer forumId) {
		org.sakaiproject.discussionforums.api.DiscussionForumsService service = getInstance();
		service.updateForum(forumName, forumDescription, forumId);
	}
	
	public static void deleteForum(String siteId, Integer forumId) {
		org.sakaiproject.discussionforums.api.DiscussionForumsService service = getInstance();
		service.deleteForum(siteId, forumId);
	}
	
	public static List getForumContent(Integer forumId) {
		org.sakaiproject.discussionforums.api.DiscussionForumsService service = getInstance();
		if (service == null)
			return null;

		return service.getForumContent(forumId);
	}
	
	public static List getForumList(String siteId, String sortby, String sortorder) {
		org.sakaiproject.discussionforums.api.DiscussionForumsService service = getInstance();
		if (service == null)
			return null;

		return service.getForumList(siteId, sortby, sortorder);
	}
	
	public static java.lang.Integer getForumCount(String forumName, String siteId) {
		org.sakaiproject.discussionforums.api.DiscussionForumsService service = getInstance();
		if (service == null)
			return null;

		return service.getForumCount(forumName, siteId);
	}
	
	public static java.lang.String getForumsPerSiteCounter(String siteId) {
		org.sakaiproject.discussionforums.api.DiscussionForumsService service = getInstance();
		if (service == null)
			return null;

		return service.getForumsPerSiteCounter(siteId);
	}
	
	public static java.lang.String getForumByName(String forumName, String siteId) {
		org.sakaiproject.discussionforums.api.DiscussionForumsService service = getInstance();
		if (service == null)
			return null;

		return service.getForumByName(forumName, siteId);
	}
	/////// Forum Details End ///////
	
	/////// Topic Details Start ///////
	public static void insertTopic(ForumTopicDetails forumTopicDetails) {
		org.sakaiproject.discussionforums.api.DiscussionForumsService service = getInstance();
		service.insertTopic(forumTopicDetails);
	}
	
	public static void updateTopic(String topicName, Integer topicId) {
		org.sakaiproject.discussionforums.api.DiscussionForumsService service = getInstance();
		service.updateTopic(topicName, topicId);
	}
	
	public static void deleteTopic(Integer topicId) {
		org.sakaiproject.discussionforums.api.DiscussionForumsService service = getInstance();
		service.deleteTopic(topicId);
	}
	
	public static List getTopicContent(Integer topicId) {
		org.sakaiproject.discussionforums.api.DiscussionForumsService service = getInstance();
		if (service == null)
			return null;

		return service.getTopicContent(topicId);
	}
	
	public static List getTopics(Integer forumId, String sortby, String sortorder) {
		org.sakaiproject.discussionforums.api.DiscussionForumsService service = getInstance();
		if (service == null)
			return null;

		return service.getTopics(forumId, sortby, sortorder);
	}
	
	public static java.lang.Integer getTopicCount(String topicName, Integer forumId) {
		org.sakaiproject.discussionforums.api.DiscussionForumsService service = getInstance();
		if (service == null)
			return null;

		return service.getTopicCount(topicName, forumId);
	}
	/////// Topic Details End ///////
	
	/////// Message Details Start ///////
	public static void insertMessage(ForumMessage forumMessage) {
		org.sakaiproject.discussionforums.api.DiscussionForumsService service = getInstance();
		service.insertMessage(forumMessage);
	}
	
	public static void deleteMessage(Integer messageId) {
		org.sakaiproject.discussionforums.api.DiscussionForumsService service = getInstance();
		service.deleteMessage(messageId);
	}
	
	public static List getMessageList(Integer topicId) {
		org.sakaiproject.discussionforums.api.DiscussionForumsService service = getInstance();
		if (service == null)
			return null;

		return service.getMessageList(topicId);
	}
	
	public static ForumMessage getTopicPosting(Integer topicId) {
		org.sakaiproject.discussionforums.api.DiscussionForumsService service = getInstance();
		if (service == null)
			return null;

		return service.getTopicPosting(topicId);
	}
	
	public static ForumMessage getMessageDetail(Integer messageId) {
		org.sakaiproject.discussionforums.api.DiscussionForumsService service = getInstance();
		if (service == null)
			return null;

		return service.getMessageDetail(messageId);
	}
	/////// Message Details End ///////
	
	public static java.lang.String getCurrentUserName() {
		org.sakaiproject.discussionforums.api.DiscussionForumsService service = getInstance();
		if (service == null)
			return null;

		return service.getCurrentUserName();
	}

	public static java.lang.String getSakaiUserId(String eid) {
		org.sakaiproject.discussionforums.api.DiscussionForumsService service = getInstance();
		if (service == null)
			return null;

		return service.getSakaiUserId(eid);
	}

}
