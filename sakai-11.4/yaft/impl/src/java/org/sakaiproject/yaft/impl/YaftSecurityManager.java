package org.sakaiproject.yaft.impl;

import java.util.ArrayList;
import java.util.List;

import org.sakaiproject.yaft.api.ActiveDiscussion;
import org.sakaiproject.yaft.api.Discussion;
import org.sakaiproject.yaft.api.Group;
import org.sakaiproject.yaft.api.SakaiProxy;
import org.sakaiproject.yaft.api.Forum;
import org.sakaiproject.yaft.api.YaftFunctions;
import org.sakaiproject.yaft.api.Message;

public class YaftSecurityManager
{
	private SakaiProxy sakaiProxy;
	
	public YaftSecurityManager(SakaiProxy sakaiProxy)
	{
		this.sakaiProxy = sakaiProxy;
	}
	
	public List<Forum> filterFora(List<Forum> fora,String siteId)
	{
		if("IndexManager".equals(Thread.currentThread().getName())) {
			return fora;
		}
		
		if(siteId == null || siteId.length() == 0) {
			siteId = sakaiProxy.getCurrentSiteId();
		}
		
		List<Forum> filtered = new ArrayList<Forum>();
		
		for(Forum forum : fora)
		{
			if(filterForum(forum, siteId) == null)
				continue;
			
			filtered.add(forum);
		}
		
		return filtered;
	}
	//this methos is for site  import to pass the siteId
	public List<Discussion> filterDiscussions(List<Discussion> discussions,String siteId)
	{
		if("IndexManager".equals(Thread.currentThread().getName())) {
			return discussions;
		}
		
		//String siteId = sakaiProxy.getCurrentSiteId();
		
		List<Discussion> filtered = new ArrayList<Discussion>();
		
		for(Discussion discussion : discussions)
		{
			if(filterDiscussion(discussion, siteId) == null)
				continue;
			
			filtered.add(discussion);
		}
		
		return filtered;
	}
	
	public List<Discussion> filterDiscussions(List<Discussion> discussions)
	{
		if("IndexManager".equals(Thread.currentThread().getName())) {
			return discussions;
		}
		
		String siteId = sakaiProxy.getCurrentSiteId();
		
		List<Discussion> filtered = new ArrayList<Discussion>();
		
		for(Discussion discussion : discussions)
		{
			if(filterDiscussion(discussion, siteId) == null)
				continue;
			
			filtered.add(discussion);
		}
		
		return filtered;
	}
	
	

	public Forum filterForum(Forum forum,String siteId)
	{
		if("IndexManager".equals(Thread.currentThread().getName())) {
			return forum;
		}
		
		if(forum == null) return null;
		
		if(siteId == null) siteId = sakaiProxy.getCurrentSiteId();
		
		if(!forum.getSiteId().equals(siteId))
			return null;
		
		// Is the current user a member of the site?
		if(!sakaiProxy.isCurrentUserSuperUser() && !sakaiProxy.isCurrentUserMemberOfSite(siteId)) {
			return null;
		}
		
		List<Group> groups = forum.getGroups();
			
		if(groups.size() > 0
				&& !sakaiProxy.isCurrentUserMemberOfAnyOfTheseGroups(groups)
				&& !sakaiProxy.currentUserHasFunction(YaftFunctions.YAFT_FORUM_VIEW_GROUPS))
		{
			return null;
		}
		
		return forum;
	}
	
	public Discussion filterDiscussion(Discussion discussion,String siteId)
	{
		if("IndexManager".equals(Thread.currentThread().getName())) {
			return discussion;
		}
		
		if(discussion == null) return null;
		
		if(siteId == null) siteId = sakaiProxy.getCurrentSiteId();
		
		// Is the current user a member of the site?
		if(!sakaiProxy.isCurrentUserSuperUser() && !sakaiProxy.isCurrentUserMemberOfSite(siteId)) {
			return null;
		}
		
		List<Group> groups = discussion.getGroups();
			
		if(groups.size() > 0
				&& !sakaiProxy.isCurrentUserMemberOfAnyOfTheseGroups(groups)
				&& !sakaiProxy.currentUserHasFunction(YaftFunctions.YAFT_FORUM_VIEW_GROUPS))
		{
			return null;
		}
		
		return discussion;
	}

	public List<ActiveDiscussion> filterActiveDiscussions(List<ActiveDiscussion> discussions) {
		
		List<ActiveDiscussion> filtered = new ArrayList<ActiveDiscussion>();
		
		for(ActiveDiscussion discussion : discussions)
		{
			if(filterActiveDiscussion(discussion) == null)
				continue;
			
			filtered.add(discussion);
		}
		
		return filtered;
	}
	
