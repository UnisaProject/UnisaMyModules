package za.ac.unisa.lms.tools.cronjobs.jobs;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SitePage;
import org.sakaiproject.site.api.ToolConfiguration;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;
import za.ac.unisa.lms.tools.cronjobs.util.SiteManagement;

public class GatewaySiteJob extends SingleClusterInstanceJob implements StatefulJob, OutputGeneratingJob {
	
	private SessionManager sessionManager;
	private SiteService siteService;
	private UserDirectoryService userDirectoryService;
	

	public static long runcount = 1L;
	private Log log = null;
	private ByteArrayOutputStream outputStream;
	private PrintWriter output;

	public void createGateway() throws Exception {
		
		siteService = (SiteService) ComponentManager.get(SiteService.class);

		SiteManagement sm = new SiteManagement(output);

		Site site = sm.getSite("!gateway");
		site.setTitle("MyUNISA");
		site.setDescription("MyUNISA");
		site.setJoinable(false);
		site.setPublished(true);
		site.setShortDescription("MyUNISA");
		site.setJoinerRole("access");

		SitePage page = null;
		ToolConfiguration toolconfig = null;

		page = sm.getPage(site, "Welcome");
		toolconfig = sm.getTool(page, "Welcome", "sakai.iframe");
		toolconfig.getPlacementConfig().setProperty("height", "550px");
		toolconfig.getPlacementConfig().setProperty("source", "/cmsys/myUnisa/index.html");
		toolconfig.getPlacementConfig().setProperty("reset.button", "true");

		page = sm.getPage(site, "Join myUnisa");
		toolconfig = sm.getTool(page, "Join myUnisa", "unisa.join");

		page = sm.getPage(site, "Forgotten Your Password?");
		toolconfig = sm.getTool(page, "Forgotten Your Password?", "unisa.password");

		page = sm.getPage(site, "Study Information");
		toolconfig = sm.getTool(page, "Study Information", "unisa.proxy");
		toolconfig.getPlacementConfig().setProperty("base", ServerConfigurationService.getString("cmsys.base"));
		toolconfig.getPlacementConfig().setProperty("url", "/Default.asp");
		toolconfig.getPlacementConfig().setProperty("parms", "Cmd=ViewContent&ContentID=17552");
		toolconfig.getPlacementConfig().setProperty("permParm", "P_XSLFile=unisa/lms.xsl");

		page = sm.getPage(site, "Admission");
		toolconfig = sm.getTool(page, "Admission", "unisa.proxy");
		toolconfig.getPlacementConfig().setProperty("base", ServerConfigurationService.getString("cmsys.base"));
		toolconfig.getPlacementConfig().setProperty("url", "/Default.asp");
		toolconfig.getPlacementConfig().setProperty("parms", "Cmd=ViewContent&ContentID=16586");
		toolconfig.getPlacementConfig().setProperty("permParm", "P_XSLFile=unisa/lms.xsl");

		page = sm.getPage(site, "Registration");
		toolconfig = sm.getTool(page, "Registration", "unisa.proxy");
		toolconfig.getPlacementConfig().setProperty("base", ServerConfigurationService.getString("cmsys.base"));
		toolconfig.getPlacementConfig().setProperty("url", "/Default.asp");
		toolconfig.getPlacementConfig().setProperty("parms", "Cmd=ViewContent&ContentID=17552");
		toolconfig.getPlacementConfig().setProperty("permParm", "P_XSLFile=unisa/lms.xsl");

		page = sm.getPage(site, "Examinations");
		toolconfig = sm.getTool(page, "Examinations", "unisa.proxy");
		toolconfig.getPlacementConfig().setProperty("base", ServerConfigurationService.getString("cmsys.base"));
		toolconfig.getPlacementConfig().setProperty("url", "/Default.asp");
		toolconfig.getPlacementConfig().setProperty("parms", "Cmd=ViewContent&ContentID=17553");
		toolconfig.getPlacementConfig().setProperty("permParm", "P_XSLFile=unisa/lms.xsl");

		page = sm.getPage(site, "Graduations");
		toolconfig = sm.getTool(page, "Graduations", "unisa.proxy");
		toolconfig.getPlacementConfig().setProperty("base", ServerConfigurationService.getString("cmsys.base"));
		toolconfig.getPlacementConfig().setProperty("url", "/Default.asp");
		toolconfig.getPlacementConfig().setProperty("parms", "Cmd=ViewContent&ContentID=17464");
		toolconfig.getPlacementConfig().setProperty("permParm", "P_XSLFile=unisa/lms.xsl");

		page = sm.getPage(site, "Counselling");
		toolconfig = sm.getTool(page, "Counselling", "unisa.proxy");
		toolconfig.getPlacementConfig().setProperty("base", ServerConfigurationService.getString("cmsys.base"));
		toolconfig.getPlacementConfig().setProperty("url", "/Default.asp");
		toolconfig.getPlacementConfig().setProperty("parms", "Cmd=ViewContent&ContentID=15974");
		toolconfig.getPlacementConfig().setProperty("permParm", "P_XSLFile=unisa/lms.xsl");

		page = sm.getPage(site, "Learner Support");
		toolconfig = sm.getTool(page, "Learner Support", "unisa.proxy");
		toolconfig.getPlacementConfig().setProperty("base", ServerConfigurationService.getString("cmsys.base"));
		toolconfig.getPlacementConfig().setProperty("url", "/Default.asp");
		toolconfig.getPlacementConfig().setProperty("parms", "Cmd=ViewContent&ContentID=82");
		toolconfig.getPlacementConfig().setProperty("permParm", "P_XSLFile=unisa/lms.xsl");

		page = sm.getPage(site, "Library");
		toolconfig = sm.getTool(page, "Library", "unisa.proxy");
		toolconfig.getPlacementConfig().setProperty("base", ServerConfigurationService.getString("cmsys.base"));
		toolconfig.getPlacementConfig().setProperty("url", "/Default.asp");
		toolconfig.getPlacementConfig().setProperty("parms", "Cmd=ViewContent&ContentID=18913");
		toolconfig.getPlacementConfig().setProperty("permParm", "P_XSLFile=unisa/lms.xsl");

		page = sm.getPage(site, "News");
		toolconfig = sm.getTool(page, "News", "unisa.proxy");
		toolconfig.getPlacementConfig().setProperty("base", ServerConfigurationService.getString("cmsys.base"));
		toolconfig.getPlacementConfig().setProperty("url", "/Default.asp");
		toolconfig.getPlacementConfig().setProperty("parms", "Cmd=LatestNews&TypeID=11&Count=12&Cmd=GetNav&NavID=11");
		toolconfig.getPlacementConfig().setProperty("permParm", "P_XSLFile=unisa/lms.xsl");

		page = sm.getPage(site, "TSA Student Numbers");
		toolconfig = sm.getTool(page, "TSA Student Numbers", "unisa.tsastudentnumber");

		page = sm.getPage(site, "e-Bookshop");
		toolconfig = sm.getTool(page, "e-Bookshop", "unisa.ebookshop");

		page = sm.getPage(site, "FAQs");
		toolconfig = sm.getTool(page, "FAQs", "unisa.faqs");

		page = sm.getPage(site, "Contact Details");
		toolconfig = sm.getTool(page, "Contact Details", "unisa.proxy");
		toolconfig.getPlacementConfig().setProperty("base", ServerConfigurationService.getString("cmsys.base"));
		toolconfig.getPlacementConfig().setProperty("url", "/Default.asp");
		toolconfig.getPlacementConfig().setProperty("permParm", "P_XSLFile=unisa/lms.xsl");
		toolconfig.getPlacementConfig().setProperty("parms", "Cmd=ViewContent&ContentID=17554");

		page = sm.getPage(site, "Contact Us");
		toolconfig = sm.getTool(page, "Contact Us", "unisa.contactus");

		if (toolconfig != null) ;

		siteService.save(site);

	}

	public void executeLocked(JobExecutionContext context) throws JobExecutionException {
		
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		
		log = LogFactory.getLog(this.getClass());
		outputStream = new ByteArrayOutputStream();
		output = new PrintWriter(outputStream);

		runcount++;

		log.info(runcount + ": Started.");

		try {

			User user = userDirectoryService.authenticate(
				ServerConfigurationService.getString("admin.user"),
				ServerConfigurationService.getString("admin.password")
			);
			if (user != null) {
				log.debug(runcount + ": Authenticated.");
				Session session = sessionManager.startSession();
				if (session == null) throw new JobExecutionException("No session");
				log.debug(runcount + ": Session started: "+session.getId());

				session.setUserEid(user.getId());
				session.setUserId(user.getId());
				sessionManager.setCurrentSession(session);

				createGateway();
				updateLock();

			} else {
				throw new Exception("User not authenticated");
			}

		} catch (Exception e) {
			log.error(runcount + ": Exception "+e.getClass().getName()+": "+e.getMessage());
			throw new JobExecutionException(e);
		}

		log.debug(runcount + ": Stopped. "+(runcount));

	}

	public String getOutput() {
		if (output != null) {
			output.flush();
			return outputStream.toString();
		}
		return null;
	}
}