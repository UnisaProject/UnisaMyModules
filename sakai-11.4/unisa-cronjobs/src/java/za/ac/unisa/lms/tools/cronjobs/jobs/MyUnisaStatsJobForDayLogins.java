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

import za.ac.unisa.lms.tools.cronjobs.dao.MyUnisaStatsHistoryDAO;
import za.ac.unisa.lms.tools.cronjobs.dao.MyUnisaStatsStudentOracleDAO;
import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;

public class MyUnisaStatsJobForDayLogins extends SingleClusterInstanceJob implements StatefulJob, OutputGeneratingJob {
	
	private EmailService emailService;

	public static long runcount = 1L;
	private Log log = null;
	private ByteArrayOutputStream outputStream;
	private PrintWriter output;
	private String[] adminEvents = {"acadlist.view", "acadhistory.view","biodetails.view","bulkemail.view","regdetails.view",
			"results.view","studentlist.view","studyquotation.view","trackandtrace.view","payments.viewcurrent","payments.viewhistory",
			"biodetails.addresschange","biodetails.addresschangeworkflow","biodetails.contactdetailschange","biodetails.correspondencechange",
			"biodetails.examcentrechange","bulkemail.send","changepassword.change","changepassword.view",
			"regdetails.addition","regdetails.cancellation","regdetails.semesterexchange","studentlist.display","studentlist.download",
			"creditcard.payment","contactus.emaildepartment","contactus.displaystudentnumber"};
	private String[] gatewayMonthlyEvents = {"ebookshop.view" ,"join.view","rejoin.view","password.view","ebookshop.edit","ebookshop.update",
			"ebookshop.add","ebookshop.delete"};
	private String[] gatewayDailyEvents = {"join.activation","password.activate","password.sendemail","freedomtoaster.write","freedomtoaster.nowrite","freedomtoaster.cancel"};
	private String[] helpdeskEvents = {"audit.view"};

	private String[] siteViewEvents = {"classlist.view","faqs.contentview","faqs.view","studentemail.view","welcome.view"};
	private String[] siteEvents = {"discussionforums.add","discussionforums.addreply","discussionforums.addtopic","discussionforums.delete",
			"discussionforums.edit","discussionforums.deletereply","discussionforums.deletetopic",
			"disc.delete.category","disc.delete.own","disc.new","disc.new.category",
			"faqs.categoryadd","faqs.contentadd","faqs.categorydelete","faqs.categoryedit","faqs.contentedit",
			"welcome.update","welcome.revert","annc.new","annc.delete.own","annc.revice.own","annc.delete.any","annc.revise.any",
			"calendar.new","calendar.revise","classlist.update",
			"studentemail.sendmail","studentemail.changeemail","content.delete","content.new","content.revise","content.read"};

	/** Run stats for specific date in case automatic stats did not run 
	* Sonette 10-Jun-2008: Stats did not run from 3 June 2008 to 9 June 2008, run them daily 
	**/
	public void executeLocked(JobExecutionContext context)
			throws JobExecutionException {

		log = LogFactory.getLog(this.getClass());
		outputStream = new ByteArrayOutputStream();
		output = new PrintWriter(outputStream);

		System.out.println("in MyUnisaStatsJob");
		runcount++;

		log.info(runcount + ": << MyUnisaStatsJob>> Started.");

		MyUnisaStatsHistoryDAO dao = new MyUnisaStatsHistoryDAO();
		String[] dateRange = {"2010-07-13","2010-06-29","2010-07-23","2010-08-03","2010-08-07"};

		/** ****************************************************************************************************************/
		/** Set date for which stats must run: YYYY-MM-DD */
		for (int i = 0; i< dateRange.length; i++) {
			String currentDate = dateRange[i].toString();
			String feedbackMessage = "<html><body>";
	
			System.out.println("FOR DAY <h1> LMSMIS Login Stats feedback for day: "+currentDate+" </h1>");
			feedbackMessage = feedbackMessage+"<h1> LMSMIS Login Stats feedback for day: "+currentDate+" </h1>";
			String errorMessage = "";
			
			/** ****************************************************************************************************************/
			/** ALL USER LOGINS
			 * daily
			 * CATEGORY=GATEWAY
			 * USER_TYPE=STUDENT(S)/LECTURER(L)
			 */
			feedbackMessage = feedbackMessage + "<hr> USER LOGIN EVENTS <br>";
			try{
				errorMessage = dao.getUserLoginDaily("user.login",currentDate);
				if (errorMessage.length()> 0) {
					feedbackMessage = feedbackMessage+"Event=user.login <b> Error: </b> "+errorMessage+"<br>";
				} else {
					feedbackMessage = feedbackMessage+"Event=user.login <br> Success <br>";
				}
			} catch (Exception ex) {
				//throw new Exception("MyUnisaStatsJob: dao.getEventStats: Error occurred / "+ ex,ex);
			}

			feedbackMessage = feedbackMessage+"</body></html>";

			/** ****************************************************************************************************************/
			String emailSubject = "LMSMIS STATS FEEDBACK "+currentDate;
			try {
				sendEmail(emailSubject,feedbackMessage,"syzelle@unisa.ac.za");
			} catch (AddressException e) {
				log.info("error sending mail from LMSMIS STATS to syzelle@unisa.ac.za");
			}
		} // end for dateRange

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