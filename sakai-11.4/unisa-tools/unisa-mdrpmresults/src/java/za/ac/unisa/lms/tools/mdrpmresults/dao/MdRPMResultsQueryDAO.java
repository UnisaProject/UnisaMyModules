package za.ac.unisa.lms.tools.mdrpmresults.dao;

import java.sql.Timestamp;
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
import za.ac.unisa.lms.tools.mdrpmresults.Constants;
import za.ac.unisa.lms.tools.mdrpmresults.MdRPMResultsBr;
import za.ac.unisa.lms.tools.mdrpmresults.forms.AddressPH;
import za.ac.unisa.lms.tools.mdrpmresults.forms.Qualification;
import za.ac.unisa.lms.tools.mdrpmresults.forms.Staff;
import za.ac.unisa.lms.tools.mdrpmresults.forms.Student;
import za.ac.unisa.lms.tools.mdrpmresults.forms.StudyUnit;

public class MdRPMResultsQueryDAO extends StudentSystemDAO {

	public static Log log = LogFactory.getLog(MdRPMResultsQueryDAO.class);

	/**
	 * This method return the logged in user's personal and routing details
	 * 
	 * @param networkCode
	 * 		  The logged in user's net work code
	 * @return
	 * 		Staff object - Logged in user detials
	 * @throws Exception
	 */
	public Staff getStaffRoutDet(String networkCode) throws Exception{
		log.debug("get staff from network code");
		// Return staff name
		Staff staff = new Staff();

		String query = "SELECT staff.persno, staff.contact_telno, " +
							"staff.email_address, " +
							"staff.title || ' ' || staff.initials ||' ' || staff.surname as name, " +
							"qsprout.final_flag " +
						"FROM staff, qsprout " +
						"WHERE staff.novell_user_id = ? and staff.PERSNO = qsprout.STAFF_NUMBER " ;
		log.debug(query);

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(query, new Object []{networkCode.toUpperCase()});

		Iterator i = queryList.iterator();
		if (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			if(data.get("EMAIL_ADDRESS")!= null){
				staff.setEmailAddress(data.get("EMAIL_ADDRESS").toString().trim());
			}
			if(data.get("CONTACT_TELNO")!= null){
				staff.setTelephone(data.get("CONTACT_TELNO").toString().trim());
			}
			staff.setName(data.get("NAME").toString().trim());
			staff.setStaffNr(data.get("PERSNO").toString().trim());
			staff.setFinalFlag(data.get("FINAL_FLAG").toString());
		}

		log.debug("get staff from network code END");

		return staff;
	}
	
