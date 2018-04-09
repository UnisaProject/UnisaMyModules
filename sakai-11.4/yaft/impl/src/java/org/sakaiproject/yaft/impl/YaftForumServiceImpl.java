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
package org.sakaiproject.yaft.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.sakaiproject.authz.api.SecurityAdvisor;
import org.sakaiproject.entity.api.Entity;
import org.sakaiproject.entity.api.EntityTransferrer;
import org.sakaiproject.entity.api.HttpAccess;
import org.sakaiproject.entity.api.Reference;
import org.sakaiproject.entity.api.ResourceProperties;
import org.sakaiproject.yaft.api.ActiveDiscussion;
import org.sakaiproject.yaft.api.Group;
import org.sakaiproject.yaft.api.YaftSecurityException;
import org.sakaiproject.yaft.api.Discussion;
import org.sakaiproject.yaft.api.Forum;
import org.sakaiproject.yaft.api.ForumPopulatedStates;
import org.sakaiproject.yaft.api.Message;
import org.sakaiproject.yaft.api.Author;
import org.sakaiproject.yaft.api.SakaiProxy;
import org.sakaiproject.yaft.api.XmlDefs;
import org.sakaiproject.yaft.api.YaftForumService;
import org.sakaiproject.yaft.api.YaftFunctions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class YaftForumServiceImpl implements YaftForumService, EntityTransferrer, SecurityAdvisor
{
	private Logger logger = Logger.getLogger(YaftForumServiceImpl.class);

	private SakaiProxy sakaiProxy = null;

	private YaftPersistenceManager persistenceManager = null;

	private YaftSecurityManager securityManager;
	
	private boolean includeMessageBodyInEmail = false;

	public void init()
	{
		if (logger.isDebugEnabled())
			logger.debug("init()");

		sakaiProxy = new SakaiProxyImpl();
		
		securityManager = new YaftSecurityManager(sakaiProxy);

		logger.info("Registering Yaft functions ...");

		sakaiProxy.registerFunction(YaftFunctions.YAFT_MODIFY_PERMISSIONS);
		sakaiProxy.registerFunction(YaftFunctions.YAFT_FORUM_CREATE);
		sakaiProxy.registerFunction(YaftFunctions.YAFT_FORUM_DELETE_OWN);
		sakaiProxy.registerFunction(YaftFunctions.YAFT_FORUM_DELETE_ANY);
		sakaiProxy.registerFunction(YaftFunctions.YAFT_FORUM_VIEW_GROUPS);
		sakaiProxy.registerFunction(YaftFunctions.YAFT_DISCUSSION_CREATE);
		sakaiProxy.registerFunction(YaftFunctions.YAFT_DISCUSSION_DELETE_OWN);
		sakaiProxy.registerFunction(YaftFunctions.YAFT_DISCUSSION_DELETE_ANY);
		sakaiProxy.registerFunction(YaftFunctions.YAFT_MESSAGE_CREATE);
		sakaiProxy.registerFunction(YaftFunctions.YAFT_MESSAGE_CENSOR);
		sakaiProxy.registerFunction(YaftFunctions.YAFT_MESSAGE_DELETE_OWN);
		sakaiProxy.registerFunction(YaftFunctions.YAFT_MESSAGE_DELETE_ANY);
		//unisa change: added two more roles (permissions) to edit the message 
		sakaiProxy.registerFunction(YaftFunctions.YAFT_MESSAGE_UPDATE_OWN);
		sakaiProxy.registerFunction(YaftFunctions.YAFT_MESSAGE_UPADTE_ANY);
		sakaiProxy.registerFunction(YaftFunctions.YAFT_MESSAGE_READ);
		sakaiProxy.registerFunction(YaftFunctions.YAFT_VIEW_INVISIBLE);
		sakaiProxy.registerFunction(YaftFunctions.YAFT_SEND_ALERTS);

		persistenceManager = new YaftPersistenceManager();
		persistenceManager.setSakaiProxy(sakaiProxy);
		persistenceManager.setSecurityManager(securityManager);
		persistenceManager.init();
		persistenceManager.setupTables();

		sakaiProxy.registerEntityProducer(this);
		sakaiProxy.pushSecurityAdvisor(this);
		
		includeMessageBodyInEmail = sakaiProxy.getIncludeMessageBodyInEmailSetting();
	}

	public Forum getForum(String forumId, String state)
	{
		if (logger.isDebugEnabled())
			logger.debug("getForum()");

		return securityManager.filterForum(persistenceManager.getForum(forumId, state),null);
	}
	
	public Forum getUnfilteredForum(String forumId, String state)
	{
		if (logger.isDebugEnabled())
			logger.debug("getForum()");

		return persistenceManager.getForum(forumId, state);
	}

	public Discussion getDiscussion(String discussionId, boolean fully) {
		return securityManager.filterDiscussion(persistenceManager.getDiscussion(discussionId, fully),null);
	}
	
	public Discussion getUnfilteredDiscussion(String discussionId, boolean fully) {
		return persistenceManager.getDiscussion(discussionId, fully);
	}

	public List<Forum> getSiteForums(String siteId, boolean fully)
	{
		if (logger.isDebugEnabled())
			logger.debug("getSiteForums()");
		
		return securityManager.filterFora(persistenceManager.getFora(siteId, fully),siteId);
	}
	
	public String currentUserId(){
		return sakaiProxy.getUserIdFromSession();
	}
	
	public boolean addOrUpdateForum(Forum forum) {

        try {
		    return addOrUpdateForum(forum, sakaiProxy.getUserIdFromSession(), true);
        } catch (YaftSecurityException yse) {}

        return false;
	}
	public boolean addOrUpdateForum(Forum forum, String userId, boolean sendMail) throws YaftSecurityException {

        logger.debug("addOrUpdateForum()");

		// Every forum needs a title
		String title = forum.getTitle();
		if (title == null || title.length() <= 0) {
			return false;
        }

		boolean creating = (forum.getId().length() == 0);

        if (securityManager.canUserAddOrUpdateForumInCurrentSite(userId, forum)) {
            boolean succeeded = persistenceManager.addOrUpdateForum(forum);

            if (succeeded && creating) {
                // SiteStats/Search etc event
                //unisa-change: change the event name
                sakaiProxy.postEvent(YAFT_FORUM_CREATED, forum.getReference(),true);
                
                if (sendMail && sakaiProxy.canCurrentUserSendAlerts()) {
                    // NotificationService event
                    try {
                        sakaiProxy.postEvent(YAFT_FORUM_CREATED_SE, forum.getReference(),true);
                    } catch (Exception e) {
                        logger.error("Failed to post forum created event",e);
                    }
                }
            }

            return succeeded;
        } else {
            throw new YaftSecurityException("'" + userId + "' is not allowed to add or update forum '" + forum.getTitle() + "'");
        }
	}

	
	public boolean addOrUpdateForum(Forum forum,boolean sendMail){
		if (logger.isDebugEnabled())
			logger.debug("addOrUpdateForum()");
			// Every forum needs a title
			if (forum.getTitle() == null || forum.getTitle().length() <= 0)
				return false;

			boolean creating = (forum.getId().length() == 0);
			boolean succeeded = persistenceManager.addOrUpdateForum(forum);
			if (succeeded && creating) {
				// SiteStats/Search etc event
				// unisa-change: change the event name
				sakaiProxy.postEvent(YAFT_FORUM_CREATED, forum.getReference(),true);
				if (sendMail && sakaiProxy.canCurrentUserSendAlerts()) {
					// NotificationService event
					try {
						// unisa-change: change the event name
						sakaiProxy.postEvent(YAFT_FORUM_CREATED_SE,
								forum.getReference(), true);
					} catch (Exception e) {
						logger.error("Failed to post forum created event", e);
					}
				}
			}
			return succeeded;
	}

	public SakaiProxy getSakaiProxy()
	{
		return sakaiProxy;
	}

	public boolean addOrUpdateMessage(String siteId, String forumId, String userId, Message message, boolean sendMail) throws YaftSecurityException {

        logger.debug("addOrUpdateMessage()");    
        
        

       // if (securityManager.canUserAddOrUpdateMessageInCurrentSite(userId, message)) {
            if (!persistenceManager.addOrUpdateMessage(siteId, forumId, message,null)) {
                return false;
            }

            // SiteStats/Search etc event
               //unisa-change: change the event name
            sakaiProxy.postEvent(YAFT_MESSAGE_CREATED, message.getReference(),true);

            if (sendMail && "READY".equals(message.getStatus()) && sakaiProxy.canCurrentUserSendAlerts()) {
                // NotificationService event
                try {
                //unisa-change: change the event name
                    sakaiProxy.postEvent(YAFT_MESSAGE_CREATED_SE, message.getReference(),true);
                } catch (Exception e) {
                    logger.error("Failed to post message created event",e);
                }
            }

            return true;
        /*} else {
            throw new YaftSecurityException("'" + userId + "' is not allowed to add or update message '" + message.getSubject() + "'");
        }*/
	}
	
	public boolean addMessageRecursively(String siteId, String forumId, Message message,String parentId, String discussionId) {
		if (logger.isDebugEnabled()) {
			logger.debug("addOrUpdateMessage()");
		}
		
		message.setId("");
		message.setSiteId(siteId);
		message.setParent(parentId);
		message.setDiscussionId(discussionId);

		if (!persistenceManager.addOrUpdateMessage(siteId, forumId, message,null)) {
			return false;
		}

		// SiteStats/Search etc event
		//unisa-change: change the event name
		sakaiProxy.postEvent(YAFT_MESSAGE_CREATED, message.getReference(), true);
		
		for(Message child : message.getChildren()) {
			addMessageRecursively(siteId, forumId, child,message.getId(),discussionId);
		}

		return true;
	}

	public Discussion addDiscussion(String siteId, String forumId, String userId, Discussion discussion, boolean sendMail) throws YaftSecurityException {

        logger.debug("addDiscussion()");

		Message message = discussion.getFirstMessage();

		// Get this before calling addDiscussion as it will get set by it.
		String id = discussion.getId();

      //  if (securityManager.canUserAddDiscussionInCurrentSite(userId, discussion)) {
            if (persistenceManager.addDiscussion(siteId, forumId, discussion)) {
                if (id.length() == 0) {
                    // From the empty id we know this is a new discussion
                    
                    // SiteStats/Search etc event
                    //unisa-change: change the event name
                    sakaiProxy.postEvent(YAFT_DISCUSSION_CREATED, discussion.getReference(),true);
                }

                if (sendMail && sakaiProxy.canCurrentUserSendAlerts()) {
                    // NotificationService event
                    try {
                    //unisa-change: change the event name
                        sakaiProxy.postEvent(YAFT_DISCUSSION_CREATED_SE, discussion.getReference(),true);
                    } catch (Exception e) {
                        logger.error("Failed to post message created event",e);
                    }
                }
            }
        /*} else {
            throw new YaftSecurityException("'" + userId + "' is not allowed to add discussion '" + discussion.getSubject() + "'");
        }*/

		return discussion;
	}

	public List<Forum> getFora(boolean fully)
	{
		if (logger.isDebugEnabled())
			logger.debug("getFora()");

		return securityManager.filterFora(persistenceManager.getFora(fully),null);
	}

	/**
	 * Used by LessonBuilder
	 */
	public List<Discussion> getForumDiscussions(String forumId, boolean fully)
	{
		if (logger.isDebugEnabled())
			logger.debug("getForumDiscussions(" + forumId + ")");

		Forum forum = persistenceManager.getForum(forumId, (fully) ? ForumPopulatedStates.FULL : ForumPopulatedStates.PART);
		return securityManager.filterDiscussions(forum.getDiscussions());
	}

	public void deleteForum(String forumId, String siteId, String userId) throws YaftSecurityException {

        Forum forum = getForum(forumId, ForumPopulatedStates.EMPTY);

        if (securityManager.canUserDeleteForumInCurrentSite(userId, forum)) {
            if (persistenceManager.deleteForum(forumId)) {
                String reference = YaftForumService.REFERENCE_ROOT + "/" + siteId + "/forums/" + forumId;
                sakaiProxy.postEvent(YAFT_FORUM_DELETED, reference,true);
            } else {
			    logger.error("Failed to delete forum '" + forum.getTitle() + "'");
            }
        } else {
            throw new YaftSecurityException("'" + userId + "' is not allowed to delete forum '" + forum.getTitle() + "'");
        }
	}

	public boolean deleteDiscussion(String discussionId, String siteId, String userId) throws YaftSecurityException {

        Discussion discussion = getDiscussion(discussionId, false);

        if (securityManager.canUserDeleteDiscussionInCurrentSite(userId, discussion)) {

            try {
                if (persistenceManager.deleteDiscussion(discussionId)) {
                    sakaiProxy.removeCalendarEntry("Start of '" + discussion.getSubject() + "'", "Start of '" + discussion.getSubject() + "' Discussion (Click to launch)");
                    sakaiProxy.removeCalendarEntry("End of '" + discussion.getSubject() + "'", "End of '" + discussion.getSubject() + "' Discussion");

                    String reference = YaftForumService.REFERENCE_ROOT + "/" + siteId + "/discussions/" + discussionId;
                    //unisa change
                    sakaiProxy.postEvent(YAFT_DISCUSSION_DELETED, reference,true);

                    return true;
                }
            } catch (Exception e) {
                logger.error("Failed to delete discussion.", e);
            }

            return false;
        } else {
            throw new YaftSecurityException("'" + userId + "' is not allowed to delete discussion '" + discussion.getSubject() + "'");
        }
	}
	
	public void deleteMessage(Message message, String siteId, String forumId, String userId) throws YaftSecurityException {

        if (securityManager.canUserDeleteMessageInCurrentSite(userId, message)) {
            persistenceManager.deleteMessage(message, forumId);
            String reference = YaftForumService.REFERENCE_ROOT + "/" + siteId + "/messages/" + message.getId();
            //unisa change
            sakaiProxy.postEvent(YAFT_MESSAGE_DELETED, reference,true);
        } else {
            throw new YaftSecurityException("'" + userId + "' is not allowed to delete message '" + message.getSubject() + "'");
        }
	}
	
	public void undeleteMessage(Message message, String forumId)
	{
		persistenceManager.undeleteMessage(message, forumId);
	}

	public void showMessage(Message message)
	{
		persistenceManager.showMessage(message);
	}

	public void deleteAttachment(String attachmentId, String messageId)
	{
		persistenceManager.deleteAttachment(attachmentId, messageId);
	}

	public Message getMessage(String messageId)
	{
		return persistenceManager.getMessage(messageId);
	}

	public Forum getForumContainingMessage(String messageId)
	{
		return securityManager.filterForum(persistenceManager.getForumContainingMessage(messageId), null);
	}

	public boolean markMessageRead(String messageId, String forumId, String discussionId)
	{
		return persistenceManager.markMessageRead(messageId, forumId, discussionId);
	}

	public boolean markMessageUnRead(String messageId, String forumId, String discussionId)
	{
		return persistenceManager.markMessageUnRead(messageId, forumId, discussionId);
	}

	public boolean markDiscussionRead(String discussionId, String forumId)
	{
		return persistenceManager.markDiscussionRead(discussionId, forumId);
	}

	public List<String> getReadMessageIds(String discussionId)
	{
		return persistenceManager.getReadMessageIds(discussionId);
	}

	public void moveDiscussion(String discussionId, String currentForumId, String newForumId)
	{
		persistenceManager.moveDiscussion(discussionId, currentForumId, newForumId);
	}

	/** START EntityProducer IMPLEMENTATION */

	public String archive(String siteId, Document doc, Stack stack, String archivePath, List attachments)
	{
		if (logger.isDebugEnabled())
			logger.debug("archive(siteId:" + siteId + ",archivePath:" + archivePath + ")");

		StringBuilder results = new StringBuilder();

		results.append(getLabel() + ": Started.\n");

		int forumCount = 0;

		try
		{
			// start with an element with our very own (service) name
			Element element = doc.createElement(YaftForumService.class.getName());
			element.setAttribute("version", "2.5.x");
			((Element) stack.peek()).appendChild(element);
			stack.push(element);

			Element forums = doc.createElement("forums");
			element.appendChild(forums);
			stack.push(forums);
			List<Forum> fora = getSiteForums(siteId, true);
			if (fora != null && fora.size() > 0)
			{
				for (Forum forum : fora)
				{
					forum.toXml(doc, stack);
					forumCount++;
				}
			}

			stack.pop();

			results.append(getLabel() + ": Finished. " + forumCount + " forum(s) archived.\n");
		}
		catch (Exception any)
		{
			results.append(getLabel() + ": exception caught. Message: " + any.getMessage());
			logger.warn(getLabel() + " exception caught. Message: " + any.getMessage());
		}

		stack.pop();

		return results.toString();
	}

	public Entity getEntity(Reference reference)
	{
		String referenceString = reference.getReference();
		String[] parts = referenceString.split(Entity.SEPARATOR);

		if (!parts[1].equals("yaft"))
			return null;

		String type = parts[3];

		String entityId = parts[4];

		if ("forums".equals(type)) {
			return getForum(entityId,ForumPopulatedStates.EMPTY);
		} else if ("discussions".equals(type)) {
			return getDiscussion(entityId,false);
		} else if ("messages".equals(type)) {
			return getMessage(entityId);
		}

		return null;
	}

	public Collection getEntityAuthzGroups(Reference ref, String userId) {
		List ids = new ArrayList();
		ids.add("/site/" + ref.getContext());
		return ids;
	}

	public String getEntityDescription(Reference arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public ResourceProperties getEntityResourceProperties(Reference arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String getEntityUrl(Reference reference)
	{
		return null;
	}

	public HttpAccess getHttpAccess()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String getLabel()
	{
		return ENTITY_PREFIX;
	}

	public String merge(String siteId, Element root, String archivePath, String fromSiteId, Map attachmentNames, Map userIdTrans, Set userListAllowImport)
	{
		logger.debug("merge(siteId:" + siteId + ",root tagName:" + root.getTagName() + ",archivePath:" + archivePath + ",fromSiteId:" + fromSiteId);

		StringBuilder results = new StringBuilder();

		try
		{
			int forumCount = 0;

			NodeList forumNodes = root.getElementsByTagName(XmlDefs.FORUM);
			final int numberForums = forumNodes.getLength();

			for (int i = 0; i < numberForums; i++)
			{
				Node child = forumNodes.item(i);
				if (child.getNodeType() != Node.ELEMENT_NODE)
				{
					// Problem
					continue;
				}

				Element forumElement = (Element) child;

				Forum forum = new Forum();
				forum.fromXml(forumElement);
				forum.setSiteId(siteId);

				addOrUpdateForum(forum);

				NodeList discussionNodes = forumElement.getElementsByTagName(XmlDefs.DISCUSSION);

				for (int j = 0; j < discussionNodes.getLength(); j++)
				{
					Node discussionNode = discussionNodes.item(j);
					NodeList discussionChildNodes = discussionNode.getChildNodes();
					for (int k = 0; k < discussionChildNodes.getLength(); k++)
					{
						Node discussionChildNode = discussionChildNodes.item(k);
						if (discussionChildNode.getNodeType() == Node.ELEMENT_NODE && XmlDefs.MESSAGES.equals(((Element) discussionChildNode).getTagName()))
						{
							NodeList messageNodes = discussionChildNode.getChildNodes();
							mergeDescendantMessages(siteId, forum.getId(), null, null, messageNodes, results);
							break;
						}
					}
				}

				forumCount++;
			}

			results.append("Stored " + forumCount + " forums.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return results.toString();
	}

	private void mergeDescendantMessages(String siteId, String forumId, String discussionId, String parentId, NodeList messageNodes, StringBuilder results)
	{
		for (int i = 0; i < messageNodes.getLength(); i++)
		{
			Node node = messageNodes.item(i);

			if (node.getNodeType() != Node.ELEMENT_NODE)
				continue;

			Element messageElement = (Element) node;

			if (!"message".equals(messageElement.getTagName()))
				continue;

			Message message = new Message();
			message.fromXml(messageElement);
			message.setParent(parentId);
			message.setSiteId(siteId);

			if (parentId == null)
				discussionId = message.getId();

			message.setDiscussionId(discussionId);

			  try {
	                addOrUpdateMessage(siteId, forumId, sakaiProxy.getUserIdFromSession(), message, false);

	                NodeList repliesNodes = messageElement.getElementsByTagName(XmlDefs.REPLIES);
	                if (repliesNodes.getLength() >= 1) {
	                    Node repliesNode = repliesNodes.item(0);
	                    NodeList replyMessageNodes = repliesNode.getChildNodes();
	                    mergeDescendantMessages(siteId, forumId, discussionId, message.getId(), replyMessageNodes, results);
	                }
	            } catch (YaftSecurityException yse) {
	                logger.error(yse);
	            }
		}
	}

	public boolean parseEntityReference(String referenceString, Reference reference)
	{
		String[] parts = referenceString.split(Entity.SEPARATOR);
		
		if (parts.length < 2 || !parts[1].equals("yaft")) // Leading slash adds an empty element
			return false;
		
		if(parts.length == 2) {
			reference.set("sakai:yaft", "", "", null, "");
			return true;
		}
		
		String siteId = parts[2];
		String type = parts[3];
		String entityId = parts[4];
		
		if ("forums".equals(type)) {
			reference.set("yaft","forums" , entityId, null, siteId);
			return true;
		}
		else if ("discussions".equals(type)) {
			reference.set("yaft","discussions" , entityId, null, siteId);
			return true;
		}
		else if ("messages".equals(type)) {
			reference.set("yaft","messages" , entityId, null, siteId);
			return true;
		}

		return false;
	}

	public boolean willArchiveMerge()
	{
		return true;
	}

	/** END EntityProducer IMPLEMENTATION */
	
	public SecurityAdvice isAllowed(String userId, String function, String reference) {
		return null;
	}

	public Map<String, Integer> getReadMessageCountForAllFora()
	{
		return persistenceManager.getReadMessageCountForAllFora(sakaiProxy.getCurrentUser().getId());
	}

	public Map<String, Integer> getReadMessageCountForForum(String forumId)
	{
		return persistenceManager.getReadMessageCountForForum(sakaiProxy.getCurrentUser().getId(), forumId);
	}

	public Forum getForumForTitle(String title, String state, String siteId)
	{
		return securityManager.filterForum(persistenceManager.getForumForTitle(title, state, siteId),siteId);
	}

	public List<ActiveDiscussion> getActiveDiscussions()
	{
		return persistenceManager.getActiveDiscussions();
	}

	public String getIdOfSiteContainingMessage(String messageId)
	{
		return persistenceManager.getIdOfSiteContainingMessage(messageId);
	}
	
	public List<Author> getAuthorsForCurrentSite() {
		return persistenceManager.getAuthorsForCurrentSite();
	}

	public List<Message> getMessagesForAuthorInCurrentSite(String authorId) {
		return persistenceManager.getMessagesForAuthorInCurrentSite(authorId);
	}

	public List<Author> getAuthorsForDiscussion(String discussionId) {
		return persistenceManager.getAuthorsForDiscussion(discussionId);
	}

	public List<Message> getMessagesForAuthorInDiscussion(String authorId, String discussionId) {
		return persistenceManager.getMessagesForAuthorInDiscussion(authorId,discussionId);
	}

	public boolean setDiscussionGroups(String discussionId, Collection<String> groups) {
		return persistenceManager.setDiscussionGroups(discussionId,groups);
	}

	public boolean clearDiscussion(String discussionId) {
		return persistenceManager.clearDiscussion(discussionId);
	}
	
	private List<Forum> getForaForSite(String siteId) {
		return persistenceManager.getForaForSite(siteId);
	}

	public String[] myToolIds() {
		String[] toolIds = { "sakai.yaft" };
		return toolIds;
	}
	
	
	
	
	//this method is for site import with siteID as parameter 
	
	public List<Discussion> getForumDiscussions(String forumId,String siteId, boolean fully)
	{
		//
		String siteImport="";
		if (logger.isDebugEnabled())
			logger.debug("getForumDiscussions(" + forumId + ")");

		//Forum forum = persistenceManager.getForum(forumId, (fully) ? ForumPopulatedStates.FULL : ForumPopulatedStates.PART);
		Forum forum = persistenceManager.getForum(forumId,siteId,siteImport, (fully) ? ForumPopulatedStates.FULL : ForumPopulatedStates.PART);
		
		if("IndexManager".equals(Thread.currentThread().getName())) {
			return forum.getDiscussions();
		}
		
		//filter discussions
		List<Discussion> filtered = new ArrayList<Discussion>();
		for(Discussion discussion : forum.getDiscussions()){			
			
			if(filterDiscussion(discussion, siteId) == null)
				continue;
			
			filtered.add(discussion);
		}
		return filtered;
		
	}
	
	public Discussion filterDiscussion(Discussion discussion,String siteId)
	{
		if("IndexManager".equals(Thread.currentThread().getName())) {
			return discussion;
		}
		
		if(discussion == null) return null;
		
		if(siteId == null) return null;
		
		// Is the current user a member of the site?
	/*	if(!sakaiProxy.isCurrentUserSuperUser() && !sakaiProxy.isCurrentUserMemberOfSite(siteId)) {
			return null;
		}*/
		
		List<Group> groups = discussion.getGroups();
			
		if(groups.size() > 0
				&& !sakaiProxy.isCurrentUserMemberOfAnyOfTheseGroups(groups)
				&& !sakaiProxy.currentUserHasFunction(YaftFunctions.YAFT_FORUM_VIEW_GROUPS))
		{
			return null;
		}
		
		return discussion;
	}
	
	public void transferCopyEntities(String fromContext, String toContext, List ids) {
		List<Forum> fora = getForaForSite(fromContext);
		for(Forum forum : fora) {
			// Get the discussions before we null the forum id !
			List<Discussion> discussions = getForumDiscussions(forum.getId(),fromContext, true);
			forum.setId(""); // This'll force a new GUID for the copy
			forum.setSiteId(toContext);
			//addOrUpdateForum(forum);
			persistenceManager.addOrUpdateForum(forum);
			for(Discussion discussion : discussions) {
				discussion.setForumId(forum.getId());
				discussion.getFirstMessage().setId(""); // Again, this'll force a new GUID id
				
				 try {
                    addDiscussion(toContext, forum.getId(), sakaiProxy.getUserIdFromSession(), discussion, false);
                    Message firstMessage = discussion.getFirstMessage();
                    
                    for (Message child : firstMessage.getChildren()) {
                        addMessageRecursively(toContext, forum.getId(), child, firstMessage.getId(),discussion.getId());
                    }
                } catch (Exception yse) {
                    logger.error(yse);
                }		
			}
		}
	}
	
	public void transferCopyEntities(String fromContext, String toContext, List ids, boolean cleanup) {
		
		if(cleanup) {
			// Deep delete the target context's forum data.
			List<Forum> fora = getForaForSite(toContext);
			for(Forum forum : fora) {
				  try {
					   // deleteForum(forum.getId(), toContext, sakaiProxy.getUserIdFromSession());
					  //forum = getForum(forum.getId(), ForumPopulatedStates.EMPTY);
					   forum= filterForum(persistenceManager.getForum(forum.getId(), ForumPopulatedStates.EMPTY),toContext);
					     
					     if (persistenceManager.deleteForum(forum.getId())) {
					    	 
					     }else{
					    	 logger.error("Site import Failed to delete forum '" + forum.getTitle() + "'");
					     }    
					    
		        } catch (Exception yse) {
		            logger.error(yse);
		        }
		        }
		}
		
		transferCopyEntities(fromContext, toContext, ids);
	}
	
	
	
	public Forum filterForum(Forum forum,String siteId)
	{
		if("IndexManager".equals(Thread.currentThread().getName())) {
			return forum;
		}
		
		if(forum == null) return null;
		
		if(siteId == null) return null;
		
		if(!forum.getSiteId().equals(siteId))
			return null;
		
		// Is the current user a member of the site?
	
		List<Group> groups = forum.getGroups();
			
		if(groups.size() > 0
				&& !sakaiProxy.isCurrentUserMemberOfAnyOfTheseGroups(groups)
				&& !sakaiProxy.currentUserHasFunction(YaftFunctions.YAFT_FORUM_VIEW_GROUPS))
		{
			return null;
		}
		
		return forum;
	}
	
}