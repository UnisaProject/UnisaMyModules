package org.sakaiproject.james.dao;

import org.apache.commons.collections.map.ListOrderedMap;
import java.util.Iterator;
import java.util.List;

import za.ac.unisa.lms.db.SakaiDAO;
import org.springframework.jdbc.core.JdbcTemplate;

public class UnisaEmailLogDAO extends SakaiDAO {

	private Log log = LogFactory.getLog(this.getClass());

	/**
	 * Method: insertJoinActivation
	 *  to validate if students myUnisa account was already activated
	 *  if act_status = 'Y'
	 *  mysql> desc EMAIL_LOGS;
	 *  +--------------+--------------+------+-----+---------------------+----------------+
	 *  | Field        | Type         | Null | Key | Default             | Extra          |
	 *  +--------------+--------------+------+-----+---------------------+----------------+
	 *  | Entry_ID     | int(12)      |      | PRI | NULL                | auto_increment |
	 *  | To_address   | varchar(255) |      | MUL |                     |                |
	 *  | From_Address | varchar(255) |      | MUL |                     |                |
	 *  | Subject      | varchar(255) |      |     |                     |                |
	 *  | Date         | datetime     |      | MUL | 0000-00-00 00:00:00 |                |
	 *  | Message_ID   | int(12)      |      | MUL | 0                   |                |
	 *  +--------------+--------------+------+-----+---------------------+----------------+
	 *
	 * input: toAddress,fromAddress,subject,messageId
	 *	 	messageId = email-thread
	 * @throws Exception
	 */
	public void insertEmailLog(String toAddress, String fromAddress, String subject, String messageId) throws Exception{

			// insert EMAIL_LOG
			String sql = "INSERT INTO EMAIL_LOGS(ENTRY_ID,TO_ADDRESS,FROM_ADDRESS,SUBJECT,DATE_CREATED,MESSAGE_ID) "+
						"VALUES (EMAIL_LOGS_0.NEXTVAL,?,?,?,SYSDATE,?)";
			if((messageId == null) || (messageId.equals(""))) {
				messageId = "0";
			}
			try{
				JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
				jdt.update(sql,new Object[] {toAddress,fromAddress,subject,messageId});

			} catch (Exception e) {
				log.error(this+" toAddress:"+toAddress+" fromAddress:"+fromAddress+" subject:"+subject+" messageId:"+messageId);
				log.error(this+": INSERT FAILED on insertEmailLog: "+e+": "+e.getMessage());
				//throw e;
			}
	}

	public boolean isValidThread(String id) {
		return (Integer.parseInt(querySingleValue("SELECT count(*) as count from EMAIL_LOGS where Message_ID = "+id,"count")) > 0);
	}

	public String getNextThreadId() throws Exception{

		String sql = "SELECT EMAIL_LOGS_0.NEXTVAL AS TID "+
		             "FROM  DUAL ";
		String tid = "1";

		try{
			tid = this.querySingleValue(sql,"TID");

		} catch (Exception e) {
			log.error(this+": SELECT FAILED on getNextThreadId: "+e+": "+e.getMessage());
			throw e;
		}

       return tid;
	}

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
