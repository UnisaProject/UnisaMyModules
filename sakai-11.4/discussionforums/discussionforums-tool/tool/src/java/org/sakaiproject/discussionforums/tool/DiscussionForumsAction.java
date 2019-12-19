/**********************************************************************************
 * $URL$
 * $Id$
 ***********************************************************************************
 *
 * Copyright (c) 2003, 2004, 2005, 2006, 2008 The Sakai Foundation
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

package org.sakaiproject.discussionforums.tool;

import java.sql.Timestamp;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.logging.LogFactory;
import org.sakaiproject.authz.cover.SecurityService;
import org.sakaiproject.cheftool.Context;
import org.sakaiproject.cheftool.JetspeedRunData;
import org.sakaiproject.cheftool.RunData;
import org.sakaiproject.cheftool.VelocityPortlet;
import org.sakaiproject.cheftool.VelocityPortletPaneledAction;
import org.sakaiproject.cheftool.api.Menu;
import org.sakaiproject.cheftool.menu.MenuDivider;
import org.sakaiproject.cheftool.menu.MenuEntry;
import org.sakaiproject.cheftool.menu.MenuImpl;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.SessionState;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.cover.UsageSessionService;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.discussionforums.cover.DiscussionForumsService;
import org.sakaiproject.discussionforums.api.model.Forum;
import org.sakaiproject.discussionforums.api.model.ForumTopicDetails;
import org.sakaiproject.discussionforums.api.model.ForumDetailsForm;
import org.sakaiproject.discussionforums.api.model.ForumTopicsForm;
import org.sakaiproject.site.cover.SiteService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserNotDefinedException;
import org.sakaiproject.user.cover.UserDirectoryService;
import org.sakaiproject.util.ResourceLoader;
import org.sakaiproject.tool.cover.SessionManager;
import org.sakaiproject.tool.cover.ToolManager;

/**
 * <p>
 * DiscussionForumsToolAction is the discussionforums display tool
 * </p>
 */
public class DiscussionForumsAction extends VelocityPortletPaneledAction {
	private static final long serialVersionUID = 1L;
	private static Logger M_log = LoggerFactory.getLogger(DiscussionForumsAction.class);

	/** Resource bundle using current language locale */
	private static ResourceLoader rb = new ResourceLoader("discussionforums");
	protected static final String STATE_DISPLAY_MODE = "display_mode";
	private EventTrackingService eventTrackingService = null;
	
	ForumDetailsForm forumDetailsForm = new ForumDetailsForm();
	ForumTopicsForm forumTopicForm = new ForumTopicsForm();

	/**
	 * Populate the state object, if needed.
	 */
	/*protected void initState(SessionState state, VelocityPortlet portlet, JetspeedRunData rundata) {

	}*/

