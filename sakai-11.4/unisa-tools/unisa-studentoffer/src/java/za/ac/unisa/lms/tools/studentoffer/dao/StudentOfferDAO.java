package za.ac.unisa.lms.tools.studentoffer.dao;

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
import za.ac.unisa.lms.tools.studentoffer.bo.Categories;
import za.ac.unisa.lms.tools.studentoffer.bo.Qualifications;
import za.ac.unisa.lms.tools.studentoffer.bo.Specializations;
import za.ac.unisa.lms.tools.studentoffer.bo.StudySelected;
import za.ac.unisa.lms.tools.studentoffer.dao.KeyValue;
import za.ac.unisa.lms.tools.studentoffer.forms.Qualification;
import za.ac.unisa.lms.tools.studentoffer.forms.Student;

@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
public class StudentOfferDAO extends StudentSystemDAO {

	public static Log log = LogFactory.getLog(StudentOfferDAO.class);


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
			//log.debug("StudentOfferDAO - validateClosingDate - Year = " + acaYear + ", Query: " + query);
				
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(query, new Object []{acaYear});
				Iterator i = queryList.iterator();
				while (i.hasNext()) {
					//log.debug("validateClosingDate - has Next");
					ListOrderedMap data = (ListOrderedMap) i.next();
					String dateType = data.get("dateType").toString().trim().toUpperCase();
					//log.debug("StudentOfferDAO - validateClosingDate - TYPE = " + dateType+", from_date = " + data.get("from_date").toString().trim()+", to_date = " + data.get("to_date").toString().trim());
					sDates.add(dateType);
				}
			} catch (Exception ex) {
				throw new Exception(
					"StudentOfferDAO : Error reading closing date: " + acaYear + "  / " + ex);
			}
		//Testing sDates
		//log.debug("StudentOfferDAO - validateClosingDate - sDates Size="+sDates.size());
		//if (!sDates.isEmpty()){ //Check Dates Array before building Query String
		//	for (int i=0; i < sDates.size(); i++){
		//		//log.debug("StudentOfferDAO - validateClosingDate - sDates="+sDates.get(i).toString());
		//	}
		//}
		
		return sDates;
	}
		
	public boolean validateClosingDateAll(String acaYear) throws Exception {
	
		String query = "select academic_year "
				+ " from regdat "
				+ " where type like 'WAP%' "
				+ " and academic_year = ? "
				+ " and trunc(sysdate) between from_date and to_date";
				
		//log.debug("StudentOfferDAO - validateClosingDateAll - Query: " + query);
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{acaYear});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				//log.debug("StudentOfferDAO - validateClosingDateAll - Result: TRUE");
				return true;
			}else{
				//log.debug("StudentOfferDAO - validateClosingDateAll - Result: FALSE");
				return false;
			}
		} catch (Exception ex) {
			throw new Exception(
				"StudentOfferDAO : Error closing date" + acaYear + " / " + ex);
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
					"StudentOfferDAO : Error validating Leap Year / " + ex);
		}
		return leapCheck;
	}

	public String getSTUXMLRef(String studentNr, String acaYear, String acaPeriod, String referenceType, String referenceValue, String callingMethod) {
		
		//log.debug("StudentOfferDAO - getSTUXMLRef - "+studentNr+" - Start");
		
		String result = "False"; 
		
		//log.debug("StudentOfferDAO - getSTUXMLRef - "+studentNr+" - Calling Method: " + callingMethod);
	
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
			//log.debug("StudentOfferDAO - getSTUXMLRef - "+studentNr+" - Query: " + query);
			//log.debug("StudentOfferDAO - getSTUXMLRef - "+studentNr+" - Query: " +studentNr+", "+acaYear+", "+acaPeriod+", "+referenceType+", "+referenceValue);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, acaPeriod, referenceType, referenceValue});
			Iterator<?> i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				//log.debug("StudentOfferDAO - getSTUXMLRef: Has Row");
				result = data.get("REFERENCE_SEQUENCE").toString().trim();
				//log.debug("StudentOfferDAO - getSTUXMLRef - "+studentNr+" - Result: STUXML StudentInfo Exists");
			}else{
				result = "0";
				//log.debug("StudentOfferDAO - getSTUXMLRef - "+studentNr+" - Result: No STUXML StudentInfo");
			}
			
		} catch (Exception ex) {
			//log.debug("StudentOfferDAO: getSTUXMLRef - Error Retrieving STUXML StuInfo"+referenceType+" for Student / "+studentNr + "/ " +ex);
			result = "Error Retrieving STUXML StuInfo for Returning Student / "+studentNr;
		}
		//log.debug("StudentOfferDAO - getSTUXMLRef - "+studentNr+" - Retrieve StuInfo Sequence - Done");
		return result;
	}

	public String saveSTUXMLRef(String studentNr, String acaYear, String acaPeriod, String referenceType, int referenceSequence, String referenceData, String callingMethod, String queryType) {

		//log.debug("StudentOfferDAO - saveSTUXMLRef - "+studentNr+" - Start");
		
		String query = "";
		String dbParam = "";
		int resultInt = 0;
		String result = "False";  //Note: Not Boolean;

		//log.debug("StudentOfferDAO - saveSTUXMLRef - "+studentNr+" - referenceData="+referenceData+", Calling Method: " + callingMethod);
		
		try{
			query  = "insert into STUXML (mk_student_nr,mk_academic_year,mk_academic_period,reference_type,reference_value,reference_sequence,date_initial,date_modified,detail) "
					+ " values (?, ?, ?, ?, '1', ?, systimestamp, systimestamp, ?)";
	
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			resultInt = jdt.update(query, new Object []{studentNr,acaYear,acaPeriod,referenceType,referenceSequence,referenceData});
			dbParam = studentNr+","+acaYear+","+acaPeriod+","+referenceType+","+referenceSequence+", "+referenceData;

			//log.debug("StudentOfferDAO - saveSTUXMLRef - "+studentNr+" - QueryType="+queryType+", Query="+query+", resultInt="+resultInt);
			//log.debug("StudentOfferDAO - saveSTUXMLRef - "+studentNr+" - dbParam: "+dbParam);
			
			if (resultInt > 0){
				result = "True";
			}
		} catch (Exception ex) {
			//log.debug("StudentOfferDAO: Error "+queryType+" "+referenceType+" "+referenceSequence+" for Student= "+studentNr + " / " + dbParam + " / " + ex);
			//log.debug("StudentOfferDAO: Error "+queryType+" "+referenceType+" "+referenceSequence+" for Student= "+studentNr + " / " + dbParam + " / " + ex);
			result = "Error "+queryType+" STUXML "+referenceType+" "+referenceSequence+" for Student= "+studentNr;
		}
		//log.debug("StudentOfferDAO - saveSTUXMLRef - "+studentNr+" - End");
		return result;
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
				+ " and stuapq.academic_year= ? "		
				+ " and (stu.nr <= 70000000 or stu.nr >=80000000)";
		
		try {
			//log.debug("validateSTUAPQ - query: " +  query+" - dbParam: " +  surname.toUpperCase()+","+firstNames.toUpperCase()+","+bDay+","+acaYear);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{surname.toUpperCase(), firstNames.toUpperCase(), bDay, acaYear});
			
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				//log.debug("validateSTUAPQ - isNumber= " +  data.get("mk_student_nr").toString());
				stuapqCheck=true;
			}
		} catch (Exception ex) {
			throw new Exception(
					"StudentOfferDAO : Error validating STUAPQ student number / " + ex);
		}
		//log.debug("validateSTUAPQ - stuapqCheck: " +  stuapqCheck);
		return stuapqCheck;
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
		//log.debug("StudentOfferDAO - personalDetail="+ query);

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
					"StudentOfferDAO : Error retrieving student details / " + ex);
		}
		//log.debug("StudentOfferDAO - personalDetail="+ result);
		return result;
		
	}
	
	public String isSTUXML(String studentNr, String acaYear, String acaPeriod, String referenceType, String referenceValue, String callingMethod) {
		
		//log.debug("StudentOfferDAO - isSTUXML - "+studentNr+" - Start");
		
		String result = "False";  //Note: Not Boolean;
		
		//log.debug("StudentOfferDAO - isSTUXML - "+studentNr+" - Calling Method: " + callingMethod);

		String query = "select detail from STUXML where mk_student_nr = ? and mk_academic_year = ? and mk_academic_period = ? and reference_type = ? and reference_value = ?";

		try {
			//log.debug("StudentOfferDAO - isSTUXML - "+studentNr+" - Query: " + query);
			//log.debug("StudentOfferDAO - isSTUXML - "+studentNr+" - Query: " +studentNr+", "+acaYear+", "+acaPeriod+", "+referenceType+", "+referenceValue);
			
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, acaPeriod, referenceType, referenceValue});
			Iterator<?> i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				result = "True";
				//log.debug("StudentOfferDAO - isSTUXML - "+studentNr+" - Result: STUXML Exists="+data.get("detail").toString().trim());
			}else{
				result = "False";
				//log.debug("StudentOfferDAO - isSTUXML - "+studentNr+" - Result: No STUXML");
			}
			
		} catch (Exception ex) {
			//log.debug("StudentOfferDAO: Error Retrieving STUXML "+referenceType+" "+referenceValue+" for Student / "+studentNr + "/ " +ex);
			result = "Error Retrieving STUXML Category for Returning Student / "+studentNr;
		}
		//log.debug("StudentOfferDAO - isSTUXML - "+studentNr+" - "+callingMethod+" - Done");
		return result;
	}
	
	public String saveSTUXML(String studentNr, String acaYear, String acaPeriod, String referenceType, String referenceValue, String referenceSequence, String referenceData, String callingMethod, String queryType) {

		//log.debug("StudentOfferDAO - saveSTUXML - "+studentNr+" - Start");
		
		String query = "";
		String dbParam = "";
		int resultInt = 0;
		String result = "False";  //Note: Not Boolean;

		//log.debug("StudentOfferDAO - saveSTUXML - "+studentNr+" - referenceSequence="+referenceSequence+" - referenceData="+referenceData+", Calling Method: " + callingMethod);
		
		try{
			query  = "insert into STUXML (mk_student_nr,mk_academic_year,mk_academic_period,reference_type,reference_value,reference_sequence,date_initial,date_modified,detail) "
					+ " values (?, ?, ?, ?, ?, ?, systimestamp, systimestamp, ?)";

			dbParam = studentNr+","+acaYear+","+acaPeriod+","+referenceType+","+referenceValue+", "+referenceSequence+", "+referenceData;
			//log.debug("StudentOfferDAO - saveSTUXML - INSERT - Query="+query+" - "+studentNr+" - dbParam: "+dbParam);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			resultInt = jdt.update(query, new Object []{studentNr,acaYear,acaPeriod,referenceType,referenceValue,referenceSequence,referenceData});
			
			//log.debug("StudentOfferDAO - saveSTUXML - "+studentNr+" - QueryType="+queryType+", resultInt="+resultInt);
			
			if (resultInt > 0){
				result = "True";
			}
		} catch (Exception ex) {
			//log.debug("StudentOfferDAO: Error "+queryType+" "+referenceType+" "+referenceValue+" "+referenceSequence+" "+referenceData+" for Student= "+studentNr + " / " + dbParam + " / " + ex);
			log.info("StudentOfferDAO: Error "+queryType+" "+referenceType+" "+referenceValue+" "+referenceSequence+" "+referenceData+" for Student= "+studentNr + " / " + dbParam + " / " + ex);
			result = "Error "+queryType+" saveSTUXML "+referenceType+" "+referenceValue+" "+referenceSequence+" for Student= "+studentNr;
		}
		//log.debug("StudentOfferDAO - saveSTUXML - "+studentNr+" - End, Result="+result);
		return result;
	}
	
	public boolean getOfferStatus(String studentNr,String acaYear, String acaPeriod, String choice, String callingMethod) throws Exception{
		
		boolean status = false;;
		try{		
			String query = "select status_code, space_offered  "
						+ " from stuapq "
						+ " where mk_student_nr = ? "
						+ " and academic_year = ? "
						+ " and application_period = ?"
						+ " and choice_nr = ? "
						+ " and status_code in ('TO', 'EO') "
						+ " and space_offered = 'Y' "
						+ " and (offer_accepted <> 'Y' or offer_accepted is null) ";

			//log.debug("getOfferStatus - query="+query+", studentNr="+studentNr+", acaYear="+acaYear+", choice="+choice+", CallingMethod="+callingMethod);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, acaPeriod, choice});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				status = true; //data.get("status_code").toString();
			}
		} catch (Exception ex) {
			throw new Exception(
					"StudentOfferDAO : Error reading STUAPQ Offer status / " + ex);
		} finally {
			try {

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		//log.debug("getOfferStatus - Status: " + status );
		return status;
	}
	
	
public boolean getPendingStatus(String studentNr,String acaYear, String acaPeriod, String choice, String callingMethod) throws Exception{
		
		boolean status = false;;
		try{		
			String query = "select status_code, space_offered  "
						+ " from stuapq "
						+ " where mk_student_nr = ? "
						+ " and academic_year = ? "
						+ " and application_period = ?"
						+ " and choice_nr = ? "
						+ " and status_code in ('AP', 'EM', 'RO')";

			//log.debug("getOfferStatus - query="+query+", studentNr="+studentNr+", acaYear="+acaYear+", choice="+choice+", CallingMethod="+callingMethod);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, acaPeriod, choice});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				status = true; //data.get("status_code").toString();
			}
		} catch (Exception ex) {
			throw new Exception(
					"StudentOfferDAO : Error reading STUAPQ Pending status / " + ex);
		} finally {
			try {

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		//log.debug("getOfferStatus - Status: " + status );
		return status;
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
					+ " grd.eng_description as QualDesc, "
					+ " grd.long_eng_descripti as QualLongDesc "
					+ " from stuapq, grd "
					+ " where stuapq.new_qual = grd.code "
					+ " and stuapq.mk_student_nr = ? "
					+ " and stuapq.academic_year = ? "
					+ " and stuapq.application_period = ? "
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
					+ " and stuapq.application_period = ? "
					+ " and stuapq.choice_nr = ? ";
		}

		try {
			//log.debug("StudentOfferDAO - getStatusQual - Query:" + query+", studentNr: " + studentNr+", acaYear: " + acaYear+", choice: " + choice+", qualSpec: " + qualSpec);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, acaPeriod, choice});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				while (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
						//log.debug("StudentOfferDAO - getStatusQual - Choice:" + choice);
						if ("Qual".equalsIgnoreCase(qualSpec)){
							result = data.get("new_qual").toString().trim() + " - " + data.get("QualLongDesc").toString().trim();
							//result = data.get("new_qual").toString().trim() + " - " + data.get("QualDesc").toString().trim() + " (" + data.get("QualLongDesc").toString().trim() + ")";
							//log.debug("StudentOfferDAO - getStatusQual - Qual:" + result);
						}else if ("Spec".equalsIgnoreCase(qualSpec)){
							if (data.get("new_spes").toString().trim() != null && !"".equalsIgnoreCase(data.get("new_spes").toString().trim()) && !"0".equalsIgnoreCase(data.get("new_spes").toString().trim())){
								result = data.get("new_spes").toString().trim() + " - " + data.get("SpecDesc").toString().trim();
							}else{
								result = "N/A - Not Applicable";
							}
							//log.debug("StudentOfferDAO - getStatusQual - Spec:" + result);
						}
				}
			}else{
				result = "Not Found";
			}
		} catch (Exception ex) {
			throw new Exception(
					"StudentOfferDAO : Error retrieving student saved quals / " + ex);
		}
		return result;
	}

	/************ Get Offer Reason *************/
	public String getApplyStatus(String studentNr, String acaYear, String acaPeriod, String choice) throws Exception{
	
		  //log.debug("StudentOfferDAO - getApplyStatus studentNr: " + studentNr+", acaYear: " + acaYear+", choice: " + choice);
	
		  String result = "";
		  boolean overSubscribed = false;
	
	    try {
	  	  String query1 = "select stuapq.new_qual "
	  			  	+ " from stuapq, gencod "
	  			  	+ " where stuapq.new_qual = gencod.code "
	  			  	+ " and stuapq.choice_nr = ?"
	  			  	+ " and stuapq.academic_year = ? "
	  			  	+ " and stuapq.application_period = ?"
	  			  	+ " and gencod.fk_gencatcode='249' "
	  			  	+ " and gencod.in_use_flag='Y' "
	  			  	+ " and mk_student_nr = ? ";
	  	  
	  	  //log.debug("StudentOfferDAO getQualSubscription - getApplyStatus - Checked OverSubscribed - query1: " + query1+", choice="+choice+", acaYear="+acaYear+", studentNr="+studentNr);
	  	  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	  	  List queryList = jdt.queryForList(query1, new Object []{choice, acaYear, acaPeriod, studentNr});
	  	  Iterator i = queryList.iterator();
	  	  if (i.hasNext()) {
		    	  //log.debug("StudentOfferDAO getQualSubscription - getApplyStatus - studentNr="+studentNr+", - choice="+choice+" - IS Oversubscribed");
	  		  overSubscribed = true;
	  	  }else{
	  		  //log.debug("StudentOfferDAO getQualSubscription - getApplyStatus - studentNr="+studentNr+", - choice="+choice+" - IS NOT Oversubscribed");
	  	  }
	
			  String query2 = "select new_qual, status_code, admission_verified, docs_outstanding, "
					  	+ " COALESCE(space_offered, ' ') as space_offered,"
					  	+ " COALESCE(results_outstanding, ' ') as results_outstanding"
			  			+ " from stuapq "
	  			  	+ " where choice_nr = ? "
	  			  	+ " and academic_year = ? "
	  			  	+ " and application_period = ?"
	  			  	+ " and mk_student_nr = ? ";
	  			  		    	  
			  //log.debug("StudentOfferDAO getApplyStatus - Check Status - query2: " + query2+", choice="+choice+", acaYear="+acaYear+", studentNr="+studentNr);
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
	  		  
	  		  //log.debug("StudentOfferDAO getApplyStatus - Qualification="+qual+", choice="+choice+", status="+status+", verified="+verified+", docs="+docs+", space="+space);
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
	    					"StudentOfferDAO: Error occured on reading Application Status / "+ex);
	    	}
	}
	
	public String getApplyPeriod(String StudentNr, String acaYear, String qualCode, String choice) throws Exception{
			
		String result = "";
	
		try{ 
			String query = "select application_period "
						+ " from stuapq "
						+ " where mk_student_nr = ? "
						+ " and academic_year = ? "
						+ " and new_qual = ? "
						+ " and choice_nr = ? ";
	
	    		//log.debug("StudentOfferDAO - getApplyPeriod - Query: " + query +", StudentNr="+StudentNr+", acaYear="+acaYear+", qualCode="+qualCode+", Choice="+choice);
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(query, new Object []{StudentNr, acaYear, qualCode, choice});
				Iterator i = queryList.iterator();
				if (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					result = data.get("application_period").toString().trim();
				}
		    } catch (Exception ex) {
		    	throw new Exception("StudentOfferDAO - getApplyPeriod : Error reading application period / " + ex);
	    }
		//log.debug("StudentOfferDAO - getApplyPeriod - QualCode="+qualCode+", Period=" + result); 
		    return result;
	}
	
	public String getStatusDesc(String status) throws Exception{
	  	
	  	//log.debug("StudentOfferDAO - getStatusDesc - Status: "+status);
	
	  	String result = "";
	
	  	try{ 
	  		if (status.equalsIgnoreCase("NOAPP")){
	  			result = "No Application found for this academic year";
	  		}else{
		    		String query = "select info from gencod "
		    					+ " where code = ? "
		    					+ " and fk_gencatcode='264' "
		    					+ " and in_use_flag='Y' ";
	
		    		//log.debug("StudentOfferDAO - getStatusDesc - Query: " + query + ", Status: "+status);
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
		    	throw new Exception("StudentOfferDAO - getStatusDesc : Error reading status / " + ex);
	      }
	  	//log.debug("StudentOfferDAO - getStatusDesc - Final Status=" + result); 
		    return result;
	 }
	  
	public String getDeclineReason(String StudentNr, String qualCode, String acaYear, String acaPeriod) throws Exception{
		
		//log.debug("StudentOfferDAO - getDeclineReason - StudentNr: "+StudentNr+", qualCode: "+qualCode);
	
		String result = "";
	
		try{ 
			String query = "select eng_description "
						+ " from gencod, stuapq "
						+ " where stuapq.GC263_DECLINE_REASON = gencod.code "
						+ " and gencod.fk_gencatcode = 263 "
						+ " and gencod.in_use_flag = 'Y' "
						+ " and stuapq.academic_year = ? "
						+ " and stuapq.application_period = ?"
						+ " and stuapq.mk_student_nr = ? "
						+ " and stuapq.new_qual = ? ";
	
	    		//log.debug("StudentOfferDAO - getDeclineReason - Query: " + query +", acaYear="+acaYear+", StudentNr="+StudentNr+", qualCode="+qualCode);
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
		    	throw new Exception("StudentOfferDAO - getDeclineReason : Error reading decline reason / " + ex);
	    }
		//log.debug("StudentOfferDAO - getDeclineReason - Decline Reseason=" + result); 
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
					+ " and stuapq.application_period = ?"
					+ " and stuapq.choice_nr = ? ";
		try {
			//log.debug("StudentOfferDAO - vrfyNewQualShort - qualSpec: " + qualSpec + ", query:" + query +", studentNr="+studentNr+", acaYear="+acaYear+", choice="+choice);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, acaPeriod, choice});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				result = data.get("new_qual").toString().trim();
				//log.debug("StudentOfferDAO - vrfyNewQualShort - Qual:" + result);
			}
		} catch (Exception ex) {
			throw new Exception(
					"StudentOfferDAO : Error retrieving STUAPQ Qualification / " + ex);
		}
		return result;
	}
}
