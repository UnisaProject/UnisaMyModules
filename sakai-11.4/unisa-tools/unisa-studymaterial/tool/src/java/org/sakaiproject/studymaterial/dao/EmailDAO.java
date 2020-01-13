package org.sakaiproject.studymaterial.dao;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.db.StudentSystemDAO;

public class EmailDAO extends StudentSystemDAO {
		
	public String getEmailID(String course) throws Exception {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		
				
		String sql ="select g.Eng_description Eng_description  from gencod g, sun s,colleg c where g.code=c.abbreviation and fk_gencatcode = '121'"+ 
			         " and s.college_code=c.code and s.code='"+course+"'";
		String email;
		try{
			email = querySingleValue(sql,"Eng_description");
			} catch (Exception ex) {
			    throw new Exception("MyUnisaContentQueryDAO : getEmailID Error occurred / "+ ex,ex);
			}
			return email;		
	    }
	private String querySingleValue(String query, String field) {

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList;
		String result = "";

		queryList = jdt.queryForList(query);
		Iterator i = queryList.iterator();
		i = queryList.iterator();
		if (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			if (data.get(field) == null) {
			} else {
				result = data.get(field).toString();
			}
		}
		return result;
	}
}
