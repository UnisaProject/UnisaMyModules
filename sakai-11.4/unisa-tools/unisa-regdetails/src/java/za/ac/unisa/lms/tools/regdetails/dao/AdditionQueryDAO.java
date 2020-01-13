package za.ac.unisa.lms.tools.regdetails.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
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
import za.ac.unisa.lms.tools.regdetails.forms.Qualification;
import za.ac.unisa.lms.tools.regdetails.forms.RegDetailsForm;
import za.ac.unisa.lms.tools.regdetails.forms.StudyUnit;

@SuppressWarnings({"unchecked", "rawtypes", "unused"})
public class AdditionQueryDAO extends StudentSystemDAO {

	public static Log log = LogFactory.getLog(AdditionsAction.class);

	/**
	 * This method returns the Qualification description
	 *
	 * @param qualCode    The Qualification code
	 */
	public String getQualDesc(String qualCode) throws Exception {
		// Return Qualification description
		String desc = "";

		String query = "select eng_description from grd "
				+ " where code = '" + qualCode + "'";
		try {
			desc = this.querySingleValue(query, "ENG_DESCRIPTION").trim();

		} catch (Exception ex) {
			throw new Exception(
					"AdditionsQueryDao : Error reading Qualification description/ "
							+ ex);
		}

		return desc.trim();
	}

