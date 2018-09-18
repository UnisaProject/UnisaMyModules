package za.ac.unisa.lms.tools.studentregistration.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.studentregistration.bo.Categories;
import za.ac.unisa.lms.tools.studentregistration.bo.Doc;
import za.ac.unisa.lms.tools.studentregistration.bo.Qualifications;
import za.ac.unisa.lms.tools.studentregistration.bo.Specializations;
import za.ac.unisa.lms.tools.studentregistration.bo.Status;
import za.ac.unisa.lms.tools.studentregistration.bo.StudySelected;
import za.ac.unisa.lms.tools.studentregistration.dao.KeyValue;
import za.ac.unisa.lms.tools.studentregistration.forms.HistoryOther;
import za.ac.unisa.lms.tools.studentregistration.forms.HistoryUnisa;
import za.ac.unisa.lms.tools.studentregistration.forms.Qualification;
import za.ac.unisa.lms.tools.studentregistration.forms.Student;

@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
public class ApplyForStudentNumberQueryDAO extends StudentSystemDAO {

	public static Log log = LogFactory.getLog(ApplyForStudentNumberQueryDAO.class);

	public String getQualDesc(String sQual) throws Exception {
		String query = "select upper(long_eng_descripti) as ENG_DESCRIPTION from grd where code= ? ";
		String desc = "";

		try {
    	  	//log.debug("ApplyForStudentNumberQueryDAO - getQualDesc - query: " + query);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{sQual});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
					desc = data.get("ENG_DESCRIPTION").toString().toUpperCase();
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error retrieving Qual description. / " + ex);
		}
		return desc;
	}

	public String getSpecDesc(String qualCode, String specCode) throws Exception {
		
		String desc = "";
		
		if (specCode == null || "0".equals(specCode) || "".equals(specCode) || "NVT".equalsIgnoreCase(specCode) || "undefined".equalsIgnoreCase(specCode)){ specCode = " ";}

		String query = "select ENGLISH_DESCRIPTIO from quaspc "
					+ " where mk_qualification_c = ? "
					+ " and speciality_code = ? ";

		try {
    	  	//log.debug("ApplyForStudentNumberQueryDAO - getSpecDesc - query=" + query+", Qual=" + qualCode+", Spec=" + specCode);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{qualCode, specCode});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
					desc = data.get("ENGLISH_DESCRIPTIO").toString().toUpperCase();
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error retrieving Spec description / " + ex);
		}
		return desc;
	}
	
	/**
	 * This method returns a list of titles
	 */
	public ArrayList<LabelValueBean> getTitles() throws Exception {
		ArrayList list = new ArrayList();
		String query = "select title from title where used_by='A' order by title";

		try {
    	  	//log.debug("ApplyForStudentNumberQueryDAO - getTitles - query: " + query);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				list.add(new org.apache.struts.util.LabelValueBean(data.get("TITLE").toString(), data.get("TITLE").toString()));
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error reading title list / " + ex);
		}
		return list;
	}
	
	/**
	 * This method returns a list of provinces
	 */
	public ArrayList<LabelValueBean> getProvinces() throws Exception {
		ArrayList list = new ArrayList();
		String query = "select code, eng_description from prv order by code";

		try {
			list = readDatabase(query, "getProvinces");
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error reading province list / " + ex);
		}
		return list;
	}
	
