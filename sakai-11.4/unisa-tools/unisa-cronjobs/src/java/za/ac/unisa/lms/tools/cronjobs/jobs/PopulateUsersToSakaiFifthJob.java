package za.ac.unisa.lms.tools.cronjobs.jobs;

import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

import za.ac.unisa.lms.tools.cronjobs.dao.DropboxEventsUpdateDAO;
import za.ac.unisa.lms.tools.cronjobs.dao.PopulateUsersStudentSystemDAO;
import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;
import za.ac.unisa.utils.LecturerAccessLevel;
import za.ac.unisa.utils.NoStudyMaterialIssue;

public class PopulateUsersToSakaiFifthJob extends SingleClusterInstanceJob implements StatefulJob, InterruptableJob, OutputGeneratingJob {
	
	private Log log = null;
	private UserDirectoryService userDirectoryService;
	private SiteService siteService;
	private SessionManager sessionManager;
	private AuthzGroupService authzGroupService;
	
	 String tutorMode = null;
	 String inactiveDate = null;
	
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
		  List<?> allStudents=null;
		  List<?> studentCourseList=null;
		  List<?> studentPersDetails=null;
		  String userid=null;
		  List<?> userCourseList=null;
		  
		  
          //populate students from LSSINT 
		  
		  try{	
			  
			  userCourseList = populateUsersStudentSystemDAO.getStudentCourseForFifthJob();	//stusun like 6
			  Iterator<?> courseList = userCourseList.iterator();
			  
			  while (courseList.hasNext()) {					 
								  
				 ListOrderedMap courseDetails = (ListOrderedMap) courseList.next();
				 String userId =  courseDetails.get("USER_ID").toString();
				 String userRole = courseDetails.get("USER_ROLE").toString();
				 String keyData = courseDetails.get("KEY_DATA").toString();
				 String tableName = courseDetails.get("TABLE_NAME").toString();
				 String action = courseDetails.get("ACTION").toString();
				 String fromValue = courseDetails.get("FROM_VALUE").toString();
				 String toValue = courseDetails.get("TO_VALUE").toString();
				 String modifiedFlag = courseDetails.get("MODIFIED_FLAG").toString();
				 String createdDate = courseDetails.get("CREATED_ON").toString();				 
				 
				 try{
					 String siteId=null;
					 
					 String[] courseData = keyData.split("\\|");
					 if (courseData[0].length() > 3){
						 siteId = courseData[0]+"-"+courseData[1].substring(2, 4)+"-"+convertPeriod(Integer.parseInt(courseData[2]));						 
						 if (courseData.length > 3){
								if (!"".equalsIgnoreCase(courseData[3])  || !"".equalsIgnoreCase(courseData[4])){
									siteId = siteId + "-"+courseData[4]+courseData[3].substring(0,1);
								}								
					        }
			    	 }else{
			    		 siteId = courseData[0]+courseData[1]+courseData[2];
			    	 }
		  
					 if(action.equalsIgnoreCase("DELETE")){
						 String status = removeUserToSite(siteId,userId,userRole,keyData,tableName,action,fromValue,toValue,createdDate);
						 populateUsersStudentSystemDAO.deleteUserFromInterfaceTable(status,userId,userRole,keyData,tableName,action,fromValue,toValue,modifiedFlag,createdDate);    
							
					 }else if(action.equalsIgnoreCase("INSERT")){
							 //check user from sakai
							checkUserInSakai(userId);
							 //add user to site
						  String status = addUserToSite(siteId,userId,"Student",userRole,keyData,tableName,action,fromValue,toValue,createdDate);		
						  populateUsersStudentSystemDAO.deleteUserFromInterfaceTable(status,userId,userRole,keyData,tableName,action,fromValue,toValue,modifiedFlag,createdDate);  
						 
						 }	
				 }catch(Exception e){
						
					 e.printStackTrace();
					 continue;
				 }
			  }
				 
		  }catch(Exception e)
		  {
			  e.printStackTrace();
		  }
		  
		  
	}
	
	  public String addUserToSite(String siteId, String eId, String sakaiRole,String userRole,String keyData, String tableName, String action,String fromValue, String toValue,String createdDate) throws Exception{
			 
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
		        	  log.info(this+" addUserToSite: Adding student "+eId+" to site "+siteId+" failed and moved to SAKINT_FAIL");
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
	
   public String removeUserToSite(String siteId, String eId,String userRole, String keyData,String tableName,String action,String fromValue,String toValue,String createdDate) throws Exception{
		
	    Site site=null;
		String userid =null;

		try {
			AuthzGroup authzgroup = authzGroupService.getAuthzGroup("/site/"+siteId);
			userid = userDirectoryService.getUserByEid(eId).getId();
			authzgroup.removeMember(userid);
			authzGroupService.save(authzgroup);		
			
			if(authzgroup.hasRole(userid,userRole)){
				log.info(this+" removeUserToSite: Removing student "+eId+" from site "+siteId+" failed and moved to INTSAK_FAIL");
				return "fail";
		        }else{
		        	 log.info(this+" removeUserToSite: Removed student "+eId+" from site "+siteId);
		        return "success";
		        }
		}catch (Exception e) {  
			log.error(this+" removeUserToSite: Removing student "+eId+" from site "+siteId+" failed and record moved to INTSAK_FAIL");
			return "fail";
		}finally{
			site=null;
			userid=null;
		}
	}

	
     public void checkUserInSakai(String userId){		 
	     //check the user in sakai. if not exists add the user before linking to site
		  userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
	       try {
			User userTocheck = null;
			userTocheck = userDirectoryService.getUserByEidFromDb(userId.toLowerCase());
			List<?> persDetails=null;
			
			if (userTocheck == null) {
				String userName="";
				String userSurName="";
				String userEmail="";

				PopulateUsersStudentSystemDAO populateUsersStudentSystemDAO = new PopulateUsersStudentSystemDAO();			
				persDetails = populateUsersStudentSystemDAO.getStudentPersDetails(userId);
				Iterator<?> staffDetails = persDetails.iterator();
				 while (staffDetails.hasNext()) {	
					 ListOrderedMap staffPersonnelDetails = (ListOrderedMap) staffDetails.next();
					 if(staffPersonnelDetails.get("name") !=null){
					  userName = staffPersonnelDetails.get("name").toString().toUpperCase();
					 }
					  if(staffPersonnelDetails.get("surname") !=null){
					  userSurName = staffPersonnelDetails.get("surname").toString().toUpperCase();
					  }
				 }					
				userDirectoryService.addUser(null, userId, userName, userSurName, userId+"@mylife.unisa.ac.za", "", "Student", null);
				log.info(this+" Added user to sakai "+userId);
			} 
		}
		catch (Exception e) {	
			log.error(this+" populateStaffDataToSakai: ERROR on Adding Student "+userId+" to sakai ");
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
