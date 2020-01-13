package za.ac.unisa.lms.tools.cronjobs.dao;

import java.sql.Types;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class ONLImportDAO extends StudentSystemDAO {

	private Log log;

	public ONLImportDAO() {
		log = LogFactory.getLog(this.getClass());
	}

	public List<?> getCourses(int academicYear) {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());

		String query = "select distinct s.code Course,u.mk_academic_year academicYear, u.mk_semester_period semesterPeriod ," +
	                   "CONCAT(u.group_nr,substr(u.TUTOR_MODE_GC181,1,1) ) tutormode " +
	                   "from sun s, usrsun u,sunpdt sp "+
	                   "where s.code = u.mk_study_unit_code "+
	                   "  and u.mk_study_unit_code = sp.fk_suncode "+
	                   "  and u.mk_academic_year = ?"+
	                   "  and s.in_use_flag='Y' "+
	                   "  and u.system_type = 'C' "+
	                   "  and u.access_level = 'TUTOR' "+
	                   "  and s.code =sp.fk_suncode "+
	                   "  and sp.mk_academic_year=u.mk_academic_year "+
	                   "  and exists(select * from usrsun u2 where u.mk_study_unit_code = u2.mk_study_unit_code "+
	                   "  and u2.mk_academic_year = u.mk_academic_year "+
	                   "  and  u.mk_semester_period = u2.mk_semester_period "+
	                   "  and u2.system_type = 'L' "+
	                   "  and u2.access_level = 'PRIML' ) "+
	                   "order by s.code, u.mk_semester_period, CONCAT(u.group_nr,substr(u.TUTOR_MODE_GC181,1,1) ) ";
		

    	log.debug("Executing query: "+query);
		List<?> records = jdt.queryForList(
			query,
			new Object[] {
				new Integer(academicYear)
			},
			new int[] {
				Types.NUMERIC
			}
		);
		log.debug("Query executed successfully");
		return records;
	}

}
