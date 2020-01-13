package za.ac.unisa.lms.tools.studentnosearch.dao;


import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.db.StudentSystemDAO;

public class StudentNoSearchQueryDAO extends StudentSystemDAO {

	public String getUnisaStudentNumber(String surname, String firstNames, String birthDate, String id, String passp){

		String result = "";

		surname=surname.replaceAll("'", "''");
		firstNames=firstNames.replaceAll("'", "''");
		
		String sql = "select nr from stu where surname ='" + surname.toUpperCase() + 
		"' and first_names='"+ firstNames.toUpperCase()+
		"' and birth_date = '"+ birthDate+"'";
		if(!"".equals(id)){
			sql = sql + "and identity_nr = '" + id +"' ";
		}else{
			if (!"".equals(passp)){
				sql = sql + "and passport_no = '" + passp +"' ";
			}
		}

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);

		Iterator i = queryList.iterator();
		int counter = 0;
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			String studNo = data.get("NR").toString();
//			if("3".equals(studNo.substring(0,1)) || "4".equals(studNo.substring(0,1)) || "5".equals(studNo.substring(0,1))){
			if((studNo.length()==8 && !"7".equals(studNo.substring(0,1))) || (studNo.length()==7)){
			  counter = counter +1;
			  if(counter >1){
				result = "more";
			  }else{
				result = data.get("NR").toString();
			  }
			}


		}

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

