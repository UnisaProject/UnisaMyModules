package za.ac.unisa.lms.services.calendar.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.collections.map.ListOrderedMap;
import org.sakaiproject.event.cover.EventTrackingService;
import org.sakaiproject.util.StringUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.utils.CoursePeriodLookup;
import Saual60h.Abean.Saual60sGetUnqass;
import Wasql01h.Abean.Wasql01sGetSql;

public class calendarDateStudentSystemQueryDAO extends StudentSystemDAO {

	private Log log = LogFactory.getLog(calendarDateStudentSystemQueryDAO.class.getName());
	private long timeInitialMillis = 0;
	private long timeExecuteMillis = 0;
	private long timeEndMillis = 0;
	private final String DEADLINE = "RGVhZGxpbmU="; // event code for a deadline, used for assignment deadlines
	private final String EXAM = "RXhhbQ==";  // event code for an exam
	private final String SPECIAL_EVENT = "U3BlY2lhbCBldmVudA==";
	protected static final String[] FIELDS = { "EVENT_START", "EVENT_END" };

	private class operListener implements java.awt.event.ActionListener {
		private Exception exception = null;

		operListener() {
			exception = null;
		}

		public Exception getException() {
			return exception;
		}

		public void actionPerformed(java.awt.event.ActionEvent aEvent) {
			exception = new Exception(aEvent.getActionCommand());
		}
	}

