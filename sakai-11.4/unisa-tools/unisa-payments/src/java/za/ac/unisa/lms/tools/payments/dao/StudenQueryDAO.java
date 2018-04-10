package za.ac.unisa.lms.tools.payments.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.payments.forms.Student;

public class StudenQueryDAO extends StudentSystemDAO {
	
	public short getFinGlobalYear() throws Exception{
		short currentyear = 0;
		String sql = "select CURRENT_ACADEMIC_YEAR " +
				" from FINGLO" +
				" WHERE TYPE = 'F'";
		try { 
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(sql);
				Iterator i = queryList.iterator();
				while (i.hasNext()){
					ListOrderedMap data = (ListOrderedMap) i.next();
					currentyear = Short.parseShort(data.get("CURRENT_ACADEMIC_YEAR").toString());
				}
			
		} catch (Exception ex) {
            throw new Exception("StudentQueryDAO: getFinGlobalYear : Error occurred / "+ ex,ex);
		}
	  return currentyear;
	}
	public Student getStudent(String number) throws Exception {
		Student student = new Student();
		String sql = "Select SURNAME, INITIALS, MK_TITLE " +
				"        FROM STU" +
				"       WHERE NR = "+number;
		try { 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			Iterator i = queryList.iterator();
			while (i.hasNext()){
				ListOrderedMap data = (ListOrderedMap) i.next();
				student.setNumber(number);
				student.setName(data.get("SURNAME").toString());
				student.setInitials(data.get("INITIALS").toString());
				student.setTitle(data.get("MK_TITLE").toString());
			}
		
		} catch (Exception ex) {
			throw new Exception("StudentQueryDAO: getStudent : Error occurred / "+ ex,ex);
		}
		return student;
	}
}
