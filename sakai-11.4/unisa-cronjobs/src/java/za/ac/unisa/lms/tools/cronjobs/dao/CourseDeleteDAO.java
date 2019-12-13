package za.ac.unisa.lms.tools.cronjobs.dao;

import java.sql.Types;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.SakaiDAO;

public class CourseDeleteDAO extends SakaiDAO {
	

	private Log log;

	public CourseDeleteDAO() {
		log = LogFactory.getLog(this.getClass());
	}

	public List<?> getSakaiSites(String siteYear) {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		String query = "select site_id from SAKAI_SITE where substr(site_id,8,4) = ?";
		log.debug("Executing query: "+query);
		log.debug("Executing query: "+query);
		List<?> records = jdt.queryForList(
			query,
			new Object[] {
				siteYear
			},
			new int[] {
				Types.VARCHAR
			}
		);
		log.debug("Query executed successfully");
		return records;
	}

}