	/**
	 * Method: getExamInformation
	 * 		to select the exam dates for a student
	 * @param studentNr
	 * @param courseCycle = <course code>=<unisa Pretoria cycle code>-<unisa Florida cycle>) (ANH101P-06-S1)
	 * @param isStudent: true = student; false = lecturer
	 * @return list of all the exam information for a student number and site/course
	 * @throws Exception
	 */
	public ArrayList getExamInformationList(String studentNr, String courseCycle, boolean isStudent) throws Exception{
		
		boolean isReExam = false;
		String tmpStudyUnit = courseCycle.substring(0,7);
		String tmpYear = courseCycle.substring(8,10);
		String tmpCycle = courseCycle.substring(11,13);
		short acadSemester = CoursePeriodLookup.getPeriodTypeAsString(tmpCycle);
		int acadYear = CoursePeriodLookup.getYearTypeAsString(tmpYear);
		String acadYearString = Integer.toString(acadYear);
		String acadSemString = Integer.toString(acadSemester);
		ArrayList examEventList = new ArrayList();
		try
		{
		
		String examSqlId = "";
		if (isStudent == true){
			examSqlId = "SELECT stusun.mk_study_unit_code AS SU, xamdat.fk_nr AS Paper_No,"+
                        " TO_CHAR(date0,'YYYY') AS XYear, "+
                        " TO_CHAR(date0,'MM') AS XMonth, "+ 
                        " TO_CHAR(date0,'DD') AS XDay, "+
                        " TO_CHAR(starting_time,'HH24') AS XHour,"+
                        " TO_CHAR(starting_time,'MI') AS XMinute,"+
                        " xamdat.duration_days ||' Days' AS Xdays,"+
                        " xamdat.duration_hours ||' Hours' AS Xhours, "+
                        " xamdat.duration_Minutes||' Minutes' AS Xminutes , eng_description as ExCentre, 'Final date'"+ 
                        " FROM stusun, xamdat,xampap, stuxct, eks"+
                        " WHERE fk_student_nr = "+studentNr+
                        " AND xampap.exam_year = stusun.exam_year"+ 
                        " AND xampap.mk_study_unit_code = stusun.mk_study_unit_code"+
                        " AND xamdat.fk_exam_year = xampap.exam_year"+    
                        " AND xamdat.mk_exam_period_cod = stusun.mk_exam_period"+
                        " AND xamdat.fk_study_unit_code = xampap.mk_study_unit_code"+
                        " AND xamdat.fk_nr = xampap.nr"+
                        " AND exam_admission_cod IN (2,3,4,5)"+ 
                        " AND status_code IN ('RG','FC') "+
                        " AND TO_CHAR(date0,'YYYYMMDD') > ('19999999')"+ 
                        " and stuxct.mk_student_nr = stusun.fk_student_nr"+
                        " and stuxct.mk_exam_period_cod = stusun.mk_exam_period"+
                        " and eks.code = stuxct.mk_exam_centre_cod"+
                        " AND EXISTS "+
                        " ( SELECT * FROM xaddon "+
                        " WHERE xaddon.mk_exam_year = stusun.exam_year"+ 
                        " AND xaddon.mk_exam_period = stusun.mk_exam_period"+ 
                        " AND admission_done_flag = 'Y' "+
                        " AND xaddon.mk_exam_type_code  = xampap.mk_exam_type_code )"+ 
                        " ORDER BY  TO_CHAR(date0,'YYYYMMDD'), TO_CHAR(starting_time,'HH24:MI'),stusun.mk_study_unit_code";
		} else {
			examSqlId = "SELECT sunpdt.fk_suncode AS SU, xamdat.fk_nr AS Paper_No,"+
                        " TO_CHAR(date0,'YYYY') AS XYear,"+ 
                        " TO_CHAR(date0,'MM') AS XMonth,"+  
                        " TO_CHAR(date0,'DD') AS XDay, "+
                        " TO_CHAR(starting_time,'HH24') AS XHour,"+ 
                        " TO_CHAR(starting_time,'MI') AS XMinute, "+
                        " xamdat.duration_days ||' Days' AS Xdays, "+
                        " xamdat.duration_hours ||' Hours' AS Xhours, "+
                        " xamdat.duration_Minutes||' Minutes' AS Xminutes , "+
                        " 'Final Timetable'"+
                        " FROM sunpdt, xamdat,xampap"+
                        " WHERE sunpdt.mk_academic_year ="+acadYearString+
                        " AND sunpdt.mk_academic_period = 1"+
                        " AND sunpdt.semester_period = "+acadSemString+
                        " AND sunpdt.fk_suncode = "+"'"+tmpStudyUnit+"'"+
                        " AND registration_allow = 'Y' "+ 
                        " AND xampap.exam_year = sunpdt.mk_exam_year "+
                        " AND xampap.mk_study_unit_code = sunpdt.fk_suncode"+
                        " AND xamdat.fk_exam_year = xampap.exam_year "+   
                        " AND xamdat.mk_exam_period_cod = sunpdt.mk_exam_period"+
                        " AND xamdat.fk_study_unit_code = xampap.mk_study_unit_code"+
                        " AND xamdat.fk_nr = xampap.nr"+
                        " AND TO_CHAR(date0,'YYYYMMDD') > ('19999999')"+
                        " AND EXISTS"+
                        " ( SELECT * FROM xaddon "+
                        " WHERE xaddon.mk_exam_year= xampap.exam_year "+
                        " AND xaddon.mk_exam_period= xamdat.mk_exam_period_cod "+
                        " AND xaddon.mk_exam_type_code= xampap.mk_exam_type_code"+
                        " AND xaddon.admission_done_flag= 'Y' ) "+  
                        " ORDER BY  TO_CHAR(date0,'YYYYMMDD'), TO_CHAR(starting_time,'HH24:MI'),sunpdt.fk_suncode";
		}

		/* 1. Normal Exam: Final dates */
		String examType = "Final exam";
		
		if(examSqlId != ""){
			ArrayList tmpExamEventList1 = new ArrayList();
			tmpExamEventList1 = executeExamSelect(examSqlId,isStudent,studentNr,acadYearString,
					acadSemString,tmpStudyUnit,courseCycle,examType,isReExam);

			// read through exam the list
			for (int i=0; i <tmpExamEventList1.size(); i++) {
				//Resource entry = readResource(calendar,tmpExamEventList1.get(i).toString());
	            examEventList.add(tmpExamEventList1.get(i));
			}

		}
		
	    /* 2. Normal Exam: Provisional dates */
		String examSqlId2 = "";
		if (isStudent == true){
			examSqlId2 = "SELECT stusun.mk_study_unit_code AS SU,"+ 
                         " xamdat.fk_nr AS Paper_No,"+
                         " TO_CHAR(date0,'YYYY') AS XYear, "+
                         " TO_CHAR(date0,'MM') AS XMonth,"+  
                         " TO_CHAR(date0,'DD') AS XDay,"+ 
                         " TO_CHAR(starting_time,'HH24') AS XHour,"+
                         " TO_CHAR(starting_time,'MI') AS XMinute, "+
                         " xamdat.duration_days ||' Days' AS Xdays, "+
                         " xamdat.duration_hours ||' Hours' AS Xhours,"+ 
                         " xamdat.duration_Minutes||' Minutes' AS Xminutes ,"+ 
                         " eks.eng_description as ExCentre,"+
                         " 'Preliminary date' "+
                         " FROM stusun, xamdat,xampap ,stuxct, eks"+
                         " WHERE fk_student_nr = "+studentNr+
                         " AND xampap.exam_year = stusun.exam_year "+
                         " AND xampap.mk_study_unit_code = stusun.mk_study_unit_code"+
                         " AND xamdat.fk_exam_year = xampap.exam_year "+
                         " AND xamdat.mk_exam_period_cod = stusun.mk_exam_period"+
                         " AND xamdat.fk_study_unit_code = xampap.mk_study_unit_code"+
                         " AND xamdat.fk_nr = xampap.nr"+
                         " AND exam_admission_cod IN (0,2,3,4,5)"+ 
                         " AND status_code IN ('RG','FC') "+
                         " AND TO_CHAR(date0,'YYYYMMDD') > ('19999999') "+
                         " AND stusun.fk_student_nr = stuxct.mk_student_nr"+
                         " AND stusun.mk_exam_period = stuxct.mk_exam_period_cod"+
                         " and stuxct.mk_exam_centre_cod = eks.code"+
                         " AND NOT EXISTS "+
                         " ( SELECT * FROM xaddon "+
                         " WHERE xaddon.mk_exam_year = stusun.exam_year "+
                         " AND xaddon.mk_exam_period = stusun.mk_exam_period"+ 
                         " AND admission_done_flag = 'Y' "+
                         " AND xaddon.mk_exam_type_code  = xampap.mk_exam_type_code ) "+
                         " ORDER BY  TO_CHAR(date0,'YYYYMMDD'), TO_CHAR(starting_time,'HH24:MI'),stusun.mk_study_unit_code";
		} else {
			//examSqlId2 = "736828";
			examSqlId2 =" SELECT sunpdt.fk_suncode AS SU, xamdat.fk_nr AS Paper_No,"+
					    " TO_CHAR(date0,'YYYY') AS XYear, "+
					    " TO_CHAR(date0,'MM') AS XMonth, "+ 
					    " TO_CHAR(date0,'DD') AS XDay, "+
					    " TO_CHAR(starting_time,'HH24') AS XHour, "+
					    " TO_CHAR(starting_time,'MI') AS XMinute, "+
					    " xamdat.duration_days ||' Days' AS Xdays, "+
					    " xamdat.duration_hours ||' Hours' AS Xhours, "+
					    " xamdat.duration_Minutes||' Minutes' AS Xminutes ,"+ 
					    " 'Preliminary Timetable' "+
					    " FROM sunpdt, xamdat,xampap"+
					    " WHERE sunpdt.mk_academic_year = "+acadYearString+
					    " AND sunpdt.mk_academic_period = 1"+
					    " AND sunpdt.semester_period = "+acadSemString+
					    " AND sunpdt.fk_suncode = "+"'"+tmpStudyUnit+"'"+
					   " AND registration_allow = 'Y'  "+
					  " AND xampap.exam_year = sunpdt.mk_exam_year "+
					  " AND xampap.mk_study_unit_code = sunpdt.fk_suncode"+
					  " AND xamdat.fk_exam_year = xampap.exam_year "+   
					  " AND xamdat.mk_exam_period_cod = sunpdt.mk_exam_period"+
					  " AND xamdat.fk_study_unit_code = xampap.mk_study_unit_code"+
					  " AND xamdat.fk_nr = xampap.nr"+
					  " AND TO_CHAR(date0,'YYYYMMDD') > ('19999999')"+
					  " AND NOT EXISTS"+
					  " ( SELECT * FROM xaddon "+
					  " WHERE xaddon.mk_exam_year= xampap.exam_year "+
					  " AND xaddon.mk_exam_period= xamdat.mk_exam_period_cod "+
					  " AND xaddon.mk_exam_type_code= xampap.mk_exam_type_code"+ 
					  " AND xaddon.admission_done_flag= 'Y' )   "+
					 " ORDER BY  TO_CHAR(date0,'YYYYMMDD'), TO_CHAR(starting_time,'HH24:MI'),sunpdt.fk_suncode";

          
		}
		examType = "Provisional exam";
		if(examSqlId2 != ""){
			ArrayList tmpExamEventList2 = new ArrayList();
			tmpExamEventList2 = executeExamSelect(examSqlId2,isStudent,studentNr,acadYearString,
					acadSemString,tmpStudyUnit,courseCycle,examType,isReExam);
	
			// read through exam the list
			for (int i=0; i <tmpExamEventList2.size(); i++) {
				//Resource entry = readResource(calendar,tmpExamEventList1.get(i).toString());
	            examEventList.add(tmpExamEventList2.get(i));
			}
		}
		
		isReExam = true;
		/* 3. Re-Exam: Final dates */
		String examSqlId3 = "";
		if (isStudent == false){
			//examSqlId3 = "228908";
			examSqlId3 = " SELECT sunpdt.fk_suncode AS SU, xamdat.fk_nr AS Paper_No,"+
			             " TO_CHAR(date0,'YYYY') AS XYear, TO_CHAR(date0,'MM') AS XMonth,  TO_CHAR(date0,'DD') AS XDay, TO_CHAR(starting_time,'HH24') AS XHour,"+ 
			             " TO_CHAR(starting_time,'MI') AS XMinute, xamdat.duration_days ||' Days' AS Xdays, xamdat.duration_hours ||' Hours' AS Xhours, "+
			             " xamdat.duration_Minutes||' Minutes' AS Xminutes , 'Final Timetable'"+
			             " FROM sunpdt, xamdat,xampap WHERE sunpdt.mk_academic_year = "+acadYearString+
			             " AND sunpdt.mk_academic_period = 1 AND sunpdt.semester_period = "+acadSemString+
			             " AND sunpdt.fk_suncode = "+"'"+tmpStudyUnit+"'"+
			             " AND registration_allow = 'Y'  AND xampap.exam_year = sunpdt.mk_supp_exam_year AND xampap.mk_study_unit_code = sunpdt.fk_suncode"+
			             " AND xamdat.fk_exam_year = xampap.exam_year AND xamdat.mk_exam_period_cod = sunpdt.mk_supp_exam_perio"+
			             " AND xamdat.fk_study_unit_code = xampap.mk_study_unit_code AND xamdat.fk_nr = xampap.nr"+
			             " AND TO_CHAR(date0,'YYYYMMDD') > ('19999999')"+
			             " AND EXISTS"+
			             " ( SELECT * FROM xaddon "+
			             " WHERE xaddon.mk_exam_year= xampap.exam_year "+
			             " AND xaddon.mk_exam_period= xamdat.mk_exam_period_cod "+
			             " AND xaddon.mk_exam_type_code= xampap.mk_exam_type_code"+
			             " AND xaddon.admission_done_flag= 'Y' )   "+
			             " ORDER BY  TO_CHAR(date0,'YYYYMMDD'), TO_CHAR(starting_time,'HH24:MI'),sunpdt.fk_suncode";
			              

		}
		examType = "Re-exam Final date";
		if(examSqlId3 != ""){
			ArrayList tmpExamEventList3 = new ArrayList();
			tmpExamEventList3 = executeExamSelect(examSqlId3,isStudent,studentNr,acadYearString,
					acadSemString,tmpStudyUnit,courseCycle,examType,isReExam);
	
			// read through exam the list
			for (int i=0; i <tmpExamEventList3.size(); i++) {
				//Resource entry = readResource(calendar,tmpExamEventList1.get(i).toString());
	            examEventList.add(tmpExamEventList3.get(i));
			}
		}
	    /* 4. Re-Exam: Provisional dates */
		String examSqlId4 = "";
		if (isStudent == false){
			//examSqlId4 = "914440";
			examSqlId4 = "SELECT sunpdt.fk_suncode AS SU, xamdat.fk_nr AS Paper_No, TO_CHAR(date0,'YYYY') AS XYear, TO_CHAR(date0,'MM') AS XMonth, TO_CHAR(date0,'DD') AS XDay, "+
			             " TO_CHAR(starting_time,'HH24') AS XHour, TO_CHAR(starting_time,'MI') AS XMinute, xamdat.duration_days ||' Days' AS Xdays, xamdat.duration_hours ||' Hours' AS Xhours,"+ 
			             " xamdat.duration_Minutes||' Minutes' AS Xminutes , 'Preliminary Timetable' "+
			             " FROM sunpdt, xamdat,xampap WHERE sunpdt.mk_academic_year ="+acadYearString+
			             " AND sunpdt.mk_academic_period = 1 AND sunpdt.semester_period = "+acadSemString+
			             " AND sunpdt.fk_suncode = "+"'"+tmpStudyUnit+"'"+
			             " AND registration_allow = 'Y'  "+
			             " AND xampap.exam_year = sunpdt.mk_supp_exam_year  AND xampap.mk_study_unit_code = sunpdt.fk_suncode AND xamdat.fk_exam_year = xampap.exam_year "+   
			             " AND xamdat.mk_exam_period_cod = sunpdt.mk_supp_exam_perio"+
			             " AND xamdat.fk_study_unit_code = xampap.mk_study_unit_code"+
			             " AND xamdat.fk_nr = xampap.nr"+
			             " AND TO_CHAR(date0,'YYYYMMDD') > ('19999999')"+
			             " AND NOT EXISTS"+
			             " ( SELECT * FROM xaddon "+
			             " WHERE xaddon.mk_exam_year= xampap.exam_year "+
			             " AND xaddon.mk_exam_period= xamdat.mk_exam_period_cod "+
			             " AND xaddon.mk_exam_type_code= xampap.mk_exam_type_code"+
			             " AND xaddon.admission_done_flag= 'Y' ) "+
			             " ORDER BY  TO_CHAR(date0,'YYYYMMDD'), TO_CHAR(starting_time,'HH24:MI'),sunpdt.fk_suncode";
			            

		}
		examType = "Re-exam Provisional date";
		if(examSqlId4 != ""){
			ArrayList tmpExamEventList4 = new ArrayList();
			tmpExamEventList4 = executeExamSelect(examSqlId4,isStudent,studentNr,acadYearString,
					acadSemString,tmpStudyUnit,courseCycle,examType,isReExam);
	
			// read through exam the list
			for (int i=0; i <tmpExamEventList4.size(); i++) {
				//Resource entry = readResource(calendar,tmpExamEventList1.get(i).toString());
	            examEventList.add(tmpExamEventList4.get(i));
			}
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	    return examEventList;
	}


	/**
	 * Method: getExamInformation
	 * 		to select the exam dates for a student
	 * @param studentNr
	 * @param courseCycle = <course code>=<unisa Pretoria cycle code>-<unisa Florida cycle>) (ANH101P-06-S1)
	 * @param isStudent: true = student; false = lecturer
	 * @return list of all the exam information for a student number and site/course
	 * @throws Exception
	 */
	public ArrayList executeExamSelect(String examSqlId, boolean isStudent, String studentNr, String acadYearString,
			String acadSemString, String tmpStudyUnit, String courseCycle, String examType, boolean isReExam) throws Exception{
		ArrayList examList = new ArrayList();

		this.timeInitialMillis = System.currentTimeMillis();
		log.debug("[javaproxy.funnel.init] [funnel=Wasq101sGetSql] [stamp="+Long.toString(timeInitialMillis)+"]"); 

	    /*Wasql01sGetSql op = new Wasql01sGetSql();
	    operListener opl = new operListener();
	    op.addExceptionListener(opl);
	    op.clear();*/

	    /* set input params for proxy: academic year, semester and study unit */
	   /* op.setInWsSqlQueryDescription("");
	    op.setInWsSqlQuerySqlId(examSqlId);
		this.timeExecuteMillis = System.currentTimeMillis();
		log.debug("[javaproxy.funnel.execute] [funnel=Wasq101sGetSql] [stamp="+this.timeExecuteMillis+"] [elapsed="+Long.toString(this.timeExecuteMillis - this.timeInitialMillis)+"]"); 
		try {
	    		op.execute();
			this.timeEndMillis = System.currentTimeMillis();
			log.debug("[javaproxy.funnel.end] [funnel=Wasq101sGetSql] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - this.timeExecuteMillis)+"]"); 
		} catch(Exception e) {
			this.timeEndMillis = System.currentTimeMillis();
			log.error("[javaproxy.funnel.end with exception] [funnel=Wasq101sGetSql] [id="+Long.toString(this.timeExecuteMillis)+"] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - this.timeExecuteMillis)+"]"); 
		}

	    /* was an exception thrown 
	    if (opl.getException() != null) {
		    EventTrackingService.post(
					EventTrackingService.newEvent(EventTrackingTypes.EVENT_CALENDAR_ERROR, "JavaProxy error: "+opl.getException(), false));
			log.error("[javaproxy.funnel.end with exception] [funnel=Wasq101sGetSql] [id="+Long.toString(this.timeExecuteMillis)+"] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - this.timeExecuteMillis)+"]"); 
	      	throw opl.getException();
	    }
	    if (op.getExitStateType() < 3) {
		    EventTrackingService.post(
					EventTrackingService.newEvent(EventTrackingTypes.EVENT_CALENDAR_ERROR, "JavaProxy error: "+op.getExitStateMsg(), false));
			log.error("[javaproxy.funnel.end with exception] [funnel=Wasq101sGetSql] [id="+Long.toString(this.timeExecuteMillis)+"] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - this.timeExecuteMillis)+"]"); 
	       	throw new Exception(op.getExitStateMsg());
	    }*/

	    /* get the number of records returned by the proxy */
	 //   int noOfRecords = op.getOutSqlGroupCount();

	    /* read through each record */
	    // retrieve select
	    //for (int i = 0; i < noOfRecords; i++) {
	    	//String select = op.getOutGWsSqlQuerySql(i);
	    	String[] selectArray = null;
	    	/*if (isStudent == true) {
	    		selectArray = StringUtil.split(select, "$");
	    		selectArray[1] = selectArray[1].replaceFirst("student_nr",studentNr);
	    		select = selectArray[0]+" "+selectArray[1];
			log.debug("Institutional Calendar Service SQL for student with examSqlId="+examSqlId+" : "+select);
	    	} else if (isStudent == false) {
	    		selectArray = StringUtil.split(select, "$");
	    		selectArray[1] = selectArray[1].replaceFirst("Academic_year",acadYearString);
	    		selectArray[2] = selectArray[2].replaceFirst("Semester",acadSemString);
	    		selectArray[3] = selectArray[3].replaceFirst("StudyUnit",tmpStudyUnit);
	    		select =  selectArray[0]+selectArray[1]+selectArray[2]+selectArray[3];
			log.debug("Institutional Calendar Service SQL for non-student with examSqlId="+examSqlId+" : "+select);
	    	}

	    	select = select.toUpperCase();*/
	
	    	try{
	    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
	    		List <Map<String, Object>> results  = jdt.queryForList(examSqlId);
    			//Iterator j = examInfoList.iterator();
	    		
    			List examInfoList = new Vector();
    			
    			for (Map<String, Object> row : results) {
    				String studyUnit = row.get("SU").toString();
    				String fkNr = row.get("PAPER_NO").toString();
    				String examYear = row.get("XYEAR").toString();
    				String examMonth = row.get("XMONTH").toString();
    				String examDay = row.get("XDAY").toString();
    				String examHour = row.get("XHOUR").toString();
    				String examMinute = row.get("XMINUTE").toString();
    				String durDays = row.get("XDAYS").toString();
    				String durHours = row.get("XHOURS").toString();
    				String durMinutes = row.get("XMINUTES").toString();
    				
    				String exCentre = "";
	    			selectArray = null;
	    			selectArray = StringUtil.split(durHours, " ");
	    			durHours = selectArray[0];

	    			selectArray = null;
	    			selectArray = StringUtil.split(durDays, " ");
	    			durDays = selectArray[0];

	    			selectArray = null;
	    			selectArray = StringUtil.split(durMinutes, " ");
	    			durMinutes = selectArray[0];

	    			if (isStudent == true) {
	    				exCentre = row.get("EXCENTRE").toString();
	    			}
		    			examHour = examHour.substring(0,2);

	    			if (tmpStudyUnit.equalsIgnoreCase(studyUnit)) {

	    				String examEvent = createExamEvent(courseCycle,examDay,examMonth,examYear,examHour,examMinute,durDays,durHours,durMinutes,fkNr,
	    						examType,exCentre,isReExam);
	    				examList.add(examEvent);
	    			}
    				
    			}// end for (Map<String, Object> row : results)
    			
    			/** remove listordereMap
    			 * 
    			 
	    			ListOrderedMap data = (ListOrderedMap) j.next();
	    			String studyUnit = data.get("SU").toString();
	    			String fkNr = data.get("PAPER_NO").toString();
	    			String examYear = data.get("XYEAR").toString();
	    			String examMonth = data.get("XMONTH").toString();
	    			String examDay = data.get("XDAY").toString();
	    			String examHour = data.get("XHOUR").toString();
	    			String examMinute = data.get("XMINUTE").toString();
	    			String durDays = data.get("XDAYS").toString();
	    			String durHours = data.get("XHOURS").toString();
	    			String durMinutes = data.get("XMINUTES").toString();
	    			//String examType = "Final date";
	    			String exCentre = "";
	    			selectArray = null;
	    			selectArray = StringUtil.split(durHours, " ");
	    			durHours = selectArray[0];

	    			selectArray = null;
	    			selectArray = StringUtil.split(durDays, " ");
	    			durDays = selectArray[0];

	    			selectArray = null;
	    			selectArray = StringUtil.split(durMinutes, " ");
	    			durMinutes = selectArray[0];

	    			if (isStudent == true) {
	    				exCentre = data.get("EXCENTRE").toString();
	    			}
		    			examHour = examHour.substring(0,2);

	    			if (tmpStudyUnit.equalsIgnoreCase(studyUnit)) {

	    				String examEvent = createExamEvent(courseCycle,examDay,examMonth,examYear,examHour,examMinute,durDays,durHours,durMinutes,fkNr,
	    						examType,exCentre,isReExam);
	    				examList.add(examEvent);
	    			}
	    		} // end while
	    		*/

	    	} catch (Exception ex) {
	    		throw new Exception("calendarDateStudentSystemQueryDAO: getExamInformation: (stno: "+studentNr+
	    				") Error occurred: "+ ex,ex);
	    	} // end try

	  //  } // end for (int i = 0; i < noOfRecords; i++) {

		return examList;
	}

	public ArrayList getAssignmentInformationList(String studentNr, String courseCycle) throws Exception{
		ArrayList assignmentEventList = new ArrayList();
		
		String tmpStudyUnit = courseCycle.substring(0,7);
		String tmpYear = courseCycle.substring(8,10);
		String tmpCycle = courseCycle.substring(11,13);
		short semester = CoursePeriodLookup.getPeriodTypeAsString(tmpCycle);
		int acadYear = CoursePeriodLookup.getYearTypeAsString(tmpYear);
		short acadYearShort= (short) acadYear;

		this.timeInitialMillis = System.currentTimeMillis();
		log.debug("[javaproxy.funnel.init] [funnel=Saual60sGetUnqass] [stamp="+Long.toString(timeInitialMillis)+"]"); 
		/* create instance of proxy */
		Saual60sGetUnqass op = new Saual60sGetUnqass();
	    operListener opl = new operListener();
	    op.addExceptionListener(opl);
	    op.clear();

	    /* set input params for proxy: academic year, semester and study unit */
	    op.setInWsStudentAnnualRecordMkAcademicYear(acadYearShort);
	    op.setInWsStudentStudyUnitMkStudyUnitCode(tmpStudyUnit);
	    op.setInWsStudentStudyUnitSemesterPeriod(semester);
		this.timeExecuteMillis = System.currentTimeMillis();
		log.debug("[javaproxy.funnel.execute] [funnel=Saual60sGetUnqass] [stamp="+this.timeExecuteMillis+"] [elapsed="+Long.toString(this.timeExecuteMillis - this.timeInitialMillis)+"]"); 
		try {
	    		op.execute();
			this.timeEndMillis = System.currentTimeMillis();
			log.debug("[javaproxy.funnel.end] [funnel=Saual60sGetUnqass] [id="+Long.toString(this.timeExecuteMillis)+"] [stamp="+this.timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - this.timeExecuteMillis)+"]"); 
		} catch(Exception e) {
			this.timeEndMillis = System.currentTimeMillis();
			log.error("[javaproxy.funnel.end with exception] [funnel=Saual60sGetUnqass] [id="+Long.toString(this.timeExecuteMillis)+"] [stamp="+this.timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - this.timeExecuteMillis)+"]"); 
		}

	    /* was an exception thrown */
	    if (opl.getException() != null) {
		    EventTrackingService.post(
					EventTrackingService.newEvent(EventTrackingTypes.EVENT_CALENDAR_ERROR, "JavaProxy error: "+opl.getException(), false));
			log.error("[javaproxy.funnel.end with exception] [funnel=Saual60sGetUnqass] [id="+Long.toString(this.timeExecuteMillis)+"] [stamp="+this.timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - this.timeExecuteMillis)+"]"); 
	      	throw opl.getException();
	    }
	    if (op.getExitStateType() < 3) {
		    EventTrackingService.post(
					EventTrackingService.newEvent(EventTrackingTypes.EVENT_CALENDAR_ERROR, "JavaProxy error: "+op.getExitStateMsg(), false));
			log.error("[javaproxy.funnel.end with exception] [funnel=Saual60sGetUnqass] [id="+Long.toString(this.timeExecuteMillis)+"] [stamp="+this.timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - this.timeExecuteMillis)+"]"); 
	       	throw new Exception(op.getExitStateMsg());
	    }

	    /* get the number of records returned by the proxy */
	    int noOfRecords = op.getOutUnqassGrpCount();

	    /* read through each record */
	    for (int i = 0; i < noOfRecords; i++) {

	    	/* select assignment detail from proxy */
	    	Calendar dueDate;
	    	short tmpAssignmentNr = op.getOutGUniqueAssignmentAssignmentNr(i); // assignment number
	    	dueDate = op.getOutGUniqueAssignmentClosingDate(i); // assignment duedate
	    	String compulsory = op.getOutGUniqueAssignmentCompulsory(i);

	    	/* DAY of the duedate */
	    	int dueDay = dueDate.get(Calendar.DAY_OF_MONTH);
	    	// make sure day is 2 characters long.
	    	String dueDayStr = "";
	    	if (dueDay <= 9) {
	    		dueDayStr = "0"+dueDay;
	    	} else {
	    		dueDayStr = ""+dueDay;
	    	}

	    	/* MONTH of duedate */
	    	// Month 0 = January, so add one month to get correct month number
	    	int dueMonth = dueDate.get(Calendar.MONTH) + 1;
	    	// make sure month is 2 characters long
	    	String dueMonthStr = "";
	    	if (dueMonth <= 9) {
	    		dueMonthStr = "0"+dueMonth;
	    	} else {
	    		dueMonthStr = ""+dueMonth;
	    	}

	    	/* YEAR of duedate */
	    	int dueYear = dueDate.get(Calendar.YEAR);
	    	String dueYearStr = ""+dueYear;

	    	/* create dueDateStr YYYYMMDD */
	    	String dueDateStr = dueYearStr+dueMonthStr+dueDayStr;

	    	/* create an xml event for this assignment */
	    	String assignmentEvent = createAssignmentEvent(courseCycle,dueDateStr,tmpAssignmentNr,compulsory,dueYear,dueMonth,dueDay);

	    	/* add event to list */
	    	assignmentEventList.add(assignmentEvent);
	    	

	    } // end for (int i = 0; i < noOfRecords; i++)

	    /* return list of all the events for this course and semester */
		return assignmentEventList;
	}

	public String getAssignmentInformation(String studentNr, String courseCycle, String requestedAssignment) throws Exception{
		
		String assignmentEvent = "";
		String tmpStudyUnit = courseCycle.substring(0,7);
		short acadYear = 2006;
		//short acadYear = 2000+courseCycle.substring(8,10);
		short semester = 1; //courseCycle.substring(11,13)

		/* create instance of proxy */
		Saual60sGetUnqass op = new Saual60sGetUnqass();
	    operListener opl = new operListener();
	    op.addExceptionListener(opl);
	    op.clear();

	    /* set input params for proxy: academic year, semester and study unit */
	    op.setInWsStudentAnnualRecordMkAcademicYear(acadYear);
	    op.setInWsStudentStudyUnitMkStudyUnitCode(tmpStudyUnit);
	    op.setInWsStudentStudyUnitSemesterPeriod(semester);
	    op.execute();

	    /* was an exception thrown */
	    if (opl.getException() != null) {
		    EventTrackingService.post(
					EventTrackingService.newEvent(EventTrackingTypes.EVENT_CALENDAR_ERROR, "JavaProxy error: "+opl.getException(), false));
	      	throw opl.getException();
	    }
	    if (op.getExitStateType() < 3) {
		    EventTrackingService.post(
					EventTrackingService.newEvent(EventTrackingTypes.EVENT_CALENDAR_ERROR, "JavaProxy error: "+op.getExitStateMsg(), false));
	       	throw new Exception(op.getExitStateMsg());
	    }

	    /* get the number of records returned by the proxy */
	    int noOfRecords = op.getOutUnqassGrpCount();

	    /* read through each record */
	    for (int i = 0; i < noOfRecords; i++) {

	    	/* select assignment detail from proxy */
//	    	Calendar dueDate;
	    	short tmpAssignmentNr = op.getOutGUniqueAssignmentAssignmentNr(i); // assignment number
//	    	dueDate = op.getOutGUniqueAssignmentClosingDate(i); // assignment duedate
	    	String tmpAssignmentNrStr = tmpAssignmentNr+"";

	    	if (tmpAssignmentNrStr.equals(requestedAssignment)) {
	    		/* DAY of the duedate */
//	    		int dueDay = dueDate.get(Calendar.DAY_OF_MONTH);
	    		// make sure day is 2 characters long.
/*	    		String dueDayStr = "";
	    		if (dueDay <= 9) {
	    			dueDayStr = "0"+dueDay;
	    		} else {
	    			dueDayStr = ""+dueDay;
	    		}*/

	    		/* MONTH of duedate */
	    		// Month 0 = January, so add one month to get correct month number
//	    		int dueMonth = dueDate.get(Calendar.MONTH) + 1;
	    		// make sure month is 2 characters long
//	    		String dueMonthStr = "";
/*	    		if (dueMonth <= 9) {
	    			dueMonthStr = "0"+dueMonth;
	    		} else {
	    			dueMonthStr = ""+dueMonth;
	    		}*/

		    	/* YEAR of duedate */
//		    	int dueYear = dueDate.get(Calendar.YEAR);
//		    	String dueYearStr = ""+dueYear;

		    	/* create dueDateStr YYYYMMDD */
//		    	String dueDateStr = dueYearStr+dueMonthStr+dueDayStr;

		    	/* create an xml event for this assignment */
		    	//assignmentEvent = createAssignmentEvent(courseCycle,dueDateStr,tmpAssignmentNr);
	    	}

	    } // end for (int i = 0; i < noOfRecords; i++)

	    /* return list of all the events for this course and semester */
		return assignmentEvent;
	}
	
	
	/**
	 * Method: createExamEvent
	 * 		Create an event of the exam
	 * 		dateRange = YYYYMMDDHHMM00000]YYYYMMDDHHMM00000
	 * 					before ] = start time; after ] = end time
	 *
	 * @param studyUnit (ANH101P)
	 * @param examDate (YYYY-MM-DD)
	 * @param startHours (HH24)
	 * @param startMinutes (MM)
	 * @param durDays (DD)
	 * @param durHours (HH)
	 * @param durMinutes (MM)
	 * @param fkNr
	 * @param courseCycle (ANH101P-06-S1)
	 * @return list of all exam events, each event an xml String.
	 */
	public String createExamEvent(String courseCycle, String examDay, String examMonth, String examYear,
			String startHour, String startMinutes,
			String durDays, String durHours, String durMinutes, String fkNr, String examType, String exCentre,
			boolean isReExam) {

		String studyUnit = courseCycle.substring(0,7); 		// get studyUnit code from the courceCycle
		String examEvent = "";
		String endYear = "";
		String endMonth = "";
		String endDay = "";
		String endHH = "";
		String endMM = "";
		String duration = "";

		//Handle Null durDays, durHours and durMinutes
		
		if (durDays.equals("") || durDays == null || durDays.equals(" ")){
			durDays = "0";
		}
		if (durHours.equals("") || durHours == null || durHours.equals(" ")){
			durHours = "0";
		}		
		if (durMinutes.equals("") || durMinutes == null || durMinutes.equals(" ")){
			durMinutes = "0";
		}
		
		// set all variables to int
		int examYearInt = Integer.parseInt(examYear);
		int examMonthInt = Integer.parseInt(examMonth) -1; // month 0 is January for calendar
		int examDayInt = Integer.parseInt(examDay);
		int startHourInt = Integer.parseInt(startHour) - 2; // daterange, hours is 2 less than actual hour on schedule
		int startMinInt = Integer.parseInt(startMinutes);
		int durDaysInt =  Integer.parseInt(durDays);
		int durHoursInt = Integer.parseInt(durHours);
		int durMinutesInt = Integer.parseInt(durMinutes);

		/**
		 *  use java calendar function to manipulate date
		 *  */
		Calendar c1 = Calendar.getInstance();
		c1.set(examYearInt,examMonthInt,examDayInt,startHourInt, startMinInt);
		// add duration to calendar
		c1.add(Calendar.MINUTE,durMinutesInt);
		c1.add(Calendar.HOUR,durHoursInt);
		c1.add(Calendar.DATE,durDaysInt);

		// get the calculated end date from calendar
		endYear = c1.get(Calendar.YEAR)+"";
		endMonth = (c1.get(Calendar.MONTH)+1)+""; // month 0 is January for calendar, we want it to be 1
		endDay = c1.get(Calendar.DATE)+"";
		endHH = c1.get(Calendar.HOUR_OF_DAY)+"";
		endMM = c1.get(Calendar.MINUTE)+"";

		// set format of the variables
		if (endMonth.length() <= 1) {
			endMonth = "0"+endMonth;
		}
		if (endDay.length() <= 1) {
			endDay = "0"+endDay;
		}
		if (endHH.length() <= 1) {
			endHH = "0"+endHH;
		}
		if (endMM.length() <= 1) {
			endMM = "0"+endMM;
		}
		startHour = startHourInt+"";
		if (startHour.length() <= 1) {
			startHour = "0"+startHour;
		}

		// set duration variable
		if (durDaysInt > 0) {
			duration = durDaysInt + " days";
		}
		if (durHoursInt > 0) {
			duration = duration + " "+ durHoursInt+ " hours";
		}
		if (durMinutesInt > 0) {
			duration = duration + " "+durMinutesInt+" minutes";
		}

		// create date range
		String dateRange = "";
		dateRange = examYear+examMonth+examDay+startHour+startMinutes+"00000]";
		dateRange = dateRange+endYear+endMonth+endDay+endHH+endMM+"00000";

		String eventId = "";
		if (isReExam == true) {
			//eventId = "ss:r:1:"; // Sonette 20 March 2012: Change because if 2 exams then duplicate id, id should be unique
			eventId = "ss:r:"+fkNr+":";
		} else {
			//eventId = "ss:x:1:"; // Sonette 20 March 2012: Change because if 2 exams then duplicate id, id should be unique
			eventId = "ss:x:"+fkNr+":";
		}

		// create xml for exam event
		examEvent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
						   "<event access=\"site\" id=\""+eventId+courseCycle+"\" range=\""+dateRange+"\">" +
		                   "<properties>" +
                           "<property enc=\"\" name=\"CHEF:description-html\" value=\"These details are for information only.  " + 
		                   			"Final arrangements are published in the Examination Timetable tool.  Note that only the South " +
		                   			"African Standard Time Zone (SAST) is applicable.  Information relating to other time zones should " +
		                   			"be ignored. &lt;br/&gt; &lt;br/&gt;"+
		                   			"Examination of "+studyUnit+" - "+examType+" "+fkNr+
                           			".  Duration: "+duration+".  " +
                           			"Final examination timetables will be sent to students by post prior to the examination\"/>"+
                           "<property enc=\"\" name=\"CHEF:calendar-location\" value=\""+exCentre+"\"/>" +
                           "<property enc=\"BASE64\" name=\"CHEF:calendar-type\" value=\""+EXAM+"\"/>" +
                           "<property enc=\"\" name=\"CHEF:description\" value=\"aa"+examType+" "+fkNr+", Duration: "+duration+". " +
                           		"Final examination timetables will be sent to students by post prior to the examination\"/>" +
                           "<property enc=\"\" name=\"DAV:displayname\" value=\"Exam: "+studyUnit+"\"/>" +
                           "</properties>" +
                           "</event>";
		
		//System.out.print(examEvent+"\n\n");

		return (examEvent);

	}

	/**
	 * Method: createAssignmentEvent
	 * 		Create assignment event to be displayed in unisa-calendar /schedul
	 * 		dateRange = YYYYMMDDHHMM00000]YYYYMMDDHHMM00000
	 * 					before ] = start time; after ] = end time
	 *
	 * @input params: studyUnit code; duedate (format YYYYMMDD); assignmentNr; courceCycle (ANH101P-06-S1)
	 * @return: list of xml (all events in xml format, each event his own xml.)
	 */
	public String createAssignmentEvent(String courseCycle,String dueDateStr,short tmpAssignmentNr, String compulsory,
			int dueYear,int dueMonth,int dueDay) {

		String assignmentEvent = "";


		// complete date range
		//20060315230000000]20060316220000000
		//20060317010000000]20060317220000000
		String dateRange = dueDateStr+"010000000]";
		dateRange = dateRange+dueDateStr+"215900000";

		// get studyUnit code from the courceCycle
		String studyUnit = courseCycle.substring(0,7);

		// Assignment description
		//String assignmentDescription =

		// create xml for assignment event
		assignmentEvent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
						   "<event access=\"site\" id=\"ss:a:"+tmpAssignmentNr+":"+courseCycle+"\" range=\""+dateRange+"\">" +
		                   "<properties>" +
                           "<property enc=\"\" name=\"CHEF:description-html\" value=\"Assignment "+tmpAssignmentNr+" for " +studyUnit+"\"/>"+
                           "<property enc=\"\" name=\"CHEF:calendar-location\" value=\"\"/>" +
                           "<property enc=\"BASE64\" name=\"CHEF:calendar-type\" value=\""+DEADLINE+"\"/>" +
                           "<property enc=\"\" name=\"CHEF:description\" value=\"Assignment "+tmpAssignmentNr+" for " +studyUnit+"\"/>"+
                           "<property enc=\"\" name=\"DAV:displayname\" value=\"Assignment "+tmpAssignmentNr+" for " +studyUnit+"\"/>"+
                           "</properties>" +
                           "</event>";

		return (assignmentEvent);
	}
	
}
