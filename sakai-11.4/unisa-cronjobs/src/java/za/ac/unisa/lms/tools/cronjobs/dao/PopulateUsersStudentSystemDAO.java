package za.ac.unisa.lms.tools.cronjobs.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.sakaiproject.component.cover.ServerConfigurationService;

import za.ac.unisa.lms.db.StudentSystemDAO;


public class PopulateUsersStudentSystemDAO extends StudentSystemDAO{

	private Log log;

	public PopulateUsersStudentSystemDAO() {
		log = LogFactory.getLog(this.getClass());
	}
	
	public List<?> getAllStudents() {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());

		String selectStudents = "select distinct(s.nr) studentNr, s.first_names name,s.surname surname, i.email_address email,i.password password      "
				+ "     from stu s,Idvalt i ,stusun ss where s.nr = i.mk_student_nr and exists(select mk_study_unit_code from stusun where mk_study_unit_code = 'ABT1520'  "
				+ "and fk_academic_year=2017 and  i.mk_student_nr = FK_STUDENT_NR and status_code='RG' ) ";

		List<?> studentRecords = jdt.queryForList(selectStudents);
		log.debug("PopulateUsersStudentSystemDAO: getAllStudents select all students");
		return studentRecords;
	}
	
	public List<?> getStudentPersDetails(String studentNr) {

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List<?> studentPersDetails = null;
		String studentPersInfo = null;
		try {
			studentPersInfo = "select distinct(s.nr) studentNr, nvl(s.first_names,' ') name, nvl(s.surname,' ') surname     "
					+ " from stu s where s.nr= "+studentNr;

			studentPersDetails = jdt.queryForList(studentPersInfo);
			log.debug(this+" PopulateUsersStudentSystemDAO: getStudentPersDetails ");
		} catch (Exception e) {
			log.error(this+ " :getStudentPersDetails Failed to get student details from STU for student "+ studentNr);
			e.printStackTrace();
		} finally {
			studentPersInfo = null;
		}
		return studentPersDetails;
	}

	
	public List<?> getStudentPersonnelDetails(String studentNr) {

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List<?> studentPersDetails = null;
		String studentPersInfo = null;
		try {
			studentPersInfo = "select distinct(s.nr) studentNr, nvl(s.first_names,' ') name, nvl(s.surname,' ') surname, nvl(i.email_address,' ') email, nvl(i.password,' ') password      "
					+ " from stu s,Idvalt i where s.nr = i.mk_student_nr and s.nr= "
					+ studentNr;

			studentPersDetails = jdt.queryForList(studentPersInfo);
			log.debug(" PopulateUsersStudentSystemDAO: getStudentPersonnelDetails ");
		} catch (Exception e) {
			log.debug(this
					+ " :getStudentPersonnelDetails get personnel details for student "
					+ studentNr);
			e.printStackTrace();
		} finally {
			studentPersInfo = null;
		}
		return studentPersDetails;
	}

	public List<?> getStaffPersonnelDetails(String userId) {

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List<?> staffPersDetails = null;
		String staffPersInfoFromStaff = null;
		try {
			staffPersInfoFromStaff = "select nvl(name, ' ') name, nvl(surname,' ') surname, nvl(email_address,' ') email    "
					+ " from staff where NOVELL_USER_ID = '"
					+ userId.toUpperCase() + "'";

			staffPersDetails = jdt.queryForList(staffPersInfoFromStaff);
			log.debug(" PopulateUsersStudentSystemDAO: getStaffPersonnelDetails ");

			if (staffPersDetails.size() == 0) {
				String staffPersInfoFromUsr = "select nvl(name, ' ') name, nvl(E_MAIL,' ') email    "
						+ " from usr where NOVELL_USER_CODE = '"
						+ userId.toUpperCase() + "'";

				staffPersDetails = jdt.queryForList(staffPersInfoFromUsr);
			}
		} catch (Exception e) {
			log.debug(this
					+ " :getStaffPersonnelDetails get personnel details for staff "
					+ userId);
			e.printStackTrace();
		} finally {
			staffPersInfoFromStaff = null;
		}
		return staffPersDetails;
	}

	
	
	public List<?> getOldStudentsList() {

		
		String oldStudentsList = null;
		List<?> studentsList = null;
		String sakaidbname= ServerConfigurationService.getString("sakaidb");

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			oldStudentsList = "select distinct(acasun.FK_STUDENT_NR) student_nr, STU.FIRST_NAMES name ,STU.SURNAME surname, nvl(IDVALT.EXCHANGEMAIL,' ') email, nvl(IDVALT.PASSWORD,' ') password "
					+ " from acasun , Idvalt , stu  where acasun.FK_STUDENT_NR=STU.NR and acasun.FK_STUDENT_NR=IDVALT.MK_STUDENT_NR "
					+ " and IDVALT.MK_STUDENT_NR=STU.NR and  MK_ACADEMIC_YEAR > 2011  and exists(select * from join_activation"+sakaidbname+" where user_id=acasun.FK_STUDENT_NR)  ";// check join activation table records from 2013

			studentsList = jdt.queryForList(oldStudentsList);
			} catch (Exception e) {
			log.error(this
					+ " ERROR On :getOldStudentsList get all the old students list from ACASUN ");
			e.printStackTrace();
		} finally {
			oldStudentsList = null;
		}
		return studentsList;
	}
	
