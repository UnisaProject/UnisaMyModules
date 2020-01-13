package za.ac.unisa.lms.tools.registrationstatus.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.registrationstatus.forms.FlagDocument;
import za.ac.unisa.lms.tools.registrationstatus.forms.GeneralItem;
import za.ac.unisa.lms.tools.registrationstatus.forms.Qualification;
import za.ac.unisa.lms.tools.registrationstatus.forms.RegistrationStatusForm;
import za.ac.unisa.lms.tools.registrationstatus.forms.Student;
import za.ac.unisa.lms.tools.registrationstatus.forms.StudyUnit;

public class RegistrationStatusQueryDAO extends StudentSystemDAO {

	public static Log log = LogFactory.getLog(RegistrationStatusQueryDAO.class);


	/**
	 * This method reads and returns the student according to specified student nr.
	 * If found return true else return false
	 *
	 * @param studentNr       The Student number
	 */
	public Student getStudent(String studentNr) throws Exception{


		Student stuFromDb = new Student();

		String sql = "select nr, first_names, surname, to_char(birth_date,'YYYYMMDD') as birthdate" +
					   " from stu " +
					   " where nr = ? ";


		log.debug(sql);

		try{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql, new Object[] { studentNr });
			Iterator i = queryList.iterator();
			
			if (i.hasNext()) {
				// student found
				ListOrderedMap data = (ListOrderedMap) i.next();
				stuFromDb = new Student();
				stuFromDb.setNumber(data.get("NR").toString());
				stuFromDb.setFirstnames(data.get("FIRST_NAMES").toString());
				stuFromDb.setSurname(data.get("SURNAME").toString());
				stuFromDb.setBirthYear(data.get("BIRTHDATE").toString().substring(0,4));
				stuFromDb.setBirthMonth(data.get("BIRTHDATE").toString().substring(4,6));
				stuFromDb.setBirthDay(data.get("BIRTHDATE").toString().substring(6,8));

			}else{
				// student not found
				stuFromDb = null;
			}
		} catch (Exception ex) {
			throw new Exception("RegistrationStatusQueryDao : Error reading student / "+ ex,ex);
		}