	/**
	 * @param portlet
	 * @param context
	 * @param rundata
	 * @param state
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String buildMainPanelContext(VelocityPortlet portlet, Context context, RunData rundata, SessionState state) 
	{
		String template = null;
		context.put("tlang", rb);
		
		String siteId ="";
		String totalPages = "";
		String siteReference = "";
		int pages = 0;
		Integer pageNo;
		boolean generalForumCreated = false;
		String siteTitle="";
		int generalForum = -1;
		
		context.put(Menu.CONTEXT_ACTION, state.getAttribute(STATE_ACTION));
		String userID = UserDirectoryService.getCurrentUser().getEid().toLowerCase();
		
		siteId = ToolManager.getCurrentPlacement().getContext();
		
		try {
			siteReference = SiteService.getSite(siteId).getReference();
		} catch (IdUnusedException e) {
			context.put("nopermission", rb.getString("discussionforums.permissionerror"));
			return "_noaccess";
		}
		
		context.put("addForum", SecurityService.unlock("discuss.addforum", siteReference));
		context.put("updateAnyForum", SecurityService.unlock("discuss.updateforum.any", siteReference));
		context.put("updateOwnForum", SecurityService.unlock("discuss.updateforum.own", siteReference));
		
		forumDetailsForm.setAddForum(SecurityService.unlock("discuss.addforum", siteReference));
		forumDetailsForm.setUpdateAnyForum(SecurityService.unlock("discuss.updateforum.any", siteReference));
		forumDetailsForm.setUpdateOwnForum(SecurityService.unlock("discuss.updateforum.own", siteReference));
		
        try {
            siteTitle = SiteService.getSite(siteId).getTitle().toString();
            context.put("siteTitle", siteTitle);
            forumDetailsForm.setTitle(siteTitle);
        } catch(IdUnusedException e){
        
        }
        
		state.setAttribute("userID", userID);
		state.setAttribute("siteId", siteId);
        forumDetailsForm.getForum().setSiteId(siteId);
		forumDetailsForm.getForum().setUserId(userID);
		
		String strPageNo = rundata.getParameters().getString("pageNo");
		
		if (strPageNo == null){
			pageNo = new Integer("1");
		} else {
			pageNo = new Integer(strPageNo);
		}
		
		forumDetailsForm.setPageNo(pageNo);
		
		if (forumDetailsForm.getSortBy() == null && forumDetailsForm.getSortOrder() == null){
			forumDetailsForm.setSortBy("Creation_Date");
			forumDetailsForm.setSortOrder("Asc");
		}
		
		if (forumDetailsForm.getSortIcon() == null){
			forumDetailsForm.setSortIcon("0");
		} else if (forumDetailsForm.getSortIcon().equalsIgnoreCase("1")){
			forumDetailsForm.setSortOrder("Asc");
		} else if (forumDetailsForm.getSortIcon().equalsIgnoreCase("2")){
			forumDetailsForm.setSortOrder("Desc");
		} else if (forumDetailsForm.getSortIcon().equalsIgnoreCase("3")){
			forumDetailsForm.setSortOrder("Asc");
		} else if (forumDetailsForm.getSortIcon().equalsIgnoreCase("4")){
			forumDetailsForm.setSortOrder("Desc");
		}else if (forumDetailsForm.getSortIcon().equalsIgnoreCase("5")){
			forumDetailsForm.setSortOrder("Asc");
		} else if (forumDetailsForm.getSortIcon().equalsIgnoreCase("6")){
			forumDetailsForm.setSortOrder("Desc");
		}
        
		List siteForums = DiscussionForumsService.getForumList(forumDetailsForm.getForum().getSiteId(), forumDetailsForm.getSortBy(), forumDetailsForm.getSortOrder());
		Iterator i = siteForums.iterator();
		if(siteForums.isEmpty()) {
			Forum forum = new Forum();
			forum.setForumName("General Subject Related Discussions");
			forum.setForumDescription("A forum on general discussion about the subject content");
			forum.setSiteId(siteId);
			forum.setUserId("admin");
					
			generalForum = DiscussionForumsService.getForumCount(forum.getForumName(),siteId);
			
			System.out.println("The inside general forum count value:>>>> " + generalForum);
						
			if(generalForum == 0)
			{
				DiscussionForumsService.insertForum(forum);
				generalForumCreated = true;
				//forums = forumDao.getForumList(forumDetailsForm.getForum().getSiteId(),forumDetailsForm.getSortBy(),forumDetailsForm.getSortOrder());
				//if(forums.isEmpty()) {
				//	logger.debug("Forum List is STILL empty after trying to insert default forum");
				//}
			}
		} else {
			while(i.hasNext()){
				Forum fm = (Forum) i.next();
				if (fm.getForumName().equalsIgnoreCase("General Subject Related Discussions")){
					generalForumCreated = true;
				}
			}
			
			Forum forum = new Forum();
			if (!generalForumCreated){
				forum.setForumName("General Subject Related Discussions");
				forum.setForumDescription("A forum on general discussion about the subject content");
				forum.setSiteId(siteId);
				forum.setUserId("admin");
				
				generalForum = DiscussionForumsService.getForumCount(forum.getForumName(),siteId);
				
				if(generalForum == 0){
					DiscussionForumsService.insertForum(forum);
					generalForumCreated = true;
				}
				
				//forums = forumDao.getForumList(forumDetailsForm.getForum().getSiteId(),forumDetailsForm.getSortBy(),forumDetailsForm.getSortOrder());
			}
		}
		
		try {
			totalPages = DiscussionForumsService.getForumsPerSiteCounter(siteId);
		} catch (Exception ex) { }
		
		try {
			pages =  Integer.parseInt(totalPages)/10;
			if ((Integer.parseInt(totalPages)% 10) != 0) {
				pages = pages + 1;
			}
		} catch(NumberFormatException ne) { }
		
		forumDetailsForm.setTotalPages(new Integer(pages));
		forumDetailsForm.setSiteForums(siteForums);
		context.put("siteForums", siteForums);
		
		context.put("service", DiscussionForumsService.getInstance());
				
		if ("ADD_FORUM".equals(state.getAttribute(STATE_DISPLAY_MODE))) {
			if (forumDetailsForm.isAddForum()) {
				template = buildAddForumModeContext(portlet, context, rundata, state);
			}
		} else if ("EDIT_FORUM".equals(state.getAttribute(STATE_DISPLAY_MODE))) {
				template = buildEditForumModeContext(portlet, context, rundata, state);
		} else if ("DELETE_FORUM".equals(state.getAttribute(STATE_DISPLAY_MODE))) {
				template = buildDeleteForumModeContext(portlet, context, rundata, state);
		} else if ("SHOW_TOPICS".equals(state.getAttribute(STATE_DISPLAY_MODE))) {
				template = buildDisplayForumTopicsModeContext(portlet, context, rundata, state);
		} else if ("ADD_TOPIC".equals(state.getAttribute(STATE_DISPLAY_MODE))) {
				template = buildAddTopicModeContext(portlet, context, rundata, state);
		} else if ("EDIT_TOPIC".equals(state.getAttribute(STATE_DISPLAY_MODE))) {
				template = buildEditTopicModeContext(portlet, context, rundata, state);
		} else if ("DELETE_TOPIC".equals(state.getAttribute(STATE_DISPLAY_MODE))) {
				template = buildDeleteTopicModeContext(portlet, context, rundata, state);
		} else if ("SHOW_MESSAGES".equals(state.getAttribute(STATE_DISPLAY_MODE))) {
				template = buildDisplayTopicMessagesModeContext(portlet, context, rundata, state);
		} else if ("DELETE_MESSAGE".equals(state.getAttribute(STATE_DISPLAY_MODE))) {
				template = buildDeleteMessageModeContext(portlet, context, rundata, state);
		} else {
			template = buildDisplayForumsModeContext(portlet, context, rundata, state);
		}
		System.out.println("The template to show:>>>> " + template);
		return (String) getContext(rundata).get("template") + template;
	 } // buildMainPanelContext
	
	
	
	/////////////////// Forum Details Start/////////////////////////////////
	/** build the context for add forum */
	public String buildAddForumModeContext(VelocityPortlet portlet, Context context, RunData rundata, SessionState state) 
	{
		context.put("tlang", rb);
		context.put("message", state.getAttribute("message"));
	    context.put("systemDate", new Timestamp(new Date().getTime()));
	    return "_createforum";
	}
	
	
	/** build the context for edit forum */
	public String buildEditForumModeContext(VelocityPortlet portlet, Context context, RunData rundata, SessionState state) 
	{
		context.put("tlang", rb);
		context.put("forum", state.getAttribute("forumList"));
		context.put("message", state.getAttribute("message"));
	    context.put("systemDate", new Timestamp(new Date().getTime()));
	    return "_editforum";
	}
	
	
	/** build the context for delete forum */
	public String buildDeleteForumModeContext(VelocityPortlet portlet, Context context, RunData rundata, SessionState state) 
	{
		context.put("tlang", rb);
		//context.put("forum", state.getAttribute("forumList"));
		//context.put("message", state.getAttribute("message"));
	    context.put("systemDate", new Timestamp(new Date().getTime()));
	    return "_deleteforum";
	}
	
	
	/** build the context for display forums */
	public String buildDisplayForumsModeContext(VelocityPortlet portlet, Context context, RunData rundata, SessionState state) 
	{
		context.put("tlang", rb);
	    buildMenu(context);
	    return "_viewforums";
	}
	