public List<?> getCurrentStudentsList() {

		
		String currentStudentsList = null;
		List<?> studentsList = null;
	
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			currentStudentsList = " select distinct(stusun.fk_student_nr) student_nr , STU.FIRST_NAMES name ,STU.SURNAME surname, nvl(IDVALT.EXCHANGEMAIL,' ') email, nvl(IDVALT.PASSWORD,' ') password  "
							+ " from stusun, stu,idvalt where stusun.fk_student_nr = stu.nr and stusun.fk_student_nr = idvalt.mk_student_nr and stu.nr=idvalt.MK_STUDENT_NR "
							+ " and stusun.status_code in ( 'RG','FC','DC' )  and stusun.FK_ACADEMIC_YEAR>2011 order by student_nr";
			
			studentsList = jdt.queryForList(currentStudentsList);
			} catch (Exception e) {
			log.error(this
					+ " ERROR On :getCurrentStudentsList get all the current students list from STUSUN ");
			e.printStackTrace();
		} finally {
			currentStudentsList = null;
		}
		return studentsList;
	}
	
	public List<?> getStudentCourses() {

		
		List<?> studentRecords = null;
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());

		/*	String selectCourseList = "select stusun_temp.FK_STUDENT_NR studentNr,stusun_temp.mk_study_unit_code as studyUnit,stusun_temp.semester_period as semester,stusun_temp.fk_academic_year as academicYear, "
					+ " tustgr_temp.group_nr || substr(tustgr_temp.tutor_mode_gc181,1,1)  as tutorMode,grd.college_code as collegeCode,grd.under_post_categor as studyCategory , "
					+ " to_char(tustgr_temp.inactive_date,'yyyy-mm-dd hh24:mi:ss') as inactiveDate "
					+ " from stusun_temp,stuann,grd,tustgr_temp "
					+ " where  stusun_temp.fk_academic_year=stuann.mk_academic_year "
					+ " and stusun_temp.fk_academic_period=1 "
					+ " and stusun_temp.fk_student_nr=stuann.mk_student_nr "
					+ " and stusun_temp.status_code in ( 'RG','FC','DC' ) "
					+ " and grd.code = stuann.MK_HIGHEST_QUALIFI "
					+ " and tustgr_temp.mk_academic_year(+) = stusun_temp.fk_academic_year "
					+ " and tustgr_temp.semester(+) = stusun_temp.semester_period "
					+ " and tustgr_temp.mk_study_unit_code(+) = stusun_temp.mk_study_unit_code "
					+ " and tustgr_temp.mk_student_nr(+) = stusun_temp.fk_student_nr "
					+ " and stusun_temp.FK_ACADEMIC_YEAR > 2011   and stusun_temp.mk_study_unit_code NOT LIKE 'A%'  and stusun_temp.mk_study_unit_code NOT LIKE 'I%'  "
					+ "and stusun_temp.mk_study_unit_code NOT LIKE 'C%'  and stusun_temp.mk_study_unit_code NOT LIKE 'F%'  and stusun_temp.mk_study_unit_code NOT LIKE 'E%'  "
					+ "and stusun_temp.mk_study_unit_code NOT LIKE 'N%'   and  stusun_temp.mk_study_unit_code NOT LIKE 'S%' and stusun_temp.mk_study_unit_code NOT LIKE 'T%'  "
					+ "and stusun_temp.mk_study_unit_code NOT LIKE 'P%'   and  stusun_temp.mk_study_unit_code NOT LIKE 'H%' and  stusun_temp.mk_study_unit_code NOT LIKE 'M%'  order by studyUnit,semester,academicYear";*/
			
			/*String selectCourseList = "select stusun_temp.FK_STUDENT_NR studentNr,stusun_temp.mk_study_unit_code as studyUnit,stusun_temp.semester_period as semester,stusun_temp.fk_academic_year as academicYear, "
					+ " tustgr_temp.group_nr || substr(tustgr_temp.tutor_mode_gc181,1,1)  as tutorMode,grd.college_code as collegeCode,grd.under_post_categor as studyCategory , "
					+ " to_char(tustgr_temp.inactive_date,'yyyy-mm-dd hh24:mi:ss') as inactiveDate "
					+ " from stusun_temp,stuann,grd,tustgr_temp "
					+ " where  stusun_temp.fk_academic_year=stuann.mk_academic_year "
					+ " and stusun_temp.fk_academic_period=1 "
					+ " and stusun_temp.fk_student_nr=stuann.mk_student_nr "
					+ " and stusun_temp.status_code in ( 'RG','FC','DC' ) "
					+ " and grd.code = stuann.MK_HIGHEST_QUALIFI "
					+ " and tustgr_temp.mk_academic_year(+) = stusun_temp.fk_academic_year "
					+ " and tustgr_temp.semester(+) = stusun_temp.semester_period "
					+ " and tustgr_temp.mk_study_unit_code(+) = stusun_temp.mk_study_unit_code "
					+ " and tustgr_temp.mk_student_nr(+) = stusun_temp.fk_student_nr "
					+ " and stusun_temp.FK_ACADEMIC_YEAR > 2011  and  (stusun_temp.mk_study_unit_code  LIKE 'P%') and SEMESTER_PERIOD = 2  order by studyUnit,semester,academicYear";*/
			
			String selectCourseList = "select stusun_temp.FK_STUDENT_NR studentNr,stusun_temp.mk_study_unit_code as studyUnit,stusun_temp.semester_period as semester,stusun_temp.fk_academic_year as academicYear, "
					+ " tustgr_temp.group_nr || substr(tustgr_temp.tutor_mode_gc181,1,1)  as tutorMode,grd.college_code as collegeCode,grd.under_post_categor as studyCategory , "
					+ " to_char(tustgr_temp.inactive_date,'yyyy-mm-dd hh24:mi:ss') as inactiveDate "
					+ " from stusun_temp,stuann,grd,tustgr_temp "
					+ " where  stusun_temp.fk_academic_year=stuann.mk_academic_year "
					+ " and stusun_temp.fk_academic_period=1 "
					+ " and stusun_temp.fk_student_nr=stuann.mk_student_nr "
					+ " and stusun_temp.status_code in ( 'RG','FC','DC' ) "
					+ " and grd.code = stuann.MK_HIGHEST_QUALIFI "
					+ " and tustgr_temp.mk_academic_year(+) = stusun_temp.fk_academic_year "
					+ " and tustgr_temp.semester(+) = stusun_temp.semester_period "
					+ " and tustgr_temp.mk_study_unit_code(+) = stusun_temp.mk_study_unit_code "
					+ " and tustgr_temp.mk_student_nr(+) = stusun_temp.fk_student_nr "
					+ " and stusun_temp.FK_ACADEMIC_YEAR > 2011  and  (stusun_temp.mk_study_unit_code  LIKE 'PY%' ) order by studyUnit,semester,academicYear";
							
			
			studentRecords = jdt.queryForList(selectCourseList);
			
		} catch (Exception e) {
			log.error(this
					+ " ERROR On :getStudentCourses get all the active students list from temp tables ");
		}
		return studentRecords;

	}

	public List<?> getStudentCoursesStartsWithAI() {

		
		List<?> studentRecords = null;
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());

			/*String selectCourseList = "select stusun_temp.FK_STUDENT_NR studentNr,stusun_temp.mk_study_unit_code as studyUnit,stusun_temp.semester_period as semester,stusun_temp.fk_academic_year as academicYear, "
					+ " tustgr_temp.group_nr || substr(tustgr_temp.tutor_mode_gc181,1,1)  as tutorMode,grd.college_code as collegeCode,grd.under_post_categor as studyCategory , "
					+ " to_char(tustgr_temp.inactive_date,'yyyy-mm-dd hh24:mi:ss') as inactiveDate "
					+ " from stusun_temp,stuann,grd,tustgr_temp "
					+ " where  stusun_temp.fk_academic_year=stuann.mk_academic_year "
					+ " and stusun_temp.fk_academic_period=1 "
					+ " and stusun_temp.fk_student_nr=stuann.mk_student_nr "
					+ " and stusun_temp.status_code in ( 'RG','FC','DC' ) "
					+ " and grd.code = stuann.MK_HIGHEST_QUALIFI "
					+ " and tustgr_temp.mk_academic_year(+) = stusun_temp.fk_academic_year "
					+ " and tustgr_temp.semester(+) = stusun_temp.semester_period "
					+ " and tustgr_temp.mk_study_unit_code(+) = stusun_temp.mk_study_unit_code "
					+ " and tustgr_temp.mk_student_nr(+) = stusun_temp.fk_student_nr "
					+ " and stusun_temp.FK_ACADEMIC_YEAR > 2011  and  (stusun_temp.mk_study_unit_code  LIKE 'A%' or stusun_temp.mk_study_unit_code  LIKE 'I%')  order by studyUnit,semester,academicYear";*/
			
		/*	String selectCourseList = "select stusun_temp.FK_STUDENT_NR studentNr,stusun_temp.mk_study_unit_code as studyUnit,stusun_temp.semester_period as semester,stusun_temp.fk_academic_year as academicYear, "
					+ " tustgr_temp.group_nr || substr(tustgr_temp.tutor_mode_gc181,1,1)  as tutorMode,grd.college_code as collegeCode,grd.under_post_categor as studyCategory , "
					+ " to_char(tustgr_temp.inactive_date,'yyyy-mm-dd hh24:mi:ss') as inactiveDate "
					+ " from stusun_temp,stuann,grd,tustgr_temp "
					+ " where  stusun_temp.fk_academic_year=stuann.mk_academic_year "
					+ " and stusun_temp.fk_academic_period=1 "
					+ " and stusun_temp.fk_student_nr=stuann.mk_student_nr "
					+ " and stusun_temp.status_code in ( 'RG','FC','DC' ) "
					+ " and grd.code = stuann.MK_HIGHEST_QUALIFI "
					+ " and tustgr_temp.mk_academic_year(+) = stusun_temp.fk_academic_year "
					+ " and tustgr_temp.semester(+) = stusun_temp.semester_period "
					+ " and tustgr_temp.mk_study_unit_code(+) = stusun_temp.mk_study_unit_code "
					+ " and tustgr_temp.mk_student_nr(+) = stusun_temp.fk_student_nr "
					+ " and stusun_temp.FK_ACADEMIC_YEAR > 2011  and  (stusun_temp.mk_study_unit_code  LIKE 'H%' or stusun_temp.mk_study_unit_code  LIKE 'M%') and SEMESTER_PERIOD = 2  order by studyUnit,semester,academicYear";*/
			/*String selectCourseList = "select stusun_temp.FK_STUDENT_NR studentNr,stusun_temp.mk_study_unit_code as studyUnit,stusun_temp.semester_period as semester,stusun_temp.fk_academic_year as academicYear, "
					+ " tustgr_temp.group_nr || substr(tustgr_temp.tutor_mode_gc181,1,1)  as tutorMode,grd.college_code as collegeCode,grd.under_post_categor as studyCategory , "
					+ " to_char(tustgr_temp.inactive_date,'yyyy-mm-dd hh24:mi:ss') as inactiveDate "
					+ " from stusun_temp,stuann,grd,tustgr_temp "
					+ " where  stusun_temp.fk_academic_year=stuann.mk_academic_year "
					+ " and stusun_temp.fk_academic_period=1 "
					+ " and stusun_temp.fk_student_nr=stuann.mk_student_nr "
					+ " and stusun_temp.status_code in ( 'RG','FC','DC' ) "
					+ " and grd.code = stuann.MK_HIGHEST_QUALIFI "
					+ " and tustgr_temp.mk_academic_year(+) = stusun_temp.fk_academic_year "
					+ " and tustgr_temp.semester(+) = stusun_temp.semester_period "
					+ " and tustgr_temp.mk_study_unit_code(+) = stusun_temp.mk_study_unit_code "
					+ " and tustgr_temp.mk_student_nr(+) = stusun_temp.fk_student_nr "
					+ " and stusun_temp.FK_ACADEMIC_YEAR > 2011  and  (stusun_temp.mk_study_unit_code  LIKE 'P%')  and SEMESTER_PERIOD <> 2   and STUSUN_TEMP.FK_STUDENT_NR not like '5%'  order by studyUnit,semester,academicYear";
							*/
			
			String selectCourseList = "select stusun_temp.FK_STUDENT_NR studentNr,stusun_temp.mk_study_unit_code as studyUnit,stusun_temp.semester_period as semester,stusun_temp.fk_academic_year as academicYear, "
					+ " tustgr_temp.group_nr || substr(tustgr_temp.tutor_mode_gc181,1,1)  as tutorMode,grd.college_code as collegeCode,grd.under_post_categor as studyCategory , "
					+ " to_char(tustgr_temp.inactive_date,'yyyy-mm-dd hh24:mi:ss') as inactiveDate "
					+ " from stusun_temp,stuann,grd,tustgr_temp "
					+ " where  stusun_temp.fk_academic_year=stuann.mk_academic_year "
					+ " and stusun_temp.fk_academic_period=1 "
					+ " and stusun_temp.fk_student_nr=stuann.mk_student_nr "
					+ " and stusun_temp.status_code in ( 'RG','FC','DC' ) "
					+ " and grd.code = stuann.MK_HIGHEST_QUALIFI "
					+ " and tustgr_temp.mk_academic_year(+) = stusun_temp.fk_academic_year "
					+ " and tustgr_temp.semester(+) = stusun_temp.semester_period "
					+ " and tustgr_temp.mk_study_unit_code(+) = stusun_temp.mk_study_unit_code "
					+ " and tustgr_temp.mk_student_nr(+) = stusun_temp.fk_student_nr "
					+ " and stusun_temp.FK_ACADEMIC_YEAR > 2011  and  (stusun_temp.mk_study_unit_code  LIKE 'P%' )  and  (stusun_temp.mk_study_unit_code not LIKE 'PY%' )  order by studyUnit,semester,academicYear";
			
			studentRecords = jdt.queryForList(selectCourseList);
			
		} catch (Exception e) {
			log.error(this
					+ " ERROR On :getStudentCourses get all the active students list from temp tables ");
		}
		return studentRecords;

	}
