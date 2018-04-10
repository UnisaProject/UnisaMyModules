package za.ac.unisa.lms.tools.tutorprebooks.dao;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import za.ac.unisa.lms.db.SakaiDAO;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.tutorprebooks.forms.MainForm;
import org.sakaiproject.component.cover.ServerConfigurationService;

public class TutorPreBooksDAO extends StudentSystemDAO{


	public ArrayList selectLinkedCourses(String networkCode, String year,String acadPeriod) throws Exception {
		ArrayList courseList = new ArrayList();
			
	/*String select = " select u.NOVELL_USER_ID,u.MK_STUDY_UNIT_CODE course,nvl(TRIM(TB.NO_OF_TUTORBOOKS),' ') AS tutors ,	nvl(TRIM(TB.NOVELL_USER_ID),' ') AS modifiedBy from  "
				+ "usrsun u left outer join tutorbooks@SAKAIDEV TB ON U.MK_STUDY_UNIT_CODE = TB.MK_STUDY_UNIT_CODE "
				+ "	where u.SYSTEM_TYPE = 'L' and u.novell_user_id = '"+networkCode +"' and u.MK_ACADEMIC_YEAR='"+ year
				+ "' and u.MK_SEMESTER_PERIOD='"+acadPeriod +"'";*/
		
		String sakaidb = ServerConfigurationService.getString("sakaidb");
		//System.out.println("sakaidb "+sakaidb);
		
		String select = " select u.NOVELL_USER_ID,u.MK_STUDY_UNIT_CODE course, nvl(TRIM(TB.NO_OF_TUTORBOOKS),' ') AS tutors , nvl(TRIM(TB.NOVELL_USER_ID),' ') AS modifiedBy "
				+ "	from usrsun u, tutorbooks"+sakaidb+" TB 	where u.MK_STUDY_UNIT_CODE = TB.MK_STUDY_UNIT_CODE(+) "
				+ "	and u.SYSTEM_TYPE = 'L' and u.novell_user_id = '"+networkCode +"' 	and u.MK_ACADEMIC_YEAR='"+ year 
				+ "'	and u.MK_SEMESTER_PERIOD='"+acadPeriod +"' 	and TB.MK_ACADEMIC_YEAR(+) = u.MK_ACADEMIC_YEAR "
				+ "	and TB.MK_ACADEMIC_PERIOD(+) = u.MK_SEMESTER_PERIOD 	order by u.MK_STUDY_UNIT_CODE";

			try{
			JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
			List courseInfoList = jdt.queryForList(select);
			Iterator j = courseInfoList.iterator();
	   		while (j.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) j.next();
				CourseDetailsBean courseDetails = new CourseDetailsBean();
				courseDetails.setCoursecode(data.get("course").toString());
				courseDetails.setLastModifiedBy(data.get("modifiedBy").toString());
				courseDetails.setNumberOfTutors(data.get("tutors").toString());
				//courseDetails.setDepartment(data.get("dpt").toString());
				
				courseList.add(courseDetails);
	
	   		}
		} catch (Exception ex) {
			throw new Exception("TutorPreBooksDAO: selectLinkedCourses: Error occurred / "+ ex,ex);
		} // end try
	
		return courseList;
	}

