package za.ac.unisa.lms.tools.telecentre.dao;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
//import org.sakaiproject.db.cover.SqlService;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.db.StudentSystemDAO;
public class stuDAO  extends StudentSystemDAO{
	 private String getTitle(String studentNum){
		   String query ="select mk_title from stu where nr ="+studentNum;
		   return  querySingleValue(query,"mk_title");
	 }
	 private String getSurname(String studentNum){
		   String query ="select surname from stu where nr ="+studentNum;
		   return  querySingleValue(query,"surname");
	 }
	 private String getFirstNames(String studentNum){
		   String query ="select initials from stu where nr ="+studentNum;
		   return  querySingleValue(query,"initials");
	 }
	 public String stuDetails(String studentNum){
		          return getTitle(studentNum)+" "+getFirstNames(studentNum)+" "+getSurname(studentNum);
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
