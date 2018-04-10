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

public class PersonnelDAO  extends StudentSystemDAO{
	
	        private PersonnelClass getSenderDetails(String message){
	        	     String personnelNum=getPersonnelNumber(message);
                     String sql="select title,initials,surname,email_address,contact_telno,persno from  staff  where persno="+personnelNum;
                     return getDataFromDatabase(sql);
            }
	        private PersonnelClass  getDataFromDatabase(String sql){
               ArrayList results = new ArrayList();
               JdbcTemplate jdt = new JdbcTemplate(getDataSource());
               List queryList;
               queryList = jdt.queryForList(sql);
               Iterator i = queryList .iterator();
               PersonnelClass  personnelObj = new PersonnelClass();
               if(i.hasNext()){	
                    ListOrderedMap data = (ListOrderedMap) i.next();
                    try{
                  	     personnelObj.setInitial(data.get("initial").toString());
        	             personnelObj.setTitle(data.get("title").toString());
        	             personnelObj.setSurname(data.get("surname").toString());
        	             personnelObj.setEmail(data.get("email_address").toString());
        	             personnelObj.setSurname(data.get("contact_telno").toString());
        	             personnelObj.setPersonnelNumber(data.get("persno").toString());
                    }catch(NullPointerException e ){e.printStackTrace();}
                }
               return personnelObj;
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
           private String getBatchNumber(String message){
        	               String query="select mk_batch_nr  from msmlog where message="+message;
        	               return querySingleValue(query,"mk_batch_nr");
        	   
           }
           private String getPersonnelNumber(String message){
                   String query="select mk_pers_nr from  msmlog where message="+message;
                   return querySingleValue(query,"mk_pers_nr");
           }
} //3416
