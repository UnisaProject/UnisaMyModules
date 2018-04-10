package za.ac.unisa.lms.tools.discussionforums.dao.impl;

import java.sql.Types;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import za.ac.unisa.lms.db.SakaiDAO;
import za.ac.unisa.lms.tools.discussionforums.dao.ForumMessage;
import za.ac.unisa.lms.tools.discussionforums.dao.MessageDao;
import za.ac.unisa.lms.tools.discussionforums.dao.MessageRowMapper;
import za.ac.unisa.lms.tools.discussionforums.dao.OracleDAO;

/**
 * DAO class for all message related queries
 * 
 * @author SYzelle
 *
 */
public class MessageDAOImpl extends SakaiDAO implements MessageDao {
	
	//private PreparedStatementCreatorFactory pstsmtCreatorFactoryMessage;
	private boolean transactionSuccess=false;
	protected static Logger logger = Logger.getLogger(MessageDAOImpl.class);
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
				String lastPostUserName = "";
				transactionSuccess=false;
				OracleDAO oracleDAO = null;
				try{
					jdbcTemplate = new JdbcTemplate(getDataSource());
					oracleDAO = new OracleDAO();
					String intialMessage="N";
					lastPostUserName = oracleDAO.getUserNames(forumMessage.getAuthor());
					StringBuilder insertMessage = new StringBuilder("insert into UFORUM_MESSAGE(Message_Id,Topic_Id,Content,Creation_Date,User_Id,User_Identifier,Msg_Url,File_Type, First_Topic_Msg)" );
					insertMessage.append("values(UFORUM_MESSAGE_0.nextval,?,?,sysdate,?,?,?,?,?)");	
					PreparedStatementCreatorFactory pstsmtCreatorFactoryMessage = new PreparedStatementCreatorFactory(insertMessage.toString(),
							new int[] {Types.INTEGER,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.VARCHAR});

					pstsmtCreatorFactoryMessage.setGeneratedKeysColumnNames(new String[]{"Message_Id"});
					pstsmtCreatorFactoryMessage.setReturnGeneratedKeys(true);

					PreparedStatementCreator psCreatorMessageInsert = pstsmtCreatorFactoryMessage.newPreparedStatementCreator(
							new Object[]{forumMessage.getTopicId(),forumMessage.getMessageReply(),forumMessage.getAuthor(),forumMessage.getUserType(),forumMessage.getAttachment(),forumMessage.getFileType(),intialMessage}
							);
				
					GeneratedKeyHolder MessageKeyHolder = new GeneratedKeyHolder();
					jdbcTemplate.update(psCreatorMessageInsert,MessageKeyHolder);
					PreparedStatementCreatorFactory updateTopic = new PreparedStatementCreatorFactory( "update UFORUM_TOPIC set Last_Post_Date = sysdate , Last_Post_user =? Where Topic_ID = ?",
							new int[] {Types.VARCHAR,Types.INTEGER});
					PreparedStatementCreatorFactory updateForum = new PreparedStatementCreatorFactory("update UFORUM_FORUM set Last_Post_Date = sysdate , Last_Post_user =? Where Forum_ID = ?",
							new int[] {Types.VARCHAR,Types.INTEGER});
					
					PreparedStatementCreator psUpdateTopic = updateTopic.newPreparedStatementCreator(
							new Object[]{lastPostUserName,forumMessage.getTopicId()});
					
					PreparedStatementCreator psUpdateForum = updateForum.newPreparedStatementCreator(
							new Object[]{lastPostUserName,Integer.parseInt(forumMessage.getForumId())});
										
					jdbcTemplate.update(psUpdateTopic);
					jdbcTemplate.update(psUpdateForum);
					Integer messageKey = MessageKeyHolder.getKey().intValue();
					forumMessage.setMessageId(messageKey);		
		
				}catch (Exception e) {
					//use this to rollback the data in case of exception
					logger.debug("DISCUSSIONFORUMS::FAILED_TO_ADD_MESSAGE - [TOPIC ID - "+forumMessage.getTopicId()+"]");
					e.printStackTrace();
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
	public ForumMessage getTopicPosting(Integer topicId)
	{
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		ForumMessage forumMessage = new ForumMessage();
		
		/*Commented out by mphahsm on 2015/11. This query will not return correct first topic message if the sequence number returned were not in order
		StringBuilder selectMessagesSQL = new StringBuilder("select Message_Id,Topic_Id,Content,TO_CHAR(Creation_Date,'YYYY-MM-DD HH24:MI:SS') as Creation_Date,User_Id,User_Identifier,nvl(Msg_Url,'') as Msg_Url,nvl(File_Type,' ') as File_Type ");
		selectMessagesSQL.append(" from UFORUM_MESSAGE where Topic_Id = ? ");
		selectMessagesSQL.append(" and Message_Id = (select min(Message_Id) from UFORUM_MESSAGE where Topic_Id = ?)");
		*/
		
		//Added by mphahsm on 2015/11 to return exact first topic message irrespective of sequence order
		StringBuilder selectMessagesSQL = new StringBuilder("SELECT Message_Id,Topic_Id,Content,TO_CHAR(Creation_Date,'YYYY-MM-DD HH24:MI:SS') as Creation_Date,User_Id,User_Identifier,nvl(Msg_Url,'') as Msg_Url,nvl(File_Type,' ') as File_Type,first_topic_msg ");
		selectMessagesSQL.append(" FROM uforum_message WHERE Topic_Id = ? AND first_topic_msg = 'Y'");
		//End of mphahsm Add
		
		long beforeTime = System.currentTimeMillis();
		
		PreparedStatementCreatorFactory selectMessages = new PreparedStatementCreatorFactory(selectMessagesSQL.toString(),
				new int[] {Types.INTEGER});
		PreparedStatementCreator psSelectMessages = selectMessages.newPreparedStatementCreator(
				new Object[] {topicId});
		
		List messagesList;
		
	    try
	    {
		 messagesList = jdbcTemplate.query(psSelectMessages,new MessageRowMapper());
	    } 
	    catch (EmptyResultDataAccessException ex) {
			return null; 
	    }
	    		
	    Iterator i = messagesList.iterator();
		if (i.hasNext()){
			forumMessage = (ForumMessage) i.next();
		}
		
		long afterTime = System.currentTimeMillis();
		long executionTime = afterTime-beforeTime;
		logger.info("DISCUSSIONFORUMS::SQL_EXECUTION_TIME : "+executionTime+"- [In the getTopicPosting() method.]");
		
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
		if (!(messageId == null)) //Added by mphahsm on 2015/11
		{
			Integer initialId= Integer.parseInt(messageId);
			if(!(initialId==forumMessage.getMessageId()))
			{
				DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(getDataSource());
				TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
				transactionTemplate.execute(new TransactionCallbackWithoutResult() {
					public void doInTransactionWithoutResult(TransactionStatus transactionStatus) 
					{
						JdbcTemplate jdbcTemplate;
						transactionSuccess=false;
						String oldRecord="";
						
						try
						{
							jdbcTemplate = new JdbcTemplate(getDataSource());
							String transactionAction="DELETE";
							String tableUforum_Message="UFORUM_MESSAGE";
							oldRecord = forumMessage.getMessageId()+","+forumMessage.getTopicId()+","+forumMessage.getAuthor();
							
							/* Commented out by mphahsm on 2015/11: The trigger UFORUM_MESSAGE_DELETELOG_TRG is taking care of this and it creates a duplication of records
							StringBuilder insertUforumLogSQL = new StringBuilder("insert into uforum_log (log_id, ACTIVITY_TIME, ACTION, FROM_TABLE,");
							insertUforumLogSQL.append(" OLD_RECORD,ACITIVITY_BY, CLIENT_IPADDRESS,msg_content) values (uforum_log_s.nextval,sysdate,?,?,?,?,?,?)");
							
							PreparedStatementCreatorFactory psInsertLog = new PreparedStatementCreatorFactory(insertUforumLogSQL.toString(),
									new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.CLOB}
									);
							
							psInsertLog.setGeneratedKeysColumnNames(new String[]{"log_id"});
							psInsertLog.setReturnGeneratedKeys(true);
							PreparedStatementCreator psInsertUforumLog = psInsertLog.newPreparedStatementCreator(
										new Object[]{transactionAction,tableUforum_Message, oldRecord,activityBy,ipAddress,forumMessage.getMessage()}
										);
							
							GeneratedKeyHolder LogKeyHolder = new GeneratedKeyHolder();
							*/
								
							String deleteMessageSQL = "delete FROM UFORUM_MESSAGE WHERE Message_Id =?";
							PreparedStatementCreatorFactory deleteMessage = new PreparedStatementCreatorFactory(deleteMessageSQL,
									new int[] {Types.INTEGER}
									);
							
							PreparedStatementCreator psDeleteMessage = deleteMessage.newPreparedStatementCreator(
									new Object[]{forumMessage.getMessageId()}
									);
							
							jdbcTemplate.update(psDeleteMessage);
							
							/*Commented out by mphahsm on 2015/11
							jdbcTemplate.update(psInsertUforumLog,LogKeyHolder);
							*/
						}
						catch (Exception e) 
						{
							logger.debug("DISCUSSIONFORUMS::FAILED_TO_DELETE_MESSAGE - [MESSAGE ID - "+forumMessage.getMessageId()+"]");
							//use this to rollback exception in case of exception
							transactionStatus.setRollbackOnly();
							transactionSuccess= transactionStatus.isRollbackOnly();
						}
						finally
						{
							jdbcTemplate=null;
						}
					}
				});
			}
			else
			{
				transactionSuccess=true;
			}
		}
		else
		{
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
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		
		/* Commented out by mphahsm on 2015/09. This query will return more than one records in case of concurrent transactions
		StringBuilder selectInitialMessageSQL =new StringBuilder("select m.message_id as messageId from uforum_message m");
		selectInitialMessageSQL.append(" inner join (select  min(CREATION_DATE) as mindate");
		selectInitialMessageSQL.append(" from uforum_message where TOPIC_ID=? )st on  m.CREATION_DATE = st.mindate");
		*/
		
		//Added by mphahsm on 2015/09
		StringBuilder selectInitialMessageSQL =new StringBuilder("SELECT message_id FROM UFORUM_MESSAGE");
		selectInitialMessageSQL.append(" WHERE topic_id = ? AND first_topic_msg = 'Y'");
		//End of mphahsm Add
		
		long beforeTime = System.currentTimeMillis();
		
		try{
			intialMessage = (String) jdbcTemplate.queryForObject(selectInitialMessageSQL.toString(), new Object[] { topicId}, String.class);
		} 
		catch (Exception ex) {
			return null;
         //   throw new Exception("ForumAction: getintialMessage Error occurred / "+ ex,ex);
		}
		
		long afterTime = System.currentTimeMillis();
		long executionTime = afterTime-beforeTime;
		logger.info("DISCUSSIONFORUMS::SQL_EXECUTION_TIME : "+executionTime+" - [In the getInitialMessage() method.]");
		
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
		StringBuilder selectMessageSQL = new StringBuilder("select Message_Id,Topic_Id,Content,TO_CHAR(Creation_Date,'YYYY-MM-DD HH24:MI:SS') as Creation_Date,User_Id,User_Identifier,nvl(Msg_Url,' ') as Msg_Url,nvl(File_Type,' ') as File_Type ");
		selectMessageSQL.append(" from UFORUM_MESSAGE where Message_Id = ?");
		long beforeTime = System.currentTimeMillis();
		PreparedStatementCreatorFactory selectMessage = new PreparedStatementCreatorFactory(selectMessageSQL.toString(),
				new int[] {Types.INTEGER});
		PreparedStatementCreator psSelectMessages = selectMessage.newPreparedStatementCreator(
				new Object[] {messageId });
		List messageDetailList;
		try{
		 messageDetailList = jdbcTemplate.query(psSelectMessages,new MessageRowMapper());
		}catch(EmptyResultDataAccessException e){return null;}
		Iterator i = messageDetailList.iterator();
		if (i.hasNext()){
			forumMessage = (ForumMessage) i.next();
		}
		long afterTime = System.currentTimeMillis();
		long executionTime = afterTime-beforeTime;
		logger.info("DISCUSSIONFORUMS::SQL_EXECUTION_TIME : "+executionTime+" - [In the getMessageDetail() method.]");
		return forumMessage;
	} // end of public ForumMessage getMessageDetail(Integer messageId)
	
	
	/**
	 * select all messages for a specific topic except initial message
	 * 
	 * @param topicId
	 * @return
	 */
	public List getMessageList(Integer topicId) 
	{
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		
		/* Commented out by mphahsm on 2015/11.
		StringBuilder selectMessagesSQL = new StringBuilder("select Message_Id,Topic_Id,Content,TO_CHAR(Creation_Date,'YYYY-MM-DD HH24:MI:SS') as Creation_Date,User_Id,User_Identifier,nvl(Msg_Url,'') as Msg_Url,nvl(File_Type,' ') as File_Type ");
		selectMessagesSQL.append("from UFORUM_MESSAGE where Topic_Id = ? ");
		selectMessagesSQL.append(" and Message_Id <> (select min(Message_Id) from UFORUM_MESSAGE where Topic_Id = ? ) ");
		selectMessagesSQL.append("order by Creation_Date DESC");
		*/
		
		//Added by mphahsm on 2015/11 To get all correct messages for a specific topic except initial message 
		StringBuilder selectMessagesSQL = new StringBuilder("SELECT Message_Id,Topic_Id,Content,TO_CHAR(Creation_Date,'YYYY-MM-DD HH24:MI:SS') as Creation_Date,User_Id,User_Identifier,nvl(Msg_Url,'') as Msg_Url,nvl(File_Type,' ') as File_Type,first_topic_msg ");
		selectMessagesSQL.append("FROM uforum_message WHERE Topic_Id = ? ");
		selectMessagesSQL.append("AND first_topic_msg = 'N' ");
		selectMessagesSQL.append("ORDER BY creation_date DESC");
		//End of mphahsm Add
		
		long beforeTime = System.currentTimeMillis();
		
		PreparedStatementCreatorFactory selectMessage = new PreparedStatementCreatorFactory(selectMessagesSQL.toString(),
				new int[] {Types.INTEGER});
		PreparedStatementCreator psSelectMessages = selectMessage.newPreparedStatementCreator(
				new Object[] {topicId});
		
		List messagesList;
		
		try
		{
			messagesList = jdbcTemplate.query(psSelectMessages,new MessageRowMapper());
		}
		catch(EmptyResultDataAccessException e){
			return null;
		}
		
		long afterTime = System.currentTimeMillis();
		long executionTime = afterTime-beforeTime;
		logger.info("DISCUSSIONFORUMS::SQL_EXECUTION_TIME : "+executionTime+" - [In the getMessageList() method.]");
		
		return messagesList;
	}
	
	public String initailMessage(ForumMessage forumMessage) throws Exception{
		String intialMessage;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		String sql ="select first_topic_msg from uforum_message where message_id=?";
		try{
			intialMessage = (String) jdbcTemplate.queryForObject(sql, new Object[] { forumMessage.getMessageId()}, String.class);
		} catch (EmptyResultDataAccessException ex) {
			return null;
         //   throw new Exception("ForumAction: initailMessage Error occurred / "+ ex,ex);
		}
       return intialMessage;
	}

}
