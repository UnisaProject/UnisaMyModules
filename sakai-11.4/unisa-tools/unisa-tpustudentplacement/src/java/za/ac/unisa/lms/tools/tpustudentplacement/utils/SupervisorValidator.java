package za.ac.unisa.lms.tools.tpustudentplacement.utils;

import javax.servlet.http.HttpServletRequest;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Supervisor;
import org.apache.struts.action.ActionMessages;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.SupervisorListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.SupervisorUI;;

public class SupervisorValidator {
	
	        private static final String secMessage="Supervisor has already been allocated its maximum allowed"+
	                                                "students. Please select another supervisor.";
			private static final String message="The supervisor's contract has expired  or expires "+
	                                            "within 30 days. Please select another supervisor";
			
	       SupervisorUI supervisorUI;
	       InfoMessagesUtil infoMessagesUtil;
	        public SupervisorValidator(SupervisorUI supervisorUI){
		            this.supervisorUI=supervisorUI;
		            infoMessagesUtil=new InfoMessagesUtil();
	        }
	        public SupervisorValidator() {
				// TODO Auto-generated constructor stub
			}
			public String  validateSupervForPlacement(HttpServletRequest request,
                            StudentPlacementForm studentPlacementForm,
                            ActionMessages messages,
                            SupervisorListRecord supervisor)throws Exception{
		                       
		                        String  returnStr="";
		                        if(supervisorUI.isExpiredSupervisor(supervisor)){
            	                        infoMessagesUtil.addMessages(messages, message);
                                }else{
                                	    validateAllocatedStudentTotals(supervisor,studentPlacementForm, messages);
                                }
                                if (!messages.isEmpty()) {
                                	returnStr="listSupervisor";				
                                 }
                                return returnStr;
           }
	       private void validateAllocatedStudentTotals(SupervisorListRecord supervisor,
	                             StudentPlacementForm studentPlacementForm,
	                             ActionMessages messages)throws Exception{
		                              int allowedStuNum=Integer.parseInt(supervisor.getStudentsAllowed());
	                                  int allocatedStuNum=Integer.parseInt(supervisor.getStudentsAllocated());
	                                  if((supervisor.getCode()!=283)
	                                        &&(allocatedStuNum>=allowedStuNum)){
	        	                                  infoMessagesUtil.addMessages(messages,secMessage);
	                                  }
	        }
	
            public void validateIndexArrForSupervSelection(String[] indexArr,ActionMessages messages){ 
	                        SupervisorSelectionValidator  supervisorSelectionValidator=new SupervisorSelectionValidator();
	                        supervisorSelectionValidator.validateIndexArrForSupervSelection(indexArr, messages);
            }
            public void validateIndexArrForSupervAddOrView(String[] indexArr,ActionMessages messages){ 
                         SupervisorSelectionValidator  supervisorSelectionValidator=new SupervisorSelectionValidator();
                         supervisorSelectionValidator.validateIndexArrForSupervAddOrView(indexArr, messages);
            }
            public void validateSupervData(Supervisor supervisor,ActionMessages messages){
            	              SupervisorDataValidator personalDataValidator=new SupervisorDataValidator();
            	             SupervisorCommunicValidator communiValidator=new SupervisorCommunicValidator();
            	             personalDataValidator.validatePersonalData(supervisor, messages);
            	             communiValidator.validateCommunicData(supervisor, messages);
            }
            public boolean isSupValid(int supervisorCode) throws Exception{
                SupervisorUI sup=new SupervisorUI();
                DateUtil dateUtil=new DateUtil();
                int year=dateUtil.yearInt();
                int totalStuAllocated=Integer.parseInt(sup.getStudentsAllocated(supervisorCode, year)); 
                int totAllowed=Integer.parseInt(sup.getStudentsAllowed(supervisorCode,year));
                if(totalStuAllocated<totAllowed){
               	    return true;
                }else{
               	    return false;
                }
           }

}
