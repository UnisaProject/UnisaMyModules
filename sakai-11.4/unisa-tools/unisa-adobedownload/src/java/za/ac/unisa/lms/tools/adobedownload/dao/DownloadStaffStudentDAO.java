package za.ac.unisa.lms.tools.adobedownload.dao;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.adobedownload.forms.CourseForm;

import java.util.ArrayList;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

public class DownloadStaffStudentDAO extends StudentSystemDAO{
	
	/**
	 * This method will select all registration periods
	 *
	*/
	public ArrayList selectRegPeriodOptions() throws Exception {
		ArrayList acadPeriodOptions = new ArrayList();
		
		acadPeriodOptions.add(new org.apache.struts.util.LabelValueBean("Select Period",""));
		acadPeriodOptions.add(new org.apache.struts.util.LabelValueBean("Semester 1","1"));
		acadPeriodOptions.add(new org.apache.struts.util.LabelValueBean("Semester 2","2"));
		acadPeriodOptions.add(new org.apache.struts.util.LabelValueBean("Year Course","0"));
		acadPeriodOptions.add(new org.apache.struts.util.LabelValueBean("Year Course June","6"));
		
		return acadPeriodOptions;
	}
	
	/**
	 * This method will test if user linked to a particular course in table USRSUN.
	 *
	 * @param courseCode    course code
	 * @param year   	academic year
	 * @param acadPeriod   academic period
	 * @output personLinked		boolean true=valid; false=not valid
	*/
	public boolean personLinked(String year,String acadPeriod,String courseCode,String userId ) throws Exception {
		boolean personLinked = false;
		String noOfRecords;

		String sql1 = " SELECT COUNT(*) as NoOfRecords "+
					  " FROM   USRSUN " +
					  " WHERE  MK_STUDY_UNIT_CODE = upper('"+courseCode+"') AND " +
					  " NOVELL_USER_ID = upper('"+userId+"') " +
					  " AND    (SYSTEM_TYPE = 'L' OR SYSTEM_TYPE = 'J') "+
					  " AND    MK_ACADEMIC_YEAR = "+year+
					  " AND    MK_SEMESTER_PERIOD = "+acadPeriod;
					
		
		
		try
		{
			noOfRecords = this.querySingleValue(sql1,"NoOfRecords");
		} 
		catch (Exception ex) 
		{
            throw new Exception("DownloadStaffStudentDAO: courseExist: Error occurred / "+ ex,ex);
		}

		if (Integer.parseInt(noOfRecords) > 0) 
		{
			personLinked = true;
		} else 
		{
			personLinked = false;
		}

		return personLinked;
	}
	
	
	/**
	 * This method will test if user linked to a particular course in table USRSUN.
	 *
	 * @param courseCode    course code
	 * @param year   	academic year
	 * @param acadPeriod   academic period
	 * @output personLinked		boolean true=valid; false=not valid
	*/
	public String selectPrimaryLecturer(String year,String acadPeriod,String courseCode) throws Exception {
		String primaryLecturer;

		String sql1 = "select  NOVELL_USER_ID  from usrsun " +
		              " WHERE  MK_STUDY_UNIT_CODE = upper('"+courseCode+"')" +
		              " AND    ACCESS_LEVEL = 'PRIML' " + 
		              " AND    SYSTEM_TYPE = 'L' "+ 
		              " AND    MK_ACADEMIC_YEAR = "+year+ 
		              " AND    MK_SEMESTER_PERIOD = "+acadPeriod;
		 
		
	
		try{
			primaryLecturer = this.querySingleValue(sql1,"NOVELL_USER_ID");
		} catch (Exception ex) {
            throw new Exception("DownloadStaffStudentDAO: selectPrimaryLecturer: Error occurred / "+ ex,ex);
		}

		if (primaryLecturer.length()> 0) {
			primaryLecturer=primaryLecturer+"@unisa.ac.za";
		} else {
			primaryLecturer="";
		}

		return primaryLecturer;
	}
	
