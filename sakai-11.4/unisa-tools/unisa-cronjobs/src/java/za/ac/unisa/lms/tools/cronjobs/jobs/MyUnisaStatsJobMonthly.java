package za.ac.unisa.lms.tools.cronjobs.jobs;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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

public class MyUnisaStatsJobMonthly extends SingleClusterInstanceJob implements StatefulJob, OutputGeneratingJob {

	/**
	 * MyUnisaStatsJobMonthly
	 * To run stats Monthly
	 */

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
			"payments.ccpayment","contactus.emaildepartment","contactus.displaystudentnumber"};
	private String[] gatewayMonthlyEvents = {"ebookshop.view" ,"join.view","rejoin.view","password.view","ebookshop.edit","ebookshop.update",
			"ebookshop.add","ebookshop.delete"};
	private String[] gatewayDailyEvents = {"join.activation","password.activate","password.sendemail"};
	private String[] helpdeskEvents = {"audit.view"};

	private String[] siteViewEvents = {"classlist.view","faqs.contentview","faqs.view","studentemail.view","welcome.view"};
	private String[] siteEvents = {"discussionforums.add","discussionforums.addreply","discussionforums.addtopic","discussionforums.delete",
			"discussionforums.edit","discussionforums.deletereply","discussionforums.deletetopic",
			"disc.delete.category","disc.delete.own","disc.new","disc.new.category",
			"faqs.categoryadd","faqs.contentadd","faqs.categorydelete","faqs.categoryedit","faqs.contentedit",
			"welcome.update","welcome.revert","annc.new","annc.delete.own","annc.revise.own","annc.delete.any","annc.revise.any",
			"calendar.new","calendar.revise","classlist.update",
			"studentemail.sendmail","studentemail.changeemail","content.delete","content.new","content.revise","content.read"};

	public void executeLocked(JobExecutionContext context)
			throws JobExecutionException {

		log = LogFactory.getLog(this.getClass());
		outputStream = new ByteArrayOutputStream();
		output = new PrintWriter(outputStream);
		String feedbackMessage = "<html><body>";
		feedbackMessage = feedbackMessage+"<h1> LMSMIS History Stats feedback</h1>";
		String errorMessage = "";

		runcount++;

		log.info(runcount + ": Started.");

		MyUnisaStatsHistoryDAO dao = new MyUnisaStatsHistoryDAO();
		MyUnisaStatsStudentOracleDAO dao1 = new MyUnisaStatsStudentOracleDAO();

		/** ****************************************************************************************************************/
		// RUN DAILY STATS FOR month
		GregorianCalendar startDate1 = new GregorianCalendar(2008,8,30);
	    GregorianCalendar endDate1 = new GregorianCalendar(2008,10,1);
	    GregorianCalendar calcDate1 = new GregorianCalendar(2008,9,1);

	    feedbackMessage = feedbackMessage +"<hr><b>DAILY STATS </b><br>";
	    while ((calcDate1.after(startDate1))&&(calcDate1.before(endDate1))) {

	    	String tmpDate = ""; //YYYY-MM-DD
	    	tmpDate = calcDate1.get(Calendar.YEAR)+"-";
	    	int tmpMonth = calcDate1.get(Calendar.MONTH);
	    	if (tmpMonth <= 9) {
	    		tmpDate = tmpDate+"0"+tmpMonth+"-";
	    	} else {
	    		tmpDate = tmpDate+tmpMonth+"-";
	    	}
	    	int tmpDay = calcDate1.get(Calendar.DAY_OF_MONTH);
	    	if (tmpDay <= 9) {
	    		tmpDate = tmpDate+"0"+tmpDay;
	    	} else {
	    		tmpDate = tmpDate+tmpDay;
	    	}
	    	System.out.println("calcDate: "+calcDate1);

	    	for (int i=0; i< gatewayDailyEvents.length; i++) {
	    		try{
	    			errorMessage = dao.getEventStats(gatewayDailyEvents[i],tmpDate,"d","gateway","A");
	    			if ((null != errorMessage)||(errorMessage.length()> 0)) {
	    				//System.out.println(gatewayDailyEvents[i]+" Error: "+errorMessage);
						feedbackMessage = feedbackMessage + "<br>"+gatewayDailyEvents[i]+": <b>Error: </b> "+errorMessage;
					}
	    		} catch (Exception ex) {
	    			System.out.println(">>ERROR<<: MyUnisaStatsJob: "+ex);
	    			//throw new Exception("MyUnisaStatsJob: dao.getEventStats: Error occurred / "+ ex,ex);
	    		}
	    	}

	    	try {
				dao.getUserLoginDaily("user.login",tmpDate);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(">>ERROR<<: MyUnisaStatsJobMonhtly: "+e);
				//e.printStackTrace();
			}
			
			/** ****************************************************************************************************************/
			/**
			 * JOINED STUDENTS:
			 * DAILY
			 * CAGEGORY=GATEWAY;
			 * USER_TYPE=S
			 * */
			feedbackMessage = feedbackMessage + "JOINED STUDENTS <br>";
			try{
				errorMessage = dao.getJoinActivationStatistics(tmpDate);
				if ((null != errorMessage)||(errorMessage.length()> 0)) {
					feedbackMessage = feedbackMessage + "<br>Join activation MIS count error on "+tmpDate+": <b>Error: </b> "+errorMessage;
				}
			} catch (Exception ex) {
				//throw new Exception("MyUnisaStatsJob: dao.getEventStats: Error occurred / "+ ex,ex);
			}


			/** ****************************************************************************************************************/
			/**
			 * ACTIVE STUDENTS ACCORDING TO STUANN:
			 * DAILY
			 * CAGEGORY=GATEWAY;
			 * USER_TYPE=S
			 * */
			feedbackMessage = feedbackMessage + "JOINED STUDENTS <br>";
			try{
				errorMessage = dao1.getActiveStudents(tmpDate);
				if ((null != errorMessage)||(errorMessage.length()> 0)) {
					feedbackMessage = feedbackMessage + "<br>Join activation MIS count error on "+tmpDate+": <b>Error: </b> "+errorMessage;
				}
			} catch (Exception ex) {
				//throw new Exception("MyUnisaStatsJob: dao.getEventStats: Error occurred / "+ ex,ex);
			}

	    	calcDate1.add(Calendar.DATE, 1);
	    }

		feedbackMessage = feedbackMessage+"</body></html>";

		String emailSubject = "LMSMIS STATS FEEDBACK FOR SEPTEMBER 2008";
		try {
			sendEmail(emailSubject,feedbackMessage,"syzelle@unisa.ac.za");
		} catch (AddressException e) {
			log.info("error sending mail from LMSMIS HISTORY STATS to syzelle@unisa.ac.za");
		}

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
