package za.ac.unisa.lms.tools.cronjobs.jobs;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.sakaiproject.authz.api.Role;
import org.sakaiproject.authz.api.RoleAlreadyDefinedException;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.entity.api.ResourceProperties;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SitePage;
import org.sakaiproject.site.api.ToolConfiguration;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.exception.IdUnusedException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import za.ac.unisa.lms.tools.cronjobs.dao.CourseImportDAO;
import za.ac.unisa.lms.tools.cronjobs.dao.SakaiDAO;
import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;
import za.ac.unisa.lms.tools.cronjobs.util.SiteManagement;
import za.ac.unisa.utils.CoursePeriod;
import za.ac.unisa.utils.CoursePeriodLookup;

public class CourseImportJob extends SingleClusterInstanceJob implements StatefulJob, InterruptableJob, OutputGeneratingJob {

	
	private SiteService siteService;
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private CoursePeriodLookup coursePeriodLookup;
	
	
	public static long runcount = 1L;
	private Log log = null;
	private boolean interrupt = false;
	private ByteArrayOutputStream outputStream;
	private PrintWriter output;

	
	public void executeLocked(JobExecutionContext context)
			throws JobExecutionException {
		
		siteService = (SiteService) ComponentManager.get(SiteService.class);
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
					ServerConfigurationService.getString("admin.password"));
			System.out.println("admin user "+ServerConfigurationService.getString("admin.user"));
			System.out.println("admin password "+ServerConfigurationService.getString("admin.user"));
			System.out.println("admin password "+user);
			if (user != null) {
				log.debug(runcount + ": Authenticated.");
				Session session = sessionManager.startSession();
				if (session == null) throw new JobExecutionException("No session");
				log.debug(runcount + ": Session started: "+session.getId());

				session.setUserEid(user.getId());
				session.setUserId(user.getId());
				sessionManager.setCurrentSession(session);

				CourseImportDAO dao = new CourseImportDAO();
				//commented to check the sakai_locks: This is not needed because on starting a 
				//job we are updating/inserting a lock to cronjob_lock not to sakai_lock- vijay
				
				/*SakaiDAO sakaiDAO = new SakaiDAO();
				sakaiDAO.removeStaleSakaiLocks();*/
				ArrayList yearList = CoursePeriodLookup.getYearList();
				
				for(int i = 0; i < yearList.size(); i++){
					Site siteTemplate = null;
					String year=yearList.get(i).toString();
					try {
					siteTemplate = siteService.getSite("!site.template.course."+year);
					}catch(IdUnusedException ie){
						siteTemplate = null;
						log.debug(runcount + ": site template not created for year : "+year);
					}
					//check the site template for the year. if the site template not created on myUnisa admin then Sites will not creates here
					if(null == siteTemplate) {
                		log.error("siteTemplate !site.template.course."+year+" does NOT exist (null). Cannot import courses for "+year);
                		output.println("siteTemplate !site.template.course."+year+" does NOT exist (null). Cannot import courses for "+year);
                	} else {
					//get the course list from the data base for the year
					List<?> courseListfromdao = dao.getCourses(Integer.parseInt(year));
					log.debug(runcount + ": Query for "+new Integer(year).toString()+" returned "+courseListfromdao.size()+" courses");
					
					Iterator<?> courselist = courseListfromdao.iterator();
					// updateLock();//this will update a lock in cronjob_lock with job Id
					while (courselist.hasNext()) {
					 ListOrderedMap course = (ListOrderedMap) courselist.next();
						 String siteName = (String) course.get("Course");
                         siteName += "-" + course.get("academicYear").toString().substring(2);
                         siteName += "-" + CoursePeriodLookup.getCourseTypeAsString(course.get("semesterPeriod").toString());
                        try {
                        	//check the newly creating site is already created, if the site not created before- in catch block the call the method to create the site
                            siteService.getSite(siteName);
							log.info("check sitename in siteservice "+siteName);
                            } catch (IdUnusedException iue) {
                            	
                            //createSiteFromXML(siteName, siteLayoutElement);
                        	if(null != siteTemplate) {
							    log.info("CourseImportJob: creating sitename "+siteName);
                        		createSiteFromTemplate(siteName, siteTemplate);
                        	} else {
                        		log.error("CourseImportJob: Cannot find siteTemplate: !site.template.course, SiteService.getSite() returned null");
                        	}
                        }
						if (interrupt) {
							throw new Exception("CourseImportJob: Execution interrupted.");
						}
					}
					siteTemplate = null;					
				} // end of else
				} //end of for loop 
			} else {
				throw new Exception("CourseImportJob: User not authenticated");
			}
		 } catch (Exception e) {
			log.error(runcount + ": Exception "+e.getClass().getName()+": "+e.getMessage());
			throw new JobExecutionException(e);
		}
		log.debug(runcount + ": Stopped. "+(runcount));
	}
	
	public void createSiteFromTemplate(String sitename, Site siteTemplate) throws Exception {
		siteService = (SiteService) ComponentManager.get(SiteService.class);
		Site newSite = siteService.addSite(sitename, siteTemplate);
		newSite.setDescription(sitename);
		newSite.setShortDescription(sitename);
		newSite.setType("course");
		newSite.setTitle(sitename);
		newSite.setProviderGroupId(sitename);
		Iterator<?> roleIter = siteTemplate.getRoles().iterator();
		Role nextRole = null;
		while(roleIter.hasNext()) {
			nextRole = (Role) roleIter.next();
			try {
				newSite.addRole(nextRole.getId(), nextRole);
			} catch(RoleAlreadyDefinedException rade) {
			}
		}
		newSite.setMaintainRole(siteTemplate.getMaintainRole());
		siteService.save(newSite);
		
		//code to update the term and term eid  
		try{
		Site courseSite = siteService.getSite(newSite.getId());
		coursePeriodLookup = (CoursePeriodLookup) ComponentManager.get(CoursePeriodLookup.class);		
		CoursePeriod coursePeriod = CoursePeriodLookup.getCoursePeriod(newSite.getId());
		int year = coursePeriod.getYear();
		String semester = newSite.getId().substring(11,13);
		String semesterLongAsString = coursePeriod.getSemesterType();
		ResourceProperties siteProperties = courseSite.getProperties();
		if (siteProperties.get("term") == null){
			log.debug("Adding term and term_eid for " + newSite.getId() +"term="+year+" "+semesterLongAsString+" term_eid="+year+semester );
			siteProperties.addProperty("term", year+" "+semesterLongAsString);
			siteProperties.addProperty("term_eid", year+semester);
			log.debug("Adding term and term_eid: success "+newSite.getId());				
		} else {
			log.debug("Removing term and term_eid");
			siteProperties.removeProperty("term");
			siteProperties.removeProperty("term_eid");
			log.debug("Adding term and term_eid for " + newSite.getId() +"term="+year+" "+semesterLongAsString+" term_eid="+year+semester );
			siteProperties.addProperty("term", year+" "+semesterLongAsString);
			siteProperties.addProperty("term_eid", year+semester);
			log.debug("Adding term and term_eid: success"+newSite.getId());		
		}
		siteService.save(courseSite);
		}catch(Exception ex){
			log.error("CourseImportJob: Adding term and term_eid failed "+ex);
		}
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
