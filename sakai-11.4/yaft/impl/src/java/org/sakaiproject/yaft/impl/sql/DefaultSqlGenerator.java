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
package org.sakaiproject.yaft.impl.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.sakaiproject.yaft.api.Attachment;
import org.sakaiproject.yaft.api.Discussion;
import org.sakaiproject.yaft.api.Forum;
import org.sakaiproject.yaft.api.Group;
import org.sakaiproject.yaft.api.Message;
import org.sakaiproject.yaft.api.YaftGBAssignment;

public class DefaultSqlGenerator implements SqlGenerator
{
	private Logger logger = Logger.getLogger(DefaultSqlGenerator.class);

	protected String VARCHAR = "VARCHAR";

	protected String DATETIME = "DATETIME";

	protected String TEXT = "TEXT";
	
	protected String MEDIUMTEXT = "MEDIUMTEXT";
	
	protected String BOOL = "BOOL";
	
	protected String INT = "INT";
	
	protected String ACTIVE_DISCUSSIONS_TTL = "(NOW() - 86400)"; // 1 Day

	public List<String> getSetupStatements()
	{
		if (logger.isDebugEnabled())
			logger.debug("getSetupStatements()");

		List<String> statements = new ArrayList<String>();
		
		statements.add("CREATE TABLE YAFT_FORUM (FORUM_ID CHAR(36) NOT NULL,SITE_ID " + VARCHAR + "(99) NOT NULL,CREATOR_ID " + VARCHAR + "(99) NOT NULL,TITLE " + VARCHAR + "(255) NOT NULL,DESCRIPTION " + VARCHAR + "(255),DISCUSSION_COUNT " + INT + " NOT NULL,MESSAGE_COUNT " + INT + " NOT NULL,LAST_MESSAGE_DATE " + DATETIME + ",START_DATE " + DATETIME + ",END_DATE " + DATETIME + ",LOCKED_FOR_WRITING " + BOOL + " NOT NULL,LOCKED_FOR_READING " + BOOL + " NOT NULL,STATUS " + VARCHAR + "(36) NOT NULL,CONSTRAINT yaft_forum_pk PRIMARY KEY (FORUM_ID))");
		
		statements.add("CREATE TABLE YAFT_FORUM_GROUP (FORUM_ID CHAR(36) NOT NULL,GROUP_ID " + VARCHAR + "(36) NOT NULL,CONSTRAINT yaft_forum_group_pk PRIMARY KEY (FORUM_ID,GROUP_ID))");

		statements.add("CREATE TABLE YAFT_FORUM_DISCUSSION (FORUM_ID CHAR(36) NOT NULL,DISCUSSION_ID CHAR(36) NOT NULL,CONSTRAINT yaft_forum_discussion_pk PRIMARY KEY (FORUM_ID,DISCUSSION_ID))");

		statements.add("CREATE TABLE YAFT_DISCUSSION (DISCUSSION_ID CHAR(36) NOT NULL," + "LAST_MESSAGE_DATE " + DATETIME + " NOT NULL," + "MESSAGE_COUNT " + INT + " NOT NULL," + "STATUS " + VARCHAR + "(36) NOT NULL," + "START_DATE " + DATETIME + "," + "END_DATE " + DATETIME + "," + "LOCKED_FOR_WRITING " + BOOL + " NOT NULL," + "LOCKED_FOR_READING " + BOOL + " NOT NULL,GRADEBOOK_ASSIGNMENT_ID BIGINT(20),"+ "CONSTRAINT yaft_discussion_pk PRIMARY KEY (DISCUSSION_ID))");

		statements.add("CREATE TABLE YAFT_MESSAGE (MESSAGE_ID CHAR(36) NOT NULL," + "SITE_ID " + VARCHAR + "(99) NOT NULL," + "PARENT_MESSAGE_ID CHAR(36)," + "DISCUSSION_ID CHAR(36)," + "SUBJECT " + VARCHAR + "(255) NOT NULL," + "CONTENT " + MEDIUMTEXT + " NOT NULL," + "CREATOR_ID " + VARCHAR + "(99) NOT NULL," + "CREATED_DATE " + DATETIME + " NOT NULL," + "STATUS " + VARCHAR + "(36)," + "CONSTRAINT yaft_message_pk PRIMARY KEY (MESSAGE_ID))");

		statements.add("CREATE TABLE YAFT_MESSAGE_CHILDREN (MESSAGE_ID CHAR(36) NOT NULL," + "CHILD_MESSAGE_ID CHAR(36) NOT NULL," + "CONSTRAINT yaft_message_children_pk PRIMARY KEY (MESSAGE_ID,CHILD_MESSAGE_ID))");

		statements.add("CREATE TABLE YAFT_READ_MESSAGES (USER_ID " + VARCHAR + "(99) NOT NULL," + "MESSAGE_ID CHAR(36) NOT NULL," + "CONSTRAINT yaft_read_messages_pk PRIMARY KEY (USER_ID,MESSAGE_ID))");

		statements.add("CREATE TABLE YAFT_MESSAGE_ATTACHMENTS (MESSAGE_ID CHAR(36) NOT NULL," + "RESOURCE_ID " + VARCHAR + "(255) NOT NULL," + "CONSTRAINT yaft_message_attachments_pk PRIMARY KEY (MESSAGE_ID,RESOURCE_ID))");

		statements.add("CREATE TABLE YAFT_FORUM_READ (FORUM_ID CHAR(36) NOT NULL," + "USER_ID " + VARCHAR + "(99) NOT NULL," + "NUMBER_READ " + INT + " NOT NULL," + "CONSTRAINT yaft_forum_read_pk PRIMARY KEY (FORUM_ID,USER_ID))");

		statements.add("CREATE TABLE YAFT_DISCUSSION_READ (DISCUSSION_ID CHAR(36) NOT NULL," + "USER_ID " + VARCHAR + "(99) NOT NULL," + "NUMBER_READ " + INT + " NOT NULL," + "CONSTRAINT yaft_discussion_read_pk PRIMARY KEY (DISCUSSION_ID,USER_ID))");

		statements.add("CREATE TABLE YAFT_ACTIVE_DISCUSSIONS (DISCUSSION_ID CHAR(36) NOT NULL,SITE_ID " + VARCHAR + "(99) NOT NULL,SUBJECT " + VARCHAR + "(255) NOT NULL,LAST_MESSAGE_DATE " + DATETIME + " NOT NULL,LATEST_MESSAGE_SUBJECT " + VARCHAR + "(255) NOT NULL)");
		
		statements.add("CREATE TABLE YAFT_DISCUSSION_GROUP (DISCUSSION_ID CHAR(36) NOT NULL,GROUP_ID " + VARCHAR + "(36) NOT NULL,CONSTRAINT yaft_discussion_group_pk PRIMARY KEY (DISCUSSION_ID,GROUP_ID))");
		
		return statements;
	}

	public String getForaSelectStatement()
	{
		return getForaSelectStatement(null);
	}

	public String getForaSelectStatement(String siteId)
	{
		if (siteId == null)
			return "SELECT * FROM YAFT_FORUM";
		else
			return "SELECT * FROM YAFT_FORUM WHERE SITE_ID = '" + siteId + "'";
	}

	public String getForumSelectStatement(String forumId)
	{
		return "SELECT * FROM YAFT_FORUM WHERE FORUM_ID = '" + forumId + "'";
	}

