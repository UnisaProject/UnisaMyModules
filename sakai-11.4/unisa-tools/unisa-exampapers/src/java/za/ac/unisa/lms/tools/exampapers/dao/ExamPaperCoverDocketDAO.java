package za.ac.unisa.lms.tools.exampapers.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class ExamPaperCoverDocketDAO extends StudentSystemDAO{
	public boolean isExaminerType(String userId,String studyUnit,String examYear,String examPeriod,String paperNr,String examinerType) {
		String sql = "select count(*) " +
		             "from usrsun " +
		             "where novell_user_id = '" + userId.toUpperCase().trim() + "'" +
		             " and mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
		             " and mk_academic_year = " + Short.parseShort(examYear.trim()) +
		             " and mk_semester_period = " + Short.parseShort(examPeriod.trim()) +
		             " and nr = " + Short.parseShort(paperNr.trim()) +
		             " and system_type = 'E'" +
		             " and access_level = '" + examinerType + "'";
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	
	public boolean isCod(String persno,String studyUnit) {
		String sql = "select count(*) " +
		             "from sun,dpt " +
		             "where sun.code = '" + studyUnit.toUpperCase().trim() + "'" +
		             " and sun.mk_department_code = dpt.code" +
		             " and dpt.head_of_dept = '" + persno + "'";	            
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			sql = "select count(*) " +
            "from sun,usrdpt " +
            "where sun.code = '" + studyUnit.toUpperCase().trim() + "'" +
            " and sun.mk_department_code = usrdpt.mk_department_code" +
            " and usrdpt.personnel_no = '" + persno + "'";
			result=jdt.queryForInt(sql);
			if (result == 0){
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}	
	
	public boolean isExamsQa1(String userId){
		String sql = "select count(*) " +
        "from usr,funtyp " +
        "where usr.novell_user_code = '" + userId.toUpperCase().trim() + "'" +
        " and usr.nr = funtyp.fk_usrnr" +
        " and funtyp.fk_functnr=795" +
        " and funtyp.type='QA1'";      
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isExamsQa2(String userId){
		String sql = "select count(*) " +
        "from usr,funtyp " +
        "where usr.novell_user_code = '" + userId.toUpperCase().trim() + "'" +
        " and usr.nr = funtyp.fk_usrnr" +
        " and funtyp.fk_functnr=795" +
        " and funtyp.type='QA2'";      
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isPrintProd(String userId){
		String sql = "select count(*) " +
        "from usr,funtyp " +
        "where usr.novell_user_code = '" + userId.toUpperCase().trim() + "'" +
        " and usr.nr = funtyp.fk_usrnr" +
        " and funtyp.fk_functnr=796" +
        " and funtyp.type='PP'";      
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public List getExaminersForModulePerType(String studyUnit,String examYear,String examPeriod,String paperNr,String examinerType) throws Exception {
		//Determine which of the following 
		ArrayList list = new ArrayList();
		String examiner = "";
		
		String sql = "select title || ' ' || initials || ' ' || surname as examiner" +
        " from usrsun, staff" +
        " where mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
        " and mk_academic_year = " + Short.parseShort(examYear.trim()) +
        " and mk_semester_period = " + Short.parseShort(examPeriod.trim()) +
        " and nr = '" + Short.parseShort(paperNr.trim()) + "'" +
        " and system_type = 'E'" +
        " and access_level = '" + examinerType + "'" +
        " and usrsun.persno = staff.persno order by surname,initials";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				examiner=data.get("examiner").toString();	
				list.add(examiner);
			}
		}
		catch (Exception ex) {
			throw new Exception("ExamPaperCoverDocketDao : Error reading table usrsun / " + ex,ex);
		}		
		return list;		
	}
	
	public boolean xPaperLogExists(String studyUnit,String examYear,Short examPeriod,String paperNr, String docType){
		String sql = "select count(*) " +
        "from xpolog " +
        "where exam_year = " + Short.parseShort(examYear.trim()) +
        " and mk_exam_period = " + examPeriod +
        " and mk_study_unit_code = '" + studyUnit.trim() + "'" +
        " and paper_nr = " + Short.parseShort(paperNr.trim()) +
        " and doc_type = '" + docType.trim() + "'";
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}

}
