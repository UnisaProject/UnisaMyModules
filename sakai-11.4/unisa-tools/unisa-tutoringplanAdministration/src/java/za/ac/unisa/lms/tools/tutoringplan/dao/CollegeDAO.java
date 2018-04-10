package za.ac.unisa.lms.tools.tutoringplan.dao;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.tutoringplan.forms.CollegeRecord;
import za.ac.unisa.lms.tools.tutoringplan.forms.DepartmentRecord;
import za.ac.unisa.lms.tools.tutoringplan.forms.SchoolRecord;

public class CollegeDAO extends StudentSystemDAO {
	public CollegeRecord getCollege(Short code) throws Exception{
		
		CollegeRecord college = new CollegeRecord();
		
		String sql = "select code,eng_description,abbreviation" + 
        " from colleg" +
        " where code =" + code; 
		
		try{ 
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);
		
		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			college.setCode(Short.parseShort((String)data.get("CODE").toString()));
			college.setDescription(data.get("ENG_DESCRIPTION").toString());
			college.setAbbreviation(data.get("ABBREVIATION").toString());
			}
		}
		catch (Exception ex) {
		throw new Exception("FinalMarkConcessionDAO : Error reading College / " + ex,ex);
		}		
		return college;		
		
	}
	
public List<SchoolRecord> getSchoolList(Short college) throws Exception {
		
		List<SchoolRecord> schoolList = new ArrayList<SchoolRecord>();		
		SchoolRecord school = new SchoolRecord();	
		
		String sql = "select code,college_code,abbreviation,eng_description from school" + 
		" where school.in_use_flag <> 'N'" +
		" and school.college_code=" + college +
		" order by eng_description";
		
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
			throw new Exception("TutoringPlanDAO: Error school list - table school/ " + ex);
		}
		
		return schoolList;	
		
	}
	
	public List<DepartmentRecord> getDepartmentList(Short college) throws Exception {
		
		List<DepartmentRecord> dptList = new ArrayList<DepartmentRecord>();
		List queryList = new ArrayList();
		
		DepartmentRecord dpt = new DepartmentRecord();				
		
		try{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			queryList = jdt.queryForList("select code,eng_description,college_code,school_code from dpt where college_code =" + college + " and in_use_flag='Y' order by eng_description");
			for (int i=0; i<queryList.size();i++){
					dpt = new DepartmentRecord();				
					ListOrderedMap data = (ListOrderedMap) queryList.get(i);
					dpt.setCode(Short.parseShort((String)data.get("CODE").toString()));
					dpt.setDescription((String) data.get("ENG_DESCRIPTION").toString());
					dpt.setCollegeCode(Short.parseShort((String)data.get("COLLEGE_CODE").toString()));
					dpt.setSchoolCode(Short.parseShort((String)data.get("SCHOOL_CODE").toString()));
					dptList.add(dpt);						
				}
		}
		catch (Exception ex) {
			throw new Exception("TutoringPlanDAO: Error reading dpt/ " + ex);
		}	
		return dptList;
	}
   public DepartmentRecord getDepartment(Short code) throws Exception{
		
		DepartmentRecord dpt = new DepartmentRecord();
		
		String sql = "select code,eng_description,college_code" + 
        " from dpt" +
        " where code =" + code +
        " and in_use_flag<>'N'"; 
						
		try{ 
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);
		
		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			dpt.setCode(Short.parseShort((String)data.get("CODE").toString()));
			dpt.setDescription(data.get("ENG_DESCRIPTION").toString());
			dpt.setCollegeCode(Short.parseShort((String)data.get("COLLEGE_CODE").toString()));
			}
		}
		catch (Exception ex) {
		throw new Exception("TutoringPlanDAO: Error reading DPT / " + ex,ex);
		}		
		return dpt;		
		
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
			throw new Exception("TutoringPlanDAO: Error reading Study unit period detail / " + ex);
		}				
	}	
}
