package za.ac.unisa.lms.db;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.component.cover.ComponentManager;

public class StudentSystemDAO {
	private DataSource dataSource;
	private Log log;

	public StudentSystemDAO() {
		log = LogFactory.getLog(this.getClass());
		dataSource = (DataSource) ComponentManager.get("za.ac.unisa.lms.db.StudentSystemDataSource");
		log.debug("Got a javax.sql.DataSource from the ComponentManager");
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}


}
