package za.ac.unisa.lms.tools.cronjobs.dao;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.PreparedStatement;		//Sifiso Changes:2017/03/15:SR63678:To optimize SQL update

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.collections.map.ListOrderedMap;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;		//Sifiso Changes:2017/03/15:SR63678:To optimize SQL update


public class TelecentreDAO extends SakaiDAO {
		
	public int getTotalCount( ) throws Exception {
		
		String query = "select COUNT(tele_id) as number_telecentre from telecentre order by tele_id";
        	
          String recCount = this.querySingleValue(query,"number_telecentre");
          int count =  Integer.parseInt(recCount);
          return count;
    }
    public int getTeleId( ) throws Exception {
		
		String query = "select min(tele_id) as tele from telecentre order by tele_id";
        	
          String recCount = this.querySingleValue(query,"tele");
          int count =  Integer.parseInt(recCount);
          return count;
    }
    
    //Sifiso Changes:2017/03/10:SR63678:Changed return type to int to determine if update is successful
    //public void updateTelecode(String teleCode, int tele_id) throws Exception  	
    public int updateTelecode(String teleCode, int tele_id) throws Exception	 
    {
    		 //Sifiso Changes:2017/03/15:SR63678:Removed update by query
             //String  query = "UPDATE TELECENTRE SET TELE_CODE ='"+teleCode+"'"+
             //		         " where tele_id="+tele_id;
    	
    		 //Sifiso Changes:2017/03/15:SR63678:Added update by PreparedStatement for improved performance
    		 PreparedStatementCreator pscForJdt = updateTeleCodePreparedStatementCreator(teleCode,tele_id);
    		 
             JdbcTemplate jdt = new JdbcTemplate(getDataSource());
             int updateResult = 0;    					//Sifiso Changes:2017/03/10:SR63678:To check update success
              try{     	
	          //jdt.update(query);	  					//Sifiso Changes:2017/03/10:SR63678:Removed
              updateResult = jdt.update(pscForJdt); 	//Sifiso Changes:2017/03/15:SR63678:Use PreparedStatement for update
	          }catch(Exception ex){
		      throw new Exception("TelecentreDAO.java: updateTelecode"+ex);
	          }
              
              return updateResult;						//Sifiso Changes:2017/03/10:SR63678:Return update Success
     }
	
		  public ArrayList getTelecentres()
		 {
					 String sql= "SELECT CENTRE_NAME,TELE_ID FROM TELECENTRE WHERE ACTIVE='Y' ORDER BY CENTRE_NAME";
		 			  
		 			 ArrayList results = new ArrayList();
					  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
					  List queryList;
					  queryList = jdt.queryForList(sql);
					  Iterator i = queryList .iterator();
					  while(i.hasNext()) {
						ListOrderedMap data = (ListOrderedMap) i.next();
				
						String centrename = data.get("CENTRE_NAME").toString();
						String teleid = data.get("TELE_ID").toString();
						results.add(new org.apache.struts.util.LabelValueBean(centrename,teleid));
					 }
			return results;
		}
		public String getTele_Id(String centreName){
			  String sql= "SELECT TELE_ID FROM TELECENTRE WHERE ACTIVE='Y' AND CENTRE_NAME = "+centreName;
			  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			  List queryList;
			  queryList = jdt.queryForList(sql);
			  Iterator i = queryList .iterator();
			  String telecode="";
			  while(i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
		
				telecode= data.get("TELE_ID").toString();
				break;
			  }
			  return telecode;
		}
		public ArrayList getCentres()
		 {
		 			 String sql= "SELECT CENTRE_NAME,TELE_ID,CENTRE_PROVINCE AS PROVINCE FROM TELECENTRE WHERE ACTIVE='Y' ORDER BY CENTRE_NAME";
		 			  
		 			 ArrayList results = new ArrayList();
					  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
					  List queryList;
					  queryList = jdt.queryForList(sql);
					  Iterator i = queryList .iterator();
					  while(i.hasNext()) {
						ListOrderedMap data = (ListOrderedMap) i.next();
						String centrename = data.get("CENTRE_NAME").toString();
						String tele_id = data.get("TELE_ID").toString();
						String province= data.get("PROVINCE").toString();
						results.add(new org.apache.struts.util.LabelValueBean((centrename+"("+province+")"),tele_id));
					  }
	    	 		return results;
		}
		
