package za.ac.unisa.lms.tools.mdapplications.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.mdapplications.forms.MdApplicationsForm;
import za.ac.unisa.lms.tools.mdapplications.forms.Qualification;
import za.ac.unisa.lms.tools.mdapplications.forms.Student;
import za.ac.unisa.lms.tools.mdapplications.forms.MdPrev;
import za.ac.unisa.lms.tools.mdapplications.forms.StudentFile;

@SuppressWarnings({"rawtypes", 	"unchecked", "unused"})
public class MdApplicationsQueryDAO extends StudentSystemDAO {

	public static Log log = LogFactory.getLog(MdApplicationsQueryDAO.class);

	/**
	 * This method returns a list of qualifications
	 *
	 * @param applyType
	 *            The application type : F = formal / S = Short courses
	 */

	public ArrayList getQualList(String applyType, String sYear)
			throws Exception {
		ArrayList list = new ArrayList();
		String query = "";
		String formalUnisa = "select code,eng_description from grd where"
				+ " (type <> 'S' or code='00051')"
				+ " and in_use_flag = 'Y' "
				+ " and (to_year=0 or to_year>=" + sYear + ") "
				+ " and (from_year=0 or from_year<=" + sYear + ") "
				+ " and (repeaters_from_yea =0 or repeaters_from_yea > " +sYear +") "
				+ " and UNDER_POST_CATEGOR in ('M','D')"
				+ " and code not in ('0605X','08052','97837','98549','98550')"
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
					"MdApplicationsQueryDAO : Error reading qual list / " + ex, ex);
		}
		return list;
	}

	/**
	 * This method checks is a qualifications is SBL
	 *
	 * @param qualCode
	 */
	public boolean isSBLQual(String qualCode)
			throws Exception {
		
		boolean result = false;
		String query = "select mk_department_code "
					+ " from grd "
					+ " where mk_department_code = 5 "
					+ " and code = '"+qualCode+"'";
		try {
			//log.debug("isSBLQual - query="+query);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				result = true;
			}
		} catch (Exception ex) {
			throw new Exception(
					"MdApplicationsQueryDAO : Error reading SBL Qualification / " + ex);
		}
		return result;
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
					"MdApplicationsQueryDAO : Error reading title list / " + ex, ex);
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
					"MdApplicationsQueryDAO : Error reading province list / " + ex, ex);
		}
		return list;
	}

	public Student getStudentann(String studentNr, String acadyear) throws Exception{
		String sql;
		String title;
		Student stu = new Student();
		title="";
		sql = "select mk_student_nr, mk_highest_qualifi, speciality_code from stuann " +
				"where mk_student_nr = " + studentNr + " and mk_academic_year = " + acadyear;
		try {
			//log.debug("MdApplicationsQuery - getMDAPPLRecord - studentNr: " + studentNr + ", sql: " + sql);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			Iterator i = queryList.iterator();
			while (i.hasNext()){
				ListOrderedMap data = (ListOrderedMap) i.next();
				//log.debug("MdApplicationsQuery - getMDAPPLRecord - studentNr: " + studentNr + ", mk_highest_qualifi: " + data.get("mk_highest_qualifi").toString() + ", speciality_code: " + data.get("speciality_code").toString());
				stu.setNumber(data.get("mk_student_nr").toString());
				stu.setMediaAccess1(data.get("mk_highest_qualifi").toString());
				stu.setMediaAccess2(data.get("speciality_code").toString());
			}
		} catch (Exception ex) {
			throw new Exception("MdApplicationsQueryDAO : getStudent : Error occurred / "+ ex,ex);
		}

		return stu;
	}

	/**
	 * Validate student number
	 */
	public boolean validateStudent(String studentNr) throws Exception {
		String query = "select nr from stu where nr="+ studentNr;
		//log.debug(query);

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				return true;
			}else{
				return false;
			}
		} catch (Exception ex) {
			throw new Exception(
					"MdApplicationsQueryDAO : Error validating student / " + ex, ex);
		}
	}

	public String validateQualification(String sQual) throws Exception {
		String query = "select type, under_post_categor, fk_katcode "
					+ " from grd "
					+ " where code='" + sQual + "' "
					+ " and in_use_flag = 'Y' ";
		//log.debug(query);
		String code = "U";
		String code2 = "G";
		String code3 = "00";

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
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
				return code;
			}else{
				return code;
			}
		} catch (Exception ex) {
			throw new Exception(
					"MdApplicationsQueryDAO : Error validating student / " + ex, ex);
		}
	}

	public boolean validateClosingDate(String sYear, String dateSelect) throws Exception {

		boolean result = false;
		
		String query="Select regdat.type from regdat "
			+ " where regdat.type = ? "
			+ " and regdat.academic_year = ? "
		    + " and trunc(sysdate) between regdat.from_date and regdat.to_date ";
		
		//log.debug("MdApplicationsQueryDAO - validateClosingDate - query="+query+", sYear="+sYear+", dateSelect="+dateSelect);

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{dateSelect, sYear});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				result = true;
				//log.debug("MdApplicationsQueryDAO - validateClosingDate - dateSelect="+dateSelect+" = TRUE");
			}else{
				result = false;
			}
		} catch (Exception ex) {
			throw new Exception(
					"MdApplicationsQueryDAO : Error reading closing dates" + dateSelect +", "+sYear+" / "+ ex);
		}
		return result;
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
					"MdApplicationsQueryDAO : Error reading language list / " + ex, ex);
		}
		return list;
	}

	/**
	 * This method returns a list of specialisations
	 */
	public ArrayList getSpesList(String sQual, String sYear, boolean isAdmin) throws Exception {
		ArrayList list = new ArrayList();

		String wapString = " and trunc(sysdate) between regdat.from_date and regdat.to_date ";
		if (isAdmin){
			wapString = "    and quaspc.app_open like 'WAP%' " ;
		}
		
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
						" and (repeaters_from_yea =0 or repeaters_from_yea >"  +sYear + ")" +
						" and (from_year=0 or from_year<=" +sYear + ")" +
						" and (to_year=0 or to_year>=" +sYear + ")" +
						" and regdat.academic_year =" +sYear +
						" and quaspc.app_open=regdat.type" +
						 wapString +
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
			"MdApplicationsQueryDAO : Error reading spes list / " + ex, ex);
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
					"MdApplicationsQueryDAO : Error reading occupation list / " + ex, ex);
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
					"MdApplicationsQueryDAO : Error reading economic sectors list / " + ex, ex);
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
					"MdApplicationsQueryDAO : Error reading previous activity list / " + ex, ex);
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
					"MdApplicationsQueryDAO : Error reading disability list / " + ex, ex);
		}
		return list;
	}

	/**
	 * This method returns a list of Interest Areas
	 */
	public ArrayList getInterestAreas(String qualCode, String specCode) throws Exception {
		ArrayList list = new ArrayList();

		if (("".equals(specCode)) || ("NVT".equalsIgnoreCase(specCode))){
			specCode = " ";
		}
		String query = "select area_of_interest as CODE, area_of_interest as ENG_DESCRIPTION  from qspint "
					+ " where mk_qual_code = '" + qualCode + "' "
					+ " and mk_spes_code = '" + specCode + "' "
					+ " order by seq_nr ";

		//log.debug(query);
		//log.debug("MdApplicationsQueryDAO - Area of Interest - query: " + query);
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();
			//log.debug("Area of Interest - In Try");
			while (i.hasNext()) {
				//log.debug("Area of Interest - In While");
				ListOrderedMap data = (ListOrderedMap) i.next();
				//log.debug("Area of Interest: " + data.get("ENG_DESCRIPTION").toString());
				list.add(new org.apache.struts.util.LabelValueBean(data.get("CODE").toString(), data.get("ENG_DESCRIPTION").toString()));
			}

			//list = readDatabase(query);
		} catch (Exception ex) {
			throw new Exception(
					"MdApplicationsQueryDAO : Error reading Interest Area list / " + ex, ex);
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
					"MdApplicationsQueryDAO : Error reading other university list / " + ex, ex);
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
					"MdApplicationsQueryDAO : Error reading country list / " + ex, ex);
		}
		return list;
	}

	/**
	 * This method returns a list of examination centres
	 */
	public ArrayList getExaminationCentreList() throws Exception {
		ArrayList list = new ArrayList();
		String query = "select code, eng_description from eks where in_use_flag = 'Y'" +
        			   " and type = 'F' order by eng_description";
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
					"MdApplicationsQueryDAO : Error reading exam list / " + ex, ex);
		}
		return list;
	}


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

	/**
	 * This method returns a list of disabilities
	 */
	public String getNextDummyStudentNr() throws Exception {
		String dummyNr ="";

		String query = "Select dummyStudnr_seq.nextval dummyNr from dual";
		//log.debug(query);

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				dummyNr = data.get("DUMMYNR").toString();
			}
		} catch (Exception ex) {
			throw new Exception(
					"MdApplicationsQueryDAO : Error reading other university list / " + ex, ex);
		}
		return dummyNr;
	}

	public Student getMDrecord(String studentNr) throws Exception{
		String sql;
		String title;
		Student stu = new Student();
		title="";

		sql = "select mk_student_nr, app_sequence_nr, mk_qualification_c, speciality_code, " +
			  "admission_status, interest_area, proposed_title , to_char(application_date,'YYYYMMDD') as apppyr, " +
			  "lecturer_name, passed_ndp, prev_apply_md, prev_apply_qual " +
			  "from mdappl where mk_student_nr = " + studentNr +
			  " order by app_sequence_nr";
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			Iterator i = queryList.iterator();
			while (i.hasNext()){
				ListOrderedMap data = (ListOrderedMap) i.next();
				stu.setNumber(data.get("mk_student_nr").toString());
				stu.setSeqnr(data.get("app_sequence_nr").toString());
				stu.setAdmstatus(data.get("admission_status").toString());
				stu.setApplYear(data.get("apppyr").toString());
				if(data.get("interest_area") !=null){
					stu.setInterestArea(data.get("interest_area").toString());
					//log.debug("DAO getMDRecord NOT NULL: " + stu.getInterestArea());
				}else{
					stu.setInterestArea("");
					//log.debug("DAO getMDRecord NULL: " + stu.getInterestArea());
				}
				if(data.get("proposed_title") !=null){
					stu.setResearchTopic(data.get("proposed_title").toString());
				}else{
					stu.setResearchTopic("");
				}
				//new 2013
				if(data.get("lecturer_name") !=null){
					stu.setLecturer(data.get("lecturer_name").toString());
				}else{
					stu.setLecturer("");
				}
				if(data.get("passed_ndp") !=null){
					stu.setPassedndp(data.get("passed_ndp").toString());
				}else{
					stu.setPassedndp("");
				}
				if(data.get("prev_apply_md") !=null){
					stu.setAppliedmd(data.get("prev_apply_md").toString());
				}else{
					stu.setAppliedmd("");
				}
				if(data.get("prev_apply_qual") !=null){
					stu.setAppliedqual(data.get("prev_apply_qual").toString());
				}else{
					stu.setAppliedqual("");
				}
			}
		} catch (Exception ex) {
			throw new Exception("MdApplicationsQueryDAO : getStudent : Error occurred / "+ ex,ex);
		}

		return stu;
	}
	
	public Student getMDAPPLRecord(String studentNr) throws Exception{
		String sql;
		Student stu = new Student();
		MdApplicationsForm mdForm = new MdApplicationsForm();

		sql = "select mk_student_nr, mk_qualification_c, speciality_code, " +
			  " admission_status " +
			  " from mdappl where mk_student_nr = ? " +
			  " and rownum = 1 " +
			  " order by app_sequence_nr desc";
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql, new Object []{studentNr});
			Iterator i = queryList.iterator();
			while (i.hasNext()){
				ListOrderedMap data = (ListOrderedMap) i.next();
				//log.debug("MdApplicationsQuery - getMDAPPLRecord - studentNr: " + studentNr + ", admission_status: " + data.get("admission_status").toString() + ", mk_qualification_c: " + data.get("mk_qualification_c").toString() + ", speciality_code: " + data.get("speciality_code").toString());
				stu.setAdmstatus(data.get("admission_status").toString());
				stu.setMediaAccess1(data.get("mk_qualification_c").toString());
				stu.setMediaAccess2(data.get("speciality_code").toString());
			}
		} catch (Exception ex) {
			throw new Exception("MdApplicationsQueryDAO : getMDAPPLRecord : Error occurred / "+ ex,ex);
		}
		return stu;
	}
	

	public Student getMDrecordOLD(String studentNr, String qcode, String scode) throws Exception{
		//only used when read if application exist for documents
		
		//log.debug("MdApplicationsQuery - getMDrecordOLD - studentNr: " + studentNr + ", qcode: " + qcode + ", scode: " + scode);
		String sql;
		String sql2;
		String title;
		Student stu = new Student();
		title="";

		if("".equals(scode)){
			scode=" ";
		}

		sql = "select mk_student_nr, app_sequence_nr, mk_qualification_c, speciality_code, " +
			  " admission_status, interest_area, proposed_title " +
			  " from mdappl where mk_student_nr = " + studentNr +
			  " and mk_qualification_c = '" + qcode + "' " +
			  " and speciality_code = '" + scode + "' " +
			  " and admission_status in ('I','O') " +
			  " order by app_sequence_nr";
		try {
			//log.debug("MdApplicationsQuery - getMDrecordOLD - studentNr: " + studentNr + ", sql: " + sql);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			Iterator i = queryList.iterator();
			while (i.hasNext()){
				ListOrderedMap data = (ListOrderedMap) i.next();
				//log.debug("MdApplicationsQuery - getMDrecordOLD - studentNr: " + studentNr + ", I/O Record found");
				stu.setNumber(data.get("mk_student_nr").toString());
				stu.setSeqnr(data.get("app_sequence_nr").toString());
				stu.setAdmstatus(data.get("admission_status").toString());
				if(data.get("interest_area") !=null){
					stu.setInterestArea(data.get("interest_area").toString());
					//log.debug("DAO getMDRecordOLD NOT NULL: " + stu.getInterestArea());
				}else{
					stu.setInterestArea("");
					//log.debug("DAO getMDRecordOLD NULL: " + stu.getInterestArea());
				}
				if(data.get("proposed_title") !=null){
					stu.setResearchTopic(data.get("proposed_title").toString());
				}else{
					stu.setResearchTopic("");
				}
			}
		} catch (Exception ex) {
			throw new Exception("MdApplicationsQueryDAO : getStudent : Error occurred / "+ ex,ex);
		}

		if (stu.getNumber()==null){
			//log.debug("MdApplicationsQuery : getMDRecordOLD IS NULL - studentNr: " + studentNr + "- Check for any MDAPPL Record");
			sql2 = "select mk_student_nr, app_sequence_nr, mk_qualification_c, speciality_code, " +
			  " admission_status, interest_area, proposed_title " +
			  " from mdappl where mk_student_nr = " + studentNr +
			  " and (mk_qualification_c = '" + qcode + "' or applied_qual = '" + qcode + "' ) " +
			  " order by app_sequence_nr";
			try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql2);
			//log.debug("MdApplicationsQuery - getMDrecordOLD - studentNr: " + studentNr + ", sql2="+sql2);
			Iterator i = queryList.iterator();
			while (i.hasNext()){
				ListOrderedMap data = (ListOrderedMap) i.next();
				//log.debug("MdApplicationsQuery : getMDRecordOLD IS NULL - studentNr: " + studentNr + " - Record Found");
				stu.setNumber(data.get("mk_student_nr").toString());
				stu.setSeqnr(data.get("app_sequence_nr").toString());
				if(data.get("interest_area") !=null){
					stu.setInterestArea(data.get("interest_area").toString());
					//log.debug("DAO getMDRecordOLD StuNr = NULL and Int NOT NULL: " + stu.getInterestArea());
				}else{
					stu.setInterestArea("");
					//log.debug("DAO getMDRecordOLD StuNr = NULL and Int NULL: " + stu.getInterestArea());
				}
				if(data.get("proposed_title") !=null){
					stu.setResearchTopic(data.get("proposed_title").toString());
				}else{
					stu.setResearchTopic("");
				}
				stu.setAdmstatus(data.get("admission_status").toString());
			}
			} catch (Exception ex) {
			throw new Exception("MdApplicationsQueryDAO : getStudent : Error occurred / "+ ex,ex);
			}
		}
		//log.debug("MdApplicationsQuery : getMDRecordOLD - studentNr: " + studentNr + " - STU="+stu.getNumber());
		return stu;
	}

	public void insertMDhistory(String studentNr, String appseqnr, List<MdPrev> mdprev) throws Exception{
		String sql;

		sql = "delete mdprev where mk_student_nr = " + studentNr +
			" and app_sequence_nr = " + appseqnr;
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int result = jdt.update(sql);
		} catch (Exception ex) {
			throw new Exception(
					"MdApplicationsQueryDAO : Error updating previous academic / " + ex, ex);
		}
		final String stunr = studentNr;
		final String seqnr = appseqnr;
		final List<MdPrev> prev = mdprev;
		final String sql2 = "insert into mdprev " +
			  "(mk_student_nr, app_sequence_nr, prev_sequence_nr, prev_qual, " +
              "year_completed, institution, accredit_source) " +
            //  "values(dummyStudnr_seq.nextval,?,?,?,?,?,?,?,?,?,"+
              "values(?,?,?,?,?,?,?)";

		for (int i = 0; i < prev.size(); i++) {
			final int index = i +1;
			final int rowIndex = i;
			if(!"".equalsIgnoreCase(prev.get(rowIndex).getHistUniv())){

				try{
					JdbcTemplate jdt = new JdbcTemplate(getDataSource());
					jdt.update( new PreparedStatementCreator() {
						public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
							PreparedStatement ps = conn.prepareStatement(sql2);
							ps.setString(1,stunr);
							ps.setString(2,seqnr);
					        ps.setString(3,Integer.toString(index));
					        ps.setString(4,prev.get(rowIndex).getHistQual());
							ps.setString(5,prev.get(rowIndex).getHistComplete());
							ps.setString(6,prev.get(rowIndex).getHistUniv());
							ps.setString(7,prev.get(rowIndex).getHistAccredit());
							return ps;
						}
					});  //end jdt update
				} catch (Exception ex) {
					throw new Exception(
							"MdApplicationsQueryDAO : Error updating previous academic history / " + ex, ex);
				}
			}//if
		}  //end while

		return;
	}


	public void updMDrecordOld(Student stu, String qcode, String scode) throws Exception {
		//log.debug("DAO updMDrecordOld - InterestArea: " + stu.getInterestArea());
		
		String query="";
		query="update mdappl set interest_area = '" + stu.getInterestArea() + "' " +
		      ", proposed_title = '" + stu.getResearchTopic() + "' " +
		      ", mk_qualification_c = '" + qcode + "' " +
		      ", speciality_code = '" + scode + "' " +
		      ", applied_qual = '" + qcode + "' " +
		      ", applied_spes = '" + scode + "' " +
		      ", lecturer_name = '" + stu.getLecturer() + "' " +
		      ", passed_ndp = '" + stu.getPassedndp().toUpperCase() + "' " +
		      ", prev_apply_md = '" + stu.getAppliedmd().toLowerCase() + "' " +
		      ", prev_apply_qual = '" + stu.getAppliedqual().toUpperCase() + "' " +
		      " where mk_student_nr = " + stu.getNumber() +
		      " and app_sequence_nr = " + stu.getSeqnr();
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int result = jdt.update(query);
		} catch (Exception ex) {
			throw new Exception(
					"MdApplicationsQueryDAO : Error updating student application for student nr / "+stu.getNumber() + ex, ex);
		}
		return ;
	}

	public List getMDhistory(String studentNr, String seqnr) throws Exception{
		String sql;
		List<MdPrev> histList = new ArrayList();

		sql = "select mk_student_nr, app_sequence_nr, prev_sequence_nr, prev_qual, " +
			  "year_completed, institution , accredit_source " +
			  "from mdprev where mk_student_nr = " + studentNr +
			  " and app_sequence_nr = " + seqnr;
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			Iterator i = queryList.iterator();
			while (i.hasNext()){
				ListOrderedMap data = (ListOrderedMap) i.next();
				MdPrev prev = new MdPrev();
				if (data.get("institution") == null){
					prev.setHistUniv("");
				}else{
					prev.setHistUniv(data.get("institution").toString());
				}
				if (data.get("prev_qual") == null){
					prev.setHistQual("");
				}else{
					prev.setHistQual(data.get("prev_qual").toString());
				}
				if (data.get("year_completed") == null){
					prev.setHistComplete("");
				}else{
					prev.setHistComplete(data.get("year_completed").toString());
				}
				if (data.get("accredit_source") == null){
					prev.setHistAccredit("");
				}else{
					prev.setHistAccredit(data.get("accredit_source").toString());
				}
				histList.add(prev);
			}
		} catch (Exception ex) {
			throw new Exception("MdApplicationsQueryDAO : getStudent : Error occurred / "+ ex,ex);
		}
		if (histList.isEmpty() || histList.size()<4){
			for (int i = histList.size(); i < 4; i++) {
				MdPrev prev = new MdPrev();
				prev.setHistUniv("");
				prev.setHistQual("");
				prev.setHistComplete("");
				prev.setHistAccredit("");
				histList.add(prev);
			}
		}
		return histList;
	}

	public void updateMDrecord(Student stu, String qcode, String scode) throws Exception {
		Log log = LogFactory.getLog(MdApplicationsQueryDAO.class);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new java.util.Date().getTime());

		MdApplicationsForm mdForm = new MdApplicationsForm();
		//log.debug("DAO updateMDrecord - Form: " + mdForm.getStudent().getInterestArea());
		//log.debug("DAO updateMDrecord - Stu: " + stu.getInterestArea());
		
		if (stu.getNumber()==null)
	    	 throw new Exception("MdApplicationsQueryDAO: student number not set");

  		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
        Connection conn = jdt.getDataSource().getConnection();
        String sql="update mdappl set mk_qualification_c = ?, " +
        		" speciality_code = ?, " +
        		" interest_area = ?, " +
        		" proposed_title = ?, " +
        		" applied_qual = ?, " +
        		" applied_spes = ?, " +
        		" lecturer_name = ?, " +
        		" passed_ndp = ?, " +
        		" prev_apply_md = ?, " +
        		" prev_apply_qual = ? " +
        		" where mk_student_nr = " + stu.getNumber() +
        		" and app_sequence_nr = " + stu.getSeqnr();
        PreparedStatement ps = conn.prepareStatement(sql);

        	ps.setString(1,qcode);
        	ps.setString(2,scode.toUpperCase());
        	ps.setString(3,stu.getInterestArea());
        	ps.setString(4,stu.getResearchTopic());
        	ps.setString(5,qcode);
        	ps.setString(6,scode.toUpperCase());
        	ps.setString(7,stu.getLecturer());
        	ps.setString(8,stu.getPassedndp().toUpperCase());
        	ps.setString(9,stu.getAppliedmd().toUpperCase());
        	ps.setString(10,stu.getAppliedqual().toUpperCase());
        	ps.executeUpdate();
        	//conn.close();
	}

	public void insertMDrecord(Student student, Qualification qualification) throws Exception {

		Log log = LogFactory.getLog(MdApplicationsQueryDAO.class);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new java.util.Date().getTime());

		if (student.getNumber()==null)
	    	 throw new Exception("MdApplicationsQueryDAO: student number not set");

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		final Student stu = student;
		final Qualification qual = qualification;
		//final Date dateTime = currentDate;

		final String sql = "insert into mdappl " +
			  "(mk_student_nr, app_sequence_nr, mk_qualification_c, speciality_code, " +
              "applied_qual, applied_spes, application_date, admission_status, " +
              "refer_status, interest_area, proposed_title, comments, contact_person, supervisor, " +
              "joint_supervisor, struct_degree, additional_requirements, reason_not_admit, advisor_comment, " +
              "recommend_status, " +
              "lecturer_name, passed_ndp, prev_apply_md, prev_apply_qual) " +
              "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		jdt.update( new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				
				MdApplicationsForm mdForm = new MdApplicationsForm();
				//log.debug("DAO insertMDrecord - Form: " + mdForm.getStudent().getInterestArea());
				//log.debug("DAO insertMDrecord - Stu: " + stu.getInterestArea());
				
				PreparedStatement ps = conn.prepareStatement(sql);

				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(new java.util.Date().getTime());
				//student nr
				ps.setString(1,stu.getNumber());
				//sequence nr
				ps.setString(2,stu.getSeqnr());
				//qualification
				if (qual.getQualCode().length() > 5)
					ps.setString(3,qual.getQualCode().substring(0,5));
				else
			        ps.setString(3,qual.getQualCode());
				//speciality
				if (qual.getSpecCode().length() > 3)
			        ps.setString(4,qual.getSpecCode().substring(0,3).toUpperCase());
				else
			        ps.setString(4,qual.getSpecCode().toUpperCase());
				//applied qual
				if (qual.getQualCode().length() > 5)
					ps.setString(5,qual.getQualCode().substring(0,5));
				else
					ps.setString(5,qual.getQualCode());
				//applied spes
				if (qual.getSpecCode().length() > 3)
					ps.setString(6,qual.getSpecCode().substring(0,3).toUpperCase());
				else
					ps.setString(6,qual.getSpecCode().toUpperCase());
				//application date
				ps.setDate(7,new java.sql.Date(cal.getTime().getTime()));
				//admission status
				ps.setString(8,"I");
				ps.setString(9,"");
				//interest area
				ps.setString(10,stu.getInterestArea());
				//proposed_title
				ps.setString(11,stu.getResearchTopic());
				ps.setString(12,"");
				ps.setString(13,"0");
				ps.setString(14,"0");
				ps.setString(15,"0");
				ps.setString(16,"");
				ps.setString(17,"");
				ps.setString(18,"");
				ps.setString(19,"");
				ps.setString(20,"");
				ps.setString(21,stu.getLecturer());
				ps.setString(22,stu.getPassedndp().toUpperCase());
				ps.setString(23,stu.getAppliedmd().toUpperCase());
				ps.setString(24,stu.getAppliedqual().toUpperCase());
				return ps;
			}
		});
		//log.debug("MdApplicationsQueryDAO: mdappl record written for " + student.getNumber());
		return;
	}

	public void updMddocsx(String studentNr,String sequenceNr, String doctype) throws Exception {
		//not used - checked manually on student system
		final String sql="insert into mddocs (mk_student_nr, app_sequence_nr, code, verify_flag) values (?,?,?,?)";
		final String stunr=studentNr;
		final String seqnr=sequenceNr;
		final String doc=doctype;

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		jdt.update( new PreparedStatementCreator() {
		public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql);
				//student nr
				ps.setString(1,stunr);
				//sequence nr
				ps.setString(2,seqnr);
				ps.setString(3,doc);
				ps.setString(4,"I");
				return ps;
				}
			});
		return ;
	}
	
	/**
	 * Builds list of required documents for new students
	 * @param studentNr
	 * @param year
	 * @param period
	 * @param required
	 * @param matrix
	 * @return
	 * @throws Exception
	 */
	public ArrayList<StudentFile> getAllRequiredDocs(String studentNr,String year, String qualCode, String specCode) throws Exception{

		//log.debug("MdApplicationsQueryDAO getAllRequiredDocs - studentNr=" + studentNr + ", year=" + year+", qualCode="+qualCode+", specCode="+specCode);
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List<?> queryList = null;
		ArrayList<StudentFile> docs = new ArrayList<StudentFile>();
		String query = "";
		
		try {
			if (specCode == null || "0".equals(specCode) || "".equals(specCode) || "NVT".equalsIgnoreCase(specCode) || "undefined".equalsIgnoreCase(specCode)){ specCode = " ";}

				
			//log.debug("MdApplicationsQueryDAO - getAllRequiredDocs - qualCode: " + qualCode + ", specCode: " + specCode);
				
				query = "SELECT distinct docCode, docDescription from ( "
							+ " (select gencod.code as docCode, gencod.eng_description as  docdescription "
							+ "  from qspdoc, gencod "
							+ "  where qspdoc.document_code = gencod.code "
							+ "  and gencod.in_use_flag = 'Y' "
							+ "  and gencod.fk_gencatcode = '134' "
							+ "  and qspdoc.mk_qual_code = ? "
							+ "  and qspdoc.mk_spes_code = ?) "
							+ " UNION "
							+ " (select stuapd.document_code as docCode, gencod.eng_description as docDescription "
							+ "  from stuapd, gencod "
							+ "  where stuapd.document_code = gencod.code "
							+ "  and gencod.in_use_flag = 'Y' "
							+ "  and gencod.fk_gencatcode = '134' "
							+ "  and stuapd.mk_student_nr = ? "
							+ "  and stuapd.academic_year = ?) "
							+ " ) tabname "
							+ " ORDER BY DocDescription ASC ";
				//log.debug("MdApplicationsQueryDAO - getAllRequiredDocs - query=" + query + ", qual="+qualCode+", spec="+specCode+", studentNr="+studentNr+", year="+year);
				queryList = jdt.queryForList(query, new Object []{qualCode, specCode, studentNr, year});

			Iterator<?> j = queryList.iterator();
			while (j.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) j.next();
				StudentFile doc = new StudentFile();
				doc.setFileType(data.get("docCode").toString());
				doc.setFileName(data.get("docDescription").toString());
				docs.add(doc);
			}
		} catch (Exception ex) {
			throw new Exception(
				"MdApplicationsQueryDAO : Error reading getting RequiredDocs list / " + ex);
		} finally {
			try {
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return docs;
	}
}
