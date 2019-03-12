package za.ac.unisa.lms.tools.cronjobs.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.collections.map.ListOrderedMap;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;
import org.springframework.jdbc.core.JdbcTemplate;

public class DropboxEventsUpdateDAO extends SakaiDAO{
	 private EmailService emailService;
	
	 public boolean checkDropboxJob() throws Exception{
		/* Date d = new Date();
		 String day    = new SimpleDateFormat ("dd").format (d);
		 String month    = new SimpleDateFormat ("MMM").format (d);
		 String year    = new SimpleDateFormat ("yyyy").format (d);		 
		 String date=day+"/"+month.toUpperCase()+"/"+year; */
		// String statsDB = ServerConfigurationService.getString("sitestatsDB");
		 String statsDB="@SAKH_PROD.UNISA.AC.ZA";
		 boolean count= false;
		 
		 String select = "select COUNT(*) as COUNT from SST_events"+statsDB+"  where " +
         " event_id like '%drop%' and " +
         " to_DATE(event_date,'DD/MON/YYYY') = to_date(SYSDATE,'DD/MON/YYYY')-1";

             try {
                    String recCount = this.querySingleValue(select,"COUNT");
                  int counter =  Integer.parseInt(recCount); 
                 if(counter > 0){
                  count= true;
                     }else {
                   count =false;
                   }	
              } catch (Exception ex) {    
    throw new Exception("in DropboxEventsUpdateDAO updateSitestatsSstEvents select "+ ex,ex);
              }
              return count;
	 }
	 
	public void updateDropboxEvents(String content) throws Exception{
		int eventStats = 0;
		String errorMessage = "";
		/*Date d = new Date();
		 String day    = new SimpleDateFormat ("dd").format (d);
		 String month    = new SimpleDateFormat ("MMM").format (d);
		 String year    = new SimpleDateFormat ("yyyy").format (d);
		 
		 String date=day+"/"+month.toUpperCase()+"/"+year; */
		 String query = " select ss.session_user userId,context,event,to_char(event_date,'DD/MON/YYYY') as eventdate from sakai_event se,sakai_session ss " +
		 		"where  ss.session_id=se.session_id and   LENGTH(REGEXP_REPLACE(REF,'[^/]'))>4" +
		 		" and substr(ref,regexp_instr( ref, '/', instr(ref, '/', -1))+1) is not null and " +
		 		"ref like '%group-user%'  and event='"+content+"' and to_DATE(event_date,'DD/MON/YYYY') = to_date(SYSDATE,'DD/MON/YYYY')-1" +
		 	    " and context is not null ";

		try {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List<?> queryList = jdt.queryForList(query);
		Iterator<?> i = queryList.iterator();
		while(i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			String coursecode = data.get("context").toString();
			String userId = data.get("userId").toString();
			String eventId = data.get("event").toString();
			String eventDate = data.get("eventdate").toString();
			
			updateSitestatsSstEvents(coursecode, userId,eventId,eventDate);
			updateSiteActivity(coursecode,eventId,eventDate);
		}
	}catch (Exception ex) {
		 sendEmail("unisa-cronjobs:error occured","DropboxEventsUpdateDAO: updateDropboxEvents select(main) sakai_event Error occurred","udcsweb@unisa.ac.za");
		throw new Exception("DropboxEventsUpdateDAO:getEventStats Error "+ ex);		
	}
	}
	
