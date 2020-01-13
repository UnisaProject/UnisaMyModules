package za.ac.unisa.lms.tools.examtimetable.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.db.StudentSystemDAO;

public class DatabaseUtils extends StudentSystemDAO { 

	private JdbcTemplate  jdt ;

      public String querySingleValue(String query, String field,String errorMsg) throws Exception{
    	         String result = "";
    	         try{
                      jdt = new JdbcTemplate(getDataSource());
                      List queryList;
                  
                      queryList = jdt.queryForList(query);
                  Iterator i = queryList.iterator();
                  if (i.hasNext()) {
                        ListOrderedMap data = (ListOrderedMap) i.next();
                        if (data.get(field) == null){
                        } else {
                            result = data.get(field).toString();
                        }
                  }
    	        }catch (Exception ex) {
    	        	throw new Exception(errorMsg + ex,ex);
      		}
                  return result;
      }
}