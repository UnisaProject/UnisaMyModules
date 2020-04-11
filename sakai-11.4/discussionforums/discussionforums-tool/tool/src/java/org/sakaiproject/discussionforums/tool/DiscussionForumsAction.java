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
import java.io.DataInputStream;
import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
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
import org.sakaiproject.discussionforums.api.model.ForumMessage;
import org.sakaiproject.discussionforums.api.model.ForumDetailsForm;
import org.sakaiproject.discussionforums.api.model.ForumTopicsForm;
import org.sakaiproject.discussionforums.api.model.ForumMessageForm;
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
	protected static final String uploadPath = "/data/sakai/content/discussionForums/";
	ForumDetailsForm forumDetailsForm = new ForumDetailsForm();
	ForumTopicsForm forumTopicForm = new ForumTopicsForm();
	ForumMessageForm forumMessageForm = new ForumMessageForm();
	
	private int start;//start of the page to be viewed;

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
        
		generalForum = DiscussionForumsService.getForumCount("General Subject Related Discussions", siteId);
		
		if(generalForum == 0)
		{
			System.out.println("The general forum count value:>>>> " + generalForum);
			Forum forum = new Forum();
			forum.setForumName("General Subject Related Discussions");
			forum.setForumDescription("A forum on general discussion about the subject content");
			forum.setSiteId(siteId);
			forum.setUserId("admin");
			DiscussionForumsService.insertForum(forum);
		}
		
		List siteForums = DiscussionForumsService.getForumList(forumDetailsForm.getForum().getSiteId(), forumDetailsForm.getSortBy(), forumDetailsForm.getSortOrder());
		
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
		} else if ("ADD_ATTACHMENT".equals(state.getAttribute(STATE_DISPLAY_MODE))) {
				template = buildMessageAttachmentModeContext(portlet, context, rundata, state);
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
	    context.put("systemDate", new Timestamp(new Date().getTime()));
	    return "_deleteforum";
	}
	
	
	/** build the context for display forums */
	public String buildDisplayForumsModeContext(VelocityPortlet portlet, Context context, RunData rundata, SessionState state) 
	{
		context.put("tlang", rb);
	    buildMenu(context);
		context.put("message", state.getAttribute("message"));
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
		eventTrackingService.post(eventTrackingService.newEvent("discussionforums.add",
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
		eventTrackingService.post(eventTrackingService.newEvent("discussionforums.edit",
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
		eventTrackingService.post(eventTrackingService.newEvent("discussionforums.delete",
				ToolManager.getCurrentPlacement().getContext(), false));
	}
	/////////////////// Forum Details End/////////////////////////////////
	
	
	/////////////////// Topic Details Start/////////////////////////////////
	/** build the context for display forum topics */
	public String buildDisplayForumTopicsModeContext(VelocityPortlet portlet, Context context, RunData rundata, SessionState state) 
	{
		context.put("tlang", rb);
		context.put("forumName", state.getAttribute("forumName"));
		
		String siteReference = "";
		Integer numberOfRecords;
		int generalTopic = -1;
		Integer forumId = Integer.parseInt(state.getAttribute("forumId").toString());
		
		try {
			siteReference = SiteService.getSite(forumDetailsForm.getForum().getSiteId()).getReference();
		} catch (IdUnusedException e) {
			context.put("nopermission", rb.getString("discussionforums.permissionerror"));
			return "_noaccess";
		}
		
		context.put("addTopic", SecurityService.unlock("discuss.addtopic", siteReference));
		context.put("deleteAnyTopic", SecurityService.unlock("discuss.deletetopic.any", siteReference));
		
		System.out.println("The addTopic to show:>>>> " + SecurityService.unlock("discuss.addtopic", siteReference));
		System.out.println("The deleteAnyTopic to show:>>>> " + SecurityService.unlock("discuss.deletetopic.any", siteReference));
		
		forumTopicForm.setTopicAddable(SecurityService.unlock("discuss.addtopic", siteReference));
		forumTopicForm.setTopicDeletable(SecurityService.unlock("discuss.deletetopic.any", siteReference));
		forumTopicForm.getForumTopicDetails().setForumId(forumId);
		forumTopicForm.setTopicForumName(state.getAttribute("forumName").toString());
		
		if (forumTopicForm.getSortBy() == null && forumTopicForm.getSortOrder() == null) {
			forumTopicForm.setSortBy("Modification_Date");
			forumTopicForm.setSortOrder("Asc");
		}
		
		if (forumTopicForm.getSortIcon() == null) {
			forumTopicForm.setSortIcon("0");
		} else if (forumTopicForm.getSortIcon().equalsIgnoreCase("1")) {
			forumTopicForm.setSortOrder("Asc");
		} else if (forumTopicForm.getSortIcon().equalsIgnoreCase("2")) {
			forumTopicForm.setSortOrder("Desc");
		} else if (forumTopicForm.getSortIcon().equalsIgnoreCase("3")) {
			forumTopicForm.setSortOrder("Asc");
		} else if (forumTopicForm.getSortIcon().equalsIgnoreCase("4")) {
			forumTopicForm.setSortOrder("Desc");
		}
		
		if (forumTopicForm.getTopicForumName().equals("General Subject Related Discussions")) {
		
			generalTopic = DiscussionForumsService.getTopicCount("General Discussions", forumId);
		
			if (generalTopic == 0)
			{
				System.out.println("The general topic count value:>>>> " + generalTopic);
				ForumTopicDetails forumTopicDetails = new ForumTopicDetails();
				forumTopicDetails.setForumId(forumId);
				forumTopicDetails.setTopicTitle("General Discussions");
				forumTopicDetails.setTopicMessage("Welcome to the General Discussions topic. In this topic you can correspond with your fellow class members on any issues regarding this course. Use the Your Message box below to add your message to the list. If you want to start a totally new topic of discussion, use the Add New Topic link which you will find in the Topics List.");
				forumTopicDetails.setTopicAuthor("admin");
				forumTopicDetails.setSiteId(state.getAttribute("siteId").toString());		
				DiscussionForumsService.insertTopic(forumTopicDetails);
			}
		
		}
		
		String topicNavigateButton = state.getAttribute("topicNavigateButton").toString();
		
		if (topicNavigateButton.equals("false")) {
			String records = rundata.getParameters().getString("records");
			if (records == null) {
				numberOfRecords = new Integer("10");
			} else {
				numberOfRecords = new Integer(records);
			}
			forumTopicForm.setRecords(numberOfRecords.toString());
		
			List forumTopics = DiscussionForumsService.getTopics(forumTopicForm.getForumTopicDetails().getForumId(), forumTopicForm.getSortBy(), forumTopicForm.getSortOrder());	
						
			forumTopicForm.setAllTopics(forumTopics);
			forumTopicForm.setTopics(pager(0,numberOfRecords,forumTopics));
			forumTopicForm.setNumberOfItems(forumTopics.size());
			forumTopicForm.setStart(1);
			forumTopicForm.setEnd(Math.min(numberOfRecords,forumTopicForm.getNumberOfItems()));
			if (forumTopicForm.getNumberOfItems() < 1){
				forumTopicForm.setStart(0);
				forumTopicForm.setEnd(0);
			}
			context.put("forumTopicForm", forumTopicForm);
			context.put("t_start", forumTopicForm.getStart());
			context.put("t_end", forumTopicForm.getEnd());
			context.put("t_numberOfItems", forumTopicForm.getNumberOfItems());
			
		} else {
			numberOfRecords = Integer.parseInt(forumTopicForm.getRecords());
			forumTopicForm.setRecords(numberOfRecords.toString());
			List forumTopics = forumTopicForm.getAllTopics();
			forumTopicForm.setTopics(pager(forumTopicForm.getStart(),numberOfRecords,forumTopics));
			if (forumTopicForm.getNumberOfItems() < 1){
				forumTopicForm.setStart(0);
				forumTopicForm.setEnd(0);
			}
			context.put("forumTopicForm", forumTopicForm);
			context.put("t_start", forumTopicForm.getStart());
			context.put("t_end", forumTopicForm.getEnd());
			context.put("t_numberOfItems", forumTopicForm.getNumberOfItems());
		}
		
		// build the menu
	    Menu bar = new MenuImpl();
		if (forumTopicForm.isTopicAddable()) {
			bar.add(new MenuEntry(rb.getString("forum.link.returnToForums"), "doCancel"));    
			bar.add(new MenuEntry(rb.getString("forum.link.createTopic"), "createTopic"));
		} else {
			bar.add(new MenuEntry(rb.getString("forum.link.returnToForums"), "doCancel"));
		}
		context.put(Menu.CONTEXT_MENU, bar);
	    context.put(Menu.CONTEXT_ACTION, "DiscussionForumsAction");
		
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
	    context.put("systemDate", new Timestamp(new Date().getTime()));
	    return "_deletetopic";
	}
	
	public void showTopics(RunData rundata, Context context) {
		String peid = ((JetspeedRunData) rundata).getJs_peid();
		SessionState state = ((JetspeedRunData) rundata).getPortletSessionState(peid);
		Integer forumId = Integer.parseInt(rundata.getParameters().getString("forumId").trim());
		state.setAttribute("forumId", forumId);
		state.setAttribute("forumName", rundata.getParameters().getString("forumName").trim());
		state.setAttribute("topicNavigateButton", "false");
		state.setAttribute(STATE_DISPLAY_MODE, "SHOW_TOPICS");
	}
	
	public void createTopic(RunData data, Context context) 
	{
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(((JetspeedRunData) data).getJs_peid());
		state.setAttribute(STATE_DISPLAY_MODE, "ADD_TOPIC");
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
		forumTopicForm.getForumTopicDetails().setTopicAuthor(forumDetailsForm.getForum().getUserId());
		forumTopicForm.getForumTopicDetails().setSiteId(forumDetailsForm.getForum().getSiteId());
		
		// save forum to db
		DiscussionForumsService.insertTopic(forumTopicForm.getForumTopicDetails());

		if (eventTrackingService == null) {
			eventTrackingService = (EventTrackingService) ComponentManager
					.get("org.sakaiproject.event.api.EventTrackingService");
		}
		eventTrackingService.post(
					eventTrackingService.newEvent("discussionforums.addtopic", ToolManager.getCurrentPlacement().getContext()+" Topic id: "+forumTopicForm.getForumTopicDetails().getTopicId()+" was created", false));
	
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
					eventTrackingService.newEvent("discussionforums.edit", ToolManager.getCurrentPlacement().getContext()+" Topic id: "+topicId+" was edited", false));
	
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
					eventTrackingService.newEvent("discussionforums.deletetopic", ToolManager.getCurrentPlacement().getContext()+" Topic id: "+topicId+" was deleted", false));
	
	}
	
	public void firstTopics(RunData data, Context context)
	{
		String peid = ((JetspeedRunData) data).getJs_peid();
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(peid);
		
		int numberOfRecords = new Integer(forumTopicForm.getRecords()).intValue();
		List forumTopics = forumTopicForm.getAllTopics();
				
		forumTopicForm.setStart(1);
		forumTopicForm.setTopics(pager(0,numberOfRecords,forumTopics));
		forumTopicForm.setEnd(Math.min(numberOfRecords, forumTopicForm.getNumberOfItems()));
		if (forumTopicForm.getNumberOfItems() < 1) {
			forumTopicForm.setStart(0);
		}
		
		state.setAttribute("topicNavigateButton", "true");
		state.setAttribute(STATE_DISPLAY_MODE, "SHOW_TOPICS");
	}
	
	public void previousTopics(RunData data, Context context)
	{
		String peid = ((JetspeedRunData) data).getJs_peid();
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(peid);
		
		int start = forumTopicForm.getStart();
		int numberOfRecords = new Integer(forumTopicForm.getRecords()).intValue();
		List forumTopics = forumTopicForm.getAllTopics();
		if ((start - numberOfRecords) <= 0){
			forumTopicForm.setStart(1);
			forumTopicForm.setTopics(pager(0,numberOfRecords,forumTopics));
			forumTopicForm.setEnd(Math.min(numberOfRecords, forumTopicForm.getNumberOfItems()));
		} else if ((start + numberOfRecords) < forumTopicForm.getNumberOfItems()){
			int end = forumTopicForm.getStart() - 1;
			start = forumTopicForm.getStart() - forumTopicForm.getNumberOfItems();
			forumTopicForm.setTopics(pager(start,numberOfRecords,forumTopics));
			forumTopicForm.setStart(start);
			forumTopicForm.setEnd(Math.min(end , forumTopicForm.getNumberOfItems()));
		} else {
			int end = forumTopicForm.getStart() - 1;
			start = forumTopicForm.getStart()- numberOfRecords;
			forumTopicForm.setTopics(pager(start,numberOfRecords,forumTopics));
			forumTopicForm.setStart(start);
			forumTopicForm.setEnd(Math.min(end , forumTopicForm.getNumberOfItems()));
		}
		
		if (forumTopicForm.getNumberOfItems() < 1){
			forumTopicForm.setStart(0);
		}
		state.setAttribute("topicNavigateButton", "true");
		state.setAttribute(STATE_DISPLAY_MODE, "SHOW_TOPICS");
	}
	
	public void nextTopics(RunData data, Context context)
	{
		String peid = ((JetspeedRunData) data).getJs_peid();
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(peid);
		
		int start = forumTopicForm.getStart();
		int numberOfRecords = new Integer(forumTopicForm.getRecords()).intValue();
		List forumTopics = forumTopicForm.getAllTopics();
				
		if (start+numberOfRecords > forumTopicForm.getNumberOfItems()){
			forumTopicForm.setStart(start);
			forumTopicForm.setTopics(pager(start,numberOfRecords,forumTopics));
			forumTopicForm.setEnd(forumTopicForm.getNumberOfItems());
		} else if ((start+numberOfRecords - 1) <= forumTopicForm.getNumberOfItems()){  
			start = forumTopicForm.getEnd()+1;
			int end = (start + numberOfRecords - 1);
			forumTopicForm.setStart(start);
			forumTopicForm.setTopics(pager(start, numberOfRecords,forumTopics));
			end=Math.min(end,forumTopicForm.getNumberOfItems());
			forumTopicForm.setEnd(end);
		} else {
			start = forumTopicForm.getEnd();
			int end = start + numberOfRecords - 1;
			forumTopicForm.setStart(start);
			forumTopicForm.setTopics(pager(start, numberOfRecords,forumTopics));
			end = Math.min(end,forumTopicForm.getNumberOfItems());
			forumTopicForm.setEnd(end);
		 }
		
		if ((forumTopicForm.getStart() + numberOfRecords - 1) == forumTopicForm.getNumberOfItems()){
			start=forumTopicForm.getStart();
			int end = forumTopicForm.getNumberOfItems();
			end=Math.min(end,forumTopicForm.getNumberOfItems());
			forumTopicForm.setEnd(end);
			forumTopicForm.setTopics(forumTopics.subList(start-1, end));
		}
		
		if (forumTopicForm.getNumberOfItems() < 1){
			forumTopicForm.setStart(0);
		}
		
		state.setAttribute("topicNavigateButton", "true");
		state.setAttribute(STATE_DISPLAY_MODE, "SHOW_TOPICS");
	}
	
	public void lastTopics(RunData data, Context context)
	{
		String peid = ((JetspeedRunData) data).getJs_peid();
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(peid);
		
		int start = forumTopicForm.getStart();
		int numberOfRecords = new Integer(forumTopicForm.getRecords()).intValue();
		List forumTopics = forumTopicForm.getAllTopics();
		
		if (forumTopicForm.getNumberOfItems() == 0){
			state.setAttribute(STATE_DISPLAY_MODE, "SHOW_TOPICS");
			return;
		}
		
		if ((forumTopicForm.getStart() + numberOfRecords-1) == forumTopicForm.getNumberOfItems()){
			start = forumTopicForm.getStart();
			int end = forumTopicForm.getNumberOfItems();
			end = Math.min(end , forumTopicForm.getNumberOfItems());
			forumTopicForm.setEnd(end);
			forumTopicForm.setTopics(forumTopics.subList(start, end));
		} else {
			start = (1 + forumTopicForm.getNumberOfItems()) - (forumTopicForm.getNumberOfItems() % numberOfRecords);
			forumTopicForm.setTopics(pager(start, numberOfRecords,forumTopics));
			forumTopicForm.setStart(start);
			forumTopicForm.setEnd(forumTopicForm.getNumberOfItems());
		}
		
		if ((forumTopicForm.getStart()-1) == forumTopicForm.getNumberOfItems()){
			start = forumTopicForm.getNumberOfItems() - numberOfRecords + 1;
			forumTopicForm.setTopics(forumTopics.subList(start, forumTopicForm.getNumberOfItems()));
			forumTopicForm.setStart(forumTopicForm.getNumberOfItems() - numberOfRecords + 1);
		}
		state.setAttribute("topicNavigateButton", "true");
		state.setAttribute(STATE_DISPLAY_MODE, "SHOW_TOPICS");
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
		context.put("message", state.getAttribute("message"));
		context.put("forumName", state.getAttribute("forumName"));
		context.put("topicId", state.getAttribute("topicId"));
		Integer topicId = Integer.parseInt(state.getAttribute("topicId").toString());
		context.put("topicTitle", state.getAttribute("topicTitle"));
		String topicTitle = state.getAttribute("topicTitle").toString();
		context.put("hidden", state.getAttribute("hidden"));
		context.put("upload", state.getAttribute("upload"));
		ForumMessage forumMessage = new ForumMessage();
		Vector messages = new Vector();
		Integer numberOfRecords;
		String siteReference = "";
		int count = 0;
		
		if (forumMessageForm.getUpload().equals("upload")) {
		
			String attachFile = state.getAttribute("attachFile").toString();
		
			if (attachFile.equals("true"))
			{
				context.put("messageReply", state.getAttribute("messageReply"));
				context.put("inputFileName", state.getAttribute("inputFileName"));
				context.put("addressLink", state.getAttribute("addressLink"));
			}
			forumMessageForm.setFilename(forumMessageForm.getFilename());
			
		} else {
			String clearAttach = state.getAttribute("clearAttach").toString();
			if (clearAttach.equals("true"))
			{
				context.put("clearAttach", state.getAttribute("clearAttach"));
				context.put("messageReply", state.getAttribute("messageReply"));
			}
			else
			{
				forumMessageForm.setFilename("");
				state.setAttribute("messageReply", "");
				state.setAttribute("inputFileName", "");
				state.setAttribute("addressLink", "");
			}
		}
		
		try {
			siteReference = SiteService.getSite(forumDetailsForm.getForum().getSiteId()).getReference();
		} catch (IdUnusedException e) {
			context.put("nopermission", rb.getString("discussionforums.permissionerror"));
			return "_noaccess";
		}
		
		context.put("addReply", SecurityService.unlock("discuss.addreply", siteReference));
		context.put("deleteAnyReply", SecurityService.unlock("discuss.deletereply.any", siteReference));
				
		forumMessageForm.setReplyAddable(SecurityService.unlock("discuss.addreply", siteReference));
		forumMessageForm.setAnyReplyDeledable(SecurityService.unlock("discuss.deletereply.any", siteReference));
		
		forumMessageForm.getForumMessage().setTopicId(topicId);
		
		forumMessage = DiscussionForumsService.getTopicPosting(forumMessageForm.getForumMessage().getTopicId());
		
		if (forumMessage.getAuthor()== null){
			forumMessage.setAuthor("Not Available");
		}
		if (forumMessage.getAuthor().equalsIgnoreCase("admin")){
			forumMessage.setAuthor("MyUnisa Administrator");
		} else if (forumMessage.getAuthor().equalsIgnoreCase("NotAvailable")) {
			forumMessage.setAuthor("Not Available");
		}
		context.put("forumMessage", forumMessage);
		forumMessageForm.setForumMessage(forumMessage);
		forumMessageForm.setMessageTopic(topicTitle);
		forumMessageForm.setForumId(state.getAttribute("forumId").toString());
		forumMessageForm.setForumName(state.getAttribute("forumName").toString());
		
		String messageNavigateButton = state.getAttribute("messageNavigateButton").toString();
		
		if (messageNavigateButton.equals("false")) {		
			String msgRecords = rundata.getParameters().getString("msgRecords");
			if (msgRecords == null){
				numberOfRecords = new Integer("10");
			} else {
				numberOfRecords = new Integer(msgRecords);
			}
			
			forumMessageForm.setMsgRecords(numberOfRecords.toString());
						
			List topicMessages = DiscussionForumsService.getMessageList(topicId);
			forumMessageForm.setallMessages(topicMessages);
			forumMessageForm.setNumberOfItems(topicMessages.size());
			forumMessageForm.setStart(1);
			forumMessageForm.setTopicMessages(pager(0,numberOfRecords,topicMessages));
			forumMessageForm.setEnd(Math.min(numberOfRecords.intValue(), new Integer(forumMessageForm.getNumberOfItems()).intValue()));
						
			if (forumMessageForm.getNumberOfItems() < 1){
				forumMessageForm.setStart(0);
				forumMessageForm.setEnd(0);
			}
			context.put("forumMessageForm", forumMessageForm);
			context.put("start", forumMessageForm.getStart());
			context.put("end", forumMessageForm.getEnd());
			context.put("numberOfItems", forumMessageForm.getNumberOfItems());
		} else {	
			if (forumMessageForm.getNumberOfItems() < 1){
				forumMessageForm.setStart(0);
				forumMessageForm.setEnd(0);
			}
			context.put("forumMessageForm", forumMessageForm);
			context.put("start", forumMessageForm.getStart());
			context.put("end", forumMessageForm.getEnd());
			context.put("numberOfItems", forumMessageForm.getNumberOfItems());
		}
		
	    return "_viewmessages";
	}
	
	/** build the context for delete message */
	public String buildDeleteMessageModeContext(VelocityPortlet portlet, Context context, RunData rundata, SessionState state) 
	{
		context.put("tlang", rb);
		context.put("message", state.getAttribute("message"));
		context.put("forumMessage", state.getAttribute("forumMessage"));
	    return "_deletemessage";
	}
	
	/** build the context for message attachment */
	public String buildMessageAttachmentModeContext(VelocityPortlet portlet, Context context, RunData rundata, SessionState state) 
	{
		context.put("tlang", rb);
		context.put("message", state.getAttribute("message"));
	    return "_addattachment";
	}
	
	public void showMessages(RunData rundata, Context context) {
		String peid = ((JetspeedRunData) rundata).getJs_peid();
		SessionState state = ((JetspeedRunData) rundata).getPortletSessionState(peid);
		Integer topicId = Integer.parseInt(rundata.getParameters().getString("topicId").trim());
		state.setAttribute("topicId", topicId);
		state.setAttribute("topicTitle", rundata.getParameters().getString("topicTitle").trim());
		forumMessageForm.setHidden(rundata.getParameters().getString("hidden").trim());
		state.setAttribute("hidden", forumMessageForm.getHidden());
		forumMessageForm.setUpload(rundata.getParameters().getString("upload").trim());
		state.setAttribute("upload", forumMessageForm.getUpload());
		state.setAttribute("clearAttach", "false");
		
		if (forumMessageForm.getUpload().equalsIgnoreCase("no")){
			forumMessageForm.setFilename("");
			forumMessageForm.setAddressLink("");
			forumMessageForm.getForumMessage().setAttachment("");
		}
		state.setAttribute("messageNavigateButton", "false");
		state.setAttribute(STATE_DISPLAY_MODE, "SHOW_MESSAGES");
	}
	
	public void saveMessage(RunData data, Context context)
	{
		String peid = ((JetspeedRunData) data).getJs_peid();
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(peid);
		state.setAttribute(STATE_DISPLAY_MODE, "SHOW_MESSAGES");
		Integer topicId = Integer.parseInt(data.getParameters().getString("topicId").trim());
		String messageReply = data.getParameters().getString("messageReply").trim();
		String attachment = "";
				
		forumMessageForm.setTopicId(topicId);
		forumMessageForm.getForumMessage().setTopicId(forumMessageForm.getTopicId());
				
		forumMessageForm.setForumId(state.getAttribute("forumId").toString());
		forumMessageForm.getForumMessage().setForumId(forumMessageForm.getForumId());
		
		if (messageReply == null || messageReply.length() < 1) {
			addAlert(state, rb.getString("message.alert.nomessagereply"));
			state.setAttribute("message", "emptyAddMessageReply");
			state.setAttribute(STATE_DISPLAY_MODE, "SHOW_MESSAGES");
			return;
		}
		forumMessageForm.getForumMessage().setMessageReply(messageReply);
		boolean test =false;
		boolean test1 =false;
		boolean test2 =false;
		boolean test3 =false;
		boolean test4 =false;
		boolean test5 =false;
		CharSequence s ="width:100%;height:100%";
		CharSequence s1="width: 100%; bottom: 0px; height: 100%";
		CharSequence s2 ="height:100%";
		CharSequence s3 ="height: 100%";
		CharSequence s4 ="width:100%";
		CharSequence s5 ="width: 100%";
		test = messageReply.contains(s);
		test1 = messageReply.contains(s1);
		test2 = messageReply.contains(s2);
		test3 = messageReply.contains(s3);
		test4 = messageReply.contains(s4);
		test5 = messageReply.contains(s5);
		
		if(test==true||test1==true||test2==true||test3==true||test4==true||test5==true){
			addAlert(state, rb.getString("message.alert.incorrectWidth"));
			state.setAttribute("message", "incorrectWidthMessageReply");
			state.setAttribute(STATE_DISPLAY_MODE, "SHOW_MESSAGES");
			return;
		}
		
		if (forumMessageForm.getUpload().equals("upload")){
			if (!forumMessageForm.getFilename().equals("")){
				attachment = state.getAttribute("siteId").toString()+"/"+forumMessageForm.getTopicId()+"/"+forumMessageForm.getFilename();
				forumMessageForm.getForumMessage().setAttachment(attachment);
				forumMessageForm.getForumMessage().setFileType("F");
			} else {
				if (!forumMessageForm.getAddressLink().equals("")){
					forumMessageForm.getForumMessage().setAttachment(forumMessageForm.getAddressLink().toLowerCase());
					forumMessageForm.getForumMessage().setFileType("L");
				}
			}
		}
		else
		{
			forumMessageForm.getForumMessage().setAttachment(" ");
			forumMessageForm.getForumMessage().setFileType(" ");
		}
		
		forumMessageForm.getForumMessage().setAuthor(state.getAttribute("userID").toString());
		if(isInteger(forumMessageForm.getForumMessage().getAuthor())){
			forumMessageForm.getForumMessage().setUserType("S");
		} else {
			forumMessageForm.getForumMessage().setUserType("L");
		}
		
		//if topic id is zero
		if(forumMessageForm.getTopicId() == null||forumMessageForm.getTopicId().equals(0)){
			addAlert(state, rb.getString("message.alert.zeroTopicId"));
			state.setAttribute("message", "zeroTopicId");
			state.setAttribute(STATE_DISPLAY_MODE, "SHOW_MESSAGES");
			return;
		}
		
		//save message to db
		DiscussionForumsService.insertMessage(forumMessageForm.getForumMessage());
		
		forumMessageForm.getForumMessage().setMessageReply("");
		if (eventTrackingService == null) {
			eventTrackingService = (EventTrackingService) ComponentManager
					.get("org.sakaiproject.event.api.EventTrackingService");
		}
		eventTrackingService.post(
					eventTrackingService.newEvent("discussionforums.addreply", ToolManager.getCurrentPlacement().getContext()+" Message id: "+forumMessageForm.getForumMessage().getMessageId()+" was added", false));
		forumMessageForm.setHidden("0");
		forumMessageForm.setUpload("no");
		forumMessageForm.setAddressLink("");
		//forumMessageForm.setTheFile(null);
		forumMessageForm.setCancel("no");
		state.setAttribute("clearAttach", "false");
	}
	
	public void confirmDeleteMessage(RunData rundata, Context context) {
		String peid = ((JetspeedRunData) rundata).getJs_peid();
		SessionState state = ((JetspeedRunData) rundata).getPortletSessionState(peid);
		ForumMessage forumMessage = new ForumMessage();
		Integer messageId = Integer.parseInt(rundata.getParameters().getString("messageId").trim());
		forumMessage = DiscussionForumsService.getMessageDetail(messageId);	
		state.setAttribute("forumMessage", forumMessage);
		String loginUser = state.getAttribute("userID").toString();
		String userCreatedMessage = forumMessage.getUserId();
		if (forumMessageForm.isAnyReplyDeledable()) {
			addAlert(state, rb.getString("message.alert.confirmdeletemessage"));
			state.setAttribute("message", "deleteMessage");
			forumMessageForm.setForumMessage(forumMessage);
			state.setAttribute(STATE_DISPLAY_MODE, "DELETE_MESSAGE");
		} else {
			if (loginUser.equals(userCreatedMessage)) {
				addAlert(state, rb.getString("message.alert.confirmdeletemessage"));
				state.setAttribute("message", "deleteMessage");
				forumMessageForm.setForumMessage(forumMessage);
				state.setAttribute(STATE_DISPLAY_MODE, "DELETE_MESSAGE");
			} else {
				addAlert(state, rb.getString("message.alert.forbiddendeletemessage"));
				state.setAttribute("message", "forbiddendeletemessage");
				state.setAttribute(STATE_DISPLAY_MODE, null);
			}
		}
	}
	
	public void deleteMessage(RunData data, Context context) 
	{
		String peid = ((JetspeedRunData) data).getJs_peid();
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(peid);
		state.setAttribute(STATE_DISPLAY_MODE, "SHOW_MESSAGES");
		Integer messageId = Integer.parseInt(data.getParameters().getString("messageId").trim());
		String attach="";
		
		// delete message
		DiscussionForumsService.deleteMessage(messageId);
				
		if (forumMessageForm.getForumMessage().getFileType().equals("F"))
		{
			String currentDir = forumMessageForm.getForumMessage().getAttachment().substring(0, forumMessageForm.getForumMessage().getAttachment().lastIndexOf("/")-1);
			attach = uploadPath+forumMessageForm.getForumMessage().getAttachment();
			
			File file = new File(attach);
			if (file.exists()){
				file.delete();
			}
			File listDir = new File(uploadPath+currentDir);
			if (listDir.isDirectory()){
				if (listDir.listFiles().length < 1){
					listDir.delete();
				}
			}
		}
		
		if (eventTrackingService == null) {
			eventTrackingService = (EventTrackingService) ComponentManager
					.get("org.sakaiproject.event.api.EventTrackingService");
		}
		eventTrackingService.post(
					eventTrackingService.newEvent("discussionforums.deletereply", ToolManager.getCurrentPlacement().getContext()+" Message id: "+messageId+" was deleted", false));
		
		forumMessageForm.getForumMessage().setMessageReply("");
		forumMessageForm.setHidden("0");
	}
	
	public void doMessageCancel(RunData data, Context context) 
	{
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(((JetspeedRunData) data).getJs_peid());
		state.setAttribute(STATE_DISPLAY_MODE, "SHOW_MESSAGES");
	}
	
	public void addAttachment(RunData rundata, Context context) {
		String peid = ((JetspeedRunData) rundata).getJs_peid();
		SessionState state = ((JetspeedRunData) rundata).getPortletSessionState(peid);		
		String messageReply = rundata.getParameters().getString("messageReply").trim();
		state.setAttribute("messageReply", messageReply);
		state.setAttribute(STATE_DISPLAY_MODE, "ADD_ATTACHMENT");
	}
		
	public void attachFile(RunData rundata, Context context) {
		String peid = ((JetspeedRunData) rundata).getJs_peid();
		SessionState state = ((JetspeedRunData) rundata).getPortletSessionState(peid);
		
		String readFile = "";
		String writeFile = "";
		FileInputStream buffIn = null;
		FileOutputStream buffout = null;
		String fileDir;
		
		String upload = rundata.getParameters().getString("upload").trim();	
		Integer topicId = Integer.parseInt(rundata.getParameters().getString("topicId").trim());
		String inputFileName = rundata.getRequest().getAttribute("theFile").toString();
		String addressLink = rundata.getParameters().getString("addressLink").trim();
				
		if ((inputFileName == null || inputFileName.length() < 1) && (addressLink == null || addressLink.length() < 1))  {
			addAlert(state, rb.getString("message.alert.noattachment"));
			state.setAttribute("message", "emptyAddAttachment");
			state.setAttribute(STATE_DISPLAY_MODE, "ADD_ATTACHMENT");
			return;
		} else if ((inputFileName.length() > 1) && (addressLink.length() > 1))  {
			addAlert(state, rb.getString("message.alert.bothattachment"));
			state.setAttribute("message", "bothAddAttachment");
			state.setAttribute(STATE_DISPLAY_MODE, "ADD_ATTACHMENT");
			return;
		}
		
		forumMessageForm.setUpload(upload);
		state.setAttribute("upload", forumMessageForm.getUpload());
		
		forumMessageForm.setTopicId(topicId);
		forumMessageForm.getForumMessage().setTopicId(forumMessageForm.getTopicId());
		state.setAttribute("topicId", topicId);
		
		state.setAttribute("attachFile", "true");
		
		String [] input = inputFileName.split(",");
		String[] parseInput;
		String extensions = "exe,zip,avi";
		String[] extList = extensions.split(",");
		
		for (int i=0; i < input.length; i++){
			parseInput = input[i].split("=");
			if (parseInput.length > 1){
				if (i == 0){						
					String tmp = parseInput[1];
					if (parseInput[1].lastIndexOf("\\") > 0 ){
						tmp = parseInput[1].substring(parseInput[1].lastIndexOf("\\")+1);
					}
					writeFile = tmp;
				} else if (i == 1){
					readFile = parseInput[1];
				}
			}
		}
		
		if (writeFile.length() > 0)  {
		
			boolean notAllowed = false;
			state.setAttribute("inputFileName", writeFile);
			forumMessageForm.setFilename(writeFile);
			File testSize = new File(readFile);
			for(int i=0; i < extList.length; i++){
				if (writeFile.substring(writeFile.lastIndexOf(".")+1).equalsIgnoreCase(extList[i])){
					notAllowed = true;
				}
			}
			
			if (notAllowed){
				addAlert(state, rb.getString("message.alert.wrongfileformat"));
				state.setAttribute("message", "wrongfileformat");
				state.setAttribute(STATE_DISPLAY_MODE, "ADD_ATTACHMENT");
				return;
			}
						
			if (testSize.length() > 6291456){
				addAlert(state, rb.getString("message.alert.bigfile"));
				state.setAttribute("message", "bigfile");
				state.setAttribute(STATE_DISPLAY_MODE, "ADD_ATTACHMENT");
				return;
			} else {
				try {
					buffIn = new FileInputStream(readFile);
					fileDir = uploadPath+state.getAttribute("siteId").toString()+"/"+forumMessageForm.getTopicId()+"/";
					File file = new File(fileDir);
					if (!file.exists()){
						file.mkdirs();
					}
					buffout = new FileOutputStream(fileDir+writeFile);
					boolean eof = false;
					while(!eof){
						int line = buffIn.read();
						if (line == -1){
							eof  = true;
						} else {
							buffout.write(line);
						}
					}
					buffIn.close();
					buffout.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (NullPointerException ne) {						
					ne.printStackTrace();						
				} catch (IOException ioe){
					ioe.printStackTrace();
				} finally {
					try{
						if (buffIn != null){
							buffIn.close();
							buffout.close();
						}
					} catch(IOException ie){
						ie.printStackTrace();
					}catch(NullPointerException ne){
						ne.printStackTrace();
					}
				}
			}
		
		} else if (addressLink.length() > 0) {
		
			if (!addressLink.startsWith("http://")){	
				forumMessageForm.setAddressLink("");
				addAlert(state, rb.getString("message.alert.wrongURL"));
				state.setAttribute("message", "wrongURL");
				state.setAttribute(STATE_DISPLAY_MODE, "ADD_ATTACHMENT");
				return;
			} else {
				forumMessageForm.setAddressLink(addressLink);
				state.setAttribute("addressLink", addressLink);
			}
			
		}
		forumMessageForm.setHidden("0");
		state.setAttribute(STATE_DISPLAY_MODE, "SHOW_MESSAGES");
	}
	
	public void clearAttachment(RunData rundata, Context context) 
	{
		String peid = ((JetspeedRunData) rundata).getJs_peid();
		SessionState state = ((JetspeedRunData) rundata).getPortletSessionState(peid);
		String clearAttach = rundata.getParameters().getString("fname").trim();
		if (!(clearAttach.equals("clearUrl"))){
			String fileName = uploadPath+state.getAttribute("siteId").toString()+"/"+state.getAttribute("topicId").toString()+"/"+clearAttach;
			File file = new File(fileName);
			if (file.exists()){
				file.delete();
			}
			state.setAttribute("inputFileName", "");
		} else {
			forumMessageForm.setAddressLink("");
			state.setAttribute("addressLink", "");
		}
		forumMessageForm.setUpload("no");
		state.setAttribute("upload", forumMessageForm.getUpload());
		state.setAttribute("clearAttach", "true");
		forumMessageForm.setFilename("");
		
		state.setAttribute(STATE_DISPLAY_MODE, "SHOW_MESSAGES");
	}
	
	public void readAttachment(RunData rundata, Context context) 
	{
		String peid = ((JetspeedRunData) rundata).getJs_peid();
		SessionState state = ((JetspeedRunData) rundata).getPortletSessionState(peid);
		//ServletContext application;
		DataInputStream in = null;
		ServletOutputStream sos = null;
		
		String attachment = rundata.getParameters().getString("attachment");
		String fileName = attachment.substring(attachment.lastIndexOf("/")+1);
		System.out.println("The attachment from readAttachment value to show:>>>> " + attachment);		
		System.out.println("The fileName from readAttachment value to show:>>>> " + fileName);
				
		/* try 
		{
			in = new DataInputStream(new FileInputStream(new File(uploadPath+attachment)));
			sos = response.getOutputStream();
		} 
		catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} */
		
		/* Added by mphahsm on 2016/05 to handle any file content type and download file instead of opening it inline*/
		//response.setContentType("application/octet-stream");
		//response.addHeader("content-disposition", "attachment; filename="+fileName); 
		/* End of mphahsm Add*/
		
		/* int w;
		try {
			w = in.read();
			while (w != -1) {
				sos.write(w);
				w = in.read();
			}
			sos.flush();
			sos.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} */
		
	}
	
	public void firstMessages(RunData data, Context context)
	{
		String peid = ((JetspeedRunData) data).getJs_peid();
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(peid);
		
		forumMessageForm.getForumMessage().setForumId(forumMessageForm.getForumId());
				
		int numberOfRecords = new Integer(forumMessageForm.getMsgRecords()).intValue();
		List forumMessages = forumMessageForm.getAllMessages();
				
		forumMessageForm.setStart(1);
		forumMessageForm.setTopicMessages(pager(0,numberOfRecords,forumMessages));
		forumMessageForm.setEnd(Math.min(numberOfRecords, forumMessageForm.getNumberOfItems()));
		if (forumMessageForm.getNumberOfItems() < 1){
			forumMessageForm.setStart(0);
		}
		state.setAttribute("messageNavigateButton", "true");
		state.setAttribute(STATE_DISPLAY_MODE, "SHOW_MESSAGES");
	}
	
	public void previousMessages(RunData data, Context context)
	{
		String peid = ((JetspeedRunData) data).getJs_peid();
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(peid);
		
		forumMessageForm.getForumMessage().setForumId(forumMessageForm.getForumId());
		
		int start = forumMessageForm.getStart();
		int numberOfRecords = new Integer(forumMessageForm.getMsgRecords()).intValue();
		List forumMessages = forumMessageForm.getAllMessages();
		if ((start - numberOfRecords) <= 0){
			forumMessageForm.setStart(1);
			forumMessageForm.setTopicMessages(pager(0,numberOfRecords,forumMessages));
			forumMessageForm.setEnd(Math.min(numberOfRecords, forumMessageForm.getNumberOfItems()));
		} else if ((start + numberOfRecords) < forumMessageForm.getNumberOfItems()){
			int end = forumMessageForm.getStart() - 1;
			start = forumMessageForm.getStart() - numberOfRecords;
			forumMessageForm.setTopicMessages(pager(start,numberOfRecords,forumMessages));
			forumMessageForm.setStart(start);
			forumMessageForm.setEnd(Math.min(end , forumMessageForm.getNumberOfItems()));
		} else {
			int end = forumMessageForm.getStart() - 1;
			start = forumMessageForm.getStart()- numberOfRecords;
			forumMessageForm.setTopicMessages(pager(start,numberOfRecords,forumMessages));
			forumMessageForm.setStart(start);
			forumMessageForm.setEnd(Math.min(end , forumMessageForm.getNumberOfItems()));
		}
		
		if (forumMessageForm.getNumberOfItems() < 1){
			forumMessageForm.setStart(0);
		}
		
		state.setAttribute("messageNavigateButton", "true");
		state.setAttribute(STATE_DISPLAY_MODE, "SHOW_MESSAGES");
	}
	
	public void nextMessages(RunData data, Context context)
	{
		String peid = ((JetspeedRunData) data).getJs_peid();
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(peid);
		
		forumMessageForm.getForumMessage().setForumId(forumMessageForm.getForumId());
				
		int start = forumMessageForm.getStart();
		int numberOfRecords = new Integer(forumMessageForm.getMsgRecords()).intValue();
		List forumMessages = forumMessageForm.getAllMessages();
				
		if (start+numberOfRecords > forumMessageForm.getNumberOfItems()){
			forumMessageForm.setStart(start);
			forumMessageForm.setTopicMessages(pager(start,numberOfRecords,forumMessages));
			forumMessageForm.setEnd(forumMessageForm.getNumberOfItems());
		} else if ((start+numberOfRecords - 1) <= forumMessageForm.getNumberOfItems()){  
			start = forumMessageForm.getEnd()+1;
			int end = (start + numberOfRecords - 1);
			forumMessageForm.setStart(start);
			forumMessageForm.setTopicMessages(pager(start, numberOfRecords,forumMessages));
			end=Math.min(end,forumMessageForm.getNumberOfItems());
			forumMessageForm.setEnd(end);
		} else {
			start = forumMessageForm.getEnd();
			int end = start + numberOfRecords - 1;
			forumMessageForm.setStart(start);
			forumMessageForm.setTopicMessages(pager(start, numberOfRecords,forumMessages));
			end = Math.min(end,forumMessageForm.getNumberOfItems());
			forumMessageForm.setEnd(end);
		 }
		
		if ((forumMessageForm.getStart() + numberOfRecords - 1) == forumMessageForm.getNumberOfItems()){
			start=forumMessageForm.getStart();
			int end = forumMessageForm.getNumberOfItems();
			end=Math.min(end,forumMessageForm.getNumberOfItems());
			forumMessageForm.setEnd(end);
			forumMessageForm.setTopicMessages(forumMessages.subList(start, end));
		}
		
		if (forumMessageForm.getNumberOfItems() < 1){
			forumMessageForm.setStart(0);
		}
		
		state.setAttribute("messageNavigateButton", "true");
		state.setAttribute(STATE_DISPLAY_MODE, "SHOW_MESSAGES");
	}
	
	public void lastMessages(RunData data, Context context)
	{
		String peid = ((JetspeedRunData) data).getJs_peid();
		SessionState state = ((JetspeedRunData) data).getPortletSessionState(peid);
		
		forumMessageForm.getForumMessage().setForumId(forumMessageForm.getForumId());
		
		int start = forumMessageForm.getStart();
		int numberOfRecords = new Integer(forumMessageForm.getMsgRecords()).intValue();
		List forumMessages = forumMessageForm.getAllMessages();
		
		if (forumMessageForm.getNumberOfItems() == 0){
			state.setAttribute(STATE_DISPLAY_MODE, "SHOW_MESSAGES");
			return;
		}
		
		if ((forumMessageForm.getStart() + numberOfRecords-1) == forumMessageForm.getNumberOfItems()){
			start = forumMessageForm.getStart();
			int end = forumMessageForm.getNumberOfItems();
			end = Math.min(end , forumMessageForm.getNumberOfItems());
			forumMessageForm.setEnd(end);
			forumMessageForm.setTopicMessages(forumMessages.subList(start, end));
		} else {
			start = (1 + forumMessageForm.getNumberOfItems()) - (forumMessageForm.getNumberOfItems() % numberOfRecords);
			forumMessageForm.setTopicMessages(pager(start, numberOfRecords,forumMessages));
			forumMessageForm.setStart(start);
			forumMessageForm.setEnd(forumMessageForm.getNumberOfItems());
		}
		
		if ((forumMessageForm.getStart()-1) == forumMessageForm.getNumberOfItems()){
			start = forumMessageForm.getNumberOfItems() - numberOfRecords + 1;
			forumMessageForm.setTopicMessages(forumMessages.subList(start, forumMessageForm.getNumberOfItems()));
			forumMessageForm.setStart(forumMessageForm.getNumberOfItems() - numberOfRecords + 1);
		}
		
		state.setAttribute("messageNavigateButton", "true");
		state.setAttribute(STATE_DISPLAY_MODE, "SHOW_MESSAGES");
	}
	
	/////////////////// Message Details End /////////////////////////////////
	
	private List pager(int start,int records,List listofItems)
	{
		int end = start+records;
		int listSize=0;
		try{
		 listSize = listofItems.size();
		} catch(NullPointerException ne){}
		if (start<0){
			start=1;
		}
		if (listSize == 0){
			start=0;
			return listofItems;
		} else if (listSize <=records){
			start = 0;
			listofItems = listofItems.subList(start,listSize);
			end=listSize;
		} else {
			if(listSize > records){ 
				if (start+records <= listSize){
					if (start==0)
					{
						start=1;
					}
			        listofItems=listofItems.subList(start-1,start+records-1);
			        end =start+records-1;
				} else if( start+records > listSize){
					start =listSize - (listSize%records);
					listofItems=listofItems.subList(start,listSize);
					end=listSize;
				}
			}
		}
		this.start = start;
		return listofItems;
	}		
	
	private boolean isInteger(String i)
	{
		try{
			Integer.parseInt(i);
			return true;
		}catch(NumberFormatException nfe)
		{
			return false;
		}
	}

} //DiscussionForumsAction
