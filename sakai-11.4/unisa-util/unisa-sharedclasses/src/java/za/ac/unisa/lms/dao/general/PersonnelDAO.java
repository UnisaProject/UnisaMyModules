package za.ac.unisa.lms.dao.general;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.domain.general.Person;

public class PersonnelDAO extends StudentSystemDAO{
	public Person getPerson(Integer persno) throws Exception{
		Person person = new Person();
		person = getPersonnelFromSTAFF(persno);
		if (person.getPersonnelNumber()==null){
			person = getPersonnelfromUSR(String.valueOf(persno));
		}		
		return person;
	}
	
	public Person getPersonnelFromSTAFF(Integer persno)throws Exception {
		Person person = new Person();
		
		String sql = "select persno,surname,initials,title,email_address,contact_telno,novell_user_id,mk_dept_code,to_char(RESIGN_DATE,'YYYYMMDD') as resignDate, mk_rc_code" + 
        " from staff" +
        " where persno =" + persno; 
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			person.setPersonnelNumber(replaceNull(data.get("PERSNO")));
			String name = replaceNull(data.get("TITLE"));
			name = name.trim() + " " + replaceNull(data.get("INITIALS"));
			name = name.trim() + " " + replaceNull(data.get("SURNAME"));
			person.setName(name);
			String nameReverse = replaceNull(data.get("SURNAME"));
			nameReverse = nameReverse.trim() + " " + replaceNull(data.get("INITIALS"));
			nameReverse = nameReverse.trim() + " " + replaceNull(data.get("TITLE"));
			person.setNameReverse(nameReverse);
			person.setEmailAddress(replaceNull(data.get("EMAIL_ADDRESS")));
			person.setNovellUserId(replaceNull(data.get("NOVELL_USER_ID")));
			person.setContactNumber(replaceNull(data.get("CONTACT_TELNO")));
			person.setDepartmentCode(replaceNull(data.get("MK_DEPT_CODE")));
			person.setResignDate(replaceNull(data.get("resignDate")));
			person.setRcCode(replaceNull(data.get("mk_rc_code")));
		}
	}
	catch (Exception ex) {
		throw new Exception("PersonnelDAO : Error reading STAFF / " + ex,ex);
	}		
	return person;		
	}
	
	public Person getPersonnelfromUSR(String persno) throws Exception{
		Person person = new Person();
			
		String sql = "select personnel_no,name,e_mail,phone_number,novell_user_code,mk_department_code" + 
        " from usr" +
        " where personnel_no ='" + persno +"'"; 
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			person.setPersonnelNumber(replaceNull(data.get("PERSONNEL_NO")));
			person.setName(replaceNull(data.get("NAME")));
			person.setNameReverse(replaceNull(data.get("NAME")));
			person.setEmailAddress(replaceNull(data.get("E_MAIL")));
			person.setNovellUserId(replaceNull(data.get("NOVELL_USER_CODE")));
			person.setContactNumber(replaceNull(data.get("PHONE_NUMBER")));	
			person.setDepartmentCode(replaceNull(data.get("MK_DEPARTMENT_CODE")));
			person.setResignDate("");
		}
	}
	catch (Exception ex) {
		throw new Exception("PersonnelDAO : Error reading USR / " + ex,ex);
	}		
	return person;		
	}
	
	private String replaceNull(Object object){
		String stringValue="";
		if (object==null){			
		}else{
			stringValue=object.toString();
		}
			
		return stringValue;
	}
}