	/**
	 * This method returns a list of Matric schools
	 */
	public ArrayList<KeyValue> getSchools(String province) throws Exception {

		 ArrayList<KeyValue> keyvalues = new ArrayList<KeyValue>();
		String query = "WITH FullMTR AS (SELECT CODE, NAME AS ENG_DESCRIPTION FROM MTRSCH WHERE PROVINCE= ? ORDER BY ENG_DESCRIPTION ASC) "
						+ " SELECT CODE, ENG_DESCRIPTION FROM FullMTR "
						+ " UNION ALL SELECT CODE, NAME AS ENG_DESCRIPTION FROM MTRSCH WHERE CODE=9999999 ";

		//log.debug("ApplyForStudentNumberDAO - getSchools - Query="+query+", province="+province);
		
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{province});
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				KeyValue school = new KeyValue();
				school.setKey(data.get("CODE").toString());
				school.setValue(data.get("ENG_DESCRIPTION").toString().trim());
				keyvalues.add(school);
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error reading Matric School list / " + ex);
		}
		return keyvalues;
	}
	
	/**
	 * This method returns a list of Exam Centres
	 */
	public ArrayList<KeyValue> getExamCentres(String examCentreType) throws Exception {

		ArrayList<KeyValue> keyvalues = new ArrayList<KeyValue>();
		String query = "";
		String paramDB = " 'F','P' ";
		if (examCentreType.trim() != null && !"".equals(examCentreType.trim())){
			if ("FP".equalsIgnoreCase(examCentreType.trim())){
				paramDB = " 'F','P' ";;
			}else if ("F".equalsIgnoreCase(examCentreType.trim())){
				paramDB = " 'F' ";
			}else if ("P".equalsIgnoreCase(examCentreType.trim())){
				paramDB = " 'P' ";
			}
		}

		query = "select CODE, rpad(eng_description,28,' ') as ENG_DESCRIPTION, " 
					+ " case when mk_country_code='1015' and mk_province_code > 0 then " 
					+ " (select eng_description from prv where prv.code=mk_province_code) " 
					+ " else " 
					+ " (select eng_description from lns where lns.code=mk_country_code) "  
					+ " end as PROVINCE, TYPE " 
					+ " from eks where in_use_flag = 'Y' " 
					+ " and TYPE in ("+paramDB+") "
					+ " order by mk_province_code desc, PROVINCE, eng_description ";

		//log.debug("ApplyForStudentNumberDAO - getExamCentres - Query="+query);
		//log.debug("ApplyForStudentNumberDAO - getExamCentres - examCentreType="+examCentreType);
		
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();

			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				KeyValue exam = new KeyValue();
				String prv="";
				String prisonText = "";
				if (data.get("PROVINCE") != null){
					prv=data.get("PROVINCE").toString().toUpperCase();
				}
				if ("P".equalsIgnoreCase(data.get("TYPE").toString())){
					prisonText = " - (Correctional Facility)";
				}
				exam.setKey(data.get("CODE").toString()+"@"+data.get("ENG_DESCRIPTION").toString().toUpperCase().trim());
				exam.setValue(prv+" -- " +data.get("ENG_DESCRIPTION").toString().toUpperCase().trim()+" - "+data.get("CODE").toString()+prisonText);
				keyvalues.add(exam);
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error reading Exam Centre list / " + ex);
		}
		return keyvalues;
	}
	
	/**
	 * This method returns a list of Countries
	 */
	public ArrayList<KeyValue> getCountriesList() throws Exception {

		ArrayList<KeyValue> keyvalues = new ArrayList<KeyValue>();

		String query = "select code, eng_description from lns where in_use_flag = 'Y' order by eng_description";

		//log.debug("ApplyForStudentNumberDAO - getCountries - Query="+query);
		
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();

			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				KeyValue country = new KeyValue();
				country.setKey(data.get("CODE").toString());
				country.setValue(data.get("ENG_DESCRIPTION").toString().toUpperCase());
				keyvalues.add(country);
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error reading Country Centre list / " + ex);
		}
		return keyvalues;
	}
	
	
	/**
	 * This method returns the APS score of a specific qualification
	 */
	public ArrayList<KeyValue> getQualAPS(String selQualCode) throws Exception {

		ArrayList<KeyValue> keyvalues = new ArrayList<KeyValue>();
		String query = "";
		String paramDB = "F";
		if (selQualCode != null && !"".equals(selQualCode)){
			paramDB = selQualCode;
		}

		query = "select aps_score, admission_require  "
				+ " from quaspc "
				+ " where in_use_flag = 'Y' "
				+ " and mk_qualification_c = ? "
				+ " and aps_score is not null "
				+ " and rownum = 1 ";

		//log.debug("ApplyForStudentNumberDAO - getQualAPS - Query="+query);
		//log.debug("ApplyForStudentNumberDAO - getQualAPS - selQualCode="+selQualCode);
		
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{paramDB});
			Iterator i = queryList.iterator();

			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				KeyValue exam = new KeyValue();
				String prv="";
				String prisonText = "";
				if (data.get("aps_score") != null){
					prv=data.get("admission_require").toString().toUpperCase();
				}
				exam.setKey(data.get("aps_score").toString());
				exam.setValue(data.get("admission_require").toString().toUpperCase().trim());
				keyvalues.add(exam);
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error reading Qualification APS Score / " + ex);
		}
		return keyvalues;
	}
	
	/**
	 * Check if student has a student/reference number
	 */
	public String validateStudentLogin(String surname, String firstNames, String  bDay, String mainSelect) throws Exception {
		
		String query  = "";
		String stuCheck = "newStu"; //New Student
		
		if ("SLP".equalsIgnoreCase(mainSelect)){ //SLP Student 7-Series
			query  = "select nr from stu "
					+ " where surname = ? "
					+ " and first_names = ? "
					+ " and birth_date = (SELECT TO_DATE (?, 'DD/MM/YYYY') FROM DUAL) "
					+ " and nr between 70000000 and 80000000 "
					+ " order by mk_last_academic_y desc ";
		}else{ //Formal Student
			query  = "select nr from stu "
					+ " where surname = ? "
					+ " and first_names = ? "
					+ " and birth_date = (SELECT TO_DATE (?, 'DD/MM/YYYY') FROM DUAL) "
					+ " and (nr < 70000000 or nr >= 80000000) "
					+ " and rownum = 1 "
					+ " order by mk_last_academic_y desc ";
		}
		
		//Check if student record exists by using Surname, First Names, Date of Birth and ID (Or ForeignID or Passport)
		
		try {
			//log.debug("ApplyForStudentNumberQueryDAO - validateStudentLogin: query=" +  query+", surname=" +  surname.toUpperCase()+", firstNames=" +  firstNames.toUpperCase()+". bDay=" +  bDay);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{surname.toUpperCase(), firstNames.toUpperCase(), bDay});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				stuCheck = data.get("nr").toString(); //Current Student
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error validating STU student number / " + ex);
		}
		//log.debug("ApplyForStudentNumberQueryDAO - validateStudentLogin: stuCheck" +  stuCheck);
		return stuCheck;
	}
	
	/**
	 * Check if student has a temporary student/reference number
	 */
	public String checkStuTempNr(String surname, String firstNames, String  bDay, String acaYear, String acaPeriod) throws Exception {
		
		String stuCheck = "";
		
		//Check if student record exists by using Surname, First Names, Date of Birth
		String query  = "select mk_student_nr "
						+ "from stuxml "
						+ "where mk_academic_year = ? "
						+ "and mk_academic_period = ? "
						+ "and detail like ? "
						+ "and detail like ? " 
						+ "and detail like ? "
						+ "and rownum = 1 "
						+ "order by date_initial desc ";
		try {
			//log.debug("ApplyForStudentNumberQueryDAO - checkStuTempNr: query="+query+", acaYear="+acaYear+", acaPeriod="+acaPeriod+", surname="+surname.toUpperCase()+", firstNames="+firstNames.toUpperCase()+", bDay="+bDay);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{acaYear, acaPeriod, surname.toUpperCase()+"%", "%"+firstNames.toUpperCase()+"%", "%"+bDay});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				stuCheck = data.get("nr").toString(); //Current Student
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error validating STU Temp student number / " + ex);
		}
		//log.debug("ApplyForStudentNumberQueryDAO - checkStuTempNr" +  stuCheck);
		return stuCheck;
	}
	
	/**
	 * Check if student has a student/reference number
	 */
	public boolean validateSTUAPQ(String surname, String firstNames, String  bDay, String acaYear) throws Exception {
		
		boolean stuapqCheck = false;
		String dbParam = "";
		String result = "";
		//Check if student record exists by using Surname, First Names, Date of Birth
		//Note we do not check the Period as student is only allowed one application per year (except SLP)
		String query  = "select DISTINCT stuapq.mk_student_nr "
				+ " from stu, stuann, stuapq "
				+ " where stu.nr = stuann.mk_student_nr "
				+ " and stu.nr = stuapq.mk_student_nr "
				+ " and stu.surname = ? "
				+ " and stu.first_names = ? "
				+ " and to_char(stu.birth_date, 'DD/MM/YYYY') = ? "
				+ " and stuapq.academic_year= ? ";
		
		try {
			dbParam = surname.toUpperCase()+","+firstNames.toUpperCase()+","+bDay+","+acaYear;
			//log.debug("ApplyForStudentNumberQueryDAO - validateSTUAPQ - query: " +  query);
			//log.debug("ApplyForStudentNumberQueryDAO - validateSTUAPQ - dbParam: " +  dbParam);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{surname.toUpperCase(), firstNames.toUpperCase(), bDay, acaYear});
			
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				//log.debug("ApplyForStudentNumberQueryDAO - validateSTUAPQ - isNumber= " +  data.get("mk_student_nr").toString());
				stuapqCheck=true;
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error validating STUAPQ student number / " + ex);
		}
		//log.debug("ApplyForStudentNumberQueryDAO - validateSTUAPQ - stuapqCheck: " +  stuapqCheck);
		return stuapqCheck;
	}
	
	/**
	 * Check if SLP student has a STUAPQ Record and if so, how many
	 */
	public int validateSLPSTUAPQ(String surname, String firstNames, String  bDay, String acaYear, String acaPeriod) throws Exception {
		
		int stuapqCheck = 0;
		String dbParam = "";
		String result = "";
		//Check if student record exists by using Surname, First Names, Date of Birth
		
		String query  = "select count(stuapq.new_qual) as Count "
				+ " from stu, stuann, stuapq "
				+ " where stu.nr = stuann.mk_student_nr "
				+ " and stu.nr = stuapq.mk_student_nr "
				+ " and stu.surname = ? "
				+ " and stu.first_names = ? "
				+ " and to_char(stu.birth_date, 'DD/MM/YYYY') = ? "
				+ " and stuann.mk_academic_year= ? "
				+ " and stuapq.academic_year= ? "
				+ " and stuapq.application_period = ? ";

		try {
			dbParam = surname.toUpperCase()+","+firstNames.toUpperCase()+","+bDay+","+acaYear+","+acaYear+","+acaPeriod;
			//log.debug("ApplyForStudentNumberQueryDAO - validateSLPSTUAPQ - query: " +  query + ", dbParam: " +  dbParam);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{surname.toUpperCase(), firstNames.toUpperCase(), bDay, acaYear, acaYear, acaPeriod});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				//log.debug("ApplyForStudentNumberQueryDAO - validateSLPSTUAPQ - SLP Count="+data.get("Count"));
				stuapqCheck = Integer.parseInt(data.get("Count").toString().trim());
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error validating STUAPQ SLP Record / " + ex);
		}
		//log.debug("ApplyForStudentNumberQueryDAO - validateSLPSTUAPQ - stuapqCheck: " +  stuapqCheck);
		return stuapqCheck;
	}
	
	  /************ Get previous category to pre-select radio buttons *************/
	  public String getSLPQual(String studentNr, String acaYear, String acaPeriod, String choiceNr, String choice, String callingMethod) throws Exception{
		  
	      String result = "";

	      try {

	    	  String query = "SELECT STUAPQ.NEW_QUAL, STUAPQ.NEW_SPES, GRD.FK_KATCODE, GRD.TYPE "
		        		+ " FROM STUAPQ, GRD "
		        		+ " WHERE STUAPQ.NEW_QUAL = GRD.CODE "
		        		+ " AND MK_STUDENT_NR = ? "
		        		+ " AND ACADEMIC_YEAR = ? "
		        		+ " AND APPLICATION_PERIOD = ? "
		        		+ " AND STUAPQ.CHOICE_NR = ? ";

	    	  //log.debug("ApplyForStudentNumberQueryDAO - getSLPQual - query=" + query+", studentNr=" + studentNr+", acaYear=" + acaYear+", acaPeriod=" + acaPeriod+", choiceNr=" + choiceNr+", choice=" + choice+", callingMethod="+callingMethod);

				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, acaPeriod, choiceNr});
				Iterator i = queryList.iterator();
				if (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					if ("CAT".equalsIgnoreCase(choice)){
						String cat = data.get("TYPE").toString();
						if ("S".equalsIgnoreCase(cat)){
							result = "15"; //Hardcoded by type for now - Refer Ina for new code
						}else{
							result = cat;
						}
					}else if ("QUAL".equalsIgnoreCase(choice)){
						result = data.get("NEW_QUAL").toString();
					}else if ("SPEC".equalsIgnoreCase(choice)){
						result = data.get("NEW_SPES").toString();
					}
					//log.debug("ApplyForStudentNumberQueryDAO - getSLPQual - Choice=" + choice+",  result=" + result);
				}
	      	} catch (Exception ex) {
	      			throw new Exception(
	      					"ApplyForStudentNumberQueryDAO: Error occured on reading previous "+choice+" / " + ex);
	      	} finally {
	      	}
	      	return result;
	  }
	  
	/**
	 * Retrieve student completed Academic Record
	 */
	public ArrayList getQualsUnisa(String studentNr) throws Exception {
		
		//log.debug("ApplyForStudentNumberQueryDAO - getQualsUnisa - Start");

		ArrayList list = new ArrayList();
				
		String query = "select  stuaca.mk_qualification_c as qualCode, upper(grd.long_eng_descripti) as qualDesc, "
					+ "	to_char(stuaca.first_registration, 'YYYY') as first_registration, stuaca.actual_completion, "
					+ " 'UNISA' as institution "
					+ "	from stuaca, grd "
					+ "	where stuaca.status_code = 'CO' "
					+ "	and stuaca.mk_student_nr = ? "
					+ "	and stuaca.mk_qualification_c = grd.code "
					+ "	order by actual_completion ";

		//log.debug("ApplyForStudentNumberQueryDAO - getQualsUnisa - query="+query);
		try{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr});
			
			Iterator i = queryList.iterator();
			int count = 0;
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				HistoryUnisa qual = new HistoryUnisa();
				qual.setHistUniv(data.get("institution").toString().trim().toUpperCase());
				qual.setHistQual(data.get("qualCode").toString().trim().toUpperCase());
				qual.setHistQualDesc(data.get("qualDesc").toString().trim().toUpperCase());
				qual.setHistYear(data.get("first_registration").toString().trim());
				qual.setHistComplete(data.get("actual_completion").toString().trim());
				list.add(qual);
				count++;
			}
			//log.debug("ApplyForStudentNumberQueryDAO - getQualsUnisa - Records Found="+count);
		} catch (Exception ex) {
			throw new Exception(
				"ApplyForStudentNumberQueryDAO : Error validating student completed history record / " + ex);
		}
		//log.debug("ApplyForStudentNumberQueryDAO - getQualsUnisa - Start");
	return list;
	}

	/** Retrieve Previous Academic Record count**/
	public int getSTUPREVMax(String studentNr) throws Exception {
		
		//log.debug("ApplyForStudentNumberQueryDAO - getSTUPREVMax - Start");

		int prevMax = 0;
		
		try {
			String query  = "select COALESCE(max(seq_nr), 0) as MaxSEQUENCE "
						+ " from STUPREV "
						+ " where mk_student_nr = ? ";
			
			//log.debug("ApplyForStudentNumberQueryDAO - getSTUPREVMax - Query="+query+ ", StudentNr="+studentNr);
			
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr});
			
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				//log.debug("ApplyForStudentNumberQueryDAO - getSTUPREVMax - has Next");
				ListOrderedMap data = (ListOrderedMap) i.next();
				prevMax = (Integer.parseInt(data.get("MaxSEQUENCE").toString()));
			}
		} catch (Exception ex) {
				log.warn("ApplyForStudentNumberQueryDAO: Error - Retrieving Max Previous Academic Record for student nr / "+ studentNr + " / " +ex);
		}
		return prevMax;
	}
	
	/** Retrieve Previous Academic Record **/
	public HistoryOther getPREVADM(String studentNr) throws Exception {
		
		//log.debug("ApplyForStudentNumberQueryDAO - getPREVADM - Start");

		HistoryOther history = new HistoryOther();
		
		try {
			String query  = "select MK_STUDENT_NR, SEQ_NR, FIRST_YEAR_REG, YEAR_COMPLETED, INSTITUTION, "
					+ " OTHER_INST_STU_NR, OTHER_INST_QUAL, FOREIGN_IND, NQF_LEVEL, LOCK_UPDATE_IND, "
					+ " COMPLETED_IND, COALESCE(MK_UNK, ' ') as MK_UNK, COALESCE(MK_COUNTRY_CODE, ' ') as MK_COUNTRY_CODE "
					+ " from STUPREV "
					+ " where MK_STUDENT_NR = ? "
					+ " order by SEQ_NR ";
							
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr});

			//log.debug("ApplyForStudentNumberQueryDAO - getPREVADM - Query="+query);
			
			Iterator i = queryList.iterator();
			int count = 0;
			while (i.hasNext()) {
				//log.debug("ApplyForStudentNumberQueryDAO - getPREVADM ---------------------------------------------------------");
				//log.debug("ApplyForStudentNumberQueryDAO - getPREVADM - has Next");
				ListOrderedMap data = (ListOrderedMap) i.next();
				
				if (data.get("SEQ_NR") != null && !"".equals(data.get("SEQ_NR").toString().trim())){
					//log.debug("ApplyForStudentNumberQueryDAO - getPREVADM - SEQ_NR="+data.get("SEQ_NR").toString().trim());
					
					if (Integer.parseInt(data.get("SEQ_NR").toString().trim()) == 1){
						history.setHistoryOTHERSEQ2(Integer.parseInt(data.get("SEQ_NR").toString().trim()));
						if (" ".equals(data.get("MK_UNK").toString())){
							history.setHistoryOTHERUniv2("OTHR");
						}else{
							history.setHistoryOTHERUniv2(data.get("MK_UNK").toString().trim());
						}
						if (data.get("INSTITUTION") != null && !"".equals(data.get("INSTITUTION").toString().trim())){
							history.setHistoryOTHERUnivText2(data.get("INSTITUTION").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERUnivText2(" ");
						}
						if (data.get("OTHER_INST_STU_NR") != null && !"".equals(data.get("OTHER_INST_STU_NR").toString().trim())){
							history.setHistoryOTHERStudnr2(data.get("OTHER_INST_STU_NR").toString().trim());
						}else{
							history.setHistoryOTHERStudnr2(" ");
			}
						if (data.get("OTHER_INST_QUAL") != null && !"".equals(data.get("OTHER_INST_QUAL").toString().trim())){
							history.setHistoryOTHERQual2(data.get("OTHER_INST_QUAL").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERQual2(" ");
						}
						if (data.get("FIRST_YEAR_REG") != null && !"".equals(data.get("FIRST_YEAR_REG").toString().trim())){
							history.setHistoryOTHERYearStart2(data.get("FIRST_YEAR_REG").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERYearStart2("-1");
						}
						if (data.get("YEAR_COMPLETED") != null && !"".equals(data.get("YEAR_COMPLETED").toString().trim())){
							history.setHistoryOTHERYearEnd2(data.get("YEAR_COMPLETED").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERYearEnd2("-1");
						}
						history.setHistoryOTHERYearEndDB2(history.getHistoryOTHERYearEnd2());
						if (data.get("MK_COUNTRY_CODE") != null && !"".equals(data.get("MK_COUNTRY_CODE").toString().trim())){
							history.setHistoryOTHERCountry2(data.get("MK_COUNTRY_CODE").toString().trim());
						}else{
							history.setHistoryOTHERCountry2("-1");
						}
						if (data.get("COMPLETED_IND") != null && !"".equals(data.get("COMPLETED_IND").toString().trim())){
							history.setHistoryOTHERComplete2(data.get("COMPLETED_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERComplete2("N");
						}
						if (data.get("FOREIGN_IND") != null && !"".equals(data.get("FOREIGN_IND").toString().trim())){
							history.setHistoryOTHERForeign2(data.get("FOREIGN_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERForeign2("N");
						}
						if (data.get("LOCK_UPDATE_IND") != null && !"".equals(data.get("LOCK_UPDATE_IND").toString().trim())){
							history.setHistoryOTHERLock2(data.get("LOCK_UPDATE_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERLock2("N");
						}
					}else if (Integer.parseInt(data.get("SEQ_NR").toString().trim()) == 2){
						history.setHistoryOTHERSEQ3(Integer.parseInt(data.get("SEQ_NR").toString().trim()));
						if (" ".equals(data.get("MK_UNK").toString())){
							history.setHistoryOTHERUniv3("OTHR");
						}else{
							history.setHistoryOTHERUniv3(data.get("MK_UNK").toString().trim());
						}
						if (data.get("INSTITUTION") != null && !"".equals(data.get("INSTITUTION").toString().trim())){
							history.setHistoryOTHERUnivText3(data.get("INSTITUTION").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERUnivText3(" ");
						}
						if (data.get("OTHER_INST_STU_NR") != null && !"".equals(data.get("OTHER_INST_STU_NR").toString().trim())){
							history.setHistoryOTHERStudnr3(data.get("OTHER_INST_STU_NR").toString().trim());
						}else{
							history.setHistoryOTHERStudnr3(" ");
						}
						if (data.get("OTHER_INST_QUAL") != null && !"".equals(data.get("OTHER_INST_QUAL").toString().trim())){
							history.setHistoryOTHERQual3(data.get("OTHER_INST_QUAL").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERQual3(" ");
						}
						if (data.get("FIRST_YEAR_REG") != null && !"".equals(data.get("FIRST_YEAR_REG").toString().trim())){
							history.setHistoryOTHERYearStart3(data.get("FIRST_YEAR_REG").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERYearStart3("-1");
						}
						if (data.get("YEAR_COMPLETED") != null && !"".equals(data.get("YEAR_COMPLETED").toString().trim())){
							history.setHistoryOTHERYearEnd3(data.get("YEAR_COMPLETED").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERYearEnd3("-1");
						}
						history.setHistoryOTHERYearEndDB3(history.getHistoryOTHERYearEnd3());
						if (data.get("MK_COUNTRY_CODE") != null && !"".equals(data.get("MK_COUNTRY_CODE").toString().trim())){
							history.setHistoryOTHERCountry3(data.get("MK_COUNTRY_CODE").toString().trim());
						}else{
							history.setHistoryOTHERCountry3("-1");
						}
						if (data.get("COMPLETED_IND") != null && !"".equals(data.get("COMPLETED_IND").toString().trim())){
							history.setHistoryOTHERComplete3(data.get("COMPLETED_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERComplete3("N");
						}
						if (data.get("FOREIGN_IND") != null && !"".equals(data.get("FOREIGN_IND").toString().trim())){
							history.setHistoryOTHERForeign3(data.get("FOREIGN_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERForeign3("N");
						}
						if (data.get("LOCK_UPDATE_IND") != null && !"".equals(data.get("LOCK_UPDATE_IND").toString().trim())){
							history.setHistoryOTHERLock3(data.get("LOCK_UPDATE_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERLock3("N");
						}
					}else if (Integer.parseInt(data.get("SEQ_NR").toString().trim()) == 3){
						history.setHistoryOTHERSEQ4(Integer.parseInt(data.get("SEQ_NR").toString().trim()));
						if (" ".equals(data.get("MK_UNK").toString())){
							history.setHistoryOTHERUniv4("OTHR");
						}else{
							history.setHistoryOTHERUniv4(data.get("MK_UNK").toString().trim());
						}
						if (data.get("INSTITUTION") != null && !"".equals(data.get("INSTITUTION").toString().trim())){
							history.setHistoryOTHERUnivText4(data.get("INSTITUTION").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERUnivText4(" ");
						}
						if (data.get("OTHER_INST_STU_NR") != null && !"".equals(data.get("OTHER_INST_STU_NR").toString().trim())){
							history.setHistoryOTHERStudnr4(data.get("OTHER_INST_STU_NR").toString().trim());
						}else{
							history.setHistoryOTHERStudnr4(" ");
						}
						if (data.get("OTHER_INST_QUAL") != null && !"".equals(data.get("OTHER_INST_QUAL").toString().trim())){
							history.setHistoryOTHERQual4(data.get("OTHER_INST_QUAL").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERQual4(" ");
						}
						if (data.get("FIRST_YEAR_REG") != null && !"".equals(data.get("FIRST_YEAR_REG").toString().trim())){
							history.setHistoryOTHERYearStart4(data.get("FIRST_YEAR_REG").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERYearStart4("-1");
						}
						if (data.get("YEAR_COMPLETED") != null && !"".equals(data.get("YEAR_COMPLETED").toString().trim())){
							history.setHistoryOTHERYearEnd4(data.get("YEAR_COMPLETED").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERYearEnd4("-1");
						}
						history.setHistoryOTHERYearEndDB4(history.getHistoryOTHERYearEnd4());
						if (data.get("MK_COUNTRY_CODE") != null && !"".equals(data.get("MK_COUNTRY_CODE").toString().trim())){
							history.setHistoryOTHERCountry4(data.get("MK_COUNTRY_CODE").toString().trim());
						}else{
							history.setHistoryOTHERCountry4("-1");
						}
						if (data.get("COMPLETED_IND") != null && !"".equals(data.get("COMPLETED_IND").toString().trim())){
							history.setHistoryOTHERComplete4(data.get("COMPLETED_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERComplete4("N");
						}
						if (data.get("FOREIGN_IND") != null && !"".equals(data.get("FOREIGN_IND").toString().trim())){
							history.setHistoryOTHERForeign4(data.get("FOREIGN_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERForeign4("N");
						}
						if (data.get("LOCK_UPDATE_IND") != null && !"".equals(data.get("LOCK_UPDATE_IND").toString().trim())){
							history.setHistoryOTHERLock4(data.get("LOCK_UPDATE_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERLock4("N");
						}
					}else if (Integer.parseInt(data.get("SEQ_NR").toString().trim()) == 4){
						history.setHistoryOTHERSEQ5(Integer.parseInt(data.get("SEQ_NR").toString().trim()));
						if (" ".equals(data.get("MK_UNK").toString())){
							history.setHistoryOTHERUniv5("OTHR");
						}else{
							history.setHistoryOTHERUniv5(data.get("MK_UNK").toString().trim());
						}
						if (data.get("INSTITUTION") != null && !"".equals(data.get("INSTITUTION").toString().trim())){
							history.setHistoryOTHERUnivText5(data.get("INSTITUTION").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERUnivText5(" ");
						}
						if (data.get("OTHER_INST_STU_NR") != null && !"".equals(data.get("OTHER_INST_STU_NR").toString().trim())){
							history.setHistoryOTHERStudnr5(data.get("OTHER_INST_STU_NR").toString().trim());
						}else{
							history.setHistoryOTHERStudnr5(" ");
						}
						if (data.get("OTHER_INST_QUAL") != null && !"".equals(data.get("OTHER_INST_QUAL").toString().trim())){
							history.setHistoryOTHERQual5(data.get("OTHER_INST_QUAL").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERQual5(" ");
						}
						if (data.get("FIRST_YEAR_REG") != null && !"".equals(data.get("FIRST_YEAR_REG").toString().trim())){
							history.setHistoryOTHERYearStart5(data.get("FIRST_YEAR_REG").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERYearStart5("-1");
						}
						if (data.get("YEAR_COMPLETED") != null && !"".equals(data.get("YEAR_COMPLETED").toString().trim())){
							history.setHistoryOTHERYearEnd5(data.get("YEAR_COMPLETED").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERYearEnd5("-1");
						}
						history.setHistoryOTHERYearEndDB5(history.getHistoryOTHERYearEnd5());
						if (data.get("MK_COUNTRY_CODE") != null && !"".equals(data.get("MK_COUNTRY_CODE").toString().trim())){
							history.setHistoryOTHERCountry5(data.get("MK_COUNTRY_CODE").toString().trim());
						}else{
							history.setHistoryOTHERCountry5("-1");
						}
						if (data.get("COMPLETED_IND") != null && !"".equals(data.get("COMPLETED_IND").toString().trim())){
							history.setHistoryOTHERComplete5(data.get("COMPLETED_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERComplete5("N");
						}
						if (data.get("FOREIGN_IND") != null && !"".equals(data.get("FOREIGN_IND").toString().trim())){
							history.setHistoryOTHERForeign5(data.get("FOREIGN_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERForeign5("N");
						}
						if (data.get("LOCK_UPDATE_IND") != null && !"".equals(data.get("LOCK_UPDATE_IND").toString().trim())){
							history.setHistoryOTHERLock5(data.get("LOCK_UPDATE_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERLock5("N");
						}
					}else if (Integer.parseInt(data.get("SEQ_NR").toString().trim()) == 5){
						history.setHistoryOTHERSEQ6(Integer.parseInt(data.get("SEQ_NR").toString().trim()));
						if (" ".equals(data.get("MK_UNK").toString())){
							history.setHistoryOTHERUniv6("OTHR");
						}else{
							history.setHistoryOTHERUniv6(data.get("MK_UNK").toString().trim());
						}
						if (data.get("INSTITUTION") != null && !"".equals(data.get("INSTITUTION").toString().trim())){
							history.setHistoryOTHERUnivText6(data.get("INSTITUTION").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERUnivText6(" ");
						}
						if (data.get("OTHER_INST_STU_NR") != null && !"".equals(data.get("OTHER_INST_STU_NR").toString().trim())){
							history.setHistoryOTHERStudnr6(data.get("OTHER_INST_STU_NR").toString().trim());
						}else{
							history.setHistoryOTHERStudnr6(" ");
						}
						if (data.get("OTHER_INST_QUAL") != null && !"".equals(data.get("OTHER_INST_QUAL").toString().trim())){
							history.setHistoryOTHERQual6(data.get("OTHER_INST_QUAL").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERQual6(" ");
						}
						if (data.get("FIRST_YEAR_REG") != null && !"".equals(data.get("FIRST_YEAR_REG").toString().trim())){
							history.setHistoryOTHERYearStart6(data.get("FIRST_YEAR_REG").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERYearStart6("-1");
						}
						if (data.get("YEAR_COMPLETED") != null && !"".equals(data.get("YEAR_COMPLETED").toString().trim())){
							history.setHistoryOTHERYearEnd6(data.get("YEAR_COMPLETED").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERYearEnd6("-1");
						}
						history.setHistoryOTHERYearEndDB6(history.getHistoryOTHERYearEnd6());
						if (data.get("MK_COUNTRY_CODE") != null && !"".equals(data.get("MK_COUNTRY_CODE").toString().trim())){
							history.setHistoryOTHERCountry6(data.get("MK_COUNTRY_CODE").toString().trim());
						}else{
							history.setHistoryOTHERCountry6("-1");
						}
						if (data.get("COMPLETED_IND") != null && !"".equals(data.get("COMPLETED_IND").toString().trim())){
							history.setHistoryOTHERComplete6(data.get("COMPLETED_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERComplete6("N");
						}
						if (data.get("FOREIGN_IND") != null && !"".equals(data.get("FOREIGN_IND").toString().trim())){
							history.setHistoryOTHERForeign6(data.get("FOREIGN_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERForeign6("N");
						}
						if (data.get("LOCK_UPDATE_IND") != null && !"".equals(data.get("LOCK_UPDATE_IND").toString().trim())){
							history.setHistoryOTHERLock6(data.get("LOCK_UPDATE_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERLock6("N");
						}
					}else if (Integer.parseInt(data.get("SEQ_NR").toString().trim()) == 6){
						history.setHistoryOTHERSEQ7(Integer.parseInt(data.get("SEQ_NR").toString().trim()));
						if (" ".equals(data.get("MK_UNK").toString())){
							history.setHistoryOTHERUniv7("OTHR");
						}else{
							history.setHistoryOTHERUniv7(data.get("MK_UNK").toString().trim());
						}
						if (data.get("INSTITUTION") != null && !"".equals(data.get("INSTITUTION").toString().trim())){
							history.setHistoryOTHERUnivText7(data.get("INSTITUTION").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERUnivText7(" ");
						}
						if (data.get("OTHER_INST_STU_NR") != null && !"".equals(data.get("OTHER_INST_STU_NR").toString().trim())){
							history.setHistoryOTHERStudnr7(data.get("OTHER_INST_STU_NR").toString().trim());
						}else{
							history.setHistoryOTHERStudnr7(" ");
						}
						if (data.get("OTHER_INST_QUAL") != null && !"".equals(data.get("OTHER_INST_QUAL").toString().trim())){
							history.setHistoryOTHERQual7(data.get("OTHER_INST_QUAL").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERQual7(" ");
						}
						if (data.get("FIRST_YEAR_REG") != null && !"".equals(data.get("FIRST_YEAR_REG").toString().trim())){
							history.setHistoryOTHERYearStart7(data.get("FIRST_YEAR_REG").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERYearStart7("-1");
						}
						if (data.get("YEAR_COMPLETED") != null && !"".equals(data.get("YEAR_COMPLETED").toString().trim())){
							history.setHistoryOTHERYearEnd7(data.get("YEAR_COMPLETED").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERYearEnd7("-1");
						}
						history.setHistoryOTHERYearEndDB7(history.getHistoryOTHERYearEnd7());
						if (data.get("MK_COUNTRY_CODE") != null && !"".equals(data.get("MK_COUNTRY_CODE").toString().trim())){
							history.setHistoryOTHERCountry7(data.get("MK_COUNTRY_CODE").toString().trim());
						}else{
							history.setHistoryOTHERCountry7("-1");
						}
						if (data.get("COMPLETED_IND") != null && !"".equals(data.get("COMPLETED_IND").toString().trim())){
							history.setHistoryOTHERComplete7(data.get("COMPLETED_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERComplete7("N");
						}
						if (data.get("FOREIGN_IND") != null && !"".equals(data.get("FOREIGN_IND").toString().trim())){
							history.setHistoryOTHERForeign7(data.get("FOREIGN_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERForeign7("N");
						}
						if (data.get("LOCK_UPDATE_IND") != null && !"".equals(data.get("LOCK_UPDATE_IND").toString().trim())){
							history.setHistoryOTHERLock7(data.get("LOCK_UPDATE_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERLock7("N");
						}
					}else if (Integer.parseInt(data.get("SEQ_NR").toString().trim()) == 7){
						history.setHistoryOTHERSEQ8(Integer.parseInt(data.get("SEQ_NR").toString().trim()));
						if (" ".equals(data.get("MK_UNK").toString())){
							history.setHistoryOTHERUniv8("OTHR");
						}else{
							history.setHistoryOTHERUniv8(data.get("MK_UNK").toString().trim());
						}
						if (data.get("INSTITUTION") != null && !"".equals(data.get("INSTITUTION").toString().trim())){
							history.setHistoryOTHERUnivText8(data.get("INSTITUTION").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERUnivText8(" ");
						}
						if (data.get("OTHER_INST_STU_NR") != null && !"".equals(data.get("OTHER_INST_STU_NR").toString().trim())){
							history.setHistoryOTHERStudnr8(data.get("OTHER_INST_STU_NR").toString().trim());
						}else{
							history.setHistoryOTHERStudnr8(" ");
						}
						if (data.get("OTHER_INST_QUAL") != null && !"".equals(data.get("OTHER_INST_QUAL").toString().trim())){
							history.setHistoryOTHERQual8(data.get("OTHER_INST_QUAL").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERQual8(" ");
						}
						if (data.get("FIRST_YEAR_REG") != null && !"".equals(data.get("FIRST_YEAR_REG").toString().trim())){
							history.setHistoryOTHERYearStart8(data.get("FIRST_YEAR_REG").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERYearStart8("-1");
						}
						if (data.get("YEAR_COMPLETED") != null && !"".equals(data.get("YEAR_COMPLETED").toString().trim())){
							history.setHistoryOTHERYearEnd8(data.get("YEAR_COMPLETED").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERYearEnd8("-1");
						}
						history.setHistoryOTHERYearEndDB8(history.getHistoryOTHERYearEnd8());
						if (data.get("MK_COUNTRY_CODE") != null && !"".equals(data.get("MK_COUNTRY_CODE").toString().trim())){
							history.setHistoryOTHERCountry8(data.get("MK_COUNTRY_CODE").toString().trim());
						}else{
							history.setHistoryOTHERCountry8("-1");
						}
						if (data.get("COMPLETED_IND") != null && !"".equals(data.get("COMPLETED_IND").toString().trim())){
							history.setHistoryOTHERComplete8(data.get("COMPLETED_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERComplete8("N");
						}
						if (data.get("FOREIGN_IND") != null && !"".equals(data.get("FOREIGN_IND").toString().trim())){
							history.setHistoryOTHERForeign8(data.get("FOREIGN_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERForeign8("N");
						}
						if (data.get("LOCK_UPDATE_IND") != null && !"".equals(data.get("LOCK_UPDATE_IND").toString().trim())){
							history.setHistoryOTHERLock8(data.get("LOCK_UPDATE_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERLock8("N");
						}
					}else if (Integer.parseInt(data.get("SEQ_NR").toString().trim()) == 8){
						history.setHistoryOTHERSEQ9(Integer.parseInt(data.get("SEQ_NR").toString().trim()));
						if (" ".equals(data.get("MK_UNK").toString())){
							history.setHistoryOTHERUniv9("OTHR");
						}else{
							history.setHistoryOTHERUniv9(data.get("MK_UNK").toString().trim());
						}
						if (data.get("INSTITUTION") != null && !"".equals(data.get("INSTITUTION").toString().trim())){
							history.setHistoryOTHERUnivText9(data.get("INSTITUTION").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERUnivText9(" ");
						}
						if (data.get("OTHER_INST_STU_NR") != null && !"".equals(data.get("OTHER_INST_STU_NR").toString().trim())){
							history.setHistoryOTHERStudnr9(data.get("OTHER_INST_STU_NR").toString().trim());
						}else{
							history.setHistoryOTHERStudnr9(" ");
						}
						if (data.get("OTHER_INST_QUAL") != null && !"".equals(data.get("OTHER_INST_QUAL").toString().trim())){
							history.setHistoryOTHERQual9(data.get("OTHER_INST_QUAL").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERQual9(" ");
						}
						if (data.get("FIRST_YEAR_REG") != null && !"".equals(data.get("FIRST_YEAR_REG").toString().trim())){
							history.setHistoryOTHERYearStart9(data.get("FIRST_YEAR_REG").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERYearStart9("-1");
						}
						if (data.get("YEAR_COMPLETED") != null && !"".equals(data.get("YEAR_COMPLETED").toString().trim())){
							history.setHistoryOTHERYearEnd9(data.get("YEAR_COMPLETED").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERYearEnd9("-1");
						}
						history.setHistoryOTHERYearEndDB9(history.getHistoryOTHERYearEnd9());
						if (data.get("MK_COUNTRY_CODE") != null && !"".equals(data.get("MK_COUNTRY_CODE").toString().trim())){
							history.setHistoryOTHERCountry9(data.get("MK_COUNTRY_CODE").toString().trim());
						}else{
							history.setHistoryOTHERCountry9("-1");
						}
						if (data.get("COMPLETED_IND") != null && !"".equals(data.get("COMPLETED_IND").toString().trim())){
							history.setHistoryOTHERComplete9(data.get("COMPLETED_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERComplete9("N");
						}
						if (data.get("FOREIGN_IND") != null && !"".equals(data.get("FOREIGN_IND").toString().trim())){
							history.setHistoryOTHERForeign9(data.get("FOREIGN_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERForeign9("N");
						}
						if (data.get("LOCK_UPDATE_IND") != null && !"".equals(data.get("LOCK_UPDATE_IND").toString().trim())){
							history.setHistoryOTHERLock9(data.get("LOCK_UPDATE_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERLock9("N");
						}
					}else if (Integer.parseInt(data.get("SEQ_NR").toString().trim()) == 9){
						history.setHistoryOTHERSEQ10(Integer.parseInt(data.get("SEQ_NR").toString().trim()));
						if (" ".equals(data.get("MK_UNK").toString())){
							history.setHistoryOTHERUniv10("OTHR");
						}else{
							history.setHistoryOTHERUniv10(data.get("MK_UNK").toString().trim());
						}
						if (data.get("INSTITUTION") != null && !"".equals(data.get("INSTITUTION").toString().trim())){
							history.setHistoryOTHERUnivText10(data.get("INSTITUTION").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERUnivText10(" ");
						}
						if (data.get("OTHER_INST_STU_NR") != null && !"".equals(data.get("OTHER_INST_STU_NR").toString().trim())){
							history.setHistoryOTHERStudnr10(data.get("OTHER_INST_STU_NR").toString().trim());
						}else{
							history.setHistoryOTHERStudnr10(" ");
						}
						if (data.get("OTHER_INST_QUAL") != null && !"".equals(data.get("OTHER_INST_QUAL").toString().trim())){
							history.setHistoryOTHERQual10(data.get("OTHER_INST_QUAL").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERQual10(" ");
						}
						if (data.get("FIRST_YEAR_REG") != null && !"".equals(data.get("FIRST_YEAR_REG").toString().trim())){
							history.setHistoryOTHERYearStart10(data.get("FIRST_YEAR_REG").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERYearStart10("-1");
						}
						if (data.get("YEAR_COMPLETED") != null && !"".equals(data.get("YEAR_COMPLETED").toString().trim())){
							history.setHistoryOTHERYearEnd10(data.get("YEAR_COMPLETED").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERYearEnd10("-1");
						}
						history.setHistoryOTHERYearEndDB10(history.getHistoryOTHERYearEnd10());
						if (data.get("MK_COUNTRY_CODE") != null && !"".equals(data.get("MK_COUNTRY_CODE").toString().trim())){
							history.setHistoryOTHERCountry10(data.get("MK_COUNTRY_CODE").toString().trim());
						}else{
							history.setHistoryOTHERCountry10("-1");
						}
						if (data.get("COMPLETED_IND") != null && !"".equals(data.get("COMPLETED_IND").toString().trim())){
							history.setHistoryOTHERComplete10(data.get("COMPLETED_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERComplete10("N");
						}
						if (data.get("FOREIGN_IND") != null && !"".equals(data.get("FOREIGN_IND").toString().trim())){
							history.setHistoryOTHERForeign10(data.get("FOREIGN_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERForeign10("N");
						}
						if (data.get("LOCK_UPDATE_IND") != null && !"".equals(data.get("LOCK_UPDATE_IND").toString().trim())){
							history.setHistoryOTHERLock10(data.get("LOCK_UPDATE_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERLock10("N");
						}
					}else if (Integer.parseInt(data.get("SEQ_NR").toString().trim()) == 10){
						history.setHistoryOTHERSEQ11(Integer.parseInt(data.get("SEQ_NR").toString().trim()));
						if (" ".equals(data.get("MK_UNK").toString())){
							history.setHistoryOTHERUniv11("OTHR");
						}else{
							history.setHistoryOTHERUniv11(data.get("MK_UNK").toString().trim());
						}
						if (data.get("INSTITUTION") != null && !"".equals(data.get("INSTITUTION").toString().trim())){
							history.setHistoryOTHERUnivText11(data.get("INSTITUTION").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERUnivText11(" ");
						}
						if (data.get("OTHER_INST_STU_NR") != null && !"".equals(data.get("OTHER_INST_STU_NR").toString().trim())){
							history.setHistoryOTHERStudnr11(data.get("OTHER_INST_STU_NR").toString().trim());
						}else{
							history.setHistoryOTHERStudnr11(" ");
						}
						if (data.get("OTHER_INST_QUAL") != null && !"".equals(data.get("OTHER_INST_QUAL").toString().trim())){
							history.setHistoryOTHERQual11(data.get("OTHER_INST_QUAL").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERQual11(" ");
						}
						if (data.get("FIRST_YEAR_REG") != null && !"".equals(data.get("FIRST_YEAR_REG").toString().trim())){
							history.setHistoryOTHERYearStart11(data.get("FIRST_YEAR_REG").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERYearStart11("-1");
						}
						if (data.get("YEAR_COMPLETED") != null && !"".equals(data.get("YEAR_COMPLETED").toString().trim())){
							history.setHistoryOTHERYearEnd11(data.get("YEAR_COMPLETED").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERYearEnd11("-1");
						}
						history.setHistoryOTHERYearEndDB11(history.getHistoryOTHERYearEnd11());
						if (data.get("MK_COUNTRY_CODE") != null && !"".equals(data.get("MK_COUNTRY_CODE").toString().trim())){
							history.setHistoryOTHERCountry11(data.get("MK_COUNTRY_CODE").toString().trim());
						}else{
							history.setHistoryOTHERCountry11("-1");
						}
						if (data.get("COMPLETED_IND") != null && !"".equals(data.get("COMPLETED_IND").toString().trim())){
							history.setHistoryOTHERComplete11(data.get("COMPLETED_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERComplete11("N");
						}
						if (data.get("FOREIGN_IND") != null && !"".equals(data.get("FOREIGN_IND").toString().trim())){
							history.setHistoryOTHERForeign11(data.get("FOREIGN_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERForeign11("N");
						}
						if (data.get("LOCK_UPDATE_IND") != null && !"".equals(data.get("LOCK_UPDATE_IND").toString().trim())){
							history.setHistoryOTHERLock11(data.get("LOCK_UPDATE_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERLock11("N");
						}
					}else if (Integer.parseInt(data.get("SEQ_NR").toString().trim()) == 11){
						history.setHistoryOTHERSEQ12(Integer.parseInt(data.get("SEQ_NR").toString().trim()));
						if (" ".equals(data.get("MK_UNK").toString())){
							history.setHistoryOTHERUniv12("OTHR");
						}else{
							history.setHistoryOTHERUniv12(data.get("MK_UNK").toString().trim());
						}
						if (data.get("INSTITUTION") != null && !"".equals(data.get("INSTITUTION").toString().trim())){
							history.setHistoryOTHERUnivText12(data.get("INSTITUTION").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERUnivText12(" ");
						}
						if (data.get("OTHER_INST_STU_NR") != null && !"".equals(data.get("OTHER_INST_STU_NR").toString().trim())){
							history.setHistoryOTHERStudnr12(data.get("OTHER_INST_STU_NR").toString().trim());
						}else{
							history.setHistoryOTHERStudnr12(" ");
						}
						if (data.get("OTHER_INST_QUAL") != null && !"".equals(data.get("OTHER_INST_QUAL").toString().trim())){
							history.setHistoryOTHERQual12(data.get("OTHER_INST_QUAL").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERQual12(" ");
						}
						if (data.get("FIRST_YEAR_REG") != null && !"".equals(data.get("FIRST_YEAR_REG").toString().trim())){
							history.setHistoryOTHERYearStart12(data.get("FIRST_YEAR_REG").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERYearStart12("-1");
						}
						if (data.get("YEAR_COMPLETED") != null && !"".equals(data.get("YEAR_COMPLETED").toString().trim())){
							history.setHistoryOTHERYearEnd12(data.get("YEAR_COMPLETED").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERYearEnd12("-1");
						}
						history.setHistoryOTHERYearEndDB12(history.getHistoryOTHERYearEnd12());
						if (data.get("MK_COUNTRY_CODE") != null && !"".equals(data.get("MK_COUNTRY_CODE").toString().trim())){
							history.setHistoryOTHERCountry12(data.get("MK_COUNTRY_CODE").toString().trim());
						}else{
							history.setHistoryOTHERCountry12("-1");
						}
						if (data.get("COMPLETED_IND") != null && !"".equals(data.get("COMPLETED_IND").toString().trim())){
							history.setHistoryOTHERComplete12(data.get("COMPLETED_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERComplete12("N");
						}
						if (data.get("FOREIGN_IND") != null && !"".equals(data.get("FOREIGN_IND").toString().trim())){
							history.setHistoryOTHERForeign12(data.get("FOREIGN_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERForeign12("N");
						}
						if (data.get("LOCK_UPDATE_IND") != null && !"".equals(data.get("LOCK_UPDATE_IND").toString().trim())){
							history.setHistoryOTHERLock12(data.get("LOCK_UPDATE_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERLock12("N");
						}
					}else if (Integer.parseInt(data.get("SEQ_NR").toString().trim()) == 12){
						history.setHistoryOTHERSEQ13(Integer.parseInt(data.get("SEQ_NR").toString().trim()));
						if (" ".equals(data.get("MK_UNK").toString())){
							history.setHistoryOTHERUniv13("OTHR");
						}else{
							history.setHistoryOTHERUniv13(data.get("MK_UNK").toString().trim());
						}
						if (data.get("INSTITUTION") != null && !"".equals(data.get("INSTITUTION").toString().trim())){
							history.setHistoryOTHERUnivText13(data.get("INSTITUTION").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERUnivText13(" ");
						}
						if (data.get("OTHER_INST_STU_NR") != null && !"".equals(data.get("OTHER_INST_STU_NR").toString().trim())){
							history.setHistoryOTHERStudnr13(data.get("OTHER_INST_STU_NR").toString().trim());
						}else{
							history.setHistoryOTHERStudnr13(" ");
						}
						if (data.get("OTHER_INST_QUAL") != null && !"".equals(data.get("OTHER_INST_QUAL").toString().trim())){
							history.setHistoryOTHERQual13(data.get("OTHER_INST_QUAL").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERQual13(" ");
						}
						if (data.get("FIRST_YEAR_REG") != null && !"".equals(data.get("FIRST_YEAR_REG").toString().trim())){
							history.setHistoryOTHERYearStart13(data.get("FIRST_YEAR_REG").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERYearStart13("-1");
						}
						if (data.get("YEAR_COMPLETED") != null && !"".equals(data.get("YEAR_COMPLETED").toString().trim())){
							history.setHistoryOTHERYearEnd13(data.get("YEAR_COMPLETED").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERYearEnd13("-1");
						}
						history.setHistoryOTHERYearEndDB13(history.getHistoryOTHERYearEnd13());
						if (data.get("MK_COUNTRY_CODE") != null && !"".equals(data.get("MK_COUNTRY_CODE").toString().trim())){
							history.setHistoryOTHERCountry13(data.get("MK_COUNTRY_CODE").toString().trim());
						}else{
							history.setHistoryOTHERCountry13("-1");
						}
						if (data.get("COMPLETED_IND") != null && !"".equals(data.get("COMPLETED_IND").toString().trim())){
							history.setHistoryOTHERComplete13(data.get("COMPLETED_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERComplete13("N");
						}
						if (data.get("FOREIGN_IND") != null && !"".equals(data.get("FOREIGN_IND").toString().trim())){
							history.setHistoryOTHERForeign13(data.get("FOREIGN_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERForeign13("N");
						}
						if (data.get("LOCK_UPDATE_IND") != null && !"".equals(data.get("LOCK_UPDATE_IND").toString().trim())){
							history.setHistoryOTHERLock13(data.get("LOCK_UPDATE_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERLock13("N");
						}
					}else if (Integer.parseInt(data.get("SEQ_NR").toString().trim()) == 13){
						history.setHistoryOTHERSEQ14(Integer.parseInt(data.get("SEQ_NR").toString().trim()));
						if (" ".equals(data.get("MK_UNK").toString())){
							history.setHistoryOTHERUniv14("OTHR");
						}else{
							history.setHistoryOTHERUniv14(data.get("MK_UNK").toString().trim());
						}
						if (data.get("INSTITUTION") != null && !"".equals(data.get("INSTITUTION").toString().trim())){
							history.setHistoryOTHERUnivText14(data.get("INSTITUTION").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERUnivText14(" ");
						}
						if (data.get("OTHER_INST_STU_NR") != null && !"".equals(data.get("OTHER_INST_STU_NR").toString().trim())){
							history.setHistoryOTHERStudnr14(data.get("OTHER_INST_STU_NR").toString().trim());
						}else{
							history.setHistoryOTHERStudnr14(" ");
						}
						if (data.get("OTHER_INST_QUAL") != null && !"".equals(data.get("OTHER_INST_QUAL").toString().trim())){
							history.setHistoryOTHERQual14(data.get("OTHER_INST_QUAL").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERQual14(" ");
						}
						if (data.get("FIRST_YEAR_REG") != null && !"".equals(data.get("FIRST_YEAR_REG").toString().trim())){
							history.setHistoryOTHERYearStart14(data.get("FIRST_YEAR_REG").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERYearStart14("-1");
						}
						if (data.get("YEAR_COMPLETED") != null && !"".equals(data.get("YEAR_COMPLETED").toString().trim())){
							history.setHistoryOTHERYearEnd14(data.get("YEAR_COMPLETED").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERYearEnd14("-1");
						}
						history.setHistoryOTHERYearEndDB14(history.getHistoryOTHERYearEnd14());
						if (data.get("MK_COUNTRY_CODE") != null && !"".equals(data.get("MK_COUNTRY_CODE").toString().trim())){
							history.setHistoryOTHERCountry14(data.get("MK_COUNTRY_CODE").toString().trim());
						}else{
							history.setHistoryOTHERCountry14("-1");
						}
						if (data.get("COMPLETED_IND") != null && !"".equals(data.get("COMPLETED_IND").toString().trim())){
							history.setHistoryOTHERComplete14(data.get("COMPLETED_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERComplete14("N");
						}
						if (data.get("FOREIGN_IND") != null && !"".equals(data.get("FOREIGN_IND").toString().trim())){
							history.setHistoryOTHERForeign14(data.get("FOREIGN_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERForeign14("N");
						}
						if (data.get("LOCK_UPDATE_IND") != null && !"".equals(data.get("LOCK_UPDATE_IND").toString().trim())){
							history.setHistoryOTHERLock14(data.get("LOCK_UPDATE_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERLock14("N");
						}
					}else if (Integer.parseInt(data.get("SEQ_NR").toString().trim()) == 14){
						history.setHistoryOTHERSEQ15(Integer.parseInt(data.get("SEQ_NR").toString().trim()));
						if (" ".equals(data.get("MK_UNK").toString())){
							history.setHistoryOTHERUniv15("OTHR");
						}else{
							history.setHistoryOTHERUniv15(data.get("MK_UNK").toString().trim());
						}
						if (data.get("INSTITUTION") != null && !"".equals(data.get("INSTITUTION").toString().trim())){
							history.setHistoryOTHERUnivText15(data.get("INSTITUTION").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERUnivText15(" ");
						}
						if (data.get("OTHER_INST_STU_NR") != null && !"".equals(data.get("OTHER_INST_STU_NR").toString().trim())){
							history.setHistoryOTHERStudnr15(data.get("OTHER_INST_STU_NR").toString().trim());
						}else{
							history.setHistoryOTHERStudnr15(" ");
						}
						if (data.get("OTHER_INST_QUAL") != null && !"".equals(data.get("OTHER_INST_QUAL").toString().trim())){
							history.setHistoryOTHERQual15(data.get("OTHER_INST_QUAL").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERQual15(" ");
						}
						if (data.get("FIRST_YEAR_REG") != null && !"".equals(data.get("FIRST_YEAR_REG").toString().trim())){
							history.setHistoryOTHERYearStart15(data.get("FIRST_YEAR_REG").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERYearStart15("-1");
						}
						if (data.get("YEAR_COMPLETED") != null && !"".equals(data.get("YEAR_COMPLETED").toString().trim())){
							history.setHistoryOTHERYearEnd15(data.get("YEAR_COMPLETED").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERYearEnd15("-1");
						}
						history.setHistoryOTHERYearEndDB15(history.getHistoryOTHERYearEnd15());
						if (data.get("MK_COUNTRY_CODE") != null && !"".equals(data.get("MK_COUNTRY_CODE").toString().trim())){
							history.setHistoryOTHERCountry15(data.get("MK_COUNTRY_CODE").toString().trim());
						}else{
							history.setHistoryOTHERCountry15("-1");
						}
						if (data.get("COMPLETED_IND") != null && !"".equals(data.get("COMPLETED_IND").toString().trim())){
							history.setHistoryOTHERComplete15(data.get("COMPLETED_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERComplete15("N");
						}
						if (data.get("FOREIGN_IND") != null && !"".equals(data.get("FOREIGN_IND").toString().trim())){
							history.setHistoryOTHERForeign15(data.get("FOREIGN_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERForeign15("N");
						}
						if (data.get("LOCK_UPDATE_IND") != null && !"".equals(data.get("LOCK_UPDATE_IND").toString().trim())){
							history.setHistoryOTHERLock15(data.get("LOCK_UPDATE_IND").toString().toUpperCase().trim());
						}else{
							history.setHistoryOTHERLock15("N");
						}
					}
				}
				count++;
				//log.debug("ApplyForStudentNumberQueryDAO - getPREVADM ---------------------------------------------------------");
			}
			//log.debug("ApplyForStudentNumberQueryDAO - getPREVADM - Records Found="+count+", List size="+count);
			
		} catch (Exception ex) {
				log.warn("ApplyForStudentNumberQueryDAO: Error - Retrieving Previous Academic Record for student nr / "+ studentNr + " / " +ex);
		}
		//log.debug("ApplyForStudentNumberQueryDAO - getPREVADM - Done - Return List to page");
		return history;
	}
	
	/** Retrieve Previous Academic Record **/
	public ArrayList<KeyValue> getPREVADMNew(String studentNr, String acaYear, String acaPeriod) throws Exception {
			
		//log.debug("ApplyForStudentNumberQueryDAO - getPREVADMNew - Start");

		ArrayList<KeyValue> keyvalues = new ArrayList<KeyValue>();
		
		try {
			String query  = "select MK_STUDENT_NR, SEQ_NR, FIRST_YEAR_REG, YEAR_COMPLETED, INSTITUTION, "
					+ " OTHER_INST_STU_NR, OTHER_INST_QUAL, FOREIGN_IND, LOCK_UPDATE_IND, "
					+ " COALESCE(COMPLETED_IND, 'N') as COMPLETED_IND, COALESCE(MK_UNK, ' ') as MK_UNK, COALESCE(MK_COUNTRY_CODE, ' ') as MK_COUNTRY_CODE "
					+ " from STUPREV "
					+ " where MK_STUDENT_NR = ? "
					+ " order by SEQ_NR ";
							
			//log.debug("ApplyForStudentNumberQueryDAO - getPREVADMNew - Query="+query+", StudentNr="+studentNr+", StudentNr="+studentNr+", acaYear="+acaYear+", acaPeriod="+acaPeriod);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr});
			Iterator i = queryList.iterator();
			
			int count = 0;
			while (i.hasNext()) {
				//log.debug("ApplyForStudentNumberQueryDAO - getPREVADMNew - has Next");
				ListOrderedMap data = (ListOrderedMap) i.next();
				KeyValue qual = new KeyValue();
				//log.debug("ApplyForStudentNumberQueryDAO - getPREVADMNew - SEQ_NR="+data.get("SEQ_NR").toString().trim());
				qual.setKey(data.get("SEQ_NR").toString().trim());

				StringBuilder values = new StringBuilder();
				if (" ".equals(data.get("MK_UNK").toString())){
					values.append("OTHR");
				}else{
					values.append(data.get("MK_UNK").toString().toUpperCase().trim());
				}
				values.append("~").append(data.get("INSTITUTION").toString().toUpperCase().trim());
				values.append("~").append(data.get("OTHER_INST_STU_NR").toString().toUpperCase().trim());
				values.append("~").append(data.get("OTHER_INST_QUAL").toString().toUpperCase().trim());
				values.append("~").append(data.get("FIRST_YEAR_REG").toString());
				values.append("~").append(data.get("YEAR_COMPLETED").toString());
				values.append("~").append(data.get("MK_COUNTRY_CODE").toString().toUpperCase().trim());
				values.append("~").append(data.get("COMPLETED_IND").toString().toUpperCase().trim());
				values.append("~").append(data.get("FOREIGN_IND").toString().toUpperCase().trim());
				values.append("~").append(data.get("LOCK_UPDATE_IND").toString().toUpperCase().trim());
				values.append("~").append("Y");
				qual.setValue(values.toString());
				keyvalues.add(qual);
				count++;
				//log.debug("ApplyForStudentNumberQueryDAO - getPREVADMNew - Records Found="+count+", Values="+values.toString());
			}
			//log.debug("ApplyForStudentNumberQueryDAO - getPREVADMNew - Records Found="+count);
			
		} catch (Exception ex) {
				//log.debug("ApplyForStudentNumberQueryDAO: Error - Retrieving Previous Academic Record for student nr / "+ studentNr + " / " +ex);
				log.warn("ApplyForStudentNumberQueryDAO: Error - Retrieving Previous Academic Record for student nr / "+ studentNr + " / " +ex);
		}
		//log.debug("ApplyForStudentNumberQueryDAO - getPREVADMNew - Done - Return List to page");
		return keyvalues;
	}
	
	/** Check for Existing Record 
	 * @throws Exception **/
	public boolean checkPREV(String studentNr, int seqNr) throws Exception{
		
		//log.debug("ApplyForStudentNumberQueryDAO - checkPREV - checkPREV");

		boolean isExist = false;
		
		try {
			String query  = "select MK_STUDENT_NR "
					+ " from STUPREV "
					+ " where MK_STUDENT_NR = ? "
					+ " and SEQ_NR = ? ";
							
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr, seqNr});

			//log.debug("ApplyForStudentNumberQueryDAO - checkPREV - Query="+query+", StudentNr="+studentNr+", SEQ_Nr="+seqNr);
			
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				isExist = true;
				//log.debug("ApplyForStudentNumberQueryDAO - checkPREV - Records Found");
			}
		} catch (Exception ex) {
				//log.debug("ApplyForStudentNumberQueryDAO: Error - Checking Previous Academic Record for student nr / "+ studentNr + " / " +ex);
				log.warn("ApplyForStudentNumberQueryDAO: Error - Checking Previous Academic Record for student nr / "+ studentNr + " / " +ex);
				throw new Exception(
						"ApplyForStudentNumberQueryDAO : Error - Checking Previous Academic Qualification for student nr / StudentNr="+studentNr+", SEQ_Nr="+seqNr+ " / " +ex);
		}
		//log.debug("ApplyForStudentNumberQueryDAO - checkPREV - Done - Return List to page");
		return isExist;

		
	}
	
	
	/** Update Existing Previous Academic Record 
	 * @throws Exception **/
	public int updatePREVADM(String UnisaStuNr, int SEQ_NR, String finalYear, String complete) throws Exception {

			//log.debug("ApplyForStudentNumberQueryDAO - updatePREVADM - Start");
		
			String dbParam = "";
			int resultInt = 0;
			int year = Integer.parseInt(finalYear);
			try {
				String query  = "update STUPREV "
							+ " set YEAR_COMPLETED = ?, "
							+ " COMPLETED_IND = ? "
							+ " where MK_STUDENT_NR = ? " 
							+ " and SEQ_NR = ? ";
		
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				resultInt = jdt.update(query, new Object []{year, complete, UnisaStuNr, SEQ_NR});
				dbParam = year+", "+complete+", "+UnisaStuNr+", "+SEQ_NR;
			
				//log.debug("ApplyForStudentNumberQueryDAO - updatePREVADM - Update - Query="+query+", resultInt="+resultInt);
				//log.debug("ApplyForStudentNumberQueryDAO - updatePREVADM - Update - resultInt: "+resultInt);
				//log.debug("ApplyForStudentNumberQueryDAO - updatePREVADM - Update - dbParam: "+dbParam);

			} catch (Exception ex) {
				log.warn("ApplyForStudentNumberQueryDAO: Error - Updating Previous Academic Qualification for student nr / "+UnisaStuNr + " / " + dbParam + " / " +ex);
				throw new Exception(
						"ApplyForStudentNumberQueryDAO : Error - Updating Previous Academic Qualification for student nr / "+UnisaStuNr + " / " + dbParam + " / " +ex);
			}
		//log.debug("ApplyForStudentNumberQueryDAO - updatePREVADM - End");
		return resultInt;
	}

	/** Save Previous Academic Record 
	 * @throws Exception **/
	public int savePREVADM(String UnisaStuNr, int maxPrev, String firstYear, String finalYear, String institution, 
				 String otherStuNr, String qualCode, String foreign, String complete, String instCode, String country) throws Exception {

			//log.debug("ApplyForStudentNumberQueryDAO - savePREVADM - Start");
		
			String dbParam = "";
			int resultInt = 0;
			
			try {
				String query  = "insert into STUPREV "
						+ " (MK_STUDENT_NR, SEQ_NR, FIRST_YEAR_REG, YEAR_COMPLETED, INSTITUTION, "
						+ " OTHER_INST_STU_NR, OTHER_INST_QUAL, ACCREDIT_SOURCE, ACCREDIT_REF, FOREIGN_IND,  "
						+ " NQF_LEVEL, OTHER_COMMENT,LOCK_UPDATE_IND, COMPLETED_IND, MK_UNK, MK_COUNTRY_CODE ) "
						+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
				//Insert into STUPREV (MK_STUDENT_NR,SEQ_NR,FIRST_YEAR_REG,YEAR_COMPLETED,INSTITUTION,OTHER_INST_STU_NR,OTHER_INST_QUAL,FOREIGN_IND,NQF_LEVEL,LOCK_UPDATE_IND,
				//COMPLETED_IND,MK_UNK,MK_COUNTRY_CODE) 
				//values (35320222,2,2011,2015,' ','A001654623','Masters in Business Administration (MBA)','Y','9','N','N','1031','2151');
				
				if ("1015".equalsIgnoreCase(country)){
					foreign = "N";
					if ("OTHR".equalsIgnoreCase(instCode)){
						instCode = " ";
					}
				}else{
					foreign = "Y";
					instCode = " ";
				}
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				resultInt = jdt.update(query, new Object []{UnisaStuNr,maxPrev,firstYear,finalYear,institution,otherStuNr,qualCode," "," ",foreign," "," ","N",complete,instCode,country});
				dbParam = UnisaStuNr+", "+maxPrev+", "+firstYear+", "+finalYear+", "+institution+", "+otherStuNr+", "+qualCode+", , ,"+foreign+", , ,N ,"+complete+", "+instCode+", "+country;
			
				//log.debug("ApplyForStudentNumberQueryDAO - savePREVADM - Insert - Query="+query+", resultInt="+resultInt);
				//log.debug("ApplyForStudentNumberQueryDAO - savePREVADM - Insert - resultInt: "+resultInt);
				//log.debug("ApplyForStudentNumberQueryDAO - savePREVADM - Insert - dbParam: "+dbParam);

			} catch (Exception ex) {
				log.warn("ApplyForStudentNumberQueryDAO: Error - Saving Previous Academic Qualification for student nr / "+UnisaStuNr + " / " + dbParam + " / " +ex);
				throw new Exception(
						"ApplyForStudentNumberQueryDAO : Error - Saving Previous Academic Qualification for student nr / "+UnisaStuNr + " / " + dbParam + " / " +ex);
			}
		//log.debug("ApplyForStudentNumberQueryDAO - savePREVADM - End");
		return resultInt;
	}

	
	public boolean getNDPMod(String acaYear, String acaPeriod, String studentNr, String qualCode, String studyUnit) {

		//log.debug("ApplyForStudentNumberQueryDAO - getNDPMod - Start");
	
		String dbParam = "";
		boolean result = false;

		try {
			String query  = "select Mk_study_unit_code from STUAPSUN "
					+ " where Academic_year = ? "
					+ " and Application_period = ? "
					+ " and MK_STUDENT_NR = ? "
					+ " and Qaulification_code = ? "
					+ " and Mk_study_unit_code = ? ";
				
			dbParam = acaYear+", "+acaPeriod+", "+studentNr+", "+qualCode+", "+studyUnit;
			
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{acaYear, acaPeriod, studentNr, qualCode, studyUnit});

			//log.debug("ApplyForStudentNumberQueryDAO - getNDPMod - Query="+query);
			//log.debug("ApplyForStudentNumberQueryDAO - getNDPMod - dbParam: "+dbParam);

			Iterator i = queryList.iterator();
			if(i.hasNext()) {
				result = true;
			}
		} catch (Exception ex) {
			log.warn("ApplyForStudentNumberQueryDAO: Error - Retrieving NDP Study Units for student nr / "+studentNr + " / " + dbParam + " / " +ex);
		}
		//log.debug("ApplyForStudentNumberQueryDAO - getNDPMod - End");
		return result;
	}
	
	public ArrayList<String> getNDPList(String acaYear, String acaPeriod, String studentNr) {

		//log.debug("ApplyForStudentNumberQueryDAO - getNDPList - Start");
	
		String dbParam = "";
		ArrayList<String> list = new ArrayList<String>();

		try {
			String query  = "select Mk_study_unit_code from STUAPSUN "
					+ " where Academic_year = ? "
					+ " and Application_period = ? "
					+ " and MK_STUDENT_NR = ? ";
				
			dbParam = acaYear+", "+acaPeriod+", "+studentNr;
			
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{acaYear, acaPeriod, studentNr});

			//log.debug("ApplyForStudentNumberQueryDAO - getNDPList - Query="+query);
			//log.debug("ApplyForStudentNumberQueryDAO - getNDPList - dbParam: "+dbParam);

			Iterator i = queryList.iterator();
			while(i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				list.add(data.get("Mk_study_unit_code").toString().trim());
			}
		} catch (Exception ex) {
			log.warn("ApplyForStudentNumberQueryDAO: Error - Retrieving NDP Study Unit List for student nr / "+studentNr + " / " + dbParam + " / " +ex);
		}
		//log.debug("ApplyForStudentNumberQueryDAO - getNDPList - End");
		return list;
	}
	
	
	public int saveNDPMod(String acaYear, String acaPeriod, String studentNr, String qualCode, String studyUnit) {

		//log.debug("ApplyForStudentNumberQueryDAO - saveNDPMod - Start");
	
		String dbParam = "";
		int resultInt = 0;

		try {
			String query  = "insert into STUAPSUN "
					+ " (Academic_year, Application_period, MK_STUDENT_NR, Qaulification_code, Mk_study_unit_code) "
					+ " values (?, ?, ?, ?, ?)";
			
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			resultInt = jdt.update(query, new Object []{acaYear, acaPeriod, studentNr, qualCode, studyUnit});
			dbParam = acaYear+", "+acaPeriod+", "+studentNr+", "+qualCode+", "+studyUnit;
		
			//log.debug("ApplyForStudentNumberQueryDAO - saveNDPMod - Insert - dbParam: "+dbParam);
			//log.debug("ApplyForStudentNumberQueryDAO - saveNDPMod - Insert - Query="+query+", resultInt="+resultInt);
			
		} catch (Exception ex) {
			log.warn("ApplyForStudentNumberQueryDAO: Error - Saving NDP Study Units for student nr / "+studentNr + " / " + dbParam + " / " +ex);
		}
		//log.debug("ApplyForStudentNumberQueryDAO - saveNDPMod - End");
		return resultInt;
	}

	/**
	 * Validate student Academic Record
	 */
	public boolean validateStudentRec(String studentNr, String acaYear) throws Exception {
		
		String query = "select mk_student_nr from stuaca "
				+ " where mk_student_nr = ? "
				+ " union "
				+ " select mk_student_nr from stuann "
				+ " where mk_student_nr= ? "
				+ " and mk_academic_year < ? ";

		try {
			//log.debug("ApplyForStudentNumberQueryDAO - validateStudentRec - Query="+query+", StudentNr="+studentNr+", AcademicYear="+acaYear);
			
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr, studentNr, acaYear});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				return true;
			}else{
				return false;
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error validating student academic record / " + ex);
		}
		
	}

	/**
	 * Validate student Annual Record
	 */
	public boolean validateStudentAnnual(String studentNr, String acaYear) throws Exception {
		
		String query = "select mk_student_nr "
				+ " from stuann "
				+ " where mk_student_nr= ? "
				+ " and mk_academic_year < ? ";

		try {
			//log.debug("ApplyForStudentNumberQueryDAO - validateStudentAnnual - Query="+query+", StudentNr="+studentNr+", AcademicYear="+acaYear);
			
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr, acaYear});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				return true;
			}else{
				return false;
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error validating student annual record / " + ex);
		}
	}

	/**
	 * Validate student birthday
	 */
	public String personalDetails(String studentNr, String selectInfo) throws Exception {
		
		String result="error";
		
		if (studentNr == null || selectInfo == null){
			return result;
		}

		String query="";
		
		if("NUMBER".equalsIgnoreCase(selectInfo)){
			query = "SELECT nr as dbInfo from stu where nr= ? ";
		}else if("BIRTH_DATE".equalsIgnoreCase(selectInfo)){
			query = "SELECT to_char(birth_date, 'YYYYMMDD') as dbInfo from stu where nr= ? ";
		}else if ("CELL_NUMBER".equalsIgnoreCase(selectInfo)){
			query = "SELECT cell_number as dbInfo from adrph where fk_adrcatcode = 1 and reference_no = ? ";
		}else if ("EMAIL_ADDRESS".equalsIgnoreCase(selectInfo)){
			query = "SELECT email_address as dbInfo from adrph where fk_adrcatcode = 1 and reference_no = ? ";
		}else{
			query = "SELECT " + selectInfo + " as dbInfo from stu where nr= ? ";
		}
		//log.debug("ApplyForStudentNumberQueryDAO - personalDetail="+ query);

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				if(data.get("dbInfo") !=null && !data.get("dbInfo").equals("")){
					result = data.get("dbInfo").toString().trim();
				}else{
					result = " ";
				}
			}	
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error retrieving student details / " + ex);
		}
		//log.debug("ApplyForStudentNumberQueryDAO - personalDetail="+ result);
		return result;
		
	}

	public boolean validateClosingDateAll(String acaYear) throws Exception {

		String query = "select academic_year "
				+ " from regdat "
				+ " where type like 'WAP%' "
				+ " and academic_year = ? "
				+ " and trunc(sysdate) between from_date and to_date";
				
				//log.debug("ApplyForStudentNumberQueryDAO - validateClosingDateAll - Query: " + query);
				try {
					JdbcTemplate jdt = new JdbcTemplate(getDataSource());
					List queryList = jdt.queryForList(query, new Object []{acaYear});
					Iterator i = queryList.iterator();
					if (i.hasNext()) {
						//log.debug("ApplyForStudentNumberQueryDAO - validateClosingDateAll - Result: TRUE");
						return true;
					}else{
						//log.debug("ApplyForStudentNumberQueryDAO - validateClosingDateAll - Result: FALSE");
						return false;
					}
				} catch (Exception ex) {
					throw new Exception(
							"ApplyForStudentNumberQueryDAO : Error closing date" + acaYear + " / " + ex);
				}
	}
	
	  /************ Get open dates to select qualifications *************/
		public ArrayList<String> validateClosingDate(String acaYear) throws Exception {
			
			boolean result = false;
			ArrayList<String> sDates = new ArrayList();

			String query = "select upper(regdat.type) as dateType, from_date, to_date "
					+ " from regdat "
					+ " where academic_year = ? "
					+ " and type like 'WAP%' "
					+ " and trunc(sysdate) between from_date and to_date "; 
			
			try{
				//log.debug("ApplyForStudentNumberQueryDAO - validateClosingDate - Year = " + acaYear + ", Query: " + query);
					
					JdbcTemplate jdt = new JdbcTemplate(getDataSource());
					List queryList = jdt.queryForList(query, new Object []{acaYear});
					Iterator i = queryList.iterator();
					while (i.hasNext()) {
						//log.debug("ApplyForStudentNumberQueryDAO - validateClosingDate - has Next");
						ListOrderedMap data = (ListOrderedMap) i.next();
						String dateType = data.get("dateType").toString().trim().toUpperCase();
						//log.debug("ApplyForStudentNumberQueryDAO - validateClosingDate - TYPE = " + dateType+", from_date = " + data.get("from_date").toString().trim()+", to_date = " + data.get("to_date").toString().trim());
						sDates.add(dateType);
					}
				} catch (Exception ex) {
					throw new Exception(
						"ApplyForStudentNumberQueryDAO : Error reading closing date: " + acaYear + "  / " + ex);
				}
			//Testing sDates
			//log.debug("ApplyForStudentNumberQueryDAO - validateClosingDate - sDates Size="+sDates.size());
			//if (!sDates.isEmpty()){ //Check Dates Array before building Query String
			//	for (int i=0; i < sDates.size(); i++){
			//		//log.debug("ApplyForStudentNumberQueryDAO - validateClosingDate - sDates="+sDates.get(i).toString());
			//	}
			//}
			
			return sDates;
		}
	
	public String validateQualification(String studentNr, String sQual) throws Exception {

		String query = "select type, under_post_categor , fk_katcode from grd where code= ? ";

		String code = "U";
		String code2 = "G";
		String code3 = "00";
		try {
			//log.debug("ApplyForStudentNumberQueryDAO - validateQualification - "+studentNr+" - Query: " + query);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{sQual});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				if (data.get("UNDER_POST_CATEGOR") != null){
					code = data.get("UNDER_POST_CATEGOR").toString();
					code2 = data.get("TYPE").toString();
					code3 = data.get("FK_KATCODE").toString();
					
				}
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error validating Qualification / " + ex);
		}
		code = code + code2 + code3;
		//log.debug("ApplyForStudentNumberQueryDAO - validateQualification - "+studentNr+" - Code="+code);
		return code;
	}
  
	/**
	 * Retrieve categories from database
	 */
	public Categories getCategories(String acaYear, String UHMDSelect, boolean isAdmin, boolean isWAPU, boolean isWAPRU, boolean isWAPADMU, boolean isWAPH, boolean isWAPRH, boolean isWAPADMH, boolean isWAPD, boolean isWAPADMD, boolean isWAPM, boolean isWAPADMM, boolean isWAPS, boolean isWAPADMS) throws Exception{
		
		//log.debug("ApplyForStudentNumberQueryDAO - getCategories - acaYear: " + acaYear);
		
		//log.debug("ApplyForStudentNumberQueryDAO - getCategories - UHMDSelect: " + UHMDSelect + ", isAdmin: " + isAdmin);
		//log.debug("ApplyForStudentNumberQueryDAO - getCategories - isWAPU: " + isWAPU + ", isWAPRU: " + isWAPRU);
		//log.debug("ApplyForStudentNumberQueryDAO - getCategories - isWAPADMU: " + isWAPADMU + ", isWAPH: " + isWAPH);
		//log.debug("ApplyForStudentNumberQueryDAO - getCategories - isWAPRH: " + isWAPRH + ", isWAPADMH: " + isWAPADMH);
		//log.debug("ApplyForStudentNumberQueryDAO - getCategories - isWAPS: " + isWAPS + ", isWAPADMS: " + isWAPADMS);
		//log.debug("ApplyForStudentNumberQueryDAO - getCategories - isWAPD: " + isWAPD + ", isWAPADMD: " + isWAPADMD);
		//log.debug("ApplyForStudentNumberQueryDAO - getCategories - isWAPM: " + isWAPM + ", isWAPADMM: " + isWAPADMM);
		
		boolean isDateWAPU = false;
		boolean isDateWAPH = false;
		boolean isDateWAPS = false;
		boolean isDateWAPD = false;
		boolean isDateWAPM = false;
		boolean isAnyOpen = false;
		boolean isM = false;
		boolean isUD = false;
		
		Categories categories = new Categories();
	
		//Check which dates are open
		StringBuilder queryString = new StringBuilder();
		queryString.append("Select ");
		
		if (isWAPU || isWAPRU || (isAdmin && isWAPADMU)){
			isDateWAPU = true;
			isAnyOpen = true;
		}
		if (isWAPH || isWAPRH || (isAdmin && isWAPADMH)){
			isDateWAPH = true;
			isAnyOpen = true;
		}
		if (isWAPD || (isAdmin && isWAPADMD)){
			isDateWAPD = true;
			isAnyOpen = true;
		}
		if (isWAPM || (isAdmin && isWAPADMM)){
			isDateWAPM = true;
			isAnyOpen = true;
		}
		if (isWAPS || (isAdmin && isWAPADMS)){
			isDateWAPS = true;
			isAnyOpen = true;
		}
	
		if(isAnyOpen){
			if ("MD".equalsIgnoreCase(UHMDSelect)){ //Master's
				if (isDateWAPM){ //Master's
					queryString.append(
						" '11' as CODE, 'MAGISTER TECHNOLOGIAE DEGREES' as ENG_DESCRIPTION from DUAL "
						+ " union all select '12' as CODE, 'MASTER`S DEGREES' as ENG_DESCRIPTION from DUAL ");
					isM = true;
				}
				if (isM && isDateWAPD){ //Doctoral
					queryString.append(
						" union all select '14' as CODE, 'DOCTORAL DEGREES' as ENG_DESCRIPTION from DUAL ");
				}
				if (!isM && isDateWAPD){ //Doctoral
					queryString.append(
						" '14' as CODE, 'DOCTORAL DEGREES' as ENG_DESCRIPTION from DUAL ");
				}
			}else if ("SLP".equalsIgnoreCase(UHMDSelect)){ //Short Learning Programmes
				if (isDateWAPS){ //SLP's
					queryString.append(
						" '15' as CODE, 'SHORT LEARNING PROGRAMMES' as ENG_DESCRIPTION from DUAL ");
				}
			}else { //Undergraduates & Honours (DSAR want both to be listed in same dropdown for now. May be split later)
				if (isDateWAPU){
					queryString.append(
						" '02' as CODE, 'HIGHER CERTIFICATES' as ENG_DESCRIPTION from DUAL "
						+ " union all select '03' as CODE, 'ADVANCED CERTIFICATES' as ENG_DESCRIPTION from DUAL "
						+ " union all select '04' as CODE, 'DIPLOMAS' as ENG_DESCRIPTION from DUAL "
						+ " union all select '05' as CODE, 'ADVANCED DIPLOMAS' as ENG_DESCRIPTION from DUAL "
						+ " union all select '06' as CODE, 'BACCALAUREUS TECHNOLOGIAE DEGREES' as ENG_DESCRIPTION from DUAL "
						+ " union all select '07' as CODE, 'BACHELOR DEGREES' as ENG_DESCRIPTION from DUAL "
						+ " union all select '99' as CODE, 'NON-DEGREES STUDIES (NDP)' as ENG_DESCRIPTION from DUAL ");
					isUD = true;
				}
				if (isUD && isDateWAPH){
					queryString.append(
						" union all select '09' as CODE, 'HONOURS DEGREES' as ENG_DESCRIPTION from DUAL "
						+ " union all select '10' as CODE, 'POSTGRADUATE DIPLOMAS' as ENG_DESCRIPTION from DUAL ");
				}else if (!isUD && isDateWAPH){
					queryString.append(
						" '09' as CODE, 'HONOURS DEGREES' as ENG_DESCRIPTION from DUAL "
						+ " union all select '10' as CODE, 'POSTGRADUATE DIPLOMAS' as ENG_DESCRIPTION from DUAL ");
				}
			}
		}else { //No dates open so do not return anything
			queryString.append(
				" 'XX' as CODE, 'ALL DATES CLOSED' as ENG_DESCRIPTION from DUAL ");
		}
		
	
		ArrayList catCodes = new ArrayList();
		ArrayList catDescs = new ArrayList();
		try {
			//log.debug("ApplyForStudentNumberQueryDAO - getCategories: " + queryString.toString());
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(queryString.toString());
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				catCodes.add(data.get("CODE").toString());
				catDescs.add(data.get("ENG_DESCRIPTION").toString());
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO: Error reading categories list / " + ex);
	
		} finally {
	
		}
		categories.setCatCodes(catCodes);
		categories.setCatDescs(catDescs);
	
		return categories;
	
	}
	
	/**
	 * Retrieve qualifications from database
	 */
	public Qualifications getQualifications(String studentNr, String stuExist, String acaYear, String acaPeriod, String catCode, String UHMDSelect, boolean isAdmin, boolean isWAPU, boolean isWAPRU, boolean isWAPADMU, boolean isWAPH, boolean isWAPRH, boolean isWAPADMH, boolean isWAPD, boolean isWAPADMD, boolean isWAPM, boolean isWAPADMM, boolean isWAPS, boolean isWAPADMS) throws SQLException, Exception{

		String query = "";
		
		//log.debug("ApplyForStudentNumberQueryDAO - getQualifications - acaYear: " + acaYear + ", catCode: " + catCode);
		
		//log.debug("ApplyForStudentNumberQueryDAO - getQualifications - UHMDSelect: " + UHMDSelect + ", isAdmin: " + isAdmin);
		//log.debug("ApplyForStudentNumberQueryDAO - getQualifications - isWAPU: " + isWAPU + ", isWAPRU: " + isWAPRU);
		//log.debug("ApplyForStudentNumberQueryDAO - getQualifications - isWAPADMU: " + isWAPADMU + ", isWAPH: " + isWAPH);
		//log.debug("ApplyForStudentNumberQueryDAO - getQualifications - isWAPRH: " + isWAPRH + ", isWAPADMH: " + isWAPADMH);
		//log.debug("ApplyForStudentNumberQueryDAO - getQualifications - isWAPS: " + isWAPS + ", isWAPADMS: " + isWAPADMS);
		//log.debug("ApplyForStudentNumberQueryDAO - getQualifications - isWAPD: " + isWAPD + ", isWAPADMD: " + isWAPADMD);
		//log.debug("ApplyForStudentNumberQueryDAO - getQualifications - isWAPM: " + isWAPM + ", isWAPADMM: " + isWAPADMM);
		
		ArrayList qualCodes = new ArrayList();
		ArrayList qualDescs = new ArrayList();
		
		Qualifications qualifications = new Qualifications();
		StringBuilder underPostString = new StringBuilder();
		
		boolean isDateWAPU 	= false;
		boolean isDateWAPH 	= false;
		boolean isDateWAPS	= false;
		boolean isDateWAPD 	= false;
		boolean isDateWAPM 	= false;
		boolean isM1 = false;
		boolean isM2 = false;
		boolean isUD1 = false;
		boolean isUD2 = false;
		boolean isAnyOpen = false;

		//log.debug("ApplyForStudentNumberQueryDAO - getQualifications - UHMDSelect: " + UHMDSelect);
		
		if (isWAPU || isWAPRU || (isAdmin && isWAPADMU)){
			isDateWAPU = true;
			isAnyOpen = true;
		}
		if (isWAPH || isWAPRH || (isAdmin && isWAPADMH)){
			isDateWAPH = true;
			isAnyOpen = true;
		}
		if (isWAPS || (isAdmin && isWAPADMS)){
			isDateWAPS = true;
			isAnyOpen = true;
		}
		if (isWAPD || (isAdmin && isWAPADMD)){
			isDateWAPD = true;
			isAnyOpen = true;
		}
		if (isWAPM || (isAdmin && isWAPADMM)){
			isDateWAPM = true;
			isAnyOpen = true;
		}
		
		//log.debug("ApplyForStudentNumberQueryDAO - getQualifications - catCode: " + catCode);

		//Check which dates are open
		if (!isAnyOpen){
			//Final catch-all if No dates open. Use 'Dummy' to not return anything
			//log.debug("ApplyForStudentNumberQueryDAO - getQualifications: No dates open, so use Dummy to not return anything");
			underPostString.append(
					" 'DOESNOTEXIST'");
		}else if ("SLP".equalsIgnoreCase(UHMDSelect) || "15".equalsIgnoreCase(catCode)){ //SLP
			if (isDateWAPS){ //SLP
				underPostString.append("'U','H','N','O'");
			}
		}else if ("MD".equalsIgnoreCase(UHMDSelect)){ //Master's & Doctoral
			if (isDateWAPM){ //Master's
				underPostString.append("'M'");
				isM2 = true;
			}
			if (isM2){
				underPostString.append(", 'D'");
			}else{
				underPostString.append("'D'");
			}
		}else { //Undergraduates & Honours (DSAR want both to be listed in same dropdown for now. May be split later)
			if (isDateWAPU){
				underPostString.append(
						"'U'" );
				isUD2 = true;
			}
			if (isUD2 && isDateWAPH){
				underPostString.append(
					" , 'H'");
			}else if (!isUD2 && isDateWAPH){
				underPostString.append(
					"'H'");
			}
		}	
		
		String wapString = "            and quaspc.app_open=regdat.type ";
		if (isAdmin){
			wapString = "            and quaspc.app_open like 'WAP%' " ;
		}
		String catString = "";
		if (catCode.equalsIgnoreCase("99")){ //Only NDP
			catString = "            and grd.fk_katcode = 9 " ;
			catCode = "07";
		}else if (catCode.equalsIgnoreCase("07")){ //Everything but NDP
			catString = "            and grd.fk_katcode <> 9 " ;
		}
		
		//log.debug("ApplyForStudentNumberQueryDAO - getQualifications - UnderPost: " + underPostString.toString());
		
		try {
			if ("SLP".equalsIgnoreCase(UHMDSelect) || "15".equalsIgnoreCase(catCode)){ //Short Learning Programmes
				query = "SELECT CODE, ENG_DESCRIPTION from ( "
						+ "		select distinct(trim(grd.code)) as CODE, upper(grd.long_eng_descripti) as ENG_DESCRIPTION "
						+ "		from grd, regdat, quaspc, gencod "
						+ "		where grd.in_use_flag = 'Y' "
						+ "		and grd.type = 'S' "
						+ "		and trim(grd.code) = trim(quaspc.mk_qualification_c) "
						+ "		and quaspc.app_open=regdat.type "
						+ "		and quaspc.repeaters_only <> 'Y' "
						+ "		and (trunc(sysdate) between regdat.from_date and regdat.to_date) "   
						+ "		and regdat.type like 'WAPS' "
						+ "		and (grd.to_year = 0 or grd.to_year >= ?) "
						+ "		and (grd.from_year = 0 or grd.from_year <= ? ) " 
						+ "		and (grd.repeaters_from_yea = 0 or grd.repeaters_from_yea > ? ) " 
						+ "		and trim(grd.code) not in ( "
  						+ "		select trim(mk_qualification_c) "
						+ "		  from flexdat "
						+ "		  where year = ? "
						+ "		  and period = ? "
						+ "		  and type like 'APP' " 
						+ "		  and (trunc(sysdate) < from_date or trunc(sysdate) > to_date)) "
						+ "		and trim(grd.mk_department_code) = trim(gencod.code) "
						+ "		and gencod.in_use_flag = 'Y' "
						+ "		and gencod.fk_gencatcode = '306' "
						+ "		and gencod.afr_description = 'UCL' "
						+ "		"
						+ "		union all "
						+ "		"
						+ "		select distinct(trim(grd.code)) as CODE, upper(grd.long_eng_descripti) as ENG_DESCRIPTION " 
						+ "		from grd, regdat, quaspc, gencod "
						+ "		where grd.in_use_flag = 'Y' "
						+ "		and grd.type = 'S' "
						+ "		and trim(grd.code) = trim(quaspc.mk_qualification_c) "
						+ "		and quaspc.app_open=regdat.type "
						+ "		and quaspc.repeaters_only <> 'Y' "
						+ "		and (trunc(sysdate) < regdat.from_date or trunc(sysdate) > regdat.to_date) " 
						+ "		and regdat.type like 'WAPS' "
						+ "		and (grd.to_year = 0 or grd.to_year >= ? ) "
						+ "		and (grd.from_year = 0 or grd.from_year <= ? ) " 
						+ "		and (grd.repeaters_from_yea = 0 or grd.repeaters_from_yea > ? ) " 
						+ "		and trim(grd.code) in ( "
						+ "		  select trim(mk_qualification_c) "
						+ "		  from flexdat "
						+ "		  where year = ? "
						+ "		  and period = ? "
						+ "		  and type like 'APP' " 
						+ "		  and (trunc(sysdate) between from_date and to_date)) "
						+ "		and trim(grd.mk_department_code) = trim(gencod.code) "
						+ "		and gencod.in_use_flag = 'Y' "
						+ "		and gencod.fk_gencatcode = '306' "
						+ "		and gencod.afr_description = 'UCL') "
						+ "		order by ENG_DESCRIPTION asc ";

				//log.debug("ApplyForStudentNumberQueryDAO - getQualifications - SLP - query=" + query);
				//log.debug("ApplyForStudentNumberQueryDAO - getQualifications - SLP - dbParam="+acaYear+", "+acaYear+", "+acaYear+", "+acaYear+", "+acaPeriod+", "+acaYear+", "+acaYear+", "+acaYear+", "+acaYear+", "+acaPeriod);
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(query, new Object []{acaYear, acaYear, acaYear, acaYear, acaPeriod, acaYear, acaYear, acaYear, acaYear, acaPeriod});
				Iterator i = queryList.iterator();
				while (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					qualCodes.add(data.get("CODE").toString());
					qualDescs.add(data.get("ENG_DESCRIPTION").toString().toUpperCase());
				}
			}else{
				query = "SELECT DISTINCT(T.CODE) as CODE, T.ENG_DESCRIPTION FROM ("
						+ "		select grd.code, upper(grd.long_eng_descripti) as eng_description "
					 	+ "		from grd, gencod, regdat, quaspc "
					 	+ "		where grd.in_use_flag = 'Y' "
						+ "		and (grd.type <> 'S' or grd.code='00051') " 
						+ "		and grd.code not in ('0605X','08052','97837') " 
						+ "		and grd.fk_katcode = gencod.code " 
						+ "		and gencod.fk_gencatcode = 172 "
						+ "		and (trim(gencod.afr_description) = ? or trim(gencod.info) = ?) "
						+ "		and grd.code = quaspc.mk_qualification_c "
						+ 		wapString
						+ 		catString
						+ "		and quaspc.repeaters_only <> 'Y' "
						+ "		and (trunc(sysdate) between regdat.from_date and regdat.to_date) "
						+ "		and (regdat.type like 'WAP%' or regdat.type like 'WEBAPP%') "
						+ "		and UNDER_POST_CATEGOR in ("+underPostString.toString()+") "
						+ "		and regdat.academic_year = ? "
						+ "		and (grd.to_year = 0 or grd.to_year >= ? ) "
						+ "		and (grd.from_year = 0 or grd.from_year <= ? ) "
						+ "		and (grd.repeaters_from_yea = 0 or grd.repeaters_from_yea > ? ) "
						+ " 	and grd.code not in ( "
						+ "			select mk_qualification_c "
						+ "			from flexdat "
						+ "			where year = ? "
						+ "			and period = ? "
						+ "			and type like 'APP' "
						+ "			and (trunc(sysdate) < from_date or trunc(sysdate) > to_date) "
						+ " 	) "
						+ " 	UNION ALL "
						+ "		select grd.code, upper(grd.long_eng_descripti) as eng_description "
					 	+ " 	from grd, gencod, regdat, quaspc "
					 	+ " 	where grd.in_use_flag = 'Y' "
						+ " 	and (grd.type <> 'S' or grd.code='00051') " 
						+ " 	and grd.code not in ('0605X','08052','97837') " 
						+ " 	and grd.fk_katcode = gencod.code " 
						+ " 	and gencod.fk_gencatcode = 172 "
						+ " 	and (trim(gencod.afr_description) = ? or trim(gencod.info) = ?) "
						+ " 	and grd.code = quaspc.mk_qualification_c "
						+ 		wapString
						+ 		catString
						+ " 	and quaspc.repeaters_only <> 'Y' "
						+ " 	and (trunc(sysdate) < regdat.from_date or trunc(sysdate) > regdat.to_date) "
						+ " 	and (regdat.type like 'WAP%' or regdat.type like 'WEBAPP%') "
						+ " 	and UNDER_POST_CATEGOR in ("+underPostString.toString()+") "
						+ " 	and regdat.academic_year = ? "
						+ " 	and (grd.to_year = 0 or grd.to_year >= ? ) "
						+ " 	and (grd.from_year = 0 or grd.from_year <= ? ) "
						+ " 	and (grd.repeaters_from_yea = 0 or grd.repeaters_from_yea > ? )"
						+ "		and grd.code in ( "
					 	+ "			select mk_qualification_c "
					 	+ "			from flexdat "
					 	+ "			where year = ? "
					 	+ "			and period = ? "
					 	+ "			and type like 'APP' "
					 	+ "     	and trunc(sysdate) between from_date and to_date "
					 	+ " 	) "
						+ " ) T "
						+ " order by T.ENG_DESCRIPTION ASC ";
				
				//log.debug("ApplyForStudentNumberQueryDAO - getQualifications - UHMD - query=" + query);
				//log.debug("ApplyForStudentNumberQueryDAO - getQualifications - UHMD - dbParam="+catCode+", "+catCode+", "+acaYear+", "+acaYear+", "+acaYear+", "+acaYear+", "+acaYear+", "+acaPeriod+", "+catCode+", "+catCode+", "+acaYear+", "+acaYear+", "+acaYear+", "+acaYear+", "+acaYear+", "+acaPeriod);
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(query, new Object []{catCode, catCode, acaYear, acaYear, acaYear, acaYear, acaYear, acaPeriod, catCode, catCode, acaYear, acaYear, acaYear, acaYear, acaYear, acaPeriod});
				Iterator i = queryList.iterator();
				while (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					qualCodes.add(data.get("CODE").toString());
					qualDescs.add(data.get("ENG_DESCRIPTION").toString().toUpperCase());
				}
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO: Error reading qualifications list / " + ex);

		} finally {

		}
		qualifications.setQualCodes(qualCodes);
		qualifications.setQualDescs(qualDescs);

		return qualifications;

	}
	
	/**
	 * Retrieve specializations for selected qualification
	 */
	public Specializations getSpecializations(String qualCode, String acaYear, String acaPeriod) throws Exception{
		
		//log.debug("ApplyForStudentNumberQueryDAO - getSpecializations - acaYear: " + acaYear);
		
		Specializations specializations = new Specializations();

		ArrayList<String> specCodes = new ArrayList<String>();
		ArrayList<String> specDescs = new ArrayList<String>();

		String query  = "SELECT DISTINCT(T.CODE) as CODE, T.ENG_DESCRIPTION FROM ( "
					+ " 	select substr(trim(quaspc.speciality_code)||'NVT',1,3) as CODE , "
					+ " 	substr(quaspc.english_descriptio,1,60) as ENG_DESCRIPTION  "
   					+ " 	from quaspc, regdat, grd "
					+ " 	where quaspc.in_use_flag = 'Y' "
					+ " 	and quaspc.mk_qualification_c = ? "
					+ " 	and (quaspc.repeaters_from_yea = 0 or quaspc.repeaters_from_yea > ? ) "
					+ " 	and (quaspc.from_year = 0 or quaspc.from_year <= ? ) "
					+ " 	and (quaspc.to_year = 0 or quaspc.to_year >= ? ) "
					+ " 	and quaspc.repeaters_only <> 'Y' "
					+ " 	and regdat.academic_year = ? "
					+ " 	and quaspc.app_open = regdat.type "
					+ " 	and quaspc.mk_qualification_c = grd.code "
					+ " 	and (trunc(sysdate) between regdat.from_date and regdat.to_date) "
					+ " 	and quaspc.speciality_code not in ( "
					+ " 		select flexdat.speciality_code "
					+ " 		from flexdat "
					+ " 		where flexdat.mk_qualification_c = quaspc.mk_qualification_c "
					+ " 		and flexdat.year = ? "
					+ " 		and flexdat.period = ? "
					+ " 		and flexdat.type like 'APP' "
					+ " 		and trunc(sysdate) between flexdat.from_date and flexdat.to_date "
					+ " 	) "
					+ "		UNION ALL "
					+ " 	select substr(trim(quaspc.speciality_code)||'NVT',1,3) as CODE , "
					+ " 	substr(quaspc.english_descriptio,1,60) as ENG_DESCRIPTION "
					+ " 	from quaspc, regdat, grd "
					+ " 	where quaspc.in_use_flag = 'Y' "
					+ " 	and quaspc.mk_qualification_c = ? "
					+ " 	and (quaspc.repeaters_from_yea = 0 or quaspc.repeaters_from_yea > ? ) "
					+ " 	and (quaspc.from_year = 0 or quaspc.from_year <= ? ) "
					+ " 	and (quaspc.to_year = 0 or quaspc.to_year >= ? ) "
					+ " 	and quaspc.repeaters_only <> 'Y' "
					+ " 	and regdat.academic_year = ? "
					+ " 	and quaspc.app_open = regdat.type "
					+ " 	and quaspc.mk_qualification_c = grd.code "
					+ " 	and (trunc(sysdate) < regdat.from_date or trunc(sysdate) > regdat.to_date) "
					+ " 	and quaspc.speciality_code not in ( "
					+ " 	select flexdat.speciality_code "
					+ " 		from flexdat "
					+ " 		where flexdat.mk_qualification_c = quaspc.mk_qualification_c "
					+ " 		and flexdat.year = ? "
					+ " 		and flexdat.period = ? "
					+ " 		and flexdat.type like 'APP' "
					+ "			and trunc(sysdate) < flexdat.from_date or trunc(sysdate) > flexdat.to_date "
					+ " 	) "
					+ " ) T "
					+ "	order by T.ENG_DESCRIPTION ASC ";
		try {
			//log.debug("ApplyForStudentNumberQueryDAO - getSpecializations - query=" + query+", qualCode=" + qualCode+", acaYear=" + acaYear+", acaPeriod=" + acaPeriod);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{qualCode, acaYear, acaYear, acaYear, acaYear, acaYear, acaPeriod, qualCode, acaYear, acaYear, acaYear, acaYear, acaYear, acaPeriod});
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				specCodes.add(data.get("CODE").toString());
				specDescs.add(data.get("ENG_DESCRIPTION").toString());
			}
		} catch (Exception ex) {
			//log.debug("ApplyForStudentNumberQueryDAO - getSpecializations - Error reading specializations list / " + ex);
			throw new Exception(
					"ApplyForStudentNumberQueryDAO: Error reading specializations list / " + ex);

		} finally {

		}
		specializations.setSpecCodes(specCodes);
		specializations.setSpecDescs(specDescs);

		return specializations;
	}
	
	  /************ Get type of qualification *************/
	  public String getQualType(String qualCode) throws Exception{
		  
	      String query   = "";
	      String dbCatCode = "";
	      String dbUnderPost = "";
	      String dbDepartment = "";
	      String dbType = "";
	      String qualType = "";


	      try {
		        query = "SELECT CAST (FK_KATCODE AS INT) AS FK_KATCODE, MK_DEPARTMENT_CODE, UNDER_POST_CATEGOR, TYPE "
		        		+ " from GRD "
						+ " where IN_USE_FLAG='Y' "
						+ " and CODE = ? ";

				//log.debug("ApplyForStudentNumberQueryDAO - getQualType - query="+query+", qualCode="+qualCode);
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(query, new Object []{qualCode});
				Iterator i = queryList.iterator();
				if (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();

					dbCatCode = data.get("FK_KATCODE").toString().trim();
					dbDepartment = data.get("MK_DEPARTMENT_CODE").toString().trim();
					dbUnderPost = data.get("UNDER_POST_CATEGOR").toString().trim();
					dbType    = data.get("TYPE").toString().trim();
					//log.debug("ApplyForStudentNumberQueryDAO - getQualType - dbCatCode="+dbCatCode);

					if ("05".equalsIgnoreCase(dbDepartment) && !"S".equalsIgnoreCase(dbType)){
						/**SBL Qualification**/
						qualType = "SBL";
					}else if ("S".equalsIgnoreCase(dbType)){
						/**SLP Qualification**/
						qualType = "SLP";
					}else{
						if ("U".equalsIgnoreCase(dbUnderPost)){
							/**Undergraduate**/
							qualType = "APP";
						}else if ("H".equalsIgnoreCase(dbUnderPost)){
							
							if ("05".equalsIgnoreCase(dbCatCode) || "06".equalsIgnoreCase(dbCatCode) || "48".equalsIgnoreCase(dbCatCode) || "70".equalsIgnoreCase(dbCatCode)){
								/**Honours**/
								qualType = "HON";
							}else if (("04".equalsIgnoreCase(dbCatCode)) || ("47".equalsIgnoreCase(dbCatCode)) || ("69".equalsIgnoreCase(dbCatCode))){
								/**Post Graduate**/
								qualType = "PG";
							}
					
						}else if ("M".equalsIgnoreCase(dbUnderPost) || "D".equalsIgnoreCase(dbUnderPost)){
							/**Masters & Doctoral**/
							qualType = "MD";
						}
					}
				}
	      	} catch (Exception ex) {
	      			throw new Exception(
	      					"ApplyForStudentNumberQueryDAO: Error occured on reading Qualification Type / " + ex);
	      	} finally {
	      	}
	      //log.debug("ApplyForStudentNumberQueryDAO - getQualType - qualType="+qualType);
	      return qualType;
	  }
	
	/**
	 * Retrieve student's previous or current qualification
	 */
	//Johanet 2018July BRD - added qualdesc
	public Qualifications getCompletedQualifications(String studentNr) throws Exception{

		//log.debug("ApplyForStudentNumberQueryDAO - getCompletedQualifications studentNr: " + studentNr + ", acaYear: " + acaYear);
		
		ArrayList qualCodes = new ArrayList();
		ArrayList qualDescs = new ArrayList();
		
		Qualifications qualifications = new Qualifications();
		
		String query = " select distinct stuaca.mk_qualification_c as CODE, upper(grd.long_eng_descripti) as QUALDESC "
					+ " from stuaca, grd"  
					+ " where stuaca.mk_qualification_c = grd.code"
					+ " and stuaca.mk_student_nr = ? "
					+ " and stuaca.status_code = 'CO' "
					+ " order by CODE asc ";

		try {
			//log.debug("ApplyForStudentNumberQueryDAO - getCompletedQualifications: " + query);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr});
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				qualCodes.add(data.get("CODE").toString());
				qualDescs.add(data.get("QUALDESC").toString());
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO: Error reading completed qualifications list / " + ex);

		} finally {

		}
		qualifications.setQualCodes(qualCodes);
		qualifications.setQualDescs(qualDescs);

		return qualifications;

	}
	//END Johanet 2018July BRD - added qualdesc

	/**
	 * Retrieve student's previous or current qualification
	 */	
	public ArrayList<String> getPrevQualifications(String studentNr) throws Exception{

	//log.debug("ApplyForStudentNumberQueryDAO - getPrevQualifications studentNr: " + studentNr);
		
		String query = " select distinct mk_qualification_c as CODE, substr(trim(fk_speciality_code)||'NVT',1,3) as SPEC "
					+ " from stuaca "
					+ " where mk_student_nr = ? "
					+ " order by CODE asc ";
		
		ArrayList qualPrevCodes = new ArrayList();

		try {
			//log.debug("ApplyForStudentNumberQueryDAO - getPrevQualifications: " + query);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr});
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				qualPrevCodes.add(data.get("CODE").toString());
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO: Error reading qualifications list / " + ex);

		} finally {

		}
		return qualPrevCodes;

	}
	
	/**
	 * Retrieve student's previous or current qualification
	 */
	public Qualifications getPrevSTUACA(String studentNr) throws Exception{

		//log.debug("ApplyForStudentNumberQueryDAO - getPrevSTUACA studentNr: " + studentNr);
		ArrayList qualCodes = new ArrayList();
		ArrayList qualDescs = new ArrayList();
		ArrayList specCodes = new ArrayList();
		ArrayList specDescs = new ArrayList();
		
		Qualifications qualifications = new Qualifications();	
		
		String query = " select * from ( "
					+ " select distinct stuaca.mk_qualification_c as QUALCODE, upper(grd.long_eng_descripti) as QUALDESC, "
					+ " COALESCE(stuaca.fk_speciality_code,'0') as SPECCODE, quaspc.ENGLISH_DESCRIPTIO as SPECDESC "
					+ " from stuaca, grd, quaspc "
					+ " where stuaca.mk_qualification_c = grd.code "
					+ " and stuaca.mk_qualification_c = quaspc.MK_QUALIFICATION_C "
					+ " and COALESCE(stuaca.fk_speciality_code,' ')  = quaspc.SPECIALITY_CODE "
					+ " and stuaca.mk_student_nr = ? "
					+ " and stuaca.last_academic_regi > 0 "
					+ " union "
					+ " select distinct(stuann.MK_HIGHEST_QUALIFI) AS QUALCODE, upper(grd.long_eng_descripti) as QUALDESC, "
					+ " COALESCE(stuann.speciality_code,'0') as SPECCODE, quaspc.ENGLISH_DESCRIPTIO as SPECDESC "
					+ " from stuann, grd, quaspc "
					+ " where stuann.MK_HIGHEST_QUALIFI = grd.code "
					+ " and stuann.MK_HIGHEST_QUALIFI = quaspc.MK_QUALIFICATION_C "
					+ " and COALESCE(stuann.speciality_code,' ')  = quaspc.SPECIALITY_CODE "
					+ " and stuann.mk_student_nr = ? "
					+ " and status_code  in ('RG','CA') "
					+ " ) order by QUALCODE ";
		try {
			//log.debug("ApplyForStudentNumberQueryDAO - getPrevSTUACA: " + query+", StudentNr="+studentNr);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());			
			List queryList = jdt.queryForList(query, new Object []{studentNr, studentNr});
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				qualCodes.add(data.get("QUALCODE").toString());
				qualDescs.add(data.get("QUALDESC").toString());
				specCodes.add(data.get("SPECCODE").toString());
				specDescs.add(data.get("SPECDESC").toString());
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO: Error reading previous qualifications list / " + ex);

		} finally {

		}
		qualifications.setQualCodes(qualCodes);
		qualifications.setQualDescs(qualDescs);
		qualifications.setSpecCodes(specCodes);
		qualifications.setSpecDescs(specDescs);
		
		return qualifications;

	}
	
	public StudySelected queryStudySelected(String studentNr, String acaYear, String acaPeriod) throws Exception {

		StudySelected selectedNew = new StudySelected();

		//log.debug("ApplyForStudentNumberQueryDAO - queryStudySelectedNew - studentNr: " + studentNr);
		if ("Temp".equalsIgnoreCase(studentNr) || "".equalsIgnoreCase(studentNr) ||studentNr == null){
			return null;
		}else{
			
			String query = "select new_qual, new_spes, choice_nr "
						+ " from STUAPQ "
						+ " where mk_student_nr = ? "
						+ " and academic_year = ? "
						+ " and application_period = ? "
						+ " order by choice_nr " ;

			try {
				//log.debug("ApplyForStudentNumberQueryDAO - queryStudySelectedNew - queryNew (else): " + query);
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, acaPeriod});
				Iterator i = queryList.iterator();
				while (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();

					if ("1".equals(data.get("choice_nr").toString())){
						if (data.get("new_qual").toString() != null && !"".equalsIgnoreCase(data.get("new_qual").toString())){
							selectedNew.setQual1(data.get("new_qual").toString());
						
							if ("".equalsIgnoreCase(data.get("new_spes").toString()) || " ".equalsIgnoreCase(data.get("new_spes").toString())){
								selectedNew.setSpec1("0");
							}else{
								selectedNew.setSpec1(data.get("new_spes").toString());
							}
						}
					}else if ("2".equals(data.get("choice_nr").toString())){
						if (data.get("new_qual").toString() != null && !"".equalsIgnoreCase(data.get("new_qual").toString())){
							selectedNew.setQual2(data.get("new_qual").toString());
							
							if ("".equalsIgnoreCase(data.get("new_spes").toString()) || " ".equalsIgnoreCase(data.get("new_spes").toString())){
								selectedNew.setSpec2("0");
							}else{
								selectedNew.setSpec2(data.get("new_spes").toString());
							}
						}
					}
				}
			} catch (Exception ex) {
				throw new Exception(
						"ApplyForStudentNumberQueryDAO: Error reading STUAPQ qualification / " + ex);

			} finally {

			}
			if (selectedNew.getQual1() != null && selectedNew.getQual2() != null){
				return selectedNew;
			}else{
				return null;
			}
		}
	}
	
	  /************ Get previous category to pre-select radio buttons *************/
	  public String selectPrevCategory(String qualCode) throws Exception{
		  
	      String query   = "";
	      String dbCatCode;
	      String dbUnderPost;
	      String dbType = "";
	      String catCode = "";


	      try {

		        query = "SELECT CAST (FK_KATCODE AS INT) AS FK_KATCODE, UNDER_POST_CATEGOR, TYPE "
		        		+ " from GRD "
						+ " where IN_USE_FLAG='Y' "
						+ " and CODE = ? ";

	    	  	//log.debug("ApplyForStudentNumberQueryDAO - selectPrevCategory - query: " + query);

				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(query, new Object []{qualCode});
				Iterator i = queryList.iterator();
				if (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();

					dbCatCode = data.get("FK_KATCODE").toString();
					dbUnderPost = data.get("UNDER_POST_CATEGOR").toString();
					dbType    = data.get("TYPE").toString();
					//log.debug("ApplyForStudentNumberQueryDAO - selectPrevCategory - CatCode=" + dbCatCode+",  UnderPost=" + dbUnderPost+",  Type=" + dbType);

					 /**Undergraduate**/
					if ("U".equalsIgnoreCase(dbUnderPost)){
						if (("1".equalsIgnoreCase(dbCatCode)) || ("11".equalsIgnoreCase(dbCatCode)) || ("21".equalsIgnoreCase(dbCatCode)) || ("22".equalsIgnoreCase(dbCatCode)) || ("41".equalsIgnoreCase(dbCatCode))){
							catCode = "2";
						 }else if ((("4".equalsIgnoreCase(dbCatCode)) && ("C".equalsIgnoreCase(dbType))) || ("42".equalsIgnoreCase(dbCatCode))){
							 catCode = "03";
						 }else if (("23".equalsIgnoreCase(dbCatCode)) || ("43".equalsIgnoreCase(dbCatCode))){
							 catCode = "04";
						 }else if ((("4".equalsIgnoreCase(dbCatCode)) && ("D".equalsIgnoreCase(dbType))) || ("24".equalsIgnoreCase(dbCatCode)) || ("25".equalsIgnoreCase(dbCatCode)) || ("44".equalsIgnoreCase(dbCatCode))){
							 catCode = "05";
						 }else if (("26".equalsIgnoreCase(dbCatCode)) || ("48".equalsIgnoreCase(dbCatCode))){
							 catCode = "06";
						 }else if (("2".equalsIgnoreCase(dbCatCode)) || ("3".equalsIgnoreCase(dbCatCode)) || ("45".equalsIgnoreCase(dbCatCode)) || ("46".equalsIgnoreCase(dbCatCode))){
							 catCode = "07";
						 }
					/**Post Graduate**/
					}else if ("H".equalsIgnoreCase(dbUnderPost)){
						if (("6".equalsIgnoreCase(dbCatCode)) || ("48".equalsIgnoreCase(dbCatCode))){
							 catCode = "09";
						}else if (("4".equalsIgnoreCase(dbCatCode)) || ("47".equalsIgnoreCase(dbCatCode))){
							 catCode = "10";
						}
					/**Masters & Doctoral**/
					}else if ("M".equalsIgnoreCase(dbUnderPost) || "D".equalsIgnoreCase(dbUnderPost)){
						if ("28".equalsIgnoreCase(dbCatCode)){
							 catCode = "11";
						}else if (("7".equalsIgnoreCase(dbCatCode)) || ("49".equalsIgnoreCase(dbCatCode))){
							 catCode = "12";
						}else if (("8".equalsIgnoreCase(dbCatCode)) || ("50".equalsIgnoreCase(dbCatCode))){
							 catCode = "14";
						}
					}
				}
	      	} catch (Exception ex) {
	      			throw new Exception(
	      					"ApplyForStudentNumberQueryDAO: Error occured on reading previous Category / " + ex);

	      	} finally {

	      	}
	      	return catCode;

	  }
	  
	  /************ Get previous category to show current qualification *************/
	  public String getExistQualDesc(String studentNr) throws Exception{

		  //log.debug("ApplyForStudentNumberQueryDAO - getExistQualDesc studentNr: " + studentNr);

		  String existQual = "";

	      try {

	    	  String query   = "select stuann.mk_highest_qualifi AS CODE, upper(grd.long_eng_descripti) as ENG_DESCRIPTION "
	    			  	+ " from stuann,grd "
						+ " where stuann.mk_highest_qualifi = grd.code "
						+ " and stuann.mk_student_nr = ? "
						+ " and stuann.status_code = 'RG' "
						+ " and rownum = 1 "
						+ " order by stuann.mk_academic_year desc";

	    	  	//log.debug("ApplyForStudentNumberQueryDAO - getExistQualDesc - query: " + query);

				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(query, new Object []{studentNr});
				Iterator i = queryList.iterator();
				if (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					existQual = data.get("CODE").toString() + " - " + data.get("ENG_DESCRIPTION").toString();
				}
	      	} catch (Exception ex) {
	      			throw new Exception(
	      					"ApplyForStudentNumberQueryDAO: Error occured on reading existing Qualification / " + ex);

	      	} finally {

	      	}
	      	return existQual;

	  }


	  /************ Get previous specialization show current qualification *************/
	  public String getExistSpec(String studentNr, String acaYear, String acaPeriod ) throws Exception{

		  //log.debug("ApplyForStudentNumberQueryDAO - getExistSpec studentNr: " + studentNr);
		  //log.debug("ApplyForStudentNumberQueryDAO - getExistSpec acaYear: " + acaYear);
		  //log.debug("ApplyForStudentNumberQueryDAO - getExistSpec acaPeriod: " + acaPeriod);

	      String existSpec = "";

	      try {

	    	  String query   = "select speciality_code as CODE, english_descriptio as eng_description "
							+ " from quaspc "
							+ " where in_use_flag = 'Y' "
							+ " and mk_qualification_c in (select mk_highest_qualifi from (select mk_highest_qualifi from stuann where mk_student_nr = ? and status_code = 'RG' and rownum = 1 order by mk_academic_year desc)) "
							+ " and speciality_code = (select speciality_code from (select speciality_code from stuann where mk_student_nr = ? and status_code = 'RG' and rownum = 1 order by mk_academic_year desc))";

	    	  	//log.debug("ApplyForStudentNumberQueryDAO getExistSpec - query: " + query);

				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(query, new Object []{studentNr, studentNr});
				Iterator i = queryList.iterator();
				if (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					existSpec = data.get("CODE").toString() + " - " + data.get("ENG_DESCRIPTION").toString();
				}
	      	} catch (Exception ex) {
	      			throw new Exception(
	      					"ApplyForStudentNumberQueryDAO: Error occured on reading existing Qualification / " + ex);

	      	} finally {

	      	}
	      	return existSpec;
	  }
	  
	 // select code from GENCOD where afr_description = '07' and fk_gencatcode = 172 and code <> '09' --NDP

	  /************ Get category or under post of qualification to show MasterDoctor Screen *************/
	  public ArrayList<String> getGENCODCategory(String genCod) throws Exception{

		  //log.debug("ApplyForStudentNumberQueryDAO - getGENCODCategory genCod=" + genCod);
		  
		  ArrayList<String> list = new ArrayList<String>();
		  
		  String doNDP = " and code <> '09' ";
		  
		  if ("99".equals(genCod)){
			  doNDP = " and code = '09' "; //Do NDP
		  }else{
			  doNDP = " and code <> '09' "; //Backup to ensure no NDP
		  }

	      try {
	    	  String query = "";
	    	  String query1   = "select code from gencod "
	    			  		+ " where afr_description = ? "
	    			  		+ " and fk_gencatcode = 172 "
	    			  		+ doNDP;

	    	  String query2   = "select code from ( "
	    			  		+ " select code from gencod " 
	    			  		+ " where afr_description = ? " 
	    			  		+ " and fk_gencatcode = 172 "
	    			  		+ " and code <> '09' "
	    			  		+ " union "
	    			  		+ " select '04' as code from dual "
	    			  		+ " )order by code ";
	    			  		
	    	  if ("02".equals(genCod)){
	    		  query = query2;
	    	  }else{
	    		  query = query1;
	    	  }
	    			 
	    	  //log.debug("ApplyForStudentNumberQueryDAO - getGENCODCategory - Query="+query+", genCod=" + genCod);
	    	  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	    	  List queryList = jdt.queryForList(query, new Object []{genCod});
	    	  Iterator i = queryList.iterator();
	    	  while(i.hasNext()) {
	    		  ListOrderedMap data = (ListOrderedMap) i.next();
	    		  //log.debug("ApplyForStudentNumberQueryDAO - getGENCODCategory - FK_CATCODE=" + data.get("code").toString());
	    		  
	    		  list.add(data.get("code").toString());	    		 
	    	  }
	      	}catch (Exception ex) {
	      		throw new Exception(
	      				"ApplyForStudentNumberQueryDAO: Error occured on reading GENCOD Category Code / " + ex);
	      	}
	      	return list;
	  }
	  
	  /************ Get category or under post of qualification to show MasterDoctor Screen *************/
	  public boolean getQualCategory(String qualCode) throws Exception{

		  //log.debug("ApplyForStudentNumberQueryDAO - getQualCategory qualCode: " + qualCode);

		  boolean isPrevQual = false;

	      try {

	    	  String query   = "select under_post_categor, fk_katcode, trim(gencod.afr_description) as category "
	    			  	+ " from grd, gencod "
	    			  	+ " where grd.in_use_flag = 'Y' "
	    			  	+ " and grd.fk_katcode = gencod.code "
	    			  	+ " and gencod.fk_gencatcode = 172 "
	    			  	+ " and grd.code = ? ";

	    	  //log.debug("ApplyForStudentNumberQueryDAO - getQualCategory - query: " + query);

	    	  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	    	  List queryList = jdt.queryForList(query, new Object []{qualCode});
	    	  Iterator i = queryList.iterator();
	    	  if (i.hasNext()) {
	    		  ListOrderedMap data = (ListOrderedMap) i.next();
	    		  //log.debug("ApplyForStudentNumberQueryDAO - getQualCategory - UnderPost="+data.get("under_post_categor").toString() + ", FK_CATCODE" + data.get("fk_katcode").toString() + ", CATEGORY" + data.get("category").toString());
	    		  
	    		  if ("H".equals(data.get("under_post_categor").toString())|| "M".equals(data.get("under_post_categor").toString()) || "D".equals(data.get("under_post_categor").toString())){
	    			  isPrevQual = true;
	    		  }else if ("47".equals(data.get("fk_katcode").toString())){
	    			  isPrevQual = true;
	    		  }else if("00019".equals(qualCode) || 
	    				  "03".equals(data.get("category").toString()) || 
	    				  "05".equals(data.get("category").toString()) || 
	    				  "06".equals(data.get("category").toString()) || 
	    				  "10".equals(data.get("category").toString())){
	    			  isPrevQual = true;
	    		  }
	    	  }
	      	}catch (Exception ex) {
	      		throw new Exception(
	      				"ApplyForStudentNumberQueryDAO: Error occured on reading Qualification Category / " + ex);
	      	} finally {
	      	}
	      	return isPrevQual;

	  }
	  
	/**
	 * This method returns a list of languages
	 */
	public ArrayList<LabelValueBean> getLanguages() throws Exception {
		ArrayList list = new ArrayList();
		String query = "select code, eng_description from tal order by eng_description";

		try {
			list = readDatabase(query, "getLanguages");
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error reading language list / " + ex);
		}
		return list;
	}
	

	/**
	 * This method returns a list of population groups
	 */
	public ArrayList<LabelValueBean> getPopulationGroups() throws Exception {
		ArrayList list = new ArrayList();
		String query = "select code, eng_description from etn order by eng_description";

		try {
			list = readDatabase(query, "getPopulationGroups");
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error reading population group list / " + ex);
		}
		return list;
	}

	/**
	 * This method returns a list of occupations
	 */
	public ArrayList<LabelValueBean> getOccupations() throws Exception {
		ArrayList list = new ArrayList();
		String query = "select code, eng_description from brp where in_use_flag = 'Y'" +
        				" order by eng_description";

		try {
			list = readDatabase(query, "getOccupations");
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error reading occupation list / " + ex);
		}
		return list;
	}

	/**
	 * This method returns a list of economic sectors
	 */
	public ArrayList<LabelValueBean> getEconomicSectors() throws Exception {
		ArrayList list = new ArrayList();
		String query = "select code, eng_description from eko where in_use_flag = 'Y'" +
        			   "order by eng_description";

		try {
			list = readDatabase(query, "getEconomicSectors");
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error reading economic sectors list / " + ex);
		}
		return list;
	}

	public ArrayList<LabelValueBean> getPrevActivity() throws Exception {
		ArrayList list = new ArrayList();
		String query = "select code, eng_description from gencod where in_use_flag = 'Y' " +
        			   "and fk_gencatcode=87 order by eng_description";

		try {
			list = readDatabase(query, "getPrevActivity");
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error reading previous activity list / " + ex);
		}
		return list;
	}

	/**
	 * This method returns a list of disabilities
	 */
	public ArrayList<LabelValueBean> getDisabilities() throws Exception {
		ArrayList list = new ArrayList();
		String query = "select code, eng_description from distype order by eng_description";

		try {
			list = readDatabase(query, "getDisabilities");
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error reading disability list / " + ex);
		}
		return list;
	}

	/**
	 * This method returns a list of Universities
	 */
	public ArrayList<LabelValueBean> getUniversities() throws Exception {
		ArrayList list = new ArrayList();
		String query = "Select code, upper(eng_name) as eng_description from UNK where "+
					   " (TYPE = 'UNIV' or TYPE='TECH') and IN_USE_FLAG = 'Y'" +
					   " Order by eng_description";
		//log.debug("ApplyForStudentNumberQueryDAO - getUniversities - query="+query);

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				list.add(new org.apache.struts.util.LabelValueBean(data.get("eng_description").toString().toUpperCase(), data.get("CODE").toString()+"@"+data.get("eng_description").toString()));
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error reading other university list / " + ex);
		}
		return list;
	}
	
	/**
	 * This method returns a list of Universities without name in value
	 */
	public ArrayList<LabelValueBean> getUniversitiesShort() throws Exception {
		ArrayList list = new ArrayList();
		String query = "Select * from ( "
					+ " Select code, upper(eng_name) as eng_description from UNK where "
					+ " (TYPE = 'UNIV' or TYPE='TECH') and IN_USE_FLAG = 'Y' "
					+ " Order by eng_description) "
					+ " union all "
					+ " Select 'OTHR' as code, 'OTHER' as eng_description from dual ";
		
		//log.debug("ApplyForStudentNumberQueryDAO - getUniversitiesShort - query="+query);

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				list.add(new org.apache.struts.util.LabelValueBean(data.get("eng_description").toString().toUpperCase(), data.get("CODE").toString()));
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error reading other university list / " + ex);
		}
		return list;
	}
	
	/**
	 * This method returns a list of Universities
	 */
	public ArrayList<LabelValueBean> getUniversitiesOther() throws Exception {
		ArrayList list = new ArrayList();
		String query = "Select * from ( "
					+ " Select code, upper(eng_name) as eng_description from UNK where "
					+ " (TYPE = 'UNIV' or TYPE='TECH') and IN_USE_FLAG = 'Y' "
					+ " Order by eng_description) "
					+ " union all "
					+ " Select '0000' as code, 'OTHER' as eng_description from dual ";
		
	  	//log.debug("ApplyForStudentNumberQueryDAO - getUniversitiesOther - query: " + query);

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				list.add(new org.apache.struts.util.LabelValueBean(data.get("eng_description").toString().toUpperCase(), data.get("CODE").toString()+"@"+data.get("eng_description").toString()));
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error reading other university list / " + ex);
		}
		return list;
	}
	
	/**
	 * This method returns a list of Exam Centres
	 */
	public ArrayList<KeyValue> getInstitutions() throws Exception {

		ArrayList<KeyValue> keyvalues = new ArrayList<KeyValue>();
		String query = "Select * from ( "
				+ " Select code, upper(eng_name) as eng_description from UNK where "
				+ " (TYPE = 'UNIV' or TYPE='TECH') and IN_USE_FLAG = 'Y' "
				+ " Order by eng_description) "
				+ " union all "
				+ " Select 'OTHR' as code, 'OTHER' as eng_description from dual ";

		//log.debug("ApplyForStudentNumberDAO - getUniversities - Query="+query);
		
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();

			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				KeyValue univ = new KeyValue();
				univ.setKey(data.get("CODE").toString());
				univ.setValue(data.get("ENG_DESCRIPTION").toString().toUpperCase());
				keyvalues.add(univ);
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error reading Institution list / " + ex);
		}
		return keyvalues;
	}

	/**
	 * This method returns a list of countries
	 */
	public ArrayList<LabelValueBean> getCountries() throws Exception {
		ArrayList list = new ArrayList();
		String query = "select code, eng_description from lns where in_use_flag = 'Y' order by eng_description";

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
					"ApplyForStudentNumberQueryDAO : Error reading country list / " + ex);
		}
		return list;
	}
	
	/**
	 * This method returns a list of countries without name in value
	 */
	public ArrayList<LabelValueBean> getCountriesShort() throws Exception {
		ArrayList list = new ArrayList();
		String query = "select code, eng_description from lns where in_use_flag = 'Y' order by eng_description";

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				list.add(new org.apache.struts.util.LabelValueBean(data.get("ENG_DESCRIPTION").toString(), data.get("CODE").toString()));
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error reading country list / " + ex);
		}
		return list;
	}
	
	/**
	 * This method returns a list of examination centres
	 */
	public ArrayList<LabelValueBean> getExaminationCentreListold() throws Exception {
		ArrayList list = new ArrayList();
		String query = "select code, eng_description, type from eks where in_use_flag = 'Y'"
        			 + " order by eng_description";

		try {
    	  	//log.debug("ApplyForStudentNumberQueryDAO - getExaminationCentreListold - query: " + query);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				list.add(new org.apache.struts.util.LabelValueBean(data.get("ENG_DESCRIPTION").toString().toUpperCase()+" - "+data.get("CODE").toString(), data.get("CODE").toString()+data.get("ENG_DESCRIPTION").toString()));
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error reading exam list / " + ex);
		}
		return list;
	}

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
	public ArrayList<LabelValueBean> getPostalCodes(String suburb, String town, String listIdentifier, String postalBoxStreet) throws Exception {
		
		//log.debug("ApplyForStudentNumberQueryDAO: - getPostalCodes - suburb=" + suburb+", town=" + town+", listIdentifier=" + listIdentifier+", postalBoxStreet=" + postalBoxStreet);
		String query = "";
		String bsParam1 = "B";
		String bsParam2 = "S";
		String label = null;
		String descr = null;

		ArrayList<LabelValueBean> list = new ArrayList<LabelValueBean>();
		
		try {
			if ((suburb == null || suburb.trim()=="")&&	(town == null || town.trim()=="")){
				return list;
			}
			
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = null;

			String suburbParam = (suburb == null || suburb.trim()=="")? 
					suburbParam = "%" : 
					suburb.toUpperCase() + "%";
			String townParam = (town == null || town.trim()=="")?
						townParam = "%":
						town.toUpperCase() + "%";
	
			//2016 Edmund
			//Rule changed to get Postal Code for Postal as Box first, then if not exit, get Postal Code for Street and vice versa
			if (listIdentifier.equalsIgnoreCase("Postal")){
				if (postalBoxStreet == null || "".equals(postalBoxStreet)){
					bsParam1 = "B";
				}else{
					bsParam1 = postalBoxStreet;
				}
			}else{
				bsParam1 = "S";
				bsParam2 = "B";
			}
			String physicalTest = "";
			if (listIdentifier.equalsIgnoreCase("Physical")){
				physicalTest = "where query2.suburb not in (select suburb from query1) ";
			}
		
			//log.debug("ApplyForStudentNumberQueryDAO - getPostalCodes - bsParam1=" + bsParam1+", bsParam2=" + bsParam2);
			if (listIdentifier.equalsIgnoreCase("Postal")){
				
				query = "Select code , type , suburb, town from pstcod "
						+ " where suburb like ? "
						+ " and town like ? "
						+ " and type = ? "
						+ " order by code";
				
				//log.debug("ApplyForStudentNumberQueryDAO - getPostalCodes - query=" + query + " suburbParam=" + suburbParam + ", townParam=" + townParam + ", bsParam1=" + bsParam1);
				queryList = jdt.queryForList(query, new Object []{suburbParam, townParam, bsParam1});
				
			}else{
				query = " WITH query1 as (                                     "
				    	+ "       	Select code , type , suburb, town from pstcod "
				    	+ "       	where upper(suburb) like ?                    " 
				    	+ " 		and upper(town) like ?                        " 
				    	+ "			and type = ?                                  " 
				    	+ " 		order by code                                 "
				    	+ "  ),                                                   "
				    	+ "  query2 as (                                          "
				    	+ "       	Select code , type , suburb, town from pstcod "
				    	+ "       	where upper(suburb) like ?                    " 
				    	+ " 		and upper(town) like ?                        " 
				    	+ "			and type = ?                                  " 
				    	+ " 		order by code                                 "
				    	+ " )                                                     "
				    	+ " SELECT *                                              "
				    	+ " FROM query1                                           "
				    	+ " UNION ALL                                             "
				    	+ " SELECT *                                              "
				    	+ " FROM query2                                           "
				    	+ physicalTest;
				
				//log.debug("ApplyForStudentNumberQueryDAO - getPostalCodes - query=" + query + " suburbParam=" + suburbParam + ", townParam=" + townParam + ", bsParam1=" + bsParam1 + ", bsParam2=" + bsParam2);
				queryList = jdt.queryForList(query, new Object []{suburbParam, townParam, bsParam1, suburbParam, townParam, bsParam2});
			}
	
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
					"ApplyForStudentNumberQueryDAO : Error reading postal codes list / " + ex);
		}
		return list;
	}

	private ArrayList readDatabase(String query, String callMethod){
		ArrayList list = new ArrayList();

	  	//log.debug("ApplyForStudentNumberQueryDAO - "+callMethod+" - query: " + query);

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(query);
		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			list.add(new org.apache.struts.util.LabelValueBean(data.get("eng_description").toString().toUpperCase(), data.get("CODE").toString()+"@"+data.get("eng_description").toString()));
		}

		return list;
	}

	/**
	 * This method returns the next sequential student number
	 */
	public String getNextDummyStudentNr() throws Exception {
		String dummyNr ="";

		String query = "Select dummyStudnr_seq.nextval dummyNr from dual";

		try {
    	  	//log.debug("ApplyForStudentNumberQueryDAO - getNextDummyStudentNr - query: " + query);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				dummyNr = data.get("DUMMYNR").toString();
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error reading other university list / " + ex);
		}
		return dummyNr;
	}

	public void updStudentApp(String studentNr,String doctype) throws Exception {
		String query="";
		if("1".equalsIgnoreCase(doctype)){
			query="update stuapp set doc_supplied_schoo='Y' where application_nr= ? ";
		}else if ("2".equalsIgnoreCase(doctype)){
			query="update stuapp set doc_supplied_acadr='Y' where application_nr= ? ";
		}else if ("3".equalsIgnoreCase(doctype)){
			query="update stuapp set doc_supplied_id='Y' where application_nr= ? ";
		}else if ("4".equalsIgnoreCase(doctype)){
			query="update stuapp set doc_supplied_marri='Y' where application_nr= ? ";
		}else if ("5".equalsIgnoreCase(doctype)){
			query="update stuapp set doc_supplied_toefl='Y' where application_nr= ? ";
		}

	  	//log.debug("ApplyForStudentNumberQueryDAO - updStudentApp - query: " + query);

		
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int result = jdt.update(query, new Object []{studentNr});
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error updating student application for student nr / "+studentNr+ "  / " + ex);
		}
		return ;
	}

	public Calendar insertSolSunRecord(Student student, Qualification qualification, Date currentDate) throws Exception {

		Log log = LogFactory.getLog(ApplyForStudentNumberQueryDAO.class);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new java.util.Date().getTime());

		if (student.getNumber()==null)
	    	 throw new Exception("ApplyForStudentNr: dummy student number not set");

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		final Student stu = student;
		final Qualification qual = qualification;
		final Date dateTime = currentDate;

		final String sql = "insert into solstu "+
			  "(mk_student_nr, mk_qualification_c, surname, initials, first_names, " +
              "previous_name, birth_date, gender, correspondence_lan, idnum, disability_type, " +
              "cell_number, home_number, work_number, fax_number, email_address, " +
              "address_line1, address_line2, address_line3, address_line4, address_line5, " +
              "address_line6, country, postal_code, fellow_students_fl, exam_centre, " +
              "occupation,econ_sector,previous_year,type,mk_title," +
              "mk_home_language, mk_etncode, change_timestamp," +
              "physical_adr1,physical_adr2,physical_adr3,physical_adr4,physical_adr5," +
              "physical_adr6, phys_postal_code, contact_telno, nationality," +
              "courier_adr1,courier_adr2,courier_adr3,courier_adr4,courier_adr5,courier_adr6," +
              "courier_postal_cod, previous_status, prev_instit_name, passport_nr, " +
              "second_institute) " +
            //  "values(dummyStudnr_seq.nextval,?,?,?,?,?,?,?,?,?,"+
              "values(?,?,?,?,?,?,?,?,?,?," +
              		 "?,?,?,?,?,?,?,?,?,?," +
              		 "?,?,?,?,?,?,?,?,?,?," +
              		 "?,?,?,?,?,?,?,?,?,?," +
              		 "?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		jdt.update( new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql);

				  Calendar cal = Calendar.getInstance();
			      cal.set(Integer.parseInt(stu.getBirthYear()),(Integer.parseInt(stu.getBirthMonth())-1),Integer.parseInt(stu.getBirthDay()));
			      //student nr
			      ps.setString(1,stu.getNumber());
			      //qualification
			      if (qual.getQualCode().length() > 5)
			        ps.setString(2,qual.getQualCode().substring(0,5));
			      else
			        ps.setString(2,qual.getQualCode());
			      //surname
			      if (stu.getSurname().length() > 28)
			        ps.setString(3,stu.getSurname().substring(0,28).toUpperCase());
			      else
			        ps.setString(3,stu.getSurname().toUpperCase());
			      // initials
			      if (stu.getInitials().length() > 20)
			        ps.setString(4,stu.getInitials().substring(0,20).toUpperCase());
			      else
			        ps.setString(4,stu.getInitials().toUpperCase());
			      // first names
			      if (stu.getFirstnames().length() > 60)
			        ps.setString(5,stu.getFirstnames().substring(0,60).toUpperCase());
			      else
			        ps.setString(5,stu.getFirstnames().toUpperCase());
			      // Maiden name
			      if (stu.getMaidenName().length() > 28)
			        ps.setString(6,stu.getMaidenName().substring(0,28).toUpperCase());
			      else
			        ps.setString(6,stu.getMaidenName().toUpperCase());
			      // birth date
			      ps.setDate(7,new java.sql.Date(cal.getTime().getTime()));
			      //gender
			      ps.setString(8,stu.getGender().substring(0,1));
			      //correspondance language
			      ps.setString(9,stu.getLanguage().substring(0,1));
			      //RSA id nr
			      if("R".equalsIgnoreCase(stu.getIdType())){
			    	  if (stu.getIdNumber().length() > 13)
			    		  ps.setString(10,stu.getIdNumber().substring(0,13));
			    	  else
			    		  ps.setString(10,stu.getIdNumber());
			      }else{
			    	  ps.setString(10,"");
			      }
			      //disability
			      if(stu.getDisability().getCode()!=null){
			    	  ps.setInt(11,Integer.parseInt(stu.getDisability().getCode().trim()));
			      }
			      //cell nr
			      if (stu.getCellNr().length() > 28)
			        ps.setString(12,stu.getCellNr().substring(0,28));
			      else
			        ps.setString(12,stu.getCellNr());
			      //homephone
			      if (stu.getHomePhone().length() > 28)
			        ps.setString(13,stu.getHomePhone().substring(0,28));
			      else
			        ps.setString(13,stu.getHomePhone());
			      //work phone
			      if (stu.getWorkPhone().length() > 28)
			        ps.setString(14,stu.getWorkPhone().substring(0,28));
			      else
			        ps.setString(14,stu.getWorkPhone());
			      //fax nr
			      if (stu.getFaxNr().length() > 28)
			        ps.setString(15,stu.getFaxNr().substring(0,28));
			      else
			        ps.setString(15,stu.getFaxNr());
			      //email address
			      if (stu.getEmailAddress().length() > 60)
			        ps.setString(16,stu.getEmailAddress().substring(0,60));
			      else
			        ps.setString(16,stu.getEmailAddress());
			      //postal address
			      //line1
			      if (stu.getPostalAddress().getLine1().length() > 28)
			        ps.setString(17,stu.getPostalAddress().getLine1().substring(0,28).toUpperCase());
			      else
			        ps.setString(17,stu.getPostalAddress().getLine1().toUpperCase());
			      //line2
			      if (stu.getPostalAddress().getLine2().length() > 28)
			        ps.setString(18,stu.getPostalAddress().getLine2().substring(0,28).toUpperCase());
			      else
			        ps.setString(18,stu.getPostalAddress().getLine2().toUpperCase());
			      //line3
			      if (stu.getPostalAddress().getLine3().length() > 28)
			        ps.setString(19,stu.getPostalAddress().getLine3().substring(0,28).toUpperCase());
			      else
			        ps.setString(19,stu.getPostalAddress().getLine3().toUpperCase());
			      //line4
			      if (stu.getPostalAddress().getLine4().length() > 28)
			        ps.setString(20,stu.getPostalAddress().getLine4().substring(0,28).toUpperCase());
			      else
			        ps.setString(20,stu.getPostalAddress().getLine4().toUpperCase());
			      //line5
			      if (stu.getPostalAddress().getLine5().length() > 28)
			        ps.setString(21,stu.getPostalAddress().getLine5().substring(0,28).toUpperCase());
			      else
			        ps.setString(21,stu.getPostalAddress().getLine5().toUpperCase());
			      //line6
			      if (stu.getPostalAddress().getLine6().length() > 28)
			        ps.setString(22,stu.getPostalAddress().getLine6().substring(0,28).toUpperCase());
			      else
			        ps.setString(22,stu.getPostalAddress().getLine6().toUpperCase());
			      //country
			      if (stu.getCountry().getDesc()!= null && stu.getCountry().getDesc().length() > 28)
			        ps.setString(23,stu.getCountry().getDesc().substring(0,28).toUpperCase());
			      else
			        ps.setString(23,stu.getCountry().getDesc().toUpperCase());
			      //postal code
			      if (stu.getPostalAddress().getAreaCode().length() > 4)
			        ps.setString(24,stu.getPostalAddress().getAreaCode().substring(0,4));
			      else
			        ps.setString(24,stu.getPostalAddress().getAreaCode());
			      //share contact details
			      ps.setString(25,stu.getShareContactDetails().substring(0,1));
			      //exam centre
			      if (stu.getExam().getExamCentre().getCode().length() > 5)
			        ps.setString(26,stu.getExam().getExamCentre().getCode().substring(0,5));
			      else
			        ps.setString(26,stu.getExam().getExamCentre().getCode());
			      //occupation
			      if (stu.getOccupation().getCode().length() > 5)
			        ps.setString(27,stu.getOccupation().getCode().substring(0,5));
			      else
			        ps.setString(27,stu.getOccupation().getCode());
			      //economic sector
			      if (stu.getEconomicSector().getCode().length() > 5)
			        ps.setString(28,stu.getEconomicSector().getCode().substring(0,5));
			      else
			        ps.setString(28,stu.getEconomicSector().getCode());
			      //last year of registration at last tertiary institution
			      if (stu.getLastRegYear().length() > 4)
			        ps.setString(29,stu.getLastRegYear().substring(0,4));
			      else
			        ps.setString(29,stu.getLastRegYear());
			      //Type
			      ps.setString(30,"F");
			      //title
			      if (stu.getTitle().length() > 10)
			        ps.setString(31,stu.getTitle().substring(0,10));
			      else
			        ps.setString(31,stu.getTitle());
			      //home language
			      if (stu.getHomeLanguage().getCode().length() > 2)
			        ps.setString(32,stu.getHomeLanguage().getCode().substring(0,2));
			      else
			        ps.setString(32,stu.getHomeLanguage().getCode());
			      //population
			      if (stu.getPopulationGroup().getCode().length() > 4)
			        ps.setString(33,stu.getPopulationGroup().getCode().substring(0,4));
			      else
			        ps.setString(33,stu.getPopulationGroup().getCode());
			      //timestamp
			      ps.setTimestamp(34,new Timestamp(dateTime.getTime()));
			      //physical address
			      //line1
			      if (stu.getPhysicalAddress().getLine1().length() > 28)
			        ps.setString(35,stu.getPhysicalAddress().getLine1().substring(0,28).toUpperCase());
			      else
			        ps.setString(35,stu.getPhysicalAddress().getLine1().toUpperCase());
			      //line2
			      if (stu.getPhysicalAddress().getLine2().length() > 28)
			        ps.setString(36,stu.getPhysicalAddress().getLine2().substring(0,28).toUpperCase());
			      else
			        ps.setString(36,stu.getPhysicalAddress().getLine2().toUpperCase());
			      //line3
			      if (stu.getPhysicalAddress().getLine3().length() > 28)
			        ps.setString(37,stu.getPhysicalAddress().getLine3().substring(0,28).toUpperCase());
			      else
			        ps.setString(37,stu.getPhysicalAddress().getLine3().toUpperCase());
			      //line4
			      if (stu.getPhysicalAddress().getLine4().length() > 28)
			        ps.setString(38,stu.getPhysicalAddress().getLine4().substring(0,28).toUpperCase());
			      else
			        ps.setString(38,stu.getPhysicalAddress().getLine4().toUpperCase());
			      if (stu.getPhysicalAddress().getLine5().length() > 28)
				    ps.setString(39,stu.getPhysicalAddress().getLine5().substring(0,28).toUpperCase());
				  else
				    ps.setString(39,stu.getPhysicalAddress().getLine5().toUpperCase());
			      if (stu.getPhysicalAddress().getLine6().length() > 28)
			    	ps.setString(40,stu.getPhysicalAddress().getLine6().substring(0,28).toUpperCase());
				  else
				    ps.setString(40,stu.getPhysicalAddress().getLine6().toUpperCase());
			      if (stu.getPhysicalAddress().getAreaCode().length() > 4)
			    	ps.setString(41,stu.getPhysicalAddress().getAreaCode().substring(0,4));
			      else
				    ps.setString(41,stu.getPhysicalAddress().getAreaCode());
			      //contact tel nr
			      if (stu.getContactNr().length() > 40)
			        ps.setString(42,stu.getContactNr().substring(0,40));
			      else
			    	ps.setString(42,stu.getContactNr());
			      //nationality
			      if (stu.getNationalty().getCode()!=null && stu.getContactNr().length() > 4)
			        ps.setString(43,stu.getNationalty().getCode().substring(0,4));
			      else
			        ps.setString(43,stu.getNationalty().getCode());
			      //courier address
			      if (stu.getCourierAddress().getLine1().length() > 28)
			    	  ps.setString(44, stu.getCourierAddress().getLine1().substring(0,28).toUpperCase());
			      else
			    	  ps.setString(44, stu.getCourierAddress().getLine1().toUpperCase());
			      if (stu.getCourierAddress().getLine2().length() > 28)
			    	  ps.setString(45, stu.getCourierAddress().getLine2().substring(0,28).toUpperCase());
			      else
			    	  ps.setString(45, stu.getCourierAddress().getLine2().toUpperCase());
			      if (stu.getCourierAddress().getLine3().length() > 28)
			    	  ps.setString(46, stu.getCourierAddress().getLine3().substring(0,28).toUpperCase());
			      else
			    	  ps.setString(46, stu.getCourierAddress().getLine3().toUpperCase());
			      if (stu.getCourierAddress().getLine4().length() > 28)
			    	  ps.setString(47, stu.getCourierAddress().getLine4().substring(0,28).toUpperCase());
			      else
			    	  ps.setString(47, stu.getCourierAddress().getLine4().toUpperCase());
			      if (stu.getCourierAddress().getLine5().length() > 28)
			    	  ps.setString(48, stu.getCourierAddress().getLine5().substring(0,28).toUpperCase());
			      else
			    	  ps.setString(48, stu.getCourierAddress().getLine5().toUpperCase());
			      if (stu.getCourierAddress().getLine6().length() > 28)
			    	  ps.setString(49, stu.getCourierAddress().getLine6().substring(0,28).toUpperCase());
			      else
			    	  ps.setString(49, stu.getCourierAddress().getLine6().toUpperCase());
			      if (stu.getCourierAddress().getAreaCode().length() > 4)
				      ps.setString(50,stu.getCourierAddress().getAreaCode().substring(0,4));
				  else
				      ps.setString(50,stu.getCourierAddress().getAreaCode());
			      
			      // status of last tertiary institution
			      if (stu.getLastStatus()!= null && stu.getLastStatus().length() > 50)
				        ps.setString(51,stu.getLastStatus().substring(0,1));
				      else
				        ps.setString(51,stu.getLastStatus());
			      //Last tertiary institute
			      if (stu.getLastInstitution()!= null && stu.getLastInstitution().length() > 50)
			        ps.setString(52,stu.getLastInstitution().substring(0,50).toUpperCase());
			      else
			        ps.setString(52,stu.getLastInstitution().toUpperCase());
			      //Passportnr/foreign id
			      if("P".equalsIgnoreCase(stu.getIdType())){
			    	  if (stu.getPassportNumber()!=null && !"".equalsIgnoreCase(stu.getPassportNumber()))
			    		  ps.setString(53,stu.getPassportNumber());
			      } else if("F".equalsIgnoreCase(stu.getIdType())){
			    	  if (stu.getForeignIdNumber()!= null && !"".equalsIgnoreCase(stu.getForeignIdNumber()))
			    		  ps.setString(53,stu.getForeignIdNumber());
			      }else{
			    	  ps.setString(53,"");
			      }
			      if (stu.getOtherUniversity().getCode()!= null && !"".equalsIgnoreCase(stu.getOtherUniversity().getCode()))
			        ps.setString(54,stu.getOtherUniversity().getCode());
			      else
			        ps.setString(54,"");
				return ps;
			}
		});

		//log.debug("ApplyForStudentNumberQueryDAO - Solsun record written for " + student.getNumber());
		// return timestamp
		return cal;

	}
	

	/**
	 * This method updated the address table with the student Cellular Number and Email Address
	 * Note: This is only for returning students as new applications are created with correct details.
	 * 
	 * @param 
	 */
	public void updStudentContact(String studentNr,String emailAddress, String cellNumber) throws Exception {
		
		String query="update adrph set email_address = ?, cell_number = ? where reference_no = ? and fk_adrcatcode=1";
	
		try {
    	  	//log.debug("ApplyForStudentNumberQueryDAO - updStudentContact - query: " + query);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int result = jdt.update(query, new Object []{emailAddress, cellNumber, studentNr});
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error updating student contact details for student nr / "+studentNr + "  / " + ex);
		}
		return ;
	}
	
	/**
	 * This method updated the STUANN table with the Staff or Staff Dependent and Qualification Completion Detail
	 * Note: This is only for returning students as new applications are created with correct details.
	 */
	public void updateStudent(String studentNr,String staffCurrent, String staffDependant, String qualComplete, String qualExplain, String prisoner, String acaYear) throws Exception {
		
		String query="update stuann "
				+ " set current_staff = ?, "
				+ " dependant_staff = ?, "
				+ " qual_completing = ?, "
				+ " qual_explain = ?, "
				+ " prisoner = ? "
				+ " where mk_student_nr = ? "
				+ " and mk_academic_year = ? ";

		try {
    	  	//log.debug("ApplyForStudentNumberQueryDAO - updateStudent - query: " + query);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int result = jdt.update(query, new Object []{staffCurrent, staffDependant, qualComplete, qualExplain, prisoner, studentNr, acaYear});
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error updating student staff details for student nr / "+studentNr + "  / " + ex);
		}
		return ;
	}
	
	/**
	 * This method updated the STU table with the Hard-Coded Correspondence Language = English
	 * Note: This is as per request from Vinesh - 2017
	 */
	public void updateStudentCorrespondence(String studentNr) throws Exception {
		
		String query = "UPDATE STU SET MK_CORRESPONDENCE = 'E' "
					+ " WHERE nr = ?";

		try {
    	  	//log.debug("ApplyForStudentNumberQueryDAO - updateStudentCorrespondence - query: " + query);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int result = jdt.update(query, new Object []{studentNr});
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error updating Correspondence details for student nr / "+studentNr+ " / " + ex);
		}
		return ;
	}
	
	public String getSTUXMLRef(String studentNr, String acaYear, String acaPeriod, String referenceType, String referenceValue, String callingMethod) {
		
		//log.debug("ApplyForStudentNumberQueryDAO - getSTUXMLRef - "+studentNr+" - Start");
		
		String result = "False"; 
		
		//log.debug("ApplyForStudentNumberQueryDAO - getSTUXMLRef - "+studentNr+" - Calling Method: " + callingMethod);

		String query = "SELECT COALESCE( ( "
				+ " select max(REFERENCE_SEQUENCE) "
				+ " from STUXML "
				+ " where mk_student_nr = ? "
				+ " and mk_academic_year = ? "
				+ " and mk_academic_period = ? "
				+ " and reference_type = ? "
				+ " and reference_value = ? "
				+ " ), 0) as REFERENCE_SEQUENCE from dual ";

		try {
			//log.debug("ApplyForStudentNumberQueryDAO - getSTUXMLRef - "+studentNr+" - Query: " + query);
			//log.debug("ApplyForStudentNumberQueryDAO - getSTUXMLRef - "+studentNr+" - Query: " +studentNr+", "+acaYear+", "+acaPeriod+", "+referenceType+", "+referenceValue);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, acaPeriod, referenceType, referenceValue});
			Iterator<?> i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				//log.debug("ApplyForStudentNumberQueryDAO - getSTUXMLRef: Has Row");
				result = data.get("REFERENCE_SEQUENCE").toString().trim();
				//log.debug("ApplyForStudentNumberQueryDAO - getSTUXMLRef - "+studentNr+" - Result: STUXML "+callingMethod+" Exists");
			}else{
				result = "0";
				//log.debug("ApplyForStudentNumberQueryDAO - getSTUXMLRef - "+studentNr+" - Result: No STUXML "+callingMethod);
			}
			
		} catch (Exception ex) {
			//log.debug("ApplyForStudentNumberQueryDAO: getSTUXMLRef - Error Retrieving STUXML StuInfo"+referenceType+" for Student / "+studentNr + "/ " +ex);
			result = "Error Retrieving STUXML StuInfo for Returning Student / "+studentNr;
		}
		//log.debug("ApplyForStudentNumberQueryDAO - getSTUXMLRef - "+studentNr+" - Retrieve StuInfo Sequence - Done");
		return result;
	}
	
	
	public String isSTUXML(String studentNr, String acaYear, String acaPeriod, String referenceType, String referenceValue, String callingMethod) {
		
		//log.debug("ApplyForStudentNumberQueryDAO - isSTUXML - "+studentNr+" - Start");
		
		String result = "False";  //Note: Not Boolean;
		
		//log.debug("ApplyForStudentNumberQueryDAO - isSTUXML - "+studentNr+" - Calling Method: " + callingMethod);

		String query = "select detail from STUXML where mk_student_nr = ? and mk_academic_year = ? and mk_academic_period = ? and reference_type = ? and reference_value = ?";

		try {
			Thread.sleep(Long.parseLong("1000"));
			//log.debug("ApplyForStudentNumberQueryDAO - isSTUXML - "+studentNr+" - Query: " + query);
			//log.debug("ApplyForStudentNumberQueryDAO - isSTUXML - "+studentNr+" - Query: " +studentNr+", "+acaYear+", "+acaPeriod+", "+referenceType+", "+referenceValue);
			
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, acaPeriod, referenceType, referenceValue});
			Iterator<?> i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				result = "True";
				//log.debug("ApplyForStudentNumberQueryDAO - isSTUXML - "+studentNr+" - Result: STUXML Exists="+data.get("detail").toString().trim());
			}else{
				result = "False";
				//log.debug("ApplyForStudentNumberQueryDAO - isSTUXML - "+studentNr+" - Result: No STUXML");
			}
			
		} catch (Exception ex) {
			//log.debug("ApplyForStudentNumberQueryDAO: Error Retrieving STUXML "+referenceType+" "+referenceValue+" for Student / "+studentNr + "/ " +ex);
			result = "Error Retrieving STUXML Category for Returning Student / "+studentNr;
		}
		//log.debug("ApplyForStudentNumberQueryDAO - isSTUXML - "+studentNr+" - "+callingMethod+" - Done");
		return result;
	}
	
	public String saveSTUXML(String studentNr, String acaYear, String acaPeriod, String referenceType, String referenceValue, int referenceSequence, String referenceData, String callingMethod, String queryType) {

		//log.debug("ApplyForStudentNumberQueryDAO - saveSTUXML - "+studentNr+" - Start");
		
		String query = "";
		String dbParam = "";
		int resultInt = 0;
		String result = "False";  //Note: Not Boolean;

		//log.debug("ApplyForStudentNumberQueryDAO - saveSTUXML - "+studentNr+" - referenceSequence="+referenceSequence+" - referenceData="+referenceData+", Calling Method: " + callingMethod);
		
		try{
			query  = "insert into STUXML (mk_student_nr,mk_academic_year,mk_academic_period,reference_type,reference_value,reference_sequence,date_initial,date_modified,detail) "
					+ " values (?, ?, ?, ?, ?, ?, systimestamp, systimestamp, ?)";

			dbParam = studentNr+","+acaYear+","+acaPeriod+","+referenceType+","+referenceValue+", "+referenceSequence+", "+referenceData;
			//log.debug("ApplyForStudentNumberQueryDAO - saveSTUXML - INSERT - Query="+query+" - "+studentNr+" - dbParam: "+dbParam);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			resultInt = jdt.update(query, new Object []{studentNr,acaYear,acaPeriod,referenceType,referenceValue,referenceSequence,referenceData});
			
			//log.debug("ApplyForStudentNumberQueryDAO - saveSTUXML - "+studentNr+" - QueryType="+queryType+", resultInt="+resultInt);
			
			if (resultInt > 0){
				result = "True";
			}
		} catch (Exception ex) {
			//log.debug("ApplyForStudentNumberQueryDAO: Error "+queryType+" "+referenceType+" "+referenceValue+" "+referenceSequence+" "+referenceData+" for Student= "+studentNr + " / " + dbParam + " / " + ex);
			log.info("ApplyForStudentNumberQueryDAO: Error "+queryType+" "+referenceType+" "+referenceValue+" "+referenceSequence+" "+referenceData+" for Student= "+studentNr + " / " + dbParam + " / " + ex);
			result = "Error "+queryType+" saveSTUXML "+referenceType+" "+referenceValue+" "+referenceSequence+" for Student= "+studentNr;
		}
		//log.debug("ApplyForStudentNumberQueryDAO - saveSTUXML - "+studentNr+" - End, Result="+result);
		return result;
	}

	public String getXMLSelected(String reference_type, String reference_value, String studentNr, String acaYear, String acaPeriod, String callingMethod) {

		String result="";

		//log.debug("ApplyForStudentNumberQueryDAO - getXMLSelected - reference_type : " + reference_type + " reference_value: " + reference_value + " : " + studentNr + " : " + acaYear + " : " + acaPeriod + " : " + callingMethod);
	
		String checkXML	= "select detail "
						+ " from STUXML "
						+ " where mk_student_nr = '" + studentNr + "'"
						+ " and mk_academic_year = '" + acaYear + "'" 
						+ " and mk_academic_period = '" + acaPeriod + "'" 
						+ " and reference_type = '" + reference_type + "'" 
						+ " and reference_value = '" + reference_value + "'"
						+ " and rownum = 1 "
						+ " order by reference_sequence desc ";
		try {
			//log.debug("ApplyForStudentNumberQueryDAO - getXMLSelected - Doing Select - checkXML: " +checkXML);
			//log.debug(checkXML);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(checkXML);
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				//log.debug("ApplyForStudentNumberQueryDAO - getXMLSelected - checkXML: Has Row");
				result = data.get("detail").toString().trim();
			}
		} catch (Exception ex) {
			//log.debug("ApplyForStudentNumberQueryDAO: Error retrieving Temp STUXML for Student / "+studentNr + " / " + ex);
		}
	return result;

	}
	
	public int getSTULOGRef(String acaYear, String acaPeriod, String studentNr) {
		
		int result = 0; 

		String query = "SELECT COALESCE( ( "
					+ " select max(SEQ_NR) "
					+ " from STULOG "
					+ " where year = ? "
					+ " and period = ? "
					+ " and student = ? "
					+ " ), 0) as SEQ_NR from dual ";

		try {
			//log.debug("ApplyForStudentNumberQueryDAO - getSTULOGRef - Query="+query+", dbparam=" +acaYear+", "+acaPeriod+", "+studentNr);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query, new Object []{acaYear, acaPeriod, studentNr});
			Iterator<?> i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				//log.debug("ApplyForStudentNumberQueryDAO - getSTULOGRef: Has Row");
				result = Integer.parseInt(data.get("SEQ_NR").toString().trim());
				//log.debug("ApplyForStudentNumberQueryDAO - getSTULOGRef - "+studentNr+" - Result: STULOG Exists");
			}else{
				//log.debug("ApplyForStudentNumberQueryDAO - getSTULOGRef - "+studentNr+" - Result: No STULOG Exists");
			}
			
		} catch (Exception ex) {
			//log.debug("ApplyForStudentNumberQueryDAO - getSTULOGRef - Error Retrieving STULOG Sequence for Student="+studentNr + "/ " +ex);
			log.warn("Error Retrieving STULOG Sequence / "+studentNr+" / " + ex);
		}
		//log.debug("ApplyForStudentNumberQueryDAO - getSTULOGRef - "+studentNr+" - Retrieve STULOG Sequence - Done="+result);
		return result;
	}
	
	public boolean getSTULOG(String acaYear, String acaPeriod, String studentNr, String valueDesc, String action, int seqNr) {
		
		boolean result = false; 

    	//log.debug("ApplyForStudentNumberAction - getSTULOG - INPUT_DESC="+valueDesc+", TYPE_GC303="+action+", STUDENT="+studentNr+", YEAR="+acaYear+", PERIOD="+acaPeriod+", SEQ="+seqNr);
				
		try{
			String query  = "select STUDENT "
					+ " from stulog "
					+ " where YEAR = ? " 
					+ " and PERIOD = ? " 
					+ " and STUDENT = ? " 
					+ " and SEQ_NR = ? " 
					+ " and APP_TYPE = 'APP' " 
					+ " and TYPE_GC303 = ? " 
					+ " and INPUT_DESC = ? ";

			//log.debug("ApplyForStudentNumberQueryDAO - getSTULOG - Query="+query+" - "+acaYear+","+acaPeriod+","+studentNr+","+seqNr+", APP, "+action+", "+valueDesc);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query, new Object []{acaYear,acaPeriod,studentNr,seqNr,action,valueDesc});
			Iterator<?> i = queryList.iterator();
			if (i.hasNext()) {
				//log.debug("ApplyForStudentNumberQueryDAO - getSTULOG: Has Row");
				result = true;
				//log.debug("ApplyForStudentNumberQueryDAO - getSTULOG - "+studentNr+" - Result: STULOG Exists");
			}else{
				result = false;;
				//log.debug("ApplyForStudentNumberQueryDAO - getSTULOG - "+studentNr+" - Result: No STULOG Exists");
			}
			
		} catch (Exception ex) {
			//log.debug("ApplyForStudentNumberQueryDAO: getSTULOG - Error Retrieving STULOG for Student="+studentNr + "/ " +ex);
			log.warn("Error Retrieving STULOG Sequence / "+studentNr+" / " + ex);
		}
		//log.debug("ApplyForStudentNumberQueryDAO - getSTULOG - "+studentNr+" - Retrieve STULOG Sequence - Done="+result);
		return result;
	}
	
	public String saveSTULOG(String acaYear, String acaPeriod, String studentNr, String value, String valueDesc, String action, int seqNr, String sqlAction) throws Exception {

    	//log.debug("ApplyForStudentNumberAction - saveSTULOG - INPUT_DETAIL="+value+", INPUT_DESC="+valueDesc+", TYPE_GC303="+action+", STUDENT="+studentNr+", YEAR="+acaYear+", PERIOD="+acaPeriod+", SEQ="+seqNr+", SQLAction="+sqlAction);
		
		String queryUpdate = "";
		String queryInsert = "";
		int resultInt = 0;
		String result = "False";  //Note: Not Boolean;
		
		try{
			if ("UPDATE".equalsIgnoreCase(sqlAction)){
				queryUpdate  = "update stulog "
					+ " set INPUT_DETAIL = ? "
					+ " where YEAR = ? " 
					+ " and PERIOD = ? " 
					+ " and STUDENT = ? " 
					+ " and SEQ_NR = ? " 
					+ " and APP_TYPE = 'APP' " 
					+ " and TYPE_GC303 = ? " 
					+ " and INPUT_DESC = ? ";

				//log.debug("ApplyForStudentNumberQueryDAO - saveSTULOG - INSERT - queryUpdate="+queryUpdate+" - "+acaYear+","+acaPeriod+","+studentNr+","+seqNr+", APP, "+action+", "+valueDesc+", "+value);
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				resultInt = jdt.update(queryUpdate, new Object []{value, acaYear,acaPeriod,studentNr,seqNr,action,valueDesc});
			
			}else{
				queryInsert  = "insert into stulog (YEAR ,PERIOD, STUDENT, SEQ_NR, APP_TYPE, TYPE_GC303, INPUT_DESC, INPUT_DETAIL, TIMESTAMP, INFO) "
					+ " values (?, ?, ?, ?, 'APP', ?, ?, ?, systimestamp, ' ') ";
	
				//log.debug("ApplyForStudentNumberQueryDAO - saveSTULOG - INSERT - queryInsert="+queryInsert+" - "+acaYear+","+acaPeriod+","+studentNr+","+seqNr+", APP, "+action+", "+valueDesc+", "+value);
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				resultInt = jdt.update(queryInsert, new Object []{acaYear,acaPeriod,studentNr,seqNr,action,valueDesc,value});
			}
			
			if (resultInt > 0){
				result = "True";
			}
		} catch (Exception ex) {
			//log.debug("ApplyForStudentNumberQueryDAO - saveSTULOG - Error saving STULOG / " + ex);
			result = "Error saving STULOG";
		}
		//log.debug("ApplyForStudentNumberQueryDAO - saveSTULOG - "+studentNr+" - End, Result="+result);
		return result;
	}
			
	public String getQualAdminSection(String qualCode) throws Exception{

		//log.debug("ApplyForStudentNumberQueryDAO - GetQualUnderPostCategory - Start");

		String type = "";
		String underPost = " ";
		String admin = " ";
		String katCode = " ";
		String query = "select type, under_post_categor,admin_section, fk_katcode from grd where code = ? ";

		try {
			//log.debug("ApplyForStudentNumberQueryDAO - GetQualUnderPostCategory - Query: " + query);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query, new Object []{qualCode});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				type = data.get("type").toString();
				underPost = data.get("under_post_categor").toString();
				admin = data.get("admin_section").toString();
				katCode = data.get("fk_katcode").toString();
				if ("S".equalsIgnoreCase(type)){
					admin = "084";
				}else if ("U".equalsIgnoreCase(underPost)){
					admin = "064";
				}else if ("47".equals(katCode) || "H".equalsIgnoreCase(underPost)){
						admin = "083";
				}else if ("03115".equalsIgnoreCase(qualCode) && ("4".equals(katCode) || "26".equals(katCode) || "42".equals(katCode) || "44".equals(katCode))){
					admin = "083";
				}else{
					admin = "064"; //Fallback just in case
				}
				//log.debug("ApplyForStudentNumberQueryDAO - getQualAdminSection - Result: under_post_categor: " + data.get("under_post_categor").toString() + ", admin_section: " + data.get("admin_section").toString() + "UNDERPOST: " + underPost);
			}else{
				//log.debug("ApplyForStudentNumberQueryDAO - getQualAdminSection - Result: No Rows");
			}
		} catch (Exception ex) {
			throw new Exception(
				"ApplyForStudentNumberQueryDAO: Error reading Qualification Admin Section for new Qualification / "+qualCode+ " / " + ex);
		} finally {
		}
		return admin;
	}
	
	public String getCollegeCategory(String qualCode2) throws Exception{

		//log.debug("ApplyForStudentNumberQueryDAO - GetQualCollegeCategory - Start");
		
		String college = " ";
		String query = "select college_code from grd where code = ? ";

		try {
			//log.debug("ApplyForStudentNumberQueryDAO - GetQualCollegeCategory - Query: " + query);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query, new Object []{qualCode2});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				college = data.get("college_code").toString();
				//log.debug("ApplyForStudentNumberQueryDAO - GetQualCollegeCategory - Result: college_code: " + data.get("college_code").toString());
			}else{
				//log.debug("ApplyForStudentNumberQueryDAO - GetQualCollegeCategory - Result: No Rows");
			}
		} catch (Exception ex) {
			throw new Exception(
				"ApplyForStudentNumberQueryDAO: Error reading College Code for new Qualification / "+qualCode2+ "  / " + ex);
		} finally {
		}
		return college;
	}
	
public boolean getSTUAPQDetail(String studentNr, String acaYear, String acaPeriod, String choiceNr, String callingMethod) throws Exception{

		//log.debug("ApplyForStudentNumberQueryDAO - getSTUAPQDetail - Start");
		
		boolean result = false;
		
		String query = "select new_qual,new_spes "
					+ " from STUAPQ "
					+ " where mk_student_nr = ? "
					+ " and academic_year = ? "
					+ " and application_period = ? "
					+ " and choice_nr = ? ";

		try {
			//log.debug("ApplyForStudentNumberQueryDAO - getSTUAPQDetail - Query: " +studentNr+", "+acaYear+", "+acaPeriod+", "+choiceNr);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, acaPeriod, choiceNr});
			Iterator<?> i = queryList.iterator();
			if (i.hasNext()) {
				//log.debug("ApplyForStudentNumberQueryDAO - getSTUAPQDetail - Result: STUAPQ Exists");
				result =  true;
			}else{
				//log.debug("ApplyForStudentNumberQueryDAO - getSTUAPQDetail - Result: No STUAPQ");
				result =  false;
			}
			
		} catch (Exception ex) {
			throw new Exception(
		    "ApplyForStudentNumberQueryDAO: Error Retrieving STUAPQ Saved Qualification for Returning Student nr / "+studentNr + " / " + acaYear + " / " + acaPeriod  + " / " + choiceNr + "  / " + ex);
		}
		return result;
	}

	public boolean getMDAPPLRecord(String studentNr) throws Exception{
	
		boolean result = false;
		
		String query = "select mk_student_nr, mk_qualification_c, speciality_code, "
				  + " admission_status "
				  + " from mdappl where mk_student_nr = ? "
				  + " and rownum = 1 "
				  + " order by app_sequence_nr desc";
		try {
			//log.debug("ApplyForStudentNumberQueryDAO - getMDAPPLRecord - Query: "+query+ " - " +studentNr);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr});
			Iterator i = queryList.iterator();
			if (i.hasNext()){
				//log.debug("ApplyForStudentNumberQueryDAO - getMDAPPLRecord - Record: TRUE");
				result = true;
			}
		} catch (Exception ex) {
			throw new Exception("ApplyForStudentNumberQueryDAO : getMDAPPLRecord : Error occurred / "+ ex,ex);
		}
		//log.debug("ApplyForStudentNumberQueryDAO - getMDAPPLRecord - Result: "+result);
		return result;
	}

	public int delSTUAPQ(String studentNr, String acaYear, String acaPeriod, String choiceNr, String callingMethod) {

		//log.debug("ApplyForStudentNumberQueryDAO - delSTUAPQ - Start");
	
		String dbParam = "";
		int resultInt = 0;
		
		try {
			String query  = "delete from STUAPQ "
						+ " where mk_student_nr = ? "
						+ " and academic_year = ? "
						+ " and application_period = ? "
						+ " and choice_nr = ?";
				
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			resultInt = jdt.update(query, new Object []{studentNr,acaYear,acaPeriod,choiceNr});
			dbParam = studentNr+","+acaYear+","+acaPeriod+","+choiceNr;
	
			//log.debug("ApplyForStudentNumberQueryDAO - delSTUAPQ - Delete - QueryType="+callingMethod+", Query="+query+", resultInt="+resultInt);
			//log.debug("ApplyForStudentNumberQueryDAO - delSTUAPQ - Delete - resultInt: "+resultInt);
			//log.debug("ApplyForStudentNumberQueryDAO - delSTUAPQ - Delete - dbParam: "+dbParam);
			
		} catch (Exception ex) {
			log.warn("ApplyForStudentNumberQueryDAO: Error "+callingMethod+" Deleting APQ Qualification for Returning student nr / "+studentNr + " / " + dbParam + " / " +ex);
		}
		//log.debug("ApplyForStudentNumberQueryDAO - delSTUAPQ - End");
		return resultInt;
	}
	
	public int saveSTUAPQ(
					String qualCode, 
					String specCode, 
					String choiceNr, 
					String studentNr, 
					String acaYear, 
					String acaPeriod, 
					String statusCode, 
					String collegeCode, 
					String admin_section, 
					String radioPrev, 
					String radioRPL, 
					String radioNDP,
					String callingMethod) {

		//log.debug("ApplyForStudentNumberQueryDAO - saveSTUAPQ - Start");
	
		String dbParam = "";
		int resultInt = 0;

		if (specCode == null || "0".equals(specCode) || "".equals(specCode) || "NVT".equalsIgnoreCase(specCode) || "undefined".equalsIgnoreCase(specCode)){ specCode = " ";}
		
		try {
			String query  = "insert into STUAPQ ("
						+ " mk_student_nr,academic_year,application_period,qualification_code,speciality_code,new_qual,new_spes, "
						+ " status_code,mk_user_number,college_code,admin_section,choice_nr,admission_verified,admission_auto_chk, "
						+ " docs_outstanding,record_status,comments,alternative_route,science_foundation,application_date,alt_degree,pg_degree, "
						+ " app_timestamp,waps, space_offered, gc263_decline_reason, access_count, results_outstanding, offer_accepted, passed_ndp, "
						+ " prev_apply_qual, applied_rpl "
						+ " ) "
						+ " values (?, ?, ?, ?, ?, ?, ?, ?, '99998', ?, ?, ?,' ',' ',' ',' ',' ',' ',' ',sysdate,' ',' ', "
						+ " systimestamp ,0, ' ', ' ', 0, ' ', ' ', ?, ?, ?) ";
	
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			resultInt = jdt.update(query, new Object []{studentNr,acaYear,acaPeriod,qualCode,specCode,qualCode,specCode,statusCode,collegeCode,admin_section,choiceNr, radioNDP, radioPrev, radioRPL});
			dbParam = studentNr+","+acaYear+","+acaPeriod+","+qualCode+","+specCode+","+qualCode+","+specCode+","+statusCode+","+collegeCode+","+admin_section+","+choiceNr+","+radioNDP+","+radioPrev+","+radioRPL;
		
			//log.debug("ApplyForStudentNumberQueryDAO - saveSTUAPQ - Insert - QueryType="+callingMethod+", Query="+query+", resultInt="+resultInt);
			//log.debug("ApplyForStudentNumberQueryDAO - saveSTUAPQ - Insert - resultInt: "+resultInt);
			//log.debug("ApplyForStudentNumberQueryDAO - saveSTUAPQ - Insert - dbParam: "+dbParam);
			
		} catch (Exception ex) {
			//log.debug("ApplyForStudentNumberQueryDAO: Error "+callingMethod+" Saving APQ Qualification for Returning student nr / "+studentNr + " / " + dbParam + " / " +ex);
			log.warn("ApplyForStudentNumberQueryDAO: Error "+callingMethod+" Saving APQ Qualification for Returning student nr / "+studentNr + " / " + dbParam + " / " +ex);
		}
		//log.debug("ApplyForStudentNumberQueryDAO - saveSTUAPQ - End");
		return resultInt;
	}
	
	  /************ Get previous category to show current qualification *************/
	  public String getExistQual(String studentNr) throws Exception{

		  //log.debug("ApplyForStudentNumberQueryDAO - getExistQual studentNr: " + studentNr);

		  String existQual = "";

	      try {

	    	  String query   = "select stuann.mk_highest_qualifi AS CODE "
	    			  	+ " from stuann,grd "
						+ " where stuann.mk_highest_qualifi = grd.code "
						+ " and stuann.mk_student_nr = ? "
						+ " and stuann.status_code = 'RG' "
						+ " and rownum = 1 "
						+ " order by stuann.mk_academic_year desc";

	    	  	//log.debug("ApplyForStudentNumberQueryDAO - getExistQual - query: " + query);
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(query, new Object []{studentNr});
				Iterator i = queryList.iterator();
				if (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					existQual = data.get("CODE").toString() ;
				}
	      	} catch (Exception ex) {
	      			throw new Exception(
	      					"ApplyForStudentNumberQueryDAO: Error occured on reading existing Qualification / " + ex);

	      	} finally {

	      	}
	      	return existQual;

	  }


	  /************ Get previous specialization show current qualification *************/
	  public String getExistSpec(String studentNr) throws Exception{

		  //log.debug("ApplyForStudentNumberQueryDAO - getExistSpec studentNr: " + studentNr);

	      String existSpec = "";

	      try {

	    	  String query   = "select speciality_code as CODE"
							+ " from quaspc "
							+ " where in_use_flag = 'Y' "
							+ " and mk_qualification_c in (select mk_highest_qualifi from (select mk_highest_qualifi from stuann where mk_student_nr = ? and status_code = 'RG' and rownum = 1 order by mk_academic_year desc)) "
							+ " and speciality_code = (select speciality_code from (select speciality_code from stuann where mk_student_nr = ? and status_code = 'RG' and rownum = 1 order by mk_academic_year desc))";

	    	  	//log.debug("ApplyForStudentNumberQueryDAO - getExistSpec - query: " + query);

				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(query, new Object []{studentNr, studentNr});
				Iterator i = queryList.iterator();
				if (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					existSpec = data.get("CODE").toString();
					if("".equalsIgnoreCase(existSpec)){
						existSpec = " ";
					}
				}
	      	} catch (Exception ex) {
	      			throw new Exception(
	      					"ApplyForStudentNumberQueryDAO: Error occured on reading existing Specialisation / " + ex);

	      	} finally {

	      	}
	      	return existSpec;

	  }
	  
	  /************ Get previous specialization show current qualification *************/
	  public boolean getXMLRandom(Integer studentNr) throws Exception{

		  //log.debug("ApplyForStudentNumberQueryDAO - getXMLRandom studentNr: " + studentNr);

	      boolean existStuNr = false;

	      try {
	    	  String query   = "select mk_student_nr as NR "
	    			  		+ " from STUXML "
	    			  		+ " where mk_student_nr = ? "
	    			  		+ " and not exists ( "
	    			  		+ " select nr from stu where nr = ? "
	    			  		+ " ) ";
							
	    	  	//log.debug("ApplyForStudentNumberQueryDAO - getXMLRandom - query: " + query);
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(query, new Object []{studentNr, studentNr});
				Iterator i = queryList.iterator();
				if (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					existStuNr = true;
					//log.debug("ApplyForStudentNumberQueryDAO - getXMLRandom Exists: " + studentNr);
				}else{
					//log.debug("ApplyForStudentNumberQueryDAO - getXMLRandom Does Not Exist: " + studentNr);
				}
	      	} catch (Exception ex) {
	      			throw new Exception(
	      					"ApplyForStudentNumberQueryDAO: Error occured on reading existing Temp No / " + ex);

	      	} finally {

	      	}
	      	return existStuNr;

	  }

  
	public String vrfyPrevQual(String qualSpec, String choice, String studentNr, String acaYear, String acaPeriod) throws Exception {

		String query = "";
		String stuChoice = "";
		String result = "";
		
		if ("Qual".equalsIgnoreCase(qualSpec)){
			query = "select stuann.mk_highest_qualifi AS qualification_code, "
					+ " upper(grd.long_eng_descripti) as QualDesc, "
					+ " grd.long_eng_descripti as QualLongDesc "
    			  	+ " from stuann, grd "
    			  	+ " where stuann.mk_highest_qualifi = grd.code "
					+ " and stuann.mk_student_nr = ? "
					+ " and stuann.status_code = 'RG' "
					+ " and rownum = 1 "
					+ " order by stuann.mk_academic_year desc";
		}else{
			query = "select stuann.speciality_code, "
					+ " quaspc.english_descriptio as SpecDesc "
					+ " from stuann, grd, quaspc "
					+ " where stuann.mk_highest_qualifi = grd.code "
					+ " and stuann.mk_highest_qualifi = quaspc.mk_qualification_c "
					+ " and stuann.speciality_code = quaspc.speciality_code "
					+ " and stuann.mk_student_nr = ? "
					+ " and stuann.status_code = 'RG' "
					+ " and rownum = 1 "
					+ " order by stuann.mk_academic_year desc";
		}

		try {
			//log.debug("ApplyForStudentNumberQueryDAO - vrfyPrevQual - query:" + query);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr});
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
					//log.debug("ApplyForStudentNumberQueryDAO - vrfyPrevQual - stuChoice:" + stuChoice);
					if ("Qual".equalsIgnoreCase(qualSpec)){
						result = data.get("qualification_code").toString().trim() + " - " + data.get("QualLongDesc").toString().trim();
						//log.debug("ApplyForStudentNumberQueryDAO - vrfyPrevQual - Qual:" + result);
					}else if ("Spec".equalsIgnoreCase(qualSpec)){
						if (data.get("speciality_code").toString().trim() != null && !"".equalsIgnoreCase(data.get("speciality_code").toString().trim()) && !"0".equalsIgnoreCase(data.get("speciality_code").toString().trim())){
							result = data.get("speciality_code").toString().trim() + " - " + data.get("SpecDesc").toString().trim();
						}else{
							result = "N/A - Not Applicable";
						}
						//log.debug("ApplyForStudentNumberQueryDAO - vrfyPrevQual - Spec:" + result);
					}
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error validating student current and saved quals / " + ex);
		}
		return result;
	}
	
	/**
	 * This method retrieves the student's saved qualifications from STUAPQ as a final validation check
	 * and returns the values to applysnrcomplete.jsp and applysnrcompleteRet.jsp for new and returning
	 * students respectively
	 * 
	 * @param studentNr
	 * @param acaYear
	 * @param acaPeriod
	 * @return
	 * @throws Exception
	 */
	public String vrfyNewQual(String qualSpec, String choice, String studentNr, String acaYear, String acaPeriod) throws Exception {

		String query = "";
		String stuChoice = "";
		String result = "";
		
		if ("Qual".equalsIgnoreCase(qualSpec)){
			query = "select stuapq.new_qual, stuapq.choice_nr, "
					+ " upper(grd.long_eng_descripti) as QualDesc, "
					+ " grd.long_eng_descripti as QualLongDesc "
					+ " from stuapq, grd "
					+ " where stuapq.new_qual = grd.code "
					+ " and stuapq.mk_student_nr = ? "
					+ " and stuapq.academic_year = ? "
					+ " and application_period = ? "
					+ " and stuapq.choice_nr = ? ";
		}else{
			query = "select stuapq.new_spes, stuapq.choice_nr, "
					+ " quaspc.english_descriptio as SpecDesc "
					+ " from stuapq, grd, quaspc "
					+ " where stuapq.new_qual = grd.code "
					+ " and stuapq.new_qual = quaspc.mk_qualification_c "
					+ " and stuapq.new_spes = quaspc.speciality_code "
					+ " and stuapq.mk_student_nr = ? "
					+ " and stuapq.academic_year = ? "
					+ " and application_period = ? "
					+ " and stuapq.choice_nr = ? ";
		}

		try {
			//log.debug("ApplyForStudentNumberQueryDAO - vrfyNewQual - qualSpec: " + qualSpec + ", query:" + query);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, acaPeriod, choice});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				while (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
						//log.debug("ApplyForStudentNumberQueryDAO - vrfyNewQual - stuChoice:" + stuChoice);
						if ("Qual".equalsIgnoreCase(qualSpec)){
							result = data.get("new_qual").toString().trim() + " - " + data.get("QualDesc").toString().trim() + " (" + data.get("QualLongDesc").toString().trim() + ")";
							//log.debug("ApplyForStudentNumberQueryDAO - vrfyNewQual - Qual:" + result);
						}else if ("Spec".equalsIgnoreCase(qualSpec)){
							if (data.get("new_spes").toString().trim() != null && !"".equalsIgnoreCase(data.get("new_spes").toString().trim()) && !"0".equalsIgnoreCase(data.get("new_spes").toString().trim())){
								result = data.get("new_spes").toString().trim() + " - " + data.get("SpecDesc").toString().trim();
							}else{
								result = "N/A - Not Applicable";
							}
							//log.debug("ApplyForStudentNumberQueryDAO - vrfyNewQual - Spec:" + result);
						}
				}
			}else{
				result = "N/A - Not Applicable";
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error validating student saved quals / " + ex);
		}
		//log.debug("ApplyForStudentNumberQueryDAO - vrfyNewQual - result:" + result);
		return result;
	}
	
	public String vrfyNewQualShort(String qualSpec, String choice, String studentNr, String acaYear, String acaPeriod) throws Exception {

		String query = "";
		String stuChoice = "";
		String result = "";
		
			query = "select stuapq.new_qual "
					+ " from stuapq, grd "
					+ " where stuapq.new_qual = grd.code "
					+ " and stuapq.mk_student_nr = ? "
					+ " and stuapq.academic_year = ? "
					+ " and application_period = ? "
					+ " and stuapq.choice_nr = ? ";
		try {
			//log.debug("ApplyForStudentNumberQueryDAO - vrfyNewQualShort - qualSpec: " + qualSpec + ", query:" + query);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, acaPeriod, choice});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				result = data.get("new_qual").toString().trim();
				//log.debug("ApplyForStudentNumberQueryDAO - vrfyNewQualShort - Qual:" + result);
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error retrieving STUAPQ Qualification / " + ex);
		}
		return result;
	}
	
	/**
	 * This method retrieves the student's saved qualifications from STUAPQ as a final validation check
	 * and returns the values to applysnrcomplete.jsp and applysnrcompleteRet.jsp for new and returning
	 * students respectively
	 * 
	 * @param studentNr
	 * @param acaYear
	 * @param acaPeriod
	 * @return
	 * @throws Exception
	 */
	public String vrfyNewMDQual(String qualSpec, String studentNr, String acaYear, String acaPeriod) throws Exception {

		String query = "";
		String stuChoice = "";
		String result = "";
		
		if ("Qual".equalsIgnoreCase(qualSpec)){
			query = "select stuann.mk_highest_qualifi, "
					+ " upper(grd.long_eng_descripti) as QualDesc, "
					+ " grd.long_eng_descripti as QualLongDesc "
					+ " from stuann, grd "
					+ " where stuann.mk_highest_qualifi = grd.code "
					+ " and stuann.mk_student_nr = ? "
					+ " and stuann.mk_academic_year = ? "
					+ " and stuann.status_code in ('AP') ";
		}else{
			query = "select stuann.speciality_code, "
					+ "quaspc.english_descriptio as SpecDesc "
					+ "from stuann, grd, quaspc "
					+ "where stuann.mk_highest_qualifi = grd.code "
					+ "and stuann.mk_highest_qualifi = quaspc.mk_qualification_c "
					+ "and stuann.speciality_code = quaspc.speciality_code "
					+ " and stuann.mk_student_nr = ? "
					+ " and stuann.mk_academic_year = ? "
					+ " and stuann.status_code in ('AP') ";
		}

		try {
			//log.debug("ApplyForStudentNumberQueryDAO - vrfyNewMDQual - query:" + query);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr, acaYear});
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
					//log.debug("ApplyForStudentNumberQueryDAO - vrfyNewMDQual - stuChoice:" + stuChoice);
					if ("Qual".equalsIgnoreCase(qualSpec)){
						result = data.get("mk_highest_qualifi").toString().trim() + " - " + data.get("QualDesc").toString().trim() + " (" + data.get("QualLongDesc").toString().trim() + ")";
						//log.debug("ApplyForStudentNumberQueryDAO - vrfyNewMDQual - Qual:" + result);
					}else if ("Spec".equalsIgnoreCase(qualSpec)){
						if (data.get("speciality_code").toString().trim() != null && !"".equalsIgnoreCase(data.get("speciality_code").toString().trim()) && !"0".equalsIgnoreCase(data.get("speciality_code").toString().trim())){
							result = data.get("speciality_code").toString().trim() + " - " + data.get("SpecDesc").toString().trim();
						}else{
							result = data.get("SpecDesc").toString().trim();
						}
						//log.debug("ApplyForStudentNumberQueryDAO - vrfyNewMDQual - Spec:" + result);
					}
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error validating MD student saved quals / " + ex);
		}
		return result;
	}
	
	public String getSTUAPQStatus(String studentNr,String acaYear,String acaPeriod, String choice, String callingMethod) throws Exception{
		
		String status = "AP";
		try{		
			String query = "select status_code "
						+ " from stuapq "
						+ " where mk_student_nr = ? "
						+ " and academic_year = ? "
						+ " and application_period = ? "
						+ " and choice_nr = ? "
						+ " and status_code in ('ND', 'NP') ";

			//log.debug("ApplyForStudentNumberQueryDAO - getSTUAPQStatus - query="+query+", studentNr="+studentNr+", acaYear="+acaYear+", acaPeriod="+acaPeriod+", choice="+choice+", CallingMethod="+callingMethod);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, acaPeriod, choice});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				status = data.get("status_code").toString();
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error reading STUAPQ status / " + ex);
		} finally {
			try {

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		//log.debug("getSTUAPQStatus - Status: " + status );
		return status;
	}
	
	public String getBasicStatus(String studentNr,String acaYear,String acaPeriod, String choice) throws Exception{
		
		String status = "";
		try{		
			String query = "select status_code "
						+ " from stuapq "
						+ " where mk_student_nr = ? "
						+ " and academic_year = ? "
						+ " and application_period = ? "
						+ " and choice_nr = ? ";

			//log.debug("ApplyForStudentNumberQueryDAO - getBasicStatus - query="+query+", studentNr="+studentNr+", acaYear="+acaYear+", acaPeriod="+acaPeriod+", choice="+choice);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, acaPeriod, choice});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				status = data.get("status_code").toString();
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error reading STUAPQ status / " + ex);
		}
		//log.debug("ApplyForStudentNumberQueryDAO - getBasicStatus - Status: " + status );
		return status;
	}
	
	public String getBasicSTUAPQInfo(String studentNr,String acaYear,String acaPeriod, String choice, String info) throws Exception{
		
		String result = "";
		try{		
			String query = "select passed_ndp, prev_apply_qual, applied_rpl "
						+ " from stuapq "
						+ " where mk_student_nr = ? "
						+ " and academic_year = ? "
						+ " and application_period = ? "
						+ " and choice_nr = ? ";

			//log.debug("ApplyForStudentNumberQueryDAO - getBasicSTUAPQInfo - query="+query+", studentNr="+studentNr+", acaYear="+acaYear+", acaPeriod="+acaPeriod+", choice="+choice);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, acaPeriod, choice});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				if ("PASSED".equalsIgnoreCase(info)){
					result = data.get("passed_ndp").toString();
				}else if ("APPLIED".equalsIgnoreCase(info)){
					result = data.get("prev_apply_qual").toString();
				}else if ("RPL".equalsIgnoreCase(info)){
					result = data.get("applied_rpl").toString();
				}
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error reading STUAPQ NDP Info / " + ex);
		}
		//log.debug("ApplyForStudentNumberQueryDAO - getBasicSTUAPQInfo - Result: " + result );
		return result;
	}
	
	public String getFinalMD(String studentNr,String acaYear,String acaPeriod) throws Exception{
		
		String checkMD = "";
		try{		
			String query = "select grd.under_post_categor "
						+ " from stuann, grd "
						+ " where stuann.mk_highest_qualifi = grd.code "
						+ " and stuann.mk_student_nr = ? "
						+ " and stuann.mk_academic_year = ? "
						+ " and stuann.mk_academic_period = ? ";

			//log.debug("ApplyForStudentNumberQueryDAO - getFinalMD - query: " + query );
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, acaPeriod});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				checkMD = data.get("under_post_categor").toString();
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error reading getting M&D Under_Post list / " + ex);
		} finally {
			try {

			} catch (Exception e) {

				e.printStackTrace();
			}

		}
		//log.debug("ApplyForStudentNumberQueryDAO - getFinalMD - checkMD: " + checkMD );
		return checkMD;
	}
	

	
	/**
	 * Check if year is a leap year if date 29 Feb CCYY
	 */
	public String checkLeapYear(String bYear) throws Exception {
		String leapCheck = "";
		
		String query  = "SELECT CASE EXTRACT(DAY FROM LAST_DAY(TO_DATE(?, 'DD/MM/YYYY'))) "
					+ " WHEN 28 THEN 'Not a leap year' "
					+ " WHEN 29 THEN 'Leap year' "
					+ " END AS LEAP "
					+ " FROM DUAL";
		
		try {
			//log.debug("ApplyForStudentNumberQueryDAO - checkLeapYear: query" +  query);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query, new Object []{"15/02/"+bYear});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				leapCheck = data.get("LEAP").toString();
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error validating Leap Year / " + ex);
		}
		return leapCheck;
	}
	
    public boolean checkSuburbExact(String postalCode, String suburb) throws Exception{
    	
    	//log.debug("ApplyForStudentNumberQueryDAO - checkSuburbExact - Suburb/Town="+suburb+", PostalCode="+postalCode);
 	    boolean result = false;
 	    String query = "SELECT code from pstcod where (upper(suburb) = ? or upper(town) = ?) and code = ? ";

 	    ResultSet rs;

 	    try{ 
 	    	//log.debug("ApplyForStudentNumberQueryDAO - checkSuburbExact query: " + query + ", postalCode: " + postalCode + ", suburb: " + suburb);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{suburb.toUpperCase(), suburb.toUpperCase(), postalCode});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				result = true;
			}
 	    } catch (Exception ex) {
 	    	throw new Exception("ApplyForStudentNumberQueryDAO - checkSuburbExact : Error reading suburb / " + ex);
        }
		//log.debug("ApplyForStudentNumberQueryDAO - Database Check=" + result); 
 	    return result;
     }

	  /************ Get Offer Reason *************/
	  public String getApplyStatus(String studentNr, String acaYear, String acaPeriod, String choice) throws Exception{

		  //log.debug("ApplyForStudentNumberQueryDAO - getApplyStatus studentNr: " + studentNr+", acaYear: " + acaYear+", acaPeriod: " + acaPeriod+", choice: " + choice);

		  String result = "";
		  boolean overSubscribed = false;

	      try {
	    	  String query1 = "select stuapq.new_qual "
	    			  	+ " from stuapq, gencod "
	    			  	+ " where stuapq.new_qual = gencod.code "
	    			  	+ " and stuapq.choice_nr = ?"
	    			  	+ " and stuapq.academic_year = ? "
	    			  	+ " and stuapq.application_period = ? "
	    			  	+ " and gencod.fk_gencatcode='249' "
	    			  	+ " and gencod.in_use_flag='Y' "
	    			  	+ " and mk_student_nr = ? ";
	    	  
	    	  //log.debug("ApplyForStudentNumberQueryDAO - getQualSubscription - getApplyStatus - Checked OverSubscribed - query1: " + query1+", choice="+choice+", acaYear="+acaYear+", acaPeriod="+acaPeriod+", studentNr="+studentNr);
	    	  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	    	  List queryList = jdt.queryForList(query1, new Object []{choice, acaYear, acaPeriod, studentNr});
	    	  Iterator i = queryList.iterator();
	    	  if (i.hasNext()) {
		    	  //log.debug("ApplyForStudentNumberQueryDAO - getQualSubscription - getApplyStatus - studentNr="+studentNr+", - choice="+choice+" - IS Oversubscribed");
	    		  overSubscribed = true;
	    	  }else{
	    		  //log.debug("ApplyForStudentNumberQueryDAO - getQualSubscription - getApplyStatus - studentNr="+studentNr+", - choice="+choice+" - IS NOT Oversubscribed");
	    	  }

			  String query2 = "select new_qual, status_code, admission_verified, docs_outstanding, "
					  	+ " COALESCE(space_offered, ' ') as space_offered,"
					  	+ " COALESCE(results_outstanding, ' ') as results_outstanding"
			  			+ " from stuapq "
	    			  	+ " where choice_nr = ? "
	    			  	+ " and academic_year = ? "
	    			  	+ " and application_period = ? "
	    			  	+ " and mk_student_nr = ? ";
	    			  		    	  
			  //log.debug("ApplyForStudentNumberQueryDAO - getApplyStatus - Check Status - query2: " + query2+", choice="+choice+", acaYear="+acaYear+", acaPeriod="+acaPeriod+", studentNr="+studentNr);
	    	  JdbcTemplate jdtQual = new JdbcTemplate(getDataSource());
	    	  List queryListQual = jdtQual.queryForList(query2, new Object []{choice, acaYear, acaPeriod, studentNr});
	    	  Iterator quals = queryListQual.iterator();
	    	  if (quals.hasNext()) {
	    		  ListOrderedMap data = (ListOrderedMap) quals.next();
	    		  String qual = data.get("new_qual").toString().trim();
	    		  String status = data.get("status_code").toString().trim();
	    		  String verified = data.get("admission_verified").toString().trim();
	    		  String docs = data.get("docs_outstanding").toString().trim();
	    		  String space = data.get("space_offered").toString().trim();
	    		  String results = data.get("results_outstanding").toString().trim();
	    		  
	    		  //log.debug("ApplyForStudentNumberQueryDAO - getApplyStatus - Qualification="+qual+", choice="+choice+", status="+status+", verified="+verified+", docs="+docs+", space="+space);
	    		  if ("AP".equalsIgnoreCase(status)){
		    		  if ("Y".equalsIgnoreCase(docs)){
	    				  return "APD";
		    		  }else{
		    			  return "AP";
		    		  }
	    		  }else if ("TN".equalsIgnoreCase(status)){
	    			  if (overSubscribed && "N".equalsIgnoreCase(space)){
		    			  return "TNN";
		    		  }else if (overSubscribed & (space == null || "".equalsIgnoreCase(space) || " ".equalsIgnoreCase(space))){
	    				  return "TNE";
		    		  }else{
		    			  return "TN";
		    		  }
	    		  }else if ("EM".equalsIgnoreCase(status)){
	    			  if (overSubscribed && "Y".equalsIgnoreCase(space)){
		    			  return "EMY";
		    		  }else{
		    			  return "EM";
		    		  }
	    		  }else if ("EO".equalsIgnoreCase(status)){
	    			  if (overSubscribed && "Y".equalsIgnoreCase(space)){
		    			  return "EO"; //EOY??
		    		  }else{
		    			  return "EO";
		    		  }
	    		  }else if ("CG".equalsIgnoreCase(status)){
	    			  if ("Y".equalsIgnoreCase(space) && "Y".equalsIgnoreCase(results)){
		    			  return "CGP";
	    			  }else if ("Y".equalsIgnoreCase(space)){
		    			  return "CGY";
		    		  }else{
		    			  return "CG";
		    		  }
	    		  }else{
	    			  return status;
	    		  }
	    	  }else{
	    		  return "NOAPP";
	    	  }
	      	} catch (Exception ex) {
	      			throw new Exception(
	      					"ApplyForStudentNumberQueryDAO: Error occured on reading Application Status / "+ex);
	      	}
	  }
	  
	  public String getStatusDesc(String status) throws Exception{
	    	
	    	//log.debug("ApplyForStudentNumberQueryDAO - getStatusDesc - Status: "+status);

	    	String result = "";

	    	try{ 
	    		if (status.equalsIgnoreCase("NOAPP")){
	    			result = "No Application found for this academic year";
	    		}else{
		    		String query = "select info from gencod "
		    					+ " where code = ? "
		    					+ " and fk_gencatcode='264' "
		    					+ " and in_use_flag='Y' ";
	
		    		//log.debug("ApplyForStudentNumberQueryDAO - getStatusDesc - Query: " + query + ", Status: "+status);
					JdbcTemplate jdt = new JdbcTemplate(getDataSource());
					List queryList = jdt.queryForList(query, new Object []{status});
					Iterator i = queryList.iterator();
					if (i.hasNext()) {
						ListOrderedMap data = (ListOrderedMap) i.next();
						result = data.get("info").toString().trim();
					}else{
						result = "Status not found";
					}
	    		}
	 	    } catch (Exception ex) {
	 	    	throw new Exception("ApplyForStudentNumberQueryDAO - getStatusDesc : Error reading status / " + ex);
	        }
	    	//log.debug("ApplyForStudentNumberQueryDAO - getStatusDesc - Final Status=" + result); 
	 	    return result;
	   }
	  
	  public Status getStatusFee(String StudentNr, String acaYear, String acaPeriod) throws Exception{
		  
		  String from_Month = "";
		  String to_Month = "";
		  int from_year = 0;
		  int to_year = 0;
		  int academicYear = Integer.parseInt(acaYear);
		  Status status = new Status();
		  
		  //log.debug("ApplyForStudentNumberQueryDAO - getStatusFee - StudentNr="+StudentNr+", acaYear="+acaYear+", acaPeriod="+acaPeriod);
		  //2018 Edmund - Probable future implementation as not sure till when a payment can be made/accepted.
		  Calendar cal = Calendar.getInstance();
		  int month = cal.get(Calendar.MONTH)+1; //zero-based
			
		  int cYear = cal.get(Calendar.YEAR); //Current Year
		  int aYear = Integer.parseInt(acaYear); //Academic Year
			
		  if (cYear < aYear){
				acaPeriod = "1";
				from_Month =  "AUG";
				to_Month = "MAR";
				from_year = cYear;
				to_year = aYear;
		  }else{
			  if (month < 8 && month > 3){
				  acaPeriod = "2";
				  from_Month =  "APR";
				  to_Month = "SEP";
				  from_year = cYear;
				  to_year = aYear;
			  }else{
				  acaPeriod = "1";
				  from_Month =  "AUG";
				  to_Month = "MAR";
				  from_year = cYear - 1;
				  to_year = aYear;
			  }
		  }

		  try{ 
			  String query = "select STUAPP.FEE_PAID, MAX(STSXTN.BUNDLE_DATE) as PAYDATE, sum(STSXTN.AMOUNT) as AMOUNT "
					  	+ " from STUAPP, STUAPQ, STSXTN "
					  	+ " where STUAPP.APPLICATION_NR = STUAPQ.MK_STUDENT_NR "
					  	+ " and STUAPP.APPLICATION_NR = STSXTN.MK_STUDENT_NR "
					  	+ " and STUAPQ.ACADEMIC_YEAR = ? "
					  	+ " and STUAPQ.APPLICATION_PERIOD = ? "
					  	+ " and STUAPQ.CHOICE_NR = 1 "
					  	+ " and STUAPP.APPLICATION_NR = ? "
					  	+ " and STSXTN.MK_GL_ENTITY = '53708' "
					  	+ " and STSXTN.MK_GL_ACCOUNT = '10030' "
					  	+ " group by STUAPP.FEE_PAID ";
			  
		 		//log.debug("ApplyForStudentNumberQueryDAO - getStatusFee - Query=" + query+", Current Year=" + cYear+", acaYear=" + aYear+", FROM="+from_Month+"/"+from_year+", TO="+to_Month+"/"+to_year);
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(query, new Object []{acaYear, acaPeriod, StudentNr});
				Iterator i = queryList.iterator();
				if (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					status.setPayDate(data.get("PAYDATE").toString().trim());
					status.setPayFee(new BigDecimal(data.get("AMOUNT").toString().trim()));
					if (data.get("FEE_PAID").toString().trim() == null || !"Y".equalsIgnoreCase(data.get("FEE_PAID").toString().trim())){
						status.setPayFull(false);
					}else{
						status.setPayFull(true);
					}
				}else{
					status.setPayComment("NO PAYMENT");
				}
 	    } catch (Exception ex) {
 	    	throw new Exception("ApplyForStudentNumberQueryDAO - getStatusFee : Error Student Payment / " + ex);
        }
		
		//Debug
    	//log.debug("ApplyForStudentNumberQueryDAO - getStatusFee - PayDate="+status.getPayDate());
    	//log.debug("ApplyForStudentNumberQueryDAO - getStatusFee - PayFee ="+status.getPayFee());
    	//log.debug("ApplyForStudentNumberQueryDAO - getStatusFee - PayFull="+status.isPayFull());
    	//log.debug("ApplyForStudentNumberQueryDAO - getStatusFee - PayCom ="+status.getPayComment());
    	
 	    return status;
   }
	    				
	  public String getDeclineReason(String StudentNr, String qualCode, String acaYear, String acaPeriod) throws Exception{
	    	
	    	//log.debug("ApplyForStudentNumberQueryDAO - getDeclineReason - StudentNr: "+StudentNr+", qualCode: "+qualCode);

	    	String result = "";

	    	try{ 
	    		String query = "select eng_description "
							+ " from gencod, stuapq "
							+ " where stuapq.GC263_DECLINE_REASON = gencod.code "
							+ " and gencod.fk_gencatcode = 263 "
							+ " and gencod.in_use_flag = 'Y' "
							+ " and stuapq.academic_year = ? "
							+ " and stuapq.application_period = ? "
							+ " and stuapq.mk_student_nr = ? "
							+ " and stuapq.qualification_code = ? ";
	
		    		//log.debug("ApplyForStudentNumberQueryDAO - getDeclineReason - Query: " + query);
					JdbcTemplate jdt = new JdbcTemplate(getDataSource());
					List queryList = jdt.queryForList(query, new Object []{acaYear, acaPeriod, StudentNr, qualCode});
					Iterator i = queryList.iterator();
					if (i.hasNext()) {
						ListOrderedMap data = (ListOrderedMap) i.next();
						result = data.get("eng_description").toString().trim();
					}else{
						result = "Reason not given";
					}
	 	    } catch (Exception ex) {
	 	    	throw new Exception("ApplyForStudentNumberQueryDAO - getDeclineReason : Error reading decline reason / " + ex);
	        }
	    	//log.debug("ApplyForStudentNumberQueryDAO - getDeclineReason - Decline Reseason=" + result); 
	 	    return result;
	   }
	    
		/**
		 * This method retrieves the student's saved qualifications from STUAPQ
		 * and returns the values to applyStatus.jsp 		 * 
		 * @param studentNr
		 * @param acaYear
		 * @param acaPeriod
		 * @return
		 * @throws Exception
		 */
		public String getStatusQual(String qualSpec, String choice, String studentNr, String acaYear, String acaPeriod) throws Exception {

			String query = "";
			String result = "";
			
			if ("Qual".equalsIgnoreCase(qualSpec)){
				query = "select stuapq.new_qual, stuapq.choice_nr, "
						+ " upper(grd.long_eng_descripti) as QualDesc, "
						+ " grd.long_eng_descripti as QualLongDesc "
						+ " from stuapq, grd "
						+ " where stuapq.new_qual = grd.code "
						+ " and stuapq.mk_student_nr = ? "
						+ " and stuapq.academic_year = ? "
						+ " and application_period = ? "
						+ " and stuapq.choice_nr = ? ";
			}else{
				query = "select stuapq.new_spes, stuapq.choice_nr, "
						+ " quaspc.english_descriptio as SpecDesc "
						+ " from stuapq, grd, quaspc "
						+ " where stuapq.new_qual = grd.code "
						+ " and stuapq.new_qual = quaspc.mk_qualification_c "
						+ " and stuapq.new_spes = quaspc.speciality_code "
						+ " and stuapq.mk_student_nr = ? "
						+ " and stuapq.academic_year = ? "
						+ " and application_period = ? "
						+ " and stuapq.choice_nr = ? ";
			}

			try {
				//log.debug("ApplyForStudentNumberQueryDAO - getStatusQual - qualSpec: " + qualSpec + ", query:" + query);
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, acaPeriod, choice});
				Iterator i = queryList.iterator();
				if (i.hasNext()) {
					while (i.hasNext()) {
						ListOrderedMap data = (ListOrderedMap) i.next();
							//log.debug("ApplyForStudentNumberQueryDAO - getStatusQual - Choice:" + choice);
							if ("Qual".equalsIgnoreCase(qualSpec)){
								result = data.get("new_qual").toString().trim() + " - " + data.get("QualLongDesc").toString().trim();
								//result = data.get("new_qual").toString().trim() + " - " + data.get("QualDesc").toString().trim() + " (" + data.get("QualLongDesc").toString().trim() + ")";
								//log.debug("ApplyForStudentNumberQueryDAO - getStatusQual - Qual:" + result);
							}else if ("Spec".equalsIgnoreCase(qualSpec)){
								if (data.get("new_spes").toString().trim() != null && !"".equalsIgnoreCase(data.get("new_spes").toString().trim()) && !"0".equalsIgnoreCase(data.get("new_spes").toString().trim())){
									result = data.get("new_spes").toString().trim() + " - " + data.get("SpecDesc").toString().trim();
								}else{
									result = "N/A - Not Applicable";
								}
								//log.debug("ApplyForStudentNumberQueryDAO - getStatusQual - Spec:" + result);
							}
					}
				}else{
					result = "Not Found";
				}
			} catch (Exception ex) {
				throw new Exception(
						"ApplyForStudentNumberQueryDAO : Error retrieving student saved quals / " + ex);
			}
			return result;
		}
		
		public boolean getOfferStatus(String studentNr,String acaYear,String acaPeriod, String choice, String callingMethod) throws Exception{
			
			boolean status = false;;
			try{		
				String query = "select status_code, space_offered  "
							+ " from stuapq "
							+ " where mk_student_nr = ? "
							+ " and academic_year = ? "
							+ " and application_period = ? "
							+ " and choice_nr = ? "
							+ " and status_code in ('TO', 'EO', 'TX', 'EX') "
							+ " and space_offered = 'Y' "
							+ " and (offer_accepted <> 'Y' or offer_accepted is null) ";

				//log.debug("ApplyForStudentNumberQueryDAO - getOfferStatus - query="+query+", studentNr="+studentNr+", acaYear="+acaYear+", acaPeriod="+acaPeriod+", choice="+choice+", CallingMethod="+callingMethod);

				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, acaPeriod, choice});
				Iterator i = queryList.iterator();
				if (i.hasNext()) {
					status = true; //data.get("status_code").toString();
				}
			} catch (Exception ex) {
				throw new Exception(
						"ApplyForStudentNumberQueryDAO : Error reading STUAPQ Offer status / " + ex);
			} finally {
				try {

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			//log.debug("ApplyForStudentNumberQueryDAO - getOfferStatus - Status: " + status );
			return status;
		}
	//Update STUAPQ for Enrolment Offer

	public ArrayList<KeyValue> getMatricSubjects(String matCert, String matric1, String matric2, String matric3, String matric4, String matric5, String matric6, String matric7, String matric8, String matric9, String matric10, String matric11, String matric12) throws Exception {
	
		//log.debug("ApplyForStudentNumberQueryDAO - getMatricSubjects - matCert:" + matCert);
		
		String query = "";
		StringBuilder matricExclude = new StringBuilder();
		ArrayList<KeyValue> keyvalues = new ArrayList<KeyValue>();
		
		if (!"0".equals(matric1)){matricExclude.append(" and code <> '"+matric1+"' ");}
		if (!"0".equals(matric2)){matricExclude.append(" and code <> '"+matric2+"' ");}
		if (!"0".equals(matric3)){matricExclude.append(" and code <> '"+matric3+"' ");}
		if (!"0".equals(matric4)){matricExclude.append(" and code <> '"+matric4+"' ");}
		if (!"0".equals(matric5)){matricExclude.append(" and code <> '"+matric5+"' ");}
		if (!"0".equals(matric6)){matricExclude.append(" and code <> '"+matric6+"' ");}
		if (!"0".equals(matric7)){matricExclude.append(" and code <> '"+matric7+"' ");}
		if (!"0".equals(matric8)){matricExclude.append(" and code <> '"+matric8+"' ");}
		if (!"0".equals(matric9)){matricExclude.append(" and code <> '"+matric9+"' ");}
		if (!"0".equals(matric10)){matricExclude.append(" and code <> '"+matric10+"' ");}
		if (!"0".equals(matric11)){matricExclude.append(" and code <> '"+matric11+"' ");}
		if (!"0".equals(matric12)){matricExclude.append(" and code <> '"+matric12+"' ");}
		
		//log.debug("ApplyForStudentNumberQueryDAO - getMatricSubjects - matricExclude= " + matricExclude.toString());

		//ToDo
		String querySC = "  select code, eng_description from mvk "
						+ " where (h_grade_border <> 0 and h_grade_border is not null) "
						+ " and (s_grade_border <> 0 or s_grade_border is not null) "
						+ " and in_use_flag = 'Y' "
						+ matricExclude.toString()
						+ " order by eng_description ";
		
		String queryNSC = " select code, eng_description from mvk "
						+ " where (n_grade_border <> 0 and n_grade_border is not null) "
						+ " and in_use_flag = 'Y' "
						+ matricExclude.toString()
						+ " order by eng_description ";
		
		if ("MATR".equalsIgnoreCase(matCert) || "SENR".equalsIgnoreCase(matCert) || "ONBK".equalsIgnoreCase(matCert)){
			query = querySC;
		}else{
			query = queryNSC;
		}
	  	//log.debug("ApplyForStudentNumberQueryDAO - getMatricSubjects - query: " + query);
	
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				KeyValue subjects = new KeyValue();
				subjects.setKey(data.get("CODE").toString());
				subjects.setValue(data.get("ENG_DESCRIPTION").toString().toUpperCase().trim());
				//log.debug("ApplyForStudentNumberQueryDAO - getMatricSubjects - CODE:" + data.get("CODE").toString()+", DESC="+data.get("ENG_DESCRIPTION").toString());
				keyvalues.add(subjects);
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error reading Matric Subject list / " + ex);
		}
		return keyvalues;
	}

	public ArrayList<LabelValueBean> getAppealDocTypes() throws Exception {
		
		//log.debug("ApplyForStudentNumberQueryDAO - getAppealDocTypes");

		ArrayList list = new ArrayList();

		String query = "select distinct code as docCode, info as docType, eng_description as docDescription "
					+ " from gencod "
					+ " where fk_gencatcode = '208' "
					+ " and in_use_flag = 'Y' "
					+ " order by docDescription asc";
		
	  	//log.debug("ApplyForStudentNumberQueryDAO - getAppealDocTypes - query: " + query);

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				list.add(new org.apache.struts.util.LabelValueBean(data.get("docDescription").toString(), data.get("docCode").toString()+"@"+data.get("docType").toString()));
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error reading title list / " + ex);
		}
		return list;
	}	
	
	public int updateSTUXML(String oldStuNr, String newStuNr, String acaYear, String acaPeriod) throws Exception {

		//log.debug("ApplyForStudentNumberQueryDAO - updateSTUXML - Start");
	
		String dbParam = "";
		int result = 0;

		try {
			String query  = "update STUXML "
						+ " set MK_STUDENT_NR = ? "
						+ " WHERE MK_STUDENT_NR = ? "
						+ " AND MK_ACADEMIC_YEAR = ? "
						+ " AND MK_ACADEMIC_PERIOD = ? ";

			//log.debug("ApplyForStudentNumberQueryDAO - updateSTUXML - Query="+query+", oldStuNr="+oldStuNr+", newStuNr="+newStuNr+", acaYear="+acaYear+", acaPeriod="+acaPeriod);
			
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			result = jdt.update(query, new Object []{newStuNr, oldStuNr, acaYear, acaPeriod});
		} catch (Exception ex) {
			//log.debug("ApplyForStudentNumberQueryDAO: Error - Updating Temp Record for student nr / "+newStuNr + " / oldStuNr="+oldStuNr+", newStuNr="+newStuNr+", acaYear="+acaYear+", acaPeriod="+acaPeriod + " / " +ex);
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error - Updating Temp Record for student="+newStuNr + " / oldStuNr="+oldStuNr+", newStuNr="+newStuNr+", acaYear="+acaYear+", acaPeriod="+acaPeriod + " / " +ex);
		}
		//log.debug("ApplyForStudentNumberQueryDAO - updateSTUXML - End");
		return result;
	}
	
	public int updateSTULOG(String oldStuNr, String newStuNr, String acaYear, String acaPeriod) throws Exception {

		//log.debug("ApplyForStudentNumberQueryDAO - updateSTULOG - Start");
	
		String dbParam = "";
		int result = 0;

		try {
			String query  = "update STULOG "
						+ " set STUDENT = ? "
						+ " WHERE STUDENT = ? "
						+ " AND YEAR = ? "
						+ " AND PERIOD = ? ";

			//log.debug("ApplyForStudentNumberQueryDAO - updateSTULOG - Query="+query+", oldStuNr="+oldStuNr+", newStuNr="+newStuNr+", acaYear="+acaYear+", acaPeriod="+acaPeriod);
			
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			result = jdt.update(query, new Object []{newStuNr, oldStuNr, acaYear, acaPeriod});
		} catch (Exception ex) {
			//log.debug("ApplyForStudentNumberQueryDAO: Error - Updating Log Record for student nr / "+newStuNr + " / oldStuNr="+oldStuNr+", newStuNr="+newStuNr+", acaYear="+acaYear+", acaPeriod="+acaPeriod + " / " +ex);
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error - Updating Log Record for student="+newStuNr + " / oldStuNr="+oldStuNr+", newStuNr="+newStuNr+", acaYear="+acaYear+", acaPeriod="+acaPeriod + " / " +ex);
		}
		//log.debug("ApplyForStudentNumberQueryDAO - updateSTULOG - End");
		return result;
	}
	
	public String getCountry(String studentNr) throws Exception{
		
		String result = "";
		try{		
			String query = "select mk_country_code  "
						+ " from stu "
						+ " where nr = ? ";

			//log.debug("ApplyForStudentNumberQueryDAO - getCountry - query="+query+", studentNr="+studentNr);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				result = data.get("mk_country_code").toString().trim();
			}
		} catch (Exception ex) {
			throw new Exception(
					"ApplyForStudentNumberQueryDAO : Error reading Student Country / " + ex);
		} finally {
			try {

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		//log.debug("ApplyForStudentNumberQueryDAO - getCountry - Result: " + result );
		return result;
	}
}