	/**
	   * Build the top level menu
	   *
	   * @param context
	   */
	private void buildMenu(Context context) 
	{
	    // build the menu
	    Menu bar = new MenuImpl();    
	    bar.add(new MenuEntry(rb.getString("forum.link.createForum"), "createForum"));
		context.put(Menu.CONTEXT_MENU, bar);
	    context.put(Menu.CONTEXT_ACTION, "DiscussionForumsAction");
	}
	
	/**
	   * Set the state so the main panel renderer knows what to do
	   *
	   * @param data RunData
	   * @param context Context
	   */
	public void createForum(RunData data, Context context) 
	{
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(((JetspeedRunData) data).getJs_peid());
		state.setAttribute(STATE_DISPLAY_MODE, "ADD_FORUM");
		forumDetailsForm.getForum().setForumName("");
		forumDetailsForm.getForum().setForumDescription("");
	}
	
	public void editForum(RunData rundata, Context context) {
		String peid = ((JetspeedRunData) rundata).getJs_peid();
		SessionState state = ((JetspeedRunData) rundata).getPortletSessionState(peid);
		Integer forumId = Integer.parseInt(rundata.getParameters().getString("forumId").trim());
		Boolean editForum =  Boolean.parseBoolean(rundata.getParameters().getString("editForum").trim());
		state.setAttribute("forumList", DiscussionForumsService.getForumContent(forumId));
		if (editForum) {
			state.setAttribute(STATE_DISPLAY_MODE, "EDIT_FORUM");
		} else {
			state.setAttribute(STATE_DISPLAY_MODE, "DELETE_FORUM");
		}
	}
	
