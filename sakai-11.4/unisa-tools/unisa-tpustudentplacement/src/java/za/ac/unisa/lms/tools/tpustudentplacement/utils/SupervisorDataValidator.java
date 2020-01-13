package za.ac.unisa.lms.tools.tpustudentplacement.utils;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Supervisor;
import org.apache.struts.action.ActionMessages;

public class SupervisorDataValidator {
	
	private void validateSupervPersonalData(Supervisor supervisor,ActionMessages messages){
		                    InfoMessagesUtil  infoMessagesUtil =new InfoMessagesUtil();
		                    String message="Supervisor initials are required";
	                        if (supervisor.getInitials()==null || 
	                        		supervisor.getInitials().trim().equalsIgnoreCase("")){
		             		              infoMessagesUtil.addMessages(messages,message);
						    }
                            if (supervisor.getSurname()==null ||
                            		supervisor.getSurname().trim().equalsIgnoreCase("")){
	           					         message="Supervisor surname is required";
	           				    	      infoMessagesUtil.addMessages(messages,message);
                            }
                            if (!supervisor.getCountryCode().equalsIgnoreCase("1015")
		                         && supervisor.getListArea()!=null
		                         && supervisor.getListArea().size()>0){
                            	         message="Supervisor can only be linked to areas if country is SOUTH AFRICA."+
		                                         "Remove supervisor's areas first.";
                            	        infoMessagesUtil.addMessages(messages,message);
						  }
                          if (supervisor.getCountryCode().equalsIgnoreCase("1015") && 
                        		 supervisor.getListArea().size()==0){
                        	       message="Supervisor must be linked to at least one area.";
                        	       infoMessagesUtil.addMessages(messages,message);
                         }
	  }
	
	private void validateSupervContractData(Supervisor supervisor,ActionMessages messages){
                              InfoMessagesUtil  infoMessagesUtil =new InfoMessagesUtil();
                              if (supervisor.getContract().equalsIgnoreCase("Y")){
                            	       validateContractStartDate(supervisor.getContractStart(),infoMessagesUtil,messages);
         	                           validatetEndDate(supervisor.getContractEnd(),infoMessagesUtil, messages);
                              }
       }	
	                          
	    private  void validateContractStartDate(String contractStartDate,InfoMessagesUtil  infoMessagesUtil,ActionMessages messages){
	                       	 String message="Contract start date is required, if supervisor has a contract";
	                       	 String secErroMessage="Invalid Contract start date";
	                       	 validateContractDate(contractStartDate,infoMessagesUtil,messages,message,secErroMessage);
	    }
	    private  void validatetEndDate(String contractEndDate,InfoMessagesUtil  infoMessagesUtil,ActionMessages messages){
          	                 String message ="Contract end date is required, if supervisor has a contract";
          	                 String secErroMessage="Invalid Contract end date";
          	                 validateContractDate(contractEndDate,infoMessagesUtil,messages,message,secErroMessage);
	    }
         private  void validateContractDate(String contractDate,InfoMessagesUtil  infoMessagesUtil,ActionMessages messages,
        		                String message,String secErroMessage){
        	                              PlacementUtilities dateValidator = new PlacementUtilities();
                                              if (contractDate==null || 
                                            		  contractDate.trim().equalsIgnoreCase("")){
  	                                                    infoMessagesUtil.addMessages(messages,message);
                                               }else{
                                                       if (!dateValidator.isValidDate(contractDate, "yyyy/MM/dd")){
              	                                            infoMessagesUtil.addMessages(messages,secErroMessage);
                                                       }
                                               }
         }
         public void validatePersonalData(Supervisor supervisor,ActionMessages messages){
        	                  validateSupervPersonalData(supervisor,messages);
        	                  validateSupervContractData(supervisor,messages);
         }
}