		return stuFromDb;
	}
	

	/**
	 * This method reads and returns the student according to specified student nr.
	 * If found return true else return false
	 *
	 * @param studentNr       The Student number
	 */
	public boolean auditTrailExists(String studentNr) throws Exception{

		boolean result = false;

		String sqlNormal = "select * from stuchg s, regdat r  " +
				"where s.MK_STUDENT_NR =" + studentNr+
				"and s.MK_USER_CODE=9999 "+
				" and trunc(s.TIMESTAMP) between r.FROM_DATE and r.TO_DATE"+
				" and s.CHANGE_CODE='IR' and s.VALUE_CHANGED='complete'"+
				" and r.ACADEMIC_YEAR="+getCurrentYear()+
				" and r.TYPE='IR'";
		String sqlSelfhelpWorkflow = "select s.* from stuchg s, regdat r" +
				" where s.MK_STUDENT_NR ="+ studentNr+
				" and s.MK_USER_CODE=99998" +
				" and trunc(s.TIMESTAMP) between r.FROM_DATE and r.TO_DATE" +
				" and s.CHANGE_CODE='IR' and s.VALUE_CHANGED='SHworkflow'" +
				" and r.ACADEMIC_YEAR="+getCurrentYear()+
				" and r.TYPE='IR'";
		String sqlSelfhelpSuccess = "select * from stuchg s, regdat r " +
				" where s.MK_STUDENT_NR ="+ studentNr+
				" and s.MK_USER_CODE=99998" +
				" and trunc(s.TIMESTAMP) between r.FROM_DATE and r.TO_DATE" +
				" and s.CHANGE_CODE='TS'"+
				" and r.ACADEMIC_YEAR="+getCurrentYear()+
				" and r.TYPE='IR'";

		try{
			// check normal web reg workflow
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			log.debug(sqlNormal);
			List queryList = jdt.queryForList(sqlNormal);
			
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				return true;
			}
			// check Self-help Workflow
			jdt = new JdbcTemplate(getDataSource());
			log.debug(sqlSelfhelpWorkflow);
			queryList = jdt.queryForList(sqlSelfhelpWorkflow);

			i = queryList.iterator();
			if (i.hasNext()) {
				return true;
			}
			
			// check selfhelp-success
			jdt = new JdbcTemplate(getDataSource());
			log.debug(sqlSelfhelpSuccess);
			queryList = jdt.queryForList(sqlSelfhelpSuccess);

			i = queryList.iterator();
			if (i.hasNext()) {
				return true;
			}
		} catch (Exception ex) {
			throw new Exception("RegistrationStatusQueryDao : Error reading audit trail / "+ ex,ex);
		}
		

		return result;
	}


	public ArrayList getApplyForStudyUnits(String studentNr) throws Exception{

		// Get applied for registration study units for student
		ArrayList list = new ArrayList();

		/*
		 * Query:
		 * 1.Read all stusun(TP) and solsun study units for the student,
		 *  take only the distinct study units between them.
		 * 2. Take those study units and read their stusun and
		 *  solsun records
		 *
		 * If a stusun and solsun record exists only show the stusun record.
		 */
		String sql = " select distinct(suncode),lang, u.ENG_SHORT_DESCRIPT, s.*, TO_CHAR(s.REGISTRATION_DATE ,'DD Month YYYY') as indate1, o.semester_period solsun_semester_period, TO_CHAR(o.CHANGE_TIMESTAMP,'DD Month YYYY, HH24:MI') as indate2 from ("+
			   			" (select l.MK_STUDY_UNIT_CODE as suncode, mk_language_code as lang"+
			   			" from solsun l "+
			   			" where l.MK_STUDENT_NR ="+studentNr +")"+
			   			" union"+
			   			" (select t.MK_STUDY_UNIT_CODE as suncode, language_code as lang"+
			   			" from stusun t, stuann a "+
			   			" where a.MK_STUDENT_NR="+studentNr +
			   			" and a.mk_academic_year="+getCurrentYear()+
			   			" and a.mk_academic_period = 1"+
			   			" and a.mk_student_nr = t.FK_STUDENT_NR"+
			   			" and t.STATUS_CODE ='TP')"+
			   			" )"+
		              " , sun u"+
		              " , stusun s"+
		              " , solsun o"+
		              " where u.CODE=suncode"+
		              " and s.fK_STUDENT_NR (+) ="+studentNr +
		              " and s.MK_STUDY_UNIT_CODE (+)=suncode"+
		              " and o.MK_STUDENT_NR (+) ="+studentNr +
		              " and o.MK_STUDY_UNIT_CODE(+)=suncode";
		log.debug("getApplyForStudyUnits");
		log.debug(sql);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				StudyUnit studyUnit = new StudyUnit();
				studyUnit.setCode(data.get("SUNCODE").toString());
				studyUnit.setDesc(data.get("ENG_SHORT_DESCRIPT").toString());
				if (data.get("MK_STUDY_UNIT_CODE") != null && data.get("STATUS_CODE") != null && "TP".equalsIgnoreCase(data.get("STATUS_CODE").toString()) ){
					//if stusun and solsun exist, rather display stusun
					studyUnit.setRegPeriod(data.get("SEMESTER_PERIOD").toString());
					studyUnit.setLanguage(data.get("LANG").toString());
					studyUnit.setSupplCode(data.get("SUPPLEMENTARY_EXAM").toString());
					studyUnit.setStatus(data.get("INDATE1").toString());
					String status = "";
					status = getStudentAnnualStatus(studentNr);
					if ("TN".equalsIgnoreCase(status)){
						/* Student NOT registered and have TP study units -
						 * waiting for money or documentation
						 */
						studyUnit.setStatusIndicator("TN");
					}else{
						/* Student is registered, but have TP study units probably
						 * because it was an addition
						 */
						studyUnit.setStatusIndicator("TP");
					}
				}else if (data.get("SOLSUN_SEMESTER_PERIOD")!= null && data.get("STATUS_CODE") == null ){
					/*
					 * if solsun exists, web registration application have not
					 * been processed
					 */
					studyUnit.setRegPeriod(data.get("SOLSUN_SEMESTER_PERIOD").toString());
					studyUnit.setLanguage(data.get("LANG").toString());
					studyUnit.setStatus(data.get("INDATE2").toString());
					studyUnit.setStatusIndicator("A");
				}else{
					// Dont show study unit
					studyUnit = null;
				}
				if (studyUnit != null)
					list.add(studyUnit);
			}

		return list;
	}

	public ArrayList getDocsOutstanding(String studentNr){


		ArrayList flagDocuments = new ArrayList();

		/* read outstanding docs */
		String sql = "select f.CODE as CODE from stadoc s, stuflg f where s.FK_STUNR=" +studentNr
					+ " and f.CODE = s.FK_STUFLGCODE and f.DOCUMENT_TYPE='3'"
					+ " and f.FLAG_TYPE='D' order by s.FK_STUFLGCODE asc";

		log.debug(sql);

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);

		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			FlagDocument doc = new FlagDocument();
			// Set flagcode for document
			doc.setDocCode(data.get("CODE").toString());

			// get document lines for specific flag
			String docSql = "select TEXT from doklyn d where d.FK_DOKCODE = '"+ doc.getDocCode()+
						 "' and d.FK_DOKMK_LANGUAGE='E' and d.FK_DOKWIDTH_TYPE='L' " +
						 " and d.FK_DOKCONTENT='W' order by d.SEQUENCE_NUMBER";

			log.debug(docSql);

			JdbcTemplate jdt2 = new JdbcTemplate(getDataSource());
			List queryList2 = jdt.queryForList(docSql);
			Iterator s = queryList2.iterator();
			ArrayList docLines = new ArrayList();
			while (s.hasNext()) {
				ListOrderedMap data2 = (ListOrderedMap) s.next();
				docLines.add(data2.get("TEXT").toString());
			}

			// add document with lines to return array
			doc.setDocLines(docLines);
			flagDocuments.add(doc);

		}

		return flagDocuments;
	}



	public String getStudentAnnualStatus(RegistrationStatusForm regStatusForm){

		/* check stuann only */
		String sql = "select status_code, TO_CHAR(REGISTRATION_DATE ,'DD Month YYYY') as regdate from stuann a" +
		" where a.mk_academic_year="+getCurrentYear()+
		" and a.mk_academic_period = 1"+
		" and a.mk_student_nr = " +regStatusForm.getStudent().getNumber();

		log.debug(sql);

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);

		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			if (data.get("REGDATE")!= null){
				regStatusForm.setRegistrationDate(data.get("REGDATE").toString());
			}
			return data.get("STATUS_CODE").toString();
		}

		return "";
	}
	
	public String getStudentAnnualStatus(String studentNr){

		/* check stuann only */
		String sql = "select status_code from stuann a" +
		" where a.mk_academic_year="+getCurrentYear()+
		" and a.mk_academic_period = 1"+
		" and a.mk_student_nr = " +studentNr;

		log.debug(sql);

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);

		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			return data.get("STATUS_CODE").toString();
		}

		return "";
	}
	/**
	 * This method returns the student's country
	 *
	 * @param StudentNr
	 *            The student number
	 */
	public String getCountry(String studentNr) throws Exception {
		// Return country code
		String country = "";

		String query = "select mk_country_code " + " from stu "
				+ " where nr = '" + studentNr + "'";
		try {
			country = this.querySingleValue(query, "MK_COUNTRY_CODE");

		} catch (Exception ex) {
			throw new Exception(
					"AdditionsQueryDao : Error reading country code/ " + ex, ex);
		}

		return country.trim();
	}
	
	public String getCountryCodeFromDesc(String countryDesc) throws Exception {
		// Return country code
		String country = "";

		String query = "select code " + " from lns "
				+ " where eng_description = '" + countryDesc + "'";
		try {
			country = this.querySingleValue(query, "CODE").trim();

		} catch (Exception ex) {
			throw new Exception(
					"AdditionsQueryDao : Error reading country code from description " + ex, ex);
		}

		return country.trim();
	}

	public Qualification getRegistrationQualification(String studentNr)
		throws Exception {
		
		// Get student qual code and description
		Qualification qual = new Qualification();

		String sql1 = "select s.MK_HIGHEST_QUALIFI,g.ENG_DESCRIPTION,"
				+ "g.INSTITUTION_CODE,a.FK_SPECIALITY_CODE,"
				+ "q.ENGLISH_DESCRIPTIO,g.under_post_categor"
				+ " from stuann s, grd g, stuaca a , quaspc q "
				+ " where s.MK_ACADEMIC_YEAR = " + getCurrentYear()
				+ " and s.MK_ACADEMIC_PERIOD = 1" + " and s.MK_STUDENT_NR = "
				+ studentNr + " and g.CODE = s.MK_HIGHEST_QUALIFI"
				+ " and a.MK_QUALIFICATION_C = s.MK_HIGHEST_QUALIFI"
				+ " and a.MK_STUDENT_NR = s.MK_STUDENT_NR"
				+ " and q.MK_QUALIFICATION_C(+) = a.mk_qualification_c"
				+ " and q.SPECIALITY_CODE(+) = a.FK_SPECIALITY_CODE";
		
		log.debug(sql1);

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql1);

		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			qual.setQualCode(data.get("MK_HIGHEST_QUALIFI").toString());
			qual.setQualDesc(data.get("ENG_DESCRIPTION").toString());
			if (data.get("FK_SPECIALITY_CODE") != null) {
				qual.setSpecCode(data.get("FK_SPECIALITY_CODE").toString());
				if (data.get("ENGLISH_DESCRIPTIO") != null) {
					qual.setSpecDesc(data.get("ENGLISH_DESCRIPTIO").toString());
				} else {
					qual.setSpecDesc("");
				}
			} else {
				qual.setSpecCode("");
				qual.setSpecDesc("");
			}
			if (data.get("INSTITUTION_CODE") == null) {
				qual.setInstitution("");
			} else {
				qual.setInstitution(data.get("INSTITUTION_CODE").toString());
			}
			if (data.get("UNDER_POST_CATEGOR") == null) {
				qual.setQualType("");
			} else {
				qual.setQualType(data.get("UNDER_POST_CATEGOR").toString());
			}
		}
		if ("".equalsIgnoreCase(qual.getSpecCode())) {
			/* if spec code empty read again with a space */
			String sql2 = "select ENGLISH_DESCRIPTIO" + " from quaspc "
					+ " where MK_QUALIFICATION_C = '" + qual.getQualCode()
					+ "'" + " and SPECIALITY_CODE = ' '";
			qual.setSpecDesc(this.querySingleValue(sql2, "ENGLISH_DESCRIPTIO"));
		}

		return qual;
	}

	public Qualification getApplicationQualification(String studentNr){


		Qualification qual = new Qualification();

		String sql = "select solstu.MK_QUALIFICATION_C as qual, grd.ENG_DESCRIPTION as DESCR, solstu.speciality_code as spec, ENGlish_DESCRIPTIO as specdescr from solstu, grd, quaspc " +
					 "where MK_STUDENT_NR=" +studentNr +
					 " and grd.CODE= solstu.MK_QUALIFICATION_C" +
					 " and quaspc.mk_qualification_c= solstu.mK_QUALIFICATION_C" +
					 " and quaspc.speciality_code = solstu.speciality_code";

		log.debug(sql);

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);

		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			qual.setQualCode(data.get("QUAL").toString());
			qual.setQualDesc(data.get("DESCR").toString());
			qual.setSpecCode(data.get("SPEC").toString());
			qual.setSpecDesc(data.get("SPECDESCR").toString());
		}

		return qual;
	}

	public void getApplicationInformation(Student student) throws Exception{


		String sql = "select * from solstu s where s.MK_STUDENT_NR=" +student.getNumber();

		log.debug(sql);

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);

		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			student.getCountry().setDesc(data.get("COUNTRY").toString());
			// unfortunately the country description is stored in solsun not the code
			student.getCountry().setCode(getCountryCodeFromDesc(data.get("COUNTRY").toString()));
		}

	}

	public GeneralItem getWorkflowReason(Student student){

		GeneralItem result = new GeneralItem();
		
		// read the last entry that was written away after the year modue registrations opened for the current reg year
		String sql = "select TO_CHAR(s.timestamp ,'DD Month YYYY') as changeDate, s.value_changed from stuchg s,  regdat d "+
					 " where s.MK_USER_CODE=9999 and s.MK_STUDENT_NR= " +student.getNumber()+ 
					 " and s.CHANGE_CODE='RW' and trunc(s.timestamp)>= trunc(d.FROM_DATE) and d.ACADEMIC_YEAR="+getCurrentYear()+
					 " and d.SEMESTER_PERIOD = 0 and d.TYPE='IR'"+
					 " and s.CHANGE_CODE='RW' order by s.timestamp desc";

		log.debug(sql);

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);

		Iterator i = queryList.iterator();
		// return first record only
		if (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			result.setCode(data.get("VALUE_CHANGED").toString().substring(0,2));
			result.setDesc(data.get("CHANGEDATE").toString());
		}

		return result;

	}


	public int getCurrentYear() {

		int currentYear = 0;
		log.debug("get current year - RegistrationStatus.");
			/* jan = 0, Feb=1 , Nov=10, Dec=11 etc */
		if (Calendar.getInstance().get(Calendar.MONTH) < 11) {
			currentYear = Calendar.getInstance().get(Calendar.YEAR);
		} else {
			/*Removed for test purposes +1*/
			currentYear = (Calendar.getInstance().get(Calendar.YEAR) + 1 );
		}
		log.debug("Returning "+currentYear+" as the current year for registration");

		return currentYear;

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
