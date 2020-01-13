package za.ac.unisa.lms.tools.maintainstaff.DAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import Gistl01h.Abean.Gistl01sProxyForGistf01m;
import za.ac.unisa.lms.db.StudentSystemDAO;
//import za.ac.unisa.lms.tools.maintainstaff.actions.prev;
//import za.ac.unisa.lms.tools.maintainstaff.actions.role;
import za.ac.unisa.lms.tools.maintainstaff.forms.CourseForm;
import za.ac.unisa.lms.tools.maintainstaff.forms.HeadingForm;
import za.ac.unisa.lms.tools.maintainstaff.forms.PersonForm;
import za.ac.unisa.lms.tools.maintainstaff.forms.RecordForm;
import za.ac.unisa.lms.tools.maintainstaff.forms.MainForm;
import za.ac.unisa.utils.CoursePeriodLookup;

public class MaintainStaffStudentDAO extends StudentSystemDAO{

private SessionManager sessionManager;
	private final static String NULLVALUEERROR = "Unexpected error occurred please try again.";
	/*
	 * USRSUN INDEX:
	 * SYSTEM_TYPE, NOVELL_USER_ID, MK_STUDY_UNIT_CODE, USER_SEQUENCE
	 */
	/**
	 * This method will test if valid course code was entered using table SUN.
	 *
	 * @param coursecode   	Course code that was entered
	 * @output courseExist		boolean true=valid; false=not valid
	*/
	public boolean courseExist(String courseCode) throws Exception {
		boolean courseExist = false;
		String noOfRecords;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

		String checkCourse = "SELECT COUNT(*) as NoOfRecords FROM   SUN WHERE  CODE =?";
		
		try{
			noOfRecords =(String) jdbcTemplate.queryForObject(checkCourse.toString(), new Object[]{courseCode.toUpperCase()}, String.class);
			} catch (Exception ex) {
            throw new Exception("MaintainStaffStudentDAO: courseExist: Error occurred / "+ ex,ex);
		}

		if (Integer.parseInt(noOfRecords) > 0) {
			courseExist = true;
		} else {
			courseExist = false;
		}

		return courseExist;
	}
	
	/**
	 * This method will test if college code of the course and college code of the linker matches .
	 *
	 * @param coursecode   	Course code that was entered
	 * @output courseExist		boolean true=valid; false=not valid
	*/
	public boolean checkCollegeCodeMatch(String courseCode,String networdId) throws Exception {
		boolean collegeCodeMatch = false;
		String courseCollegeCode=null;
		String linkerCollegeCode=null;
		String dept_code=null;
		String persno=null;
        
		//check the college code for the linker using staff network code
		String userDetailsOnNetworkId = "select d.college_code collegecode,s.mk_dept_code dept_code,s.persno persno from staff s, dpt d " +
						                    " where s.mk_dept_code=d.code and (s.resign_date is null or   s.resign_date > sysdate ) and s.NOVELL_USER_ID='"+networdId+"'";
		
		
		 try{
		    linkerCollegeCode = this.querySingleValue(userDetailsOnNetworkId,"collegecode");
			dept_code = this.querySingleValue(userDetailsOnNetworkId,"dept_code");
			persno = this.querySingleValue(userDetailsOnNetworkId,"persno");
           
			if ((null == linkerCollegeCode)||(linkerCollegeCode.length()==0)) {
		        //check the college code in usr for contractors 
				userDetailsOnNetworkId = "select d.college_code collegecode,u.PERSONNEL_NO personnelno, u.MK_DEPARTMENT_CODE dept_code " +
					                	"from usr u, dpt d where " +
  		                                " u.MK_DEPARTMENT_CODE=d.code and u.NOVELL_USER_CODE='"+networdId+"'";
				
				linkerCollegeCode = this.querySingleValue(userDetailsOnNetworkId,"collegecode");
				dept_code = this.querySingleValue(userDetailsOnNetworkId,"dept_code");
				persno = this.querySingleValue(userDetailsOnNetworkId,"personnelno");
				if(null==linkerCollegeCode || linkerCollegeCode.length()==0){
					linkerCollegeCode=null;
		      	}
		    }
		   }catch (Exception ex) {
	            throw new Exception("MaintainStaffStudentDAO: checkCollegeCodeMatch: Error occurred / "+ ex,ex);
			}
		
		   String collegeCodeForCourse = "select college_code from sun where code = '"+courseCode+"'";

			try{
				courseCollegeCode = this.querySingleValue(collegeCodeForCourse,"college_code");
				if(null==courseCollegeCode || courseCollegeCode.length()==0){
					courseCollegeCode=null;
				}

			} catch (Exception ex) {
	            throw new Exception("MaintainStaffStudentDAO: checkCollegeCodeMatch: Error occurred / "+ ex,ex);
			}

		
			if(dept_code.equalsIgnoreCase("777")){
				
			 String verifyPerson = "select COUNT(*) as collegeCodeMatch from dpt d, stafrc s where d.code=S.MK_DEPARTMENT_CODE and " +
			 		                " s.MK_PERSNO = "+persno+" and d.college_code = "+courseCollegeCode+""; 
			 
			  try{
				  String collegeCodeMatchCheck = this.querySingleValue(verifyPerson,"collegeCodeMatch");
				  if (Integer.parseInt(collegeCodeMatchCheck) > 0) {
					  collegeCodeMatch=true;
				  }else{
					  collegeCodeMatch=false;
				  }
				} catch (Exception ex) {
		          throw new Exception("MaintainStaffStudentDAO: courseExist (2): Error occurred / "+ ex,ex);
				}
			  return collegeCodeMatch;
			}else{
				if(courseCollegeCode != null && linkerCollegeCode!= null ){
					if(courseCollegeCode.equalsIgnoreCase(linkerCollegeCode)){
						collegeCodeMatch=true;
					}else{
						collegeCodeMatch=false;
					}
				}
			}
         return collegeCodeMatch;
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
	public boolean courseExist(String courseCode, String year, String period, String systemType) throws Exception {
		boolean courseExist = false;
		String noOfRecords;

		String sql1 = " SELECT COUNT(*) as NoOfRecords "+
		              " FROM   USRSUN "+
		              " WHERE  SYSTEM_TYPE = upper('"+systemType+"')"+
		              " AND    MK_STUDY_UNIT_CODE = UPPER('"+courseCode+"')"+
		              " AND    MK_ACADEMIC_YEAR = "+year+
		              " AND    MK_SEMESTER_PERIOD = "+period;

		try{
			noOfRecords = this.querySingleValue(sql1,"NoOfRecords");

		} catch (Exception ex) {
            throw new Exception("MaintainStaffStudentDAO: courseExist (2): Error occurred / "+ ex,ex);
		}

		if (Integer.parseInt(noOfRecords) > 0) {
			courseExist = true;
		} else {
			courseExist = false;
		}

		return courseExist;
	}
	
	/**
	 * This method will test if valid course code, year and semester combination was entered.
	 *
	 * @param coursecode   	Course code that was entered
	 * @param year 			Academic year
	 * @param period		Semester period
	 * @output courseExist		boolean true=valid; false=not valid
	*/
	public boolean courseValid(String courseCode, String year, String period,String selectedType) throws Exception {
		boolean courseValid = false;
		String courseType;

		String sql1 = " SELECT FK_SUNTYPCODE"+ 
				      " FROM   SUN"+
				      " WHERE  CODE = upper('"+courseCode+"')";

		try{
			courseType = this.querySingleValue(sql1,"FK_SUNTYPCODE");

		} catch (Exception ex) {
            throw new Exception("MaintainStaffStudentDAO: courseValid: Error occurred / "+ ex,ex);
		}
		if(selectedType.equalsIgnoreCase("E")){
			if (courseType.equals("15")) {
				courseValid = true;
			}else if (courseType.equals("12")) {
				if ((period.equals("6"))||(period.equals("10"))) {
					courseValid = true;
				}
			} else {
				if ((period.equals("1"))||(period.equals("6"))||(period.equals("10"))){
					courseValid = true;
				}			
			}
		}else{
		/* Course types:
		 * all except 12 and 15 = year course = allow period 0 and 6
		 * 12 = semester course = allow period 1 and 2
		 * 15 = both (year + semester) = allow all periods
		 */
	
		if (courseType.equals("15")) {
			courseValid = true;
		} else if (courseType.equals("12")) {
			if ((period.equals("1"))||(period.equals("2"))) {
				courseValid = true;
			}
		} else {
			if ((period.equals("0"))||(period.equals("6"))) {
				courseValid = true;
			}			
		}
		}
		return courseValid;
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
            throw new Exception("MaintainStaffStudentDAO: courseExpired: Error occurred / "+ ex,ex);
		}
		
		return courseValid;
	}
	
	/**
	 * This method will test if person is unique for course, year and period
	 *
	 * @param coursecode   	Course code that was entered
	 * @param year			Academic year
	 * @param period		Semester period
	 * @param person		Novell user id	
	 * @param systemType	L = teaching roles, X = Examination functions.
	 * @output isDuplicate		boolean true=valid; false=not valid
	*/
	public boolean isDuplicatePerson(String courseCode, String year, String period, String person, String systemType,String paperNr) throws Exception {
		boolean isDuplicate = false;
		String noOfRecords;

		String sql1 = " SELECT COUNT(*) as NoOfRecords "+
		              " FROM   USRSUN "+
		              " WHERE  SYSTEM_TYPE = upper('"+systemType+"')" +
		              " AND    MK_STUDY_UNIT_CODE = UPPER('"+courseCode+"')"+
		              " AND    MK_ACADEMIC_YEAR = "+year+
		              " AND    MK_SEMESTER_PERIOD = "+period+
		              " AND     UPPER(NOVELL_USER_ID) = UPPER('"+person+"')";
		if (systemType.equals("E")) {
		    sql1 = sql1+" AND    USRSUN.NR = "+paperNr;
		}
		
		              
		              
		try{
			noOfRecords = this.querySingleValue(sql1,"NoOfRecords");

		} catch (Exception ex) {
            throw new Exception("MaintainStaffStudentDAO: isDuplicatePerson: Error occurred / "+ ex,ex);
		}

		if (Integer.parseInt(noOfRecords) > 0) {
			isDuplicate = true;
		} else {
			isDuplicate = false;
		}

		return isDuplicate;
	}
	
