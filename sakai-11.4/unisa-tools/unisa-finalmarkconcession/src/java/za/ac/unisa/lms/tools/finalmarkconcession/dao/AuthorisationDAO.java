package za.ac.unisa.lms.tools.finalmarkconcession.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.util.LabelValueBean;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.dao.general.PersonnelDAO;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.domain.general.Department;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.tools.finalmarkconcession.forms.AlternativeExamOpportunityRecord;
import za.ac.unisa.lms.tools.finalmarkconcession.forms.AuthRequestRecord;
import za.ac.unisa.lms.tools.finalmarkconcession.forms.FinalMarkConcessionForm;
import za.ac.unisa.lms.tools.finalmarkconcession.forms.ResultRecord;
import za.ac.unisa.lms.tools.finalmarkconcession.forms.DepartmentRecord;;

public class AuthorisationDAO extends StudentSystemDAO {
	public List getCODDeptList(Integer personnelNo) throws Exception {
		ArrayList dptList = new ArrayList();
		
		String sql = "select dpt.code,dpt.eng_description " +
	                 "from dpt " +
	                 "where dpt.head_of_dept = " + personnelNo +
	                 " and in_use_flag <> 'N'" +
	                 " order by dpt.eng_description";
	                
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
			throw new Exception("AuthorisationDAO : Error reading table staff,dpt / " + ex);
		}		
		return dptList;		
	}
	
	public List getActingCODDeptList(Integer personnelNo) throws Exception {
		ArrayList dptList = new ArrayList();
		
		//String sql = "select usrsun.mk_study_unit_code" +
	    //             " from usrsun" +
	    //             " where usrsun.system_type = 'H'" +
	    //             " and usrsun.novell_user_id= '" + userid.toUpperCase().trim() + "'";	 
		String sql = "select a.mk_department_code" +
				" from usrdpt a, dpt b" +
				" where a.personnel_no= " + personnelNo +	
				" and a.MK_DEPARTMENT_CODE = b.code" +
				" and a.type='DPT'" +
		 		" and b.in_use_flag <> 'N'" +
		 		" order by b.eng_description";
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
					throw new Exception("AuthorisationDAO : Error reading table dpt/ " + ex);
				}
				dptList.add(new LabelValueBean(label,value));				
			}
		}
		catch (Exception ex) {
			throw new Exception("AuthorisationDAO : Error reading table usrdpt/ " + ex);
		}		
		return dptList;		
	}
	
	public List getDeanDeptList(Integer personnelNo)  throws Exception {
		ArrayList dptList = new ArrayList();
		
		/* get Dean department list*/
		String sql = "select b.code, b.eng_description" +
		" from colleg a, dpt b" +
		" where a.dean = " + personnelNo +
		" and a.code = b.college_code" +
		" and b.in_use_flag <> 'N'" +
        " order by b.eng_description";
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
			throw new Exception("AuthorisationDAO : Error reading Dean dept list - table colleg and dpt/ " + ex);
		}		
		
		/* get Deputy Dean department list*/
		sql = "select c.code as dptCode, c.eng_description as dptDesc" +
				" from usrdpt a, dpt c" +
				" where a.personnel_no= " + personnelNo +	
				" and a.college_code = c.college_code" +				
				" and a.type='COLLEGE'" +
		 		" and c.in_use_flag <> 'N'" +
		 		" order by c.eng_description";
		
