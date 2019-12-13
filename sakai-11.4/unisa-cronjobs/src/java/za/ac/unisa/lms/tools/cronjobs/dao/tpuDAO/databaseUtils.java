package za.ac.unisa.lms.tools.cronjobs.dao.tpuDAO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.db.StudentSystemDAO;

public class databaseUtils extends StudentSystemDAO {
	
	      public  static final String saCode="1015";
	      public databaseUtils(){
	    	       jdt = new JdbcTemplate(getDataSource());
	      }
	      private JdbcTemplate  jdt ;
	      public String querySingleValue(String query, String field,String errorMsg) throws Exception{
	    	         String result = "";
	    	         try{
                          List queryList=jdt.queryForList(query);
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
	      public String replaceNull(Object object){
				String stringValue="";
				if (object==null){			
				}else{
					stringValue=object.toString();
				}			
				return stringValue;
	       }
	      public String replaceNull(Object object,String replacementStr){
				String stringValue=replacementStr;
				if (object==null){			
				}else{
					stringValue=object.toString();
				}			
				return stringValue;
	       }
			public JdbcTemplate getdbcTemplate(){
				         jdt = new JdbcTemplate(getDataSource());
	                     return jdt;
			}
			
			public String update(String sql,String errorMsg) throws Exception{
				          try{    
			                     jdt.update(sql);
	                      }catch(Exception ex){
	                    	      return errorMsg + ex.getMessage();
	                      }
				          return "";
	       }
			public List queryForList(String sql,String errorMsg)throws Exception{
				             List queryList= new ArrayList();
				             try{
				                 queryList = jdt.queryForList(sql);
                                 if(queryList==null){
                            	     return new ArrayList();
                                 }else{
                                       return queryList;
                                 }
				             }catch(Exception ex){
				            	   return queryList;
			                 }
			}
			
}
