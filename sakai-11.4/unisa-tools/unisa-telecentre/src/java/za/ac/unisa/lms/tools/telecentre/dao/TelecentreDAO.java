package za.ac.unisa.lms.tools.telecentre.dao;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.sql.Timestamp;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.collections.map.ListOrderedMap;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.db.SakaiDAO;
import za.ac.unisa.lms.tools.telecentre.dao.StudentTelecentre;
import za.ac.unisa.lms.tools.telecentre.forms.dateUtil;
import za.ac.unisa.lms.tools.telecentre.validate.ValidateTele;
public class TelecentreDAO extends SakaiDAO {
  public ArrayList getTelecentres()
	 {
				 String sql= "SELECT Centre_province ,CENTRE_NAME,TELE_ID FROM TELECENTRE WHERE ACTIVE='Y' ORDER BY CENTRE_NAME";
	 			  
	 			 ArrayList results = new ArrayList();
				  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				  List queryList;
				  queryList = jdt.queryForList(sql);
				  Iterator i = queryList .iterator();
				  while(i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
			
					String centrename = data.get("CENTRE_NAME").toString();
					String teleid = data.get("TELE_ID").toString();
					//01November2014
					String province  = data.get("Centre_province").toString();
					
					
					results.add(new org.apache.struts.util.LabelValueBean(centrename+" : "+province,teleid));
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
		          String name ="";
		          StringBuilder queryBuilder  = new StringBuilder();
	 			 
		          if(!ValidateTele.isEmpty(teleId))
		          {
		        	
		        	  queryBuilder.append("SELECT CENTRE_NAME FROM TELECENTRE WHERE TELE_ID ="+teleId);	 					
	 						name = this.querySingleValue(queryBuilder.toString(),"CENTRE_NAME");
	 						queryBuilder=null;
	 						
		          }  
	 			 
				 return name;
	}
	public String getTeleId(String latestTime)
	 {
	 			  String sql= " select TELE_ID from STUDENT_TELECENTRE where " +
	 			  		      " TO_CHAR(START_TIMESTAMP, 'YYYY-MM-DD HH24:mi:SS') ='"+latestTime.substring(0,19)+"'";
	 			  
	 			  String teleId = this.querySingleValue(sql,"TELE_ID");
				  				
				  
		return teleId;
	}
	public String  getTele_id(String sql){
		  ArrayList results = new ArrayList();
		  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		  List queryList = jdt.queryForList(sql);
		  Iterator i = queryList.iterator();
		  String tele_id="";
		  if(i.hasNext()){
			  ListOrderedMap data = (ListOrderedMap) i.next();
			  tele_id = data.get("TELE_ID").toString();
		  }
		return tele_id;
    }
	public String getTeleId(int stuNum){
		      dateUtil dateutil=new dateUtil();
	          String dateStr = dateutil.dateOnly();
	          String sql= " select TELE_ID from STUDENT_TELECENTRE where " +
		      " TO_CHAR(START_TIMESTAMP, 'YYYY-MM-DD') ='"+dateStr+"' and student_number="+stuNum;
              String teleId = getTele_id(sql);
	          return teleId;
    }
	public String getTelecentreId(String latestTime, String studentNumber)
	 {
	 			  String sql= " select TELE_ID from STUDENT_TELECENTRE where " +
	 			  		      " TO_CHAR(START_TIMESTAMP, 'YYYY-MM-DD HH24:mi:SS') ='"+latestTime.substring(0,19)+"' "+
	 			  		      " and student_number = "+studentNumber;
	 			  
		return this.querySingleValue(sql,"TELE_ID");
	}
	public String getTelecentreId(String studentNumber)
	 {
	 			  String sql= " select TELE_ID from STUDENT_TELECENTRE where " +
	 			  		      " total_hrs=0"+
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
	
	//Sifiso Changes:Added:2016/09/08: For SR - SR8071
	public String getLatestEndTimeStamp(String studentNumber)
	{
		String sql = "select end_timestamp from student_telecentre u1 "+
					 "where u1.end_timestamp = (select max(end_timestamp) "+
                    "from student_telecentre u2 "+
                    "where u1.student_number = u2.student_number) "+
                    "and u1.student_number = "+studentNumber;
		
		return this.querySingleValue(sql, "end_timestamp");
	}

	
	
	/*
	 * check the student got the available hours
	 */
	public void setTimeStamps(String teleid, String studentNumber, String startTimeStamp) throws Exception{
		// This is called when the user logs in.The start time stamp is set to the timestamp at that point of logon, the 
		// endTimeStamp is initialized to the timestap we get by adding student_Available_hrs to the timestamp on logn
   		
			   dateUtil dateutil=new dateUtil();
			   //Sifiso Changes:Changed line below:2016/06/30-To change type from int to double 
		       //int stuAvalHrs=getStudentAvailableHours(studentNumber,dateutil.getMonthInt(),dateutil.yearInt()); 
		       double stuAvalHrs=getStudentAvailableHours(studentNumber,dateutil.getMonthInt(),dateutil.yearInt()); //Sifiso Changes
		       int sessionTime=1;
		       if(stuAvalHrs>=2){
		    	   sessionTime=2;
		  
		       }
		       String query1 = //" INSERT INTO STUDENT_TELECENTRE (STUDENT_NUMBER, TELE_ID,TOTAL_HRS, START_TIMESTAMP,"+ 		  //Sifiso Changes:Changed:2016/07/11-Added 'TIME_UNIT' column
		    		   		   " INSERT INTO STUDENT_TELECENTRE (STUDENT_NUMBER, TELE_ID,TOTAL_HRS,TIME_UNIT, START_TIMESTAMP,"+  //Sifiso Changes
                              //" END_TIMESTAMP) VALUES (?, ? ,? ,to_TIMESTAMP(?,'YYYY-MM-DD HH24:MI:SS.FF1'), " +		 		  //Sifiso Changes:Changed:2016/07/11-Added 'TIME_UNIT' column
                              " END_TIMESTAMP) VALUES (?, ? ,? ,?,to_TIMESTAMP(?,'YYYY-MM-DD HH24:MI:SS.FF1'), " +		 		  //Sifiso Changes
                              " to_TIMESTAMP('"+startTimeStamp+"','YYYY-MM-DD HH24:MI:SS.FF1')+"+sessionTime+"/24)";
               try{
            	      JdbcTemplate jdt = new JdbcTemplate(getDataSource());
            	      int stuNum=Integer.parseInt(studentNumber);
            	      ArrayList currVisits=getAllCurrVisitsForStu(stuNum);
            	      int totSessionsOn=0;
            	      if(currVisits!=null){
            	     	  totSessionsOn=currVisits.size();
            	      }
            	      String storedStartTimeStamp=getStartTimeStamp(studentNumber);
      	    	      int len1=storedStartTimeStamp.lastIndexOf(':');
      	    	      int len2=startTimeStamp.lastIndexOf(':');
      	    	      String substrOfstoredTime="";
      	    	      try{
      	    	       substrOfstoredTime=storedStartTimeStamp.substring(0,len1);
      	    	      }catch(IndexOutOfBoundsException ie){}
      	    	      String substrOfNewTime=startTimeStamp.substring(0,len2);
      	    	      if(substrOfstoredTime.equals(substrOfNewTime)){
    		              return;
      	    	      }
            	      if(totSessionsOn==0){
            	    	   //Sifiso Changes:Changed Line below:2016/07/11-To add the fourth default value for 'TIME_UNIT' as 'Hours'
            	    	   //jdt.update(query1,new Object[] {studentNumber,teleid,0,startTimeStamp}); 
            	    	   jdt.update(query1,new Object[] {studentNumber,teleid,0,"Hours",startTimeStamp}); 	//Sifiso Changes
            	      }
		  		     
               }catch(Exception ex){
	  				throw new Exception("TelecentreDAO.java: setUsedHrs"+ex);
	  		   }
								
	}
	public int activeSessions(int stuNum){
		//check for sessions based on end_timestamp that is bigger than systime
		         ArrayList currVisits=getAllCurrVisitsForStu(stuNum);
	             if(currVisits!=null){
	     	         return currVisits.size();
	             }else{
	        	    return 0;
	             }
	}
	
	/*
	 * updating student used hrs.
	 */
	
	public void updateTimeStamps(String teleid, String studentNumber, String startTimeStamp, String endTimeStamp) throws Exception
	  
    {
		     dateUtil dateutil=new dateUtil();
		     //Sifiso Changes:Changed line below:2016/06/30-To change type from int to double 
	         //int stuAvalHrs=getStudentAvailableHours(studentNumber,dateutil.getMonthInt(),dateutil.yearInt());
	         double stuAvalHrs=getStudentAvailableHours(studentNumber,dateutil.getMonthInt(),dateutil.yearInt()); //Sifiso Changes
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
	//Sifiso Changes:Changed line below:2016/07/04-To return the total_hrs column as double
	//public int getTotalHrsBeforeUpdate(String teleid, String studentNumber, String startTimeStamp)throws Exception{
	public double getTotalHrsBeforeUpdate(String teleid, String studentNumber, String startTimeStamp)throws Exception{  //Sifiso Changes
		String query1 = " select  total_hrs FROM STUDENT_TELECENTRE  where STUDENT_NUMBER= "+studentNumber+
                        " AND TELE_ID= "+teleid+
                        " AND TO_CHAR(START_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS') ='"+startTimeStamp.substring(0,19)+"'";
						String hours = this.querySingleValue(query1,"total_hrs");
		                
		                
		                //return Integer.parseInt(hours);	//Sifiso Changes:Changed:2016/07/04-We must return double value
		                return Double.parseDouble(hours);	//Sifiso Changes

	}
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
	public void updateTotalHrs(String teleid, String studentNumber, String startTimeStamp ,String totalTime ,String timeUnit) throws Exception
	  
    {
   		
			//Sifiso Changes:Changed line below:2016/06/30-Added TIME_UNIT column:2016/07/11
	        //String  query = " UPDATE STUDENT_TELECENTRE SET TOTAL_HRS ="+totalTime+
			String  query = " UPDATE STUDENT_TELECENTRE SET TOTAL_HRS ="+totalTime+", TIME_UNIT = '"+timeUnit+"'"+ //Sifiso Changes
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
	//Sifiso Changes:2016/08/26:Added 'timeUnit' parameter and sql set statement
	public void updateTotalHrs(String teleid, String studentNumber,String totalTime,String timeUnit) 
	  
    {
             //String  query = " UPDATE STUDENT_TELECENTRE SET TOTAL_HRS ="+totalTime+
			 String  query = " UPDATE STUDENT_TELECENTRE SET TOTAL_HRS ="+totalTime+","+
					 		 " TIME_UNIT ='"+timeUnit+"'"+	//Sifiso Changes
                             " WHERE STUDENT_NUMBER = "+studentNumber+
                             " AND TELE_ID = "+teleid+" AND  TOTAL_HRS=0" ;

             JdbcTemplate jdt = new JdbcTemplate(getDataSource());
              try{     
            		
	           jdt.update(query);
	             
	            }catch(Exception ex){
		                 
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
	 			 StringBuilder queryBuilder = new StringBuilder();
	 			 StringBuilder queryBuilder2 = new StringBuilder();
	 			 
	 			 
	 		/*	 String query = " SELECT TELE_ID FROM STUDENT_TELECENTRE "+ 
	                            " WHERE STUDENT_NUMBER = "+studentNumber+
		                        " AND TO_CHAR(START_TIMESTAMP, 'YYYY-MM-DD HH24:mi:SS') ='"+latestTime.substring(0,19)+"'"+
		                        " and to_char(sysdate,'YYYY-MM-DD HH24:MI:SS') between TO_CHAR(START_TIMESTAMP, 'YYYY-MM-DD HH24:mi:SS') and TO_CHAR(END_TIMESTAMP, 'YYYY-MM-DD HH24:mi:SS')";*/
	 			
	 			queryBuilder.append(" SELECT TELE_ID FROM STUDENT_TELECENTRE ");
	 			queryBuilder.append(" WHERE STUDENT_NUMBER = "+studentNumber);
	 			queryBuilder.append(" AND TO_CHAR(START_TIMESTAMP, 'YYYY-MM-DD HH24:mi:SS') ='"+latestTime.substring(0,19)+"'");
	 			queryBuilder.append(" and to_char(sysdate,'YYYY-MM-DD HH24:MI:SS') between TO_CHAR(START_TIMESTAMP, 'YYYY-MM-DD HH24:mi:SS') and TO_CHAR(END_TIMESTAMP, 'YYYY-MM-DD HH24:mi:SS')");
	 			
	 			 String teleId = this.querySingleValue(queryBuilder.toString(),"TELE_ID");
	 			 queryBuilder = null;
	 			  if(!ValidateTele.isEmpty(teleId))
		          {
	 					queryBuilder2.append("SELECT CENTRE_NAME FROM TELECENTRE");
	 					queryBuilder2.append( " WHERE TELE_ID = "+teleId);
	 					telecentreName = this.querySingleValue(queryBuilder2.toString(),"CENTRE_NAME");
	 	
		          }
	 		/*	 String query1 = " SELECT CENTRE_NAME FROM TELECENTRE "+ 
                               " WHERE TELE_ID = "+teleId;*/
                              
	 			 queryBuilder2=null;
	 			 
	 		return telecentreName;
	}
	public String getcentreName(String studentNumber){
	     		String telecentreName ="";
	 			  
	     		
	 			 String query = " SELECT TELE_ID FROM STUDENT_TELECENTRE "+ 
	                            " WHERE STUDENT_NUMBER = "+studentNumber+
		                        " AND TOTAL_HRS=0";
	 			
	 			 String teleId = this.querySingleValue(query,"TELE_ID");
	 		
	 			if(!ValidateTele.isEmpty(teleId))
		        {
	 			 String query1 = "SELECT CENTRE_NAME FROM TELECENTRE "+ 
                                 "WHERE TELE_ID = "+teleId;                            
	 			 telecentreName = this.querySingleValue(query1,"CENTRE_NAME");
	 		
		        }
	 			
	 		return telecentreName;
	}

	private int availableHours(String sql,String dataType){
		   String data=querySingleValue(sql,dataType);
		   if(data.equals("")){
			    data="0";
		   }
		   Integer res=new Integer(data);
		   return res.intValue();
	}
	//Sifiso Changes:Added:2016/07/04-Added below method to return a double type value for method availableHours()
	private double availableHoursDouble(String sql,String dataType){		
		   String data=querySingleValue(sql,dataType);
		   if(data.equals("")){
			    data="0";
		   }
		   Double res=new Double(data);
		   return res.doubleValue();
	}
	
	//Sifiso Changes:Changed line below:2016/06/30-To change the return type to double
	//public int getStudentAvailableHours(String studentNo, int month,int year){
	public double getStudentAvailableHours(String studentNo, int month,int year){
	      String sql="SELECT HOURS FROM STUDENT_AVAILABLE_HRS "+
	                  "WHERE STUDENT_NUMBER = "+studentNo+
	                  " AND MONTH = "+month+ " AND YEAR = "+year;
	      
	      //return  availableHours(sql,"HOURS"); 	 	//Sifiso Changes:Changed line:2016/06/30-To return the HOURS column as double
		  return  availableHoursDouble(sql,"HOURS");	//Sifiso Changes
	}
	//Sifiso Changes:Changed line below:2016/06/30-To change the return type to double
    //public int getTelecentreAvailableHours(String teleID, int month,int year){
	public double getTelecentreAvailableHours(String teleID, int month,int year){
    	    String sql="SELECT HOURS FROM TELECENTRE_AVAILABLE_HRS "+
	         "WHERE TELE_ID = "+teleID +
	         " AND MONTH = "+month+ " AND YEAR = "+year;
    	    //return  availableHours(sql,"HOURS");		//Sifiso Changes:Changed line:2016/06/30-To return the HOURS column as double
		    return  availableHoursDouble(sql,"HOURS");	//Sifiso Changes
    }
	//Sifiso Changes:Changed line below:2016/07/05-To use double type for 'totHours' method parameter
    //public  void updateStuAvailHours(int studentNum,int totHours){
	public  void updateStuAvailHours(int studentNum,double totHours){	//Sifiso Changes
        dateUtil  dateutil=new dateUtil();
        //Sifiso Changes:Changed line below:2016/06/30-to add the double 'stuAvailHrs' variable
        //int stuAvailHrs=getStudentAvailableHours(""+studentNum,dateutil.getMonthInt(),dateutil.yearInt());
        double stuAvailHrs=getStudentAvailableHours(""+studentNum,dateutil.getMonthInt(),dateutil.yearInt());
        stuAvailHrs-=totHours;
        if(stuAvailHrs<0){
	             stuAvailHrs=0;
        }
        updateStudentHours(""+studentNum,dateutil.getMonthInt(),stuAvailHrs,dateutil.yearInt());
    }
	   //Sifiso Changes:Changed line below:2016/07/05-To use double type for 'totHours' method parameter
	   //public  void updateTeleAvailHours(String teleCode,int totHours){
	   public  void updateTeleAvailHours(String teleCode,double totHours){	//Sifiso Changes
            dateUtil  dateutil=new dateUtil();
            //Sifiso Changes:Changed line below:2016/06/30-to add the double 'centreAvailHrs' variable
            //int centreAvailHrs=getTelecentreAvailableHours(teleCode,dateutil.getMonthInt(),dateutil.yearInt());
            double centreAvailHrs=getTelecentreAvailableHours(teleCode,dateutil.getMonthInt(),dateutil.yearInt());
            centreAvailHrs-=totHours;
            if(centreAvailHrs<0){
	                  centreAvailHrs=0;
            }
            updateCentreHours(teleCode,dateutil.getMonthInt(),centreAvailHrs,dateutil.yearInt());
    }
	//Sifiso Changes:Changed line below:2016/06/30-To add the double 'hours' parameter
	//public void updateStudentHours(String  studentNo,int month, int hours,int year){
	public void updateStudentHours(String  studentNo,int month, double hours,int year){  //Sifiso Changes
	      String sql="UPDATE STUDENT_AVAILABLE_HRS "+
	    		  "SET HOURS = "+hours+				
	              " WHERE STUDENT_NUMBER = "+studentNo+
	              " AND MONTH = "+month+ 
	              " AND YEAR = "+year;
	      updateUsingJdbcTemplate(sql);
	}
	//Sifiso Changes:2016/06/30:Changed line below:2016/06/30-To add the double 'hours' parameter
	//public void updateCentreHours(String  teleID,int month, int hours,int year){
	public void updateCentreHours(String  teleID,int month, double hours,int year){	 //Sifiso Changes
	        String sql="UPDATE TELECENTRE_AVAILABLE_HRS "+
	                "SET HOURS = "+hours+	
	                " WHERE TELE_ID ="+teleID+
	                " AND MONTH = "+month+ 
	                " AND YEAR ="+year;
	        updateUsingJdbcTemplate(sql);
	}
	public void allocateStudentTelecentreHrs(String auditUser, Date auditDate, String allocationType, String telecentre, String studentNr, int hours){
		  Calendar cal=Calendar.getInstance();
		  DateFormat dateFormat = new SimpleDateFormat("MM");
		  String monthStr=dateFormat.format(cal.getTime());
          int  month=Integer.parseInt(monthStr);//-1;
          String sql="";
          if(studentNr.equals("")){
        	       sql="INSERT INTO HOURS_ALLOCATION_AUDIT (AUDIT_USER,AUDIT_DATE,ALLOC_TYPE,MONTH,TELE_ID,HOURS)VALUES('"+auditUser+"',SYSDATE,'"+allocationType+"',"+month+","+Integer.parseInt(telecentre)+","+hours+")";
          }else{
        	       sql="INSERT INTO HOURS_ALLOCATION_AUDIT (AUDIT_USER,AUDIT_DATE,ALLOC_TYPE,MONTH,STUDENT_NUMBER,HOURS)VALUES('"+auditUser+"',SYSDATE,'"+allocationType+"',"+month+","+Integer.parseInt(studentNr)+","+hours+")";
          }
          updateUsingJdbcTemplate(sql);
    }
	public void assignStudentHours(String  studentNo,int month, int hours,int year){
	           String sql="INSERT INTO  STUDENT_AVAILABLE_HRS  values("+studentNo+","+month+","+year+","+hours+")";
	           updateUsingJdbcTemplate(sql);
	}
	public void assignCentreHours(String  teleID,int month, int hours,int year){
		       String sql="INSERT INTO  TELECENTRE_AVAILABLE_HRS  values("+teleID+","+month+","+year+","+hours+")";
		       updateUsingJdbcTemplate(sql);
	}
	public boolean isStudentHoursAssigned(String studentNo, int month, int year)
	{
	    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	    
	    if(jdt.queryForInt("select count(*) from student_available_hrs where student_number = '"+studentNo+"' and month = "+month+" and year = "+year) > 0){
	        return true;
	    }else{
	    	return false;
	    }
	}
	public boolean isTelecentreHoursAssigned(String telecentreID, int month, int year)
	{
	    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	    
	    if(jdt.queryForInt("select count(*) from telecentre_available_hrs where tele_id = '"+telecentreID+"' and month = "+month+" and year = "+year) > 0){
	        return true;
	     }else{
	    	return false;
	    }
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
	
	//Sifiso Changes:Changed line below:2016/07/05-To return double return type
	//public int getStudentTotalUsedHours(int StudentNum,int month){
	public double getStudentTotalUsedHours(int StudentNum,int month){		//Sifiso Changes
		         String sql="select sum(total_hrs) from student_telecentre where student_number ="+StudentNum+" and month = "+month;
		         //return availableHours(sql,"HOURS");				//Sifiso Changes:Changed:2016/07/05-To return a double type value
		         return availableHoursDouble(sql,"total_hrs");		//Sifiso Changes
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
   //Sifiso Changes:Added:2016/06/27:This method is called in TeleCentreVisits.setVisits() method
   //to get the telecente visit info for display. It MUST BE CHANGED to display the exact total hours for visit
   public ArrayList getTelecentreVisits(String studentNo,String fromDate, String toDate, String teleID){
           String sql="SELECT  A.TELE_ID AS TELECENTRE_ID,CENTRE_PROVINCE AS PROVINCE, A.CENTRE_NAME AS CENTRE ,TO_CHAR(B.START_TIMESTAMP, 'YYYY-MM-DD')  AS TO_DATE, STUDENT_NUMBER,"+
           "TO_CHAR(B.START_TIMESTAMP, 'HH24:MI')AS TIME_IN,"+
           "TO_CHAR(B.END_TIMESTAMP, 'HH24:MI')AS TIME_OUT, B.TOTAL_HRS AS DURATION,"+
           //Sifiso Changes:Added:2016/06/24-Duration timestamp difference for EXACT total hours (duration)
           "SUBSTR(TO_CHAR(B.END_TIMESTAMP - B.START_TIMESTAMP, 'HH24'),12,2)||':'||"+
           "SUBSTR(TO_CHAR(B.END_TIMESTAMP - B.START_TIMESTAMP, 'MI'),15,2)||':'||"+
           "SUBSTR(TO_CHAR(B.END_TIMESTAMP - B.START_TIMESTAMP, 'SS'),18,2) AS DURATION_DIFFER," +
		   "CASE"+
			    " WHEN to_number((SUBSTR(TO_CHAR(B.END_TIMESTAMP - B.START_TIMESTAMP, 'MI'),15,2)))<5 AND"+
			    		" to_number(SUBSTR(TO_CHAR(B.END_TIMESTAMP - B.START_TIMESTAMP, 'HH24'),12,2))<1"+
		        	" THEN 0"+
		        " WHEN to_number((SUBSTR(TO_CHAR(B.END_TIMESTAMP - B.START_TIMESTAMP, 'MI'),15,2)))>=5 AND"+
		        		//" to_number((SUBSTR(TO_CHAR(B.END_TIMESTAMP - B.START_TIMESTAMP, 'MI'),15,2)))<=60 AND"+	//Sifiso Changes:Removed:2017-02-28 -SR
		        		" to_number((SUBSTR(TO_CHAR(B.END_TIMESTAMP - B.START_TIMESTAMP, 'MI'),15,2)))<=59 AND"+    //Sifiso Changes:Added:2017-02-28 -SR
		        		" to_number(SUBSTR(TO_CHAR(B.END_TIMESTAMP - B.START_TIMESTAMP, 'HH24'),12,2))<1"+
		        	" THEN 1"+
		        " WHEN to_number((SUBSTR(TO_CHAR(B.END_TIMESTAMP - B.START_TIMESTAMP, 'MI'),15,2)))>=1 AND"+	//Sifiso Changes:Added:2017-02-28:>=61 Minutes<=120 Minutes = 2 Hours -SR
	                 " to_number(SUBSTR(TO_CHAR(B.END_TIMESTAMP - B.START_TIMESTAMP, 'HH24'),12,2))>=1"+		//Sifiso Changes:Added:2017-02-28 -SR
	                 " THEN 2"+																					//Sifiso Changes:Added:2017-02-28 -SR
		        " WHEN to_number(SUBSTR(TO_CHAR(B.END_TIMESTAMP - B.START_TIMESTAMP, 'HH24'),12,2))=1"+
		        	" THEN 1"+
		        " WHEN to_number(SUBSTR(TO_CHAR(B.END_TIMESTAMP - B.START_TIMESTAMP, 'HH24'),12,2))=2"+
		        	" THEN 2"+
		        " WHEN to_number(SUBSTR(TO_CHAR(B.END_TIMESTAMP - B.START_TIMESTAMP, 'HH24'),12,2))>2"+
		        	" THEN 2"+		//Changes: 2017/01/16
		   " END AS DURATION_REPORT"+
		   " FROM TELECENTRE A, STUDENT_TELECENTRE B"+  
           " WHERE A.TELE_ID = B.TELE_ID"; 
           if(!teleID.equals("")){
         	   sql+=(" AND A.TELE_ID = "+ teleID);
           }
           if(!fromDate.equals("")){
         	    sql+=(" AND TO_CHAR (START_TIMESTAMP, 'yyyy-MM-dd') >= '"+fromDate+"'");
           }
           if(!toDate.equals("")){
         	  sql+=(" AND TO_CHAR (START_TIMESTAMP, 'yyyy-MM-dd') <= '"+toDate+"'");
           }
           if(!studentNo.equals("")){
     	        sql+=(" AND STUDENT_NUMBER = "+studentNo);
     	   }//if
           sql+=(" ORDER BY TO_DATE DESC, CENTRE ASC");
           return getCentreArrayList(sql);
      }
   private ArrayList getCentreArrayList(String sql){
	    ArrayList<TelecentreDetails> results = new ArrayList();
	    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
        List queryList;
        queryList = jdt.queryForList(sql);
        Iterator i = queryList .iterator();
        ListOrderedMap data;
        int x=0;
        while (i.hasNext()){
	        data = (ListOrderedMap) i.next();
	        writeDataToClass(data,results);
        }
        return results;
  }
  private void writeDataToClass(ListOrderedMap data,ArrayList results){
	      TelecentreDetails telecentreDetails = new TelecentreDetails();
         try{
	          telecentreDetails.setProvince(data.get("PROVINCE").toString());
              telecentreDetails.setCentre(data.get("CENTRE").toString());
              telecentreDetails.setTodate(data.get("TO_DATE").toString());
              telecentreDetails.setTimeIn(data.get("TIME_IN").toString());
              telecentreDetails.setTimeOut(data.get("TIME_OUT").toString());
              //telecentreDetails.setDuration(data.get("DURATION").toString());
              //Sifiso Changes:Added:2016/06/24-Duration timestamp difference for EXACT total hours (duration)
              //write the exact time using setter method
              telecentreDetails.setDuration(data.get("DURATION_DIFFER").toString()); //-Sifiso Changes
              telecentreDetails.setDurationReport(data.get("DURATION_REPORT").toString());	//Sifiso Changes
              telecentreDetails.setStudentNr(data.get("STUDENT_NUMBER").toString());
              telecentreDetails.setTeleId(data.get("TELECENTRE_ID").toString());
	          results.add(telecentreDetails);
	          
	      }catch(NullPointerException e ){
	    	  e.printStackTrace();
          }
    }
    public ArrayList getHoursExtensionAuditList(String fromDate, String toDate){
	       String sql="SELECT  audit_User,TO_CHAR(audit_Date, 'YYYY-MM-DD') as audit_Date,alloc_Type,month,tele_Id,student_number,hours"+
	     	        " FROM hours_allocation_audit  where "+
	               	" TO_CHAR (audit_Date, 'yyyy-MM-DD') >= '"+fromDate+"'"+
	                " AND TO_CHAR (audit_Date, 'yyyy-MM-DD') <= '"+toDate+"'";
	                return getExtensionRecordList(sql);
    }
    private ArrayList getExtensionRecordList(String sql){
        ArrayList<extendedHourDetails> results = new ArrayList();
        JdbcTemplate jdt = new JdbcTemplate(getDataSource());
        List queryList;
        queryList = jdt.queryForList(sql);
        Iterator i = queryList .iterator();
        ListOrderedMap data;
         while (i.hasNext()){
              data = (ListOrderedMap) i.next();
              writeExtensionRecordsToClass(data,results);
        }
          return results;
    }
    private void writeExtensionRecordsToClass(ListOrderedMap data,ArrayList results){
                extendedHourDetails hrsExtensionRec = new extendedHourDetails();
                try{
	                  hrsExtensionRec.setAuditUser(data.get("audit_User").toString());
	                  hrsExtensionRec.setAuditDate(data.get("audit_Date").toString());
	                  hrsExtensionRec.setAllocType(data.get("alloc_Type").toString());
	                  hrsExtensionRec.setMonth(data.get("month").toString());
	                  if(data.get("tele_Id")!=null){
	                	  hrsExtensionRec.setTeleId(data.get("tele_Id").toString()); 
	                  }else{
	                	  hrsExtensionRec.setTeleId("");
	                  }
	                  if(data.get("student_number")!=null){
	                	  hrsExtensionRec.setStudentNr(data.get("student_number").toString());	 
	                  }else{
	                	  hrsExtensionRec.setStudentNr("");	
	                  }
	                  hrsExtensionRec.setHours(data.get("hours").toString());
	                  results.add(hrsExtensionRec);
               }catch(NullPointerException e ){
	                   e.printStackTrace();
               }
   }
    public ArrayList getTelecentreVisits(String studentNumber,String month){
		
		ArrayList results = new ArrayList();
		if(month.equals("January")){
		 month = "01";
		}else if(month.equals("February")){
		 month = "02";
		}else if(month.equals("March")){
		 month = "03";
		}else if(month.equals("April")){
		 month = "04";
		}else if(month.equals("May")){
		 month = "05";
		}else if(month.equals("June")){
		 month = "06";
		}else if(month.equals("July")){
		 month = "07";
		}else if(month.equals("August")){
		 month = "08";
		}else if(month.equals("September")){
		 month = "09";
		}else if(month.equals("October")){
		 month = "10";
		}else if(month.equals("November")){
		 month = "11";
		}else if(month.equals("December")){
		 month = "12";
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String year = dateFormat.format(date);
	    String sql = " SELECT A.CENTRE_NAME AS CENTRE ,TO_CHAR(B.START_TIMESTAMP, 'YYYY-MM-DD')  AS TO_DATE, "+
	                 " TO_CHAR(B.START_TIMESTAMP, 'HH24:MI')AS TIME_IN, "+
	                 " TO_CHAR(B.END_TIMESTAMP, 'HH24:MI')AS TIME_OUT, B.TOTAL_HRS AS DURATION "+
		             /*Sifiso Changes:Added:2016/06/24-Duration timestamp difference for EXACT total hours (duration)
		             "SUBSTR(TO_CHAR(B.END_TIMESTAMP - B.START_TIMESTAMP,'HH24:MI:SS'),12,8) AS EXACT_DURATION" +*/
	                 " FROM TELECENTRE A, STUDENT_TELECENTRE B "+ 
	                 " WHERE STUDENT_NUMBER="+studentNumber+ 
	                 " AND TO_CHAR(START_TIMESTAMP, 'YYYY-MM')= "+"'"+year+"-"+month+"'"+ 
	                 " ORDER BY TO_DATE DESC, CENTRE ASC";
	           
		  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		  List queryList;
		  queryList = jdt.queryForList(sql);
		  Iterator i = queryList .iterator();
		  
	  while (i.hasNext())
	  {	ListOrderedMap data = (ListOrderedMap) i.next();
	  TelecentreDetails telecentreDetails = new TelecentreDetails();
			
		try{
			telecentreDetails.setCentre(data.get("CENTRE").toString());
			telecentreDetails.setTodate(data.get("TO_DATE").toString());
			telecentreDetails.setTimeIn(data.get("TIME_IN").toString());
			telecentreDetails.setTimeOut(data.get("TIME_OUT").toString());
			telecentreDetails.setDuration(data.get("DURATION").toString());
			/*Sifiso Changes:Added:2016/06/27-Duration timestamp difference for EXACT total hours (duration)
			telecentreDetails.setDuration(data.get("EXACT_DURATION").toString());*/
			
	
			results.add(telecentreDetails);
			}
	    catch(NullPointerException e ){e.printStackTrace();}
		
	  }			  
      return results;
   }
public ArrayList getCurrentVisits(){
	
	     ArrayList results = new ArrayList();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-DD");
    Date date = new Date();
    String dateStr = dateFormat.format(date);
    String sql = " SELECT TO_CHAR(START_TIMESTAMP,'HH24:MI') AS TIME_IN,TO_CHAR(END_TIMESTAMP, 'HH24:MI') AS TIME_OUT,STUDENT_NUMBER,TELE_ID,"+
    		" TIME_UNIT AS UNIT,"+								//Sifiso Changes:Added:2016/07/11-Time unit in seconds,minutes,hours
            " TOTAL_HRS AS DURATION  FROM STUDENT_TELECENTRE  "; 
    
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
	           telecentreDetails.setUsedHours(Double.parseDouble(data.get("DURATION").toString()));		//Sifiso Changes
	           telecentreDetails.setTeleId(Integer.parseInt(data.get("TELE_ID").toString()));
	           telecentreDetails.setTimeUnit(data.get("UNIT").toString());		//Sifiso Changes:Added:2016/07/11-Time unit in seconds,minutes,hour
	           results.add(telecentreDetails);
	       }catch(NullPointerException e ){e.printStackTrace();}
    }			  
    return results;
}
public ArrayList getCurrentVisitsForStu(String stuNum){
	
    ArrayList results = new ArrayList();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    String dateStr = dateFormat.format(date);
    String sql = " SELECT TO_CHAR(START_TIMESTAMP,'HH24:MI') AS TIME_IN,TO_CHAR(END_TIMESTAMP, 'HH24:MI') AS TIME_OUT,STUDENT_NUMBER,TELE_ID,"+
    		" TIME_UNIT AS UNIT,"+				//Sifiso Changes:Added:2016/07/11-Time unit in seconds,minutes,hours
    		" TOTAL_HRS AS DURATION  FROM STUDENT_TELECENTRE  WHERE  TO_CHAR(START_TIMESTAMP, 'YYYY-MM-DD')= "+"'"+dateStr+"'  AND STUDENT_NUMBER="+stuNum;
    
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
           //Sifiso Changes:Changed line below:2016/07/06-To get the 'DURATION' (TOTAL_HRS) column as double
           //telecentreDetails.setUsedHours(Integer.parseInt(data.get("DURATION").toString()));
           telecentreDetails.setUsedHours(Double.parseDouble(data.get("DURATION").toString()));		//Sifiso Changes
           telecentreDetails.setTeleId(Integer.parseInt(data.get("TELE_ID").toString()));
           telecentreDetails.setTimeUnit(data.get("UNIT").toString());		//Sifiso Changes:Added:2016/07/11-Time unit in seconds,minutes,hour
           results.add(telecentreDetails);
           int pos=checkValueExistInList(results,telecentreDetails.getStudentNumber());
           addRecordOfVisit(results,pos,telecentreDetails);
      }catch(NullPointerException e ){e.printStackTrace();}
}			  
return results;
}
public ArrayList getPrevDayVisits(){
	
	     ArrayList results = new ArrayList();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    dateUtil dateutil=new dateUtil();
    String sql = " SELECT TO_CHAR(START_TIMESTAMP,'HH24:MI') AS TIME_IN,TO_CHAR(END_TIMESTAMP, 'HH24:MI') AS TIME_OUT,STUDENT_NUMBER,TELE_ID,"+
    		" TIME_UNIT AS UNIT,"+		//Sifiso Changes:Added:2016/07/11-Time unit in seconds,minutes,hours
    		" TOTAL_HRS AS DURATION  FROM STUDENT_TELECENTRE WHERE TO_CHAR(START_TIMESTAMP, 'YYYY-MM-DD')= "+"'"+dateutil.getPrevDate()+"'  and total_hrs=0";
    
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
	           //int usedHours=Integer.parseInt(data.get("DURATION").toString());		//Sifiso Changes:2016/07/11:Changed type from int to double
	           double usedHours=Double.parseDouble(data.get("DURATION").toString());	//Sifiso Changes
	           //telecentreDetails.setUsedHours(Integer.parseInt(data.get("DURATION").toString()));		//Sifiso Changes:2016/07/11:Changed type from int to double
	           telecentreDetails.setUsedHours(Double.parseDouble(data.get("DURATION").toString()));		//Sifiso Changes
	           telecentreDetails.setTeleId(Integer.parseInt(data.get("TELE_ID").toString()));
	           telecentreDetails.setTimeUnit(data.get("UNIT").toString());		//Sifiso Changes:Added:2016/07/11-Time unit in seconds,minutes,hour
	           results.add(telecentreDetails);
	       }catch(NullPointerException e ){e.printStackTrace();}
    }		
   
    return results;
}

public ArrayList getAllCurrVisitsForStu(int stuNum){
	    return getList(stuNum);
}
private ArrayList getList(int stuNum){

    dateUtil dateutil=new dateUtil();
    String dateStr = dateutil.dateOnly();
	String sql = " SELECT TO_CHAR(START_TIMESTAMP,'HH24:MI') AS TIME_IN,TO_CHAR(END_TIMESTAMP, 'HH24:MI') AS TIME_OUT,STUDENT_NUMBER,TELE_ID,"+
	" TIME_UNIT AS UNIT,"+				//Sifiso Changes:Added:2016/07/11-Time unit in seconds,minutes,hours
    " TOTAL_HRS AS DURATION  FROM STUDENT_TELECENTRE  WHERE  TO_CHAR(START_TIMESTAMP, 'YYYY-MM-DD')= "+"'"+dateStr+"'  AND STUDENT_NUMBER="+stuNum;
    
	ArrayList results = new ArrayList();
	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
    List queryList;
    queryList = jdt.queryForList(sql);
    Iterator i = queryList .iterator();
    while (i.hasNext()){	
      ListOrderedMap data = (ListOrderedMap) i.next();
     StudentTelecentre telecentreDetails = new StudentTelecentre();
      try{
    	  int studentNumber=Integer.parseInt(data.get("STUDENT_NUMBER").toString());
    	  String endTime=data.get("TIME_OUT").toString();
    	  if((stuNum==studentNumber)&&
    			  givenGreaterThanSysTime(endTime)){
    		      telecentreDetails.setStudentNumber(stuNum); 
                  telecentreDetails.setStartTime(data.get("TIME_IN").toString());
                  telecentreDetails.setEndTime(data.get("TIME_OUT").toString());
                  //Sifiso Changes:Changed line below:2016/07/05-To parse 'DURATION' value as double
                  //telecentreDetails.setUsedHours(Integer.parseInt(data.get("DURATION").toString()));
                  telecentreDetails.setUsedHours(Double.parseDouble(data.get("DURATION").toString()));		//Sifiso Changes
                  telecentreDetails.setTeleId(Integer.parseInt(data.get("TELE_ID").toString()));
                  telecentreDetails.setTimeUnit(data.get("UNIT").toString());		//Sifiso Changes:Added:2016/07/11-Time unit in seconds,minutes,hour
                  results.add(telecentreDetails);
    	  }
       }catch(NullPointerException e ){e.printStackTrace();}
    }
    return results;
}
private boolean givenGreaterThanSysTime(String givenTime){// to check that the givenTime  if it is less than curr system time
    dateUtil du=new dateUtil();
    if(du.givenTimeGreaterSystime(givenTime)){
 	 return true;
    }else{
 	 return false;
    }
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
		  "The session has expired for the following student "+stuNum+"   <br><br>To continue using the Telecentre the student must login again<br><br>"+
        "Date: "+onlyDate+"<br>"+
        "Time ended: "+onlyTime+"<br><br>"+
        "Regards  <br>"+
			  "UNISA ICT"+
        "</body>"+
        "</html>";
       return body;
    }
    
    //public int getUsedHours(String  stuNum){		//Sifiso Changes:Changed:2016/07/04-Must return double type for total_hrs
    public double getUsedHours(String  stuNum){		//Sifiso Changes
    	 //used to get the record of the telecentre  visit still in session
    			String sql = " SELECT total_hrs from student_telecentre  where student_number="+stuNum;
                String  hrsStr="";
    			//return availableHours(sql,"TOTAL_HRS");		//Sifiso Changes:Changed:2016/06/30-To return the TOTAL_HRS column data as double
                return availableHoursDouble(sql,"total_hrs");		//Sifiso Changes
     }
    
    //Sifiso Changes:Added:2016/08/01:Method to add new Telecentre Profile to database, method returns number of rows affected
	public int addCentreProfile(String province,String centreName, String centreEmail,int centreHours,String centreActive,
								 String centreCode) throws Exception{
		try{
		        String sql=" INSERT INTO TELECENTRE(TELE_ID,CENTRE_PROVINCE,CENTRE_NAME,EMAIL_ADDRESS,MONTHLY_HOURS,ACTIVE,TELE_CODE) "+
		        		   "VALUES(telecentre_seq.nextval,'"+province+"','"+centreName+"','"+centreEmail+"',"+centreHours+",'"+centreActive+
		        		   "','"+centreCode+"')";
		        JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				int resultUpdate=jdt.update(sql);
				return resultUpdate;		//return the number of rows affected(inserted)
		}catch (Exception ex) {
            throw new Exception("TelecentreDAO.java, 'addCentreProfile':"+ ex);
      }
	}
	
	//Sifiso Changes:Added:2016/08/02:Method to get all Telecentre codes for comparison
	//Method used to identify if tele code already exists when generating a new tele code
	public ArrayList getTeleCodes(){
		 String sql= " SELECT TELE_CODE FROM TELECENTRE ORDER BY TELE_CODE ASC";	  
		 ArrayList results = new ArrayList();
		  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		  List queryList;
		  queryList = jdt.queryForList(sql);
		  Iterator i = queryList .iterator();
		  while(i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			String telecode = data.get("TELE_CODE").toString();
			results.add(telecode);
		  }
		  return results;
	}
	
	//Sifiso Changes:Added:2016/08/02:Method to get all Telecentre names for comparison
	//Method used to identify if a Telecentre name with the same province and same email already exists
	public ArrayList getTeleNames(){
		 String sql= " SELECT CENTRE_NAME FROM TELECENTRE ORDER BY CENTRE_NAME ASC";	  
		 ArrayList results = new ArrayList();
		  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		  List queryList;
		  queryList = jdt.queryForList(sql);
		  Iterator i = queryList .iterator();
		  while(i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			String telecode = data.get("CENTRE_NAME").toString();
			results.add(telecode);
		  }
		  return results;
	}
	
	//Sifiso Changes:Added:2016/08/02:Method used to check if a Telecentre name with the same province and same email already exists
	public boolean teleNameExists(String province, String email){
		 String sql= " SELECT count(CENTRE_NAME) as name_occur FROM TELECENTRE WHERE CENTRE_PROVINCE='"+province+"'"+
				 	 " AND EMAIL_ADDRESS='"+email+"' ORDER BY CENTRE_NAME ASC";	  
		 String name_occurances=this.querySingleValue(sql, "name_occur");
		 if((name_occurances==null)||(name_occurances=="")||(name_occurances=="0")){
			 return false;
		 }else{
			 return true;
		 }
	}
	
	//Sifiso Changes:Added:2016/08/04:Method to get all telecentre for display on update page drop-down list
	//purpose of method is to get all centres whether active or inactive, method of a variant of getCentres()
	public ArrayList getAllCentres()
	 {
	 			 String sql= "SELECT CENTRE_NAME,CENTRE_PROVINCE AS PROVINCE FROM TELECENTRE ORDER BY CENTRE_NAME";
	 			 ArrayList results = new ArrayList();
				  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				  List queryList;
				  queryList = jdt.queryForList(sql);
				  Iterator i = queryList .iterator();
				  while(i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					String centrename = data.get("CENTRE_NAME").toString();
					String province= data.get("PROVINCE").toString();
					results.add(new org.apache.struts.util.LabelValueBean((centrename+"("+province+")"),centrename));
				  }
   	 		return results;
	}
	
	//Sifiso Changes:Added:2016/08/05:Method to get and set the telecentre information from database display on update page
	public ArrayList getCentreUpdateInfo(String teleName){
		String sql= "SELECT CENTRE_NAME,EMAIL_ADDRESS,CENTRE_PROVINCE,ACTIVE,TELE_CODE FROM TELECENTRE"+
					" WHERE CENTRE_NAME='"+teleName+"' ORDER BY CENTRE_NAME";
		return getCentreUpdateArrayList(sql);
	}
	
	//Sifiso Changes:Added:2016/08/05:Method to write database information to middle-man class (TelecentreDetails)
	//to use to update TelecentreForm-Method must only bes used within this TelecentreDAO class
	private ArrayList getCentreUpdateArrayList(String sql){
	    ArrayList<TelecentreDetails> results = new ArrayList();
	    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
        List queryList;
        queryList = jdt.queryForList(sql);
        Iterator i = queryList .iterator();
        ListOrderedMap data;
        int x=0;
        while (i.hasNext()){
	        data = (ListOrderedMap) i.next();
	        writeUpdateDataToClass(data,results);
        }
        return results;
    }
	//Sifiso Changes:Added:2016/08/05:Method to write database information to middle-man class (TelecentreDetails)
	//to use to update TelecentreForm-Method must only bes used within this TelecentreDAO class
	private void writeUpdateDataToClass(ListOrderedMap data,ArrayList results){
	   	TelecentreDetails telecentreDetails = new TelecentreDetails();
	    try{
	    	  telecentreDetails.setCentre(data.get("CENTRE_NAME").toString());
	          telecentreDetails.setEmail(data.get("EMAIL_ADDRESS").toString());	        	  
	          telecentreDetails.setProvince(data.get("CENTRE_PROVINCE").toString());
	          telecentreDetails.setStatus(data.get("ACTIVE").toString());
	          telecentreDetails.setCode(data.get("TELE_CODE").toString());
	          results.add(telecentreDetails);
	          
	    }catch(NullPointerException e ){
	    	e.printStackTrace();
	    }
	}
	
    //Sifiso Changes:Added:2016/08/12:Method to update any or all Telecentre fields in database, method returns number of rows affected
	public int updateCentreDb(String updateName,String updateEmail,String updateProvince,String updateStatus,String currCentreName,
			String currTelecode,String currProvince,String currEmail,String updateField) 
			throws Exception{
		try{
				String queryName="SET CENTRE_NAME='"+updateName+"'";
				String queryEmail="SET EMAIL_ADDRESS='"+updateEmail+"'";
				String queryProvince="SET CENTRE_PROVINCE='"+updateProvince+"'";
				String queryStatus="SET ACTIVE='"+updateStatus+"'";
				String queryNameEmail="SET CENTRE_NAME='"+updateName+"', EMAIL_ADDRESS='"+updateEmail+"'";
				String queryNameProvince="SET CENTRE_NAME='"+updateName+"', CENTRE_PROVINCE='"+updateProvince+"'";
				String queryNameStatus="SET CENTRE_NAME='"+updateName+"', ACTIVE='"+updateStatus+"'";
				String queryNameEmailProvince="SET CENTRE_NAME='"+updateName+"', EMAIL_ADDRESS='"+updateEmail+"',"+
											  " CENTRE_PROVINCE='"+updateProvince+"'";
				String queryNameEmailStatus="SET CENTRE_NAME='"+updateName+"', EMAIL_ADDRESS='"+updateEmail+"',"+
						  					" ACTIVE='"+updateStatus+"'";
				String queryNameProvinceStatus="SET CENTRE_NAME='"+updateName+"', CENTRE_PROVINCE='"+updateProvince+"',"+
	  										   " ACTIVE='"+updateStatus+"'";
				String queryEmailProvince="SET EMAIL_ADDRESS='"+updateEmail+"', CENTRE_PROVINCE='"+updateProvince+"'";
				String queryEmailStatus="SET EMAIL_ADDRESS='"+updateEmail+"', ACTIVE='"+updateStatus+"'";
				String queryEmailProvinceStatus="SET EMAIL_ADDRESS='"+updateEmail+"', CENTRE_PROVINCE='"+updateProvince+"',"+
												" ACTIVE='"+updateStatus+"'";
				String queryProvinceStatus="SET CENTRE_PROVINCE='"+updateProvince+"', ACTIVE='"+updateStatus+"'";
				String queryNameEmailProvinceStatus="SET CENTRE_NAME='"+updateName+"', EMAIL_ADDRESS='"+updateEmail+"', CENTRE_PROVINCE='"+
													 updateProvince+"', ACTIVE='"+updateStatus+"'";
				String updateQuery="";				
				if(updateField.equals("name")){
					updateQuery=queryName;
				}
				if(updateField.equals("email")){
					updateQuery=queryEmail;
				}
				if(updateField.equals("province")){
					updateQuery=queryProvince;
				}
				if(updateField.equals("status")){
					updateQuery=queryStatus;
				}
				if(updateField.equals("nameEmail")){
					updateQuery=queryNameEmail;
				}
				if(updateField.equals("nameProvince")){
					updateQuery=queryNameProvince;
				}
				if(updateField.equals("nameStatus")){
					updateQuery=queryNameStatus;
				}
				if(updateField.equals("nameEmailProvince")){
					updateQuery=queryNameEmailProvince;
				}
				if(updateField.equals("nameEmailStatus")){
					updateQuery=queryNameEmailStatus;
				}
				if(updateField.equals("nameProvinceStatus")){
					updateQuery=queryNameProvinceStatus;
				}
				if(updateField.equals("emailProvinceStatus")){
					updateQuery=queryEmailProvinceStatus;
				}
				if(updateField.equals("emailProvince")){
					updateQuery=queryEmailProvince;
				}
				if(updateField.equals("emailStatus")){
					updateQuery=queryEmailStatus;
				}
				if(updateField.equals("provinceStatus")){
					updateQuery=queryProvinceStatus;
				}
				if(updateField.equals("allCheck")){
					updateQuery=queryNameEmailProvinceStatus;
				}
				
				String sql=" UPDATE TELECENTRE "+updateQuery+
						   " WHERE CENTRE_NAME='"+currCentreName+"' AND TELE_CODE='"+currTelecode+"'"+
						   " AND CENTRE_PROVINCE='"+currProvince+"' AND EMAIL_ADDRESS='"+currEmail+"'";
		        JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				int resultUpdate=jdt.update(sql);
				return resultUpdate;		//return the number of rows affected(updated)
		}catch (Exception ex) {
            throw new Exception("TelecentreDAO.java, 'updateCentreStatus':"+ ex);
      }
	}
	
    //Sifiso Changes:Added:2016/08/14:Method to delete a Telecentre in database, method returns number of rows affected
	public String updateCentreDb(String removeName) throws Exception{
		try{
			String telePrimaryKey=getTeleIdPk(removeName);							//get the primary key
			String deleteResult="";													//return the result of each delete statement
			int telecentreTableDl=deleteFromTelecentre(telePrimaryKey);				//delete from TELECENTRE table
			int telecentreAvailHrsDl=deleteFromTelecentreAvailHrs(telePrimaryKey);  //delete from TELECENTRE_AVAILABLE_HRS table
			int studentTelecentreDl=deleteFromStudentTelecentre(telePrimaryKey);	//delete from STUDENT_TELECENTRE table
			if(telecentreTableDl>0){
				deleteResult=deleteResult+"\r\nDelete From 'TELECENTRE' database Table is successfull!\r\n";
			}else{
				deleteResult=deleteResult+"\r\nDelete From 'TELECENTRE' database Table failed! Return value is:"+telecentreTableDl+"\r\n";
			}
			if(telecentreAvailHrsDl>0){
				deleteResult=deleteResult+"\r\nDelete From 'TELECENTRE_AVAILABLE_HRS' database Table is successfull!\r\n";
			}else{
				deleteResult=deleteResult+"\r\nDelete From 'TELECENTRE_AVAILABLE_HRS' database Table failed! Return value is:"+telecentreAvailHrsDl+"\r\n";
			}
			if(studentTelecentreDl>0){
				deleteResult=deleteResult+"\r\nDelete From 'STUDENT_TELECENTRE' database Table is successfull!\r\n";
			}else{
				deleteResult=deleteResult+"\r\nDelete From 'STUDENT_TELECENTRE' database Table failed! Return value is:"+studentTelecentreDl+"\r\n";
			}
			return deleteResult;
		}catch (Exception ex){
			throw new Exception("TelecentreDAO.java, 'updateCentreDb("+removeName+")':"+ex);
		}
	}
	//Sifiso Changes:Added:2016/08/14:Get a Tele_ID primary key using the exact database Telecentre Name
	public String getTeleIdPk(String centreName){
		String result;
		String sql="SELECT TELE_ID FROM TELECENTRE"+
				   " WHERE CENTRE_NAME='"+centreName+"'";
		result=querySingleValue(sql, "TELE_ID");
		return result;
	}
	//Sifiso Changes:Added:2016/08/14:Delete a record from the TELECENTRE table
	public int deleteFromTelecentre(String teleId) throws Exception{
		try{
			String sql="DELETE FROM TELECENTRE"+
			" WHERE TELE_ID="+teleId;
			JdbcTemplate jdt=new JdbcTemplate(getDataSource());
			int result=jdt.update(sql);
			return result;
		}catch (Exception ex){
			throw new Exception("TelecentreDAO.java, 'deleteFromTelecentre("+teleId+")':"+ex);
		}
	}
	//Sifiso Changes:Added:2016/08/14:Delete a record from the TELECENTRE_AVAILABLE_HRS table
	public int deleteFromTelecentreAvailHrs(String teleId) throws Exception{
		try{
			String sql="DELETE FROM TELECENTRE_AVAILABLE_HRS"+
			" WHERE TELE_ID="+teleId;
			JdbcTemplate jdt=new JdbcTemplate(getDataSource());
			int result=jdt.update(sql);
			return result;
		}catch (Exception ex){
			throw new Exception("TelecentreDAO.java, 'deleteFromTelecentreAvailHrs("+teleId+")':"+ex);
		}
	}
	//Sifiso Changes:Added:2016/08/14:Delete a record from the STUDENT_TELECENTRE table
	public int deleteFromStudentTelecentre(String teleId) throws Exception{
		try{
			String sql="DELETE FROM STUDENT_TELECENTRE"+
			" WHERE TELE_ID="+teleId;
			JdbcTemplate jdt=new JdbcTemplate(getDataSource());
			int result=jdt.update(sql);
			return result;
		}catch (Exception ex){
			throw new Exception("TelecentreDAO.java, 'deleteFromStudentTelecentre("+teleId+")':"+ex);
		}
	}
    //Sifiso Changes:Added:2016/08/14:Method to update Telecentre Active Status to 'Y' or 'N' in database
	//method returns number of rows affected
	public int updateStatusDB(String teleName,String status) throws Exception{
		try{
			String telePrimaryKey=getTeleIdPk(teleName);			//get the primary key
			String sql="UPDATE TELECENTRE SET ACTIVE='"+status+"'"+
					" WHERE TELE_ID="+telePrimaryKey;
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int resultUpdate=jdt.update(sql);
			return resultUpdate;									//return the number of rows affected(updated)
		}catch (Exception ex){
			throw new Exception("TelecentreDAO.java, 'updateActiveDB("+teleName+")':"+ex);
		}
	}

	//Sifiso Changes:2016/10/28-To return number of hours used in current day
	public double getStudentDailyUsedHours(String studentNum, String dateStr){		
		         String sql="SELECT SUM(TOTAL_HRS) AS DailyHrs FROM STUDENT_TELECENTRE"+
		        		 	" WHERE STUDENT_NUMBER ="+studentNum+
		        		 	" AND TO_CHAR(END_TIMESTAMP, 'YYYY-MM-DD') ='"+dateStr+"'";
		         return availableHoursDouble(sql,"DailyHrs");		
	}
	
	//Sifiso Changes:2016/10/28-To return number of hours used in current week
	public double getStudentWeeklyUsedHours(String studentNum, String dateStr){
		String sql="SELECT"+
				   " CASE"+
				       " WHEN TRIM(TO_CHAR (wkDay.maxEndTime, 'Day'))='Monday'"+
				            " THEN (SELECT NVL(SUM(TOTAL_HRS),0) AS WeeklyHrs"+
				                    " FROM STUDENT_TELECENTRE"+
				                    " WHERE TO_CHAR (END_TIMESTAMP, 'YYYY-MM-DD') >="+
				                        " CASE"+
				                            " WHEN TO_CHAR(wkDay.maxEndTime-1,'YYYY-MM') = TO_CHAR(CURRENT_TIMESTAMP,'YYYY-MM')"+
				                                " THEN TO_CHAR(wkDay.maxEndTime-1,'YYYY-MM-DD')"+           
				                            " WHEN TO_CHAR(wkDay.maxEndTime-1,'YYYY-MM') = TO_CHAR(ADD_MONTHS(wkDay.maxEndTime,-1),'YYYY-MM')"+
				                                " THEN TO_CHAR(wkDay.maxEndTime,'YYYY-MM-DD')"+
				                        " END"+
				                    " AND TO_CHAR (END_TIMESTAMP, 'YYYY-MM-DD') <= TO_CHAR(wkDay.maxEndTime,'YYYY-MM-DD')"+
				                    " AND STUDENT_NUMBER="+studentNum+")"+ 
				        " WHEN TRIM(TO_CHAR (wkDay.maxEndTime, 'Day'))='Tuesday'"+
				            " THEN (SELECT NVL(SUM(TOTAL_HRS),0) AS WeeklyHrs"+
				                    " FROM STUDENT_TELECENTRE"+
				                    " WHERE TO_CHAR (END_TIMESTAMP, 'YYYY-MM-DD') >="+
				                        " CASE"+
				                            " WHEN TO_CHAR(wkDay.maxEndTime-2,'YYYY-MM') = TO_CHAR(CURRENT_TIMESTAMP,'YYYY-MM')"+
				                                " THEN TO_CHAR(wkDay.maxEndTime-2,'YYYY-MM-DD')"+
				                            " WHEN TO_CHAR(wkDay.maxEndTime-1,'YYYY-MM') = TO_CHAR(CURRENT_TIMESTAMP,'YYYY-MM')"+
				                                " THEN TO_CHAR(wkDay.maxEndTime-1,'YYYY-MM-DD')"+
				                            " WHEN TO_CHAR(wkDay.maxEndTime-1,'YYYY-MM') = TO_CHAR(ADD_MONTHS(wkDay.maxEndTime,-1),'YYYY-MM')"+
				                                " THEN TO_CHAR(wkDay.maxEndTime,'YYYY-MM-DD')"+
				                        " END"+
				                    " AND TO_CHAR (END_TIMESTAMP, 'YYYY-MM-DD') <= TO_CHAR(wkDay.maxEndTime,'YYYY-MM-DD')"+
				                    " AND STUDENT_NUMBER="+studentNum+")"+  
				        " WHEN TRIM(TO_CHAR (wkDay.maxEndTime, 'Day'))='Wednesday'"+
				            " THEN (SELECT NVL(SUM(TOTAL_HRS),0) AS WeeklyHrs"+
				                    " FROM STUDENT_TELECENTRE"+
				                    " WHERE TO_CHAR (END_TIMESTAMP, 'YYYY-MM-DD') >="+ 
				                        " CASE"+ 
				                            " WHEN TO_CHAR(wkDay.maxEndTime-3,'YYYY-MM') = TO_CHAR(CURRENT_TIMESTAMP,'YYYY-MM')"+
				                                " THEN TO_CHAR(wkDay.maxEndTime-3,'YYYY-MM-DD')"+
				                            " WHEN TO_CHAR(wkDay.maxEndTime-2,'YYYY-MM') = TO_CHAR(CURRENT_TIMESTAMP,'YYYY-MM')"+
				                                " THEN TO_CHAR(wkDay.maxEndTime-2,'YYYY-MM-DD')"+
				                            " WHEN TO_CHAR(wkDay.maxEndTime-1,'YYYY-MM') = TO_CHAR(CURRENT_TIMESTAMP,'YYYY-MM')"+
				                                "  THEN TO_CHAR(wkDay.maxEndTime-1,'YYYY-MM-DD')"+
				                            " WHEN TO_CHAR(wkDay.maxEndTime-1,'YYYY-MM') = TO_CHAR(ADD_MONTHS(wkDay.maxEndTime,-1),'YYYY-MM')"+
				                                " THEN TO_CHAR(wkDay.maxEndTime,'YYYY-MM-DD')"+
				                        " END"+
				                    " AND TO_CHAR (END_TIMESTAMP, 'YYYY-MM-DD') <= TO_CHAR(wkDay.maxEndTime,'YYYY-MM-DD')"+
				                    " AND STUDENT_NUMBER="+studentNum+")"+    
				        " WHEN TRIM(TO_CHAR (wkDay.maxEndTime, 'Day'))='Thursday'"+
				            " THEN (SELECT NVL(SUM(TOTAL_HRS),0) AS WeeklyHrs"+
				                    " FROM STUDENT_TELECENTRE"+
				                    " WHERE TO_CHAR (END_TIMESTAMP, 'YYYY-MM-DD') >="+ 
				                        " CASE"+
				                            " WHEN TO_CHAR(wkDay.maxEndTime-4,'YYYY-MM') = TO_CHAR(CURRENT_TIMESTAMP,'YYYY-MM')"+
				                                " THEN TO_CHAR(wkDay.maxEndTime-4,'YYYY-MM-DD')"+
				                            " WHEN TO_CHAR(wkDay.maxEndTime-3,'YYYY-MM') = TO_CHAR(CURRENT_TIMESTAMP,'YYYY-MM')"+
				                                " THEN TO_CHAR(wkDay.maxEndTime-3,'YYYY-MM-DD')"+
				                            " WHEN TO_CHAR(wkDay.maxEndTime-2,'YYYY-MM') = TO_CHAR(CURRENT_TIMESTAMP,'YYYY-MM')"+
				                                " THEN TO_CHAR(wkDay.maxEndTime-2,'YYYY-MM-DD')"+
				                            " WHEN TO_CHAR(wkDay.maxEndTime-1,'YYYY-MM') = TO_CHAR(CURRENT_TIMESTAMP,'YYYY-MM')"+
				                                 " THEN TO_CHAR(wkDay.maxEndTime-1,'YYYY-MM-DD')"+
				                            " WHEN TO_CHAR(wkDay.maxEndTime-1,'YYYY-MM') = TO_CHAR(ADD_MONTHS(wkDay.maxEndTime,-1),'YYYY-MM')"+
				                                " THEN TO_CHAR(wkDay.maxEndTime,'YYYY-MM-DD')"+    
				                        " END"+
				                    " AND TO_CHAR (END_TIMESTAMP, 'YYYY-MM-DD') <= TO_CHAR(wkDay.maxEndTime,'YYYY-MM-DD')"+
				                    " AND STUDENT_NUMBER="+studentNum+")"+   
				        " WHEN TRIM(TO_CHAR (wkDay.maxEndTime, 'Day'))='Friday'"+
				            " THEN (SELECT NVL(SUM(TOTAL_HRS),0) AS WeeklyHrs"+
				                    " FROM STUDENT_TELECENTRE"+
				                    " WHERE TO_CHAR (END_TIMESTAMP, 'YYYY-MM-DD') >="+ 
				                        " CASE"+ 
				                            " WHEN TO_CHAR(wkDay.maxEndTime-5,'YYYY-MM') = TO_CHAR(CURRENT_TIMESTAMP,'YYYY-MM')"+
				                                " THEN TO_CHAR(wkDay.maxEndTime-5,'YYYY-MM-DD')"+
				                            " WHEN TO_CHAR(wkDay.maxEndTime-4,'YYYY-MM-DD') = TO_CHAR(CURRENT_TIMESTAMP,'YYYY-MM')"+
				                                " THEN TO_CHAR(wkDay.maxEndTime-4,'YYYY-MM-DD')"+
				                            " WHEN TO_CHAR(wkDay.maxEndTime-3,'YYYY-MM') = TO_CHAR(CURRENT_TIMESTAMP,'YYYY-MM')"+
				                                " THEN TO_CHAR(wkDay.maxEndTime-3,'YYYY-MM-DD')"+
				                            " WHEN TO_CHAR(wkDay.maxEndTime-2,'YYYY-MM') = TO_CHAR(CURRENT_TIMESTAMP,'YYYY-MM')"+
				                                " THEN TO_CHAR(wkDay.maxEndTime-2,'YYYY-MM-DD')"+                                               
				                            " WHEN TO_CHAR(wkDay.maxEndTime-1,'YYYY-MM') = TO_CHAR(CURRENT_TIMESTAMP,'YYYY-MM')"+ 
				                                 " THEN TO_CHAR(wkDay.maxEndTime-1,'YYYY-MM-DD')"+                                               
				                            " WHEN TO_CHAR(wkDay.maxEndTime-1,'YYYY-MM') = TO_CHAR(ADD_MONTHS(wkDay.maxEndTime,-1),'YYYY-MM')"+ 
				                                " THEN TO_CHAR(wkDay.maxEndTime,'YYYY-MM-DD')"+    
				                        " END"+
				                    " AND TO_CHAR (END_TIMESTAMP, 'YYYY-MM-DD') <= TO_CHAR(wkDay.maxEndTime,'YYYY-MM-DD')"+
				                    " AND STUDENT_NUMBER="+studentNum+")"+
				        " WHEN TRIM(TO_CHAR (wkDay.maxEndTime, 'Day'))='Saturday'"+
				            " THEN (SELECT NVL(SUM(TOTAL_HRS),0) AS WeeklyHrs"+
				                    " FROM STUDENT_TELECENTRE"+
				                    " WHERE TO_CHAR (END_TIMESTAMP, 'YYYY-MM-DD') >="+ 
				                        " CASE"+ 
				                            " WHEN TO_CHAR(wkDay.maxEndTime-6,'YYYY-MM') = TO_CHAR(CURRENT_TIMESTAMP,'YYYY-MM')"+               
				                            " 	THEN TO_CHAR(wkDay.maxEndTime-6,'YYYY-MM-DD')"+                                                   
				                            " WHEN TO_CHAR(wkDay.maxEndTime-5,'YYYY-MM-DD') = TO_CHAR(CURRENT_TIMESTAMP,'YYYY-MM')"+            
				                                " THEN TO_CHAR(wkDay.maxEndTime-5,'YYYY-MM-DD')"+                                               
				                            " WHEN TO_CHAR(wkDay.maxEndTime-4,'YYYY-MM-DD') = TO_CHAR(CURRENT_TIMESTAMP,'YYYY-MM')"+            
				                                " THEN TO_CHAR(wkDay.maxEndTime-4,'YYYY-MM-DD')"+                                               
				                            " WHEN TO_CHAR(wkDay.maxEndTime-3,'YYYY-MM') = TO_CHAR(CURRENT_TIMESTAMP,'YYYY-MM')"+               
				                                " THEN TO_CHAR(wkDay.maxEndTime-3,'YYYY-MM-DD')"+                                               
				                            " WHEN TO_CHAR(wkDay.maxEndTime-2,'YYYY-MM') = TO_CHAR(CURRENT_TIMESTAMP,'YYYY-MM')"+               
				                                " THEN TO_CHAR(wkDay.maxEndTime-2,'YYYY-MM-DD')"+                                               
				                            " WHEN TO_CHAR(wkDay.maxEndTime-1,'YYYY-MM') = TO_CHAR(CURRENT_TIMESTAMP,'YYYY-MM')"+               
				                                 " THEN TO_CHAR(wkDay.maxEndTime-1,'YYYY-MM-DD')"+                                               
				                            " WHEN TO_CHAR(wkDay.maxEndTime-1,'YYYY-MM') = TO_CHAR(ADD_MONTHS(wkDay.maxEndTime,-1),'YYYY-MM')"+ 
				                                " THEN TO_CHAR(wkDay.maxEndTime,'YYYY-MM-DD')"+
				                        " END"+
				                    " AND TO_CHAR (END_TIMESTAMP, 'YYYY-MM-DD') <= TO_CHAR(wkDay.maxEndTime,'YYYY-MM-DD')"+
				                    " AND STUDENT_NUMBER="+studentNum+")"+
				        " WHEN TRIM(TO_CHAR (wkDay.maxEndTime, 'Day'))='Sunday'"+
				            " THEN (SELECT NVL(SUM(TOTAL_HRS),0) AS WeeklyHrs"+
				                    " FROM STUDENT_TELECENTRE"+
				                    " WHERE TO_CHAR (END_TIMESTAMP, 'YYYY-MM-DD') = TO_CHAR(wkDay.maxEndTime,'YYYY-MM-DD')"+
				                    " AND STUDENT_NUMBER="+studentNum+")"+
				        " ELSE 0"+                                                                   
				    " END AS TotalWeeklyHrs"+
				 " FROM (SELECT NVL(MAX(END_TIMESTAMP),CURRENT_TIMESTAMP) AS maxEndTime"+
				        " FROM STUDENT_TELECENTRE"+
				        " WHERE TO_CHAR (END_TIMESTAMP, 'YYYY-MM-DD') = '"+dateStr+"'"+
				        " AND STUDENT_NUMBER="+studentNum+                                 
				        " ) wkDay";
		return availableHoursDouble(sql, "TotalWeeklyHrs");
	}
	
	public int addTelecentreAdmin(String username, String staffName, String staffEmail, String regional) throws Exception{
		try{
			//sub-query to get the Sakai user ID from the SAKAI_SITE_USER table-to be user as a primary key
			String sqlForPrimaryKey=" (SELECT suser.USER_ID"+
								  	" FROM SAKAI_SITE_USER suser, SAKAI_SITE ssite, SAKAI_USER_ID_MAP smap"+
								  	" WHERE suser.SITE_ID = ssite.SITE_ID"+
								        " AND suser.USER_ID = smap.USER_ID"+
								        " AND LOWER (ssite.TITLE) = 'telecentres'"+
								        " AND suser.PERMISSION = -1"+
								        " AND lower(smap.EID)='"+username.toLowerCase()+"')";
			
			//use above subquery in query below for primary key
			String sql=" INSERT INTO TELECENTRE_ADMIN(SAKAI_USER_ID,LDAP_USR,STAFF_NAME,STAFF_EMAIL,REGIONAL) "+
	        		   "VALUES("+sqlForPrimaryKey+",'"+username.toLowerCase()+"','"+staffName+"','"+staffEmail+"','"+regional+"')";
	        JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int resultUpdate=jdt.update(sql);
			return resultUpdate;		
		}catch(Exception ex){
			throw new Exception("TelecentreDAO.java, 'addTelecentreAdmin("+username+")':"+ex);
		}
	}
	
	public boolean checkTelecentreAdminExists(String adUsername) throws Exception{
		try{
			String sql= " SELECT count(LDAP_USR) as name_occur FROM TELECENTRE_ADMIN"+
						" WHERE lower(LDAP_USR)='"+adUsername.toLowerCase()+"'"+
						" ORDER BY LDAP_USR ASC";	  
			 String name_occurances=this.querySingleValue(sql, "name_occur");
			 if((name_occurances.trim().equals(null))||(name_occurances.trim().equals(""))||(name_occurances.trim().equals("0"))){
				 return false;
			 }else{
				 return true;
			 }
		}catch(Exception ex){
			throw new Exception("TelecentreDAO.java, 'checkTelecentreAdminExists("+adUsername+")':"+ex);
		}
	}
	
	public int updateTelAdminDetails(String username, String staffName, String staffEmail, String regional, String updateField) throws Exception{
		try{
			int resultUpdate=0;
			//name, email and region in DB are not identical to user input - then update sname, email and region
			if(updateField.equals("all")){
				String sql=" UPDATE TELECENTRE_ADMIN"+
		        		   " SET STAFF_NAME='"+staffName+"', STAFF_EMAIL='"+staffEmail+"', REGIONAL='"+regional+"'"+
		        		   " WHERE lower(LDAP_USR)='"+username.toLowerCase()+"'";
		        JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				resultUpdate=jdt.update(sql);
			}
			//name and email in DB are not identical to user input - then update name and email
			if(updateField.equals("nameEmail")){
				String sql=" UPDATE TELECENTRE_ADMIN"+
		        		   " SET STAFF_NAME='"+staffName+"', STAFF_EMAIL='"+staffEmail+"'"+
		        		   " WHERE lower(LDAP_USR)='"+username.toLowerCase()+"'";
		        JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				resultUpdate=jdt.update(sql);
			}
			//name and region in DB are not identical to user input - then update name and region
			if(updateField.equals("nameRegion")){
				String sql=" UPDATE TELECENTRE_ADMIN"+
		        		   " SET STAFF_NAME='"+staffName+"', REGIONAL='"+regional+"'"+
		        		   " WHERE lower(LDAP_USR)='"+username.toLowerCase()+"'";
		        JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				resultUpdate=jdt.update(sql);
			}
			//email and region in DB are not identical to user input - then update email and region
			if(updateField.equals("emailRegion")){
				String sql=" UPDATE TELECENTRE_ADMIN"+
		        		   " SET STAFF_EMAIL='"+staffEmail+"', REGIONAL='"+regional+"'"+
		        		   " WHERE lower(LDAP_USR)='"+username.toLowerCase()+"'";
		        JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				resultUpdate=jdt.update(sql);
			}
			//name in DB is not identical to user input - then update staff name
			if(updateField.equals("name")){
				String sql=" UPDATE TELECENTRE_ADMIN"+
		        		   " SET STAFF_NAME='"+staffName+"'"+
		        		   " WHERE lower(LDAP_USR)='"+username.toLowerCase()+"'";
		        JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				resultUpdate=jdt.update(sql);
			}
			//email in DB is not identical to user input - then update staff email
			if(updateField.equals("email")){
				String sql=" UPDATE TELECENTRE_ADMIN"+
		        		   " SET STAFF_EMAIL='"+staffEmail+"'"+
		        		   " WHERE lower(LDAP_USR)='"+username.toLowerCase()+"'";
		        JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				resultUpdate=jdt.update(sql);
			}
			//regional flag in DB is not identical to user input - then update regional flag
			if(updateField.equals("region")){
				String sql=" UPDATE TELECENTRE_ADMIN"+
		        		   " SET REGIONAL='"+regional+"'"+
		        		   " WHERE lower(LDAP_USR)='"+username.toLowerCase()+"'";
		        JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				resultUpdate=jdt.update(sql);
			}
			return resultUpdate;	
		}catch(Exception ex){
			throw new Exception("TelecentreDAO.java, 'updateTelAdminDetails("+username+")':"+ex);
		}
	}
	
	public boolean queryTelAdminDetails(String username, String staffName, String staffEmail, String regional, String selector) throws Exception{
		try{
			boolean adminDetailExists = false;
			//query identical Staff Name
			if(!staffName.equals("") && selector.equals("name")){
				String sameStaffNameSql="SELECT count(STAFF_NAME) as name_query FROM TELECENTRE_ADMIN"+
					   " WHERE lower(LDAP_USR)='"+username.toLowerCase()+"' AND STAFF_NAME='"+staffName+"'";
				String nameQuery=this.querySingleValue(sameStaffNameSql, "name_query");
				if((nameQuery.trim().equals(null))||(nameQuery.trim().equals(""))||(nameQuery.trim().equals("0"))){
					adminDetailExists=false;
				 }else{
					adminDetailExists=true;
				 }
			}
			//query identical Staff Email
			if(!staffEmail.equals("") && selector.equals("email") ){
				String sameStaffEmailSql="SELECT count(STAFF_EMAIL) as email_query FROM TELECENTRE_ADMIN"+
					   " WHERE lower(LDAP_USR)='"+username.toLowerCase()+"' AND STAFF_EMAIL='"+staffEmail+"'";
				String emailQuery=this.querySingleValue(sameStaffEmailSql, "email_query");
				if((emailQuery.trim().equals(null))||(emailQuery.trim().equals(""))||(emailQuery.trim().equals("0"))){
					adminDetailExists=false;
				 }else{
					adminDetailExists=true;
				 }
			}
			//query identical Regional flag
			if(!regional.equals("") && selector.equals("region")){
				String sameRegionalSql="SELECT count(REGIONAL) as regional_query FROM TELECENTRE_ADMIN"+
					   " WHERE lower(LDAP_USR)='"+username.toLowerCase()+"' AND REGIONAL='"+regional+"'";
				String regionalQuery=this.querySingleValue(sameRegionalSql, "regional_query");
				if((regionalQuery.trim().equals(null))||(regionalQuery.trim().equals(""))||(regionalQuery.trim().equals("0"))){
					adminDetailExists=false;
				 }else{
					adminDetailExists=true;
				 }
			}
			return adminDetailExists;
		}catch(Exception ex){
			throw new Exception("TelecentreDAO.java, 'queryTelAdminDetails("+username+")':"+ex);
		}
	}
	
	public int removeTelecentreAdmin(String sakaiUsrId) throws Exception{
		try{
			String sql=" DELETE FROM TELECENTRE_ADMIN"+
					" WHERE SAKAI_USER_ID='"+sakaiUsrId+"'";
	        JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int resultUpdate=jdt.update(sql);
			return resultUpdate;		//return the number of rows affected(inserted)
		}catch(Exception ex){
			throw new Exception("TelecentreDAO.java, 'removeTelecentreAdmin("+sakaiUsrId+")':"+ex);
		}
	}
	
	public ArrayList getAdminInfo(){
		String sql=" SELECT LDAP_USR, REGIONAL, nvl(STAFF_EMAIL,'NO EMAIL AVAILABLE') ST_EMAIL,"+
				   " nvl(STAFF_NAME,'NO STAFF NAME AVAILABLE') ST_NAME FROM TELECENTRE_ADMIN"+
				   " WHERE REGIONAL <> 'A'";
		return getAdminArrayList(sql);
	}
	
	public ArrayList getAdminInfo(String sakaiUsrID){
		String sql=" SELECT LDAP_USR, REGIONAL, nvl(STAFF_EMAIL,'NO EMAIL AVAILABLE') ST_EMAIL,"+
				   " nvl(STAFF_NAME,'NO STAFF NAME AVAILABLE') ST_NAME FROM TELECENTRE_ADMIN"+
				   " WHERE REGIONAL <> 'A' AND SAKAI_USER_ID='"+sakaiUsrID+"'";
		return getAdminArrayList(sql);
	}
	
	private ArrayList getAdminArrayList(String sql){
	    ArrayList<TelecentreDetails> results = new ArrayList();
	    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
        List queryList;
        queryList = jdt.queryForList(sql);
        Iterator i = queryList .iterator();
        ListOrderedMap data;
        int x=0;
        while (i.hasNext()){
	        data = (ListOrderedMap) i.next();
	        writeDataToAdminClass(data,results);
        }
        return results;
	}
	private void writeDataToAdminClass(ListOrderedMap data,ArrayList results){
	      TelecentreDetails telecentreDetails = new TelecentreDetails();
         try{
	          telecentreDetails.setAdminADusername(data.get("LDAP_USR").toString());
              telecentreDetails.setAdminEmail(data.get("ST_EMAIL").toString());
              telecentreDetails.setAdminName(data.get("ST_NAME").toString());
              telecentreDetails.setAdminRegionFlag(data.get("REGIONAL").toString());
	          results.add(telecentreDetails);
	     }catch(NullPointerException e ){
	    	  e.printStackTrace();
         }
    }
	
	public ArrayList getAdminNameLabelPairs(){
		 String sql= " SELECT nvl(STAFF_NAME,'NO STAFF NAME AVAILABLE') ST_NAME,LDAP_USR,SAKAI_USER_ID"+
				 	 " FROM TELECENTRE_ADMIN WHERE REGIONAL <> 'A' ORDER BY ST_NAME";
		  
		 ArrayList results = new ArrayList();
		  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		  List queryList;
		  queryList = jdt.queryForList(sql);
		  Iterator i = queryList .iterator();
		  while(i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			String adminName = data.get("ST_NAME").toString();
			String adminUsr = data.get("LDAP_USR").toString();
			String adminSakaiID = data.get("SAKAI_USER_ID").toString();
			results.add(new org.apache.struts.util.LabelValueBean((adminName+"("+adminUsr+")"),adminUsr));
		  }
		  return results;
	}
	
	//get sakai user ID for Telecentre Admin using Admin User Name
	public String getAdminSakaiID(String AdminDBUsrName){
		String queryResult;
		String sql = " SELECT SAKAI_USER_ID FROM TELECENTRE_ADMIN"+
				 	 " WHERE lower(LDAP_USR)='"+AdminDBUsrName.toLowerCase()+"'";
		queryResult=querySingleValue(sql, "SAKAI_USER_ID");
		return queryResult;
	}
	
	public boolean isRegionalAdmin(String username) throws Exception{
		try{
			boolean adminIsRegional = false;
			//query if username is found as regional=Y
			String userRegional="SELECT count(LDAP_USR) AS regional_query FROM TELECENTRE_ADMIN"+
				" WHERE LDAP_USR='"+username.toLowerCase()+"' AND REGIONAL='Y'";
			
			String regionQuery=this.querySingleValue(userRegional, "regional_query");
			if((regionQuery.trim().equals(null))||(regionQuery.trim().equals(""))||(regionQuery.trim().equals("0"))){
				adminIsRegional=false;
			 }else{
				 adminIsRegional=true;
			 }
			 return adminIsRegional;
		}catch(Exception ex){
			throw new Exception("TelecentreDAO.java, 'isRegionalAdmin("+username+")':"+ex);
		}
	}
	
}
