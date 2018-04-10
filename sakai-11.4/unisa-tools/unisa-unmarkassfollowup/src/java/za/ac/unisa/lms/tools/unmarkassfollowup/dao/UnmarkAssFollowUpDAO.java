package za.ac.unisa.lms.tools.unmarkassfollowup.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.util.LabelValueBean;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.dao.general.DepartmentDAO;
import za.ac.unisa.lms.dao.general.PersonnelDAO;
import za.ac.unisa.lms.dao.general.UserDAO;
import za.ac.unisa.lms.domain.general.Department;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.tools.unmarkassfollowup.forms.*;

public class UnmarkAssFollowUpDAO extends StudentSystemDAO {

	public List getCollegeList(Integer persno) throws Exception {
		
		List collegeList = new ArrayList<CollegeRecord>();
				
		CollegeRecord college = new CollegeRecord();	
		
		String sql = "select a.code, a.eng_description, a.abbreviation" +
		" from colleg a";
		if (persno != null){
			sql = sql.trim() +
			" where a.dean = " + persno;
		}
		
        sql = sql.trim() + " order by a.eng_description";
        try{ 
        	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
        	List queryList = new ArrayList();
    		queryList = jdt.queryForList(sql);
    		for (int i=0; i<queryList.size();i++){
    				college = new CollegeRecord();				
    				ListOrderedMap data = (ListOrderedMap) queryList.get(i);
    				college.setCode(Short.parseShort((String)data.get("CODE").toString()));
    				college.setDescription(data.get("ENG_DESCRIPTION").toString());
    				college.setAbbreviation(data.get("ABBREVIATION").toString());
    				collegeList.add(college);						
    			}
        }catch (Exception ex) {
			throw new Exception("UnmarkAssFollowUpDAO : Error reading Dean college list - table colleg/ " + ex);
		}
		
		/* get Deputy Dean college list*/
        if (persno != null){
        	sql = "select b.code, b.eng_description, b.abbreviation" +
			" from usrdpt a, colleg b" +
			" where a.personnel_no= " + persno +	
			" and a.college_code = b.code" +				
			" and a.type='COLLEGE'" +
	 		" order by b.eng_description";
			try{ 
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(sql);
		
				Iterator i = queryList.iterator();
				for(int j=0; j<queryList.size();j++) {
					college = new CollegeRecord();				
					ListOrderedMap data = (ListOrderedMap) queryList.get(j);
					college.setCode(Short.parseShort((String)data.get("CODE").toString()));
					college.setDescription(data.get("ENG_DESCRIPTION").toString());
					college.setAbbreviation(data.get("ABBREVIATION").toString());
					collegeList.add(college);					
				}
			}
			catch (Exception ex) {
				throw new Exception("UnmarkAssFollowUpDAO : Error reading Depity Dean college list - table usrdpt, colleg and dpt/ " + ex);
			}
        }
		
		
		return collegeList;	
		
	}
	
