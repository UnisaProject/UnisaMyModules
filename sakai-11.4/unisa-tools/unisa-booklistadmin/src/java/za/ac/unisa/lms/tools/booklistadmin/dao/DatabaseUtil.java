package za.ac.unisa.lms.tools.booklistadmin.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.RowMapper;
import za.ac.unisa.lms.db.StudentSystemDAO;

public class DatabaseUtil extends StudentSystemDAO {
	      
	      
	      public  PreparedStatementCreatorFactory  getPrepStmtCreatorFactory(String sql,int[] types){
	    	                               PreparedStatementCreatorFactory pstsmtCreatorFactory;
	    	                               pstsmtCreatorFactory=  new PreparedStatementCreatorFactory(sql,types);
	    					    		   return  pstsmtCreatorFactory;
	      }
	      public  PreparedStatementCreator   getPrepStmtCreator(String sql,int[] types,Object[] params){
	    	                            PreparedStatementCreatorFactory pstsmtCreatorFactory=getPrepStmtCreatorFactory(sql,types);;
                                        PreparedStatementCreator pstsmtCreator=pstsmtCreatorFactory.newPreparedStatementCreator(sql, params);
                                        return pstsmtCreator;
	      }
	      public String querySingleValue(String query, String field){
	    	         String result = "";
	    	             	  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
                             List queryList= jdt.queryForList(query); 
                             Iterator i = queryList.iterator();
                             if(queryList==null){
                            	 return result;
                             }else{
                                     if (i.hasNext()) {
                                           ListOrderedMap data = (ListOrderedMap) i.next();
                                           result = data.get(field).toString();
                                     }
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
		   public void update(String sql) {
				         JdbcTemplate jdt = new JdbcTemplate(getDataSource());
                                 jdt.update(sql);
                          
	       }
		   public void update(PreparedStatementCreator stmt) {
		         JdbcTemplate jdt = new JdbcTemplate(getDataSource());
                       jdt.update(stmt);
                
           }
		   public List queryForList(String sql){
				             List queryList= new ArrayList();
				              	 JdbcTemplate jdt = new JdbcTemplate(getDataSource());
                                  queryList = jdt.queryForList(sql);
                                  if(queryList==null){
                                	    queryList= new ArrayList();
                                  }
				             return queryList;
		  }
		   public List query(String sql,Object[] values, int[] types,RowMapper rowMapper){
	                 List queryList= new ArrayList();
	              	 JdbcTemplate jdt = new JdbcTemplate(getDataSource());
                    queryList = jdt.query(sql,values, types,rowMapper);
                    if(queryList==null){
                  	    queryList= new ArrayList();
                    }
	             return queryList;
           }
		   public  void update(String sql,int[] types,Object[] params){
	                      JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	                      PreparedStatementCreator prpdStmtCreator=getPrepStmtCreator(sql, types,params);
                          jdt.update(prpdStmtCreator);
	       }
		   public  Object query(String sql,Class requiredType){
	             Object queryObj=new Object();
	             JdbcTemplate jdt = new JdbcTemplate(getDataSource());
                          queryObj= jdt.queryForObject(sql,requiredType);
	                    return queryObj;
          }
		  public void execute(String sql){
	               JdbcTemplate jdt = new JdbcTemplate(getDataSource());
                         jdt.execute(sql);
	      }
  
		  public int queryInt(String sql){
				         int totRows=0;
	                    	   JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	                           totRows = jdt.queryForInt(sql);
                         return totRows;
            }
		  public String makeJDBCCompatible(String convert) {
		        String converted = null;
		        if( convert==null){
		        	 convert="";
		        }
		         if (convert.lastIndexOf("'") > -1) {
		            converted = convert.replaceAll("'","''");
		        }
		        else 
		            converted = convert;
		            
		        return converted;
		    }
}