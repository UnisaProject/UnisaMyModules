package za.ac.unisa.lms.tools.maintainstaff.DAO;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.SakaiDAO;

public class MaintainStaffSakaiDAO extends SakaiDAO{
	
	/**
	 * Select user rights (maintain (-1) or access only (1))
	*/
	public String getUserRights(String userId, String userEID) throws Exception {
		String userTmp;
		String userPermission;

		String sql1 = "SELECT PERMISSION "+
			          "FROM   SAKAI_SITE_USER "+
			          "WHERE  SITE_ID = 'unisa-maintainstaff1' "+
			          "AND    (upper(USER_ID) = upper('"+userEID+"') "+
			          "OR     USER_ID = '"+userId+"')";
		//System.out.println("sql1 "+sql1);
		try{
			userTmp = this.querySingleValue(sql1,"PERMISSION");

		} catch (Exception ex) {
			throw new Exception("MAINTAINSTAFFSTUDENTDAO: getUserRights: Error occurred / "+ ex,ex);
		}

		if (userTmp.equals("-1")) {
			userPermission = "MAINTAIN";
		} else {
			userPermission = "ACCESS";
		}

		return userPermission;
	}
	
	
	public String getUserPerms(String userId, String userEID) throws Exception {
		String userTmp;
		String userPermission;

		String sql1 = "SELECT PERMISSION "+
			          "FROM   SAKAI_SITE_USER "+
			          "WHERE  SITE_ID = 'MAINTAIN_HELP' "+
			          "AND    (upper(USER_ID) = upper('"+userEID+"') "+
			          "OR     USER_ID = '"+userId+"')";
		//System.out.println("sql1 "+sql1);
		try{
			userTmp = this.querySingleValue(sql1,"PERMISSION");

		} catch (Exception ex) {
			throw new Exception("MAINTAINSTAFFSTUDENTDAO: getUserPerms: Error occurred / "+ ex,ex);
		}
		
		if(null==userTmp || userTmp.length()==0){
			userPermission = "";
		}
		else {
			userPermission = "MAINTAIN";
		} 
		return userPermission;
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