public List<?> getStudentCoursesStartsWithCF() {

		
		List<?> studentRecords = null;
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());

			/*String selectCourseList = "select stusun_temp.FK_STUDENT_NR studentNr,stusun_temp.mk_study_unit_code as studyUnit,stusun_temp.semester_period as semester,stusun_temp.fk_academic_year as academicYear, "
					+ " tustgr_temp.group_nr || substr(tustgr_temp.tutor_mode_gc181,1,1)  as tutorMode,grd.college_code as collegeCode,grd.under_post_categor as studyCategory , "
					+ " to_char(tustgr_temp.inactive_date,'yyyy-mm-dd hh24:mi:ss') as inactiveDate "
					+ " from stusun_temp,stuann,grd,tustgr_temp "
					+ " where  stusun_temp.fk_academic_year=stuann.mk_academic_year "
					+ " and stusun_temp.fk_academic_period=1 "
					+ " and stusun_temp.fk_student_nr=stuann.mk_student_nr "
					+ " and stusun_temp.status_code in ( 'RG','FC','DC' ) "
					+ " and grd.code = stuann.MK_HIGHEST_QUALIFI "
					+ " and tustgr_temp.mk_academic_year(+) = stusun_temp.fk_academic_year "
					+ " and tustgr_temp.semester(+) = stusun_temp.semester_period "
					+ " and tustgr_temp.mk_study_unit_code(+) = stusun_temp.mk_study_unit_code "
					+ " and tustgr_temp.mk_student_nr(+) = stusun_temp.fk_student_nr "
					+ " and stusun_temp.FK_ACADEMIC_YEAR > 2011  and  (stusun_temp.mk_study_unit_code  LIKE 'C%' or stusun_temp.mk_study_unit_code  LIKE 'F%')  order by studyUnit,semester,academicYear";*/
			
			String selectCourseList = "select stusun_temp.FK_STUDENT_NR studentNr,stusun_temp.mk_study_unit_code as studyUnit,stusun_temp.semester_period as semester,stusun_temp.fk_academic_year as academicYear, "
					+ " tustgr_temp.group_nr || substr(tustgr_temp.tutor_mode_gc181,1,1)  as tutorMode,grd.college_code as collegeCode,grd.under_post_categor as studyCategory , "
					+ " to_char(tustgr_temp.inactive_date,'yyyy-mm-dd hh24:mi:ss') as inactiveDate "
					+ " from stusun_temp,stuann,grd,tustgr_temp "
					+ " where  stusun_temp.fk_academic_year=stuann.mk_academic_year "
					+ " and stusun_temp.fk_academic_period=1 "
					+ " and stusun_temp.fk_student_nr=stuann.mk_student_nr "
					+ " and stusun_temp.status_code in ( 'RG','FC','DC' ) "
					+ " and grd.code = stuann.MK_HIGHEST_QUALIFI "
					+ " and tustgr_temp.mk_academic_year(+) = stusun_temp.fk_academic_year "
					+ " and tustgr_temp.semester(+) = stusun_temp.semester_period "
					+ " and tustgr_temp.mk_study_unit_code(+) = stusun_temp.mk_study_unit_code "
					+ " and tustgr_temp.mk_student_nr(+) = stusun_temp.fk_student_nr "
					+ " and stusun_temp.FK_ACADEMIC_YEAR > 2011  and  (stusun_temp.mk_study_unit_code  LIKE 'S%' or stusun_temp.mk_study_unit_code  LIKE 'T%')  and SEMESTER_PERIOD = 2   order by studyUnit,semester,academicYear";
							
			studentRecords = jdt.queryForList(selectCourseList);
			
		} catch (Exception e) {
			log.error(this
					+ " ERROR On :getStudentCourses get all the active students list from temp tables ");
		}
		return studentRecords;

	}

public List<?> getStudentCoursesStartsWithEN() {

	
	List<?> studentRecords = null;
	try {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());

		String selectCourseList = "select stusun_temp.FK_STUDENT_NR studentNr,stusun_temp.mk_study_unit_code as studyUnit,stusun_temp.semester_period as semester,stusun_temp.fk_academic_year as academicYear, "
				+ " tustgr_temp.group_nr || substr(tustgr_temp.tutor_mode_gc181,1,1)  as tutorMode,grd.college_code as collegeCode,grd.under_post_categor as studyCategory , "
				+ " to_char(tustgr_temp.inactive_date,'yyyy-mm-dd hh24:mi:ss') as inactiveDate "
				+ " from stusun_temp,stuann,grd,tustgr_temp "
				+ " where  stusun_temp.fk_academic_year=stuann.mk_academic_year "
				+ " and stusun_temp.fk_academic_period=1 "
				+ " and stusun_temp.fk_student_nr=stuann.mk_student_nr "
				+ " and stusun_temp.status_code in ( 'RG','FC','DC' ) "
				+ " and grd.code = stuann.MK_HIGHEST_QUALIFI "
				+ " and tustgr_temp.mk_academic_year(+) = stusun_temp.fk_academic_year "
				+ " and tustgr_temp.semester(+) = stusun_temp.semester_period "
				+ " and tustgr_temp.mk_study_unit_code(+) = stusun_temp.mk_study_unit_code "
				+ " and tustgr_temp.mk_student_nr(+) = stusun_temp.fk_student_nr "
				+ " and stusun_temp.FK_ACADEMIC_YEAR > 2011  and  (stusun_temp.mk_study_unit_code  LIKE 'E%' or stusun_temp.mk_study_unit_code  LIKE 'N%')  order by studyUnit,semester,academicYear";
						
		studentRecords = jdt.queryForList(selectCourseList);
		
	} catch (Exception e) {
		log.error(this
				+ " ERROR On :getStudentCourses get all the active students list from temp tables ");
	}
	return studentRecords;

}
public List<?> getStudentCoursesStartsWithST() {

	
	List<?> studentRecords = null;
	try {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());

		String selectCourseList = "select stusun_temp.FK_STUDENT_NR studentNr,stusun_temp.mk_study_unit_code as studyUnit,stusun_temp.semester_period as semester,stusun_temp.fk_academic_year as academicYear, "
				+ " tustgr_temp.group_nr || substr(tustgr_temp.tutor_mode_gc181,1,1)  as tutorMode,grd.college_code as collegeCode,grd.under_post_categor as studyCategory , "
				+ " to_char(tustgr_temp.inactive_date,'yyyy-mm-dd hh24:mi:ss') as inactiveDate "
				+ " from stusun_temp,stuann,grd,tustgr_temp "
				+ " where  stusun_temp.fk_academic_year=stuann.mk_academic_year "
				+ " and stusun_temp.fk_academic_period=1 "
				+ " and stusun_temp.fk_student_nr=stuann.mk_student_nr "
				+ " and stusun_temp.status_code in ( 'RG','FC','DC' ) "
				+ " and grd.code = stuann.MK_HIGHEST_QUALIFI "
				+ " and tustgr_temp.mk_academic_year(+) = stusun_temp.fk_academic_year "
				+ " and tustgr_temp.semester(+) = stusun_temp.semester_period "
				+ " and tustgr_temp.mk_study_unit_code(+) = stusun_temp.mk_study_unit_code "
				+ " and tustgr_temp.mk_student_nr(+) = stusun_temp.fk_student_nr "
				+ " and stusun_temp.FK_ACADEMIC_YEAR > 2011  and  (stusun_temp.mk_study_unit_code  LIKE 'S%' or stusun_temp.mk_study_unit_code  LIKE 'T%')  and SEMESTER_PERIOD <> 2   order by studyUnit,semester,academicYear";
						
		studentRecords = jdt.queryForList(selectCourseList);
		
	} catch (Exception e) {
		log.error(this
				+ " ERROR On :getStudentCourses get all the active students list from temp tables ");
	}
	return studentRecords;

}

