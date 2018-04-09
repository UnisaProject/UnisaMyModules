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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.sakaiproject.service.gradebook.shared.GradeDefinition;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.ToolConfiguration;
import org.sakaiproject.yaft.api.ActiveDiscussion;
import org.sakaiproject.yaft.api.Attachment;
import org.sakaiproject.yaft.api.Discussion;
import org.sakaiproject.yaft.api.Forum;
import org.sakaiproject.yaft.api.ForumPopulatedStates;
import org.sakaiproject.yaft.api.Group;
import org.sakaiproject.yaft.api.Message;
import org.sakaiproject.yaft.api.Author;
import org.sakaiproject.yaft.api.SakaiProxy;
import org.sakaiproject.yaft.api.YaftGBAssignment;
import org.sakaiproject.yaft.impl.sql.ColumnNames;
import org.sakaiproject.yaft.impl.sql.DefaultSqlGenerator;
import org.sakaiproject.yaft.impl.sql.HypersonicGenerator;
import org.sakaiproject.yaft.impl.sql.OracleGenerator;
import org.sakaiproject.yaft.impl.sql.SqlGenerator;

public class YaftPersistenceManager
{
	private Logger logger = Logger.getLogger(YaftPersistenceManager.class);
	
	private SakaiProxy sakaiProxy = null;
	public void setSakaiProxy(SakaiProxy sakaiProxy) {
		this.sakaiProxy = sakaiProxy;
	}
	
	private SqlGenerator sqlGenerator = null;
	
	private YaftSecurityManager securityManager = null;
	public void setSecurityManager(YaftSecurityManager sm) {
		this.securityManager = sm;
	}

	private int activeDiscussionLimit = 5;

	public YaftPersistenceManager() {
	}
	
	void init()
	{
		if(logger.isDebugEnabled()) logger.debug("init()");
		
		String dbVendor = sakaiProxy.getDbVendor();
		if(dbVendor.equals("mysql"))
			sqlGenerator = new DefaultSqlGenerator();
		else if(dbVendor.equals("oracle"))
			sqlGenerator = new OracleGenerator();
		else if(dbVendor.equals("hsqldb"))
			sqlGenerator = new HypersonicGenerator();
		else
		{
			logger.warn("'" + dbVendor + "' not directly supported. Defaulting to DefaultSqlGenerator.");
			sqlGenerator = new DefaultSqlGenerator();
		}
		
		try {
			activeDiscussionLimit = Integer.parseInt(sakaiProxy.getString("yaft.activeDiscussionLimit","5"));
		} catch(NumberFormatException nfe) {
			logger.warn("yaft.activeDiscussionLimit needs to be a number. Defaulting to " + activeDiscussionLimit);
		}
	}
	
	void setupTables() {
		if(logger.isDebugEnabled()) logger.debug("setupTables()");
		
		if(!sakaiProxy.isAutoDDL())
		{
			if(logger.isDebugEnabled())
				logger.debug("auto.ddl is set to false. Returning ...");
			
			return;
		}

		Connection connection = null;
		Statement statement = null;

		try
		{
			connection = sakaiProxy.borrowConnection();
			boolean oldAutoCommitFlag = connection.getAutoCommit();
			connection.setAutoCommit(false);

			try
			{
				List<String> statements = sqlGenerator.getSetupStatements();

				statement = connection.createStatement();

				for (String sql : statements)
					statement.executeUpdate(sql);

				connection.commit();
			}
			catch (SQLException sqle)
			{
				logger.error("Caught exception whilst setting up tables. Rolling back ...");
				connection.rollback();
			}
			finally
			{
				connection.setAutoCommit(oldAutoCommitFlag);
			}
		}
		catch (Exception e)
		{
			logger.error("Caught exception whilst setting up tables");
		}
		finally
		{
			try
			{
				if(statement != null) statement.close();
			}
			catch(Exception e) {}
			
			sakaiProxy.returnConnection(connection);
		}
	}

	//This methos is for site import function to pass the SiteId
	
	Forum getForum(String forumId,String siteId,String siteImport,String state)
	{
		Connection connection = null;

		try
		{
			connection = sakaiProxy.borrowConnection();
			return getForum(forumId,siteId,state,connection);
		}
		catch (Exception e)
		{
			logger.error("Caught exception whilst getting forum.", e);
			return null;
		}
		finally
		{
			sakaiProxy.returnConnection(connection);
		}
	}
	
	//This methos is for site import function to pass the SiteId
	private Forum getForum(String forumId,String siteId,String state, Connection connection) throws Exception
	{
		if(logger.isDebugEnabled()) logger.debug("getForum()");
		
		Forum forum = null;
		
		String sql = sqlGenerator.getForumSelectStatement(forumId);
		Statement st = null;
		ResultSet rs = null;
		
		try
		{
			st = connection.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next())
				forum = getForumFromResults(rs,connection);
			
			if(ForumPopulatedStates.FULL.equals(state))
				forum.setDiscussions(getForumDiscussions(forumId,siteId,true,connection));
			else if(ForumPopulatedStates.PART.equals(state))
				forum.setDiscussions(getForumDiscussions(forumId,siteId,false,connection));
		}
		finally
		{
			try
			{
				if(rs != null) rs.close();
			}
			catch(Exception e) {}
			
			try
			{
				if(st != null) st.close();
			}
			catch(Exception e) {}
		}
		
