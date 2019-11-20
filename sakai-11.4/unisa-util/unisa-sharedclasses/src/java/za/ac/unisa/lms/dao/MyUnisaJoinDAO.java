package za.ac.unisa.lms.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.SakaiDAO;

/** Class for all join and activation related methods. */
public class MyUnisaJoinDAO extends SakaiDAO{

	/**
	 * Method: studentStatusFlag
	 *  To select student status, OT = joined, NC=not joined/completed
	 *  BL = blocked
	 *
	 * input: studentNr,
	 * output: student status
	 * @throws Exception
	 */
	public String studentStatusFlag(String studentNr) throws Exception{

		String studentStatus = "";
		String sql = "SELECT upper(STATUS_FLAG) AS STATUS "+
		             "FROM   JOIN_ACTIVATION "+
		             "WHERE  USER_ID = "+studentNr+" ";
		System.out.println("studentStatusFlag "+sql);

		try{
			studentStatus = this.querySingleValue(sql,"STATUS");

		} catch (Exception ex) {
            throw new Exception("MyUnisaSakaiDAO: mayChangePassword: Error occurred / "+ ex,ex);
		}

       return studentStatus;
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