	public String getCollegeAbbreviation(Short code) throws Exception{
			
			String collegeAbbreviation= "";
			
			String sql = "select abbreviation" + 
	        " from colleg" +
	        " where code =" + code; 
			
			try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				collegeAbbreviation = data.get("ABBREVIATION").toString();
				}
			}
			catch (Exception ex) {
			throw new Exception("UnmarkAssFollowUpDAO : : Error reading College / " + ex,ex);
			}		
			return collegeAbbreviation;		
			
		}
	
	public List getDepartmentList(Integer persno, Short college, Short school) throws Exception {
		
		List dptList = new ArrayList();
		List queryList = new ArrayList();
		
		DepartmentRecord dpt = new DepartmentRecord();	
		
		String sql = "select distinct dpt.CODE as dptCode,dpt.ENG_DESCRIPTION as dptDesc,dpt.college_code as college,dpt.school_code as school" +
					 " from dpt,usrdpt" + 
					 " where dpt.in_use_flag <> 'N'";
		
		if (persno!=null){
			sql = sql.trim() + 
			" and (DPT.HEAD_OF_DEPT=" + persno +
			" or (USRDPT.PERSONNEL_NO=" + persno +
			" and USRDPT.TYPE= 'DPT'" +
			" and DPT.CODE = USRDPT.MK_DEPARTMENT_CODE))";
		}
		
		if (college!=null){
			sql = sql.trim() + 
			" and dpt.college_code = " + college; 
		}
		
		if (school!=null){
			sql = sql.trim() + 
			" and dpt.school_code = " + school; 
		}
		
		sql = sql.trim() + " order by dpt.eng_description";
			
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		queryList = jdt.queryForList(sql);
		for (int i=0; i<queryList.size();i++){
				dpt = new DepartmentRecord();				
				ListOrderedMap data = (ListOrderedMap) queryList.get(i);
				dpt.setCode(Short.parseShort(data.get("dptCode").toString()));
				dpt.setDescription(data.get("dptDesc").toString());
				dpt.setCollegeCode(Short.parseShort((data.get("college").toString())));
				dpt.setSchoolCode(Short.parseShort((data.get("school").toString())));
				dptList.add(dpt);						
			}
		return dptList;	
		
	}
	
	public List getSchoolList(Integer persno, Short college) throws Exception {
		
		List schoolList = new ArrayList();		
		SchoolRecord school = new SchoolRecord();	
		
		String sql = "select code,college_code,abbreviation,eng_description from school" + 
		" where school.in_use_flag <> 'N'";
		
		if (persno!=null){
			sql = sql.trim() +
			" and school.HEAD_OF_SCHOOL=" + persno ;
		}
		
		if (college!=null){
			sql = sql.trim() +
			" and school.college_code=" + college ;
		}
		
        sql = sql.trim() + " order by eng_description";
		
        try{ 
        	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
    		List queryList = jdt.queryForList(sql);
    		for (int i=0; i<queryList.size();i++){
    				school = new SchoolRecord();				
    				ListOrderedMap data = (ListOrderedMap) queryList.get(i);
    				school.setCode(Short.parseShort(data.get("CODE").toString()));
    				school.setDescription(data.get("ENG_DESCRIPTION").toString());
    				school.setCollegeCode(Short.parseShort((data.get("COLLEGE_CODE").toString())));
    				school.setAbbreviation(data.get("ABBREVIATION").toString());
    				String collegeSchoolCode = data.get("COLLEGE_CODE").toString().trim() + "~" + data.get("CODE").toString().trim();
    				school.setCollegeSchoolCode(collegeSchoolCode);
    				schoolList.add(school);						
    			}
        }
		catch (Exception ex) {
			throw new Exception("UnmarkAssFollowUpDAO : Error reading Direcor school list - table school/ " + ex);
		}
		
		if (persno!=null){
			/* get Deputy Director school list*/
			sql = "select b.code, b.college_code,b.abbreviation,b.eng_description" +
					" from usrdpt a, school b" +
					" where a.personnel_no= " + persno +	
					" and a.school_code = b.code" +	
					" and a.college_code= b.college_code" +
					" and a.type='SCHOOL'" +
			 		" order by b.eng_description";
			try{ 
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(sql);

				Iterator i = queryList.iterator();
				for(int j=0; j<queryList.size();j++) {
					school = new SchoolRecord();				
					ListOrderedMap data = (ListOrderedMap) queryList.get(j);
					school.setCode(Short.parseShort(data.get("CODE").toString()));
					school.setDescription(data.get("ENG_DESCRIPTION").toString());
					school.setCollegeCode(Short.parseShort((data.get("COLLEGE_CODE").toString())));
					school.setAbbreviation(data.get("ABBREVIATION").toString());
					String collegeSchoolCode = data.get("COLLEGE_CODE").toString().trim() + "~" + data.get("CODE").toString().trim();
					school.setCollegeSchoolCode(collegeSchoolCode);
					schoolList.add(school);						
				}
			}
			catch (Exception ex) {
				throw new Exception("UnmarkAssFollowUpDAO : Error reading Depity Direcor school list - table usrdpt and school/ " + ex);
			}
		}
		
		return schoolList;	
		
	}

	public List getUserStudyUnitList(Short acadYear, Short semester, String userId){
		List userStudyUnitList = new ArrayList();
		
		String sql = "select mk_study_unit_code " +
        "from usrsun " +
        "where novell_user_id = '" + userId.toUpperCase().trim() + "'" +
        " and mk_academic_year = " + acadYear +
        " and mk_semester_period= " + semester +
        " and system_type = 'L'" +
        " and access_level in ('SECDL','CADMN','PRIML')";		
        	
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = new ArrayList();
		queryList = jdt.queryForList(sql);
		for (int i=0; i<queryList.size();i++){
				String studyUnit="";				
				ListOrderedMap data = (ListOrderedMap) queryList.get(i);
				studyUnit =data.get("mk_study_unit_code").toString();
				userStudyUnitList.add(studyUnit);						
			}
		
		return userStudyUnitList;
	}
	
	public boolean isDsaaUser(String userId) {
		String sql = "select count(*) from usr,usgrus" +
			" where USGRUS.FK_USRNR=USR.NR" +
			" and USGRUS.FK_USRGRPNR = 7" +
			" and USGRUS.IN_USE_FLAG <> 'N'" +
			" and USR.NOVELL_USER_CODE='" + userId + "'";

		           
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isAcademic(String userId) {
		int year = Calendar.getInstance().get(Calendar.YEAR) - 1;
		String sql = "select count(*) " +
		             "from usrsun " +
		             "where novell_user_id = '" + userId.toUpperCase().trim() + "'" +
		             " and mk_academic_year >=" + year +
		             " and system_type = 'L'" +
		             " and access_level in ('SECDL','CADMN','PRIML')";
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isValidAcademic(String userId,String studyUnit,Short academicYear,Short semester) {
		String sql = "select count(*) " +
		             "from usrsun " +
		             "where novell_user_id = '" + userId.toUpperCase().trim() + "'" +
		             " and mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
		             " and mk_academic_year = " + academicYear +
		             " and mk_semester_period = " + semester +
		             " and system_type = 'L'" +
		             " and access_level in ('SECDL','CADMN','PRIML')";
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public List getDetailList(Short acadYear, Short semester, Short college, Short school, Short dpt, String studyUnit, String marker, AssignmentRecord assignment, Integer daysOutstanding, String assType, String subMode, String markingMode, Integer toDaysOutstanding) throws Exception {
		
		List detailList = new ArrayList();
		List queryList = new ArrayList();
		
		DetailedRecord detail = new DetailedRecord();	
		
		String sql = 
			"select DECODE(SIGN(TRUNC(STUASS.DATE_RECEIVED - UNQASS.CLOSING_DATE)),-1,TRUNC(sysdate - UNQASS.CLOSING_DATE),TRUNC(sysdate - STUASS.DATE_RECEIVED)) as days," +
			" DPT.ENG_DESCRIPTION as dptDescription, dpt.code as dptCode, stuass.FK_STUDY_UNIT_CODE as studyUnit,stuass.MK_ASSIGNMENT_NR as assNr," +
			" stuass.FK_STUDENT_NR as studentNr,to_char(stuass.DATE_RECEIVED,'YYYY-MM-DD') as dateReceived," + 
			" decode(yrmrk.type,'P','Portfolio','Written') as assType," +
			" decode(stuass.sol_flag,'Y','myUnisa','S','Scanned','O','myUnisa','R','myUnisa','Post') as subMode," +
			" decode(stuass.sol_flag,'O','Onscreen','R','Onscreen','Paper') as markMode," +
			" usrsun.novell_user_id as primLecPersno," +
			" ASSDCT.MK_LECTURER_NR as markerPersno," +
			" (select max(substr(title || ' ' || initials || ' ' || surname,1,28)) from staff where staff.novell_user_id=usrsun.novell_user_id) as primLec," +
			" (select max(substr(title || ' ' || initials || ' ' || surname,1,28)) from staff where staff.persno=ASSDCT.MK_LECTURER_NR) as marker," +
			" ASSDCT.NR as docketNr" +  
			" from stuass, unqass, sun, dpt, dctstu, assdct, stusun, yrmrk,usrsun" + 
			" where STUASS.FK_ACADEMIC_YEAR=" + acadYear +
			" and STUASS.FK_ACADEMIC_PERIOD=1" + 
			" and STUASS.FK_SEMESTER_PERIOD=" + semester +
			" and (stuass.date_returned < to_date('19010101','YYYYMMDD') or stuass.date_returned is null)" +
			" and unqass.year=stuass.fk_academic_year" +
			" and unqass.period=stuass.fk_semester_period" + 
			" and unqass.mk_study_unit_code=stuass.fk_study_unit_code" +
			" and unqass.assignment_nr=stuass.mk_assignment_nr" +
			" and unqass.type in ('H')" + 
			" and stusun.fk_academic_year=stuass.fk_academic_year" +
			" and STUSUN.FK_ACADEMIC_PERIOD=1" +
			" and stusun.fk_student_nr=stuass.fk_student_nr" +
			" and stusun.mk_study_unit_code=stuass.fk_study_unit_code" +
			" and stusun.semester_period=stuass.fk_semester_period" +
			" and stusun.status_code in ('RG','FC')" +
			" and yrmrk.fk_unq_ass_year=unqass.year" +
			" and yrmrk.fk_unq_ass_period=unqass.period" +
			" and yrmrk.fk_unique_nr=unqass.unique_nr" +
			" and yrmrk.normal_weight>0" +
			" and STUASS.FK_STUDY_UNIT_CODE=sun.code" +
			" and sun.mk_department_code=dpt.code" +
			" and ASSDCT.MK_ACADEMIC_YEAR=STUASS.FK_ACADEMIC_YEAR" +
			" and ASSDCT.MK_ACADEMIC_PERIOD=STUASS.FK_SEMESTER_PERIOD" +
			" and ASSDCT.MK_STUDY_UNIT_CODE=STUASS.FK_STUDY_UNIT_CODE" +
			" and ASSDCT.ASSIGNMENT_NR=STUASS.MK_ASSIGNMENT_NR" +
			" and ASSDCT.NR=DCTSTU.FK_ASS_DCT_NR" +
			" and DCTSTU.MK_STUDENT_NR=STUASS.FK_STUDENT_NR" +
			" and DCTSTU.ASS_SEQ_NR=STUASS.SEQUENCE_NR" +
			" and usrsun.mk_study_unit_code(+)=stusun.mk_study_unit_code" +
			" and usrsun.mk_academic_year(+)=stusun.fk_academic_year" +
			" and usrsun.mk_semester_period(+)=stusun.semester_period" +
			" and usrsun.access_level(+)='PRIML'" +
			" and DECODE(SIGN(TRUNC(STUASS.DATE_RECEIVED - UNQASS.CLOSING_DATE)),-1,TRUNC(sysdate - UNQASS.CLOSING_DATE),TRUNC(sysdate - STUASS.DATE_RECEIVED)) >=" + daysOutstanding;
			if (toDaysOutstanding!=null && toDaysOutstanding!=0){
				sql = sql.trim() + " and DECODE(SIGN(TRUNC(STUASS.DATE_RECEIVED - UNQASS.CLOSING_DATE)),-1,TRUNC(sysdate - UNQASS.CLOSING_DATE),TRUNC(sysdate - STUASS.DATE_RECEIVED)) <=" + toDaysOutstanding;
			}
			if (dpt != null){
				sql = sql.trim() + " and SUN.MK_DEPARTMENT_CODE=" + dpt;
			}	
			
			if (college != null){
				sql = sql.trim() + " and SUN.college_code=" + college;
			}
			
			if (school != null){
				sql = sql.trim() + " and SUN.school_code=" + school;
			}
			
			if (studyUnit != null && !studyUnit.trim().equalsIgnoreCase("")){
				sql = sql.trim() + " and STUASS.fk_study_unit_code='" + studyUnit + "'";
			}
			if (assignment != null && !assignment.getStudyUnit().equalsIgnoreCase("") && !assignment.getAssNumber().trim().equalsIgnoreCase("")) {
				sql= sql.trim() + " and STUASS.fk_study_unit_code='" + assignment.getStudyUnit().trim().toUpperCase() + "'" +
				" and STUASS.mk_assignment_nr =" + Short.parseShort(assignment.getAssNumber());
			}
			
			if (marker != null) {
				sql = sql.trim() + " and assdct.mk_lecturer_nr=" + Integer.parseInt(marker);
			}
			
			/*sql = sql.trim() + " order by days desc";*/
			
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			queryList = jdt.queryForList(sql);
			String ignore = "N";
			for (int i=0; i<queryList.size();i++){
					detail = new DetailedRecord();				
					ListOrderedMap data = (ListOrderedMap) queryList.get(i);
					ignore = "N";
					detail.setDaysOutstanding(data.get("days").toString()) ;
					detail.setDptDesc(replaceNull(data.get("dptDescription")));					
					detail.setDptCode(Short.parseShort(data.get("dptCode").toString()));
					detail.setStudyUnit(replaceNull(data.get("studyUnit")));
					detail.setAssignmentNr(replaceNull(data.get("assNr")));
					detail.setStudentNr(replaceNull(data.get("studentNr")));
					detail.setDateReceived(replaceNull(data.get("dateReceived")));
					detail.setAssignmentType(replaceNull(data.get("assType")));
					detail.setSubmissionMode(replaceNull(data.get("subMode")));
					detail.setMarkingMode(replaceNull(data.get("markMode")));
					detail.setMarker(replaceNull(data.get("marker")));
					detail.setMarkerPersno(replaceNull(data.get("markerPersno")));
					detail.setDocketNr(replaceNull(data.get("docketNr")));
					detail.setPrimLecPersno(replaceNull(data.get("primLecPersno")));
					detail.setPrimLecturer(replaceNull(data.get("primLec")));
					if (assType.equalsIgnoreCase("WRITTEN")){
						if (!detail.getAssignmentType().equalsIgnoreCase("WRITTEN")){
							ignore="Y";
						}
					}
					if (assType.equalsIgnoreCase("PORTFOLIO")){
						if (!detail.getAssignmentType().equalsIgnoreCase("PORTFOLIO")){
							ignore="Y";
						}
					}
					if (subMode.equalsIgnoreCase("POST")){
						if (!detail.getSubmissionMode().equalsIgnoreCase("POST")){
							ignore="Y";
						}
					}
					if (subMode.equalsIgnoreCase("SCANNED")){
						if (!detail.getSubmissionMode().equalsIgnoreCase("SCANNED")){
							ignore="Y";
						}
					}
					if (subMode.equalsIgnoreCase("MYUNISA")){
						if (!detail.getSubmissionMode().equalsIgnoreCase("MYUNISA")){
							ignore="Y";
						}
					}
					if (subMode.equalsIgnoreCase("SCANNED")){
						if (!detail.getSubmissionMode().equalsIgnoreCase("SCANNED")){
							ignore="Y";
						}
					}
					if(markingMode.equalsIgnoreCase("PAPER")){
						if(!detail.getMarkingMode().equalsIgnoreCase("PAPER")){	
							ignore = "Y";
						}
					}
					if(markingMode.equalsIgnoreCase("ONSCREEN")){
						if(!detail.getMarkingMode().equalsIgnoreCase("ONSCREEN")){	
							ignore = "Y";
						}	
					}
					if (ignore.equalsIgnoreCase("N")){
						detailList.add(detail);		
					}						
									
				}
		}		
		catch (Exception ex) {
			throw new Exception("UnmarkAssFollowUpDAO : Error reading detail list / " + ex);
		}	
		return detailList;	
		
	}	
	
	public List getSummaryList(Short acadYear, Short semester, Short college, Short school, Short dpt, String studyUnit, AssignmentRecord assignment, Integer marker, Integer daysOutstanding, String assType, String subMode, String markingMode, String summariseOn, String sortOrder) throws Exception {
		
		List summaryList = new ArrayList();
		List queryList = new ArrayList();
		
		SummaryRecord summary = new SummaryRecord();	
		
		String sql = "select";
		
		if (summariseOn.equalsIgnoreCase("DEPARTMENT")){
			sql = sql.trim() +		
				" COLLEG.ABBREVIATION as collegeAbbreviation, COLLEG.CODE as collegeCode," +
				" SCHOOL.ABBREVIATION as schoolAbbreviation, SCHOOL.CODE as schoolCode, " +
				" DPT.ENG_DESCRIPTION as dptDesc, DPT.CODE as dptCode,";
		}
		if (summariseOn.equalsIgnoreCase("STUDYUNIT")){
			sql = sql.trim() +		
				" COLLEG.ABBREVIATION as collegeAbbreviation, COLLEG.CODE as collegeCode," +
				" SCHOOL.ABBREVIATION as schoolAbbreviation, SCHOOL.CODE as schoolCode, " +
				" DPT.ENG_DESCRIPTION as dptDesc, DPT.CODE as dptCode," +				
				" SUN.CODE as studyUnit," +
				" USRSUN.NOVELL_USER_ID as primLec,";
		}
		if (summariseOn.equalsIgnoreCase("ASSIGNMENT")){
			sql = sql.trim() +		
				" COLLEG.ABBREVIATION as collegeAbbreviation, COLLEG.CODE as collegeCode," +
				" SCHOOL.ABBREVIATION as schoolAbbreviation, SCHOOL.CODE as schoolCode, " +
				" DPT.ENG_DESCRIPTION as dptDesc, DPT.CODE as dptCode," +
				" SUN.CODE as studyUnit," +
				" USRSUN.NOVELL_USER_ID as primLec," +
				" STUASS.MK_ASSIGNMENT_NR as assNr,";
		}
			
		sql = sql.trim() +
			" decode(TRUNC((DECODE(SIGN(TRUNC(STUASS.DATE_RECEIVED - UNQASS.CLOSING_DATE)),-1," +  		
			" TRUNC(sysdate - UNQASS.CLOSING_DATE),TRUNC(sysdate - STUASS.DATE_RECEIVED)) -1)/10),1,'11-20'," +
			                                    " 2,'21-30'," +
			                                    " 3,'31-40'," +
			                                    " 4,'41-50'," +
			                                    " 5,'51-60'," +
			                                    " 6,'61-70'," +
			                                    " 7,'71-80'," +
			                                    " 8,'81-90'," +
			                                    " 9,'91-100'," +
			                                    " decode(SIGN(DECODE(SIGN(TRUNC(STUASS.DATE_RECEIVED - UNQASS.CLOSING_DATE)),-1," +
			" TRUNC(sysdate - UNQASS.CLOSING_DATE),TRUNC(sysdate - STUASS.DATE_RECEIVED))-100),1,'101+','0-10')) as outstandingRange," +
			" count(*) as total";
		if (marker !=null && marker!=0){
			sql = sql.trim() +
			" from stuass, unqass, sun, dpt, stusun, yrmrk, usrsun, colleg, school, dctstu, assdct";
		}else{
			sql = sql.trim() +
			" from stuass, unqass, sun, dpt, stusun, yrmrk, usrsun, colleg, school";
		}
		sql = sql.trim() +	
			" where STUASS.FK_ACADEMIC_YEAR=" + acadYear +
			" and STUASS.FK_ACADEMIC_PERIOD=1" + 
			" and STUASS.FK_SEMESTER_PERIOD=" + semester +
			" and (stuass.date_returned < to_date('19010101','YYYYMMDD')" +
			" or stuass.date_returned is null) and unqass.year=stuass.fk_academic_year" + 
			" and unqass.period=stuass.fk_semester_period" + 
			" and unqass.mk_study_unit_code=stuass.fk_study_unit_code" + 
			" and unqass.assignment_nr=stuass.mk_assignment_nr" + 
			" and unqass.type in ('H')" + 
			" and stusun.fk_academic_year=stuass.fk_academic_year" + 
			" and STUSUN.FK_ACADEMIC_PERIOD=1" + 
			" and stusun.fk_student_nr=stuass.fk_student_nr" + 
			" and stusun.mk_study_unit_code=stuass.fk_study_unit_code" + 
			" and stusun.semester_period=stuass.fk_semester_period" + 
			" and stusun.status_code in ('RG','FC')" + 
			" and yrmrk.fk_unq_ass_year=unqass.year" + 
			" and yrmrk.fk_unq_ass_period=unqass.period" + 
			" and yrmrk.fk_unique_nr=unqass.unique_nr" + 
			" and yrmrk.normal_weight>0" + 
			" and STUASS.FK_STUDY_UNIT_CODE=sun.code" + 
			" and sun.mk_department_code=dpt.code" + 
			" and SUN.COLLEGE_CODE=COLLEG.CODE" +
			" and SUN.SCHOOL_CODE=SCHOOL.CODE" +
			" and SCHOOL.COLLEGE_CODE=SUN.COLLEGE_CODE" +
			" and usrsun.mk_study_unit_code(+)=stusun.mk_study_unit_code" + 
			" and usrsun.mk_academic_year(+)=stusun.fk_academic_year" + 
			" and usrsun.mk_semester_period(+)=stusun.semester_period" + 
			" and usrsun.access_level(+)='PRIML'" +
			" and DECODE(SIGN(TRUNC(STUASS.DATE_RECEIVED - UNQASS.CLOSING_DATE)),-1," +
			" TRUNC(sysdate - UNQASS.CLOSING_DATE),TRUNC(sysdate - STUASS.DATE_RECEIVED))>=" + daysOutstanding;
		
			if (marker !=null && marker!=0){
				sql = sql.trim() +
				" and ASSDCT.MK_ACADEMIC_YEAR=STUASS.FK_ACADEMIC_YEAR" +
				" and ASSDCT.MK_ACADEMIC_PERIOD=STUASS.FK_SEMESTER_PERIOD" +
				" and ASSDCT.MK_STUDY_UNIT_CODE=STUASS.FK_STUDY_UNIT_CODE" +
				" and ASSDCT.ASSIGNMENT_NR=STUASS.MK_ASSIGNMENT_NR" +
				" and ASSDCT.NR=DCTSTU.FK_ASS_DCT_NR" +
				" and DCTSTU.MK_STUDENT_NR=STUASS.FK_STUDENT_NR" +
				" and DCTSTU.ASS_SEQ_NR=STUASS.SEQUENCE_NR" +
				" and ASSDCT.MK_LECTURER_NR=" + marker;
			}
		
			if (dpt != null){
				sql = sql.trim() + " and SUN.MK_DEPARTMENT_CODE=" + dpt;
			}	
			
			if (college != null){
				sql = sql.trim() + " and SUN.college_code=" + college;
			}
			
			if (school != null){
				sql = sql.trim() + " and SUN.school_code=" + school;
			}
			
			if (studyUnit != null && !studyUnit.trim().equalsIgnoreCase("")){
				sql = sql.trim() + " and STUASS.fk_study_unit_code='" + studyUnit + "'";
			}
			if (assignment != null && !assignment.getStudyUnit().equalsIgnoreCase("") && !assignment.getAssNumber().equalsIgnoreCase("")) {
				sql= sql.trim() + " and STUASS.fk_study_unit_code='" + assignment.getStudyUnit().trim().toUpperCase() + "'" +
				" and STUASS.mk_assignment_nr =" + Short.parseShort(assignment.getAssNumber());
			}	
			if (!assType.equalsIgnoreCase("ALL")){
				if (assType.equalsIgnoreCase("PORTFOLIO")){
					sql = sql.trim() + " and yrmrk.type='P'";
				}
				if (assType.equalsIgnoreCase("WRITTEN")){
					sql = sql.trim() + " and yrmrk.type<>'P'";
				}
			}
			
			if (!subMode.equalsIgnoreCase("ALL")){
				if (subMode.equalsIgnoreCase("POST")){
					sql = sql.trim() + " and stuass.sol_flag not in ('Y','O','R','S')";
				}
				if (subMode.equalsIgnoreCase("SCANNED")){
					sql = sql.trim() + " and stuass.sol_flag='S'";
				}
				if (subMode.equalsIgnoreCase("MYUNISA")){
					sql = sql.trim() + " and stuass.sol_flag in ('Y','O','R')";
				}
			}
			
			if (!markingMode.equalsIgnoreCase("ALL")){
				if (markingMode.equalsIgnoreCase("ONSCREEN")){
					sql = sql.trim() + " and stuass.sol_flag in ('O','R')";
				}
				if (markingMode.equalsIgnoreCase("PAPER")){
					sql = sql.trim() + " and stuass.sol_flag not in ('O','R')";
				}
			}
		
			sql = sql.trim() + " group by";
			
			if (summariseOn.equalsIgnoreCase("DEPARTMENT")){
				sql = sql.trim() + 
				" COLLEG.ABBREVIATION, COLLEG.CODE," +
				" SCHOOL.ABBREVIATION, SCHOOL.CODE," +
				" DPT.ENG_DESCRIPTION, DPT.CODE,";
			}	
			if (summariseOn.equalsIgnoreCase("STUDYUNIT")){
				sql = sql.trim() + 
				" COLLEG.ABBREVIATION, COLLEG.CODE," +
				" SCHOOL.ABBREVIATION, SCHOOL.CODE," +
				" DPT.ENG_DESCRIPTION, DPT.CODE," +
				" SUN.CODE," +
				" USRSUN.NOVELL_USER_ID,";
			}	
			if (summariseOn.equalsIgnoreCase("ASSIGNMENT")){
				sql = sql.trim() + 
				" COLLEG.ABBREVIATION, COLLEG.CODE," +
				" SCHOOL.ABBREVIATION, SCHOOL.CODE," +
				" DPT.ENG_DESCRIPTION, DPT.CODE," +
				" SUN.CODE," +
				" USRSUN.NOVELL_USER_ID," +
				" STUASS.MK_ASSIGNMENT_NR,";				
			}
			
			sql = sql.trim() + 
			" decode(TRUNC((DECODE(SIGN(TRUNC(STUASS.DATE_RECEIVED - UNQASS.CLOSING_DATE)),-1," +
			" TRUNC(sysdate - UNQASS.CLOSING_DATE),TRUNC(sysdate - STUASS.DATE_RECEIVED)) -1)/10),1,'11-20'," +
			                                    " 2,'21-30'," +
			                                    " 3,'31-40'," +
			                                    " 4,'41-50'," +
			                                    " 5,'51-60'," +
			                                    " 6,'61-70'," +
			                                    " 7,'71-80'," +
			                                    " 8,'81-90'," +
			                                    " 9,'91-100'," +
			                                    " decode(SIGN(DECODE(SIGN(TRUNC(STUASS.DATE_RECEIVED - UNQASS.CLOSING_DATE)),-1," +
			" TRUNC(sysdate - UNQASS.CLOSING_DATE),TRUNC(sysdate - STUASS.DATE_RECEIVED))-100),1,'101+','0-10'))";
			
			
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			queryList = jdt.queryForList(sql);
			for (int i=0; i<queryList.size();i++){
					summary = new SummaryRecord();	
					DepartmentRecord dptRec = new DepartmentRecord();
					Person primLec = new Person();
					ListOrderedMap data = (ListOrderedMap) queryList.get(i);
					summary.setDaysOutstandingRange(replaceNull(data.get("outstandingRange")));
					summary.setTotal(replaceNull(data.get("total")));
					summary.setStudyUnit(replaceNull(data.get("studyUnit")));
					dptRec.setCode(Short.parseShort(data.get("dptCode").toString()));
					dptRec.setDescription(replaceNull(data.get("dptDesc")));
					dptRec.setCollegeCode(Short.parseShort(data.get("collegeCode").toString()));
					dptRec.setSchoolCode(Short.parseShort(data.get("schoolCode").toString()));
					summary.setDepartment(dptRec);
					summary.setCollegeAbbreviation(replaceNull(data.get("collegeAbbreviation")));
					summary.setSchoolAbbreviation(replaceNull(data.get("schoolAbbreviation")));
					summary.setAssignmentNr(replaceNull(data.get("assNr")));
					
					int endIndex=0;
					if (summary.getDaysOutstandingRange().contains("-")){
						endIndex = summary.getDaysOutstandingRange().indexOf("-");
					}else{
						endIndex = summary.getDaysOutstandingRange().indexOf("+");
					}						
					summary.setFirstValueInRange(Short.parseShort(summary.getDaysOutstandingRange().substring(0, endIndex)));
					if (summary.getDaysOutstandingRange().substring(endIndex+1).equalsIgnoreCase("")){
						summary.setLastValueInRange(Short.parseShort("999"));
					}else{
						summary.setLastValueInRange(Short.parseShort(summary.getDaysOutstandingRange().substring(endIndex+1)));
					}					
					UserDAO dao = new UserDAO();
					if (summariseOn.equalsIgnoreCase("DEPARTMENT")){
						summary.setStudyUnit("");
						summary.setAssignmentNr("");
						primLec.setName("");
						summary.setPrimLecturer(primLec);						
					}
					if (summariseOn.equalsIgnoreCase("STUDYUNIT")){
						summary.setAssignmentNr("");
						String novellUserId = replaceNull(data.get("primLec"));
						if (novellUserId!=""){
							primLec = dao.getPerson(novellUserId);
							if (primLec.getName()==null || primLec.getName().trim().equalsIgnoreCase("")){
								primLec.setName(novellUserId);
							}
						}else{
							primLec.setName("");
						}						
						summary.setPrimLecturer(primLec);	
//						primLec.setName(replaceNull(data.get("primLec")));
//						summary.setPrimLecturer(primLec);
					}
					if (summariseOn.equalsIgnoreCase("ASSIGNMENT")){
						String novellUserId = replaceNull(data.get("primLec"));
						if (novellUserId!=""){
							primLec = dao.getPerson(novellUserId);
							if (primLec.getName()==null || primLec.getName().trim().equalsIgnoreCase("")){
								primLec.setName(novellUserId);
							}
						}else{
							primLec.setName("");
						}						
						summary.setPrimLecturer(primLec);	
//						primLec.setName(replaceNull(data.get("primLec")));
//						summary.setPrimLecturer(primLec);	
						
					}
					summaryList.add(summary);
				}
		}		
		catch (Exception ex) {
			throw new Exception("UnmarkAssFollowUpDAO : Error reading summary list / " + ex);
		}	
		return summaryList;	
		
	}	
	
	public DepartmentRecord getDepartment(Short code) throws Exception{
		
		DepartmentRecord dpt = new DepartmentRecord();
		
		String sql = "select code,eng_description,college_code,school_code" + 
        " from dpt" +
        " where code =" + code; 
						
		try{ 
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);
		
		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			dpt.setCode(Short.parseShort((String)data.get("CODE").toString()));
			dpt.setDescription(data.get("ENG_DESCRIPTION").toString());
			dpt.setCollegeCode(Short.parseShort((String)data.get("COLLEGE_CODE").toString()));
			dpt.setSchoolCode(Short.parseShort((String)data.get("SCHOOL_CODE").toString()));
			}
		}
		catch (Exception ex) {
		throw new Exception("UnmarkAssFollowUpDAO : Error reading DPT / " + ex,ex);
		}		
		return dpt;		
		
	}
	
	public List getDeanList(Short code) throws Exception{
		Person person = new Person();
		List deanList = new ArrayList();
				
		String sql = "select dean,abbreviation,deputy_dean1,deputy_dean2,deputy_dean3,deputy_dean4" +
		             " from colleg" +
		             " where colleg.code =" + code;
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				RecipientRecord recipient = new RecipientRecord();
				//dean
				String deanCode = replaceNull(data.get("DEAN"));
				if ("".equalsIgnoreCase(deanCode)){
					}else{
						PersonnelDAO daopers = new PersonnelDAO();
						person = daopers.getPerson(Integer.parseInt(deanCode));
						recipient.setRole("Dean");
						recipient.setRoleAbbrv("DEAN");
						recipient.setPerson(person);
						recipient.setRoleOn(replaceNull(data.get("ABBREVIATION")));
						deanList.add(recipient);
					}
				//deputy deans
//				deanCode = replaceNull(data.get("DEPUTY_DEAN1"));
//				if ("".equalsIgnoreCase(deanCode)){
//					}else{
//						PersonnelDAO daopers = new PersonnelDAO();
//						person = daopers.getPerson(Integer.parseInt(deanCode));
//						if (person.getPersonnelNumber()!=null){
//							recipient = new RecipientRecord();
//							recipient.setRole("Deputy Dean");
//							recipient.setRoleAbbrv("DEAN");
//							recipient.setPerson(person);
//							recipient.setRoleOn(replaceNull(data.get("ABBREVIATION")));
//							deanList.add(recipient);
//						}						
//				}	
//				deanCode = replaceNull(data.get("DEPUTY_DEAN2"));
//				if ("".equalsIgnoreCase(deanCode)){
//					}else{
//						PersonnelDAO daopers = new PersonnelDAO();
//						person = daopers.getPerson(Integer.parseInt(deanCode));
//						if (person.getPersonnelNumber()!=null){
//							recipient = new RecipientRecord();
//							recipient.setRole("Deputy Dean");
//							recipient.setRoleAbbrv("DEAN");
//							recipient.setPerson(person);
//							recipient.setRoleOn(replaceNull(data.get("ABBREVIATION")));
//							deanList.add(recipient);
//						}		
//				}	
//				deanCode = replaceNull(data.get("DEPUTY_DEAN3"));
//				if ("".equalsIgnoreCase(deanCode)){
//					}else{
//						PersonnelDAO daopers = new PersonnelDAO();
//						person = daopers.getPerson(Integer.parseInt(deanCode));
//						if (person.getPersonnelNumber()!=null){
//							recipient = new RecipientRecord();
//							recipient.setRole("Deputy Dean");
//							recipient.setRoleAbbrv("DEAN");
//							recipient.setPerson(person);
//							recipient.setRoleOn(replaceNull(data.get("ABBREVIATION")));
//							deanList.add(recipient);
//						}		
//				}	
//				deanCode = replaceNull(data.get("DEPUTY_DEAN4"));
//				if ("".equalsIgnoreCase(deanCode)){
//					}else{
//						PersonnelDAO daopers = new PersonnelDAO();
//						person = daopers.getPerson(Integer.parseInt(deanCode));
//						if (person.getPersonnelNumber()!=null){
//							recipient = new RecipientRecord();
//							recipient.setRole("Deputy Dean");
//							recipient.setRoleAbbrv("DEAN");
//							recipient.setPerson(person);
//							recipient.setRoleOn(replaceNull(data.get("ABBREVIATION")));
//							deanList.add(recipient);
//						}		
//				}				
			}
		}
		catch (Exception ex) {
			throw new Exception("UnmarkAssFollowUpDAO : Error college for dean / " + ex,ex);
		}	
		sql = "select a.personnel_no,b.surname,b.initials" +
		" from usrdpt a,staff b" +
		" where a.college_code = " + code +	 		
		" and a.type='COLLEGE'" +
 		" and a.personnel_no=b.persno" +
 		" order by b.surname, b.initials";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				RecipientRecord recipient = new RecipientRecord();
				//dean
				String deanCode = replaceNull(data.get("personnel_no"));
				if ("".equalsIgnoreCase(deanCode)){
					}else{
						PersonnelDAO daopers = new PersonnelDAO();
						person = daopers.getPerson(Integer.parseInt(deanCode));
						recipient.setRole("Deputy Dean");
						recipient.setRoleAbbrv("DEAN");
						recipient.setPerson(person);
						recipient.setRoleOn(replaceNull(data.get("ABBREVIATION")));
						deanList.add(recipient);
					}
			}
		}
		catch (Exception ex) {
			throw new Exception("UnmarkAssFollowUpDAO : Error reading usrdpt for deputy dean list / " + ex,ex);
		}
		return deanList;		
	}
	
	public List getUSRSUNTeachingRoles (Short year,Short period,String studyUnit) throws Exception {
		ArrayList list = new ArrayList();
		
		String sql = "select distinct usrsun.novell_user_id,access_level" +
        " from usrsun" +
        " where usrsun.mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
        " and usrsun.mk_academic_year = " + year +
        " and usrsun.mk_semester_period = " + period +
        " and usrsun.system_type = 'L'" +
        " and usrsun.access_level is not null" +
        " and usrsun.access_level <> ' '" +
        " and usrsun.access_level <> 'OBSRV'";
        //" and usrsun.access_level in ('SECDL','PRIML')";
        
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				RecipientRecord recipient = new RecipientRecord();
				UserDAO daouser = new UserDAO();
				PersonnelDAO dao = new PersonnelDAO();
				String novellUserId=data.get("NOVELL_USER_ID").toString();
				String role=data.get("access_level").toString();
				recipient.setRoleAbbrv(role);
				recipient.setPerson(daouser.getPerson(novellUserId));
				if (role.equalsIgnoreCase("SECDL")){
					role = "Secondary Lecturer";
				}
				if (role.equalsIgnoreCase("PRIML")){
					role = "Primary Lecturer";
				}
				if (role.equalsIgnoreCase("CADMIN")){
					role = "Course Admin";
				}
				if (role.equalsIgnoreCase("TUTOR")){
					role = "Tutor";
				}
				recipient.setRole(role);
				recipient.setRoleOn(studyUnit);
				if (recipient.getPerson().getPersonnelNumber()!=null && isInteger(recipient.getPerson().getPersonnelNumber())){
					if (recipient.getPerson().getResignDate()==null || 
							recipient.getPerson().getResignDate().equalsIgnoreCase("")){
						list.add(recipient);
					}else{
						Calendar currentDate = Calendar.getInstance();
						Date nowDate = currentDate.getTime();
							
						SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd");
						String str_date=recipient.getPerson().getResignDate();
					    Date resignDate = (Date)formatter.parse(str_date); 
					        
					    if (resignDate.after(nowDate)){
					       	list.add(recipient);
					    }					
					}				
				}
			}
		}
		catch (Exception ex) {
			throw new Exception("UnmarkAssFollowUpDAO : : : Error reading table usrsun / " + ex);
		}		
		return list;		
	}
	
	public List getDirectorList(Short collegeCode, Short schoolCode)throws Exception{
		
		List directorList = new ArrayList();
		Person person = new Person();
		
		String sql = "select head_of_school, abbreviation" + 
        " from school" +
        " where code =" + schoolCode +
        " and college_code=" + collegeCode; 
						
		try{ 
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);
		
		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			String directorCode = replaceNull(data.get("HEAD_OF_SCHOOL"));
			if ("".equalsIgnoreCase(directorCode)){
				}else{
					PersonnelDAO daopers = new PersonnelDAO();					
					RecipientRecord director = new RecipientRecord();
					person = daopers.getPerson(Integer.parseInt(directorCode));
					director.setRole("Director");
					director.setRoleAbbrv("DIRECTOR");
					director.setPerson(person);
					director.setRoleOn(replaceNull(data.get("ABBREVIATION")));
					directorList.add(director);
				}
			}
		}
		catch (Exception ex) {
			throw new Exception("UnmarkAssFollowUpDAO : Error reading school for school director/ " + ex,ex);
		}	
		sql = "select a.personnel_no,b.surname,b.initials" +
		" from usrdpt a,staff b" +
		" where a.college_code = " + collegeCode +	 
		" and a.school_code = " + schoolCode +
		" and a.type='SCHOOL'" +
 		" and a.personnel_no=b.persno" +
 		" order by b.surname, b.initials";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				RecipientRecord recipient = new RecipientRecord();
				//dean
				String deanCode = replaceNull(data.get("personnel_no"));
				if ("".equalsIgnoreCase(deanCode)){
					}else{
						PersonnelDAO daopers = new PersonnelDAO();
						person = daopers.getPerson(Integer.parseInt(deanCode));
						recipient.setRole("Deputy Director");
						recipient.setRoleAbbrv("DIRECTOR");
						recipient.setPerson(person);
						recipient.setRoleOn(replaceNull(data.get("ABBREVIATION")));
						directorList.add(recipient);
					}
			}
		}
		catch (Exception ex) {
			throw new Exception("UnmarkAssFollowUpDAO : Error getting usrdpt for deputy director list / " + ex,ex);
		}
		
		return directorList;
	}
	
	public void insertEmailLog(EmailLogRecord log) throws Exception {
		
		String sql = "insert into emllog (email_address,date_sent,recipient,recip_type_gc137,program," +
			"email_type_gc138,subject,body0) " +
			"values " +
			"('" + log.getEmailAddress().toUpperCase() + "'," + 
			"timestamp'" + log.getDateSent() + "'," +
			"'" + log.getRecipient() + "'," +
			"'" + log.getRecipientType() + "'," +
			"'ASSFOLLOWUPONLINE'," +
			"'" + log.getEmailType() + "'," +
			"'" + log.getSubject() + "'," +
			"'" + log.getBody() + "')";	
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int result = jdt.update(sql);	
		}
		catch (Exception ex) {
			throw new Exception("UnmarkAssFollowUpDAO : : Error inserting EMLLOG / " + ex,ex);
		}	
	}
	