	public ActiveDiscussion filterActiveDiscussion(ActiveDiscussion discussion)
	{
		if(discussion == null) return null;
		
		// Is the current user a member of the site?
		if(!sakaiProxy.isCurrentUserSuperUser() && !sakaiProxy.isCurrentUserMemberOfSite(discussion.getSiteId())) {
			return null;
		}
		
		List<Group> groups = discussion.getGroups();
			
		if(groups.size() > 0
				&& !sakaiProxy.isCurrentUserMemberOfAnyOfTheseGroups(groups)
				&& !sakaiProxy.currentUserHasFunction(YaftFunctions.YAFT_FORUM_VIEW_GROUPS))
		{
			return null;
		}
		
		return discussion;
	}
	  public boolean canUserAddOrUpdateForumInCurrentSite(String userId, Forum forum) {

			boolean isNew = (forum.getId().length() == 0);

	        if (isNew) {
	            if (sakaiProxy.userHasFunctionInCurrentSite(userId, YaftFunctions.YAFT_FORUM_CREATE)) {
	                return true;
	            }
	        }else if(forum.getCreatorId().equals(userId)){
	        	 return true;
	        }
	        /* else if (sakaiProxy.userHasFunctionInCurrentSite(userId, YaftFunctions.YAFT_FORUM_DELETE_ANY)) {
	            return true;
	        } else if (sakaiProxy.userHasFunctionInCurrentSite(userId, YaftFunctions.YAFT_FORUM_DELETE_OWN) &&  forum.getCreatorId().equals(userId)) {
	            return true;
	        }*/

	        return false;
	    }
       //this security check we are not calling because all the users allowed to add discussion topics
	    public boolean canUserAddDiscussionInCurrentSite(String userId, Discussion discussion) {

			boolean isNew = (discussion.getId().length() == 0);

	        if (isNew) {
	            if (sakaiProxy.userHasFunctionInCurrentSite(userId, YaftFunctions.YAFT_DISCUSSION_CREATE)) {
	                return true;
	            }
	        } else if (sakaiProxy.userHasFunctionInCurrentSite(userId, YaftFunctions.YAFT_DISCUSSION_DELETE_ANY)) {
	            return true;
	        } else if (sakaiProxy.userHasFunctionInCurrentSite(userId, YaftFunctions.YAFT_DISCUSSION_DELETE_OWN) &&  discussion.getCreatorId().equals(userId)) {
	            return true;
	        }
	        return false;
	    }
	      //this security check we are not calling because all the users allowed to add discussion topics
	    public boolean canUserAddOrUpdateMessageInCurrentSite(String userId, Message message) {

			boolean isNew = (message.getId().length() == 0);

	        if (isNew) {
	           if (sakaiProxy.userHasFunctionInCurrentSite(userId, YaftFunctions.YAFT_MESSAGE_CREATE)) {
	                return true;
	            }
	        } else if (sakaiProxy.userHasFunctionInCurrentSite(userId, YaftFunctions.YAFT_MESSAGE_DELETE_ANY)) {
	            return true;
	        } else if (sakaiProxy.userHasFunctionInCurrentSite(userId, YaftFunctions.YAFT_MESSAGE_DELETE_OWN) &&  message.getCreatorId().equals(userId)) {
	            return true;
	        }

	        return false;
	    }

	    public boolean canUserDeleteForumInCurrentSite(String userId, Forum forum) {

		    if (sakaiProxy.userHasFunctionInCurrentSite(userId, YaftFunctions.YAFT_FORUM_DELETE_ANY)) {
	            return true;
	        } else if (sakaiProxy.userHasFunctionInCurrentSite(userId, YaftFunctions.YAFT_FORUM_DELETE_OWN) &&  forum.getCreatorId().equals(userId)) {
	            return true;
	        }

	        return false;
	    }

	    public boolean canUserDeleteDiscussionInCurrentSite(String userId, Discussion discussion) {

		    if (sakaiProxy.userHasFunctionInCurrentSite(userId, YaftFunctions.YAFT_DISCUSSION_DELETE_ANY)) {
	            return true;
	        } else if (sakaiProxy.userHasFunctionInCurrentSite(userId, YaftFunctions.YAFT_DISCUSSION_DELETE_OWN) &&  discussion.getCreatorId().equals(userId)) {
	            return true;
	        }

	        return false;
	    }

	    public boolean canUserDeleteMessageInCurrentSite(String userId, Message message) {
	    	
	    	//if the user is student, check the message is created by student or not 

		    if (sakaiProxy.userHasFunctionInCurrentSite(userId, YaftFunctions.YAFT_MESSAGE_DELETE_ANY)) {
	            return true;
	        } else if (sakaiProxy.userHasFunctionInCurrentSite(userId, YaftFunctions.YAFT_MESSAGE_DELETE_OWN) &&  message.getCreatorId().equals(userId)) {
	            return true;
	        }

	        return false;
	    }
}
