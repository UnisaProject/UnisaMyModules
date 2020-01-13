package za.ac.unisa.lms.tools.mdregistrations.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.mdregistrations.forms.Student;
import za.ac.unisa.lms.tools.mdregistrations.forms.StudyUnit;

@SuppressWarnings({"unused", "unchecked", "rawtypes"})
public class MdRegistrationsQueryDAO extends StudentSystemDAO {

	public static Log log = LogFactory.getLog(MdRegistrationsQueryDAO.class);

	/**
	 * This method returns a list of qualifications
	 *
	 * @param applyType
	 *            The application type : F = formal / S = Short courses
	 */

	public ArrayList getQualList(String applyType)
			throws Exception {
		ArrayList list = new ArrayList();
		String query = "";
		String formalUnisa = "select code,eng_description from grd where"
				+ " (type <> 'S' or code='00051')"
				+ " and in_use_flag = 'Y'"
				+ " and (to_year=0 or to_year>=2012)"
				+ " and (from_year=0 or from_year<=2012)"
				+ " and ((repeaters_only <>'Y' and repeaters_from_yea =0) or (repeaters_only <>'Y' and repeaters_from_yea is null) or (repeaters_from_yea >2012)) "
				+ " and UNDER_POST_CATEGOR in ('M','D')" 
				+ " and code not in ('0605X','08052','97837')"
				+ " order by eng_description";
		query = formalUnisa;
		
		try {
			//log.debug(query);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				String code = data.get("CODE").toString();
				String desc = data.get("ENG_DESCRIPTION").toString();
				list.add(new org.apache.struts.util.LabelValueBean(code + " - "	+ desc, code + desc));
			}
		} catch (Exception ex) {
			throw new Exception(
					"MDregistrationsQueryDAO : Error reading qual list / " + ex, ex);
		}
		return list;
	}

	/**
	 * This method returns a list of titles
	 */
	public ArrayList getTitles() throws Exception {
		ArrayList list = new ArrayList();
		String query = "select title from title where used_by='A' order by title";
		//log.debug(query);
		
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				list.add(new org.apache.struts.util.LabelValueBean(data.get("TITLE").toString(), data.get("TITLE").toString()));
			}
		} catch (Exception ex) {
			throw new Exception(
					"MDregistrationsQueryDAO : Error reading title list / " + ex, ex);
		}
		return list;
	}
	
	public Student getStudentann(String studentNr, String acadyear) throws Exception{
		String sql;
		String title;
		Student stu = new Student();
		title="";
		
		//get STUANN record for this year - should be created when applied for admission
		sql = "select mk_student_nr, mk_highest_qualifi, speciality_code , status_code from stuann " +
			  " where mk_student_nr = " + studentNr + 
		      " and mk_academic_year = " + acadyear ;
//		      " and status_code ='TN' ";
		try { 
			//log.debug("MDRegistrationsDAO - getStudentann - query"+sql+", studentNr="+studentNr+", acadyear="+acadyear);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			Iterator i = queryList.iterator();
			while (i.hasNext()){
				ListOrderedMap data = (ListOrderedMap) i.next();
				stu.setNumber(data.get("mk_student_nr").toString());
				stu.setMediaAccess1(data.get("mk_highest_qualifi").toString());
				stu.setMediaAccess2(data.get("speciality_code").toString());
				stu.setStatusCode(data.get("status_code").toString());
				stu.setAdmstatus("THISYEAR");
			}
			} catch (Exception ex) {
				throw new Exception("MDregistrationsQueryDAO : getStudent : Error occurred / "+ ex,ex);
		}
		String qcode=stu.getMediaAccess1();
		String scode=stu.getMediaAccess2();

		//if STUANN for this year not exits then must be registered student of previous year
		if (stu.getNumber()==null){
			sql = "select mk_student_nr, mk_highest_qualifi, speciality_code from stuann " +
				" where mk_student_nr = " + studentNr + 
				" and mk_academic_year = (" + acadyear+" -1)" +
				" and status_code in ('RG','TN') ";
			try { 
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(sql);
				Iterator i = queryList.iterator();
				while (i.hasNext()){
					ListOrderedMap data = (ListOrderedMap) i.next();
					stu.setNumber(data.get("mk_student_nr").toString());
					stu.setMediaAccess1(data.get("mk_highest_qualifi").toString());
					stu.setMediaAccess2(data.get("speciality_code").toString());
					stu.setAdmstatus("PREVRG");
				}
			} catch (Exception ex) {
				throw new Exception("MDregistrationsQueryDAO : getStudent : Error occurred / "+ ex,ex);
			}
		}
		// Read STU record here for student names validation (use lastStatus in interim)
		if (stu.getNumber()!=null){
			sql = "select surname, initials, first_names, mk_title, " +
			  " to_char(birth_date,'YYYYMMDD') as bdate, post_graduate_stud,  " +
			  " (select email_address from adrph where reference_no = "+ studentNr + 
			  " and fk_adrcatcode=1 ) as emailadr " +
			  " from stu where nr = " + studentNr;
		try { 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			Iterator i = queryList.iterator();
			while (i.hasNext()){
				ListOrderedMap data = (ListOrderedMap) i.next();
				stu.setBirthYear(data.get("bdate").toString().substring(0, 4));
				stu.setBirthMonth(data.get("bdate").toString().substring(4, 6));
				stu.setBirthDay(data.get("bdate").toString().substring(6, 8));
				stu.setSurname(data.get("surname").toString());
				stu.setFirstnames(data.get("first_names").toString());
				stu.setInitials(data.get("initials").toString());
				stu.setTitle(data.get("mk_title").toString());
				stu.setLastStatus(data.get("post_graduate_stud").toString());
				stu.setEmailAddress(data.get("emailadr").toString());
			}
			} catch (Exception ex) {
				throw new Exception("MdApplicationsQueryDAO : getStudentannual : Error occurred / "+ ex,ex);
			}
		}

		return stu;
	}
	
	public String getQualRules(String qual, String spes, Integer seqn) throws Exception{
		String sql;
		String ruleline=null;
		String seqno = (Integer.toString(seqn));
		
		sql = "select text0 from qsprul where mk_qualification_c="+ " '" + qual + "' " +
			" and mk_speciality_code="+ " '" + spes +"'  and seq_no=" + seqno;
		try { 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			Iterator i = queryList.iterator();
			while (i.hasNext()){
				ListOrderedMap data = (ListOrderedMap) i.next();
				ruleline=(data.get("text0").toString());
			}
		} catch (Exception ex) {
			throw new Exception("MDregistrationsQueryDAO : getStudent : Error occurred / "+ ex,ex);
		}
		return ruleline;
	}
	
	/**
	 * Validate student number
	 */
	public String validateStudent(String studentNr) throws Exception {
		String query = "";
		String mess = "";
		String numbr="";
		String phase="";
		String disp="";
		String libr="";
		String finbl="";
		String exfee="";
		String docs="";
		String postg="";
		String caochk="";
		String appst="";
		query="select nr, phased_out_status, disciplinary_incid, library_black_list, "
				+ " financial_block_fl, statutory_outstanding_flag, post_graduate_stud, cao_admission_chec, "
				+ " exam_fees_debt_fla, application_status "
				+ " from stu "
				+ " where nr = ? ";
		
		//log.debug("MDRegistrationsDAO - validateStudent - query: " + query+", studentNr="+studentNr); 
		
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object [] {studentNr});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				numbr=(data.get("nr").toString());
				phase=(data.get("phased_out_status").toString());
				disp=(data.get("disciplinary_incid").toString());
				libr=(data.get("library_black_list").toString());
				if (data.get("financial_block_fl") != null){
					finbl=(data.get("financial_block_fl").toString());
				}else{
					finbl="N";
				}
				if (data.get("statutory_outstanding_flag") != null){
					docs=(data.get("statutory_outstanding_flag").toString());
				}else{
					docs="N";
				}
				if (data.get("post_graduate_stud") != null){
					postg=(data.get("post_graduate_stud").toString());
				}else{
					postg="N";
				}
				if (data.get("exam_fees_debt_fla") !=null){
					exfee=(data.get("exam_fees_debt_fla").toString());
				}else{
					exfee="N";
				}
				if (data.get("cao_admission_chec") != null){
					caochk=(data.get("cao_admission_chec").toString());
				}else{
					caochk="N";
				}
				if (data.get("application_status") != null){
					appst=(data.get("application_status").toString());
				}else{
					appst="";
				}
			}else{
				mess="student number does not exist";
			}
		} catch (Exception ex) {
			throw new Exception(
					"MDregistrationsQueryDAO : Error validating student / " + ex, ex);
		}
		if (!"".equalsIgnoreCase(numbr) && numbr!=null){
			if ("1".equalsIgnoreCase(phase) || "2".equals(phase)){
				mess="PHASEOUT";
			}
			if (!"0".equalsIgnoreCase(disp)){
				mess="DISCIPLINARY";
			}
			if (!"0".equalsIgnoreCase(libr)){
				mess="LIBRARY";
			}
			if ("Y".equalsIgnoreCase(finbl)){
				mess="FINBLOCK";
			}
			if ("Y".equalsIgnoreCase(exfee)){
				mess="STUDYFEES";
			}
			if ("Y".equalsIgnoreCase(docs)){
				mess="DOCSFLAG";
			}
			if ("N".equalsIgnoreCase(postg)){
				mess="POSTGRADFLAG";
			}
			if ("N".equalsIgnoreCase(caochk)){
				mess="CAOCHK";
			}
			if ("AP".equalsIgnoreCase(appst)){
				mess="APPLICSTATUS";
			}
		}else{
			mess="NF";
		}
		return mess;
	}
	
	public String validateDocs(String studentNr) throws Exception {
		String query = "";
		String mess = "";
		String numbr="";
		query="select fk_stunr from stadoc where fk_stunr=" + studentNr +
		" and fk_stuflgcode > 500 and fk_stuflgcode < 530";
		//log.debug(query);
		
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				numbr=(data.get("fk_stunr").toString());
			}
		} catch (Exception ex) {
			throw new Exception(
					"MDregistrationsQueryDAO : Error validating student docs/ " + ex, ex);
		}
		if (!"".equalsIgnoreCase(numbr) && numbr!=null){
			mess="Your application to study at Unisa is incomplete. You either have admission documents outstanding or have not paid your application fee or your studies still need to be approved";
		}
		return mess;
	}
	
	public String getQualification(String qualcode) throws Exception {
		String query = "";
		String qdesc = "";

		query="select eng_description from grd where code='" + qualcode +"' ";

		//log.debug("MdRegistrationsQueryDAO - getQualification - query="+query+", qualcode=" + qualcode);
		
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				qdesc=(data.get("eng_description").toString());
			}
		} catch (Exception ex) {
			throw new Exception(
					"MDregistrationsQueryDAO : Error read qualification description/ " + ex, ex);
		}
		return qdesc;
	}
	
	public String validateQualification(String sQual) throws Exception {
		String query = "select type, under_post_categor , fk_katcode from grd where code=?";
		//log.debug(query);
		String code = "X";
		String code2 = "G";
		String code3 = "00";
		
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{sQual});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				if (data.get("UNDER_POST_CATEGOR") == null){
				}else{
					code = data.get("UNDER_POST_CATEGOR").toString();
					code2 = data.get("TYPE").toString();
					code3 = data.get("FK_KATCODE").toString();
					code = code + code2 + code3;
				}
				//return code;
			}else{
				//return code;
			}
		} catch (Exception ex) {
			throw new Exception(
					"MDregistrationsQueryDAO : Error validating qualification / " + ex, ex);
		}
		return code;
	}

	public String validateSpeciality(String sQual, String sSpes) throws Exception {
		String query = "";
		query= "select mk_qualification_c, speciality_code from quaspc " +
		" where mk_qualification_c='" + sQual + "' and speciality_code= '" + sSpes +"' ";
		//log.debug(query);
		String code = "T";
		
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				if (data.get("mk_qualification_c") == null){
				}else{
					code = "S";
				}
				//return code;
			}else{
				//return code;
			}
		} catch (Exception ex) {
			throw new Exception(
					"MDregistrationsQueryDAO : Error validating speciality / " + ex, ex);
		}
		return code;
	}

	public boolean validateClosingDate(String sYear, String regdatType) throws Exception {
		//log.debug("MdRegistrationsQueryDAO - studinput - DateCheck: " + sYear + ", regdatType: " + regdatType);
		// validateClosingDate(String sQual, String sYear) throws Exception {
		//		Int currentYear = (Calendar.getInstance().get(Calendar.YEAR));
		//      + "and substr(regdat.type,7,1) = grd.under_post_categor "
		String sQual="";
		
		String query="Select regdat.type from regdat " 
			+ " where regdat.type = ? "
			+ " and regdat.academic_year = ? " 
		    + " and trunc(sysdate) between regdat.from_date and regdat.to_date "; 
		//log.debug(query);
		//log.debug("MdRegistrationQuery - validateClosingDate - query="+query+", regdatType="+regdatType+", sYear="+sYear);
		
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object[]{regdatType,sYear });
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				return true;
			}else{
				return false;
			}
		} catch (Exception ex) {
			throw new Exception(
					"MDregistrationsQueryDAO : Error validating closing date" + sQual, ex);
		}
	}
	
	/**
	 * This method returns a list of languages
	 */
	public ArrayList getLanguages() throws Exception {
		ArrayList list = new ArrayList();
		String query = "select code, eng_description from tal order by eng_description";
		//log.debug(query);
		
		try {
			list = readDatabase(query);
		} catch (Exception ex) {
			throw new Exception(
					"MDregistrationsQueryDAO : Error reading language list / " + ex, ex);
		}
		return list;
	}
	
	/**
	 * This method returns a list of specialisations
	 */
	public ArrayList getSpesList(String sQual) throws Exception {
		ArrayList list = new ArrayList();
		String query1 = "select substr(trim(speciality_code)||'NVT',1,3)||'-'||mk_qualification_c as speciality_code ," +
		" substr(english_descriptio,1,60) as english_descriptio from quaspc" +
		" where in_use_flag = 'Y'" +
		" order by mk_qualification_c, english_descriptio";
		String query2  = "select substr(trim(speciality_code)||'NVT',1,3)||'-'||mk_qualification_c as speciality_code ," +
		" substr(english_descriptio,1,60) as english_descriptio " +
		" from quaspc, regdat " +
		" where in_use_flag = 'Y'" +
		" and mk_qualification_c = '" + sQual + "' " + 
		" and speciality_code <> ' ' " +
		" and (repeaters_from_yea =0 or repeaters_from_yea >2012) " +
		" and (from_year=0 or from_year<=2012) " +
		" and (to_year=0 or to_year>= 2012) " +
		" and regdat.academic_year = 2012 " +
		" and quaspc.app_open = regdat.type " +
		" and trunc(sysdate) between regdat.from_date and regdat.to_date " +
		" order by mk_qualification_c, english_descriptio";
		String query="";
		if ("00000".equalsIgnoreCase(sQual)){
		   query=query1;
		}else{
		   query=query2;
		}
		
		try {
		//log.debug(query);
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(query);
		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			String code = data.get("SPECIALITY_CODE").toString();
			String desc = data.get("ENGLISH_DESCRIPTIO").toString();
			list.add(new org.apache.struts.util.LabelValueBean(code + " - "	+ desc, code + desc));
			}
		} catch (Exception ex) {
			throw new Exception(
			"MDregistrationsQueryDAO : Error reading spes list / " + ex, ex);
		}
		return list;
	}

	/**
	 * This method returns a list of population groups
	 */
	public ArrayList getPopulationGroups() throws Exception {
		ArrayList list = new ArrayList();
		String query = "select code, eng_description from etn order by eng_description";
		//log.debug(query);

		try {
			list = readDatabase(query);
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDao : Error reading population group list / " + ex, ex);
		}
		return list;
	}

	/**
	 * This method returns a list of occupations
	 */
	public ArrayList getOccupations() throws Exception {
		ArrayList list = new ArrayList();
		String query = "select code, eng_description from brp where in_use_flag = 'Y'" +
        				" order by eng_description";
		//log.debug(query);

		try {
			list = readDatabase(query);
		} catch (Exception ex) {
			throw new Exception(
					"MDregistrationsQueryDAO : Error reading occupation list / " + ex, ex);
		}
		return list;
	}

	/**
	 * This method returns a list of economic sectors
	 */
	public ArrayList getEconomicSectors() throws Exception {
		ArrayList list = new ArrayList();
		String query = "select code, eng_description from eko where in_use_flag = 'Y'" +
        			   "order by eng_description";
		//log.debug(query);

		try {
			list = readDatabase(query);
		} catch (Exception ex) {
			throw new Exception(
					"MDregistrationsQueryDAO : Error reading economic sectors list / " + ex, ex);
		}
		return list;
	}

	public ArrayList getProvinces() throws Exception {
		ArrayList list = new ArrayList();
		String query = "select code, eng_description from prv order by code";
		log.debug(query);
		try {
			list = readDatabase(query);
		} catch (Exception ex) {
			throw new Exception(
					"MDregistrationsQueryDAO : Error reading province list / " + ex, ex);
		}
		return list;
	}

	public ArrayList getPrevActivity() throws Exception {
		ArrayList list = new ArrayList();
		String query = "select code, eng_description from gencod where in_use_flag = 'Y' " +
        			   "and fk_gencatcode=87 order by eng_description";
		//log.debug(query);

		try {
			list = readDatabase(query);
		} catch (Exception ex) {
			throw new Exception(
					"MDregistrationsQueryDAO : Error reading previous activity list / " + ex, ex);
		}
		return list;
	}

	/**
	 * This method returns a list of disabilities
	 */
	public ArrayList getDisabilities() throws Exception {
		ArrayList list = new ArrayList();
		String query = "select code, eng_description from distype order by eng_description";
		//log.debug(query);

		try {
			list = readDatabase(query);
		} catch (Exception ex) {
			throw new Exception(
					"MDregistrationsQueryDAO : Error reading disability list / " + ex, ex);
		}
		return list;
	}

	/**
	 * This method returns a list of disabilities
	 */
	public ArrayList getOtherUniversities() throws Exception {
		ArrayList list = new ArrayList();
		String query = "Select code, eng_name from UNK where "+
					   " (TYPE = 'UNIV' or TYPE='TECH') and IN_USE_FLAG = 'Y'" +
					   " Order by ENG_NAME";
		//log.debug(query);

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				list.add(new org.apache.struts.util.LabelValueBean(data.get("ENG_NAME").toString(), data.get("CODE").toString()+"@"+data.get("ENG_NAME").toString()));
			}
		} catch (Exception ex) {
			throw new Exception(
					"MDregistrationsQueryDAO : Error reading other university list / " + ex, ex);
		}
		return list;
	}

	/**
	 * This method returns a list of countries
	 */
	public ArrayList getCountries() throws Exception {
		ArrayList list = new ArrayList();
		String query = "select code, eng_description from lns where in_use_flag = 'Y'" +
					   " order by eng_description";
		//log.debug(query);

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				list.add(new org.apache.struts.util.LabelValueBean(data.get("ENG_DESCRIPTION").toString(), data.get("CODE").toString()+data.get("ENG_DESCRIPTION").toString()));
			}
		} catch (Exception ex) {
			throw new Exception(
					"MDregistrationsQueryDAO : Error reading country list / " + ex, ex);
		}
		return list;
	}

	/**
	 * This method returns a list of examination centres
	 */
	public ArrayList getExaminationCentreList() throws Exception {
		ArrayList list = new ArrayList();
		String query = "select code, eng_description from eks "
					+ " where in_use_flag = 'Y' "
					+ " and type = 'F' "
					+ " and code <> '99899' "
					+ " order by eng_description";
		//log.debug(query);

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				list.add(new org.apache.struts.util.LabelValueBean(data.get("ENG_DESCRIPTION").toString()+" - "+data.get("CODE").toString(), data.get("CODE").toString()+data.get("ENG_DESCRIPTION").toString()));
			}
		} catch (Exception ex) {
			throw new Exception(
					"MDregistrationsQueryDAO : Error reading exam list / " + ex, ex);
		}
		return list;
	}

	public StudyUnit getStudyUnitInfoForOpenRegPeriod(String studyUnitCode,
			String regPeriod1, String regPeriod2, String regPeriod0, String ayear) throws Exception {

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
		regPeriods = regPeriods.substring(0, regPeriods.length() - 1) + ")";

		String sql = " SELECT p.semester_period" + " FROM sunpdt p"
				+ " WHERE p.fk_suncode = '" + studyUnitCode + "'"
				+ " AND p.mk_academic_year = " + ayear
				+ " AND p.mk_academic_period = 1"
				+ " AND p.registration_allow = 'Y'" + regPeriods;
		log.debug(sql);

		// set genereral study unit information
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
					"MDregistrationsQueryDAO: Error getStudyUnitForOpenRegPeriod/"  +ex, ex);
		}

		return studyUnit;
	}
	
	
	/**
	 * Retrieves the student's registered study units list
	 * 
	 * @param studentNumber		The Student number
	 * @param academicYear		The current academic year
	 * @param statusCode		Registration status code
	 * @return
	 * 		List<StudyUnit>		Study unit list
	 * @throws Exception 
	 */
	public List<StudyUnit> getCurrentRegStudyUnitsList(String studentNumber, String academicYear, String statusCode) throws Exception
	{
		List<StudyUnit> currentRegStudyUnits =  new ArrayList<StudyUnit>();
		StudyUnit studyUnit = null;
		
		String query = "Select stusun.mk_study_unit_code, sun.eng_short_descript , " +
						"stusun.status_code, stusun.language_code, " +
						"gencod.eng_description statusDesc , " +
						"tal.eng_description languageDesc " +
						"from stusun , sun,  gencod, tal " +
						"where stusun.fk_student_nr = ? and " +
						"stusun.fk_academic_year = ? and " +
						"stusun.fk_academic_period = '1' and " +
						"stusun.status_code = ? and " +
						"stusun.mk_study_unit_code = sun.code and " +
						"gencod.code =  stusun.status_code and " +
						"gencod.fk_gencatcode = '18' and " +
						"stusun.language_code = tal.code";
		log.info(query);	
		//log.debug("MDRegistrationsDAO - getCurrentRegStudyUnitsList - query: " + query);
		
		List list = getLanguages();
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(
				query, new Object []{studentNumber, academicYear, statusCode});
		
		Iterator it = queryList.iterator();
		
		while(it.hasNext())
		{
			ListOrderedMap data = (ListOrderedMap) it.next();
			studyUnit = new StudyUnit();
			
			studyUnit.setDesc(data.get("ENG_SHORT_DESCRIPT").toString());
			studyUnit.setCode(data.get("MK_STUDY_UNIT_CODE").toString());
			studyUnit.setStatus(data.get("STATUS_CODE").toString());
			studyUnit.setStatusDesc(data.get("STATUSDESC").toString());
			studyUnit.setLanguage(data.get("LANGUAGE_CODE").toString());
			studyUnit.setLanguageDesc(data.get("LANGUAGEDESC").toString());
			
			currentRegStudyUnits.add(studyUnit);
		}
				
		return currentRegStudyUnits;
	}

	public ArrayList getSpecificSUList(String qualCode, String specCode, boolean postGrad, String ayear) throws Exception {

		ArrayList list = new ArrayList();
		
		MdRegistrationsQueryDAO db = new MdRegistrationsQueryDAO();
		
		String regPeriods = " and SUNPDT.SEMESTER_PERIOD in (";
		String orderByClause = "";
		StudyUnit su = new StudyUnit();

		try {
			String type = "PT"; /* undergrad =IA*/ 
			if (postGrad) {
				type = "PT"; /* postgrad =HA*/
			}
			// Setup reg periods that are currently open
			if (db.isRegPeriodValid(type, "0", ayear)) {
				regPeriods = regPeriods + "0,";
			}
			if (db.isRegPeriodValid(type, "1", ayear)) {
				regPeriods = regPeriods + "1,";
			}
			if (db.isRegPeriodValid(type, "2", ayear)) {
				regPeriods = regPeriods + "2,";
			}
			if (db.isRegPeriodValid(type, "6", ayear)) {
				regPeriods = regPeriods + "6,";
			}
			else
			{
				regPeriods = regPeriods + "0,";
			}
			
			regPeriods = regPeriods.substring(0, regPeriods.length() - 1) + ")";

			// Setup order by clause
			orderByClause = " ORDER BY code";

			String sql = "SELECT DISTINCT SUNPDT.FK_SUNCODE as code,"
					+ "SUN.ENG_SHORT_DESCRIPT" + " from qspsun, sunpdt,sun";
			/* read spec code only if spec code not empty */
			if (specCode == null || "".equalsIgnoreCase(specCode.trim())) {
				sql = sql + " where ";
			} else {
				sql = sql + " where qspsun.mk_spes_code ='" + specCode
						+ "' and ";
			}
			sql = sql + "qspsun.mk_qual_code = '" + qualCode + "'"
					+ " and qspsun.old_code = 'N'"
					+ " and sunpdt.mk_academic_year = " + ayear 
					+ " and sunpdt.mk_academic_period=1"
					+ " and SUNPDT.REGISTRATION_ALLOW = 'Y'"
					+ " and sunpdt.fk_suncode = qspsun.mk_study_unit_code"
					+ " and SUNPDT.FK_SUNCODE = SUN.CODE" + regPeriods
					+ orderByClause;
			log.info(sql);
			//log.debug("MDRegistrationsDAO - getSpecificSUList - query: " + sql);
			
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				// setup record for this study unit
				su = new StudyUnit();
				su.setDesc(data.get("ENG_SHORT_DESCRIPT").toString());
				su.setCode(data.get("CODE").toString());
				list.add(su);
			}

		} catch (Exception ex) {
			throw new Exception(
					"MDregistrationsQueryDAO : Error reading getSpecificRegSUList"
							+ ex, ex);
		}

		return list;
	}

	public boolean isRegPeriodValid(String type,String addPeriod, String ayear) throws Exception{

		boolean result = false;
		String toDate = "";

		String query1 = "select to_char(to_date, 'YYYY-MM-DD') as to_date from regdat where academic_year="+ayear+" and type='"+type+"' and semester_period="+addPeriod;
		String query2 = "select to_char(to_date, 'YYYY-MM-DD') as to_date from regdat where academic_year="+ayear+" and type='"+type+"' and semester_period="+addPeriod+" and trunc(sysdate) between from_date and to_date";
		String query3 = "select to_char(to_date, 'YYYY-MM-DD') as to_date from regdat where academic_year="+ayear+" and type='"+type+"' and semester_period="+addPeriod+" and trunc(sysdate)>trunc(from_date)";

		try{
			//Log log = LogFactory.getLog(RegQueryDAO.class);
			toDate = this.querySingleValue(query1,"TO_DATE");

			if (!"".equalsIgnoreCase(toDate)) {
				// If record exists default to false
				result = false;
				toDate="";
				if ("0001-01-01".equalsIgnoreCase(toDate)){
					toDate = this.querySingleValue(query3,"TO_DATE");
				}else{
					toDate = this.querySingleValue(query2,"TO_DATE");
				}
				if (!"".equalsIgnoreCase(toDate)) {
					result = true;
				}
			}

			log.debug("Registration period "+addPeriod+" = "+ Boolean.toString(result));
		}catch(Exception ex){
			throw new Exception("MDregistrationsQueryDAO : Error validating additions period / "+ ex,ex);
		}

		return result;
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
					"AdditionsQueryDao : Error reading study unit language/ "
							+ ex, ex);
		}

		return desc.trim();
	}

	/**Edmund Added 2015**/
	/**
	 * This method returns a list of postal codes for the specified parameters
	 * 
	 * @param suburb
	 * 			The suburb
	 * @param town
	 * 			The town
	 * @return
	 * 		List of postal codes for the specified parameters
	 * @throws Exception
	 */
	public ArrayList<LabelValueBean> getPostalCodes(String suburb, String town) throws Exception {
		ArrayList<LabelValueBean> list = new ArrayList<LabelValueBean>();
		String query = "Select code , type , suburb, town from pstcod " +
						"where suburb like ? and town like ? order by code";
	
		
		String suburbParam = (suburb == null || suburb.trim()=="")? 
								suburbParam = "%" : 
								suburb.toUpperCase() + "%";
		String townParam = (town == null || town.trim()=="")?
							townParam = "%":
							town.toUpperCase() + "%";
				
		if ((suburb == null || suburb.trim()=="")&&
			(town == null || town.trim()==""))
		{
			return list;
		}
		//log.debug(query);

		try {
			String label = null;
			String descr = null;
			
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{suburbParam, townParam});
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();

				String code = String.format("%04d", Integer.valueOf(data.get("CODE").toString()));
				label = code + " " + data.get("TYPE").toString();
				descr = data.get("SUBURB").toString()+ ": " + data.get("TOWN").toString();
				list.add(new LabelValueBean(label + " " + descr, label + " " + descr));
			}
		} catch (Exception ex) {
			throw new Exception(
					"MDregistrationsQueryDAO : Error reading postal codes list / " + ex);
		}
		return list;
	}

	/** Edmund Added 2015 **/
    /********** Update adr to update courier address *************/
    public boolean writeAddressCourier(Student student) throws Exception {

    	boolean result = false;
    	ResultSet rs;
    	ArrayList list = new ArrayList();
    	Connection connection =null;

    	Vector adr = new Vector();
    	int postalCode = 0;
    	String AdrLine1 = "";
    	String AdrLine2 = "";
    	String AdrLine3 = "";
    	String AdrLine4 = "";
    	String AdrLine5 = "";
    	String AdrLine6 = "";
    	
    	if (student.getCourierAddress().getLine1() == null || "".equals(student.getCourierAddress().getLine1())){
    		AdrLine1 = " ";
    	}else{
    		AdrLine1 = (student.getCourierAddress().getLine1().toString()).toUpperCase();
    	}
    	if (student.getCourierAddress().getLine2() == null || "".equals(student.getCourierAddress().getLine2())){
    		AdrLine2 = " ";
    	}else{
    		AdrLine2 = (student.getCourierAddress().getLine2().toString()).toUpperCase();
    	}
    	if (student.getCourierAddress().getLine3() == null || "".equals(student.getCourierAddress().getLine3())){
    		AdrLine3 = " ";
    	}else{
    		AdrLine3 = (student.getCourierAddress().getLine3().toString()).toUpperCase();
    	}
    	if (student.getCourierAddress().getLine4() == null || "".equals(student.getCourierAddress().getLine4())){
    		AdrLine4 = " ";
    	}else{
    		AdrLine4 = (student.getCourierAddress().getLine4().toString()).toUpperCase();
    	}
    	if (student.getCourierAddress().getLine5() == null || "".equals(student.getCourierAddress().getLine5())){
    		AdrLine5 = " ";
    	}else{
    		AdrLine5 = (student.getCourierAddress().getLine5().toString()).toUpperCase();
    	}
    	if (student.getCourierAddress().getLine6() == null || "".equals(student.getCourierAddress().getLine6())){
    		AdrLine6 = " ";
    	}else{
    		AdrLine6 = (student.getCourierAddress().getLine6().toString()).toUpperCase();
    	}
    	// courier
   	  	if(!"".equals(student.getCourierAddress().getAreaCode())){
   	  		postalCode = Integer.parseInt(student.getCourierAddress().getAreaCode());
   	  	}


   	  	try {

    	  // read address
    	  String query1 = "select * from adr "
        				+ " where adr.REFERENCE_NO=? " 
        				+ " and adr.FK_ADRCATYPFK_ADRC= 1 "
        				+ " and adr.FK_ADRCATYPFK_ADRT = 7";
        
    	  String query2 = "update adr "
                  		+ " set ADDRESS_LINE_1=?, "
                  		+ " ADDRESS_LINE_2=?, "
                  		+ " ADDRESS_LINE_3=?, "
                  		+ " ADDRESS_LINE_4=?, "
                  		+ " ADDRESS_LINE_5=?, "
                  		+ " ADDRESS_LINE_6=?, "
                  		+ " POSTAL_CODE=?, "
                  		+ " USER_CODE=? "
                  		+ " where REFERENCE_NO=? "
                  		+ " and FK_ADRCATYPFK_ADRC= 1 "
                  		+ " and FK_ADRCATYPFK_ADRT =? ";

    	  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
    	  List queryList = jdt.queryForList(query1, new Object []{student.getNumber()});
    	  Iterator i = queryList.iterator();
    	  if (i.hasNext()) {
    		  //log.debug("MDRegistrationsDAO - writeAddressCourier - Previous Line Found: " + query1);
	          //*************************
	          // record exist, do update
	          // ************************
          
	          connection = jdt.getDataSource().getConnection();
	          connection.setAutoCommit(false);			   
			   
	          //log.debug("MDRegistrationsDAO - writeAddressCourier - Update: " + query2);
	          JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			  jdt1.update(query2,new Object[]  {AdrLine1, AdrLine2, AdrLine3, AdrLine4, AdrLine5, AdrLine6, postalCode, 9999, Integer.parseInt(student.getNumber()), 7});			   
			  result = true;
          
    	  }else{
	          //*************************
	          // record not found, insert
	          //*************************
	
    		  //log.debug("MDRegistrationsDAO - writeAddressCourier - Previous Line NOT Found: " + query1);
	
	          String query3 = "insert into adr "
		                  	+ " (REFERENCE_NO, ADDRESS_LINE_1, ADDRESS_LINE_2, ADDRESS_LINE_3, "
		                  	+ " ADDRESS_LINE_4, ADDRESS_LINE_5, ADDRESS_LINE_6, POSTAL_CODE, "
		                  	+ " FK_ADRCATYPFK_ADRC, FK_ADRCATYPFK_ADRT, ERROR_CODE, USER_CODE) "
		                  	+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
	          
	          //log.debug("MDRegistrationsDAO - writeAddressCourier - Insert: " + query3);
	                    
	          JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			  jdt1.update(query3,new Object[]  {Integer.parseInt(student.getNumber()), AdrLine1, AdrLine2, AdrLine3, AdrLine4, AdrLine5, AdrLine6, postalCode, 1, 7, 0, 9999});			   
			  result = true;
    	  }
          
  		}catch (Exception ex) {
  			throw new Exception("MDRegistrationsDAO : Data has been rollback, Error updating Courier Address / " + ex,ex);
  		}
   	  	return result;
    }

	/** Edmund Added 2015 **/
    /********** Update adrph to update courier contact number *************/
    public boolean writeAddressContactNr(Student student) throws Exception {

    	boolean result = false;
    	Connection connection =null;

    	// courier
   	  	String valueChanged = "courNr-upd";

   	  	try {

	    	  String query = "update adrph "
	    			  		+ " set courier_contact_no =? "
	    			  		+ " where reference_no = ? "
	    			  		+ " and fk_adrcatcode=1";
	          
	    	  JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			  jdt1.update(query,new Object[]  {student.getContactNr(),Integer.parseInt(student.getNumber())});			   
			  result = true;

  		}catch (Exception ex) {
			throw new Exception("MDRegistrationsDAO : Data has been rollback, Error updating Courier Contact Nr / " + ex,ex);
  		}
   	  	return result;
    }

    public void writeChangedStatusToDb(String valueChanged,String studNr,String changeCode) throws Exception{
   	 ResultSet rs;
   	 boolean success = false;
   	 int tries = 0;
      
   	 String query = "insert into stuchg (mk_student_nr, mk_user_code, change_code, timestamp, value_changed) values (?, ?, ?, sysdate, ?)";
   	 
   	 try{
   		 JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
   		 
		        
		       do {
			        try {
				        if (tries < 10 || !success){
				        	tries = tries +1;
				        	
					        // Wait one second as another audit trail could have been written just before this one
					        // resulting in the same timestamp, thus giving a uniqueness error on table
					        Thread.sleep(Long.parseLong("1000"));
					        
					        jdt1.update(query,new Object[]  {Integer.parseInt(studNr), 9999, changeCode, valueChanged});
					        success = true;
				        }else{
				        	success = true; //Forced Exit
				        }
			        } catch (Exception ex) {
			           success = false;
			        }
		       } while(!success);
		}catch (Exception ex) {
			throw new Exception("MDRegistrationsDAO : Data has been rollback, Error updating Audit Trail / " + ex,ex);
		} 
   }
    
    /** End 2015 Changes **/
    
	private ArrayList readDatabase(String query){
		ArrayList list = new ArrayList();

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(query);
		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			list.add(new org.apache.struts.util.LabelValueBean(data.get("eng_description").toString(), data.get("CODE").toString()+"@"+data.get("eng_description").toString()));
		}

		return list;
	}

}