	public List<PreparedStatement> getAddOrUpdateForumStatements(Forum forum, Connection connection) throws SQLException
	{
		List<PreparedStatement> statements = new ArrayList<PreparedStatement>();
		
		// If the forum has an id, it is probably an existing one. Maybe not though ...
		boolean existing = (forum.getId().length() > 0);
		
		if(existing) {
			// Do a proper check against the DB
			Statement testST = null;
		
			try {
				testST = connection.createStatement();
				ResultSet testRS = testST.executeQuery("SELECT FORUM_ID FROM YAFT_FORUM WHERE FORUM_ID = '" + forum.getId() + "'");
				if(testRS.next()) existing = true; // If we get a record, it exists.
				testRS.close();
			} catch(SQLException sqle) {
			} finally {
				if(testST != null) testST.close();
			}
		}
		
		if (existing) {
			String updateSql = "UPDATE YAFT_FORUM SET TITLE = ?, DESCRIPTION = ?,START_DATE = ?,END_DATE = ?,LOCKED_FOR_WRITING = ?,LOCKED_FOR_READING = ? WHERE FORUM_ID = ?";

			PreparedStatement ps = connection.prepareStatement(updateSql);
			ps.setString(1, forum.getTitle());
			ps.setString(2, forum.getDescription());

			long start = forum.getStart();
			long end = forum.getEnd();

			if (start > -1 && end > -1)
			{
				ps.setTimestamp(3, new Timestamp(start));
				ps.setTimestamp(4, new Timestamp(end));
			}
			else
			{
				ps.setNull(3, Types.NULL);
				ps.setNull(4, Types.NULL);
			}

			ps.setBoolean(5, forum.isLockedForWriting());
			ps.setBoolean(6, forum.isLockedForReading());

			ps.setString(7, forum.getId());
			
			statements.add(ps);
			
			PreparedStatement deleteGroupsStatement = connection.prepareStatement("DELETE FROM YAFT_FORUM_GROUP WHERE FORUM_ID = ?");
			deleteGroupsStatement.setString(1, forum.getId());
			
			statements.add(deleteGroupsStatement);
			
			for(Group group : forum.getGroups())
			{
				PreparedStatement addGroupsStatement = connection.prepareStatement("INSERT INTO YAFT_FORUM_GROUP VALUES(?,?)");
				addGroupsStatement.setString(1,forum.getId());
				addGroupsStatement.setString(2,group.getId());
				
				statements.add(addGroupsStatement);
			}
		}
		else
		{
			forum.setId(UUID.randomUUID().toString());

			String insertSql = "INSERT INTO YAFT_FORUM (FORUM_ID,SITE_ID,CREATOR_ID,TITLE,DESCRIPTION,START_DATE,END_DATE,LOCKED_FOR_WRITING,LOCKED_FOR_READING,STATUS,MESSAGE_COUNT,DISCUSSION_COUNT) VALUES(?,?,?,?,?,?,?,?,?,?,0,0)";

			long start = forum.getStart();
			long end = forum.getEnd();

			PreparedStatement ps = connection.prepareStatement(insertSql);
			ps.setString(1, forum.getId());
			ps.setString(2, forum.getSiteId());
			ps.setString(3, forum.getCreatorId());
			ps.setString(4, forum.getTitle());
			ps.setString(5, forum.getDescription());

			if (start > -1 && end > -1)
			{
				ps.setTimestamp(6, new Timestamp(start));
				ps.setTimestamp(7, new Timestamp(end));
			}
			else
			{
				ps.setNull(6, Types.NULL);
				ps.setNull(7, Types.NULL);
			}

			ps.setBoolean(8, forum.isLockedForWriting());
			ps.setBoolean(9, forum.isLockedForReading());

			ps.setString(10, forum.getStatus());
			
			statements.add(ps);
			
			for(Group group : forum.getGroups())
			{
				PreparedStatement addGroupsStatement = connection.prepareStatement("INSERT INTO YAFT_FORUM_GROUP VALUES(?,?)");
				addGroupsStatement.setString(1,forum.getId());
				addGroupsStatement.setString(2,group.getId());
				
				statements.add(addGroupsStatement);
			}
		}
		
		return statements;
	}

	public String getMessageSelectStatement(String messageId)
	{
		return "SELECT * FROM YAFT_MESSAGE WHERE MESSAGE_ID  = '" + messageId + "'";
	}

	public String getMessageAttachmentsSelectStatement(String messageId)
	{
		return "SELECT * FROM YAFT_MESSAGE_ATTACHMENTS WHERE MESSAGE_ID  = '" + messageId + "'";
	}

	/*
	 * public String getDiscussionsSelectStatement() { return getDiscussionsSelectStatement(null); }
	 */

	public String getDiscussionsSelectStatement(String forumId)
	{
		String sql = "SELECT YAFT_DISCUSSION.*,FORUM_ID" + " FROM YAFT_FORUM_DISCUSSION,YAFT_DISCUSSION";

		if (forumId != null)
		{
			sql += " WHERE YAFT_FORUM_DISCUSSION.FORUM_ID = '" + forumId + "'" + " AND YAFT_FORUM_DISCUSSION.DISCUSSION_ID = YAFT_DISCUSSION.DISCUSSION_ID";
		}
		else
			sql += " WHERE YAFT_FORUM_DISCUSSION.DISCUSSION_ID = YAFT_DISCUSSION.DISCUSSION_ID";

		return sql;
	}

