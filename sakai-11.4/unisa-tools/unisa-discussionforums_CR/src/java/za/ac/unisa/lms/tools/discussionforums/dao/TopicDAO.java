package za.ac.unisa.lms.tools.discussionforums.dao;

import java.sql.Types;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import za.ac.unisa.lms.db.SakaiDAO;

/**
 * DAO class for all topic related queries
 * 
 * @author SYzelle
 * 
 */
public class TopicDAO extends SakaiDAO {

	private boolean transactionSuccess = false;

	/**
	 * method to insert discussion topic and first message
	 * 
	 * @param forumTopicDetails
	 * @return transactionSuccess (boolean) was transaction a success -
	 *         true/false
	 */
	public boolean insertTopic(final ForumTopicDetails forumTopicDetails) {

		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(
				getDataSource());
		TransactionTemplate transactionTemplate = new TransactionTemplate(
				transactionManager);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.springframework.transaction.support.
			 * TransactionCallbackWithoutResult
			 * #doInTransactionWithoutResult(org.
			 * springframework.transaction.TransactionStatus)
			 */
			public void doInTransactionWithoutResult(
					TransactionStatus transactionStatus) {
				JdbcTemplate jdbcTemplate;
				try {
					jdbcTemplate = new JdbcTemplate(getDataSource());

					String insertTopicSql = "insert into UFORUM_TOPIC(Topic_Id,Forum_Id,Topic_Subject,Creation_Date,Modification_Date,View_Count,Hide_Status) values(?,?,?,sysdate,sysdate,1,'N')";
					String insertMessageSql = "insert into UFORUM_MESSAGE(Message_Id,Topic_Id,Content,Creation_Date,User_Id,User_Identifier,Msg_Url,File_Type) values(?,?,?,sysdate,?,?,?,?)";
					String selectTopicIdSql = "select uforum_topic_0.nextval as topicId from dual";

					String fileUrl = null;
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

					List topicIdList = jdbcTemplate
							.queryForList(selectTopicIdSql);
					String topicId = "";
					Iterator i = topicIdList.iterator();
					i = topicIdList.iterator();
					if (i.hasNext()) {
						ListOrderedMap data = (ListOrderedMap) i.next();
						if (data.get("topicId") == null) {
						} else {
							topicId = data.get("topicId").toString();
						}
					}

					jdbcTemplate.update(
							insertTopicSql,
							new Object[] { topicId,
									forumTopicDetails.getForumId(),
									forumTopicDetails.getTopicTitle() });
					String selectMessageIdSql = "select uforum_message_0.nextval as messageId from dual";
					List queryList1 = jdbcTemplate
							.queryForList(selectMessageIdSql);
					String messageId = "";
					Iterator j = queryList1.iterator();
					j = queryList1.iterator();
					if (j.hasNext()) {
						ListOrderedMap data = (ListOrderedMap) j.next();
						if (data.get("messageId") == null) {
						} else {
							messageId = data.get("messageId").toString();
						}
					}
					jdbcTemplate.update(insertMessageSql,
							new Object[] { messageId, topicId,
									forumTopicDetails.getTopicMessage(),
									forumTopicDetails.getTopicAuthor(),
									authorUserType, fileUrl, fileUrl });
					forumTopicDetails.setTopicId(Integer.parseInt(topicId));
				} catch (Exception e) {
					// use this to rollback exception in case of exception
					transactionStatus.setRollbackOnly();
					transactionSuccess = transactionStatus.isRollbackOnly();
				}finally {
					jdbcTemplate=null;
				}
			} // end public void doInTransactionWithoutResult(TransactionStatus
				// transactionStatus) {
		}); // transactionTemplate.execute(new
			// TransactionCallbackWithoutResult() {
		return transactionSuccess;
	}

	/**
	 * message to delete a topic from a discussion
	 * 
	 * @param forumTopicDetails
	 * @param activityBy
	 * @param ipAddress
	 * @return transactionSuccess (boolean value, was transaction delete of
	 *         topic a success (true or false))
	 */
	public boolean deleteTopic(final ForumTopicDetails forumTopicDetails,
			final String activityBy, final String ipAddress) {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(
				getDataSource());
		TransactionTemplate transactionTemplate = new TransactionTemplate(
				transactionManager);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.springframework.transaction.support.
			 * TransactionCallbackWithoutResult
			 * #doInTransactionWithoutResult(org.
			 * springframework.transaction.TransactionStatus)
			 */
			public void doInTransactionWithoutResult(
					TransactionStatus transactionStatus) {
				JdbcTemplate jdbcTemplate;
				try {
					jdbcTemplate = new JdbcTemplate(getDataSource());
					String transactionAction = "DELETE";
					String tableUforum_Topic = "UFORUM_TOPIC";
					String oldRecord = forumTopicDetails.getTopicId() + ", "
							+ forumTopicDetails.getForumId();
					String selectNextValLogId = "select uforum_log_s.nextval as logId from dual";
					List logIdList = jdbcTemplate
							.queryForList(selectNextValLogId);
					String logId = "";
					Iterator i = logIdList.iterator();
					i = logIdList.iterator();
					if (i.hasNext()) {
						ListOrderedMap data = (ListOrderedMap) i.next();
						if (data.get("logId") == null) {
						} else {
							logId = data.get("logId").toString();
						}
					}

					String insertUforumLogSQL = "insert into uforum_log (log_id, ACTIVITY_TIME, ACTION, FROM_TABLE, OLD_RECORD,ACITIVITY_BY, CLIENT_IPADDRESS,msg_content) values (?,sysdate,?,?,?,?,?,?)";

					StringBuilder sqlStringBuilder = new StringBuilder(
							"insert into uforum_log (log_id, ACTIVITY_TIME, ACTION, FROM_TABLE,");

					sqlStringBuilder
							.append(" OLD_RECORD,ACITIVITY_BY, CLIENT_IPADDRESS,msg_content) values (?,sysdate,?,?,?,?,?,?)");

					String updateUforumTopicHideSQL = " update UFORUM_TOPIC set Hide_Status = "
							+ "'"
							+ forumTopicDetails.getHideStatus()
							+ "'"
							+ " WHERE Topic_Id ="
							+ forumTopicDetails.getTopicId();
					jdbcTemplate.update(updateUforumTopicHideSQL);
					jdbcTemplate.update(
							insertUforumLogSQL,
							new Object[] { logId, transactionAction,
									tableUforum_Topic, oldRecord, activityBy,
									ipAddress,
									forumTopicDetails.getTopicTitle() });
				} catch (Exception e) {
					// use this to rollback exception in case of exception
					transactionStatus.setRollbackOnly();
					transactionSuccess = transactionStatus.isRollbackOnly();
				}finally {
					jdbcTemplate=null;
				}
			} // end of doInTransactionWithoutResult
		}); // end of transactionTemplate.execute(new
			// TransactionCallbackWithoutResult() {
		return transactionSuccess;
	} // end of deleteTopic

	/**
	 * method to update the discussion topic subject
	 * 
	 * @param topicName
	 * @param topicId
	 */
	public void editTopicName(String topicName, int topicId) {

		String updateTopicSubjectSQL = " UPDATE UFORUM_TOPIC SET TOPIC_SUBJECT =? WHERE TOPIC_ID =?";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		try {
			jdbcTemplate.update(updateTopicSubjectSQL, new Object[] {topicName,topicId});
		} catch (Exception ex) {

		}finally {
			jdbcTemplate=null;
		}
	} // end of public void editTopicName(String topicName, int topicId)

	/**
	 * method to update the view counter of a topic
	 * 
	 * @param topicId
	 * @throws Exception
	 */
	/*
	 * public void updateViewCounter(Integer topicId) throws Exception{ String
	 * updateViewCounterSQL =
	 * "update UFORUM_TOPIC set View_Count = View_Count + 1 where Topic_Id = "
	 * +topicId; try{ JdbcTemplate jdbcTemplate = new
	 * JdbcTemplate(getDataSource());
	 * jdbcTemplate.execute(updateViewCounterSQL); } catch (Exception ex) {
	 * throw new
	 * Exception("ForumAction: getForumLastPostDate Error occurred / "+ ex,ex);
	 * } } // end updateViewCounter(Integer topicId)
	 */

	/**
	 * Get the subject for a specific topic
	 * 
	 * @param topicId
	 * @return topicName (String)
	 * @throws Exception
	 */
	public String getTopicName(Integer topicId) throws Exception {
		String topicName;
		String selectTopicNameSQL = "select Topic_Subject from UFORUM_TOPIC where Topic_Id = "
				+ topicId;
		try {
			topicName = this.querySingleValue(selectTopicNameSQL,
					"Topic_Subject");
		} catch (Exception ex) {
			throw new Exception(
					"FORUMDAO Action: getTopicName Error occurred / " + ex, ex);
		}
		return topicName;
	}

	/**
	 * Get the number of topics for a specific forum
	 * 
	 * @param forumId
	 * @param siteId
	 * @return
	 * @throws Exception
	 */
	public String getTopicsCounter(Integer forumId, String siteId)
			throws Exception {
		String totalCount;
		String getNumberOfTopicsSQL = "select count(ft.Forum_Id) as TopicsTotal "
				+ "from UFORUM_FORUM ff,UFORUM_TOPIC ft "
				+ "where ff.Forum_Id = ft.Forum_Id "
				+ "and ft.Forum_Id ="
				+ forumId
				+ " "
				+ "and ff.Site_Id = '"
				+ siteId
				+ "' "
				+ "and ft.Hide_Status = 'N'";
		try {
			totalCount = this.querySingleValue(getNumberOfTopicsSQL,
					"TopicsTotal");
		} catch (Exception ex) {
			throw new Exception(
					"ForumAction: getTopicsTotals Error occurred / " + ex, ex);
		}
		return totalCount;
	}

	/**
	 * @param forumId
	 * @param topicId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public String getTopicLastPostUser(Integer forumId, Integer topicId,
			String type) throws Exception {
		String user = "";
		String selectLastPostUserSQL = "";
		if (type.equalsIgnoreCase("creator")) {
			selectLastPostUserSQL = "select fm.User_Id as User_Id  "
					+ "from UFORUM_TOPIC ft,UFORUM_MESSAGE fm "
					+ "where ft.Forum_Id =" + forumId + " "
					+ "and ft.Topic_Id = fm.Topic_Id " + "and fm.Topic_Id="
					+ topicId + " " + "and ft.Hide_Status = 'N' "
					+ "and fm.Creation_Date = (select min(fm1.Creation_Date) "
					+ "from UFORUM_TOPIC ft1,UFORUM_MESSAGE fm1 "
					+ "where ft1.Forum_Id =" + forumId + " "
					+ "and ft1.Topic_Id = fm1.Topic_Id " + "and fm1.Topic_Id="
					+ topicId + ")";
		} else {
			selectLastPostUserSQL = "select fm.User_Id as User_Id  "
					+ "from UFORUM_TOPIC ft,UFORUM_MESSAGE fm "
					+ "where ft.Forum_Id =" + forumId + " "
					+ "and ft.Topic_Id = fm.Topic_Id " + "and fm.Topic_Id="
					+ topicId + " " + "and ft.Hide_Status = 'N' "
					+ "and fm.Creation_Date = (select max(fm1.Creation_Date) "
					+ "from UFORUM_TOPIC ft1,UFORUM_MESSAGE fm1 "
					+ "where ft1.Forum_Id =" + forumId + " "
					+ "and ft1.Topic_Id = fm1.Topic_Id " + "and fm1.Topic_Id="
					+ topicId + ")";
		}
		JdbcTemplate jdbcTemplate;
		try {
			jdbcTemplate = new JdbcTemplate(getDataSource());
			List queryList = jdbcTemplate.queryForList(selectLastPostUserSQL);
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				user = data.get("User_Id").toString();
			}
		} catch (Exception ex) {
			throw new Exception(
					"ForumDAOImpl: getTopicLastPostUser Error occurred / " + ex, ex);
		}finally {
			jdbcTemplate=null;
		}
		return user;
	}

	/**
	 * select list of topics for a specific forum
	 * 
	 * @param forumId
	 * @param sortby
	 * @param sortorder
	 * @return topicList (List) - list of topics
	 */
	public List getTopics(Integer forumId, String sortby, String sortorder) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

		String selectListOfTopicsSQL = "select ft.Topic_Id, ft.Forum_Id, ft.Topic_Subject, ft.Creation_Date, ft.Modification_Date,nvl(ft.View_Count,0) as View_Count,"
				+ " ft.Hide_Status, TO_CHAR(ft.Last_Post_Date,'YYYY-MM-DD HH24:MI') as Last_Post_Date, ft.Last_Post_User,"
				+ " count(fm.Topic_Id) as messagesCount,"
				+ " (count(fm.Message_Id)-1) replycount"
				+ " from UFORUM_TOPIC ft, UFORUM_MESSAGE fm"
				+ " where ft.Forum_Id =?"
				+ " and ft.Topic_Id = fm.Topic_Id"
				+ " and ft.Hide_Status = 'N'"
				+ " group by  ft.Topic_Id, ft.Forum_Id, ft.Topic_Subject, ft.Creation_Date, "
				+ " ft.Modification_Date,nvl(ft.View_Count,0),"
				+ " ft.Hide_Status, TO_CHAR(ft.Last_Post_Date,'YYYY-MM-DD HH24:MI'), ft.Last_Post_User"
				+ " order by ft.Topic_Id, upper(" + sortby + ") " + sortorder;

		List topicList = jdbcTemplate.query(selectListOfTopicsSQL,
				new Object[] { forumId }, new int[] { Types.INTEGER },
				new TopicRowMapper());
		return topicList;
	}

	/**
	 * @param topicid
	 */
	/*
	 * public void rollbackTopic(Integer topicid){ JdbcTemplate jdt = new
	 * JdbcTemplate(getDataSource()); PreparedStatementCreator stmt =
	 * pstsmtCreatorFactoryTopicDelete.newPreparedStatementCreator( new
	 * Object[]{'C',topicid} ); jdt.update(stmt); }
	 */

	/**
	 * @param i
	 * @return
	 */
	private boolean isInteger(String i) {
		try {
			Integer.parseInt(i);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	private String querySingleValue(String query, String field) {

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList;
		String result = "";

		queryList = jdt.queryForList(query);
		Iterator i = queryList.iterator();
		i = queryList.iterator();
		if (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			if (data.get(field) == null) {
			} else {
				result = data.get(field).toString();
			}
		}
		return result;
	}
}
