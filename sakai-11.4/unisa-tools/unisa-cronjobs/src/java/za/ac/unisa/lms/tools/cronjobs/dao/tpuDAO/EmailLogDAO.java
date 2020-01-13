package za.ac.unisa.lms.tools.cronjobs.dao.tpuDAO;

import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.map.ListOrderedMap;
import za.ac.unisa.lms.tools.cronjobs.forms.model.EmailLogRecord;
import za.ac.unisa.lms.tools.cronjobs.forms.util.DateUtil;
public class EmailLogDAO {
	public void insertEmailLog(EmailLogRecord log) throws Exception {
		       int count=countMailsToOneAddressForTheDay(log.getEmailAddress().trim().toUpperCase());
		           
                  String sql = "insert into emllog (email_address,date_sent,recipient,recip_type_gc137,program," +
                      "email_type_gc138,subject,body0) " +
                      "values " +
                      "('" + log.getEmailAddress().toUpperCase() + "'," + 
                      "sysdate," +
                      "'" + log.getRecipient() + "'," +
                      "'" + log.getRecipientType() + "'," +
                      "'"+ log.getProgram()+"'," +
                      "'" + log.getEmailType() + "'," +
                      "'" + log.getSubject() + "'," +
                      "'" + log.getBody() + "')";	
                  databaseUtils dbu=new databaseUtils();
                    String errorMsg="EmailLogDAO : : Error inserting EMLLOG / ";
                    if(count==0){
                            dbu.update(sql,errorMsg);
                    }
        
     }
	 public List getEmailLogs(String todayDate) throws Exception {
	   		   
                          String sql =" select * from emllog where to_char(date_sent,'YYYY-MM-DD')='"+todayDate+
                    		          "'and program='TPU_BATCH' order by date_sent desc";
		                  String errorMsg="Error reading emllog  / ";
		                  databaseUtils dbu=new databaseUtils();
                          List queryList = dbu.queryForList(sql,errorMsg);
		                  Iterator i = queryList.iterator();
                          while (i.hasNext()) {
                                ListOrderedMap data = (ListOrderedMap) i.next();
                                EmailLogRecord emailLogRecord=new EmailLogRecord();
                                emailLogRecord.setBody(data.get("BODY0").toString());
                                emailLogRecord.setRecipient(data.get("recipient").toString());
                                queryList.add(emailLogRecord);
                         }
                         return queryList;
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
	 public int getTotNumOfStuInEmailsSend(String todayDate) throws Exception {
		              List emailLogList=getEmailLogs(todayDate);
                      Iterator i=emailLogList.iterator();
                      int totStuNum=0;
                      while(i.hasNext()){
        	                  EmailLogRecord emailLogRecord=(EmailLogRecord)i.next();
        	                  String body=emailLogRecord.getBody();
        	                  String students= body.substring((body.indexOf(':')+1));
        	                  int totStuForEmail= Integer.parseInt(students);
        	                  totStuNum+=totStuForEmail;
                      }
                      return totStuNum;
    }
}
