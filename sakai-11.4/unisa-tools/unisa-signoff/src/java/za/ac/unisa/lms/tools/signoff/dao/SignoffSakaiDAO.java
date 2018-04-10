package za.ac.unisa.lms.tools.signoff.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.SakaiDAO;

public class SignoffSakaiDAO extends SakaiDAO{

	
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
		System.out.println("sql1 "+sql1);

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
