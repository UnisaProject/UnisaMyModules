package za.ac.unisa.lms.tools.cronjobs.forms.model;

import javax.mail.internet.InternetAddress;
import java.util.List;
import java.util.ArrayList;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.component.cover.ComponentManager;

public class Email{
	
	protected String toEmail="",fromEmail="";
	protected String subject="",body="";
	
	public String getToEmail(){
		return toEmail;
	}
	public void setToEmail(String toEmail){
		      this.toEmail=toEmail;
	}
	public void setFromEmail(String fromEmail){
		      this.fromEmail=fromEmail;
	}
	public String getFromEmail(){
		return fromEmail;
	}
	public void setBody(String body){
	      this.body=body;
    }
    public String getBody(){
	   return body;
    }
    public String getSubject(){
       return subject;
    }
    public void setSubject(String subject){
    	  this.subject=subject;
    }
	public void sendEmail() {
		
		try{
		String serverPath = ServerConfigurationService.getToolUrl();
		InternetAddress toAddress = new InternetAddress(toEmail);
		InternetAddress iaTo[] = new InternetAddress[1];
		iaTo[0] = toAddress;
		InternetAddress iaHeaderTo[] = new InternetAddress[1];
		iaHeaderTo[0] = toAddress;
		InternetAddress iaReplyTo[] = new InternetAddress[1];
		iaReplyTo[0] = new InternetAddress(fromEmail);
		/* Setup path to contact details action addressee */
		String myUnisaPath = ServerConfigurationService.getServerUrl();
		List contentList = new ArrayList();
		contentList.add("Content-Type: text/html");
		EmailService emailService = (EmailService)ComponentManager.get(EmailService.class); 
		emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
		}catch(Exception ex){}
   }
   public void  prepAdressing(String email){
                  toEmail=email;
                  fromEmail="teachprac@unisa.ac.za";
                  // send email to baloyar@unisa.ac.za on localhost,pretoj@uisa.ac.za on dev
                  //to masanmr@unisa.ac.za  on 	
                  String serverpath = ServerConfigurationService.getServerUrl();
                  if(serverpath.contains("myqa")){
                     	 toEmail="baloyar@unisa.ac.za";
                  }else if(serverpath.contains("localhost")){
	    			    	toEmail="baloyar@unisa.ac.za";
                  }else if(serverpath.contains("mydev")){
          	                 //toEmail= "pretoj@unisa.ac.za";
          	                 toEmail="baloyar@unisa.ac.za";
                  } 
     }

}
