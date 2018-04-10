package za.ac.unisa.lms.tools.uploadmanager.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class StudyMaterialDAO extends StudentSystemDAO{
	
	   
	   public ArrayList getModuleCodes(String usertId)
	    {
		   String novel_user = usertId.toUpperCase();
	                       String sql= "select DISTINCT MK_STUDY_UNIT_CODE from usrsun where NOVELL_USER_ID="+"'"+novel_user+"'"+
	                       		       " order by MK_STUDY_UNIT_CODE";
	                       ArrayList results = new ArrayList();
	                       try{
	                       JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	                       List queryList;
	                       
	                       queryList = jdt.queryForList(sql);
	                       Iterator i = queryList .iterator();
	                       while(i.hasNext()) {
	                           ListOrderedMap data = (ListOrderedMap) i.next();
	                           String modCode= data.get("MK_STUDY_UNIT_CODE").toString();
	                                         
	                           results.add(new org.apache.struts.util.LabelValueBean(modCode,modCode));
	                                               
	                       }
	                       }catch(Exception  ex){}
	         return results;
	   }
	   
	   
	 	public boolean isLecturer(String userid, String modCode) throws Exception{
			
			String query = "SELECT SYSTEM_TYPE FROM USRSUN WHERE  NOVELL_USER_ID="+"'"+userid.toUpperCase()+"'"+
					       "AND  MK_STUDY_UNIT_CODE="+"'"+modCode+"'";

                      
                     	try {
                     		 String results = this.querySingleValue(query,"SYSTEM_TYPE");

        	        		if(results.equals("L")){
        	        			return true;
        	        		}	else {
        	        			return false;
        	        		}
        	        		
        	        	} catch (Exception ex) {
        	        		throw new Exception(" /isLecturer "+ ex,ex);
        	        		
        	        	}
            
	    }
	 public List getModuleCodes1()
	    {
		  List<String> results;
	                       String sql= "select code from sun ";
	                       results =  new ArrayList<String>();
	                       JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	                       List queryList;
	                       queryList = jdt.queryForList(sql);
	                       Iterator i = queryList .iterator();
	                       while(i.hasNext()) {
	                           ListOrderedMap data = (ListOrderedMap) i.next();
	                           String modCode= data.get("code").toString();
	                                         
	                           results.add(modCode);
	                                               
	                       }
	         return results;
	   }
	
	  public List<String> getData(String query) {
		  
		  List<String> result = getModuleCodes1();
		  int size = result.size();
	        String country = null;
	        query = query.toLowerCase();
	        List<String> matched = new ArrayList<String>();
	        for(int i=0; i<size; i++) {
	            country = result.get(i).toLowerCase();
	            if(country.startsWith(query)) {
	                matched.add(result.get(i));
	            }
	        }
	        return matched;
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
	
}
