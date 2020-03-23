package za.ac.unisa.lms.tools.cronjobs.jobs;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.quartz.UnableToInterruptJobException;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.exception.IdUnusedException;
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

import za.ac.unisa.lms.tools.cronjobs.dao.CourseDeleteDAO;
import za.ac.unisa.lms.tools.cronjobs.dao.CourseImportDAO;
import za.ac.unisa.lms.tools.cronjobs.dao.DeleteCourseUsrSunDAO;
import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;
import za.ac.unisa.utils.CoursePeriodLookup;

public class CourseDeleteJob extends SingleClusterInstanceJob implements OutputGeneratingJob, StatefulJob, InterruptableJob {
	
	private SessionManager sessionManager;
	private SiteService siteService;
	private UserDirectoryService userDirectoryService;

	public static long runcount = 1L;
	private Log log = null;
	private boolean interrupt = false;
	private ByteArrayOutputStream outputStream;
	private PrintWriter output;

	public void removeSite(String sitename) throws Exception {
		try {
			siteService.removeSite(siteService.getSite(sitename));
			log.info(runcount + ": "+sitename+" site removed.");
			output.println(sitename+" site removed.");
		} catch (IdUnusedException ide) {
			log.debug(runcount + ": Site doesn't exist. Not deleted: "+sitename);
		}
	}
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

				session.setUserEid(user.getId());
				session.setUserId(user.getId());
				sessionManager.setCurrentSession(session);

				CourseImportDAO dao = new CourseImportDAO();
				DeleteCourseUsrSunDAO userSunDAO = new DeleteCourseUsrSunDAO();
				CourseDeleteDAO cdao = new CourseDeleteDAO();
				

				InputStream is = this.getClass().getResourceAsStream("/site-delete.xml");
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = dbf.newDocumentBuilder();
				InputSource inputSource = new InputSource(is);
				Document doc = docBuilder.parse(inputSource);
				Element siteDeleteElement;
				String yearStr;
				int year;
				List<?> courses;
				Iterator<?> ci;
				ListOrderedMap course;
				String siteName;

				Element root = doc.getDocumentElement();
				NodeList nodes = root.getChildNodes();


				for (int i = 0; i < nodes.getLength(); i++) {
					if (nodes.item(i).getNodeType() != Node.ELEMENT_NODE) continue;
					siteDeleteElement = (Element) nodes.item(i);
					yearStr = getRequiredAttribute(siteDeleteElement, "year");
					year = Integer.parseInt(yearStr);
					courses = dao.getCourses(year);
					log.debug(runcount + ": Query for "+new Integer(year).toString()+" returned "+courses.size()+" courses");

					ci = courses.iterator();
					while (ci.hasNext()) {
						updateLock();
						course = (ListOrderedMap) ci.next();
						siteName = (String) course.get("FK_SUNCODE");
						siteName += "-" + course.get("MK_ACADEMIC_YEAR").toString().substring(2);
						siteName += "-" + CoursePeriodLookup.getCourseTypeAsString(course.get("SEMESTER_PERIOD").toString());
                        try {
                            removeSite(siteName);
                        } catch (IdUnusedException iue) {
                        		log.error("Cannot find site for removal: "+siteName);
                        }
						if (interrupt) {
							throw new Exception("Execution interrupted.");
						}
					}
					userSunDAO.DeleteCourses(year);
				}
				for (int i = 0; i < nodes.getLength(); i++) {
					if (nodes.item(i).getNodeType() != Node.ELEMENT_NODE) continue;
					siteDeleteElement = (Element) nodes.item(i);
					yearStr = getRequiredAttribute(siteDeleteElement, "year");
					String siteYear = "-"+yearStr.toString().substring(2)+"-";
					courses = cdao.getSakaiSites(siteYear);
					log.debug(runcount + ": Query for "+yearStr+" returned "+courses.size()+" courses");

					ci = courses.iterator();
					while (ci.hasNext()) {
						updateLock();
						course = (ListOrderedMap) ci.next();
						siteName = (String) course.get("site_id");
						try {
							removeSite(siteName);
						} catch (IdUnusedException iue) {
							log.error("Cannot find site for removal: "+siteName);
						}
						if (interrupt) {
							throw new Exception("Execution interrupted.");
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
