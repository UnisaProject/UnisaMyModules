package org.sakaiproject.studymaterial.impl;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.logging.Log;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.studymaterial.dao.EmailDAO;
import org.sakaiproject.studymaterial.module.ExamPaperModel;
import org.sakaiproject.studymaterial.utils.Utilities;

public class Email {
	
	String siteId;
	String courseCode;
	public Email(String siteId){
		this.siteId=siteId;
		setSubject(siteId);
		
	}
	public  void sendEmail(ExamPaperModel examPaperModel,Log log) {
		try{
		     String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");
		InternetAddress toEmail = new InternetAddress(emailAddress);
		InternetAddress iaTo[] = new InternetAddress[1];
		iaTo[0] = toEmail;
		InternetAddress iaHeaderTo[] = new InternetAddress[1];
		iaHeaderTo[0] = toEmail;
		InternetAddress iaReplyTo[] = new InternetAddress[1];
		iaReplyTo[0] = new InternetAddress(tmpEmailFrom);
		List contentList = new ArrayList();
		contentList.add("Content-Type: text/html");
		setEmailAddress(courseCode);
		EmailService emailService = (EmailService) ComponentManager.get(EmailService.class);
		setEmailBody(examPaperModel);
	    emailService.sendMail(iaReplyTo[0],iaTo,subject,emailBody,iaHeaderTo,iaReplyTo,contentList);
		}catch(Exception ex){
			 log.debug("Failed to send email informing the Exam department about missing Exam paper  for course :"+siteId);
		}
	} // end of sendEmail	
      String subject;
    public void setSubject(String siteId){
    	        	subject="Exam Question Paper File does not exist from "+siteId;
    }
    String emailAddress;
    public void setEmailAddress(String coursecode) throws Exception{
    	          EmailDAO dao = new EmailDAO();
    	          this.emailAddress=dao.getEmailID(coursecode);
                  if(Utilities.isTestEnvironment()){
                	  emailAddress="baloyar@unisa.ac.za";
     	          }
    }
    private String emailBody;
    public void setEmailBody(ExamPaperModel examPaperModel){
    	  emailBody="Hallo <p>"+						
 			   "A request via the official Study Material tool in myUnisa was made for "+courseCode+"-"+examPaperModel.getModule()+"-"+examPaperModel.getPeriod()+"-"+examPaperModel.getLanguage()+"-"+examPaperModel.getUnitNumber()+".pdf <p>"+ 
 			   "The file does not exist/had gone missing on Examination Question Paper archive.<br>"+"Please attend to this matter urgently. <p>"+
                "Regards <br>myUnisa Support Team ";
	              
    }
}