		return forum;
	}
	
	
	Forum getForum(String forumId,String state)
	{
		Connection connection = null;

		try
		{
			connection = sakaiProxy.borrowConnection();
			return getForum(forumId,state,connection);
		}
		catch (Exception e)
		{
			logger.error("Caught exception whilst getting forum.", e);
			return null;
		}
		finally
		{
			sakaiProxy.returnConnection(connection);
		}
	}
	
	private Forum getForum(String forumId,String state, Connection connection) throws Exception
	{
		if(logger.isDebugEnabled()) logger.debug("getForum()");
		
		Forum forum = null;
		
		String sql = sqlGenerator.getForumSelectStatement(forumId);
		Statement st = null;
		ResultSet rs = null;
		
		try
		{
			st = connection.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next())
				forum = getForumFromResults(rs,connection);
			
			if(ForumPopulatedStates.FULL.equals(state))
				forum.setDiscussions(getForumDiscussions(forumId,true,connection));
			else if(ForumPopulatedStates.PART.equals(state))
				forum.setDiscussions(getForumDiscussions(forumId,false,connection));
		}
		finally
		{
			try
			{
				if(rs != null) rs.close();
			}
			catch(Exception e) {}
			
			try
			{
				if(st != null) st.close();
			}
			catch(Exception e) {}
		}
		
		return forum;
	}

	List<Forum> getFora(String siteId,boolean fully)
	{
		if(logger.isDebugEnabled()) logger.debug("getFora()");
		
		List<Forum> fora = new ArrayList<Forum>();
		
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;

		try
		{
			connection = sakaiProxy.borrowConnection();
			String sql = sqlGenerator.getForaSelectStatement(siteId);
			st = connection.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next())
			{
				Forum forum = getForumFromResults(rs,connection);
				
				// We do not want to return deleted fora
				if("DELETED".equals(forum.getStatus())) continue;
				
				if(fully)
				{
					// Add the discussions
					forum.setDiscussions(getForumDiscussions(forum.getId(), true,connection));
				}
				fora.add(forum);
			}
		}
		catch (Exception e)
		{
			logger.error("Caught exception whilst getting fora.", e);
		}
		finally
		{
			try
			{
				if(rs != null) rs.close();
			}
			catch(Exception e) {}
			
			if(st != null)
			{
				try
				{
					st.close();
				}
				catch(Exception e) {}
			}
			
			sakaiProxy.returnConnection(connection);
		}
		
		return securityManager.filterFora(fora,siteId);
	}

	boolean addOrUpdateForum(Forum forum)
	{
		if(logger.isDebugEnabled()) logger.debug("addOrUpdateForum()");
		
		Connection connection = null;
		List<PreparedStatement> statements = null;

		try
		{
			boolean oldAutoCommitFlag = false;
			
			connection = sakaiProxy.borrowConnection();
			oldAutoCommitFlag = connection.getAutoCommit();
			connection.setAutoCommit(false);

			statements = sqlGenerator.getAddOrUpdateForumStatements(forum,connection);
			
			try
			{
				for(PreparedStatement statement : statements)
					statement.executeUpdate();
				
				connection.commit();
			
				return true;
			}
			catch(Exception e)
			{
				logger.error("Caught exception whilst adding or updating a forum. Rolling back ...",e);
				connection.rollback();
				return false;
			}
			finally
			{
				connection.setAutoCommit(oldAutoCommitFlag);
			}
		}
		catch (Exception e)
		{
			logger.error("Caught exception whilst adding or updating forum", e);
			return false;
		}
		finally
		{
			if(statements != null)
			{
				for(PreparedStatement st : statements)
				{
					try
					{
						st.close();
					}
					catch (Exception e) {}
				}
			}
			
			sakaiProxy.returnConnection(connection);
		}
	}

	boolean addOrUpdateMessage(String siteId, String forumId,Message message,Connection connection) {
		if(logger.isDebugEnabled()) logger.debug("addOrUpdateMessage()");
		
		boolean isLocalConnection = false;
		
		// If a connection was passed in we don't want to release it here
		if(connection == null)
			isLocalConnection = true;
			
		List<PreparedStatement> messageStatements = null;
		List<PreparedStatement> newStatements = null;

		try {
			boolean oldAutoCommitFlag = false;
			
			if(isLocalConnection) {
				connection = sakaiProxy.borrowConnection();
				oldAutoCommitFlag = connection.getAutoCommit();
				connection.setAutoCommit(false);
			}

			try {
				// Save any attachments in content hosting
				for(Attachment attachment : message.getAttachments()) {
					if(attachment.getResourceId().length() == 0) {
						String resourceId = sakaiProxy.saveFile(siteId, message.getCreatorId(),attachment.getName(),attachment.getMimeType(),attachment.getData());
						attachment.setResourceId(resourceId);
					}
				}
				
				messageStatements = sqlGenerator.getAddOrUpdateMessageStatements(forumId,message,connection);
				
				for(PreparedStatement statement : messageStatements) {
					statement.executeUpdate();
				}
			
				if(!"DRAFT".equals(message.getStatus())) {
					markMessageRead(message.getId(), forumId, message.getDiscussionId(),connection);
				
					newStatements = sqlGenerator.getAddNewMessageToActiveDiscussionsStatements(message,connection);
			
					for(PreparedStatement statement : newStatements)
						statement.executeUpdate();
				}
				
				if(isLocalConnection)
					connection.commit();
				
				return true;
			}
			catch(Exception e) {
				if(isLocalConnection) {
					logger.error("Caught exception whilst adding or updating a message. Rolling back ...",e);
					connection.rollback();
				}
				
				return false;
			}
			finally {
				if(isLocalConnection)
					connection.setAutoCommit(oldAutoCommitFlag);
			}
		}
		catch (Exception e) {
			logger.error("Caught exception whilst adding or updating message", e);
			return false;
		}
		finally {
			if(messageStatements != null) {
				for(PreparedStatement st : messageStatements) {
					try {
						st.close();
					}
					catch (Exception e) {}
				}
			}
			
			if(newStatements != null) {
				for(PreparedStatement st : newStatements) {
					try {
						st.close();
					}
					catch (Exception e) {}
				}
			}
				
			if(isLocalConnection)
				sakaiProxy.returnConnection(connection);
		}
	}

	List<Forum> getFora(boolean fully)
	{
		if(logger.isDebugEnabled()) logger.debug("getFora()");
		
		List<Forum> fora = new ArrayList<Forum>();
		
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;

		try
		{
			connection = sakaiProxy.borrowConnection();
			String sql = sqlGenerator.getForaSelectStatement();
			st = connection.createStatement();
			rs = st.executeQuery(sql);
			
			while(rs.next())
			{
				Forum forum = getForumFromResults(rs,connection);
				
				if(fully) {
					forum.setDiscussions(getForumDiscussions(forum.getId(), fully,connection));
				}
				
				// Only add this forum to the list if is has not been deleted
				if(!"DELETED".equals(forum.getStatus())) fora.add(forum);
			}
		}
		catch (Exception e)
		{
			logger.error("Caught exception whilst deleting comment.", e);
		}
		finally
		{
			try
			{
				if(rs != null) rs.close();
			}
			catch(Exception e) {}
			
			if(st != null)
			{
				try
				{
					st.close();
				}
				catch (Exception e) {}
			}
			
			sakaiProxy.returnConnection(connection);
		}
		
		return securityManager.filterFora(fora,null);
	}

	private void getMessageChildren(Message message,List<Group> groups, Connection connection) throws Exception
	{
		Statement statement = null;
		ResultSet rs = null;
		
		try
		{
			statement = connection.createStatement();
			String sql = sqlGenerator.getMessageChildrenSelectStatement(message.getId());
	
			rs = statement.executeQuery(sql);
			while(rs.next())
			{
				Message child = getMessageFromResults(rs,connection);
				child.setGroups(groups);
				getMessageChildren(child,groups,connection);
				message.addChild(child);
			}
		}
		finally
		{
			try
			{
				if(rs != null) rs.close();
			}
			catch(Exception e) {}
			
			if(statement != null)
			{
				try
				{
					statement.close();
				}
				catch(Exception e) {}
			}
		}
	}

	
	//method for siteimport to pass siteId 
	private List<Discussion> getForumDiscussions(String forumId,String siteId,boolean fully,Connection connection) throws Exception
	{
		List<Discussion> discussions = new ArrayList<Discussion>();

		Statement st = null;
		ResultSet rs = null;
		
		try
		{
			String sql = sqlGenerator.getDiscussionsSelectStatement(forumId);
			st = connection.createStatement();
			rs = st.executeQuery(sql);
			
			while(rs.next())
			{
				Discussion discussion = getDiscussionFromResults(rs,fully,connection);
			
				if(!"DELETED".equals(discussion.getStatus()))
					discussions.add(discussion);
			}
		
			return securityManager.filterDiscussions(discussions,siteId);
		}
		finally
		{
			try
			{
				if(rs != null) rs.close();
			}
			catch(Exception e) {}
			
			if(st != null)
			{
				try
				{
					st.close();
				}
				catch(Exception e) {}
			}
		}
	}
	
	
	private List<Discussion> getForumDiscussions(String forumId,boolean fully,Connection connection) throws Exception
	{
		List<Discussion> discussions = new ArrayList<Discussion>();

		Statement st = null;
		ResultSet rs = null;
		
		try
		{
			String sql = sqlGenerator.getDiscussionsSelectStatement(forumId);
			st = connection.createStatement();
			rs = st.executeQuery(sql);
			
			while(rs.next())
			{
				Discussion discussion = getDiscussionFromResults(rs,fully,connection);
			
				if(!"DELETED".equals(discussion.getStatus()))
					discussions.add(discussion);
			}
		
			return securityManager.filterDiscussions(discussions);
		}
		finally
		{
			try
			{
				if(rs != null) rs.close();
			}
			catch(Exception e) {}
			
			if(st != null)
			{
				try
				{
					st.close();
				}
				catch(Exception e) {}
			}
		}
	}
	
	private Discussion getDiscussionFromResults(ResultSet rs,boolean fully,Connection connection) throws Exception
	{
		String discussionId = rs.getString(ColumnNames.DISCUSSION_ID);
		String forumId = rs.getString(ColumnNames.FORUM_ID);
		
		Statement st = null;
		try
		{
			// Get the first message. It has the same id as the discussion.
			st = connection.createStatement();
			String sql = sqlGenerator.getMessageSelectStatement(discussionId);
			ResultSet messageRS = st.executeQuery(sql);
		
			if(!messageRS.next())
			{
				messageRS.close();
				st.close();
				throw new Exception("The database is inconsistent. There is no first message for discussion '" + discussionId + "'");
			}
		
			Message firstMessage = getMessageFromResults(messageRS,connection);
		
			messageRS.close();
			
			Discussion discussion = new Discussion();
			discussion.setFirstMessage(firstMessage);
			discussion.setForumId(forumId);
			discussion.setStatus(rs.getString("STATUS"));
			discussion.setLockedForReading(rs.getBoolean("LOCKED_FOR_READING"));
			discussion.setLockedForWriting(rs.getBoolean("LOCKED_FOR_WRITING"));
		
			Timestamp startStamp = rs.getTimestamp("START_DATE");
			if(startStamp != null)
				discussion.setStart(startStamp.getTime());
		
			Timestamp endStamp = rs.getTimestamp("END_DATE");
			if(endStamp != null)
				discussion.setEnd(endStamp.getTime());
		
			discussion.setMessageCount(rs.getInt(ColumnNames.MESSAGE_COUNT));
			discussion.setLastMessageDate(rs.getTimestamp(ColumnNames.LAST_MESSAGE_DATE).getTime());
			Forum forum = getForum(forumId,ForumPopulatedStates.EMPTY,connection);
			if(forum.getGroups().size() > 0) {
				// Parent forum groups take precedence over child discussions
				discussion.setGroups(forum.getGroups());
				discussion.setGroupsInherited(true);
			} else {
				List<Group> groups = getGroupsForDiscussion(discussionId, connection);
				discussion.setGroups(groups);
				discussion.setGroupsInherited(false);
			}
			
			firstMessage.setGroups(discussion.getGroups());
			
			if(fully)
				getMessageChildren(firstMessage, discussion.getGroups(), connection);
			
			int gradebookAssignmentId = rs.getInt("GRADEBOOK_ASSIGNMENT_ID");
			if(gradebookAssignmentId != 0) {
				YaftGBAssignment  assignment = sakaiProxy.getGradebookAssignment(gradebookAssignmentId);
				discussion.setAssignment(assignment);
			}
		
			return discussion;
		}
		finally
		{
			if(st != null)
			{
				try
				{
					st.close();
				}
				catch(Exception e) {}
			}
		}
	}
	
	Discussion getDiscussion(String discussionId,boolean fully)
	{
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		
		Discussion discussion = null;

		try
		{
			connection = sakaiProxy.borrowConnection();
			st = connection.createStatement();
			String sql = sqlGenerator.getDiscussionSelectStatement(discussionId);
			rs = st.executeQuery(sql);
			
			if(rs.next())
			{
				discussion = getDiscussionFromResults(rs,fully, connection);
			}
			else
			{
				logger.error("No discussion with id: " + discussionId + ". Returning null ...");
			}
		}
		catch(Exception e)
		{
			logger.error("Caught exception whilst getting discussion.",e);
		}
		finally
		{
			try
			{
				if(rs != null) rs.close();
			}
			catch(Exception e) {}
			
			if(st != null)
			{
				try
				{
					st.close();
				}
				catch(Exception e) {}
			}
			
			sakaiProxy.returnConnection(connection);
		}
		
		return discussion;
	}
	
	private boolean deleteFromActiveDiscussions(String discussionId)
	{
		Connection connection = null;
		Statement st = null;

		try
		{
			connection = sakaiProxy.borrowConnection();
			st = connection.createStatement();
			String sql = sqlGenerator.getDeleteFromActiveDiscussionStatement(discussionId);
			st.executeUpdate(sql);
			return true;
		}
		catch(Exception e)
		{
			logger.error("Caught exception whilst deleting from active discussions",e);
			return  false;
		}
		finally
		{
			if(st != null)
			{
				try
				{
					st.close();
				}
				catch(Exception e) {}
			}
			
			sakaiProxy.returnConnection(connection);
		}
	}
	
	private Message getMessageFromResults(ResultSet rs, Connection connection) throws Exception
	{
		Message message = new Message();
		message.setId(rs.getString(ColumnNames.MESSAGE_ID));
		message.setSubject(rs.getString(ColumnNames.SUBJECT));
		message.setParent(rs.getString(ColumnNames.PARENT_MESSAGE_ID));
		message.setSiteId(rs.getString(ColumnNames.SITE_ID));
		message.setDiscussionId(rs.getString(ColumnNames.DISCUSSION_ID));
		message.setContent(rs.getString(ColumnNames.CONTENT));
		Timestamp createdTimestamp = rs.getTimestamp(ColumnNames.CREATED_DATE);
		message.setCreatedDate(createdTimestamp.getTime());
		String creatorId = rs.getString(ColumnNames.CREATOR_ID);
		message.setCreatorId(creatorId);
		message.setStatus(rs.getString(ColumnNames.STATUS));
		message.setCreatorDisplayName(sakaiProxy.getDisplayNameForUser(creatorId));
		
		String sql = sqlGenerator.getMessageAttachmentsSelectStatement(message.getId());
		
		ResultSet rs2 = null;
		Statement st = null;
		
		List<Attachment> attachments = new ArrayList<Attachment>();
		
		try
		{
			st = connection.createStatement();
			rs2 = st.executeQuery(sql);
		
			while(rs2.next())
			{
				String resourceId = rs2.getString(ColumnNames.RESOURCE_ID);
				
				Attachment attachment = new Attachment();
				attachment.setResourceId(resourceId);
				
				sakaiProxy.getAttachment(message.getSiteId(), attachment);
				
				attachments.add(attachment);
			}
		}
		finally
		{
			try
			{
				if(rs2 != null) rs2.close();
			}
			catch(Exception e) {}
			
			if(st != null)
			{
				try
				{
					st.close();
				}
				catch(Exception e) {}
			}
		}
		
		message.setAttachments(attachments);
		
		return message;
	}
	
	private Forum getForumFromResults(ResultSet rs, Connection conn) throws Exception
	{
		Forum forum = new Forum();
		forum.setId(rs.getString(ColumnNames.FORUM_ID));
		forum.setSiteId(rs.getString(ColumnNames.SITE_ID));
		forum.setTitle(rs.getString(ColumnNames.TITLE));
		forum.setCreatorId(rs.getString(ColumnNames.CREATOR_ID));
		forum.setDescription(rs.getString(ColumnNames.DESCRIPTION));
		Timestamp ts = rs.getTimestamp(ColumnNames.LAST_MESSAGE_DATE);
		if(ts != null)
			forum.setLastMessageDate(ts.getTime());
		else
			forum.setLastMessageDate(-1);
		
		Timestamp startStamp = rs.getTimestamp("START_DATE");
		if(startStamp != null)
			forum.setStart(startStamp.getTime());
		
		Timestamp endStamp = rs.getTimestamp("END_DATE");
		if(endStamp != null)
			forum.setEnd(endStamp.getTime());
		
		forum.setLockedForReading(rs.getBoolean("LOCKED_FOR_READING"));
		forum.setLockedForWriting(rs.getBoolean("LOCKED_FOR_WRITING"));
		
		forum.setDiscussionCount(rs.getInt(ColumnNames.DISCUSSION_COUNT));
		forum.setMessageCount(rs.getInt(ColumnNames.MESSAGE_COUNT));
		forum.setStatus(rs.getString("STATUS"));
		
		Statement st = null;
		ResultSet groupRS = null;
		
		try
		{
			st = conn.createStatement();
			String sql = sqlGenerator.getForumGroupsSelectStatement(forum.getId());
			groupRS = st.executeQuery(sql);
			
			List<Group> groups = new ArrayList<Group>();
		
			while(groupRS.next())
				groups.add(new Group(groupRS.getString("GROUP_ID"),groupRS.getString("TITLE")));
			
			forum.setGroups(groups);
		}
		catch(Exception e)
		{
			logger.error("Caught exception whilst getting forum groups.",e);
		}
		finally
		{
			try
			{
				if(groupRS != null) groupRS.close();
			}
			catch(Exception e) {}
			
			if(st != null)
			{
				try
				{
					st.close();
				}
				catch(Exception e) {}
			}
		}

		return forum;
	}

	//unisa change: change statement to PreparedStatement
	private boolean reallyDeleteForum(String forumId)
	{
		if(logger.isDebugEnabled()) logger.debug("reallyDeleteForum()");
		
		Connection connection = null;
		List<PreparedStatement> statements = null;

		try
		{
			connection = sakaiProxy.borrowConnection();
			boolean oldAutoCommitFlag = connection.getAutoCommit();
			connection.setAutoCommit(false);
			
			statements = sqlGenerator.getDeleteForumStatements(forumId, connection);

			try
			{
								
				for(PreparedStatement statement : statements)
					statement.executeUpdate();
				
				connection.commit();
				
				return true;
			}
			catch (Exception e)
			{
				logger.error("Caught exception whilst adding or updating message. Rolling back ...", e);
				connection.rollback();
				return false;
			}
			finally
			{
				connection.setAutoCommit(oldAutoCommitFlag);
			}
		}
		catch (Exception e)
		{
			logger.error("Caught exception whilst adding or updating message", e);
			return false;
		}
		finally
		{
			if(statements != null)
			{
				for(PreparedStatement st : statements){
					try
					{
						st.close();
					}
					catch (Exception e) {}
				}
			}
			
			sakaiProxy.returnConnection(connection);
		}
	}

	boolean deleteForum(String forumId)
	{
		Connection connection = null;
		Statement statement = null;
		Statement deleteDiscussionST = null;

		try
		{
			connection = sakaiProxy.borrowConnection();
			boolean oldAutoCommitFlag = connection.getAutoCommit();
			connection.setAutoCommit(false);
			
			try
			{

				String sql = sqlGenerator.getMarkForumDeletedStatement(forumId);
				
				statement = connection.createStatement();
				
				statement.executeUpdate(sql);
			
				String selectDiscussionsSql = sqlGenerator.getSelectDiscussionIdsForForumStatement(forumId);
			
				ResultSet rs = statement.executeQuery(selectDiscussionsSql);
			
				deleteDiscussionST = connection.createStatement();
			
				while(rs.next())
				{
					String discussionId = rs.getString("DISCUSSION_ID");
					String deleteDiscussionSql = sqlGenerator.getDeleteFromActiveDiscussionStatement(discussionId);
					deleteDiscussionST.executeUpdate(deleteDiscussionSql);
				}
				
				connection.commit();
				
				return true;
			}
			catch (SQLException sqle)
			{
				logger.error("Caught exception whilst deleting forum. Rolling back ...", sqle);
				connection.rollback();
				return false;
			}
			finally
			{
				connection.setAutoCommit(oldAutoCommitFlag);
			}
		}
		catch (Exception e)
		{
			logger.error("Caught exception whilst marking forum deleted", e);
			return false;
		}
		finally
		{
			if(statement != null)
			{
				try
				{
					statement.close();
				}
				catch (Exception e) {}
			}
			
			if(deleteDiscussionST != null)
			{
				try
				{
					deleteDiscussionST.close();
				}
				catch (Exception e) {}
			}
			
			sakaiProxy.returnConnection(connection);
		}
	}

	boolean deleteDiscussion(String discussionId)
	{
		if(logger.isDebugEnabled()) logger.debug("deleteDiscussion()");
		
		Connection connection = null;
		List<PreparedStatement> statements = null;

		try
		{
			Discussion discussion = getDiscussion(discussionId, false);
			
			if(discussion == null)
			{
				logger.error("No discussion for id: " + discussionId + ". Returning false ...");
				return false;
			}
			
			String forumId = discussion.getForumId();
			connection = sakaiProxy.borrowConnection();
			boolean oldAutoCommitFlag = connection.getAutoCommit();
			connection.setAutoCommit(false);
			
			statements = sqlGenerator.getDeleteDiscussionStatements(forumId,discussionId,connection);

			try
			{
				
				for(PreparedStatement statement : statements)
					statement.executeUpdate();
				
				String deleteActiveDiscussionSql = sqlGenerator.getDeleteFromActiveDiscussionStatement(discussionId);
				//Unisa Changes: change statement to PreparedStatement
				PreparedStatement delActiveDiscussionPs = connection.prepareStatement(deleteActiveDiscussionSql);
				//statement.executeUpdate(deleteActiveDiscussionSql);
				delActiveDiscussionPs.executeUpdate();
				
				connection.commit();
				
				return true;
			}
			catch (Exception e)
			{
				logger.error("Caught exception whilst adding or updating message. Rolling back ...", e);
				connection.rollback();
				return false;
			}
			finally
			{
				connection.setAutoCommit(oldAutoCommitFlag);
			}
		}
		catch (Exception e)
		{
			logger.error("Caught exception whilst adding or updating message", e);
			return false;
		}
		finally
		{
			if(statements != null)
			{
				for(PreparedStatement st: statements){
					try
					{
						st.close();
					}
					catch (Exception e) {}
				}
			}
			
			sakaiProxy.returnConnection(connection);
		}
	}

	boolean deleteMessage(Message message,String forumId)
	{
		if(logger.isDebugEnabled()) logger.debug("deleteMessage()");
		
		Connection connection = null;
		List<PreparedStatement> statements = null;

		try
		{
			boolean oldAutoCommitFlag = false;
			
			connection = sakaiProxy.borrowConnection();
			oldAutoCommitFlag = connection.getAutoCommit();
			connection.setAutoCommit(false);

			statements = sqlGenerator.getDeleteMessageStatements(message,forumId,connection);
			
			try
			{
				for(PreparedStatement statement : statements)
					statement.executeUpdate();
				
				connection.commit();
			
				return true;
			}
			catch(Exception e)
			{
				logger.error("Caught exception whilst deleting a message. Rolling back ...",e);
				connection.rollback();
				return false;
			}
			finally
			{
				connection.setAutoCommit(oldAutoCommitFlag);
			}
		}
		catch (Exception e)
		{
			logger.error("Caught exception whilst deleting a message", e);
			return false;
		}
		finally
		{
			if(statements != null)
			{
				for(PreparedStatement st : statements)
				{
					try
					{
						st.close();
					}
					catch (Exception e) {}
				}
			}
			
			sakaiProxy.returnConnection(connection);
		}
	}
	
	
	boolean undeleteMessage(Message message,String forumId)
	{
		if(logger.isDebugEnabled()) logger.debug("undeleteMessage()");
		
		Connection connection = null;
		List<PreparedStatement> statements = null;

		try
		{
			connection = sakaiProxy.borrowConnection();
			boolean oldAutoCommitFlag = connection.getAutoCommit();
			connection.setAutoCommit(false);
			
			statements = sqlGenerator.getUndeleteMessageStatements(message,forumId,connection);

			try
			{
								
				for(PreparedStatement statement : statements)
					statement.executeUpdate();
				
				connection.commit();
				
				return true;
			}
			catch (Exception e)
			{
				logger.error("Caught exception whilst un-deleting message. Rolling back ...", e);
				connection.rollback();
				return false;
			}
			finally
			{
				connection.setAutoCommit(oldAutoCommitFlag);
			}
		}
		catch (Exception e)
		{
			logger.error("Caught exception whilst un-deleting message", e);
			return false;
		}
		finally
		{
			if(statements != null)
			{
				for(PreparedStatement st : statements){
					try
					{
						st.close();
					}
					catch (Exception e) {}
				}
			}
			
			sakaiProxy.returnConnection(connection);
		}
	}

	boolean showMessage(Message message)
	{
		Connection connection = null;
		Statement statement = null;

		try
		{
			connection = sakaiProxy.borrowConnection();

			String sql = sqlGenerator.getShowMessageStatement(message);
				
			statement = connection.createStatement();
				
			statement.executeUpdate(sql);
			
			return true;
		}
		catch (Exception e)
		{
			logger.error("Caught exception whilst showing message", e);
			return false;
		}
		finally
		{
			if(statement != null)
			{
				try
				{
					statement.close();
				}
				catch (Exception e) {}
			}
			
			sakaiProxy.returnConnection(connection);
		}
	}

	void deleteAttachment(String attachmentId, String messageId)
	{
		Connection connection = null;
		Statement statement = null;

		try
		{
			connection = sakaiProxy.borrowConnection();
			boolean oldAutoCommitFlag = connection.getAutoCommit();
			connection.setAutoCommit(false);

			try
			{
				String sql = sqlGenerator.getDeleteAttachmentStatement(attachmentId,messageId);
				
				statement = connection.createStatement();
				
				statement.executeUpdate(sql);
				
				connection.commit();
				
				sakaiProxy.deleteFile(attachmentId);
			}
			catch (Exception e)
			{
				logger.error("Caught exception whilst deleting attachment. Rolling back ...", e);
				connection.rollback();
			}
			finally
			{
				connection.setAutoCommit(oldAutoCommitFlag);
			}
		}
		catch (Exception e)
		{
			logger.error("Caught exception whilst adding or updating message", e);
		}
		finally
		{
			if(statement != null)
			{
				try
				{
					statement.close();
				}
				catch (SQLException e) {}
			}
			
			sakaiProxy.returnConnection(connection);
		}
	}

	Message getMessage(String messageId)
	{
		String sql = sqlGenerator.getMessageSelectStatement(messageId);
		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		try
		{
			connection = sakaiProxy.borrowConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);
			
			if(rs.next())
				return getMessageFromResults(rs, connection);
		}
		catch (Exception e)
		{
			logger.error("Caught exception whilst getting message.", e);
		}
		finally
		{
			try
			{
				if(rs != null) rs.close();
			}
			catch (SQLException e) {}
			
			try
			{
				if(statement != null) statement.close();
			}
			catch (SQLException e) {}
			
			sakaiProxy.returnConnection(connection);
		}
		
		return null;
	}

	Forum getForumContainingMessage(String messageId)
	{
		String sql = sqlGenerator.getSelectForumContainingMessageStatement(messageId);
		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		try
		{
			connection = sakaiProxy.borrowConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);
			if(rs.next())
			{
				String forumId = rs.getString(ColumnNames.FORUM_ID);
				return securityManager.filterForum(getForum(forumId, ForumPopulatedStates.EMPTY),null);
			}
		}
		catch (Exception e)
		{
			logger.error("Caught exception whilst getting forum.", e);
		}
		finally
		{
			try
			{
				if(rs != null) rs.close();
			}
			catch (SQLException e) {}
			
			try
			{
				if(statement != null) statement.close();
			}
			catch (SQLException e) {}
			
			sakaiProxy.returnConnection(connection);
		}
		
		return null;
	}
	
	boolean markMessageRead(String messageId,String forumId,String discussionId)
	{
		Connection connection = null;
		
		try
		{
			connection = sakaiProxy.borrowConnection();
			boolean oldAutoCommitFlag = connection.getAutoCommit();
			connection.setAutoCommit(false);
			
			try
			{
				markMessageRead(messageId, forumId, discussionId, connection);
				connection.commit();
				return true;
			}
			catch(Exception e)
			{
				connection.rollback();
			}
			finally
			{
				connection.setAutoCommit(oldAutoCommitFlag);
			}
		}
		catch(Exception e)
		{
		}
		finally
		{
			sakaiProxy.returnConnection(connection);
		}
		
		return false;
	}

	//Unisa Changes: change from statement to PreparedStatement
	private void markMessageRead(String messageId,String forumId,String discussionId,Connection connection) throws Exception
	{
		if(logger.isDebugEnabled()) logger.debug("markMessageRead()");
		// Has this message already been read?
		String testSql = sqlGenerator.getSelectMessageReadStatement(sakaiProxy.getCurrentUser().getId(),messageId);
		
		Statement statement = null;
		ResultSet rs  =  null;
		List<PreparedStatement> updateSqlPs = null;
		
		try
		{
			statement = connection.createStatement();
			rs = statement.executeQuery(testSql);
			if(!rs.next())
			{
				updateSqlPs = sqlGenerator.getMarkMessageReadStatements(sakaiProxy.getCurrentUser().getId(),messageId,forumId,discussionId,connection);
			
				for(PreparedStatement statementPs : updateSqlPs)
					statementPs.executeUpdate();
			}
		}
		finally
		{
			try
			{
				if(rs != null) rs.close();
			}
			catch (SQLException e) {}
			
			try
			{
				if(statement != null){
					statement.close();
				}
				//Unisa Changes: close prepared statement connection also
				if(updateSqlPs != null){
					for(PreparedStatement st : updateSqlPs){
						st.close();
					}
				}
			}
			catch (Exception e) {}
		}
	}

	//Unisa changes: change to prepared statements
	boolean markMessageUnRead(String messageId,String forumId,String discussionId)
	{
		if(logger.isDebugEnabled()) logger.debug("markMessageUnRead()");
		Connection connection = null;
		List<PreparedStatement> statements = null;
		
		try
		{
			connection = sakaiProxy.borrowConnection();
			boolean oldAutoCommitFlag = connection.getAutoCommit();
			connection.setAutoCommit(false);
			statements = sqlGenerator.getMarkMessageUnReadStatements(sakaiProxy.getCurrentUser().getId(),messageId,forumId,discussionId,connection);
			
			try
			{
							

				for(PreparedStatement statement : statements)
					statement.executeUpdate();
				
				connection.commit();
				return true;
			}
			catch (Exception e)
			{
				logger.error("Caught exception whilst marking message unread. Rolling back ...", e);
				connection.rollback();
				return false;
			}
			finally
			{
				connection.setAutoCommit(oldAutoCommitFlag);
			}
		}
		catch (Exception e)
		{
			logger.error("Caught exception whilst marking message unread", e);
			return false;
		}
		finally
		{
			try
			{
				//Unisa changes: change to prepared statements
				if(statements != null){
					for(PreparedStatement st : statements )
						st.close();
				}
			}
			catch (Exception e) {}
			
			sakaiProxy.returnConnection(connection);
		}
	}
	
	//Unisa Changes: change to prepared statements
	boolean markDiscussionRead(String discussionId,String forumId)
	{
		if(logger.isInfoEnabled())
			logger.info("markDiscussionRead(" + discussionId + "," + forumId + ")");
		
		Connection connection = null;
		List<PreparedStatement> statements = null;
		
		try
		{
			connection = sakaiProxy.borrowConnection();
			boolean oldAutoCommitFlag = connection.getAutoCommit();
			connection.setAutoCommit(false);
			
			statements = sqlGenerator.getMarkDiscussionReadStatements(sakaiProxy.getCurrentUser().getId(),discussionId,forumId,connection);

			try
			{
								
				for(PreparedStatement statement : statements)
					statement.executeUpdate();
				
				connection.commit();
				return true;
			}
			catch (Exception e)
			{
				logger.error("Caught exception whilst marking discussion read. Rolling back ...", e);
				connection.rollback();
				return false;
			}
			finally
			{
				connection.setAutoCommit(oldAutoCommitFlag);
			}
		}
		catch (Exception e)
		{
			logger.error("Caught exception whilst marking discussion unread", e);
			return false;
		}
		finally
		{
			try
			{
				if(statements != null){
					for(PreparedStatement st : statements)
						st.close();
				}
			}
			catch (SQLException e) {}
			
			sakaiProxy.returnConnection(connection);
		}
	}

	List<String> getReadMessageIds(String discussionId)
	{
		String sql = sqlGenerator.getSelectReadMessageIds(sakaiProxy.getCurrentUser().getId(),discussionId);
		
		List<String> results = new ArrayList<String>();
		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		try
		{
			connection = sakaiProxy.borrowConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);
			while(rs.next())
				results.add(rs.getString(ColumnNames.MESSAGE_ID));
		}
		catch (Exception e)
		{
			logger.error("Caught exception whilst getting read message ids.", e);
		}
		finally
		{
			try
			{
				if(rs != null) rs.close();
			}
			catch (SQLException e) {}
			
			try
			{
				if(statement != null) statement.close();
			}
			catch (SQLException e) {}
			
			sakaiProxy.returnConnection(connection);
		}
		
		return results;
	}

	//Unisa Changes: change to prepared statements
	void moveDiscussion(String discussionId, String currentForumId,String newForumId)
	{
		if(logger.isInfoEnabled())
			logger.info("moveDiscussion(" + discussionId + "," + currentForumId + "," + newForumId + ")");
		Connection connection = null;
		List<PreparedStatement> statements = null;

		try
		{
			connection = sakaiProxy.borrowConnection();
			boolean oldAutoCommitFlag = connection.getAutoCommit();
			connection.setAutoCommit(false);
			
			statements = sqlGenerator.getMoveDiscussionStatements(discussionId, currentForumId, newForumId,connection);

			try
			{
								
				for(PreparedStatement statement: statements)
					statement.executeUpdate();
				
				connection.commit();
			}
			catch (Exception e)
			{
				logger.error("Caught exception whilst moving discussion. Rolling back ...", e);
				connection.rollback();
			}
			finally
			{
				connection.setAutoCommit(oldAutoCommitFlag);
			}
		}
		catch (Exception e)
		{
			logger.error("Caught exception whilst moving discussion.", e);
		}
		finally
		{
			try
			{
				if(statements != null){
					for(PreparedStatement st : statements)
						st.close();
				}
			}
			catch (SQLException e) {}
			
			sakaiProxy.returnConnection(connection);
		}
	}

	Map<String,Integer> getReadMessageCountForAllFora(String userId) {
		Map<String,Integer> counts = new HashMap<String,Integer>();
		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			connection = sakaiProxy.borrowConnection();
			statement = connection.createStatement();
			String sql = sqlGenerator.getSelectReadMessageCountForAllForaStatement(userId);
			rs = statement.executeQuery(sql);
			while(rs.next()) {
				String forumId = rs.getString("FORUM_ID");
				int read = rs.getInt("NUMBER_READ");
				counts.put(forumId, read);
			}
		}
		catch (Exception e) {
			logger.error("Caught exception whilst getting read message ids.", e);
		}
		finally {
			try {
				if(rs != null) rs.close();
			}
			catch (Exception e) {}
			
			try {
				if(statement != null) statement.close();
			}
			catch (Exception e) {}
			
			sakaiProxy.returnConnection(connection);
		}
		
		return counts;
	}

	Map<String, Integer> getReadMessageCountForForum(String userId, String forumId) {
		Map<String,Integer> counts = new HashMap<String,Integer>();
		
		Connection connection = null;
		Statement discussionIdsST = null;
		PreparedStatement countST = null;
		ResultSet rs = null;
		
		try {
			connection = sakaiProxy.borrowConnection();
			countST = sqlGenerator.getSelectReadMessageCountForDiscussionStatement(userId,connection);
			discussionIdsST = connection.createStatement();
			
			String discussionIdsQuery = sqlGenerator.getSelectDiscussionIdsForForumStatement(forumId);
			ResultSet idsRS = discussionIdsST.executeQuery(discussionIdsQuery);
			while(idsRS.next()) {
				String discussionId = idsRS.getString("DISCUSSION_ID");
				
				countST.setString(1, discussionId);
				
				ResultSet countRS = countST.executeQuery();
				if(countRS.next()) {
					int count = countRS.getInt("NUMBER_READ");
					counts.put(discussionId, count);
				}
				
				countRS.close();
			}
		}
		catch (Exception e) {
			logger.error("Caught exception whilst getting read message ids.", e);
		}
		finally {
			try {
				if(rs != null) rs.close();
			}
			catch (SQLException e) {}
			
			try {
				if(countST != null) countST.close();
			}
			catch (SQLException e) {}
			
			try {
				if(discussionIdsST != null) discussionIdsST.close();
			}
			catch (SQLException e) {}
			
			sakaiProxy.returnConnection(connection);
		}
		
		return counts;
	}

	Forum getForumForTitle(String title,String state,String siteId)
	{
		String sql = sqlGenerator.getSelectForumIdForTitleStatement(title,siteId);
		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		try
		{
			connection = sakaiProxy.borrowConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);
			String forumId = null;
			
			if(rs.next())
				forumId = rs.getString("FORUM_ID");
			else
				return null;
			
			return securityManager.filterForum(getForum(forumId,state),siteId);
		}
		catch (Exception e)
		{
			logger.error("Caught exception whilst getting forum id for title.", e);
			return null;
		}
		finally
		{
			try
			{
				if(rs != null) rs.close();
			}
			catch (SQLException e) {}
			
			try
			{
				if(statement != null) statement.close();
			}
			catch (SQLException e) {}
			
			sakaiProxy.returnConnection(connection);
		}
	}

	boolean addDiscussion(String siteId, String forumId, Discussion discussion)
	{
		PreparedStatement statement = null;
		Connection connection = null;
		List<PreparedStatement> groupsStatements = null;
		
		try
		{
			connection = sakaiProxy.borrowConnection();
			boolean oldAutoCommitFlag = connection.getAutoCommit();
			connection.setAutoCommit(false);
			
			try
			{
				if(!addOrUpdateMessage(siteId, forumId, discussion.getFirstMessage(),connection))
					throw new Exception("addOrUpdateMessage returned false");
				
				statement = sqlGenerator.getSetDiscussionDatesStatement(discussion,connection);
				statement.executeUpdate();
				
				// If the parent forum is group restricted the same restrictions implicitly apply to child discussions
				Forum forum = getForum(forumId, ForumPopulatedStates.EMPTY,connection);
				if(forum.getGroups().size() <= 0) {
					groupsStatements = sqlGenerator.getSetDiscussionGroupsStatements(discussion,connection);
					for(PreparedStatement groupsStatement : groupsStatements) {
						groupsStatement.executeUpdate();
					}
				}
				
				connection.commit();
			}
			catch(Exception e)
			{
				logger.error("Caught exception whilst adding discussion. Rolling back ...",e);
				connection.rollback();
				
				return false;
			}
			finally
			{
				connection.setAutoCommit(oldAutoCommitFlag);
			}
			
			long start = discussion.getStart();
			long end = discussion.getEnd();
			
			if(start > 0L && end > 0L)
			{
				String url = sakaiProxy.getServerUrl() + discussion.getUrl();
				sakaiProxy.addCalendarEntry("Start of '" + discussion.getSubject() + "'","<a onclick=\"window.open('" + url + "','discussion','location=0,width=500,height=400,scrollbars=1,resizable=1,toolbar=0,menubar=0'); return false;\" href=\"" + url + "\">Start of '" + discussion.getSubject() + "' Discussion (Click to launch)</a>","Activity",start + 32400000,start + 32460000);
				sakaiProxy.addCalendarEntry("End of '" + discussion.getSubject() + "'","End of '" + discussion.getSubject() + "' Discussion","Activity",end + 32400000,end + 32460000);
			}
			
			return true;
		}
		catch (Exception e)
		{
			logger.error("Caught exception whilst adding discussion.", e);
			return false;
		}
		finally
		{
			try
			{
				if(statement != null) statement.close();
			}
			catch (SQLException e) {}
			
			if(groupsStatements != null) {
				for(PreparedStatement groupsStatement : groupsStatements) {
					try {
						groupsStatement.close();
					}
					catch (SQLException e) {}
				}
			}
			
			sakaiProxy.returnConnection(connection);
		}
	}

	List<ActiveDiscussion> getActiveDiscussions()
	{
		List<ActiveDiscussion> discussions = new ArrayList<ActiveDiscussion>();
		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		
		try
		{
			connection = sakaiProxy.borrowConnection();
			
			String sql = sqlGenerator.getSelectActiveDiscussionsStatement();
			
			statement = connection.createStatement();
				
			rs = statement.executeQuery(sql);
			
			int count = 0;
			
			while(rs.next() && count < activeDiscussionLimit)
			{
				ActiveDiscussion discussion = new ActiveDiscussion();
				
				String discussionId = rs.getString("DISCUSSION_ID"); 
				String siteId = rs.getString("SITE_ID"); 
				Site site = sakaiProxy.getSite(siteId);
				ToolConfiguration tc = site.getToolForCommonId("sakai.yaft");
				String url = "/portal/tool/" + tc.getId() + "/discussions/" + discussionId + ".html";
				discussion.setUrl(url);
				discussion.setSubject(rs.getString("SUBJECT"));
				discussion.setSiteId(siteId);
				discussion.setLastMessageDate(rs.getTimestamp("LAST_MESSAGE_DATE").getTime());
				discussion.setLatestMessageSubject(rs.getString("LATEST_MESSAGE_SUBJECT"));
				
				List<Group> groups = getGroupsForDiscussion(discussionId, connection);
				discussion.setGroups(groups);
				discussions.add(discussion);
				
				count++;
			}
		}
		catch (Exception e)
		{
			logger.error("Caught exception whilst getting active discussions", e);
		}
		finally
		{
			try
			{
				if(rs != null) rs.close();
			}
			catch (SQLException e) {}
			
			if(statement != null)
			{
				try
				{
					statement.close();
				}
				catch (SQLException e) {}
			}
			
			sakaiProxy.returnConnection(connection);
		}
		
		return securityManager.filterActiveDiscussions(discussions);
	}
	
	private List<Group> getGroupsForDiscussion(String discussionId, Connection conn) throws Exception{
		
		Statement st = null;
		ResultSet groupsRS = null;
		
		try {
			st = conn.createStatement();
			String groupsSql = sqlGenerator.getDiscussionGroupsSelectStatement(discussionId);
			groupsRS = st.executeQuery(groupsSql);
			List<Group> groups = new ArrayList<Group>();
			while(groupsRS.next()) {
				groups.add(new Group(groupsRS.getString("GROUP_ID"),groupsRS.getString("TITLE")));
			}
			return groups;
		} finally {
			if(groupsRS != null) {
				try {
					groupsRS.close();
				} catch (SQLException ignore) {}
			}
			if(st != null) {
				try {
					st.close();
				} catch (SQLException ignore) {}
			}
		}
	}

	String getIdOfSiteContainingMessage(String messageId)
	{
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		
		try
		{
			connection = sakaiProxy.borrowConnection();
			
			String sql = sqlGenerator.getSelectIdOfSiteContainingMessage(messageId);
			
			statement = connection.createStatement();
				
			rs = statement.executeQuery(sql);
			
			if(rs.next())
				return rs.getString("SITE_ID");
			
		}
		catch (Exception e)
		{
			logger.error("Caught exception whilst getting site id", e);
		}
		finally
		{
			try
			{
				if(rs != null) rs.close();
			}
			catch (SQLException e) {}
			
			if(statement != null)
			{
				try
				{
					statement.close();
				}
				catch (SQLException e) {}
			}
			
			sakaiProxy.returnConnection(connection);
		}
		
		return null;
	}

	List<Author> getAuthorsForCurrentSite() {
		List<Author> posters = new ArrayList<Author>();
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try {
			connection = sakaiProxy.borrowConnection();
			
			statement = sqlGenerator.getSelectSiteAuthors(sakaiProxy.getCurrentSiteId(),connection);
				
			rs = statement.executeQuery();
			
			while(rs.next()) {
				String id = rs.getString("CREATOR_ID");
				posters.add(new Author( id,sakaiProxy.getDisplayNameForUser(id),rs.getInt("NUMBER_OF_POSTS") ));
			}
			
			return posters;
		}
		catch (Exception e) {
			logger.error("Caught exception whilst getting authors for site '" + sakaiProxy.getCurrentSiteId(), e);
		}
		finally {
			
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {}
			}
			
			if(statement != null) {
				try {
					statement.close();
				}
				catch (SQLException e) {}
			}
			
			sakaiProxy.returnConnection(connection);
		}
		
		return null;
	}

	List<Message> getMessagesForAuthorInCurrentSite(String authorId) {
		List<Message> messages = new ArrayList<Message>();
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try {
			connection = sakaiProxy.borrowConnection();
			
			statement = sqlGenerator.getSelectMessagesForAuthorInSite(authorId,sakaiProxy.getCurrentSiteId(),connection);
				
			rs = statement.executeQuery();
			
			while(rs.next()) {
				messages.add(this.getMessageFromResults(rs, connection));
			}
			
			return messages;
		}
		catch (Exception e) {
			logger.error("Caught exception whilst getting messages for author '" + authorId + "' and site '" + sakaiProxy.getCurrentSiteId(), e);
		}
		finally {
			
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {}
			}
			
			if(statement != null) {
				try {
					statement.close();
				}
				catch (SQLException e) {}
			}
			
			sakaiProxy.returnConnection(connection);
		}
		
		return null;
	}

	List<Author> getAuthorsForDiscussion(String discussionId) {
		List<Author> posters = new ArrayList<Author>();
		
		Discussion discussion = getDiscussion(discussionId,false);
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try {
			connection = sakaiProxy.borrowConnection();
			
			statement = sqlGenerator.getSelectDiscussionAuthors(discussionId,connection);
				
			rs = statement.executeQuery();
			
			while(rs.next()) {
				String id = rs.getString("CREATOR_ID");
				Author author = new Author( id,sakaiProxy.getDisplayNameForUser(id),rs.getInt("NUMBER_OF_POSTS") );
				
				if(discussion.isGraded()) {
					GradeDefinition grade = sakaiProxy.getAssignmentGrade(id,discussion.getAssignment().id);
					author.setGrade(grade);
				}
				
				posters.add(author);
			}
			
			return posters;
		}
		catch (Exception e) {
			logger.error("Caught exception whilst getting authors for discussion '" + discussionId + "'", e);
		}
		finally {
			
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {}
			}
			
			if(statement != null) {
				try {
					statement.close();
				}
				catch (SQLException e) {}
			}
			
			sakaiProxy.returnConnection(connection);
		}
		
		return null;
	}

	List<Message> getMessagesForAuthorInDiscussion(String authorId, String discussionId) {
		List<Message> messages = new ArrayList<Message>();
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try {
			connection = sakaiProxy.borrowConnection();
			
			statement = sqlGenerator.getSelectMessagesForAuthorInDiscussion(authorId,discussionId,connection);
				
			rs = statement.executeQuery();
			
			while(rs.next()) {
				messages.add(this.getMessageFromResults(rs, connection));
			}
			
			return messages;
		}
		catch (Exception e) {
			logger.error("Caught exception whilst getting messages for author '" + authorId + "' and discussion '" + discussionId + "'", e);
		}
		finally {
			
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {}
			}
			
			if(statement != null) {
				try {
					statement.close();
				}
				catch (SQLException e) {}
			}
			
			sakaiProxy.returnConnection(connection);
		}
		
		return null;
	}

	public boolean setDiscussionGroups(String discussionId, Collection<String> groupIds) {
		
		if(groupIds.size() <= 0) return false;
		
		Connection connection = null;
		List<PreparedStatement> groupsStatements = null;
		
		try {
			connection = sakaiProxy.borrowConnection();
			boolean oldAutoCommitFlag = connection.getAutoCommit();
			connection.setAutoCommit(false);
			
			try {
				// If the parent forum is group restricted the same restrictions implicitly apply to child discussions
				Discussion discussion = getDiscussion(discussionId,false);
				Forum forum = getForum(discussion.getForumId(), ForumPopulatedStates.EMPTY,connection);
				if(forum.getGroups().size() <= 0) {
					List<Group> groups = new ArrayList<Group>(groupIds.size());
					for(String groupId : groupIds) {
						groups.add(new Group(groupId,null));
					}
					discussion.setGroups(groups);
					groupsStatements = sqlGenerator.getSetDiscussionGroupsStatements(discussion,connection);
					for(PreparedStatement groupsStatement : groupsStatements) {
						groupsStatement.executeUpdate();
					}
				}
				
				connection.commit();
				
				return true;
			}
			catch(Exception e) {
				logger.error("Caught exception whilst setting discussion groups. Rolling back ...",e);
				connection.rollback();
				
				return false;
			}
			finally {
				connection.setAutoCommit(oldAutoCommitFlag);
			}
		}
		catch (Exception e) {
			logger.error("Caught exception whilst setting discussion groups.", e);
			return false;
		}
		finally {
			
			if(groupsStatements != null) {
				for(PreparedStatement groupsStatement : groupsStatements) {
					try {
						groupsStatement.close();
					}
					catch (SQLException e) {}
				}
			}
			
			sakaiProxy.returnConnection(connection);
		}
	}

	//Unisa Changes: change to prepared statement
	public boolean clearDiscussion(String discussionId) {
		Connection connection = null;
		List<PreparedStatement> statements = null;

		try
		{
			Discussion discussion = getDiscussion(discussionId, false);
			
			if(discussion == null)
			{
				logger.error("No discussion for id: " + discussionId + ". Returning false ...");
				return false;
			}
			
			String forumId = discussion.getForumId();
			connection = sakaiProxy.borrowConnection();
			boolean oldAutoCommitFlag = connection.getAutoCommit();
			connection.setAutoCommit(false);

			try
			{
				statements = sqlGenerator.getClearDiscussionStatements(forumId,discussionId,connection);
								
				for(PreparedStatement statement : statements)
					statement.executeUpdate();
				
				String deleteActiveDiscussionSql = sqlGenerator.getDeleteFromActiveDiscussionStatement(discussionId);
				//Unisa Changes: change to prepared statement
				//statement.executeUpdate(deleteActiveDiscussionSql);
				PreparedStatement deleteActiveDiscussionPs = connection.prepareStatement(deleteActiveDiscussionSql);
				deleteActiveDiscussionPs.executeUpdate();
				
				connection.commit();
				
				return true;
			}
			catch (Exception e)
			{
				logger.error("Caught exception whilst clearing discussion. Rolling back ...", e);
				connection.rollback();
				return false;
			}
			finally
			{
				connection.setAutoCommit(oldAutoCommitFlag);
			}
		}
		catch (Exception e)
		{
			logger.error("Caught exception whilst clearing discussion.", e);
			return false;
		}
		finally
		{
			if(statements != null)
			{
				try
				{	for(PreparedStatement st : statements)
						st.close();
				}
				catch (Exception e) {}
			}
			
			sakaiProxy.returnConnection(connection);
		}
	}

	public List<Forum> getForaForSite(String siteId) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<Forum> fora = new ArrayList<Forum>();

		try {
			connection = sakaiProxy.borrowConnection();
			statement = sqlGenerator.getSelectSiteForaStatement(siteId,connection);
			rs = statement.executeQuery();
			while(rs.next()) {
				fora.add(getForumFromResults(rs, connection));
			}
		} catch(Exception e) {
			logger.warn("Caught exception whilst getting fora for site " + siteId + ". Reason: " + e.getMessage() + ". An empty list will be returned.");
		} finally {
			if(rs != null) {
				try {
					rs.close();
				}
				catch (Exception e) {}
			}
			if(statement != null) {
				try {
					statement.close();
				}
				catch (Exception e) {}
			}
			
			sakaiProxy.returnConnection(connection);
		}
		return fora;
	}
}
