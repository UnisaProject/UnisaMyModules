package za.ac.unisa.lms.tools.cronjobs.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class SakaiDAO extends za.ac.unisa.lms.db.SakaiDAO {

	public void removeStaleSakaiLocks() {
		Log log = LogFactory.getLog(this.getClass());
		log.debug("Removing stale locks from SAKAI_LOCKS");
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		String query = "delete from SAKAI_LOCKS where (usage_session_id NOT IN (select " +
				"CS.SESSION_ID from SAKAI_SESSION CS where " +
				"CS.SESSION_START = CS.SESSION_END)) or (usage_session_id IS NULL)";
		int hitcount = jdt.update(query);
		if (hitcount > 0) {
			log.info("Removed "+hitcount+" stale lock(s) from SAKAI_LOCKS");
		}
	}

}
