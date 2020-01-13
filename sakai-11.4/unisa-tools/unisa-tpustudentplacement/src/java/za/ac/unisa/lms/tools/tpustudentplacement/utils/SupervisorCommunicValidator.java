package za.ac.unisa.lms.tools.tpustudentplacement.utils;

import org.apache.struts.action.ActionMessages;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Supervisor;
import za.ac.unisa.utils.CellPhoneVerification;

public class SupervisorCommunicValidator extends  CommValidator{
	
	
    
          public void validatePhysicalAddressr(Supervisor supervisor,ActionMessages messages){
                             InfoMessagesUtil  infoMessagesUtil =new InfoMessagesUtil();
                             String message="Enter data in first line of Physical address before proceding to the next lines.";
	                         if ((supervisor.getPhysicalAddress1()==null || 
	                        		 supervisor.getPhysicalAddress1().trim().equalsIgnoreCase("")) 
			                          &&  ((supervisor.getPhysicalAddress2()!=null && 
			                                   !supervisor.getPhysicalAddress2().trim().equalsIgnoreCase(""))
					                         || (supervisor.getPhysicalAddress3()!=null && 
					                                !supervisor.getPhysicalAddress3().trim().equalsIgnoreCase("")))){
	                        	                     infoMessagesUtil.addMessages(messages,message);
	                       }
          }
          public void validatePostalAddressr(Supervisor supervisor,ActionMessages messages){
        	                InfoMessagesUtil  infoMessagesUtil =new InfoMessagesUtil();
        	                String message="Enter data in first line of Postal address before proceding to the next lines.";
                  	        if ((supervisor.getPostalAddress1()==null ||
                  			      supervisor.getPostalAddress1().trim().equalsIgnoreCase("")) 
			                       &&  ((supervisor.getPostalAddress2()!=null && 
			                         !supervisor.getPostalAddress2().trim().equalsIgnoreCase(""))
					                  || (supervisor.getPostalAddress3()!=null && 
					                       !supervisor.getPostalAddress3().trim().equalsIgnoreCase("")))){
                  	        	                infoMessagesUtil.addMessages(messages,message);
                  	        }
		
    	 }
          public void validatePostalCode(Supervisor supervisor,ActionMessages messages){
        	                   InfoMessagesUtil  infoMessagesUtil =new InfoMessagesUtil();
                               String message="Postal code is required if postal address is entered.";
                               if (supervisor.getCountryCode().equalsIgnoreCase("1015")){
	                               if (supervisor.getPostalAddress1()!= null 
			                            && !supervisor.getPostalAddress1().trim().equalsIgnoreCase("") 
			                               && (supervisor.getPostalCode()==null ||
			                                   supervisor.getPostalCode().trim().equalsIgnoreCase(""))){
         	        	                infoMessagesUtil.addMessages(messages,message);
	                               }
		       
	                         }
	     }
          public void validateCommunicData(Supervisor supervisor,ActionMessages messages){
        	                   validateCellNr(supervisor.getCellNr(),supervisor.getCountryCode(),messages);
        	                   validateEmailAddressr(supervisor.getEmailAddress(),messages);
        	                   validatePhysicalAddressr(supervisor,messages);
        	                   validatePostalAddressr(supervisor,messages);
        	                   validatePostalCode(supervisor,messages);
          }
}
