package za.ac.unisa.lms.tools.tpustudentplacement.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	             public String replaceNull(Object object){
				String stringValue="";
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
			
			public void update(String sql,String errorMsg) throws Exception{
				          try{    
			                 jdt = new JdbcTemplate(getDataSource());
                              jdt.update(sql);
	                      }catch(Exception ex){
	                    	  throw new Exception(errorMsg + ex,ex);
	                      }
	       }
			public List queryForList(String sql,String errorMsg)throws Exception{
				             List queryList= new ArrayList();
				             try{
				                  jdt = new JdbcTemplate(getDataSource());
                                  queryList = jdt.queryForList(sql);
                                  if(queryList==null){
                                	  queryList= new ArrayList();
                                  }
				             }catch(Exception ex){
			                    	throw new Exception(errorMsg + ex,ex);
			                 }
				             return queryList;
			}
			public int queryInt(String sql,String errorMsg)throws Exception{
				         int totRows=0;
	                     try{
	                          jdt = new JdbcTemplate(getDataSource());
	                           totRows = jdt.queryForInt(sql);
                           
	                      }catch(Exception ex){
                   	                throw new Exception(errorMsg + ex,ex);
                         }
	                     return totRows;
            }
			
			
	

}
