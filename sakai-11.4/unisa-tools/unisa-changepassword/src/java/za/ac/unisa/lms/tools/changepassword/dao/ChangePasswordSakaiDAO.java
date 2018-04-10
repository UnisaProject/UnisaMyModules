package za.ac.unisa.lms.tools.changepassword.dao;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.SakaiDAO;

public class ChangePasswordSakaiDAO extends SakaiDAO {
	
	
	
	public void updateChangePasswordStatus(String studentNr) throws Exception{

		Calendar cal = Calendar.getInstance();
	    cal.setTimeInMillis(new java.util.Date().getTime());
	    
		boolean studentExists = false;
		String sql = "SELECT user_id "+
		             "FROM   UNISA_PASSWORD "+
		             "WHERE  USER_ID = "+studentNr;

		try{
			String userid = this.querySingleValue(sql,"user_id");
		      if(userid.length()>0){
		    	  studentExists=true;
		      }
		} catch (Exception ex) {
            throw new Exception("ChangePasswordSakaiDAO: updateChangePasswordStatus: Error occurred / "+ ex,ex);
		}
      
		if(studentExists==false){			
			String sql1 = "INSERT INTO UNISA_PASSWORD(USER_ID,RVN,REQUEST_DATE,TOOL_ID,CHANGE_PWD)"+
		      "VALUES (?,?,?,?,?)";
		      try{
		      	JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
		      	jdt1.update(sql1,new Object[] {studentNr,0,new Timestamp(cal.getTimeInMillis()),"UNISA-CHANGEPASSWORD","Y"});		      	
		      } catch (Exception ex) {
		      	throw new Exception("ChangePasswordSakaiDAO: updateChangePasswordStatus (Insert): Error occurred / "+ ex,ex);
		     }
		} else{
			GregorianCalendar calCurrent = new GregorianCalendar();
			int currentDay = calCurrent.get(Calendar.DAY_OF_MONTH); //  run stats for yesterday
			int currentMonth = calCurrent.get(Calendar.MONTH) + 1; // IN java january start at 0 and not 1
			int currentYear = calCurrent.get(Calendar.YEAR);
			int currentHH = calCurrent.get(Calendar.HOUR_OF_DAY);
			int currentMM = calCurrent.get(Calendar.MINUTE);
			String currentDate = currentYear+"-";
			if (currentMonth <= 9) {
				currentDate = currentDate +"0"+currentMonth+"-";
			} else {
				currentDate = currentDate +currentMonth+"-";
			}
			if (currentDay <=9) {
				currentDate = currentDate+"0"+currentDay;
			} else {
				currentDate = currentDate+currentDay;
			}
			if (currentHH <=9) {
				currentDate = currentDate+" 0"+currentHH;
			} else {
				currentDate = currentDate+" "+currentHH;
			}
			if (currentMM <=9) {
				currentDate = currentDate+":0"+currentMM;
			} else {
				currentDate = currentDate+":"+currentMM;
			}
			
			String update_sql = "UPDATE UNISA_PASSWORD "+
            "SET REQUEST_DATE=TO_DATE('"+currentDate+"','YYYY-MM-DD HH24:MI'),"+
            " TOOL_ID = 'UNISA-CHANGEPASSWORD',CHANGE_PWD='Y' WHERE USER_ID = "+studentNr;
        try{
	      JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
	         jdt.update(update_sql);
            } catch (Exception ex) {
	     throw new Exception("ChangePasswordSakaiDAO: updateChangePasswordStatus (update): Error occurred / "+ ex,ex);
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

