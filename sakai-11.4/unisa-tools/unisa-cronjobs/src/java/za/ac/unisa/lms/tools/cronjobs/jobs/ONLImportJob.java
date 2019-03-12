package za.ac.unisa.lms.tools.cronjobs.jobs;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.mail.internet.AddressException;
import javax.xml.rpc.ServiceException;

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
import org.sakaiproject.content.api.ContentHostingService;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.entity.api.EntityProducer;
import org.sakaiproject.entity.api.EntityTransferrer;
import org.sakaiproject.entity.api.EntityTransferrerRefMigrator;
import org.sakaiproject.entity.api.ResourceProperties;
import org.sakaiproject.entity.cover.EntityManager;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SitePage;
import org.sakaiproject.site.api.ToolConfiguration;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.util.ArrayUtil;


import za.ac.unisa.lms.tools.cronjobs.dao.ONLImportDAO;
import za.ac.unisa.lms.tools.cronjobs.dao.SakaiDAO;
import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;
import za.ac.unisa.lms.tools.cronjobs.util.SiteManagement;
import za.ac.unisa.unisa_axis.ExtractGradebookResultsBatchWebService_jws.ExtractGradebookResultsBatchWebServiceServiceLocator;
import za.ac.unisa.unisa_axis.UnipooleMeleteMappingWebService_jws.UnipooleMeleteMappingWebServiceServiceLocator;
import za.ac.unisa.unisa_axis.UnipooleMeleteMappingWebService_jws.UnipooleMeleteMappingWebService_PortType;
import za.ac.unisa.utils.CoursePeriod;
import za.ac.unisa.utils.CoursePeriodLookup;

public class ONLImportJob extends SingleClusterInstanceJob implements StatefulJob, InterruptableJob, OutputGeneratingJob {

	
	private SiteService siteService;
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private CoursePeriodLookup coursePeriodLookup;
	private ContentHostingService m_contentHostingService = (ContentHostingService) ComponentManager.get("org.sakaiproject.content.api.ContentHostingService");
	private EmailService emailService;
	
	
	public static long runcount = 1L;
	private Log log = null;
	private boolean interrupt = false;
	private ByteArrayOutputStream outputStream;
	private PrintWriter output;
	private int count = 0;