		public List  getTelecentreDetails()
		 {
		 			  String sql= "SELECT CENTRE_NAME,PROVINCE,TELE_ID,TO_CHAR(START_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS'),TO_CHAR(END_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS')"+
		 			  " FROM TELECENTRE WHERE ACTIVE='Y' ORDER BY CENTRE_NAME";
		 			  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
					  List queryList= jdt.queryForList(sql);
					  return queryList;
		 }
		/*
		 * check telecentre code is valid or not
		 * 
		 */
		public boolean isTelecentreCodeValid(String telecentreCode, String TelecedntreName) throws Exception{
			String query = " SELECT COUNT(*) as COUNT "+
	                       " FROM TELECENTRE "+
	                       " WHERE ACTIVE = 'Y'" +
	                       " AND CENTRE_NAME = '"+TelecedntreName +"'"+
	                       " AND TELE_CODE = '"+telecentreCode +"'";
	        	try {
	        	    String recCount = this.querySingleValue(query,"COUNT");
	        	    int counter =  Integer.parseInt(recCount); 

	        		if(counter > 0){
	        			return true;
	        		}	else {
	        			return false;
	        		}
	        		
	        	} catch (Exception ex) {
	        		throw new Exception("TELECENTRE CODE VALID OR NOT: Error occurred / "+ ex,ex);
	        		
	        	}
			
	    }
		public boolean isTelecentreCodeValid(String TelecedntreName) throws Exception{
			String query = " SELECT COUNT(*) as COUNT "+
	                       " FROM TELECENTRE "+
	                       " WHERE ACTIVE = 'Y'" +
	                       " AND CENTRE_NAME = '"+TelecedntreName +"'";
	            	try {
	        	    String recCount = this.querySingleValue(query,"COUNT");
	        	    int counter =  Integer.parseInt(recCount); 

	        		if(counter > 0){
	        			return true;
	        		}	else {
	        			return false;
	        		}
	        		
	        	} catch (Exception ex) {
	        		throw new Exception("TELECENTRE CODE VALID OR NOT: Error occurred / "+ ex,ex);
	        		
	        	}
		 }
		 public String getStartTimeStamp(String studentNumber)
		{
			String sql = "select start_timestamp from student_telecentre u1 "+
						 "where u1.start_timestamp = (select max(start_timestamp) "+
	                     "from student_telecentre u2 "+
	                     "where u1.student_number = u2.student_number) "+
	                     "and u1.student_number = "+studentNumber;
			
			return this.querySingleValue(sql, "start_timestamp");
		}
		public String getCentreName(String teleId)
		 {
		 			  String sql= "SELECT CENTRE_NAME FROM TELECENTRE WHERE TELE_ID ="+teleId;
		 			  String name = this.querySingleValue(sql,"CENTRE_NAME");
					 return name;
		}
		public String getTeleId(String latestTime)
		 {
		 			  String sql= " select TELE_ID from STUDENT_TELECENTRE where " +
		 			  		      " TO_CHAR(START_TIMESTAMP, 'YYYY-MM-DD HH24:mi:SS') ='"+latestTime.substring(0,19)+"'";
		 			  
		 			  String teleId = this.querySingleValue(sql,"TELE_ID");
					  				
					  
			return teleId;
		}
		public String getTelecentreId(String latestTime, String studentNumber)
		 {
		 			  String sql= " select TELE_ID from STUDENT_TELECENTRE where " +
		 			  		      " TO_CHAR(START_TIMESTAMP, 'YYYY-MM-DD HH24:mi:SS') ='"+latestTime.substring(0,19)+"' "+
		 			  		      " and student_number = "+studentNumber;
		 			  
			return this.querySingleValue(sql,"TELE_ID");
		}
		
		/*
		 *   get the latest date and time 
		 */

		public String getLatestTime(String studentNumber)
		 {
		 			  String sql= " SELECT MAX(START_TIMESTAMP) AS LATEST FROM student_telecentre where STUDENT_NUMBER= "+studentNumber;
		 			  String latestTime = this.querySingleValue(sql,"LATEST");
				
				 
			return latestTime;
		}
		public String getEndTimestamp(String studentNumber)
		 {
		 			  String sql= " SELECT END_TIMESTAMP AS LATEST FROM student_telecentre where STUDENT_NUMBER= "+studentNumber;
		 			  String latestTime = this.querySingleValue(sql,"LATEST");
				
				 
			return latestTime;
		}

		
		/*
		 * check the student got the available hours
		 */
		public void setTimeStamps(String teleid, String studentNumber, String startTimeStamp) throws Exception{
			       dateUtil dateutil=new dateUtil();
			       //Sifiso Changes:Changed line below:2016/06/30-Changed type from int to double 
			       //int stuAvalHrs=getStudentAvailableHours(studentNumber,dateutil.getMonthInt(),dateutil.yearInt());
			       double stuAvalHrs=getStudentAvailableHours(studentNumber,dateutil.getMonthInt(),dateutil.yearInt());
			       int sessionTime=1;
			       if(stuAvalHrs>=2){
			    	   sessionTime=2;
			  
			       }
			       String query1 = //" INSERT INTO STUDENT_TELECENTRE (STUDENT_NUMBER, TELE_ID,TOTAL_HRS, START_TIMESTAMP,"+			//Sifiso Changes:Changed:2016/07/11-Added TIME_UNIT column
			    		   		  " INSERT INTO STUDENT_TELECENTRE (STUDENT_NUMBER, TELE_ID,TOTAL_HRS,TIME_UNIT, START_TIMESTAMP,"+		//Sifiso Changes
	                              //" END_TIMESTAMP) VALUES (?, ? ,? ,to_TIMESTAMP(?,'YYYY-MM-DD HH24:MI:SS.FF1'), " +					//Sifiso Changes:Changed:2016/07/11-Added TIME_UNIT column
	                              " END_TIMESTAMP) VALUES (?, ? ,?, ?,to_TIMESTAMP(?,'YYYY-MM-DD HH24:MI:SS.FF1'), " +					//Sifiso Changes
	                              " to_TIMESTAMP('"+startTimeStamp+"','YYYY-MM-DD HH24:MI:SS.FF1')+"+sessionTime+"/24)";
	               try{
	            	      JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		      		      //jdt.update(query1,new Object[] {studentNumber,teleid,stuAvalHrs,startTimeStamp});		//Sifiso Cchanges:Changed:2016/07/11-Added fourth value for 'TIME_UNIT' as 'Second'
	            	      jdt.update(query1,new Object[] {studentNumber,teleid,stuAvalHrs,"Hours",startTimeStamp});	//Sifiso Changes
	               }catch(Exception ex){
		  				throw new Exception("TelecentreDAO.java: setUsedHrs"+ex);
		  		   }
									
		}
		public void updateTimeStamps(String teleid, String studentNumber, String startTimeStamp, String endTimeStamp) throws Exception
		  