	public List<PreparedStatement> getAddOrUpdateMessageStatements(String forumId, Message message, Connection connection) throws Exception {
		List<PreparedStatement> statements = new ArrayList<PreparedStatement>();

		if (message.getId().length() > 0) {
			// unisa change: CHange the created date on editing the message 
			String updateSql = "UPDATE YAFT_MESSAGE SET " + "SUBJECT = ?,CONTENT = ?,CREATED_DATE=? WHERE MESSAGE_ID = ?";

			PreparedStatement ps = connection.prepareStatement(updateSql);
			ps.setString(1, message.getSubject());
			ps.setString(2, message.getContent());
			ps.setTimestamp(3, new Timestamp(message.getCreatedDate()));
			ps.setString(4, message.getId());
            statements.add(ps);
         
	       statements.add(getDiscussionMessageDateStatement(message, connection));
		   statements.add(getForumMessageDateStatement(forumId, message, connection));
			
		}
		else {
			// This is a new message
			message.setId(UUID.randomUUID().toString());

			String insertSql = "INSERT INTO YAFT_MESSAGE (" + "MESSAGE_ID,";

			if (message.hasParent()) {
				insertSql += "PARENT_MESSAGE_ID,";
			}

			insertSql += "DISCUSSION_ID," + "SITE_ID," + "STATUS," + "SUBJECT," + "CONTENT," + "CREATOR_ID," + "CREATED_DATE) VALUES(?,";

			if (message.hasParent()) {
				insertSql += "?,";
			}

			insertSql += "?,?,?,?,?,?,?)";

			PreparedStatement insertMessagePS = connection.prepareStatement(insertSql);
			insertMessagePS.setString(1, message.getId());
			if (message.hasParent()) {
				// This is a reply

				insertMessagePS.setString(2, message.getParent());
				insertMessagePS.setString(3, message.getDiscussionId());
				insertMessagePS.setString(4, message.getSiteId());
				insertMessagePS.setString(5, message.getStatus());
				insertMessagePS.setString(6, message.getSubject());
				insertMessagePS.setString(7, message.getContent());
				insertMessagePS.setString(8, message.getCreatorId());
				insertMessagePS.setTimestamp(9, new Timestamp(message.getCreatedDate()));

				String childrenSql = "INSERT INTO YAFT_MESSAGE_CHILDREN VALUES(?,?)";

				PreparedStatement childrenPS = connection.prepareStatement(childrenSql);
				childrenPS.setString(1, message.getParent());
				childrenPS.setString(2, message.getId());
				statements.add(childrenPS);

				if (message.getStatus().equals("READY")) {
					statements.add(getIncrementDiscussionMessageCountStatement(message, connection));
					statements.add(getIncrementForumMessageCountStatement(forumId, message, connection));
				}
			}
			else {
				// This is a discussion

				// The fact that this message doesn't have a parent means that
				// it is a discussion, or 'top level message'
				insertMessagePS.setString(2, message.getId()); // TODO: Tidy this up. Confusion between getId and getDiscussionId
				insertMessagePS.setString(3, message.getSiteId());
				insertMessagePS.setString(4, "READY");
				insertMessagePS.setString(5, message.getSubject());
				insertMessagePS.setString(6, message.getContent());
				insertMessagePS.setString(7, message.getCreatorId());
				insertMessagePS.setTimestamp(8, new Timestamp(message.getCreatedDate()));

				insertSql = "INSERT INTO YAFT_DISCUSSION (DISCUSSION_ID,LAST_MESSAGE_DATE,MESSAGE_COUNT,STATUS,LOCKED_FOR_WRITING,LOCKED_FOR_READING) VALUES(?,?,1,'READY',0,0)";
				PreparedStatement discussionPS = connection.prepareStatement(insertSql);
				discussionPS.setString(1, message.getId());
				discussionPS.setTimestamp(2, new Timestamp(message.getCreatedDate()));

				statements.add(discussionPS);

				insertSql = "INSERT INTO YAFT_FORUM_DISCUSSION VALUES(?,?)";
				PreparedStatement forumDiscussionPS = connection.prepareStatement(insertSql);
				forumDiscussionPS.setString(1, forumId);
				forumDiscussionPS.setString(2, message.getId());
				statements.add(forumDiscussionPS);

				insertSql = "UPDATE YAFT_FORUM SET DISCUSSION_COUNT = DISCUSSION_COUNT + 1 WHERE FORUM_ID = ?";
				PreparedStatement forumDiscussionCountPS = connection.prepareStatement(insertSql);
				forumDiscussionCountPS.setString(1, forumId);
				statements.add(forumDiscussionCountPS);

				statements.add(getIncrementForumMessageCountStatement(forumId, message, connection));
			}

			statements.add(insertMessagePS);
		}

		for (Attachment attachment : message.getAttachments()) {
			String insertSql = "INSERT INTO YAFT_MESSAGE_ATTACHMENTS VALUES(?,?)";
			PreparedStatement attachmentPS = connection.prepareStatement(insertSql);
			attachmentPS.setString(1, message.getId());
			attachmentPS.setString(2, attachment.getResourceId());
			statements.add(attachmentPS);
		}

		return statements;
	}
	private PreparedStatement getDiscussionMessageDateStatement(Message message, Connection connection) throws SQLException
	{
		Statement st = null;
		ResultSet rs = null;
		String discussionId=null;

		try
		{
			st = connection.createStatement();
			// Get a count of the current read messages in this discussion
			rs = st.executeQuery("SELECT DISCUSSION_ID FROM YAFT_MESSAGE WHERE MESSAGE_ID  ='"+message.getId()+"'");
			rs.next();
			 discussionId = rs.getString("DISCUSSION_ID");
			rs.close();
		}catch(Exception e)
		{
			logger.error("Caught exception whilst selecting user ids from YAFT_READ_MESSAGES",e);
		}
		finally
		{
			try
			{
				if(st != null) st.close();
			}
			catch(Exception e1) {}
		}
		
		String insertSql = "UPDATE YAFT_DISCUSSION SET LAST_MESSAGE_DATE = ? WHERE DISCUSSION_ID = ?";
		PreparedStatement discussionMessageCountPS = connection.prepareStatement(insertSql);
		discussionMessageCountPS.setTimestamp(1, new Timestamp(message.getCreatedDate()));
		discussionMessageCountPS.setString(2, discussionId);
		return discussionMessageCountPS;
	}

	private PreparedStatement getForumMessageDateStatement(String forumId, Message message, Connection connection) throws SQLException
	{
		String insertSql = "UPDATE YAFT_FORUM SET LAST_MESSAGE_DATE = ? WHERE FORUM_ID = ?";
		PreparedStatement forumMessageCountPS = connection.prepareStatement(insertSql);
		forumMessageCountPS.setTimestamp(1, new Timestamp(message.getCreatedDate()));
		forumMessageCountPS.setString(2, forumId);
		return forumMessageCountPS;
	}

	private PreparedStatement getIncrementDiscussionMessageCountStatement(Message message, Connection connection) throws SQLException
	{
		String insertSql = "UPDATE YAFT_DISCUSSION SET MESSAGE_COUNT = MESSAGE_COUNT + 1,LAST_MESSAGE_DATE = ? WHERE DISCUSSION_ID = ?";
		PreparedStatement discussionMessageCountPS = connection.prepareStatement(insertSql);
		discussionMessageCountPS.setTimestamp(1, new Timestamp(message.getCreatedDate()));
		discussionMessageCountPS.setString(2, message.getDiscussionId());
		return discussionMessageCountPS;
	}

	private PreparedStatement getIncrementForumMessageCountStatement(String forumId, Message message, Connection connection) throws SQLException
	{
		String insertSql = "UPDATE YAFT_FORUM SET MESSAGE_COUNT = MESSAGE_COUNT + 1,LAST_MESSAGE_DATE = ? WHERE FORUM_ID = ?";
		PreparedStatement forumMessageCountPS = connection.prepareStatement(insertSql);
		forumMessageCountPS.setTimestamp(1, new Timestamp(message.getCreatedDate()));
		forumMessageCountPS.setString(2, forumId);
		return forumMessageCountPS;
	}

	public String getMessageChildrenSelectStatement(String id)
	{
		return "SELECT * FROM YAFT_MESSAGE WHERE MESSAGE_ID IN " + "(SELECT CHILD_MESSAGE_ID FROM YAFT_MESSAGE_CHILDREN WHERE MESSAGE_ID = '" + id + "')" + " ORDER BY CREATED_DATE";
	}

	public String getDiscussionSelectStatement(String discussionId)
	{
		return "SELECT YAFT_DISCUSSION.*,FORUM_ID" + " FROM YAFT_DISCUSSION,YAFT_FORUM_DISCUSSION" + " WHERE YAFT_DISCUSSION.DISCUSSION_ID = '" + discussionId + "' AND YAFT_DISCUSSION.DISCUSSION_ID = YAFT_FORUM_DISCUSSION.DISCUSSION_ID";
	}