public void insertEmailApp(EmailLogRecord log, String userId) throws Exception {
		
	String sql = "select count(*) " +
    "from emllog " +
    "where email_address = '" + log.getEmailAddress().toUpperCase().trim() + "'" +
    " and date_sent = timestamp'" + log.getDateSent() + "'";
	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	int result = jdt.queryForInt(sql) ;
	if (result == 0) 
		{//do nothing
	} else {
		sql = "select count(*) " +
	    "from emlapp " +
	    "where fk_email_address = '" + log.getEmailAddress().toUpperCase().trim() + "'" +
	    " and fk_date_sent = timestamp'" + log.getDateSent() + "'";		
		result = jdt.queryForInt(sql) ;
		if (result == 1) 
			{//do nothing
			 //user has already opened email
			}
		else {
			sql = "insert into emlapp (fk_email_address,fk_date_sent,opened_on,opened_by) " +
			"values " +
			"('" + log.getEmailAddress().toUpperCase().trim() + "'," + 
			"timestamp'" + log.getDateSent() + "',sysdate," +
			"'" + userId + "')";	
			try{ 
			result = jdt.update(sql);	
			}
			catch (Exception ex) {
				throw new Exception("UnmarkAssFollowUpDAO : : Error inserting EMLAPP / " + ex,ex);
			}
		}
	}
}
	
	private String replaceNull(Object object){
		String stringValue="";
		if (object==null){			
		}else{
			stringValue=object.toString();
		}			
		return stringValue;
	}
	
	public boolean isInteger(String stringValue) {
		try
		{
			Integer i = Integer.parseInt(stringValue);
			return true;
		}	
		catch(NumberFormatException e)
		{}
		return false;
	}
	
}
