package za.ac.unisa.lms.tools.studentlist.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.HashSet;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import Gistl01h.Abean.Gistl01sProxyForGistf01m;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.utils.CoursePeriodLookup;

import za.ac.unisa.lms.tools.studentlist.forms.StudentListForm;

public class StudentListDAO extends StudentSystemDAO {
	private Log log;
	/**maps reoff.code to regoff.eng_description
	 *
	 */
	private HashMap<String,String>  regOffHashMap = new HashMap<String,String>();
	/**maps regoff.eng_description to ldd.code
	 *
	 */
	private HashMap<String,String>  reglddMap = new HashMap<String,String>();
/**
 * Set of codes from the ldd table
 */
	private HashSet<String> lddCodes = new HashSet<String>();
	
	public String getPersonalNR(String networkCode) throws Exception {
		String persnr= "";

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
        int tmpPersnr = op.getOutStaffPersno();
	    String tmpPersnrStr = Integer.toString(tmpPersnr);
	 
	    
		return tmpPersnrStr;
	}
	
	public String verifyHodOrNot(String persnr) throws Exception{

		String hod;
		String sql = "select head_of_dept from dpt where head_of_dept  ="+"'"+persnr+"'"+"";

		try{
			JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
			hod = this.querySingleValue(sql,"head_of_dept");
		
			
			if ((hod == null)||(hod.length() == 0)||(hod.equals(""))||(hod.equals("0"))) {
				hod = "";
			}

		} catch (Exception ex) {
            throw new Exception("StudentListDAO: verifyHodOrNot: (personal number: "+persnr+") Error occurred / "+ ex,ex);
		}
       return hod;
	}
	public String getDepartmentCode(String hod) throws Exception{

		String dptCode;
		String sql = "select code from dpt where head_of_dept  ="+hod;

		try{
			JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
			dptCode = this.querySingleValue(sql,"code");
		
			
			if ((dptCode == null)||(dptCode.length() == 0)||(dptCode.equals(""))) {
				dptCode = null;
			}

		} catch (Exception ex) {
            throw new Exception("StudentListDAO: verifyHodOrNot: (noveluserId: "+hod+") Error occurred / "+ ex,ex);
		}
       return dptCode;
	}

	public ArrayList getSitesPerHod(String dptCode,String year,String semester) throws Exception {
		ArrayList list = new ArrayList();
		String suc = "";
		String ay = "";
		String sp = "";
		String spString = "";
		
		String select = "select u.mk_study_unit_code, u.mk_academic_year, u.mk_semester_period ";
		String from ="from usrsun u,sun s  ";
		String where = "where s.mk_department_code = "+"'"+ dptCode +"'" +
			"and s.academic_level <> 'D' and s.research_flag = 'N' and s.college_flag <> 'Y' and s.formal_tuition_fla = 'F' " +
			"and u.mk_study_unit_code = s.code and u.access_level = 'PRIML' and u.system_type = 'L' and in_use_flag = 'Y'";
		 if(year.length()>0){
			where += " and u.MK_ACADEMIC_YEAR = '"+year+"' ";
		 }
		 if(semester.length()>0){
		 where += " and u.MK_SEMESTER_PERIOD='"+semester+"' ";
		 }
		 where+=" order by u.mk_study_unit_code, u.mk_academic_year, u.mk_semester_period";	

		String sitesQuery=select+from+where;
		log = LogFactory.getLog(this.getClass());			
		
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			log.debug("GENERATED QUERY FOR STUDENTLIST SITES: " + sitesQuery);
			List queryList = jdt.queryForList(sitesQuery);
			Iterator i = queryList.iterator();
			while(i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				StudentListSites studentListSites = new StudentListSites();
				suc = data.get("MK_STUDY_UNIT_CODE").toString();
				ay = data.get("MK_ACADEMIC_YEAR").toString();
				sp = (data.get("MK_SEMESTER_PERIOD")).toString();
				spString = CoursePeriodLookup.getCourseTypeAsString("0"+sp);
				studentListSites.setLecturerSites(suc + "-" + ay.substring(2,4) + "-" + spString);

				list.add(studentListSites);
			}
		}catch (Exception ex) {
			throw new Exception("Student List DAO : Error "+ ex);
		}