	//unisa change: change to prepared statements
	public List<PreparedStatement> getDeleteForumStatements(String forumId,Connection conn) throws Exception
	{
		List<PreparedStatement> statements = new ArrayList<PreparedStatement>();
		
		PreparedStatement deleteMessagePs = conn.prepareStatement("DELETE FROM YAFT_MESSAGE WHERE DISCUSSION_ID IN (SELECT " + ColumnNames.DISCUSSION_ID + " FROM YAFT_FORUM_DISCUSSION WHERE FORUM_ID = '" + forumId + "')");
		statements.add(deleteMessagePs);	
		PreparedStatement deleteDiscussionPs = conn.prepareStatement("DELETE FROM YAFT_DISCUSSION WHERE DISCUSSION_ID IN (SELECT DISCUSSION_ID FROM YAFT_FORUM_DISCUSSION WHERE FORUM_ID = '" + forumId + "')");
		statements.add(deleteDiscussionPs);
		PreparedStatement deleteForumDiscussionPs = conn.prepareStatement("DELETE FROM YAFT_FORUM_DISCUSSION WHERE FORUM_ID = '" + forumId + "'");
		statements.add(deleteForumDiscussionPs);
		PreparedStatement deleteForumPs = conn.prepareStatement("DELETE FROM YAFT_FORUM WHERE FORUM_ID = '" + forumId + "'");
		statements.add(deleteForumPs);

		return statements;
	}

	//unisa change: change to prepared statements
	public List<PreparedStatement> getDeleteDiscussionStatements(String forumId, String discussionId, Connection conn) throws Exception
	{
		List<PreparedStatement> statements = new ArrayList<PreparedStatement>();
		
		PreparedStatement updtDiscussionCountPs = conn.prepareStatement("UPDATE YAFT_FORUM SET DISCUSSION_COUNT = DISCUSSION_COUNT - 1 WHERE FORUM_ID = '" + forumId + "' AND DISCUSSION_COUNT > 0");
		statements.add(updtDiscussionCountPs);
		PreparedStatement updtMessageCountPs = conn.prepareStatement("UPDATE YAFT_FORUM SET MESSAGE_COUNT = MESSAGE_COUNT - (SELECT MESSAGE_COUNT FROM YAFT_DISCUSSION WHERE DISCUSSION_ID = '" + discussionId + "') WHERE FORUM_ID = '" + forumId + "'");
		statements.add(updtMessageCountPs);
		//Unisa changes
		//statements.add("UPDATE YAFT_FORUM SET LAST_MESSAGE_DATE = (SELECT MAX(yd.LAST_MESSAGE_DATE) FROM YAFT_FORUM_DISCUSSION AS yfd,YAFT_DISCUSSION AS yd WHERE yfd.DISCUSSION_ID = yd.DISCUSSION_ID AND yfd.FORUM_ID = '" + forumId + "') WHERE FORUM_ID = '" + forumId + "'");
		//Unisa changes: prepared statement
		PreparedStatement updtLastMsgDatePs = conn.prepareStatement("UPDATE YAFT_FORUM SET LAST_MESSAGE_DATE = (SELECT MAX(yd.LAST_MESSAGE_DATE) FROM YAFT_FORUM_DISCUSSION yfd,YAFT_DISCUSSION yd WHERE yfd.DISCUSSION_ID = yd.DISCUSSION_ID AND yfd.FORUM_ID = '" + forumId + "') WHERE FORUM_ID = '" + forumId + "'");
		statements.add(updtLastMsgDatePs);
		PreparedStatement uptLastMsgDateNullPs = conn.prepareStatement("UPDATE YAFT_FORUM SET LAST_MESSAGE_DATE = NULL WHERE FORUM_ID = '" + forumId + "' AND MESSAGE_COUNT  = 0");			
		statements.add(uptLastMsgDateNullPs);
		PreparedStatement updtStatusPs = conn.prepareStatement("UPDATE YAFT_DISCUSSION SET STATUS = 'DELETED' WHERE DISCUSSION_ID = '" + discussionId + "'");
		statements.add(updtStatusPs);
		PreparedStatement updtMsgStatusPs = conn.prepareStatement("UPDATE YAFT_MESSAGE SET STATUS = 'DELETED' WHERE DISCUSSION_ID = '" + discussionId + "'");
		statements.add(updtMsgStatusPs);

		return statements;
	}
	
	//Unisa Changes: change to prepared statement
	public List<PreparedStatement> getClearDiscussionStatements(String forumId, String discussionId, Connection conn) throws Exception
	{
		List<PreparedStatement> statements = new ArrayList<PreparedStatement>();

		PreparedStatement updtMsgCountPs = conn.prepareStatement("UPDATE YAFT_FORUM SET MESSAGE_COUNT = MESSAGE_COUNT - (SELECT MESSAGE_COUNT FROM YAFT_DISCUSSION WHERE DISCUSSION_ID = '" + discussionId + "') + 1 WHERE FORUM_ID = '" + forumId + "'");
		statements.add(updtMsgCountPs);
		//unisa change: mysql changes in oracle
		//statements.add("UPDATE YAFT_FORUM SET LAST_MESSAGE_DATE = (SELECT MAX(yd.LAST_MESSAGE_DATE) FROM YAFT_FORUM_DISCUSSION AS yfd,YAFT_DISCUSSION AS yd WHERE yfd.DISCUSSION_ID = yd.DISCUSSION_ID AND yfd.FORUM_ID = '" + forumId + "') WHERE FORUM_ID = '" + forumId + "'");
		PreparedStatement updtLastMsgDatePs = conn.prepareStatement("UPDATE YAFT_FORUM SET LAST_MESSAGE_DATE = (SELECT MAX(yd.LAST_MESSAGE_DATE) FROM YAFT_FORUM_DISCUSSION yfd,YAFT_DISCUSSION yd WHERE yfd.DISCUSSION_ID = yd.DISCUSSION_ID AND yfd.FORUM_ID = '" + forumId + "') WHERE FORUM_ID = '" + forumId + "'");
		statements.add(updtLastMsgDatePs);
		PreparedStatement updtLastMsgDateNullPs = conn.prepareStatement("UPDATE YAFT_FORUM SET LAST_MESSAGE_DATE = NULL WHERE FORUM_ID = '" + forumId + "' AND MESSAGE_COUNT  = 0");
		statements.add(updtLastMsgDateNullPs);
		// Delete the child messages, not the topic starter
		PreparedStatement updtStatusPs = conn.prepareStatement("UPDATE YAFT_MESSAGE SET STATUS = 'DELETED' WHERE DISCUSSION_ID = '" + discussionId + "' AND PARENT_MESSAGE_ID IS NOT NULL");
		statements.add(updtStatusPs);
		PreparedStatement updtMsgCountDiscPs = conn.prepareStatement("UPDATE YAFT_DISCUSSION SET MESSAGE_COUNT = 1 WHERE DISCUSSION_ID = '" + discussionId + "'");
		statements.add(updtMsgCountDiscPs);
		PreparedStatement updtLastMsgDateDiscPs = conn.prepareStatement("UPDATE YAFT_DISCUSSION SET LAST_MESSAGE_DATE = (SELECT MAX(CREATED_DATE) FROM YAFT_MESSAGE WHERE STATUS <> 'DELETED' AND DISCUSSION_ID = '" + discussionId + "') WHERE DISCUSSION_ID = '" + discussionId + "'");
		statements.add(updtLastMsgDateDiscPs);

		return statements;
	}
	
	

