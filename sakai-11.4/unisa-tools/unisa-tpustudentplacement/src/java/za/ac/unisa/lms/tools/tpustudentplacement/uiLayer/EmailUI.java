package za.ac.unisa.lms.tools.tpustudentplacement.uiLayer;
import org.sakaiproject.component.cover.ServerConfigurationService;

import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.model.Email.EmailImpl;;

public class EmailUI {

	EmailImpl emailImpl;
	public  EmailUI(){
		      emailImpl=new EmailImpl();
	}
	
	public String  sendEmail(){
		           //setTestersEmails();
		            try{
		                  emailImpl.sendEmail();
		                  return "Email was sent successfully";
		            }catch(Exception ex){
		            	return "Email was not Send  "+ex.getMessage();
		            }
	}
	 private void setTestersEmails(){
                   String serverpath = ServerConfigurationService.getServerUrl();
		            if (serverpath.contains("mydev")){
        		             emailImpl.setToEmail("pretoj@unisa.ac.za");
                    }else if(serverpath.contains("myqa")) {
                             //"masanmr@unisa.ac.za") 
                        	 //"setshrh@unisa.ac.za") 
                    		   emailImpl.setToEmail("ramobpt@unisa.ac.za");
			         }else{
	                	       emailImpl.setToEmail("baloyar@unisa.ac.za");
                     }			
     }
	 public void setEmailData(String body,String toEmail,
	         StudentPlacementListRecord placementListRec){
		         String fromEmail="teachprac@unisa.ac.za";
		         emailImpl.setSubject("Teaching Practice Placement Letter");
		         emailImpl.setToEmail(toEmail);
		         emailImpl.setFromEmail(fromEmail);
                 emailImpl.setBody(body);
    }
}
//0745126573