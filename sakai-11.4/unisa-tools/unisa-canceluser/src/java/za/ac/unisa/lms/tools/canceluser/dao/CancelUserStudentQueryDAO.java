package za.ac.unisa.lms.tools.canceluser.dao;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
public class CancelUserStudentQueryDAO extends StudentSystemDAO {

	public boolean getvalidStudent(String studentNr) throws Exception{
		// Get valid Student number
				String studentNrTmp = "";
				boolean studentExist = false;

		String sql = " select nr "+
				" from stu " +
				" where nr = "+studentNr;

		try {
	    	studentNrTmp = this.querySingleValue(sql,"NR");
			if ((studentNrTmp == null)||(studentNrTmp.length() == 0)) {
				studentNr = " ";
				studentExist = false;

			} else {
				studentExist = true;
			}
		} catch (Exception ex) {
	       throw new Exception("CancelUserStudentQueryDAO : getvalidStudent / "+ ex,ex);
	    }
		return studentExist ;
	}
	public StudentInfo getStudentDetails(String stno){
		StudentInfo studentInfo = new StudentInfo();
		List studentList = null;
		String sql = "select NR, INITIALS, SURNAME from stu where NR = "+stno;
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		studentList = jdt.queryForList(sql);
		Iterator i = studentList.iterator();
		while(i.hasNext()){
			ListOrderedMap data = (ListOrderedMap) i.next();
			studentInfo.setStudentNr(data.get("NR").toString());
			studentInfo.setStudentName(data.get("INITIALS").toString());
		    studentInfo.setSurname(data.get("SURNAME").toString());		
		}
		return studentInfo;
	}
public void setUserInactiveSOLACT (String studentNr) throws Exception{

		String sql = " UPDATE SOLACT" +
				" SET ACT_STATUS = 'N'"+
				" where student_NR = "+studentNr;

		System.out.println("update: "+sql);

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql);
		} catch (Exception ex) {
			throw new Exception("CancelUserStudentQueryDAO: setUserInactiveSOLACT (Update): Error occurred /"+ ex,ex);
		}
}
public void setMyLifeUserInactiveSOLACT (String studentNr) throws Exception{

	String sql = " UPDATE JOIN_ACTIVATE SET MYLIFE_ACT_STATUS = 'N'"+
			" where USER_ID= "+studentNr;
	try{
		JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
		jdt1.update(sql);
	} catch (Exception ex) {
	}
}
public void setUserInactiveSTUANN (String studentNr) throws Exception{

	String sql = "UPDATE STUANN set SOL_USER_FLAG = 'N' where  mk_academic_year  = (select max(mk_academic_year)from stuann where  mk_student_nr = "+ studentNr +")"+
	               " and mk_student_nr ="+ studentNr; 

	System.out.println("update: "+sql);

	try{
		JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
//		jdt1.update(sql);1`	 m 
	} catch (Exception ex) {
		throw new Exception("CancelUserStudentQueryDAO: setUserInactiveSOLACT (Update): Error occurred /"+ ex,ex);
	}
}

public void insertAuditLog (String studentNr) throws Exception{

	InetAddress thisIp = InetAddress.getLocalHost();

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new java.util.Date().getTime());

		String sql = " INSERT INTO SOLGEN" +
				" (STUDENT_NR,TIMESTAMP,IPADDRESS, CATEGORY,DESCRIPTION,COURSE,SYSTEM_GC6,SEQUENCE_NR)"+
				" VALUES (?,?,?,?,?,?,?,solgen_sequence_nr.nextval)";

		System.out.println("update: "+sql);

		String category = "CSUSR";
		String description = "Cancellation of Sol user";
		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql,new Object[] {
					studentNr,new Timestamp(cal.getTimeInMillis()),thisIp.getHostAddress(),category,description,"",""});
		} catch (Exception ex) {
			throw new Exception("CancelUserStudentQueryDAO: insertAuditLog (Update): Error occurred /"+ ex,ex);
		}
}

	/**
	 * method: querySingleValue
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
  public String getBlockedStatus(String studentNr) { 
		        String query = "select status_flag from JOIN_ACTIVATE where USER_ID ="+studentNr; 
		        return querySingleValue(query,"status_flag");  
  } 
}