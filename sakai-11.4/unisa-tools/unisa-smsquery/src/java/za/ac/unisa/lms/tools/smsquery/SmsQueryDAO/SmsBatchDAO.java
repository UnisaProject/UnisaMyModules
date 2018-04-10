package za.ac.unisa.lms.tools.smsquery.SmsQueryDAO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.db.StudentSystemDAO;

public class SmsBatchDAO extends StudentSystemDAO{
	
	     public ArrayList getAllBatches(){

	     		String sql = " select request_timestamp,batch_nr,nvl(message_cnt,'0') as message_cnt,nvl(total_cost,'0') as total_cost,nvl(sms_msg,'')   as msg"+
		             " from smsreq";
	     		return getBatchesFromDatabase(sql);
	     }
	     public ArrayList getAllBatchesForTimeInterval(String fromDate,String toDate){

	     		String sql = " select request_timestamp,batch_nr,nvl(message_cnt,'0') as message_cnt,nvl(total_cost,'0') as total_cost,nvl(sms_msg,'')   as msg"+
		             " from smsreq  where to_char(request_timestamp,'YYYY-MM-DD')>='"+fromDate+"' and to_char(request_timestamp,'YYYY-MM-DD')<='"+toDate+"'";
	     		return getBatchesFromDatabase(sql);
	     }
	     public ArrayList getBatchesForRefCode(String responsiblityCode,String fromDate,String toDate){

	     		String sql = " select request_timestamp,batch_nr,nvl(message_cnt,'0') as message_cnt,nvl(total_cost,'0') as total_cost,nvl(sms_msg,'')   as msg"+
		             " from smsreq where mk_rc_code="+responsiblityCode+" and"+
	     		     "  to_char(request_timestamp,'YYYY-MM-DD')>='"+fromDate+"' and to_char(request_timestamp,'YYYY-MM-DD')<='"+toDate+"'";
	     		return getBatchesFromDatabase(sql);
	     }
	     public  ArrayList getBatchesForPersNum(String personnelNumber,String fromDate,String toDate){
 	    	         String sql = " select request_timestamp,batch_nr,nvl(message_cnt,'0') as message_cnt,nvl(total_cost,'0') as total_cost,nvl(sms_msg,'')  as msg"+
                     " from smsreq where mk_pers_nr="+personnelNumber+" and"+
                     "  to_char(request_timestamp,'YYYY-MM-DD')>='"+fromDate+"' and to_char(request_timestamp,'YYYY-MM-DD')<='"+toDate+"'";
	     		return getBatchesFromDatabase(sql);
	     }
	     public ArrayList getBatches(String responsiblityCode,String personnelNumber,String fromDate,String toDate){
	    	        if(!responsiblityCode.equals("")){
	    	        	return getBatchesForRefCode(responsiblityCode,fromDate,toDate);	
	    	        }else if(!personnelNumber.equals("")){
	    	        	return   getBatchesForRefCode(responsiblityCode,fromDate,toDate);
	    	        }else{
	    	        	return    getAllBatchesForTimeInterval(fromDate,toDate);
	    	        }
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
	     public  int getFROM_SYSTEM_GC26_field(String batchNum,String message){
  	       String query="select FROM_SYSTEM_GC26 from smsreq  where batch_nr='"+batchNum+"'  and sms_msg='"+message+"'";
  	       String  fromSysGC26=querySingleValue(query,"FROM_SYSTEM_GC26");
  	       if(fromSysGC26.equals("")){
  	    	   fromSysGC26="0";
  	       }
  	       return Integer.parseInt(fromSysGC26);
  	   
         }
	     public int getREASON_GC27_field(String batchNum,String message){
	          String query="select REASON_GC27 from smsreq  where batch_nr='"+batchNum+"'  and sms_msg='"+message+"'";
	          String  fromSysGC26=querySingleValue(query,"REASON_GC27");
	          if(fromSysGC26.equals("")){
	    	       fromSysGC26="0";
	          }
	          return Integer.parseInt(fromSysGC26);
	   
      }
	  private ArrayList getBatchesFromDatabase(String sql){
             ArrayList results = new ArrayList();
             JdbcTemplate jdt = new JdbcTemplate(getDataSource());
             List queryList;
             queryList = jdt.queryForList(sql);
             Iterator i = queryList.iterator();
             while (i.hasNext()){	
                  ListOrderedMap data = (ListOrderedMap) i.next();
                  SmsBatch smsBatch = new SmsBatch();
                  try{
       	                smsBatch.setBatchNr(data.get("batch_nr").toString());
	                    smsBatch.setRequestDate(data.get("request_timestamp").toString());
	                    smsBatch.setMessage(data.get("msg").toString());
	                    smsBatch.setBudgetAmount(data.get("total_cost").toString());
	                    smsBatch.setMessageCount(data.get("message_cnt").toString());
	                    results.add(smsBatch);			
                    }catch(NullPointerException e ){e.printStackTrace();}
                    }
              return results;
   }
}
