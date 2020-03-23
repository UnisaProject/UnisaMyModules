/**********************************************************************************
 * $URL$
 * $Id$
 ***********************************************************************************
 *
 * Copyright (c) 2003, 2004, 2005, 2006, 2007, 2008 The Sakai Foundation
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

package org.sakaiproject.discussionforums.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.sakaiproject.db.api.SqlService;
import org.sakaiproject.discussionforums.api.model.Forum;
import org.sakaiproject.discussionforums.api.model.ForumTopicDetails;
import org.sakaiproject.discussionforums.api.model.ForumMessage;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserNotDefinedException;
import org.sakaiproject.user.cover.UserDirectoryService;


public class DbDiscussionForumsService extends BaseDiscussionForumsService {
	/** Our log (commons). */
	private static Logger M_log = LoggerFactory.getLogger(DbDiscussionForumsService.class);

	/** Dependency: SqlService */
	protected SqlService m_sqlService = null;

	/**
	 * Dependency: SqlService.
	 * 
	 * @param service The SqlService.
	 */
	public void setSqlService(SqlService service) {
		m_sqlService = service;
	}

	protected Storage newStorage() {
		return new DbStorage();
	}

	/**
	 * Final initialization, once all dependencies are set.
	 */
	public void init() {
		try {

			super.init();
		} catch (Exception t) {
			M_log.warn("init(): ", t);
		}
	}

	protected class DbStorage implements Storage {
		
		/////// Forum Details Start ///////
		public List getForumList(String siteId, String sortby, String sortorder) {
			List results = new ArrayList();
			Connection dbConnection = null;
			ResultSet rs = null;
			sortby = "uf." + sortby;
			try {
				dbConnection = m_sqlService.borrowConnection();

				/* String statement = "select Forum_Id,Forum_Name,Description,User_Id,DATE_FORMAT(Creation_Date,'%Y-%m-%d %H:%i') as Creation_Date, "
						+ "Site_Id,DATE_FORMAT(Last_Post_Date,'%Y-%m-%d %H:%i') as Last_Post_Date,ifnull(Last_Post_User,'') as Last_Post_User "
						+ "from UFORUM_FORUM where SITE_ID = ? "
						+ "and Hide_Status = 'N' "
						+ " order by upper("+sortby+") "+sortorder; */
				
				String statement = "select uf.Forum_Id,Forum_Name,Description,User_Id,DATE_FORMAT(uf.Creation_Date,'%Y-%m-%d %H:%i') as Creation_Date, "
						+ "Site_Id,DATE_FORMAT(uf.Last_Post_Date,'%Y-%m-%d %H:%i') as Last_Post_Date,ifnull(uf.Last_Post_User,'') as Last_Post_User, "
						+ "(select count(Topic_Id) from uforum_topic where forum_id = uf.forum_id and Hide_Status = 'N') topicsCount, "
						+ "(select count(Message_Id) from uforum_topic ft, uforum_message fm where ft.topic_id = fm.topic_id and ft.forum_id = uf.forum_id and ft.Hide_Status = 'N' and fm.first_topic_msg = 'N') forumPosts "
						+ "from UFORUM_FORUM AS uf LEFT JOIN UFORUM_TOPIC AS ut "
						+ "on uf.Forum_Id = ut.Forum_Id "
						+ "where SITE_ID = ? "
						+ "and uf.Hide_Status = 'N' "
						+ "group by uf.Forum_Id "
						+ "order by upper("+sortby+") "+sortorder;
				PreparedStatement pstmt = dbConnection.prepareStatement(statement);
				pstmt.setString(1, siteId);
				rs = pstmt.executeQuery();
				pstmt.close();
				if (rs != null) {
					while (rs.next()) {
						Forum forum = new Forum();
						forum.setForumId(new Integer(rs.getInt("Forum_Id")));
						forum.setForumName(rs.getString("Forum_Name"));
						forum.setForumDescription(rs.getString("Description"));
						forum.setUserId(rs.getString("User_Id"));
						forum.setCreationDate(rs.getString("Creation_Date"));
						forum.setSiteId(rs.getString("Site_Id"));
						forum.setHideStatus("N");
						forum.setLastPostDate(rs.getString("Last_Post_Date"));
						forum.setLastPoster(rs.getString("Last_Post_User"));
						forum.setForumTopicsCount(new Integer(rs.getInt("topicsCount")));
						forum.setForumPosts(new Integer(rs.getInt("forumPosts")));
						results.add(forum);
					}
				}

			} catch (SQLException e) {
				M_log.error(this+" Error on getForumList for the site "+siteId+" error is "+e.getMessage());
				e.printStackTrace();
			} finally {
				m_sqlService.returnConnection(dbConnection);
			}
			return results;

		}
		
		public List getForumContent(Integer forumId) {
			List results = new ArrayList();
			Connection dbConnection = null;
			ResultSet rs = null;
			try {
				dbConnection = m_sqlService.borrowConnection();

				String statement = "select Forum_Id,Forum_Name,Description,User_Id,DATE_FORMAT(Creation_Date,'%Y-%m-%d %H:%i') as Creation_Date, "
						+ "Site_Id,DATE_FORMAT(Last_Post_Date,'%Y-%m-%d %H:%i') as Last_Post_Date,ifnull(Last_Post_User,'') as Last_Post_User "
						+ "from UFORUM_FORUM where Forum_Id = ? ";
				PreparedStatement pstmt = dbConnection.prepareStatement(statement);
				pstmt.setInt(1, forumId);
				rs = pstmt.executeQuery();
				pstmt.close();
				if (rs != null) {
					while (rs.next()) {
						Forum forum = new Forum();
						forum.setForumId(new Integer(rs.getInt("Forum_Id")));
						forum.setForumName(rs.getString("Forum_Name"));
						forum.setForumDescription(rs.getString("Description"));
						forum.setUserId(rs.getString("User_Id"));
						forum.setCreationDate(rs.getString("Creation_Date"));
						forum.setSiteId(rs.getString("Site_Id"));
						forum.setHideStatus("N");
						forum.setLastPostDate(rs.getString("Last_Post_Date"));
						forum.setLastPoster(rs.getString("Last_Post_User"));
						results.add(forum);
					}
				}

			} catch (SQLException e) {
				M_log.error(this+" Error on getForumContent for the forum "+forumId+" error is "+e.getMessage());
				e.printStackTrace();
			} finally {
				m_sqlService.returnConnection(dbConnection);
			}
			return results;

		}
		
		public void insertForum(Forum forum) {
			Connection dbConnection = null;
			ResultSet rs = null;
			try {
				dbConnection = m_sqlService.borrowConnection();
				String statement = "insert into UFORUM_FORUM (Forum_Id,Forum_Name,Description,User_Id,Creation_Date,Site_Id,Hide_Status) "
									+ "values(NULL,?,?,?,sysdate(),?,'N')";
				PreparedStatement pstmt = dbConnection.prepareStatement(statement);
				pstmt.setString(1, forum.getForumName());
				pstmt.setString(2, forum.getForumDescription());
				pstmt.setString(3, forum.getUserId());
				pstmt.setString(4, forum.getSiteId());
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				M_log.error(this+" Error on createForum for the Forum siteId "+forum.getSiteId()+" userID "+forum.getUserId()+ " and error "+e.getMessage());
				e.printStackTrace();
			} finally {
				m_sqlService.returnConnection(dbConnection);
			}
		}
		
		public void updateForum(String forumName, String forumDescription, Integer forumId) {
			Connection dbConnection = null;
			ResultSet rs = null;
			
			try {
				dbConnection = m_sqlService.borrowConnection();
				String statement = "update UFORUM_FORUM set Forum_Name = ?, Description = ? where Forum_Id = ? ";

				PreparedStatement pstmt = dbConnection.prepareStatement(statement);
				pstmt.setString(1, forumName);
				pstmt.setString(2, forumDescription);
				pstmt.setInt(3, forumId);
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				M_log.error(this+" Error on updateForum for the forum "+forumId+" error is "+e.getMessage());
				e.printStackTrace();
			} finally {
				m_sqlService.returnConnection(dbConnection);
			}
		}
		
		public void deleteForum(String siteId, Integer forumId) {
			Connection dbConnection = null;
			ResultSet rs = null;
			
			try {
				dbConnection = m_sqlService.borrowConnection();
				String statement = "update UFORUM_FORUM set Hide_Status = 'Y' where Site_Id = ? and Forum_Id = ? ";

				PreparedStatement pstmt = dbConnection.prepareStatement(statement);
				pstmt.setString(1, siteId);
				pstmt.setInt(2, forumId);
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				M_log.error(this+" Error on deleteForum for the forum "+forumId+" error is "+e.getMessage());
				e.printStackTrace();
			} finally {
				m_sqlService.returnConnection(dbConnection);
			}
		}
		
		public String getForumsPerSiteCounter(String siteId) {
			
			Object[] fields = new Object[1];
			fields[0] = siteId;
			String totalForumsForSite = "0";
			String statement = "select count(*) from UFORUM_FORUM where Site_Id = ? and Hide_Status = 'N'";
			List totalForumsForSites = m_sqlService.dbRead(statement, fields, null);

			if(!totalForumsForSites.isEmpty())
			{
				totalForumsForSite = totalForumsForSites.get(0).toString();
			}
			return totalForumsForSite;
		}
		
		public String getForumByName(String forumName, String siteId) {
			
			Object[] fields = new Object[2];
			fields[0] = forumName;
			fields[1] = siteId;
			String returnForumName = "";
			String statement = "select Forum_Name from UFORUM_FORUM where Forum_Name = ? and Site_Id = ? and Hide_Status = 'N'";
			List returnForumNames = m_sqlService.dbRead(statement, fields, null);

			if(!returnForumNames.isEmpty())
			{
				returnForumName = returnForumNames.get(0).toString();
			}
			return returnForumName;
		}
		
		public int getForumCount(String forumName, String siteId) {
			
			//String strSql = "select count(Forum_Name) from UFORUM_FORUM where Hide_Status = 'N' and Forum_Name = '" + forumName + "' and Site_Id = '" + siteId + "'";
			//int returnCount = (new Integer((String) m_sqlService.dbRead(strSql).iterator().next())).intValue();
			Object[] fields = new Object[2];
			fields[0] = forumName;
			fields[1] = siteId;
			
			int returnCount = 0;
			
			String statement = "select count(Forum_Name) from UFORUM_FORUM where Forum_Name = ? and Site_Id = ? and Hide_Status = 'N'";
			List returnCounts = m_sqlService.dbRead(statement, fields, null);

			if(!returnCounts.isEmpty())
			{
				returnCount = Integer.parseInt(returnCounts.get(0).toString());
			}
						
			return returnCount;
		}
		/////// Forum Details End ///////
		
		/////// Topic Details Start ///////
		public void insertTopic(ForumTopicDetails forumTopicDetails) {
			Connection dbConnection = null;
			Long topicId;
			try {
				dbConnection = m_sqlService.borrowConnection();
				String statement = "insert into UFORUM_TOPIC (Topic_Id,Forum_Id,Topic_Subject,Creation_Date,Modification_Date,View_Count,Hide_Status) "
									+ "values(NULL,?,?,sysdate(),sysdate(),1,'N')";
				PreparedStatement pstmt = dbConnection.prepareStatement(statement, PreparedStatement.RETURN_GENERATED_KEYS);
				pstmt.setInt(1, forumTopicDetails.getForumId());
				pstmt.setString(2, forumTopicDetails.getTopicTitle());
				
				int affectedRows = pstmt.executeUpdate();
				if (affectedRows == 0) {
					throw new SQLException("Creating new topic failed, no rows affected.");
				}
				
				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						topicId = generatedKeys.getLong(1);
						forumTopicDetails.setTopicId(topicId);
					}
					else {
						throw new SQLException("Creating new topic failed, no ID obtained.");
					}
				}
				pstmt.close();
				String topicAuthor = forumTopicDetails.getTopicAuthor();
                // User type = student(S)/lecturer(L)
                String authorUserType = "";
                /*
                * if the user string is an integer then it is a student "S"
                * if the user string is not a number then it is a lecturer
                * "S"
                */
                if (isInteger(topicAuthor)) {
					authorUserType = "S";
                } else {
					authorUserType = "L";
                }
				statement = "insert into UFORUM_MESSAGE (Message_Id,Topic_Id,Content,Creation_Date,User_Id,User_Identifier,Msg_Url,File_Type,First_Topic_MSG) "
									+ "values(NULL,?,?,sysdate(),?,?,NULL,NULL,'Y')";
				pstmt = dbConnection.prepareStatement(statement);
				pstmt.setLong(1, forumTopicDetails.getTopicId());
				pstmt.setString(2, forumTopicDetails.getTopicMessage());
				pstmt.setString(3, forumTopicDetails.getTopicAuthor());
				pstmt.setString(4, authorUserType);
				pstmt.executeUpdate();
				pstmt.close();
				
			} catch (SQLException e) {
				M_log.error(this+" Error on insertTopic for the topic of the forumId "+forumTopicDetails.getForumId()+ " and error "+e.getMessage());
				e.printStackTrace();
			} finally {
				m_sqlService.returnConnection(dbConnection);
			}
		}
		
		public void updateTopic(String topicName, Integer topicId) {
			Connection dbConnection = null;
			ResultSet rs = null;
			
			try {
				dbConnection = m_sqlService.borrowConnection();
				String statement = "UPDATE UFORUM_TOPIC SET TOPIC_SUBJECT =? WHERE TOPIC_ID =?";

				PreparedStatement pstmt = dbConnection.prepareStatement(statement);
				pstmt.setString(1, topicName);
				pstmt.setInt(2, topicId);
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				M_log.error(this+" Error on updateTopic for the topic "+topicId+" error is "+e.getMessage());
				e.printStackTrace();
			} finally {
				m_sqlService.returnConnection(dbConnection);
			}
		}
		
		public void deleteTopic(Integer topicId) {
			Connection dbConnection = null;
			ResultSet rs = null;
			
			try {
				dbConnection = m_sqlService.borrowConnection();
				String statement = "update UFORUM_TOPIC set Hide_Status = 'Y' where Topic_Id = ? ";

				PreparedStatement pstmt = dbConnection.prepareStatement(statement);
				pstmt.setInt(1, topicId);
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				M_log.error(this+" Error on deleteTopic for the topic "+topicId+" error is "+e.getMessage());
				e.printStackTrace();
			} finally {
				m_sqlService.returnConnection(dbConnection);
			}
		}
		
		public List getTopicContent(Integer topicId) {
			List results = new ArrayList();
			Connection dbConnection = null;
			ResultSet rs = null;
			try {
				dbConnection = m_sqlService.borrowConnection();

				String statement = "select Topic_Id, Forum_Id, Topic_Subject, DATE_FORMAT(Creation_Date,'%Y-%m-%d %H:%i') as Creation_Date, "
						+ "DATE_FORMAT(Modification_Date,'%Y-%m-%d %H:%i') as Modification_Date, View_Count, DATE_FORMAT(Last_Post_Date,'%Y-%m-%d %H:%i') as Last_Post_Date, ifnull(Last_Post_User,'') as Last_Post_User "
						+ "from UFORUM_TOPIC where Topic_Id = ? ";
				PreparedStatement pstmt = dbConnection.prepareStatement(statement);
				pstmt.setInt(1, topicId);
				rs = pstmt.executeQuery();
				pstmt.close();
				if (rs != null) {
					while (rs.next()) {
						ForumTopicDetails forumTopicDetails = new ForumTopicDetails();
						forumTopicDetails.setTopicId(new Long(rs.getLong("Topic_Id")));
						forumTopicDetails.setForumId(new Integer(rs.getInt("Forum_Id")));
						forumTopicDetails.setTopicTitle(rs.getString("Topic_Subject"));
						forumTopicDetails.setCreationDate(rs.getString("Creation_Date"));
						forumTopicDetails.setModificationDate(rs.getString("Modification_Date"));
						forumTopicDetails.setViewCounter(new Integer(rs.getInt("View_Count")));
						forumTopicDetails.setHideStatus("N");
						forumTopicDetails.setLastPostDate(rs.getString("Last_Post_Date"));
						forumTopicDetails.setLastPostUser(rs.getString("Last_Post_User"));
						results.add(forumTopicDetails);
					}
				}

			} catch (SQLException e) {
				M_log.error(this+" Error on getTopicContent for the topic "+topicId+" error is "+e.getMessage());
				e.printStackTrace();
			} finally {
				m_sqlService.returnConnection(dbConnection);
			}
			return results;

		}
		
		public List getTopics(Integer forumId, String sortby, String sortorder) {
			List results = new ArrayList();
			Connection dbConnection = null;
			ResultSet rs = null;
			String userCreatedTopic = "";
			User user = null;
			try {
				dbConnection = m_sqlService.borrowConnection();

				String statement = "select ft.Topic_Id, ft.Forum_Id, ft.Topic_Subject, ft.Creation_Date, ft.Modification_Date, ifnull(ft.View_Count,0) as View_Count, "
						+ "ft.Hide_Status, DATE_FORMAT(ft.Last_Post_Date,'%Y-%m-%d %H:%i') as Last_Post_Date, ft.Last_Post_User, fm.User_Id, "
						+ "(select count(Message_Id)-1 from UFORUM_MESSAGE where topic_id = fm.Topic_Id) replycount "
						+ "from UFORUM_TOPIC ft, UFORUM_MESSAGE fm "
						+ "where ft.Forum_Id = ? and ft.Topic_Id = fm.Topic_Id "
						+ "and ft.Hide_Status = 'N' "
						+ "and fm.First_Topic_MSG = 'Y' "
						+ "order by ft.Topic_Id, upper("+sortby+") "+sortorder;
				PreparedStatement pstmt = dbConnection.prepareStatement(statement);
				pstmt.setInt(1, forumId);
				rs = pstmt.executeQuery();
				pstmt.close();
				if (rs != null) {
					while (rs.next()) {
						ForumTopicDetails forumTopic = new ForumTopicDetails();
						forumTopic.setTopicId(new Long(rs.getLong("Topic_Id")));
						forumTopic.setForumId(new Integer(rs.getInt("Forum_Id")));
						forumTopic.setTopicTitle(rs.getString("Topic_Subject"));
						forumTopic.setCreationDate(rs.getString("Creation_Date"));
						forumTopic.setModificationDate(rs.getString("Modification_Date"));
						forumTopic.setViewCounter(new Integer(rs.getInt("View_Count")));
						forumTopic.setHideStatus("N");
						forumTopic.setLastPostDate(rs.getString("Last_Post_Date"));
						forumTopic.setLastPostUser(rs.getString("Last_Post_User"));
						forumTopic.setUserId(rs.getString("User_Id"));
						forumTopic.setReplies(new Integer(rs.getInt("replycount")));
						try	{
							user = UserDirectoryService.getUserByEid(rs.getString("User_Id"));
						} catch (Exception e) {	
							e.printStackTrace();}
						
						userCreatedTopic = user.getDisplayName();
						forumTopic.setTopicAuthor(userCreatedTopic);
						results.add(forumTopic);
					}
				}

			} catch (SQLException e) {
				M_log.error(this+" Error on getTopics for the forum "+forumId+" error is "+e.getMessage());
				e.printStackTrace();
			} finally {
				m_sqlService.returnConnection(dbConnection);
			}
			return results;

		}
		
		public int getTopicCount(String topicName, Integer forumId) {
			
			Object[] fields = new Object[2];
			fields[0] = topicName;
			fields[1] = forumId;
			
			int returnCount = 0;
			
			String statement = "select count(Topic_Subject) from UFORUM_TOPIC where Topic_Subject = ? and Forum_Id = ? and Hide_Status = 'N'";
			List returnCounts = m_sqlService.dbRead(statement, fields, null);

			if(!returnCounts.isEmpty())
			{
				returnCount = Integer.parseInt(returnCounts.get(0).toString());
			}
						
			return returnCount;
		}
		/////// Topic Details End ///////
		
		/////// Message Details Start ///////
		public void insertMessage(ForumMessage forumMessage) {
			Connection dbConnection = null;
			ResultSet rs = null;
			Long messageId;
			String lastPostUserName = "";
			User user = null;
			
			try {
				dbConnection = m_sqlService.borrowConnection();
				
				try	{
					user = UserDirectoryService.getUserByEid(forumMessage.getAuthor());
				}
				catch (Exception e)
				{e.printStackTrace();}
				
				lastPostUserName = user.getDisplayName();
				
				String statement = "insert into UFORUM_MESSAGE (Message_Id,Topic_Id,Content,Creation_Date,User_Id,User_Identifier,Msg_Url,File_Type,First_Topic_MSG) "
									+ "values(NULL,?,?,sysdate(),?,?,?,?,'N')";
				PreparedStatement pstmt = dbConnection.prepareStatement(statement, PreparedStatement.RETURN_GENERATED_KEYS);
				pstmt.setInt(1, forumMessage.getTopicId());
				pstmt.setString(2, forumMessage.getMessageReply());
				pstmt.setString(3, forumMessage.getAuthor());
				pstmt.setString(4, forumMessage.getUserType());
				pstmt.setString(5, forumMessage.getAttachment());
				pstmt.setString(6, forumMessage.getFileType());
				
				int affectedRows = pstmt.executeUpdate();
				if (affectedRows == 0) {
					throw new SQLException("Creating new message reply failed, no rows affected.");
				}
				
				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						messageId = generatedKeys.getLong(1);
						forumMessage.setMessageId(messageId);
					}
					else {
						throw new SQLException("Creating new message reply failed, no ID obtained.");
					}
				}
				
				pstmt.close();
				
				String statement1 = "update UFORUM_TOPIC set Last_Post_Date = sysdate() , Last_Post_user = ? Where Topic_ID = ? ";
				PreparedStatement pstmt1 = dbConnection.prepareStatement(statement1);
				pstmt1.setString(1, lastPostUserName);
				pstmt1.setInt(2, forumMessage.getTopicId());
				pstmt1.executeUpdate();
				pstmt1.close();
				
				String statement2 = "update UFORUM_FORUM set Last_Post_Date = sysdate() , Last_Post_user = ? Where Forum_ID = ? ";
				PreparedStatement pstmt2 = dbConnection.prepareStatement(statement2);
				pstmt2.setString(1, lastPostUserName);
				pstmt2.setInt(2, Integer.parseInt(forumMessage.getForumId()));
				pstmt2.executeUpdate();
				pstmt2.close();
				
			} catch (SQLException e) {
				M_log.error(this+" Error on insertMessage for the topic "+forumMessage.getTopicId()+ " userID " +forumMessage.getAuthor()+ " and error "+e.getMessage());
				e.printStackTrace();
			} finally {
				m_sqlService.returnConnection(dbConnection);
			}
		}
		
		public void deleteMessage(Integer messageId) {
			Connection dbConnection = null;
			ResultSet rs = null;
			
			try {
				dbConnection = m_sqlService.borrowConnection();
				String statement = "delete FROM UFORUM_MESSAGE WHERE Message_Id = ? ";

				PreparedStatement pstmt = dbConnection.prepareStatement(statement);
				pstmt.setInt(1, messageId);
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				M_log.error(this+" Error on deleteMessage for the message "+messageId+" error is "+e.getMessage());
				e.printStackTrace();
			} finally {
				m_sqlService.returnConnection(dbConnection);
			}
		}
		
		public List getMessageList(Integer topicId) {
			List results = new ArrayList();
			Connection dbConnection = null;
			ResultSet rs = null;
			String userCreatedMessage = "";
			User user = null;
			try {
				dbConnection = m_sqlService.borrowConnection();

				String statement = "SELECT Message_Id, Topic_Id, Content, DATE_FORMAT(Creation_Date,'%Y-%m-%d %H:%i:%s') as Creation_Date, User_Id, User_Identifier, "
						+ "ifnull(Msg_Url,' ') as Msg_Url, ifnull(File_Type,' ') as File_Type, first_topic_msg "
						+ "FROM UFORUM_MESSAGE WHERE Topic_Id = ? "
						+ "AND first_topic_msg = 'N' "
						+ "ORDER BY creation_date DESC";
				PreparedStatement pstmt = dbConnection.prepareStatement(statement);
				pstmt.setInt(1, topicId);
				rs = pstmt.executeQuery();
				pstmt.close();
				if (rs != null) {
					while (rs.next()) {
						ForumMessage forumMessage = new ForumMessage();
						forumMessage.setMessageId(new Long(rs.getLong("Message_Id")));
						forumMessage.setTopicId(new Integer(rs.getInt("Topic_Id")));
						forumMessage.setMessage(rs.getString("Content"));
						forumMessage.setMessageDate(rs.getString("Creation_Date"));
						forumMessage.setUserId(rs.getString("User_Id"));
						forumMessage.setUserType(rs.getString("User_Identifier"));
						forumMessage.setAttachment(rs.getString("Msg_Url"));
						forumMessage.setFileType(rs.getString("File_Type"));
						try	{
							user = UserDirectoryService.getUserByEid(rs.getString("User_Id"));
						} catch (Exception e) {	
							e.printStackTrace();}
						
						userCreatedMessage = user.getDisplayName();
						forumMessage.setAuthor(userCreatedMessage);
						results.add(forumMessage);
					}
				}

			} catch (SQLException e) {
				M_log.error(this+" Error on getMessageList for the topic "+topicId+" error is "+e.getMessage());
				e.printStackTrace();
			} finally {
				m_sqlService.returnConnection(dbConnection);
			}
			return results;

		}
		
		public ForumMessage getTopicPosting(Integer topicId) {
			List results = new ArrayList();
			Connection dbConnection = null;
			ResultSet rs = null;
			ForumMessage forumMessage = new ForumMessage();
			String userCreatedMessage = "";
			User user = null;
			try {
				dbConnection = m_sqlService.borrowConnection();

				String statement = "SELECT Message_Id, Topic_Id, Content, DATE_FORMAT(Creation_Date,'%Y-%m-%d %H:%i:%s') as Creation_Date, User_Id, User_Identifier, "
						+ "ifnull(Msg_Url,' ') as Msg_Url, ifnull(File_Type,' ') as File_Type, first_topic_msg "
						+ "FROM UFORUM_MESSAGE WHERE Topic_Id = ? "
						+ "AND first_topic_msg = 'Y'";
				PreparedStatement pstmt = dbConnection.prepareStatement(statement);
				pstmt.setInt(1, topicId);
				rs = pstmt.executeQuery();
				pstmt.close();
				if (rs != null) {
					while (rs.next()) {
						forumMessage.setMessageId(new Long(rs.getLong("Message_Id")));
						forumMessage.setTopicId(new Integer(rs.getInt("Topic_Id")));
						forumMessage.setMessage(rs.getString("Content"));
						forumMessage.setMessageDate(rs.getString("Creation_Date"));
						forumMessage.setUserId(rs.getString("User_Id"));
						forumMessage.setUserType(rs.getString("User_Identifier"));
						forumMessage.setAttachment(rs.getString("Msg_Url"));
						forumMessage.setFileType(rs.getString("File_Type"));
						try	{
							user = UserDirectoryService.getUserByEid(rs.getString("User_Id"));
						} catch (Exception e) {	
							e.printStackTrace();}
						userCreatedMessage = user.getDisplayName();
						forumMessage.setAuthor(userCreatedMessage);
						results.add(forumMessage);
					}
				}
				
				Iterator i = results.iterator();
				if (i.hasNext()){
					forumMessage = (ForumMessage) i.next();
				}

			} catch (SQLException e) {
				M_log.error(this+" Error on getTopicPosting for the topic "+topicId+" error is "+e.getMessage());
				e.printStackTrace();
			} finally {
				m_sqlService.returnConnection(dbConnection);
			}
			return forumMessage;

		}
		
		public ForumMessage getMessageDetail(Integer messageId) {
			List results = new ArrayList();
			Connection dbConnection = null;
			ResultSet rs = null;
			ForumMessage forumMessage = new ForumMessage();
			String userCreatedMessage = "";
			User user = null;
			try {
				dbConnection = m_sqlService.borrowConnection();

				String statement = "SELECT Message_Id, Topic_Id, Content, DATE_FORMAT(Creation_Date,'%Y-%m-%d %H:%i:%s') as Creation_Date, User_Id, User_Identifier, "
						+ "ifnull(Msg_Url,' ') as Msg_Url, ifnull(File_Type,' ') as File_Type "
						+ "FROM UFORUM_MESSAGE WHERE Message_Id = ? "
						+ "AND first_topic_msg = 'N'";
				PreparedStatement pstmt = dbConnection.prepareStatement(statement);
				pstmt.setInt(1, messageId);
				rs = pstmt.executeQuery();
				pstmt.close();
				if (rs != null) {
					while (rs.next()) {
						forumMessage.setMessageId(new Long(rs.getLong("Message_Id")));
						forumMessage.setTopicId(new Integer(rs.getInt("Topic_Id")));
						forumMessage.setMessage(rs.getString("Content"));
						forumMessage.setMessageDate(rs.getString("Creation_Date"));
						forumMessage.setUserId(rs.getString("User_Id"));
						forumMessage.setUserType(rs.getString("User_Identifier"));
						forumMessage.setAttachment(rs.getString("Msg_Url"));
						forumMessage.setFileType(rs.getString("File_Type"));
						try	{
							user = UserDirectoryService.getUserByEid(rs.getString("User_Id"));
						} catch (Exception e) {	
							e.printStackTrace();}
						userCreatedMessage = user.getDisplayName();
						forumMessage.setAuthor(userCreatedMessage);
						results.add(forumMessage);
					}
				}
				
				Iterator i = results.iterator();
				if (i.hasNext()){
					forumMessage = (ForumMessage) i.next();
				}

			} catch (SQLException e) {
				M_log.error(this+" Error on getMessageDetail for the message "+messageId+" error is "+e.getMessage());
				e.printStackTrace();
			} finally {
				m_sqlService.returnConnection(dbConnection);
			}
			return forumMessage;

		}
		/////// Message Details End ///////
		
		private boolean isInteger(String i) {
                try {
                        Integer.parseInt(i);
                        return true;
                } catch (NumberFormatException nfe) {
                        return false;
                }
        }
		
		public String getSakaiUserId(String eid) {
			Object[] fields = new Object[1];
			fields[0] = eid;
			String EID = null;
			String statement = "select * from sakai_user_id_map where eid = ?";
			List<Object> e_ids = m_sqlService.dbRead(statement, fields, null);

			for (Object e_id : e_ids) {
				EID = e_id.toString();
			}
			return EID;
		}
	}

}
