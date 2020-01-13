package za.ac.unisa.lms.tools.tutorprebooks.dao;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import za.ac.unisa.lms.db.SakaiDAO;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;

public class TutorPreBooksSakaiDAO extends SakaiDAO{
	
	
	public String insertOrUpdateTutorRecords(String userId, String period, String year, String courseCode, String tutorsCount,String dpt) throws Exception{
	 
		boolean recordExist=checkUserRecord(period, year, courseCode);
		Calendar cal = Calendar.getInstance();
	    cal.setTimeInMillis(new java.util.Date().getTime());
	    if(tutorsCount.length()==0 || tutorsCount.equals(" ")){
	    	tutorsCount=null;
	       }
	    
			    
		if(recordExist){
			//update query here

		    try{
				String sql1="update TUTORBOOKS set NO_OF_TUTORBOOKS=?, CREATED_DATE=?, NOVELL_USER_ID=? where MK_STUDY_UNIT_CODE=? and MK_ACADEMIC_YEAR=? and "
					    	+ " MK_ACADEMIC_PERIOD=? and DEPARTMENT=? ";
				 JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
		  	    jdt1.update(sql1,new Object[] {tutorsCount,new Timestamp(cal.getTimeInMillis()),userId,courseCode,year,period,dpt});
				}catch (Exception ex) {
			      	throw new Exception("TutorPreBooksSakaiDAO: insertOrUpdateTutorRecords (Insert): Error occurred / "+ ex,ex);
			    }
			
		
			
		}else{
			//insert here
			String sql2 = "INSERT INTO TUTORBOOKS (MK_STUDY_UNIT_CODE, MK_ACADEMIC_YEAR, MK_ACADEMIC_PERIOD, NO_OF_TUTORBOOKS, DEPARTMENT , NOVELL_USER_ID, CREATED_DATE) "
				        	+ "VALUES (?,?,?,?,?,?,?)";
		      try{
		    	  JdbcTemplate jdt2 = new JdbcTemplate(super.getDataSource());
		        	jdt2.update(sql2,new Object[] {courseCode,year,period,tutorsCount,dpt,userId,new Timestamp(cal.getTimeInMillis())});
		   
		      } catch (Exception ex) {
		      	throw new Exception("TutorPreBooksSakaiDAO- insertOrUpdateTutorRecords (Insert): Error occurred / "+ ex,ex);
		      	}  
		}

       return "";
	}

	
	public boolean checkUserRecord(String period, String year, String courseCode) throws Exception{

		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		Calendar cal = Calendar.getInstance();
	    cal.setTimeInMillis(new java.util.Date().getTime());
	    
	    boolean tutorExist = false;
	    
		String sql = "select novell_user_id from tutorbooks where  MK_STUDY_UNIT_CODE= ?"
				+ " and MK_ACADEMIC_YEAR=? and MK_ACADEMIC_PERIOD=?";
		
		try{
			List  userid =jdbcTemplate.queryForList(sql, new Object[] {courseCode,year,period},String.class);	
		      if(userid.isEmpty()){
		    	  tutorExist=false;
		      }else{
		    	  tutorExist=true; 
		      }
		} catch (Exception ex) {
            throw new Exception("TutorPreBooksSakaiDAO: checkUserRecord: Error occurred / "+ ex,ex);
		}
       return tutorExist;
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
