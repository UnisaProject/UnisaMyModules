package za.ac.unisa.lms.tools.cronjobs.jobs;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.xml.rpc.ServiceException;

import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.email.api.EmailService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;
import za.ac.unisa.unisa_axis.GradebookSyncStudentSystemWebService_jws.GradebookSyncStudentSystemWebServiceServiceLocator;
import za.ac.unisa.unisa_axis.GradebookSyncStudentSystemWebService_jws.GradebookSyncStudentSystemWebService_PortType;
import za.ac.unisa.lms.tools.cronjobs.dao.GradebookIntegrationStudentSystemDAO;

public class GradebookIntegrationJob extends SingleClusterInstanceJob implements StatefulJob, OutputGeneratingJob {

	public static long runcount = 1L;
	private ByteArrayOutputStream outputStream;
	private PrintWriter output;
	private EmailService emailService;
	String url = "";

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

	public void executeLocked(JobExecutionContext context)
			throws JobExecutionException {

		System.out.println("~~~~~~~~~~ Cronjob: GradebookIntegrationJob");
		GradebookIntegrationStudentSystemDAO dao = new GradebookIntegrationStudentSystemDAO();
		
		/*String host = request.getHeader("Host");
		System.out.println("HOST: "+host);
		
		setStudentnumber(request.getParameter("studentnumber"));
		setIpaddress(request.getParameter("ipaddress"));
		setEventtype(request.getParameter("eventtype"));
		if ("localhost:8083".equals(host)){
			URL = "https://mydev.unisa.ac.za";
		} else {
			URL = "https://"+host;
		}*/
		
		String serverUrl = ServerConfigurationService.getString("serverUrl");
		if (serverUrl.equals("http://localhost:8080")) {
			serverUrl = "https://mydev.unisa.ac.za";
		} 
		
		//url = "http://localhost:8082";
		url = serverUrl;
		url = url+"/sakai-axis/GradebookSyncStudentSystemWebService.jws";
		//FreedomToasterWebService_PortType events = null;
		GradebookSyncStudentSystemWebService_PortType events = null;
		
		// registration periods to be considered 
        // 24 Nov - Sonette add 2017 periods: "2017#0","2017#6","2017#1","2017#2"
		// 27 March 2018 - Sonette add 2018 periods
		// 20 March 2019 - Sonette 2019 periods
		String[] periods = {"2019#0","2019#6","2019#1","2019#2","2020#0","2020#6","2020#1","2020#2"};
		
		/** Retrieve modules with online assessments */
		/*String module = "EUP1501";
		String acadYear = "2012";
		String semPeriod = "0";
		String assignmentNr = "1";
		String onlineType = "B";*/
		
		/*String module = "MAT1503";
		String acadYear = "2013";
		String semPeriod = "1";
		String assignmentNr = "2";
		String onlineType = "DF";*/
		
		for (int m=0; m < periods.length; m++) {

			String[] tmpPeriod = periods[m].split("#");
			
			String forYear = tmpPeriod[0];
			String forSem = tmpPeriod[1];
			
			String module = "";
			String acadYear = "";
			String semPeriod = "";
			String assignmentNr = "";
			String onlineType = "";
			ArrayList<String> onlineAssessments = new ArrayList<String>();
			try {
				onlineAssessments = dao.selectOnlineAssessments(forYear, forSem);
				
			} catch (Exception e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}
			
			for (int i=0; i<onlineAssessments.size(); i++) {
				String onlineAssessment = onlineAssessments.get(i).toString();
				if (!onlineAssessment.equals("No online assessments found")) {
					String[] parts1 = onlineAssessment.split("#");
					module = parts1[0]; 
					acadYear = parts1[1];
					semPeriod = parts1[2];
					assignmentNr = parts1[3];
					onlineType = parts1[4];
					
					String moduleSite = module+"-"+acadYear.substring(2,4)+"-";
					if (semPeriod.equals("0")) {
						moduleSite = moduleSite+"Y1";
					}else if (semPeriod.equals("6")) {
						moduleSite = moduleSite+"Y2";
					}else if (semPeriod.equals("1")) {
						moduleSite = moduleSite+"S1";
					}else if (semPeriod.equals("2")) {
						moduleSite = moduleSite+"S2";
					}
					
					/** Select the Primary lecturer for the course */
					String tmpPrimaryLecturer = "";
					String primaryLecturer = "";
					String primaryLecturerEmail = "";
					try {
						tmpPrimaryLecturer = dao.selectPrimaryLecturer(module,acadYear, semPeriod);
						if (tmpPrimaryLecturer.equals("")) {
							primaryLecturer = "";
							primaryLecturerEmail = "";
							
							// send e-mail
							String subject = "Gradebook Integration - No Primary lecturer";
							String body = "Good day, <br> <br> Please note that there is no Primary Lecturer for "
								+moduleSite+
								". <br>Gradebook integration was not done for this module.";
							sendEmail(subject, body, "syzelle@unisa.ac.za");
							sendEmail(subject, body, "fmyburgh@unisa.ac.za");
						} else {
	 						String[] parts = tmpPrimaryLecturer.split("#");
							primaryLecturer = parts[0]; 
							String email1 = parts[1];
							String email2 = parts[2];
							String email3 = parts[3];
							if (email1.equals("none")) {
								primaryLecturerEmail = email2;
							} else {
								primaryLecturerEmail = email1;
							}
							if (primaryLecturerEmail.equals("none")) {
								primaryLecturerEmail = email3;
							}
						}
					} catch (Exception e) {
						// email primary lecturer not found
						e.printStackTrace();
					}
					if (!serverUrl.equals("https://my.unisa.ac.za")) {
						primaryLecturer = "syzelle";
						primaryLecturerEmail = "syzelle@unisa.ac.za";
					}
					
					if (!primaryLecturer.equals("")) {
						/** Build the module site */

						
						/** Update student system with Sakai Gradebook results */
						try {
							URL url1 = new URL(url);
							events = new GradebookSyncStudentSystemWebServiceServiceLocator().getGradebookSyncStudentSystemWebService(url1);	
						} catch (MalformedURLException e1) {
							e1.printStackTrace();
						} catch (ServiceException e3) {
							e3.printStackTrace();
						} catch (Exception e2) {
							e2.printStackTrace();
						} // end try
				
						
						try {
				
							events.getGradebookMarks(module, moduleSite, acadYear, semPeriod, assignmentNr, onlineType, primaryLecturer, primaryLecturerEmail);
				
						}catch (Exception vE) {
							// TODO: handle exception
							vE.printStackTrace();
						} // end try getGradebookMarks
						
					} // end of if (!primaryLecturer.equals(""))
				} // end of if (!onlineAssessment.equals("No online assessments found"))
			}  // end of for (int i =0; i<=onlineAssessments.size(); i++)

		} // end of for (int m=0; m < periods.length; m++)
		
		String subjectC = "Gradebook Integration Job Completed";
		String bodyC = "Gradebook Integration Job Completed";
		System.out.println("~~~~~~~~~~ Gradebook Integration Job Completed");
		try {
			sendEmail(subjectC, bodyC, "syzelle@unisa.ac.za");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} // end of public void executeLocked
	
	public void sendEmail(String subject, String body, String emailAddress) throws AddressException {
		
		emailService = (EmailService) ComponentManager.get(EmailService.class);

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

		emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
	} // end of sendEmail

	public String getOutput() {
		if (output != null) {
			output.flush();
			return outputStream.toString();
		}
		return null;
	} // end of public String getOutput()

} // end of public class GradebookIntegrationJob