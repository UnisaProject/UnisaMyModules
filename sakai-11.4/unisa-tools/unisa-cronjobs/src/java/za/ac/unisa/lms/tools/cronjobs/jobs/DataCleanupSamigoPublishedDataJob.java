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
import za.ac.unisa.unisa_axis.DataCleanup_SamigoPublishedDataWebService_jws.DataCleanup_SamigoPublishedDataWebServiceServiceLocator;
import za.ac.unisa.unisa_axis.DataCleanup_SamigoPublishedDataWebService_jws.DataCleanup_SamigoPublishedDataWebService_PortType;

public class DataCleanupSamigoPublishedDataJob extends SingleClusterInstanceJob implements StatefulJob, OutputGeneratingJob {

	private ByteArrayOutputStream outputStream;
	private PrintWriter output;
	private EmailService emailService;
	String url = "";
	
	private static final String LOCAL_URL_PORT80 = "http://localhost:8080";
    private static final String LOCAL_URL_PORT82 = "http://localhost:8082";
    private static final String PORTAL_URL = "/portal";
    
    private String webServiceResult = "";

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

		System.out.println("~~~~~~~~~~ Cronjob: DataCleanupSamigoPublishedDataJob");
		String subjectStart = "DataCleanupSamigoPublishedDataJob Job Started";
		String bodyStart = "DataCleanupSamigoPublishedDataJob Job Started";
		try {
			sendEmail(subjectStart, bodyStart, "mphahsm@unisa.ac.za");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		String serverUrl = ServerConfigurationService.getString("serverUrl");
		String localPortal80 = LOCAL_URL_PORT80 + PORTAL_URL;
		String localPortal82 = LOCAL_URL_PORT82 + PORTAL_URL;
		if ( serverUrl.equals(LOCAL_URL_PORT80) || serverUrl.equals(LOCAL_URL_PORT82)
			|| serverUrl.equals(localPortal80) || serverUrl.equals(localPortal82) ) {
			serverUrl = "https://mydev.int.unisa.ac.za";
		}  
		
		url = serverUrl;
		url = url+"/unisa-axis/DataCleanup_SamigoPublishedDataWebService.jws";
		DataCleanup_SamigoPublishedDataWebService_PortType events = null;
		
		// registration periods to be considered 
		String[] periods = {"2011#0"};
		
		// for each period		
		for (int m=0; m < periods.length; m++) {

			String[] tmpPeriod = periods[m].split("#");
			
			Integer forYear = Integer.parseInt(tmpPeriod[0]);
			String forSem = tmpPeriod[1];
						
			/** Delete old samigo published copies data */
			try {
				URL url1 = new URL(url);
				events = new DataCleanup_SamigoPublishedDataWebServiceServiceLocator().getDataCleanup_SamigoPublishedDataWebService(url1);	
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (ServiceException e3) {
				e3.printStackTrace();
			} catch (Exception e2) {
				e2.printStackTrace();
			} // end try
			
					
			try {
			
				webServiceResult = events.dataCleanupPerYear(forYear);
				String subjectC = "";
				String bodyC = "";

				if( webServiceResult.trim().equals("Success") ){
					subjectC = "DataCleanupSamigoPublishedDataJob Job Completed";
					bodyC = "DataCleanupSamigoPublishedDataJob Job Completed";
					System.out.println("~~~~~~~~~~ DataCleanupSamigoPublishedDataJob Job Completed");
					
				} else {
					subjectC = "DataCleanupSamigoPublishedDataJob Job FAILED";
					bodyC = "DataCleanupSamigoPublishedDataJob Job FAILED";
					System.out.println("~~~~~~~~~~ DataCleanupSamigoPublishedDataJob Job FAILED");
				}
				
				try {
					sendEmail(subjectC, bodyC, "mphahsm@unisa.ac.za");
				} catch (AddressException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}catch (Exception vE) {
				// TODO: handle exception
				vE.printStackTrace();
			} // end try events.dataCleanupPerYear
						
		} // end of for (int m=0; m < periods.length; m++)
		
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

} // end of public class DataCleanupSamigoPublishedDataJob