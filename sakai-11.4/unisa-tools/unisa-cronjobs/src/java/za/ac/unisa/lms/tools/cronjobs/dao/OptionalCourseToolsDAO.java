package za.ac.unisa.lms.tools.cronjobs.dao;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.db.api.SqlService;
import org.sakaiproject.component.cover.ComponentManager;

public class OptionalCourseToolsDAO {
	private Log log = null;
	private SqlService sqlService;

	public OptionalCourseToolsDAO() {
		log = LogFactory.getLog(this.getClass());
	}

	public boolean checkEmailTool(String siteId) throws Exception {
		sqlService = (SqlService) ComponentManager.get(SqlService.class);
		boolean emailtool=false;
		String sql = "select VALUE from SAKAI_SITE_PROPERTY WHERE SITE_ID = '"+siteId+"' AND NAME = 'contact-email'";
		try {
			Iterator<?> i = sqlService.dbRead(sql).iterator();
			if (i.hasNext()){
				log.debug("Found email");
				emailtool=true;
			}
		} catch (Exception ex) {
			throw new Exception("OptionalToolsDao : check Course Contact "+ex);
		}
		return emailtool;

	}
	public boolean checkFAQTool(String siteId) throws Exception {
		boolean faqtool=false;
		sqlService = (SqlService) ComponentManager.get(SqlService.class);
		String sql = "select DESCRIPTION from FAQ_CATEGORY WHERE SITE_ID = '"+siteId+"'";
		try {
			Iterator<?> i = sqlService.dbRead(sql).iterator();
			if (i.hasNext()){
				faqtool=true;
			}
		} catch (Exception ex) {
			throw new Exception("OptionalToolsDao : checkFAQTool "+ex);
		}
		return faqtool;

	}

}
