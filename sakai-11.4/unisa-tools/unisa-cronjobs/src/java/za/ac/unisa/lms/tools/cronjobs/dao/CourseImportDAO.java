package za.ac.unisa.lms.tools.cronjobs.dao;

import java.sql.Types;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class CourseImportDAO extends StudentSystemDAO {

	private Log log;

	public CourseImportDAO() {
		log = LogFactory.getLog(this.getClass());
	}

	public List<?> getCourses(int academicYear) {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());

				   
	 String query = "select s.code Course, u.mk_academic_year academicYear, u.mk_semester_period semesterPeriod , sp.online_method_gc175 from sun s, usrsun u, sunpdt sp "+
                                         "where s.code = u.mk_study_unit_code " +
                                        "and u.mk_study_unit_code = sp.fk_suncode "+
                                         "and u.mk_academic_year = ? "+
                                         "and s.in_use_flag='Y' "+
                                         "and u.system_type = 'L' "+
                                         "and u.access_level = 'PRIML' "+
                                        // "and (sp.online_method_gc175 !='SOAO' or sp.online_method_gc175 is null) "+
                                         //"and sp.online_method_gc175 !='SOAO' "+
                      		             " and sp.FORMATIVE_ASSESS_IND_GC197='MIXED' " +
                                         "and s.code =sp.fk_suncode "+
                                         "and sp.mk_academic_year = u.mk_academic_year "+
                                         "and sp.semester_period = u.mk_semester_period "+
                                         "order by s.code, u.mk_semester_period ";

		List<?> records = jdt.queryForList(
			query,
			new Object[] {
				new Integer(academicYear)
			},
			new int[] {
				Types.NUMERIC
			}
		);
		log.debug("CourseImportDAO: getCourses query executed successfully");
		return records;
	}

}
