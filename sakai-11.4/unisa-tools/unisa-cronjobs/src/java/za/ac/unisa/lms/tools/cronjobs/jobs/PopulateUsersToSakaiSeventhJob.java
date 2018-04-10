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

import za.ac.unisa.lms.tools.cronjobs.dao.DropboxEventsUpdateDAO;
import za.ac.unisa.lms.tools.cronjobs.dao.PopulateUsersStudentSystemDAO;
import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;
import za.ac.unisa.utils.NoStudyMaterialIssue;

public class PopulateUsersToSakaiSeventhJob extends SingleClusterInstanceJob implements StatefulJob, InterruptableJob, OutputGeneratingJob {
	
	private Log log = null;
	private UserDirectoryService userDirectoryService;
	private SiteService siteService;
	private SessionManager sessionManager;
	 String tutorMode = null;
	 String inactiveDate = null;
	
	public void populateUsers() throws Exception{
		
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		siteService = (SiteService) ComponentManager.get(SiteService.class);
		
		User user = userDirectoryService.authenticate(ServerConfigurationService.getString("admin.user"),ServerConfigurationService.getString("admin.password") );
		PopulateUsersStudentSystemDAO populateUsersStudentSystemDAO = new PopulateUsersStudentSystemDAO();
		
		  Session session = sessionManager.getCurrentSession();
		  session.setUserEid("admin");
		  session.setUserId("admin");
		  List<?> allStudents=null;
		  List<?> studentCourseList=null;
		  List<?> studentPersDetails=null;
		  String userid=null;
		 
		  
		  //get the current students from stusun and update to sakai
		  
		  try{		  

			  studentCourseList = populateUsersStudentSystemDAO.getStudentCoursesStartsWithHM();	
		    
			  Iterator<?> courseList = studentCourseList.iterator();
			  while (courseList.hasNext()) {			  
				 
				 ListOrderedMap courseDetails = (ListOrderedMap) courseList.next();
				 String studentNr =  courseDetails.get("studentNr").toString();
				 String studyUnit = courseDetails.get("studyUnit").toString();
				 String semester = getCourseTypeAsString(Short.parseShort(courseDetails.get("semester").toString()));
				 String academicYear = courseDetails.get("academicYear").toString();
				 String collegeCode =courseDetails.get("collegeCode").toString();
				 String postCategoryType =courseDetails.get("studyCategory").toString();
				
				/* if(courseDetails.get("InactiveDate") != null) {
				 inactiveDate =courseDetails.get("inactiveDate").toString();
				 }
				 
				 if(courseDetails.get("tutorMode") != null){
					  tutorMode = courseDetails.get("tutorMode").toString();		
				 }*/
						 
				 //check the user in sakai. if not exists add the user before linking to site
/*				 try {
						User userTocheck = null;
						userTocheck = userDirectoryService.getUserByEidFromDb(studentNr);
						
						if (userTocheck == null) {
							String studentName="";
							String studentSurName="";
							String studentEmail="";
							String studentPassword="";							
							
							studentPersDetails = populateUsersStudentSystemDAO.getStudentPersonnelDetails(studentNr);
							Iterator<?> studDetails = studentPersDetails.iterator();
							 while (studDetails.hasNext()) {	
								 ListOrderedMap studPersDetails = (ListOrderedMap) studDetails.next();
								  studentName = studPersDetails.get("name").toString().toUpperCase();
								  studentSurName = studPersDetails.get("surname").toString().toUpperCase();								 
							    	 if(studPersDetails.get("email") == null){
									 studentEmail=studentNr+"@mylife.unisa.ac.za";
								 }else{
									 studentEmail = studPersDetails.get("email").toString();
								 }
							    	 if(studPersDetails.get("password") != null){
								   studentPassword = studPersDetails.get("password").toString();
							    	 }
							 }
							
							userDirectoryService.addUser(null, studentNr, studentName, studentSurName, studentEmail, studentPassword, "Student", null);
						} 
					}
					catch (Exception e) {	
						log.debug(this+" populateUsers: Addind student "+studentNr+" to sakai ");
					}finally{						
					}*/	
				 
				try{
				 
				 if(postCategoryType.equalsIgnoreCase("M")){
					 String masterSite="COL" + collegeCode + "CATM";					 
					 addUserToSite(masterSite,studentNr,"Student","","","","");
					 
					 String mainCourse = studyUnit + "-" + academicYear.substring(2) + "-" + semester;
					 addUserToSite(mainCourse,studentNr,"Student",studyUnit,academicYear,semester,"");	
					 
		/*			 if(tutorMode != null ){
							//add tutor site
							if(getInactiveDateStatus(inactiveDate)){
							String tutorCourse = studyUnit + "-" + academicYear.substring(2) + "-" + semester + "-" + tutorMode;
							addUserToSite(tutorCourse,studentNr,"Student",studyUnit,academicYear,semester,tutorMode);	
							}
							
						String mainCourse = studyUnit + "-" + academicYear.substring(2) + "-" + semester;
						addUserToSite(mainCourse,studentNr,"Student",studyUnit,academicYear,semester,"");						
					
					 }else{
						 String mainCourse = studyUnit + "-" + academicYear.substring(2) + "-" + semester;
						 addUserToSite(mainCourse,studentNr,"Student",studyUnit,academicYear,semester,"");			
					 }*/
					 
			    	}else  if(postCategoryType.equalsIgnoreCase("D")){
						 String masterSite="COL" + collegeCode + "CATD";					 
						 addUserToSite(masterSite,studentNr,"Student","","","","");
						 
						 String mainCourse = studyUnit + "-" + academicYear.substring(2) + "-" + semester;
						 addUserToSite(mainCourse,studentNr,"Student",studyUnit,academicYear,semester,"");	
						 
						/* if(tutorMode != null ){							
							 	//add tutor site
								if(getInactiveDateStatus(inactiveDate)){
								String tutorCourse = studyUnit + "-" + academicYear.substring(2) + "-" + semester + "-" + tutorMode;
								addUserToSite(tutorCourse,studentNr,"Student",studyUnit,academicYear,semester,tutorMode);
								}
							String mainCourse = studyUnit + "-" + academicYear.substring(2) + "-" + semester;
							addUserToSite(mainCourse,studentNr,"Student",studyUnit,academicYear,semester,"");								
							
						 }else{
							 String mainCourse = studyUnit + "-" + academicYear.substring(2) + "-" + semester;
							 addUserToSite(mainCourse,studentNr,"Student",studyUnit,academicYear,semester,"");	
						 }*/
						 
				    	}
			        	else{
			        			String mainCourse = studyUnit + "-" + academicYear.substring(2) + "-" + semester;
			        			addUserToSite(mainCourse,studentNr,"Student",studyUnit,academicYear,semester,"");	
				    		/*if(tutorMode != null ){
				    			//add tutor site
								if(getInactiveDateStatus(inactiveDate)){
								String tutorCourse = studyUnit + "-" + academicYear.substring(2) + "-" + semester + "-" + tutorMode;
								addUserToSite(tutorCourse,studentNr,"Student",studyUnit,academicYear,semester,tutorMode);
								}
								String mainCourse = studyUnit + "-" + academicYear.substring(2) + "-" + semester;
								addUserToSite(mainCourse,studentNr,"Student",studyUnit,academicYear,semester,"");					
								
							 }else{
								 String mainCourse = studyUnit + "-" + academicYear.substring(2) + "-" + semester;
								 addUserToSite(mainCourse,studentNr,"Student",studyUnit,academicYear,semester,"");	
							 }*/
				    	}	
				 }catch(Exception e){
					
					 e.printStackTrace();
					 continue;
				 }
				/* this.inactiveDate=null;
				 this.tutorMode=null;*/
				 
			  }		      
		      
		  }catch(Exception e)
		  {
			  e.printStackTrace();
		  }
		  
		  
	}

