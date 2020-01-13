package za.ac.unisa.lms.tools.tpustudentplacement.model.Email;
import java.util.ArrayList;
import java.util.List;
import javax.mail.internet.InternetAddress;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;

public class EmailImpl  extends EmailModul{
       
	
	
	public void sendEmail() throws Exception{
		
		
        String serverPath = ServerConfigurationService.getToolUrl();
        InternetAddress toAddress = new InternetAddress(getToEmail());
        InternetAddress iaTo[] = new InternetAddress[1];
        iaTo[0]=toAddress;
        InternetAddress iaHeaderTo[] = new InternetAddress[1];
        iaHeaderTo[0] = toAddress;
        InternetAddress iaReplyTo[] = new InternetAddress[1];
        iaReplyTo[0] = new InternetAddress(getFromEmail());
        /* Setup path to contact details action addressee */
         String myUnisaPath = ServerConfigurationService.getServerUrl();
         List contentList = new ArrayList();
         contentList.add("Content-Type: text/html");
         EmailService emailService = (EmailService)ComponentManager.get(EmailService.class); 
         emailService.sendMail(iaReplyTo[0],iaTo,getSubject(),
			  getBody(),iaHeaderTo,iaReplyTo,contentList);
} 

}
