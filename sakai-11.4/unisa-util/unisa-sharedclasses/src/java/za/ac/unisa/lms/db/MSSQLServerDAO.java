package za.ac.unisa.lms.db;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.component.cover.ComponentManager;

public class MSSQLServerDAO {
	private DataSource dataSource;
	private Log log;

	public MSSQLServerDAO(String database) {
		log = LogFactory.getLog(this.getClass());
		if (database.equalsIgnoreCase("persdb")) {
			dataSource = (DataSource)ComponentManager.get("za.ac.unisa.lms.db.MSSQLServerDataSource.persdb");
		} else if (database.equalsIgnoreCase("libresource")) {
			dataSource = (DataSource)ComponentManager.get("za.ac.unisa.lms.db.MSSQLServerDataSource.libresource");
		} else {
			dataSource = null;
			log.debug("No database specified!");
		}
		log.debug("Got a javax.sql.DataSource from the ComponentManager");
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}


}
