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
import za.ac.unisa.lms.tools.discussionforums.actions.ForumMessageAction;
import za.ac.unisa.lms.tools.discussionforums.dao.Forum;
import za.ac.unisa.lms.tools.discussionforums.dao.ForumDao;
import za.ac.unisa.lms.tools.discussionforums.dao.ForumRowMapper;

/**
 * DAO class for all forum related queries
 * 
 * @author SYzelle
 *
 */
public class ForumDAOImpl extends SakaiDAO implements ForumDao{
	private boolean transactionSuccess=false;
	protected static Logger logger = Logger.getLogger(ForumDAOImpl.class);

	/**
	 * insert new forum
	 * 
	 * @param forum
	 */
	public void insertForum(Forum forum){
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		StringBuilder insertForumSql = new StringBuilder("insert into UFORUM_FORUM (Forum_Id,Forum_Name,Description,User_Id,Creation_Date,Site_Id,Hide_Status) ");
		insertForumSql.append("values(UFORUM_FORUM_0.nextval,?,?,?,sysdate,?,'N')");
		PreparedStatementCreatorFactory psFactoryForumInsert = new PreparedStatementCreatorFactory(insertForumSql.toString(),
				new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR}
				);	
		psFactoryForumInsert.setGeneratedKeysColumnNames(new String[]{"Forum_Id"});
		psFactoryForumInsert.setReturnGeneratedKeys(true);
		PreparedStatementCreator psCreatorForumInsert = psFactoryForumInsert.newPreparedStatementCreator(
				new Object[]{forum.getForumName(),forum.getForumDescription(),forum.getUserId(),forum.getSiteId()}
				);
		GeneratedKeyHolder ForumKeyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(psCreatorForumInsert,ForumKeyHolder);
		Integer forumIdKey = ForumKeyHolder.getKey().intValue();
		forum.setForumId(forumIdKey);
	} // end of public void insertForum(Forum forum){

	
	/**
	 * update existing forum details
	 * 
	 * @param forum
	 */
	public void updateForum(Forum forum){
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		StringBuilder editForumSql = new StringBuilder("update UFORUM_FORUM set Forum_Name = ?,Description = ? WHERE Site_Id = ? and Forum_Id = ? ");
		PreparedStatementCreatorFactory psFactoryForumEdit = new PreparedStatementCreatorFactory(editForumSql.toString(),
				new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR}
				);
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
				transactionSuccess=false;
				String transactionAction="DELETE";
				String tableUforum_forum="UFORUM_FORUM";
				String oldRecord = forum.getForumId()+", "+forum.getUserId();
				String selectNextValLogId = "select uforum_log_s.nextval as logId from dual";
				String logId="";
				
				try{
					jdbcTemplate = new JdbcTemplate(getDataSource());	
					
					/* Commented out by mphahsm on 2015/12: No need to insert this into log table since we are not permanently deleting the record but hiding it
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
					insertUforumLogSQL.append("OLD_RECORD,ACITIVITY_BY, CLIENT_IPADDRESS,msg_content) values (uforum_log_s.nextval,sysdate,?,?,?,?,?,?)");
					PreparedStatementCreatorFactory psInsertLog = new PreparedStatementCreatorFactory(insertUforumLogSQL.toString(),
							new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.VARCHAR}
							);
					psInsertLog.setGeneratedKeysColumnNames(new String[]{"log_id"});
					psInsertLog.setReturnGeneratedKeys(true);
					PreparedStatementCreator psInsertUforumLog = psInsertLog.newPreparedStatementCreator(
							new Object[]{transactionAction,tableUforum_forum, oldRecord,activityBy,ipAddress, forum.getForumName()}
							);
					GeneratedKeyHolder LogForumKeyHolder = new GeneratedKeyHolder();
					*/
			
					String deleteForumSql = "update UFORUM_FORUM set Hide_Status =? WHERE Site_Id =? and Forum_Id =?";
					PreparedStatementCreatorFactory deleteForum = new PreparedStatementCreatorFactory(deleteForumSql,
							new int[] {Types.VARCHAR,Types.VARCHAR,Types.INTEGER}
							);
					PreparedStatementCreator pdDeleteForum = deleteForum.newPreparedStatementCreator(
							new Object[]{forum.getHideStatus(),forum.getSiteId(),forum.getForumId()}
							);
					jdbcTemplate.update(pdDeleteForum);
					//jdbcTemplate.update(psInsertUforumLog,LogForumKeyHolder); Commented out by mphahsm: 2015/12
				}
				catch (Exception e) 
				{
					//use this to rollback exception in case of exception
					logger.debug("DISCUSSIONFORUMS::FAILED_TO_DELETE_FORUM - [FORUM ID - "+forum.getForumId()+"]");
					transactionStatus.setRollbackOnly();
					transactionSuccess= transactionStatus.isRollbackOnly();
				}
				finally{
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
		StringBuilder selectForumsForSite = new StringBuilder("select Forum_Id,Forum_Name,Description,User_Id,TO_CHAR(Creation_Date,'YYYY-MM-DD HH24:MI') as Creation_Date,");
		selectForumsForSite.append(" Site_Id,TO_CHAR(Last_Post_Date,'YYYY-MM-DD HH24:MI') as Last_Post_Date,nvl(Last_Post_User,'') as Last_Post_User  ");
		selectForumsForSite.append(" from UFORUM_FORUM where Site_Id = ? ");
		selectForumsForSite.append(" and Hide_Status = 'N' ");
		selectForumsForSite.append(" order by upper("+sortby+") "+sortorder);
		
		
		long beforeTime = System.currentTimeMillis();
		PreparedStatementCreatorFactory psselectForumsSite = new PreparedStatementCreatorFactory(selectForumsForSite.toString(),
				new int[] {Types.VARCHAR});
		PreparedStatementCreator psSelectForumsSites = psselectForumsSite.newPreparedStatementCreator(
				new Object[] { siteId});
		List forumList = jdbcTemplate.query(psSelectForumsSites,new ForumRowMapper());
		long afterTime = System.currentTimeMillis();
		long executionTime = afterTime-beforeTime;
		logger.info("DISCUSSIONFORUMS::SQL_EXECUTION_TIME : "+executionTime+" - [In the getForumList() method.]");
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
		
		PreparedStatementCreatorFactory selectForum = new PreparedStatementCreatorFactory(selectForumSQL,
				new int[] {Types.INTEGER});
		PreparedStatementCreator psSelectForum = selectForum.newPreparedStatementCreator(
				new Object[] {forumId});
		List forumList;
		try{
		forumList = jdbcTemplate.query(psSelectForum,new ForumRowMapper());
	    } catch (EmptyResultDataAccessException ex) {
		return null; 
	    }
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
		
		String returnForumName="";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		
		StringBuilder selectForumName = new StringBuilder("select Forum_Name from UFORUM_FORUM where Forum_Name = ?");
		selectForumName.append(" and Site_Id = ? and Hide_Status = 'N'");
		long beforeTime = System.currentTimeMillis();
		try{
			returnForumName = (String) jdbcTemplate.queryForObject(selectForumName.toString(), new Object[]{forumName, siteId}, String.class);
		} 
		catch (EmptyResultDataAccessException ex) {
			return null;
		}
		long afterTime = System.currentTimeMillis();
		long executionTime = afterTime-beforeTime;
		logger.info("DISCUSSIONFORUMS::SQL_EXECUTION_TIME : "+executionTime+" -[In the getForumByName() method.]");
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
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		String selectNumberOfPosts = "select count(fm.Message_Id) as MessagesTotal " +
				" from UFORUM_FORUM ff,UFORUM_TOPIC ft,UFORUM_MESSAGE fm " +
				" where ff.Site_Id = ? "+
				" and ff.Forum_Id = ft.Forum_Id " +
				" and ff.Forum_Id =? " +
				" and ft.Topic_Id = fm.Topic_Id " +
				" and ft.Hide_Status='N'";
		long beforeTime = System.currentTimeMillis();
		try{
			//totalMessagesForForum = this.querySingleValue(selectNumberOfPosts,"MessagesTotal");
			totalMessagesForForum = (String) jdbcTemplate.queryForObject(selectNumberOfPosts, new Object[] { siteId, forumId}, String.class);
		} catch (Exception ex) {
			return null;
		}
		long afterTime = System.currentTimeMillis();
		long executionTime = afterTime-beforeTime;
		logger.info("DISCUSSIONFORUMS::SQL_EXECUTION_TIME : "+executionTime+" - [In the getForumPostCounter() method.]");
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
		long beforeTime = System.currentTimeMillis();
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
		long afterTime = System.currentTimeMillis();
		long executionTime = afterTime-beforeTime;
		logger.info("DISCUSSIONFORUMS::SQL_EXECUTION_TIME : "+executionTime+" - [In the getForumLastPostUser() method.]");
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
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		
		StringBuilder selectForumsForSite = new StringBuilder("select count(*) as ForumsTotal from UFORUM_FORUM ");
		selectForumsForSite.append(" Where Site_Id = ? and Hide_Status = 'N'");
		
		long beforeTime = System.currentTimeMillis();
		
		try{
			totalForumsForSite = (String) jdbcTemplate.queryForObject(selectForumsForSite.toString(), new Object[] { siteId}, String.class);
		} 
		catch (Exception ex) {
            throw new Exception("ForumAction: getForumsTotals Error occurred / "+ ex,ex);
		}
		
		long afterTime = System.currentTimeMillis();
		long executionTime = afterTime-beforeTime;
		logger.info("DISCUSSIONFORUMS::SQL_EXECUTION_TIME : "+executionTime+" - [In the getForumsPerSiteCounter() method.]");
		
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
    
	public int getForumCount(String forumName,String siteId)
	{ 	
		int returnCount = -1;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		
		StringBuilder selectForumName = new StringBuilder("select count(Forum_Name) from UFORUM_FORUM where Forum_Name = ?");
		selectForumName.append(" and Site_Id = ? and Hide_Status = 'N'");
		long beforeTime = System.currentTimeMillis();
		try{
				returnCount = jdbcTemplate.queryForInt(selectForumName.toString(), new Object[]{forumName, siteId});
			} 
		catch (Exception ex) {
			return -1;
		}
		long afterTime = System.currentTimeMillis();
		long executionTime = afterTime-beforeTime;
		logger.info("DISCUSSIONFORUMS::SQL_EXECUTION_TIME : "+executionTime+" -[In the getForumCount() method.]");
		return returnCount;
	}

}