	public Student getStudentRecord(String sudentNumber, 
									String academicYear,
									String statusCode, 
									String rpmFlag,
									String adrCatCode)
	{
		
		log.debug("Begin; Student record retrieval");
		
		Student student = null;
		
		String query = "SELECT stu.mk_title, stu.surname,  stu.initials, mdappl.mk_qualification_c, " +
							  "grd.LONG_ENG_DESCRIPTI as qualificationDescr, stusun.mk_study_unit_code, " +
							  "sun.eng_long_descripti as studyUnitDescr, adrph.cell_number, " +
							  "adrph.email_address, adrph.home_number, mdappl.app_sequence_nr " +
					   "FROM mdappl, stusun, stu , sun, grd, adrph " +
					   "WHERE mdappl.mk_student_nr = ?  and " +
					   		 "mdappl.mk_student_nr = stusun.fk_student_nr and " +
					   		 "stu.nr = mdappl.mk_student_nr and " +
					   		 "stusun.fk_academic_year = ? and " +
					   		 "stusun.status_code = ? and " +
					   		 "stusun.mk_study_unit_code = sun.code and " +
					   		 "sun.research_flag = ? and " +
							 "grd.code = mdappl.MK_QUALIFICATION_C and " +
							 "reference_no = mdappl.mk_student_nr and " +
							 "FK_ADRCATCODE = ?" ;
		log.debug(query);
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		Object [] params = {sudentNumber, academicYear, statusCode, rpmFlag, adrCatCode};
		List queryList = jdt.queryForList(query, params);

		Iterator i = queryList.iterator();
		if (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			student = new Student();
			
			String name = "";
			if(data.get("MK_TITLE") != null)
			{
				name = data.get("MK_TITLE").toString() + ' ';
			}
			
			if(data.get("INITIALS") != null)
			{
				name = name + data.get("INITIALS").toString() + ' ';
			}
			
			if(data.get("SURNAME") != null)
			{
				name = name + data.get("SURNAME").toString();
			}
			
			student.setName(name);
			
			Qualification qualification = student.getQualification();
			
			if (data.get("MK_QUALIFICATION_C") != null){
				qualification.setQualCode(data.get("MK_QUALIFICATION_C").toString());
			}
			if (data.get("QUALIFICATIONDESCR") != null){
				qualification.setQualDesc(data.get("QUALIFICATIONDESCR").toString());
			}
			
			StudyUnit studyUnit = student.getStudyUnit();
			
			if  (data.get("MK_STUDY_UNIT_CODE") != null){
				studyUnit.setCode((data.get("MK_STUDY_UNIT_CODE").toString()));
			}

			if  (data.get("STUDYUNITDESCR") != null){
				studyUnit.setDescription(data.get("STUDYUNITDESCR").toString());
			}
			
			AddressPH adrph = student.getAddressPH();
			
			if (data.get("HOME_NUMBER") != null)
			{
				adrph.setHomeNumber(data.get("HOME_NUMBER").toString());
			}
			
			if (data.get("CELL_NUMBER") != null)
			{
				adrph.setCellNumber(data.get("CELL_NUMBER").toString());
			}
			
			if (data.get("EMAIL_ADDRESS") != null)
			{
				adrph.setEmail(data.get("EMAIL_ADDRESS").toString());
			}		
			
			student.setApplSequenceNr(data.get("APP_SEQUENCE_NR").toString());
		}

		return student;
	}
	
	/**
	 * This method retrives supervisors linked to the student proposal module
	 * @param studentNumber
	 * 		  The student to be processed
	 * @param studyUnitCode
	 * 		  The student study unit
	 * @return
	 * 		Staff oject -contains supervisor detials
	 */
	public ArrayList<Staff> getSupervisorList(
								String studentNumber, 
								String studyUnitCode)
	{
		log.debug("Start get supervisor query");
		
		Staff supervisor 			 = null;
		ArrayList<Staff> supervisors = null;
		
		String query ="SELECT MK_PROMOTOR_NR, SUPERVISOR_FLAG, EMAIL_ADDRESS, " +
							"CONTACT_TELNO , PERSNO, " +
							"title || ' ' || initials ||' ' || surname as name " +
					  "FROM dispro, staff " +
					  "WHERE fk_student_nr = ?  and " +
					  		"fk_study_unit_code = ? and " +
					  		"mk_promotor_nr = staff.persno";
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		
		Object [] params = {studentNumber, studyUnitCode};
		List queryList = jdt.queryForList(query, params);
		
		Iterator it = queryList.iterator();
		
		while(it.hasNext())
		{
			ListOrderedMap data = (ListOrderedMap) it.next();
			supervisors 		= new ArrayList<Staff>();
			supervisor 			= new Staff();
			
			supervisor.setStaffNr(data.get("MK_PROMOTOR_NR").toString());   
			supervisor.setIsSupervisor(data.get("SUPERVISOR_FLAG").toString());
			
			if(data.get("EMAIL_ADDRESS")!= null){
				supervisor.setEmailAddress(
						data.get("EMAIL_ADDRESS").toString().trim());
			}
			
			if(data.get("CONTACT_TELNO")!= null){
				supervisor.setTelephone(
						data.get("CONTACT_TELNO").toString().trim());
			}

			supervisor.setName(data.get("NAME").toString().trim());
			supervisor.setStaffNr(data.get("PERSNO").toString().trim());
			
			supervisors.add(supervisor);
		}
		
		log.debug("End get supervisor query");
		return supervisors;
	}

