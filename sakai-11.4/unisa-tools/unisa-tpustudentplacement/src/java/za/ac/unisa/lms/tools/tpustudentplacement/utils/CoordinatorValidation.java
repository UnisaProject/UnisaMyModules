package za.ac.unisa.lms.tools.tpustudentplacement.utils;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import za.ac.unisa.lms.tools.tpustudentplacement.model.Coordinator;

public class CoordinatorValidation {
	private static final String sadecIntProvError="If SADEC and International Indicator is set to 'Yes' then 'NONE' must be selected for the Workstation";	
	 public static final int sadecIntCode=21;
		public   void  validateSelection(ActionMessages messages,String[]  selectedIndexArr){
	                    if (selectedIndexArr==null ||
			                  selectedIndexArr.length==0){
		                       messages.add(ActionMessages.GLOBAL_MESSAGE,
				                       new ActionMessage("message.generalmessage",
							                     "Please select a coordinator"));
	                   }
                        if (selectedIndexArr!=null &&
    		                    selectedIndexArr.length>1){
		                             messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage",
							              "Please select only one coordinator"));
	                   }
	}
   public void validateInput(Coordinator coordinator,ActionMessages messages){
	               PlacementUtilities placementUtilities=new PlacementUtilities();
	               if (placementUtilities.isStringEmpty(coordinator.getNetworkCode())){
		                    messages.add(ActionMessages.GLOBAL_MESSAGE,
				               new ActionMessage("message.generalmessage","Invalid Network Code "));
		        
                  }
	              if (coordinator.getSadecInt().trim().equals("Y")){
		                 if (!coordinator.getWorkStationCode().equals(""+sadecIntCode)){
		                        messages.add(ActionMessages.GLOBAL_MESSAGE,
				                new ActionMessage("message.generalmessage",sadecIntProvError));
		                 }
	              }
	              if (coordinator.getSadecInt().trim().equals("N")){
			            if (coordinator.getWorkStationCode().equals(""+sadecIntCode)){
			                   messages.add(ActionMessages.GLOBAL_MESSAGE,
					             new ActionMessage("message.generalmessage",
					                 "Please select a province "));
			          }
	            }
	            EmailValidater emailValidator = new EmailValidater();
                emailValidator.validateEmailAddressr(coordinator.getEmailAddress(), messages);
   }
}