	public void doCancel(RunData data, Context context) 
	{
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(((JetspeedRunData) data).getJs_peid());
		state.setAttribute(STATE_DISPLAY_MODE, null);
	}

	public void saveForum(RunData data, Context context)
	{
		String peid = ((JetspeedRunData) data).getJs_peid();
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(peid);
		state.setAttribute(STATE_DISPLAY_MODE, null);
		String tmpDbName = "";
		String tmpName = "";
		String forumName = data.getParameters().getString("forumName").trim();
		String forumDescription = data.getParameters().getString("forumDescription").trim();
				
		if (forumName == null || forumName.length() < 1) {
			addAlert(state, rb.getString("forum.alert.noforumname"));
			state.setAttribute("message", "emptyAddForum");
			state.setAttribute(STATE_DISPLAY_MODE, "ADD_FORUM");
			return;
		}
		
		tmpName = forumName.toUpperCase();
		tmpDbName = DiscussionForumsService.getForumByName(forumName, forumDetailsForm.getForum().getSiteId()).toUpperCase();
		if (tmpName.equals(tmpDbName))
		{
			addAlert(state, rb.getString("forum.alert.sameforumname"));
			state.setAttribute("message", "existAddForum");
			state.setAttribute(STATE_DISPLAY_MODE, "ADD_FORUM");
			return;
		}
		
		forumDetailsForm.getForum().setForumName(forumName);
		forumDetailsForm.getForum().setForumDescription(forumDescription);
		
		// save forum to db
		if (!forumDetailsForm.isEditForum()){
			DiscussionForumsService.insertForum(forumDetailsForm.getForum());
		}

		if (eventTrackingService == null) {
			eventTrackingService = (EventTrackingService) ComponentManager
					.get("org.sakaiproject.event.api.EventTrackingService");
		}
		eventTrackingService.post(eventTrackingService.newEvent("discussionforums.forumadd",
				ToolManager.getCurrentPlacement().getContext(), false));
	}
	
	public void updateForum(RunData data, Context context) {
		String peid = ((JetspeedRunData) data).getJs_peid();
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(peid);
		state.setAttribute(STATE_DISPLAY_MODE, null);
		String tmpDbName = "";
		String tmpName = "";
		String forumName = data.getParameters().getString("forumName").trim();
		String forumDescription = data.getParameters().getString("forumDescription").trim();
		Integer forumId = Integer.parseInt(data.getParameters().getString("forumId").trim());

		if (forumName == null || forumName.length() < 1) {
			addAlert(state, rb.getString("forum.alert.noforumname"));
			state.setAttribute("message", "emptyEditForum");
			state.setAttribute(STATE_DISPLAY_MODE, "EDIT_FORUM");
			return;
		}
		tmpName = forumName.toUpperCase();
		tmpDbName = DiscussionForumsService.getForumByName(forumName,forumDetailsForm.getForum().getSiteId()).toUpperCase();
		if (tmpName.equals(tmpDbName))
		{
			addAlert(state, rb.getString("forum.alert.sameforumname"));
			state.setAttribute("message", "existEditForum");
			state.setAttribute(STATE_DISPLAY_MODE, "EDIT_FORUM");
			return;
		}
		// save edited forum to db
		DiscussionForumsService.updateForum(forumName, forumDescription, forumId);

		if (eventTrackingService == null) {
			eventTrackingService = (EventTrackingService) ComponentManager
					.get("org.sakaiproject.event.api.EventTrackingService");
		}
		eventTrackingService.post(eventTrackingService.newEvent("faqs.categoryedit",
				ToolManager.getCurrentPlacement().getContext(), false));
	}
	
