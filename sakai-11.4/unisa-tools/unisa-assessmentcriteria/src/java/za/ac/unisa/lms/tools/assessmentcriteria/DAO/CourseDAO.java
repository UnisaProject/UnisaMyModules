package za.ac.unisa.lms.tools.assessmentcriteria.DAO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.dao.general.DepartmentDAO;
import za.ac.unisa.lms.dao.general.UserDAO;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.domain.assessmentCriteria.Examination;
import za.ac.unisa.lms.domain.assessmentCriteria.Marker;

public class CourseDAO extends StudentSystemDAO{
	
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
	
	public boolean isSunpdtExist(Short academicYear,Short semester,String studyUnit) throws Exception{
			
		String sql="select count(*) " +
		           "from sunpdt " +
		           "where mk_academic_year= " + academicYear +
		           " and mk_academic_period= 1" +
		           " and fk_suncode = '" + studyUnit.toUpperCase().trim() + "'" +
		           " and semester_period = " + semester;
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int result = jdt.queryForInt(sql) ;
			if (result == 0) {
				return false;
			} else {
				return true;
			}
		}
		catch (Exception ex) {
			throw new Exception("Error reading Study unit period detail / " + ex);
		}				
	}	
	
	public boolean isDueDateOnPublicHoliday(String dueDate,Short year)throws Exception{
			
		String sql="select count(*)" +
			" from regdat a, gencod b" + 
			" where a.type=b.code " +
			" and a.academic_year=" + year +
		    " and semester_period=0" +
		    " and b.info='Public Holidays'" +
		    " and b.in_use_flag ='Y'" +
		    " and to_char(a.from_date,'YYYYMMDD')='" + dueDate +"'";

		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int result = jdt.queryForInt(sql) ;
			if (result == 0) {
				return false;
			} else {
				return true;
			}
		}
		catch (Exception ex) {
			throw new Exception("Error reading REGDAT / " + ex);
		}				
	}	
	
	public boolean isPublicHolidaysSetForAcademicYear(Short year)throws Exception{
		
		String sql="select count(*)" +
			" from regdat a, gencod b" + 
			" where a.type=b.code " +
			" and a.academic_year=" + year +
		    " and semester_period=0" +
		    " and b.info='Public Holidays'" +
		    " and b.in_use_flag ='Y'";

		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int result = jdt.queryForInt(sql) ;
			if (result < 12) {
				return false;
			} else {
				return true;
			}
		}
		catch (Exception ex) {
			throw new Exception("Error reading REGDAT : Public holidays set/ " + ex);
		}				
	}	
	
	public List getExamDates(Short year,Short period,String studyUnit) throws Exception {
		ArrayList list = new ArrayList();
		String examDate = "";
		
		String sql="select to_char(date0,'YYYYMMDD') as exam_date " +
        "from xamdat" +
        " where fk_exam_year= " + year +
        " and mk_exam_period_cod = " + period +
        " and fk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'";
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				examDate=replaceNull(data.get("exam_date"));	
				list.add(examDate);
			}
		}
		catch (Exception ex) {
			throw new Exception("CourseDAO : Error reading table xamdat / " + ex);
		}		
		return list;		
	}
	
	public int getProjectedNumberOfAss(String testDate,String studyUnit, Short acadYear,Integer dummyAcadYear, Short semester) throws Exception{
		int totalProjection = 0;
		int year = acadYear - 1; 
				
		String sql = "select a.mk_study_unit_code as module, SUM(b.NR_OF_STUDENTS_REG - b.NR_OF_STUDENTS_CAN) as projection" +
			" from unqass a, sunpdt b" +
			" where a.year in (" + acadYear + "," + dummyAcadYear + ")" +
			" and a.period=" + semester + 
			" and a.type in ('A','H')" + 
			" and to_char(a.closing_date,'YYYYMMDD') = '" + testDate + "'" +
			" and b.MK_ACADEMIC_YEAR = " + year +
			" and a.period = b.SEMESTER_PERIOD" +
			" and a.mk_study_unit_code = b.fk_suncode" +
			" group by a.mk_study_unit_code"; 
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				String module="";
				int projection=0;
				ListOrderedMap data = (ListOrderedMap) i.next();
				module = data.get("module").toString();
				projection = Integer.parseInt(data.get("projection").toString());
				
				totalProjection = projection + totalProjection;
			}
		}
		catch (Exception ex) {
			throw new Exception("Error reading ass projection for an module / " + ex);
		}		
		//get unqass records with no sunpdt records - possible new codes
		String sql2 = "select a.mk_study_unit_code as module" +
					" from unqass a" +
					" where a.year in (" + acadYear + "," + dummyAcadYear + ")" +
					" and a.period=" + semester + 
					" and a.type in ('A','H')" + 
					" and to_char(a.closing_date,'YYYYMMDD') = '" + testDate + "'" +
					" and not exists(select * from sunpdt b" +
					" where b.MK_ACADEMIC_YEAR = " + year +
					" and b.SEMESTER_PERIOD = a.period" +
					" and b.fk_suncode = a.mk_study_unit_code)";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql2);

			Iterator j = queryList.iterator();
			while (j.hasNext()) {
				String module="";
				int projection=0;
				ListOrderedMap data = (ListOrderedMap) j.next();
				module = data.get("module").toString();				
				projection = getNrStudentsRegistered(module,acadYear,semester);				
				totalProjection = projection + totalProjection;
			}
		}
		catch (Exception ex) {
			throw new Exception("Error reading ass projection for an module / " + ex);
		}		
		
		return totalProjection;		
	}
	
	public int getNrStudentsRegistered(String studyUnit, Short acadYear,Short semester) throws Exception{
		
		int nrStudentsReg =0;
		int year = acadYear - 1; 
				
		String sql = "select (nr_of_students_reg - nr_of_students_can) as  nrRegStudents"+ 
		             " from sunpdt " +
		             " where fk_suncode = '" + studyUnit.toUpperCase() + "'" +
		             " and mk_academic_year = " + year +
		             " and semester_period=" + semester;
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				nrStudentsReg = Integer.parseInt(data.get("nrRegStudents").toString());
			}
		}
		catch (Exception ex) {
			throw new Exception("Error reading SUNPDT - new code/ " + ex);
		}	
		
		if (nrStudentsReg==0){
				//check if this is a new module code that replaces an old module code(only 1 old code)
				String oldModule = getOldCode(studyUnit, acadYear);
				if (!oldModule.equalsIgnoreCase("")){
						//get number of students registered for old code
						String sql2 = "select (nr_of_students_reg - nr_of_students_can) as  nrRegStudents"+ 
			             " from sunpdt " +
			             " where fk_suncode = '" + oldModule + "'" +
			             " and mk_academic_year = " + year +
			             " and semester_period=" + semester;
						try{ 
							JdbcTemplate jdt = new JdbcTemplate(getDataSource());
							List queryList = jdt.queryForList(sql2);
			
							Iterator j = queryList.iterator();
							while (j.hasNext()) {
								ListOrderedMap data = (ListOrderedMap) j.next();
								nrStudentsReg = Integer.parseInt(data.get("nrRegStudents").toString());
							}
						}
						catch (Exception ex) {
							throw new Exception("Error reading SUNPDT - old code / " + ex);
						}
					}
			}
		return nrStudentsReg;		
	}	
	
	public String getOldCode(String newModule, Short fromYear) throws Exception{
		String module = "";
		//check if this is a new module code that replaces an old module code(only 1 old code)
		List oldList = new ArrayList();
		oldList = getOldModuleList(newModule, fromYear);
		if (oldList.size()==1){
			//check that old module do not split in 2
			String oldModule = oldList.get(0).toString(); 
			List newList = new ArrayList();
			newList = getNewModuleList(oldModule, fromYear);
			if (newList.size()==1){
			 	module = oldModule;
			}
		}
		return module;
	}
	
	public List getOldModuleList(String newModule, Short fromYear) throws Exception{
		 
		List moduleList = new ArrayList();
		String oldModule ="";
		int toYear = fromYear -1 ;
		
		String sql = "select a.fk_startcode as oldCode, a.fk_equivcode as newCode" +
		" from sunequ a, sun oldsun, sun newsun" +
		" where a.fk_equivcode='" + newModule + "'" +
		" and a.fk_equivcode=newsun.code" +
		" and newsun.from_year=" + fromYear +
		" and a.fk_startcode=oldsun.code" +
		" and oldsun.to_year=" + toYear + "" +
		" and a.type=1";
		
	try{
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);

		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			oldModule = data.get("oldCode").toString();
			moduleList.add(oldModule);
		}	
	}
	catch (Exception ex) {
		throw new Exception("Error reading SUNEQU / " + ex);
	}
		return moduleList;
	
	}	
	
	public List getNewModuleList(String oldModule, Short fromYear) throws Exception{
		 
		List moduleList = new ArrayList();
		String newModule ="";
		int toYear = fromYear - 1;
		
		String sql = "select a.fk_startcode as oldCode, a.fk_equivcode as newCode" +
		" from sunequ a, sun oldsun, sun newsun" +
		" where a.fk_startcode='" + oldModule + "'" +
		" and a.fk_startcode=oldsun.code" +
		" and oldsun.to_year=" + toYear +
		" and fk_equivcode=newsun.code" +
		" and newsun.from_year=" + fromYear +		
		" and a.type=1";
		
	try{
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);

		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			newModule = data.get("newCode").toString();
			moduleList.add(newModule);
		}	
	}
	catch (Exception ex) {
		throw new Exception("Error reading SUNEQU / " + ex);
	}
	
		return moduleList;
	
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