	//unisa change: change from statement to prepared statement
	public List<PreparedStatement> getDeleteMessageStatements(Message message, String forumId,Connection conn) throws Exception
	   {
		List<PreparedStatement> statements = new ArrayList<PreparedStatement>();
		String messageId = message.getId();
		String discussionId = message.getDiscussionId();
	
		PreparedStatement deleteMessagePs = conn.prepareStatement("UPDATE YAFT_MESSAGE SET STATUS = 'DELETED' WHERE MESSAGE_ID  = '" + messageId + "'");
		statements.add(deleteMessagePs);
		
		if("READY".equals(message.getStatus()))
		{
			PreparedStatement updateForumPs = conn.prepareStatement("UPDATE YAFT_FORUM SET MESSAGE_COUNT = MESSAGE_COUNT - 1 WHERE FORUM_ID = '" + forumId + "'");
			statements.add(updateForumPs);
			PreparedStatement updateDiscussionPs = conn.prepareStatement("UPDATE YAFT_DISCUSSION SET MESSAGE_COUNT = MESSAGE_COUNT - 1 WHERE DISCUSSION_ID = '" + discussionId + "'");
			statements.add(updateDiscussionPs);
			PreparedStatement updateDiscussionDatePs = conn.prepareStatement("UPDATE YAFT_DISCUSSION SET LAST_MESSAGE_DATE = (SELECT MAX(CREATED_DATE) FROM YAFT_MESSAGE WHERE STATUS <> 'DELETED' AND DISCUSSION_ID = '" + discussionId + "') WHERE DISCUSSION_ID = '" + discussionId + "'");
			statements.add(updateDiscussionDatePs);
			PreparedStatement updateForumDatePs = conn.prepareStatement("UPDATE YAFT_FORUM SET LAST_MESSAGE_DATE = (SELECT MAX(yd.LAST_MESSAGE_DATE) FROM YAFT_FORUM_DISCUSSION yfd,YAFT_DISCUSSION yd WHERE yfd.DISCUSSION_ID = yd.DISCUSSION_ID AND yfd.FORUM_ID = '" + forumId + "') WHERE FORUM_ID = '" + forumId + "'");
			statements.add(updateForumDatePs);
		}
	
		Statement st = null;
		
		try
		{
			st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT USER_ID FROM YAFT_READ_MESSAGES WHERE MESSAGE_ID = '" + message.getId() + "'");
			
			PreparedStatement updateForumReadPs = null;			//to be used in while loop
			PreparedStatement updateDiscussionReadPs = null;	
			while(rs.next())
			{
				String userId = rs.getString("USER_ID");
				
				//re-assign in while loop and add to statements List
				updateForumReadPs = conn.prepareStatement("UPDATE YAFT_FORUM_READ SET NUMBER_READ = NUMBER_READ - 1 WHERE FORUM_ID = '" + forumId + "' AND USER_ID = '" + userId + "'");
				statements.add(updateForumReadPs);
				updateDiscussionReadPs = conn.prepareStatement("UPDATE YAFT_DISCUSSION_READ SET NUMBER_READ = NUMBER_READ - 1 WHERE DISCUSSION_ID = '" + message.getDiscussionId() + "' AND USER_ID = '" + userId + "'");
				statements.add(updateDiscussionReadPs);
			}
			
			rs.close();
		}
		catch(Exception e)
		{
			logger.error("Caught exception whilst selecting user ids from YAFT_READ_MESSAGES",e);
		}
		finally
		{
			try
			{
				if(st != null) st.close();
			}
			catch(Exception e1) {}
		}
		PreparedStatement deleteReadMessagesPs = conn.prepareStatement("DELETE FROM YAFT_READ_MESSAGES WHERE MESSAGE_ID = '" + message.getId() + "'");
		statements.add(deleteReadMessagesPs);
		
		return statements;
	}
	

	public String getShowMessageStatement(Message message)
	{
		return "UPDATE YAFT_MESSAGE SET STATUS = 'READY' WHERE MESSAGE_ID  = '" + message.getId() + "'";
	}

	//Unisa Changes: Change statements to prepared statements
	public List<PreparedStatement> getUndeleteMessageStatements(Message message, String forumId, Connection conn) throws Exception
	{
		String messageId = message.getId();
		String discussionId = message.getDiscussionId();
		List<PreparedStatement> statements = new ArrayList<PreparedStatement>();
		
		PreparedStatement updateStatusPs = conn.prepareStatement("UPDATE YAFT_MESSAGE SET STATUS = 'READY' WHERE MESSAGE_ID  = '" + messageId + "'");
		statements.add(updateStatusPs);
		PreparedStatement uptMsgCountPs = conn.prepareStatement("UPDATE YAFT_FORUM SET MESSAGE_COUNT = MESSAGE_COUNT + 1 WHERE FORUM_ID = '" + forumId + "'");
		statements.add(uptMsgCountPs);
		PreparedStatement uptMsgCountDiscPs = conn.prepareStatement("UPDATE YAFT_DISCUSSION SET MESSAGE_COUNT = MESSAGE_COUNT + 1 WHERE DISCUSSION_ID = '" + discussionId + "'");
		statements.add(uptMsgCountDiscPs);
		PreparedStatement updtLastMsgDatePs = conn.prepareStatement("UPDATE YAFT_DISCUSSION SET LAST_MESSAGE_DATE = (SELECT MAX(CREATED_DATE) FROM YAFT_MESSAGE WHERE STATUS <> 'DELETED' AND DISCUSSION_ID = '" + discussionId + "')  WHERE DISCUSSION_ID = '" + discussionId + "'");
		statements.add(updtLastMsgDatePs);
		//unisa change 
		//statements.add("UPDATE YAFT_FORUM SET LAST_MESSAGE_DATE = (SELECT MAX(yd.LAST_MESSAGE_DATE) FROM YAFT_FORUM_DISCUSSION AS yfd,YAFT_DISCUSSION AS yd WHERE yfd.DISCUSSION_ID = yd.DISCUSSION_ID AND yfd.FORUM_ID = '" + forumId + "') WHERE FORUM_ID = '" + forumId + "'");
		//Unisa Change: change to Prepared statement
		PreparedStatement updtLastMsgDateForumPs = conn.prepareStatement("UPDATE YAFT_FORUM SET LAST_MESSAGE_DATE = (SELECT MAX(yd.LAST_MESSAGE_DATE) FROM YAFT_FORUM_DISCUSSION yfd,YAFT_DISCUSSION yd WHERE yfd.DISCUSSION_ID = yd.DISCUSSION_ID AND yfd.FORUM_ID = '" + forumId + "') WHERE FORUM_ID = '" + forumId + "'");
		statements.add(updtLastMsgDateForumPs);
		
		return statements;
	}

	public String getDeleteAttachmentStatement(String resourceId, String messageId)
	{
		return "DELETE FROM YAFT_MESSAGE_ATTACHMENTS WHERE MESSAGE_ID = '" + messageId + "' AND RESOURCE_ID = '" + resourceId + "'";
	}

	public String getSelectForumContainingMessageStatement(String messageId)
	{
		return "SELECT FORUM_ID FROM YAFT_DISCUSSION,YAFT_FORUM_DISCUSSION" + " WHERE YAFT_DISCUSSION.DISCUSSION_ID = YAFT_FORUM_DISCUSSION.DISCUSSION_ID" + " AND YAFT_DISCUSSION.DISCUSSION_ID" + " IN (SELECT DISTINCT DISCUSSION_ID FROM YAFT_MESSAGE" + " WHERE MESSAGE_ID = '" + messageId + "')";
	}

