package za.ac.unisa.lms.tools.cronjobs.jobs;

import java.io.File;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
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
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.exception.PermissionException;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserAlreadyDefinedException;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.user.api.UserEdit;
import org.sakaiproject.user.api.UserNotDefinedException;
import org.sakaiproject.authz.api.AuthzGroup;
import org.sakaiproject.authz.api.AuthzGroupService;
import org.sakaiproject.authz.api.Member;
import org.sakaiproject.authz.api.Role;
import org.sakaiproject.authz.api.Member;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import za.ac.unisa.lms.tools.cronjobs.dao.DropboxEventsUpdateDAO;
import za.ac.unisa.lms.tools.cronjobs.dao.PopulateUsersStudentSystemDAO;
import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;
import za.ac.unisa.utils.LecturerAccessLevel;
import za.ac.unisa.utils.NoStudyMaterialIssue;

public class PopulateMissingRecordsToSakaiJob extends SingleClusterInstanceJob implements StatefulJob, InterruptableJob, OutputGeneratingJob {
	
	private Log log = null;
	private UserDirectoryService userDirectoryService;
	private SiteService siteService;
	private SessionManager sessionManager;
	private AuthzGroupService authzGroupService;
	 String tutorMode = null;
	 String inactiveDate = null;
	 //String backup = "C:/Users/udcsweb/Desktop/backup"; //for local
	 
