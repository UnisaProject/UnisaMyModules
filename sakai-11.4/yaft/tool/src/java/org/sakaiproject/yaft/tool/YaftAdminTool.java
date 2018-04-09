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

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.sakaiproject.component.api.ComponentManager;
import org.sakaiproject.db.cover.SqlService;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.ToolConfiguration;
import org.sakaiproject.yaft.api.Attachment;
import org.sakaiproject.yaft.api.Forum;
import org.sakaiproject.yaft.api.SakaiProxy;
import org.sakaiproject.yaft.api.YaftForumService;

public class YaftAdminTool extends HttpServlet
{
	private Logger logger = Logger.getLogger(YaftAdminTool.class);
	
	private YaftForumService yaftForumService = null;

	private SakaiProxy sakaiProxy;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String pathInfo = request.getPathInfo();
		logger.debug("Path Info: " + pathInfo);
		if(pathInfo != null && pathInfo.length() > 1)
		{
			String[] parts = pathInfo.substring(1).split("/");
		
			if(parts.length >= 1)
			{
				String data = parts[0];
				if(logger.isDebugEnabled()) logger.debug("data=" + data);
				
				if(data.equals("sites"))
				{
					List<Site> sites = sakaiProxy.getAllSites();
					JSONArray array = new JSONArray();
					for(Site site : sites)
					{
						JSONObject json = new JSONObject();
						json.put("title",site.getTitle());
						json.put("id",site.getId());
						array.add(json);
					}
					response.setStatus(HttpServletResponse.SC_OK);
					array.write(response.getWriter());
					response.getWriter().close();
					return;
				}
			}
		}
		else
			response.sendRedirect("/yaft-tool/admin.html?placementId=" + sakaiProxy.getCurrentToolId());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		logger.info("doPost()");
		
		String function = request.getParameter("function");
		
		if (logger.isDebugEnabled()) logger.debug("function=" + function);
		if (logger.isDebugEnabled()) logger.debug("Current SITE ID: " + sakaiProxy.getCurrentSiteId());
		
		if(function.equals("migrate"))
		{
			Connection conn = null;
			Statement sourceForumST = null;
			Statement sourceTopicST = null;
			Statement sourceDiscussionST = null;
			Statement targetST = null;
			try
			{
				conn = SqlService.borrowConnection();
				sourceForumST = conn.createStatement();
				sourceTopicST = conn.createStatement();
				sourceDiscussionST = conn.createStatement();
				targetST = conn.createStatement();
			
				String[] siteIds = request.getParameterValues("sites");
			
				for(String siteId : siteIds)
				{
					logger.debug("Site: " + siteId);
					ToolConfiguration tc = sakaiProxy.getFirstInstanceOfTool(siteId,"sakai.yaft");
					
					// Get the forums
					ResultSet forumRS = sourceForumST.executeQuery("SELECT * FROM MFR_OPEN_FORUM_T WHERE surrogateKey IN (SELECT ID FROM MFR_AREA_T WHERE CONTEXT_ID = '" + siteId + "')");
					while(forumRS.next())
					{
						Forum yaftForum = new Forum();
						yaftForum.setSiteId(siteId);
						yaftForum.setTitle(forumRS.getString("TITLE"));
						yaftForum.setDescription(forumRS.getString("SHORT_DESCRIPTION"));
						//yaftForum.setPlacementId(tc.getId());
						yaftForumService.addOrUpdateForum(yaftForum);
						ResultSet topicRS = sourceTopicST.executeQuery("SELECT * FROM MFR_TOPIC_T WHERE of_surrogateKey = " + forumRS.getInt("ID"));
						while(topicRS.next())
						{
							// Get the threads for this forum. Not interested in the topics.
							ResultSet discussionRS = sourceDiscussionST.executeQuery("SELECT * FROM MFR_MESSAGE_T WHERE surrogateKey = " + topicRS.getInt("ID") + " AND IN_REPLY_TO IS NULL");
							
							while(discussionRS.next())
							{
								org.sakaiproject.yaft.api.Message firstMessage
									= new org.sakaiproject.yaft.api.Message();
								firstMessage.setSubject(discussionRS.getString("TITLE"));
								firstMessage.setContent(discussionRS.getString("BODY"));
								//yaftDiscussion.setPlacementId(tc.getId());
								firstMessage.setCreatorId(discussionRS.getString("CREATED_BY"));
								firstMessage.setCreatedDate(discussionRS.getTimestamp("CREATED").getTime());
								org.sakaiproject.yaft.api.Discussion discussion = new org.sakaiproject.yaft.api.Discussion();
								discussion.setFirstMessage(firstMessage);
								yaftForumService.addDiscussion(sakaiProxy.getCurrentSiteId(), yaftForum.getId(), sakaiProxy.getUserIdFromSession(), discussion, false);
								addChildMessages(conn,yaftForum.getId(),firstMessage.getId(),tc.getId(),firstMessage.getId(),discussionRS.getInt("ID"));
							}
						}
						
						topicRS.close();
					}
					
					forumRS.close();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if(sourceTopicST != null) sourceTopicST.close();
					if(sourceForumST != null) sourceForumST.close();
					if(targetST != null) targetST.close();
					if(conn != null) conn.close();
				}
				catch(Exception e) {}
			}
				
		}
		