	public String getSelectMessageReadStatement(String userId, String messageId)
	{
		return "SELECT * FROM YAFT_READ_MESSAGES WHERE USER_ID = '" + userId + "' AND MESSAGE_ID = '" + messageId + "'";
	}

	//Unisa Changes: change from statement to PreparedStatement
	public List<PreparedStatement> getMarkMessageReadStatements(String userId, String messageId, String forumId, String discussionId, Connection conn) throws SQLException
	{
		List<PreparedStatement> statements = new ArrayList<PreparedStatement>();
		PreparedStatement insertReadMsgPs = conn.prepareStatement("INSERT INTO YAFT_READ_MESSAGES (USER_ID,MESSAGE_ID) VALUES('" + userId + "','" + messageId + "')");
		statements.add(insertReadMsgPs);

		Statement st = null;
		ResultSet rs = null;

		try
		{
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM YAFT_FORUM_READ WHERE FORUM_ID = '" + forumId + "' AND USER_ID = '" + userId + "'");
			//Unisa Changes: Changed statement.add to prepared statement
			if (rs.next()){
				PreparedStatement updtNrReadPs = conn.prepareStatement("UPDATE YAFT_FORUM_READ SET NUMBER_READ = NUMBER_READ + 1 WHERE FORUM_ID = '" + forumId + "' AND USER_ID = '" + userId + "'");
				statements.add(updtNrReadPs);
			}
			else{
				PreparedStatement insertForumReadPs = conn.prepareStatement("INSERT INTO YAFT_FORUM_READ (FORUM_ID,USER_ID,NUMBER_READ) VALUES('" + forumId + "','" + userId + "',1)");
				statements.add(insertForumReadPs);
			}

			rs.close();

			rs = st.executeQuery("SELECT * FROM YAFT_DISCUSSION_READ WHERE DISCUSSION_ID = '" + discussionId + "' AND USER_ID = '" + userId + "'");
			if (rs.next()){
				PreparedStatement updtDiscReadPs = conn.prepareStatement("UPDATE YAFT_DISCUSSION_READ SET NUMBER_READ = NUMBER_READ + 1 WHERE DISCUSSION_ID = '" + discussionId + "' AND USER_ID = '" + userId + "'");
				statements.add(updtDiscReadPs);
			}
			else{
				PreparedStatement insertDiscReadPs = conn.prepareStatement("INSERT INTO YAFT_DISCUSSION_READ (DISCUSSION_ID,USER_ID,NUMBER_READ) VALUES('" + discussionId + "','" + userId + "',1)");
				statements.add(insertDiscReadPs);
			}
		}
		finally
		{
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
		}

		return statements;
	}

	//Unisa changes: change to prepared statements
	public List<PreparedStatement> getMarkMessageUnReadStatements(String userId, String messageId, String forumId, String discussionId, Connection conn) throws Exception
	{
		List<PreparedStatement> statements = new ArrayList<PreparedStatement>();
		
		PreparedStatement deleteReadMsgPs = conn.prepareStatement("DELETE FROM YAFT_READ_MESSAGES WHERE USER_ID = '" + userId + "' AND MESSAGE_ID = '" + messageId + "'");
		statements.add(deleteReadMsgPs);
		PreparedStatement updtNrReadPs = conn.prepareStatement("UPDATE YAFT_FORUM_READ SET NUMBER_READ = NUMBER_READ - 1 WHERE FORUM_ID = '" + forumId + "' AND USER_ID = '" + userId + "'");
		statements.add(updtNrReadPs);
		PreparedStatement updtNrReadDiscPs = conn.prepareStatement("UPDATE YAFT_DISCUSSION_READ SET NUMBER_READ = NUMBER_READ - 1 WHERE DISCUSSION_ID = '" + discussionId + "' AND USER_ID = '" + userId + "'");
		statements.add(updtNrReadDiscPs);
		
		return statements;
	}

	//Unisa Changes: change to prepared statements
	public List<PreparedStatement> getMarkDiscussionReadStatements(String userId, String discussionId, String forumId, Connection conn) throws SQLException
	{
		List<PreparedStatement> statements = new ArrayList<PreparedStatement>();

		Statement st = null;
		ResultSet rs = null;

		try
		{
			st = conn.createStatement();
			// Get a count of the current read messages in this discussion
			rs = st.executeQuery("SELECT COUNT(*) AS CURRENT_READ" + " FROM YAFT_READ_MESSAGES,YAFT_MESSAGE" + " WHERE YAFT_READ_MESSAGES.MESSAGE_ID = YAFT_MESSAGE.MESSAGE_ID" + " AND DISCUSSION_ID = '" + discussionId + "' AND USER_ID = '" + userId + "'");
			rs.next();
			int currentRead = rs.getInt("CURRENT_READ");
			rs.close();

			// Now delete the current read messages for this discussion
			PreparedStatement deleteReadMsgPs = conn.prepareStatement("DELETE FROM YAFT_READ_MESSAGES WHERE MESSAGE_ID IN (SELECT MESSAGE_ID FROM YAFT_MESSAGE WHERE DISCUSSION_ID = '" + discussionId + "') AND USER_ID = '" + userId + "'");
			statements.add(deleteReadMsgPs);

			rs = st.executeQuery("SELECT MESSAGE_ID FROM YAFT_MESSAGE WHERE DISCUSSION_ID = '" + discussionId + "'");
			int count = 0;
			PreparedStatement insertReadMsgPs = null;	//to be used in while loop
			
			while (rs.next())
			{
				//re-assign in while loop
				insertReadMsgPs = conn.prepareStatement("INSERT INTO YAFT_READ_MESSAGES (MESSAGE_ID,USER_ID) VALUES('" + rs.getString("MESSAGE_ID") + "','" + userId + "')");
				statements.add(insertReadMsgPs);
				count++;
			}
			rs.close();

			int diff = count - currentRead;

			rs = st.executeQuery("SELECT * FROM YAFT_FORUM_READ WHERE FORUM_ID = '" + forumId + "' AND USER_ID = '" + userId + "'");
			if (rs.next()){
				PreparedStatement updtNrReadPs = conn.prepareStatement("UPDATE YAFT_FORUM_READ SET NUMBER_READ = NUMBER_READ + " + diff + " WHERE FORUM_ID = '" + forumId + "' AND USER_ID = '" + userId + "'");
				statements.add(updtNrReadPs);
			}
			else{
				PreparedStatement insertForumReadPs = conn.prepareStatement("INSERT INTO YAFT_FORUM_READ (FORUM_ID,USER_ID,NUMBER_READ) VALUES('" + forumId + "','" + userId + "'," + diff + ")");
				statements.add(insertForumReadPs);
			}
			rs.close();

			rs = st.executeQuery("SELECT * FROM YAFT_DISCUSSION_READ WHERE DISCUSSION_ID = '" + discussionId + "' AND USER_ID = '" + userId + "'");
			if (rs.next()){
				PreparedStatement updtDiscReadPs = conn.prepareStatement("UPDATE YAFT_DISCUSSION_READ SET NUMBER_READ = NUMBER_READ + " + diff + " WHERE DISCUSSION_ID = '" + discussionId + "' AND USER_ID = '" + userId + "'");
				statements.add(updtDiscReadPs);
			}
			else{
				PreparedStatement insertDiscReadPs = conn.prepareStatement("INSERT INTO YAFT_DISCUSSION_READ (DISCUSSION_ID,USER_ID,NUMBER_READ) VALUES('" + discussionId + "','" + userId + "'," + diff + ")");
				statements.add(insertDiscReadPs);
			}
		}
		finally
		{
			if (st != null)
				st.close();
		}

		return statements;
	}

