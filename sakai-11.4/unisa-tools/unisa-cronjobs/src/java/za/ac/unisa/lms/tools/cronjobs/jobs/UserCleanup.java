package za.ac.unisa.lms.tools.cronjobs.jobs;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.struts.util.LabelValueBean;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.sakaiproject.authz.api.AuthzGroup;
import org.sakaiproject.authz.api.AuthzGroupService;
import org.sakaiproject.authz.api.AuthzPermissionException;
import org.sakaiproject.authz.api.GroupNotDefinedException;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.component.cover.ComponentManager;
//import org.sakaiproject.authz.*;


import za.ac.unisa.lms.tools.cronjobs.dao.UserCleanupSakaiDAO;
import za.ac.unisa.lms.tools.cronjobs.dao.UserCleanupStudDAO;
import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;

public class UserCleanup extends SingleClusterInstanceJob implements StatefulJob, OutputGeneratingJob {

	public static long runcount = 1L;
	private ByteArrayOutputStream outputStream;
	private PrintWriter output;
	
	private EmailService emailService;
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;

	/*
	 * Crontab file
___________
Crontab syntax :-
A crontab file has five fields for specifying day , date and time  followed by the command to be run at that interval.
*     *   *   *    *  command to be executed
-     -    -    -    -
|     |     |     |     |
|     |     |     |     +----- day of week (0 - 6) (Sunday=0)
|     |     |     +------- month (1 - 12)
|     |     +--------- day of month (1 - 31)
|     +----------- hour (0 - 23)
+------------- min (0 - 59) (non-Javadoc)
	 */

	private AuthzGroupService authzGroupService;
	
	/*
	public void setAuthzGroupService(AuthzGroupService authzGroupService) {
		this.authzGroupService = authzGroupService;
	}
	*/
	
	
	public void executeLocked(JobExecutionContext context)
			throws JobExecutionException {
		
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);

		System.out.println("Cronjob: SonetteTest");
		UserCleanupStudDAO db1 = new UserCleanupStudDAO();
		UserCleanupSakaiDAO db2 = new UserCleanupSakaiDAO();
		String feedbackMsg = "<b> The following users was removed from myUnisa: </b>";
		String notRemoved = "";
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, -15);
		now.add(Calendar.DATE, 13);
		int year1 = now.get(Calendar.YEAR);
		int month1 = (now.get(Calendar.MONTH)+1);
		int date1 = now.get(Calendar.DATE);
		
      String month = "";
      if(month1<10){
    	  month = "0"+month1;
      }else{
    	  month = Integer.toString(month1);
      }
      String date ="";
      if(date1<10){
    	  date="0"+date1;
      }else{
    	  date=Integer.toString(date1);
      }
      String startyear = year1+"-"+month+"-"+date;
        Calendar now1 = Calendar.getInstance();
        
        now1.add(Calendar.MONTH, -3);
        now1.add(Calendar.DATE,-18 );
        int year2 = now1.get(Calendar.YEAR);
		int month2 = (now1.get(Calendar.MONTH)+1);
		int date2 = now1.get(Calendar.DATE);
      String endmonth = "";
      if(month2<10){
    	  endmonth = "0"+month2;
      }else{
    	  endmonth=Integer.toString(month2);
      }
        String enddate = "";
        if(date2<10){
        	enddate = "0"+date2;
        }else{
        	enddate = Integer.toString(date2);
        }
        String endyear = year2+"-"+endmonth+"-"+enddate;
        System.out.println("start date:"+startyear);
        System.out.println("end date:"+endyear);
		ArrayList resignedUsers= new ArrayList();
		try {
			resignedUsers = db1.selectResignedPers(startyear,endyear);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		for (int i=0; i < resignedUsers.size(); i++) {
			LabelValueBean rUser = (LabelValueBean) resignedUsers.get(i);
			String userEid = rUser.getValue();
		
			// get userId
			String userId = "";
			try {
				userId = db2.getUserId(userEid);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			//if ((null!=userId)||(userId.length() != 0)||(!userId.equalsIgnoreCase(""))) {
			if (userId.equals("")) {
				notRemoved = notRemoved+"<br> "+userEid;
			} else {			
				//authzGroupService = org.sakaiproject.authz.cover.AuthzGroupService.getInstance();
				authzGroupService = (AuthzGroupService) ComponentManager.get(AuthzGroupService.class);
				
				//authzGroupService.getAuthzGroupsIsAllowed: the Set (String) of AuthzGroup ids in which this user is allowed to perform this function
		 		java.util.Set authzGroups = authzGroupService.getAuthzGroupsIsAllowed(userId , "site.visit", null); // (1)
				java.util.Iterator it = authzGroups.iterator(); // (2)
		
				User user = userDirectoryService.authenticate(
						ServerConfigurationService.getString("admin.user"),
						ServerConfigurationService.getString("admin.password"));
				
				if (user != null) {
			
					Session session = sessionManager.startSession();
					if (session == null) throw new JobExecutionException("No session");
		
					session.setUserEid(user.getId());
					session.setUserId(user.getId());
					sessionManager.setCurrentSession(session);

					while (it.hasNext()) {
						AuthzGroup group;
						try {
							group = authzGroupService.getAuthzGroup((String) it.next());
							group.removeMember(userId);
							authzGroupService.save(group);
							
						} catch (GroupNotDefinedException e1) {
							// TODO Auto-generated catch block
							System.out.println("error occured: "+e1);
							feedbackMsg = feedbackMsg + " error: "+e1;
							//e1.printStackTrace();
						}
						//AuthzGroup group = authzGroups.get(i);
						catch (AuthzPermissionException e) {
							// TODO Auto-generated catch block
							System.out.println("error occured: "+e);
							feedbackMsg = feedbackMsg + " error: "+e;
							e.printStackTrace();
						}
						
					}
	
				} // if user!=null
				feedbackMsg = feedbackMsg +"<br> "+userEid+" = "+userId;
			} // end if userId is null
		} // end for each resigned user
		
		// Send feedback email
		String feedbackEmail = "<html><body>"+feedbackMsg +
								" <b> Users not removed: </b> "+notRemoved+" "+
								" </body></html>";
    	String emailSubject = "myUnisa User Cleanup";
    	try {
			sendEmail(emailSubject,feedbackEmail,"syzelle@unisa.ac.za");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendEmail(String subject, String body, String emailAddress) throws AddressException {

		String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");

		InternetAddress toEmail = new InternetAddress(emailAddress);
		InternetAddress iaTo[] = new InternetAddress[1];
		iaTo[0] = toEmail;
		InternetAddress iaHeaderTo[] = new InternetAddress[1];
		iaHeaderTo[0] = toEmail;
		InternetAddress iaReplyTo[] = new InternetAddress[1];
		iaReplyTo[0] = new InternetAddress(tmpEmailFrom);
		List<String> contentList = new ArrayList<String>();
		contentList.add("Content-Type: text/html");
		//contentList.add("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		
		emailService = (EmailService) ComponentManager.get(EmailService.class);

		emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
		//log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
	} // end of sendEmail
	
	public String getOutput() {
		if (output != null) {
			output.flush();
			return outputStream.toString();
		}
		return null;
	}

}
