package za.ac.unisa.lms.tools.sitestatsexport.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.SakaiDAO;

public class SiteStatesDAO extends SakaiDAO{

	 private Map collegeMap;
	 private Map collegeMap1;
	    
	   public SiteStatesDAO()
	    { 
	    	collegeMap = new HashMap<String,String>();
	   	    collegeMap1=Collections.synchronizedMap(new TreeMap());}
	
	
		/*
		 *  gets the events.
		 */
		public ArrayList getEvents()
	    {
	                       String sql = " select distinct event_id as EVENT from sst_events order by EVENT";
	                      ArrayList results = new ArrayList();
	                       
	                       JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	                       List queryList;
	                       queryList = jdt.queryForList(sql);
	                       Iterator i = queryList .iterator();
	                       while(i.hasNext()) {
	                           ListOrderedMap data = (ListOrderedMap) i.next();
	                        
	                           String event   = data.get("EVENT").toString();
	                          
	                           results.add(new org.apache.struts.util.LabelValueBean(event,event));
	                          // results.addElement(event);
	                           //collegeMap.put(code, college);                            
	                       }
	         return results;
	   }
	
}