	/**
	 * This method returns the Specification description
	 *
	 * @param qualCode    The Qualification code
	 * @param specCode    The Specification code
	 */
	public String getSpecDesc(String qualCode, String specCode) throws Exception {
		// Return Specification description
		String desc = "";
		if ("N/A".equalsIgnoreCase(specCode) || "".equalsIgnoreCase(specCode) || specCode == null){
			specCode = " ";
		}
		String query = "select english_descriptio from quaspc "
				+ " where mk_qualification_c = '" + qualCode + "' "
				+ " and speciality_code = '" + specCode + "'";
		try {
			desc = this.querySingleValue(query, "ENGLISH_DESCRIPTIO").trim();

		} catch (Exception ex) {
			throw new Exception(
					"AdditionsQueryDao : Error reading Specification description/ "
							+ ex);
		}

		return desc;
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
					"AdditionsQueryDao : Error reading study unit language/ "
							+ ex);
		}

		return language.trim();
	}

	/**
	 * This method returns the study unit description
	 *
	 * @param studyUnitCode    The study unit code
	 */
	public String getStudyDesc(String studyUnitCode) throws Exception {
		// Return study unit desc
		String desc = "";

		String query = "select eng_short_descript " + " from sun "
				+ " where code = '" + studyUnitCode + "'";
		try {
			desc = this.querySingleValue(query, "ENG_SHORT_DESCRIPT").trim();

		} catch (Exception ex) {
			throw new Exception(
					"AdditionsQueryDao : Error reading study unit description/ "
							+ ex);
		}

		return desc.trim();
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
							+ ex);
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
					"AdditionsQueryDao : Error reading country code/ " + ex);
		}

		return country.trim();
	}

	/**
	 * This method returns the study unit info
	 *
	 * @param studyUnitCode
	 *            The study unit code
	 * @param regPeriod1
	 *            Y if registration period 1 is open, else N
	 * @param regPeriod2
	 *            Y if registration period 2 is open, else N
	 * @param regPeriod6
	 *            Y if registration period 6 is open, else N
	 * @param regPeriod0
	 *            Y if registration period 0 is open, else N
	 */
	public StudyUnit getStudyUnitInfoForOpenRegPeriod(String studyUnitCode,
			String regPeriod1, String regPeriod2, String regPeriod6,
			String regPeriod0) throws Exception {

		// Return studyUnit
		StudyUnit studyUnit = new StudyUnit();

		// Setup reg periods that are currently open
		String regPeriods = " and p.SEMESTER_PERIOD in (";
		if ("Y".equalsIgnoreCase(regPeriod0)) {
			regPeriods = regPeriods + "0,";
		}
		if ("Y".equalsIgnoreCase(regPeriod1)) {
			regPeriods = regPeriods + "1,";
		}
		if ("Y".equalsIgnoreCase(regPeriod2)) {
			regPeriods = regPeriods + "2,";
		}
		if ("Y".equalsIgnoreCase(regPeriod6)) {
			regPeriods = regPeriods + "6,";
		}
		regPeriods = regPeriods.substring(0, regPeriods.length() - 1) + ")";

		String sql = " SELECT p.semester_period" + " FROM sunpdt p"
				+ " WHERE p.fk_suncode = '" + studyUnitCode + "'"
				+ " AND p.mk_academic_year = " + RegQueryDAO.getCurrentYear()
				+ " AND p.mk_academic_period = 1"
				+ " AND p.registration_allow = 'Y'" + regPeriods;
		//log.debug(sql);

		// set general study unit information
		studyUnit.setCode(studyUnitCode);
		studyUnit.setDesc(getStudyDesc(studyUnitCode));

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
							+ ex);
		}

		return studyUnit;
	}

	/**
	 * This method returns false if the student tries to submit an application
	 * within 30 days from the previous application
	 *
	 * @param studentNr      The student number
	 */
	public boolean canSubmitAddition(String studentNr) throws Exception {

		boolean result = true;
		Calendar testDate = Calendar.getInstance();
		Calendar lastDate = Calendar.getInstance();
		testDate.add(Calendar.DATE, -30);

		/*
		 * String sql = "select to_char(timestamp, 'DD-mon-YYYY') as chgdate "+ "
		 * from stuchg" + " where mk_student_nr = " + studentNr + " and
		 * change_code='IA' order by timestamp";
		 */
		String sql = "select to_char(creation_date, 'DD-mon-YYYY') as creation_date from stadoc where FK_STUFLGCODE= 99 and fk_stunr="
				+ studentNr;
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
				Date last = formatter.parse(data.get("CREATION_DATE").toString());
				lastDate.setTime(last);
				DateFormat strDate = new SimpleDateFormat("dd-MM-yyyy");
				//log.debug(strDate.format(testDate.getTime()));
				//log.debug(strDate.format(lastDate.getTime()));
				if (!testDate.after(lastDate)) {
					result = false;
				}
			}
			/*
			 * while (i.hasNext()) { //Loop through results to get the last date
			 * HashMap data = (HashMap) i.next(); SimpleDateFormat formatter =
			 * new SimpleDateFormat("dd-MMM-yyyy"); Date last =
			 * formatter.parse(data.get("CHGDATE").toString());
			 * lastDate.setTime(last); check = true; }
			 */
			// DateFormat strDate = new SimpleDateFormat( "dd-MM-yyyy" );
			// //log.debug(strDate.format(testDate.getTime()));
			// //log.debug(strDate.format(lastDate.getTime()));

		} catch (Exception ex) {
			throw new Exception("AdditionsQueryDao : Error reading stadoc / "
					+ ex);
		}

		return result;
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
					+ ex);
		}

		return result;
	}
	
	/**
	 * This method returns false if the student doesn't have a  address
	 *
	 * @param studentNr     The student number
	 */
	public boolean hasValidAddress(String studentNr, String addressType) throws Exception {

		boolean result = false;
		String category = "1";
		if ("Postal".equalsIgnoreCase(addressType)){
			category = "1";
		}else if ("Physical".equalsIgnoreCase(addressType)){
			category = "3";
		}else if ("Courier".equalsIgnoreCase(addressType)){
			category = "7";
		}
		
		String sql = "select address_line_1 from adr a where a.reference_no='"+ studentNr +
				"' and a.fk_adrcatypfk_adrc=1"+
				"  and a.fk_adrcatypfk_adrt='"+category+"'";

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				result = true;
			}
		} catch (Exception ex) {
			throw new Exception("AdditionsQueryDao : Error reading student "+addressType+" address / "
					+ ex);
		}

		return result;
	}
	
	/**
	 * This method returns false if the suburb of the student's courier address doesn't match the postal code
	 *
	 * @param studentNr     The student number
	 */

	public boolean isAddressSubCodeValid(String studentNr, String addressLine, String postalCode, String isBoxOrStreet, String postalType) throws Exception {

		//log.debug("AdditionsQueryDAO - isAddressSubCodeValid - studentNr="+studentNr+", addressLine="+addressLine+", postalCode="+postalCode+", isBoxOrStreet="+isBoxOrStreet+", postalType="+postalType);
		
		boolean result = false;
		String searchType = "B";
		
		if("Postal".equalsIgnoreCase(postalType)){
			if (isBoxOrStreet == "B"){
				searchType = " and type ='B' ";
			}else{
				searchType = " and type ='S' ";
			}
		}else{
			searchType = " and type in ('S', 'B') ";
		}

		//log.debug("AdditionsQueryDAO - isAddressSubCodeValid - studentNr="+studentNr+", searchType="+searchType);
			
		try {
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
				
				String sql1 = "SELECT suburb from pstcod "
							+ " where upper(suburb) = ? "
							+ " and code=? "
							+ searchType;
				//log.debug("AdditionsQueryDAO - isAddressSubCodeValid - SQL1: " + sql1);
				//log.debug("AdditionsQueryDAO - isAddressSubCodeValid - studentNr="+studentNr+", sql1="+sql1+", addressLine="+addressLine.toUpperCase().trim()+", postalCode="+postalCode.trim() );

				List queryList = jdt1.queryForList(sql1,
						new Object[] { addressLine.toUpperCase().trim(), postalCode.trim() },
						new int[] { Types.VARCHAR, Types.VARCHAR });
				Iterator i = queryList.iterator();
				if (i.hasNext()) {
					result = true;
					//log.debug("AdditionsQueryDAO - isAddressSubCodeValid - studentNr="+studentNr+", result next="+result);
				}
		} catch (Exception ex) {
			throw new Exception("AdditionsQueryDao : Error validating courier address suburb vs postal code / "
					+ ex);
		}

		//log.debug("AdditionsQueryDAO - isAddressSubCodeValid - studentNr="+studentNr+", result final="+result);
		return result;
	}
	
	/**
	 * This method returns true if the String entered is a Suburb or Town
	 *
	 * @param addressLine     The Address Line
	 */
	public boolean isLineSub(String addressLine) throws Exception {

		//log.debug("AdditionsQueryDAO - isLineSub - addressLine="+addressLine);
		
		boolean result = false;

		try {
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
				
				String sql1 = "SELECT suburb, town from pstcod "
							+ " where (upper(suburb) = ? or upper(town) = ?) ";

				//log.debug("AdditionsQueryDAO - isLineSub - sql1="+sql1+", addressLine="+addressLine.toUpperCase().trim());

				List queryList = jdt1.queryForList(sql1,
						new Object[] { addressLine.toUpperCase().trim(), addressLine.toUpperCase().trim() });
				Iterator i = queryList.iterator();
				if (i.hasNext()) {
					result = true;
				}
		} catch (Exception ex) {
			throw new Exception("AdditionsQueryDao : Error validating courier address suburb on first line / " + ex);
		}

		//log.debug("AdditionsQueryDAO - isLineSub - result final="+result);
		return result;
	}
	
	/**
	 * This method returns true if the student was registered by a licensee
	 *
	 * @param studentNr     The student number
	 */
	public boolean isLicenseeStudent(String studentNr) throws Exception {

		boolean result = false;

		String sql = "select acttyp.contract_flag from stuann, acttyp "
				+ " where stuann.mk_student_nr='"+ studentNr + "' "
				+ " and stuann.mk_academic_year='"+ RegQueryDAO.getCurrentYear()+"' "
				+ " and stuann.mk_academic_period=1 "
				+ " and stuann.fk_account_type not in ('0108','0116','0124') "
				+ " and acttyp.code=stuann.fk_account_type "
				+ " and acttyp.branch_code=stuann.fk_branch_code";

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				if (data.get("CONTRACT_FLAG") != null && "Y".equalsIgnoreCase(data.get("CONTRACT_FLAG").toString())){
						result = true;
				}
			}
		} catch (Exception ex) {
			throw new Exception("AdditionsQueryDao : Error reading student account for lsa test / "
					+ ex);
		}

		return result;
	}
	
	/**
	 * This method returns true if the student is a staff member or one of their dependents
	 *
	 * @param studentNr     The student number
	 */
	public boolean isStaffStudent(String studentNr) throws Exception {

		boolean result = false;

		String sql = "select fk_account_type from stuann " 
				+ " where mk_student_nr='"+ studentNr +"' "
				+ " and mk_academic_year='"+ RegQueryDAO.getCurrentYear()+"' "
				+ " and mk_academic_period=1 "
				+ " and fk_account_type in ('0116','0124')";

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			if (i.hasNext()) {
					result = true;
			}
		} catch (Exception ex) {
			throw new Exception("AdditionsQueryDao : Error reading student account for staff test / "
					+ ex);
		}

		return result;
	}
	
	/**
	 * This method returns true if the qualification and spec combination is allowed as selfhelp
	 *
	 * @param studentNr     The student number
	 */
	public boolean canGoToSelfhelp(String qualCode, String specCode) throws Exception {

		boolean result = false;
		if ("N/A".equalsIgnoreCase(specCode) || "".equalsIgnoreCase(specCode) || specCode == null){
			specCode = " ";
		}
		String sql = "select selfhelp_flag from quaspc " 
				+ " where mk_qualification_c='"+ qualCode +"' "
				+ " and speciality_code='"+ specCode+"' "
				+ " and selfhelp_flag = 'Y' ";

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				result = true;
			}
		} catch (Exception ex) {
			throw new Exception("AdditionsQueryDao : Error reading selfhelp flag / " + ex);
		}

		return result;
	}

	/**
	 * This method returns a list of specific undergraduate study units for a
	 * given registration period if the additions period for that registration
	 * period is valid for the current date
	 *
	 * @param qualCode
	 *            The qualification code
	 * @param specCode
	 *            The speciality code
	 * @param orderBy
	 *            Order by code or description (C or D)
	 * @param postGrad
	 *            True if student is postgrad
	 */
	public ArrayList getSpecificSUList(String qualCode, String specCode, String orderBy, boolean postGrad) throws Exception {

		ArrayList list = new ArrayList();
		RegQueryDAO db = new RegQueryDAO();
		StudyUnit su = new StudyUnit();
		String regPeriods = " and SUNPDT.SEMESTER_PERIOD in (";
		String orderByClause = "";
		
		if ("N/A".equalsIgnoreCase(specCode) || "".equalsIgnoreCase(specCode) || specCode == null){
			specCode = " ";
		}

		try {
			String type = "IA"; /* undergrad */
			if (postGrad) {
				type = "HA"; /* postgrad */
			}
			// Setup reg periods that are currently open
			if (db.isRegPeriodValid(type, "0")) {
				regPeriods = regPeriods + "0,";
			}
			if (db.isRegPeriodValid(type, "1")) {
				regPeriods = regPeriods + "1,";
			}
			if (db.isRegPeriodValid(type, "2")) {
				regPeriods = regPeriods + "2,";
			}
			if (db.isRegPeriodValid(type, "6")) {
				regPeriods = regPeriods + "6,";
			}
			regPeriods = regPeriods.substring(0, regPeriods.length() - 1) + ")";

			// Setup order by clause
			orderByClause = " ORDER BY code";
			if ("D".equalsIgnoreCase(orderBy)) {
				orderByClause = " ORDER BY SUN.ENG_SHORT_DESCRIPT";
			}

			String sql = "SELECT DISTINCT SUNPDT.FK_SUNCODE as code,"
					+ "SUN.ENG_SHORT_DESCRIPT, "
					+ " nvl((select NQF_CREDITS from sunwgt where MK_QUAL_CODE = grd.code and sunwgt.mk_study_unit_code = qspsun.mk_study_unit_code),0) as CREDITWGT, "
            		+ " SUN.NQF_CREDITS, grd.pqm_compliant_flag as HEQF_COMPLIANT " 
					+ " from qspsun, sunpdt, sun, grd ";
			/* read spec code only if spec code not empty */
			if (specCode == null || "".equalsIgnoreCase(specCode.trim())) {
				sql = sql + " where ";
			} else {
				sql = sql + " where qspsun.mk_spes_code ='" + specCode
						+ "' and ";
			}
			sql = sql + "qspsun.mk_qual_code = '" + qualCode + "'"
					+ " and qspsun.old_code = 'N'"
					+ " and sunpdt.mk_academic_year = "
					+ RegQueryDAO.getCurrentYear()
					+ " and sunpdt.mk_academic_period=1"
					+ " and SUNPDT.REGISTRATION_ALLOW = 'Y'"
					+ " and sunpdt.fk_suncode = qspsun.mk_study_unit_code"
					+ " and grd.code = qspsun.mk_qual_code "
					+ " and SUNPDT.FK_SUNCODE = SUN.CODE" + regPeriods
					+ orderByClause;
			
			//log.debug("AdditionsQueryDAO - getSpecificSUList - SQL="+sql);
			
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				// setup record for this study unit
				su = new StudyUnit();
				su.setDesc(data.get("ENG_SHORT_DESCRIPT").toString());
				su.setCode(data.get("CODE").toString());
				if (Integer.parseInt(data.get("CREDITWGT").toString()) > 0){
					su.setCredits(Integer.parseInt(data.get("CREDITWGT").toString()));
				}else{
					su.setCredits(Integer.parseInt(data.get("NQF_CREDITS").toString()));
				}
				su.setHEQF(data.get("HEQF_COMPLIANT").toString());
				list.add(su);
			}

		} catch (Exception ex) {
			throw new Exception(
					"AdditionsQueryDao : Error reading getSpecificRegSUList"
							+ ex);
		}

		return list;
	}

	/**
	 * This method returns a list of undergraduate study units for a given
	 * registration period if the additions period for that registration period
	 * is valid for the current date
	 *
	 * @param orderBy
	 *            Order by code or description (C or D)
	 */
	public ArrayList getAllUnderRegSUList(String orderBy) throws Exception {

		ArrayList list = new ArrayList();
		RegQueryDAO db = new RegQueryDAO();
		String sql = "";
		String type = "IA"; /* undergrad */
		String regPeriods = " and SUNPDT.SEMESTER_PERIOD in (";
		String orderByClause = "";
		StudyUnit su = new StudyUnit();

		try {
			// Setup reg periods that are currently open
			if (db.isRegPeriodValid(type, "0")) {
				regPeriods = regPeriods + "0,";
			}
			if (db.isRegPeriodValid(type, "1")) {
				regPeriods = regPeriods + "1,";
			}
			if (db.isRegPeriodValid(type, "2")) {
				regPeriods = regPeriods + "2,";
			}
			if (db.isRegPeriodValid(type, "6")) {
				regPeriods = regPeriods + "6,";
			}
			regPeriods = regPeriods.substring(0, regPeriods.length() - 1) + ")";

			// Setup order by clause
			orderByClause = " ORDER BY code";
			if ("D".equalsIgnoreCase(orderBy)) {
				orderByClause = " ORDER BY SUN.ENG_SHORT_DESCRIPT";
			}

			sql = "SELECT distinct(SUN.CODE) as code, "
					+ " SUN.ENG_SHORT_DESCRIPT, "
					+ " SUN.NQF_CREDITS AS CREDITWGT, SUN.NQF_CREDITS, 'Y' AS HEQF_COMPLIANT "
					+ " FROM SUN, SUNPDT "
					+ " WHERE SUN.IN_USE_FLAG = 'Y' "
					+ " AND ((SUN.ACADEMIC_LEVEL= 'U' AND SUN.FORMAL_TUITION_FLA IN ('F', 'A')) OR "
					+ "     (SUN.ACADEMIC_LEVEL= 'N' AND SUN.FORMAL_TUITION_FLA IN ('A')) ) "
					+ " AND SUN.COLLEGE_FLAG in ('T','N') "
					+ " AND SUNPDT.MK_ACADEMIC_YEAR = "
					+ RegQueryDAO.getCurrentYear()
					+ " AND SUNPDT.MK_ACADEMIC_PERIOD = 1 "
					+ " AND SUNPDT.FK_SUNCODE = SUN.CODE "
					+ " AND SUNPDT.REGISTRATION_ALLOW = 'Y' " 
					+ regPeriods
					+ orderByClause;
			log.info(sql);
			
			//log.debug("AdditionsQueryDAO - getAllUnderRegSUList - SQL="+sql);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				// setup record for this study unit
				su = new StudyUnit();
				su.setDesc(data.get("ENG_SHORT_DESCRIPT").toString());
				su.setCode(data.get("CODE").toString());
				if (Integer.parseInt(data.get("CREDITWGT").toString()) > 0){
					su.setCredits(Integer.parseInt(data.get("CREDITWGT").toString()));
				}else{
					su.setCredits(Integer.parseInt(data.get("NQF_CREDITS").toString()));
				}
				su.setHEQF(data.get("HEQF_COMPLIANT").toString());
				list.add(su);
			}

		} catch (Exception ex) {
			throw new Exception(
					"AdditionsQueryDao : Error reading getAllUnderRegSUList"
							+ ex);
		}
		return list;
	}

	/**
	 * This method returns a list of undergraduate study units for a given
	 * registration period if the additions period for that registration period
	 * is valid for the current date
	 *
	 * @param orderBy
	 *            Order by code or description (C or D)
	 */
	public ArrayList getAllHonsRegSUList(String orderBy) throws Exception {

		RegQueryDAO db = new RegQueryDAO();
		ArrayList list = new ArrayList();
		String sql = "";
		String type = "IA"; /* undergrad */
		String regPeriods = " and SUNPDT.SEMESTER_PERIOD in (";
		String orderByClause = "";
		StudyUnit su = new StudyUnit();

		try {
			// Setup reg periods that are currently open
			if (db.isRegPeriodValid(type, "0")) {
				regPeriods = regPeriods + "0,";
			}
			if (db.isRegPeriodValid(type, "1")) {
				regPeriods = regPeriods + "1,";
			}
			if (db.isRegPeriodValid(type, "2")) {
				regPeriods = regPeriods + "2,";
			}
			if (db.isRegPeriodValid(type, "6")) {
				regPeriods = regPeriods + "6,";
			}
			regPeriods = regPeriods.substring(0, regPeriods.length() - 1) + ")";

			// Setup reg periods that are currently open:
			// Hons only has year modules

			// Setup order by clause
			orderByClause = " ORDER BY code";
			if ("D".equalsIgnoreCase(orderBy)) {
				orderByClause = " ORDER BY SUN.ENG_SHORT_DESCRIPT";
			}

			sql = "SELECT distinct(SUN.CODE) as code,"
					+ " SUN.ENG_SHORT_DESCRIPT, " 
					+ " SUN.NQF_CREDITS AS CREDITWGT, SUN.NQF_CREDITS, 'Y' AS HEQF_COMPLIANT "
					+ " FROM SUN, SUNPDT "
					+ " WHERE SUN.IN_USE_FLAG = 'Y'"
					+ " AND SUN.ACADEMIC_LEVEL='H'"
					+ " AND SUN.FORMAL_TUITION_FLA IN ('F', 'A')"
					+ " AND SUN.COLLEGE_FLAG = 'N'"
					+ " AND SUNPDT.MK_ACADEMIC_YEAR ="
					+ 	RegQueryDAO.getCurrentYear()
					+ " AND SUNPDT.MK_ACADEMIC_PERIOD = 1"
					+ " AND SUNPDT.FK_SUNCODE = SUN.CODE"
					+ " AND SUNPDT.REGISTRATION_ALLOW = 'Y'"
					+ regPeriods
					+ orderByClause;
			log.info(sql);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				// setup record for this study unit
				su = new StudyUnit();
				su.setDesc(data.get("ENG_SHORT_DESCRIPT").toString());
				su.setCode(data.get("CODE").toString());
				if (Integer.parseInt(data.get("CREDITWGT").toString()) > 0){
					su.setCredits(Integer.parseInt(data.get("CREDITWGT").toString()));
				}else{
					su.setCredits(Integer.parseInt(data.get("NQF_CREDITS").toString()));
				}
				su.setHEQF(data.get("HEQF_COMPLIANT").toString());
				list.add(su);
			}

		} catch (Exception ex) {
			throw new Exception(
					"AdditionsQueryDao : Error reading getAllUnderRegSUList"
							+ ex);
		}
		return list;
	}

	/**
	 * 2014 Edmund change to get qualifications from STUACA and STUAPQ
	 * 
	 * This method returns a list of qualifications
	 *
	 * @param qualType
	 *            The qualification type : U = undergrad / H = postgrad
	 * @param institution
	 *            The institution code : T = tsa
	 */
	public ArrayList getQualList2014(String qualType, String institution, String studentNr)
			throws Exception {
		ArrayList list = new ArrayList();
		String query = "";
		String checkUH = "";
		String checkUH2 = "";
		if ("U".equalsIgnoreCase(qualType)) {
			query = "select distinct qual_code, qual_desc, spec_code from ( "
					+ " select stuaca.mk_qualification_c as qual_code, grd.eng_description as qual_desc, substr(trim(stuaca.fk_speciality_code)||'N/A',1,3) as spec_code " 
					+ " from stuaca, grd , quaspc " 
					+ " where stuaca.mk_student_nr='" + studentNr + "' " 
					+ " and stuaca.status_code <> 'CO' " 
					+ " and stuaca.status_code <> 'PC' " 
					+ " and grd.code = stuaca.mk_qualification_c " 
					+ " and grd.in_use_flag='Y' " 
					+ " and grd.code not like '1%' "
					+ " and grd.under_post_categor in ('U') "
					+ " and (grd.from_year<='"+ RegQueryDAO.getCurrentYear() +"' and (grd.to_year = '0' or grd.to_year >= '"+ RegQueryDAO.getCurrentYear() +"')) "
					+ " and (grd.institution_code not in ('V','E','L') or grd.institution_code is null) "
					+ " and quaspc.mk_qualification_c=stuaca.mk_qualification_c " 
					+ " and ((quaspc.speciality_code=stuaca.fk_speciality_code) or (stuaca.fk_speciality_code is null and quaspc.speciality_code=' ')) " 
					+ " and quaspc.in_use_flag='Y' " 
					+ " and (quaspc.from_year<='"+ RegQueryDAO.getCurrentYear() +"' and (quaspc.to_year = '0' or quaspc.to_year >= '"+ RegQueryDAO.getCurrentYear() +"')) "
					+ " union " 
					+ " select stuapq.new_qual as qual_code, grd.eng_description as qual_desc, substr(trim(stuapq.new_spes)||'N/A',1,3) as spec_code " 
					+ " from stuapq, grd, quaspc " 
					+ " where stuapq.mk_student_nr='" + studentNr + "' "
					+ " and grd.code = stuapq.new_qual " 
					+ " and stuapq.status_code='TN' " 
					+ " and stuapq.admission_verified = 'Y' "
					+ " and grd.in_use_flag='Y' " 
					+ " and grd.code not like '1%' "
					+ " and grd.under_post_categor in ('U') "
					+ " and (grd.from_year<='"+ RegQueryDAO.getCurrentYear() +"' and (grd.to_year = '0' or grd.to_year >= '"+ RegQueryDAO.getCurrentYear() +"')) "
					+ " and (grd.institution_code not in ('V','E','L') or grd.institution_code is null) "
					+ " and quaspc.mk_qualification_c=stuapq.new_qual "
					+ " and ((quaspc.speciality_code=stuapq.new_spes) or (stuapq.new_spes is null and quaspc.speciality_code=' ')) " 
					+ " and quaspc.in_use_flag='Y' " 
					+ " and (quaspc.from_year<='"+ RegQueryDAO.getCurrentYear() +"' and (quaspc.to_year = '0' or quaspc.to_year >= '"+ RegQueryDAO.getCurrentYear() +"')) "
					+ " and stuapq.academic_year ='"+ RegQueryDAO.getCurrentYear() +"' "
					+ " )order by qual_desc ";
			
		} else if ("H".equalsIgnoreCase(qualType)) {
			query = "select distinct qual_code, qual_desc, spec_code from ( "
				+ " select stuaca.mk_qualification_c as qual_code, grd.eng_description as qual_desc, substr(trim(stuaca.fk_speciality_code)||'N/A',1,3) as spec_code " 
				+ " from stuaca, grd , quaspc " 
				+ " where stuaca.mk_student_nr='" + studentNr + "' " 
				+ " and stuaca.status_code <> 'CO' " 
				+ " and stuaca.status_code <> 'PC' " 
				+ " and grd.code = stuaca.mk_qualification_c " 
				+ " and grd.in_use_flag='Y' " 
				+ " and grd.code not like '1%' "
				+ " and (grd.fk_katcode in (4, 5, 47, 65, 69, 6, 48, 70) or grd.under_post_categor in ('H')) "
				+ " and (grd.from_year<='"+ RegQueryDAO.getCurrentYear() +"' and (grd.to_year = '0' or grd.to_year >= '"+ RegQueryDAO.getCurrentYear() +"')) "
				+ " and (grd.institution_code not in ('V','E','L') or grd.institution_code is null) "
				+ " and quaspc.mk_qualification_c=stuaca.mk_qualification_c " 
				+ " and ((quaspc.speciality_code=stuaca.fk_speciality_code) or (stuaca.fk_speciality_code is null and quaspc.speciality_code=' ')) " 
				+ " and quaspc.in_use_flag='Y' " 
				+ " and (quaspc.from_year<='"+ RegQueryDAO.getCurrentYear() +"' and (quaspc.to_year = '0' or quaspc.to_year >= '"+ RegQueryDAO.getCurrentYear() +"')) "
				+ " union " 
				+ " select stuapq.new_qual as qual_code, grd.eng_description as qual_desc, substr(trim(stuapq.new_spes)||'N/A',1,3) as spec_code " 
				+ " from stuapq, grd, quaspc " 
				+ " where stuapq.mk_student_nr='" + studentNr + "' "
				+ " and grd.code = stuapq.new_qual " 
				+ " and stuapq.status_code='TN' " 
				+ " and stuapq.admission_verified = 'Y' "
				+ " and grd.in_use_flag='Y' " 
				+ " and grd.code not like '1%' "
				+ " and (grd.fk_katcode in (4, 5, 47, 65, 69, 6, 48, 70 ) or grd.under_post_categor in ('H')) "
				+ " and (grd.from_year<='"+ RegQueryDAO.getCurrentYear() +"' and (grd.to_year = '0' or grd.to_year >= '"+ RegQueryDAO.getCurrentYear() +"')) "
				+ " and (grd.institution_code not in ('V','E','L') or grd.institution_code is null) "
				+ " and quaspc.mk_qualification_c=stuapq.new_qual "
				+ " and ((quaspc.speciality_code=stuapq.new_spes) or (stuapq.new_spes is null and quaspc.speciality_code=' ')) " 
				+ " and quaspc.in_use_flag='Y' " 
				+ " and (quaspc.from_year<='"+ RegQueryDAO.getCurrentYear() +"' and (quaspc.to_year = '0' or quaspc.to_year >= '"+ RegQueryDAO.getCurrentYear() +"')) "
				+ " )order by qual_desc ";
		}
		try {

			//log.debug("AdditionsQueryDao - getQualList2014 - query: " + query);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				String code = data.get("QUAL_CODE").toString();
				String desc = data.get("QUAL_DESC").toString();
				String spec = data.get("SPEC_CODE").toString();
				list.add(new org.apache.struts.util.LabelValueBean(code + " - "	+ desc + " - Speciality: "	+ spec, code + "@" + spec));
			}
		} catch (Exception ex) {
			throw new Exception(
					"AdditionsQueryDao : Error reading qual list / " + ex);
		}
		return list;
	}
	
	/**
	 * This method returns a list of specialities
	 *
	 * @param qualCode
	 *            The qualification code
	 */
	public ArrayList getSpecList(String qualCode) throws Exception {
		ArrayList list = new ArrayList();
		String query = "select speciality_code,english_descriptio"
				+ " from quaspc where in_use_flag = 'Y'"
				+ " and mk_qualification_c = '" + qualCode
				+ "'  order by speciality_code";

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				String code = data.get("SPECIALITY_CODE").toString();
				String desc = data.get("ENGLISH_DESCRIPTIO").toString();
				list.add(new org.apache.struts.util.LabelValueBean(code + " - " + desc, code + " - " + desc));
			}
		} catch (Exception ex) {
			throw new Exception(
					"AdditionsQueryDao : Error reading spec list / " + ex);
		}
		return list;
	}

	//2017 Edmund
	public boolean getQualFlag281(String studNo) throws Exception{
		  	
		//log.info("AdditionsQueryDao - getQualFlag281 - Student="+studNo);
		
		ResultSet rs;
		PreparedStatement preparedS = null;
		boolean result = false;
		
		String query = "select * from STADOC "
		   			+ " where fk_stuflgcode = 281 "
		   			+ " and fk_stunr = ? ";

	    try{ 
	    	//log.info("AdditionsQueryDao - getQualFlag281 query: " + query + ", Student: " + studNo);
	    	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query,new Object[] { studNo });
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				result = true;
			}
	    } catch (Exception ex) {
	    	throw new Exception("AdditionsQueryDao : Error reading Flag 281 / "+ ex);
	    }finally{
	    	try {
	  			if (preparedS != null){
	  				preparedS.close();
	  			}
	  		}catch (Exception ex) {
	  			//ex.printStackTrace();
	  			//log.debug("Unable to close connection (AdditionsQueryDao) - getQualFlag281 / "+ ex.getMessage());
	  		}
	    }
		return result;
	}
	
	//2014 Edmund
	public boolean isQualPhasedOut(String studentNr, String qualCode) throws Exception {
		boolean result = false;

		//First check if qual is for repeaters only.
		//log.info("AdditionsQueryDao -  isQualPhasedOut - StudentNr: " + studentNr + ", Qual: " + qualCode);
		
		String sql1 = "select code from grd "
					+ " where in_use_flag = 'Y' "
					+ " and repeaters_from_yea >0 "
					+ " and repeaters_from_yea <="+RegQueryDAO.getCurrentYear()
					+ " and code='" + qualCode + "'";
		
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql1);
	
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				result=true;
	        }
		} catch (Exception ex) {
			throw new Exception(
					"AdditionsQueryDao : Error reading qual phased out status / " + ex);
		}
		return result;
	}
	
	//2014 Edmund
	public boolean isSpecPhasedOut(String studentNr, String qualCode, String specCode) throws Exception {
		boolean result = false;
		String spec = " ";
		if (!"0".equals(specCode.trim()) && !"".equals(specCode.trim()) && specCode.trim() != null){
		    spec = specCode;
		}
		
		String sql1 =  "select MK_QUALIFICATION_C from quaspc "
					+ " where in_use_flag = 'Y' "
					+ " and MK_QUALIFICATION_C='" + qualCode + "' "
					+ " and SPECIALITY_CODE ='" + spec + "' "
					+ " and repeaters_from_yea >0 "
					+ " and repeaters_from_yea <="+RegQueryDAO.getCurrentYear();
		
		try{
	        JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql1);
	
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				result=true; 
	        }
		} catch (Exception ex) {
			throw new Exception(
					"AdditionsQueryDao : Error reading qual phased out status / " + ex);
		}
		return result;
	}
	
	
	/**
	 * This method returns a StudyUnit
	 *
	 * @param studyunit
	 *            A StudyUnit object
	 */
	public StudyUnit getStudyUnitInformation(StudyUnit studyUnit) throws Exception { 
		
		String query = "select language0,tutor_flag, work_integr_learn from sun where "
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
					"AdditionsQueryDao : Error reading odl/wil - " + ex);
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

			//log.debug(sql);

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
	public boolean isAdditionsPeriodValid(Qualification qual, RegDetailsForm form) throws Exception {
		boolean result = false;
		RegQueryDAO db = new RegQueryDAO();
		form.setRegPeriodOpen0("");
		form.setRegPeriodOpen1("");
		form.setRegPeriodOpen2("");
		form.setRegPeriodOpen6("");

		//log.debug("AdditionsQueryDAO - isAdditionsPeriodValid - QualType="+qual.getQualType());
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
			//Change Johanet 20181115 - Remove test and text for registration period 6 - Never set period to open
//			if (db.isRegPeriodValid("IA", "6")) {
//				form.setRegPeriodOpen6("Y");
//				result = true;
//			}
		} else if ("H".equalsIgnoreCase(qual.getQualType())) {
			if (db.isRegPeriodValid("HA", "0")) {
				// Hons additions OPEN
				form.setRegPeriodOpen0("Y");
				result = true;
			}
		}
		//log.debug("AdditionsQueryDAO - isAdditionsPeriodValid - Type="+qual.getQualType() + ", RegPeriodOpen0="+form.getRegPeriodOpen0());
		//log.debug("AdditionsQueryDAO - isAdditionsPeriodValid - Type="+qual.getQualType() + ", RegPeriodOpen1="+form.getRegPeriodOpen1());
		//log.debug("AdditionsQueryDAO - isAdditionsPeriodValid - Type="+qual.getQualType() + ", RegPeriodOpen2="+form.getRegPeriodOpen2());
		//log.debug("AdditionsQueryDAO - isAdditionsPeriodValid - Type="+qual.getQualType() + ", RegPeriodOpen6="+form.getRegPeriodOpen6());

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
			//log.debug("Additions delete solsun records for " + studentNr + ".");
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
			//log.debug("Additions delete stusun records for " + studentNr + ".");
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
		String sql = "insert into solsun (MK_STUDENT_NR,MK_STUDY_UNIT_CODE,SEMESTER_PERIOD,MK_OPTION_CODE,MK_LANGUAGE_CODE,CHANGE_TIMESTAMP,NDP_INDICATOR, WIL,ODL) values (?,?,?,?,?,SYSTIMESTAMP,?,?,?)";

		for (int i = 0; i < studyUnits.size(); i++) {
			StudyUnit su = new StudyUnit();
			su = (StudyUnit) studyUnits.get(i);
			if (su.getCode() != null && !"".equals(su.getCode())) {
				String lang = "E";
				try{
					if (su.getLanguage() != null
							&& !"".equals(su.getLanguage())
							&& su.getLanguage().length() >= 1
							&& "A".equalsIgnoreCase(su.getLanguage()
									.substring(0, 1))) {
						lang = "A";
					}
					Thread.sleep(Long.parseLong("1000"));
					jdt.update(sql, new Object[] { studentNr, su.getCode(), su.getRegPeriod(), "", lang, su.getNdp(),su.getWilAnswer(),su.getOdlAnswer() });
					//log.debug("Addition add solsun record " + su.getCode()+ " for " + studentNr + ".");
				}catch (Exception e) {
					log.info("Addition solsun add failed for: " + studentNr +  ", studyunit: " + su.getCode() + ", regperiod: " + su.getRegPeriod() + ", language: " + lang + ", Ndp:" + su.getNdp()+ ", Wil:" +su.getWilAnswer()+ ", Odl:" +su.getOdlAnswer());
				}
			}
		}
	}

	public void updateAcadRecordQual(String studentNr, String qualCode, String specCode, String newQualCode, String newSpecCode, String acaYear) {

		Log log = LogFactory.getLog(RegQueryDAO.class);
		String sql = "";

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		sql = "update stuaca set mk_qualification_c ='" + newQualCode + "', "
				+ " fk_speciality_code ='" + newSpecCode + "' "
				+ " where mk_student_nr =" + studentNr
				+ " and mk_qualification_c ='" + qualCode + "' "
				+ " and fk_speciality_code ='" + specCode + "' "
				+ " and academic_year = " + acaYear;

		int recordsAffected = 0;
		recordsAffected = jdt.update(sql);
		if (recordsAffected > 0) {
			// write audit trail
			RegQueryDAO dao = new RegQueryDAO();
			dao.writeAuditTrail(studentNr, "IA", "Qualupdate");
			//log.debug("Additions update stuaca Qual code for " + studentNr+ ".");
		}
	}
	
	public void updateAcadRecordSpec(String studentNr, String qualCode, String specCode, String acaYear) {

		Log log = LogFactory.getLog(RegQueryDAO.class);
		String sql = "";

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		if (specCode == null){
		  sql = "update stuaca "
				  + " set fk_speciality_code = null"
				  + " where mk_student_nr = " + studentNr
				  + " and mk_qualification_c = '" + qualCode + "'"
				  + " and academic_year = " + acaYear;
		}else{
		  sql = "update stuaca "
				  + " set a.fk_speciality_code = '" + specCode + "'"
				  + " where mk_student_nr = " + studentNr
				  + " and  mk_qualification_c = '" + qualCode + "'"
				  + " and academic_year = " + acaYear;
		}
		int recordsAffected = 0;
		recordsAffected = jdt.update(sql);
		if (recordsAffected > 0) {
			// write audit trail
			RegQueryDAO dao = new RegQueryDAO();
			dao.writeAuditTrail(studentNr, "IA", "Specupdate");
			//log.debug("Additions update stuaca Spec code for " + studentNr+ ".");
		}
	}

    public int getCredits(String qualCode, String suCode) throws Exception { 
        int result = 0;
        boolean checkResult = false;
        
        String query1 = "select NQF_CREDITS from sunwgt where MK_QUAL_CODE = ? and mk_study_unit_code = ?";
        String query2 = "select NQF_CREDITS from sun where code= ?";

        try {
        	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query1, 
					new Object[] { qualCode, suCode },
					new int[] { Types.VARCHAR, Types.VARCHAR });

			Iterator i = queryList.iterator();
			if(i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				if (data.get("NQF_CREDITS") != null){
					result = Integer.parseInt(data.get("NQF_CREDITS").toString().trim());
					checkResult = true;
				}
			}
			if (!checkResult){
				JdbcTemplate jdt2 = new JdbcTemplate(getDataSource());
				List queryList2 = jdt2.queryForList(query2, 
						new Object[] {suCode },
						new int[] { Types.VARCHAR });

				Iterator k = queryList2.iterator();
				if(k.hasNext()) {
					ListOrderedMap data2 = (ListOrderedMap) k.next();
					if (data2.get("NQF_CREDITS") != null){
						result = Integer.parseInt(data2.get("NQF_CREDITS").toString().trim());
					}
				}
            }
            
        }catch (Exception ex) {
     	   throw new Exception("Error occurred in RegistrationQueries - getCredits / "+ ex.getMessage() + " / "+ ex.getStackTrace() ,ex);
 		}
        return result;
    }
    
	public int getQueue(String studentNr, String acadYear, String acadPeriod) {

		int result = 0;
		
		String sql = "SELECT Count(SEQUENCE_NUMBER) as Count "
					+ " FROM STUREGQ "
					+ " WHERE MK_STUDENT_NR = ? "
					+ " AND MK_ACADEMIC_YEAR = ? "
					+ " AND SEMESTER_PERIOD = ? ";
		
		String sql2 = "SELECT Max(SEQUENCE_NUMBER) as SEQUENCE_NUMBER "
					+ " FROM STUREGQ "
					+ " WHERE MK_STUDENT_NR = ? "
					+ " AND MK_ACADEMIC_YEAR = ? "
					+ " AND SEMESTER_PERIOD = ? ";

		//log.debug("AdditionsQueryDAO - getQueue - SQL: " + sql);

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		
		List queryList = jdt.queryForList(sql, 
				new Object[] { Integer.parseInt(studentNr), Integer.parseInt(acadYear), Integer.parseInt(acadPeriod) },
				new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER });
		
		Iterator i = queryList.iterator();
		if (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			if (Integer.parseInt(data.get("Count").toString()) > 0){
				JdbcTemplate jdt2 = new JdbcTemplate(getDataSource());
				
				List queryList2 = jdt2.queryForList(sql2, 
						new Object[] { Integer.parseInt(studentNr), Integer.parseInt(acadYear), Integer.parseInt(acadPeriod) },
						new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER });

				Iterator j = queryList.iterator();
				if (j.hasNext()) {
				ListOrderedMap data2 = (ListOrderedMap) j.next();
				if (data2.get("SEQUENCE_NUMBER") != null){
					result = Integer.parseInt(data2.get("SEQUENCE_NUMBER").toString());
				}
				//log.debug("AdditionsQueryDAO - getQueue - studentNr="+studentNr+", acadYear="+acadYear+", acadPeriod="+acadPeriod+", result="+result);
				}
			}
			//log.debug("AdditionsQueryDAO - getQueue - studentNr="+studentNr+", acadYear="+acadYear+", acadPeriod="+acadPeriod+", result="+result);
		}
		
		return result;
	}
	
	public void writeQueue(String studentNr, String acadYear, String acadPeriod, String studyUnit, String qualCode, String shReason, int sequenceNr) {

		String sql = "INSERT INTO STUREGQ "
					+ " (MK_STUDENT_NR, MK_ACADEMIC_YEAR, SEMESTER_PERIOD, STUDY_UNIT_CODE, QUALIFICATION_CODE, "
					+ "  REGISTRATION_DATE, SOURCE_GC236, TYPE_GC237, SEQUENCE_NUMBER, WF_REASON_CODE_GC270, STATUS_CODE_GC271, "
					+ "  PROCESS_DATE, MK_USER_NUMBER, REGISTRATION_VERIFIED, REGISTRATION_AUTO_CHK)"
					+ " VALUES (?, ?, ?, ?, ?, SYSTIMESTAMP, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

		//log.debug("AdditionsQueryDAO - writeQueue - SQL: " + sql);

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int recordsAffected = jdt.update(sql, new Object[] { Integer.parseInt(studentNr), Integer.parseInt(acadYear), Integer.parseInt(acadPeriod), studyUnit, qualCode, 
														    "ADD", "WEB", sequenceNr, shReason , "IN", 
														    " 01/JAN/01", 0, " ", " " });

		if (recordsAffected == 0) {
			log.debug("AdditionsQueryDAO - writeQueue failed for " + studentNr);
		}
	}
    
}
