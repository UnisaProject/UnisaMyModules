package za.ac.unisa.lms.tools.discussionforums.dao.impl;

import java.sql.Types;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import za.ac.unisa.lms.db.SakaiDAO;
import za.ac.unisa.lms.tools.discussionforums.dao.Forum;
import za.ac.unisa.lms.tools.discussionforums.dao.IForumDao;
import za.ac.unisa.lms.tools.discussionforums.dao.ForumRowMapper;

/**
 * DAO class for all forum related queries
 * 
 * @author SYzelle
 *
 */
public class ForumDAOImpl  extends SakaiDAO implements IForumDao{
	private PreparedStatementCreatorFactory psFactoryForumInsert;
	private PreparedStatementCreatorFactory psFactoryForumEdit;
	private boolean transactionSuccess=false;

	/**
	 * constructor
	 */
	public ForumDAOImpl(){
		psFactoryForumInsert = new PreparedStatementCreatorFactory(
				"insert into UFORUM_FORUM (Forum_Id,Forum_Name,Description,User_Id,Creation_Date,Site_Id,Hide_Status)"+
				" values(UFORUM_FORUM_0.nextval,?,?,?,sysdate,?,'N')",
				new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR}
				);	

		psFactoryForumEdit = new PreparedStatementCreatorFactory(
				"update UFORUM_FORUM set Forum_Name = ?,Description = ? WHERE Site_Id = ? and Forum_Id = ?",
				new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR}
				);

		/*new PreparedStatementCreatorFactory(
				"update UFORUM_FORUM set Hide_Status = ? WHERE Site_Id = ? and Forum_Id = ?",
				new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR}
				);*/

		//psFactoryForum.setGeneratedKeysColumnNames(new String[]{"Forum_Id"});
		//psFactoryForum.setReturnGeneratedKeys(true);

		/*
		pstsmtCreatorFactoryTopic = new PreparedStatementCreatorFactory(
				"insert into UFORUM_TOPIC(Topic_Id,Forum_Id,Topic_Subject,Creation_Date,Modification_Date,View_Count,Hide_Status)"+
				"values(UFORUM_TOPIC_0.nextval,?,?,sysdate,sysdate,1,'N')",
				new int[] {Types.INTEGER,Types.VARCHAR}
				);
		pstsmtCreatorFactoryTopic.setGeneratedKeysColumnNames(new String[]{"Topic_Id"});
		pstsmtCreatorFactoryTopic.setReturnGeneratedKeys(true);*/

		/*
		pstsmtCreatorFactoryTopicDelete = new PreparedStatementCreatorFactory(
				"update UFORUM_TOPIC set Hide_Status = ? WHERE Topic_Id = ?",
				new int[] {Types.VARCHAR,Types.VARCHAR}
				);/

		/*
		pstsmtCreatorFactoryMessage = new PreparedStatementCreatorFactory(
				
				"insert into UFORUM_MESSAGE(Message_Id,Topic_Id,Content,Creation_Date,User_Id,User_Identifier,Msg_Url,File_Type)"+
				"values(UFORUM_MESSAGE_0.nextval,?,?,sysdate,?,?,?,?)",
				new int[] {Types.INTEGER,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR}
				);
		pstsmtCreatorFactoryMessage.setGeneratedKeysColumnNames(new String[]{"Message_Id"});
		pstsmtCreatorFactoryMessage.setReturnGeneratedKeys(true);*/

		/*
		pstsmtCreatorFactoryMessageDelete = new PreparedStatementCreatorFactory(
				"delete FROM UFORUM_MESSAGE WHERE Message_Id = ?",
				new int[] {Types.INTEGER}
				);*/
	}

	/**
	 * insert new forum
	 * 
	 * @param forum
	 */
	public void insertForum(Forum forum){
		try
		{
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
	
		System.out.print(" "+forum.getForumName()+" / "+forum.getForumDescription()+" / "+forum.getUserId()+" / "+forum.getSiteId());
		PreparedStatementCreator psCreatorForumInsert = psFactoryForumInsert.newPreparedStatementCreator(
				new Object[]{forum.getForumName(),forum.getForumDescription(),forum.getUserId(),forum.getSiteId()}
				);
		
		GeneratedKeyHolder ForumKeyHolder = new GeneratedKeyHolder();
		
		/* 15 Aug 2014 Jeremia & Sonette remove as ForumKeyHolder is causing an exception */
		//jdbcTemplate.update (psCreatorForumInsert,ForumKeyHolder);
		jdbcTemplate.update(psCreatorForumInsert);
		//Integer forumIdKey = ForumKeyHolder.getKey().intValue();
		//forum.setForumId(forumIdKey);
		}
		catch(Exception ex)
		{
			StackTraceElement[] element  = ex.getStackTrace();
			System.out.println("Forum error  "+ex.getMessage());
			/*for(int i=0; i < element.length ; i++)
			{
			System.out.println("Forum error  "+element[i]);
			}*/
		}
		
	} // end of public void insertForum(Forum forum){

	
	/**
	 * update existing forum details
	 * 
	 * @param forum
	 */
	public void updateForum(Forum forum){
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		PreparedStatementCreator psCreatorForumEdit = psFactoryForumEdit.newPreparedStatementCreator(
				new Object[]{forum.getForumName(),forum.getForumDescription(),forum.getSiteId(),forum.getForumId()}
				);
		jdbcTemplate.update(psCreatorForumEdit);
	} // end of public void updateForum(Forum forum)

	
	/**
	 * delete an existing forum
	 * 
	 * @param forum
	 * @param activityBy
	 * @param ipAddress
	 * @return
	 */
	public boolean deleteForum(final Forum forum, final String activityBy, final String ipAddress){
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(getDataSource());
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				
				JdbcTemplate jdbcTemplate;
				String transactionAction="DELETE";
				String tableUforum_forum="UFORUM_FORUM";
				String oldRecord = forum.getForumId()+", "+forum.getUserId();
				String selectNextValLogId = "select uforum_log_s.nextval as logId from dual";
				String logId="";
				
				try{
					jdbcTemplate = new JdbcTemplate(getDataSource());	
					List logIdList = jdbcTemplate.queryForList(selectNextValLogId);
					Iterator logIdListIterator = logIdList.iterator();
					logIdListIterator = logIdList.iterator();
					if (logIdListIterator.hasNext()) {
						ListOrderedMap data = (ListOrderedMap) logIdListIterator.next();
						if (data.get("logId") == null){
						} else {
							logId = data.get("logId").toString();
						}
					}

					StringBuilder insertUforumLogSQL = new StringBuilder("insert into uforum_log (log_id, ACTIVITY_TIME, ACTION, FROM_TABLE, ");
					insertUforumLogSQL.append(" OLD_RECORD,ACITIVITY_BY, CLIENT_IPADDRESS,msg_content)");
					insertUforumLogSQL.append(" values (?,sysdate,?,?,?,?,?,?)");
							

					String hideForumSQL = "update UFORUM_FORUM set Hide_Status =? WHERE Site_Id =? and Forum_Id =?";
					jdbcTemplate.update(hideForumSQL, new Object[] {forum.getHideStatus(),forum.getSiteId(),forum.getForumId()});
					jdbcTemplate.update(insertUforumLogSQL.toString(),new Object[] {logId,transactionAction,tableUforum_forum, oldRecord,activityBy,ipAddress,forum.getForumName() });		 
				}catch (Exception e) {
					//use this to rollback exception in case of exception
					transactionStatus.setRollbackOnly();
					transactionSuccess= transactionStatus.isRollbackOnly();
				}finally{
					jdbcTemplate=null;
				}
			}
		});
		return transactionSuccess;
	} // end of public boolean deleteForum(final Forum forum, final String activityBy, final String ipAddress){

	
	/**
	 * select all forums for a specific site
	 * 
	 * @param siteId
	 * @param sortby
	 * @param sortorder
	 * @return
	 */
	public List<ForumRowMapper> getForumList(String siteId,String sortby,String sortorder) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		String selectForumsForSite = "select Forum_Id,Forum_Name,Description,User_Id,TO_CHAR(Creation_Date,'YYYY-MM-DD HH24:MI') as Creation_Date,Site_Id,TO_CHAR(Last_Post_Date,'YYYY-MM-DD HH24:MI') as Last_Post_Date,nvl(Last_Post_User,'') as Last_Post_User  " +
				" from UFORUM_FORUM " +
				" where Site_Id = ? " +
				" and Hide_Status = 'N' " +
				" order by upper("+sortby+") "+sortorder; 
		List  forumList = jdbcTemplate.query(selectForumsForSite,
				new Object[] { siteId}, new int[] { Types.VARCHAR },
				new ForumRowMapper());
		return forumList;
	} // end of public List getAllForumsList(String siteId,String sortby,String sortorder) {


	/**
	 * select detail for a forum with a specific forum id
	 * @param forumId
	 * @return
	 */
	public Forum getForumContent(Integer forumId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		Forum forum = new Forum();
		String selectForumSQL = "select * from UFORUM_FORUM where Forum_Id = ?";
		List forumList = jdbcTemplate.query(selectForumSQL,
				new Object[] { forumId },
				new int[] { Types.INTEGER },
				new ForumRowMapper());
		Iterator forumListIterator = forumList.iterator();
		if (forumListIterator.hasNext()){
			forum = (Forum) forumListIterator.next();
		}
		return forum;
	} // end of  public Forum getForumContent(Integer forumId) 


	/**
	 * @param forumName
	 * @param siteId
	 * @return
	 * @throws Exception
	 */
	public String getForumByName(String forumName,String siteId) throws Exception {
		String returnForumName;
		String selectForumName = "select Forum_Name " +
				" from UFORUM_FORUM " +
				" where Forum_Name = '"+forumName+"' " +
				" and Site_Id = '"+siteId+"' " +
				" and Hide_Status = 'N'";
		try{
			returnForumName = this.querySingleValue(selectForumName,"Forum_Name");
		} catch (Exception ex) {
            throw new Exception("FORUMDAO Action: getTopicName Error occurred / "+ ex,ex);
		}
		return returnForumName;

	} // end of public String getForumByName(String forumName,String siteId) throws Exception {

	
	/**
	 * get number of messages that was poster for the specific forum
	 * 
	 * @param forumId
	 * @param siteId
	 * @return totalMessagesForForum
	 * @throws Exception
	 */
	public String getForumPostCounter(Integer forumId,String siteId) throws Exception{
		String totalMessagesForForum;
		String selectNumberOfPosts = "select count(fm.Message_Id) as MessagesTotal " +
				" from UFORUM_FORUM ff,UFORUM_TOPIC ft,UFORUM_MESSAGE fm " +
				"where ff.Site_Id = '"+siteId+"' " +
				"and ff.Forum_Id = ft.Forum_Id " +
				"and ff.Forum_Id ="+forumId+" " +
				"and ft.Topic_Id = fm.Topic_Id " +
				"and ft.Hide_Status='N'";
		try{
			totalMessagesForForum = this.querySingleValue(selectNumberOfPosts,"MessagesTotal");
		} catch (Exception ex) {
            throw new Exception("ForumAction: getTopicsTotals Error occurred / "+ ex,ex);
		}
       return totalMessagesForForum;
	} // end of public String getForumPostCounter(Integer forumId,String siteId) throws Exception{

	
	/**
	 * select name of user who made the latest post
	 * 
	 * @param forumId
	 * @param siteId
	 * @return
	 * @throws Exception
	 */
	public String getForumLastPostUser(Integer forumId,String siteId) throws Exception{
		String user = "";
		String selectLastPostUser = "select fm.User_Id as User_Id  " +
				" from UFORUM_FORUM ff,UFORUM_TOPIC ft,UFORUM_MESSAGE fm " +
				" where ff.Site_Id = '"+siteId+"' " +
				" and ff.Forum_Id = ft.Forum_Id " +
				" and ff.Forum_Id ="+forumId+" " +
				" and ft.Topic_Id = fm.Topic_Id " +
				" and ft.Hide_Status='N' " +
				" and fm.Message_Id != (select min(fm1.Message_Id) " +
									" from UFORUM_FORUM ff1,UFORUM_TOPIC ft1,UFORUM_MESSAGE fm1 " +
									" where ff1.Site_Id = '"+siteId+"' " +
									" and ff1.Forum_Id = ft1.Forum_Id " +
									" and ff1.Forum_Id ="+forumId+" " +
									" and ft1.Topic_Id = fm1.Topic_Id " +
									" and ft1.Hide_Status='N') " +
									" and fm.Creation_Date = (select max(fm2.Creation_Date) from UFORUM_FORUM ff2,UFORUM_TOPIC ft2,UFORUM_MESSAGE fm2 where ff2.Site_Id = '"+siteId+"' and ff2.Forum_Id = ft2.Forum_Id and ff2.Forum_Id ="+forumId+" and ft2.Topic_Id = fm2.Topic_Id and ft2.Hide_Status='N')";
		JdbcTemplate jdbcTemplate;
		try{
			jdbcTemplate = new JdbcTemplate(getDataSource());
			List lastPostUserList = jdbcTemplate.queryForList(selectLastPostUser);
			Iterator lastPostUserListIterator = lastPostUserList.iterator();
			while (lastPostUserListIterator.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) lastPostUserListIterator.next();
				user = data.get("User_Id").toString();
			}
		}  catch (Exception ex) {
			throw new Exception("ForumAction: getForumLastPostUser Error occurred / "+ ex,ex);
	    } finally {
	    	jdbcTemplate = null;
	    }
		return user;
	} // end of public String getForumLastPostUser(Integer forumId,String siteId)

	
	/**
	 * select number of forums for a site
	 * 
	 * @param siteId
	 * @return
	 * @throws Exception
	 */
	public String getForumsPerSiteCounter(String siteId) throws Exception{
		String totalForumsForSite;
		String selectForumsForSite = "select count(*) as ForumsTotal " +
				" from UFORUM_FORUM " +
				" Where Site_Id = '"+siteId+"' " +
				" and Hide_Status = 'N'";
		try{
			totalForumsForSite = this.querySingleValue(selectForumsForSite,"ForumsTotal");
		} catch (Exception ex) {
            throw new Exception("ForumAction: getForumsTotals Error occurred / "+ ex,ex);
		}
       return totalForumsForSite;
	} // end of getForumsPerSiteCounter

	
    /**
     * remove all single quotes from a string
     * 
     * @param convertString
     * @return
     */
    public String makeJDBCCompatible(String convertString) {
        String convertedString = null;
        
        if (convertString.lastIndexOf("'") > -1) {
            convertedString = convertString.replaceAll("'","''");
        }
        else 
            convertedString = convertString;
            
        return convertedString;
    }  // end of makeJDBCCompatible 
	
    
	/**
	 * @param query
	 * @param field
	 * @return
	 */
	private String querySingleValue(String query, String field){

		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
    	List queryList;
    	String result = "";

 	   queryList = jdbcTemplate.queryForList(query);
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
	} // end of querySingleValue	
}
