
package za.ac.unisa.lms.tools.mylife.dao;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.SakaiDAO;

public class MyLifesakaiDAO extends SakaiDAO {
	
	public void updateJoinActivation(String studentNr, String statusFlag, String actCode,String actStatus, String type ,String mylifeStatus,boolean joinActivationExist) throws Exception{

	    Calendar cal = Calendar.getInstance();
	    cal.setTimeInMillis(new java.util.Date().getTime());

		String joinExist;

		// does record already exist for student in JOIN_ACTIVATION
		/*
		try{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
            try{
            	joinExist=(String) jdt.queryForObject("SELECT USER_ID "+
		             "FROM   JOIN_ACTIVATION "+
		             "WHERE  USER_ID = ?", 
                      new Object[] {studentNr},String.class);          
                   
	             if (joinExist.equals(null) || joinExist.equals("")|| joinExist.equals(" ")) {
	            	 joinExist="";
	             }
	        } catch (Exception ex) {
	           throw new Exception("MyLifesakaiDAO: updateJoinActivation: (SELECT USER_ID "+
			             "FROM   JOIN_ACTIVATION "+
			             "WHERE  USER_ID =: "+studentNr+") Error occurred / "+ ex,ex);
	        }

		} catch (Exception ex) {
            throw new Exception("MyLifesakaiDAO: insertJoinActivation (Select): Error occurred / "+ ex,ex);
		}*/

		if (joinActivationExist==true) { // UPDATE
			// update JOIN_ACTIVATION
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

			String sql = "UPDATE JOIN_ACTIVATION"+
			             " SET JOIN_DATE = TO_DATE(?,'YYYY-MM-DD HH24:MI'),"+
			             " STATUS_FLAG = ?, "+
			             " ACT_CODE = ?, "+
			             " ACT_STATUS = ?, "+
			             " USER_TYPE = ?, "+ 
			             " MYLIFE_ACT_STATUS = ? "+
			             " WHERE USER_ID = ?";      

			try{
				JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
				jdt.update(sql,new Object[] {currentDate,statusFlag,actCode,actStatus,type,mylifeStatus,studentNr});

			} catch (Exception ex) {
				throw new Exception("MyLifesakaiDAO: insertJoinActivation (update): Error occurred / "+ ex,ex);
			}
		} else { //if (joinExist.length() > 0) { insert
			// insert JOIN_ACTIVATION
			String sql = "INSERT INTO JOIN_ACTIVATION(USER_ID,JOIN_DATE, STATUS_FLAG, ACT_CODE,ACT_STATUS, USER_TYPE,MYLIFE_ACT_STATUS) "+
						"VALUES (?,?,?,?,?,?,?)";

			try{
				JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
				jdt.update(sql,new Object[] {studentNr,new Timestamp(cal.getTimeInMillis()),statusFlag, actCode, actStatus, type,mylifeStatus});

			} catch (Exception ex) {
				throw new Exception("MyLifesakaiDAO: insertJoinActivation (insert): Error occurred / "+ ex,ex);
			}
		}
	}
	
	public boolean mylifeClaimedorNot(String studentNr) throws Exception{

		boolean myLifeClaimed = false;

		try{
			String actStatus = "";
			
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
            try{
            	actStatus=(String) jdt.queryForObject("SELECT MYLIFE_ACT_STATUS FROM   JOIN_ACTIVATION WHERE  USER_ID =?",
                      new Object[] {studentNr},String.class);          
                   
	            if (actStatus.equals(null) || actStatus.equals("")|| actStatus.equals(" ")) {
	               actStatus="";
	            }
            } catch (Exception ex) {
            	throw new Exception("MyLifesakaiDAO: updateJoinActivation: (SELECT MYLIFE_ACT_STATUS FROM   JOIN_ACTIVATION WHERE  USER_IDWHERE  USER_ID  =: "+studentNr+") Error occurred / "+ ex,ex);
            }
			
			if (actStatus.equals("Y")) {
				myLifeClaimed = true;
			} else {
				myLifeClaimed = false;
			}

		} catch (Exception ex) {
            throw new Exception("MyLifesakaiDAO: myUnisaAccountExist: Error occurred / "+ ex,ex);
		}

       return myLifeClaimed;
	}
		
	public boolean claimedUnisaLogin(String studentNr) throws Exception{

		boolean myLifeClaimed = false;
		String sql = "SELECT ACT_STATUS,MYLIFE_ACT_STATUS"+
		             " FROM   JOIN_ACTIVATION "+
		             " WHERE  USER_ID = ?";

		try{
			String actStatus = "";
			String myLifeStatus = "";
			JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List joinRecord = jdt.queryForList(sql,new Object[] {studentNr});
    		if (!joinRecord.isEmpty()) {
				Iterator j = joinRecord.iterator();
	    		while (j.hasNext()) {
	    			ListOrderedMap data = (ListOrderedMap) j.next();
	    			actStatus = data.get("ACT_STATUS").toString();
	    			myLifeStatus = data.get("MYLIFE_ACT_STATUS").toString();
	    		} // end while
    		} // end if (!joinRecord.isEmpty()) {
    		
			if (actStatus.equals("Y") && myLifeStatus.equals("Y")) {
				myLifeClaimed = true;
			} else {
				myLifeClaimed = false;
			}

		} catch (Exception ex) {
            throw new Exception("MyLifesakaiDAO: myUnisaAccountExist: Error occurred / "+ ex,ex);
		}

       return myLifeClaimed;
	}
	public boolean changedInitialPwd(String studentNr) throws Exception{

		boolean changePwd = false;
		String actStatus = "";
		String sql = "SELECT CHANGE_PWD "+
		             "FROM   UNISA_PASSWORD "+
		             "WHERE  USER_ID = ?";

		try{		
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
            try{
            	actStatus=(String) jdt.queryForObject(sql,new Object[] {studentNr},String.class);          
                   
	            if (actStatus.equals(null) || actStatus.equals("")|| actStatus.equals(" ")) {
	               actStatus="";
	            }
            } catch (Exception ex) {
            	throw new Exception("MyLifesakaiDAO: changedInitialPwd: ("+sql+" =: "+studentNr+") Error occurred / "+ ex,ex);
            }

		} catch (Exception ex) {
            throw new Exception("MyLifesakaiDAO: changedInitialPwd: Error occurred / "+sql+" =: "+studentNr+" / "+ ex,ex);
		}
		
		if (actStatus.equals("Y")) {
			changePwd = true;
		} else {
			changePwd = false;
		}

       return changePwd;
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
