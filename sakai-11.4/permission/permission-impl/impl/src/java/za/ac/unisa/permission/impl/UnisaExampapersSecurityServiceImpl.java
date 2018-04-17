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

public class UnisaExampapersSecurityServiceImpl {
	private FunctionManager functionManager;
	private SecurityService securityService;
	private SiteService siteService;
	private ToolManager toolManager;
	private EntityManager entityManager;

	private SessionManager sessionManager;
	private Log log = LogFactory.getLog(this.getClass());
	public static final String SECURE_CDREADANY = "exampapers.coverdocket.readany";
	public static final String SECURE_CDREADOWN = "exampapers.coverdocket.readown";
	public static final String SECURE_CDREADSTATUSOPEN = "exampapers.coverdocket.readstatusopen";
	public static final String SECURE_CDUPDATE = "exampapers.coverdocket.update";
	public static final String SECURE_CDUPDATENRPAGES = "exampapers.coverdocket.updatenrofpages";
	public static final String SECURE_CDUPDATESTATUSCLOSE = "exampapers.coverdocket.updatestatusclose";
	public static final String SECURE_CDUPDATESTATUSOPEN="exampapers.coverdocket.updatestatusopen";

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

		List l = functionManager.getRegisteredFunctions("exampapers.");
		if (!l.contains(SECURE_CDREADANY))
			functionManager.registerFunction(SECURE_CDREADANY);
		if (!l.contains(SECURE_CDREADOWN))
			functionManager.registerFunction(SECURE_CDREADOWN);
		if (!l.contains(SECURE_CDREADSTATUSOPEN))
			functionManager.registerFunction(SECURE_CDREADSTATUSOPEN);
		
		if (!l.contains(SECURE_CDUPDATE))
			functionManager.registerFunction(SECURE_CDUPDATE);
		if (!l.contains(SECURE_CDUPDATENRPAGES))
			functionManager.registerFunction(SECURE_CDUPDATENRPAGES);
		if (!l.contains(SECURE_CDUPDATESTATUSCLOSE))
			functionManager.registerFunction(SECURE_CDUPDATESTATUSCLOSE);
		if (!l.contains(SECURE_CDUPDATESTATUSOPEN))
			functionManager.registerFunction(SECURE_CDUPDATESTATUSOPEN);
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
