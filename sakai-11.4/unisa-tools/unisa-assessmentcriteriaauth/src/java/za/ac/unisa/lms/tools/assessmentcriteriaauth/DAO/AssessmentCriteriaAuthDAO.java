package za.ac.unisa.lms.tools.assessmentcriteriaauth.DAO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.util.LabelValueBean;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.tools.assessmentcriteriaauth.forms.*;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.utils.*;

public class AssessmentCriteriaAuthDAO extends StudentSystemDAO{
	
	public List getCODDeptList(Integer personnelNo) throws Exception {
		ArrayList dptList = new ArrayList();
		
		String sql = "select dpt.code,dpt.eng_description " +
	                 "from dpt " +
	                 "where dpt.head_of_dept = " + personnelNo;
	                
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			String value="";
			String label="";

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				value = data.get("CODE").toString();
				label = data.get("ENG_DESCRIPTION").toString();
				dptList.add(new LabelValueBean(label,value));						
			}
		}
		catch (Exception ex) {
			throw new Exception("AssessmentCriteriaAuthDAO : Error reading table staff,dpt / " + ex);
		}		
		return dptList;		
	}
	
	public List getActingCODDeptList(Integer personnelNo) throws Exception {
		ArrayList dptList = new ArrayList();
		
		//String sql = "select usrsun.mk_study_unit_code" +
	    //             " from usrsun" +
	    //             " where usrsun.system_type = 'H'" +
	    //             " and usrsun.novell_user_id= '" + userid.toUpperCase().trim() + "'";	 
		String sql = "select mk_department_code" +
				" from usrdpt" +
				" where personnel_no= " + personnelNo +
				" and type='DPT'";	
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			String value="";
			String label="";

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				value = data.get("MK_DEPARTMENT_CODE").toString();
				Short code = Short.parseShort(value);
				String sqlDpt = "select dpt.code,dpt.eng_description " +
                "from dpt " +
                "where code = " + code; 
				try{
					List queryDptList = jdt.queryForList(sqlDpt);
					Iterator j = queryDptList.iterator();
					while (j.hasNext()){
						ListOrderedMap dataDpt = (ListOrderedMap) j.next();
						label = dataDpt.get("ENG_DESCRIPTION").toString();
					}
				}
				catch (Exception ex) {
					throw new Exception("AssessmentCriteriaAuthDAO : Error reading table dpt/ " + ex);
				}
				dptList.add(new LabelValueBean(label,value));				
			}
		}
		catch (Exception ex) {
			throw new Exception("AssessmentCriteriaAuthDAO : Error reading table usrdpt/ " + ex);
		}		
		return dptList;		
	}
	
public List <DepartmentRecord> getDepartmentList(Integer persno, Short college, Short school) throws Exception {
		
		List <DepartmentRecord>  dptList = new  ArrayList<DepartmentRecord>();
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
	
public List <CollegeRecord> getCollegeList(Integer persno) throws Exception {
		
		List <CollegeRecord> collegeList = new ArrayList<CollegeRecord>();
				
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
			throw new Exception("AssessmentCriteriaAuthDAO : Error reading Dean college list - table colleg/ " + ex);
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
				throw new Exception("AssessmentCriteriaAuthDAO : Error reading Depity Dean college list - table usrdpt, colleg and dpt/ " + ex);
			}
        }
		
		
		return collegeList;	
		
	}
