package za.ac.unisa.lms.tools.exampapers.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class CourseDAO extends StudentSystemDAO{
	
	public StudyUnit getStudyUnit(String code) throws Exception{
		StudyUnit studyUnit = new StudyUnit();
		studyUnit.setErrorMsg("Study unit not found");
		
		String sql = "select code,eng_long_descripti,mk_department_code,academic_level,formal_tuition_fla "+ 
		             " from sun " +
		             " where code = '" + code.toUpperCase() + "'";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				studyUnit.setCode(data.get("CODE").toString());
				studyUnit.setDescription(data.get("ENG_LONG_DESCRIPTI").toString());
				studyUnit.setDepartment(data.get("MK_DEPARTMENT_CODE").toString());	
				studyUnit.setTuitionType(data.get("FORMAL_TUITION_FLA").toString());
				studyUnit.setAcademicLevel(data.get("ACADEMIC_LEVEL").toString());
				studyUnit.setErrorMsg("");
			}
		}
		catch (Exception ex) {
			studyUnit.setErrorMsg("Error reading Study Unit / " + ex);
		}		
		return studyUnit;		
	}
	
	public List getCourses(Short examYear,Short examPeriod, String studyUnit, Short numberOfPapers) throws Exception{
		List list = new Vector();
		
		String sql="select fk_suncode,mk_academic_year,semester_period " +
	           "from sunpdt " +
	           "where ((mk_exam_year= " + examYear +
	           " and mk_exam_period= " + examPeriod +
	           ") or (mk_supp_exam_year= " + examYear +
	           " and mk_supp_exam_perio= " + examPeriod +
	           ") or (mk_3rd_exam_year= " + examYear + 
	           " and mk_3rd_exam_period= " + examPeriod + 
	           ")) and mk_academic_period= 1" +
	           " and fk_suncode = '" + studyUnit.toUpperCase().trim() + "'";	           
			if (numberOfPapers >= 2) {
				sql=sql.trim() + " and number_of_exam_pap >=" + numberOfPapers;
			}
			sql = sql.trim() + " order by mk_academic_year,semester_period desc";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				Course course = new Course();
				course.setCode((data.get("FK_SUNCODE").toString()));
				course.setAcademicYear(Short.parseShort((data.get("MK_ACADEMIC_YEAR").toString())));
				course.setSemester(Short.parseShort((data.get("SEMESTER_PERIOD").toString())));
				list.add(course);				
			}
		}
		catch (Exception ex) {
			throw new Exception("CourseDAO : Error reading study unit period detail / "+ ex,ex);
		}			
		return list;
	}
	
	public boolean isValid(String novellid,String studyUnit,Short academicYear,Short semester) {
		String sql = "select count(*) " +
		             "from usrsun " +
		             "where novell_user_id = '" + novellid.toUpperCase().trim() + "'" +
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

}
