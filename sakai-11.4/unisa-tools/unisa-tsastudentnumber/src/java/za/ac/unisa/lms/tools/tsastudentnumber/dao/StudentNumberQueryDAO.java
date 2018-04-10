package za.ac.unisa.lms.tools.tsastudentnumber.dao;


import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.db.StudentSystemDAO;

public class StudentNumberQueryDAO extends StudentSystemDAO {

	public String getUnisaStudentNumber(String tsaStudentNr){

		String result = "";

		String sql = "select mk_student_nr from sturef r where r.OLD_STUDENT_NR =" + tsaStudentNr + " and r.OLD_INSTITUTION=4";
		result = this.querySingleValue(sql,"MK_STUDENT_NR");

		return result;
	}

	/**
	 * This method executes a query and returns a String value as result.
	 * An empty String value is returned if the query returns no results.
	 *
	 * @param query       The query to be executed on the database
	 * @param field		  The field that is queried on the database
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

