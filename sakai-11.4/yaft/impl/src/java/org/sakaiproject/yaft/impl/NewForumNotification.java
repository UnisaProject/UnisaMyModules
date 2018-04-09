package org.sakaiproject.yaft.impl;

import java.util.ArrayList;
import java.util.List;

import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.entity.api.Reference;
import org.sakaiproject.entity.cover.EntityManager;
import org.sakaiproject.event.api.Event;
import org.sakaiproject.event.api.Notification;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.site.cover.SiteService;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserNotDefinedException;
import org.sakaiproject.user.cover.UserDirectoryService;
import org.sakaiproject.util.ResourceLoader;
import org.sakaiproject.util.SiteEmailNotification;
import org.sakaiproject.yaft.api.Forum;
import org.sakaiproject.yaft.api.Group;
import org.sakaiproject.yaft.api.SakaiProxy;
import org.sakaiproject.yaft.api.YaftFunctions;

public class NewForumNotification extends SiteEmailNotification{
	
	private static ResourceLoader rb = new ResourceLoader("org.sakaiproject.yaft.impl.bundle.newforumnotification");
	
	private SakaiProxy sakaiProxy = null;
	
	public NewForumNotification() {
	}
	
    public NewForumNotification(String siteId) {
        super(siteId);
    }
    
    public void setSakaiProxy(SakaiProxy sakaiProxy) {
    	this.sakaiProxy = sakaiProxy;
    }
    
    protected String getFromAddress(Event event)
    {
        String userEmail = "no-reply@" + ServerConfigurationService.getServerName();
        String userDisplay = ServerConfigurationService.getString("ui.service", "Sakai");
        String no_reply= "From: \"" + userDisplay + "\" <" + userEmail + ">";
        String from= getFrom(event);
        // get the message
        Reference ref = EntityManager.newReference(event.getResource());
        Forum msg = (Forum) ref.getEntity();
        String userId=msg.getCreatorId();

        //checks if "from" email id has to be included? and whether the notification is a delayed notification?. SAK-13512
        if ((ServerConfigurationService.getString("emailFromReplyable@org.sakaiproject.event.api.NotificationService").equals("true")) && from.equals(no_reply) && userId !=null){

                try
                {
                    User u = UserDirectoryService.getUser(userId);
                    userDisplay = u.getDisplayName();
                    userEmail = u.getEmail();
                    if ((userEmail != null) && (userEmail.trim().length()) == 0) userEmail = null;

                }
                catch (UserNotDefinedException e)
                {
                }

                // some fallback positions
                if (userEmail == null) userEmail = "no-reply@" + ServerConfigurationService.getServerName();
                if (userDisplay == null) userDisplay = ServerConfigurationService.getString("ui.service", "Sakai");
                from="From: \"" + userDisplay + "\" <" + userEmail + ">";
        }

        return from;
    }
	
	protected String plainTextContent(Event event) {
		Reference ref = EntityManager.newReference(event.getResource());
        Forum forum = (Forum) ref.getEntity();
        
		String creatorName = "";
		try {
			creatorName = UserDirectoryService.getUser(forum.getCreatorId()).getDisplayName();
		} catch (UserNotDefinedException e) {
			e.printStackTrace();
		}
		
		return rb.getFormattedMessage("noti.neworupdatedforum", new Object[]{creatorName,forum.getTitle(),ServerConfigurationService.getServerUrl() + forum.getUrl()});
	}
	
	protected String getSubject(Event event) {
		Reference ref = EntityManager.newReference(event.getResource());
        Forum forum = (Forum) ref.getEntity();
        
        String siteTitle = "";
		try {
			siteTitle = SiteService.getSite(forum.getSiteId()).getTitle();
		} catch (IdUnusedException e) {
			e.printStackTrace();
		}
        
        return rb.getFormattedMessage("noti.subject", new Object[]{siteTitle, forum.getTitle()});
	}
	
	protected List<User> getRecipients(Event event) {
		Reference ref = EntityManager.newReference(event.getResource());
        Forum forum = (Forum) ref.getEntity();
        
        List<User> users = new ArrayList<User>();
        
        List<Group> groups = forum.getGroups();
        if(groups.size() > 0) {
        	// This forum is limited to groups. Make sure the alert only goes
		    // to the group members
		    users = sakaiProxy.getGroupUsers(groups);
		    
		    // Maintainers need to get emails also.
		    users.addAll(sakaiProxy.getCurrentSiteMaintainers());
        }
        else {
        	users = super.getRecipients(event);
        }
        
	    return users;
	}
	
	protected String getTag(String title, boolean shouldUseHtml) {
		return rb.getFormattedMessage("noti.tag", new Object[]{ServerConfigurationService.getString("ui.service", "Sakai"), ServerConfigurationService.getPortalUrl(), title});
    }
	
	protected List getHeaders(Event event) {
        List rv = super.getHeaders(event);
        rv.add("Subject: " + getSubject(event));
        rv.add(getFromAddress(event));
        rv.add(getTo(event));
        return rv;
    }
}