public List<?> getStudentCoursesStartsWithP() {

	
	List<?> studentRecords = null;
	try {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());

		String selectCourseList = "select stusun_temp.FK_STUDENT_NR studentNr,stusun_temp.mk_study_unit_code as studyUnit,stusun_temp.semester_period as semester,stusun_temp.fk_academic_year as academicYear, "
				+ " tustgr_temp.group_nr || substr(tustgr_temp.tutor_mode_gc181,1,1)  as tutorMode,grd.college_code as collegeCode,grd.under_post_categor as studyCategory , "
				+ " to_char(tustgr_temp.inactive_date,'yyyy-mm-dd hh24:mi:ss') as inactiveDate "
				+ " from stusun_temp,stuann,grd,tustgr_temp "
				+ " where  stusun_temp.fk_academic_year=stuann.mk_academic_year "
				+ " and stusun_temp.fk_academic_period=1 "
				+ " and stusun_temp.fk_student_nr=stuann.mk_student_nr "
				+ " and stusun_temp.status_code in ( 'RG','FC','DC' ) "
				+ " and grd.code = stuann.MK_HIGHEST_QUALIFI "
				+ " and tustgr_temp.mk_academic_year(+) = stusun_temp.fk_academic_year "
				+ " and tustgr_temp.semester(+) = stusun_temp.semester_period "
				+ " and tustgr_temp.mk_study_unit_code(+) = stusun_temp.mk_study_unit_code "
				+ " and tustgr_temp.mk_student_nr(+) = stusun_temp.fk_student_nr "
				+ " and stusun_temp.FK_ACADEMIC_YEAR > 2011  and  (stusun_temp.mk_study_unit_code  LIKE 'P%')  and SEMESTER_PERIOD <> 2   and STUSUN_TEMP.FK_STUDENT_NR  like '5%'  order by studyUnit,semester,academicYear";
						
		studentRecords = jdt.queryForList(selectCourseList);
		
	} catch (Exception e) {
		log.error(this
				+ " ERROR On :getStudentCourses get all the active students list from temp tables ");
	}
	return studentRecords;

}

