package za.ac.unisa.lms.tools.password.dao;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.db.SakaiDAO;

 public class ForgetPasswordSakaiDAO extends SakaiDAO{
	
 
	 public PasswordDetails getPasswordDetails(String stuno)  throws Exception {
		 PasswordRowMapper passwordRowMapper=null;
		 PasswordDetails passwordDetails =new PasswordDetails();
			try{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			String sql = "select request_date ,nvl(change_pwd,' ') as change_pwd from   unisa_password where  user_id =?";
			
			List passwordDetailList=jdt.query(sql, new Object[] {stuno},new PasswordRowMapper());
			
			if(passwordDetailList==null || passwordDetailList.size()==0){
				passwordDetails.setRequestDate("");
				passwordDetails.setChangePwd("");				
			}else{
			Iterator i = passwordDetailList.iterator();
			if (i.hasNext()){
				passwordDetails = (PasswordDetails) i.next();
			}
			}
		    } catch (Exception ex) {
	        throw new Exception("MyUnisaSakaiDAO: getPasswordDetails: (stno: "+stuno+") Error occurred / "+ ex,ex);
		  }
			return passwordDetails;		
		}
	 
	
  /* public String requestTime(String stuNum) throws Exception{
	     String request_date="";
	     JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		String sql="select request_date from   unisa_password where  user_id = ?";		             
		try {
			List<String> request_date1 =jdt.queryForList(sql, new Object[] {stuNum},String.class);
			if(request_date1.isEmpty()){
				request_date="";	
		}else{
			request_date=request_date1.get(0);
		}			
		} catch (Exception ex) {
			throw new Exception("MyUnisaSakaiDAO: requestTime: Error occurred / "+ ex, ex);
		}
		return request_date;
	}*/
		
  public void updatePassword(String stuNum) throws Exception{
		Calendar cal = Calendar.getInstance();
	    cal.setTimeInMillis(new java.util.Date().getTime());
	    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	    try{
			String sql="update unisa_password set request_date=?,tool_id = ?,change_pwd=? where user_id =?";
	  	    jdt.update(sql,new Object[] {new Timestamp(cal.getTimeInMillis()),"UNISA-FORGOTPASSWORD","Y",stuNum});
			}catch (Exception ex) {
		      	throw new Exception("Forgot password tool- MyUnisaSakaiDAO: updatePassword (Insert): Error occurred / "+ ex,ex);
		    }
	
 }
	public void insertForgottenPassword(String studentNr) throws Exception{

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		Calendar cal = Calendar.getInstance();
	    cal.setTimeInMillis(new java.util.Date().getTime());
	    boolean studentExists = false;
		String sql = "SELECT user_id FROM   UNISA_PASSWORD WHERE  USER_ID =?";
		
		try{
			List<String>  userid =jdt.queryForList(sql, new Object[] {studentNr},String.class);	
		      if(userid.isEmpty()){
		    	  studentExists=false;
		      }else{
		    	  studentExists=true; 
		      }
		} catch (Exception ex) {
            throw new Exception("ChangePasswordSakaiDAO: insertForgottenPassword: Error occurred / "+ ex,ex);
		}
			
		
		if(studentExists==false){		
		String sql1 = "INSERT INTO UNISA_PASSWORD(USER_ID,RVN,REQUEST_DATE,TOOL_ID,CHANGE_PWD)"+
			      "VALUES (?,?,?,?,?)";
	      try{
	      	jdt.update(sql1,new Object[] {studentNr,0,new Timestamp(cal.getTimeInMillis()),"UNISA-FORGOTPASSWORD","Y"});
	   
	      } catch (Exception ex) {
	      	throw new Exception("Forgot password tool- MyUnisaSakaiDAO: insertForgottenPassword (Insert): Error occurred / "+ ex,ex);
	      	}  
		}else{
			updatePassword(studentNr);
		}
		
	}	

	public String getJoinDate(String studentNr) throws Exception{		    
		    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		    String date="";
			String sql = "SELECT TO_CHAR(JOIN_DATE,'YYYY-MM-DD') AS JOINDATE FROM   JOIN_ACTIVATION "+
		                  "WHERE  USER_ID =?";
			
			try {
				List<String> queryList = jdt.queryForList(sql, new Object[] {studentNr},String.class);	
				if(queryList.isEmpty()){
				 date="";
				}else{
					 date=queryList.get(0);
				}
			} catch (Exception ex) {
				throw new Exception("MyUnisaSakaiDAO: getJoinDate: Error occurred / "+ ex, ex);
			}
           
		return  date;
	}

}