	 String backup = "/data/sakai/PopulateUsers/backup/";
	public void populateUsers() throws Exception{
		
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		siteService = (SiteService) ComponentManager.get(SiteService.class);
		authzGroupService = (AuthzGroupService) ComponentManager.get(AuthzGroupService.class.getName());
				
		User user = userDirectoryService.authenticate(ServerConfigurationService.getString("admin.user"),ServerConfigurationService.getString("admin.password") );
		PopulateUsersStudentSystemDAO populateUsersStudentSystemDAO = new PopulateUsersStudentSystemDAO();
		
		  Session session = sessionManager.getCurrentSession();
		  session.setUserEid("admin");
		  session.setUserId("admin");
		  
		  //File inPutFolder = new File("C:/Users/udcsweb/Desktop/missing"); //for local
		
		  File inPutFolder = new File("/data/sakai/PopulateUsers/in/");
		  log.info(this+" inPutFolder contains"+inPutFolder.length());
		        File[] listOfFiles = inPutFolder.listFiles();
		        for (File file : listOfFiles) {
		        if (file.isFile()) {
		        	if(file.getName().equalsIgnoreCase("STUSUN_STUDENT_missings.xml")){
		        		 log.info(this+" Processing STUSUN_STUDENT_missings ");
		        		updateMissingCourseSitesForStudents();		        		 
		        	}if(file.getName().equalsIgnoreCase("TUSTGR_STUDENT_missings.xml")){
		        		 log.info(this+" Processing TUSTGR_STUDENT_missings ");
		        		updateMissingTutorSitesForStudents();
		        	}if(file.getName().equalsIgnoreCase("USRSUN_CourseSites_missings.xml")){
		        		 log.info(this+" Processing USRSUN_CourseSites_missings ");
		        		updateMissingCourseSitesForStaff();
		        	}if(file.getName().equalsIgnoreCase("USRSUN_GroupSites_missings.xml")){
		        		 log.info(this+" Processing USRSUN_GroupSites_missings ");
		        		updateMissingTutorSitesForStaff();
		        	}if(file.getName().equalsIgnoreCase("Delete_missings.xml")){
		        		 log.info(this+" Processing Delete_missings ");
		        		deleteMiddingUsersFromSakai();
		        	}
		        }
		       }
			 		 
	   }
	  				
	
		  public void updateMissingCourseSitesForStudents()  throws Exception{
			
			// File inputFile = new File("C:/Users/udcsweb/Desktop/missing/STUSUN_STUDENT_missings.xml");
			  File inputFile = new File("/data/sakai/PopulateUsers/in/STUSUN_STUDENT_missings.xml");
			 
			 if(inputFile.exists()==true){
			 
			try{	
	      		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	  			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	  			Document doc = dBuilder.parse(inputFile);
	  			doc.getDocumentElement().normalize();
	  			
	  			NodeList nList = doc.getElementsByTagName("DATA_RECORD");	  			
	  			
	  			
	  			for (int temp = 0; temp < nList.getLength(); temp++){
	  				 Node nNode = nList.item(temp);
	  				try{	
	  				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	  					Element eElement = (Element) nNode;
	  		
	  					String userId =eElement.getElementsByTagName("FK_STUDENT_NR").item(0).getTextContent();
	  					String studyunit =eElement.getElementsByTagName("MK_STUDY_UNIT_CODE").item(0).getTextContent();					
	  					String semester =eElement.getElementsByTagName("SEMESTER_PERIOD").item(0).getTextContent();  	
	  					String academic_year =eElement.getElementsByTagName("FK_ACADEMIC_YEAR").item(0).getTextContent();  
	  					String sem = convertPeriod(Integer.parseInt(semester));
	  					String siteId = studyunit+"-"+academic_year.substring(2)+"-"+sem;	  					
	  				    String status = addUserToSite(siteId,userId,"Student");		
	  				 
	  				}
			 }catch(Exception e){					
				 e.printStackTrace();
				 continue;
			 }
	  		}  
            
			 }catch(Exception e){					
				 e.printStackTrace();
			 }
			
			
			 }
			 try{
					File dir = new File(backup);
			 	    boolean success = inputFile.renameTo(new File(dir, inputFile.getName()));
			 	    if (success) {
			 		  log.info(this+" File moved to backup:");
		             }
					 }catch(Exception e){					
						  log.error(this+" File move to backup Failed");
					 }
		}
		  
		  public void updateMissingTutorSitesForStudents()  throws Exception{
				
				// File inputFile = new File("C:/Users/udcsweb/Desktop/missing/TUSTGR_STUDENT_missings.xml");
			  File inputFile = new File("/data/sakai/PopulateUsers/in/TUSTGR_STUDENT_missings.xml");
				 if(inputFile.exists()==true){
				try{	
		      		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		  			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		  			Document doc = dBuilder.parse(inputFile);
		  			doc.getDocumentElement().normalize();
		  			
		  			NodeList nList = doc.getElementsByTagName("DATA_RECORD");	  			
		  			
		  		
		  			for (int temp = 0; temp < nList.getLength(); temp++){
		  				Node nNode = nList.item(temp);
		  				try{	
		  				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		  					Element eElement = (Element) nNode;
		  		
		  					String userId =eElement.getElementsByTagName("FK_STUDENT_NR").item(0).getTextContent();
		  					String studyunit =eElement.getElementsByTagName("MK_STUDY_UNIT_CODE").item(0).getTextContent();					
		  					String semester =eElement.getElementsByTagName("SEMESTER_PERIOD").item(0).getTextContent();  
		  					String academic_year =eElement.getElementsByTagName("MK_ACADEMIC_YEAR").item(0).getTextContent();  
		  					String tutor_mode =eElement.getElementsByTagName("TUTOR_MODE").item(0).getTextContent(); 		  					
		  					
		  					String sem = convertPeriod(Integer.parseInt(semester));

		  					String siteId = studyunit+"-"+academic_year.substring(2)+"-"+sem+"-"+tutor_mode;	
		  					
		  				  String status = addUserToSite(siteId,userId,"Student");		
		  				}
		  			}catch(Exception e){						
					 e.printStackTrace();
					 continue;
				 }
				}
		  			
				 }catch(Exception e){					
					 e.printStackTrace();
				 }
				
				 }
				 try{
						File dir = new File(backup);
				 	    boolean success = inputFile.renameTo(new File(dir, inputFile.getName()));
				 	    if (success) {
				 		  log.info(this+" File moved to backup:");
			             }
						 }catch(Exception e){					
							  log.error(this+" File move to backup Failed");
						 }
			}
		  
		  public void updateMissingCourseSitesForStaff()  throws Exception{
				
				// File inputFile = new File("C:/Users/udcsweb/Desktop/missing/USRSUN_CourseSites_missings.xml");
			    File inputFile = new File("/data/sakai/PopulateUsers/in/USRSUN_CourseSites_missings.xml");
				 if(inputFile.exists()==true){
				try{	
		      		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		  			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		  			Document doc = dBuilder.parse(inputFile);
		  			doc.getDocumentElement().normalize();
		  			
		  			NodeList nList = doc.getElementsByTagName("DATA_RECORD");	  			
		  			
		  				
		  			for (int temp = 0; temp < nList.getLength(); temp++){
		  				Node nNode = nList.item(temp);
		  				try{
		  				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		  					Element eElement = (Element) nNode;
		  		
		  					String novellUserId =eElement.getElementsByTagName("NOVELL_USER_ID").item(0).getTextContent();
		 					String studyunit =eElement.getElementsByTagName("MK_STUDY_UNIT_CODE").item(0).getTextContent();					
		 					String semester =eElement.getElementsByTagName("SEMESTER_PERIOD").item(0).getTextContent();
		 					String academic_year =eElement.getElementsByTagName("MK_ACADEMIC_YEAR").item(0).getTextContent();  
		  					
		  					String sem = convertPeriod(Integer.parseInt(semester));

		  					String siteId = studyunit+"-"+academic_year.substring(2)+"-"+sem;	  	
		  					
		  				  String status = addUserToSite(siteId,novellUserId.toLowerCase(),"Instructor");		
		  				}		  			
				 }catch(Exception e){
						
					 e.printStackTrace();
					 continue;
				 }
		  		} 
				 }catch(Exception e){					
					 e.printStackTrace();
				 }
				  
				 }
				 try{
						File dir = new File(backup);
				 	    boolean success = inputFile.renameTo(new File(dir, inputFile.getName()));
				 	    if (success) {
				 		  log.info(this+" File moved to backup:");
			             }
						 }catch(Exception e){					
							  log.error(this+" File move to backup Failed");
						 }
			}
		  
		  public void updateMissingTutorSitesForStaff()  throws Exception{
				
				 //File inputFile = new File("C:/Users/udcsweb/Desktop/missing/USRSUN_GroupSites_missings.xml");
			     File inputFile = new File("/data/sakai/PopulateUsers/in/USRSUN_GroupSites_missings.xml");
				 if(inputFile.exists()==true){
				try{	
		      		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		  			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		  			Document doc = dBuilder.parse(inputFile);
		  			doc.getDocumentElement().normalize();
		  			
		  			NodeList nList = doc.getElementsByTagName("DATA_RECORD");	  			
		  			
		  			
		  			for (int temp = 0; temp < nList.getLength(); temp++){
		  				Node nNode = nList.item(temp);
		  				try{	
		  				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		  					Element eElement = (Element) nNode;
		  		
		  					String userId =eElement.getElementsByTagName("NOVELL_USER_ID").item(0).getTextContent();
		  					String studyunit =eElement.getElementsByTagName("MK_STUDY_UNIT_CODE").item(0).getTextContent();					
		  					String semester =eElement.getElementsByTagName("SEMESTER_PERIOD").item(0).getTextContent();  
		  					String tutor_mode =eElement.getElementsByTagName("TUTOR_MODE").item(0).getTextContent(); 
		  					String academic_year =eElement.getElementsByTagName("MK_ACADEMIC_YEAR").item(0).getTextContent(); 
		  					String systemType =eElement.getElementsByTagName("SYSTEM_TYPE").item(0).getTextContent(); 		  					
		  					String sem = convertPeriod(Integer.parseInt(semester));
		  					String role ="";
		  					if(systemType.equalsIgnoreCase("L")){
		  						role = "Instructor";
		  					}else{
		  						role="Teaching Assistant";
		  					}
		  					

		  					String siteId = studyunit+"-"+academic_year.substring(2)+"-"+sem+"-"+tutor_mode;	
		  					
		  				  String status = addUserToSite(siteId,userId.toLowerCase(),role);		
		  				}		  		
				 }catch(Exception e){
						
					 e.printStackTrace();
					 continue;
				 }
			  			
		  		} 
		
				 }catch(Exception e){					
					 e.printStackTrace();
				 }
				
				 }
				 try{
						File dir = new File(backup);
				 	    boolean success = inputFile.renameTo(new File(dir, inputFile.getName()));
				 	    if (success) {
				 		  log.info(this+" File moved to backup:");
			             }
						 }catch(Exception e){					
							  log.error(this+" File move to backup Failed");
						 }

			}
		  
		  public void deleteMiddingUsersFromSakai()  throws Exception{
				
			  // File inputFile = new File("C:/Users/udcsweb/Desktop/missing/Delete_missings.xml");
			   File inputFile = new File("/data/sakai/PopulateUsers/in/Delete_missings.xml");
				 if(inputFile.exists()==true){
				try{	
		      		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		  			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		  			Document doc = dBuilder.parse(inputFile);
		  			doc.getDocumentElement().normalize();
		  			
		  			NodeList nList = doc.getElementsByTagName("DATA_RECORD");	  			
		  			
		  			
		  			for (int temp = 0; temp < nList.getLength(); temp++){
		  				Node nNode = nList.item(temp);
		  				try{	
		  				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		  					Element eElement = (Element) nNode;
		  		
		  					String eId =eElement.getElementsByTagName("EID").item(0).getTextContent();
		  					String studyunit =eElement.getElementsByTagName("SITE_ID").item(0).getTextContent();	
		  					if (eId.matches("[0-9]+")) {		  						
		  					}else{
		  						eId=eId.toLowerCase();
		  					}
	  					
		  				  String status = removeUserFromSite(eId,studyunit);	
		  				}		  		
				 }catch(Exception e){
						
					 e.printStackTrace();
					 continue;
				 }			  			
		  		} 
		  			
				 }catch(Exception e){					
					 e.printStackTrace();
				 }
				
				 }
				 try{
						File dir = new File(backup);
				 	    boolean success = inputFile.renameTo(new File(dir, inputFile.getName()));
				 	    if (success) {
				 		  log.info(this+" File moved to backup:");
			             }
						 }catch(Exception e){					
							  log.error(this+" File move to backup Failed");
						 }

			}
		  
	  		public String addUserToSite(String siteId, String eId,String sakaiRole) throws Exception {
	  					
	  			Site site=null;
				String userid =null;
			  
				try {	
					AuthzGroup authzgroup = authzGroupService.getAuthzGroup("/site/"+siteId);
					Role role = authzgroup.getRole(sakaiRole);
					if(role == null) {
						log.error(this+" addUserToSite: Adding student "+eId+" to site "+siteId+" failed because it does not contain role: "+sakaiRole);
						return "fail";
					}		
				    userid = userDirectoryService.getUserByEid(eId).getId();
					authzgroup.addMember(userid,sakaiRole,true,false);
					authzGroupService.save(authzgroup);	
					
					 if(authzgroup.hasRole(userid,sakaiRole)){
						 log.info(this+" addUserToSite: Added student "+eId+" to site "+siteId);
			        	  return "success";
			          }else{
			        	  return "fail";
			          }
				}catch (Exception e) {  
					log.error(this+" addUserToSite: Adding student "+eId+" to site "+siteId+" failed ");
				 	return "fail";
				}finally{
					site=null;
					userid=null;
				}			
	  			}
	
	  		 public String removeUserFromSite( String eId,String siteId) throws Exception{
	  			 
	  			Site site=null;
	  			String userid =null;

	  			try {
	  				AuthzGroup authzgroup = authzGroupService.getAuthzGroup("/site/"+siteId);
	  				userid = userDirectoryService.getUserByEid(eId).getId();
	  				authzgroup.removeMember(userid);
	  				authzGroupService.save(authzgroup);	
	  				log.info(this+" removeUserToSite: Removed student "+eId+" from site "+siteId);
	  				return "success";
	  			}catch (Exception e) {  
	  				log.error(this+" removeUserToSite: Removing student "+eId+" from site "+siteId+" failed and record moved to INTSAK_FAIL");
	  				return "fail";
	  			}finally{
	  				site=null;
	  				userid=null;
	  			}
	  		 }
  
	
	private String convertPeriod(int prd){
		switch(prd){
		case 0 : return "Y1";
		case 1 : return "S1";
		case 2 : return "S2";
		case 6 : return "Y2";
		default : return "";
		}
	}
	
			
	public String getOutput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void executeLocked(JobExecutionContext arg0)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		try {		
			log = LogFactory.getLog(this.getClass());
			populateUsers();		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
