package za.ac.unisa.lms.tools.cronjobs.jobs;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;

import za.ac.unisa.lms.tools.cronjobs.dao.PopulateUsersStudentSystemDAO;
import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;

public class PopulateCurrentStudentsToSakaiJob extends SingleClusterInstanceJob implements
StatefulJob, InterruptableJob, OutputGeneratingJob {
	
	private Log log = null;
	private UserDirectoryService userDirectoryService;
	private SiteService siteService;
	private SessionManager sessionManager;
	
	public void populateCurrentStudentsToSakai() throws Exception{
	
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		siteService = (SiteService) ComponentManager.get(SiteService.class);
		
		User user = userDirectoryService.authenticate(ServerConfigurationService.getString("admin.user"),ServerConfigurationService.getString("admin.password") );
		PopulateUsersStudentSystemDAO populateUsersStudentSystemDAO = new PopulateUsersStudentSystemDAO();
		
		  Session session = sessionManager.getCurrentSession();
		  session.setUserEid("admin");
		  session.setUserId("admin");
		  List<?> studentList=null;
		  List<?> currentStudentList=null;
		  User userCheck = null;
		   
		  //check the user in sakai. if not exists add the user before adding the user to sakai_user table
	    		 try {
						String studentNr="";
						String studentName="";
						String studentSurName="";
						String studentEmail="";
						String studentPassword="";							
						
						 studentList = populateUsersStudentSystemDAO.getCurrentStudentsList();
						Iterator<?> studDetails = studentList.iterator();
						 while (studDetails.hasNext()) {	
							 ListOrderedMap studPersDetails = (ListOrderedMap) studDetails.next();
							  studentNr = studPersDetails.get("student_nr").toString();
							  studentName = studPersDetails.get("name").toString();
							  studentSurName = studPersDetails.get("surname").toString();
							  //studentEmail = studPersDetails.get("email").toString();
							  studentEmail=studentNr+"@mylife.unisa.ac.za";
							 if(studentEmail.length()==0){
								 studentEmail=studentNr+"@mylife.unisa.ac.za";
							 }
							  studentPassword = studPersDetails.get("password").toString();						
						     //chek the student already exists in sakai
						     userCheck = userDirectoryService.getUserByEidFromDb(studentNr);
						    // log.info(this+" UerCheck current student "+studentNr+"to sakai from STUSUN");
							if (userCheck == null) {
						    userDirectoryService.addUser(null, studentNr, studentName, studentSurName, studentEmail, "", "Student", null);
						       log.info(this+" Current Student added to sakai "+studentNr);
							}else{
								log.info(this+" Current Student already exists in sakai "+studentNr);
							}
						 }
					}
				catch (Exception e) {	
					e.printStackTrace();
					log.debug(this+" Error occured: populateUsers: Addind student  to sakai ");
					
				}finally{		
					studentList=null;
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
			populateCurrentStudentsToSakai();		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