public List <SchoolRecord> getSchoolList(Integer persno, Short college) throws Exception {
	
	List <SchoolRecord> schoolList = new ArrayList<SchoolRecord>();		
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
				school.setDescription(replaceNull(data.get("ENG_DESCRIPTION")));
				school.setCollegeCode(Short.parseShort((data.get("COLLEGE_CODE").toString())));
				school.setAbbreviation(replaceNull(data.get("ABBREVIATION")));
				String collegeSchoolCode = data.get("COLLEGE_CODE").toString().trim() + "~" + data.get("CODE").toString().trim();
				school.setCollegeSchoolCode(collegeSchoolCode);
				schoolList.add(school);						
			}
    }
	catch (Exception ex) {
		throw new Exception("AssessmentCriteriaAuthDAO : Error reading Direcor school list - table school/ " + ex);
	}
	
	if (persno!=null){
		/* get Deputy Director school list*/
		sql = "select b.code, b.college_code,b.abbreviation,b.eng_description" +
				" from usrdpt a, school b" +
				" where a.personnel_no= " + persno +	
				" and a.school_code = b.code" +	
				" and a.college_code= b.college_code" +
				" and a.type='SCHOOL'" +
				" and b.in_use_flag <> 'N'" +
		 		" order by b.eng_description"; 
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			for(int j=0; j<queryList.size();j++) {
				school = new SchoolRecord();				
				ListOrderedMap data = (ListOrderedMap) queryList.get(j);
				school.setCode(Short.parseShort(data.get("CODE").toString()));
				school.setDescription(replaceNull(data.get("ENG_DESCRIPTION")));
				school.setCollegeCode(Short.parseShort((data.get("COLLEGE_CODE").toString())));
				school.setAbbreviation(replaceNull(data.get("ABBREVIATION")));
				String collegeSchoolCode = data.get("COLLEGE_CODE").toString().trim() + "~" + data.get("CODE").toString().trim();
				school.setCollegeSchoolCode(collegeSchoolCode);
				schoolList.add(school);						
			}
		}
		catch (Exception ex) {
			throw new Exception("AssessmentCriteriaAuthDAO : Error reading Depity Direcor school list - table usrdpt and school/ " + ex);
		}
	}
	
	return schoolList;	
	
}

