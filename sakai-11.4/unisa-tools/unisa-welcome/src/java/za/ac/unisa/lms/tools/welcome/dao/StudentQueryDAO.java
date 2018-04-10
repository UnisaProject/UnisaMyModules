package za.ac.unisa.lms.tools.welcome.dao;

import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.tool.api.ToolManager;

public class StudentQueryDAO {
	private ToolManager toolManager;
	private SiteService siteService;
	
	public boolean getMaintainLevel() throws Exception{
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		String currentSite = toolManager.getCurrentPlacement().getContext();
		siteService = (SiteService) ComponentManager.get(SiteService.class);
		return siteService.allowUpdateSite(currentSite);

	}
}