	public void createSiteFromonlGroupTemplate(String sitename, Site siteTemplate) throws Exception {
		//System.out.println("From onlgroup Sites");
		siteService = (SiteService) ComponentManager.get(SiteService.class);
		Site newSite = siteService.addSite(sitename, siteTemplate);
		newSite.setDescription(sitename);
		newSite.setShortDescription(sitename);
		newSite.setType("onlgroup");
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
		
		//code for adding term and term_eid to the site 
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
				
		} else {
			log.debug("Removing term and term_eid");
			siteProperties.removeProperty("term");
			siteProperties.removeProperty("term_eid");
			log.debug("Adding term and term_eid for " + newSite.getId() +"term="+year+" "+semesterLongAsString+" term_eid="+year+semester );
			siteProperties.addProperty("term", year+" "+semesterLongAsString);
			siteProperties.addProperty("term_eid", year+semester);
		}
		log.debug("ONLImportJob: createSiteFromonlGroupTemplate siteService.save: value"+newSite);
		siteService.save(courseSite);
		  }catch(Exception ex){
				log.error("ONLImportJob: Adding term and term_eid failed "+ex);
		}
	}

	public void createSiteFromGroupTemplate(String sitename, Site siteTemplate1) throws Exception {
		//System.out.println("From group Sites");
		siteService = (SiteService) ComponentManager.get(SiteService.class);
		Site newSite = siteService.addSite(sitename, siteTemplate1);
		newSite.setDescription(sitename);
		newSite.setShortDescription(sitename);
		newSite.setType("group");
		newSite.setTitle(sitename);
		newSite.setProviderGroupId(sitename);
		Iterator<?> roleIter = siteTemplate1.getRoles().iterator();
		Role nextRole = null;
		while(roleIter.hasNext()) {
			nextRole = (Role) roleIter.next();
			try {
				newSite.addRole(nextRole.getId(), nextRole);
			} catch(RoleAlreadyDefinedException rade) {
			}
		}
		newSite.setMaintainRole(siteTemplate1.getMaintainRole());
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
				
		} else {
			log.debug("Removing term and term_eid");
			siteProperties.removeProperty("term");
			siteProperties.removeProperty("term_eid");
			log.debug("Adding term and term_eid for " + newSite.getId() +"term="+year+" "+semesterLongAsString+" term_eid="+year+semester );
			siteProperties.addProperty("term", year+" "+semesterLongAsString);
			siteProperties.addProperty("term_eid", year+semester);
		}
		log.debug("ONLImportJob:createSiteFromGroupTemplate siteService.save: value"+newSite);
		siteService.save(courseSite);
		 }catch(Exception ex){
				log.error("ONLImportJob:  Adding term and term_eid failed "+ex);
		}
	}


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
			
			if (user != null) {
				log.debug(runcount + ": Authenticated.");
				Session session = sessionManager.startSession();
				if (session == null) throw new JobExecutionException("No session");
				log.debug(runcount + ": Session started: "+session.getId());

				session.setUserEid(user.getId());
				session.setUserId(user.getId());
				sessionManager.setCurrentSession(session);

	
				ONLImportDAO onlImportDAO = new ONLImportDAO();
				 /* Commented to check the sakai_locks: This is not needed because on starting a 
				job we are updating/inserting a lock to cronjob_lock not to sakai_lock- vijay
			    
			    SakaiDAO sdao = new SakaiDAO();
				sdao.removeStaleSakaiLocks();  */
				ArrayList yearList = CoursePeriodLookup.getYearList();
				
				for(int i = 0; i < yearList.size(); i++){
					Site onlGroupSiteTemplate = null;
					Site groupSiteTemplate = null;
					
					String year=yearList.get(i).toString();
					
					try {
					 onlGroupSiteTemplate = siteService.getSite("!site.template.onlgroup."+year);
					 }catch(IdUnusedException ie){
						onlGroupSiteTemplate = null;
						log.debug(runcount + ": site.template.onlgroup not created for year : "+year);
					}
					
					try {
						groupSiteTemplate = siteService.getSite("!site.template.group."+year);
						 }catch(IdUnusedException ie){
							 groupSiteTemplate = null;
							log.debug(runcount + ": site.template.group not created for year : "+year);
					}
					
					if(null == onlGroupSiteTemplate || null == groupSiteTemplate) {
                		log.error("siteTemplate !site.template.onlgroup."+year+"  or !site.template.group."+year+" does" +
                				" NOT exist (null). Cannot import Online Courses for "+year);
                		output.println("siteTemplate !site.template.onlgroup."+year+" or !site.template.group."+year+" " +
                				"does NOT exist (null). Cannot import Online courses for "+year);
                	}else{
					
					List<?> courses = onlImportDAO.getCourses(Integer.parseInt(year));
					log.debug(runcount + ": Query for "+new Integer(year).toString()+" returned "+courses.size()+" courses");

					Iterator<?> courseList = courses.iterator();
				  
					  // updateLock();//this method will update a lock in cronjob_lock with job Id
				       while (courseList.hasNext()) {
				    	  
						ListOrderedMap course = (ListOrderedMap) courseList.next();
						 String siteName = (String) course.get("Course");
                         siteName += "-" + course.get("academicYear").toString().substring(2);
                         siteName += "-" + CoursePeriodLookup.getCourseTypeAsString(course.get("semesterPeriod").toString());
                         siteName += "-" + course.get("tutormode");
                         String[] tutor = siteName.split("-");
                         	char tutorprefix = tutor[3].charAt(tutor[3].length()-1);
        	       			if(tutorprefix == 'T' || tutorprefix == 't'){
                        		try {
                                    //check the site already created
                        			siteService.getSite(siteName);
                        			log.info("ONLImportJob: ONL Group site: check sitename in siteservice "+siteName);
                                     } catch (IdUnusedException iue) {
                                       if(null != onlGroupSiteTemplate) {
                                    	   log.info("ONLImportJob: sitename "+siteName);
                                    	 createSiteFromonlGroupTemplate(siteName, onlGroupSiteTemplate);
                                    	 //this method will copies the data from master site to newly created group sites
                                    	 String toSite=siteName;
                                    	 String masterSite = (String) course.get("Course")+"-"+course.get("academicYear").toString().substring(2)+"-"+"Master";                                    	
                                    	importToolIntoSite(masterSite,toSite);
                                    	 
                                	} else {
                                		log.error("ONLImportJob: Cannot find siteTemplate: !site.template.onlgroup, SiteService.getSite() returned null");
                                	}
                                }
        						if (interrupt) {
        							throw new Exception("Execution interrupted.");
        						}
        					 
                        	} else {
                        		//create the normal group sites
                        		try {
                        			 siteService.getSite(siteName);
                        			 log.info("ONLImportJob: Group site: check sitename in siteservice "+siteName);
                                     } catch (IdUnusedException iue) {
                                       if(null != groupSiteTemplate) {
        								createSiteFromGroupTemplate(siteName, groupSiteTemplate);
                                	} else {
                                		log.error("Cannot find siteTemplate: !site.template.group, SiteService.getSite() returned null");
                                	}
                                }
        						if (interrupt) {
        							throw new Exception("Execution interrupted.");
        						}
        					 
                        	}
                         }
				   onlGroupSiteTemplate = null;
				   groupSiteTemplate = null;
                	}
					}

			} else {
				 throw new Exception("User not authenticated");
			}

		} catch (Exception exp) {
			 log.error(runcount + ":: Exception "+exp.getClass().getName()+": "+exp.getMessage());
			exp.printStackTrace();
			//throw new JobExecutionException(exp);
		}

		log.debug(runcount + ": Stopped. "+(runcount));

	}
    // import tool content into site
    private void importToolIntoSite(String masterSiteId,String toSiteId) {
          String job="cronjob";
          List<String> toolIds = new ArrayList<String>();
          toolIds.add("unisa.welcome");
          toolIds.add("sakai.gradebook.tool");
          toolIds.add("sakai.yaft");
          toolIds.add("sakai.melete");         
          toolIds.add("sakai.resources");
          
          System.out.println("*************** importToolIntoSite("+masterSiteId+", "+toSiteId+")");
		try {
			// import resources first
			boolean resourcesImported = false;
			log.debug("importToolIntoSite toolIds.size(): "+toolIds.size()+" resourcesImported:"+resourcesImported);
			for (int i = 0; i < toolIds.size() && !resourcesImported; i++) {

				String toolId = (String) toolIds.get(i);
				log.debug("importToolIntoSite toolId: "+toolId);

				if (toolId.equalsIgnoreCase("sakai.resources")) {
					// List importSiteIds = (List) importTools.get(toolId);

					log.info("importToolIntoSite: Import resources");
					String fromSiteCollectionId = m_contentHostingService
							.getSiteCollection(masterSiteId);
					String toSiteCollectionId = m_contentHostingService
							.getSiteCollection(toSiteId);

					transferCopyEntities(toolId, fromSiteCollectionId,
							toSiteCollectionId);
					resourcesImported = true;
					// }
				}
			}
			// import other tools then
			for (int i = 0; i < toolIds.size(); i++) {
				String toolId = (String) toolIds.get(i);
				log.debug("importToolIntoSite toolId: "+toolId);
				if (!toolId.equalsIgnoreCase("sakai.resources")) {
					log.info("importToolIntoSite: Import other than resources");
					transferCopyEntities(toolId, masterSiteId, toSiteId);

				}
			}
		} catch (Exception ex) {
			log.error("Auto populate failed: importToolIntoSite");
		}
		
		// Do the Unipoole mapping (SYZELLE)
		try {
//			meleteContentMappingDAO.meleteDataToBeMapped("sakai.melete",masterSiteId,toSiteId);
			
			StringBuffer webServiceUrl = new StringBuffer();
			webServiceUrl.append(ServerConfigurationService.getString("serverUrl"));
			if (webServiceUrl.equals("http://localhost:8080")) {
				webServiceUrl = new StringBuffer();
				webServiceUrl.append("https://mydev.unisa.ac.za");
			}
			//url = "http://localhost:8082";
			//StringBuffer url = new StringBuffer();
			//url.append(serverUrl);
			webServiceUrl.append("/unisa-axis/UnipooleMeleteMappingWebService.jws");
			UnipooleMeleteMappingWebService_PortType events = null;
			
			try {
				URL url = new URL(webServiceUrl.toString());
				events = new UnipooleMeleteMappingWebServiceServiceLocator().getUnipooleMeleteMappingWebService(url);	
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (ServiceException e3) {
				e3.printStackTrace();
			} catch (Exception e2) {
				e2.printStackTrace();
			} // end try
	
			
			try {
	
				events.meleteDataToBeMapped("sakai.melete", masterSiteId, toSiteId);
	
			}catch (Exception vE) {
				// TODO: handle exception
				vE.printStackTrace();
			} // end try getGradebookMarks
			
			
		} catch (Exception e) {
			System.out.println("######### meleteContentMappingDAO.meleteDataToBeMapped Exception: "+e);
		}

    } // importToolIntoSite
    
	protected void transferCopyEntities(String toolId, String fromContext,
			String toContext) {
		// TODO: used to offer to resources first - why? still needed? -ggolden
		log.info(" IN transferCopyEntities");	
		
		Map transversalMap = new HashMap();
	//	org.sakaiproject.util.Placement placement = ToolManager. setResourceBundle(toolId, filename)();
		//placement.setTool(toolId, tool)
		
		// offer to all EntityProducers
		for (Iterator i = EntityManager.getEntityProducers().iterator(); i
				.hasNext();) {
			EntityProducer ep = (EntityProducer) i.next();
			if (ep instanceof EntityTransferrer) {
				try {
					EntityTransferrer et = (EntityTransferrer) ep;

					
					// if this producer claims this tool id
					if (ArrayUtil.contains(et.myToolIds(), toolId)) {
						if(ep instanceof EntityTransferrerRefMigrator){
							EntityTransferrerRefMigrator etMp = (EntityTransferrerRefMigrator) ep;
							Map<String,String> entityMap = etMp.transferCopyEntitiesRefMigrator(fromContext, toContext,
									new Vector());
							if(entityMap != null){							 
								transversalMap.putAll(entityMap);
							}
						}else{
							et.transferCopyEntities(fromContext, toContext,	new Vector());
						}
					}
				} catch (Throwable t) {
					log.error("transferCopyEntities failed in ONLImportJOb : transferCopyEntities");
				}
			}
		}
		log.info("TRAVERSALMAP : " + transversalMap.toString());		
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