	public void deleteForum(RunData data, Context context) {
		String peid = ((JetspeedRunData) data).getJs_peid();
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(peid);
		state.setAttribute(STATE_DISPLAY_MODE, null);
		Integer forumId = Integer.parseInt(data.getParameters().getString("forumId").trim());
		// delete forum
		DiscussionForumsService.deleteForum(forumDetailsForm.getForum().getSiteId(), forumId);

		if (eventTrackingService == null) {
			eventTrackingService = (EventTrackingService) ComponentManager
					.get("org.sakaiproject.event.api.EventTrackingService");
		}
		eventTrackingService.post(eventTrackingService.newEvent("faqs.categoryedit",
				ToolManager.getCurrentPlacement().getContext(), false));
	}
	/////////////////// Forum Details End/////////////////////////////////
	
	
	/////////////////// Topic Details Start/////////////////////////////////
	/** build the context for display forum topics */
	public String buildDisplayForumTopicsModeContext(VelocityPortlet portlet, Context context, RunData rundata, SessionState state) 
	{
		context.put("tlang", rb);
		// build the menu
	    Menu bar = new MenuImpl();
	    bar.add(new MenuEntry(rb.getString("forum.link.returnToForums"), "doCancel"));    
	    bar.add(new MenuEntry(rb.getString("forum.link.createTopic"), "createTopic"));
		context.put(Menu.CONTEXT_MENU, bar);
	    context.put(Menu.CONTEXT_ACTION, "DiscussionForumsAction");
		context.put("forumName", state.getAttribute("forumName"));
	    return "_viewtopics";
	}
	
	/** build the context for add topic */
	public String buildAddTopicModeContext(VelocityPortlet portlet, Context context, RunData rundata, SessionState state) 
	{
		context.put("tlang", rb);
		context.put("message", state.getAttribute("message"));
	    context.put("systemDate", new Timestamp(new Date().getTime()));
	    return "_createtopic";
	}
	
	/** build the context for edit topic */
	public String buildEditTopicModeContext(VelocityPortlet portlet, Context context, RunData rundata, SessionState state) 
	{
		context.put("tlang", rb);
		context.put("topic", state.getAttribute("topicList"));
		context.put("message", state.getAttribute("message"));
	    context.put("systemDate", new Timestamp(new Date().getTime()));
	    return "_edittopic";
	}
	
	/** build the context for delete topic */
	public String buildDeleteTopicModeContext(VelocityPortlet portlet, Context context, RunData rundata, SessionState state) 
	{
		context.put("tlang", rb);
		context.put("topic", state.getAttribute("topicList"));
		//context.put("message", state.getAttribute("message"));
	    context.put("systemDate", new Timestamp(new Date().getTime()));
	    return "_deletetopic";
	}
	
	public void showTopics(RunData rundata, Context context) {
		String peid = ((JetspeedRunData) rundata).getJs_peid();
		SessionState state = ((JetspeedRunData) rundata).getPortletSessionState(peid);
		Integer forumId = Integer.parseInt(rundata.getParameters().getString("forumId").trim());
		state.setAttribute("forumName", rundata.getParameters().getString("forumName").trim());
		state.setAttribute("forumTopics", DiscussionForumsService.getTopics(forumId, "ft.Creation_Date", "Asc"));
		state.setAttribute(STATE_DISPLAY_MODE, "SHOW_TOPICS");
		
		forumTopicForm.getForumTopicDetails().setForumId(forumId);
		
	}
	
