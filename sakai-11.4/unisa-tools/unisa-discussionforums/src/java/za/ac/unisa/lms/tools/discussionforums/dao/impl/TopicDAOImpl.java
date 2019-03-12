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
import za.ac.unisa.lms.tools.discussionforums.dao.ForumTopicDetails;
import za.ac.unisa.lms.tools.discussionforums.dao.TopicDao;
import za.ac.unisa.lms.tools.discussionforums.dao.TopicRowMapper;

/**
 * DAO class for all topic related queries
 *
 * @author SYzelle
 *
 */
public class TopicDAOImpl extends SakaiDAO implements TopicDao{
       
       
        private boolean transactionSuccess = false;
        protected static Logger logger = Logger.getLogger(TopicDAOImpl.class);
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
                        public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                                JdbcTemplate jdbcTemplate;
                                try {
                                        jdbcTemplate = new JdbcTemplate(getDataSource());
                                        Integer topicId;
                                        String intialMessage ="Y";
                                        transactionSuccess=false;
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
                                        StringBuilder insertTopicSql = new StringBuilder("insert into UFORUM_TOPIC(Topic_Id,Forum_Id,Topic_Subject,Creation_Date,Modification_Date,View_Count,Hide_Status) ");
                                        insertTopicSql.append(" values(UFORUM_TOPIC_0.nextval,?,?,sysdate,sysdate,1,'N')");
                                        StringBuilder insertMessageSql = new StringBuilder("insert into UFORUM_MESSAGE(Message_Id,Topic_Id,Content,Creation_Date,User_Id,User_Identifier,Msg_Url,File_Type,");
                                        insertMessageSql.append(" First_topic_MSG) values(UFORUM_MESSAGE_0.nextval,?,?,sysdate,?,?,?,?,?)");
                                        PreparedStatementCreatorFactory pstsmtCreatorFactoryTopic = new PreparedStatementCreatorFactory(insertTopicSql.toString(), new int[] {Types.INTEGER,Types.VARCHAR});
                                        pstsmtCreatorFactoryTopic.setGeneratedKeysColumnNames(new String[]{"Topic_Id"});
                                        pstsmtCreatorFactoryTopic.setReturnGeneratedKeys(true);
                                        PreparedStatementCreator psCreatorTopicInsert = pstsmtCreatorFactoryTopic.newPreparedStatementCreator(
                                                        new Object[]{forumTopicDetails.getForumId(), forumTopicDetails.getTopicTitle()}
                                                        );
                                        GeneratedKeyHolder TopicKeyHolder = new GeneratedKeyHolder();
                                        jdbcTemplate.update(psCreatorTopicInsert,TopicKeyHolder);
                                     topicId = new Integer(TopicKeyHolder.getKey().intValue());
                                       
                                        PreparedStatementCreatorFactory insertIntialMessage = new PreparedStatementCreatorFactory(insertMessageSql.toString(),
                                                        new int[] {Types.INTEGER,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.VARCHAR}
                                                        );
                                        insertIntialMessage.setGeneratedKeysColumnNames(new String[]{"Message_Id"});
                                        insertIntialMessage.setReturnGeneratedKeys(true);
                                        PreparedStatementCreator psCreatorMessageInsert = insertIntialMessage.newPreparedStatementCreator(
                                                        new Object[]{topicId,forumTopicDetails.getTopicMessage(),forumTopicDetails.getTopicAuthor(),
                                                                        authorUserType, fileUrl, fileUrl,intialMessage}
                                                        );
                                        GeneratedKeyHolder TopicMessageKeyHolder = new GeneratedKeyHolder();
                                        jdbcTemplate.update(psCreatorMessageInsert,TopicMessageKeyHolder);
                                       
                                        forumTopicDetails.setTopicId(topicId);
                                } catch (Exception e) {
                                        // use this to rollback exception in case of exception
                                        logger.debug("DISCUSSIONFORUMS::FAILED_TO_ADD_TOPIC - [FORUM ID - "+forumTopicDetails.getForumId()+"]");
                                        transactionStatus.setRollbackOnly();
                                        transactionSuccess = transactionStatus.isRollbackOnly();
                                }finally {
                                        jdbcTemplate=null;
                                }
                        } // end public void doInTransactionWithoutResult(TransactionStatus
                               
                });
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
                        public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                                JdbcTemplate jdbcTemplate;
                                try {
                                        jdbcTemplate = new JdbcTemplate(getDataSource());
                                        transactionSuccess=false;
                                        String transactionAction = "DELETE";
                                        String tableUforum_Topic = "UFORUM_TOPIC";
                                        String oldRecord = forumTopicDetails.getTopicId() + ", "+forumTopicDetails.getForumId();
                                        String updateUforumTopicHideSQL = "update UFORUM_TOPIC set Hide_Status = ? WHERE Topic_Id= ?";
                                        PreparedStatementCreatorFactory hideTopic = new PreparedStatementCreatorFactory(updateUforumTopicHideSQL,
                                                        new int[] {Types.VARCHAR,Types.INTEGER}
                                                        );
                                        PreparedStatementCreator psDeleteTopic = hideTopic.newPreparedStatementCreator(
                                                        new Object[]{forumTopicDetails.getHideStatus(),forumTopicDetails.getTopicId()}
                                                        );
                                        /* Commented out by mphahsm on 2015/12: No need to insert this into log table since we are not permanently deleting the record but hiding it
                                        StringBuilder insertUforumLogSQL =new StringBuilder("insert into uforum_log (log_id, ACTIVITY_TIME, ACTION, FROM_TABLE,");
                                        insertUforumLogSQL.append(" OLD_RECORD,ACITIVITY_BY, CLIENT_IPADDRESS,msg_content) values (uforum_log_s.nextval,sysdate,?,?,?,?,?,?)");
                                        PreparedStatementCreatorFactory psInsertLog = new PreparedStatementCreatorFactory(insertUforumLogSQL.toString(),
                                                        new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.CLOB}
                                                        );
                                        psInsertLog.setGeneratedKeysColumnNames(new String[]{"log_id"});
                                        psInsertLog.setReturnGeneratedKeys(true);
                                        PreparedStatementCreator psInsertUforumLog = psInsertLog.newPreparedStatementCreator(
                                                        new Object[]{transactionAction,tableUforum_Topic, oldRecord, activityBy, ipAddress, forumTopicDetails.getTopicTitle() }
                                                        );
                                        GeneratedKeyHolder ForumKeyHolder = new GeneratedKeyHolder();
                                        */
                                        
                                        jdbcTemplate.update(psDeleteTopic);
                                        //jdbcTemplate.update(psInsertUforumLog,ForumKeyHolder); commented out by mphahsm: 2015/12
                                } 
                                catch (Exception e) 
                                {
                                        // use this to rollback exception in case of exception
                                        logger.debug("DISCUSSIONFORUMS::FAILED_TO_DELETE_TOPIC - [TOPIC ID - "+forumTopicDetails.getTopicId()+"]");
                                        transactionStatus.setRollbackOnly();
                                        transactionSuccess = transactionStatus.isRollbackOnly();
                                }
                                finally {
                                        jdbcTemplate=null;}
                        } // end of doInTransactionWithoutResult
                });
                return transactionSuccess;
        } // end of deleteTopic

        /**
         * method to update the discussion topic subject
         *
         * @param topicName
         * @param topicId
         */
        public void editTopicName(String topicName, int topicId) {
                JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
                String updateTopicSubjectSQL = " UPDATE UFORUM_TOPIC SET TOPIC_SUBJECT =? WHERE TOPIC_ID =?";
                PreparedStatementCreatorFactory updateTopicSubject = new PreparedStatementCreatorFactory(updateTopicSubjectSQL,
                                new int[] {Types.VARCHAR,Types.INTEGER}
                                );
                PreparedStatementCreator psUpdateTopicSubject = updateTopicSubject.newPreparedStatementCreator(
                                new Object[]{topicName, topicId}
                                );
                try {
                        jdbcTemplate.update(psUpdateTopicSubject);
                } catch (Exception ex) {
                    
                }finally {
                        jdbcTemplate=null;
                }
        } // end of public void editTopicName(String topicName, int topicId)


        /**
         * Get the subject for a specific topic
         *
         * @param topicId
         * @return topicName (String)
         * @throws Exception
         */
        public String getTopicName(Integer topicId) throws Exception {
                String topicName;
                JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
                String selectTopicNameSQL = "select Topic_Subject from UFORUM_TOPIC where Topic_Id = ?";
                try {
                        topicName = (String) jdbcTemplate.queryForObject(selectTopicNameSQL, new Object[] { topicId}, String.class);
                } catch (EmptyResultDataAccessException ex) {
                        return  null;
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
        public String getTopicsCounter(Integer forumId, String siteId)throws Exception {
                String totalCount;
                JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
                String getNumberOfTopicsSQL = "select count(ft.Forum_Id) as TopicsTotal from UFORUM_FORUM ff,UFORUM_TOPIC ft "+
                                                      " where ff.Forum_Id = ft.Forum_Id "+
                                                      " and ft.Forum_Id = ?"+
                                                      " and ff.Site_Id = ?"+
                                                      " and ft.Hide_Status = 'N'";
                try {

                        totalCount = (String) jdbcTemplate.queryForObject(getNumberOfTopicsSQL, new Object[] { forumId, siteId}, String.class);
                } catch (EmptyResultDataAccessException ex) {
                        return null;
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
                                        "ForumDAO: getTopicLastPostUser Error occurred / " + ex, ex);
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

                StringBuilder selectListOfTopicsSQL = new StringBuilder("select ft.Topic_Id, ft.Forum_Id, ft.Topic_Subject, ft.Creation_Date, ft.Modification_Date,nvl(ft.View_Count,0) as View_Count,");
                selectListOfTopicsSQL.append(" ft.Hide_Status, TO_CHAR(ft.Last_Post_Date,'YYYY-MM-DD HH24:MI') as Last_Post_Date, ft.Last_Post_User,");
                selectListOfTopicsSQL.append(" count(fm.Topic_Id) as messagesCount,");
                selectListOfTopicsSQL.append(" (count(fm.Message_Id)-1) replycount from UFORUM_TOPIC ft, UFORUM_MESSAGE fm");
                selectListOfTopicsSQL.append(" where ft.Forum_Id =? and ft.Topic_Id = fm.Topic_Id");
                selectListOfTopicsSQL.append(" and ft.Hide_Status = 'N'");
                selectListOfTopicsSQL.append(" group by  ft.Topic_Id, ft.Forum_Id, ft.Topic_Subject, ft.Creation_Date, ");
                selectListOfTopicsSQL.append(" ft.Modification_Date,nvl(ft.View_Count,0),");
                selectListOfTopicsSQL.append(" ft.Hide_Status, TO_CHAR(ft.Last_Post_Date,'YYYY-MM-DD HH24:MI'), ft.Last_Post_User");
                selectListOfTopicsSQL.append(" order by ft.Topic_Id, upper(" + sortby + ") " + sortorder);
                long beforeTime = System.currentTimeMillis();
                PreparedStatementCreatorFactory selectTopicList = new PreparedStatementCreatorFactory(selectListOfTopicsSQL.toString(),
                                new int[] {Types.INTEGER});
                PreparedStatementCreator psSelectTopicsList = selectTopicList.newPreparedStatementCreator(
                                new Object[] {forumId});
                List topicList;
                try{
                 topicList = jdbcTemplate.query(psSelectTopicsList,new TopicRowMapper());
                }catch(EmptyResultDataAccessException e){return null;}
                long afterTime = System.currentTimeMillis();
                long executionTime = afterTime-beforeTime;
                logger.info("DISCUSSIONFORUMS::SQL_EXECUTION_TIME : "+executionTime+" - [In the getTopics() method.]");
                return topicList;
        }


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

}