		return list;
	}


	public ArrayList getSitesPerLecturer(String novellUserID,String year,String semester) throws Exception {
		ArrayList list = new ArrayList();
		String suc = "";
		String ay = "";
		String sp = "";
		String spString = "";
		/**
		String sitesQuery = "SELECT mk_study_unit_code, mk_academic_year, mk_semester_period " +
		"from usrsun where system_type = 'L' and novell_user_id = 'FMYBURGH' " +
		"and access_level in ('PRIML','SECDL','CADMN') " +
		"order by mk_study_unit_code, mk_academic_year, mk_semester_period";
		**/
		String select = "SELECT mk_study_unit_code, mk_academic_year, mk_semester_period ";
		String from="from usrsun ";
		String where=" where system_type = 'L' and novell_user_id = '" + novellUserID.toUpperCase() + "' " +
			"and access_level in ('PRIML','SECDL','CADMN')";
		if(year.length()>0){
		where += " and MK_ACADEMIC_YEAR = '"+year+"' ";
		}
		if(semester.length()>0){
			where += " and MK_SEMESTER_PERIOD='"+semester+"' ";
			}
		where+=" order by mk_study_unit_code, mk_academic_year, mk_semester_period";
		log = LogFactory.getLog(this.getClass());

		String sitesQuery=select + from + where;		
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			log.debug("GENERATED QUERY FOR STUDENTLIST SITES: " + sitesQuery);
			List queryList = jdt.queryForList(sitesQuery);
			Iterator i = queryList.iterator();
			while(i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				StudentListSites studentListSites = new StudentListSites();
				suc = data.get("MK_STUDY_UNIT_CODE").toString();
				ay = data.get("MK_ACADEMIC_YEAR").toString();
				sp = (data.get("MK_SEMESTER_PERIOD")).toString();
				spString = CoursePeriodLookup.getCourseTypeAsString("0"+sp);
				//CoursePeriod cp = CoursePeriodLookup.getCoursePeriod(suc + ay +  spString);
				//studentListSites.setLecturerSites(cp.getCourseCode() + "-" + ay.substring(2,4) + "-" + spString);
				studentListSites.setLecturerSites(suc + "-" + ay.substring(2,4) + "-" + spString);

				list.add(studentListSites);
			}
		}catch (Exception ex) {
			throw new Exception("Student List DAO : Error "+ ex);
		}

		return list;
	}


	//confine district has been removed....
	public ArrayList getStudents(String confineGender,
			//String confineEmail,
			String confineHomeLanguage,
			String confineCorrespondenceLanguage,
			String confineCountry,
			String confineProvince,
			String confineRegion,
			String confineResRegion,
			String confineRace,
			String confineOnline,
			boolean isRegion,
			boolean isResRegion,
			String confineGroupedStudent,
			String disabilityCode,
			Vector sites
				) throws Exception {

		//System.out.println("***********************************: "+disabilityCode);
		
		ArrayList list = new ArrayList();
		String selectedCourse = "";
		String selectedYear = "";
		String selectedSemester = "";

		if(isRegion||isResRegion) {
			long t0 = System.currentTimeMillis();
			if (isRegion){setRegOffMappings();}

			long t1 = System.currentTimeMillis();

			if (isResRegion){MapRegions();
			//System.out.println("The query took "+(System.currentTimeMillis()-t1)+" ms to execute");
			}
			//System.out.print("The query took "+(System.currentTimeMillis()-t0)+" ms to execute");
		}
		int l = 0;
		log = LogFactory.getLog(this.getClass());
		String select = "select stuann.mk_student_nr student_number, stuann.mk_regional_office regional_office, stu.fk_lddcode fk_lddcode, " +
						"stu.mk_title, stu.first_names, stu.surname, address_line_1," +
						"address_line_2, address_line_3, address_line_4, address_line_5, address_line_6," +
						"home_number, work_number, fax_number, email_address, cell_number, gender, mk_correspondence, mk_home_language, " +
						"stusun.status_code status_code, postal_code,  initials,stusun.REGISTRATION_DATE reg_date," +
						" nvl(TRIM(tustgr.GROUP_NR),' ')  groupnr, distype.code,distype.ENG_DESCRIPTION";

		String from = " from stuann, stu, adr, adrph, stusun,tustgr,distype ";

		String where = "where stusun.fk_student_nr= tustgr.MK_STUDENT_NR(+) " +
				" and stusun.fk_academic_year= tustgr.MK_ACADEMIC_YEAR(+) " +
				" and stusun.mk_study_unit_code=tustgr.MK_STUDY_UNIT_CODE(+) " +
				" and tustgr.semester(+)=stusun.SEMESTER_PERIOD" +
				" and stuann.mk_student_nr =stu.nr " +
		   "and stu.nr = adr.reference_no " +
		   "and adr.reference_no = stusun.fk_student_nr "+
		   "and adrph.reference_no(+) = stusun.fk_student_nr "+
		   "and stusun.fk_student_nr = stu.nr "+
		   "and stuann.mk_academic_year = stusun.fk_academic_year "+
		   "and stuann.mk_academic_period = 1 " +
		   "and adr.fk_adrcatypfk_adrc = 1 "+
		   "and adr.fk_adrcatypfk_adrt = 1 "+
		   "and adrph.fk_adrcatcode(+) = 1 " +
		    "and distype.code=stuann.MK_DISABILITY_TYPE "+
		   "and stusun.status_code = 'RG' and ";
		   
		where += "(";

		for(l = 0; l < sites.size(); l++) {
			selectedCourse = (sites.get(l)).toString().substring(0,7);
			selectedYear = "20" +(sites.get(l)).toString().substring(8,10);
			selectedSemester = (sites.get(l)).toString().substring(11,13);

			where += "(" +
				"stusun.semester_period = " + CoursePeriodLookup.getPeriodTypeAsString(selectedSemester) + " " +
				"and stusun.fk_academic_year = " + selectedYear + " " +
				"and stusun.fk_academic_period = 1 " +
				"and stusun.mk_study_unit_code = '" + selectedCourse + "' )";

			if(l != (sites.size() -1)) {
				where += " or ";
			}
		}

		where += ") ";

		if(!confineGender.equals("notspecified")) {
			if(confineGender.equals("M")) {
				where += "and stu.gender = 'M' ";
			}else {
				where += "and stu.gender = 'F' ";
			}
		}
		//completed
		if(!confineHomeLanguage.equalsIgnoreCase("notspecified")) {
			where += "and stu.mk_home_language = '" + confineHomeLanguage +"' ";
		}

		//completed
		if(!confineCountry.equalsIgnoreCase("notspecified")) {
			where += "and stu.mk_country_code = '" + confineCountry + "' ";
		}

		//completed
		if(!confineCorrespondenceLanguage.equalsIgnoreCase("notspecified")) {
			if (confineCorrespondenceLanguage.equalsIgnoreCase("E")){
				where += "and stu.mk_correspondence = 'E' ";
			}	else if (confineCorrespondenceLanguage.equalsIgnoreCase("A")){
				where += "and stu.mk_correspondence = 'A' ";
			}
		}

		//completed
		if(!confineOnline.equalsIgnoreCase("notspecified")) {
			if (confineOnline.equalsIgnoreCase("No")){
				where += "and stuann.sol_user_flag = 'N' ";
			}	else if (confineOnline.equalsIgnoreCase("Yes")){
				where += "and stuann.sol_user_flag = 'Y' ";
			}
		}

		//completed
		if(!confineProvince.equalsIgnoreCase("notspecified")) {
			from += ", ldd ";
			where += "and stu.fk_lddcode = ldd.code and ldd.fk_prvcode = " + confineProvince + " ";
		}

		//completed -- it shows the code and not the name
		if(!disabilityCode.equalsIgnoreCase("notspecified")) {
			
			where += " and distype.code="+disabilityCode+" " ;
			System.out.println("This where clause is :"+where);
		}

		
		if(!confineRegion.equalsIgnoreCase("notspecified")) {
			from += ", regoff ";
			//where += "and stuann.mk_regional_office(+) = regoff.code and stuann.mk_regional_office = " + confineRegion + " ";
			where += "and stuann.mk_regional_office(+) = regoff.code and distype.code=stuann.MK_DISABILITY_TYPE and distype.code="+disabilityCode+" and stuann.mk_regional_office = " + confineRegion + " "+
                     " and regoff.service_type in ('A','H','S')" ;
			System.out.println("This where clause is :"+where);
		}

		//making provision for the residential region and NOT only the registration region
		if(!confineResRegion.equalsIgnoreCase("notspecified")) {
			if(confineProvince.equalsIgnoreCase("notspecified")) {
				from += ", ldd ";
			}

			where += "and stu.fk_lddcode = ldd.code and ldd.mk_region_code = " + confineResRegion + " ";
		}

		//completed
		if(!confineRace.equalsIgnoreCase("notspecified")) {
			where += "and stu.fk_racecode = " + confineRace + " ";
		}
		//completed
		if(!confineGroupedStudent.equalsIgnoreCase("notspecified")) {
			if (confineGroupedStudent.equalsIgnoreCase("No")){
				where += " and not exists (select * from tustgr where MK_STUDENT_NR=stusun.fk_student_nr " +
						"and mk_study_unit_code=stusun.mk_study_unit_code and SEMESTER=stusun.semester_period ) ";
			}else if (confineGroupedStudent.equalsIgnoreCase("Yes")){
				where += " and exists (select * from tustgr where MK_STUDENT_NR=stusun.fk_student_nr " +
						"and mk_study_unit_code=stusun.mk_study_unit_code and SEMESTER=stusun.semester_period) ";
			}
		}
		
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			log.debug("GENERATED QUERY for STUDENT_LIST: " + select + from + where);
			System.out.println("GENERATED QUERY for STUDENT_LIST:--- "+where);
			List queryList = jdt.queryForList(select + from + where);
			Iterator i = queryList.iterator();

			long t0 = System.currentTimeMillis();
			System.out.println(" Starting to measure the time for this query.........");

			while(i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				StudentDetail studentDetails = new StudentDetail();
				studentDetails.setStudentNumber(data.get("STUDENT_NUMBER").toString());
				studentDetails.setGender(data.get("GENDER").toString());
				studentDetails.setDisability(data.get("ENG_DESCRIPTION").toString());
				studentDetails.setTitle(data.get("MK_TITLE").toString());
				studentDetails.setLastName(data.get("SURNAME").toString());
				studentDetails.setFirstNames(data.get("FIRST_NAMES").toString());
				studentDetails.setInitials(data.get("INITIALS").toString());
				
				//System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++: "+studentDetails.getDisability());
				
				if(data.get("EMAIL_ADDRESS") == null)  {
					studentDetails.setEmailAddress("");
				}else {
					studentDetails.setEmailAddress(data.get("EMAIL_ADDRESS").toString());
				}
				String al = "ADDRESS_LINE_";
				String completeAddress = "";
				int k = 1;
				for(int j = 0; j < 6; j++) {
					al = al + ("" + k);
					if(!(data.get(al) == null)) {
						completeAddress += data.get(al) + " ";
					}
					al = "ADDRESS_LINE_";
					k += 1;
				}
				studentDetails.setPostalAddress(completeAddress);
				if(data.get("HOME_NUMBER") == null) {
						studentDetails.setCellularNumber("");
				}else {
					studentDetails.setHomePhoneNumber(data.get("HOME_NUMBER").toString());
				}
				if(data.get("WORK_NUMBER") == null) {
					studentDetails.setWorkPhoneNumber("");
				}else {
					studentDetails.setWorkPhoneNumber(data.get("WORK_NUMBER").toString());
				}
				if(data.get("FAX_NUMBER") == null) {
					studentDetails.setCellularNumber("");
				}else {
					studentDetails.setFaxNumber(data.get("FAX_NUMBER").toString());
				}
				if(data.get("CELL_NUMBER") == null) {
					studentDetails.setCellularNumber("");
				}
				else {
					studentDetails.setCellularNumber(data.get("CELL_NUMBER").toString());
				}
				studentDetails.setHomeLanguage(data.get("MK_HOME_LANGUAGE").toString());
				studentDetails.setCorrespondenceLanguage(data.get("MK_CORRESPONDENCE").toString());
				studentDetails.setRegistrationStatus(data.get("STATUS_CODE").toString());
				//studentDetails.setPostalCode(data.get("POSTAL_CODE").toString());
				String postalCode = data.get("POSTAL_CODE").toString();
			
				//added zeros to the postal code
				if(postalCode.length()==4){
					postalCode = postalCode;
					studentDetails.setPostalCode(postalCode);
				}
				if(postalCode.length()==3){
					postalCode = "0"+postalCode;
					studentDetails.setPostalCode(postalCode);
				}
				if(postalCode.length()==2){
					postalCode = "00"+postalCode;
					studentDetails.setPostalCode(postalCode);
				}
				if(postalCode.length()==1){
					postalCode = "000"+postalCode;
					studentDetails.setPostalCode(postalCode);
				}
				if(postalCode.length()==0){
					postalCode = "0000"+postalCode;
					studentDetails.setPostalCode(postalCode);
				}
			
			//	System.out.println("postalcode   1stone :"+postalCode);
			
					if (isRegion){
					   studentDetails.setRegion(regOffHashMap.get(data.get("REGIONAL_OFFICE").toString()));
					}
					if (isResRegion){
					   if(null == data.get("FK_LDDCODE") | "".equals(data.get("FK_LDDCODE"))) {
						    studentDetails.setResRegion("");
					    }else {
						    studentDetails.setResRegion(reglddMap.get(data.get("FK_LDDCODE").toString()));
					    }
			    	}
					
					studentDetails.setRegistrationDate(data.get("reg_date").toString().substring(0,11));
					studentDetails.setGroupNumber(data.get("groupnr").toString());
				list.add(studentDetails);
			}
	  	}  catch (Exception ex) {
			log.error("Exception while trying to build student list object: " + ex.getMessage());
			throw new Exception("Student List DAO : Error "+ ex);
		}
		return list;
	}

	public int getAllRegisteredStudents(Vector sites)throws Exception {

		ArrayList list = new ArrayList();
		String selectedCourse = "";
		String selectedYear = "";
		String selectedSemester = "";
            int registeredstudents=0;
		int l = 0;
		log = LogFactory.getLog(this.getClass());
		String select =  "select count(*) ";
		String from = "from stuann, stu, adr, adrph, stusun ";

		String where = "where stuann.mk_student_nr =stu.nr " +
		   "and stu.nr = adr.reference_no " +
		   "and adr.reference_no = stusun.fk_student_nr "+
		   "and adrph.reference_no(+) = stusun.fk_student_nr "+
		   "and stusun.fk_student_nr = stu.nr "+
		   "and stuann.mk_academic_year = stusun.fk_academic_year "+
		   "and stuann.mk_academic_period = 1 " +
		   "and adr.fk_adrcatypfk_adrc = 1 "+
		   "and adr.fk_adrcatypfk_adrt = 1 "+
		   "and adrph.fk_adrcatcode(+) = 1 " +
		   "and stusun.status_code = 'RG' and ";

		where += "(";
        
		for(l = 0; l < sites.size(); l++) {
			selectedCourse = (sites.get(l)).toString().substring(0,7);
			selectedYear = "20" +(sites.get(l)).toString().substring(8,10);
			selectedSemester = (sites.get(l)).toString().substring(11,13);

			where += "(" +
				"stusun.semester_period = " + CoursePeriodLookup.getPeriodTypeAsString(selectedSemester) + " " +
				"and stusun.fk_academic_year = " + selectedYear + " " +
				"and stusun.fk_academic_period = 1 " +
				"and stusun.mk_study_unit_code = '" + selectedCourse + "' )";


			if(l != (sites.size() -1)) {
				where += " or ";
			}
		}

		where += ") ";



		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			log.debug("GENERATED QUERY for STUDENT_LIST: " + select + from + where);
			//System.out.println(" Trying 2 get pass this ");
			registeredstudents = jdt.queryForInt(select + from + where);
			
			//contains total no.of students ,selected by lecturer in step2 on selection of courses.
		}  catch (Exception ex) {
			log.error("Exception while trying to build student list object: " + ex.getMessage());
			throw new Exception("Student List DAO : Error "+ ex);
		}
		
	return registeredstudents;
	
	}
	
	                                      
	
	public ArrayList getCStudents(String confineGender,
			//String confineEmail,
			String confineHomeLanguage,
			String confineCorrespondenceLanguage,
			String confineCountry,
			String confineProvince,
			String confineRegion,
			String confineResRegion,
			String confineRace,
			String confineOnline,
			boolean isRegion,
			boolean isResRegion,
			String disabilityCode,
			Vector sites
				) throws Exception {
		
		System.out.println("***********************************--------------: "+disabilityCode);

		ArrayList list = new ArrayList();
		String selectedCourse = "";
		String selectedYear = "";
		String selectedSemester = "";
        
		if(isRegion||isResRegion) {
			long t0 = System.currentTimeMillis();
			if (isRegion){setRegOffMappings();}

			long t1 = System.currentTimeMillis();

			if (isResRegion){MapRegions();
			//System.out.println("The query took "+(System.currentTimeMillis()-t1)+" ms to execute");
			}
			//System.out.print("The query took "+(System.currentTimeMillis()-t0)+" ms to execute");
		}
		int l = 0;
		log = LogFactory.getLog(this.getClass());
		String select = "SELECT distinct acasun.fk_student_nr student_number,acasun.mk_academic_year acadyear, acasun.mk_study_unit_code module,"+
                      "acasun.mk_academic_period Sem,stuann.mk_regional_office regional_office, stu.fk_lddcode fk_lddcode,"+
            "stu.mk_title, stu.first_names, stu.surname, address_line_1,address_line_2, address_line_3, address_line_4, address_line_5,"+
            "address_line_6, home_number, work_number, fax_number, email_address,cell_number, gender, mk_correspondence, mk_home_language,"+
             "DECODE (cancellation_code, 'NC', 'RG', cancellation_code) status_code,postal_code, initials, distype.code,distype.ENG_DESCRIPTION ";

		String from = "from acasun, stuann, stu, adr, adrph, distype ";

		String where = "WHERE acasun.cancellation_code = 'NC' " +
                      "and  acasun.exemption_code NOT IN (2, 5) " +
                      "and stuann.mk_academic_year = acasun.mk_academic_year " +
                       "and stuann.mk_academic_period = 1 "+
                       "and stuann.mk_student_nr = acasun.fk_student_nr " +
                       "and stu.nr = acasun.fk_student_nr " +
                        "and adr.fk_adrcatypfk_adrc = 1 " +
                        "and  adr.fk_adrcatypfk_adrt = 1 " +
                        "and adr.reference_no = acasun.fk_student_nr " +
                          "and adrph.fk_adrcatcode(+) = 1 " +
                        "and distype.code=stuann.MK_DISABILITY_TYPE "+
                         "and adrph.reference_no(+) = acasun.fk_student_nr and ";

		where += "(";

		for(l = 0; l < sites.size(); l++) {
			selectedCourse = (sites.get(l)).toString().substring(0,7);
			selectedYear = "20" +(sites.get(l)).toString().substring(8,10);
			selectedSemester = (sites.get(l)).toString().substring(11,13);
			
			String academicPeriod ="";
			if (selectedSemester.equals("Y1")) {
				academicPeriod ="0";
			}else if(selectedSemester.equals("Y2")){
				academicPeriod="6";
			}else if(selectedSemester.equals("S1")){
				academicPeriod="1";
			}else{
				academicPeriod="2";
			}

			where += "(" +
			
			"acasun.mk_academic_year = "+selectedYear+" " +
			"AND acasun.mk_study_unit_code = '"+selectedCourse+"' " +
			"AND acasun.mk_academic_period = "+academicPeriod+" )";

			if(l != (sites.size() -1)) {
				where += " or ";
			}
		}

		where += ") ";

		//completed
		/*if(!confineEmail.equalsIgnoreCase("notspecified")) {
			select += ", solact.email_verified ";
			from += ", solact ";
			if (confineEmail.equalsIgnoreCase("Yes")){
				where += "and (nvl(adrph.email_address,'X') != 'X' and adrph.email_address != ' ') "  +
				"and solact.student_nr = stu.nr " +
				"and solact.email_verified = 'Y' ";
			}

		}*/
		
		
		if(!disabilityCode.equalsIgnoreCase("notspecified")) {
			
			where += " and distype.code="+disabilityCode+" " ;
			System.out.println("This where clause is :"+where);
		}

		//completed
		if(!confineGender.equals("notspecified")) {
			if(confineGender.equals("M")) {
				where += "and stu.gender = 'M' ";
			}else {
				where += "and stu.gender = 'F' ";
			}
		}
		//completed
		if(!confineHomeLanguage.equalsIgnoreCase("notspecified")) {
			where += "and stu.mk_home_language = '" + confineHomeLanguage +"' ";
		}

		//completed
		if(!confineCountry.equalsIgnoreCase("notspecified")) {
			where += "and stu.mk_country_code = '" + confineCountry + "' ";
		}

		//completed
		if(!confineCorrespondenceLanguage.equalsIgnoreCase("notspecified")) {
			if (confineCorrespondenceLanguage.equalsIgnoreCase("E")){
				where += "and stu.mk_correspondence = 'E' ";
			}	else if (confineCorrespondenceLanguage.equalsIgnoreCase("A")){
				where += "and stu.mk_correspondence = 'A' ";
			}
		}

		//completed
		if(!confineOnline.equalsIgnoreCase("notspecified")) {
			if (confineOnline.equalsIgnoreCase("No")){
				where += "and stuann.sol_user_flag = 'N' ";
			}	else if (confineOnline.equalsIgnoreCase("Yes")){
				where += "and stuann.sol_user_flag = 'Y' ";
			}
		}

		//completed
		if(!confineProvince.equalsIgnoreCase("notspecified")) {
			from += ", ldd ";
			where += "and stu.fk_lddcode = ldd.code and ldd.fk_prvcode = " + confineProvince + " ";
		}

		/**
		if(!confineDistrict.equalsIgnoreCase("notspecified")) {
			if(!(confineProvince.equalsIgnoreCase("notspecified"))) {
				where += "and ldd.code = " + confineDistrict + " ";
			}else {
				where += "and stu.fk_lddcode = " + confineDistrict + " ";
			}
		}
		**/
		//completed -- it shows the code and not the name

		if(!confineRegion.equalsIgnoreCase("notspecified")) {
			from += ", regoff ";
			//where += "and stuann.mk_regional_office(+) = regoff.code and stuann.mk_regional_office = " + confineRegion + " ";
			where += "and stuann.mk_regional_office(+) = regoff.code and stuann.mk_regional_office = " + confineRegion + " "+
                     " and regoff.service_type in ('A','H','S')" ;
			System.out.println("This where clause is :"+where);
		}

		//making provision for the residential region and NOT only the registration region
		if(!confineResRegion.equalsIgnoreCase("notspecified")) {
			if(confineProvince.equalsIgnoreCase("notspecified")) {
				from += ", ldd ";
			}

			where += "and stu.fk_lddcode = ldd.code and ldd.mk_region_code = " + confineResRegion + " ";
		}

		//completed
		if(!confineRace.equalsIgnoreCase("notspecified")) {
			where += "and stu.fk_racecode = " + confineRace + " ";
		}
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			log.debug("GENERATED QUERY for STUDENT_LIST: " + select + from + where);
			List queryList = jdt.queryForList(select + from + where);
			Iterator i = queryList.iterator();

			long t0 = System.currentTimeMillis();
			System.out.println(" Starting to measure the time for this query.........");

			while(i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				StudentDetail studentDetails = new StudentDetail();
				studentDetails.setStudentNumber(data.get("STUDENT_NUMBER").toString());
				studentDetails.setGender(data.get("GENDER").toString());
				studentDetails.setTitle(data.get("MK_TITLE").toString());
				studentDetails.setLastName(data.get("SURNAME").toString());
				studentDetails.setFirstNames(data.get("FIRST_NAMES").toString());
				studentDetails.setInitials(data.get("INITIALS").toString());
				if(data.get("EMAIL_ADDRESS") == null)  {
					studentDetails.setEmailAddress("");
				}else {
					studentDetails.setEmailAddress(data.get("EMAIL_ADDRESS").toString());
				}
				String al = "ADDRESS_LINE_";
				String completeAddress = "";
				int k = 1;
				for(int j = 0; j < 6; j++) {
					al = al + ("" + k);
					if(!(data.get(al) == null)) {
						completeAddress += data.get(al) + " ";
					}
					al = "ADDRESS_LINE_";
					k += 1;
				}
				studentDetails.setPostalAddress(completeAddress);
				if(data.get("HOME_NUMBER") == null) {
						studentDetails.setCellularNumber("");
				}else {
					studentDetails.setHomePhoneNumber(data.get("HOME_NUMBER").toString());
				}
				if(data.get("WORK_NUMBER") == null) {
					studentDetails.setWorkPhoneNumber("");
				}else {
					studentDetails.setWorkPhoneNumber(data.get("WORK_NUMBER").toString());
				}
				if(data.get("FAX_NUMBER") == null) {
					studentDetails.setCellularNumber("");
				}else {
					studentDetails.setFaxNumber(data.get("FAX_NUMBER").toString());
				}
				if(data.get("CELL_NUMBER") == null) {
					studentDetails.setCellularNumber("");
				}
				else {
					studentDetails.setCellularNumber(data.get("CELL_NUMBER").toString());
				}
				studentDetails.setHomeLanguage(data.get("MK_HOME_LANGUAGE").toString());
				studentDetails.setCorrespondenceLanguage(data.get("MK_CORRESPONDENCE").toString());
				studentDetails.setRegistrationStatus(data.get("STATUS_CODE").toString());
				//studentDetails.setPostalCode(data.get("POSTAL_CODE").toString());
				String postalCode = data.get("POSTAL_CODE").toString();
				studentDetails.setDisability(data.get("ENG_DESCRIPTION").toString());
				
				//added zeros to the postal code
				if(postalCode.length()==4){
					postalCode = postalCode;
					studentDetails.setPostalCode(postalCode);
				}
				if(postalCode.length()==3){
					postalCode = "0"+postalCode;
					studentDetails.setPostalCode(postalCode);
				}
				if(postalCode.length()==2){
					postalCode = "00"+postalCode;
					studentDetails.setPostalCode(postalCode);
				}
				if(postalCode.length()==1){
					postalCode = "000"+postalCode;
					studentDetails.setPostalCode(postalCode);
				}
				if(postalCode.length()==0){
					postalCode = "0000"+postalCode;
					studentDetails.setPostalCode(postalCode);
				}
				//System.out.println("postalcode    2nd one:"+postalCode);
				
					if (isRegion){
					   studentDetails.setRegion(regOffHashMap.get(data.get("REGIONAL_OFFICE").toString()));
					}
					if (isResRegion){
					   if(null == data.get("FK_LDDCODE") | "".equals(data.get("FK_LDDCODE"))) {
						    studentDetails.setResRegion("");
					    }else {
						    studentDetails.setResRegion(reglddMap.get(data.get("FK_LDDCODE").toString()));
					    }
			    	}
				list.add(studentDetails);
			}
	  	}  catch (Exception ex) {
			log.error("Exception while trying to build student list object: " + ex.getMessage());
			throw new Exception("Student List DAO : Error "+ ex);
		}
		return list;
	}

	
	public int getAllCompletedStudents(Vector sites)throws Exception {
		ArrayList list = new ArrayList();
		String selectedCourse = "";
		String selectedYear = "";
		String selectedSemester = "";
            int completedStudents=0;
		int l = 0;
		log = LogFactory.getLog(this.getClass());
		String select =   "select  count(*) ";
		String from = "from acasun, stuann, stu, adr, adrph ";
		
		String where = "where acasun.cancellation_code = 'NC' " +
        "and  acasun.exemption_code NOT IN (2, 5) " +
        "AND stuann.mk_academic_year = acasun.mk_academic_year " +
        "AND stuann.mk_academic_period = 1 "+
        "AND stuann.mk_student_nr = acasun.fk_student_nr " +
        "AND stu.nr = acasun.fk_student_nr " +
         "AND adr.fk_adrcatypfk_adrc = 1 " +
         "AND adr.fk_adrcatypfk_adrt = 1 "+
          "AND adr.reference_no = acasun.fk_student_nr "  +
         "AND adrph.fk_adrcatcode(+) = 1 " +
         "AND adrph.reference_no(+) = acasun.fk_student_nr and " ;
		where += "(";

		for(l = 0; l < sites.size(); l++) {
			selectedCourse = (sites.get(l)).toString().substring(0,7);
			
			selectedYear = "20" +(sites.get(l)).toString().substring(8,10);
			selectedSemester = (sites.get(l)).toString().substring(11,13);
			
			String academicPeriod ="";
			if (selectedSemester.equals("Y1")) {
				academicPeriod ="0";
			}else if(selectedSemester.equals("Y2")){
				academicPeriod="6";
			}else if(selectedSemester.equals("S1")){
				academicPeriod="1";
			}else{
				academicPeriod="2";
			}
			
			where += "(" +
			"acasun.mk_academic_year = "+selectedYear+" "+
			"AND acasun.mk_study_unit_code = '"+selectedCourse+"' "+
			"AND acasun.mk_academic_period = "+academicPeriod+" )";


			if(l != (sites.size() -1)) {
				where += " or ";
			}
		}

		

		where += ")";



		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			log.debug("GENERATED QUERY for STUDENT_LIST: " + select + from + where);
			//System.out.println(" Trying 2 get pass this ");
			completedStudents = jdt.queryForInt(select + from + where);
			
			//contains total no.of students ,selected by lecturer in step2 on selection of courses.
		}  catch (Exception ex) {
			log.error("Exception while trying to build student list object: " + ex.getMessage());
			throw new Exception("Student List DAO : Error "+ ex);
		}
		
		
	return completedStudents;
	
	}
	
	
	

	public String getResidentialCode(String fk_code) throws Exception{
		String rescodequery = "select ldd.MK_REGION_CODE resident_code from ldd where ldd.CODE = " + fk_code;
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(rescodequery);
			Iterator i = queryList.iterator();
			if(i.hasNext()) {
				ListOrderedMap dataResRegion = (ListOrderedMap) i.next();
				return dataResRegion.get("RESIDENT_CODE").toString();
			}
		}catch(Exception ex) {
			throw new Exception("Populating Residential query: Error "+ ex);
		}
		return "";
	}



	public ArrayList getHomeLanguages() throws Exception{
		ArrayList list = new ArrayList();
		String languageQuery = "SELECT CODE, ENG_DESCRIPTION FROM TAL ORDER BY ENG_DESCRIPTION, CODE";

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(languageQuery);
			Iterator i = queryList.iterator();
			list.add(new org.apache.struts.util.LabelValueBean("Not specified","notSpecified"));
			while(i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				StudentDetail homelanguages = new StudentDetail();
				homelanguages.setHomeLanguage(data.get("ENG_DESCRIPTION").toString());
				list.add(new org.apache.struts.util.LabelValueBean(homelanguages.getHomeLanguage(),data.get("CODE").toString()));
			}
		}  catch (Exception ex) {
		       throw new Exception("Home language query: Error "+ ex);
		    }
		return list;
	}
	
	public ArrayList getDisabilityTypes() throws Exception{
		ArrayList list = new ArrayList();
		String languageQuery = "SELECT CODE,ENG_DESCRIPTION FROM DISTYPE WHERE  CODE BETWEEN 2 AND 23";

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(languageQuery);
			Iterator i = queryList.iterator();
			list.add(new org.apache.struts.util.LabelValueBean("Not specified","notSpecified"));
			while(i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				StudentDetail homelanguages = new StudentDetail();
				homelanguages.setHomeLanguage(data.get("ENG_DESCRIPTION").toString());
				list.add(new org.apache.struts.util.LabelValueBean(homelanguages.getHomeLanguage(),data.get("CODE").toString()));
			}
		}  catch (Exception ex) {
		       throw new Exception("Disability type query: Error "+ ex);
		    }
		return list;
	}

	public ArrayList getCountries() throws Exception{
		ArrayList list = new ArrayList();
		String languageQuery = "SELECT CODE, ENG_DESCRIPTION FROM LNS ORDER BY ENG_DESCRIPTION, CODE";

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(languageQuery);
			Iterator i = queryList.iterator();
			list.add(new org.apache.struts.util.LabelValueBean("Not specified","notSpecified"));
			while(i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				StudentDetail countries = new StudentDetail();
				countries.setCountry(data.get("ENG_DESCRIPTION").toString());
				list.add(new org.apache.struts.util.LabelValueBean(countries.getCountry(),data.get("CODE").toString()));
			}
		}  catch (Exception ex) {
		       throw new Exception("Populating country query: Error "+ ex);
		    }
		return list;
	}


	public ArrayList getProvinces() throws Exception{
		ArrayList list = new ArrayList();
		String languageQuery = "SELECT CODE, ENG_DESCRIPTION FROM PRV ORDER BY ENG_DESCRIPTION, CODE";

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(languageQuery);
			Iterator i = queryList.iterator();
			list.add(new org.apache.struts.util.LabelValueBean("Not specified","notSpecified"));
			while(i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				StudentDetail provinces = new StudentDetail();
				provinces.setProvince(data.get("ENG_DESCRIPTION").toString());
				list.add(new org.apache.struts.util.LabelValueBean(provinces.getProvince(),data.get("CODE").toString()));
			}
		}  catch (Exception ex) {
		       throw new Exception("Populating province query: Error "+ ex);
		    }
		return list;
	}


	public ArrayList getRaces() throws Exception{
		ArrayList list = new ArrayList();
		String languageQuery = "SELECT CODE, ENG_DESCRIPTION FROM RACE ORDER BY ENG_DESCRIPTION, CODE";

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(languageQuery);
			Iterator i = queryList.iterator();
			list.add(new org.apache.struts.util.LabelValueBean("Not specified","notSpecified"));
			while(i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				StudentDetail races = new StudentDetail();
				races.setRace(data.get("ENG_DESCRIPTION").toString());
				list.add(new org.apache.struts.util.LabelValueBean(races.getRace(),data.get("CODE").toString()));
			}
		}  catch (Exception ex) {
		       throw new Exception("Populating province query: Error "+ ex);
		    }
		return list;
	}


	public void setRegOffMappings() throws Exception{
		String regionsQuery = " SELECT CODE, ENG_DESCRIPTION FROM REGOFF "+
							  " WHERE SERVICE_TYPE IN  ('A','H','S') "+
							  " ORDER BY ENG_DESCRIPTION, CODE";
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(regionsQuery);
			Iterator i = queryList.iterator();
			while(i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				String key =data.get("CODE").toString();
				String val =data.get("ENG_DESCRIPTION").toString();
				regOffHashMap.put(key,val);
			}
		}  catch (Exception ex) {
		       throw new Exception("Populating Regions query: Error "+ ex);
		    }
	}

	public ArrayList getRegions() throws Exception{
		ArrayList list = new ArrayList();
		String regionsQuery = " SELECT CODE, ENG_DESCRIPTION FROM REGOFF "+
							  " WHERE SERVICE_TYPE IN  ('A','H','S') "+
							  " ORDER BY ENG_DESCRIPTION, CODE";
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(regionsQuery);
			Iterator i = queryList.iterator();
			list.add(new org.apache.struts.util.LabelValueBean("Not specified","notSpecified"));
			while(i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				StudentDetail regions = new StudentDetail();
				regions.setRegion(data.get("ENG_DESCRIPTION").toString());
				list.add(new org.apache.struts.util.LabelValueBean(regions.getRegion(),data.get("CODE").toString()));
			}
		}  catch (Exception ex) {
		       throw new Exception("Populating Regions query: Error "+ ex);
		    }
		return list;
	}
	public void MapRegions() throws Exception{
		ArrayList list = getDistricts();
	try {
    	Iterator j = lddCodes.iterator();
		while(j.hasNext()) {
		String reg_code = j.next().toString();
		String regionsQuery = " SELECT CODE, ENG_DESCRIPTION FROM REGOFF "+
							  " WHERE SERVICE_TYPE IN  ('A','H','S') and code = "+
							  " (select ldd.MK_REGION_CODE resident_code from ldd where ldd.CODE = "+reg_code+" )";


			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(regionsQuery);
			Iterator i = queryList.iterator();
			while(i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				String val =data.get("ENG_DESCRIPTION").toString();
				reglddMap.put(reg_code, val);
			 }
		 }

	}
	catch (Exception ex) {
		       throw new Exception("Populating Regions query: Error "+ ex);
		    }

	   }



	public ArrayList getDistricts() throws Exception{
		ArrayList list = new ArrayList();
		String languageQuery = "select CODE, ENG_DESCRIPTION FROM LDD ORDER BY ENG_DESCRIPTION, CODE";
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(languageQuery);
			Iterator i = queryList.iterator();
			list.add(new org.apache.struts.util.LabelValueBean("Not specified","notSpecified"));
			while(i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				StudentDetail districts = new StudentDetail();
				districts.setDistrict(data.get("ENG_DESCRIPTION").toString());
				lddCodes.add(data.get("CODE").toString());
				list.add(new org.apache.struts.util.LabelValueBean(districts.getDistrict(), data.get("CODE").toString()));
			}
		}  catch (Exception ex) {
		       throw new Exception("Populating Districts query: Error "+ ex);
		    }
		return list;
	}
	
	public boolean checkCurrentModule(String course,String year,String selectedSemester)throws Exception {
        boolean currentModule= false;
        String semister="";
		if (selectedSemester.equals("Y1")) {
			semister ="0";
		}else if(selectedSemester.equals("Y2")){
			semister="6";
		}else if(selectedSemester.equals("S1")){
			semister="1";
		}else{
			semister="2";
		}
		String select =  "select count(*) AS A from stuann, stu, adr, adrph, stusun " +
		   "where stuann.mk_student_nr =stu.nr " +
		   "and stu.nr = adr.reference_no " +
		   "and adr.reference_no = stusun.fk_student_nr "+
		   "and adrph.reference_no(+) = stusun.fk_student_nr "+
		   "and stusun.fk_student_nr = stu.nr "+
		   "and stuann.mk_academic_year = stusun.fk_academic_year "+
		   "and stuann.mk_academic_period = 1 " +
		   "and adr.fk_adrcatypfk_adrc = 1 "+
		   "and adr.fk_adrcatypfk_adrt = 1 "+
		   "and adrph.fk_adrcatcode(+) = 1 " +
		   "and stusun.status_code = 'RG'  " +
		   "and stusun.fk_academic_period = 1 " +
		   "and stusun.semester_period ='"+semister+"'" +
		   "and stusun.mk_study_unit_code = '" + course + "' " +
		   "and stusun.fk_academic_year = " + year;

		try {
			int module = Integer.parseInt(this.querySingleValue(select,"A"));
			if (module >= 1) {
				currentModule = true;
			}
			//contains total no.of students ,selected by lecturer in step2 on selection of courses.
		}  catch (Exception ex) {
			log.error("Exception while trying to build student list object: " + ex.getMessage());
			throw new Exception("Student List DAO : Error "+ ex);
		}
		
	return currentModule;
	
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
}