public ArrayList getAllPreBooksModules(String year, String acadPeriod, String completed) {
		
		ArrayList listModules = new ArrayList();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		String strQuery;
		String sakaidb = ServerConfigurationService.getString("sakaidb");
			
	    if (completed == "true")
	    {
	    	strQuery = " SELECT colleg.eng_description AS college, SCHOOL.ENG_DESCRIPTION AS school, TB.Department AS department, TB.mk_study_unit_code AS module, TB.no_of_tutorbooks, TB.NOVELL_USER_ID AS modifiedBy"+
    				" FROM sun, colleg, school, tutorbooks"+sakaidb+" TB"+
    				" WHERE TB.mk_study_unit_code IN (SELECT DISTINCT mk_study_unit_code FROM tutorbooks"+sakaidb+")"+
    				" AND TB.mk_academic_year = "+ year+
    				" AND TB.mk_academic_period = "+ acadPeriod+
    				" AND sun.college_code = colleg.code"+
    				" AND sun.school_code = school.code"+
    				" AND school.college_code = colleg.code"+
    				" AND TB.mk_study_unit_code= sun.code"+
    				" AND TB.no_of_tutorbooks IS NOT NULL"+
    				" ORDER BY TB.mk_study_unit_code";
	    }
	    else
	    {
	    	
	    	strQuery = "SELECT DISTINCT u.MK_STUDY_UNIT_CODE AS  module, colleg.eng_description AS college, SCHOOL.ENG_DESCRIPTION AS school, dpt.ENG_DESCRIPTION AS department"+
	    	" FROM usrsun u, tutorbooks"+sakaidb+" TB, sun, colleg, school, dpt"+
	    	" where u.MK_STUDY_UNIT_CODE = TB.MK_STUDY_UNIT_CODE(+)"+
	    	" and TB.MK_ACADEMIC_YEAR(+) = u.MK_ACADEMIC_YEAR" +
	    	" and TB.MK_ACADEMIC_PERIOD(+) = u.MK_SEMESTER_PERIOD"+
	    	" and u.SYSTEM_TYPE = 'L'"+
	    	" and u.MK_ACADEMIC_YEAR="+ year+ 
	    	" and u.MK_SEMESTER_PERIOD="+ acadPeriod+
	    	" AND u.MK_STUDY_UNIT_CODE NOT IN (SELECT DISTINCT MK_STUDY_UNIT_CODE from tutorbooks"+sakaidb+" WHERE NO_OF_TUTORBOOKS IS NOT NULL AND MK_ACADEMIC_YEAR =" + year+ " AND MK_ACADEMIC_PERIOD = "+ acadPeriod+ ")"+
	    	" AND sun.college_code = colleg.code"+ 
	    	" AND  dpt.code = sun.MK_DEPARTMENT_CODE"+ 
	    	" AND sun.school_code = school.code"+
	    	" AND school.college_code = colleg.code"+
	    	" AND u.MK_STUDY_UNIT_CODE = sun.CODE"+
	    	" ORDER by u.MK_STUDY_UNIT_CODE";
	    	
	    }
							
		List queryList;
		queryList = jdbcTemplate.queryForList(strQuery);
		Iterator i = queryList .iterator();
	    
		while (i.hasNext())
		{	
			ListOrderedMap data = (ListOrderedMap) i.next();
			TutorPreBooksDetails tutorPreBooksDetails = new TutorPreBooksDetails();
				
			try
			{					
				tutorPreBooksDetails.setCollegeName(data.get("college").toString());
				tutorPreBooksDetails.setSchoolName(data.get("school").toString());
				tutorPreBooksDetails.setDepartmentName(data.get("department").toString());
				tutorPreBooksDetails.setModule(data.get("module").toString());
				if (completed == "true"){
					tutorPreBooksDetails.setNoOfTutorBooks(Integer.parseInt(data.get("no_of_tutorbooks").toString()));
					tutorPreBooksDetails.setModifiedBy(data.get("modifiedBy").toString());
				}
								
				listModules.add(tutorPreBooksDetails);
			}
			
		    catch(NullPointerException e ){e.printStackTrace();}
		 }
	    
	    return listModules;
	}


public String getDepartment(String courseCode) throws Exception{
	    
	    String  department = "";
		String sql = "select S.ENG_LONG_DESCRIPTI AS dpt from sun s , dpt d where    d.code = s.MK_DEPARTMENT_CODE  and s.code = '"+courseCode+"'";
		try{
			department = this.querySingleValue(sql,"dpt").toString();			
		} catch (Exception ex) {
        throw new Exception("TutorPreBooksSakaiDAO: getDepartment:  Error occurred / "+ ex,ex);
		}
		if(department.length()>27){
			department=department.substring(0,27);
		}
		return department;
	}

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

