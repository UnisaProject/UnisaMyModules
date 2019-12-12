package za.ac.unisa.lms.tools.cronjobs.forms.model;
import java.util.List;
import java.util.Iterator;
public class CoordinatorMailBody {
   
	private String firstPartOfEmailMessage(String coordName){
	          String emailMessage="<html><body><br><br>Dear  "+coordName+"<br><br>"+
              "Please note that emails have been sent to the following Supervisors who have  students in their care.<br><br>";
                        
	          return emailMessage;
	}
	private String listSupervisorsEmailBody(List supervisorList)throws Exception{
                     String  lastPartOfEmailBody="<table cellspacing='0' border='1'>";
                     String header="<tr><th style='align:left'>Supervisor</th><th  style='align:left'>Email</th><th  style='align:left'>Total Allocated Students</th></tr>";
                     lastPartOfEmailBody+=header;
                     String tableEntries=getSupervisorPlacementInfo(supervisorList);
  	                 lastPartOfEmailBody+=tableEntries;
  	                 lastPartOfEmailBody+="</table>";
                   return lastPartOfEmailBody;
    }
	private String getSupervisorPlacementInfo(List supervisorList)throws Exception{
		             Iterator i=supervisorList.iterator();
		             String  row="";
		             while(i.hasNext()){
		            	        Supervisor supervisor=(Supervisor)i.next();
		            	        String EmailAddressStr=supervisor.getEmailAddress();
		              	        if((supervisor.getEmailAddress()==null)||(supervisor.getEmailAddress().trim().equals(""))){
		              		        EmailAddressStr="No email address, email was not sent, <br>please fix  the email address of the supervisor";
		              	        }
		                        row+=  "<tr><td>"+supervisor.getName()+"</td>"+
                                       "<td>"+EmailAddressStr+"</td><td>"+supervisor.getStudentsAllocated()+"</td>"+
          		                        "</tr>";
		             }
                     return row;
   }
   public String createEmailBody(String  coordinatorName,List supervList)throws Exception{
    	                String emailBody= firstPartOfEmailMessage( coordinatorName)+listSupervisorsEmailBody(supervList)+
                                          "</body></html>";
	                 return emailBody;
   } 
   
    
}
