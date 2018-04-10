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
import za.ac.unisa.unisa_axis.DataCleanup_SakaiManagementProcWebService_jws.DataCleanup_SakaiManagementProcWebServiceServiceLocator;
import za.ac.unisa.unisa_axis.DataCleanup_SakaiManagementProcWebService_jws.DataCleanup_SakaiManagementProcWebService_PortType;

public class DataCleanupSakaiManagementJob2011_Y2 extends SingleClusterInstanceJob implements StatefulJob, OutputGeneratingJob {

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

		System.out.println("~~~~~~~~~~ Cronjob: DataCleanupSakaiManagementJob");
		String subjectStart = "DataCleanupSakaiManagementJob Job Started";
		String bodyStart = "DataCleanupSakaiManagementJob Job Started";
		try {
			sendEmail(subjectStart, bodyStart, "syzelle@unisa.ac.za");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		String serverUrl = ServerConfigurationService.getString("serverUrl");
		if (serverUrl.equals("http://localhost:8080")) {
			serverUrl = "https://mydev.unisa.ac.za";
		} 
		
		//url = "http://localhost:8082";
		url = serverUrl;
		url = url+"/unisa-axis/DataCleanup_SakaiManagementProcWebService.jws";
		//FreedomToasterWebService_PortType events = null;
		DataCleanup_SakaiManagementProcWebService_PortType events = null;
		
		// registration periods to be considered 
		String[] periods = {"2011#Y2"};
		
		// for each period		
		for (int m=0; m < periods.length; m++) {

			String[] tmpPeriod = periods[m].split("#");
			
			String forYear = tmpPeriod[0];
			String forSem = tmpPeriod[1];
						
			/** Delete old sakai management data */
			try {
				URL url1 = new URL(url);
				events = new DataCleanup_SakaiManagementProcWebServiceServiceLocator().getDataCleanup_SakaiManagementProcWebService(url1);	
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (ServiceException e3) {
				e3.printStackTrace();
			} catch (Exception e2) {
				e2.printStackTrace();
			} // end try
			
					
			try {
			
				events.sakaiManagementDataCleanup(forYear, forSem);
			
			}catch (Exception vE) {
				// TODO: handle exception
				vE.printStackTrace();
			} // end try events.gradebookDataCleanup
						
		} // end of for (int m=0; m < periods.length; m++)
		
		String subjectC = "DataSakaiManagementJob Job Completed";
		String bodyC = "DataSakaiManagementJob Job Completed";
		System.out.println("~~~~~~~~~~ DataSakaiManagementJob Job Completed");
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

} // end of public class DataCleanup_SakaiManagementJob