	public String getSelectReadMessageIds(String userId, String discussionId)
	{
		return "SELECT MESSAGE_ID FROM YAFT_MESSAGE WHERE DISCUSSION_ID = '" + discussionId + "' AND MESSAGE_ID IN (SELECT MESSAGE_ID FROM YAFT_READ_MESSAGES WHERE USER_ID = '" + userId + "')";
	}

	//Unisa Changes: change to prepared statements
	public List<PreparedStatement> getMoveDiscussionStatements(String discussionId, String currentForumId, String newForumId,Connection conn) throws Exception
	{
		List<PreparedStatement> statements = new ArrayList<PreparedStatement>();
		Statement st = null;
		Statement st1 = null;
		
		try
		{
			st = conn.createStatement();
			st1 = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM YAFT_FORUM_READ WHERE FORUM_ID = '" + currentForumId + "'");
			PreparedStatement updateNumberReadPs = null;	//to be used in while loop
			PreparedStatement insertNumberReadPs = null;
			
			while(rs.next())
			{
				String userId = rs.getString("USER_ID");
				int currentNumberRead = rs.getInt("NUMBER_READ");
				ResultSet rs1 = st1.executeQuery("SELECT * FROM YAFT_FORUM_READ WHERE FORUM_ID = '" + newForumId + "' AND USER_ID = '" + userId + "'");
				//Unisa Changes: change to prepared statements
				if (rs1.next()){
					updateNumberReadPs = conn.prepareStatement("UPDATE YAFT_FORUM_READ SET NUMBER_READ = NUMBER_READ + " + currentNumberRead + " WHERE FORUM_ID = '" + newForumId + "' AND USER_ID = '" + userId + "'");
					statements.add(updateNumberReadPs);
				}
				else{
					insertNumberReadPs = conn.prepareStatement("INSERT INTO YAFT_FORUM_READ (FORUM_ID,USER_ID,NUMBER_READ) VALUES('" + newForumId + "','" + userId + "'," + currentNumberRead + ")");
					statements.add(insertNumberReadPs);
				}

				rs1.close();
			}
		
			rs.close();
		
			PreparedStatement updateForumIdPs = conn.prepareStatement("UPDATE YAFT_FORUM_DISCUSSION SET FORUM_ID = '" + newForumId + "' WHERE DISCUSSION_ID = '" + discussionId + "'");
			statements.add(updateForumIdPs);
			PreparedStatement updateDiscussionCountPs = conn.prepareStatement("UPDATE YAFT_FORUM SET DISCUSSION_COUNT = DISCUSSION_COUNT - 1 WHERE FORUM_ID = '" + currentForumId + "'");
			statements.add(updateDiscussionCountPs);
			PreparedStatement updateMsgCountPs = conn.prepareStatement("UPDATE YAFT_FORUM SET MESSAGE_COUNT = MESSAGE_COUNT - (SELECT MESSAGE_COUNT FROM YAFT_DISCUSSION WHERE DISCUSSION_ID = '" + discussionId + "') WHERE FORUM_ID = '" + currentForumId + "'");
			statements.add(updateMsgCountPs);
			PreparedStatement updateNewDiscussionCountPs = conn.prepareStatement("UPDATE YAFT_FORUM SET DISCUSSION_COUNT = DISCUSSION_COUNT + 1 WHERE FORUM_ID = '" + newForumId + "'");
			statements.add(updateNewDiscussionCountPs);
			PreparedStatement updateNewMsgCountPs = conn.prepareStatement("UPDATE YAFT_FORUM SET MESSAGE_COUNT = MESSAGE_COUNT + (SELECT MESSAGE_COUNT FROM YAFT_DISCUSSION WHERE DISCUSSION_ID = '" + discussionId + "') WHERE FORUM_ID = '" + newForumId + "'");
			statements.add(updateNewMsgCountPs);
			PreparedStatement updateLastMsgDateNullPs = conn.prepareStatement("UPDATE YAFT_FORUM SET LAST_MESSAGE_DATE = NULL WHERE DISCUSSION_COUNT = 0 AND FORUM_ID = '" + currentForumId + "'");
			statements.add(updateLastMsgDateNullPs);
			PreparedStatement updateLastMsgDateNewPs = conn.prepareStatement("UPDATE YAFT_FORUM SET LAST_MESSAGE_DATE = (SELECT LAST_MESSAGE_DATE FROM YAFT_DISCUSSION WHERE DISCUSSION_ID = '" + discussionId + "') WHERE FORUM_ID = '" + newForumId + "' AND ((SELECT LAST_MESSAGE_DATE FROM YAFT_DISCUSSION WHERE DISCUSSION_ID = '" + discussionId + "') > LAST_MESSAGE_DATE OR LAST_MESSAGE_DATE IS NULL)");
			statements.add(updateLastMsgDateNewPs);
			//unisa change
			//statements.add("UPDATE YAFT_FORUM SET LAST_MESSAGE_DATE = (SELECT MAX(yd.LAST_MESSAGE_DATE) FROM YAFT_FORUM_DISCUSSION AS yfd,YAFT_DISCUSSION AS yd WHERE yfd.DISCUSSION_ID = yd.DISCUSSION_ID AND yfd.FORUM_ID = '" + currentForumId + "') WHERE FORUM_ID = '" + currentForumId + "'");
			PreparedStatement updateLastMsgDateCurrentPs = conn.prepareStatement("UPDATE YAFT_FORUM SET LAST_MESSAGE_DATE = (SELECT MAX(yd.LAST_MESSAGE_DATE) FROM YAFT_FORUM_DISCUSSION yfd,YAFT_DISCUSSION yd WHERE yfd.DISCUSSION_ID = yd.DISCUSSION_ID AND yfd.FORUM_ID = '" + currentForumId + "') WHERE FORUM_ID = '" + currentForumId + "'");
			statements.add(updateLastMsgDateCurrentPs);
		}
		catch(Exception e)
		{
			logger.error("Failed to get move discussion statements",e);
			
		}
		finally
		{
			try
			{
				if(st != null) st.close();
				if(st1 != null) st1.close();
			}
			catch(Exception e1) {}
		}
		
		return statements;
	}

	public String getSelectReadMessageCountForAllForaStatement(String userId)
	{
		return "SELECT FORUM_ID,NUMBER_READ FROM YAFT_FORUM_READ WHERE USER_ID = '" + userId + "'";
	}

	public PreparedStatement getSelectReadMessageCountForDiscussionStatement(String userId, Connection conn) throws SQLException
	{
		String sql = "SELECT NUMBER_READ FROM YAFT_DISCUSSION_READ WHERE USER_ID = '" + userId + "' AND DISCUSSION_ID = ?";
		return conn.prepareStatement(sql);
	}

	public String getSelectDiscussionIdsForForumStatement(String forumId)
	{
		return "SELECT DISCUSSION_ID FROM YAFT_FORUM_DISCUSSION WHERE FORUM_ID = '" + forumId + "'";
	}

	public String getMarkForumDeletedStatement(String forumId)
	{
		return "UPDATE YAFT_FORUM SET STATUS = 'DELETED' WHERE FORUM_ID = '" + forumId + "'";
	}