	/**
	 * This method will test if valid network code was entered.
	 *
	 * @param mainForm   	Form with coursecode and network code
	 * @output personExist		boolean true=valid; false=not valid
	*/
	public boolean personExist(MainForm mainForm) throws Exception,EmptyResultDataAccessException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(super.getDataSource());
		boolean personExist = false;
		String name;

		// First check if person exists in table staff
         String findPersonInStaff = "SELECT nvl(TITLE||' '||SURNAME||' '||INITIALS, ' ') AS NAME FROM   STAFF WHERE NOVELL_USER_ID =?";
		
		try{
			String tmpName =(String) jdbcTemplate.queryForObject(findPersonInStaff.toString(), new Object[]{mainForm.getPerson().toUpperCase()}, String.class);
			if ((null == tmpName)||(tmpName.length()==0)) {
				name = "0";
			} else {
				name = tmpName;
			}
			// no records were found for person in STAFF, check in table USR
			if (name.equals("0")) {
				String findPersonInUsr = "SELECT USER_CODE_OWNER FROM   USR WHERE NOVELL_USER_CODE=?";
				
				 tmpName =(String) jdbcTemplate.queryForObject(findPersonInUsr.toString(), new Object[]{mainForm.getPerson().toUpperCase()}, String.class);
				 if ((null == tmpName)||(tmpName.length()==0)) {
						name = "0";
					} else {
						name = tmpName;
					}
			}
		} catch (EmptyResultDataAccessException  ex) {
			name = "0";
           // throw new Exception("MaintainStaffStudentDAO: networkExist: Error occurred / "+ ex,ex);
		}
		if (name.equals("0")) {
			personExist = false;
		} else {
			personExist = true;
			mainForm.setPersonName(name);
		}