	/**
	 * This method will test if valid course site was entered using table USRSUN.
	 *
	 * @param coursecode   	Course code that was entered
	 * @param year 			Academic year
	 * @param period		Semester period
	 * @param systemType	L= teaching roles, X=examination functions
	 * @output courseExist		boolean true=valid; false=not valid
	*/
	public boolean courseExist(String courseCode, String year, String period) throws Exception {
		boolean courseExist = false;
		String noOfRecords;

		String sql1 = " SELECT COUNT(*) as NoOfRecords "+
		              " FROM   USRSUN "+
		              " WHERE  SYSTEM_TYPE = 'L' "+
		              " AND    MK_STUDY_UNIT_CODE = UPPER('"+courseCode+"')"+
		              " AND    MK_ACADEMIC_YEAR = "+year+
		              " AND    MK_SEMESTER_PERIOD = "+period;

		try{
			noOfRecords = this.querySingleValue(sql1,"NoOfRecords");

		} catch (Exception ex) {
            throw new Exception("DownloadStaffStudentDAO: courseExist (2): Error occurred / "+ ex,ex);
		}

		if (Integer.parseInt(noOfRecords) > 0) {
			courseExist = true;
		} else {
			courseExist = false;
		}

		return courseExist;
	}
	
	/**
	 * This method will test if course is still active
	 *
	 * @param coursecode   	Course code that was entered
	 * @param year 			Academic year
	 * @param period		Semester period
	 * @output courseExist		boolean true=valid; false=not valid
	*/
	public boolean courseExpired(String courseCode, String year, String period) throws Exception {
		boolean courseValid = false;
		String courseType;

		String sql1 = " SELECT FK_SUNTYPCODE, FROM_YEAR, TO_YEAR"+ 
				      " FROM   SUN"+
				      " WHERE  CODE = upper('"+courseCode+"')";
		
		try{
			JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List courseInfo = jdt.queryForList(sql1);
			Iterator j = courseInfo.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			String fromYear = data.get("FROM_YEAR").toString();
    			String toYear = data.get("TO_YEAR").toString();
    			int fromYearI = Integer.parseInt(fromYear);
    			int toYearI = Integer.parseInt(toYear);
    			int yearI = Integer.parseInt(year);
    			
    		    // get current date
    			GregorianCalendar calCurrent = new GregorianCalendar();
    			int currentYear = calCurrent.get(Calendar.YEAR);
    			
    			if ((fromYearI <= yearI)&&((toYearI >= yearI)||(toYearI == 0))){
    				//valid
    				courseValid = true;
    			} else {
    				//not valid
    				courseValid = false;
    			}
	
    		}

		} catch (Exception ex) {
            throw new Exception("DownloadStaffStudentDAO: courseExpired: Error occurred / "+ ex,ex);
		}
		
		return courseValid;
	}
	public void getUserNameAndPersNo(CourseForm form) throws Exception{
		CourseForm courseForm = (CourseForm) form;

		String name = "";
		String persno = "";
		String query = "SELECT TITLE||' '||SURNAME||' '||INITIALS AS NAME,PERSNO "+
		              "FROM   STAFF "+
		              "WHERE  UPPER(NOVELL_USER_ID) = upper('"+courseForm.getUserId()+"') ";
		try{
			
	   		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List nameList = jdt.queryForList(query);
			Iterator j = nameList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			name = data.get("NAME").toString();
    			persno = data.get("PERSNO").toString();
    		} // end while
    		
    		//check from usr table if person not existing in staff
    		if(persno.length()==0){
    			String query1 ="select name,PERSONNEL_NO from usr where NOVELL_USER_CODE=upper('"+courseForm.getUserId()+"') ";
        		List nameListUser = jdt.queryForList(query1);
    			Iterator i= nameListUser.iterator();
        		while (i.hasNext()) {
        			ListOrderedMap data = (ListOrderedMap) j.next();
        			name = data.get("name").toString();
        			persno = data.get("PERSONNEL_NO").toString();
        			if(persno.length()==0){
        				persno="0";
        			}
        		} // end while
    		}

		} catch (Exception ex) {
			throw new Exception("DownloadStaffStudentDAO: getUserNameAndPersNo: Error occurred / "+ ex,ex);
		}
		courseForm.setName(name);
		courseForm.setPersonnelNr(persno);	
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