public List getStatusDeptList(Integer persno, Short college, Short school) throws Exception {
	
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

	
	public List getAuthoriseRequestList(short department) throws Exception {
		ArrayList requestList = new ArrayList();
		
		String sql = "select a.year,a.period,a.mk_study_unit_code" +
	                 " from asslog a,sun" +
	                 " where a.type_gc52 = 'ASSESSCRIT'" +
	                 " and a.action_gc53 = 'AUTHREQ'" +
	                 " and a.mk_study_unit_code=sun.code" +
	                 " and sun.mk_department_code=" + department +
	                 " and a.updated_on =" +
	                 " (select max(b.updated_on) from asslog b" +
	                 " where b.year = a.year" +
	                 " and b.period = a.period" +
	                 " and b.mk_study_unit_code = a.mk_study_unit_code" +
	                 " and b.type_gc52 = a.type_gc52)"; 
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			CoursePeriodLookup periodLookup = new CoursePeriodLookup();

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				CoursePeriod course = new CoursePeriod();
				ListOrderedMap data = (ListOrderedMap) i.next();
				course.setCourseCode(data.get("MK_STUDY_UNIT_CODE").toString());
				course.setYear(Integer.parseInt((data.get("YEAR")).toString()));
				course.setSemesterPeriod(Short.parseShort((data.get("PERIOD")).toString()));
				course.setSemesterType(periodLookup.getCourseTypeAsString(course.getSemesterPeriod()));
				requestList.add(course);				
			}
		}
		catch (Exception ex) {
			throw new Exception("AssessmentCriteriaAuthDAO : Error reading table usrsun/ " + ex);
		}		
		return requestList;		
	}
	
	public List getDeptStudyUnitList(short acadYear, short semester, short department) throws Exception {
		ArrayList list = new ArrayList();
		
		String sql = "select sun.code" +
        //" from sun,sunpdt" +
        " from sun" +
        " where sun.mk_department_code=" + department +
        " and sun.in_use_flag = 'Y'" +
        " and sun.formal_tuition_fla not in ('T','L','R','P','X','M')" +
        " and not (sun.formal_tuition_fla ='F' and (sun.academic_level='H' or sun.academic_level='M' or sun.academic_level='D') and sun.research_flag!='N')" +
        " and not (sun.formal_tuition_fla ='F' and sun.experiental_durat > 1)" +
        " and (sun.to_year = 0" +
        " or sun.to_year >= " + acadYear +
        " or sun.to_year = null)" +
        " and from_year <= " + acadYear +
        //" and sun.code = sunpdt.fk_suncode" +
        //" and sunpdt.mk_academic_year= " + acadYear +
        //" and sunpdt.mk_academic_period=1" +
        //" and sunpdt.semester_period=" + semester +
        " order by sun.code";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			CoursePeriodLookup periodLookup = new CoursePeriodLookup();

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				list.add(data.get("CODE").toString());
			}
		}
		catch (Exception ex) {
			throw new Exception("AssessmentCriteriaAuthDAO : Error reading table sun, sundpt / " + ex);
		}				
		return list;
	}
	
	public boolean existSUNPDT(short acadYear, short semester,String studyUnit) {
		String sql = "select count(*) " +
		             "from sunpdt " +
		             " where sunpdt.fk_suncode ='" + studyUnit.toUpperCase().trim() + "'" + 
		             " and sunpdt.mk_academic_year= " + acadYear +
		             " and sunpdt.mk_academic_period=1" +
		             " and sunpdt.semester_period=" + semester;
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public String getPrimaryLecturer(short academicYear, short semester, String studyUnit) throws Exception {
		String primaryLecturer="";
		
		String sql = "select novell_user_id" +
			" from usrsun" +
			" where mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
			" and mk_academic_year = " + academicYear +
			" and mk_semester_period = " + semester +
			" and system_type = 'L'" +
			" and access_level = 'PRIML'";
			try{ 
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(sql);
				CoursePeriodLookup periodLookup = new CoursePeriodLookup();

				Iterator i = queryList.iterator();
				while (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					primaryLecturer = data.get("NOVELL_USER_ID").toString();
					
				}
			}
			catch (Exception ex) {
				throw new Exception("AssessmentCriteriaAuthDAO : Error reading table usrsun / " + ex);
			}				
			return primaryLecturer;	
	}
	
	public String getStudyUnitDesc(String code) throws Exception{
		String description = "";
				
		String sql = "select code,eng_long_descripti" + 
		             " from sun " +
		             " where code = '" + code.toUpperCase() + "'";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				description=data.get("ENG_LONG_DESCRIPTI").toString();					
			}
		}
		catch (Exception ex) {
			throw new Exception("Error reading Study Unit / " + ex);
		}		
		return description;		
	}	
	
	public StudyUnit getStudyUnit(String code) throws Exception{
		StudyUnit studyUnit = new StudyUnit();
				
		String sql = "select code,eng_long_descripti,mk_department_code,research_flag,academic_level,formal_tuition_fla,experiental_durat,nqf_category,auto_exam_admissio "+ 
		             " from sun " +
		             " where code = '" + code.toUpperCase() + "'" +
		             " and in_use_flag='Y'";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				studyUnit.setCode(data.get("CODE").toString());
				studyUnit.setDescription(data.get("ENG_LONG_DESCRIPTI").toString());
				studyUnit.setFormalTuitionFlag(data.get("FORMAL_TUITION_FLA").toString());
				studyUnit.setAcademicLevel(data.get("ACADEMIC_LEVEL").toString());
				studyUnit.setResearchFlag(replaceNull(data.get("RESEARCH_FLAG")));
				studyUnit.setAutoExamAdmission(replaceNull(data.get("AUTO_EXAM_ADMISSIO")));
				studyUnit.setDepartment(data.get("MK_DEPARTMENT_CODE").toString());	
				if (data.get("EXPERIENTAL_DURAT")==null){
					studyUnit.setExperiental_duration(0);
				}else{
					studyUnit.setExperiental_duration(Integer.parseInt(data.get("EXPERIENTAL_DURAT").toString()));
				}
				if (data.get("NQF_CATEGORY")==null){
					studyUnit.setNqfCategory("");
				}else{
					studyUnit.setNqfCategory(data.get("NQF_CATEGORY").toString());
				}
				studyUnit.setPostGraduateFlag("N");
				if (studyUnit.getAcademicLevel().trim().equalsIgnoreCase("H") || 
						studyUnit.getAcademicLevel().trim().equalsIgnoreCase("M") || 
						studyUnit.getAcademicLevel().trim().equalsIgnoreCase("D")){
					studyUnit.setPostGraduateFlag("Y");
				}				
			}
		}
		catch (Exception ex) {
			throw new Exception("Error reading Study Unit / " + ex);
		}		
		return studyUnit;		
	}	
	
	private String replaceNull(Object object){
		String stringValue="";
		if (object==null){			
		}else{
			stringValue=object.toString();
		}			
		return stringValue;
	}
}