	public void createTopic(RunData data, Context context) 
	{
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(((JetspeedRunData) data).getJs_peid());
		state.setAttribute(STATE_DISPLAY_MODE, "ADD_TOPIC");
		//forumDetailsForm.getForum().setForumName("");
		//forumDetailsForm.getForum().setForumDescription("");
	}
	
	public void editTopic(RunData rundata, Context context) {
		String peid = ((JetspeedRunData) rundata).getJs_peid();
		SessionState state = ((JetspeedRunData) rundata).getPortletSessionState(peid);
		Integer topicId = Integer.parseInt(rundata.getParameters().getString("topicId").trim());
		Boolean editTopic =  Boolean.parseBoolean(rundata.getParameters().getString("editTopic").trim());
		state.setAttribute("topicList", DiscussionForumsService.getTopicContent(topicId));
		if (editTopic) {
			state.setAttribute(STATE_DISPLAY_MODE, "EDIT_TOPIC");
		} else {
			state.setAttribute(STATE_DISPLAY_MODE, "DELETE_TOPIC");
		}
	}
	
	public void doTopicCancel(RunData data, Context context) 
	{
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(((JetspeedRunData) data).getJs_peid());
		state.setAttribute(STATE_DISPLAY_MODE, "SHOW_TOPICS");
	}
	
	public void saveTopic(RunData data, Context context)
	{
		String peid = ((JetspeedRunData) data).getJs_peid();
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(peid);
		state.setAttribute(STATE_DISPLAY_MODE, "SHOW_TOPICS");
		String topicName = data.getParameters().getString("topicTitle").trim();
		String topicMessage = data.getParameters().getString("topicMessage").trim();
				
		if (topicName == null || topicName.length() < 1) {
			addAlert(state, rb.getString("topic.alert.notopicname"));
			state.setAttribute("message", "emptyAddTopic");
			state.setAttribute(STATE_DISPLAY_MODE, "ADD_TOPIC");
			return;
		} else if (topicMessage == null || topicMessage.length() < 1) {
			addAlert(state, rb.getString("topic.alert.notopicmessage"));
			state.setAttribute("message", "emptyAddTopicMessage");
			state.setAttribute(STATE_DISPLAY_MODE, "ADD_TOPIC");
			return;
		}
		
		forumTopicForm.getForumTopicDetails().setTopicTitle(topicName);
		forumTopicForm.getForumTopicDetails().setTopicMessage(topicMessage);
		System.out.println("The saveTopic userID to show:>>>> " + forumDetailsForm.getForum().getUserId());
		System.out.println("The saveTopic siteID to show:>>>> " + forumDetailsForm.getForum().getSiteId());
		forumTopicForm.getForumTopicDetails().setTopicAuthor(forumDetailsForm.getForum().getUserId());
		forumTopicForm.getForumTopicDetails().setSiteId(forumDetailsForm.getForum().getSiteId());
		
		// save forum to db
		DiscussionForumsService.insertTopic(forumTopicForm.getForumTopicDetails());

		if (eventTrackingService == null) {
			eventTrackingService = (EventTrackingService) ComponentManager
					.get("org.sakaiproject.event.api.EventTrackingService");
		}
		eventTrackingService.post(
					eventTrackingService.newEvent("EventTrackingTypes.EVENT_DISCUSSIONFORUMS_ADD_TOPIC", ToolManager.getCurrentPlacement().getContext()+" Topic id: "+forumTopicForm.getForumTopicDetails().getTopicId()+" was created", false));
	
	}
	
	public void updateTopic(RunData data, Context context)
	{
		String peid = ((JetspeedRunData) data).getJs_peid();
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(peid);
		state.setAttribute(STATE_DISPLAY_MODE, "SHOW_TOPICS");
		String topicName = data.getParameters().getString("topicTitle").trim();
		Integer topicId = Integer.parseInt(data.getParameters().getString("topicId").trim());

		if (topicName == null || topicName.length() < 1) {
			addAlert(state, rb.getString("topic.alert.notopicname"));
			state.setAttribute("message", "emptyEditTopic");
			state.setAttribute(STATE_DISPLAY_MODE, "EDIT_TOPIC");
			return;
		}
		
		// save edited topic to db
		DiscussionForumsService.updateTopic(topicName, topicId);

		if (eventTrackingService == null) {
			eventTrackingService = (EventTrackingService) ComponentManager
					.get("org.sakaiproject.event.api.EventTrackingService");
		}
		eventTrackingService.post(
					eventTrackingService.newEvent("EventTrackingTypes.EVENT_DISCUSSIONFORUMS_EDIT", ToolManager.getCurrentPlacement().getContext()+" Topic id: "+topicId+" was edited", false));
	
	}
	
