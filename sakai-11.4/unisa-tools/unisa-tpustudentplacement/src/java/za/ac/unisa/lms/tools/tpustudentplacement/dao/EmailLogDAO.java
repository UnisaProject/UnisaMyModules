package za.ac.unisa.lms.tools.tpustudentplacement.dao;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.EmailLogRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.DateUtil;
import java.util.List;
import java.util.Iterator;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.databaseUtils; 
import java.util.ArrayList;
public class EmailLogDAO extends StudentSystemDAO {
	           
	  
	  public void insertEmailLog(EmailLogRecord log) throws Exception {
	               int count=countMailsToOneAddressForTheDay(log.getEmailAddress().toUpperCase());
	               String sql = "insert into emllog (email_address,date_sent,recipient,recip_type_gc137,program," +
                                "email_type_gc138,subject,body0) " +
                                "values " +
                                "('" + log.getEmailAddress().toUpperCase() + "'," + 
                                "sysdate," +
                                "'" + log.getRecipient() + "'," +
                                "'" + log.getRecipientType() + "'," +
                                "'TEACHINGPRACTICEUNIT'," +
                               "'" + log.getEmailType() + "'," +
                               "'" + log.getSubject() + "'," +
                               "'" + log.getBody() + "')";	
    
                    String errorMsg="EmailLogDAO : : Error inserting EMLLOG / ";
                    databaseUtils databaseutils =new databaseUtils ();
                    if(count==0){
	                           databaseutils.update(sql,errorMsg);
                    }
       }
	   public List getEmailLog(int supervisorCode,int year) throws Exception {
                        String sql = " select email_address,to_char(date_sent,'YYYY-MM-DD HH24:MI') as dateSent ," +
	                                 " body0 as body  from  emllog  where"+
        		                     " recipient='"+supervisorCode+"' and recip_type_gc137 ='SUPERVISOR' and email_type_gc138='STUALLOC' and (program='TEACHINGPRACTICEUNIT' or program='TPU_BATCH') and"+
	                                 " to_char(date_sent,'YYYY')='"+year+"'";
                        databaseUtils databaseutils =new databaseUtils ();
                        String  errorMsg="EmailLogDAO: Error accessing emllog table";
                        List emaiLogList=databaseutils.queryForList(sql, errorMsg);
                        List  returnedList=new ArrayList();
                        Iterator i=emaiLogList.iterator();
                        while(i.hasNext()){
                        	ListOrderedMap data = (ListOrderedMap)i.next();
                        	EmailLogRecord log=new EmailLogRecord();
                        	String  totAllocated=databaseutils.replaceNull(data.get("body").toString());
                        	if(!totAllocated.equals("")){
                        		totAllocated=totAllocated.substring(totAllocated.indexOf(':')+1);
                        	}
                        	log.setBody(totAllocated);
                        	log.setEmailAddress(data.get("email_address").toString());
                        	log.setDateSent(data.get("dateSent").toString());
                        	returnedList.add(log);
                       }
                        return returnedList;
	   }
	  
       public int  countMailsToOneAddressForTheDay(String emailAddress) throws Exception {
              DateUtil dateUtil=new DateUtil();
              String todayDateStr=dateUtil.getTimeStampOfCurrTime();
              String sql = "select count(*)  as totEntries from emllog where email_address='"+emailAddress+
    		               "'  and to_char(date_sent,'YYYY-MM-DD HH24:mm:ss')='"+todayDateStr+"'";
              String errorMsg="EmailLogDAO : : Error reading  EMLLOG ";
              databaseUtils databaseutils =new databaseUtils ();
              String totEntries=databaseutils.querySingleValue(sql,"totEntries",errorMsg);
              if((totEntries==null)||(totEntries.trim().equalsIgnoreCase(""))){
            	       totEntries="0";
              }
              return Integer.parseInt(totEntries);
     }
}


