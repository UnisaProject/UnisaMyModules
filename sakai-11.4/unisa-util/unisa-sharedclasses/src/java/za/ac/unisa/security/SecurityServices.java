package za.ac.unisa.security;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.authz.api.FunctionManager;
import org.sakaiproject.authz.api.SecurityService;
import org.sakaiproject.component.api.ComponentManager;
import org.sakaiproject.entity.api.EntityManager;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;

import za.ac.unisa.exceptions.PermissionException;





public class SecurityServices {
	private static Log log = LogFactory.getLog(SecurityService.class);

	private SecurityService securityService;

	private SiteService siteService;

	private ToolManager toolManager;

	private EntityManager entityManager;

	private SessionManager sessionManager;

	private FunctionManager functionManager;

	public SecurityServices() {
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

	public void Registerfunction(String toolname, String permission) {
		List l = functionManager.getRegisteredFunctions(toolname);
		if (!l.contains(permission))
			functionManager.registerFunction(permission);
	}

	public String getSiteReference()
	{
		try
		{
			Site currentSite = siteService.getSite(toolManager.getCurrentPlacement().getContext());
			return currentSite.getReference();
		}
		catch (IdUnusedException e)
		{
			throw new PermissionException(
					"You must access the Welcome-tool through a proper site");
		}
	}

	public String getSiteId()
	{
		return toolManager.getCurrentPlacement().getContext();
	}

	public boolean checkPermission(String reference, String permission)
	{
		boolean cGP = securityService.unlock(permission, reference);
		return (cGP);
	}



}
