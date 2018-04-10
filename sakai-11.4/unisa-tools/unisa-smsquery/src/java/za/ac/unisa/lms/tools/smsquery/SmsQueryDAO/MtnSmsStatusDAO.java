package za.ac.unisa.lms.tools.smsquery.SmsQueryDAO;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
//import org.sakaiproject.db.cover.SqlService;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.db.StudentSystemDAO;


public class MtnSmsStatusDAO  extends StudentSystemDAO{
	
	       public PersonnelClass getSenderDetails(String batchNum,String sequenceNum){
	        	     	     String sql="select title,initials,surname,email_address,contact_telno,persno,status,nvl(description,'') as description "+
	        	     	     "from  staff x,smslog b,smsmtn a where x.persno=b.mk_pers_nr and b.mk_mtn_status_code= a.code "+
  	                         " and mk_batch_nr='"+batchNum+"' and  sequence_nr='"+sequenceNum+"'";
                     return getDataFromDatabase(sql);
            }
	        private PersonnelClass  getDataFromDatabase(String sql){
               ArrayList results = new ArrayList();
               JdbcTemplate jdt = new JdbcTemplate(getDataSource());
               List queryList;
               queryList = jdt.queryForList(sql);
               Iterator i = queryList .iterator();
               PersonnelClass  personnelObj = new PersonnelClass();
               while(i.hasNext()){	
                    ListOrderedMap data = (ListOrderedMap) i.next();
                    try{
                    	 personnelObj.setPersonnelNumber(data.get("persno").toString());
                  	     personnelObj.setInitial(data.get("initials").toString());
        	             personnelObj.setTitle(data.get("title").toString());
        	             personnelObj.setSurname(data.get("surname").toString());
        	             personnelObj.setEmail(data.get("email_address").toString());
        	             personnelObj.setTelNumber(data.get("contact_telno").toString());
        	             personnelObj.setDescription(data.get("description").toString());
        	             personnelObj.setMessageStatus(data.get("status").toString());
        	             
        	         }catch(NullPointerException e ){e.printStackTrace();}
        	         break;
                }
               return personnelObj;
           }
	       //0112074500 African Bank 
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
           public String getMessage(String batchNum,String sequenceNum){
        	               String query="select   message from  smslog where mk_batch_nr ='"+batchNum+"'  and sequence_nr='"+sequenceNum+"'";
        	               return querySingleValue(query,"message");
           }
           public String getPersonnelNumber(String batchNum,String sequenceNum){
                      String query="select mk_pers_nr from  smslog where mk_batch_nr='"+batchNum+"'  and sequence_nr='"+sequenceNum+"'";
                      return querySingleValue(query,"mk_pers_nr");
           }
           public String getMtnStatus(String message,String sequenceNum){
        	          String sql="select status from smsmtn a,smslog b  where a.code=b.mk_mtn_status_code  and message='"+message+"'  and sequence_nr='"+sequenceNum+"'";
        	          return querySingleValue(sql,"status");
           }
           
} //3416
//0860110803  morkels