	public void deleteTopic(RunData data, Context context) 
	{
		String peid = ((JetspeedRunData) data).getJs_peid();
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(peid);
		state.setAttribute(STATE_DISPLAY_MODE, "SHOW_TOPICS");
		Integer topicId = Integer.parseInt(data.getParameters().getString("topicId").trim());
		// delete topic
		DiscussionForumsService.deleteTopic(topicId);

		if (eventTrackingService == null) {
			eventTrackingService = (EventTrackingService) ComponentManager
					.get("org.sakaiproject.event.api.EventTrackingService");
		}
		eventTrackingService.post(
					eventTrackingService.newEvent("EventTrackingTypes.EVENT_DISCUSSIONFORUMS_DELETE_TOPIC", ToolManager.getCurrentPlacement().getContext()+" Topic id: "+topicId+" was deleted", false));
	
	}
	/////////////////// Topic Details End /////////////////////////////////
	
	/////////////////// Message Details Start/////////////////////////////////
	/** build the context for display topic messages */
	public String buildDisplayTopicMessagesModeContext(VelocityPortlet portlet, Context context, RunData rundata, SessionState state) 
	{
		context.put("tlang", rb);
		// build the menu
	    Menu bar = new MenuImpl();
	    bar.add(new MenuEntry(rb.getString("forum.link.returnToForums"), "doCancel"));    
	    bar.add(new MenuEntry(rb.getString("forum.link.returnToTopics"), "doTopicCancel"));
		context.put(Menu.CONTEXT_MENU, bar);
	    context.put(Menu.CONTEXT_ACTION, "DiscussionForumsAction");
		context.put("forumName", state.getAttribute("forumName"));
	    return "_viewmessages";
	}
	
	/** build the context for delete message */
	public String buildDeleteMessageModeContext(VelocityPortlet portlet, Context context, RunData rundata, SessionState state) 
	{
		context.put("tlang", rb);
		//context.put("forum", state.getAttribute("forumList"));
		//context.put("message", state.getAttribute("message"));
	    context.put("systemDate", new Timestamp(new Date().getTime()));
	    return "_deletemessage";
	}
	
	public void showMessages(RunData rundata, Context context) {
		String peid = ((JetspeedRunData) rundata).getJs_peid();
		SessionState state = ((JetspeedRunData) rundata).getPortletSessionState(peid);
		Integer forumId = Integer.parseInt(rundata.getParameters().getString("forumId").trim());
		state.setAttribute("forumName", rundata.getParameters().getString("forumName").trim());
		state.setAttribute("forumTopics", DiscussionForumsService.getTopics(forumId, "ft.Creation_Date", "Asc"));
		state.setAttribute(STATE_DISPLAY_MODE, "SHOW_MESSAGES");
	}
		
	public void confirmDeleteMessage(RunData rundata, Context context) {
		String peid = ((JetspeedRunData) rundata).getJs_peid();
		SessionState state = ((JetspeedRunData) rundata).getPortletSessionState(peid);
		Integer topicId = Integer.parseInt(rundata.getParameters().getString("topicId").trim());
		Boolean editTopic =  Boolean.parseBoolean(rundata.getParameters().getString("editTopic").trim());
		//state.setAttribute("topicList", DiscussionForumsService.getForumContent(topicId));
		state.setAttribute(STATE_DISPLAY_MODE, "DELETE_MESSAGE");
	}
		
	public void saveMessage(RunData data, Context context)
	{
	
	}
	
	public void deleteMessage(RunData data, Context context) 
	{
	
	}
	/////////////////// Message Details End /////////////////////////////////

} //DiscussionForumsAction
