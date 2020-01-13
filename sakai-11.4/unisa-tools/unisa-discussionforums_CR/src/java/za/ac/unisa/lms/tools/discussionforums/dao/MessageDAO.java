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
 * DAO class for all message related queries
 * 
 * @author SYzelle
 *
 */
public class MessageDAO extends SakaiDAO {
	private boolean transactionSuccess=false;
	
	/**
	 * @param forumMessage
	 * @return
	 */
	public boolean insertMessage(final ForumMessage forumMessage){
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(getDataSource());
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			/* (non-Javadoc)
			 * @see org.springframework.transaction.support.TransactionCallbackWithoutResult#doInTransactionWithoutResult(org.springframework.transaction.TransactionStatus)
			 */
			public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				JdbcTemplate jdbcTemplate;
				String messageId="";
				String lastPostUserName = "";
				OracleDAO oracleDAO = null;
				try{
					jdbcTemplate = new JdbcTemplate(getDataSource());
					oracleDAO = new OracleDAO();

					lastPostUserName = oracleDAO.getUserNames(forumMessage.getAuthor());
					String insertMessageSQL = "insert into UFORUM_MESSAGE(Message_d,Topic_Id,Content,Creation_Date,User_Id,User_Identifier,Msg_Url,File_Type)"+
							" values(?,?,?,sysdate,?,?,?,?)";
					String selectMessageNextValSQL = "select uforum_message_0.nextval as messageId from dual";
					List messageIdList = jdbcTemplate.queryForList(selectMessageNextValSQL);
					Iterator i = messageIdList.iterator();
					i = messageIdList.iterator();
					if (i.hasNext()) {
						ListOrderedMap messageIdData = (ListOrderedMap) i.next();
						if (messageIdData.get("messageId") == null){
						} else {
							messageId = messageIdData.get("messageId").toString();
						}
					}

					jdbcTemplate.update(insertMessageSQL, new Object[] {messageId,forumMessage.getTopicId(),forumMessage.getMessageReply(),forumMessage.getAuthor(),forumMessage.getUserType(),forumMessage.getAttachment(),forumMessage.getFileType()});
					String updateTopic = "update UFORUM_TOPIC set Last_Post_Date = sysdate , Last_Post_user =? Where Topic_ID = ?";			
					String updateForum = "update UFORUM_FORUM set Last_Post_Date = sysdate , Last_Post_user =? Where Forum_ID = ?";
					jdbcTemplate.update(updateTopic, new Object[] {lastPostUserName,forumMessage.getTopicId()});
					jdbcTemplate.update(updateForum, new Object[] {lastPostUserName,forumMessage.getForumId()});
					forumMessage.setMessageId(Integer.parseInt(messageId));			    
				}catch (Exception e) {
					//use this to rollback the data in case of exception
					transactionStatus.setRollbackOnly();
					transactionSuccess=transactionStatus.isRollbackOnly();
				}finally {
					oracleDAO=null;
					jdbcTemplate=null;
				}
			} // end of public void doInTransactionWithoutResult(TransactionStatus transactionStatus)
		});
		return transactionSuccess;
	} // end of public boolean insertMessages(final ForumMessage forumMessage)
	
	/**
	 * select all messages for a specific topic 
	 * @param topicId
	 * @return
	 */
	public ForumMessage getTopicPosting(Integer topicId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		ForumMessage forumMessage = new ForumMessage();
		String selectMessagesSQL = "select Message_Id,Topic_Id,Content,TO_CHAR(Creation_Date,'YYYY-MM-DD HH24:MI') as Creation_Date,User_Id,User_Identifier,nvl(Msg_Url,'') as Msg_Url,nvl(File_Type,' ') as File_Type " +
				"from UFORUM_MESSAGE " +
				"where Topic_Id = ? " +
				"and Message_Id = (select min(Message_Id) " +
									"from UFORUM_MESSAGE where Topic_Id = ?)";
		List messagesList = jdbcTemplate.query(selectMessagesSQL,
				new Object[] { topicId ,topicId},
				new int[] { Types.INTEGER,Types.INTEGER },
						new MessageRowMapper());
		Iterator i = messagesList.iterator();
		if (i.hasNext()){
			forumMessage = (ForumMessage) i.next();
		}
		return forumMessage;
	}
	
	/**
	 * delete message from a topic and create a log file
	 * @param forumMessage
	 * @param activityBy
	 * @param ipAddress
	 * @return
	 * @throws Exception
	 */
	public boolean deleteMessage(final ForumMessage forumMessage, final String activityBy, final String ipAddress) throws Exception {

		String messageId=getInitialMessage(forumMessage.getTopicId());
		Integer initialId= Integer.parseInt(messageId);
		if(!(initialId==forumMessage.getMessageId())){
			DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(getDataSource());
			TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
					JdbcTemplate jdbcTemplate;
					String oldRecord="";
					try{
						jdbcTemplate = new JdbcTemplate(getDataSource());
						String transactionAction="DELETE";
						String tableUforum_Message="UFORUM_MESSAGE";
						oldRecord = forumMessage.getMessageId()+","+forumMessage.getTopicId()+","+forumMessage.getAuthor();
						String selectNextValLogId = "select uforum_log_s.nextval as logId from dual";
						List logIdList = jdbcTemplate.queryForList(selectNextValLogId);
						String logId="";
						Iterator i = logIdList.iterator();
						i = logIdList.iterator();
						if (i.hasNext()) {
							ListOrderedMap data = (ListOrderedMap) i.next();
							if (data.get("logId") == null){
							} else {
								logId = data.get("logId").toString();
							}
						}
						String insertUforumLogSQL ="insert into uforum_log (log_id, ACTIVITY_TIME, ACTION, FROM_TABLE,"+
								" OLD_RECORD,ACITIVITY_BY, CLIENT_IPADDRESS,msg_content) values (?,sysdate,?,?,?,?,?,?)";
						String deleteMessage = "delete FROM UFORUM_MESSAGE WHERE Message_Id =?";
						jdbcTemplate.update(deleteMessage,new Object[] {forumMessage.getMessageId()});
						jdbcTemplate.update(insertUforumLogSQL,new Object[] {logId,transactionAction,tableUforum_Message, oldRecord,activityBy,ipAddress,forumMessage.getMessage() });

					}catch (Exception e) {
						//use this to rollback exception in case of exception
						transactionStatus.setRollbackOnly();
						transactionSuccess= transactionStatus.isRollbackOnly();
					}finally {

						jdbcTemplate=null;
					}
				}
			});
		}else{
			transactionSuccess=true;
		}
		return transactionSuccess;
	}
	
	
	/**
	 * @param topicId
	 * @return
	 * @throws Exception
	 */
	public String getInitialMessage(int topicId) throws Exception{
		String intialMessage;
		String selectInitialMessageSQL ="select m.message_id as messageId from uforum_message m"+
                    " inner join ("+
                    " select  min(CREATION_DATE) as mindate"+
                    " from uforum_message where TOPIC_ID="+topicId+
                    " )st on  m.CREATION_DATE = st.mindate";

		try{
			intialMessage = this.querySingleValue(selectInitialMessageSQL,"messageId");
		} catch (Exception ex) {
            throw new Exception("ForumAction: getintialMessage Error occurred / "+ ex,ex);
		}
       return intialMessage;
	}
	
	/**
	 * get message detail for a specific message id
	 * @param messageId
	 * @return
	 */
	public ForumMessage getMessageDetail(Integer messageId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		ForumMessage forumMessage = new ForumMessage();
		String selectMessageSQL = "select Message_Id,Topic_Id,Content,TO_CHAR(Creation_Date,'YYYY-MM-DD HH24:MI') as Creation_Date,User_Id,User_Identifier,nvl(Msg_Url,' ') as Msg_Url,nvl(File_Type,' ') as File_Type " +
				"from UFORUM_MESSAGE " +
				"where Message_Id = ?";
		List messageDetailList = jdbcTemplate.query(selectMessageSQL,
				new Object[] { messageId},
				new int[] { Types.INTEGER },
						new MessageRowMapper());
		Iterator i = messageDetailList.iterator();
		if (i.hasNext()){
			forumMessage = (ForumMessage) i.next();
		}
		return forumMessage;
	} // end of public ForumMessage getMessageDetail(Integer messageId)
	
	
	/**
	 * select all messages for a specific topic except initial message
	 * 
	 * @param topicId
	 * @return
	 */
	public List getMessageList(Integer topicId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		String selectMessagesSQL = "select Message_Id,Topic_Id,Content,TO_CHAR(Creation_Date,'YYYY-MM-DD HH24:MI') as Creation_Date,User_Id,User_Identifier,nvl(Msg_Url,'') as Msg_Url,nvl(File_Type,' ') as File_Type " +
				"from UFORUM_MESSAGE " +
				"where Topic_Id = ? " +
				"and Message_Id <> (select min(Message_Id) from UFORUM_MESSAGE where Topic_Id = ? ) " +
				"order by Creation_Date DESC";
		List messagesList = jdbcTemplate.query(selectMessagesSQL,
				new Object[] { topicId,topicId }, new int[] { Types.INTEGER,Types.INTEGER},
				new MessageRowMapper());
		return messagesList;
	}

	
	/**
	 * @param query
	 * @param field
	 * @return
	 */
	private String querySingleValue(String query, String field){

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
    	List queryList;
    	String result = "";

 	   queryList = jdt.queryForList(query);
 	   Iterator i = queryList.iterator();
 	   i = queryList.iterator();
 	   if (i.hasNext()) {
			 ListOrderedMap data = (ListOrderedMap) i.next();
			 if (data.get(field) == null){
			 } else {
				 result = data.get(field).toString();
			 }
 	   }
 	   return result;
	}
}
