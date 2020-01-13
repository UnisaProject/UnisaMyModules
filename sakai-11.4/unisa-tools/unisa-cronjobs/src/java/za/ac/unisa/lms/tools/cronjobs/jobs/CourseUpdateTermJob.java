package za.ac.unisa.lms.tools.cronjobs.jobs;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.quartz.UnableToInterruptJobException;
import org.sakaiproject.authz.api.Role;
import org.sakaiproject.authz.api.RoleAlreadyDefinedException;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.entity.api.ResourceProperties;
import org.sakaiproject.exception.InUseException;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SitePage;
import org.sakaiproject.site.api.ToolConfiguration;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import za.ac.unisa.lms.tools.cronjobs.dao.SakaiDAO;
import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;
import za.ac.unisa.lms.tools.cronjobs.util.SiteManagement;
import za.ac.unisa.utils.CoursePeriod;
import za.ac.unisa.utils.CoursePeriodLookup;

public class CourseUpdateTermJob extends SingleClusterInstanceJob implements StatefulJob, InterruptableJob, OutputGeneratingJob {

	private SiteService siteService;
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private CoursePeriodLookup coursePeriodLookup;
	
	public static long runcount = 1L;
	private Log log = null;
	private boolean interrupt = false;
	private ByteArrayOutputStream outputStream;
	private PrintWriter output;

	private String getRequiredAttribute(Element element, String attribute) throws Exception {
		String value = element.getAttribute(attribute);
		if (value == null) {
			throw new Exception("Attribute \""+attribute+"\" of element \""+element.getTagName()+"\" is required");
		}
		value = value.trim();
		if (value.equals("")) {
			throw new Exception("Attribute \""+attribute+"\" of element \""+element.getTagName()+"\" must have a non-whitespace value");
		}

		return value;
	}

	public void updateSiteTerm(String courseSiteRef) throws Exception {
		
		siteService = (SiteService) ComponentManager.get(SiteService.class);
		// get the target courseSite
		Site courseSite = siteService.getSite(courseSiteRef);
		
		coursePeriodLookup = (CoursePeriodLookup) ComponentManager.get(CoursePeriodLookup.class);
		
		CoursePeriod coursePeriod = CoursePeriodLookup.getCoursePeriod(courseSiteRef);
		
		int year = coursePeriod.getYear();
		String semester = courseSiteRef.substring(11,13);
		String semesterLongAsString = coursePeriod.getSemesterType();
		ResourceProperties siteProperties = courseSite.getProperties();
		if (siteProperties.get("term") == null){
			log.info("Adding term and term_eid for " + courseSiteRef +"term="+year+" "+semesterLongAsString+" term_eid="+year+semester );
			siteProperties.addProperty("term", year+" "+semesterLongAsString);
			siteProperties.addProperty("term_eid", year+semester);
				
		} else {
			log.debug("Removing term and term_eid");
			siteProperties.removeProperty("term");
			siteProperties.removeProperty("term_eid");
			log.info("Adding term and term_eid for " + courseSiteRef +"term="+year+" "+semesterLongAsString+" term_eid="+year+semester );
			siteProperties.addProperty("term", year+" "+semesterLongAsString);
			siteProperties.addProperty("term_eid", year+semester);
		}
		
		siteService.save(courseSite);
	}




	public void executeLocked(JobExecutionContext context)
			throws JobExecutionException {
		
		siteService = (SiteService) ComponentManager.get(SiteService.class);
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

				session.setUserEid(user.getId());
				session.setUserId(user.getId());
				sessionManager.setCurrentSession(session);

//				CourseImportDAO dao = new CourseImportDAO();
				SakaiDAO sdao = new SakaiDAO();
				sdao.removeStaleSakaiLocks();

				InputStream is = this.getClass().getResourceAsStream("/courseTermUpdate.xml");
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = dbf.newDocumentBuilder();
				InputSource inputSource = new InputSource(is);
				Document doc = docBuilder.parse(inputSource);

				Element root = doc.getDocumentElement();
				NodeList nodes = root.getChildNodes();


 				for (int i = 0; i < nodes.getLength(); i++) {
					if (nodes.item(i).getNodeType() != Node.ELEMENT_NODE) continue;
					Element siteLayoutElement = (Element) nodes.item(i);
					int year = Integer.parseInt(getRequiredAttribute(siteLayoutElement, "year"));
					String yearStr = new Integer(year).toString();
					List<?> courses = siteService.getSites(org.sakaiproject.site.api.SiteService.SelectionType.ANY, "course", "-"+yearStr.substring(2)+"-", null, org.sakaiproject.site.api.SiteService.SortType.TITLE_ASC, null);
					log.debug(runcount + ": Query for "+yearStr+" returned "+courses.size()+" courses");

					Iterator<?> ci = courses.iterator();
					while (ci.hasNext()) {
						updateLock();
						Site course = (Site) ci.next();
						String siteName = (String) course.getId();
					try {
							//updateSite(siteName, siteLayoutElement);
							updateSiteTerm(course.getId());
							log.debug(this+": Performed update of "+siteName);

						} catch (InUseException iue) {
							log.error(siteName+" is in use - cannot CourseUpdateTerm(Job)"+iue.getMessage());
						} catch (Exception e) {
							log.error(siteName+" - cannot CourseUpdateTerm(Job)"+e.getMessage());
						}
						if (interrupt) {
							throw new Exception("CourseUpdateTerm(Job) Execution interrupted.");
						}
					}
					
				}

			} else {
				throw new Exception("User not authenticated");
			}

		} catch (Exception e) {
			log.error(runcount + ": Exception "+e.getClass().getName()+": "+e.getMessage());
			throw new JobExecutionException(e);
		}

		log.debug(runcount + ": Stopped. "+(runcount));

	}

	public void interrupt() throws UnableToInterruptJobException {
		super.interrupt();
		interrupt = true;
	}

	public String getOutput() {
		if (output != null) {
			output.flush();
			return outputStream.toString();
		}
		return null;
	}

}
