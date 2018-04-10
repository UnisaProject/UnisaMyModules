package za.ac.unisa.lms.db;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.component.cover.ComponentManager;

public class SakaiDAO {
	private DataSource dataSource;
	private Log log;

	public SakaiDAO() {
		log = LogFactory.getLog(this.getClass());
		dataSource = (DataSource) ComponentManager.get("javax.sql.DataSource");
		log.debug("Got a javax.sql.DataSource from the ComponentManager");
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}


}
