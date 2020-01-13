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

public class UnisaPbooksSecurityServiceImpl {
	private FunctionManager functionManager;
	private SecurityService securityService;
	private SiteService siteService;
	private ToolManager toolManager;
	private EntityManager entityManager;

	private SessionManager sessionManager;
	private Log log = LogFactory.getLog(this.getClass());
	public static final String SECURE_EDIT = "pbooks.edit";
	public static final String SECURE_ADD = "pbooks.add";
	//public static final String SECURE_REUSEBOOK = "pbooks.reusebook";
	//public static final String SECURE_ADDCOURSENOTE = "pbooks.coursenote";
	public static final String SECURE_DELETE = "pbooks.delete";
	public static final String SECURE_CONFIRM = "pbooks.confirm";
	public static final String SECURE_UNCONFIRM = "pbooks.unconfirm";
	public static final String SECURE_AUTHORIZE= "pbooks.authorize";
	public static final String SECURE_VALIDATE = "pbooks.validate";
	
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

		List l = functionManager.getRegisteredFunctions("pbooks.");
		
		if (!l.contains(SECURE_EDIT))
			functionManager.registerFunction(SECURE_EDIT);
		
		if (!l.contains(SECURE_ADD))
			functionManager.registerFunction(SECURE_ADD);
		
		if (!l.contains(SECURE_DELETE))
			functionManager.registerFunction(SECURE_DELETE);
		
		if (!l.contains(SECURE_CONFIRM))
			functionManager.registerFunction(SECURE_CONFIRM);
		
		if (!l.contains(SECURE_UNCONFIRM))
			functionManager.registerFunction(SECURE_UNCONFIRM);
		
		if (!l.contains(SECURE_AUTHORIZE))
			functionManager.registerFunction(SECURE_AUTHORIZE);
		
		
		if (!l.contains(SECURE_VALIDATE))
			functionManager.registerFunction(SECURE_VALIDATE);
		
		
		
		
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
