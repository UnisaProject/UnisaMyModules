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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import org.sakaiproject.entity.api.Entity;
import org.sakaiproject.entity.api.ResourceProperties;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Discussion implements Entity
{
	/**
	 * The number of messages in this discussion
	 */
	private int messageCount = 0;
	
	private long lastMessageDate;

	/** Top level message */
	private Message firstMessage;
	
	private String forumId;
	
	private String status = "READY";
	
	// We need this to build direct urls in the rendered pages. Bogus, but necessary.
	private String pageId;
	
	private long start = -1L;
	private long end = -1L;
	
	private boolean lockedForWriting = false;
	private boolean lockedForReading = false;
	
	private boolean groupsInherited = false;
	
	private List<Group> groups = new ArrayList<Group>();
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	public List<Group> getGroups() {
		return groups;
	}
	
	private YaftGBAssignment assignment = null;

	
	public void setAssignment(YaftGBAssignment assignment) {
		this.assignment = assignment;
	}

	public YaftGBAssignment getAssignment() {
		return assignment;
	}
	
	public void setFirstMessage(Message firstMessage)
	{
		this.firstMessage = firstMessage;
	}

	public String getId()
	{
		return firstMessage.getId();
	}

	public void setMessageCount(int messageCount)
	{
		this.messageCount = messageCount;
	}
	
	public String getContent()
	{
		return firstMessage.getContent();
	}

	public int getMessageCount()
	{
		return messageCount;
	}

	public String getSubject()
	{
		return firstMessage.getSubject();
	}
	
	public void setSubject(String subject)
	{
		firstMessage.setSubject(subject);
	}

	public long getCreatedDate()
	{
		return firstMessage.getCreatedDate();
	}

	public String getCreatorId()
	{
		return firstMessage.getCreatorId();
	}

	public String getCreatorDisplayName()
	{
		return firstMessage.getCreatorDisplayName();
	}
	
	public Message getFirstMessage()
	{
		return firstMessage;
	}

	public void setForumId(String forumId)
	{
		this.forumId = forumId;
	}

	public String getForumId()
	{
		return forumId;
	}

	public void setLastMessageDate(long lastMessageDate)
	{
		this.lastMessageDate = lastMessageDate;
	}

	public long getLastMessageDate()
	{
		return lastMessageDate;
	}

	public void setPageId(String pageId)
	{
		this.pageId = pageId;
	}

	public String getPageId()
	{
		return pageId;
	}

	public String getUrl()
	{
		return "/portal/tool/" + getPlacementId() + "/discussions/" + getId() + ".html";
	}
	
	public String getFullUrl() {
		return "/portal/directtool/" + getPlacementId() + "/discussions/" + getId() + ".html";
	}

	public String getPlacementId()
	{
		return firstMessage.getPlacementId();
	}
	
	public Element toXml(Document doc,Stack stack)
	{
		Element discussionElement = doc.createElement(XmlDefs.DISCUSSION);

		if (stack.isEmpty())
		{
			doc.appendChild(discussionElement);
		}
		else
		{
			((Element) stack.peek()).appendChild(discussionElement);
		}

		//stack.push(discussionElement);

		discussionElement.setAttribute(XmlDefs.ID, getId());
		discussionElement.setAttribute(XmlDefs.CREATED_DATE, Long.toString(getCreatedDate()));
		discussionElement.setAttribute(XmlDefs.CREATOR_ID, getCreatorId());
		discussionElement.setAttribute(XmlDefs.SUBJECT, getSubject());
		discussionElement.setAttribute(XmlDefs.LAST_MESSAGE_DATE, Long.toString(lastMessageDate));
		discussionElement.setAttribute(XmlDefs.MESSAGE_COUNT, Integer.toString(messageCount));
		
		Element messagesElement = doc.createElement(XmlDefs.MESSAGES);
		discussionElement.appendChild(messagesElement);
		stack.push(messagesElement);
		
		firstMessage.toXml(doc,stack);
		
		stack.pop();
		
		return discussionElement;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStart(long start)
	{
		this.start = start;
	}

	public long getStart()
	{
		return start;
	}

	public void setEnd(long end)
	{
		this.end = end;
	}

	public long getEnd()
	{
		return end;
	}
	
	public boolean isCurrent()
	{
		if(start == -1 || end == -1)
			return false;
		else
		{
			long currentDate = new Date().getTime();

			if(start <= currentDate && currentDate <= end)
				return true;
			else
				return false;
		}
	}
	
	public void setLockedForWriting(boolean lockedForWriting)
	{
		this.lockedForWriting = lockedForWriting;
	}

	public boolean isLockedForWriting()
	{
		return lockedForWriting;
	}
	
	public boolean isLockedForWritingAndUnavailable()
	{
		return lockedForWriting && !isCurrent();
	}

	public void setLockedForReading(boolean lockedForReading)
	{
		this.lockedForReading = lockedForReading;
	}

	public boolean isLockedForReading()
	{
		return lockedForReading;
	}
	
	public boolean isLockedForReadingAndUnavailable()
	{
		return lockedForReading && !isCurrent();
	}

	public boolean isGraded() {
		return assignment != null;
	}
	
	public void setGroupsInherited(boolean b) {
		this.groupsInherited = b;
	}
	
	public boolean isGroupsInherited() {
		return this.groupsInherited;
	}
	public ResourceProperties getProperties() {
		return null;
	}
	public String getReference() {
		return YaftForumService.REFERENCE_ROOT + Entity.SEPARATOR + getSiteId() + Entity.SEPARATOR + "discussions" + Entity.SEPARATOR + getId();
	}
	public String getReference(String rootProperty) {
		return getReference();
	}
	public String getUrl(String rootProperty) {
		return getUrl();
	}
	public String getSiteId() {
		return firstMessage.getSiteId();
	}
}