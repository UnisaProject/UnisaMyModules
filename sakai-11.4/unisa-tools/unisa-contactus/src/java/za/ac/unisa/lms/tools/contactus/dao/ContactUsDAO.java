package za.ac.unisa.lms.tools.contactus.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.apache.commons.collections.map.ListOrderedMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.contactus.forms.ContactUsForm;

import org.apache.commons.logging.Log;

public class ContactUsDAO extends StudentSystemDAO{
	private Log log;

	public Vector retrieveStudentNumber(String name, String surname, String dob, ContactUsForm contactUsForm) throws Exception {
		log = LogFactory.getLog(this.getClass());
		Vector list = new Vector();

		String query = "SELECT stu.nr NR FROM stu WHERE stu.first_names ='" + name.toUpperCase() + "'" +
			" AND stu.surname ='" + surname.toUpperCase() + "' AND stu.birth_date= to_date('" + dob + "','YYYYMMDD')";

		System.out.println(query);
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			log.debug("CONTACT US: DETERMINING THE STUDENT NUMBER QUERY - " + query);
		  // int test =jdt.queryForInt(query);
		  // list.add(test);


			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();
			while(i.hasNext()) {
				//HashMap data = (HashMap) i.next();
				ListOrderedMap data = (ListOrderedMap) i.next();
				list.add(data.get("NR").toString());
				System.out.println(data.get("NR").toString());
			}

		}catch (Exception ex) {
			throw new Exception("ContactUs DAO : Error "+ ex);
		}

		return list;
	}

	public boolean doesStudentExist(String studentNumber) throws Exception {
		//using surname just to check if student exists.
		String query = "select stu.surname from stu where stu.nr=" +  studentNumber;
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		log = LogFactory.getLog(this.getClass());
		log.debug("CONTACT US: DOES THE STUDENT EXIST QUERY - " + query);
		List list = jdt.queryForList(query);
		if(!list.isEmpty()) {
			return true;
		}

		return false;
	}

	public boolean registered(String studentNumber) throws Exception {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(d);

		String query = "select * from stuann a" +
			" where a.mk_academic_year= " + year +
			" and a.mk_academic_period = 1" +
			" and a.mk_student_nr = " + studentNumber +
			" and a.STATUS_CODE in ('RG','CA')";

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		log = LogFactory.getLog(this.getClass());
		log.debug("CONTACT US: IS THE STUDENT REGISTERED QUERY - " + query);
		List list = jdt.queryForList(query);
		if(!list.isEmpty()) {
			return true;
		}
		return false;
	}

	public ArrayList getDegree(String studentNumber) throws Exception {
		ArrayList degree = new ArrayList();
		String query = "select grd.eng_description, stusun.mk_qualification_c " +
						"from grd, stusun " +
						"where stusun.fk_student_nr=" + studentNumber + " and " +
						"grd.code=mk_qualification_c";

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			log = LogFactory.getLog(this.getClass());
			log.debug("CONTACT US: GET THE STUDENTS DEGREE CODE and DESCRIPTION QUERY - " + query);
			List list = jdt.queryForList(query);
			Iterator i = list.iterator();
			while(i.hasNext()) {
				HashMap data = (HashMap) i.next();
				Qualification qualification = new Qualification();
				qualification.setQualificationDescription(data.get("ENG_DESCRIPTION").toString());
				qualification.setQualificationCode(data.get("MK_QUALIFICATION_C").toString());

				degree.add(qualification);
			}

		}catch(Exception e) {
			System.out.println("Exception in DAO: " + e.getMessage());
		}
		return degree;
	}

	public String getCountryCode(String studentNumber) throws Exception {
		String countryCode="";
		String query = "select mk_country_code from stu where stu.nr=" + studentNumber;
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		log = LogFactory.getLog(this.getClass());
		log.debug("CONTACT US: DETERMINE COUNTRY CODE OF STUDENT - " + query);
		List list = jdt.queryForList(query);
		Iterator i = list.iterator();
		while(i.hasNext()) {
			HashMap data = (HashMap) i.next();
			countryCode = data.get("MK_COUNTRY_CODE").toString();
		}

		return countryCode;
	}
}
