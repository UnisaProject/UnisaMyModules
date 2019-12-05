package za.ac.unisa.lms.services.calendar.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;
import org.sakaiproject.event.cover.EventTrackingService;
import org.sakaiproject.util.StringUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.utils.CoursePeriodLookup;
import Saual60h.Abean.Saual60sGetUnqass;
import Wasql01h.Abean.Wasql01sGetSql;
import za.ac.unisa.lms.db.SakaiDAO;

public class calendarDateSatbookSystemQueryDAO extends SakaiDAO {
	
	private final String SPECIAL_EVENT = "U3BlY2lhbCBldmVudA==";
	
	/**
	 * Method: getSatbookInformationList
	 * 		to select the satellite broadcast dates for a student
	 * 		Used in unisa-calendar
	 * @param studentNr
	 * @param courseCycle = <course code>=<unisa Pretoria cycle code>-<unisa Florida cycle>) (ANH101P-06-S1)
	 * @param isStudent: true = student; false = lecturer
	 * @return list of all the exam information for a student number and site/course
	 * @throws Exception
	 */
	public ArrayList getSatbookInformationList(String studentNr, String courseCycle, boolean isStudent) throws Exception{

		String tmpStudyUnit = courseCycle.substring(0,7);
		String tmpYear = courseCycle.substring(8,10);
		String tmpCycle = courseCycle.substring(11,13);
		short acadSemester = CoursePeriodLookup.getPeriodTypeAsString(tmpCycle);
		int acadYear = CoursePeriodLookup.getYearTypeAsString(tmpYear);
		String acadYearString = Integer.toString(acadYear);
		String acadSemString = Integer.toString(acadSemester);
		
		ArrayList bkngList = new ArrayList();
		
		tmpYear = "20"+tmpYear;
		
		if (tmpCycle.equals("S1")) {
			tmpCycle = "1";
		} else if (tmpCycle.equals("S2")) {
			tmpCycle = "2";
		} else if (tmpCycle.equals("Y1")) {
			tmpCycle = "0";
		} else if (tmpCycle.equals("Y2")) {
			tmpCycle = "6";
		}
		String select = "SELECT SATBOOK_BKNG_MAIN.BKNG_ID as BKNGID, BKNG_HEADING, "+
						"	TO_CHAR(BKNG_START,'YYYYMMDD') AS STARTTIME, "+
						"	TO_CHAR(BKNG_START, 'HH24') AS STARTTIMEHH, "+	
						"	TO_CHAR(BKNG_START, 'MI') AS STARTTIMEMM, "+
		                "	TO_CHAR(BKNG_END,'YYYYMMDD') AS ENDTIME, "+
		                "	TO_CHAR(BKNG_END, 'HH24') AS ENDTIMEHH, "+
		                "	TO_CHAR(BKNG_END, 'MI') AS ENDTIMEMM, "+
		                "   nvl(BKNG_REBROADCAST,'N') as REBROAD "+
		                "FROM   SATBOOK_BKNG_MAIN, SATBOOK_BKNG_SUBJECT "+
		                "WHERE  SATBOOK_BKNG_SUBJECT.SUBJ_CODE = '"+tmpStudyUnit+"' "+
		                "AND    SATBOOK_BKNG_SUBJECT.SUBJ_PERIOD = "+tmpCycle+" "+
		                "AND    SATBOOK_BKNG_SUBJECT.SUBJ_YEAR = "+tmpYear+" "+
		                "AND    SATBOOK_BKNG_MAIN.BKNG_ID = SATBOOK_BKNG_SUBJECT.BKNG_ID "+
		                "AND    UPPER(SATBOOK_BKNG_MAIN.BKNG_CONFIRMED) = 'Y' "+
		                "ORDER BY SATBOOK_BKNG_MAIN.BKNG_START";
    	
		try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List <Map<String, Object>> results = jdt.queryForList(select);
			//Iterator j = examInfoList.iterator();
    		
			List satbookInfoList = new Vector();

   			for (Map<String, Object> row : results) {
				String tmpBkngId = row.get("BKNGID").toString();
				String tmpBkngHeading=row.get("BKNG_HEADING").toString();
    			String tmpBkngStart=row.get("STARTTIME").toString();

    			String tmpBkngStartHH24=row.get("STARTTIMEHH").toString();
    			int tmpBkngStartHHint= Integer.parseInt(tmpBkngStartHH24) -2;
    			if (tmpBkngStartHHint <= 9) {
    				tmpBkngStartHH24 = "0"+Integer.toString(tmpBkngStartHHint);	
    			} else {
    				tmpBkngStartHH24 = Integer.toString(tmpBkngStartHHint);
    			}
    			String tmpBkngStartMM=row.get("STARTTIMEMM").toString();

    			String tmpBkngEnd=row.get("ENDTIME").toString();
    			String tmpBkngEndHH24=row.get("ENDTIMEHH").toString();
    			int tmpBkngEndHHint= Integer.parseInt(tmpBkngEndHH24) -2;
    			if (tmpBkngEndHHint <= 9) {
    				tmpBkngEndHH24 = "0"+Integer.toString(tmpBkngEndHHint);	
    			} else {
    				tmpBkngEndHH24 = Integer.toString(tmpBkngEndHHint);
    			}
    			String tmpBkngEndMM=row.get("ENDTIMEMM").toString();
    			String tmpBkngRebroadc=row.get("REBROAD").toString();
    			
    			Calendar bkngDate;
    			
    			String tmpBkngStartYYYY=tmpBkngStart.substring(0,4);
    			String tmpBkngStartMO=tmpBkngStart.substring(4,6);
    			String tmpBkngStartDD=tmpBkngStart.substring(6);
    			String tmpBkngEndYYYY=tmpBkngEnd.substring(0,4);
    			String tmpBkngEndMO=tmpBkngEnd.substring(4,6);
    			String tmpBkngEndDD=tmpBkngEnd.substring(6);

    			
    			String tmpEvent = createSatbookEvent(courseCycle,
    					tmpBkngStartDD,tmpBkngStartMO,tmpBkngStartYYYY,tmpBkngStartHH24,tmpBkngStartMM,
    					tmpBkngEndDD,tmpBkngEndMO, tmpBkngEndYYYY, tmpBkngEndHH24, tmpBkngEndMM, tmpBkngId,tmpBkngRebroadc);
    			bkngList.add(tmpEvent);
   			}
			/*
			while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			
    			String tmpBkngId=data.get("BKNGID").toString();
    			String tmpBkngHeading=data.get("BKNG_HEADING").toString();
    			String tmpBkngStart=data.get("STARTTIME").toString();
    			String tmpBkngStartHH24=data.get("STARTTIMEHH").toString();
    			int tmpBkngStartHHint= Integer.parseInt(tmpBkngStartHH24) -2;
    			if (tmpBkngStartHHint <= 9) {
    				tmpBkngStartHH24 = "0"+Integer.toString(tmpBkngStartHHint);	
    			} else {
    				tmpBkngStartHH24 = Integer.toString(tmpBkngStartHHint);
    			}
    			String tmpBkngStartMM=data.get("STARTTIMEMM").toString();
    			String tmpBkngEnd=data.get("ENDTIME").toString();
    			String tmpBkngEndHH24=data.get("ENDTIMEHH").toString();
    			int tmpBkngEndHHint= Integer.parseInt(tmpBkngEndHH24) -2;
    			if (tmpBkngEndHHint <= 9) {
    				tmpBkngEndHH24 = "0"+Integer.toString(tmpBkngEndHHint);	
    			} else {
    				tmpBkngEndHH24 = Integer.toString(tmpBkngEndHHint);
    			}
    			String tmpBkngEndMM=data.get("ENDTIMEMM").toString();
    			String tmpBkngRebroadc=data.get("REBROAD").toString();
    			
    			Calendar bkngDate;
    			
    			String tmpBkngStartYYYY=tmpBkngStart.substring(0,4);
    			String tmpBkngStartMO=tmpBkngStart.substring(4,6);
    			String tmpBkngStartDD=tmpBkngStart.substring(6);
    			String tmpBkngEndYYYY=tmpBkngEnd.substring(0,4);
    			String tmpBkngEndMO=tmpBkngEnd.substring(4,6);
    			String tmpBkngEndDD=tmpBkngEnd.substring(6);

    			
    			String tmpEvent = createSatbookEvent(courseCycle,
    					tmpBkngStartDD,tmpBkngStartMO,tmpBkngStartYYYY,tmpBkngStartHH24,tmpBkngStartMM,
    					tmpBkngEndDD,tmpBkngEndMO, tmpBkngEndYYYY, tmpBkngEndHH24, tmpBkngEndMM, tmpBkngId,tmpBkngRebroadc);
    			bkngList.add(tmpEvent);
    			
    		}
    		*/
    	} catch (Exception ex) {
    		throw new Exception("calendarDateStudentSystemQueryDAO: getSatbookInformationList: Error occurred / "+ ex,ex);
    	} // end try
	    return bkngList;
	}
	
	/**
	 * Method: createSatbookEvent
	 * 		Create an event of the satellite broadcast
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
	public String createSatbookEvent(String courseCycle, String sDay, String sMonth, String sYear,
			String startHour, String startMinutes,
			String eDay, String eMonth, String eYear, String eHour, String eMinutes, String bkngId,
			String tmpBkngRebroadc) {

		String studyUnit = courseCycle.substring(0,7); 		// get studyUnit code from the courceCycle
	
		// create date range
		String dateRange = "";
		dateRange = sYear+sMonth+sDay+startHour+startMinutes+"00000]";
		dateRange = dateRange+eYear+eMonth+eDay+eHour+eMinutes+"00000";

		String eventId = "";
		eventId = "ss:s:"+bkngId;
		
		String desc = "";
		
		if (tmpBkngRebroadc.equals("Y")) {
			desc = "Satellite rebroadcast for "+studyUnit;
		} else {
			desc = "Satellite broadcast for "+studyUnit;
		}

		// create xml for exam event
		String satbookEvent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
						   "<event access=\"site\" id=\""+eventId+":"+courseCycle+"\" range=\""+dateRange+"\">" +
		                   "<properties>" +
                           "<property enc=\"\" name=\"CHEF:description-html\" value=\""+desc+". For more information contact regional office/call centre(011 6709000)/ http://www.unisa.ac.za/utv\"/>"+
                           "<property enc=\"\" name=\"CHEF:calendar-location\" value=\"\"/>" +
                           "<property enc=\"BASE64\" name=\"CHEF:calendar-type\" value=\""+SPECIAL_EVENT+"\"/>" +
                           "<property enc=\"\" name=\"CHEF:description\" value=\"aa" +desc+". For more information contact regional office/call centre(011 6709000)/ http://www.unisa.ac.za/utv\"/>"+
                           "<property enc=\"\" name=\"DAV:displayname\" value=\"" +desc+"\"/>"+
                           "</properties>" +
                           "</event>";

		return satbookEvent;

	}
	
	public String calculateBookingDuration(String startHH, String endHH,
			String startMM, String endMM) throws Exception {
		String duration = "";

		int startHHI = Integer.parseInt(startHH);
		int startMMI = Integer.parseInt(startMM);
		int endHHI = Integer.parseInt(endHH);
		int endMMI = Integer.parseInt(endMM);
		int hours = 0;
		int minutes = 0;

		//hours = endHHI - startHHI;
		//minutes = endMMI - startMMI;
		String select = "SELECT TRUNC(MOD( (TO_DATE('"+endHH+":"+endMM+"', 'HH:MI') - "+
						"TO_DATE('"+startHH+":"+startMM+"', 'HH:MI'))*24,24)) "+
						"|| ':' || "+
						"TRUNC(MOD( (TO_DATE('"+endHH+":"+endMM+"', 'HH:MI') - " +
						"TO_DATE('"+startHH+":"+startMM+"','HH:MI'))*24*60,60)) AS DIFFERENCE " +
						"FROM   dual";

		try{
			duration = this.querySingleValue(select,"DIFFERENCE");

		} catch (Exception ex) {
			throw new Exception("SatbookBookingDAO: selectLecturerEmail: Error occurred / "+ ex,ex);
		} // end try

		//duration = hours+" hours and "+minutes+" minutes.";

		return duration;
	}
	/**
	 * This method executes a query and returns a String value as result.
	 * An empty String value is returned if the query returns no results.
	 *
	 * @param query       The query to be executed on the database
	 * @param field		  The field that is queried on the database
	 * method written by: E Penzhorn
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
			 if (data.get(field) == null){
			 } else {
				 result = data.get(field).toString();
			 }
 	   }
 	   return result;
	}
}