	    {
	   		
	             String query = " UPDATE STUDENT_TELECENTRE SET END_TIMESTAMP = to_date('"+endTimeStamp.substring(0,19)+"','YYYY-MM-DD HH24:mi:SS')"+
	                            " WHERE STUDENT_NUMBER = "+studentNumber+
	                            " AND TELE_ID = "+teleid+
	                            " AND TO_CHAR(START_TIMESTAMP, 'YYYY-MM-DD HH24:mi:SS') = '"+startTimeStamp.substring(0,19)+"'"; 
	        
	             JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	             try{     
	            		
		              jdt.update(query);
		             
		            } catch(Exception ex){
			                  throw new Exception("TelecentreDAO.java: updateUsedHrs"+ex);
		             }
	     }
		
		/*
		 * update if student got the open session
		 * 
		 */
		public void updateTimeStamps( String studentNumber, String startTimeStamp, String endTimeStamp) throws Exception
		  
	    {
	   		        String query = " UPDATE STUDENT_TELECENTRE SET END_TIMESTAMP =to_date('"+endTimeStamp.substring(0,19)+"','YYYY-MM-DD HH24:mi:SS')"+
	                            " WHERE STUDENT_NUMBER ="+studentNumber+
	                            " AND TO_CHAR(START_TIMESTAMP, 'YYYY-MM-DD HH24:mi:SS') ='"+startTimeStamp.substring(0,19)+"'"; 
	            
	                JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	                try{     
	            	    jdt.update(query);
		             }catch(Exception ex){
			                  throw new Exception("TelecentreDAO.java: updateUsedHrs"+ex);
		             }
	     }
		//Sifiso Changes:Changed the entire method below:2016/07/05-Modified method appears under method below
		/*public String getTotalHrs(String teleid, String studentNumber, String startTimeStamp)throws Exception
		  
	    {
			     String query1 = " select SUBSTR(TO_CHAR(END_TIMESTAMP-START_TIMESTAMP,'YYYYMMDD HH:MI:SS'),12,2) HOUR," +
			     		         " SUBSTR(TO_CHAR(END_TIMESTAMP-START_TIMESTAMP,'YYYYMMDD HH:MI:SS'),15,2) Minute," +
			     		         " SUBSTR(TO_CHAR(END_TIMESTAMP-START_TIMESTAMP,'YYYYMMDD HH:MI:SS'),18,2) Seconds "+
			                     " FROM STUDENT_TELECENTRE " +
			                     " where STUDENT_NUMBER= "+studentNumber+
			                     " AND TELE_ID= "+teleid+
			                     " AND TO_CHAR(START_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS') ='"+startTimeStamp.substring(0,19)+"'"; 
			  
			     String hours = this.querySingleValue(query1,"HOUR");
			     String minutes = this.querySingleValue(query1,"Minute");
			     String seconds = this.querySingleValue(query1,"Seconds");
			     int thours = Integer.parseInt(hours)*60*60;
			     int tminutes = Integer.parseInt(minutes)*60;
			     int tseconds = Integer.parseInt(seconds);
	             int totalSeconds = thours+tminutes+tseconds;
	             int hour = 3600;
	             double X = (double)totalSeconds/hour;
	             String totalTime="";
	             String time = Double.toString(X);
	             if(X>0&&X<1){
	            	 totalTime="1";
	             } else if(X==0){
	            	 totalTime="0";
	             }else if(time==null||time.length()==0){
	        		 totalTime="24";
	        	 } else if(X>1){
	            	
	            	 String[] strListtemp = null;
	            	

	                 strListtemp = time.split("\\.");
	                
	                 String m = strListtemp[0];
	                // String n = strListtemp[1];
	                 int p = Integer.parseInt(m);
	                 //int q = Integer.parseInt(n);
	                 if(strListtemp[1].equals("0")){
	                	 totalTime =Integer.toString(p); 
	                 }else{
	                	 p=p+1;
	                	 totalTime =Integer.toString(p); 
	                 }
	             }
	          
	            
				return totalTime;
	    }	*/
		
		//Sifiso Changes: must modify this method to reflect the true total hours for display
		public String getTotalHrs(String teleid, String studentNumber, String startTimeStamp)throws Exception
		  
