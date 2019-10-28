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
package org.sakaiproject.yaft.tool;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

import org.sakaiproject.component.api.ComponentManager;
import org.sakaiproject.search.api.SearchResult;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.Tool;
import org.sakaiproject.util.RequestFilter;
import org.sakaiproject.util.ResourceLoader;
import org.sakaiproject.yaft.api.*;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * This servlet handles all of the REST type stuff. At some point this may all
 * move into an EntityProvider.
 * 
 * @author Adrian Fish (a.fish@lancaster.ac.uk)
 */
public class YaftTool extends HttpServlet {
	
	private Logger logger = Logger.getLogger(YaftTool.class);

	private YaftForumService yaftForumService = null;

	private SakaiProxy sakaiProxy;
	
	private Template bootstrapTemplate = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("doGet()");
		}

		if (yaftForumService == null || sakaiProxy == null)
			throw new ServletException("yaftForumService and sakaiProxy MUST be initialised.");
		
		String userId = null;
		Session session = (Session) request.getAttribute(RequestFilter.ATTR_SESSION);
		if(session != null) {
			userId = session.getUserId();
		} else {
			// We are not logged in
			throw new ServletException("getCurrentUser returned null.");
		}

		String siteId = sakaiProxy.getCurrentSiteId();
		String placementId = (String) request.getAttribute(Tool.PLACEMENT_ID);
		
		String sakaiHtmlHead = (String) request.getAttribute("sakai.html.head");

		// We need to pass the language code to the JQuery code in the pages.
		Locale locale = (new ResourceLoader(userId)).getLocale();
		String language = locale.getLanguage();
		String country = locale.getCountry();
		
		String isoLanguage = language;

        if(country != null && !country.equals("")) {
            isoLanguage += "_" + country;
        }
        
		VelocityContext ctx = new VelocityContext();
		
		// This is needed so certain trimpath variables don't get parsed.
		ctx.put("D", "$");
       
		ctx.put("sakaiHtmlHead",sakaiHtmlHead);
		ctx.put("version","?2");
	    ctx.put("userId",userId);
	    ctx.put("siteId",siteId);
	    ctx.put("state","forums");
	    ctx.put("placementId",placementId);
	    ctx.put("isolanguage",isoLanguage);
	    ctx.put("editor",sakaiProxy.getWysiwygEditor());

		String pathInfo = request.getPathInfo();

		String uri = request.getRequestURI();

		if (pathInfo == null || pathInfo.length() < 1) {
			// There's no path info, so this is the initial state
			
			if (uri.contains("/portal/pda/")) {
				ctx.put("viewMode","minimal");
				ctx.put("onPDAPortal","true");
			} else {
				ctx.put("viewMode","full");
				ctx.put("onPDAPortal","false");
			}
			
	        response.setStatus(HttpServletResponse.SC_OK);
	        response.setContentType("text/html");
	        Writer writer = new BufferedWriter(response.getWriter());
	        try {
	        	bootstrapTemplate.merge(ctx,writer);
			} catch (Exception e) {
				logger.error("Failed to merge template. Returning 500.",e);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
	        writer.close();
		} else {
			String[] parts = pathInfo.substring(1).split("/");

			if (parts.length >= 1) {
				String part1 = parts[0];

				if (part1.startsWith("forums")) {
					doForumsGet(request, response, parts, userId, siteId, placementId, locale,ctx);
				} else if ("authors".equals(part1)) {
					if (parts.length == 1) {
						List<Author> authors = yaftForumService.getAuthorsForCurrentSite();
						JSONArray data = JSONArray.fromObject(authors);
						response.setStatus(HttpServletResponse.SC_OK);
						response.setContentType("application/json");
						response.getWriter().write(data.toString());
						return;
					} else if (parts.length == 3) {
						String authorId = parts[1];
						String authorOp = parts[2];

						if ("messages".equals(authorOp)) {
							List<Message> messages = yaftForumService.getMessagesForAuthorInCurrentSite(authorId);
							JsonConfig config = new JsonConfig();
							config.setExcludes(new String[] { "properties", "reference" });
							JSONArray data = JSONArray.fromObject(messages, config);
							response.setStatus(HttpServletResponse.SC_OK);
							response.setContentType("application/json");
							response.getWriter().write(data.toString());
							return;
						}
					}
				} else if ("siteGroups".equals(part1)) {
					List<Group> groups = sakaiProxy.getCurrentSiteGroups();
					JSONArray data = JSONArray.fromObject(groups);
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json");
					response.getWriter().write(data.toString());
					return;
				} else if ("assignments".equals(part1)) {
					doAssignmentsGet(response, parts);
				}

				else if ("perms.json".equals(part1)) {
					doPermsGet(response);
				}

				else if ("userData.json".equals(part1)) {
					doUserDataGet(response, siteId);
				}

				else if ("forumContainingMessage".equals(part1)) {
					String messageId = (String) request.getParameter("messageId");

					if (messageId == null) {
						response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path provided: expect to receive the message id");
						return;
					}

					Message message = yaftForumService.getMessage(messageId);

					if (message == null) {
						response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid messageId provided.");
						return;
					}

					Forum forum = yaftForumService.getForumContainingMessage(messageId);

					JSONObject data = JSONObject.fromObject(forum);
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json");
					response.getWriter().write(data.toString());
					return;
				}

				else if ("discussions".equals(part1)) {
					doDiscussionsGet(request, response, parts, userId, siteId, placementId, locale,ctx);
				}

				else if ("messages".equals(part1)) {
					doMessagesGet(request, response, parts, userId, siteId, placementId, locale,ctx);
				}
			}
		}
	}

	private void doForumsGet(HttpServletRequest request, HttpServletResponse response, String[] parts, String userId, String siteId, String placementId, Locale locale,VelocityContext ctx) throws ServletException, IOException {
		String state = request.getParameter("state");

		if (state == null)
			state = "";

		if (parts.length == 1) {
			try {
				List<Forum> fora = yaftForumService.getSiteForums(siteId, false);

				JSONArray data = JSONArray.fromObject(fora);
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("application/json");
				response.getWriter().write(data.toString());
				response.getWriter().close();
				return;
			} catch (Exception e) {
				logger.error("Caught exception whilst getting fora.", e);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
		} else if (parts.length >= 2) {
			String forumId = parts[1];

			if ("allReadMessages.json".equals(forumId)) {
				Map<String, Integer> read = yaftForumService.getReadMessageCountForAllFora();
				JSONObject data = JSONObject.fromObject(read);
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("application/json");
				response.getWriter().write(data.toString());
				return;
			}

			if (parts.length == 2) {
				try {
					if (!forumId.endsWith(".json")) {
						forumId = forumId.substring(0, forumId.length() - 5);
						
						ctx.put("forumId",forumId);
						ctx.put("state","forum");
				        
						response.setStatus(HttpServletResponse.SC_OK);
						response.setContentType("text/html");
						Writer writer = new BufferedWriter(response.getWriter());
						try {
							bootstrapTemplate.merge(ctx,writer);
						} catch (Exception e) {
							logger.error("Failed to merge template. Returning 500.",e);
							response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
						}
						writer.close();
						return;
					}
					
					forumId = forumId.substring(0,forumId.indexOf(".json"));

					Forum forum = yaftForumService.getForum(forumId, state);
					JsonConfig config = new JsonConfig();
					config.setExcludes(new String[] { "properties", "reference" });
					//config.registerJsonBeanProcessor("sqldateprocessor", new JsDateJsonValueProcessor());
					JSONObject forumObject = JSONObject.fromObject(forum, config);
					Map<String, Integer> counts = yaftForumService.getReadMessageCountForForum(forumId);
					JSONObject countsObject = JSONObject.fromObject(counts);
					JSONObject data = new JSONObject();
					data.accumulate("forum", forumObject);
					data.accumulate("counts", countsObject);
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json");
					response.getWriter().write(data.toString());
					return;
				} catch (Exception e) {
					logger.error("Caught exception whilst getting forum.", e);
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					return;
				}
			} else {
				String forumOp = parts[2];
				if ("delete".equals(forumOp)) {
                    try {
					    yaftForumService.deleteForum(forumId, siteId, userId);
                        response.setStatus(HttpServletResponse.SC_OK);
                        response.setContentType("text/plain");
                        response.getWriter().write("success");
                        response.getWriter().close();
                    } catch (YaftSecurityException yse) {
                        logger.error(yse);
                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    }
					return;
				}
			}
		}
	}

	private void doDiscussionsGet(HttpServletRequest request, HttpServletResponse response, String[] parts, String userId, String siteId, String placementId, Locale locale, VelocityContext ctx) throws ServletException, IOException {
		if (parts.length >= 2) {
			String discussionId = parts[1];
			
			if (parts.length == 2) {
				if ("discussionContainingMessage".equals(discussionId)) {
					String messageId = (String) request.getParameter("messageId");

					if (messageId == null)
						throw new IllegalArgumentException("Invalid path provided: expect to receive the message id");

					Message message = yaftForumService.getMessage(messageId);

					if (message == null) {
						response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid message id supplied.");
						return;
					}

					Discussion discussion = yaftForumService.getDiscussion(message.getDiscussionId(), true);
					JsonConfig config = new JsonConfig();
					config.setExcludes(new String[] { "properties", "reference" });
					JSONArray data = JSONArray.fromObject(discussion, config);
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json");
					response.getWriter().write(data.toString());
					response.getWriter().close();
					return;
				}

				boolean isHtmlRequest = false;
				
				if(discussionId.endsWith(".html")){
					isHtmlRequest = true;
					discussionId = discussionId.substring(0, discussionId.length() - 5);	
				} else if (discussionId.endsWith(".json")) {
					discussionId = discussionId.substring(0,discussionId.indexOf(".json"));
				}else{
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, " DiscussionId empty/null. Please check the browser version ");
					return;
				}

				/*if (discussionId.endsWith(".html")) {
					isHtmlRequest = true;
					discussionId = discussionId.substring(0, discussionId.length() - 5);
				}*/

				if (isHtmlRequest) {
					Forum forum = yaftForumService.getForumContainingMessage(discussionId);
					
				    ctx.put("discussionId",discussionId);
				    ctx.put("forumId",forum.getId());
				    ctx.put("state","full");
				        
				    response.setStatus(HttpServletResponse.SC_OK);
				    response.setContentType("text/html");
				    Writer writer = new BufferedWriter(response.getWriter());
				    try {
				    	bootstrapTemplate.merge(ctx,writer);
					} catch (Exception e) {
						logger.error("Failed to merge template. Returning 500.",e);
						response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					}
				    writer.close();
					return;
				}
			    
				try{
				if(discussionId==null || discussionId.length()==0){
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DiscussionId empty/null");
					return;
				}else{
					
			  /*	if (discussionId.endsWith(".json")) {
					discussionId = discussionId.substring(0,discussionId.indexOf(".json"));
				}	*/	

				Discussion discussion = yaftForumService.getDiscussion(discussionId, true);

				JsonConfig config = new JsonConfig();
				config.setExcludes(new String[] { "properties", "reference" });

				JSONObject data = JSONObject.fromObject(discussion, config);
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("application/json");
				response.getWriter().write(data.toString());
				response.getWriter().close();
				return;
				}
				}catch(NullPointerException ne){
					logger.error("Yaft: Failed to get the discussion id student nr"+userId,ne);
				}
			} else {
				String discussionOp = parts[2];
				if ("delete".equals(discussionOp)) {
					 try {
	                        yaftForumService.deleteDiscussion(discussionId, siteId, userId);
	                        response.setStatus(HttpServletResponse.SC_OK);
	                        response.setContentType("text/plain;");
	                        response.getWriter().write("success");
	                        response.getWriter().close();
	                    } catch (YaftSecurityException yse) {
	                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
	                    }
				} else if ("markRead".equals(discussionOp)) {
					Forum forum = yaftForumService.getForumContainingMessage(discussionId);
					String forumId = forum.getId();
					if (yaftForumService.markDiscussionRead(discussionId, forumId)) {
						response.setContentType("text/plain");
						response.getWriter().write("success");
						response.getWriter().close();
					} else {
						response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
						response.getWriter().close();
					}

					return;
				} else if ("readMessages.json".equals(discussionOp)) {
					List<String> ids = yaftForumService.getReadMessageIds(discussionId);
					JSONArray data = JSONArray.fromObject(ids);
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json");
					response.getWriter().write(data.toString());
					response.getWriter().close();
					return;
				} else if ("authors".equals(discussionOp)) {
					if (parts.length == 3) {
						List<Author> authors = yaftForumService.getAuthorsForDiscussion(discussionId);
						JSONArray data = JSONArray.fromObject(authors);
						response.setStatus(HttpServletResponse.SC_OK);
						response.setContentType("application/json");
						response.getWriter().write(data.toString());
						return;
					} else if (parts.length == 5) {
						String authorId = parts[3];
						String authorOp = parts[4];

						if ("messages".equals(authorOp)) {
							List<Message> messages = yaftForumService.getMessagesForAuthorInDiscussion(authorId, discussionId);
							JsonConfig config = new JsonConfig();
							config.setExcludes(new String[] { "properties", "reference" });
							JSONArray data = JSONArray.fromObject(messages, config);
							response.setStatus(HttpServletResponse.SC_OK);
							response.setContentType("application/json");
							response.getWriter().write(data.toString());
							return;
						}
					}
				} else if ("clear".equals(discussionOp)) {
					yaftForumService.clearDiscussion(discussionId);
					Discussion discussion = yaftForumService.getDiscussion(discussionId,false);
					JsonConfig config = new JsonConfig();
					config.setExcludes(new String[] { "properties", "reference" });
					JSONObject json = JSONObject.fromObject(discussion,config);
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json");
					response.getWriter().write(json.toString());
					response.getWriter().close();
					return;
				}
			}
		}
	}

	private void doMessagesGet(HttpServletRequest request, HttpServletResponse response, String[] parts, String userId, String siteId, String placementId, Locale locale, VelocityContext ctx) throws ServletException, IOException {
		if (parts.length >= 2) {
			String messageId = parts[1];

			boolean isHtmlRequest = false;

			if (messageId.endsWith(".html")) {
				isHtmlRequest = true;
				messageId = messageId.substring(0, messageId.length() - 5);
			}

			Message message = yaftForumService.getMessage(messageId);
			Forum forum = yaftForumService.getForumContainingMessage(messageId);

			if (parts.length == 2) {
				if (isHtmlRequest) {
					
				    ctx.put("forumId",forum.getId());
				    ctx.put("discussionId",message.getDiscussionId());
				    ctx.put("state","full");
				    ctx.put("messageId",messageId);
				        
				    response.setStatus(HttpServletResponse.SC_OK);
				    response.setContentType("text/html");
				    Writer writer = new BufferedWriter(response.getWriter());
				    try {
						bootstrapTemplate.merge(ctx,writer);
					} catch (Exception e) {
						logger.error("Failed to merge template. Returning 500.",e);
						response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					}
				    writer.close();
					return;
				}

				JsonConfig config = new JsonConfig();
				config.setExcludes(new String[] { "properties", "reference" });

				JSONObject data = JSONObject.fromObject(message, config);
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("application/json");
				response.getWriter().write(data.toString());
				response.getWriter().close();
				return;
			}

			String messageOp = "";

			if (parts.length >= 3)
				messageOp = parts[2];

			if ("markRead".equals(messageOp)) {
				if (yaftForumService.markMessageRead(messageId, forum.getId(), message.getDiscussionId())) {
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("text/plain");
					response.getWriter().write("success");
				} else
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

				response.getWriter().close();

				return;
			} else if ("markUnRead".equals(messageOp)) {
				if (yaftForumService.markMessageUnRead(messageId, forum.getId(), message.getDiscussionId())) {
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("text/plain");
					response.getWriter().write("success");
				} else {
					logger.error("Failed to mark message with id '" + messageId + "' as un-read");
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				}

				response.getWriter().close();

				return;
			} else if ("delete".equals(messageOp)) {
				 try {
	                    yaftForumService.deleteMessage(message, siteId, forum.getId(), userId);
	                    response.setStatus(HttpServletResponse.SC_OK);
	                    response.setContentType("text/plain;");
	                    response.getWriter().write("success");
	                    response.getWriter().close();
	                } catch (YaftSecurityException yse) {
	                    logger.error(yse);
	                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
	                }
				return;
			} else if ("undelete".equals(messageOp)) {
				yaftForumService.undeleteMessage(message, forum.getId());
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("text/plain");
				response.getWriter().write("success");
				response.getWriter().close();
				return;
			} else if ("attachments".equals(messageOp)) {
				if (parts.length >= 4) {
					String attachmentId = parts[3];
					if (parts.length >= 5) {
						String attachmentOp = parts[4];

						if ("delete".equals(attachmentOp)) {
							yaftForumService.deleteAttachment(attachmentId, messageId);

							response.setStatus(HttpServletResponse.SC_OK);
							response.setContentType("text/plain");
							response.getWriter().write("success");
							response.getWriter().close();
							return;
						}
					}
				}
			}

			if (parts.length == 2) {
				JsonConfig config = new JsonConfig();
				config.setExcludes(new String[] { "properties", "reference" });

				JSONObject data = JSONObject.fromObject(message, config);
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("application/json");
				response.getWriter().write(data.toString());
				response.getWriter().close();
				return;
			}
		}
	}

	private void doAssignmentsGet(HttpServletResponse response, String[] parts) throws ServletException, IOException {

		// Only a single case supported at the mo, that of a grading operation.

		if (parts.length != 6) {
			return;
		}

		String assignmentId = parts[1];
		String authorId = parts[3];
		String points = parts[5];

		if (sakaiProxy.scoreAssignment(Integer.parseInt(assignmentId), authorId, points)) {
			response.setStatus(HttpServletResponse.SC_OK);
			return;
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
	}

	private void doUserDataGet(HttpServletResponse response, String siteId) throws ServletException, IOException {
		Set<String> perms = sakaiProxy.getPermissionsForCurrentUserAndSite();

		JSONArray permsData = JSONArray.fromObject(perms);

		JSONObject data = new JSONObject();
		data.accumulate("permissions", permsData);

		if (sakaiProxy.currentUserHasFunction("gradebook.editAssignments")) {
			List<YaftGBAssignment> assignments = sakaiProxy.getGradebookAssignments();
			//JsonConfig config = new JsonConfig();
			//config.setExcludes(new String[] { "dueDate" });
			//JSONArray assignmentsData = JSONArray.fromObject(assignments, config);
			JSONArray assignmentsData = JSONArray.fromObject(assignments);
			data.accumulate("assignments", assignmentsData);
		}

		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json");
		response.getWriter().write(data.toString());
		response.getWriter().close();
		return;
	}

	private void doPermsGet(HttpServletResponse response) throws ServletException, IOException {
		Map<String, Set<String>> perms = sakaiProxy.getPermsForCurrentSite();
		JSONObject data = JSONObject.fromObject(perms);
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json");
		response.getWriter().write(data.toString());
		response.getWriter().close();
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("doPost()");

		String pathInfo = request.getPathInfo();

		String[] parts = new String[] {};

		if (pathInfo != null)
			parts = pathInfo.substring(1).split("/");

		if (parts.length >= 1) {
			String part1 = parts[0];

			if ("forums".equals(part1))
				doForumsPost(request, response, parts);
			else if ("discussions".equals(part1))
				doDiscussionsPost(request, response, parts);
			else if ("messages".equals(part1))
				doMessagesPost(request, response, parts);
			else if ("setPerms.json".equals(part1))
				doPermsPost(request, response);
			else if ("search".equals(part1))
				doSearchPost(request, response);
		}

		String function = request.getParameter("function");

		if (logger.isDebugEnabled())
			logger.debug("function=" + function);

		if (function != null && function.equals("moveDiscussion")) {
			String currentForumId = request.getParameter("currentForumId");
			String newForumId = request.getParameter("forumId");
			String discussionId = request.getParameter("discussionId");

			if (logger.isDebugEnabled()) {
				logger.debug("Current Forum ID: " + currentForumId);
				logger.debug("Forum ID: " + newForumId);
				logger.debug("Discussion ID: " + discussionId);
			}

			yaftForumService.moveDiscussion(discussionId, currentForumId, newForumId);
			response.sendRedirect("/portal/tool/" + (String) request.getAttribute(Tool.PLACEMENT_ID) + "/forums/" + currentForumId);
			return;
		}
	}

	private void doForumsPost(HttpServletRequest request, HttpServletResponse response, String[] parts) throws ServletException, IOException {
		String siteId = sakaiProxy.getCurrentSiteId();
		String state = request.getParameter("state");

		if (state == null)
			state = "";

		if (parts.length == 1) {
			try {
				String id = (String) request.getParameter("id");
				String title = (String) request.getParameter("title");
				String sendEmailString = (String) request.getParameter("sendEmail");
				String groupsString = (String) request.getParameter("groups");
				String description = (String) request.getParameter("description");
				String startDate = (String) request.getParameter("startDate");
				String endDate = (String) request.getParameter("endDate");
				String lockWritingString = (String) request.getParameter("lockWriting");
				String lockReadingString = (String) request.getParameter("lockReading");

				if (logger.isDebugEnabled()) {
					logger.debug("Title: " + title);
					logger.debug("Description: " + description);
				}
				if (title == null || title.length() == 0) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "A title of at least 1 character must be supplied");
					return;
				}

				if (description != null && description.length() > 255) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "The description cannot exceed 255 characters in length.");
					return;
				}

				boolean sendEmail = true;

				if (sendEmailString != null)
					sendEmail = sendEmailString.equals("true");
				else
					sendEmail = false;

				boolean lockWriting = true;
				boolean lockReading = true;

				if (lockWritingString != null)
					lockWriting = lockWritingString.equals("true");
				else
					lockWriting = false;

				if (lockReadingString != null)
					lockReading = lockReadingString.equals("true");
				else
					lockReading = false;

				Forum forum = new Forum();

				if (id != null)
					forum.setId(id);

				if (groupsString != null && !"".equals(groupsString)) {
					String[] groups = groupsString.split(",");
					List<Group> list = new ArrayList<Group>();
					for (String groupId : groups)
						list.add(new Group(groupId, ""));

					forum.setGroups(list);
				}

				forum.setTitle(title);
				forum.setDescription(description);
				forum.setSiteId(siteId);
				forum.setCreatorId(sakaiProxy.getUserIdFromSession());
				forum.setLockedForWriting(lockWriting);
				forum.setLockedForReading(lockReading);

				if (startDate != null && startDate.length() > 0 && endDate != null && endDate.length() > 0) {
					try {
						long start = Long.parseLong(startDate);
						long end = Long.parseLong(endDate);
						if (start > 0L && end > 0L) {
							if (end <= start) {
								throw new IllegalArgumentException("The end date MUST come after the start date.");
							} else {
								forum.setStart(start);
								forum.setEnd(end);
							}
						}
					} catch (NumberFormatException pe) {
						throw new IllegalArgumentException("The start and end dates MUST be supplied in millisecond format.");
					}
				}

				if (yaftForumService.addOrUpdateForum(forum, sakaiProxy.getUserIdFromSession(), sendEmail)) {
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("text/plain");
					response.getWriter().write(forum.getId());
					response.getWriter().close();
					return;
				} else {
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					return;
				}
			} catch (Exception e) {
				logger.error("Caught exception whilst creating forum.", e);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
		} else if(parts.length >= 2) {
			String op = parts[1];
			
			if("delete".equals(op)) {
				String[] forumIds = request.getParameterValues("forumIds[]");
				
				for(String forumId : forumIds) {
                    try {
					    yaftForumService.deleteForum(forumId, siteId, sakaiProxy.getUserIdFromSession());
                    } catch (YaftSecurityException yse) {}
				}
				
				response.setStatus(HttpServletResponse.SC_OK);
				return;
			}
		}
	}

	private void doDiscussionsPost(HttpServletRequest request, HttpServletResponse response, String[] parts) throws ServletException, IOException {
		String siteId = sakaiProxy.getCurrentSiteId();

		if (parts.length == 1) {
			String id = (String) request.getParameter("id");
			String subject = (String) request.getParameter("subject");
			String content = (String) request.getParameter("content");
			String forumId = (String) request.getParameter("forumId");
			String sendEmailString = (String) request.getParameter("sendEmail");
			String startDate = (String) request.getParameter("startDate");
			String endDate = (String) request.getParameter("endDate");
			String lockWritingString = (String) request.getParameter("lockWriting");
			String lockReadingString = (String) request.getParameter("lockReading");

			String grade = (String) request.getParameter("grade");
			
			String[] groups = request.getParameterValues("groups");

			if (subject == null || subject.length() <= 0 || content == null || content.length() <= 0 || forumId == null || forumId.length() <= 0) {
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("text/html");
				response.getWriter().write("ERROR:A forum id,subject and content must be supplied.");
				response.getWriter().close();
				return;
			}

			boolean sendEmail = true;

			if (sendEmailString != null)
				sendEmail = sendEmailString.equals("true");
			else
				sendEmail = false;

			boolean lockWriting = true;
			boolean lockReading = true;

			if (lockWritingString != null)
				lockWriting = lockWritingString.equals("true");
			else
				lockWriting = false;

			if (lockReadingString != null)
				lockReading = lockReadingString.equals("true");
			else
				lockReading = false;

			if (logger.isDebugEnabled()) {
				logger.debug("Subject: " + subject);
				logger.debug("Content: " + content);
				logger.debug("Forum ID: " + forumId);
			}

			Message message = new Message();

			if (id != null)
				message.setId(id);

			String currentUserId = sakaiProxy.getUserIdFromSession();

			message.setSubject(subject);
			message.setContent(content);
			message.setSiteId(siteId);
			message.setCreatorId(currentUserId);
			message.setCreatorDisplayName(sakaiProxy.getDisplayNameForUser(currentUserId));
			message.setAttachments(getAttachments(request));

			// The first messages in discussions always have the same id as the
			// discussion
			message.setDiscussionId(message.getId());

			message.setStatus("READY");

			Discussion discussion = new Discussion();
			discussion.setFirstMessage(message);
			discussion.setLockedForWriting(lockWriting);
			discussion.setLockedForReading(lockReading);
			
			List<Group> groupsList = new ArrayList<Group>();
			
			if(groups != null && groups.length > 0) {
				for (String groupId : groups)
					groupsList.add(new Group(groupId, ""));
			}
			
			discussion.setGroups(groupsList);

			if (grade != null && "true".equals(grade)) {
				String gradebookAssignmentIdString = (String) request.getParameter("assignmentId");
				try {
					int gradebookAssignmentId = Integer.parseInt(gradebookAssignmentIdString);
					discussion.setAssignment(sakaiProxy.getGradebookAssignment(gradebookAssignmentId));
				} catch (Exception e) {
					logger.error("Failed to set discussion assignment", e);
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					return;
				}
			}

			if (startDate != null && startDate.length() > 0 && endDate != null && endDate.length() > 0) {
				try {
					long start = Long.parseLong(startDate);
					long end = Long.parseLong(endDate);
					if (start > 0L && end > 0L) {
						if (end <= start) {
							response.setStatus(HttpServletResponse.SC_OK);
							response.setContentType("text/html");
							response.getWriter().write("ERROR:The end date MUST come after the start date.");
							response.getWriter().close();
							return;
						} else {
							discussion.setStart(start);
							discussion.setEnd(end);
						}
					}
				} catch (NumberFormatException pe) {
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("text/html");
					response.getWriter().write("ERROR:The start and end dates MUST be supplied in millisecond format.");
					response.getWriter().close();
					return;
				}
			}
	          try {
	                if (yaftForumService.addDiscussion(siteId, forumId, sakaiProxy.getUserIdFromSession(), discussion, sendEmail) != null) {
	                    response.setStatus(HttpServletResponse.SC_OK);
	                    response.setContentType("text/html");
	                    response.getWriter().write(discussion.getId());
	                    response.getWriter().close();
	                    return;
	                } else {
	                    response.setStatus(HttpServletResponse.SC_OK);
	                    response.setContentType("text/html");
	                    response.getWriter().write("ERROR");
	                    response.getWriter().close();
	                    return;
	                }
	            } catch (YaftSecurityException yse) {
	                response.sendError(HttpServletResponse.SC_FORBIDDEN);
	            }
		} else if(parts.length >= 2) {
			String op = parts[1];
			
			if("delete".equals(op)) {
				String[] discussionIds = request.getParameterValues("discussionIds[]");
				
				for (String discussionId : discussionIds) {
                    try {
					    yaftForumService.deleteDiscussion(discussionId, siteId, sakaiProxy.getUserIdFromSession());
                    } catch (YaftSecurityException yse) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                        logger.error(yse);
                    }
				}
				
				response.setStatus(HttpServletResponse.SC_OK);
				return;
			}
		}
	}

	private void doMessagesPost(HttpServletRequest request, HttpServletResponse response, String[] parts) throws ServletException, IOException {
		String siteId = sakaiProxy.getCurrentSiteId();

		if (parts.length == 1) {
			String status = (String) request.getParameter("status");
			String subject = (String) request.getParameter("subject");
			String content = (String) request.getParameter("content");
			String forumId = (String) request.getParameter("forumId");
			String viewMode = (String) request.getParameter("viewMode");
			String messageId = (String) request.getParameter("messageId");
			String messageBeingRepliedTo = (String) request.getParameter("messageBeingRepliedTo");
			String discussionId = (String) request.getParameter("discussionId");
			String sendEmailString = (String) request.getParameter("sendEmail");

			if (logger.isDebugEnabled()) {
				logger.debug("Status: " + status);
				logger.debug("Subject: " + subject);
				logger.debug("Content: " + content);
				logger.debug("Forum ID: " + forumId);
				logger.debug("View Mode: " + viewMode);
				logger.debug("Discussion ID: " + discussionId);
				logger.debug("Message Being Replied To: " + messageBeingRepliedTo);
			}
			
			try{				
			if (subject == null || subject.length() <= 0 || content == null || content.length() <= 0) {
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("text/html");
				response.getWriter().write("ERROR: subject and content must be supplied.");
				response.getWriter().close();
				return;
			}
			}catch(NullPointerException ne){
				logger.error("Yaft: Message subject or content became null ",ne);
			}
			
			/*if (subject == null || subject.length() <= 0){
				throw new IllegalArgumentException("You must supply a subject.");
			}

			if (content == null || content.length() <= 0)
				throw new IllegalArgumentException("You must supply some content.");*/

			if (viewMode == null || viewMode.length() <= 0)
				viewMode = "full";

			boolean sendEmail = true;

			if (sendEmailString != null)
				sendEmail = sendEmailString.equals("true");
			else
				sendEmail = false;		
			String currentUserId = sakaiProxy.getUserIdFromSession();

			Message message = new Message();
			message.setStatus(status);
			message.setSubject(subject);
			message.setContent(content);
			message.setSiteId(siteId);
			message.setCreatorId(currentUserId);
			message.setCreatorDisplayName(sakaiProxy.getDisplayNameForUser(currentUserId));
			message.setDiscussionId(discussionId);
			message.setAttachments(getAttachments(request));

			// If no message id has been supplied this must be a new message, so
			// we set the message id to empty
			if (messageId == null)
				message.setId("");

			if (messageBeingRepliedTo != null && messageBeingRepliedTo.length() > 0)
				message.setParent(messageBeingRepliedTo);
			else if (messageBeingRepliedTo == null || messageBeingRepliedTo.length() <= 0) {
				// This is a discussion, or top level message
				message.setId(messageId);
			}

			  try {
	                if (yaftForumService.addOrUpdateMessage(siteId, forumId, sakaiProxy.getUserIdFromSession(), message, sendEmail)) {
	                    response.setStatus(HttpServletResponse.SC_OK);
	                    response.setContentType("text/plain");
	                    response.getWriter().write("success");
	                    response.getWriter().close();
	                    return;
	                } else {
	                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	                    return;
	                }
	            } catch (YaftSecurityException yse) {
	                logger.error(yse);
	            }
			}
	}

	private void doPermsPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (sakaiProxy.setPermsForCurrentSite(request.getParameterMap())) {
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("text/plain");
			response.getWriter().write("success");
			response.getWriter().close();
			return;
		} else {
			//response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
	}

	private void doSearchPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchTerms = request.getParameter("searchTerms");

		if (searchTerms == null || searchTerms.length() == 0)
			throw new ServletException("No search terms supplied.");

		List<SearchResult> results = sakaiProxy.searchInCurrentSite(searchTerms);

		JSONArray data = JSONArray.fromObject(results);
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json");
		response.getWriter().write(data.toString());
		response.getWriter().close();
		return;
	}

	private List<Attachment> getAttachments(HttpServletRequest request) {
		List<FileItem> fileItems = new ArrayList<FileItem>();

		String uploadsDone = (String) request.getAttribute(RequestFilter.ATTR_UPLOADS_DONE);

		if (uploadsDone != null && uploadsDone.equals(RequestFilter.ATTR_UPLOADS_DONE)) {
			logger.debug("UPLOAD STATUS: " + request.getAttribute("upload.status"));

			try {
				FileItem attachment1 = (FileItem) request.getAttribute("attachment_0");
				if (attachment1 != null && attachment1.getSize() > 0)
					fileItems.add(attachment1);
				FileItem attachment2 = (FileItem) request.getAttribute("attachment_1");
				if (attachment2 != null && attachment2.getSize() > 0)
					fileItems.add(attachment2);
				FileItem attachment3 = (FileItem) request.getAttribute("attachment_2");
				if (attachment3 != null && attachment3.getSize() > 0)
					fileItems.add(attachment3);
				FileItem attachment4 = (FileItem) request.getAttribute("attachment_3");
				if (attachment4 != null && attachment4.getSize() > 0)
					fileItems.add(attachment4);
				FileItem attachment5 = (FileItem) request.getAttribute("attachment_4");
				if (attachment5 != null && attachment5.getSize() > 0)
					fileItems.add(attachment5);
			} catch (Exception e) {

			}
		}

		List<Attachment> attachments = new ArrayList<Attachment>();
		if (fileItems.size() > 0) {
			for (Iterator i = fileItems.iterator(); i.hasNext();) {
				FileItem fileItem = (FileItem) i.next();

				String name = fileItem.getName();

				if (name.contains("/"))
					name = name.substring(name.lastIndexOf("/") + 1);
				else if (name.contains("\\"))
					name = name.substring(name.lastIndexOf("\\") + 1);

				attachments.add(new Attachment(name, fileItem.getContentType(), fileItem.get()));
			}
		}

		return attachments;
	}

	/**
	 * Sets up the YaftForumService and SakaiProxy instances
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		if (logger.isDebugEnabled())
			logger.debug("init");

		try {
			ComponentManager componentManager = org.sakaiproject.component.cover.ComponentManager.getInstance();
			yaftForumService = (YaftForumService) componentManager.get(YaftForumService.class);
			sakaiProxy = yaftForumService.getSakaiProxy();
			VelocityEngine ve = new VelocityEngine();
            Properties props = new Properties();
            
           //unisa changes - to not to create velocity log file on server
            props.setProperty("file.resource.loader.path",config.getServletContext().getRealPath("/WEB-INF"));
            props.setProperty("runtime.log.logsystem.log4j.logger","org.apache.velocity");
            props.setProperty("runtime.log.logsystem.class","org.apache.velocity.runtime.log.Log4JLogChute");
            props.setProperty(" runtime.log.logsystem.log4j.category","org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
            ve.init(props);
            bootstrapTemplate = ve.getTemplate("bootstrap.vm");
		} catch (Throwable t) {
			throw new ServletException("Failed to initialise YaftTool servlet.", t);
		}
	}
}
