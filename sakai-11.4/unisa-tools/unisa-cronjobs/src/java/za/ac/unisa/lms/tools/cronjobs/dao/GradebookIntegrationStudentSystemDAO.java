package za.ac.unisa.lms.tools.cronjobs.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.mail.internet.AddressException;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.util.LabelValueBean;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class GradebookIntegrationStudentSystemDAO extends StudentSystemDAO {

	public ArrayList<String> selectOnlineAssessments(String acadYear, String semPeriod) throws Exception {
		//String[] onlineAssessments = null;
		ArrayList<String> onlineAssessments = new ArrayList<String>();
		// 24 Nov - Sonette add online type XA
		String ONLINE_TYPES = "'BL','DF','SA','XA'";

		String select = "select MK_STUDY_UNIT_CODE, YEAR, PERIOD, ASSIGNMENT_NR,ONLINE_TYPE_GC176 "+
				   " from UNQASS "+
	               " where year = "+acadYear+
	               " and   period = "+semPeriod+
	               " and   type = 'H'"+
	               " and   ONLINE_TYPE_GC176 in ("+ONLINE_TYPES+")"+
	               // 24 Nov - Sonette add assess group type F ("Formative assessment)
	               " and   ASSESS_GROUP_GC230 = 'F'"+
	               "and   sysdate between closing_date and add_months(closing_date,2)";
	               // SY: remove 2 Jul 2013" and   closing_date < sysdate";


		try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List<?> assessmentList = jdt.queryForList(select);
			Iterator<?> j = assessmentList.iterator();
			int counter = 0;
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			/** Booking information */
    			String module = data.get("MK_STUDY_UNIT_CODE").toString();
    			String year = data.get("YEAR").toString();
    			String period = data.get("PERIOD").toString();
    			String assNr = data.get("ASSIGNMENT_NR").toString();
    			String onlineType = data.get("ONLINE_TYPE_GC176").toString();
    			
    			String onlineAss = module+"#"+year+"#"+period+"#"+assNr+"#"+onlineType;
    			onlineAssessments.add(onlineAss);
     
    		} // end while

    		if ((null==onlineAssessments)||(onlineAssessments.size() == 0)) {
				onlineAssessments.add("No online assessments found");
			}
    		
    	} catch (Exception ex) {
    		throw new Exception("za.ac.unisa.lms.db.SakaiDAO.SatbookDAO: selectBookingList: Error occurred / "+ ex,ex);
    	} // end try

		return onlineAssessments;
	}
	
	public ArrayList<String> selectOnlineAssessmentsCourses(String acadYear, String semPeriod, String courses) throws Exception {
		//String[] onlineAssessments = null;
		ArrayList<String> onlineAssessments = new ArrayList<String>();
		String ONLINE_TYPES = "'BL','DF','SA'";

		String select = "select MK_STUDY_UNIT_CODE, YEAR, PERIOD, ASSIGNMENT_NR,ONLINE_TYPE_GC176 "+
				   " from UNQASS "+
	               " where year = "+acadYear+
	               " and   period = "+semPeriod+
	               " and   type = 'H'"+
	               " and   ONLINE_TYPE_GC176 in ("+ONLINE_TYPES+")"+
	               "and   sysdate between closing_date and add_months(closing_date,2)";



		try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List<?> assessmentList = jdt.queryForList(select);
			Iterator<?> j = assessmentList.iterator();
			int counter = 0;
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			/** Booking information */
    			String module = data.get("MK_STUDY_UNIT_CODE").toString();
    			String year = data.get("YEAR").toString();
    			String period = data.get("PERIOD").toString();
    			String assNr = data.get("ASSIGNMENT_NR").toString();
    			String onlineType = data.get("ONLINE_TYPE_GC176").toString();
    			
    			String onlineAss = module+"#"+year+"#"+period+"#"+assNr+"#"+onlineType;
    			onlineAssessments.add(onlineAss);
     
    		} // end while

    		if ((null==onlineAssessments)||(onlineAssessments.size() == 0)) {
				onlineAssessments.add("No online assessments found");
			}
    		
    	} catch (Exception ex) {
    		throw new Exception("za.ac.unisa.lms.db.SakaiDAO.SatbookDAO: selectBookingList: Error occurred / "+ ex,ex);
    	} // end try

		return onlineAssessments;
	}
	
	
	public String selectPrimaryLecturer(String module, String acadYear, String semPeriod) throws Exception {

		String primLecturer = "";

		/** select all bookings that will take place in 8 days. */

		String select = "select upper(novell_user_id)||'#'||"+
          		   "nvl((select usr.e_mail from usr where novell_user_code = usrsun.novell_user_id),'none')||'#'||"+
          		   "nvl((select staff.email_address from staff where NOVELL_USER_ID = usrsun.novell_user_id and resign_date is null),'none')||'#'||"+
          		   "nvl((select staff.email_address from staff where NOVELL_USER_ID = usrsun.novell_user_id and resign_date = (select max(resign_date) from staff where NOVELL_USER_ID = usrsun.novell_user_id)),'none') AS PRIML"+
				   " FROM USRSUN"+ 
				   " where mk_study_unit_code = '"+module+"'"+ 
				   " and mk_academic_year = "+acadYear+ 
				   " and mk_semester_period = "+semPeriod+
				   " and system_type = 'L'"+ 
				   " and access_level = 'PRIML'";

        try{
        	primLecturer = this.querySingleValue(select,"PRIML").toString();

        } catch (Exception ex) {
        	System.out.println("GradebookIntegrationDAO: selectPrimaryLecturer: Error occurred / "+ ex);
        	//	throw new Exception("MyUnisaStatsDAO: getJoinActivationStatistics: Error occurred / "+ ex,ex);
        }

		return primLecturer;
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
    	List<?> queryList;
    	String result = "";

 	   queryList = jdt.queryForList(query);
 	   Iterator<?> i = queryList.iterator();
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