	    {
			     String query1 = " select SUBSTR(TO_CHAR(END_TIMESTAMP-START_TIMESTAMP,'YYYYMMDD HH:MI:SS'),12,2) HOUR," +
			     		         " SUBSTR(TO_CHAR(END_TIMESTAMP-START_TIMESTAMP,'YYYYMMDD HH:MI:SS'),15,2) Minute," +
			     		         " SUBSTR(TO_CHAR(END_TIMESTAMP-START_TIMESTAMP,'YYYYMMDD HH:MI:SS'),18,2) Seconds "+
			                     " FROM STUDENT_TELECENTRE " +
			                     " where STUDENT_NUMBER= "+studentNumber+
			                     " AND TELE_ID= "+teleid+
			                     " AND TO_CHAR(START_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS') ='"+startTimeStamp.substring(0,19)+"'"; 
			  
			     String hours = this.querySingleValue(query1,"HOUR");
			     String minutes = this.querySingleValue(query1,"Minute");
			     String seconds = this.querySingleValue(query1,"Seconds");
			     //Sifiso changes these variables will be of no use since we want the real total time
			     /*Sifiso Changes: Begin
			     int thours = Integer.parseInt(hours)*60*60;   //Sifiso Changes: convert hours to seconds 
			     int tminutes = Integer.parseInt(minutes)*60;  //Sifiso Changes: convert minutes to seconds
			     int tseconds = Integer.parseInt(seconds);     //Sifiso Changes: seconds as is
	             int totalSeconds = thours+tminutes+tseconds;  //Sifiso Changes: sum of all seconds converted in previous three statements
	             int hour = 3600;							   //Sifiso Changes: total number of seconds in an hour (60min * 60sec=3600s)
	             double X = (double)totalSeconds/hour;		   //Sifiso Changes: get hours in terms of seconds (relect total student hours as seconds)
	             String totalTime="";
	             String time = Double.toString(X);
	             Sifiso Changes: End*/
	             //Sifiso Changes: These conditions must be eliminated to relect the real total time
	             /*Sifiso Changes: Begin
			     if(X>0&&X<1){
	            	 totalTime="1";
	             } else if(X==0){
	            	 totalTime="0";
	             }else if(time==null||time.length()==0){
	        		 totalTime="24";
	        	 } else if(X>1){
	            	
	            	 String[] strListtemp = null;
	            	

	                 strListtemp = time.split("\\.");
	                
	                 String m = strListtemp[0];
	                // String n = strListtemp[1];
	                 int p = Integer.parseInt(m);
	                 //int q = Integer.parseInt(n);
	                 if(strListtemp[1].equals("0")){
	                	 totalTime =Integer.toString(p); 
	                 }else{
	                	 p=p+1;
	                	 totalTime =Integer.toString(p); 
	                 }
	             }
	             Sifiso Changes: End*/
			    String totalTime="";
			    totalTime=hours+":"+minutes+":"+seconds;	//reflect the real total time 
				return totalTime;
	    }	
		
		public boolean isStudentHoursAllocated(String studenNumber,int  month,int year){
		    String query = "select count(student_number) as TOT_OCCUR from student_available_hrs where student_number ="+studenNumber+" and year =" +year+" and month ="+month;
		    String totOccurances = this.querySingleValue(query,"TOT_OCCUR");
		    if(totOccurances==null){
		    	return false;
		    }else{
		    	return true;
		    }
	    }
		public boolean isTelecentreHoursAllocated(String tele_id,int  month,int year){
		      String query = "select count(tele_id) as TOT_OCCUR from telecentre_available_hrs where tele_id ="+tele_id+" and year =" +year+" and month ="+month;
	          String totOccurances = this.querySingleValue(query,"TOT_OCCUR");
	         if(totOccurances==null){
	    	      return false;
	         }else{
	    	      return true;
	         }
	   }
		/*
		 *  get the duration of current visit.
		 */
		public String getDurationOfCurrentVisit(String teleid, String studentNumber, String startTimeStamp)throws Exception
		  
	    {		 
			     String query =  " SELECT TOTAL_HRS FROM STUDENT_TELECENTRE "+
			                     " WHERE STUDENT_NUMBER = "+studentNumber+
			                     " AND TELE_ID = "+teleid+
			                     " AND TO_CHAR(START_TIMESTAMP, 'YYYY-MM-DD HH24:mi:SS') ='"+startTimeStamp.substring(0,19)+"'";

	             String currentVisitDuration = this.querySingleValue(query,"TOTAL_HRS"); 
			  
			     if(currentVisitDuration.equals("0")||currentVisitDuration==null||currentVisitDuration.equals("")){
			    	 currentVisitDuration ="0";
			     }else{
			    
			     }
			    
	            
				return currentVisitDuration;
									
		 }	
		
		/*
		 * updating the totalhrs 
		 */
		public void updateTotalHrs(String teleid, String studentNumber, String startTimeStamp ,String totalTime, String timeUnit) throws Exception
		  
	    {
	   		
				 //Sifiso Changes:Changed line below:2016/07/11-Added 'TIME_UNIT' column
	             //String  query = " UPDATE STUDENT_TELECENTRE SET TOTAL_HRS ="+totalTime+
				 String  query = " UPDATE STUDENT_TELECENTRE SET TOTAL_HRS ="+totalTime+", TIME_UNIT = "+timeUnit+	//Sifiso Changes
	                             " WHERE STUDENT_NUMBER = "+studentNumber+
	                             " AND TELE_ID = "+teleid+
	            	             " AND TO_CHAR(START_TIMESTAMP, 'YYYY-MM-DD HH24:mi:SS') ='"+startTimeStamp.substring(0,19)+"'"; 


	             JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	              try{     
	            		
		           jdt.update(query);
		             
		            }catch(Exception ex){
			                  throw new Exception("TelecentreDAO.java: updateTotalHrs"+ex);
		            }
	     }
		
		/*
		 * get the e-mail 
		 */
		public String getEmail(String teleId)
		 {
		 			  String sql= "SELECT EMAIL_ADDRESS FROM TELECENTRE WHERE TELE_ID ="+teleId;
		 			  
		 			  String email = this.querySingleValue(sql,"EMAIL_ADDRESS");
					
						  				
					  
			return email;
		}
		
		/*
		 *  check student got open session
		 */
		
