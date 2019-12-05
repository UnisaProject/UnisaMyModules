package za.ac.unisa.lms.dao.general;

import java.util.List;
import java.util.Iterator;

import za.ac.unisa.lms.domain.general.StudyUnit;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class ModuleDAO extends StudentSystemDAO {

	public StudyUnit getStudyUnit(String code) throws Exception{
		StudyUnit studyUnit = new StudyUnit();
				
		String sql = "select code,eng_long_descripti,mk_department_code,research_flag,academic_level,formal_tuition_fla,experiental_durat,nqf_category,college_code,school_code,to_year,from_year "+ 
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
				studyUnit.setEngLongDesc(data.get("ENG_LONG_DESCRIPTI").toString());
				studyUnit.setFormalTuitionFlag(data.get("FORMAL_TUITION_FLA").toString());
				studyUnit.setAcademicLevel(data.get("ACADEMIC_LEVEL").toString());
				studyUnit.setResearchFlag(replaceNull(data.get("RESEARCH_FLAG")));
				studyUnit.setNqfCategory(replaceNull(data.get("NQF_CATEGORY")));
				studyUnit.setDepartmentCode(Short.parseShort(data.get("MK_DEPARTMENT_CODE").toString()));	
				if (data.get("EXPERIENTAL_DURAT")==null){
					studyUnit.setExperiental_duration(Short.parseShort("0"));
				}else{
					studyUnit.setExperiental_duration(Short.parseShort(data.get("EXPERIENTAL_DURAT").toString()));
				}
				if (data.get("COLLEGE_CODE")==null){
					studyUnit.setCollegeCode(Short.parseShort("0"));
				}else{
					studyUnit.setCollegeCode(Short.parseShort(data.get("COLLEGE_CODE").toString()));
				}
				if (data.get("SCHOOL_CODE")==null){
					studyUnit.setSchoolCode(Short.parseShort("0"));
				}else{
					studyUnit.setSchoolCode(Short.parseShort(data.get("SCHOOL_CODE").toString()));
				}	
				if (data.get("TO_YEAR")==null){
					studyUnit.setTo_year(Short.parseShort("0"));
				}else{
					studyUnit.setTo_year(Short.parseShort(data.get("TO_YEAR").toString()));
				}
				if (data.get("FROM_YEAR")==null){
					studyUnit.setFrom_year(Short.parseShort("0"));
				}else{
					studyUnit.setFrom_year(Short.parseShort(data.get("FROM_YEAR").toString()));
				}
			}
		}
		catch (Exception ex) {
			throw new Exception("ModuleDAO: Error reading Study Unit / " + ex,ex);
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
