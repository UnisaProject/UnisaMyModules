package za.ac.unisa.lms.tools.studentappeal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import za.ac.unisa.lms.tools.studentappeal.bo.Categories;
import za.ac.unisa.lms.tools.studentappeal.bo.Qualifications;
import za.ac.unisa.lms.tools.studentappeal.bo.Specializations;
import za.ac.unisa.lms.tools.studentappeal.bo.StudySelected;
import za.ac.unisa.lms.tools.studentappeal.dao.KeyValue;
import za.ac.unisa.lms.tools.studentappeal.forms.Qualification;
import za.ac.unisa.lms.tools.studentappeal.forms.Student;

@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
public class StudentAppealDAO extends StudentSystemDAO {

	public static Log log = LogFactory.getLog(StudentAppealDAO.class);
	
	/**
	 * Check if student has a student/reference number
	 */
	public String validateStudentLogin(String surname, String firstNames, String  bDay) throws Exception {
		
		String stuCheck = "newStu"; //New Student
		
		//Check if student record exists by using Surname, First Names, Date of Birth and ID (Or ForeignID or Passport)
		String query  = "select nr from stu "
					+ " where surname = ? "
					+ " and first_names = ? "
					+ " and birth_date = (SELECT TO_DATE (?, 'DD/MM/YYYY') FROM DUAL) "
					+ " and (nr <= 70000000 or nr >=80000000)";
		
		try {
			//log.debug("validateStudentLogin: query=" +  query+", surname=" +  surname.toUpperCase()+", firstNames=" +  firstNames.toUpperCase()+". bDay=" +  bDay);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{surname.toUpperCase(), firstNames.toUpperCase(), bDay});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				stuCheck = data.get("nr").toString(); //Current Student
			}
		} catch (Exception ex) {
			throw new Exception(
					"StudentAppealDAO : Error validating STU student number / " + ex);
		}
		//log.debug("validateStudentLogin: stuCheck" +  stuCheck);
		return stuCheck;
	}
	
	/**
	 * Check if student has a student/reference number
	 */
	public boolean validateSTUAPQ(String surname, String firstNames, String  bDay, String acaYear, String acaPeriod) throws Exception {
		
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
				+ " and stuapq.academic_year= ? "
				+ " and stuapq.application_period = ? "
				+ " and (stu.nr <= 70000000 or stu.nr >=80000000)";
				//+ " and stu.application_appeal in ('AP','TN','RG') " //We now only allow one entry, no matter what the appeal is - Confirmed with Claudette and Wynand
		
		try {
			dbParam = surname.toUpperCase()+","+firstNames.toUpperCase()+","+bDay+","+acaYear+","+acaPeriod;
			//log.debug("validateSTUAPQ - query: " +  query);
			//log.debug("validateSTUAPQ - dbParam: " +  dbParam);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{surname.toUpperCase(), firstNames.toUpperCase(), bDay, acaYear, acaPeriod});
			
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				//log.debug("validateSTUAPQ - isNumber= " +  data.get("mk_student_nr").toString());
				stuapqCheck=true;
			}
		} catch (Exception ex) {
			throw new Exception(
					"StudentAppealDAO : Error validating STUAPQ student number / " + ex);
		}
		//log.debug("validateSTUAPQ - stuapqCheck: " +  stuapqCheck);
		return stuapqCheck;
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
			//log.debug("StudentAppealDAO - validateStudentRec - Query="+query+", StudentNr="+studentNr+", AcademicYear="+acaYear);
			
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
					"StudentAppealDAO : Error validating student academic record / " + ex);
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
		//log.debug("StudentAppealDAO - personalDetail="+ query);

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
					"StudentAppealDAO : Error retrieving student details / " + ex);
		}
		//log.debug("StudentAppealDAO - personalDetail="+ result);
		return result;
		
	}

	public boolean validateClosingDateAll(String acaYear) throws Exception {

		String query = "select academic_year "
				+ " from regdat "
				+ " where type like 'WAP%' "
				+ " and academic_year = ? "
				+ " and trunc(sysdate) between from_date and to_date";
				
				//log.debug("StudentAppealDAO - validateClosingDateAll - Query: " + query);
				try {
					JdbcTemplate jdt = new JdbcTemplate(getDataSource());
					List queryList = jdt.queryForList(query, new Object []{acaYear});
					Iterator i = queryList.iterator();
					if (i.hasNext()) {
						//log.debug("StudentAppealDAO - validateClosingDateAll - Result: TRUE");
						return true;
					}else{
						//log.debug("StudentAppealDAO - validateClosingDateAll - Result: FALSE");
						return false;
					}
				} catch (Exception ex) {
					throw new Exception(
							"StudentAppealDAO : Error closing date" + acaYear + " / " + ex);
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
				//log.debug("StudentAppealDAO - validateClosingDate - Year = " + acaYear + ", Query: " + query);
					
					JdbcTemplate jdt = new JdbcTemplate(getDataSource());
					List queryList = jdt.queryForList(query, new Object []{acaYear});
					Iterator i = queryList.iterator();
					while (i.hasNext()) {
						//log.debug("validateClosingDate - has Next");
						ListOrderedMap data = (ListOrderedMap) i.next();
						String dateType = data.get("dateType").toString().trim().toUpperCase();
						//log.debug("StudentAppealDAO - validateClosingDate - TYPE = " + dateType+", from_date = " + data.get("from_date").toString().trim()+", to_date = " + data.get("to_date").toString().trim());
						sDates.add(dateType);
					}
				} catch (Exception ex) {
					throw new Exception(
						"StudentAppealDAO : Error reading closing date: " + acaYear + "  / " + ex);
				}
			//Testing sDates
			//log.debug("StudentAppealDAO - validateClosingDate - sDates Size="+sDates.size());
			//if (!sDates.isEmpty()){ //Check Dates Array before building Query String
			//	for (int i=0; i < sDates.size(); i++){
			//		//log.debug("StudentAppealDAO - validateClosingDate - sDates="+sDates.get(i).toString());
			//	}
			//}
			
			return sDates;
		}

	public String getSTUXMLRef(String studentNr, String acaYear, String acaPeriod, String referenceType, String referenceValue, String callingMethod) {
		
		//log.debug("StudentAppealDAO - getSTUXMLRef - "+studentNr+" - Start");
		
		String result = "False"; 
		
		//log.debug("StudentAppealDAO - getSTUXMLRef - "+studentNr+" - Calling Method: " + callingMethod);

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
			//log.debug("StudentAppealDAO - getSTUXMLRef - "+studentNr+" - Query: " + query);
			//log.debug("StudentAppealDAO - getSTUXMLRef - "+studentNr+" - Query: " +studentNr+", "+acaYear+", "+acaPeriod+", "+referenceType+", "+referenceValue);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, acaPeriod, referenceType, referenceValue});
			Iterator<?> i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				//log.debug("StudentAppealDAO - getSTUXMLRef: Has Row");
				result = data.get("REFERENCE_SEQUENCE").toString().trim();
				//log.debug("StudentAppealDAO - getSTUXMLRef - "+studentNr+" - Result: STUXML StudentInfo Exists");
			}else{
				result = "0";
				//log.debug("StudentAppealDAO - getSTUXMLRef - "+studentNr+" - Result: No STUXML StudentInfo");
			}
			
		} catch (Exception ex) {
			//log.debug("StudentAppealDAO: getSTUXMLRef - Error Retrieving STUXML StuInfo"+referenceType+" for Student / "+studentNr + "/ " +ex);
			result = "Error Retrieving STUXML StuInfo for Returning Student / "+studentNr;
		}
		//log.debug("StudentAppealDAO - getSTUXMLRef - "+studentNr+" - Retrieve StuInfo Sequence - Done");
		return result;
	}
	
	public String saveSTUXMLRef(String studentNr, String acaYear, String acaPeriod, String referenceType, int referenceSequence, String referenceData, String callingMethod, String queryType) {

		//log.debug("StudentAppealDAO - saveSTUXMLRef - "+studentNr+" - Start");
		
		String query = "";
		String dbParam = "";
		int resultInt = 0;
		String result = "False";  //Note: Not Boolean;

		//log.debug("StudentAppealDAO - saveSTUXMLRef - "+studentNr+" - referenceData="+referenceData+", Calling Method: " + callingMethod);
		
		try{
			query  = "insert into STUXML (mk_student_nr,mk_academic_year,mk_academic_period,reference_type,reference_value,reference_sequence,date_initial,date_modified,detail) "
					+ " values (?, ?, ?, ?, '1', ?, systimestamp, systimestamp, ?)";
	
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			resultInt = jdt.update(query, new Object []{studentNr,acaYear,acaPeriod,referenceType,referenceSequence,referenceData});
			dbParam = studentNr+","+acaYear+","+acaPeriod+","+referenceType+","+referenceSequence+", "+referenceData;

			//log.debug("StudentAppealDAO - saveSTUXMLRef - "+studentNr+" - QueryType="+queryType+", Query="+query+", resultInt="+resultInt);
			//log.debug("StudentAppealDAO - saveSTUXMLRef - "+studentNr+" - dbParam: "+dbParam);
			
			if (resultInt > 0){
				result = "True";
			}
		} catch (Exception ex) {
			//log.debug("StudentAppealDAO: Error "+queryType+" "+referenceType+" "+referenceSequence+" for Student= "+studentNr + " / " + dbParam + " / " + ex);
			//log.debug("StudentAppealDAO: Error "+queryType+" "+referenceType+" "+referenceSequence+" for Student= "+studentNr + " / " + dbParam + " / " + ex);
			result = "Error "+queryType+" STUXML "+referenceType+" "+referenceSequence+" for Student= "+studentNr;
		}
		//log.debug("StudentAppealDAO - saveSTUXMLRef - "+studentNr+" - End");
		return result;
	}
	
	public String isSTUXML(String studentNr, String acaYear, String acaPeriod, String referenceType, String referenceValue, String callingMethod) {
		
		//log.debug("StudentAppealDAO - isSTUXML - "+studentNr+" - Start");
		
		String result = "False";  //Note: Not Boolean;
		
		//log.debug("StudentAppealDAO - isSTUXML - "+studentNr+" - Calling Method: " + callingMethod);

		String query = "select detail from STUXML where mk_student_nr = ? and mk_academic_year = ? and mk_academic_period = ? and reference_type = ? and reference_value = ?";

		try {
			//log.debug("StudentAppealDAO - isSTUXML - "+studentNr+" - Query: " + query);
			//log.debug("StudentAppealDAO - isSTUXML - "+studentNr+" - Query: " +studentNr+", "+acaYear+", "+acaPeriod+", "+referenceType+", "+referenceValue);
			
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, acaPeriod, referenceType, referenceValue});
			Iterator<?> i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				result = "True";
				//log.debug("StudentAppealDAO - isSTUXML - "+studentNr+" - Result: STUXML Exists="+data.get("detail").toString().trim());
			}else{
				result = "False";
				//log.debug("StudentAppealDAO - isSTUXML - "+studentNr+" - Result: No STUXML");
			}
			
		} catch (Exception ex) {
			//log.debug("StudentAppealDAO: Error Retrieving STUXML "+referenceType+" "+referenceValue+" for Student / "+studentNr + "/ " +ex);
			result = "Error Retrieving STUXML Category for Returning Student / "+studentNr;
		}
		//log.debug("StudentAppealDAO - isSTUXML - "+studentNr+" - "+callingMethod+" - Done");
		return result;
	}
	
	public String saveSTUXML(String studentNr, String acaYear, String acaPeriod, String referenceType, String referenceValue, String referenceSequence, String referenceData, String callingMethod, String queryType) {

		//log.debug("StudentAppealDAO - saveSTUXML - "+studentNr+" - Start");
		
		String query = "";
		String dbParam = "";
		int resultInt = 0;
		String result = "False";  //Note: Not Boolean;

		//log.debug("StudentAppealDAO - saveSTUXML - "+studentNr+" - referenceSequence="+referenceSequence+" - referenceData="+referenceData+", Calling Method: " + callingMethod);
		
		try{
			query  = "insert into STUXML (mk_student_nr,mk_academic_year,mk_academic_period,reference_type,reference_value,reference_sequence,date_initial,date_modified,detail) "
					+ " values (?, ?, ?, ?, ?, ?, systimestamp, systimestamp, ?)";

			dbParam = studentNr+","+acaYear+","+acaPeriod+","+referenceType+","+referenceValue+", "+referenceSequence+", "+referenceData;
			//log.debug("StudentAppealDAO - saveSTUXML - INSERT - Query="+query+" - "+studentNr+" - dbParam: "+dbParam);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			resultInt = jdt.update(query, new Object []{studentNr,acaYear,acaPeriod,referenceType,referenceValue,referenceSequence,referenceData});
			
			//log.debug("StudentAppealDAO - saveSTUXML - "+studentNr+" - QueryType="+queryType+", resultInt="+resultInt);
			
			if (resultInt > 0){
				result = "True";
			}
		} catch (Exception ex) {
			//log.debug("StudentAppealDAO: Error "+queryType+" "+referenceType+" "+referenceValue+" "+referenceSequence+" "+referenceData+" for Student= "+studentNr + " / " + dbParam + " / " + ex);
			log.info("StudentAppealDAO: Error "+queryType+" "+referenceType+" "+referenceValue+" "+referenceSequence+" "+referenceData+" for Student= "+studentNr + " / " + dbParam + " / " + ex);
			result = "Error "+queryType+" saveSTUXML "+referenceType+" "+referenceValue+" "+referenceSequence+" for Student= "+studentNr;
		}
		//log.debug("StudentAppealDAO - saveSTUXML - "+studentNr+" - End, Result="+result);
		return result;
	}

	public String getXMLSelected(String reference_type, String reference_value, String studentNr, String acaYear, String acaPeriod, String callingMethod) {

		String result="";

		//log.debug("StudentAppealDAO: - getXMLSelected - reference_type : " + reference_type + " reference_value: " + reference_value + " : " + studentNr + " : " + acaYear + " : " + acaPeriod + " : " + callingMethod);
	
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
			//log.debug("StudentAppealDAO - Doing Select - checkXML: " +checkXML);
			//log.debug(checkXML);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(checkXML);
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				//log.debug("StudentAppealDAO - checkXML: Has Row");
				result = data.get("detail").toString().trim();
			}
		} catch (Exception ex) {
			//log.debug("StudentAppealDAO: Error retrieving Temp STUXML for Student / "+studentNr + " / " + ex);
		}
	return result;

	}
	
	public StudySelected queryStudySelected(String studentNr, String acaYear, String acaPeriod) throws Exception {

		StudySelected selectedNew = new StudySelected();

		//log.debug("StudentAppealDAO - queryStudySelectedNew - studentNr: " + studentNr);
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
				//log.debug("StudentAppealDAO - queryStudySelectedNew - queryNew (else): " + query);
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
						"StudentAppealDAO: Error reading STUAPQ qualification / " + ex);

			} finally {

			}
			if (selectedNew.getQual1() != null && selectedNew.getQual2() != null){
				return selectedNew;
			}else{
				return null;
			}
		}
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
			//log.debug("checkLeapYear: query" +  query);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query, new Object []{"15/02/"+bYear});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				leapCheck = data.get("LEAP").toString();
			}
		} catch (Exception ex) {
			throw new Exception(
					"StudentAppealDAO : Error validating Leap Year / " + ex);
		}
		return leapCheck;
	}

	  /************ Get Offer Reason *************/
	  public String getApplyStatus(String studentNr, String acaYear, String acaPeriod, String choice) throws Exception{

		  //log.debug("StudentAppealDAO - getApplyAppeal studentNr: " + studentNr+", acaYear: " + acaYear+", acaPeriod: " + acaPeriod+", choice: " + choice);

		  String result = "";
		  boolean overSubscribed = false;

	      try {
	    	  String query1 = "select stuapq.new_qual "
	    			  	+ " from stuapq, gencod "
	    			  	+ " where stuapq.new_qual = gencod.code "
	    			  	+ " and mk_student_nr = ? "
	    			  	+ " and stuapq.academic_year = ? "
	    			  	+ " and stuapq.application_period = ? "
	    			  	+ " and gencod.fk_gencatcode='249' "
	    			  	+ " and gencod.in_use_flag='Y' "
	    			  	+ " and stuapq.choice_nr = ? ";
	    	  
	    	  //log.debug("StudentAppealDAO getQualSubscription - getApplyAppeal - Checked OverSubscribed - query1: " + query1+", choice="+choice+", acaYear="+acaYear+", acaPeriod="+acaPeriod+", studentNr="+studentNr);
	    	  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	    	  List queryList = jdt.queryForList(query1, new Object []{studentNr, acaYear, acaPeriod, choice});
	    	  Iterator i = queryList.iterator();
	    	  if (i.hasNext()) {
		    	  //log.debug("StudentAppealDAO getQualSubscription - getApplyAppeal - studentNr="+studentNr+", - choice="+choice+" - IS Oversubscribed");
	    		  overSubscribed = true;
	    	  }else{
	    		  //log.debug("StudentAppealDAO getQualSubscription - getApplyAppeal - studentNr="+studentNr+", - choice="+choice+" - IS NOT Oversubscribed");
	    	  }

//			  String query2 = "select new_qual, status_code, admission_verified, docs_outstanding,admin_section, "
//					  	+ " COALESCE(space_offered, ' ') as space_offered,"
//					  	+ " COALESCE(results_outstanding, ' ') as results_outstanding"
//			  			+ " from stuapq "
//	    			  	+ " where choice_nr = ? "
//	    			  	+ " and academic_year = ? "
//	    			  	+ " and application_period = ? "
//	    			  	+ " and mk_student_nr = ? ";
			  
			  String query2 = "select new_qual,admission_verified, docs_outstanding,admin_section,status_code,"
					  	+ " COALESCE(space_offered, ' ') as space_offered,"
					  	+ " COALESCE(results_outstanding, ' ') as results_outstanding,"
					  	+ " CASE WHEN status_code='AX' AND admin_section='083' THEN (select to_char(max(STUAPL.ACTION_TIMESTAMP),'YYYYMMDD') from stuapl where STUAPL.ACADEMIC_YEAR=STUAPQ.ACADEMIC_YEAR"
					  	+ " and STUAPL.APPLICATION_PERIOD=STUAPQ.APPLICATION_PERIOD"
					  	+ " and STUAPL.MK_STUDENT_NUMBER=STUAPQ.MK_STUDENT_NR"
					  	+ " and STUAPL.ACTION_CODE_GC272='VERADM') END as decline_date"					  
			  			+ " from stuapq "
	    			  	+ " where choice_nr = ? "
	    			  	+ " and academic_year = ? "
	    			  	+ " and application_period = ? "
	    			  	+ " and mk_student_nr = ? ";
	    			  		    	  
			  //log.debug("StudentAppealDAO getApplyAppeal - Check Appeal - query2: " + query2+", choice="+choice+", acaYear="+acaYear+", acaPeriod="+acaPeriod+", studentNr="+studentNr);
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
	    		  String adminSection = data.get("admin_section").toString().trim();
	    		  
	    		  //log.debug("StudentAppealDAO getApplyAppeal - Qualification="+qual+", choice="+choice+", appeal="+status+", verified="+verified+", docs="+docs+", space="+space);
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
	    		  //Johanet July2018 BRD Hons and PGD not allowed to appeal if 10 working days from decline date
	    		  }else if ("AX".equalsIgnoreCase(status) && "083".equalsIgnoreCase(adminSection)){
	    			 //Determine decline date	
	    			  boolean withInTenDays = true;
	    			  if (data.get("decline_date")!= null) {
	    				  String declineDateStr = data.get("decline_date").toString().trim();
		    			  SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
						  Date declineDate = formatter.parse(declineDateStr);
						  Date currentDate = new Date();					  
						  int duration = calculateDuration(declineDate, currentDate) - calculatePublicHolidays(declineDate, currentDate);						 
						  if (duration > 10) {
							  withInTenDays = false;
						  }
	    			  }	    			 
	    			  if (withInTenDays) {
	    				  return "AX";
	    			  }else {
	    				  return "AXE";
	    			  }	    				  
	    		  }else {
	    			  return status;
	    		  }
	    	  }else{
	    		  return "NOAPP";
	    	  }
	      	} catch (Exception ex) {
	      			throw new Exception(
	      					"StudentAppealDAO: Error occured on reading Application Appeal / "+ex);
	      	}
	  }
	  
	  //Johanet 2018July BRD - 9.1 cannot appeal after 10 working days
	  //Number of public holidays not on a Saturday or Sunday
	  public int calculatePublicHolidays(Date startDate, Date endDate) throws Exception {		
		  
		  SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		  String startDateString = df.format(startDate);
		  String endDateString = df.format(endDate);
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(startDate);
		  int startYear = cal.get(Calendar.YEAR);
		  cal.setTime(endDate);
		  int endYear = cal.get(Calendar.YEAR);

			int publicHolidays = 0; 
			
			//Check if student record exists by using Surname, First Names, Date of Birth and ID (Or ForeignID or Passport)
			String query  = "select count(*) as days from regdat " + 
					" where REGDAT.ACADEMIC_YEAR in (?,?)" + 
					" and REGDAT.TYPE like 'PUBH_%'" + 
					" and from_date <= to_date(?,'YYYYMMDD')" + 
					" and from_date >= to_date(?,'YYYYMMDD')" + 
					" and TO_CHAR(REGDAT.FROM_DATE,'DY','NLS_DATE_LANGUAGE=ENGLISH') not in ('SAT','SUN')";
			
			try {			
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(query, new Object []{startYear, endYear,endDateString, startDateString});
				Iterator i = queryList.iterator();
				if (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					publicHolidays = Integer.parseInt(data.get("days").toString()); //Current Student
				}
			} catch (Exception ex) {
				throw new Exception(
						"StudentAppealDAO : Error reading public holidays / " + ex);
			}
			return publicHolidays;
		}
	  
	  public String getAppealDesc(String appeal) throws Exception{
	    	
	    	//log.debug("StudentAppealDAO - getAppealDesc - Appeal: "+appeal);

	    	String result = "";

	    	try{ 
	    		if (appeal.equalsIgnoreCase("NOAPP")){
	    			result = "No Application found for this academic year";
	    		}else{
		    		String query = "select info from gencod "
		    					+ " where code = ? "
		    					+ " and fk_gencatcode='264' "
		    					+ " and in_use_flag='Y' ";
	
		    		//log.debug("StudentAppealDAO - getAppealDesc - Query: " + query + ", Appeal: "+appeal);
					JdbcTemplate jdt = new JdbcTemplate(getDataSource());
					List queryList = jdt.queryForList(query, new Object []{appeal});
					Iterator i = queryList.iterator();
					if (i.hasNext()) {
						ListOrderedMap data = (ListOrderedMap) i.next();
						result = data.get("info").toString().trim();
					}else{
						result = "Appeal not found";
					}
	    		}
	 	    } catch (Exception ex) {
	 	    	throw new Exception("StudentAppealDAO - getAppealDesc : Error reading appeal / " + ex);
	        }
	    	//log.debug("StudentAppealDAO - getAppealDesc - Final Appeal=" + result); 
	 	    return result;
	   }
	  
	  public String getDeclineReason(String StudentNr, String qualCode, String acaYear, String acaPeriod) throws Exception{
	    	
	    	//log.debug("StudentAppealDAO - getDeclineReason - StudentNr: "+StudentNr+", qualCode: "+qualCode);

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
	
		    		//log.debug("StudentAppealDAO - getDeclineReason - Query: " + query);
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
	 	    	throw new Exception("StudentAppealDAO - getDeclineReason : Error reading decline reason / " + ex);
	        }
	    	//log.debug("StudentAppealDAO - getDeclineReason - Decline Reseason=" + result); 
	 	    return result;
	   }
	    
		/**
		 * This method retrieves the student's saved qualifications from STUAPQ
		 * and returns the values to applyAppeal.jsp 		 * 
		 * @param studentNr
		 * @param acaYear
		 * @param acaPeriod
		 * @return
		 * @throws Exception
		 */
		public String getAppealQual(String qualSpec, String choice, String studentNr, String acaYear, String acaPeriod) throws Exception {

			String query = "";
			String result = "";
			
			if ("Qual".equalsIgnoreCase(qualSpec)){
				query = "select stuapq.new_qual, stuapq.choice_nr, "
						+ " grd.eng_description as QualDesc, "
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
			//log.debug(query);

			try {
				//log.debug("StudentAppealDAO - getAppealQual - qualSpec: " + qualSpec + ", query:" + query);
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, acaPeriod, choice});
				Iterator i = queryList.iterator();
				if (i.hasNext()) {
					while (i.hasNext()) {
						ListOrderedMap data = (ListOrderedMap) i.next();
							//log.debug("StudentAppealDAO - getAppealQual - Choice:" + choice);
							if ("Qual".equalsIgnoreCase(qualSpec)){
								result = data.get("new_qual").toString().trim() + " - " + data.get("QualLongDesc").toString().trim();
								//result = data.get("new_qual").toString().trim() + " - " + data.get("QualDesc").toString().trim() + " (" + data.get("QualLongDesc").toString().trim() + ")";
								//log.debug("StudentAppealDAO - getAppealQual - Qual:" + result);
							}else if ("Spec".equalsIgnoreCase(qualSpec)){
								if (data.get("new_spes").toString().trim() != null && !"".equalsIgnoreCase(data.get("new_spes").toString().trim()) && !"0".equalsIgnoreCase(data.get("new_spes").toString().trim())){
									result = data.get("new_spes").toString().trim() + " - " + data.get("SpecDesc").toString().trim();
								}else{
									result = "N/A - Not Applicable";
								}
								//log.debug("StudentAppealDAO - getAppealQual - Spec:" + result);
							}
					}
				}else{
					result = "Not Found";
				}
			} catch (Exception ex) {
				throw new Exception(
						"StudentAppealDAO : Error retrieving student saved quals / " + ex);
			}
			return result;
		}
		
		public boolean getOfferAppeal(String studentNr,String acaYear,String acaPeriod, String choice, String callingMethod) throws Exception{
			
			boolean appeal = false;;
			try{		
				String query = "select appeal_code, space_offered  "
							+ " from stuapq "
							+ " where mk_student_nr = ? "
							+ " and academic_year = ? "
							+ " and application_period = ? "
							+ " and choice_nr = ? "
							+ " and appeal_code in ('TO', 'EO', 'TX', 'EX') "
							+ " and space_offered = 'Y' "
							+ " and (offer_accepted <> 'Y' or offer_accepted is null) ";

				//log.debug("getOfferAppeal - query="+query+", studentNr="+studentNr+", acaYear="+acaYear+", acaPeriod="+acaPeriod+", choice="+choice+", CallingMethod="+callingMethod);

				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, acaPeriod, choice});
				Iterator i = queryList.iterator();
				if (i.hasNext()) {
					appeal = true; //data.get("appeal_code").toString();
				}
			} catch (Exception ex) {
				throw new Exception(
						"StudentAppealDAO : Error reading STUAPQ Offer appeal / " + ex);
			} finally {
				try {

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			//log.debug("getOfferAppeal - Appeal: " + appeal );
			return appeal;
		}

	public ArrayList<LabelValueBean> getAppealDocTypes() throws Exception {
		
		//log.debug("StudentAppealDAO - getAppealDocTypes");

		ArrayList list = new ArrayList();

		String query = "select distinct code as docCode, info as docType, eng_description as docDescription "
					+ " from gencod "
					+ " where fk_gencatcode = '208' "
					+ " and in_use_flag = 'Y' "
					+ " order by docDescription asc";
		
		//log.debug(query);

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
					"StudentAppealDAO : Error reading title list / " + ex);
		}
		return list;
	}	
	
	 //Johanet 2018July BRD - 9.1 cannot appeal after 10 working days
	public static int calculateDuration(Date startDate, Date endDate)
	{
	  Calendar startCal = Calendar.getInstance();
	  startCal.setTime(startDate);

	  Calendar endCal = Calendar.getInstance();
	  endCal.setTime(endDate);

	  int workDays = 0;

	  if (startCal.getTimeInMillis() > endCal.getTimeInMillis())
	  {
	    startCal.setTime(endDate);
	    endCal.setTime(startDate);
	  }

	  do
	  {
	    startCal.add(Calendar.DAY_OF_MONTH, 1);
	    if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
	    {
	      workDays++;
	    }
	  }
	  while (startCal.getTimeInMillis() <= endCal.getTimeInMillis());
	  
	  return workDays;
	}

}
