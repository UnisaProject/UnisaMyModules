/**********************************************************************************
 * $URL$
 * $Id$
 ***********************************************************************************
 *
 * Copyright (c) 2003, 2004, 2005, 2006, 2007, 2008 The Sakai Foundation
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

package org.sakaiproject.discussionforums.api;

import java.util.List;
import org.sakaiproject.discussionforums.api.model.Forum;
import org.sakaiproject.discussionforums.api.model.ForumTopicDetails;

public interface DiscussionForumsService
{
	/** This string starts the references to resources in this service. */
	public static final String REFERENCE_ROOT = "/discussionforums";

	public void insertForum(Forum forum);
	public void updateForum(String forumName, String forumDescription, Integer forumId);
	public void deleteForum(String siteId, Integer forumId);
	public List getForumContent(Integer forumId);
	public String getForumByName(String forumName, String siteId);
	public List getForumList(String siteId, String sortby, String sortorder);
	public int getForumCount(String forumName, String siteId);
	public String getForumsPerSiteCounter(String siteId);
	
	public void insertTopic(ForumTopicDetails forumTopicDetails);
	public void updateTopic(String topicName, Integer topicId);
	public void deleteTopic(Integer topicId);
	public List getTopicContent(Integer topicId);
	public List getTopics(Integer forumId, String sortby, String sortorder);
	
	String getCurrentUserName();
	
	String getSakaiUserId(String eid);
}
