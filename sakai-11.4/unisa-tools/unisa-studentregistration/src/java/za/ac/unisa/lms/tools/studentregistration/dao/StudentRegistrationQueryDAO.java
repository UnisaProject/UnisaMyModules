package za.ac.unisa.lms.tools.studentregistration.dao;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.studentregistration.forms.Student;

public class StudentRegistrationQueryDAO extends StudentSystemDAO {

	public static Log log = LogFactory.getLog(StudentRegistrationQueryDAO.class);


	/**
	 * This method reads the student according to input and
	 * returns the first student in the list of matched students
	 * otherwise null
	 *
	 * @param Student       The Student object
	 * @param String		Registration Type (U or P or S)
	 */
	public Student getStudentNumberSearch(Student student, String registrationType) throws Exception{
		Student stu = null;
		String extra ="";

		// For Short learning programmes only display student nr's with
		// length 8 and starting with a 7
		if("S".equalsIgnoreCase(registrationType)){
			extra = " and nr > 70000000 and nr <= 79999999";
		}

		String sql = "select nr, first_names, surname, to_char(birth_date,'YYYYMMDD') as birthdate" +
					   " from stu " +
					   " where first_names = '"+ student.getFirstnames().toUpperCase()+
					   "' and surname = '"+ student.getSurname().toUpperCase()+
					   "' and birth_date = to_date('"+ student.getBirthYear()+student.getBirthMonth()+student.getBirthDay()+"','YYYYMMDD')"
					   + extra;

		log.debug(sql);

		try{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				if(!"S".equalsIgnoreCase(registrationType)){
					if (data.get("NR").toString().length() == 8 && "7".equalsIgnoreCase(data.get("NR").toString().substring(0,1))){
						// do not display short learning course students
					}else{
						stu = new Student();
						stu.setNumber(data.get("NR").toString());
						stu.setFirstnames(data.get("FIRST_NAMES").toString());
						stu.setSurname(data.get("SURNAME").toString());
						stu.setBirthYear(data.get("BIRTHDATE").toString().substring(0,4));
						stu.setBirthMonth(data.get("BIRTHDATE").toString().substring(4,6));
						stu.setBirthDay(data.get("BIRTHDATE").toString().substring(6,8));
					}
				}
			}
		} catch (Exception ex) {
			throw new Exception("StudentRegistrationQueryDao : Error validating student / "+ ex,ex);
		}

		return stu;
	}

	/**
	 * This method executes a query and returns a String value as result.
	 * An empty String value is returned if the query returns no results.
	 *
	 * @param query     The query to be executed on the database
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
			if (data.get(field)!= null){
				result = data.get(field).toString();
			}else{
				result = "";
			}
		}

		return result;
	}
}
