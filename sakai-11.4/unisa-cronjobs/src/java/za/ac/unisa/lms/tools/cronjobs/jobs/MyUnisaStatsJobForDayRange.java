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
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;

import za.ac.unisa.lms.tools.cronjobs.dao.MyUnisaStatsHistoryDAO;
import za.ac.unisa.lms.tools.cronjobs.dao.MyUnisaStatsStudentOracleDAO;
import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;

/** 
 * Sonette: Run stats for specific date range in case automatic stats did not run
 * 8 Oct 2008: Run stats from 19 Aug 2008 to 31 Aug 2008 and delete stats for certain days in oct that was run by mistake.
 * 9 Oct 2008: Run stats from 2 Oct 2008 to 5 Oct 2008
**/
public class MyUnisaStatsJobForDayRange extends SingleClusterInstanceJob implements StatefulJob, OutputGeneratingJob {
	
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


	public void executeLocked(JobExecutionContext context)
			throws JobExecutionException {

		log = LogFactory.getLog(this.getClass());
		outputStream = new ByteArrayOutputStream();
		output = new PrintWriter(outputStream);

		System.out.println("in MyUnisaStatsJobForDayRange");
		runcount++;

		log.info(runcount + ": << MyUnisaStatsJobForDayRange>> Started.");
		
		MyUnisaStatsHistoryDAO dao = new MyUnisaStatsHistoryDAO();
		MyUnisaStatsStudentOracleDAO dao1 = new MyUnisaStatsStudentOracleDAO();
	
		/** Delete incorrect stats for October YYYY-MM-DD*/
		/*
		try {
			dao.deleteEventsForDay("2008-10-02");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/

		/** ****************************************************************************************************************/
		/** Set date for which stats must run: YYYY-MM-DD */
		GregorianCalendar startDate1 = new GregorianCalendar(2010,06,16);
	    GregorianCalendar endDate1 = new GregorianCalendar(2010,06,25);
	    GregorianCalendar calcDate1 = new GregorianCalendar(2010,06,17);
	    
	    while ((calcDate1.after(startDate1))&&(calcDate1.before(endDate1))) {

	    	boolean endOfMonth = false;
	    	String currentDate = ""; //YYYY-MM-DD
	    	currentDate = calcDate1.get(Calendar.YEAR)+"-";
	    	int tmpMonth = calcDate1.get(Calendar.MONTH);
	    	if (tmpMonth <= 9) {
	    		currentDate = currentDate+"0"+tmpMonth+"-";
	    	} else {
	    		currentDate = currentDate+tmpMonth+"-";
	    	}
	    	int tmpDay = calcDate1.get(Calendar.DAY_OF_MONTH);
	    	if (tmpDay <= 9) {
	    		currentDate = currentDate+"0"+tmpDay;
	    	} else {
	    		currentDate = currentDate+tmpDay;
	    	}
	    	if (tmpDay == 30) {
	    		endOfMonth = true;
	    	}
			
			String feedbackMessage = "<html><body>";
			System.out.println("<h1> LMSMIS Stats feedback for day: "+currentDate+" </h1>");
	
			feedbackMessage = feedbackMessage+"<h1> LMSMIS Stats feedback for day: "+currentDate+" </h1>";
			String errorMessage = "";
	
			/** ****************************************************************************************************************/
			/**
			 * JOINED STUDENTS:
			 * DAILY
			 * CAGEGORY=GATEWAY;
			 * USER_TYPE=S
			 * */
			feedbackMessage = feedbackMessage + "JOINED STUDENTS <br>";
			try{
				errorMessage = dao.getJoinActivationStatistics(currentDate);
				if ((null != errorMessage)||(errorMessage.length()> 0)) {
					feedbackMessage = feedbackMessage + "<br>Join activation MIS count error on "+currentDate+": <b>Error: </b> "+errorMessage;
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
				errorMessage = dao1.getActiveStudents(currentDate);
				if ((null != errorMessage)||(errorMessage.length()> 0)) {
					feedbackMessage = feedbackMessage + "<br>Join activation MIS count error on "+currentDate+": <b>Error: </b> "+errorMessage;
				}
			} catch (Exception ex) {
				//throw new Exception("MyUnisaStatsJob: dao.getEventStats: Error occurred / "+ ex,ex);
			}
	
			/** ****************************************************************************************************************/
			/**
			 * ADMIN EVENTS:
			 * MONTHLY
			 * CAGEGORY=ADMIN;
			 * USER_TYPE=EMPTY
			 * */
			feedbackMessage = feedbackMessage + "ADMIN EVENTS <br>";
			for (int i=0; i< adminEvents.length; i++) {
				try{
					errorMessage = dao.getEventStats(adminEvents[i],currentDate,"m","admin","A");
					if (errorMessage.length()> 0) {
						feedbackMessage = feedbackMessage+"Event="+adminEvents[i]+"<b> Error: </b> "+errorMessage+"<br>";
					} else {
						feedbackMessage = feedbackMessage+"Event="+adminEvents[i]+"<br> Success <br>";
					}
				} catch (Exception ex) {
					//throw new Exception("MyUnisaStatsJob: dao.getEventStats: Error occurred / "+ ex,ex);
				}
			} // end for
	
			/** ****************************************************************************************************************/
			/** GATEWAY EVENTS
			 * MONTHLY
			 * CATEGORY = GATEWAY
			 * USER_TYPE =EMPTY
			 */
			feedbackMessage = feedbackMessage + "<hr> GATEWAY MONTHLY EVENTS <br>";
			for (int i=0; i< gatewayMonthlyEvents.length; i++) {
				try{
					errorMessage = dao.getEventStats(gatewayMonthlyEvents[i],currentDate,"m","gateway","A");
					if (errorMessage.length()> 0) {
						feedbackMessage = feedbackMessage+"Event="+gatewayMonthlyEvents[i]+"<b> Error: </b> "+errorMessage+"<br>";
					} else {
						feedbackMessage = feedbackMessage+"Event="+gatewayMonthlyEvents[i]+"<br> Success <br>";
					}
				} catch (Exception ex) {
					//throw new Exception("MyUnisaStatsJob: dao.getEventStats: Error occurred / "+ ex,ex);
				}
			} // end for
	
			/** ****************************************************************************************************************/
			/** GATEWAY DAILY EVENTS
			 * DAILY
			 * CATEGORY= GATEWAY
			 * USER_TYPE = EMPTY
			 */
			feedbackMessage = feedbackMessage + "<hr> GATEWAY DAILY EVENTS <br>";
			for (int i=0; i< gatewayDailyEvents.length; i++) {
				try{
					errorMessage = dao.getEventStats(gatewayDailyEvents[i],currentDate,"d","gateway","A");
					if (errorMessage.length()> 0) {
						feedbackMessage = feedbackMessage+"Event="+gatewayDailyEvents[i]+"<b> Error: </b> "+errorMessage+"<br>";
					} else {
						feedbackMessage = feedbackMessage+"Event="+gatewayDailyEvents[i]+"<br> Success <br>";
					}
				} catch (Exception ex) {
					//throw new Exception("MyUnisaStatsJob: dao.getEventStats: Error occurred / "+ ex,ex);
				}
			} // end for
	
			/** ****************************************************************************************************************/
			/** HELPDESK EVENT
			 * MONTHLY
			 * CATEGORY=GATEWAY
			 * USER_TYPE=EMPTY
			 */
			feedbackMessage = feedbackMessage + "<hr> HELPDESK EVENTS <br>";
			for (int i=0; i< helpdeskEvents.length; i++) {
				try{
					errorMessage = dao.getEventStats(helpdeskEvents[i],currentDate,"m","helpdesk","A");
					if (errorMessage.length()> 0) {
						feedbackMessage = feedbackMessage+"Event="+helpdeskEvents[i]+"<b> Error: </b> "+errorMessage+"<br>";
					} else {
						feedbackMessage = feedbackMessage+"Event="+helpdeskEvents[i]+"<br> Success <br>";
					}
				} catch (Exception ex) {
					//throw new Exception("MyUnisaStatsJob: dao.getEventStats: Error occurred / "+ ex,ex);
				}
			} // end foR
	
			/** ****************************************************************************************************************/
			/**
			 * SITE VIEW EVENTS
			 * MONTHLY
			 * CATEGORY=GATEWAY
			 * USER_TYPE=EMPTY
			 */
			feedbackMessage = feedbackMessage + "<hr> SITE VIEW EVENTS <br>";
			for (int i=0; i< siteViewEvents.length; i++) {
				try{
					errorMessage = dao.getEventStatsPerSite(siteViewEvents[i],currentDate,"m","A");
					if (errorMessage.length()> 0) {
						feedbackMessage = feedbackMessage+"Event="+siteViewEvents[i]+"<b> Error: </b> "+errorMessage+"<br>";
					} else {
						feedbackMessage = feedbackMessage+"Event="+siteViewEvents[i]+"<br> Success <br>";
					}
				} catch (Exception ex) {
					//throw new Exception("MyUnisaStatsJob: dao.getEventStats: Error occurred / "+ ex,ex);
				}
			} // end foR
	
			/** ****************************************************************************************************************/
			/**
			 * SITE EVENTS
			 * MONTHLY
			 * CATEGORY=GATEWAY
			 * USER_TYPE=EMPTY
			 */
			feedbackMessage = feedbackMessage + "<hr> SITE EVENTS <br>";
			for (int i=0; i< siteEvents.length; i++) {
				try{
					errorMessage = dao.getEventStatsPerSite(siteEvents[i],currentDate,"m","A");
					if (errorMessage.length()> 0) {
						feedbackMessage = feedbackMessage+"Event="+siteEvents[i]+"<b> Error: </b> "+errorMessage+"<br>";
					} else {
						feedbackMessage = feedbackMessage+"Event="+siteEvents[i]+"<br> Success <br>";
					}
				} catch (Exception ex) {
					//throw new Exception("MyUnisaStatsJob: dao.getEventStats: Error occurred / "+ ex,ex);
				}
			} // end foR
	
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
	
			/** ****************************************************************************************************************/
			/** Unique USER LOGINS
			 * MONTHLY
			 * CATEGORY=GATEWAY
			 * USER_TYPE=STUDENT(S)/LECTURER(L)
			 */
			feedbackMessage = feedbackMessage + "<hr> UNIQUE USER LOGIN EVENTS <br>";
			if (endOfMonth == true) {
				try{
					errorMessage = dao.getUserLoginMonthlyHistory("user.login",currentDate);
					if (errorMessage.length()> 0) {
						feedbackMessage = feedbackMessage+"Event=user.login.unique <b> Error: </b> "+errorMessage+"<br>";
					} else {
						feedbackMessage = feedbackMessage+"Event=user.login.unique <br> Success <br>";
					}
				} catch (Exception ex) {
					//throw new Exception("MyUnisaStatsJob: dao.getEventStats: Error occurred / "+ ex,ex);
				}
			}
	
			feedbackMessage = feedbackMessage+"</body></html>";
	
			/** ****************************************************************************************************************/
			String emailSubject = "LMSMIS STATS FEEDBACK "+currentDate;
			try {
				sendEmail(emailSubject,feedbackMessage,"syzelle@unisa.ac.za");
			} catch (AddressException e) {
				log.info("error sending mail from LMSMIS STATS to syzelle@unisa.ac.za");
			}
			
			calcDate1.add(Calendar.DATE, 1);
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