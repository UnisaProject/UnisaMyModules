package za.ac.unisa.lms.tools.examscriptstats.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class CourseDAO extends StudentSystemDAO{
	
	public boolean isValidStudyUnit(String code) {
		String sql = "select count(*) " +
		             "from sun " +
		             "where code = '" + code.toUpperCase().trim() + "'";
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}

}
