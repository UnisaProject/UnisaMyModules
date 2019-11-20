package za.ac.unisa.utils;


import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.cover.EmailService;

import za.ac.unisa.lms.dao.EmailAddressVerificationDAO;

public class EmailAddressVerification {

	public static Log log = LogFactory.getLog(EmailAddressVerification.class);

	/**
	 * This method sends an e-mail address verification email to the student.
	 * The student needs to have an act_code code on table solact, thus already
	 * registered on myUnisa.
	 *
	 * @param studentNr     				The student's number
	 * @param studentEmailAddress	  The student's email address
	 */

	public void sendVerificationEmail(String studentNr,String studentEmailAddress) {

		try {

			String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");
			InternetAddress toEmail = new InternetAddress(studentEmailAddress);
			InternetAddress iaTo[] = new InternetAddress[1];
			iaTo[0] = toEmail;
			InternetAddress iaHeaderTo[] = new InternetAddress[1];
			iaHeaderTo[0] = toEmail;
			InternetAddress iaReplyTo[] = new InternetAddress[1];
			iaReplyTo[0] = new InternetAddress(tmpEmailFrom);

			/* Get activation code */
			String code = "";
			EmailAddressVerificationDAO dao = new EmailAddressVerificationDAO();
			code = dao.getActivationCode(studentNr);
			if ("".equals(code)){
				throw new Exception("EmailAddressVerification Error: act_code on solact missing!");
			}

			/* Setup path to contact details action */
			String verificationPath = ServerConfigurationService.getServerUrl()+"/unisa-biodetails/verifyemail.do?action=verify";
			//String verificationPath2 = "https://"+ServerConfigurationService.getServerName()+"/unisa-biodetails/verifyemail.do?action=verify";
			verificationPath = verificationPath +"&studno="+studentNr+"&code="+code;

			/* send activation email to student */
			String subject = "myUnisa e-mail address verification";
			String body = "<html> "+
		  "<body> "+
				"Dear student,  <br/><br/>"+
        "NB: This is an automated response - do not reply to this e-mail.  <br/><br/> "+
        "You are receiving this e-mail in response to updating your e-mail address on myUnisa.  <br/><br/> You must verify your new address before "+
        "you will receive e-mail from UNISA.  <br/><br/>"+
        "Please click on this link in order to verify your e-mail address: <br/>"+
        "<A href='"+verificationPath+"' target='_blank'><b>Verify my e-mail address</b></A>"+
			"</body>"+
			"</html>";

		List contentList = new ArrayList();
		contentList.add("Content-Type: text/html");

		EmailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
		log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
		} catch (Exception e) {
			log.error("Unhandled exception: "+e+" "+e.getMessage());
			e.printStackTrace();
		}
	}

}
