package za.ac.unisa.lms.tools.tutoringplan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.dao.general.UserDAO;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.tools.tutoringplan.forms.AuditTrail;
import za.ac.unisa.lms.tools.tutoringplan.forms.TutoringPlan;
import za.ac.unisa.lms.tools.tutoringplan.forms.TutoringMode;
import za.ac.unisa.lms.tools.tutoringplan.forms.CollegeRecord;
import za.ac.unisa.lms.tools.tutoringplan.forms.DepartmentRecord;
import za.ac.unisa.lms.tools.tutoringplan.forms.SchoolRecord;
import za.ac.unisa.lms.tools.tutoringplan.forms.TutoringPlanDetail;

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
				tutoringModeList.add(record);
			}
		}
		catch (Exception ex) {
			throw new Exception("TutorStudentGroupingDAO: Error reading GroupsAllocatedToTutor / " + ex);
		}	
		tutoringPlan.setListTutoringMode(tutoringModeList);
		return tutoringPlan;
	}
	
	public List<AuditTrail> getAuditTrail(String studyUnit, Short acadYear, Short semester) throws Exception {
		List<AuditTrail> list = new ArrayList<AuditTrail>();
		
		String sql = "select action_gc53,comments,updated_by,to_char(updated_on,'DD-MON-YYYY HH24:MI') as updatedOn"+
        " from asslog" +
        " where year =" + acadYear +
        " and period=" + semester +
        " and mk_study_unit_code='" + studyUnit + "'" +
        " and type_gc52='TUTORPLAN'" +
        " order by updated_on";

	
	  	try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			Iterator i = queryList.iterator();

			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				AuditTrail record = new AuditTrail();
				record.setAction(replaceNull(data.get("action_gc53")));
				record.setComment(replaceNull(data.get("comments")));
				record.setUpdatedOn(replaceNull(data.get("updatedOn")));
				Person person = new Person();
				UserDAO userdao = new UserDAO();
				person = userdao.getPerson(replaceNull(data.get("updated_by")));	
				record.setUser(person);
				list.add(record);
			}
		}
		catch (Exception ex) {
			throw new Exception("TutoringPlanDAO: Error reading Audit Trail / " + ex);
		}	
		
		return list;
	}
	
    public void addTutoringMode(String studyUnit, Short acadYear, Short semester,TutoringMode tutoringMode, String networkCode)throws Exception {
    	
    	String sql = "insert into suntutmode (mk_academic_year,semester,mk_study_unit_code,tutor_mode_gc181," +
	   	"optionality,group_choice_gc182,tutor_stu_ratio,max_groups_per_tut,incl_lect_as_tutor,tut_contact_gc184,alloc_crit_gc183)" +
	   	" values (?,?,?,?,null,?,?,?,?,?,?)";
    	
    	Connection connection = null;
		
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			connection = jdt.getDataSource().getConnection();
			connection.setAutoCommit(false);
			PreparedStatement ps = connection.prepareStatement(sql);
			   
			ps.setShort(1, acadYear); 
			ps.setShort(2, semester); 
			ps.setString(3, studyUnit);
			ps.setString(4, tutoringMode.getTutorMode());
			ps.setString(5, tutoringMode.getGroupChoice());
			ps.setInt(6, Integer.parseInt(tutoringMode.getTutorStuRatio()));
			ps.setInt(7, Integer.parseInt(tutoringMode.getMaxGroupsPerTutor()));
			ps.setString(8, tutoringMode.getIncludeLectAsTutors());
			ps.setString(9, tutoringMode.getTutorContact());
			ps.setString(10, tutoringMode.getAllocationCriteria());
			ps.executeUpdate(); 
			
		sql = "insert into asslog (year,period,mk_study_unit_code,type_gc52,action_gc53," +
		      "comments,return_email_addr,updated_by,updated_on,request_action_frm)" +
		      " values " +
		      "(?,?,?,?,?,?,?,?,sysTimestamp,?)";
			
			ps = connection.prepareStatement(sql);
			ps.setShort(1, acadYear); 
			ps.setShort(2, semester); 
			ps.setString(3, studyUnit);
			ps.setString(4, "TUTORPLAN");
			ps.setString(5, "UPDATE");
			ps.setString(6, "CREATE TUTORING MODE: " + tutoringMode.getTutorModeDesc() );
			ps.setString(7, null);
			ps.setString(8, networkCode);
			ps.setString(9, null);
			ps.executeUpdate(); 
			
			connection.commit();
			connection.setAutoCommit(true);
			connection.close();
		}
		catch (Exception ex) {
			if (connection!=null){
				connection.rollback();
				connection.setAutoCommit(true);
				connection.close();
				throw new Exception("TutoringPlanDAO: Error inserting Tutoring mode SUNTUTMODE / " + ex);
			}
		}	
    }
    
 public void updateTutoringMode(String studyUnit, Short acadYear, Short semester,TutoringMode tutoringMode,String networkCode)throws Exception {
    	
    	String sql = "update suntutmode" +
	   	" set optionality=null," +
	   	" group_choice_gc182=?," +
	   	" tutor_stu_ratio=?," +
	   	" max_groups_per_tut=?," +
	   	" incl_lect_as_tutor=?," +
	   	" tut_contact_gc184=?," +
	   	" alloc_crit_gc183=?" +
	    " where mk_academic_year =?" +
        " and semester=?" + 
        " and mk_study_unit_code=?" +
        " and tutor_mode_gc181=?";
		
    	Connection connection = null;
		
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			connection = jdt.getDataSource().getConnection();
			connection.setAutoCommit(false);
			PreparedStatement ps = connection.prepareStatement(sql);
			
			ps.setString(1, tutoringMode.getGroupChoice());
			ps.setInt(2, Integer.parseInt(tutoringMode.getTutorStuRatio()));
			ps.setInt(3, Integer.parseInt(tutoringMode.getMaxGroupsPerTutor()));
			ps.setString(4, tutoringMode.getIncludeLectAsTutors());
			ps.setString(5, tutoringMode.getTutorContact());
			ps.setString(6, tutoringMode.getAllocationCriteria());
			ps.setShort(7, acadYear); 
			ps.setShort(8, semester); 
			ps.setString(9, studyUnit);
			ps.setString(10, tutoringMode.getTutorMode());
			ps.executeUpdate(); 
			
			sql = "insert into asslog (year,period,mk_study_unit_code,type_gc52,action_gc53," +
		      "comments,return_email_addr,updated_by,updated_on,request_action_frm)" +
		      " values " +
		      "(?,?,?,?,?,?,?,?,sysTimestamp,?)";
			
			ps = connection.prepareStatement(sql);
			ps.setShort(1, acadYear); 
			ps.setShort(2, semester); 
			ps.setString(3, studyUnit);
			ps.setString(4, "TUTORPLAN");
			ps.setString(5, "UPDATE");
			ps.setString(6, "UPDATE TUTORING MODE: " + tutoringMode.getTutorModeDesc() );
			ps.setString(7, null);
			ps.setString(8, networkCode);
			ps.setString(9, null);
			ps.executeUpdate(); 
			
			connection.commit();
			connection.setAutoCommit(true);
			connection.close();
		}
		catch (Exception ex) {
			if (connection!=null){
				connection.rollback();
				connection.setAutoCommit(true);
				connection.close();
				throw new Exception("TutoringPlanDAO: Error updating Tutoring mode SUNTUTMODE / " + ex);
			}
		}
    }
    
 public boolean removeTutoringMode(String studyUnit, Short acadYear, Short semester,TutoringMode tutoringMode, String networkCode) throws Exception {
		String sql = "select count(*) " +
			 "from tustgr " +
			 " where mk_academic_year=" + acadYear +
			 " and semester=" + semester +
			 " and mk_study_unit_code='" + studyUnit + "'" +
			 " and tutor_mode_gc181='" + tutoringMode.getTutorMode() + "'";
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			sql = "delete from suntutmode" + 
				" where mk_academic_year=?" +
				" and semester=?" + 
				" and mk_study_unit_code=?" +
			    " and tutor_mode_gc181=?";
			
			Connection connection = null;
				
			try {
				jdt = new JdbcTemplate(getDataSource());
				connection = jdt.getDataSource().getConnection();
				connection.setAutoCommit(false);
				PreparedStatement ps = connection.prepareStatement(sql);
					
				ps.setShort(1, acadYear); 
				ps.setShort(2, semester); 
				ps.setString(3, studyUnit);
				ps.setString(4, tutoringMode.getTutorMode());
				ps.executeUpdate(); 
				
				sql = "insert into asslog (year,period,mk_study_unit_code,type_gc52,action_gc53," +
			      "comments,return_email_addr,updated_by,updated_on,request_action_frm)" +
			      " values " +
			      "(?,?,?,?,?,?,?,?,sysTimestamp,?)";
				
				ps = connection.prepareStatement(sql);
				ps.setShort(1, acadYear); 
				ps.setShort(2, semester); 
				ps.setString(3, studyUnit);
				ps.setString(4, "TUTORPLAN");
				ps.setString(5, "UPDATE");
				ps.setString(6, "DELETE TUTORING MODE: " + tutoringMode.getTutorModeDesc() );
				ps.setString(7, null);
				ps.setString(8, networkCode);
				ps.setString(9, null);
				ps.executeUpdate(); 
				
				connection.commit();
				connection.setAutoCommit(true);
				connection.close();
			}
			catch (Exception ex) {
				if (connection!=null){
					connection.rollback();
					connection.setAutoCommit(true);
					connection.close();
					throw new Exception("TutoringPlanDAO: Error deleting TUSTGR / " + ex);
				}
			}	
			return true;
		} else {
			return false;
		}
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
	
	public List<TutoringPlanDetail> getTutorPlanDetailList(Short acadYear, Short semester, String searchCritria, Short school, Short department, CollegeRecord college) throws Exception {
		List <TutoringPlanDetail> detailList = new ArrayList<TutoringPlanDetail>();
		
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
						TutoringPlanDetail record = new TutoringPlanDetail();				
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
			
	
	public boolean isAuthorised(String novellid,String studyUnit,Short academicYear,Short semester) {
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
	
	public boolean tutoringStudentGroupsAssigned(String studyUnit,Short acadYear,Short semester) {
		String sql = "select count(*) " +
		             "from tustgr " +
		             " where mk_academic_year=" + acadYear +
		 			" and semester=" + semester +
		 			" and mk_study_unit_code='" + studyUnit + "'";
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			return false;
		} else {
			return true;
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


}