		response.sendRedirect("/yaft-tool/admin.html?placementId=" + sakaiProxy.getCurrentToolId());
		return;
	}

	private void addChildMessages(Connection conn, String forumId,String discussionId,String placementId, String parentId, int inReplyToId)
	{
		Statement st = null;
		ResultSet rs = null;
		Statement attachmentST = null;
		
		try
		{
			st = conn.createStatement();
			attachmentST = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM MFR_MESSAGE_T WHERE IN_REPLY_TO = " + inReplyToId);
		
			while(rs.next())
			{
				org.sakaiproject.yaft.api.Message yaftMessage
					= new org.sakaiproject.yaft.api.Message();
				yaftMessage.setSubject(rs.getString("TITLE"));
				yaftMessage.setContent(rs.getString("BODY"));
				//yaftMessage.setPlacementId(placementId);
				yaftMessage.setCreatorId(rs.getString("CREATED_BY"));
				yaftMessage.setCreatedDate(rs.getTimestamp("CREATED").getTime());
				yaftMessage.setParent(parentId);
				yaftMessage.setDiscussionId(discussionId);
				ResultSet attachmentRS = attachmentST.executeQuery("SELECT * FROM MFR_ATTACHMENT_T where m_surrogateKey = " + rs.getInt("ID"));
				
				List<Attachment> attachments = new ArrayList<Attachment>();
				
				while(attachmentRS.next())
				{
					Attachment attachment = new Attachment();
					attachment.setResourceId(attachmentRS.getString("ATTACHMENT_ID"));
					attachments.add(attachment);
				}
				
				attachmentRS.close();
				
				yaftMessage.setAttachments(attachments);
				
				yaftForumService.addOrUpdateMessage(null, forumId, sakaiProxy.getUserIdFromSession(), yaftMessage, false);
				
				addChildMessages(conn, forumId, discussionId,placementId, yaftMessage.getId(), rs.getInt("ID"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs != null) rs.close();
				if(attachmentST != null) attachmentST.close();
				if(st != null) st.close();
			}
			catch(Exception e) {}
		}
	}

	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		
		if(logger.isDebugEnabled()) logger.debug("init");
		
		try
		{
			ComponentManager componentManager = org.sakaiproject.component.cover.ComponentManager.getInstance();
			yaftForumService = (YaftForumService) componentManager.get(YaftForumService.class);
			sakaiProxy = yaftForumService.getSakaiProxy();
		}
		catch(Exception e)
		{
			throw new ServletException("Failed to initialise YaftAdminTool servlet.", e);
		}
	}
}



