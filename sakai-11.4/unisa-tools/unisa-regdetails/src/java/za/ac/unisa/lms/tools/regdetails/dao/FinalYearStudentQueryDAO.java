package za.ac.unisa.lms.tools.regdetails.dao;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.regdetails.actions.AdditionsAction;
import za.ac.unisa.lms.tools.regdetails.actions.FinalYearStudentAction;
import za.ac.unisa.lms.tools.regdetails.forms.Qualification;
import za.ac.unisa.lms.tools.regdetails.forms.RegDetailsForm;
import za.ac.unisa.lms.tools.regdetails.forms.StudyUnit;

public class FinalYearStudentQueryDAO extends StudentSystemDAO {

	public static Log log = LogFactory.getLog(FinalYearStudentAction.class);

	

	/**
	 * This method returns the study unit description
	 *
	 * @param studyUnitCode    The study unit code
	 */
	public String getStudyDesc(String studyUnitCode) throws Exception {
		// Return study unit desc
		String desc = "";

		String query = "select eng_short_descript " + " from sun "
				+ " where code = '" + studyUnitCode.toUpperCase() + "'";
		try {
			desc = this.querySingleValue(query, "ENG_SHORT_DESCRIPT").trim();

		} catch (Exception ex) {
			throw new Exception(
					"FinalYearStudentQueryDao : Error reading study unit / "
							+ ex, ex);
		}

		return desc.trim();
	}
	

	/**
	 * This method returns the study units language
	 *
	 * @param studyUnitCode    The study unit code
	 */
	public String getStudyUnitLanguage(String studyUnitCode) throws Exception {
		// Return study unit language
		String language = "";

		String query = "select language0 from sun " + " where code = '"
				+ studyUnitCode + "'";
		try {
			language = this.querySingleValue(query, "LANGUAGE0").trim();

		} catch (Exception ex) {
			throw new Exception(
					"FinalYearStudentQueryDao : Error reading study unit language/ "
							+ ex, ex);
		}

		return language.trim();
	}


	/**
	 * This method returns true if study unit is valid
	 *
	 * @param studyUnitCode
	 *            The study unit code
	 */
	public boolean isStudyUnitValid(String studyUnitCode) throws Exception {
		// Return study unit language
		boolean result = false;

		String query = "select code " + " from sun " + " where code = '"
				+ studyUnitCode + "'";
		try {
			if (!"".equals(querySingleValue(query, "CODE").trim())) {
				result = true;
			}

		} catch (Exception ex) {
			throw new Exception(
					"AdditionsQueryDao : Error reading study unit language/ "
							+ ex, ex);
		}

		return result;
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
			country = this.querySingleValue(query, "MK_COUNTRY_CODE").trim();

		} catch (Exception ex) {
			throw new Exception(
					"AdditionsQueryDao : Error reading country code/ " + ex, ex);
		}