	private void updateSiteActivity(String coursecode,String eventId,String activityDate) throws Exception{
		String errorMsg1="";
		String errorMsg2="";
		String errorMsg3="";		
		//String statsDB = ServerConfigurationService.getString("sitestatsDB");
		 String statsDB="@SAKH_PROD.UNISA.AC.ZA";
		boolean count= false;
		boolean dropboxCount = false;
		String dropboxEvent="";
		if(eventId.equals("content.new")){
			dropboxEvent="dropbox.new";
		}
		if(eventId.equals("content.delete")){
			dropboxEvent="dropbox.delete";
		}
		//count the events for content.new in sst_events table  SAKH_PROD.UNISA.AC.ZA 
		String select = "select COUNT(*) as COUNT from sst_siteactivity"+statsDB+"  where " +
				           "site_id='"+coursecode+"' and event_id='"+eventId+"'" +
				           " and to_char(activity_date,'DD/MON/YYYY') ='"+activityDate+"'";
		
		 try {
             String recCount = this.querySingleValue(select,"COUNT");
             int counter =  Integer.parseInt(recCount); 
	         if(counter > 0){
		          count= true;
	           }else {
	        	 count =false;
	            }	
             } catch (Exception ex) {
            	 sendEmail("unisa-cronjobs:error occured","DropboxEventsUpdateDAO: updateSiteActivity select(1) SST_siteActivity count Error occurred on "+activityDate+"","udcsweb@unisa.ac.za");
	           throw new Exception("in DropboxEventsUpdateDAO updateSitestatsSstEvents select "+ ex,ex);
                }
       //go to the loop if content.new exists in sitestats table
        if(count==true){
        	//check for the dropbox events which already there for the course,date and for the year
        	
        	String selectDropboxActivity = "select COUNT(*) as COUNT from sst_siteactivity"+statsDB+" where  " +
	           " site_id='"+coursecode+"' and event_id='"+dropboxEvent+"'" +
	           " and to_char(activity_date,'DD/MON/YYYY') ='"+activityDate+"'";
        	try {
                String recCount = this.querySingleValue(selectDropboxActivity,"COUNT");
                int dropboxCounter =  Integer.parseInt(recCount); 
   	         if(dropboxCounter > 0){
   	        	dropboxCount= true;
   	           }else {
   	        	dropboxCount =false;
   	            }} catch (Exception ex) {
   	           sendEmail("unisa-cronjobs:error occured","DropboxEventsUpdateDAO: updateSiteActivity select(2) SST_siteActivity count Error occurred on "+activityDate+"","udcsweb@unisa.ac.za");
   	           throw new Exception("in DropboxEventsUpdateDAO selectDropboxEvents "+ ex,ex);
   	       
                   }
        	if(dropboxCount==true){          	
		      String sstUpdate = "update  sst_siteactivity"+statsDB+"  set activity_count=activity_count+1 where " +
				           " site_id='"+coursecode+"' and event_id='"+dropboxEvent+"'" +
				           " and to_char(activity_date,'DD/MON/YYYY') ='"+activityDate+"'";
		      
		      String sstUpdate1 = "update  sst_siteactivity"+statsDB+"  set activity_count=activity_count-1 where " +
	           " site_id='"+coursecode+"' and event_id='"+eventId+"'" +
	           " and to_char(activity_date,'DD/MON/YYYY') ='"+activityDate+"'";
				
	    	try{
			
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sstUpdate);
			jdt1.update(sstUpdate1);
		} catch (Exception ex) {
			 sendEmail("unisa-cronjobs:error occured","DropboxEventsUpdateDAO: updateSiteActivity2 SST_siteActivity Error occurred on "+activityDate+"","udcsweb@unisa.ac.za");
			 throw new Exception("DropboxEventsUpdateDAO: updateSiteActivity2 SST_siteActivity Error occurred"+ ex,ex);
			}
        }else{
        	//update SST_events to decrease the count -1 and insert new record into SST_events
            String sstUpdate = "update  sst_siteactivity"+statsDB+"  set activity_count=activity_count-1 where " +
	           " site_id='"+coursecode+"' and event_id='"+eventId+"'" +
	           " and to_char(activity_date,'DD/MON/YYYY') ='"+activityDate+"'";
	
           try{
         JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
         jdt1.update(sstUpdate);
           } catch (Exception ex) {
         sendEmail("unisa-cronjobs:error occured","DropboxEventsUpdateDAO: updateSiteActivity2 SST_siteActivity Error occurred on "+activityDate+"","udcsweb@unisa.ac.za");
         throw new Exception(" DropboxEventsUpdateDAO: updateSiteActivity SST_siteActivity error occurred"+ ex,ex);
           //throw new Exception("MyUnisaStatsDAO: updateUnisaMIS2 (sqlUpdate="+sqlUpdate+"): (event="+event+") Error occurred / "+ ex,ex);
            }
           
           int id=getSisteActivityId(); 
                    
           String insertquery = " insert into sst_siteactivity"+statsDB+" (id,site_id,activity_date,event_id,activity_count) " +
	        " values("+id+",'"+coursecode+"','"+activityDate+"','"+dropboxEvent+"',1)";
           try{
               JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
               jdt1.update(insertquery);
                 } catch (Exception ex) {
                sendEmail("unisa-cronjobs:error occured","DropboxEventsUpdateDAO: updateSiteActivity SST_siteActivity insertquery Error occurred on "+activityDate+"","udcsweb@unisa.ac.za");
                throw new Exception("updateSiteActivity SST_siteActivity insertquery Error occurred"+ ex,ex);
                  }
          }
 
        }
	}
	
	
	private void updateSitestatsSstEvents(String coursecode, String userId,String eventId,String eventDate) throws Exception{
		boolean count= false;
		boolean dropboxCount = false;
		String dropboxEvent="";
		//String statsDB = ServerConfigurationService.getString("sitestatsDB");
		 String statsDB="@SAKH_PROD.UNISA.AC.ZA";
		
		if(eventId.equals("content.new")){
			dropboxEvent="dropbox.new";
		}
		if(eventId.equals("content.delete")){
			dropboxEvent="dropbox.delete";
		}
		//count the events for content.new in sst_events table
		String select = "select COUNT(*) as COUNT from SST_events"+statsDB+"   where " +
				           "user_id='"+userId+"'" +
				           " and site_id='"+coursecode+"' and event_id='"+eventId+"'" +
				           " and to_char(event_date,'DD/MON/YYYY') ='"+eventDate+"'";
		
		 try {
             String recCount = this.querySingleValue(select,"COUNT");
             int counter =  Integer.parseInt(recCount); 
	         if(counter > 0){
		          count= true;
	           }else {
	        	 count =false;
	            }	
             } catch (Exception ex) {
            	 sendEmail("unisa-cronjobs:error occured","DropboxEventsUpdateDAO: updateSitestatsSstEvents select(1) SST_events count Error occurred on "+eventDate+"","udcsweb@unisa.ac.za");
	           throw new Exception("in updateSitestatsSstEvents select "+ ex,ex);	      	
                }
       //go to the loop if content.new exists in sitestats table
        if(count==true){
        	//check for the dropbox events which already there for the course,date and for the year
        	
        	String selectDropboxEvents = "select COUNT(*) as COUNT from SST_EVENTS"+statsDB+"  where  " +
	           "user_id='"+userId+"'" +
	           " and site_id='"+coursecode+"' and event_id='"+dropboxEvent+"'" +
	           " and to_char(event_date,'DD/MON/YYYY') ='"+eventDate+"'";
        	try {
                String recCount = this.querySingleValue(selectDropboxEvents,"COUNT");
                int dropboxCounter =  Integer.parseInt(recCount); 
   	         if(dropboxCounter > 0){
   	        	dropboxCount= true;
   	           }else {
   	        	dropboxCount =false;
   	            }} catch (Exception ex) {
   	             sendEmail("unisa-cronjobs:error occured","DropboxEventsUpdateDAO: updateSitestatsSstEvents select(2) SST_events count Error occurred on "+eventDate+"","udcsweb@unisa.ac.za");
   	           throw new Exception("in checkBookNrAndBibNumMatch selectDropboxEvents "+ ex,ex);   	       
                   }
        	if(dropboxCount==true){          	
		      String sstUpdate = "update  sst_events"+statsDB+"  set event_count=event_count+1 where " +
				           "user_id='"+userId+"'" +
				           " and site_id='"+coursecode+"' and event_id='"+dropboxEvent+"'" +
				           " and to_char(event_date,'DD/MON/YYYY') ='"+eventDate+"'";
		      
		      String sstUpdate1 = "update  sst_events"+statsDB+"  set event_count=event_count-1 where " +
	           "user_id='"+userId+"'" +
	           " and site_id='"+coursecode+"' and event_id='"+eventId+"'" +
	           " and to_char(event_date,'DD/MON/YYYY') ='"+eventDate+"'";
			
		try{
			
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sstUpdate);
			jdt1.update(sstUpdate1);
		} catch (Exception ex) {
			sendEmail("unisa-cronjobs:error occured","DropboxEventsUpdateDAO: updateSitestatsSstEvents sstUpdate Error occurred on "+eventDate+"","udcsweb@unisa.ac.za");
			throw new Exception("iDropboxEventsUpdateDAO: updateSitestatsSstEvents sstUpdate Error occurred"+ ex,ex);
			}
        }else{
        	//update SST_events to decrease the count -1 and insert new record into SST_events
            String sstUpdate = "update  sst_events"+statsDB+"  set event_count=event_count-1 where " +
	           "user_id='"+userId+"'" +
	           " and site_id='"+coursecode+"' and event_id='"+eventId+"'" +
	           " and to_char(event_date,'DD/MON/YYYY') ='"+eventDate+"'";
	
         try{
         JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
         jdt1.update(sstUpdate);
           } catch (Exception ex) {
        	   sendEmail("unisa-cronjobs:error occured","DropboxEventsUpdateDAO: updateSitestatsSstEvents sstUpdate2 Error occurred on "+eventDate+"","udcsweb@unisa.ac.za");
        	   throw new Exception("DropboxEventsUpdateDAO: updateSitestatsSstEvents sstUpdate2 Error occurred"+ ex,ex);              
            }
           
           int id=getIdNumber(); 
           
           String insertquery = " insert into sst_events"+statsDB+" (id,user_id,site_id,event_id,event_date,event_count) " +
	        " values("+id+",'"+userId+"','"+coursecode+"','"+dropboxEvent+"','"+eventDate+"',1)";
           try{
               JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
               jdt1.update(insertquery);
                 } catch (Exception ex) {
               sendEmail("unisa-cronjobs:error occured","DropboxEventsUpdateDAO: updateSitestatsSstEvents insertquery Error occurred on "+eventDate+"","udcsweb@unisa.ac.za");
               throw new Exception("DropboxEventsUpdateDAO: updateSitestatsSstEvents insertquery Error occurred"+ ex,ex);
                  }
          }
 
        }		
	}
	
	   public int getSisteActivityId()throws Exception {
		   //String statsDB = ServerConfigurationService.getString("sitestatsDB");
		   String statsDB="@SAKH_PROD.UNISA.AC.ZA";
		  int id;
	       String query = " select max(id) as id from sst_siteactivity"+statsDB+" ";
	      try {
	       String idNr = this.querySingleValue(query,"id");	        
	         id =  Integer.parseInt(idNr); 
	         id=id+1;
	  } catch (Exception ex) {
	    throw new Exception("getIdNumber method "+ ex,ex);
	     }
	  return id;

	}
	
	   public int getIdNumber()throws Exception {
		   //String statsDB = ServerConfigurationService.getString("sitestatsDB");
		   String statsDB="@SAKH_PROD.UNISA.AC.ZA";
		  int id;
	       String query = " select max(id) as id from sst_events"+statsDB+" ";
	      try {
	       String idNr = this.querySingleValue(query,"id");	        
	         id =  Integer.parseInt(idNr); 
	         id=id+1;
	  } catch (Exception ex) {
	    throw new Exception("getIdNumber method "+ ex,ex);
	     }
	  return id;

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
	
	public void sendEmail(String subject, String body, String emailAddress) throws AddressException {
		
		emailService = (EmailService) ComponentManager.get(EmailService.class);

		String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");
        InternetAddress toEmail = new InternetAddress(emailAddress);
		InternetAddress iaTo[] = new InternetAddress[1];
		iaTo[0] = toEmail;
		InternetAddress iaHeaderTo[] = new InternetAddress[1];
		iaHeaderTo[0] = toEmail;
		InternetAddress iaReplyTo[] = new InternetAddress[1];
		iaReplyTo[0] = new InternetAddress(tmpEmailFrom);
		List<String> contentList = new ArrayList<String>();
		contentList.add("Content-Type: text/html");
		
		emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
		//log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
	} // end of sendEmail


}
