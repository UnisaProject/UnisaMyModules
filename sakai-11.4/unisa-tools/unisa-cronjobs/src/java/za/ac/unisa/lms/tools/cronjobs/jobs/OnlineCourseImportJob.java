package za.ac.unisa.lms.tools.cronjobs.jobs;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;


import za.ac.unisa.lms.tools.cronjobs.dao.OnlineCourseImportDAO;
import za.ac.unisa.lms.tools.cronjobs.dao.SakaiDAO;
import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;
import za.ac.unisa.utils.CoursePeriod;
import za.ac.unisa.utils.CoursePeriodLookup;

public class OnlineCourseImportJob extends SingleClusterInstanceJob implements StatefulJob ,OutputGeneratingJob,InterruptableJob {

	private SiteService siteService;
	private SessionManager  sessionManager;
	private UserDirectoryService userDirectoryService;
	private CoursePeriodLookup coursePeriodLookup;
	public static long runcount = 1L;
	private Log log = null;
	private boolean interrupt = false;
	private ByteArrayOutputStream outputStream;
	private PrintWriter output;

	private void createSiteFromTemplate(String sitename, Site siteTemplate) throws Exception {
		siteService = (SiteService) ComponentManager.get(SiteService.class);
		Site newSite = siteService.addSite(sitename, siteTemplate);
		newSite.setDescription(sitename);
		newSite.setShortDescription(sitename);
		newSite.setType("onlcourse");
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
		
		//code to update the term and term_eid to the site
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
			log.debug("Adding term and term_eid: success"+newSite.getId());	
				
		} else {
			log.debug("Removing term and term_eid");
			siteProperties.removeProperty("term");
			siteProperties.removeProperty("term_eid");
			log.debug("Adding term and term_eid for " + newSite.getId() +"term="+year+" "+semesterLongAsString+" term_eid="+year+semester );
			siteProperties.addProperty("term", year+" "+semesterLongAsString);
			siteProperties.addProperty("term_eid", year+semester);
			log.debug("Adding term and term_eid: success "+newSite.getId());	
		}
		log.debug("siteService.save: value "+newSite);
		siteService.save(courseSite);
		}catch(Exception ex){
				log.error("OnlineCourseImportJob: Adding term and term_eid failed "+ex);
		}
	}

	private void onlineCourseSites() throws JobExecutionException{
	
		siteService = (SiteService) ComponentManager.get(SiteService.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);

		log = LogFactory.getLog(this.getClass());
		outputStream = new ByteArrayOutputStream();
		output = new PrintWriter(outputStream);

		runcount++;

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

				OnlineCourseImportDAO onlineCourseImportDAO = new OnlineCourseImportDAO();
				
				 /* Commented to check the sakai_locks: This is not needed because on starting a 
				job we are updating/inserting a lock to cronjob_lock not to sakai_lock- vijay
			    
			    SakaiDAO sdao = new SakaiDAO();
				sdao.removeStaleSakaiLocks();  */
				ArrayList yearList = CoursePeriodLookup.getYearList();

				for(int i = 0; i < yearList.size(); i++){
					Site siteTemplate = null;
					String year=yearList.get(i).toString();
					try {
					siteTemplate = siteService.getSite("!site.template.onlcourse."+year);
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
					List<?> courseListfromdao = onlineCourseImportDAO.getOnlineCourses(Integer.parseInt(year));
					log.debug(runcount + ": Query for "+new Integer(year).toString()+" returned "+courseListfromdao.size()+" courses");
					
					Iterator<?> courselist = courseListfromdao.iterator();
					//updateLock();//this will update a lock in cronjob_lock with job Id
					while (courselist.hasNext()) {
					     ListOrderedMap course = (ListOrderedMap) courselist.next();
						 String siteName = (String) course.get("Course");
                         siteName += "-" + course.get("academicYear").toString().substring(2);
                         siteName += "-" + CoursePeriodLookup.getCourseTypeAsString(course.get("semesterPeriod").toString());
                        try {
                        	//check the newly creating site is already created, if the site not created before- in catch block the call the method to create the site
                            siteService.getSite(siteName);
							log.info("OnlineCourseImportJob: check sitename in siteservice "+siteName);
                            } catch (IdUnusedException iue) {
                            	
                        	if(null != siteTemplate) {
							    log.info("OnlineCourseImportJob: creating sitename "+siteName);
                        		createSiteFromTemplate(siteName, siteTemplate);
                        	} else {
                        		log.error("OnlineCourseImportJob: Cannot find siteTemplate: !site.template.course, SiteService.getSite() returned null");
                        	}
                        }
						if (interrupt) {
							throw new Exception("OnlineCourseImportJob: Execution interrupted.");
						}
					}
					siteTemplate = null;					
				} // end of else
				} //end of for loop 
			} else {
				throw new Exception("OnlineCourseImportJob: User not authenticated");
			}
		 } catch (Exception e) {
			log.error(runcount + ": Exception "+e.getClass().getName()+": "+e.getMessage());
			throw new JobExecutionException(e);
		}
		log.debug(runcount + ": Stopped. "+(runcount));
	 }
				

	@Override
	public void interrupt() throws UnableToInterruptJobException {
		super.interrupt();
		interrupt = true;
	}

	@Override
	public String getOutput() {
		if (output != null) {
			output.flush();
			return outputStream.toString();
		}
		return null;
	}
	@Override
	public void executeLocked(JobExecutionContext arg0)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		try {
		onlineCourseSites();
		}
		catch (Exception e) {
			// TODO: handle exception
		    log.info("something went wrong !!!");
			e.printStackTrace();
		}
	}

}
