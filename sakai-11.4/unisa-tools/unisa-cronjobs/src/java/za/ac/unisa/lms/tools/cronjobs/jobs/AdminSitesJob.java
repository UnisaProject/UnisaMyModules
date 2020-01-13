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
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SitePage;
import org.sakaiproject.site.api.ToolConfiguration;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.component.cover.ComponentManager;

import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;
import za.ac.unisa.lms.tools.cronjobs.util.SiteManagement;

public class AdminSitesJob extends SingleClusterInstanceJob implements StatefulJob, OutputGeneratingJob {
	
	private SessionManager sessionManager;
	private SiteService siteService;
	private UserDirectoryService userDirectoryService;

	public static long runcount = 1L;
	private Log log = null;
	private ByteArrayOutputStream outputStream;
	private PrintWriter output;

	public void createMyAdmin() throws Exception {
		
		siteService = (SiteService) ComponentManager.get(SiteService.class);

		SiteManagement sm = new SiteManagement(output);

		Site site = sm.getSite("myadmin");
		site.setTitle("My Admin");
		site.setDescription("My Admin");
		site.setJoinable(false);
		site.setPublished(true);
		site.setShortDescription("My Admin");
		site.setType("Administrative");
		site.setJoinerRole("access");

		SitePage page = null;
		ToolConfiguration toolconfig = null;

		page = sm.getPage(site, "Assignments");
		toolconfig = sm.getTool(page, "Assignments", "unisa.proxy");
		toolconfig.getPlacementConfig().setProperty("base", ServerConfigurationService.getString("assignments.base"));
		toolconfig.getPlacementConfig().setProperty("url", "/SACDL20/ASPs/StuAssTab.asp");
		toolconfig.getPlacementConfig().setProperty("parms", "system={system}");
		toolconfig.getPlacementConfig().setProperty("cookieUserID", "Sol_Studno");
		toolconfig.getPlacementConfig().setProperty("cookieEmail", "Sol_Email");

		page = sm.getPage(site, "Academic Record");
		toolconfig = sm.getTool(page, "Academic Record", "unisa.acadhistory");
		page = sm.getPage(site, "Biographical Details");
		toolconfig = sm.getTool(page, "Biographical Details", "unisa.biodetails");
		page = sm.getPage(site, "Change Password");
		toolconfig = sm.getTool(page, "Change Password", "unisa.changepassword");
		page = sm.getPage(site, "Edit Registration");
		toolconfig = sm.getTool(page, "Edit Registration", "unisa.regdetails");
		page = sm.getPage(site, "Examination Results");
		toolconfig = sm.getTool(page, "Examination Results", "unisa.results");
		page = sm.getPage(site, "Financial Details");
		toolconfig = sm.getTool(page, "Financial Details", "unisa.payments");
		page = sm.getPage(site, "Parcel Tracking");
		toolconfig = sm.getTool(page, "Parcel Tracking", "unisa.trackandtrace");
		page = sm.getPage(site, "Study Fees Quotation");
		toolconfig = sm.getTool(page, "Study Fees Quotation", "unisa.studyquotation");

		sm.removePage(site, "Examination Timetable");

//		pageEdit = sm.getPage(siteEdit, "Examination Timetable");
//		toolconfig = sm.getTool(pageEdit, "Examination Timetable", "unisa.examtimetable");

		if (toolconfig != null) ;

		siteService.save(site);
	}

	public void createMyStudents() throws Exception {
		
		siteService = (SiteService) ComponentManager.get(SiteService.class);
		SiteManagement sm = new SiteManagement(output);

		Site site = sm.getSite("mystudents");
		site.setTitle("My Students");
		site.setDescription("Student Administration");
		site.setJoinable(false);
		site.setPublished(true);
		site.setShortDescription("Student Admin");
		site.setType("Administrative");
		site.setJoinerRole("access");

		SitePage page = null;
		ToolConfiguration toolconfig = null;

		page = sm.getPage(site, "Assignments");
		toolconfig = sm.getTool(page, "Assignments", "unisa.proxy");
		toolconfig.getPlacementConfig().setProperty("base", ServerConfigurationService.getString("assignments.base"));
		toolconfig.getPlacementConfig().setProperty("url", "/SACDL20/ASPs/StuAssTab.asp");
		toolconfig.getPlacementConfig().setProperty("parms", "system={system}&novellid={novellid}");
		toolconfig.getPlacementConfig().setProperty("cookieUserID", "LolStudno");

		page = sm.getPage(site, "Academic Record");
		toolconfig = sm.getTool(page, "Academic Record", "unisa.acadhistory");
		page = sm.getPage(site, "Examination Results");
		toolconfig = sm.getTool(page, "Examination Results", "unisa.results");

		page = sm.getPage(site, "File Manager");
		toolconfig = sm.getTool(page, "File Manager", "unisa.proxy");
		toolconfig.getPlacementConfig().setProperty("base", ServerConfigurationService.getString("filemanager.base"));
		toolconfig.getPlacementConfig().setProperty("url", "/unisa-filemanager/index.jsp");
		toolconfig.getPlacementConfig().setProperty("parms", "userid={novellid}");

		page = sm.getPage(site, "Financial Details");
		toolconfig = sm.getTool(page, "Financial Details", "unisa.payments");
		page = sm.getPage(site, "Mailing List");
		toolconfig = sm.getTool(page, "Mailing List", "unisa.bulkemail");
		page = sm.getPage(site, "Parcel Tracking");
		toolconfig = sm.getTool(page, "Parcel Tracking", "unisa.trackandtrace");
		page = sm.getPage(site, "Student List");
		toolconfig = sm.getTool(page, "Student List", "unisa.studentlist");

		page = sm.getPage(site, "Prescribed Books");
		toolconfig = sm.getTool(page, "Prescribed Books", "unisa.proxy");
		toolconfig.getPlacementConfig().setProperty("base", ServerConfigurationService.getString("staff.base"));
		toolconfig.getPlacementConfig().setProperty("url", "/prebooks/cgi-bin/booklist.cgi");
		toolconfig.getPlacementConfig().setProperty("parms", "novellid={novellid}");

		sm.removePage(site, "Examination Timetable");

//		pageEdit = sm.getPage(siteEdit, "Examination Timetable");
//		toolconfig = sm.getTool(pageEdit, "Examination Timetable", "unisa.examtimetable");

		if (toolconfig != null) ;

		siteService.save(site);
	}

	public void executeLocked(JobExecutionContext context)
			throws JobExecutionException {
		
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);

		log = LogFactory.getLog(this.getClass());
		outputStream = new ByteArrayOutputStream();
		output = new PrintWriter(outputStream);

		runcount++;

		log.info(runcount + ": Started.");

		try {

			User user = userDirectoryService.authenticate(
					ServerConfigurationService.getString("admin.user"),
					ServerConfigurationService.getString("admin.password"));
			if (user != null) {
				log.debug(runcount + ": Authenticated.");
				Session session = sessionManager.startSession();
				if (session == null) throw new JobExecutionException("No session");
				log.debug(runcount + ": Session started: "+session.getId());

				session.setUserEid(user.getEid());
				session.setUserId(user.getId());
				sessionManager.setCurrentSession(session);

				createMyAdmin();
				updateLock();
				createMyStudents();
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
