package za.ac.unisa.lms.tools.cronjobs.jobs;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;

import za.ac.unisa.lms.tools.cronjobs.dao.MyUnisaStatsDAO;
import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;

public class MyUnisaStatsJobUniqueLogins2007 extends SingleClusterInstanceJob implements StatefulJob, OutputGeneratingJob {
	
	private EmailService emailService;

	public static long runcount = 1L;
	private Log log = null;
	private ByteArrayOutputStream outputStream;
	private PrintWriter output;

	public void executeLocked(JobExecutionContext context)
			throws JobExecutionException {

		log = LogFactory.getLog(this.getClass());
		outputStream = new ByteArrayOutputStream();
		output = new PrintWriter(outputStream);
		String feedbackMessage = "";
		String errorMessage = "";

		runcount++;

		log.info(runcount + ": << MyUnisaStatsJob>> Started.");

		MyUnisaStatsDAO dao = new MyUnisaStatsDAO();
		/** ****************************************************************************************************************/
		/** Unique USER LOGINS
		 * MONTHLY
		 * CATEGORY=GATEWAY
		 * USER_TYPE=STUDENT(S)/LECTURER(L)
		 */
		feedbackMessage = feedbackMessage + "<hr> UNIQUE USER LOGIN EVENTS FOR May 2007<br>";
		try{
			errorMessage = dao.getUserLoginMonthlyHistory("user.login","2007-05-31");
			if (errorMessage.length()> 0) {
				feedbackMessage = feedbackMessage+"Event=user.login.unique <b> Error: </b> "+errorMessage+"<br>";
			} else {
				feedbackMessage = feedbackMessage+"Event=user.login.unique <br> Success <br>";
			}
		} catch (Exception ex) {
			//throw new Exception("MyUnisaStatsJob: dao.getEventStats: Error occurred / "+ ex,ex);
		}

		feedbackMessage = feedbackMessage+"</body></html>";

		String emailSubject = "LMSMIS STATS FEEDBACK UNIQUE LOGINS May 2007";
		try {
			sendEmail(emailSubject,feedbackMessage,"syzelle@unisa.ac.za");
		} catch (AddressException e) {
			log.info("error sending mail from LMSMIS STATS to syzelle@unisa.ac.za");
		}

		feedbackMessage = "<hr> UNIQUE USER LOGIN EVENTS FOR JUN 2007<br>";
		try{
			errorMessage = dao.getUserLoginMonthlyHistory("user.login","2007-06-30");
			if (errorMessage.length()> 0) {
				feedbackMessage = feedbackMessage+"Event=user.login.unique <b> Error: </b> "+errorMessage+"<br>";
			} else {
				feedbackMessage = feedbackMessage+"Event=user.login.unique <br> Success <br>";
			}
		} catch (Exception ex) {
			//throw new Exception("MyUnisaStatsJob: dao.getEventStats: Error occurred / "+ ex,ex);
		}

		feedbackMessage = feedbackMessage+"</body></html>";

		emailSubject = "LMSMIS STATS FEEDBACK UNIQUE LOGINS Jun 2007";
		try {
			sendEmail(emailSubject,feedbackMessage,"syzelle@unisa.ac.za");
		} catch (AddressException e) {
			log.info("error sending mail from LMSMIS STATS to syzelle@unisa.ac.za");
		}


		log.debug(runcount + ": Stopped << MyUnisaStatsJob>>. "+(runcount));
	}

	public String getOutput() {
		if (output != null) {
			output.flush();
			return outputStream.toString();
		}
		return null;
	}

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
		log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
	} // end of sendEmail

}

