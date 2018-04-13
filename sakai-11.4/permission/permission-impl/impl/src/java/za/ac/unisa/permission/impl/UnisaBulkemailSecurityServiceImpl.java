package za.ac.unisa.permission.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.authz.api.FunctionManager;
import org.sakaiproject.authz.api.SecurityService;
import org.sakaiproject.component.api.ComponentManager;
import org.sakaiproject.entity.api.EntityManager;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;

public class UnisaBulkemailSecurityServiceImpl   {
	private FunctionManager functionManager;
	private SecurityService securityService;
	private SiteService siteService;
	private ToolManager toolManager;
	private EntityManager entityManager;

	private SessionManager sessionManager;
	private Log log = LogFactory.getLog(this.getClass());

	public static final String SECURE_VIEW = "bulkemail.view";
	public static final String SECURE_SEND = "bulkemail.send";


	public void init()
	{
		ComponentManager cm = org.sakaiproject.component.cover.ComponentManager
				.getInstance();
		functionManager = (FunctionManager) load(cm, FunctionManager.class
				.getName());

		entityManager = (EntityManager) load(cm, EntityManager.class.getName());
		securityService = (SecurityService) load(cm, SecurityService.class
				.getName());
		sessionManager = (SessionManager) load(cm, SessionManager.class
				.getName());
		siteService = (SiteService) load(cm, SiteService.class.getName());
		toolManager = (ToolManager) load(cm, ToolManager.class.getName());

		List l = functionManager.getRegisteredFunctions("bulkemail.");

		if (!l.contains(SECURE_VIEW))
			functionManager.registerFunction(SECURE_VIEW);
		if (!l.contains(SECURE_SEND))
			functionManager.registerFunction(SECURE_SEND);

	}
	private Object load(ComponentManager cm, String name)
	{
		Object o = cm.get(name);
		if (o == null)
		{
			log.error("Cant find Spring component named " + name);
		}
		return o;
	}


}
