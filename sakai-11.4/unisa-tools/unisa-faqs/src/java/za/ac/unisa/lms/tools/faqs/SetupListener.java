package za.ac.unisa.lms.tools.faqs;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.entity.api.EntityManager;
import org.sakaiproject.util.ToolListener;

import za.ac.unisa.lms.tools.faqs.dao.FaqDAO;

public class SetupListener implements ServletContextListener {
	private static Log M_log = LogFactory.getLog(ToolListener.class);

	public void contextInitialized(ServletContextEvent cse) {
		EntityManager entityManager = (EntityManager) ComponentManager
				.get(EntityManager.class);
		entityManager.registerEntityProducer(new FaqDAO(), "/unisa-faqs");
		M_log.info("Registered unisa-faqs as entity producer");
	}

	public void contextDestroyed(ServletContextEvent cse) {
	}
}