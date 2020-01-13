package za.ac.unisa.lms.tools.creditcardpayment.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.creditcardpayment.forms.Student;

public class StudentQueryDAO extends StudentSystemDAO {
	
	public String getInitPassword(String stuno){
	       String query="Select  password from Idvalt where mk_student_nr = "+stuno;
	       String password=querySingleValue(query,"password");
	       return password;
	}
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
				student.setStudentNumber(number);
				student.setName(data.get("SURNAME").toString());
				student.setInitials(data.get("INITIALS").toString());
				student.setTitle(data.get("MK_TITLE").toString());
			}
		
		} catch (Exception ex) {
			throw new Exception("StudentQueryDAO: getStudent : Error occurred / "+ ex,ex);
		}
		return student;
	}
	
	public String getSmartCardValue(String studentNr){
		String result = "";
		String query = "SELECT smart_card_issued from stu where nr = " + studentNr;
		result = querySingleValue(query,"SMART_CARD_ISSUED");
		
		return result;
	}
	
	public String getApplicationCost(){
		//todo year, how determine?????
		String result = "";
		String query = "select application_cost from akd where year=";
		result = querySingleValue(query,"APPLICATION_COST");
		
		return result;
	}
	
	public void cancelSmartCard(String studentNr){
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				
		String sql = "update stu set smart_card_issued='N' where nr="+ studentNr;
		jdt.update(sql);
			
	}
	
public void requestSmartCard(String studentNr){
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				
		String sql = "update stu set smart_card_issued='W' where nr="+ studentNr;
		jdt.update(sql);
			
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
			result = data.get(field).toString();
		}
		return result;
	}
}
