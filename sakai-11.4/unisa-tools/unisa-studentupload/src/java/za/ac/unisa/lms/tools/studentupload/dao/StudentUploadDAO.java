package za.ac.unisa.lms.tools.studentupload.dao;

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
import za.ac.unisa.lms.tools.studentupload.bo.Categories;
import za.ac.unisa.lms.tools.studentupload.bo.Qualifications;
import za.ac.unisa.lms.tools.studentupload.bo.Specializations;
import za.ac.unisa.lms.tools.studentupload.bo.StudySelected;
import za.ac.unisa.lms.tools.studentupload.dao.KeyValue;
import za.ac.unisa.lms.tools.studentupload.forms.Qualification;
import za.ac.unisa.lms.tools.studentupload.forms.Student;

@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
public class StudentUploadDAO extends StudentSystemDAO {

	public static Log log = LogFactory.getLog(StudentUploadDAO.class);

	public String getQualDesc(String sQual) throws Exception {
		
		String desc = "";
		
		String query = "select eng_description from grd where code= ? ";

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{sQual});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
					desc = data.get("ENG_DESCRIPTION").toString().toUpperCase();
			}
		} catch (Exception ex) {
			throw new Exception(
					"StudentUploadDAO : Error retrieving Qual description. / " + ex);
		}
		return desc;
	}
	
		
	public String getSpecDesc(String sQual, String sSpec) throws Exception {
		String query = "select ENGLISH_DESCRIPTIO from quaspc "
					+ " where mk_qualification_c = ? "
					+ " and speciality_code = ? ";

		
		String desc = "";

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{sQual, sSpec});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
					desc = data.get("ENGLISH_DESCRIPTIO").toString().toUpperCase();
			}
		} catch (Exception ex) {
			throw new Exception(
					"StudentUploadDAO : Error retrieving Spec description / " + ex);
		}
		return desc;
	}
	
	/**
	 * Check if student has a student/reference number
	 */
	public String validateStudentLogin(String surname, String firstNames, String  bDay) throws Exception {
		
		String stuCheck = "newStu"; //New Student
		
		//Check if student record exists by using Surname, First Names, Date of Birth and ID (Or ForeignID or Passport)
		String query  = "select nr from stu "
					+ " where surname = ? "
					+ " and first_names = ? "
					+ " and birth_date = (SELECT TO_DATE (?, 'DD/MM/YYYY') FROM DUAL) ";
					//+ " and (nr <= 70000000 or nr >=80000000)";
		
		try {
			//log.debug("StudentUploadDAO - validateStudentLogin: query=" +  query+", surname=" +  surname.toUpperCase()+", firstNames=" +  firstNames.toUpperCase()+". bDay=" +  bDay);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{surname.toUpperCase(), firstNames.toUpperCase(), bDay});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				stuCheck = data.get("nr").toString(); //Current Student
			}
		} catch (Exception ex) {
			throw new Exception(
					"StudentUploadDAO : Error validating STU student number / " + ex);
		}
		//log.debug("StudentUploadDAO - validateStudentLogin: stuCheck" +  stuCheck);
		return stuCheck;
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
			//log.debug("StudentUploadDAO - validateStudentRec - Query="+query+", StudentNr="+studentNr+", AcademicYear="+acaYear);
			
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
					"StudentUploadDAO : Error validating student academic record / " + ex);
		}
		
	}
	
	/**
	 * Check if student has a student/reference number
	 */
	public boolean validateSTUAPQ(String surname, String firstNames, String  bDay, String acaYear) throws Exception {
		
		boolean stuapqCheck = false;
		String dbParam = "";
		String result = "";
		//Check if student record exists by using Surname, First Names, Date of Birth
		
		String query  = "select DISTINCT stuapq.mk_student_nr "
				+ " from stu, stuann, stuapq "
				+ " where stu.nr = stuann.mk_student_nr "
				+ " and stu.nr = stuapq.mk_student_nr "
				+ " and stu.surname = ? "
				+ " and stu.first_names = ? "
				+ " and to_char(stu.birth_date, 'DD/MM/YYYY') = ? "
				+ " and stuapq.academic_year= ? ";	
		try {
			//log.debug("StudentUploadDAO - validateSTUAPQ - query="+query+", surname="+surname.toUpperCase()+", firstNames="+firstNames.toUpperCase()+", bDay="+bDay+", acaYear="+acaYear);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{surname.toUpperCase(), firstNames.toUpperCase(), bDay, acaYear});
			
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				//log.debug("StudentUploadDAO - validateSTUAPQ - isNumber= " +  data.get("mk_student_nr").toString());
				stuapqCheck=true;
			}
		} catch (Exception ex) {
			throw new Exception(
					"StudentUploadDAO : Error validating STUAPQ student number / " + ex);
		}
		//log.debug("StudentUploadDAO - validateSTUAPQ - stuapqCheck: " +  stuapqCheck);
		return stuapqCheck;
	}
	
	/**
	 * Check if student has a student/reference number
	 */
	
	public String validateNewApplicant(String acaYear, String acaPeriod, String referenceType1 ,String referenceType2, String referenceValue) throws Exception {
		
		String newApplicantNr = "";	
		
		String query  = "select a.DETAIL as studentNr from stuxml a where  a.mk_academic_year=? and a.mk_academic_period=? and a.reference_type=?" +
				" and exists (select * from stuxml b where B.MK_ACADEMIC_YEAR=A.MK_ACADEMIC_YEAR" + 
				" and B.MK_ACADEMIC_PERIOD=A.MK_ACADEMIC_PERIOD" + 
				" and B.REFERENCE_TYPE=?" + 
				" and B.MK_STUDENT_NR=A.MK_STUDENT_NR" +
				" and b.detail=?)";
		try {
			//log.debug("StudentUploadDAO - validateSTUAPQ - query="+query+", surname="+surname.toUpperCase()+", firstNames="+firstNames.toUpperCase()+", bDay="+bDay+", acaYear="+acaYear);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{acaYear, acaPeriod, referenceType1, referenceType2, referenceValue.toUpperCase()});
			
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				//log.debug("StudentUploadDAO - validateNewApplicant - isNumber= " +  data.get("mk_student_nr").toString());
				newApplicantNr=data.get("studentNr").toString();
			}
		} catch (Exception ex) {
			throw new Exception(
					"StudentUploadDAO : Error validating STUXML new Applicant / " + ex);
		}
		//log.debug("StudentUploadDAO - validateNewApplicant - newApplicantNr: " +  newApplicantNr);
		return newApplicantNr;
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
		//log.debug("StudentUploadDAO - personalDetail="+ query);

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
					"StudentUploadDAO : Error retrieving student details / " + ex);
		}
		//log.debug("StudentUploadDAO - personalDetail="+ result);
		return result;
		
	}

	public boolean validateClosingDateAll(String acaYear) throws Exception {

		String query = "select academic_year "
				+ " from regdat "
				+ " where type like 'WAP%' "
				+ " and academic_year = ? "
				+ " and trunc(sysdate) between from_date and to_date";
				
				//log.debug("StudentUploadDAO - validateClosingDateAll - Query: " + query);
				try {
					JdbcTemplate jdt = new JdbcTemplate(getDataSource());
					List queryList = jdt.queryForList(query, new Object []{acaYear});
					Iterator i = queryList.iterator();
					if (i.hasNext()) {
						//log.debug("StudentUploadDAO - validateClosingDateAll - Result: TRUE");
						return true;
					}else{
						//log.debug("StudentUploadDAO - validateClosingDateAll - Result: FALSE");
						return false;
					}
				} catch (Exception ex) {
					throw new Exception(
							"StudentUploadDAO : Error closing date" + acaYear + " / " + ex);
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
			//log.debug("StudentUploadDAO - validateClosingDate - Year = " + acaYear + ", Query: " + query);
					
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{acaYear});
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				//log.debug("StudentUploadDAO - validateClosingDate - has Next");
				ListOrderedMap data = (ListOrderedMap) i.next();
				String dateType = data.get("dateType").toString().trim().toUpperCase();
				//log.debug("StudentUploadDAO - validateClosingDate - TYPE = " + dateType+", from_date = " + data.get("from_date").toString().trim()+", to_date = " + data.get("to_date").toString().trim());
				sDates.add(dateType);
			}
		} catch (Exception ex) {
			throw new Exception(
					"StudentUploadDAO : Error reading closing date: " + acaYear + "  / " + ex);
		}
		//Testing sDates
		//log.debug("StudentUploadDAO - validateClosingDate - sDates Size="+sDates.size());
		//if (!sDates.isEmpty()){ //Check Dates Array before building Query String
		//	for (int i=0; i < sDates.size(); i++){
		//		//log.debug("StudentUploadDAO - validateClosingDate - sDates="+sDates.get(i).toString());
		//	}
		//}
		
		return sDates;
	}
		
	public StudySelected queryStudySelected(String studentNr, String acaYear) throws Exception {

		StudySelected selectedNew = new StudySelected();

		//log.debug("StudentUploadDAO - queryStudySelectedNew - studentNr: " + studentNr);
		if ("Temp".equalsIgnoreCase(studentNr) || "".equalsIgnoreCase(studentNr) ||studentNr == null){
			return null;
		}else{
			
			String query = "select new_qual, new_spes, choice_nr "
						+ " from STUAPQ "
						+ " where mk_student_nr = ? "
						+ " and academic_year = ? "
						+ " order by choice_nr " ;

			try {
				//log.debug("StudentUploadDAO - queryStudySelectedNew - queryNew (else): " + query);
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(query, new Object []{studentNr, acaYear});
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
						"StudentUploadDAO: Error reading STUAPQ qualification / " + ex);

			} finally {

			}
			if (selectedNew.getQual1() != null && selectedNew.getQual2() != null){
				return selectedNew;
			}else{
				return null;
			}
		}
	}
	  
	private ArrayList readDatabase(String query){
		ArrayList list = new ArrayList();

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(query);
		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			list.add(new org.apache.struts.util.LabelValueBean(data.get("eng_description").toString().toUpperCase(), data.get("CODE").toString()+"@"+data.get("eng_description").toString()));
		}

		return list;
	}

	public String getSTUXMLRef(String studentNr, String acaYear, String acaPeriod, String referenceType, String referenceValue, String callingMethod) {
		
		//log.debug("StudentUploadDAO - getSTUXMLRef - "+studentNr+" - Start");
		
		String result = "False"; 
		
		//log.debug("StudentUploadDAO - getSTUXMLRef - "+studentNr+" - Calling Method: " + callingMethod);

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
			//log.debug("StudentUploadDAO - getSTUXMLRef - "+studentNr+" - Query: " +studentNr+", "+acaYear+", "+acaPeriod+", "+referenceType+", "+referenceValue);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, acaPeriod, referenceType, referenceValue});
			Iterator<?> i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				//log.debug("StudentUploadDAO - getSTUXMLRef: Has Row");
				result = data.get("REFERENCE_SEQUENCE").toString().trim();
				//log.debug("StudentUploadDAO - getSTUXMLRef - "+studentNr+" - Result: STUXML StudentInfo Exists");
			}else{
				result = "0";
				//log.debug("StudentUploadDAO - getSTUXMLRef - "+studentNr+" - Result: No STUXML StudentInfo");
			}
			
		} catch (Exception ex) {
			//log.debug("StudentUploadDAO: getSTUXMLRef - Error Retrieving STUXML StuInfo"+referenceType+" for Student / "+studentNr + "/ " +ex);
			result = "Error Retrieving STUXML StuInfo for Returning Student / "+studentNr;
		}
		//log.debug("StudentUploadDAO - getSTUXMLRef - "+studentNr+" - Retrieve StuInfo Sequence - Done");
		return result;
	}
	
	public String saveSTUXMLRef(String studentNr, String acaYear, String acaPeriod, String referenceType, int referenceSequence, String referenceData, String callingMethod, String queryType) {

		//log.debug("StudentUploadDAO - saveSTUXMLRef - "+studentNr+" - Start");
		
		String query = "";
		String dbParam = "";
		int resultInt = 0;
		String result = "False";  //Note: Not Boolean;

		//log.debug("StudentUploadDAO - saveSTUXMLRef - "+studentNr+" - referenceData="+referenceData+", Calling Method: " + callingMethod);
		
		try{
			query  = "insert into STUXML (mk_student_nr,mk_academic_year,mk_academic_period,reference_type,reference_value,reference_sequence,date_initial,date_modified,detail) "
					+ " values (?, ?, ?, ?, '1', ?, systimestamp, systimestamp, ?)";
	
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			resultInt = jdt.update(query, new Object []{studentNr,acaYear,acaPeriod,referenceType,referenceSequence,referenceData});
			dbParam = studentNr+","+acaYear+","+acaPeriod+","+referenceType+","+referenceSequence+", "+referenceData;

			//log.debug("StudentUploadDAO - saveSTUXMLRef - "+studentNr+" - QueryType="+queryType+", Query="+query+", resultInt="+resultInt);
			//log.debug("StudentUploadDAO - saveSTUXMLRef - "+studentNr+" - dbParam: "+dbParam);
			
			if (resultInt > 0){
				result = "True";
			}
		} catch (Exception ex) {
			//log.debug("StudentUploadDAO: Error "+queryType+" "+referenceType+" "+referenceSequence+" for Student= "+studentNr + " / " + dbParam + " / " + ex);
			//log.debug("StudentUploadDAO: Error "+queryType+" "+referenceType+" "+referenceSequence+" for Student= "+studentNr + " / " + dbParam + " / " + ex);
			result = "Error "+queryType+" STUXML "+referenceType+" "+referenceSequence+" for Student= "+studentNr;
		}
		//log.debug("StudentUploadDAO - saveSTUXMLRef - "+studentNr+" - End");
		return result;
	}
	
	public String isSTUXML(String studentNr, String acaYear, String acaPeriod, String referenceType, String referenceValue, String callingMethod) {
		
		//log.debug("StudentUploadDAO - isSTUXML - "+studentNr+" - Start");
		
		String result = "False";  //Note: Not Boolean;
		
		//log.debug("StudentUploadDAO - isSTUXML - "+studentNr+" - Calling Method: " + callingMethod);

		String query = "select detail from STUXML where mk_student_nr = ? and mk_academic_year = ? and mk_academic_period = ? and reference_type = ? and reference_value = ?";

		try {
			//log.debug("StudentUploadDAO - isSTUXML - "+studentNr+" - Query: " + query);
			//log.debug("StudentUploadDAO - isSTUXML - "+studentNr+" - Query: " +studentNr+", "+acaYear+", "+acaPeriod+", "+referenceType+", "+referenceValue);
			
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, acaPeriod, referenceType, referenceValue});
			Iterator<?> i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				result = "True";
				//log.debug("StudentUploadDAO - isSTUXML - "+studentNr+" - Result: STUXML Exists="+data.get("detail").toString().trim());
			}else{
				result = "False";
				//log.debug("StudentUploadDAO - isSTUXML - "+studentNr+" - Result: No STUXML");
			}
			
		} catch (Exception ex) {
			//log.debug("StudentUploadDAO: Error Retrieving STUXML "+referenceType+" "+referenceValue+" for Student / "+studentNr + "/ " +ex);
			result = "Error Retrieving STUXML Category for Returning Student / "+studentNr;
		}
		//log.debug("StudentUploadDAO - isSTUXML - "+studentNr+" - "+callingMethod+" - Done");
		return result;
	}
	
	public String saveSTUXML(String studentNr, String acaYear, String acaPeriod, String referenceType, String referenceValue, String referenceSequence, String referenceData, String callingMethod, String queryType) {

		//log.debug("StudentUploadDAO - saveSTUXML - "+studentNr+" - Start");
		
		String query = "";
		String dbParam = "";
		int resultInt = 0;
		String result = "False";  //Note: Not Boolean;

		//log.debug("StudentUploadDAO - saveSTUXML - "+studentNr+" - referenceSequence="+referenceSequence+" - referenceData="+referenceData+", Calling Method: " + callingMethod);
		
		try{
			query  = "insert into STUXML (mk_student_nr,mk_academic_year,mk_academic_period,reference_type,reference_value,reference_sequence,date_initial,date_modified,detail) "
					+ " values (?, ?, ?, ?, ?, ?, systimestamp, systimestamp, ?)";

			dbParam = studentNr+","+acaYear+","+acaPeriod+","+referenceType+","+referenceValue+", "+referenceSequence+", "+referenceData;
			//log.debug("StudentUploadDAO - saveSTUXML - INSERT - Query="+query+" - "+studentNr+" - dbParam: "+dbParam);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			resultInt = jdt.update(query, new Object []{studentNr,acaYear,acaPeriod,referenceType,referenceValue,referenceSequence,referenceData});
			
			//log.debug("StudentUploadDAO - saveSTUXML - "+studentNr+" - QueryType="+queryType+", resultInt="+resultInt);
			
			if (resultInt > 0){
				result = "True";
			}
		} catch (Exception ex) {
			//log.debug("StudentUploadDAO: Error "+queryType+" "+referenceType+" "+referenceValue+" "+referenceSequence+" "+referenceData+" for Student= "+studentNr + " / " + dbParam + " / " + ex);
			log.info("StudentUploadDAO: Error "+queryType+" "+referenceType+" "+referenceValue+" "+referenceSequence+" "+referenceData+" for Student= "+studentNr + " / " + dbParam + " / " + ex);
			result = "Error "+queryType+" saveSTUXML "+referenceType+" "+referenceValue+" "+referenceSequence+" for Student= "+studentNr;
		}
		//log.debug("StudentUploadDAO - saveSTUXML - "+studentNr+" - End, Result="+result);
		return result;
	}

	public String getXMLSelected(String reference_type, String reference_value, String studentNr, String acaYear, String acaPeriod, String callingMethod) {

		String result="";

		//log.debug("StudentUploadDAO: - getXMLSelected - reference_type : " + reference_type + " reference_value: " + reference_value + " : " + studentNr + " : " + acaYear + " : " + acaPeriod + " : " + callingMethod);
	
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
			//log.debug("StudentUploadDAO - Doing Select - checkXML: " +checkXML);
			//log.debug(checkXML);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(checkXML);
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				//log.debug("StudentUploadDAO - checkXML: Has Row");
				result = data.get("detail").toString().trim();
			}
		} catch (Exception ex) {
			//log.debug("StudentUploadDAO: Error retrieving Temp STUXML for Student / "+studentNr + " / " + ex);
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
			//log.debug("StudentUploadDAO - getMDAPPLRecord - Query: "+query+ " - " +studentNr);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr});
			Iterator i = queryList.iterator();
			if (i.hasNext()){
				//log.debug("StudentUploadDAO - getMDAPPLRecord - Record: TRUE");
				result = true;
			}
		} catch (Exception ex) {
			throw new Exception("StudentUploadDAO : getMDAPPLRecord : Error occurred / "+ ex,ex);
		}
		//log.debug("StudentUploadDAO - getMDAPPLRecord - Result: "+result);
		return result;
	}
	
	  

	public String vrfyPrevQual(String qualSpec, String studentNr, String acaYear) throws Exception {

		String query = "";
		String result = "";
		
		if ("Qual".equalsIgnoreCase(qualSpec)){
			query = "select stuann.mk_highest_qualifi AS qualification_code, "
					+ " grd.eng_description as QualDesc, "
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
			//log.debug("StudentUploadDAO - vrfyPrevQual - qualSpec: " + qualSpec + ", query:" + query + ", StudentNr:" + studentNr);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
					//log.debug("StudentUploadDAO - vrfyPrevQual - Found Entry");
					if ("Qual".equalsIgnoreCase(qualSpec)){
						result = data.get("qualification_code").toString().trim() + " - " + data.get("QualLongDesc").toString().trim();
						//log.debug("StudentUploadDAO - vrfyPrevQual - Qual:" + result);
					}else if ("Spec".equalsIgnoreCase(qualSpec)){
						if (data.get("speciality_code").toString().trim() != null && !"".equalsIgnoreCase(data.get("speciality_code").toString().trim()) && !"0".equalsIgnoreCase(data.get("speciality_code").toString().trim())){
							result = data.get("speciality_code").toString().trim() + " - " + data.get("SpecDesc").toString().trim();
						}else{
							result = "N/A - Not Applicable";
						}
						//log.debug("StudentUploadDAO - vrfyPrevQual - Spec:" + result);
					}
			}else{
				//log.debug("StudentUploadDAO - vrfyPrevQual - No Data Found");
			}
		} catch (Exception ex) {
			throw new Exception(
					"StudentUploadDAO : Error validating student current and saved quals / " + ex);
		}
		return result;
	}
	
	/**
	 * This method retrieves the student's saved qualifications from STUAPQ as a final validation check
	 * and returns the values to applysnrcomplete.jsp and applysnrcompleteRet.jsp for new and returning
	 * students respectively
	 */
	public String vrfyNewQual(String qualSpec, String choice, String studentNr, String acaYear) throws Exception {

		String query = "";
		String stuChoice = "";
		String result = "";
		
		if ("Qual".equalsIgnoreCase(qualSpec)){
			query = "select stuapq.new_qual, stuapq.choice_nr, "
					+ " grd.eng_description as QualDesc, "
					+ " grd.long_eng_descripti as QualLongDesc "
					+ " from stuapq, grd "
					+ " where stuapq.new_qual = grd.code "
					+ " and stuapq.mk_student_nr = ? "
					+ " and stuapq.academic_year = ? "
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
					+ " and stuapq.choice_nr = ? ";
		}
		
		try {
			//log.debug("StudentUploadDAO - vrfyNewQual - qualSpec: " + qualSpec + ", query:" + query + ", StudentNr:" + studentNr + ", acaYear: " + acaYear + ", choice:" + choice);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, choice});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				while (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
						//log.debug("StudentUploadDAO - vrfyNewQual - Data Found");
						if ("Qual".equalsIgnoreCase(qualSpec)){
							result = data.get("new_qual").toString().trim() + " - " + data.get("QualDesc").toString().trim() + " (" + data.get("QualLongDesc").toString().trim() + ")";
							//log.debug("StudentUploadDAO - vrfyNewQual - Qual:" + result);
						}else if ("Spec".equalsIgnoreCase(qualSpec)){
							if (data.get("new_spes").toString().trim() != null && !"".equalsIgnoreCase(data.get("new_spes").toString().trim()) && !"0".equalsIgnoreCase(data.get("new_spes").toString().trim())){
								result = data.get("new_spes").toString().trim() + " - " + data.get("SpecDesc").toString().trim();
							}else{
								result = "N/A - Not Applicable";
							}
							//log.debug("StudentUploadDAO - vrfyNewQual - Spec:" + result);
						}
				}
			}else{
				//log.debug("StudentUploadDAO - vrfyNewQual - No Data Found");
				result = "N/A - Not Applicable";
			}
		} catch (Exception ex) {
			throw new Exception(
					"StudentUploadDAO : Error validating student saved quals / " + ex);
		}
		return result;
	}
	
	public String vrfyNewQualShort(String qualSpec, String choice, String studentNr, String acaYear) throws Exception {

		String query = "";
		String stuChoice = "";
		String result = "";
		
			query = "select stuapq.new_qual "
					+ " from stuapq, grd "
					+ " where stuapq.new_qual = grd.code "
					+ " and stuapq.mk_student_nr = ? "
					+ " and stuapq.academic_year = ? "
					+ " and stuapq.choice_nr = ? ";
		try {
			//log.debug("StudentUploadDAO - vrfyNewQualShort - qualSpec: " + qualSpec + ", query:" + query);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, choice});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				result = data.get("new_qual").toString().trim();
				//log.debug("StudentUploadDAO - vrfyNewQualShort - Qual:" + result);
			}
		} catch (Exception ex) {
			throw new Exception(
					"StudentUploadDAO : Error retrieving STUAPQ Qualification / " + ex);
		}
		return result;
	}
	
	/**
	 * This method retrieves the student's saved qualifications from STUAPQ as a final validation check
	 * and returns the values to applysnrcomplete.jsp and applysnrcompleteRet.jsp for new and returning
	 * students respectively
	 */
	public String vrfyNewMDQual(String qualSpec, String studentNr, String acaYear) throws Exception {

		String query = "";
		String stuChoice = "";
		String result = "";
		
		if ("Qual".equalsIgnoreCase(qualSpec)){
			query = "select stuann.mk_highest_qualifi, "
					+ " grd.eng_description as QualDesc, "
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
			//log.debug("StudentUploadDAO - vrfyNewQual - query:" + query);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr, acaYear});
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
					//log.debug("StudentUploadDAO - vrfyNewQual - stuChoice:" + stuChoice);
					if ("Qual".equalsIgnoreCase(qualSpec)){
						result = data.get("mk_highest_qualifi").toString().trim() + " - " + data.get("QualDesc").toString().trim() + " (" + data.get("QualLongDesc").toString().trim() + ")";
						//log.debug("StudentUploadDAO - vrfyNewQual - Qual:" + result);
					}else if ("Spec".equalsIgnoreCase(qualSpec)){
						if (data.get("speciality_code").toString().trim() != null && !"".equalsIgnoreCase(data.get("speciality_code").toString().trim()) && !"0".equalsIgnoreCase(data.get("speciality_code").toString().trim())){
							result = data.get("speciality_code").toString().trim() + " - " + data.get("SpecDesc").toString().trim();
						}else{
							result = data.get("SpecDesc").toString().trim();
						}
						//log.debug("StudentUploadDAO - vrfyNewQual - Spec:" + result);
					}
			}
		} catch (Exception ex) {
			throw new Exception(
					"StudentUploadDAO : Error validating MD student saved quals / " + ex);
		}
		return result;
	}
	
	public String getSTUAPQUpload(String studentNr,String acaYear, String choice, String callingMethod) throws Exception{
		
		String upload = "AP";
		try{		
			String query = "select status_code "
						+ " from stuapq "
						+ " where mk_student_nr = ? "
						+ " and academic_year = ? "
						+ " and choice_nr = ? "
						+ " and status_code in ('ND', 'NP') ";

			//log.debug("StudentUploadDAO - getSTUAPQUpload - query="+query+", studentNr="+studentNr+", acaYear="+acaYear+", choice="+choice+", CallingMethod="+callingMethod);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, choice});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				upload = data.get("status_code").toString();
			}
		} catch (Exception ex) {
			throw new Exception(
					"StudentUploadDAO : Error reading STUAPQ upload / " + ex);
		} finally {
			try {

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		//log.debug("StudentUploadDAO - getSTUAPQUpload - Upload: " + upload );
		return upload;
	}
	
	public String getFinalMD(String studentNr,String acaYear) throws Exception{
		
		String checkMD = "";
		try{		
			String query = "select grd.under_post_categor "
						+ " from stuann, grd "
						+ " where stuann.mk_highest_qualifi = grd.code "
						+ " and stuann.mk_student_nr = ? "
						+ " and stuann.mk_academic_year = ? ";

			//log.debug("StudentUploadDAO - getFinalMD - query: " + query );
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr, acaYear});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				checkMD = data.get("under_post_categor").toString();
			}
		} catch (Exception ex) {
			throw new Exception(
					"StudentUploadDAO : Error reading getting M&D Under_Post list / " + ex);
		} finally {
			try {

			} catch (Exception e) {

				e.printStackTrace();
			}

		}
		//log.debug("StudentUploadDAO - getFinalMD - checkMD: " + checkMD );
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
			//log.debug("StudentUploadDAO - checkLeapYear: query" +  query);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query, new Object []{"15/02/"+bYear});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				leapCheck = data.get("LEAP").toString();
			}
		} catch (Exception ex) {
			throw new Exception(
					"StudentUploadDAO : Error validating Leap Year / " + ex);
		}
		return leapCheck;
	}
	
	public String validateStaff(String studentNr, String testString, int funcNr) throws Exception {

		boolean validUsr = false;
		String result = "AccessUsrNo";
		String queryUsr = "SELECT NAME, "
					+ " ( "
					+ "    CHR(ASCII(SUBSTR(PASSWORD,1,1))-65 USING NCHAR_CS)  || "
					+ "    CHR(ASCII(SUBSTR(PASSWORD,2,1))-31 USING NCHAR_CS)  || "
					+ "    CHR(ASCII(SUBSTR(PASSWORD,3,1))-10 USING NCHAR_CS)  || "
					+ "    CHR(ASCII(SUBSTR(PASSWORD,4,1))-20 USING NCHAR_CS)  || "
					+ "    CHR(ASCII(SUBSTR(PASSWORD,5,1))-15 USING NCHAR_CS)  || "
					+ "    CHR(ASCII(SUBSTR(PASSWORD,6,1))-61 USING NCHAR_CS)  || "
					+ "    CHR(ASCII(SUBSTR(PASSWORD,7,1))-12 USING NCHAR_CS)  || "
					+ "    CHR(ASCII(SUBSTR(PASSWORD,8,1))-29 USING NCHAR_CS)  || "
					+ "    CHR(ASCII(SUBSTR(PASSWORD,9,1))-31 USING NCHAR_CS)  || "
					+ "    CHR(ASCII(SUBSTR(PASSWORD,10,1))-10 USING NCHAR_CS) || "
					+ "    CHR(ASCII(SUBSTR(PASSWORD,11,1))-20 USING NCHAR_CS) || "
					+ "    CHR(ASCII(SUBSTR(PASSWORD,12,1))-43 USING NCHAR_CS) || "
					+ "    CHR(ASCII(SUBSTR(PASSWORD,13,1))-13 USING NCHAR_CS)  "
					+ " ) AS PASSWORD "
					+ " FROM USR "
					+ " WHERE NR = ? "
					+ " AND IN_USED_FLAG = 'Y' "
					+ " AND NUMBER_OF_LOGON_AT < 3 ";
	
		try {
			//log.debug("StudentUploadDAO: - validateStaff - queryUsr=" + queryUsr + " studentNr=" + studentNr + ", testString=" + testString + ", funcNr=" + funcNr);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(queryUsr, new Object []{studentNr});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				String passDB = data.get("PASSWORD").toString();
				if ((passDB.toUpperCase().trim()).equalsIgnoreCase((testString.toUpperCase()).trim())){
					validUsr = true;
					//log.debug("StudentUploadDAO: - validateStaff - Password Valid");
				}else{
					//log.debug("StudentUploadDAO: - validateStaff - Password inValid - TestString="+testString.toUpperCase().trim()+", PassDB="+passDB.toUpperCase().trim());
				}
			}
		} catch (Exception ex) {
			throw new Exception(
					"StudentUploadDAO : Error validating Staff / " + ex);
		}
		
		//Check if user has access to the required function
		if (validUsr) {
			String queryFunc = "SELECT USGRFN.FK_ACTIONCODE "
							+ " FROM USGRFN, USGRUS  "
							+ " WHERE USGRFN.FK_USRGRPNR = USGRUS.FK_USRGRPNR "  
							+ " AND USGRFN.FK_FUNCTNR = ? "
							+ " AND USGRUS.FK_USRNR = ? ";
	
			try {
				//log.debug("StudentUploadDAO: - validateFunc - queryF=" + queryFunc + " studentNr=" + studentNr + ", Function=" + funcNr);
	
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(queryFunc, new Object []{funcNr, studentNr});
				Iterator i = queryList.iterator();
				if (i.hasNext()) {
					result = "AccessYes";
				}else{
					result = "AccessNo";
				}
			} catch (Exception ex) {
				throw new Exception(
						"StudentUploadDAO : Error validating Function / " + ex);
			}
		}
		return result;
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

				//log.debug("StudentUploadDAO - getQualType - query="+query+", qualCode="+qualCode);
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(query, new Object []{qualCode});
				Iterator i = queryList.iterator();
				if (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();

					dbCatCode = data.get("FK_KATCODE").toString().trim();
					dbDepartment = data.get("MK_DEPARTMENT_CODE").toString().trim();
					dbUnderPost = data.get("UNDER_POST_CATEGOR").toString().trim();
					dbType    = data.get("TYPE").toString().trim();
					//log.debug("StudentUploadDAO - getQualType - dbCatCode="+dbCatCode);

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
	      					"StudentUploadDAO: Error occured on reading Qualification Type / " + ex);
	      	} finally {
	      	}
	      //log.debug("StudentUploadDAO - getQualType - qualType="+qualType);
	      return qualType;
	  }
}
