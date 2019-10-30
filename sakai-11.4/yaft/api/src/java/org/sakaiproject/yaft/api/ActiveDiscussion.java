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

import java.util.List;

public class ActiveDiscussion
{
	private int newMessages = 0;
	private String url = "";
	private String subject = "";
	private String latestMessageSubject = "";
	private long lastMessageDate = 0L;
	private List<Group> groups;
	private String siteId;
	
	public void setNewMessages(int newMessages)
	{
		this.newMessages = newMessages;
	}
	public int getNewMessages()
	{
		return newMessages;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	public String getUrl()
	{
		return url;
	}
	public void setSubject(String subject)
	{
		this.subject = subject;
	}
	public String getSubject()
	{
		return subject;
	}
	public void setLastMessageDate(long lastMessageDate)
	{
		this.lastMessageDate = lastMessageDate;
	}
	public long getLastMessageDate()
	{
		return lastMessageDate;
	}
	public void setLatestMessageSubject(String latestMessageSubject)
	{
		this.latestMessageSubject = latestMessageSubject;
	}
	public String getLatestMessageSubject()
	{
		return latestMessageSubject;
	}
	
	public List<Group> getGroups() {
		return groups;
	}
	
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	public String getSiteId() {
		return siteId;
	}
	
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
}