		public boolean checkOPenSession(String studentNumber, String latestTime)
		 {
			
		            boolean student;	
		 			  String sql= " SELECT END_TIMESTAMP FROM STUDENT_TELECENTRE "+ 
		 			              " WHERE STUDENT_NUMBER = "+studentNumber+
		 			              " AND TO_CHAR(START_TIMESTAMP, 'YYYY-MM-DD HH24:mi:SS') ='"+latestTime.substring(0,19)+"'"+
		 			              " and to_char(sysdate,'YYYY-MM-DD HH24:MI:SS') between TO_CHAR(START_TIMESTAMP, 'YYYY-MM-DD HH24:mi:SS') and TO_CHAR(END_TIMESTAMP, 'YYYY-MM-DD HH24:mi:SS')";
		 			  
		 			  
		 			  String endTime = this.querySingleValue(sql,"END_TIMESTAMP");
		 			  
		 	
		 			 int latestEndTime;
		 		     if(endTime.equals("0")||endTime==null||endTime.equals("")){
		 		    	latestEndTime =0;
		 		    	student=false; // session not open
		 		     }else{
		 		    
		 		    	//latestEndTime = Integer.parseInt(endTime);
		 		    	student=true; //session open
		 		     }
		 		     /*
		 			  int latestStartTime;
		 			 if(latestTime.equals("0")||latestTime==null||latestTime.equals("")){
		 			    latestStartTime =0;
			 		  }else{
			 		    latestStartTime = Integer.parseInt(latestTime); 
			 		  }
		 			 int time = latestEndTime-latestStartTime;
		 			 
					if(time==6){
						student = true;
					}else{
						student=false;
					}
					*/
		 		     //3417039  
						  				
					  
			return student;
		}
		
		/*
		 * 
		 * GET THE TELECENTRE NAME IF WE HAVE OPEN SESSION
		 */
		public String getcentreName(String studentNumber, String latestTime)
		 {
			String telecentreName ="";
		 			  
		 			 String query = " SELECT TELE_ID FROM STUDENT_TELECENTRE "+ 
		                            " WHERE STUDENT_NUMBER = "+studentNumber+
			                        " AND TO_CHAR(START_TIMESTAMP, 'YYYY-MM-DD HH24:mi:SS') ='"+latestTime.substring(0,19)+"'"+
			                        " and to_char(sysdate,'YYYY-MM-DD HH24:MI:SS') between TO_CHAR(START_TIMESTAMP, 'YYYY-MM-DD HH24:mi:SS') and TO_CHAR(END_TIMESTAMP, 'YYYY-MM-DD HH24:mi:SS')";
		 			
		 			 String teleId = this.querySingleValue(query,"TELE_ID");
		 			 
		 			 String query1 = " SELECT CENTRE_NAME FROM TELECENTRE "+ 
	                               " WHERE TELE_ID = "+teleId;
	                              
		 			 telecentreName = this.querySingleValue(query1,"CENTRE_NAME");
		 		return telecentreName;
		}
		private int availableHours(String sql,String dataType){
			   String data=querySingleValue(sql,dataType);
			   if(data.equals("")){
				    while(data.equals("")){
				    	data=querySingleValue(sql,dataType);
				    }
			   }
			   Integer res=new Integer(data);
			   return res.intValue();
		}
		//Sifiso Changes:Added:2016/07/04-Added below method to return a double type value for method availableHours()
		private double availableHoursDouble(String sql,String dataType){		
			   String data=querySingleValue(sql,dataType);
			   if(data.equals("")){
				   while(data.equals("")){
				    	data=querySingleValue(sql,dataType);
				   }
			   }
			   Double res=new Double(data);
			   return res.doubleValue();
		}
		//Sifiso Changes:Changed line below:2016/07/04-Changed the return type to double
		//public int getStudentAvailableHours(String studentNo, int month,int year){
		public double getStudentAvailableHours(String studentNo, int month,int year){	//Sifiso Changes
		      String sql="SELECT HOURS FROM STUDENT_AVAILABLE_HRS "+
		                  "WHERE STUDENT_NUMBER = "+studentNo+
		                  " AND MONTH = "+month+ " AND YEAR = "+year;
		      
		      //return  availableHours(sql,"HOURS");		//Sifiso Changes:Changed:2016/06/30-Return the HOURS column as a double
		      return  availableHoursDouble(sql,"HOURS");	//Sifiso Changes
		}
		//Sifiso Changes:Changed line below:2016/07/04-Changed the return type to double
	    //public int getTelecentreAvailableHours(String teleID, int month,int year){
		public double getTelecentreAvailableHours(String teleID, int month,int year){		//Sifiso Changes
	    	    String sql="SELECT HOURS FROM TELECENTRE_AVAILABLE_HRS "+
		         "WHERE TELE_ID = "+teleID +
		         " AND MONTH = "+month+ " AND YEAR = "+year;
	    	     //return  availableHours(sql,"HOURS");		//Sifiso Changes:Changed line:2016/07/04-Return the HOURS column as double
	    	     return  availableHoursDouble(sql,"HOURS");	//Sifiso Changes
	    }
		//Sifiso Changes:Changed line below:2016/07/05-Changed to double type for 'totHours' method parameter
	    //public  void updateStuAvailHours(int studentNum,int totHours){
		public  void updateStuAvailHours(int studentNum,double totHours){
	        dateUtil  dateutil=new dateUtil();
	        //Sifiso Changes:Changed line below:2016/06/30-Changed the type to double for 'stuAvailHrs' variable
	        //int stuAvailHrs=getStudentAvailableHours(""+studentNum,dateutil.getMonthInt(),dateutil.yearInt());
	        double stuAvailHrs=getStudentAvailableHours(""+studentNum,dateutil.getMonthInt(),dateutil.yearInt());
	        stuAvailHrs-=totHours;
	        if(stuAvailHrs<0){
		             stuAvailHrs=0;
	        }
	        updateStudentHours(""+studentNum,dateutil.getMonthInt(),stuAvailHrs,dateutil.yearInt());
	    }
		   //Sifiso Changes:Changed line below:2016/07/05-Changed to double type for 'totHours' method parameter
		   //public  void updateTeleAvailHours(String teleCode,int totHours){
		   public  void updateTeleAvailHours(String teleCode,double totHours){
	            dateUtil  dateutil=new dateUtil();
	            //Sifiso Changes:Changed line below:2016/06/30-Changed type to double for 'centreAvailHrs' variable
	            //int centreAvailHrs=getTelecentreAvailableHours(teleCode,dateutil.getMonthInt(),dateutil.yearInt());
	            double centreAvailHrs=getTelecentreAvailableHours(teleCode,dateutil.getMonthInt(),dateutil.yearInt());
	            centreAvailHrs-=totHours;
	            if(centreAvailHrs<0){
		                  centreAvailHrs=0;
	            }
	            updateCentreHours(teleCode,dateutil.getMonthInt(),centreAvailHrs,dateutil.yearInt());
	    }
		//Sifiso Changes:Changed line below:2016/07/05-Changed to double type for 'hours' parameter
		//public void updateStudentHours(String  studentNo,int month, int hours,int year){
		public void updateStudentHours(String  studentNo,int month, double hours,int year){
		      String sql="UPDATE STUDENT_AVAILABLE_HRS "+
		              "SET HOURS = "+hours+					
		              " WHERE STUDENT_NUMBER = "+studentNo+
		              " AND MONTH = "+month+ 
		              " AND YEAR = "+year;
		      updateUsingJdbcTemplate(sql);
		}
		//Sifiso Changes:2016/07/05:Changed line below-Changed to double type for 'hours' parameter
		//public void updateCentreHours(String  teleID,int month, int hours,int year){
		public void updateCentreHours(String  teleID,int month, double hours,int year){
		        String sql="UPDATE TELECENTRE_AVAILABLE_HRS "+
		                "SET HOURS = "+hours+
		                " WHERE TELE_ID ="+teleID+
		                " AND MONTH = "+month+ 
		                " AND YEAR ="+year;
		        updateUsingJdbcTemplate(sql);
		}
		private void updateUsingJdbcTemplate(String sql){
			 try{
					JdbcTemplate jdt = new JdbcTemplate(getDataSource());
					jdt.update(sql);
				} catch (Exception ex) {} 
		}
		public int getStudentTotalAllocHours(int StudentNum,int month){
			       String sql="select sum(hours) from hours_allocation_audit where student_number = "+StudentNum+" and month = "+month;
			       return availableHours(sql,"HOURS");
		}
		
