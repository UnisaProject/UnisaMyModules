package za.ac.unisa.lms.tools.studentstatus.dao;

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
import za.ac.unisa.lms.tools.studentstatus.bo.Status;
import za.ac.unisa.lms.tools.studentstatus.bo.Categories;
import za.ac.unisa.lms.tools.studentstatus.bo.Qualifications;
import za.ac.unisa.lms.tools.studentstatus.bo.Specializations;
import za.ac.unisa.lms.tools.studentstatus.bo.StudySelected;
import za.ac.unisa.lms.tools.studentstatus.dao.KeyValue;
import za.ac.unisa.lms.tools.studentstatus.forms.Qualification;
import za.ac.unisa.lms.tools.studentstatus.forms.ScreeningVenue;
import za.ac.unisa.lms.tools.studentstatus.forms.Student;

@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
public class StudentStatusDAO extends StudentSystemDAO {

	public static Log log = LogFactory.getLog(StudentStatusDAO.class);

	public String getQualDesc(String sQual) throws Exception {
		String query = "select eng_description from grd where code= ? ";
		//log.debug("StudentStatusDAO - getCountries - Query="+query);
		String desc = "";

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
					"StudentStatusDAO : Error retrieving Qual description. / " + ex);
		}
		return desc;
	}
	
		
	public String getSpecDesc(String sQual, String sSpec) throws Exception {
		String query = "select ENGLISH_DESCRIPTIO from quaspc "
					+ " where mk_qualification_c = ? "
					+ " and speciality_code = ? ";

		//log.debug("StudentStatusDAO - getCountries - Query="+query);
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
					"StudentStatusDAO : Error retrieving Spec description / " + ex);
		}
		return desc;
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
				//+ " and stu.application_status in ('AP','TN','RG') " //We now only allow one entry, no matter what the status is - Confirmed with Claudette and Wynand
		
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
					"StudentStatusDAO : Error validating STUAPQ student number / " + ex);
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
		//log.debug("StudentStatusDAO - personalDetail="+ query);

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
					"StudentStatusDAO : Error retrieving student details / " + ex);
		}
		//log.debug("StudentStatusDAO - personalDetail="+ result);
		return result;
		
	}

	public boolean validateClosingDateAll(String acaYear) throws Exception {

		String query = "select academic_year "
				+ " from regdat "
				+ " where type like 'WAP%' "
				+ " and academic_year = ? "
				+ " and trunc(sysdate) between from_date and to_date";
				
				//log.debug("StudentStatusDAO - validateClosingDateAll - Query: " + query);
				try {
					JdbcTemplate jdt = new JdbcTemplate(getDataSource());
					List queryList = jdt.queryForList(query, new Object []{acaYear});
					Iterator i = queryList.iterator();
					if (i.hasNext()) {
						//log.debug("StudentStatusDAO - validateClosingDateAll - Result: TRUE");
						return true;
					}else{
						//log.debug("StudentStatusDAO - validateClosingDateAll - Result: FALSE");
						return false;
					}
				} catch (Exception ex) {
					throw new Exception(
							"StudentStatusDAO : Error closing date" + acaYear + " / " + ex);
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
				//log.debug("StudentStatusDAO - validateClosingDate - Year = " + acaYear + ", Query: " + query);
					
					JdbcTemplate jdt = new JdbcTemplate(getDataSource());
					List queryList = jdt.queryForList(query, new Object []{acaYear});
					Iterator i = queryList.iterator();
					while (i.hasNext()) {
						//log.debug("validateClosingDate - has Next");
						ListOrderedMap data = (ListOrderedMap) i.next();
						String dateType = data.get("dateType").toString().trim().toUpperCase();
						//log.debug("StudentStatusDAO - validateClosingDate - TYPE = " + dateType+", from_date = " + data.get("from_date").toString().trim()+", to_date = " + data.get("to_date").toString().trim());
						sDates.add(dateType);
					}
				} catch (Exception ex) {
					throw new Exception(
						"StudentStatusDAO : Error reading closing date: " + acaYear + "  / " + ex);
				}
			//Testing sDates
			//log.debug("StudentStatusDAO - validateClosingDate - sDates Size="+sDates.size());
			//if (!sDates.isEmpty()){ //Check Dates Array before building Query String
			//	for (int i=0; i < sDates.size(); i++){
			//		//log.debug("StudentStatusDAO - validateClosingDate - sDates="+sDates.get(i).toString());
			//	}
			//}
			
			return sDates;
		}
	
	public String getSTUXMLRef(String studentNr, String acaYear, String acaPeriod, String referenceType, String referenceValue, String callingMethod) {
		
		//log.debug("StudentStatusDAO - getSTUXMLRef - "+studentNr+" - Start");
		
		String result = "False"; 
		
		//log.debug("StudentStatusDAO - getSTUXMLRef - "+studentNr+" - Calling Method: " + callingMethod);

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
			//log.debug("StudentStatusDAO - getSTUXMLRef - "+studentNr+" - Query: " + query);
			//log.debug("StudentStatusDAO - getSTUXMLRef - "+studentNr+" - Query: " +studentNr+", "+acaYear+", "+acaPeriod+", "+referenceType+", "+referenceValue);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, acaPeriod, referenceType, referenceValue});
			Iterator<?> i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				//log.debug("StudentStatusDAO - getSTUXMLRef: Has Row");
				result = data.get("REFERENCE_SEQUENCE").toString().trim();
				//log.debug("StudentStatusDAO - getSTUXMLRef - "+studentNr+" - Result: STUXML StudentInfo Exists");
			}else{
				result = "0";
				//log.debug("StudentStatusDAO - getSTUXMLRef - "+studentNr+" - Result: No STUXML StudentInfo");
			}
			
		} catch (Exception ex) {
			//log.debug("StudentStatusDAO: getSTUXMLRef - Error Retrieving STUXML StuInfo"+referenceType+" for Student / "+studentNr + "/ " +ex);
			result = "Error Retrieving STUXML StuInfo for Returning Student / "+studentNr;
		}
		//log.debug("StudentStatusDAO - getSTUXMLRef - "+studentNr+" - Retrieve StuInfo Sequence - Done");
		return result;
	}
	
	public String saveSTUXMLRef(String studentNr, String acaYear, String acaPeriod, String referenceType, int referenceSequence, String referenceData, String callingMethod, String queryType) {

		//log.debug("StudentStatusDAO - saveSTUXMLRef - "+studentNr+" - Start");
		
		String query = "";
		String dbParam = "";
		int resultInt = 0;
		String result = "False";  //Note: Not Boolean;

		//log.debug("StudentStatusDAO - saveSTUXMLRef - "+studentNr+" - referenceData="+referenceData+", Calling Method: " + callingMethod);
		
		try{
			query  = "insert into STUXML (mk_student_nr,mk_academic_year,mk_academic_period,reference_type,reference_value,reference_sequence,date_initial,date_modified,detail) "
					+ " values (?, ?, ?, ?, '1', ?, systimestamp, systimestamp, ?)";
	
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			resultInt = jdt.update(query, new Object []{studentNr,acaYear,acaPeriod,referenceType,referenceSequence,referenceData});
			dbParam = studentNr+","+acaYear+","+acaPeriod+","+referenceType+","+referenceSequence+", "+referenceData;

			//log.debug("StudentStatusDAO - saveSTUXMLRef - "+studentNr+" - QueryType="+queryType+", Query="+query+", resultInt="+resultInt);
			//log.debug("StudentStatusDAO - saveSTUXMLRef - "+studentNr+" - dbParam: "+dbParam);
			
			if (resultInt > 0){
				result = "True";
			}
		} catch (Exception ex) {
			//log.debug("StudentStatusDAO: Error "+queryType+" "+referenceType+" "+referenceSequence+" for Student= "+studentNr + " / " + dbParam + " / " + ex);
			//log.debug("StudentStatusDAO: Error "+queryType+" "+referenceType+" "+referenceSequence+" for Student= "+studentNr + " / " + dbParam + " / " + ex);
			result = "Error "+queryType+" STUXML "+referenceType+" "+referenceSequence+" for Student= "+studentNr;
		}
		//log.debug("StudentStatusDAO - saveSTUXMLRef - "+studentNr+" - End");
		return result;
	}
	
	public String isSTUXML(String studentNr, String acaYear, String acaPeriod, String referenceType, String referenceValue, String callingMethod) {
		
		//log.debug("StudentStatusDAO - isSTUXML - "+studentNr+" - Start");
		
		String result = "False";  //Note: Not Boolean;
		
		//log.debug("StudentStatusDAO - isSTUXML - "+studentNr+" - Calling Method: " + callingMethod);

		String query = "select detail from STUXML where mk_student_nr = ? and mk_academic_year = ? and mk_academic_period = ? and reference_type = ? and reference_value = ?";

		try {
			//log.debug("StudentStatusDAO - isSTUXML - "+studentNr+" - Query: " + query);
			//log.debug("StudentStatusDAO - isSTUXML - "+studentNr+" - Query: " +studentNr+", "+acaYear+", "+acaPeriod+", "+referenceType+", "+referenceValue);
			
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, acaPeriod, referenceType, referenceValue});
			Iterator<?> i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				result = "True";
				//log.debug("StudentStatusDAO - isSTUXML - "+studentNr+" - Result: STUXML Exists="+data.get("detail").toString().trim());
			}else{
				result = "False";
				//log.debug("StudentStatusDAO - isSTUXML - "+studentNr+" - Result: No STUXML");
			}
			
		} catch (Exception ex) {
			//log.debug("StudentStatusDAO: Error Retrieving STUXML "+referenceType+" "+referenceValue+" for Student / "+studentNr + "/ " +ex);
			result = "Error Retrieving STUXML Category for Returning Student / "+studentNr;
		}
		//log.debug("StudentStatusDAO - isSTUXML - "+studentNr+" - "+callingMethod+" - Done");
		return result;
	}
	
	public String saveSTUXML(String studentNr, String acaYear, String acaPeriod, String referenceType, String referenceValue, String referenceSequence, String referenceData, String callingMethod, String queryType) {

		//log.debug("StudentStatusDAO - saveSTUXML - "+studentNr+" - Start");
		
		String query = "";
		String dbParam = "";
		int resultInt = 0;
		String result = "False";  //Note: Not Boolean;

		//log.debug("StudentStatusDAO - saveSTUXML - "+studentNr+" - referenceSequence="+referenceSequence+" - referenceData="+referenceData+", Calling Method: " + callingMethod);
		
		try{
			query  = "insert into STUXML (mk_student_nr,mk_academic_year,mk_academic_period,reference_type,reference_value,reference_sequence,date_initial,date_modified,detail) "
					+ " values (?, ?, ?, ?, ?, ?, systimestamp, systimestamp, ?)";

			dbParam = studentNr+","+acaYear+","+acaPeriod+","+referenceType+","+referenceValue+", "+referenceSequence+", "+referenceData;
			//log.debug("StudentStatusDAO - saveSTUXML - INSERT - Query="+query+" - "+studentNr+" - dbParam: "+dbParam);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			resultInt = jdt.update(query, new Object []{studentNr,acaYear,acaPeriod,referenceType,referenceValue,referenceSequence,referenceData});
			
			//log.debug("StudentStatusDAO - saveSTUXML - "+studentNr+" - QueryType="+queryType+", resultInt="+resultInt);
			
			if (resultInt > 0){
				result = "True";
			}
		} catch (Exception ex) {
			//log.debug("StudentStatusDAO: Error "+queryType+" "+referenceType+" "+referenceValue+" "+referenceSequence+" "+referenceData+" for Student= "+studentNr + " / " + dbParam + " / " + ex);
			log.info("StudentStatusDAO: Error "+queryType+" "+referenceType+" "+referenceValue+" "+referenceSequence+" "+referenceData+" for Student= "+studentNr + " / " + dbParam + " / " + ex);
			result = "Error "+queryType+" saveSTUXML "+referenceType+" "+referenceValue+" "+referenceSequence+" for Student= "+studentNr;
		}
		//log.debug("StudentStatusDAO - saveSTUXML - "+studentNr+" - End, Result="+result);
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

			//log.debug("getSTUAPQStatus - query="+query+", studentNr="+studentNr+", acaYear="+acaYear+", acaPeriod="+acaPeriod+", choice="+choice+", CallingMethod="+callingMethod);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, acaPeriod, choice});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				status = data.get("status_code").toString();
			}
		} catch (Exception ex) {
			throw new Exception(
					"StudentStatusDAO : Error reading STUAPQ status / " + ex);
		} finally {
			try {

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		//log.debug("getSTUAPQStatus - Status: " + status );
		return status;
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
					"StudentStatusDAO : Error validating Leap Year / " + ex);
		}
		return leapCheck;
	}
	

	  /************ Get Offer Reason *************/
	  public String getApplyStatus(String studentNr, String acaYear, String acaPeriod, String choice) throws Exception{

		  //log.debug("StudentStatusDAO - getApplyStatus studentNr: " + studentNr+", acaYear: " + acaYear+", acaPeriod: " + acaPeriod+", choice: " + choice);

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
	    	  
	    	  //log.debug("StudentStatusDAO getQualSubscription - getApplyStatus - Checked OverSubscribed - query1: " + query1+", choice="+choice+", acaYear="+acaYear+", acaPeriod="+acaPeriod+", studentNr="+studentNr);
	    	  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	    	  List queryList = jdt.queryForList(query1, new Object []{choice, acaYear, acaPeriod, studentNr});
	    	  Iterator i = queryList.iterator();
	    	  if (i.hasNext()) {
		    	  //log.debug("StudentStatusDAO getQualSubscription - getApplyStatus - studentNr="+studentNr+", - choice="+choice+" - IS Oversubscribed");
	    		  overSubscribed = true;
	    	  }else{
	    		  //log.debug("StudentStatusDAO getQualSubscription - getApplyStatus - studentNr="+studentNr+", - choice="+choice+" - IS NOT Oversubscribed");
	    	  }

			  String query2 = "select new_qual, status_code, admission_verified, docs_outstanding, "
					  	+ " COALESCE(space_offered, ' ') as space_offered,"
					  	+ " COALESCE(results_outstanding, ' ') as results_outstanding"
			  			+ " from stuapq "
	    			  	+ " where choice_nr = ? "
	    			  	+ " and academic_year = ? "
	    			  	+ " and application_period = ? "
	    			  	+ " and mk_student_nr = ? ";
	    			  		    	  
			  //log.debug("StudentStatusDAO getApplyStatus - Check Status - query2: " + query2+", choice="+choice+", acaYear="+acaYear+", acaPeriod="+acaPeriod+", studentNr="+studentNr);
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
	    		  
	    		  //log.debug("StudentStatusDAO getApplyStatus - Qualification="+qual+", choice="+choice+", status="+status+", verified="+verified+", docs="+docs+", space="+space);
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
	      					"StudentStatusDAO: Error occured on reading Application Status / "+ex);
	      	}
	  }
	  
	  public String getStatusDesc(String status) throws Exception{
	    	
	    	//log.debug("StudentStatusDAO - getStatusDesc - Status: "+status);

	    	String result = "";

	    	try{ 
	    		if (status.equalsIgnoreCase("NOAPP")){
	    			result = "No Application found for this academic year";
	    		}else{
		    		String query = "select info from gencod "
		    					+ " where code = ? "
		    					+ " and fk_gencatcode='264' "
		    					+ " and in_use_flag='Y' ";
	
		    		//log.debug("StudentStatusDAO - getStatusDesc - Query: " + query + ", Status: "+status);
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
	 	    	throw new Exception("StudentStatusDAO - getStatusDesc : Error reading status / " + ex);
	        }
	    	//log.debug("StudentStatusDAO - getStatusDesc - Final Status=" + result); 
	 	    return result;
	   }
	  
	  public String getDeclineReason(String StudentNr, String acaYear, String acaPeriod, String qualCode) throws Exception{
	    	
	    	//log.debug("StudentStatusDAO - getDeclineReason - StudentNr: "+StudentNr+", qualCode: "+qualCode);

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
	
		    		//log.debug("StudentStatusDAO - getDeclineReason - Query: " + query);
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
	 	    	throw new Exception("StudentStatusDAO - getDeclineReason : Error reading decline reason / " + ex);
	        }
	    	//log.debug("StudentStatusDAO - getDeclineReason - Decline Reseason=" + result); 
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
			//log.debug("StudentStatusDAO - getCountries - Query="+query);

			try {
				//log.debug("StudentStatusDAO - getStatusQual - qualSpec: " + qualSpec + ", query:" + query);
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, acaPeriod, choice});
				Iterator i = queryList.iterator();
				if (i.hasNext()) {
					while (i.hasNext()) {
						ListOrderedMap data = (ListOrderedMap) i.next();
							//log.debug("StudentStatusDAO - getStatusQual - Choice:" + choice);
							if ("Qual".equalsIgnoreCase(qualSpec)){
								result = data.get("new_qual").toString().trim() + " - " + data.get("QualLongDesc").toString().trim();
								//result = data.get("new_qual").toString().trim() + " - " + data.get("QualDesc").toString().trim() + " (" + data.get("QualLongDesc").toString().trim() + ")";
								//log.debug("StudentStatusDAO - getStatusQual - Qual:" + result);
							}else if ("Spec".equalsIgnoreCase(qualSpec)){
								if (data.get("new_spes").toString().trim() != null && !"".equalsIgnoreCase(data.get("new_spes").toString().trim()) && !"0".equalsIgnoreCase(data.get("new_spes").toString().trim())){
									result = data.get("new_spes").toString().trim() + " - " + data.get("SpecDesc").toString().trim();
								}else{
									result = "N/A - Not Applicable";
								}
								//log.debug("StudentStatusDAO - getStatusQual - Spec:" + result);
							}
					}
				}else{
					result = "Not Found";
				}
			} catch (Exception ex) {
				throw new Exception(
						"StudentStatusDAO : Error retrieving student saved quals / " + ex);
			}
			return result;
		}
		
		  public Status getStatusFee(String StudentNr, String acaYear, String acaPeriod) throws Exception{
			  
			  Status status = new Status();

			  try{ 
				  String query = "select STUAPP.FEE_PAID, TO_CHAR(MAX(STSXTN.BUNDLE_DATE), 'DD-MM-YYYY') as PAYDATE, sum(STSXTN.AMOUNT) as AMOUNT "
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
	 	    	throw new Exception("StudentStatusDAO - getStatusFee : Error Student Payment / " + ex);
	        }
			
			//Debug
	    	//log.debug("StudentStatusDAO - getStatusFee - PayDate="+status.getPayDate());
	    	//log.debug("StudentStatusDAO - getStatusFee - PayFee ="+status.getPayFee());
	    	//log.debug("StudentStatusDAO - getStatusFee - PayFull="+status.isPayFull());
	    	//log.debug("StudentStatusDAO - getStatusFee - PayCom ="+status.getPayComment());
	    	
	 	    return status;
	   }
		  
		  public ScreeningVenue getScreeningVenue(String StudentNr) throws Exception{
			  
			  ScreeningVenue venue = new ScreeningVenue();

			  try{ 
				  String query = "select LOK.CODE, LOK.ENG_NAME," + 
				  		" COALESCE(ADR.ADDRESS_LINE_1,' ') as ADDRESS1, COALESCE(ADR.ADDRESS_LINE_2,' ') as ADDRESS2," +
						" COALESCE(ADR.ADDRESS_LINE_3,' ') as ADDRESS3 ,COALESCE(ADR.ADDRESS_LINE_4,' ') as ADDRESS4," + 
				  		" COALESCE(ADR.ADDRESS_LINE_5,' ') as ADDRESS5 ,COALESCE(ADR.ADDRESS_LINE_6,' ') as ADDRESS6, LPAD(ADR.POSTAL_CODE,4,'0') as POSTCODE" + 
				  		" from stuxct,xamloc,lok,adr" + 
				  		" where STUXCT.MK_STUDENT_NR=?" + 
				  		" and STUXCT.MK_EXAM_PERIOD_COD=10" + 
				  		" and STUXCT.MK_EXAM_PERIOD_COD=XAMLOC.MK_XAMPRDCODE" + 
				  		" and XAMLOC.MK_EKVTYPECODE=1" + 
				  		" and XAMLOC.MK_EKSCODE=STUXCT.MK_EXAM_CENTRE_COD " + 
				  		" and LOK.CODE=XAMLOC.MK_LOKCODE " + 
				  		" and ADR.REFERENCE_NO=LOK.MK_ADDRESS_REF_NO " + 
				  		" and fk_adrcatypfk_adrc=2" + 
				  		" and fk_adrcatypfk_adrt=3";
				  
			 		//log.debug("ApplyForStudentNumberQueryDAO - getStatusFee - Query=" + query+", Current Year=" + cYear+", acaYear=" + aYear+", FROM="+from_Month+"/"+from_year+", TO="+to_Month+"/"+to_year);
					JdbcTemplate jdt = new JdbcTemplate(getDataSource());
					List queryList = jdt.queryForList(query, new Object []{StudentNr});
					Iterator i = queryList.iterator();
					if (i.hasNext()) {
						ListOrderedMap data = (ListOrderedMap) i.next();
						venue.setVenueCode(data.get("CODE").toString().trim());
						venue.setVenueName(data.get("ENG_NAME").toString().trim());
						ArrayList<String> address = new ArrayList<String>();
						if (!data.get("ADDRESS1").toString().trim().equalsIgnoreCase("")) {
							address.add(data.get("ADDRESS1").toString().trim());
						}
						if (!data.get("ADDRESS2").toString().trim().equalsIgnoreCase("")) {
							address.add(data.get("ADDRESS2").toString().trim());
						}
						if (!data.get("ADDRESS3").toString().trim().equalsIgnoreCase("")) {
							address.add(data.get("ADDRESS3").toString().trim());
						}
						if (!data.get("ADDRESS4").toString().trim().equalsIgnoreCase("")) {
							address.add(data.get("ADDRESS4").toString().trim());
						}
						if (!data.get("ADDRESS5").toString().trim().equalsIgnoreCase("")) {
							address.add(data.get("ADDRESS5").toString().trim());
						}
						if (!data.get("ADDRESS6").toString().trim().equalsIgnoreCase("")) {
							address.add(data.get("ADDRESS6").toString().trim());
						}	
						if (!data.get("POSTCODE").toString().trim().equalsIgnoreCase("")) {
							address.add(data.get("POSTCODE").toString().trim());
						}							
						
						venue.setAddressList(address);
						
					}
	 	    } catch (Exception ex) {
	 	    	throw new Exception("StudentStatusDAO - getScreeningVenue : Error Exam Venue - Social work screening venue / " + ex);
	        }
			
			//Debug
	    	//log.debug("StudentStatusDAO - getStatusFee - PayDate="+status.getPayDate());
	    	//log.debug("StudentStatusDAO - getStatusFee - PayFee ="+status.getPayFee());
	    	//log.debug("StudentStatusDAO - getStatusFee - PayFull="+status.isPayFull());
	    	//log.debug("StudentStatusDAO - getStatusFee - PayCom ="+status.getPayComment());
	    	
	 	    return venue;
	   }	
		  
	  /**
	 * Check if social work screening sitting must be included in status
	 */
	public boolean includeSWScreeningSitting(String studentNr, String acaYear, String acaPeriod, String qualCode) throws Exception {
				
				boolean includeSitting = false;
				//Check if student record exists by using Surname, First Names, Date of Birth
				
				String query  = "select * from stuapq" + 
						" where STUAPQ.ACADEMIC_YEAR=?" + 
						" and STUAPQ.APPLICATION_PERIOD=?" + 
						" and STUAPQ.MK_STUDENT_NR=?" + 
						" and STUAPQ.NEW_QUAL=?" + 
						" and ((STUAPQ.STATUS_CODE in ('CG','RO')) or (STUAPQ.STATUS_CODE='AP' and STUAPQ.ADMISSION_AUTO_CHK ='WA'))" + 
						" and exists (select * from stuapl where STUAPL.ACADEMIC_YEAR=STUAPQ.ACADEMIC_YEAR and STUAPL.APPLICATION_PERIOD=STUAPQ.APPLICATION_PERIOD" + 
						" and STUAPL.MK_STUDENT_NUMBER=STUAPQ.MK_STUDENT_NR and STUAPL.QUAL_CODE=STUAPQ.NEW_QUAL and STUAPL.ACTION_CODE_GC272='LETSOC')";			
				
				try {
					JdbcTemplate jdt = new JdbcTemplate(getDataSource());
					List queryList = jdt.queryForList(query, new Object []{acaYear, acaPeriod, studentNr, qualCode});
					
					Iterator i = queryList.iterator();
					if (i.hasNext()) {
						ListOrderedMap data = (ListOrderedMap) i.next();
						includeSitting=true;
					}
				} catch (Exception ex) {
					throw new Exception(
							"StudentStatusDAO : Error check to include Social Work screening sitting / " + ex);
				}			
				return includeSitting;
			}	  

}
