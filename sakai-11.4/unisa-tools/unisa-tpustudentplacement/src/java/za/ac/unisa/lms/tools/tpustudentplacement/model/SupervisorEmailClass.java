package za.ac.unisa.lms.tools.tpustudentplacement.model;
import za.ac.unisa.lms.tools.tpustudentplacement.model.Email.EmailImpl;
import za.ac.unisa.lms.tools.tpustudentplacement.model.Email.EmailLogUI;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl.StudentPlacementUI;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.DateUtil;
import  za.ac.unisa.lms.tools.tpustudentplacement.forms.Supervisor;
import org.sakaiproject.component.cover.ServerConfigurationService;
public class SupervisorEmailClass {

	 public void sendEmailToSupervisor(Supervisor  supervisor) throws Exception{
    	                       DateUtil dateUtil=new DateUtil();
    	                       SupervisorMailBody  smb=new SupervisorMailBody();
    	                       int supervCode=supervisor.getCode();
    	                       int curryear=dateUtil.getYearInt();
    	                       EmailImpl emailImpl=new EmailImpl();
    	                       emailImpl.setBody(smb.createEmailBody(supervisor.getName(),supervisor.getCode()));
    	                       String emailAddr=supervisor.getEmailAddress();
    	                       prepareAdressing(emailAddr,emailImpl);
    	                       String subject="Unisa Teaching Practice Placement Information";
    	                       emailImpl.setSubject(subject);
    	                       emailImpl.sendEmail();
    	                       String totStuAllocated=supervisor.getStudentsAllocated(supervCode,curryear);
    	                       EmailLogUI emailLogUI=new  EmailLogUI();
    	                       emailLogUI.writeEmailLog(supervCode,emailAddr,"Students allocated :"+totStuAllocated,subject);
    	                       updateEmailSentFieldPlacement(supervCode);
     }
     
     private void prepareAdressing(String supervisorEmail, EmailImpl emailImpl){
    	                String toEmail=supervisorEmail;
    	                String serverpath = ServerConfigurationService.getServerUrl();
    	                if (serverpath.contains("myqa")){
		  			    	          toEmail="RAMOBPT@UNISA.AC.ZA";
		  			    }else if(serverpath.contains("localhost")){
		  			    	    	  toEmail="baloyar@unisa.ac.za";
		  			    }else if(serverpath.contains("mydev")){
  			    	    	          toEmail="baloyar@unisa.ac.za";
		  			    }
    	                emailImpl.setToEmail(toEmail);
    	                emailImpl.setFromEmail("teachprac@unisa.ac.za");
     }
     private void updateEmailSentFieldPlacement(int supervisoCode)throws Exception{
                       StudentPlacementUI  sp=new StudentPlacementUI();
                       sp.updateEmailToSupField(supervisoCode);
    }
     
}