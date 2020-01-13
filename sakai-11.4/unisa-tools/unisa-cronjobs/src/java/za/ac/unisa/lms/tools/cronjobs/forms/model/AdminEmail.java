package za.ac.unisa.lms.tools.cronjobs.forms.model;
import org.sakaiproject.component.cover.ServerConfigurationService;
import za.ac.unisa.lms.tools.cronjobs.forms.model.Email;
import  za.ac.unisa.lms.tools.cronjobs.forms.model.StudentPlacement;
import java.util.Set;
public class AdminEmail extends Email{

	
	 public  AdminEmail(){
		      adminEmailBody=new AdminEmailBody();
		
	 }
	 AdminEmailBody adminEmailBody;
	 private boolean successfulRun=true;
	 public void setSuccessfulRun(boolean successfulRun){
		             this.successfulRun=successfulRun;
	 }
	 public boolean isSuccessfulRun(){
		       return successfulRun;
	 }
	public void sendEmailForSuccessfulRun() throws Exception{
		               String emailBody=adminEmailBody.msgForSuccessfulRun();
                       if(!adminEmailBody.isSummaryDataCompiled()){
                    	      emailBody=adminEmailBody.msgForSuccesfulRunNoSummary();
                       }
                       setEmailVariables(emailBody);
                       sendEmail();
     }
	 public void sendEmailForFailedRun(String exceptionMessage) throws Exception{
		                 String emailBody=adminEmailBody.msgForFailedRun(exceptionMessage);
		                 setEmailVariables(emailBody);
                         sendEmail();
     }
	 public void setEmailVariables(String emailBody) throws Exception{
                   String emailAddress="baloyar@unisa.ac.za";
                   subject="Unisa Teaching Practice Supervisor Email Batch Program Report";
                   body=emailBody;
                   prepAdressing(emailAddress);
     }
	 
	 public void setSummaryDataBeforeRun(int supervCodeListSize)throws Exception{
		             adminEmailBody.setNumOfSupsInPlacements(supervCodeListSize);
		             StudentPlacement  sp=new StudentPlacement();
                     int totPlacementAccessed=sp.getTotPlacmtEmailNotSend();
                     adminEmailBody.setTotPlacementsBeforeRun(totPlacementAccessed);
     }
	public void setSummaryDataAfterRun(int supervCodeListSize,int totEmailsSentToCoord,
			              int totEmailsSentToSup)throws Exception{
		                        adminEmailBody.setNumOfSupMailNotSend(supervCodeListSize);
		                        adminEmailBody.setTotEmailsSentToSup(totEmailsSentToSup);
		                        adminEmailBody.setTotEmailsSentToCoord(totEmailsSentToCoord);
    }
	public void  prepAdressing(String email){
                      toEmail=email;
                      fromEmail="teachprac@unisa.ac.za";
                      String serverpath = ServerConfigurationService.getServerUrl();
                      if(serverpath.contains("myqa")){
                    	     toEmail="masanmr@unisa.ac.za";
                      }
   }
	public void setSupervErrorList(Set supervErrorList ){
		             adminEmailBody.setSupervErrorList(supervErrorList);
	}
	 
}