		return personExist;
	}
	
	/**
	 * This method will return staff member name
	 *
	 * @param networkCode   	Form with coursecode and network code
	 * @output name				person name
	*/
	public String personExist(String networkCode) throws Exception {
		String name = "";
		String tmpName = "";

		String sql2 = "SELECT USER_CODE_OWNER "+
		              "FROM   USR "+
		              "WHERE  UPPER(NOVELL_USER_CODE) = upper('"+networkCode+"') ";
		
		try{
			tmpName = this.querySingleValue(sql2,"USER_CODE_OWNER");
			if ((null == tmpName)||(tmpName.length()==0)) {
				name = "0";
			} else {
				name = tmpName;
			}

		} catch (Exception ex) {
	      throw new Exception("MaintainStaffStudentDAO: personExist {table usr}: Error occurred / "+ ex,ex);
		}		

		return name;
	}
	
	/**
	 * This method will check for duplicate staff records and return correct staff number;
	 *
	 * @param networkCode   	network code
	 * @output errorMessage		errormessage, staff not found etc.
	 * 	*/
	public String staffValidation(String networkCode) throws Exception {
		String errorMessage= "";

		Gistl01sProxyForGistf01m op = new Gistl01sProxyForGistf01m();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();
		
		networkCode = networkCode.toUpperCase();
		
		op.setInStaffNovellUserId(networkCode);
		op.execute();
		
		if (opl.getException() != null) {
	      	throw opl.getException();
	    }
	    if (op.getExitStateType() < 3) {
	       	throw new Exception(op.getExitStateMsg());
	    }

	    errorMessage = op.getOutCsfStringsString500();
	    	    
	    String tmpNovell = op.getInStaffNovellUserId();
	    int tmpPersnr = op.getOutStaffPersno();
	    String tmpPersnrStr = Integer.toString(tmpPersnr);
	    String name = op.getOutStaffTitle() +" "+op.getOutStaffSurname()+" "+op.getOutStaffInitials();
	    
		return errorMessage;
	}
	
	/**
	 * This method will return specific person from USRSUN
	 *
	 * @param course Form   	Form with coursecode and network code
	 * @param role				Role/function code
	*/
	public void selectPerson(CourseForm courseFormIn, String role) throws Exception {
		CourseForm courseForm = (CourseForm) courseFormIn;
		
		// First check if person exists in table staff
		String sql1 = " SELECT NOVELL_USER_ID, NVL(PERSNO,'0') AS PERSNO "+
					  " FROM   USRSUN "+
					  " WHERE  SYSTEM_TYPE = upper('"+courseForm.getSelectedView()+"')" +
					  " AND    MK_STUDY_UNIT_CODE = UPPER('"+courseForm.getCourse()+"')" +
					  " AND    MK_ACADEMIC_YEAR = "+courseForm.getYear() +
					  " AND    MK_SEMESTER_PERIOD = "+courseForm.getAcadPeriod()+
					  " AND    ACCESS_LEVEL = upper('"+role+"')";

		try{
			JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List courseInfoList = jdt.queryForList(sql1);
			Iterator j = courseInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			String networkCode = data.get("NOVELL_USER_ID").toString();
    			String persno = data.get("PERSNO").toString();
	
				if ((null == networkCode)||(networkCode.length()==0)) {
					courseForm.setPrimlName("No Primary Lecturer");
				} else {
					courseForm.setPrimlNetworkCode(networkCode);
					courseForm.setPrimlStaffNo(persno);
					// select name
					selectPrimPersonName(courseForm);
					//courseForm.setPrimlName(personExist(networkCode));
				}
    		}

		} catch (Exception ex) {
            throw new Exception("MaintainStaffStudentDAO: selectPerson: Error occurred / "+ ex,ex);
		}
	}
	
	/**
	 * This method will select all the persons and their role/function for a course
	 *
	 * @param courseForm   	Course code that was entered
	*/
	public void selectCourseList(CourseForm courseFormIn) throws Exception {
		
		CourseForm courseForm = (CourseForm) courseFormIn;
		ArrayList courseList = new ArrayList();
		ArrayList expandedPeriods = new ArrayList();
		ArrayList periodHeadings = new ArrayList();
		String prevAcadperiod = "";
		
		ArrayList tmpPeriodHeadings = new ArrayList();
		if ((null != courseForm.getPeriodHeadings())&&(courseForm.getPeriodHeadings().size() > 0)) {
			tmpPeriodHeadings = courseForm.getPeriodHeadings();
		}
		
		String roles = "";
		// which roles must be selected?
		if (courseForm.getSelectedView().equals("L")) {
			// select roles for teaching roles - category 24
			roles = selectRoleOptionsString(24,"'COD'");		
			
		}else if (courseForm.getSelectedView().equals("J")) {
			// select roles for teaching roles - category 24
			roles = selectRoleOptionsString(217,"");		
			
		}else {
			// select roles for examination functions
			roles = selectRoleOptionsString(80,"");
		}
		
		String select = "SELECT USRSUN.MK_ACADEMIC_YEAR as AY, USRSUN.MK_SEMESTER_PERIOD as SP,USRSUN.ACCESS_LEVEL as AL, " +
						"USRSUN.NOVELL_USER_ID as NU, nvl(USRSUN.PERSNO,'1') AS PN, USRSUN.USER_SEQUENCE AS US,nvl(USRSUN.NR,'99') as PAPERNR "+
						"FROM   USRSUN "+
						"WHERE  SYSTEM_TYPE = upper('"+courseForm.getSelectedView()+"') " +
						 "AND USRSUN.MK_ACADEMIC_YEAR = "+courseForm.getYear()+" " +
						 "AND  USRSUN.MK_SEMESTER_PERIOD = "+courseForm.getAcadPeriod()+" "+
						"AND    USRSUN.MK_STUDY_UNIT_CODE = UPPER('"+courseForm.getCourse()+"') "+
						"AND    USRSUN.ACCESS_LEVEL IN ("+roles+") ";
		
		if (courseForm.getSelectedView().equals("L")) {
			select = select+" ORDER BY USRSUN.MK_ACADEMIC_YEAR DESC, USRSUN.MK_SEMESTER_PERIOD, DECODE(ACCESS_LEVEL,'PRIML',1,'SECDL',2,3,4),USRSUN.NOVELL_USER_ID";
		} else {
			select = select+" ORDER BY USRSUN.MK_ACADEMIC_YEAR DESC, USRSUN.MK_SEMESTER_PERIOD, USRSUN.NR, USRSUN.ACCESS_LEVEL, USRSUN.NOVELL_USER_ID ";
		}
		
		
    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List courseInfoList = jdt.queryForList(select);
			Iterator j = courseInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			String acadYearTmp = data.get("AY").toString();
    			String semPerTmp = data.get("SP").toString();
    			
    			RecordForm record = new RecordForm();
    			record.setCourse(courseForm.getCourse().toUpperCase());
    			record.setAcadYear(data.get("AY").toString());
    			record.setSemesterPeriod(data.get("SP").toString());
    			record.setRole(data.get("AL").toString());
    			record.setPrevRole(data.get("AL").toString());
    			record.setNetworkCode(data.get("NU").toString());
    			record.setStaffNo(data.get("PN").toString());
    			record.setPersonSequence(Integer.parseInt(data.get("US").toString()));
    			if(data.get("PAPERNR").toString().equals("99")){
    				record.setPaperNo("1");
    			}else{
    			record.setPaperNo(data.get("PAPERNR").toString());
    			}
    			record.setAcadPeriodDesc(record.getAcadPeriodDescription(courseForm.getSelectedView()));

   				selectPersonName(record);
   				
   				boolean assignout= validateAssignOut(record.getNetworkCode(),record.getAcadYear(),record.getCourse(),record.getSemesterPeriod());
   				if(assignout==true){
   					record.setPercentage("Y");
   				}else{
   					record.setPercentage("N");
   				}  				
   				// set role description
   				if (courseForm.getSelectedView().equals("L")) {
   					record.setRoleDesc(selectRoleDescription(record.getRole(),24));
   				} else {
   					record.setRoleDesc(selectRoleDescription(record.getRole(),80));
   				}

   				String tmpExpand = "YES";

   				// set period group headings
    			if ((prevAcadperiod.equals(""))||(!prevAcadperiod.endsWith(record.getAcadPeriodDesc()))) {
    				prevAcadperiod = record.getAcadPeriodDesc();
    				
    				// code to keep expand/collapse history
    				if ((null != tmpPeriodHeadings)&&(tmpPeriodHeadings.size() > 0)) {
    					for (int i=0; i < tmpPeriodHeadings.size(); i++) {
    						HeadingForm headingForm = (HeadingForm) tmpPeriodHeadings.get(i);
    						if (headingForm.getPeriodDesc().equals(record.getAcadPeriodDesc())) {
    							tmpExpand = headingForm.getExpand();
    						}
    					}
    				}
    				HeadingForm headingForm = new HeadingForm();
    				headingForm.setAcadPeriod(record.getSemesterPeriod());
    				headingForm.setAcadYear(record.getAcadYear());
    				headingForm.setPeriodDesc(record.getAcadPeriodDesc());
    				headingForm.setExpand(tmpExpand);
    				headingForm.setCourse(record.getCourse());
    				
    				//periodHeadings.add(new org.apache.struts.util.LabelValueBean(record.getAcadPeriodDesc(), tmpExpand));
    				periodHeadings.add(headingForm);
    			}
    			   			   			
    			courseList.add(record);
    			courseForm.setPeriodHeadings(periodHeadings);
    			
    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("MaintainStaffStudentDAO: selectCourseList(courseForm): Error occurred / "+ ex,ex);
    	} // end try

    	courseForm.setCourseList(courseList);

	}
	
	
	/**
	 * This method will select all the persons and their role/function for a course
	 *
	 * @param courseForm   	Course code that was entered
	*/
	public void selectCourseList(PersonForm personFormIn) throws Exception {
		
		PersonForm personForm = (PersonForm) personFormIn;
		ArrayList courseList = new ArrayList();
		ArrayList expandedPeriods = new ArrayList();
		ArrayList periodHeadings = new ArrayList();
		String prevAcadperiod = "";
		
		ArrayList tmpPeriodHeadings = new ArrayList();
		if ((null != personForm.getPeriodHeadings())&&(personForm.getPeriodHeadings().size() > 0)) {
			tmpPeriodHeadings = personForm.getPeriodHeadings();
		}
		
		
		String roles = "";

		// which roles must be selected?
		if (personForm.getSelectedView().equals("L")) {
			// select roles for teaching roles - category 24
			roles = selectRoleOptionsString(24,"'COD'");		
			
		} else if (personForm.getSelectedView().equals("J")) {
			// select marking roles - category 217
			roles = selectRoleOptionsString(217,"");				
		} else  {
			// select roles for examination functions
			roles = selectRoleOptionsString(80,"");
		}

		String select = "SELECT USRSUN.MK_ACADEMIC_YEAR as AY, USRSUN.MK_SEMESTER_PERIOD as SP,USRSUN.ACCESS_LEVEL as AL, " +
						"USRSUN.MK_STUDY_UNIT_CODE as SU, nvl(USRSUN.PERSNO,'1') AS PN, USRSUN.USER_SEQUENCE AS US, nvl(USRSUN.NR,'99') as PAPERNR "+
						"FROM   USRSUN "+
						"WHERE  SYSTEM_TYPE = '"+personForm.getSelectedView()+"' "+
						"AND    upper(USRSUN.NOVELL_USER_ID) = UPPER('"+personForm.getPersonNetworkCode()+"') "+
						"AND    USRSUN.ACCESS_LEVEL IN ("+roles+") ";
		if (personForm.getSelectedView().equals("E")) {
			select = select+" ORDER BY USRSUN.MK_ACADEMIC_YEAR DESC, USRSUN.MK_SEMESTER_PERIOD, USRSUN.MK_STUDY_UNIT_CODE, USRSUN.NR, USRSUN.ACCESS_LEVEL, USRSUN.NOVELL_USER_ID ";
		} else {
			select = select+" ORDER BY USRSUN.MK_ACADEMIC_YEAR DESC, USRSUN.MK_SEMESTER_PERIOD, USRSUN.MK_STUDY_UNIT_CODE, USRSUN.ACCESS_LEVEL, USRSUN.NOVELL_USER_ID ";
		}
		
		selectPersonName(personForm.getPersonNetworkCode());
		
		try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List courseInfoList = jdt.queryForList(select);
			Iterator j = courseInfoList.iterator();
			
			/*record.setNetworkCode(personForm.getPersonNetworkCode());
			selectPersonName(record);*/
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			RecordForm record = new RecordForm();
    			String acadYearTmp = data.get("AY").toString();
    			String semPerTmp = data.get("SP").toString();
    			record.setNetworkCode(personForm.getPersonNetworkCode());
    			record.setAcadYear(data.get("AY").toString());
    			record.setSemesterPeriod(data.get("SP").toString());
    			record.setCourse(data.get("SU").toString());
    			record.setRole(data.get("AL").toString());
    			record.setPrevRole(data.get("AL").toString());
    			record.setStaffNo(data.get("PN").toString());
    			record.setPersonSequence(Integer.parseInt(data.get("US").toString()));
    			if((data.get("PAPERNR").toString().equals("99"))){
    				record.setPaperNo("1");
        			}else{
        			record.setPaperNo(data.get("PAPERNR").toString());
        			}
    			    			
    			record.setAcadPeriodDesc(record.getAcadPeriodDescription(personForm.getSelectedView()));

   				//selectPersonName(record);
   				
   				// set role description
   				if (personForm.getSelectedView().equals("L")) {
   					// teaching roles view
   					record.setRoleDesc(selectRoleDescription(record.getRole(),24));
   				}else if (personForm.getSelectedView().equals("J")) {
   					// teaching roles view
   					record.setRoleDesc(selectRoleDescription(record.getRole(),217));
   				}else {
   					// examination functions view
   					record.setRoleDesc(selectRoleDescription(record.getRole(),80));
   				}

   				String tmpExpand = "NO";
   				// set period group headings
    			if ((prevAcadperiod.equals(""))||(!prevAcadperiod.endsWith(record.getAcadPeriodDesc()))) {
    				prevAcadperiod = record.getAcadPeriodDesc();
    				
    				// code to keep expand/collapse history
    				if ((null != tmpPeriodHeadings)&&(tmpPeriodHeadings.size() > 0)) {
    					for (int i=0; i < tmpPeriodHeadings.size(); i++) {
    						HeadingForm headingForm = (HeadingForm) tmpPeriodHeadings.get(i);
    						if (headingForm.getPeriodDesc().equals(record.getAcadPeriodDesc())) {
    							tmpExpand = headingForm.getExpand();
    						}
    					}
    				}
    				HeadingForm headingForm = new HeadingForm();
    				headingForm.setAcadPeriod(record.getSemesterPeriod());
    				headingForm.setAcadYear(record.getAcadYear());
    				headingForm.setPeriodDesc(record.getAcadPeriodDesc());
    				headingForm.setExpand(tmpExpand);
    				headingForm.setCourse(record.getCourse());
    				
    				//periodHeadings.add(new org.apache.struts.util.LabelValueBean(record.getAcadPeriodDesc(), tmpExpand));
    				periodHeadings.add(headingForm);
    			}
    			   			   			
    			courseList.add(record);
    			personForm.setPeriodHeadings(periodHeadings);
    			
    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("MaintainStaffStudentDAO: selectCourseList(personForm): Error occurred / "+ ex,ex);
    	} // end try

		personForm.setCourseList(courseList);

	}
	
	
	/**
	 * This method will select the personal detail of a person, firstly through proxy Gistl01sProxyForGistf01m,
	 * if not found check in table USR
	 *
	 * @param recordIn	   	 	recordForm
	*/
	public void selectPersonName(String networkId) throws Exception {
		
		RecordForm record = new RecordForm();
		String errorMessage = "";
				
		/* ********************************************************************* */
		/*  First try and select staff detail from table STAFF using this proxy */
		Gistl01sProxyForGistf01m op = new Gistl01sProxyForGistf01m();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();
		
		record.setNetworkCode(networkId.toUpperCase());
		
		op.setInStaffNovellUserId(record.getNetworkCode());
		op.execute();
		
		if (opl.getException() != null) {
	      	throw opl.getException();
	    }
	    if (op.getExitStateType() < 3) {
	       	throw new Exception(op.getExitStateMsg());
	    }

	    errorMessage = op.getOutCsfStringsString500();
	    	    
	    String tmpNovell = op.getInStaffNovellUserId();
	    int tmpPersnr = op.getOutStaffPersno();
	    record.setStaffNo(Integer.toString(tmpPersnr));
	    record.setName(op.getOutStaffTitle() +" "+op.getOutStaffSurname()+" "+op.getOutStaffInitials());

	    // check resignation date
	    Calendar tmpResignDate1 = op.getOutStaffResignDate();
	    // get current date
		GregorianCalendar calCurrent = new GregorianCalendar();
		int currentDay = calCurrent.get(Calendar.DAY_OF_MONTH);
		int currentMonth = calCurrent.get(Calendar.MONTH) + 1;
		int currentYear = calCurrent.get(Calendar.YEAR);

		String currentDayStr = "";
		String currentMonthStr = "";
		if (currentDay <= 9) {
			currentDayStr = "0"+currentDay;
		}else {
			currentDayStr = Integer.toString(currentDay);
		}
		if (currentMonth <= 9) {
			currentMonthStr = "0"+currentMonth;
		}else {
			currentMonthStr = Integer.toString(currentMonth);
		}
		String currentDate = currentDayStr+"-"+currentMonthStr+"-"+currentYear;

		int result = 0;
		if (tmpResignDate1 != null) {
			result = tmpResignDate1.compareTo(calCurrent);
		}
		if (result <= -1) {
			record.setStatus("Inactive");
		} else {
			record.setStatus("Active");
		}
	    
		/* ********************************************************************* */
		/*  If staff was not found in table STAFF, try table USR                 */
		record.setName(ltrim(record.getName()));
		record.setName(rtrim(record.getName()));
		if ((record.getName() == null)||(record.getName().equals(""))) {
			String select = " SELECT USER_CODE_OWNER FROM USR WHERE NOVELL_USER_CODE = upper('"+record.getNetworkCode()+"') ";

			try{
			String tmpName = this.querySingleValue(select,"USER_CODE_OWNER");
			if (tmpName != null) {
				record.setName(tmpName);
			} else {
				record.setName("Not found");
			}
			record.setStatus("Active");
			
			} catch (Exception ex) {
				throw new Exception("MaintainStaffStudentDAO: selectPersonName (from table USR): Error occurred / "+ ex,ex);
			} // end try
		}
	}

	/**
	 * This method will select all role/function options
	 *
	 * @param category   	category that roles must be selected for
	 * @param excludeRoles	roles that must be excluded from the select
	 * @output roleOptoins	Arraylist of available roles/functions.
	 * 24 = teaching roles
	 * 80 = examination functions
	*/
	public ArrayList selectRoleOptions(int category, String excludeRoles) throws Exception {
		ArrayList roleOptions = new ArrayList();
		roleOptions.add(new org.apache.struts.util.LabelValueBean("..",""));
		
		String select = " SELECT CODE, ENG_DESCRIPTION " +
						" FROM   GENCOD" +
						" WHERE  FK_GENCATCODE = "+category+
						" AND    IN_USE_FLAG = 'Y' ";
		if (!excludeRoles.equals("")) {
			select = select + " AND    CODE NOT IN ("+excludeRoles+") ORDER BY ENG_DESCRIPTION,CODE ";
		}
		
    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List courseInfoList = jdt.queryForList(select);
			Iterator j = courseInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			roleOptions.add(new org.apache.struts.util.LabelValueBean(data.get("ENG_DESCRIPTION").toString(),data.get("CODE").toString()));
    		
    			
    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("MaintainStaffStudentDAO: selectRoleOptions: Error occurred / "+ ex,ex);
    	} // end try

		return roleOptions;
	}
	
	
	/**
	 * This method will select all role/function options in a String
	 *
	 * @param category   	category that roles must be selected for
	 * @param excludeRoles	roles that must be excluded from the select
	 * @output roleOptions	Returns string of role/function options
	 * 24 = teaching roles
	*/
	public String selectRoleOptionsString(int category, String excludeRoles) throws Exception {
		String roleOptions = "";
		
		String select = " SELECT CODE, ENG_DESCRIPTION " +
						" FROM   GENCOD" +
						" WHERE  FK_GENCATCODE = "+category+
						" AND    IN_USE_FLAG = 'Y'";
		if (!excludeRoles.equals("")) {
			select = select + " AND    CODE NOT IN ("+excludeRoles+")";
		}
		
    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List courseInfoList = jdt.queryForList(select);
			Iterator j = courseInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			
    			// build String
    			if (roleOptions.equals("")) {
    				roleOptions = "'"+data.get("CODE").toString()+"'";
    			} else {
    				roleOptions = roleOptions+",'"+data.get("CODE").toString()+"'";
    			}
    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("MaintainStaffStudentDAO: selectRoleOptionsString: Error occurred / "+ ex,ex);
    	} // end try

		return roleOptions;
	}

	/**
	 * This method will select  Role/function Description
	 *
	 * @param role			role/function id
	 * @param category   	category that roles must be selected for
	 * 24 = teaching roles
	*/
	public String selectRoleDescription(String role, int category) throws Exception {
		String desc = "";
		
		String select = " SELECT ENG_DESCRIPTION " +
						" FROM   GENCOD" +
						" WHERE  FK_GENCATCODE = "+category+
						" AND    CODE = '"+role+"' ";
		
    	try{
    		desc = this.querySingleValue(select,"ENG_DESCRIPTION");

    	} catch (Exception ex) {
    		throw new Exception("MaintainStaffStudentDAO: selectRoleDescription: Error occurred / "+ ex,ex);
    	} // end try

		return desc;
	}
	
	
	/**
	 * This method will select the personal detail of a person, firstly through proxy Gistl01sProxyForGistf01m,
	 * if not found check in table USR
	 *
	 * @param recordIn	   	 	recordForm
	*/
	public void selectPersonName(RecordForm recordIn) throws Exception {
		
		RecordForm record = (RecordForm) recordIn;
		String errorMessage = "";
				
		/* ********************************************************************* */
		/*  First try and select staff detail from table STAFF using this proxy */
		Gistl01sProxyForGistf01m op = new Gistl01sProxyForGistf01m();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();
		
		record.setNetworkCode(record.getNetworkCode().toUpperCase());
		
		op.setInStaffNovellUserId(record.getNetworkCode());
		op.execute();
		
		if (opl.getException() != null) {
	      	throw opl.getException();
	    }
	    if (op.getExitStateType() < 3) {
	       	throw new Exception(op.getExitStateMsg());
	    }

	    errorMessage = op.getOutCsfStringsString500();
	    	    
	    String tmpNovell = op.getInStaffNovellUserId();
	    int tmpPersnr = op.getOutStaffPersno();
	    record.setStaffNo(Integer.toString(tmpPersnr));
	    record.setName(op.getOutStaffTitle() +" "+op.getOutStaffSurname()+" "+op.getOutStaffInitials());

	    // check resignation date
	    Calendar tmpResignDate1 = op.getOutStaffResignDate();
	    // get current date
		GregorianCalendar calCurrent = new GregorianCalendar();
		int currentDay = calCurrent.get(Calendar.DAY_OF_MONTH);
		int currentMonth = calCurrent.get(Calendar.MONTH) + 1;
		int currentYear = calCurrent.get(Calendar.YEAR);

		String currentDayStr = "";
		String currentMonthStr = "";
		if (currentDay <= 9) {
			currentDayStr = "0"+currentDay;
		}else {
			currentDayStr = Integer.toString(currentDay);
		}
		if (currentMonth <= 9) {
			currentMonthStr = "0"+currentMonth;
		}else {
			currentMonthStr = Integer.toString(currentMonth);
		}
		String currentDate = currentDayStr+"-"+currentMonthStr+"-"+currentYear;

		int result = 0;
		if (tmpResignDate1 != null) {
			result = tmpResignDate1.compareTo(calCurrent);
		}
		if (result <= -1) {
			record.setStatus("Inactive");
		} else {
			record.setStatus("Active");
		}
	    
		/* ********************************************************************* */
		/*  If staff was not found in table STAFF, try table USR                 */
		record.setName(ltrim(record.getName()));
		record.setName(rtrim(record.getName()));
		if ((record.getName() == null)||(record.getName().equals(""))) {
			String select = " SELECT USER_CODE_OWNER FROM USR WHERE NOVELL_USER_CODE = upper('"+record.getNetworkCode()+"') ";

			try{
			String tmpName = this.querySingleValue(select,"USER_CODE_OWNER");
			if (tmpName != null) {
				record.setName(tmpName);
			} else {
				record.setName("Not found");
			}
			record.setStatus("Active");
			
			} catch (Exception ex) {
				throw new Exception("MaintainStaffStudentDAO: selectPersonName (from table USR): Error occurred / "+ ex,ex);
			} // end try
		}
	}
	
	
	/**
	 * This method will select the personnel number of a person USING PROXY Gistl01sProxyForGistf01m
	 *
	 * @param networkCode   	network code that was entered
	 * @output staffNumber		Staff number String
	*/
	public String selectStaffPersno(String networkCode) throws Exception {
		
		String errorMessage = "";
		String staffNumber = "";
				
		/* ********************************************************************* */
		/*  First try and select staff detail from table STAFF using this proxy */
		Gistl01sProxyForGistf01m op = new Gistl01sProxyForGistf01m();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();
		
		networkCode = networkCode.toUpperCase();
		
		op.setInStaffNovellUserId(networkCode);
		op.execute();
		
		if (opl.getException() != null) {
	      	throw opl.getException();
	    }
	    if (op.getExitStateType() < 3) {
	       	throw new Exception(op.getExitStateMsg());
	    }

	    errorMessage = op.getOutCsfStringsString500();
	    int staffNr = op.getOutStaffPersno();
	    staffNumber = Integer.toString(staffNr);
	    	    
		return staffNumber;
	}
	
	
	/**
	 * This method will select the personal detail of primary lecturer
	 *
	 * @param courseIn   		CourseForm
	*/
	public void selectPersonName(CourseForm courseIn) throws Exception {
		
		CourseForm courseForm = (CourseForm) courseIn;
		String errorMessage = "";
		
		/* ********************************************************************* */
		/*  First try and select staff detail from table STAFF using this proxy */
		Gistl01sProxyForGistf01m op = new Gistl01sProxyForGistf01m();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();
		
		courseForm.setNewPrimlNetworkCode(courseForm.getNewPrimlNetworkCode().toUpperCase());
		
		op.setInStaffNovellUserId(courseForm.getNewPrimlNetworkCode());
		op.execute();
		
		if (opl.getException() != null) {
	      	throw opl.getException();
	    }
	    if (op.getExitStateType() < 3) {
	       	throw new Exception(op.getExitStateMsg());
	    }

	    errorMessage = op.getOutCsfStringsString500();
	    	    
	    String tmpNovell = op.getInStaffNovellUserId();
	    int tmpPersnr = op.getOutStaffPersno();
	    courseForm.setNewPrimlStaffNo(Integer.toString(tmpPersnr));
	    courseForm.setNewPrimlName(op.getOutStaffTitle() +" "+op.getOutStaffSurname()+" "+op.getOutStaffInitials());

	    // check resignation date
	    Calendar tmpResignDate1 = op.getOutStaffResignDate();
	    // get current date
		GregorianCalendar calCurrent = new GregorianCalendar();
		int currentDay = calCurrent.get(Calendar.DAY_OF_MONTH);
		int currentMonth = calCurrent.get(Calendar.MONTH) + 1;
		int currentYear = calCurrent.get(Calendar.YEAR);

		String currentDayStr = "";
		String currentMonthStr = "";
		if (currentDay <= 9) {
			currentDayStr = "0"+currentDay;
		}else {
			currentDayStr = Integer.toString(currentDay);
		}
		if (currentMonth <= 9) {
			currentMonthStr = "0"+currentMonth;
		}else {
			currentMonthStr = Integer.toString(currentMonth);
		}
		String currentDate = currentDayStr+"-"+currentMonthStr+"-"+currentYear;

		int result = 0;
		if (tmpResignDate1 != null) {
			result = tmpResignDate1.compareTo(calCurrent);
		}
		if (result <= -1) {
			courseForm.setNewPrimlStatus("Inactive");
		} else {
			courseForm.setNewPrimlStatus("Active");
		}
	    		
		/* ********************************************************************* */
		/*  If staff was not found in table STAFF, try table USR                 */
		courseForm.setNewPrimlName(ltrim(courseForm.getNewPrimlName()));
		courseForm.setNewPrimlName(rtrim(courseForm.getNewPrimlName()));
		if ((courseForm.getNewPrimlName() == null)||(courseForm.getNewPrimlName().equals(""))) {
			// use network code to select name in table USR
			String select = " SELECT USER_CODE_OWNER FROM USR WHERE NOVELL_USER_CODE = upper('"+courseForm.getNewPrimlNetworkCode()+"') ";

			try{
			String tmpName = this.querySingleValue(select,"USER_CODE_OWNER");
			if (tmpName != null) {
				courseForm.setNewPrimlName(tmpName);
			} else {
				courseForm.setNewPrimlName("Not Found");
			}
			courseForm.setNewPrimlStatus("Active");
			
			} catch (Exception ex) {
				throw new Exception("MaintainStaffStudentDAO: selectPersonName (table USR): Error occurred / "+ ex,ex);
			} // end try			
		}

	}
	
	/**
	 * This method will select the personal detail of primary lecturer
	 *
	 * @param coursecode   	Course code that was entered
	 * @output courseExist		boolean true=valid; false=not valid
	*/
	public void selectPrimPersonName(CourseForm courseIn) throws Exception {
		
		CourseForm courseForm = (CourseForm) courseIn;
		String errorMessage = "";
		
		/* ********************************************************************* */
		/*  First try and select staff detail from table STAFF using this proxy */
		Gistl01sProxyForGistf01m op = new Gistl01sProxyForGistf01m();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();
		
		courseForm.setPrimlNetworkCode(courseForm.getPrimlNetworkCode().toUpperCase());
		
		op.setInStaffNovellUserId(courseForm.getPrimlNetworkCode());
		op.execute();
		
		if (opl.getException() != null) {
	      	throw opl.getException();
	    }
	    if (op.getExitStateType() < 3) {
	       	throw new Exception(op.getExitStateMsg());
	    }

	    errorMessage = op.getOutCsfStringsString500();
	    	    
	    String tmpNovell = op.getInStaffNovellUserId();
	    int tmpPersnr = op.getOutStaffPersno();
	    courseForm.setPrimlStaffNo(Integer.toString(tmpPersnr));
	    courseForm.setPrimlName(op.getOutStaffTitle() +" "+op.getOutStaffSurname()+" "+op.getOutStaffInitials());

	    // check resignation date
	    Calendar tmpResignDate1 = op.getOutStaffResignDate();
	    // get current date
		GregorianCalendar calCurrent = new GregorianCalendar();
		int currentDay = calCurrent.get(Calendar.DAY_OF_MONTH);
		int currentMonth = calCurrent.get(Calendar.MONTH) + 1;
		int currentYear = calCurrent.get(Calendar.YEAR);

		String currentDayStr = "";
		String currentMonthStr = "";
		if (currentDay <= 9) {
			currentDayStr = "0"+currentDay;
		}else {
			currentDayStr = Integer.toString(currentDay);
		}
		if (currentMonth <= 9) {
			currentMonthStr = "0"+currentMonth;
		}else {
			currentMonthStr = Integer.toString(currentMonth);
		}
		String currentDate = currentDayStr+"-"+currentMonthStr+"-"+currentYear;

		int result = 0;
		if (tmpResignDate1 != null) {
			result = tmpResignDate1.compareTo(calCurrent);
		}
		if (result <= -1) {
			courseForm.setPrimlStatus("Inactive");
		} else {
			courseForm.setPrimlStatus("Active");
		}
	    		
		/* ********************************************************************* */
		/*  If staff was not found in table STAFF, try table USR                 */
		courseForm.setPrimlName(ltrim(courseForm.getPrimlName()));
		courseForm.setPrimlName(rtrim(courseForm.getPrimlName()));
		if ((courseForm.getPrimlName() == null)||(courseForm.getPrimlName().equals(""))) {
			// use network code to select name in table USR
			String select = " SELECT USER_CODE_OWNER FROM USR WHERE NOVELL_USER_CODE = upper('"+courseForm.getNewPrimlNetworkCode()+"') ";

			try{
			String tmpName = this.querySingleValue(select,"USER_CODE_OWNER");
			if (tmpName != null) {
				courseForm.setPrimlName(tmpName);
			} else {
				courseForm.setPrimlName("Not Found");
			}
			
			} catch (Exception ex) {
				throw new Exception("MaintainStaffStudentDAO: selectPersonName (table USR): Error occurred / "+ ex,ex);
			} // end try			
		}

	}
	
	/** Remove selected in Course Display */
	public void deleteRoles(String networkId, String course, String aYear, String aPeriod, String role, int uSequence ,String paperNr,CourseForm courseForm) throws Exception{

		networkId = networkId.toUpperCase();
		String sql1 = " DELETE FROM USRSUN " +
					  " WHERE  NOVELL_USER_ID = upper('"+networkId+"') " +
					  " AND    MK_STUDY_UNIT_CODE = upper('"+course+"')"+
					  " AND    MK_ACADEMIC_YEAR = "+aYear+
					  " AND    MK_SEMESTER_PERIOD = "+aPeriod+
					  " AND    ACCESS_LEVEL = '"+role+"' ";
		if (uSequence >= 0) {
			sql1 = sql1+" AND    USER_SEQUENCE = "+uSequence;
		}
		if (courseForm.getSelectedView().equals("E")){
			sql1 = sql1+" AND SYSTEM_TYPE ='E'  AND    USRSUN.NR = "+paperNr;
		}
		if(courseForm.getSelectedView().equals("L")){
			sql1 = sql1+" AND SYSTEM_TYPE ='L'";
		}
		
		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("MaintainStaffStudentDAO: deleteRoles (Delete): Error occurred /"+ ex,ex);
		}
	}
	
	/** Remove selected in Person Display */
	public void deleteRoles(String networkId, String course, String aYear, String aPeriod, String role, int uSequence ,String paperNr,PersonForm personForm) throws Exception{
		
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		
		//logged in user
		sessionManager = (SessionManager)ComponentManager.get(SessionManager.class);
		Session currentSession = sessionManager.getCurrentSession();
   		String userID = currentSession.getUserEid();

		networkId = networkId.toUpperCase();
		String sql1 = " DELETE FROM USRSUN " +
					  " WHERE  NOVELL_USER_ID = upper('"+networkId+"') " +
					  " AND    MK_STUDY_UNIT_CODE = upper('"+course+"')"+
					  " AND    MK_ACADEMIC_YEAR = "+aYear+
					  " AND    MK_SEMESTER_PERIOD = "+aPeriod+
					  " AND    ACCESS_LEVEL = '"+role+"' ";
		if (uSequence >= 0) {
			sql1 = sql1+" AND    USER_SEQUENCE = "+uSequence;
		}
		if (personForm.getSelectedView().equals("E")){
			sql1 = sql1+" AND SYSTEM_TYPE ='E'  AND    NVL(USRSUN.NR,1) = "+paperNr;
		}else if (personForm.getSelectedView().equals("L")){
			sql1 = sql1+" AND SYSTEM_TYPE ='L'";
	    }
		
		//System.out.println("delete: "+sql1);
		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("MaintainStaffStudentDAO: deleteRoles (Delete): Error occurred /"+ ex,ex);
		}
		//insert Audit log		
		if (personForm.getSelectedView().equals("E")){			
			String logTrail="Role/Function delete: user= "+networkId+ " paper number= "+paperNr+" role= "+role+"(changed by  " + userID +")";
			db1.insertAuditLog(networkId,course,personForm.getSelectedView(),aYear,aPeriod,logTrail);			
		}else{
			String logTrail="Role/Function delete: user= "+networkId+ " role= "+role+"(changed by  " + userID +")";
			db1.insertAuditLog(networkId,course,personForm.getSelectedView(),aYear,aPeriod,logTrail);
		}
	}
	
	//Validate outstanding assignments
	public boolean validateAssignOut(String username , String aYear, String course, String aPeriod) throws Exception {
		boolean assignOutst = false;
		String noOfRecords;
		
		String persNumber = selectStaffPersno(username);

		/*String sql = "	SELECT COUNT(*) as NoOfRecords"
					 +"	FROM ASSDCT,DCTSTU,STUASS " 
					 +"	WHERE Assdct.nr=dctstu.fk_ass_dct_nr "
					 +"	AND Assdct.mk_academic_year=stuass.fk_academic_year "
					 +"	AND Assdct.mk_academic_period=stuass.fk_semester_period "
					 +"	AND Stuass.fk_academic_period=1 "
					 +" AND dctstu.mk_student_nr = stuass.fk_student_nr"
					 +"	AND Assdct.mk_study_unit_code=stuass.fk_study_unit_code "
					 +" AND Assdct.mk_study_unit_code='"+course+"' "
					 +" AND Assdct.assignment_nr=stuass.mk_assignment_nr " 
					 +"	AND Dctstu.ass_seq_nr=stuass.sequence_nr "
					 +" AND Stuass.cancellation_code='NC'" 
					 +"	AND to_char(nvl(stuass.date_returned,to_date('19010101','YYYYMMDD')),'YYYYMMDD')<='19010101'" 
					 +" AND Assdct.Mk_lecturer_nr = " +persNumber
					 +" AND Assdct.mk_academic_year = " +aYear;*/
		String sql = " select count(*) as NoOfRecords"+
					 " from assmrk,unqass "+
					 " where FK_UNQ_ASS_YEAR ="+aYear+
					 " and FK_UNQ_ASS_PERIOD ="+aPeriod+
					 " and ASSMRK.FK_UNQ_ASS_YEAR=UNQASS.YEAR"+
					 " and ASSMRK.FK_UNQ_ASS_PERIOD=UNQASS.PERIOD"+
					 " and ASSMRK.FK_UNIQUE_NR=UNQASS.UNIQUE_NR"+
					 " and ASSMRK.MARK_PERCENTAGE>0"+
					 " and ASSMRK.MK_LECTURER_NR="+persNumber+
					 " and UNQASS.MK_STUDY_UNIT_CODE = '"+course+"'";
		
		try{
			noOfRecords = this.querySingleValue(sql,"NoOfRecords");

		} catch (Exception ex) {
            throw new Exception("MaintainStaffStudentDAO: validateAssignOut: Error occurred / "+ ex,ex);
		}
		RecordForm record= new RecordForm();
		
		if (Integer.parseInt(noOfRecords) > 0) {
			assignOutst = true;
		} else {
			assignOutst = false;
		}

		return assignOutst;
	}

	//Validate outstanding assignments for current year and next year
	public boolean validatecurrentAssignOut(String username , String aYear, String course, String aPeriod) throws Exception {
		boolean assignOutst = false;
		String noOfRecords;
		
		String persNumber = selectStaffPersno(username);

		String sql = " select count(*) as NoOfRecords"+
					 " from assmrk,unqass "+
					 " where FK_UNQ_ASS_YEAR ="+aYear+
					 " and FK_UNQ_ASS_PERIOD ="+aPeriod+
					 " and ASSMRK.FK_UNQ_ASS_YEAR=UNQASS.YEAR"+
					 " and ASSMRK.FK_UNQ_ASS_PERIOD=UNQASS.PERIOD"+
					 " and ASSMRK.FK_UNIQUE_NR=UNQASS.UNIQUE_NR"+
					 " and ASSMRK.MARK_PERCENTAGE>0"+
					 " and ASSMRK.MK_LECTURER_NR="+persNumber+
					 " and UNQASS.MK_STUDY_UNIT_CODE = '"+course+"' " +
					 " and  fk_unq_ass_year  In (to_char(sysdate,'YYYY'),to_char(add_months(sysdate,12),'YYYY'))";
		
		try{
			noOfRecords = this.querySingleValue(sql,"NoOfRecords");

		} catch (Exception ex) {
            throw new Exception("MaintainStaffStudentDAO: validateAssignOut: Error occurred / "+ ex,ex);
		}
		RecordForm record= new RecordForm();
		
		if (Integer.parseInt(noOfRecords) > 0) {
			assignOutst = true;
		} else {
			assignOutst = false;
		}

		return assignOutst;
	}
	
	/**  update person  for Person display */
	public void updateRoles(String networkId, String course, String aYear, String aPeriod, String role, int uSequence, String paperNr,PersonForm personForm,String userID,String prevRole) throws Exception {
		
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		
		String sql1 = " UPDATE USRSUN" +
					  " SET    ACCESS_LEVEL = '"+role+"',"+
					  "        ACCESS_UPDATED_BY = 0  "+
					  " WHERE  NOVELL_USER_ID = upper('"+networkId+"')"+
					  " AND    MK_STUDY_UNIT_CODE = upper('"+course+"')"+
					  " AND    MK_ACADEMIC_YEAR = "+aYear+
					  " AND    MK_SEMESTER_PERIOD = "+aPeriod+
					  " AND    USER_SEQUENCE = "+uSequence; 
					  
		if (personForm.getSelectedView().equals("E")){
				sql1 = sql1+" AND SYSTEM_TYPE ='E' AND    USRSUN.NR = "+paperNr;
		}else if (personForm.getSelectedView().equals("J")){
			sql1 = sql1+" AND SYSTEM_TYPE ='J'";
	    }else{
			sql1 = sql1+" AND SYSTEM_TYPE ='L'";
		}
		
		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("MaintainStaffStudentDAO: updateRoles: Error occurred / "+ ex,ex);
		}
		
		RecordForm record= new RecordForm();
		
		//insert Audit log		
		if (personForm.getSelectedView().equals("E")){			
			String logTrail="Role/Function update: user= "+networkId+ " paper number= "+paperNr+" new role= "+role+"(changed by  " + userID +")";
			db1.insertAuditLog(networkId,course,personForm.getSelectedView(),aYear,aPeriod,logTrail);			
		}else{
			String logTrail="Role/Function update: user= "+networkId+ " new role= "+role+" prev role= "+prevRole+"(changed by  " + userID +")";
			db1.insertAuditLog(networkId,course,personForm.getSelectedView(),aYear,aPeriod,logTrail);
		}
	}
	
	/** update person for Course display */
	public void updateRoles(String networkId, String course, String aYear, String aPeriod, String role, int uSequence, String paperNr,CourseForm courseForm) throws Exception {
		
		String sql1 = " UPDATE USRSUN" +
					  " SET    ACCESS_LEVEL = '"+role+"',"+
					  "        ACCESS_UPDATED_BY = 0  "+
					  " WHERE  NOVELL_USER_ID = upper('"+networkId+"')"+
					  " AND    MK_STUDY_UNIT_CODE = upper('"+course+"')"+
					  " AND    MK_ACADEMIC_YEAR = "+aYear+
					  " AND    MK_SEMESTER_PERIOD = "+aPeriod+
					  " AND    USER_SEQUENCE = "+uSequence;
					  
		if (courseForm.getSelectedView().equals("E")){
				sql1 = sql1+" AND SYSTEM_TYPE ='E' AND    USRSUN.NR = "+paperNr;
		}else if(courseForm.getSelectedView().equals("J")){
			sql1 = sql1+" AND SYSTEM_TYPE ='J'";
		}
		else{
			sql1 = sql1+" AND SYSTEM_TYPE ='L'";
		}
			
		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("MaintainStaffStudentDAO: updateRoles: Error occurred / "+ ex,ex);
		}
	}
	/*
	String sql = "insert into stadoc (creation_date, fk_stunr, FK_STUFLGCODE) values (?, ?, ?)";
		jdt.update(sql,new Object[] { currentDate,studentNr,flag}); */
	
	/** Method to insert values into table USERSUN AUDIT table for log */
	
	public void insertAuditLog(String networkId,String course,String systemType, String aYear, String aPeriod, String logTrail) throws Exception {
				
		String sql1 = "INSERT INTO USRAUD(AUD_DATE,NOVELL_USER_ID,MK_STUDY_UNIT_CODE,SYSTEM_TYPE,ACADEMIC_YEAR," +
				" ACADEMIC_PERIOD,AUD_LOG )"+
				" VALUES (sysdate,?,?,?,?,?,?)";
		
		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql1, new Object[] {networkId.toUpperCase(), course.toUpperCase(), 
					systemType,aYear, aPeriod,logTrail});
		} catch (Exception ex) {
			throw new Exception("MaintainStaffStudentDAO: insertAuditLog: Error occurred / "+ ex,ex);
		}
		
	}	
	
	/**
	 * This method will validate logs existence
	 *
	*/
	
	public boolean verifyLogsExist(String course,String systemType,String aYear,String aPeriod)throws Exception {
		boolean logsExist=false;
		String recordsReturned;
		
		String sql = " select count(*) as recordsReturned"+
		 " from usraud "+
		 " where MK_STUDY_UNIT_CODE ='"+course+"'"+
		 " and SYSTEM_TYPE ='"+systemType+"'"+
		 " and ACADEMIC_YEAR='"+aYear+"'"+
		 " and ACADEMIC_PERIOD='"+aPeriod+"'";
		

		try{
			recordsReturned = this.querySingleValue(sql,"RecordsReturned");

		} catch (Exception ex) {
			throw new Exception("MaintainStaffStudentDAO: verifyLogsExist: Error occurred / "+ ex,ex);
		}

		if (Integer.parseInt(recordsReturned) > 0) {
			logsExist = true;
		} else {
			logsExist = false;
		}

		return logsExist;
		
		
	}
	
	/**
	 * This method will select event logs for the selected criteria(course,year and period )
	 *
	*/
	public ArrayList<MaintainLogDetail> getEventLogs(String course,String systemType,String aYear,String aPeriod) throws Exception {
		ArrayList<MaintainLogDetail> listOfLogs =new ArrayList<MaintainLogDetail>();
		
			
		String selectLogs = " select AUD_DATE,NOVELL_USER_ID, AUD_LOG"+
		 " from usraud "+
		 " where MK_STUDY_UNIT_CODE ='"+course+"'"+
		 " and SYSTEM_TYPE ='"+systemType+"'"+
		 " and ACADEMIC_YEAR='"+aYear+"'"+
		 " and ACADEMIC_PERIOD='"+aPeriod+"'"+
		 " Order by AUD_DATE";
		
		try {
			
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> list = jdt.queryForList(selectLogs);
			Iterator j = list.iterator();
			int i = 1;
			
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();  
    			MaintainLogDetail logs= new MaintainLogDetail();
    			//set counter
    			logs.setCount(i);
    			//Get data from database table and set in object MaintainLogDetail
    			logs.setAudDate(data.get("AUD_DATE").toString());
    			logs.setNovellUserId(data.get("NOVELL_USER_ID").toString());
    			logs.setAudLog(data.get("AUD_LOG").toString());
    			listOfLogs.add(logs);
    			i++;
    			    			
    		}//end while
    			
		} catch (Exception ex) {
    		throw new Exception("MaintainStaffStudentDAO: getEventLogs: Error occurred / "+ ex,ex);
    	} // end try	
		
		
		return listOfLogs;
		
	}
		
	/**
	 * This method will select all examination periods
	 *
	*/
	
	public String insertRole(String networkId, String staffNr, String course, 
			String aYear, String aPeriod, String role, String systemType , String paperNr) throws Exception {
		int uSequence = 0;
		networkId = networkId.toUpperCase();
		String errorOccurred = "";
		
		boolean isDuplicate = isDuplicatePerson(course, aYear, aPeriod, 
				networkId, systemType,paperNr);
		
		if (isDuplicate == true){
			errorOccurred = "record not inserted because staff number value is null";
			return (errorOccurred);
		}
		// SELECT user sequence
		//String systemType, String networkCode, String course
		uSequence = selectUserSequence(systemType, networkId, course);
		
		if ((networkId == null)||(course==null)||(aYear==null)||(aPeriod==null)||((systemType==null)||(role==null))) {
			errorOccurred = "record not inserted because not null value is null";
			return (errorOccurred);
		}
		if ((networkId.equals(""))||(course.equals(""))||(aYear.equals(""))||(aPeriod.equals(""))||((systemType.equals("")||(role.equals(""))))) {
			errorOccurred = "record not inserted because not null value is null";
			return (errorOccurred);
		}
		if (systemType.equals("E")&&((staffNr == null)||(staffNr.equals("")))) {
			errorOccurred = "record not inserted because staff number value is null";
			return (errorOccurred);
		}
		
		String sql1 = "INSERT INTO USRSUN(NOVELL_USER_ID, MK_STUDY_UNIT_CODE, MK_ACADEMIC_YEAR," +
				" MK_SEMESTER_PERIOD, USER_SEQUENCE, ACCESS_LEVEL, SYSTEM_TYPE, PERSNO ,NR )"+
				" VALUES (?,?,?,?,?,?,?,?,?)";
		//System.out.println("sql1 "+sql1);

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql1, new Object[] {networkId.toUpperCase(), course.toUpperCase(), 
					aYear, aPeriod, uSequence, role.toUpperCase(), systemType, staffNr,paperNr});
		} catch (Exception ex) {
			throw new Exception("MaintainStaffStudentDAO: insertRole: Error occurred / "+ ex,ex);
		}
		return ("");
	}	
	
	public ArrayList selectExamPeriodOptions() throws Exception {
		ArrayList periodOptions = new ArrayList();
		periodOptions.add(new org.apache.struts.util.LabelValueBean("..",""));
		
		String select = " SELECT CODE, ENG_DESCRIPTION " +
						" FROM   XAMPRD" +
						" WHERE  CODE != 8";
		
    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List courseInfoList = jdt.queryForList(select);
			Iterator j = courseInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			periodOptions.add(new org.apache.struts.util.LabelValueBean(data.get("ENG_DESCRIPTION").toString(),data.get("CODE").toString()));
    			
    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("MaintainStaffStudentDAO: selectExamPeriodOptions: Error occurred / "+ ex,ex);
    	} // end try

		return periodOptions;
	}
	
	/**
	 * This method will select all persons linked to a course site (except primary lecturer), they will be available
	 * as options for primary lecturer
	 *
	 *@param acadYear 		academic year
	 *@param acadPeriod		semester period	
	 *@param course			course code
	 *@output arraylist		list of primary lecturer options
	*/
	public ArrayList selectPrimLecturerOptions(String acadYear, String acadPeriod, String course, String selectedView) throws Exception {
		ArrayList primLecturerOptions = new ArrayList();
		
		String select = " SELECT NOVELL_USER_ID"+
		                " FROM   USRSUN"+
		                " WHERE  MK_STUDY_UNIT_CODE = upper('"+course+"')"+
		                " AND    MK_ACADEMIC_YEAR = '"+acadYear+"'"+
		                " AND    MK_SEMESTER_PERIOD = '"+acadPeriod+"'"+
		                " AND    ACCESS_LEVEL != 'PRIML'"+
		                " AND    SYSTEM_TYPE = '"+selectedView+"'"+
		                " ORDER BY NOVELL_USER_ID";
		
    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List courseInfoList = jdt.queryForList(select);
			Iterator j = courseInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			primLecturerOptions.add(new org.apache.struts.util.LabelValueBean(data.get("NOVELL_USER_ID").toString(),data.get("NOVELL_USER_ID").toString()));
    			
    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("MaintainStaffStudentDAO: selectPrimLecturerOptions: Error occurred / "+ ex,ex);
    	} // end try

		return primLecturerOptions;
	}
	
	/**
	 * This method will select examination period description
	 *
	 *@param period			period id	
	 *@output desc			period description
	*/
	public String selectExamPeriodDesc(String period) throws Exception {
		String desc = "";
				
		String select = " SELECT ENG_DESCRIPTION " +
						" FROM   XAMPRD" +
						" WHERE  CODE = "+period;
		
    	try{
    		desc = this.querySingleValue(select,"ENG_DESCRIPTION");

    	} catch (Exception ex) {
    		throw new Exception("MaintainStaffStudentDAO: selectExamPeriodDesc: Error occurred / "+ ex,ex);
    	} // end try

		return desc;
	}
	
	/**
	 * This method will select all registration periods
	 *
	*/
	public ArrayList selectRegPeriodOptions() throws Exception {
		ArrayList acadPeriodOptions = new ArrayList();
		
		acadPeriodOptions.add(new org.apache.struts.util.LabelValueBean("..",""));
		acadPeriodOptions.add(new org.apache.struts.util.LabelValueBean("Semester 1","1"));
		acadPeriodOptions.add(new org.apache.struts.util.LabelValueBean("Semester 2","2"));
		acadPeriodOptions.add(new org.apache.struts.util.LabelValueBean("Year Course","0"));
		acadPeriodOptions.add(new org.apache.struts.util.LabelValueBean("Year Course June","6"));
		
		return acadPeriodOptions;
	}
	
	/**
	 * This method will select all periods for a course from USRSUN
	 *
	*/
	public ArrayList selectPeriodOptions(String course, String systemType) throws Exception {
		ArrayList periodOptions = new ArrayList();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

		String stringYear = sdf.format(d);

		Integer i = new Integer(stringYear);
		int currentYear = i.intValue();
		int prevYear = currentYear -1;
		int prevYear2 = currentYear -2;
		int prevYear3 = currentYear -3;
		int nextYear = currentYear + 1;
		int nextYear2 = currentYear + 2;
		String currentYearStr = Integer.toString(currentYear);
		String prevYear3Str = Integer.toString(prevYear3); 
		String prevYear2Str = Integer.toString(prevYear2); 
		String prevYearStr = Integer.toString(prevYear);
		String nextYearStr = Integer.toString(nextYear);
		String nextYear2Str = Integer.toString(nextYear2);
		String forYears = prevYear3Str+","+prevYear2Str+","+prevYearStr+","+currentYear+","+nextYearStr+","+nextYear2Str;
		
		String select = " SELECT distinct MK_ACADEMIC_YEAR, MK_SEMESTER_PERIOD " +
						" FROM   USRSUN" +
						" WHERE  MK_STUDY_UNIT_CODE = upper('"+course+"')"+
						" AND    MK_ACADEMIC_YEAR IN ("+forYears+")"+
						" AND    SYSTEM_TYPE = '"+systemType+"' "+
						" order  by mk_academic_year desc, mk_semester_period desc";
		
    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List courseInfoList = jdt.queryForList(select);
			Iterator j = courseInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			
    			String tmpYear = data.get("MK_ACADEMIC_YEAR").toString();
    			String tmpSemPeriod = data.get("MK_SEMESTER_PERIOD").toString();
    			String tmpValue = "";
    			String tmpLabel = tmpYear+"#"+tmpSemPeriod; 
    			if (tmpSemPeriod.equals("1")) {
    				tmpValue = tmpYear+" - Semester 1 (S1)";
    			} else if (tmpSemPeriod.equals("2")) {
    				tmpValue = tmpYear+" - Semester 2 (S2)";
    			} else if (tmpSemPeriod.equals("0")) {
    				tmpValue = tmpYear+" - Year course (Y1)";
    			} else if (tmpSemPeriod.equals("6")) {
    				tmpValue = tmpYear+" - Mid Year course (Y2)";
    			}
    			periodOptions.add(new org.apache.struts.util.LabelValueBean(tmpValue,tmpLabel));
    			
    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("MaintainStaffStudentDAO: selectPeriodOptions: Error occurred / "+ ex,ex);
    	} // end try

		return periodOptions;
	}
	
	/**
	 * This method will select  the next available user sequence for the
	 *  SYSTEM_TYPE + NOVELL_USER_ID + MK_STUDY_UNIT_CODE COMBINATION.
	 *
	 * @param systemType		L = Teaching roles/ X = examination functions
	 * @param networkcode		network user id	
	 * @param course			course code
	 * @output 					next available user sequence
	 * 24 = teaching roles
	*/
	public int selectUserSequence(String systemType, String networkCode, String course) throws Exception {
		int uSequence = 0;
		
		String select = " SELECT MAX(USER_SEQUENCE)+1 AS SEQ FROM USRSUN "+
						" WHERE  SYSTEM_TYPE = '"+systemType+"'"+
						" AND    NOVELL_USER_ID = upper('"+networkCode+"')"+
						" AND    MK_STUDY_UNIT_CODE = upper('"+course+"')";
		
    	try{
    		String uSequenceTmp = this.querySingleValue(select,"SEQ");
    		if (uSequenceTmp.equals("")) {
    			uSequence = 0;
    		} else {
    			uSequence = Integer.parseInt(uSequenceTmp);
    		}

    	} catch (Exception ex) {
    		throw new Exception("MaintainStaffStudentDAO: selectUserSequence: Error occurred / "+ ex,ex);
    	} // end try

		return uSequence;
	}
	
	/**
	 * This method will select  specific user sequence for course site and person
	 *  SYSTEM_TYPE + NOVELL_USER_ID + MK_STUDY_UNIT_CODE COMBINATION.
	 *
	 * @param systemType		L = Teaching roles/ X = examination functions
	 * @param networkcode		network user id	
	 * @param course			course code
	 * @output 					next available user sequence
	 * 24 = teaching roles
	*/
	public int selectSpecificUserSequence(String systemType, String networkCode, String course, String acadYear, String semesterPeriod) throws Exception {
		int uSequence = 0;
		
		String select = " SELECT MAX(USER_SEQUENCE) AS SEQ FROM USRSUN "+
						" WHERE  SYSTEM_TYPE = '"+systemType+"'"+
						" AND    NOVELL_USER_ID = upper('"+networkCode+"')"+
						" AND    MK_STUDY_UNIT_CODE = upper('"+course+"')"+
						" AND    MK_ACADEMIC_YEAR = "+acadYear+
						" AND    MK_SEMESTER_PERIOD = "+semesterPeriod;
		
    	try{
    		String uSequenceTmp = this.querySingleValue(select,"SEQ");
    		if (uSequenceTmp.equals("")) {
    			uSequence = 0;
    		} else {
    			uSequence = Integer.parseInt(uSequenceTmp);
    		}

    	} catch (Exception ex) {
    		throw new Exception("MaintainStaffStudentDAO: selectSpecificUserSequence: Error occurred / "+ ex,ex);
    	} // end try

		return uSequence;
	}
	
	/**
	 * This method will copy courses from one period to another
	 *
	 * @param courseForm   	Course code that was entered
	*/
	//mainForm.getFromYear(), mainForm.getFromPeriod()
	public String copyCourseSite(String course, String systemType, String fromYear, String fromPeriod, 
			String toYear, String toPeriod ,String userID) throws Exception {
		
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		String errorOccurred = "";
		
		String select = "SELECT USRSUN.MK_ACADEMIC_YEAR as AY, USRSUN.MK_SEMESTER_PERIOD as SP,USRSUN.ACCESS_LEVEL as AL, " +
						" USRSUN.NOVELL_USER_ID as NU, nvl(USRSUN.PERSNO,'1') AS PersN, USRSUN.USER_SEQUENCE AS US, nvl(USRSUN.NR,0) as PN "+
						" FROM   USRSUN "+
						" WHERE  SYSTEM_TYPE = '"+systemType+"' "+
						" AND    USRSUN.MK_STUDY_UNIT_CODE = UPPER('"+course+"') "+
						" AND    MK_ACADEMIC_YEAR = "+fromYear+
						" AND    MK_SEMESTER_PERIOD = "+fromPeriod+
						" ORDER BY USRSUN.MK_ACADEMIC_YEAR DESC, USRSUN.MK_SEMESTER_PERIOD, USRSUN.ACCESS_LEVEL ";
			
    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List courseInfoList = jdt.queryForList(select);
			Iterator j = courseInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			// select data
    			String acadYearTmp = data.get("AY").toString();
    			String semPerTmp = data.get("SP").toString();
    			String accessLevel = data.get("AL").toString();
    			String networkId = data.get("NU").toString();
    			String persNo = data.get("PersN").toString();
    			String paperNr = data.get("PN").toString();

    			if (systemType.equals("E")) {
    				if (paperNr.equals("0")) {
    					paperNr = "1";
    				}
    			} else {
    				paperNr = "";
    			}
    			
    			// do insert
    			if (persNo.equals("1")) {
    				errorOccurred = insertRole(networkId, "", course, toYear, toPeriod, accessLevel, systemType,paperNr);
    			} else {
    				errorOccurred = insertRole(networkId, persNo, course, toYear, toPeriod, accessLevel, systemType,paperNr);
    			}
    			if (errorOccurred.length() > 0) {
					errorOccurred = NULLVALUEERROR;
    			}
    			String logTrail1="Role/Function copy: user= "+networkId+ " Course site copied= "+course+" copied from "+acadYearTmp+" "+semPerTmp+"(changed by  " + userID + ")";
    			db1.insertAuditLog(userID,course,systemType, toYear,toPeriod,logTrail1);
    			
   			
    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("MaintainStaffStudentDAO: copyCourseSite(courseForm): Error occurred / "+ ex,ex);
    	} // end try
    	return ("");
	}
	
	/**
	 * This method will test if the exam period exist in table SUNPDT.
	 *
	 * @param courseCode    course code
	 * @param exam_year   	examination year
	 * @param exam_period   examination period
	 * @output examPeriodExist		boolean true=valid; false=not valid
	*/
	public boolean examPeriodExist(String courseCode,String examYear, String examPeriod) throws Exception {
		boolean examPeriodExist = false;
		String noOfRecords;

		String sql1 = " SELECT COUNT(*) as NoOfRecords "+
					  " FROM   SUNPDT " +
					  " WHERE  FK_SUNCODE = upper('"+courseCode+"') " +
					  " AND    ((MK_EXAM_YEAR = "+examYear+
					  " AND    MK_EXAM_PERIOD = "+examPeriod+")"+
					  " OR     (MK_SUPP_EXAM_YEAR = "+examYear+
					  " AND    MK_SUPP_EXAM_PERIO = "+examPeriod+"))";
					  
		try{
			noOfRecords = this.querySingleValue(sql1,"NoOfRecords");

		} catch (Exception ex) {
            throw new Exception("MaintainStaffStudentDAO: examPeriodExist: Error occurred / "+ ex,ex);
		}

		if (Integer.parseInt(noOfRecords) > 0) {
			examPeriodExist = true;
		} else {
			examPeriodExist = false;
		}

		return examPeriodExist;
	}
	
	/**
	 * This method will validate paper number
	*/
	public boolean validPaperNr(String examYear, String examPeriod, String course, String paperNr) throws Exception {
		boolean validPaperNo = false;
		String records = "";

		String sql2 = " SELECT COUNT(*) AS RECORDS "+
		              " FROM   XAMDAT "+
		              " WHERE  FK_EXAM_YEAR = "+examYear+
		              " AND    MK_EXAM_PERIOD_COD = "+examPeriod+
		              " AND    FK_STUDY_UNIT_CODE = '"+course+"'" +
		              " AND    FK_NR = "+paperNr;
		
		try{
			records = this.querySingleValue(sql2,"RECORDS");
			if ((null == records)||(records.length()==0)||(records.equals("0"))) {
				validPaperNo = false;
			} else {
				validPaperNo = true;
			}

		} catch (Exception ex) {
	      throw new Exception("MaintainStaffStudentDAO: validPaperNr {table XAMDAT}: Error occurred / "+ ex,ex);
		}		

		return validPaperNo;
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
	/* remove leading whitespace */
    public static String ltrim(String source) {
        return source.replaceAll("^\\s+", "");
    }

    /* remove trailing whitespace */
    public static String rtrim(String source) {
        return source.replaceAll("\\s+$", "");
    }
   
}