public List<?> getStudentCoursesStartsWithHM() {

	
	List<?> studentRecords = null;
	try {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());

		String selectCourseList = "select stusun_temp.FK_STUDENT_NR studentNr,stusun_temp.mk_study_unit_code as studyUnit,stusun_temp.semester_period as semester,stusun_temp.fk_academic_year as academicYear, "
				+ " tustgr_temp.group_nr || substr(tustgr_temp.tutor_mode_gc181,1,1)  as tutorMode,grd.college_code as collegeCode,grd.under_post_categor as studyCategory , "
				+ " to_char(tustgr_temp.inactive_date,'yyyy-mm-dd hh24:mi:ss') as inactiveDate "
				+ " from stusun_temp,stuann,grd,tustgr_temp "
				+ " where  stusun_temp.fk_academic_year=stuann.mk_academic_year "
				+ " and stusun_temp.fk_academic_period=1 "
				+ " and stusun_temp.fk_student_nr=stuann.mk_student_nr "
				+ " and stusun_temp.status_code in ( 'RG','FC','DC' ) "
				+ " and grd.code = stuann.MK_HIGHEST_QUALIFI "
				+ " and tustgr_temp.mk_academic_year(+) = stusun_temp.fk_academic_year "
				+ " and tustgr_temp.semester(+) = stusun_temp.semester_period "
				+ " and tustgr_temp.mk_study_unit_code(+) = stusun_temp.mk_study_unit_code "
				+ " and tustgr_temp.mk_student_nr(+) = stusun_temp.fk_student_nr "
				+ " and stusun_temp.FK_ACADEMIC_YEAR > 2011  and  (stusun_temp.mk_study_unit_code  LIKE 'H%' or stusun_temp.mk_study_unit_code  LIKE 'M%')  order by studyUnit,semester,academicYear";
						
		studentRecords = jdt.queryForList(selectCourseList);
		
	} catch (Exception e) {
		log.error(this
				+ " ERROR On :getStudentCourses get all the active students list from temp tables ");
	}
	return studentRecords;

}
 public List<?> getStudentGroupCourses() {

		
		List<?> studentRecords = null;
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());

			String selectCourseList = "select stusun.FK_STUDENT_NR studentNr,stusun.mk_study_unit_code as studyUnit,stusun.semester_period as semester,stusun.fk_academic_year as academicYear, "
					+ " tustgr_temp.group_nr || substr(tustgr_temp.tutor_mode_gc181,1,1)  as tutorMode,grd.college_code as collegeCode,grd.under_post_categor as studyCategory , "
					+ " to_char(tustgr_temp.inactive_date,'yyyy-mm-dd hh24:mi:ss') as inactiveDate "
					+ " from stusun,stuann,grd,tustgr_temp "
					+ " where  stusun.fk_academic_year=stuann.mk_academic_year "
					+ " and stusun.fk_academic_period=1 "
					+ " and stusun.fk_student_nr=stuann.mk_student_nr "
					+ " and stusun.status_code in ( 'RG','FC','DC' ) "
					+ " and grd.code = stuann.MK_HIGHEST_QUALIFI "
					+ " and tustgr_temp.mk_academic_year(+) = stusun.fk_academic_year "
					+ " and tustgr_temp.semester(+) = stusun.semester_period "
					+ " and tustgr_temp.mk_study_unit_code(+) = stusun.mk_study_unit_code "
					+ " and tustgr_temp.mk_student_nr(+) = stusun.fk_student_nr   and tustgr_temp.GROUP_NR is not null  "
					+ " and stusun.FK_ACADEMIC_YEAR > 2011 "
					+ " and tustgr_temp.mk_study_unit_code  NOT  LIKE 'S%'  "
					+ " and tustgr_temp.mk_study_unit_code  NOT  LIKE 'A%' "
					+ "  and tustgr_temp.mk_study_unit_code   NOT LIKE 'F%' "
					+ " and tustgr_temp.mk_study_unit_code   NOT LIKE 'E%' "
					+ " and tustgr_temp.mk_study_unit_code   NOT LIKE 'C%'"
					+ " and tustgr_temp.mk_study_unit_code   NOT LIKE 'B%'"
					+ " and tustgr_temp.mk_study_unit_code   NOT LIKE 'P%'"
					+ " and tustgr_temp.mk_study_unit_code   NOT LIKE 'I%'"
					+ " order by studyUnit,semester,academicYear";
							
			studentRecords = jdt.queryForList(selectCourseList);
			
		} catch (Exception e) {
			log.error(this
					+ " ERROR On :getStudentCourses get all the active students list from temp tables ");
		}
		return studentRecords;

	}
 public List<?> getStudentGroupCoursesStartsWithASF() {

		
		List<?> studentRecords = null;
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());

			String selectCourseList = "select stusun.FK_STUDENT_NR studentNr,stusun.mk_study_unit_code as studyUnit,stusun.semester_period as semester,stusun.fk_academic_year as academicYear, "
					+ " tustgr_temp.group_nr || substr(tustgr_temp.tutor_mode_gc181,1,1)  as tutorMode,grd.college_code as collegeCode,grd.under_post_categor as studyCategory , "
					+ " to_char(tustgr_temp.inactive_date,'yyyy-mm-dd hh24:mi:ss') as inactiveDate "
					+ " from stusun,stuann,grd,tustgr_temp "
					+ " where  stusun.fk_academic_year=stuann.mk_academic_year "
					+ " and stusun.fk_academic_period=1 "
					+ " and stusun.fk_student_nr=stuann.mk_student_nr "
					+ " and stusun.status_code in ( 'RG','FC','DC' ) "
					+ " and grd.code = stuann.MK_HIGHEST_QUALIFI "
					+ " and tustgr_temp.mk_academic_year(+) = stusun.fk_academic_year "
					+ " and tustgr_temp.semester(+) = stusun.semester_period "
					+ " and tustgr_temp.mk_study_unit_code(+) = stusun.mk_study_unit_code "
					+ " and tustgr_temp.mk_student_nr(+) = stusun.fk_student_nr   and tustgr_temp.GROUP_NR is not null  "
					+ " and stusun.FK_ACADEMIC_YEAR > 2011  and (tustgr_temp.mk_study_unit_code    LIKE 'A%' or  tustgr_temp.mk_study_unit_code   "
					+ " LIKE 'S%' or tustgr_temp.mk_study_unit_code    LIKE 'F%')"
					+ " order by studyUnit,semester,academicYear";
							
			studentRecords = jdt.queryForList(selectCourseList);
			
		} catch (Exception e) {
			log.error(this
					+ " ERROR On :getStudentCourses get all the active students list from temp tables ");
		}
		return studentRecords;

	}
 
 public List<?> getStudentGroupCoursesStartsWithBPI() {

		
		List<?> studentRecords = null;
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());

			String selectCourseList = "select stusun.FK_STUDENT_NR studentNr,stusun.mk_study_unit_code as studyUnit,stusun.semester_period as semester,stusun.fk_academic_year as academicYear, "
					+ " tustgr_temp.group_nr || substr(tustgr_temp.tutor_mode_gc181,1,1)  as tutorMode,grd.college_code as collegeCode,grd.under_post_categor as studyCategory , "
					+ " to_char(tustgr_temp.inactive_date,'yyyy-mm-dd hh24:mi:ss') as inactiveDate "
					+ " from stusun,stuann,grd,tustgr_temp "
					+ " where  stusun.fk_academic_year=stuann.mk_academic_year "
					+ " and stusun.fk_academic_period=1 "
					+ " and stusun.fk_student_nr=stuann.mk_student_nr "
					+ " and stusun.status_code in ( 'RG','FC','DC' ) "
					+ " and grd.code = stuann.MK_HIGHEST_QUALIFI "
					+ " and tustgr_temp.mk_academic_year(+) = stusun.fk_academic_year "
					+ " and tustgr_temp.semester(+) = stusun.semester_period "
					+ " and tustgr_temp.mk_study_unit_code(+) = stusun.mk_study_unit_code "
					+ " and tustgr_temp.mk_student_nr(+) = stusun.fk_student_nr   and tustgr_temp.GROUP_NR is not null  "
					+ " and stusun.FK_ACADEMIC_YEAR > 2011  and (tustgr_temp.mk_study_unit_code    LIKE 'B%' or  tustgr_temp.mk_study_unit_code   "
					+ " LIKE 'P%' or tustgr_temp.mk_study_unit_code    LIKE 'I%')"
					+ " order by studyUnit,semester,academicYear";
							
			studentRecords = jdt.queryForList(selectCourseList);
			
		} catch (Exception e) {
			log.error(this
					+ " ERROR On :getStudentCourses get all the active students list from temp tables ");
		}
		return studentRecords;

	}
 
 public List<?> getStudentGroupCoursesStartsWithEC() {

		
		List<?> studentRecords = null;
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());

			String selectCourseList = "select stusun.FK_STUDENT_NR studentNr,stusun.mk_study_unit_code as studyUnit,stusun.semester_period as semester,stusun.fk_academic_year as academicYear, "
					+ " tustgr_temp.group_nr || substr(tustgr_temp.tutor_mode_gc181,1,1)  as tutorMode,grd.college_code as collegeCode,grd.under_post_categor as studyCategory , "
					+ " to_char(tustgr_temp.inactive_date,'yyyy-mm-dd hh24:mi:ss') as inactiveDate "
					+ " from stusun,stuann,grd,tustgr_temp "
					+ " where  stusun.fk_academic_year=stuann.mk_academic_year "
					+ " and stusun.fk_academic_period=1 "
					+ " and stusun.fk_student_nr=stuann.mk_student_nr "
					+ " and stusun.status_code in ( 'RG','FC','DC' ) "
					+ " and grd.code = stuann.MK_HIGHEST_QUALIFI "
					+ " and tustgr_temp.mk_academic_year(+) = stusun.fk_academic_year "
					+ " and tustgr_temp.semester(+) = stusun.semester_period "
					+ " and tustgr_temp.mk_study_unit_code(+) = stusun.mk_study_unit_code "
					+ " and tustgr_temp.mk_student_nr(+) = stusun.fk_student_nr   and tustgr_temp.GROUP_NR is not null  "
					+ " and stusun.FK_ACADEMIC_YEAR > 2011  and (tustgr_temp.mk_study_unit_code    LIKE 'E%' or  tustgr_temp.mk_study_unit_code   "
					+ " LIKE 'C%')"
					+ " order by studyUnit,semester,academicYear";
							
			studentRecords = jdt.queryForList(selectCourseList);
			
		} catch (Exception e) {
			log.error(this
					+ " ERROR On :getStudentCourses get all the active students list from temp tables ");
		}
		return studentRecords;

	}
 
	public void deleteCourseFromTmpTable(String userId, String courseCode, String year, String semester, String tutorMode) throws Exception {
		String query=null;		
		String semesterPeried=getSemester(semester);
		//if(tutorMode == ""){
			 query = "delete from STUSUN_TEMP where status_code in ( 'RG','FC','DC' ) and FK_STUDENT_NR='"+userId+"' and MK_STUDY_UNIT_CODE='"+courseCode+"' and FK_ACADEMIC_YEAR='"+year+"'"
			 		+ " and SEMESTER_PERIOD='"+semesterPeried+"'" ;
			 log.info(this+": delete STUSUN_TEMP record for "+userId+" for site "+courseCode+" "+year+" "+semester);
/*		}else{
			String groupNr = tutorMode.substring(0, tutorMode.length() - 1);
	    	String mode = tutorMode.substring(tutorMode.length()-1, tutorMode.length());
	    	if(mode.equalsIgnoreCase("T")){
	    		tutorMode="TEACH";
	    	}else if(mode.equalsIgnoreCase("E")){
	    		tutorMode="ETUTOR";
	    	}
			 query = "delete from tustgr_temp where MK_STUDENT_NR='"+userId+"' and MK_STUDY_UNIT_CODE='"+courseCode+"' and MK_ACADEMIC_YEAR='"+year+"'"
				 		+ " and SEMESTER='"+semesterPeried+"' and GROUP_NR = '"+groupNr+"' and TUTOR_MODE_GC181 = '"+tutorMode+"'";
			 log.debug(this+": delete tustgr_temp record for "+userId+" for site "+courseCode+" "+year+" "+semester+" "+tutorMode);
		}*/
		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(query);
		} catch (Exception ex) {
			log.error(this+": deleteCourseFromTmpTable: Error on Delete from stusun_temp or tustgr_temp for student   "+userId+" and course "+courseCode);
			ex.printStackTrace();
		}
	}
	
	public void deleteTutorCourseFromTmpTable(String userId, String courseCode, String year, String semester, String tutorMode) throws Exception {
		String query=null;		
		String semesterPeried=getSemester(semester);
	
			String groupNr = tutorMode.substring(0, tutorMode.length() - 1);
	    	String mode = tutorMode.substring(tutorMode.length()-1, tutorMode.length());
	    	if(mode.equalsIgnoreCase("T")){
	    		tutorMode="TEACH";
	    	}else if(mode.equalsIgnoreCase("E")){
	    		tutorMode="ETUTOR";
	    	}else if(mode.equalsIgnoreCase("S")){
	    		tutorMode="SCIENCE";
	    	}
			 query = "delete from tustgr_temp where MK_STUDENT_NR='"+userId+"' and MK_STUDY_UNIT_CODE='"+courseCode+"' and MK_ACADEMIC_YEAR='"+year+"'"
				 		+ " and SEMESTER='"+semesterPeried+"' and GROUP_NR = '"+groupNr+"' and TUTOR_MODE_GC181 = '"+tutorMode+"'";
			 log.info(this+": delete tustgr_temp record for "+userId+" for site "+courseCode+" "+year+" "+semester+" "+tutorMode);
		
		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(query);
		} catch (Exception ex) {
			log.error(this+": deleteCourseFromTmpTable: Error on Delete from stusun or tustgr for student   "+userId+" and course "+courseCode);
			ex.printStackTrace();
		}
	}
	
	
	public void deleteUserFromInterfaceTable(String status,String userId,String userRole, String keyData,String tableName,String action,String fromValue,String toValue,String modifiedFlag,String createdDate){
		
		String deleteQuery=null;
		String insertQuery = null;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		try{
			 deleteQuery = "delete from LSSINT where USER_ID = '"+userId+"' and USER_ROLE = '"+userRole+"' and"
			 		 + " TABLE_NAME='"+tableName+"' and KEY_DATA='"+keyData+"'";
			 
				
				 jdbcTemplate.update(deleteQuery);
				 log.info(this+": delete LSSINT record for user "+userId+" for site "+keyData);
				 
		}catch (Exception ex) {
			log.error(this+": deleteUserFromInterfaceTable: Error on Delete from LSSINT for user "+userId+" and course "+keyData);
			ex.printStackTrace();
		}
		
		 if(status == "fail"){
			 //check duplicates 
			  boolean checkDuplicateRecord = checkDuplicateInINTSAKFAIL(userId,userRole,keyData,tableName,action,fromValue,toValue,modifiedFlag,"LSS",createdDate);
			  if(checkDuplicateRecord == false){			 
			  insertUserIntoFailTable(userId,userRole,keyData,tableName,action,fromValue,toValue,modifiedFlag,createdDate);
			}
	    }else{
	    	
		try{
			    //check for duplaicate
			
			   boolean checkDuplicateRecord = checkDuplicateInLSSINTLOG(userId,userRole,keyData,tableName,action,fromValue,toValue,modifiedFlag,"LSS",createdDate);
			    
			   if(checkDuplicateRecord == false){
				 //insert the same record to LSSINTLOG for audit
				 insertQuery = "INSERT INTO LSSINTLOG(USER_ID,USER_ROLE,KEY_DATA,TABLE_NAME,ACTION,FROM_VALUE,TO_VALUE,MODIFIED_FLAG,CREATED_FROM,CREATED_ON,ARCHIVED_ON)"
				 	        	+ " values (?,?,?,?,?,?,?,?,?,to_TIMESTAMP(?,'YYYY/MM/DD HH24:MI:SS'),sysdate)";
				 
				 
				 jdbcTemplate.update(insertQuery,new Object[]  {userId,userRole,keyData,tableName,action,fromValue,toValue,modifiedFlag,"LSS",createdDate});
				 log.info(this+": Inserted into LSSINTLOG for user "+userId+" for site "+keyData);
			   }
		}catch (Exception ex) {
			log.error(this+": deleteUserFromInterfaceTable: Error on Insert into LSSINTLOG for user "+userId+" and course "+keyData);
			ex.printStackTrace();
		}finally{
			deleteQuery=null;
			insertQuery=null;
			jdbcTemplate=null;
		}
		}
	}
	
	public boolean checkDuplicateInINTSAKFAIL(String userId,String userRole, String keyData,String tableName,String action,String fromValue,String toValue,String modifiedFlag,String createdFrom,String createdDate) {

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List<?> userRecords = null;
		boolean checkUser = false;
		try {
			String selectCourseList =  " select * from INTSAK_FAIL where USER_ID = '"+userId+"' and USER_ROLE = '"+userRole+"' and" 	
										+ " TABLE_NAME='"+tableName+"' and KEY_DATA='"+keyData+"' and ACTION = '"+action+"'  and created_on =TO_DATE('"+createdDate+"', 'YYYY/MM/DD HH24:MI:SS')";

			 userRecords = jdt.queryForList(selectCourseList);
			 if(userRecords.size() == 0){
				 checkUser = false;
			 }else{
				 checkUser = true;
			 }
		} catch (Exception e) {
			log.error(this
					+ " ERROR On :checkDuplicateInINTSAKFAIL select the user from INTSAK_FAIL user "+userId+" and course "+keyData);
			e.printStackTrace();
		}
		return checkUser;

	}
	
	public boolean checkDuplicateInLSSINTLOG(String userId,String userRole, String keyData,String tableName,String action,String fromValue,String toValue,String modifiedFlag,String createdFrom,String createdDate) {

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List<?> userRecords = null;
		boolean checkUser = false;
		try {
			String selectCourseList =  " select * from LSSINTLOG where USER_ID = '"+userId+"' and USER_ROLE = '"+userRole+"' and" 	
										+ " TABLE_NAME='"+tableName+"' and KEY_DATA='"+keyData+"' and ACTION = '"+action+"' and created_on =TO_DATE('"+createdDate+"', 'YYYY/MM/DD HH24:MI:SS')  ";

			 userRecords = jdt.queryForList(selectCourseList);
			 if(userRecords.size() == 0){
				 checkUser = false;
			 }else{
				 checkUser = true;
			 }
		} catch (Exception e) {
			log.error(this
					+ " ERROR On :checkDuplicateInLSSINTLOG select the user from INTSAK_FAIL user "+userId+" and course "+keyData);
			e.printStackTrace();
		}
		return checkUser;

	}
	
	