		//Sifiso Changes:Changed line below:2016/07/05-Changed the return type to double
		//public int getStudentTotalUsedHours(int StudentNum,int month){
		public double getStudentTotalUsedHours(int StudentNum,int month){
			         String sql="select sum(total_hrs) from student_telecentre where student_number ="+StudentNum+" and month = "+month;
			         //return availableHours(sql,"HOURS");		//Sifiso Changes:Changed:2016/07/05-Return a double type value
					 return availableHoursDouble(sql,"total_hrs");
		}


		public int getStudentExtendedHours(String  studentNo,  String month){
	        String sql="SELECT SUM (HOURS)	FROM HOURS_ALLOCATION_AUDIT "+
	            "WHERE STUDENT =" +studentNo+
	            " AND MONTH = "+month;
	        return availableHours(sql, "HOURS");
	   }
	   public  int  getCentreExtendedHours(String  teleID,  String month){
	        String sql="SELECT SUM (HOURS) "+
	        "FROM HOURS_ALLOCATION_AUDIT "+
	        "WHERE TELECENTRE = teleID "+
	        " AND MONTH = "+month;
	        return availableHours(sql,"HOURS");
	  }
	   public ArrayList getLastDayVisits(){
			
	   	     ArrayList results = new ArrayList();
		     DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	         dateUtil dateutil=new dateUtil();
	         String sql = " SELECT TO_CHAR(START_TIMESTAMP,'HH24:MI') AS TIME_IN,TO_CHAR(END_TIMESTAMP, 'HH24:MI') AS TIME_OUT,STUDENT_NUMBER,TELE_ID,"+
	        		 " TIME_UNIT AS UNIT,"+		//Sifiso Changes:Added:2016/07/11-Time unit in seconds,minutes,hours
	                 " TOTAL_HRS AS DURATION  FROM STUDENT_TELECENTRE WHERE TO_CHAR(START_TIMESTAMP, 'YYYY-MM-DD')= "+"'"+dateutil.getPrevDate()+"'  ";
	         
	         JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		     List queryList;
		     queryList = jdt.queryForList(sql);
		     Iterator i = queryList .iterator();
		     while (i.hasNext()){	
			       ListOrderedMap data = (ListOrderedMap) i.next();
			       
	               StudentTelecentre telecentreDetails = new StudentTelecentre();
			       try{
		    	       telecentreDetails.setStudentNumber(Integer.parseInt(data.get("STUDENT_NUMBER").toString()));
			           telecentreDetails.setStartTime(data.get("TIME_IN").toString());
			           telecentreDetails.setEndTime(data.get("TIME_OUT").toString());
			           //Sifiso Changes:Changed line below:2016/07/05-To get the used hours as a double type
			           //telecentreDetails.setUsedHours(Integer.parseInt(data.get("DURATION").toString()));
			           telecentreDetails.setUsedHours(Double.parseDouble(data.get("DURATION").toString()));	//Sifiso Changes
			           telecentreDetails.setTeleId(Integer.parseInt(data.get("TELE_ID").toString()));
			           telecentreDetails.setTimeUnit(data.get("UNIT").toString());		//Sifiso Changes:Added:2016/07/11-Time unit in seconds,minutes,hour
			           int pos=checkValueExistInList(results,telecentreDetails.getStudentNumber());
			           addRecordOfVisit(results,pos,telecentreDetails);
			       }catch(NullPointerException e ){e.printStackTrace();}
	         }			  
	         return results;
	    }
	    public ArrayList getCurrentVisits(){
			
	   	     ArrayList results = new ArrayList();
		     DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	         Date date = new Date();
	         String dateStr = dateFormat.format(date);
	         String sql = " SELECT TO_CHAR(START_TIMESTAMP,'HH24:MI') AS TIME_IN,TO_CHAR(END_TIMESTAMP, 'HH24:MI') AS TIME_OUT,STUDENT_NUMBER,TELE_ID,"+
	                 " TIME_UNIT AS UNIT,"+							//Sifiso Changes:Added:2016/07/11-Time unit in seconds,minutes,hours
	                 " TOTAL_HRS AS DURATION  FROM STUDENT_TELECENTRE WHERE TO_CHAR(START_TIMESTAMP, 'YYYY-MM-DD')= "+"'"+dateStr+"'  ";
	         JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		     List queryList;
		     queryList = jdt.queryForList(sql);
		     Iterator i = queryList .iterator();
		     while (i.hasNext()){	
			       ListOrderedMap data = (ListOrderedMap) i.next();
	               StudentTelecentre telecentreDetails = new StudentTelecentre();
			       try{
		    	       telecentreDetails.setStudentNumber(Integer.parseInt(data.get("STUDENT_NUMBER").toString()));
			           telecentreDetails.setStartTime(data.get("TIME_IN").toString());
			           telecentreDetails.setEndTime(data.get("TIME_OUT").toString());
			           //Sifiso Changes:Changed line below:2016/07/05-To get the used hours as a double type
			           //telecentreDetails.setUsedHours(Integer.parseInt(data.get("DURATION").toString()));
			           telecentreDetails.setUsedHours(Double.parseDouble(data.get("DURATION").toString()));  //Sifiso Changes
			           telecentreDetails.setTeleId(Integer.parseInt(data.get("TELE_ID").toString()));
			           telecentreDetails.setTimeUnit(data.get("UNIT").toString());		//Sifiso Changes:Added:2016/07/11-Time unit in seconds,minutes,hour
			           int pos=checkValueExistInList(results,telecentreDetails.getStudentNumber());
			           addRecordOfVisit(results,pos,telecentreDetails);
			       }catch(NullPointerException e ){e.printStackTrace();}
	         }			  
	         return results;
	    }
       
