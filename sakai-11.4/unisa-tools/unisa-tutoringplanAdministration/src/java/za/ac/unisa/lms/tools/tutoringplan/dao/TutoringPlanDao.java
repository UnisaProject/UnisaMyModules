package za.ac.unisa.lms.tools.tutoringplan.dao;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.tutoringplan.forms.CollegeRecord;
import za.ac.unisa.lms.tools.tutoringplan.forms.TutoringMode;
import za.ac.unisa.lms.tools.tutoringplan.forms.TutoringPlanDetails;
import za.ac.unisa.lms.tools.tutoringplan.impl.TutoringPlan;

public class TutoringPlanDao extends StudentSystemDAO {
	
	public TutoringPlan getTutoringPlan(String studyUnit, Short acadYear, Short semester) throws Exception {
		
		TutoringPlan tutoringPlan = new TutoringPlan();
		List<TutoringMode> tutoringModeList = new ArrayList<TutoringMode>(); 
	
		String sql = "select tutor_mode_gc181,optionality,group_choice_gc182,tutor_stu_ratio," +
		" max_groups_per_tut, incl_lect_as_tutor, tut_contact_gc184, alloc_crit_gc183" +
        " from suntutmode" +
        " where mk_academic_year =" + acadYear +
        " and semester=" + semester +
        " and mk_study_unit_code='" + studyUnit + "'";

	
	  	try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				TutoringMode record = new TutoringMode();
				record.setTutorMode(replaceNull(data.get("tutor_mode_gc181")));
				StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
				Gencod gencod = new Gencod();
				gencod = dao.getGenCode("181", record.getTutorMode());
				if (gencod!=null && gencod.getEngDescription()!=null){
					record.setTutorModeDesc(gencod.getEngDescription());
				}				
				record.setOptionality(replaceNull(data.get("optionality")));
				record.setGroupChoice(replaceNull(data.get("group_choice_gc182")));
				record.setTutorStuRatio(data.get("tutor_stu_ratio").toString());
				record.setMaxGroupsPerTutor(data.get("max_groups_per_tut").toString());
				record.setIncludeLectAsTutors(replaceNull(data.get("incl_lect_as_tutor")));
				record.setTutorContact(replaceNull(data.get("tut_contact_gc184")));
				record.setAllocationCriteria(replaceNull(data.get("alloc_crit_gc183")));
				String  hasGroupingStarted=groupingStarted(acadYear, semester, studyUnit,record.getTutorMode());
				record.setGroupingIndicator(hasGroupingStarted);
				tutoringModeList.add(record);
			}
		}
		catch (Exception ex) {
			throw new Exception("TutorStudentGroupingDAO: Error reading GroupsAllocatedToTutor / " + ex);
		}	
	  	tutoringPlan.setListTutoringMode(tutoringModeList);
		return tutoringPlan;
	}
	private String replaceNull(Object object){
		String stringValue="";
		if (object==null){			
		}else{
			stringValue=object.toString();
		}			
		return stringValue;
	}
	public List<TutoringPlanDetails> getTutorPlanDetailList(Short acadYear, Short semester, String searchCritria, Short school, Short department, CollegeRecord college) throws Exception {
		List <TutoringPlanDetails> detailList = new ArrayList<TutoringPlanDetails>();
		
		String sql = "select mk_study_unit_code,tutor_mode_gc181,optionality,group_choice_gc182,tutor_stu_ratio," +
			" max_groups_per_tut, incl_lect_as_tutor, tut_contact_gc184, alloc_crit_gc183, dpt.code as dptCode,dpt.eng_description as dptDesc, " +
			" school.code as schoolCode, school.eng_description as schoolDesc, school.abbreviation as schoolAbbrev" +
			" from suntutmode, sun, dpt, school" +
			" where mk_academic_year =" + acadYear +
			" and semester=" + semester +
			" and suntutmode.mk_study_unit_code = sun.code" +
			" and sun.mk_department_code=dpt.code" +
			" and sun.school_code=school.code" +
			" and sun.college_code=school.college_code";
		
			if (searchCritria.equalsIgnoreCase("C")){
				//search on college
				sql = sql + " and sun.college_code = " + college.getCode(); 
			}
			if (searchCritria.equalsIgnoreCase("S")){
				//search on school
				sql = sql + " and sun.college_code = " + college.getCode() +
				" and sun.school_code=" + school;
			}
			if (searchCritria.equalsIgnoreCase("D")){
				//search on department
				sql = sql + " and sun.mk_department_code = " + department; 
			}		
			sql = sql + " order by school.abbreviation, dpt.eng_description,suntutmode.mk_study_unit_code"; 
		 
			try{
				List queryList = new ArrayList();
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				queryList = jdt.queryForList(sql);
				for (int i=0; i<queryList.size();i++){
						TutoringPlanDetails record = new TutoringPlanDetails();				
						ListOrderedMap data = (ListOrderedMap) queryList.get(i);
						TutoringMode tutorMode = new TutoringMode();
						tutorMode.setTutorMode(replaceNull(data.get("tutor_mode_gc181")));
						StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
						Gencod gencod = new Gencod();
						gencod = dao.getGenCode("181", tutorMode.getTutorMode());
						if (gencod!=null && gencod.getEngDescription()!=null){
							tutorMode.setTutorModeDesc(gencod.getEngDescription());
						}				
						tutorMode.setOptionality(replaceNull(data.get("optionality")));
						tutorMode.setGroupChoice(replaceNull(data.get("group_choice_gc182")));
						tutorMode.setTutorStuRatio(data.get("tutor_stu_ratio").toString());
						tutorMode.setMaxGroupsPerTutor(data.get("max_groups_per_tut").toString());
						tutorMode.setIncludeLectAsTutors(replaceNull(data.get("incl_lect_as_tutor")));
						tutorMode.setTutorContact(replaceNull(data.get("tut_contact_gc184")));
						tutorMode.setAllocationCriteria(replaceNull(data.get("alloc_crit_gc183")));
						record.setTutoringMode(tutorMode);
						record.setStudyUnit(replaceNull(data.get("mk_study_unit_code")));
						record.setDepartmentCode(replaceNull(data.get("dptCode")));
						record.setDepartmentDesc(replaceNull(data.get("dptDesc")));
						record.setSchoolCode(replaceNull(data.get("schoolCode")));
						record.setSchoolAbbrev(replaceNull(data.get("schoolAbbrev")));
						record.setSchoolDesc(replaceNull(data.get("schoolDesc")));
						detailList.add(record);						
					}
			}
			catch (Exception ex) {
				throw new Exception("TutoringPlanDAO: Error reading Tutoring Plan detailed list/ " + ex);
			}	
		return detailList;
	}
	public String  groupingStarted(Short acadYear, Short semester,String studyUnit,String tutorMode) throws Exception {
		 String sql = "select count(*) " +
				 "from tustgr " +
				 " where mk_academic_year=" + acadYear +
				 " and semester=" + semester +
				 " and mk_study_unit_code='" + studyUnit + "'" +
				 " and tutor_mode_gc181='" + tutorMode + "'";
			
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int result = jdt.queryForInt(sql) ;
			if (result == 0) {
			       return "N";
			}
			return "Y";
	 }

	
}
