package za.ac.unisa.permission.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.authz.api.FunctionManager;
import org.sakaiproject.authz.api.SecurityService;
import org.sakaiproject.component.api.ComponentManager;
import org.sakaiproject.entity.api.EntityManager;
import org.sakaiproject.entity.api.Reference;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;

public class UnisaDiscussionForumsSecurityServiceImpl {
	private FunctionManager functionManager;
	private SecurityService securityService;
	private SiteService siteService;
	private ToolManager toolManager;
	private EntityManager entityManager;

	private SessionManager sessionManager;
	private Log log = LogFactory.getLog(this.getClass());
	public static final String SECURE_ADD = "discuss.addforum";
	public static final String SECURE_UPDATEOWN = "discuss.updateforum.own";
	public static final String SECURE_UPDATEANY = "discuss.updateforum.any";
	public static final String SECURE_ADDTOPIC = "discuss.addtopic";
	public static final String SECURE_DELETETOPIC = "discuss.deletetopic.any";
	public static final String SECURE_ADDREPLY = "discuss.addreply";
	public static final String SECURE_DELETEREPLY = "discuss.deletereply.any";


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

		List l = functionManager.getRegisteredFunctions("discuss.");
		if (!l.contains(SECURE_ADD))
			functionManager.registerFunction(SECURE_ADD);
		if (!l.contains(SECURE_UPDATEOWN))
			functionManager.registerFunction(SECURE_UPDATEOWN);
		if (!l.contains(SECURE_UPDATEANY))
			functionManager.registerFunction(SECURE_UPDATEANY);
		if (!l.contains(SECURE_ADDTOPIC))
			functionManager.registerFunction(SECURE_ADDTOPIC);
		if (!l.contains(SECURE_DELETETOPIC))
			functionManager.registerFunction(SECURE_DELETETOPIC);
		if (!l.contains(SECURE_ADDREPLY))
			functionManager.registerFunction(SECURE_ADDREPLY);
		if (!l.contains(SECURE_DELETEREPLY))
			functionManager.registerFunction(SECURE_DELETEREPLY);
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