	public void addUserToSite(String siteId, String userEid, String role,String courseCode, String year,String semester, String tutorMode) throws Exception{
		try{
		Site site = siteService.getSite(siteId);
    	 String userid = userDirectoryService.getUserByEid(userEid).getId();
    	 site.addMember(userid,"Student",true,false); //for course sites the roleid is must be Instructor or Student or Teaching assistant
    	 siteService.save(site);
    	 log.debug(this+" Added user "+userEid+" to site Id "+siteId);
    	 //delete the course from temp student system table
    	 PopulateUsersStudentSystemDAO populateUsersStudentSystemDAO = new PopulateUsersStudentSystemDAO();
    	 
    	 if(courseCode.length() > 1){
    	 populateUsersStudentSystemDAO.deleteCourseFromTmpTable(userEid,courseCode,year,semester,tutorMode);
    	 }
    	 
   		}catch(UserNotDefinedException e){
   			log.error(this+" addUserToSite: Adding student "+userEid+" to site "+siteId+" failed because UserNotDefinedException ");
			e.printStackTrace();
		}catch (IdUnusedException e) {
			// TODO: handle exception
			log.info(this+" addUserToSite: Addind student "+userEid+" user to site "+siteId+" failed because Sakai Site not exists ");
		}
		catch (PermissionException e) {
			// TODO: handle exception
			log.info(this+" addUserToSite: Addind student "+userEid+" user to site "+siteId+" failed because Sakai Site not exists ");
		}
	}
	
	public static String getCourseTypeAsString(short code) {
		switch (code) {
		case 0: { return "Y1";}
		case 1: { return "S1";}
		case 2: { return "S2";}
		case 6: { return "Y2";}
		}
		return "?";
	}
	
	public static boolean getInactiveDateStatus(String date){
		Date currentDate = new Date();
		boolean allowSite = false;
	   try{
		if(date != null){
		SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date inactiveDate = formatter.parse(date);
		if(currentDate.before(inactiveDate)){
			allowSite = true;
		}else{
			allowSite = false;
		}
    	}else{
			allowSite = true;
		}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return allowSite;
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
