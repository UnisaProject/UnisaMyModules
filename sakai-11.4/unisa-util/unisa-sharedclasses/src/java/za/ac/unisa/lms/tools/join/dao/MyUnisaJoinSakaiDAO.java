package za.ac.unisa.lms.tools.join.dao;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.SakaiDAO;

public class MyUnisaJoinSakaiDAO extends SakaiDAO{

	private Log log = LogFactory.getLog(this.getClass());

	/**
	 * Method: myUnisaAccountExist
	 *  to validate if students myUnisa account was already activated
	 *  if act_status = 'Y'
	 *
	 * input: studentNr
	 * output: accountExist true/false
	 * @throws Exception
	 */
	public boolean myUnisaAccountExist(String studentNr) throws Exception{

		boolean accountExist = false;
		String sql = "SELECT ACT_STATUS "+
		             "FROM   JOIN_ACTIVATION "+
		             "WHERE  USER_ID = "+studentNr+" ";

		try{
			String actStatus = this.querySingleValue(sql,"ACT_STATUS");
			if (actStatus.equals("Y")) {
				accountExist = true;
			} else {
				accountExist = false;
			}

		} catch (Exception e) {
			log.error(this+": simple SELECT failed: myUnisaAccountExist(String studentNr) threw an exception "+e+": "+e.getMessage());
            throw e;
		}

       return accountExist;
	}