	public String getSelectForumIdForTitleStatement(String title, String siteId)
	{
		return "SELECT FORUM_ID FROM YAFT_FORUM WHERE TITLE = '" + title + "' AND SITE_ID = '" + siteId + "' AND STATUS <> 'DELETED'";
	}

	public PreparedStatement getSetDiscussionDatesStatement(Discussion discussion, Connection conn) throws Exception
	{
		PreparedStatement st = conn.prepareStatement("UPDATE YAFT_DISCUSSION SET START_DATE = ?,END_DATE = ?,LOCKED_FOR_WRITING = ?,LOCKED_FOR_READING = ?,GRADEBOOK_ASSIGNMENT_ID = ? WHERE DISCUSSION_ID = ?");

		long start = discussion.getStart();
		long end = discussion.getEnd();

		if (start > -1 && end > -1)
		{
			st.setTimestamp(1, new Timestamp(start));
			st.setTimestamp(2, new Timestamp(end));
		}
		else
		{
			st.setNull(1, Types.NULL);
			st.setNull(2, Types.NULL);
		}

		st.setBoolean(3, discussion.isLockedForWriting());
		st.setBoolean(4, discussion.isLockedForReading());
		
		YaftGBAssignment gradebookAssignment = discussion.getAssignment();
		
		if(gradebookAssignment != null) {
			st.setLong(5, gradebookAssignment.id);
		} else {
			st.setNull(5,Types.INTEGER);
		}

		st.setString(6, discussion.getId());

		return st;
	}

	public String getSelectActiveDiscussionsStatement()
	{
		return "SELECT * FROM YAFT_ACTIVE_DISCUSSIONS ORDER BY LAST_MESSAGE_DATE DESC";
	}

	public List<PreparedStatement> getAddNewMessageToActiveDiscussionsStatements(Message message, Connection connection) throws Exception
	{
		List<PreparedStatement> statements = new ArrayList<PreparedStatement>();

		Statement testST = null;
		try
		{
			testST = connection.createStatement();

			String sql = "INSERT INTO YAFT_ACTIVE_DISCUSSIONS (DISCUSSION_ID,SITE_ID,SUBJECT,LAST_MESSAGE_DATE,LATEST_MESSAGE_SUBJECT) VALUES(?,?,(SELECT SUBJECT FROM YAFT_MESSAGE WHERE MESSAGE_ID = ?),?,?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, message.getDiscussionId());
			statement.setString(2, message.getSiteId());
			statement.setString(3, message.getDiscussionId());
			statement.setTimestamp(4, new Timestamp(message.getCreatedDate()));
			statement.setString(5, message.getSubject());
			statements.add(statement);
			//Unisa changes
			//sql = "DELETE FROM YAFT_ACTIVE_DISCUSSIONS WHERE LAST_MESSAGE_DATE < " + ACTIVE_DISCUSSIONS_TTL;
			sql = "DELETE FROM YAFT_ACTIVE_DISCUSSIONS WHERE LAST_MESSAGE_DATE < (current_timestamp - INTERVAL '1' DAY)";
			PreparedStatement st2 = connection.prepareStatement(sql);
			statements.add(st2);
		}
		finally
		{
			if (testST != null)
				testST.close();
		}

		return statements;
	}

	public String getDeleteFromActiveDiscussionStatement(String discussionId)
	{
		return "DELETE FROM YAFT_ACTIVE_DISCUSSIONS WHERE DISCUSSION_ID = '" + discussionId + "'";
	}

	public String getSelectIdOfSiteContainingMessage(String messageId)
	{
		return "SELECT SITE_ID FROM YAFT_MESSAGE WHERE MESSAGE_ID = '" + messageId + "'";
	}

	public String getForumGroupsSelectStatement(String forumId)
	{
		return "SELECT YAFT_FORUM_GROUP.GROUP_ID,TITLE FROM YAFT_FORUM_GROUP,SAKAI_SITE_GROUP WHERE FORUM_ID = '" + forumId + "' and YAFT_FORUM_GROUP.GROUP_ID = SAKAI_SITE_GROUP.GROUP_ID";
	}
	
	public String getDiscussionGroupsSelectStatement(String discussionId)
	{
		return "SELECT YAFT_DISCUSSION_GROUP.GROUP_ID,TITLE FROM YAFT_DISCUSSION_GROUP,SAKAI_SITE_GROUP WHERE DISCUSSION_ID = '" + discussionId + "' and YAFT_DISCUSSION_GROUP.GROUP_ID = SAKAI_SITE_GROUP.GROUP_ID";
	}

	@Override
	public PreparedStatement getSelectSiteAuthors(String siteId,Connection conn) throws Exception{
		PreparedStatement st = conn.prepareStatement("SELECT CREATOR_ID,COUNT(*) NUMBER_OF_POSTS FROM YAFT_MESSAGE WHERE SITE_ID = ? GROUP BY CREATOR_ID");
		st.setString(1,siteId);
		return st;
	}

	@Override
	public PreparedStatement getSelectMessagesForAuthorInSite(String authorId, String siteId, Connection connection) throws Exception {
		PreparedStatement st = connection.prepareStatement("SELECT * FROM YAFT_MESSAGE WHERE SITE_ID = ? AND CREATOR_ID = ? ORDER BY CREATED_DATE");
		st.setString(1,siteId);
		st.setString(2,authorId);
		return st;
	}

	@Override
	public PreparedStatement getSelectDiscussionAuthors(String discussionId, Connection conn) throws Exception {
		PreparedStatement st = conn.prepareStatement("SELECT CREATOR_ID,COUNT(*) NUMBER_OF_POSTS FROM YAFT_MESSAGE WHERE DISCUSSION_ID = ? GROUP BY CREATOR_ID");
		st.setString(1,discussionId);
		return st;
	}
	
	@Override
	public PreparedStatement getSelectMessagesForAuthorInDiscussion(String authorId, String discussionId, Connection conn) throws Exception {
		PreparedStatement st = conn.prepareStatement("SELECT * FROM YAFT_MESSAGE WHERE DISCUSSION_ID = ? AND CREATOR_ID = ? ORDER BY CREATED_DATE");
		st.setString(1,discussionId);
		st.setString(2,authorId);
		return st;
	}

	@Override
	public List<PreparedStatement> getSetDiscussionGroupsStatements(Discussion discussion, Connection connection) throws Exception {
		List<PreparedStatement> statements = new ArrayList<PreparedStatement>();
		
		PreparedStatement deleteGroupsStatement = connection.prepareStatement("DELETE FROM YAFT_DISCUSSION_GROUP WHERE DISCUSSION_ID = ?");
		deleteGroupsStatement.setString(1, discussion.getId());
		statements.add(deleteGroupsStatement);
			
		for(Group group : discussion.getGroups()) {
			PreparedStatement addGroupsStatement = connection.prepareStatement("INSERT INTO YAFT_DISCUSSION_GROUP VALUES(?,?)");
			addGroupsStatement.setString(1,discussion.getId());
			addGroupsStatement.setString(2,group.getId());
				
			statements.add(addGroupsStatement);
		}
		
		return statements;
	}

	@Override
	public PreparedStatement getSelectSiteForaStatement(String siteId, Connection connection) throws Exception {
		PreparedStatement st = connection.prepareStatement("SELECT * FROM YAFT_FORUM WHERE SITE_ID = ?");
		st.setString(1,siteId);
		return st;
	}
} 