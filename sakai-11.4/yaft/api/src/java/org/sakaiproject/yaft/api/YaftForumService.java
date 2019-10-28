/**
 * Copyright 2009 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sakaiproject.yaft.api;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.sakaiproject.entity.api.Entity;
import org.sakaiproject.entity.api.EntityProducer;

public interface YaftForumService extends EntityProducer
{
	
	
	public static final String ENTITY_PREFIX = "yaft";
	public static final String REFERENCE_ROOT = Entity.SEPARATOR + ENTITY_PREFIX;
	
	// Events. The _SS ones are for SiteStats,Search etc. The others are for notifications
	// and are conditional on the send email box being checked
	//Unisa-change: Changed the event names took out _ss and added .se for email sent
	public static final String YAFT_MESSAGE_CREATED_SE = "yaft.message.created.se";
	public static final String YAFT_MESSAGE_CREATED = "yaft.message.created";
	public static final String YAFT_MESSAGE_DELETED = "yaft.message.deleted";
	public static final String YAFT_FORUM_CREATED_SE = "yaft.forum.created.se";
	public static final String YAFT_FORUM_CREATED = "yaft.forum.created";
	public static final String YAFT_FORUM_DELETED = "yaft.forum.deleted";
	public static final String YAFT_DISCUSSION_CREATED_SE = "yaft.discussion.created.se";
	public static final String YAFT_DISCUSSION_CREATED = "yaft.discussion.created";
	public static final String YAFT_DISCUSSION_DELETED = "yaft.discussion.deleted";

	/**
	 * Get the forums for the specified site
	 * 
	 * @param siteId The site we want the associated forums for
	 * @return A list of Forum objects
	 */
	public List<Forum> getSiteForums(String siteId,boolean fully);
	
	/**
	 * Get the discussions for the specified forum.
	 * 
	 * @param forumId The forum we want the associated discussions for
	 * @return The Discussions in the specified forum
	 */
	public Forum getForum(String forumId,String state);
	
	public Forum getUnfilteredForum(String forumId, String state);
	
	public Forum getForumForTitle(String title,String state,String siteId);
	
	public Discussion getDiscussion(String discussionId,boolean fully);
	
	public Discussion getUnfilteredDiscussion(String discussionId, boolean fully);
	
	public SakaiProxy getSakaiProxy();

	public boolean addOrUpdateForum(Forum forum);
	public boolean addOrUpdateForum(Forum forum, String userId, boolean sendMail) throws YaftSecurityException;
	
	public Discussion addDiscussion(String siteId, String forumId, String userId, Discussion discussion, boolean sendMail) throws YaftSecurityException;

	public boolean addOrUpdateMessage(String siteId, String forumId, String userId, Message message,boolean sendMail) throws YaftSecurityException;

	public List<Forum> getFora(boolean fully);

	//public List<Discussion> getDiscussions();

	public List<Discussion> getForumDiscussions(String id,boolean fully);
	public void deleteForum(String forumId, String siteId, String userId) throws YaftSecurityException;

	public boolean deleteDiscussion(String discussionId, String siteId, String userId) throws YaftSecurityException;
	
	public void deleteMessage(Message message, String siteId, String forumId, String userId) throws YaftSecurityException;
	
	public void undeleteMessage(Message messageId,String forumId);

	public void showMessage(Message messageId);

	public void deleteAttachment(String attachmentId, String messageId);

	public Message getMessage(String messageId);

	public Forum getForumContainingMessage(String messageId);

	public boolean markMessageRead(String messageId,String forumId,String discussionId);

	public boolean markMessageUnRead(String messageId,String forumId,String discussionId);
	
	public boolean markDiscussionRead(String discussionId,String forumId);

	public List<String> getReadMessageIds(String discussionId);

	public void moveDiscussion(String discussionId, String currentForumId,String newForumId);

	public Map<String,Integer> getReadMessageCountForAllFora();

	public Map<String, Integer> getReadMessageCountForForum(String forumId);

	public List<ActiveDiscussion> getActiveDiscussions();

	public String getIdOfSiteContainingMessage(String messageId);
	
	public List<Author> getAuthorsForCurrentSite();

	public List<Message> getMessagesForAuthorInCurrentSite(String authorId);

	public List<Author> getAuthorsForDiscussion(String discussionId);

	public List<Message> getMessagesForAuthorInDiscussion(String authorId,
			String discussionId);
	
	public boolean setDiscussionGroups(String discussionId,Collection<String> groups);

	public boolean clearDiscussion(String discussionId);
}