	/**
	 * Method: myUnisaAccountActive
	 *  to validate if students myUnisa account was already activated
	 *  if act_status = 'Y'
	 *
	 * input: studentNr
	 * output: accountActive true/false
	 * @throws Exception
	 */
	public boolean myUnisaAccountActive(String studentNr) throws Exception{
		
		boolean accountActive = false;
		String result = "";
		String sql = "SELECT USER_ID FROM JOIN_ACTIVATION " +
				" WHERE USER_ID = '"+studentNr+"' AND ACT_STATUS = 'Y' AND STATUS_FLAG = 'OT'";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());	
			result = (String) jdt.queryForObject(sql, String.class);
			if (result == null || result.length() == 0) {
				accountActive = false;
			}else{
				accountActive = true;
			}	
		} catch (Exception ex) {
            throw new Exception("Unisa-Util:sharedclasses-MyUnisaJoinSakaiDAO: myUnisaAccountActive: Error occurred / "+ ex,ex);
		}
		
		/*String sql = "SELECT COUNT(*) AS ACTIVE FROM JOIN_ACTIVATION " +
				" WHERE USER_ID = '"+studentNr+"' AND ACT_STATUS = 'Y' AND STATUS_FLAG = 'OT'";
		try{
			String actStatus = this.querySingleValue(sql,"ACTIVE");
			if (actStatus.equals("0")) {
				accountActive = false;
			} else {
				accountActive = true;
			}

		} catch (Exception e) {
			log.error(this+": simple SELECT failed: myUnisaAccountActive(String studentNr) threw an exception "+e+": "+e.getMessage());
            throw e;
		}*/

       return accountActive;
	}

	/**
	 * Method: validateActivationDetail
	 *  Validate student number and activation code with
	 *  the data on our system.
	 *
	 * input: studentNr, activity code
	 * output: dataValid true/false
	 * @throws Exception
	 */
	public boolean validateActivationDetail(String studentNr, String actCode) throws Exception{

		boolean dataValid = true;
		String userId;
		// does record already exist for student in JOIN_ACTIVATION
		String sql1 = "SELECT USER_ID "+
		             "FROM   JOIN_ACTIVATION "+
		             "WHERE  USER_ID = '"+studentNr+"' "+
		             "AND    ACT_CODE = '"+actCode+"' ";
		try{
			userId = this.querySingleValue(sql1,"USER_ID");
			if (userId.length() > 0) {
				dataValid = true;
			} else {
				dataValid = false;
			}

		} catch (Exception e) {
			log.error(this+": simple SELECT failed: validateActivationDetail threw an exception "+e+": "+e.getMessage());
            throw e;
		}

       return dataValid;
	}

	/**
	 * Method: insertJoinActivation
	 *  to validate if students myUnisa account was already activated
	 *  if act_status = 'Y'
	 *
	 *  mysql> desc JOIN_ACTIVATION;
	 *+-------------+-------------+------+-----+-------------------+-------+
	 *| Field       | Type        | Null | Key | Default           | Extra |
	 *+-------------+-------------+------+-----+-------------------+-------+
	 *| USER_ID     | varchar(99) |      | PRI |                   |       |
	 *| JOIN_DATE   | timestamp   | YES  |     | CURRENT_TIMESTAMP |       |
	 *| STATUS_FLAG | char(2)     |      |     |                   |       |
	 *| ACT_CODE    | varchar(30) |      |     |                   |       |
	 *| ACT_STATUS  | char(1)     |      |     |                   |       |
	 *| USER_TYPE   | char(1)     |      |     |                   |       |
	 *+-------------+-------------+------+-----+-------------------+-------+
	 *
	 * input: studentNr, statusFlag, actCode, actStatus, type
	 *	 	statusFlag = NC (not completed); OT (when activated)
	 * 		actCode = activation code
	 * 		actStatus = N applied, Y activated
	 * 		type = S (s = student)
	 * output: accountExist true/false
	 * @throws Exception
	 */
	public void insertJoinActivation(String studentNr, String statusFlag, String actCode,String actStatus, String type) throws Exception{

	    Calendar cal = Calendar.getInstance();
	    cal.setTimeInMillis(new java.util.Date().getTime());

		String joinExist;
		// does record already exist for student in JOIN_ACTIVATION
		String sql1 = "SELECT USER_ID "+
		             "FROM   JOIN_ACTIVATION "+
		             "WHERE  USER_ID = '"+studentNr+"'";
		try{
			joinExist = this.querySingleValue(sql1,"USER_ID");

		} catch (Exception ex) {
            throw new Exception("MyUnisaJoinQueryDAO: insertJoinActivation (Select): Error occurred / "+ ex,ex);
		}

		if (joinExist.length() > 0) {
			// update JOIN_ACTIVATION
			String sql = "UPDATE JOIN_ACTIVATION "+
			             "SET JOIN_DATE = '"+new Timestamp(cal.getTimeInMillis())+"', "+
			             "    STATUS_FLAG = '"+statusFlag+"', "+
			             "    ACT_CODE = '"+actCode+"', "+
			             "    ACT_STATUS = '"+actStatus+"', "+
			             "    USER_TYPE = '"+type+"' "+
			             "WHERE USER_ID = '"+studentNr+"'";

			try{
				JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
				jdt.update(sql);

			} catch (Exception e) {
				log.error(this+": simple UPDATE JOIN_ACTIVATION failed:  threw an exception "+e+": "+e.getMessage());
	            throw e;
			}
		} else {
			// insert JOIN_ACTIVATION
			String sql = "INSERT INTO JOIN_ACTIVATION(USER_ID,JOIN_DATE, STATUS_FLAG, ACT_CODE,ACT_STATUS, USER_TYPE) "+
						"VALUES (?,?,?,?,?,?)";
			try{
				JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
				jdt.update(sql,new Object[] {studentNr,new Timestamp(cal.getTimeInMillis()),statusFlag, actCode, actStatus, type});

			} catch (Exception e) {
				log.error(this+": simple INSERT JOIN_ACTIVATION failed:  threw an exception "+e+": "+e.getMessage());
	            throw e;
			}
		}
	}

	/**
	 * Method: insertClasslist
	 *  if student want to be included in class list, save which info he chose to be included
	 *  	on the classlist
	 *
	 *  mysql> desc CLASSLIST_SETTINGS;
	 *+-----------+-------------+------+-----+---------+-------+
	 *| Field     | Type        | Null | Key | Default | Extra |
	 *+-----------+-------------+------+-----+---------+-------+
	 *| USER_ID   | varchar(99) |      | PRI |         |       |
	 *| EMAIL     | char(1)     |      |     |         |       |
	 *| CELLPHONE | char(1)     |      |     |         |       |
	 *+-----------+-------------+------+-----+---------+-------+
	 *
	 * input: studentNr, statusFlag, actCode, actStatus, type
	 *	 	statusFlag = NC (not completed); OT (when activated)
	 * 		actCode = activation code
	 * 		actStatus = N applied, Y activated
	 * 		type = S (s = student)
	 * output: accountExist true/false
	 * @throws Exception
	 */
	public void insertClasslist(String studentNr, boolean optionEmail, boolean optionCell) throws Exception{

		String classlistExist;
		// does record already exist for student in CLASSLIST_SETTINGS
		String sql = "SELECT USER_ID "+
		             "FROM   CLASSLIST_SETTINGS "+
		             "WHERE  USER_ID = '"+studentNr+"'";
		try{
			classlistExist = this.querySingleValue(sql,"USER_ID");

		} catch (Exception e) {
			log.error(this+": simple SELECT failed:  threw an exception "+e+": "+e.getMessage());
            throw e;
		}

		String tmpOptionEmail;
		String tmpOptionCell;
		if (optionEmail == false) {
			tmpOptionEmail = "N";
		} else {
			tmpOptionEmail = "Y";
		}
		if (optionCell == false) {
			tmpOptionCell = "N";
		} else {
			tmpOptionCell = "Y";
		}

		if ((classlistExist.length() > 0)&&(tmpOptionEmail.equals("N"))&&(tmpOptionCell.equals("N"))) {
			// delete CLASSLIST_SETTINGS
			String sql1 = "DELETE FROM CLASSLIST_SETTINGS " +
						  "WHERE USER_ID = '"+studentNr+"'";

			try{
				JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
				jdt1.update(sql1);
			} catch (Exception e) {
				log.error(this+": simple DELETE failed:  threw an exception "+e+": "+e.getMessage());
	            throw e;
			}
		} else if (classlistExist.length() > 0) {
			// update CLASSLIST_SETTINGS
			String sql1 = "UPDATE CLASSLIST_SETTINGS "+
						  "SET EMAIL = '"+tmpOptionEmail+"', "+
						  "    CELLPHONE = '"+tmpOptionCell+"' "+
						  "WHERE USER_ID = '"+studentNr+"'";

			try{
				JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
				jdt1.update(sql1);
			} catch (Exception e) {
				log.error(this+": simple UPDATE CLASSLIST_SETTINGS failed:  threw an exception "+e+": "+e.getMessage());
	            throw e;
			}

		} else if ((tmpOptionEmail.equals("Y"))||(tmpOptionCell.equals("Y"))) {
			// insert CLASSLIST_SETTINGS
			String sql1 = "INSERT INTO CLASSLIST_SETTINGS(USER_ID,EMAIL,CELLPHONE)"+
					      "VALUES (?,?,?)";
			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql1,new Object[] {studentNr,tmpOptionEmail,tmpOptionCell});
			} catch (Exception e) {
				log.error(this+": simple INSERT CLASSLIST_SETTINGS failed:  threw an exception "+e+": "+e.getMessage());
	            throw e;
			}
		}
	}

	/**
	 * This method executes a query and returns a String value as result.
	 * An empty String value is returned if the query returns no results.
	 *
	 * @param query       The query to be executed on the database
	 * @param field		  The field that is queried on the database
	 * method written by: E Penzhorn
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