	/**
	 * Checks if sign-off record exists for the current student 
	 * @param studentNumber
	 * 			The student
	 * @param appSequenceNr
	 * 			The application sequence number
	 * @return
	 * 		<code>true</code> OR
	 * 		<code>false</code>
	 */
	public boolean signOffRecordExists(String studentNumber, String appSequenceNr)
	{
		String query ="SELECT * FROM mdtrac " +
					  "WHERE mk_student_nr = ? and " +
					  		"app_sequence_nr = ? and " +
					  		"status_code in (?,?,?,?)";
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		Object [] params = {studentNumber, appSequenceNr, 
							Constants.COMPLIED_RESULTS_CODE,
							Constants.NOT_COMPLIED_RESULTS_CODE,
							Constants.FAILED_RESULTS_CODE,
							Constants.NOACTIVITY_RESULTS_CODE};
		List queryList = jdt.queryForList(query, params);
		
		Iterator it = queryList.iterator();

		while(it.hasNext())
		{
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * Checks if sign-off record exists for the current student 
	 * @param studentNumber
	 * 			The student
	 * @param appSequenceNr
	 * 			The application sequence number
	 * @return
	 * 		<code>true</code> OR
	 * 		<code>false</code>
	 */
	public boolean getLatestSignOffRecord(String studentNumber, String appSequenceNr)
	{
		String query ="SELECT * FROM mdtrac " +
					  "WHERE mk_student_nr = ? and " +
					  		"app_sequence_nr = ? and " +
					  		"status_code in (?,?,?,?)";
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		Object [] params = {studentNumber, appSequenceNr, 
							Constants.COMPLIED_RESULTS_CODE,
							Constants.NOT_COMPLIED_RESULTS_CODE,
							Constants.FAILED_RESULTS_CODE,
							Constants.NOACTIVITY_RESULTS_CODE};
		List queryList = jdt.queryForList(query, params);
		
		Iterator it = queryList.iterator();

		while(it.hasNext())
		{
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * Retrieves routing record for the given staff number and qualification
	 * 
	 * @param staffNr
	 * 		The logged-in staff number
	 * @param qualification
	 * 		The student qualification
	 * @return
	 * 		The routing staff record
	 */
	public Staff getRoutingRecord(String staffNr, Qualification qualification)
	{
		Staff staff = null;
		
		String query ="SELECT * FROM qsprout " +
					  "WHERE staff_number = ? and " +
					  		"mk_qual_code = ? and " +
					  		"mk_spes_code =  ?";

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		Object [] params = {staffNr, 
							qualification.getQualCode(), 
							qualification.getSpecCode()};
		
		List queryList = jdt.queryForList(query, params);
		
		Iterator it = queryList.iterator();
		
		while(it.hasNext())
		{
			ListOrderedMap data = (ListOrderedMap) it.next();
			staff = new Staff ();
			staff.setStaffNr(data.get("STAFF_NUMBER").toString());   
			staff.setFinalFlag(data.get("FINAL_FLAG").toString());    
		}
		return staff;
		
	}
	
	/**
	 * Retrieves routing list for the specified qualification
	 * @param qualification
	 * 		The student qualification
	 * @return
	 * 		Staff list
	 */
	public ArrayList<Staff> getRoutingList(Qualification qualification)
	{
		ArrayList<Staff> staffList = new ArrayList<Staff>();
		Staff staff = null;
		
		String query ="SELECT * FROM staff, qsprout " +
					  "WHERE staff.persno = qsprout.staff_number and " +
					  "qsprout.mk_qual_code = ? and " +
					  "qsprout.mk_spes_code =  ?";

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		Object [] params = {qualification.getQualCode(), 
							qualification.getSpecCode()};
		
		List queryList = jdt.queryForList(query, params);
		
		Iterator it = queryList.iterator();
		
		while(it.hasNext())
		{
			ListOrderedMap data = (ListOrderedMap) it.next();
			staff = new Staff ();
			staff.setStaffNr(data.get("STAFF_NUMBER").toString());  
			String name = data.get("TITLE").toString() + ' ' +
						  data.get("INITIALS").toString() + ' ' + 
						  data.get("SURNAME");			
			staff.setName(name);
			staff.setFinalFlag(data.get("FINAL_FLAG").toString()); 
			
			staffList.add(staff);
		}
		return staffList;
	}
	
	/**
	 * Retrieves Sign-off list for the specified qualification
	 * @param qualification
	 * 		The student qualification
	 * @param studentNumber
	 * 		The student number
	 * @param appSequenceNr
	 * 		The student record application sequence number
	 * @return
	 * 		Staff list
	 */
	public ArrayList<Staff> getSignoffList(
								Qualification qualification, 
								String studentNumber, 
								String appSequenceNr)
	{
		ArrayList<Staff> staffList = new ArrayList<Staff>();
		Staff staff = null;
		String query ="SELECT * FROM staff, qsprout , mdtrac " +
					  "WHERE staff.persno = qsprout.staff_number and " +  
					  		 "qsprout.mk_qual_code = ? and " + 
					  		 "qsprout.mk_spes_code =  ? and " + 
					  		 "qsprout.staff_number = mdtrac.PERS_NR and " +
					  		 "mdtrac.mk_student_nr = ? and " + 
					  		 "mdtrac.app_sequence_nr = ?" ;

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		Object [] params = {qualification.getQualCode(), 
							qualification.getSpecCode(),
							studentNumber, 
							appSequenceNr};
		
		List queryList = jdt.queryForList(query, params);
		
		Iterator it = queryList.iterator();
		
		while(it.hasNext())
		{
			ListOrderedMap data = (ListOrderedMap) it.next();
			staff = new Staff ();
			staff.setStaffNr(data.get("STAFF_NUMBER").toString());  
			String name = data.get("TITLE").toString() + ' ' +
						  data.get("INITIALS").toString() + ' ' + 
						  data.get("SURNAME");			
			staff.setName(name);
			staff.setFinalFlag(data.get("FINAL_FLAG").toString()); 
			
			if (data.get("EMAIL_ADDRESS") != null){				
				staff.setEmailAddress(data.get("EMAIL_ADDRESS").toString());
			}
			
			if (data.get("STATUS_CODE") != null)
			{
				staff.setSignOffStatus(MdRPMResultsBr.getSignOffResultDesc(
											data.get("STATUS_CODE").toString()));
			}
			
			if (data.get("COMMENTS") != null){				
				staff.setEmailAddress(data.get("COMMENTS").toString());
			}
			
			staffList.add(staff);
		}
		return staffList;
	}
	
	/**
	 * This method retrieves the previous sign off record for the specified staff
	 * @param staffNr
	 * 		  The staff number
	 * @param qualification
	 * 		  The student qualification
	 * @param studentNumber
	 * 			The student number
	 * @param appSequenceNr
	 * 		The application sequence number
	 * @return
	 * 		Staff sign-off record 
	 */
	public Staff getPrevSignOffRecord(
							String staffNr,
							Qualification qualification, 
							String studentNumber, 
							String appSequenceNr)
	{
		Staff staff = null;
		String query ="SELECT * FROM staff, qsprout , mdtrac " +
					  "WHERE staff.persno = qsprout.staff_number and " +  
					  		 "staff.persno = ? and " +
					  		 "qsprout.mk_qual_code = ? and " + 
					  		 "qsprout.mk_spes_code =  ? and " + 
					  		 "qsprout.staff_number = mdtrac.PERS_NR and " +
					  		 "mdtrac.mk_student_nr = ? and " + 
					  		 "mdtrac.app_sequence_nr = ?"  +
					  "ORDER BY mdtrac.timestamp desc ";

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		Object [] params = {staffNr,
							qualification.getQualCode(), 
							qualification.getSpecCode(),
							studentNumber, 
							appSequenceNr};
		
		List queryList = jdt.queryForList(query, params);
		Iterator it = queryList.iterator();
		
		if(it.hasNext())
		{
			ListOrderedMap data = (ListOrderedMap) queryList.get(0);

			staff = new Staff ();
			
			staff.setStaffNr(data.get("STAFF_NUMBER").toString());  
			String name = data.get("TITLE").toString() + ' ' +
						  data.get("INITIALS").toString() + ' ' + 
						  data.get("SURNAME");			
			staff.setName(name);
			staff.setFinalFlag(data.get("FINAL_FLAG").toString()); 
			
			if (data.get("EMAIL_ADDRESS") != null){				
				staff.setEmailAddress(data.get("EMAIL_ADDRESS").toString());
			}
			
			if (data.get("STATUS_CODE") != null)
			{
				staff.setSignOffStatus(MdRPMResultsBr.getSignOffResultDesc(
											data.get("STATUS_CODE").toString()));
			}
			
			if (data.get("COMMENTS") != null){				
				staff.setEmailAddress(data.get("COMMENTS").toString());
			}		
		}
		return staff;
	}
	
	public String getTitle(
			String studentNumber, 
			String studyUnitCode)
	{
		log.debug("Start get getTitle query");
		
		String title = null;
		
		String query ="SELECT title " +
		  "FROM studis " +
		  "WHERE mk_student_nr = ?  and " +
		  		"mk_study_unit_code = ? ";
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		
		Object [] params = {studentNumber, studyUnitCode};
		List queryList = jdt.queryForList(query, params);
		
		Iterator it = queryList.iterator();
		
		while(it.hasNext())
		{
			ListOrderedMap data = (ListOrderedMap) it.next();
			
			title = data.get("TITLE").toString();   
		}
		
		log.debug("End get Title query");
		return title;
	}

	/**
	 * This method inserts a record into mdactv table
	 * 
	 * @param studentNumber
	 * 		The student number
	 * @param studyUnitCode
	 * 		The study unit code
	 * @param staffNr
	 * 		The logged in staff number
	 * @param activityCode
	 * 		The activity code
	 * @param comment
	 * 		The comment entered
	 * @throws Exception
	 */
	public void insertMDActivity(String studentNumber, String studyUnitCode,
								 String staffNr, String activityCode,
								 String comment) 
	throws Exception
	{
		// TODO Auto-generated method stub
		log.debug("Start insertMDActivity query");
		
		String query ="INSERT INTO mdactv " +
					  "VALUES(?,?,?,?,?,to_date(?,'YYYY-MM-DD'),to_date(?,'YYYY-MM-DD'),?)";
		
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		Timestamp entryTimestamp = new Timestamp(now.getTime());
		
		String todayDate = (new java.text.SimpleDateFormat("yyyy-MM-dd").format(now).toString());
		
		Object [] params = {studentNumber, studyUnitCode, staffNr, activityCode, 
							entryTimestamp, todayDate, todayDate, comment};

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		
		jdt.update(query, params);
		
		log.debug("End insertMDActivity query");
		
	}
	
	/**
	 * 	This method updates the studies approved flag on the student record
	 * 
	 * @param studentNumber
	 * 		The student number
	 * @param isStudiesApproved
	 * 		<code>true</code> OR
	 * 		<code>false</code>
	 */
	public void updateStudiesApprovedFlag(String studentNumber, String isStudiesApproved)
	{
		log.debug("Start updateStudiesApprovedFlag query");
		
		String query ="update stu set post_graduate_stud = ? where nr = ";
		
		Object [] params = {studentNumber, isStudiesApproved};

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		
		jdt.update(query, params);
		
		log.debug("End updateStudiesApprovedFlag query");
	}
	
	/**
	 * This method inserts a sign-off(mdtrac) record
	 * 
	 * @param statusCode
	 * 		The sign-off status code
	 * @param appSequenceNr
	 * 		The application sequence number
	 * @param studentNr
	 * 		The student number
	 * @param staff
	 * 		The logged-in staff record
	 * @throws NumberFormatException
	 * @throws InterruptedException
	 */
	public void writeSignOff(String statusCode,
							 String appSequenceNr, 
							 String studentNr, 
							 Staff staff) 
	throws NumberFormatException, InterruptedException{
		
		Calendar cal = Calendar.getInstance();
		java.util.Date now = cal.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		
		String 	sql = "insert into mdtrac (mk_student_nr, app_sequence_nr,timestamp, status_code,pers_nr,comments) values (?, ?, ?, ?, ?, ?)";
		log.debug(sql);
		Thread.sleep(Long.parseLong("1000"));
		jdt.update(sql,new Object[] { studentNr,appSequenceNr,currentTimestamp,statusCode,staff.getStaffNr(),""});
		
	}
}