//		String sql = "select b.code, b.eng_description" +
//				" from colleg a, dpt b" +
//				" where (a.dean = " + personnelNo +
//				" or a.deputy_dean1 = " + personnelNo +
//				" or a.deputy_dean2 = " + personnelNo +
//				" or a.deputy_dean3 = " + personnelNo +
//				" or a.deputy_dean4 = " + personnelNo +
//				") and a.code = b.college_code" +
//				" and b.in_use_flag <> 'N'" +
//	            " order by b.eng_description";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			String value="";
			String label="";

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				value = data.get("dptCode").toString();
				label = data.get("dptDesc").toString();
				dptList.add(new LabelValueBean(label,value));				
			}
		}
		catch (Exception ex) {
			throw new Exception("AuthorisationDAO : Error reading Depity Dean dept list - table usrdpt and dpt/ " + ex);
		}
		
		return dptList;		
	}
	
	public List getDirectorDeptList(Integer personnelNo) throws Exception{
		
		ArrayList dptList = new ArrayList();
		
		/* get School Director department list*/
		String sql = "select b.code, b.eng_description from school a,dpt b" +
		" where a.head_of_school=" + personnelNo +
		" and a.code=B.SCHOOL_CODE " +
		" and A.COLLEGE_CODE=B.COLLEGE_CODE";
		
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
			throw new Exception("AuthorisationDAO : Error reading School Director Dept list - table colleg and dpt/ " + ex);
		}	
		
		/* get Deputy School Director department list*/
		sql = "select c.code as dptCode, c.eng_description as dptDesc" +
		" from usrdpt a, dpt c" +
		" where a.personnel_no= " + personnelNo +	
		" and a.college_code = c.college_code" +	
		" and a.school_code = c.school_code" + 
		" and a.type='SCHOOL'" +
 		" and c.in_use_flag <> 'N'" +
 		" order by c.eng_description";
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			String value="";
			String label="";

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				value = data.get("dptCode").toString();
				label = data.get("dptDesc").toString();
				dptList.add(new LabelValueBean(label,value));				
			}
		}
		catch (Exception ex) {
			throw new Exception("AuthorisationDAO : Error reading Deputy School Director Dept list - table usrdpt and dpt/ " + ex);
		}	
		return dptList;	
	}
	
	public ResultRecord getStudentResultRecord(Short examYear,
			Short examPeriod,
			String studyUnit,
			Integer studentNumber) throws Exception {
		
		ResultRecord result = new ResultRecord();
		
		String sql = "select mk_academic_year,semester_period," +
		"final_mark,mk_result_type_cod, year_mark" +
		" from stxres " +
		" where exam_year = " + examYear +
		" and mk_exam_period_cod = " + examPeriod +
		" and mk_student_nr = " + studentNumber +
		" and mk_study_unit_code = '" + studyUnit + "'";
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				result.setAcademicYear(Short.parseShort(data.get("mk_academic_year").toString()));
				result.setSemester(Short.parseShort(data.get("semester_period").toString()));
				result.setFinalMark(Short.parseShort(data.get("final_mark").toString()));
				result.setYearMark(Double.parseDouble(data.get("year_mark").toString()));
				result.setResultCode(Short.parseShort(data.get("mk_result_type_cod").toString()));
			}
		}
		catch (Exception ex) {
			throw new Exception("AuthorisationDAO : Error reading table stxres/ " + ex);
		}		
		return result;		
	}
		
	
	public List<AuthRequestRecord> getAuthoriseRequestList(short department,String status, short examYear, short examPeriod) throws Exception {
		ArrayList<AuthRequestRecord> requestList = new ArrayList<AuthRequestRecord>();
		
		String sql = "select a.exam_year,a.mk_exam_period_cod,a.mk_study_unit_code,a.mk_student_nr,a.track_status," +
		             " a.asses_opt_gc91,a.support_prov_gc92,a.revised_final_mark,fi_concession_mark,a.ASSESS_OPT_OTHER," +
		             " a.SUPPORT_PROD_OTHER, a.REMARKER,c.CODE as DPTCODE, c.ENG_DESCRIPTION, c.COLLEGE_CODE,"  +
		             " STXRES.MK_ACADEMIC_YEAR as acadYear,STXRES.SEMESTER_PERIOD as semester,nvl(STXRES.FINAL_MARK,0) as finalMark," +
		             " STXRES.MK_RESULT_TYPE_COD as resultCode,nvl(STXRES.YEAR_MARK,0) as yearMark " +
	                 " from xamrmk a,sun b,dpt c, stxres" +
	                 " where a.track_status = '" + status + "'" +
	                 " and a.type = 'F'" +
	                 " and a.exam_year =" + examYear +
	                 " and a.mk_exam_period_cod =" + examPeriod +
	                 " and a.status_code = 'R'" +
	                 " and a.mk_study_unit_code=b.code" +
	                 " and b.mk_department_code=" + department +
	                 " and b.mk_department_code = c.code" +
	                 " and a.EXAM_YEAR=STXRES.EXAM_YEAR" +
	     			 " and a.MK_EXAM_PERIOD_COD=STXRES.MK_EXAM_PERIOD_COD" +
	     			 " and a.MK_STUDENT_NR=STXRES.MK_STUDENT_NR" +
	     			 " and a.MK_STUDY_UNIT_CODE=STXRES.MK_STUDY_UNIT_CODE" +
	                 " order by a.mk_study_unit_code,a.mk_student_nr";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			
			Iterator i = queryList.iterator();			
			while (i.hasNext()) {
				AuthRequestRecord request = new AuthRequestRecord();
				AlternativeExamOpportunityRecord fiConcession = new AlternativeExamOpportunityRecord();
				ResultRecord result = new ResultRecord();
				DepartmentRecord dpt = new DepartmentRecord();
				ListOrderedMap data = (ListOrderedMap) i.next();
				request.setExamYear(Short.parseShort(data.get("EXAM_YEAR").toString()));
				request.setExamPeriod(Short.parseShort(data.get("MK_EXAM_PERIOD_COD").toString()));
				request.setStudyUnit(data.get("MK_STUDY_UNIT_CODE").toString());
				request.setStudentNumber(Integer.parseInt(data.get("MK_STUDENT_NR").toString()));
				request.setStatus(data.get("TRACK_STATUS").toString());
				fiConcession.setAcademicSupportOpt(data.get("support_prov_gc92").toString());
				fiConcession.setAlternativeAssessOpt(data.get("asses_opt_gc91").toString());
				fiConcession.setFinalMark(data.get("revised_final_mark").toString());
				fiConcession.setConcessionMark(replaceNull(data.get("fi_concession_mark")));
				fiConcession.setAcademicSupportOtherDesc(replaceNull(data.get("SUPPORT_PROD_OTHER")));
				fiConcession.setAlternativeAssessOtherDesc(replaceNull(data.get("ASSESS_OPT_OTHER")));									
				result.setAcademicYear(Short.parseShort(data.get("acadYear").toString()));
				result.setSemester(Short.parseShort(data.get("semester").toString()));
				result.setFinalMark(Short.parseShort(data.get("finalMark").toString()));
				result.setYearMark(Double.parseDouble(data.get("yearMark").toString()));
				result.setResultCode(Short.parseShort(data.get("resultCode").toString()));
				dpt.setCode(Short.parseShort(data.get("dptcode").toString()));
				dpt.setDescription(data.get("ENG_DESCRIPTION").toString());
				dpt.setCollegeCode(Short.parseShort(data.get("COLLEGE_CODE").toString()));
				request.setDepartment(dpt);
				request.setFiConcession(fiConcession);
				request.setResult(result);
				
				requestList.add(request);				
			}
		}
		catch (Exception ex) {
			throw new Exception("AuthorisationDAO : Error reading table xamrmk/ " + ex);
		}		
		return requestList;		
	}
	
	public String getStudentName(Integer studentNumber)throws Exception {
		String name="";
		
		String sql = "select trim(surname) || ' ' || trim(initials) || ' ' || trim(mk_title) as stuname" +
	                 " from stu" +
	                 " where nr =" + studentNumber;               
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			
			Iterator i = queryList.iterator();
			while (i.hasNext()) {				
				ListOrderedMap data = (ListOrderedMap) i.next();
				name = data.get("stuname").toString();								
			}
		}
		catch (Exception ex) {
			throw new Exception("AuthorisationDAO : Error reading table stu/ " + ex);
		}		
		return name;		
	}	
	
	public Person getSchoolDirector(Short college,Short school)throws Exception {
		Person director = new Person();
		
		String sql = "select HEAD_OF_SCHOOL" +
        " from school" +
        " where college_code =" + college +
        " and code=" + school;
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {				
				ListOrderedMap data = (ListOrderedMap) i.next();
				String headOfSchool = replaceNull(data.get("HEAD_OF_SCHOOL"));
				if ("".equalsIgnoreCase(headOfSchool)){
				}else{
					PersonnelDAO daopers = new PersonnelDAO();
					director = daopers.getPerson(Integer.parseInt(headOfSchool));
						
				}								
			}
		}
		catch (Exception ex) {
			throw new Exception("AuthorisationDAO : Error reading table school/ " + ex);
		}		
		
		return director;
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