		return country.trim();
	}
	
	/**
	 * This method returns the module count for all study units passed for a specific qualification
	 *
	 * @param StudentNr
	 *            The student number
	 * @param qualCode The qualification code
	 */
	public String getNumberOfModulesPassed(String studentNr, String qualCode) throws Exception {
		// Return country code
		String total = "0";

		String query = "select count(*) as total from acasun a, sun s"+
			" where a.FK_STUDENT_NR= " + studentNr +
			" and a.FK_QUAL_CODE="+ qualCode +
			" and a.CANCELLATION_CODE='NC'"+
			" and a.EXEMPTION_CODE<>5"+
			" and a.CREDIT_STATUS_CODE=1"+
			" and a.FK_RESULT_TYPE in (1,2,10,14,15,26)"+
			" and s.CODE= a.MK_STUDY_UNIT_CODE"+
			" and s.LOWEST_LEVEL_FLAG='Y'";
		try {
			total = this.querySingleValue(query, "TOTAL").trim();

		} catch (Exception ex) {
			throw new Exception(
					"FinalYearStudentQueryDao : Error get module count/ " + ex, ex);
		}

		return total;
	}
	
	/**
	 * This method returns the average percentage for all study units passed for a specific qualification
	 *
	 * @param StudentNr
	 *            The student number
	 * @param qualCode The qualification code
	 */
	public double getAverageModulePercentage(String studentNr, String qualCode) throws Exception {
		// Return country code
		double average = 0;

		String query = "select sum(a.FINAL_MARK) as total, count(*) as modulecount from acasun a, sun s"+
			" where a.FK_STUDENT_NR= " + studentNr +
			" and a.FK_QUAL_CODE="+ qualCode +
			" and a.CANCELLATION_CODE='NC'"+
			" and a.EXEMPTION_CODE not in (2,5)"+
			" and a.CREDIT_STATUS_CODE=1"+
			" and a.FK_RESULT_TYPE in (1,2,10,14,15,26)"+
			" and s.CODE= a.MK_STUDY_UNIT_CODE"+
			" and s.LOWEST_LEVEL_FLAG='Y'";
		try {
			String totalString = this.querySingleValue(query, "TOTAL").trim();
			if("".equals(totalString)){
				return average;
			}
			String countString = this.querySingleValue(query, "MODULECOUNT").trim();
			if("".equals(countString)){
				return average;
			}
			double total = Double.valueOf(this.querySingleValue(query, "TOTAL").trim());
			double count = Double.valueOf(this.querySingleValue(query, "MODULECOUNT").trim());
			if (total == 0 || count == 0){
				average = 0;
			}
			average = total/count;
		} catch (Exception ex) {
			throw new Exception(
					"FinalYearStudentQueryDao : Error calculating module average/ " + ex, ex);
		}

		return average;
	}

	/**
	 * This method returns false if the student has already got credit
	 * for the study unit
	 *
	 * @param studyUnit      The study unit code
	 * @param studentNr      The student number
	 */
	public boolean hasCredit(String studentNr,String studyUnit) throws Exception {

		boolean result = false;

		String sql = "select * from acasun a where a.MK_STUDY_UNIT_CODE='"+ studyUnit +
				"' and a.FK_STUDENT_NR="+studentNr+
				"and ( (a.FK_RESULT_TYPE in (1,2,10,14,15)) or (a.FK_RESULT_TYPE =1 and a.EXEMPTION_CODE =2))"+
				"and a.CANCELLATION_CODE='NC'";

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				result = true;
			}
		} catch (Exception ex) {
			throw new Exception("AdditionsQueryDao : Error reading study unit credit history / "
					+ ex, ex);
		}

		return result;
	}
	
	/**
	 * This method returns the study unit info
	 *
	 * @param studyUnitCode
	 *            The study unit code
	 *
	 */
	public StudyUnit getStudyUnitRegistrationPeriod(StudyUnit studyUnit) throws Exception {
		
		String sql = " SELECT p.semester_period" + " FROM sunpdt p"
				+ " WHERE p.fk_suncode = '" + studyUnit.getCode() + "'"
				+ " AND p.mk_academic_year = " + RegQueryDAO.getCurrentYear()
				+ " AND p.mk_academic_period = 1"
				+ " AND p.registration_allow = 'Y'";
		log.debug(sql);


		// Setup the open and valid registration periods for this study unit
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			int numberOfValidPeriods = 0;
			String onlyOpenRegPeriodForStaticDisplay = "";
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				if (data.get("SEMESTER_PERIOD") != null
						&& "1".equals(data.get("SEMESTER_PERIOD").toString())) {
					studyUnit.setRegPeriod1(true);
					numberOfValidPeriods = numberOfValidPeriods +1;
					onlyOpenRegPeriodForStaticDisplay = "1";
				}
				if (data.get("SEMESTER_PERIOD") != null
						&& "2".equals(data.get("SEMESTER_PERIOD").toString())) {
					studyUnit.setRegPeriod2(true);
					numberOfValidPeriods = numberOfValidPeriods +1;
					onlyOpenRegPeriodForStaticDisplay = "2";
				}
				if (data.get("SEMESTER_PERIOD") != null
						&& "0".equals(data.get("SEMESTER_PERIOD").toString())) {
					studyUnit.setRegPeriod0(true);
					numberOfValidPeriods = numberOfValidPeriods +1;
					onlyOpenRegPeriodForStaticDisplay = "0";
				}
				if (data.get("SEMESTER_PERIOD") != null
						&& "6".equals(data.get("SEMESTER_PERIOD").toString())) {
					studyUnit.setRegPeriod6(true);
					numberOfValidPeriods = numberOfValidPeriods +1;
					onlyOpenRegPeriodForStaticDisplay = "6";
				}

				// Use this indicator when there is only one reg period available
				// so that pick list is not displayed on screen
				if(numberOfValidPeriods==1){
					studyUnit.setRegPeriodStatic(true);
					studyUnit.setRegPeriod(onlyOpenRegPeriodForStaticDisplay);
				}else{
					studyUnit.setRegPeriodStatic(false);
				}

			}

		} catch (Exception ex) {
			throw new Exception(
					"AdditionsQueryDao : Error getStudyUnitForOpenRegPeriod/ "
							+ ex, ex);
		}

		return studyUnit;
	}
	
	
	/**
	 * This method returns a StudyUnit
	 *
	 * @param studyunit
	 *            A StudyUnit object
	 */
	public StudyUnit getStudyUnitInformation(StudyUnit studyUnit) throws Exception { 
		
		String query = "select eng_short_descript,language0 from sun where "
				+ "code = '" + studyUnit.getCode()+"'";

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				if(data.get("LANGUAGE0") != null){
					studyUnit.setLanguage(data.get("LANGUAGE0").toString().trim());
				}
				if(data.get("TUTOR_FLAG") !=null && "Y".equalsIgnoreCase(data.get("TUTOR_FLAG").toString())){
					studyUnit.setOdl(true);
				}
				if(data.get("WORK_INTEGR_LEARN") !=null && "Y".equalsIgnoreCase(data.get("WORK_INTEGR_LEARN").toString())){
					studyUnit.setWil(true);
				}
			}
		} catch (Exception ex) {
			throw new Exception(
					"AdditionsQueryDao : Error reading odl/wil - " + ex, ex);
		}
		
		return studyUnit;
		
	}


	/**
	 * This method returns a study unit object of which the exam dates has been
	 * set. Each study unit can have one or more exam dates.
	 *
	 * @param studyUnitCode
	 *            The study unit code
	 */
	public ArrayList getExamDatesForModule(String studyUnitCode,
			String registrationPeriod) throws Exception {

		String sql = "";
		ArrayList examList = new ArrayList();

		try {
			sql = sql
					+ "SELECT TO_CHAR(XAMDAT.DATE0,'DD/MM/YYYY') || ' ' || "
					+ " TO_CHAR(XAMDAT.STARTING_TIME,'HH24:MI') as exam_date"
					+ " FROM SUNPDT, XAMDAT "
					+ " WHERE SUNPDT.MK_ACADEMIC_YEAR ="
					+ RegQueryDAO.getCurrentYear()
					+ " AND SUNPDT.MK_ACADEMIC_PERIOD = 1"
					+ " AND SUNPDT.FK_SUNCODE = '"
					+ studyUnitCode
					+ "'"
					+ " AND SUNPDT.REGISTRATION_ALLOW = 'Y' "
					+ " AND SUNPDT.SEMESTER_PERIOD = "
					+ registrationPeriod
					+ " AND XAMDAT.MK_EXAM_PERIOD_COD (+) = SUNPDT.MK_EXAM_PERIOD "
					+ " AND XAMDAT.FK_EXAM_YEAR (+) = SUNPDT.MK_EXAM_YEAR"
					+ " AND XAMDAT.FK_STUDY_UNIT_CODE ( +) = SUNPDT.FK_SUNCODE";

			log.debug(sql);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				String examDate = data.get("EXAM_DATE").toString();
				if (data.get("EXAM_DATE") == null || " ".equals(data.get("EXAM_DATE"))) {
					examDate = "Departmental requirement";
				} else if (data.get("EXAM_DATE").toString().trim().equals(
						"01/01/1901 00:00")) {
					examDate = "Will be informed";
				} else if (data.get("EXAM_DATE").toString().trim().equals(
						"03/03/1903 00:00")) {
					examDate = "Departmental requirement";
				}
				examList.add(examDate);
			}

		} catch (Exception ex) {
			throw new Exception(
					"AdditionsQueryDao : Error in getExamDatesForModule" + ex,
					ex);
		}
		return examList;
	}


	/**
	 * This method executes a query and returns a String value as result. An
	 * empty String value is returned if the query returns no results.
	 *
	 * @param query
	 *            The query to be executed on the database
	 * @param field
	 *            The field that is queried on the database
	 */
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

	/**
	 * This method checks whether the additions period is open or closed for a
	 * specific qualification, returns a boolean result.
	 *
	 * @param qual
	 *            The qualification code
	 * @param form
	 *            The current RegDetails form
	 */
	public boolean isAdditionsPeriodValid(Qualification qual,
			RegDetailsForm form) throws Exception {
		boolean result = false;
		RegQueryDAO db = new RegQueryDAO();
		form.setRegPeriodOpen0("");
		form.setRegPeriodOpen1("");
		form.setRegPeriodOpen2("");
		form.setRegPeriodOpen6("");

		if ("U".equalsIgnoreCase(qual.getQualType())) {
			/* Undergrad additions OPEN */
			if (db.isRegPeriodValid("IA", "0")) {
				form.setRegPeriodOpen0("Y");
				result = true;
			}
			if (db.isRegPeriodValid("IA", "1")) {
				form.setRegPeriodOpen1("Y");
				result = true;
			}
			if (db.isRegPeriodValid("IA", "2")) {
				form.setRegPeriodOpen2("Y");
				result = true;
			}
			if (db.isRegPeriodValid("IA", "6")) {
				form.setRegPeriodOpen6("Y");
				result = true;
			}
		} else if ("H".equalsIgnoreCase(qual.getQualType())) {
			if (db.isRegPeriodValid("HA", "0")) {
				// Hons additions OPEN
				form.setRegPeriodOpen0("Y");
				result = true;
			}
		}
		//log.debug("FinalYearStudent - isAdditionsPeriodValid - Type="+qual.getQualType() + ", RegPeriodOpen0="+form.getRegPeriodOpen0());
		//log.debug("FinalYearStudent - isAdditionsPeriodValid - Type="+qual.getQualType() + ", RegPeriodOpen1="+form.getRegPeriodOpen1());
		//log.debug("FinalYearStudent - isAdditionsPeriodValid - Type="+qual.getQualType() + ", RegPeriodOpen2="+form.getRegPeriodOpen2());
		//log.debug("FinalYearStudent - isAdditionsPeriodValid - Type="+qual.getQualType() + ", RegPeriodOpen6="+form.getRegPeriodOpen6());
		return result;
	}

	public boolean deleteSolSun(String studentNr) {

		boolean result = false;
		Log log = LogFactory.getLog(RegQueryDAO.class);

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		String sql = "delete from solsun where MK_STUDENT_NR=" + studentNr;
		int recordsAffected = 0;
		recordsAffected = jdt.update(sql);
		if (recordsAffected > 0) {
			log.debug("Additions delete solsun records for " + studentNr + ".");
			/* write audit trail */
			// RegQueryDAO dao = new RegQueryDAO();
			// dao.writeAuditTrail(studentNr, "IA", "SolsunDel");
			result = true;
		}
		return result;

	}

	public boolean deleteTPStusun(String studentNr) {

		boolean result = false;
		Log log = LogFactory.getLog(RegQueryDAO.class);

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		String sql = "delete from stusun where status_code='TP' and FK_STUDENT_NR="
				+ studentNr;
		int recordsAffected = 0;
		recordsAffected = jdt.update(sql);
		if (recordsAffected > 0) {
			log.debug("Additions delete stusun records for " + studentNr + ".");
			/* write audit trail */
			// RegQueryDAO dao = new RegQueryDAO();
			// dao.writeAuditTrail(studentNr,"IA","TPDel");
			result = true;
		}
		return result;
	}

	public void writeSolSun(String studentNr, ArrayList studyUnits) {

		Log log = LogFactory.getLog(RegQueryDAO.class);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new java.util.Date().getTime());

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());

		for (int i = 0; i < studyUnits.size(); i++) {
			StudyUnit su = new StudyUnit();
			su = (StudyUnit) studyUnits.get(i);
			if (su.getCode() != null && !"".equals(su.getCode())) {
				String sql = "insert into solsun (MK_STUDENT_NR,MK_STUDY_UNIT_CODE,SEMESTER_PERIOD,MK_OPTION_CODE,MK_LANGUAGE_CODE,CHANGE_TIMESTAMP,NDP_INDICATOR, WIL,ODL) "
						+ " values (?,?,?,?,?,?,?,?,?)";
				String lang = "E";
				if (su.getLanguage() != null
						&& !"".equals(su.getLanguage())
						&& su.getLanguage().length() >= 1
						&& "A".equalsIgnoreCase(su.getLanguage()
								.substring(0, 1))) {
					lang = "A";
				}
				jdt.update(sql, new Object[] { studentNr, su.getCode(),
						su.getRegPeriod(), "", lang,
						new Timestamp(cal.getTimeInMillis()), su.getNdp(),su.getWilAnswer(),su.getOdlAnswer() });
				log.debug("Addition add solsun record " + su.getCode()
						+ " for " + studentNr + ".");
			}
		}
	}

	public void updateAcadRecordSpec(String studentNr, String qualCode,
			String specCode) {

		Log log = LogFactory.getLog(RegQueryDAO.class);
		String sql = "";

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		if (specCode == null){
		  sql = "update stuaca a set a.FK_SPECIALITY_CODE=null"
			+ " where a.MK_STUDENT_NR=" + studentNr
			+ " and  mk_qualification_c='" + qualCode + "'";
		}else{
		  sql = "update stuaca a set a.FK_SPECIALITY_CODE='" + specCode
				+ "' where a.MK_STUDENT_NR=" + studentNr
				+ " and  mk_qualification_c='" + qualCode + "'";
		}
		int recordsAffected = 0;
		recordsAffected = jdt.update(sql);
		if (recordsAffected > 0) {
			// write audit trail
			RegQueryDAO dao = new RegQueryDAO();
			dao.writeAuditTrail(studentNr, "IA", "Specupdate");
			log.debug("Additions update stuaca spec code for " + studentNr
					+ ".");
		}
	}

}
