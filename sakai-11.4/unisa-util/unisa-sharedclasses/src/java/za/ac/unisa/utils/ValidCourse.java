package za.ac.unisa.utils;

import java.sql.Types;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class ValidCourse extends StudentSystemDAO {
	private Log log;
	private String sql = 
		"select count(*) "+
		"from sun s, sunpdt p, usrsun u "+
		"where s.code = ? "+
		"and s.in_use_flag = 'Y' "+
		"and s.code = p.fk_suncode "+
		"and s.code = u.mk_study_unit_code "+
		"and p.mk_academic_year = u.mk_academic_year "+
		"and p.mk_academic_year >= 2005 "+
		"and p.mk_academic_period = 1 "+
		"and p.semester_period = u.mk_semester_period "+
		"and p.registration_allow = 'Y' "+
		"and u.system_type = 'L' "+
		"and u.access_level = 'PRIML' ";
	
	public ValidCourse() {
		log = LogFactory.getLog(this.getClass());
		log.debug("Call to ValidCourse()");
	}
	
	public boolean isValid(String course) {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(
			sql, 
			new Object[] {
				course
			},
			new int[] {
				Types.VARCHAR
			}
		);
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}
}
