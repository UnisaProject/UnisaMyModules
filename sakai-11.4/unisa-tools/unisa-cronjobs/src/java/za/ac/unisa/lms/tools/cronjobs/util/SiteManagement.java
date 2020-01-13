package za.ac.unisa.lms.tools.cronjobs.util;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.tool.api.Tool;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.authz.api.AuthzGroup;
import org.sakaiproject.authz.api.AuthzGroupService;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SitePage;
import org.sakaiproject.site.api.ToolConfiguration;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.component.cover.ComponentManager;



public class SiteManagement {
	
	private ToolManager toolManager;
	private AuthzGroupService authzGroupService;
	private SiteService siteService;

	private Log log;
	private PrintWriter output;

	public SiteManagement(PrintWriter output) {
		log = LogFactory.getLog(SiteManagement.class);
		this.output = output;
	}

	public Site getSite(String siteId) throws Exception {
		Site site = null;
		siteService = (SiteService) ComponentManager.get(SiteService.class);
		try {
		 	site = siteService.getSite(siteId);
			updateRealm(siteId);
		} catch (IdUnusedException ide) {
			site = siteService.addSite(siteId,"Course");
			//log.info("Site created: "+site.getId());
			output.println("Site created: "+site.getId());
			siteService.save(site);
			updateRealm(siteId);
		}

		return site;
	}

	public void removeSite(String siteId) throws Exception {
		Site site = null;
		try {
			site = siteService.getSite(siteId);
			siteService.removeSite(site);
			output.println("Site removed: "+siteId);
			//log.info("Site removed: "+siteId);
		} catch (IdUnusedException ide) {
			log.debug("Site does not exist for removal: "+siteId);
		}
	}

	private void updateRealm(String siteId) throws Exception {
		authzGroupService = (AuthzGroupService) ComponentManager.get(AuthzGroupService.class);
		
		AuthzGroup realm = authzGroupService.getAuthzGroup("/site/"+siteId);

		if ((realm.getProviderGroupId() == null) || (!realm.getProviderGroupId().equals(siteId))) {
			realm.setProviderGroupId(siteId);
			output.println("Changed realm for "+siteId);
			//log.info("Changed realm for "+siteId);
			authzGroupService.save(realm);
		}
	}

	public SitePage getPage(Site site, String pageTitle) {
		List<?> pages = site.getPages();
		SitePage page = null;
		Iterator<?> i = pages.iterator();
		while (i.hasNext()) {
			page = (SitePage) i.next();
			if (page.getTitle().equals(pageTitle)) {
				return page;
			}
		}
		page = site.addPage();
		page.setTitle(pageTitle);
		//log.info("Page created: "+site.getId()+" - "+pageTitle);
		output.println("Page created: "+site.getId()+" - "+pageTitle);
		return page;
	}

	public void orderPage(Site site, String pageTitle) {
		List<SitePage> pages = site.getPages();
		SitePage pageToMove = null;
		Iterator<SitePage> i = pages.iterator();
		while (i.hasNext()) {
			SitePage page = (SitePage) i.next();
			if (page.getTitle().equals(pageTitle)) {
				pageToMove = page;
			}
		}
		if (pageToMove != null) {
			pages.remove(pageToMove);
			pages.add(pageToMove);
		}
	}

	public void removePage(Site site, String pageTitle) {
		List<?> pages = site.getPages();
		SitePage page = null;
		Iterator<?> i = pages.iterator();
		while (i.hasNext()) {
			page = (SitePage) i.next();
			if (page.getTitle().equals(pageTitle)) {
				site.removePage(page);
				output.println("Page removed: "+site.getId()+"."+pageTitle);
				//log.info("Page removed: "+site.getId()+"."+pageTitle);
				return;
			}
		}
		log.debug("Page not found for removal: "+site.getId()+"."+pageTitle);
	}

	public ToolConfiguration getTool(SitePage page, String toolTitle, String toolId) {
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		Tool tool = toolManager.getTool(toolId);
		List<?> tools = page.getTools();
		Iterator<?> i = tools.iterator();
		ToolConfiguration toolConfiguration = null;
		if (tool == null) tool = toolManager.getTool("sakai.motd");

		while (i.hasNext()) {
			ToolConfiguration toolConfig = (ToolConfiguration) i.next();
			if (toolConfig.getTitle().equals(toolTitle)) {
				return toolConfig;
			}
		}
		toolConfiguration = page.addTool();
		toolConfiguration.setTool(toolTitle,tool);
		//log.info("Tool added to page: "+page.getSiteId()+" - "+page.getTitle()+" - "+toolTitle);
		output.println("Tool added to page: "+page.getSiteId()+" - "+page.getTitle()+" - "+toolTitle);
		return toolConfiguration;
	}

	public void removeTool(SitePage page, String toolTitle, String toolId) {
		List<?> tools = page.getTools();
		Iterator<?> i = tools.iterator();

		while (i.hasNext()) {
			ToolConfiguration toolConfig = (ToolConfiguration) i.next();
			if (toolConfig.getTitle().equals(toolTitle) && toolConfig.getTool().getId().equals(toolId)) {
				page.removeTool(toolConfig);
				output.println("Tool removed from page: "+page.getSiteId()+" - "+page.getTitle()+" - "+toolTitle);
				return;
				//log.info("Tool removed from page: "+page.getSiteId()+" - "+page.getTitle()+" - "+toolTitle);

			}
		}
	}

}