public void deleteUserFromFailuresTable(String status,String userId,String userRole, String keyData,String tableName,String action,String fromValue,String toValue,String modifiedFlag,String createdDate){
		
		String deleteQuery=null;
		String insertQuery = null;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		try{
			 deleteQuery = "delete from INTSAK_FAIL where USER_ID = '"+userId+"' and USER_ROLE = '"+userRole+"' and"
			 		 + " TABLE_NAME='"+tableName+"' and KEY_DATA='"+keyData+"'";
			 
				
				 jdbcTemplate.update(deleteQuery);
				 log.info(this+": delete INTSAK_FAIL record for user "+userId+" for site "+keyData);
				 
		}catch (Exception ex) {
			log.error(this+": deleteUserFromFailuresTable: Error on Delete from INTSAK_FAIL for user "+userId+" and course "+keyData);
			ex.printStackTrace();
		}
		
		 if(status == null){
			insertUserIntoFailTable(userId,userRole,keyData,tableName,action,fromValue,toValue,modifiedFlag,createdDate);
	    }else{
	    	
		try{
				 //insert the same record to LSSINTLOG for aaudit
				 insertQuery = "INSERT INTO LSSINTLOG(USER_ID,USER_ROLE,KEY_DATA,TABLE_NAME,ACTION,FROM_VALUE,TO_VALUE,MODIFIED_FLAG,CREATED_FROM,CREATED_ON,ARCHIVED_ON)"
				 	        	+ " values (?,?,?,?,?,?,?,?,?,to_TIMESTAMP(?,'YYYY/MM/DD HH24:MI:SS'),sysdate)";
				 
				 
				 jdbcTemplate.update(insertQuery,new Object[]  {userId,userRole,keyData,tableName,action,fromValue,toValue,modifiedFlag,"LSS",createdDate});
				 log.info(this+": Inserted into LSSINTLOG for user "+userId+" for site "+keyData);

		}catch (Exception ex) {
			log.error(this+": deleteUserFromFailuresTable: Error on Insert into LSSINTLOG for user "+userId+" and course "+keyData);
			ex.printStackTrace();
		}finally{
			deleteQuery=null;
			insertQuery=null;
			jdbcTemplate=null;
		}
		}
	}
	
   public void insertUserIntoFailTable(String userId,String userRole, String keyData,String tableName,String action,String fromValue,String toValue,String modifiedFlag,String createdDate){
		
	
			String insertQuery = null;
	
			  try{
				 //insert the same record to LSSINTLOG for aaudit
				 insertQuery = "INSERT INTO INTSAK_FAIL(USER_ID,USER_ROLE,KEY_DATA,TABLE_NAME,ACTION,FROM_VALUE,TO_VALUE,MODIFIED_FLAG,CREATED_FROM,CREATED_ON)"
				 	        	+ " values (?,?,?,?,?,?,?,?,?,sysdate)";
				 
				 JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
				 jdbcTemplate.update(insertQuery,new Object[]  {userId,userRole,keyData,tableName,action,fromValue,toValue,modifiedFlag,"LSS" });
				 log.info(this+": Inserted into INTSAK_FAIL for user "+userId+" for site "+keyData);
				 
			  }catch (Exception ex) {
				  log.error(this+": insertUserIntoFailTable: Error on Insert into INTSAK_FAIL for user "+userId+" and course "+keyData);
				  ex.printStackTrace();
			  }finally{
						insertQuery=null;
			  }
		}
	
	public void deleteCourseFromTmpTable(String userId, String courseCode, String year, String semester, String accessLevel, String tutorMode) throws Exception {
		
		try{
			
		String query=null;		
		String semesterPeried=getSemester(semester);
		if(tutorMode == ""){
			 query = "delete from usrsun_temp where NOVELL_USER_ID='"+userId.toUpperCase()+"' and MK_STUDY_UNIT_CODE='"+courseCode+"' and MK_ACADEMIC_YEAR='"+year+"'"
			 		+ " and ACCESS_LEVEL = '"+accessLevel+"' and MK_SEMESTER_PERIOD='"+semesterPeried+"'" ;
			 log.info(this+": delete usrsun_temp record for "+userId+" for site "+courseCode+" "+year+" "+semester);
		}else{
			String groupNr = tutorMode.substring(0, tutorMode.length() - 1);
	    	String mode = tutorMode.substring(tutorMode.length()-1, tutorMode.length());
	    	if(mode.equalsIgnoreCase("T")){
	    		tutorMode="TEACH";
	    	}else if(mode.equalsIgnoreCase("E")){
	    		tutorMode="ETUTOR";
	    	}
			
			query = "delete from usrsun_temp where NOVELL_USER_ID='"+userId.toUpperCase()+"' and MK_STUDY_UNIT_CODE='"+courseCode+"' and MK_ACADEMIC_YEAR='"+year+"'"
			 		+ " and ACCESS_LEVEL = '"+accessLevel+"' and MK_SEMESTER_PERIOD='"+semesterPeried+"' and GROUP_NR = '"+groupNr+"' and TUTOR_MODE_GC181 = '"+tutorMode+"'";
			
			log.info(this+": delete usrsun_temp record for "+userId+" for Tutor site  "+courseCode+" "+year+" "+semester+" "+tutorMode+" "+groupNr);
		}		
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(query);
		} catch (Exception ex) {
			log.error(this+": deleteCourseFromTmpTable: Error on Delete from usrsun_temp for user   "+userId+" and course "+courseCode+" "+year+" "+semester);
			ex.printStackTrace();
		}
	}
	
	public static String getSemester(String semester) {
		String sem=null;
		if(semester=="Y1"){
			sem="0";
		}else if(semester=="S1"){
			sem="1";
		}
		else if(semester=="S2"){
			sem="2";
		}
		else if(semester=="Y2"){
			sem="6";
		}
		return sem;
	}
	
	
	public List<?> getStaffCourses() {

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List<?> staffRecords = null;
		try {
			String selectCourseList = " select novell_user_id, mk_study_unit_code, mk_academic_year, mk_semester_period, access_level, CONCAT(group_nr ,substr(TUTOR_MODE_GC181,1,1)) tutor_mode  "
					+ " from usrsun  "
					+ " where  novell_user_id is not null and system_type in ('L','C') and ACCESS_LEVEL in ('PRIML','SECDL','CADMN')  and MK_ACADEMIC_YEAR  in (2017 ,2016) order by MK_ACADEMIC_YEAR desc ";

			staffRecords = jdt.queryForList(selectCourseList);
		} catch (Exception e) {
			log.error(this
					+ " ERROR On :getStaffCourses get all the active students list from USRSUN ");
			e.printStackTrace();
		}
		return staffRecords;

	}
	
	public List<?> getUserRecords() {

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List<?> userRecords = null;
		try {
			String selectCourseList =  " SELECT nvl(USER_ID,' ') as USER_ID , nvl(USER_ROLE,' ') as USER_ROLE, nvl(KEY_DATA,' ') as KEY_DATA, nvl(TABLE_NAME,' ') as TABLE_NAME , "
										+ " nvl(ACTION,' ') as ACTION, nvl(FROM_VALUE,' ') as FROM_VALUE, nvl(TO_VALUE,' ') as TO_VALUE, nvl(MODIFIED_FLAG,' ') as MODIFIED_FLAG,"
										+ " to_char(CREATED_ON,'YYYY/MM/DD HH24:MI:SS')  as CREATED_ON FROM LSSINT "
										+ " where TABLE_NAME ='USRSUN' order by table_name desc, created_on  asc   ";

			 userRecords = jdt.queryForList(selectCourseList);
		} catch (Exception e) {
			log.error(this
					+ " ERROR On :getUserRecords get all the user list from LSSINT ");
			e.printStackTrace();
		}
		return userRecords;

	}
	
	public List<?> getStudentGroupRecordsWith63And62() {

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List<?> userRecords = null;
		try {
			String selectCourseList =  " SELECT nvl(USER_ID,' ') as USER_ID , nvl(USER_ROLE,' ') as USER_ROLE, nvl(KEY_DATA,' ') as KEY_DATA, nvl(TABLE_NAME,' ') as TABLE_NAME , "
										+ " nvl(ACTION,' ') as ACTION, nvl(FROM_VALUE,' ') as FROM_VALUE, nvl(TO_VALUE,' ') as TO_VALUE, nvl(MODIFIED_FLAG,' ') as MODIFIED_FLAG,"
										+ " to_char(CREATED_ON,'YYYY/MM/DD HH24:MI:SS')  as CREATED_ON FROM LSSINT "
										+ " where TABLE_NAME ='TUSTGR' and  (USER_ID  like '63%' or USER_ID  like '62%')   order by table_name desc, created_on  asc   ";

			 userRecords = jdt.queryForList(selectCourseList);
		} catch (Exception e) {
			log.error(this
					+ " ERROR On :getStudentGroupRecordsWith63And62 get all the user list from LSSINT ");
			e.printStackTrace();
		}
		return userRecords;

	}
	
	public List<?> getStudentGroupRecordsWithout63And62() {

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List<?> userRecords = null;
		try {
			String selectCourseList =  " SELECT nvl(USER_ID,' ') as USER_ID , nvl(USER_ROLE,' ') as USER_ROLE, nvl(KEY_DATA,' ') as KEY_DATA, nvl(TABLE_NAME,' ') as TABLE_NAME , "
										+ " nvl(ACTION,' ') as ACTION, nvl(FROM_VALUE,' ') as FROM_VALUE, nvl(TO_VALUE,' ') as TO_VALUE, nvl(MODIFIED_FLAG,' ') as MODIFIED_FLAG,"
										+ " to_char(CREATED_ON,'YYYY/MM/DD HH24:MI:SS')  as CREATED_ON FROM LSSINT "
										+ " where TABLE_NAME ='TUSTGR' and USER_ID  NOT LIKE '63%'   and USER_ID  NOT LIKE '62%' and USER_ID  like '6%'  order by table_name desc, created_on  asc   ";

			 userRecords = jdt.queryForList(selectCourseList);
		} catch (Exception e) {
			log.error(this
					+ " ERROR On :getStudentGroupRecordsWithout63And62 get all the user list from LSSINT ");
			e.printStackTrace();
		}
		return userRecords;

	}
	
	public List<?> getStudentGroupRecordsWithout6() {

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List<?> userRecords = null;
		try {
			String selectCourseList =  " SELECT nvl(USER_ID,' ') as USER_ID , nvl(USER_ROLE,' ') as USER_ROLE, nvl(KEY_DATA,' ') as KEY_DATA, nvl(TABLE_NAME,' ') as TABLE_NAME , "
										+ " nvl(ACTION,' ') as ACTION, nvl(FROM_VALUE,' ') as FROM_VALUE, nvl(TO_VALUE,' ') as TO_VALUE, nvl(MODIFIED_FLAG,' ') as MODIFIED_FLAG,"
										+ " to_char(CREATED_ON,'YYYY/MM/DD HH24:MI:SS')  as CREATED_ON FROM LSSINT "
										+ " where TABLE_NAME ='TUSTGR' and USER_ID not like '6%'  order by table_name desc, created_on  asc   ";

			 userRecords = jdt.queryForList(selectCourseList);
		} catch (Exception e) {
			log.error(this
					+ " ERROR On :getStudentGroupRecordsWithout6 get all the user list from LSSINT ");
			e.printStackTrace();
		}
		return userRecords;

	}
	
	public List<?> getStudentCourseRecords() {

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List<?> userRecords = null;
		try {
			String selectCourseList =  " SELECT nvl(USER_ID,' ') as USER_ID , nvl(USER_ROLE,' ') as USER_ROLE, nvl(KEY_DATA,' ') as KEY_DATA, nvl(TABLE_NAME,' ') as TABLE_NAME , "
										+ " nvl(ACTION,' ') as ACTION, nvl(FROM_VALUE,' ') as FROM_VALUE, nvl(TO_VALUE,' ') as TO_VALUE, nvl(MODIFIED_FLAG,' ') as MODIFIED_FLAG,"
										+ " to_char(CREATED_ON,'YYYY/MM/DD HH24:MI:SS')  as CREATED_ON FROM LSSINT "
										+ " where TABLE_NAME ='STUSUN' and USER_ID not like '5%' and USER_ID not like '6%' and USER_ID not like '4%' order by  created_on asc ";

			 userRecords = jdt.queryForList(selectCourseList);
		} catch (Exception e) {
			log.error(this
					+ " ERROR On :getUserRecords get all the user list from LSSINT ");
			e.printStackTrace();
		}
		return userRecords;

	}
	
	public List<?> getStudentCourseForFourthJob() {

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List<?> userRecords = null;
		try {
			String selectCourseList =  " SELECT nvl(USER_ID,' ') as USER_ID , nvl(USER_ROLE,' ') as USER_ROLE, nvl(KEY_DATA,' ') as KEY_DATA, nvl(TABLE_NAME,' ') as TABLE_NAME , "
										+ " nvl(ACTION,' ') as ACTION, nvl(FROM_VALUE,' ') as FROM_VALUE, nvl(TO_VALUE,' ') as TO_VALUE, nvl(MODIFIED_FLAG,' ') as MODIFIED_FLAG,"
										+ " to_char(CREATED_ON,'YYYY/MM/DD HH24:MI:SS')  as CREATED_ON FROM LSSINT "
										+ " where TABLE_NAME ='STUSUN' and USER_ID  like '4%' order by  created_on asc ";

			 userRecords = jdt.queryForList(selectCourseList);
		} catch (Exception e) {
			log.error(this
					+ " ERROR On :getUserRecords get all the user list from LSSINT ");
			e.printStackTrace();
		}
		return userRecords;

	}
	
	public List<?> getStudentCourseForFifthJob() {

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List<?> userRecords = null;
		try {
			String selectCourseList =  " SELECT nvl(USER_ID,' ') as USER_ID , nvl(USER_ROLE,' ') as USER_ROLE, nvl(KEY_DATA,' ') as KEY_DATA, nvl(TABLE_NAME,' ') as TABLE_NAME , "
										+ " nvl(ACTION,' ') as ACTION, nvl(FROM_VALUE,' ') as FROM_VALUE, nvl(TO_VALUE,' ') as TO_VALUE, nvl(MODIFIED_FLAG,' ') as MODIFIED_FLAG,"
										+ " to_char(CREATED_ON,'YYYY/MM/DD HH24:MI:SS')  as CREATED_ON FROM LSSINT "
										+ " where TABLE_NAME ='STUSUN' and USER_ID  like '6%' and USER_ID Not like '63%' and USER_ID not like '64%'   order by  created_on asc ";

			 userRecords = jdt.queryForList(selectCourseList);
		} catch (Exception e) {
			log.error(this
					+ " ERROR On :getUserRecords get all the user list from LSSINT ");
			e.printStackTrace();
		}
		return userRecords;

	}
	
	public List<?> getStudentCourseForSixthJob() {

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List<?> userRecords = null;
		try {
			String selectCourseList =  " SELECT nvl(USER_ID,' ') as USER_ID , nvl(USER_ROLE,' ') as USER_ROLE, nvl(KEY_DATA,' ') as KEY_DATA, nvl(TABLE_NAME,' ') as TABLE_NAME , "
										+ " nvl(ACTION,' ') as ACTION, nvl(FROM_VALUE,' ') as FROM_VALUE, nvl(TO_VALUE,' ') as TO_VALUE, nvl(MODIFIED_FLAG,' ') as MODIFIED_FLAG,"
										+ " to_char(CREATED_ON,'YYYY/MM/DD HH24:MI:SS')  as CREATED_ON FROM LSSINT "
										+ " where TABLE_NAME ='STUSUN' and  (USER_ID  like '63%' or USER_ID  like '64%') order by  created_on asc ";

			 userRecords = jdt.queryForList(selectCourseList);
		} catch (Exception e) {
			log.error(this
					+ " ERROR On :getUserRecords get all the user list from LSSINT ");
			e.printStackTrace();
		}
		return userRecords;

	}
	
	
	public List<?> getDataFromIntSakFails() {

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List<?> userRecords = null;
		try {
			String selectCourseList =  " SELECT nvl(USER_ID,' ') as USER_ID , nvl(USER_ROLE,' ') as USER_ROLE, nvl(KEY_DATA,' ') as KEY_DATA, nvl(TABLE_NAME,' ') as TABLE_NAME , "
										+ " nvl(ACTION,' ') as ACTION, nvl(FROM_VALUE,' ') as FROM_VALUE, nvl(TO_VALUE,' ') as TO_VALUE, nvl(MODIFIED_FLAG,' ') as MODIFIED_FLAG,"
										+ " to_char(CREATED_ON,'YYYY-MM-DD HH24:MI:SS')  as CREATED_ON FROM INTSAK_FAIL order by  table_name desc, created_on asc ";

			 userRecords = jdt.queryForList(selectCourseList);
		} catch (Exception e) {
			log.error(this
					+ " ERROR On :getUserRecords get all the user list from LSSINT ");
			e.printStackTrace();
		}
		return userRecords;

	}
	
	public List<?> getStudentRecordsStartsWith5() {

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List<?> userRecords = null;
		try {
			String selectCourseList =  " SELECT nvl(USER_ID,' ') as USER_ID , nvl(USER_ROLE,' ') as USER_ROLE, nvl(KEY_DATA,' ') as KEY_DATA, nvl(TABLE_NAME,' ') as TABLE_NAME , "
										+ " nvl(ACTION,' ') as ACTION, nvl(FROM_VALUE,' ') as FROM_VALUE, nvl(TO_VALUE,' ') as TO_VALUE, nvl(MODIFIED_FLAG,' ') as MODIFIED_FLAG,"
										+ " to_char(CREATED_ON,'YYYY-MM-DD HH24:MI:SS')  as CREATED_ON FROM LSSINT "
										+ " where TABLE_NAME ='STUSUN' and USER_ID like '5%' order by created_on   ";

			 userRecords = jdt.queryForList(selectCourseList);
		} catch (Exception e) {
			log.error(this
					+ " ERROR On :getUserRecords get all the user list from LSSINT ");
			e.printStackTrace();
		}
		return userRecords;

	}
	
	public List<?> getStaffCourses2() {

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List<?> staffRecords = null;
		try {
			String selectCourseList = " select novell_user_id, mk_study_unit_code, mk_academic_year, mk_semester_period, access_level, CONCAT(group_nr ,substr(TUTOR_MODE_GC181,1,1)) tutor_mode  "
					+ " from usrsun  "
					+ " where novell_user_id is not null and  system_type in ('L','C') and ACCESS_LEVEL in ('PRIML','SECDL','CADMN') and MK_ACADEMIC_YEAR  in (2014 ,2015) order by MK_ACADEMIC_YEAR desc ";

			staffRecords = jdt.queryForList(selectCourseList);

		} catch (Exception e) {
			log.error(this
					+ " ERROR On :getStaffCourses get all the active students list from USRSUN ");
			e.printStackTrace();
		}
		return staffRecords;

	}
	
	public List<?> getStaffCourses3() {

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List<?> staffRecords = null;
		try {
			String selectCourseList = " select novell_user_id, mk_study_unit_code, mk_academic_year, mk_semester_period, access_level, CONCAT(group_nr ,substr(TUTOR_MODE_GC181,1,1)) tutor_mode  "
					+ " from usrsun  "
					+ " where novell_user_id is not null and system_type in ('L','C') and ACCESS_LEVEL in ('PRIML','SECDL','CADMN') and MK_ACADEMIC_YEAR not in (2014 ,2015,2016,2017) and MK_ACADEMIC_YEAR>2012 order by MK_ACADEMIC_YEAR desc ";

			staffRecords = jdt.queryForList(selectCourseList);

		} catch (Exception e) {
			log.error(this
					+ " ERROR On :getStaffCourses get all the active students list from USRSUN ");
			e.printStackTrace();
		}
		return staffRecords;

	}
	
	  public List<?>  getGroupSites(String courseCode , String academicYear , short period) {
	    	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> tutorSites = null;
			try {
				String selectCourseList = "select DISTINCT CONCAT(u.group_nr ,substr(u.TUTOR_MODE_GC181,1,1)) tutor_mode "+
		    			"from usrsun u "+
		    			"where u.mk_study_unit_code = '"+courseCode+"' "+
		    			"and u.mk_academic_year = '"+academicYear+"' "+
		    			"and u.mk_semester_period = '"+period+"' "+
		    			"and u.tutor_mode_gc181 != 'null' ";
				tutorSites = jdt.queryForList(selectCourseList);

			} catch (Exception e) {
				log.error(this
						+ " ERROR On :getGroupSites for staff user from USRSUN ");
				e.printStackTrace();
			}
			return tutorSites;
	    	
	    	
	    	/*List<Map<String, Object>> tutorResults = jdt.queryForList("select DISTINCT CONCAT(u.group_nr ,substr(u.TUTOR_MODE_GC181,1,1)) tutor_mode "+
	    			"from usrsun u "+
	    			"where u.mk_study_unit_code = '"+courseCode+"' "+
	    			"and u.mk_academic_year = '"+academicYear+"' "+
	    			"and u.mk_semester_period = '"+period+"' "+
	    			"and u.tutor_mode_gc181 != 'null' ");
	    	
	    	List<Usrsun> usrsuns = new Vector<Usrsun>();
	    	Iterator<?> i = tutorResults.iterator();
	    	
	    	 16 Nov 2015: 
			 * Sonette & Vijay change from ListOrderedMap to Map for upgrade to sakai 10.5 
			 * 
	    	for (Map<String, Object> row : tutorResults) {
				 Usrsun usrsun = new Usrsun();
				 usrsun.setTutorMode((String)row.get("TUTOR_MODE"));
					usrsuns.add(usrsun);
			 }
	    	
	    	return usrsuns;*/
	    }
}