        private int checkValueExistInList(ArrayList results,int stuNum){
        	//if  the record exists  the method returns it's index,otherwise it return zero
        	                   StudentTelecentre  stuTelDet;
        	                   Iterator i=results.iterator();
        	                   int x=0;
        	                   boolean found=false;
        	                   while(i.hasNext()){
        	                	   stuTelDet=(StudentTelecentre)i.next();
        	                	   if((stuTelDet.getStudentNumber()==stuNum)){
        	                		   found=true;
        	                		   break;
           	                	   }else{
        	                		   x++;
        	                	   }
        	                   }
        	                   if(found){
        	                       return x;
        	                   }else{
        	                	   return -1;
        	                   }
        }
        private void addRecordOfVisit(ArrayList results,int pos,StudentTelecentre newRec){
        	              StudentTelecentre stuTelDet;
        	              if(pos==-1){
        	            	  results.add(newRec);
        	              }else{
        	            	     stuTelDet=(StudentTelecentre)results.get(pos);
        	                     dateUtil dateutil=new dateUtil();
        	                     if((dateutil.getGreaterTime(newRec.getStartTime(),stuTelDet.getStartTime()))){
        	        	               results.remove(pos);
        	        	               results.add(newRec);
   	                             }   
        	              }
        	  
        }
        public String selectUserId(String userEID) throws Exception {
			String userId = "";
			
			String sql1 = " SELECT USER_ID "+
				          " FROM SAKAI_USER_ID_MAP" +
				          " WHERE EID = '"+userEID+"'";

			try{
				userId = this.querySingleValue(sql1,"USER_ID");	
			} catch (Exception ex) {
				throw new Exception("SatbookDAO: selectUserId: Error occurred / "+ ex,ex);
			}
			return userId;
		}
		public String getUserRights(String userId, String userEID) throws Exception {
	        String userTmp;
	        String userPermission;
	        String upperCaseUserEID="";
	        String upperCaseUserId="";
	        String sql1 = "SELECT PERMISSION "+
	                        "FROM   SAKAI_SITE_USER "+
	                        "WHERE  SITE_ID = 'Telecentre' "+ 
	                        "AND    ((upper(USER_ID) = upper('"+userEID+"')) "+
	                        "OR    (USER_ID = '"+userId+"'))";
	        try{
	              userTmp = this.querySingleValue(sql1,"PERMISSION");

	        } catch (Exception ex) {
	              throw new Exception("MAINTAINSTAFFSTUDENTDAO: getUserRights: Error occurred / "+ ex,ex);
	        }

	        if (userTmp.equals("-1")) {
	              userPermission = "MAINTAIN";
	        } else {
	              userPermission = "ACCESS";
	        }
	        return userPermission;
	  }
	  private String querySingleValue(String query, String field){
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList;
			String result = "";
			

			queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
	           ListOrderedMap data = (ListOrderedMap) i.next();
	           if (data.get(field) == null){
	           } else {
	               result = data.get(field).toString();
	               
	           }
			}
			return result;
	   }

    public void sendEmail(String subject, String body, String emailAddress) throws AddressException {

		String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");

		InternetAddress toEmail = new InternetAddress(emailAddress);
		InternetAddress iaTo[] = new InternetAddress[1];
		iaTo[0] = toEmail;
		InternetAddress iaHeaderTo[] = new InternetAddress[1];
		iaHeaderTo[0] = toEmail;
		InternetAddress iaReplyTo[] = new InternetAddress[1];
		iaReplyTo[0] = new InternetAddress(tmpEmailFrom);
		List contentList = new ArrayList();
		contentList.add("Content-Type: text/html");
		//contentList.add("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		EmailService emailService=(EmailService)ComponentManager.get(EmailService.class);
		emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
		
	} 
    public  String getBodyOfClosingEmail(String stuNum,String onlyDate,String onlyTime ){
      
      String body = "<html> "+
		  "<body> "+
		  "The session has expired for folowing student "+stuNum+"  <br><br>"+
          "Date: "+onlyDate+"<br>"+
          "Time ended: "+onlyTime+"<br><br>"+
          "Regards  <br>"+
			  "UNISA ICT"+
          "</body>"+
          "</html>";
      return body;

   }
    public void updateTotalHrs(String teleid, String studentNumber,String totalTime) 
	  
    {
	         String  query = " UPDATE STUDENT_TELECENTRE SET TOTAL_HRS ="+totalTime+
                             " WHERE STUDENT_NUMBER = "+studentNumber+
                             " AND TELE_ID = "+teleid+" AND  TOTAL_HRS=0" ; 

             JdbcTemplate jdt = new JdbcTemplate(getDataSource());
              try{     
            		
	           jdt.update(query);
	             
	            }catch(Exception ex){
		                 
	            }
     }
    //public int getUsedHours(String  stuNum){		//Sifiso Changes:Changed:2016/07/04-Changed method return type
    public double getUsedHours(String  stuNum){		//Sifiso Changes
   	 //used to get the record of the telecentre  visit still in session
   			String sql = " SELECT total_hrs from student_telecentre  where student_number="+stuNum;
            String  hrsStr="";
   			//return availableHours(sql,"TOTAL_HRS");		//Sifiso Changes:Changed:2016/06/30-Return the TOTAL_HRS column data as double
            return availableHoursDouble(sql,"total_hrs");	//Sifiso Changes
    }
    /*
	 * gets site STATS export details.
	 */
	
	public ArrayList getTelecentreCodesList(){
		
	   String sql =  "SELECT * FROM TELECENTRE ORDER BY TELE_ID";
	      ArrayList results = new ArrayList();
		  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		  List queryList;
		  queryList = jdt.queryForList(sql);
		  Iterator i = queryList .iterator();
		  
	  while (i.hasNext())
	  {	ListOrderedMap data = (ListOrderedMap) i.next();
	  TelecentreCodes telecentreCodes = new TelecentreCodes();
			
		try{
			telecentreCodes.setTeleId(data.get("TELE_ID").toString());
			telecentreCodes.setCentreProvince(data.get("CENTRE_PROVINCE").toString());
			telecentreCodes.setCentreName(data.get("CENTRE_NAME").toString());
			telecentreCodes.setEmailId(data.get("EMAIL_ADDRESS").toString());
			telecentreCodes.setMonthlyHrs(data.get("MONTHLY_HOURS").toString());
			telecentreCodes.setActive(data.get("ACTIVE").toString());
			telecentreCodes.setTeleCode(data.get("TELE_CODE").toString());
			results.add(telecentreCodes);
			}
	    catch(NullPointerException e ){e.printStackTrace();}
		
	  }			  
 return results;
}
	
	 //Sifiso Changes:2017/03/10:SR63678:Used to get the maximum number of TELE_ID values so that all records are updated-Opposed to using count of records
	 public int getMaxTeleId( ) throws Exception {
			
			String query = "select max(tele_id) as tele from telecentre order by tele_id";
	        	
	          String recCount = this.querySingleValue(query,"tele");
	          int count =  Integer.parseInt(recCount);
	          return count;
	 }
	 
	 //Sifiso Changes:2017/03/10:SR63678:To check if TELE_ID exists so that TELE_CODE can be updated instead of record count (TELE_ID)-reduces DB update time
	 public boolean isTelecentreIDExist(int teleId) throws Exception{
			String query = " SELECT COUNT(*) as COUNT "+
	                    " FROM TELECENTRE "+
	                    " WHERE TELE_ID = '"+teleId +"'";
	         	try {
	     	    String recCount = this.querySingleValue(query,"COUNT");
	     	    int counter =  Integer.parseInt(recCount); 
	
	     		if(counter > 0){
	     			return true;
	     		}	else {
	     			return false;
	     		}
	     		
	     	} catch (Exception ex) {
	     		throw new Exception("TELECENTRE ID EXISTS OR NOT: Error occurred / "+ ex,ex);
	     		
	     	}
		}
	 
	  //Sifiso Changes:2017/03/15:SR63678:To optimize SQL update - Used in updateTelecode(String,int)
	  private PreparedStatementCreator updateTeleCodePreparedStatementCreator(final String teleCodeValue, final int teleId){
		  PreparedStatementCreator psCreator = new PreparedStatementCreator(){
			  @Override
			  public PreparedStatement createPreparedStatement(Connection conn) throws SQLException{
				  PreparedStatement updateTeleCodesPs = conn.prepareStatement(
						 "UPDATE TELECENTRE SET TELE_CODE =? WHERE TELE_ID=?");
				  updateTeleCodesPs.setString(1, teleCodeValue);
				  updateTeleCodesPs.setInt(2, teleId);
				  return updateTeleCodesPs;
			  }
		  };
		  return psCreator;
	  }
    
}
