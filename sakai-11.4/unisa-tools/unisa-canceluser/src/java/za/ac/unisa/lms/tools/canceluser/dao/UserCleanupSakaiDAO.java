package za.ac.unisa.lms.tools.canceluser.dao;


import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.SakaiDAO;


public class UserCleanupSakaiDAO extends SakaiDAO {


	public String getUserId(String userEid) throws Exception{
		String userId = "";
		String errorMessage = "";

		String select = "SELECT USER_ID "+
		                "FROM   SAKAI_USER_ID_MAP "+
		                "WHERE  UPPER(EID) = UPPER('"+userEid+"') ";

		try{
			userId = this.querySingleValue(select,"USER_ID");

		} catch (Exception ex) {
			errorMessage = "za.ac.unisa.lms.tools.cronjobs.dao.UserCleanupSakaiDAO: getUserId: / "+ ex;
            //throw new Exception("MyUnisaStatsDAO: getEventStats: Error occurred / "+ ex,ex);
		}

       return userId;
	}
	
	/**
	 * This method executes a query and returns a String value as result.
	 * An empty String value is returned if the query returns no results.
	 *
	 * @param query       The query to be executed on the database
	 * @param field		  The field that is queried on the database
	 * method written by: E Penzhorn
	*/
	private String querySingleValue(String query, String field){

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
    	List queryList;
    	String result = "";

 	   queryList = jdt.queryForList(query);
 	   Iterator i = queryList.iterator();
 	   i = queryList.iterator();
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

