package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl;
import java.util.ArrayList;
import org.apache.struts.action.ActionMessages;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.InfoMessagesUtil;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.PlacementUtilities;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Supervisor;

public class PrelimPlacementScreenBuilder {
	
	
	       PrelimStudentPlacementUI prelimPlacementUI;
	       public PrelimPlacementScreenBuilder(PrelimStudentPlacementUI prelimPlacementUI){
	    	                   this.prelimPlacementUI= prelimPlacementUI;
	       }
	       public ActionMessages setListPrelimPlacementScreen(StudentPlacementForm studentPlacementForm)throws Exception{
		                     ActionMessages messages = new ActionMessages();	
	                         studentPlacementForm.setCurrentPage("listPrelimPlacement");
	                         studentPlacementForm.setListPlacement(new ArrayList());
	                         Short province=studentPlacementForm.getPlacementFilterProvince();
	                         setVariablesForCountry(studentPlacementForm,province,messages);
	                         setSupervisorData(studentPlacementForm);
	                         if (messages.isEmpty()) {
	                        	           prelimPlacementUI.setPlacementList(studentPlacementForm,province);
	                        	           prelimPlacementUI.initForIntCountry(studentPlacementForm);
	                         }
	                         return messages;
	       }
	       private void setSupervisorData(StudentPlacementForm  studentPlacementForm)
	    		               throws Exception{
	    	                               studentPlacementForm.setPlacementFilterSupervisor(283);
	    	                               Supervisor supervisor=new Supervisor();
	    	                               String supervisorName=supervisor.getSurpervisorName(283);
	                                       studentPlacementForm.setPlacementFilterSupervisorDesc(supervisorName);
	       }
	       private void checkProvinceSelectedForSA(StudentPlacementForm studentPlacementForm,short province,ActionMessages messages){
	                              if(studentPlacementForm.getPlacementFilterCountry().equals(PlacementUtilities.getSaCode())){//test case 1
                                          if(province==0){
           	                                    InfoMessagesUtil infoMessagesUtil=new InfoMessagesUtil();
                                                String message="Please select a Province  in order to display a list of Preliminary Placements";
                                                infoMessagesUtil.addMessages(messages,message);
             
                                          }
	                             }
	                              return;
	       }
	       private void setVariablesForIntCountry(StudentPlacementForm studentPlacementForm){
                             if(!studentPlacementForm.getPlacementFilterCountry().equals(PlacementUtilities.getSaCode())){
                            	    short  province=Short.parseShort("0");
                                    studentPlacementForm.setPlacementFilterProvince(province);
                                    studentPlacementForm.setPlacementFilterDistrict(null);
                                    studentPlacementForm.setPlacementFilterDistrictValue(null);
                             }
                             return;
           }
	       private void setVariablesForCountry(StudentPlacementForm studentPlacementForm,
	    		               short province,ActionMessages messages)throws Exception{
	    	                           checkProvinceSelectedForSA(studentPlacementForm,province,messages);
	    	                           setVariablesForIntCountry(studentPlacementForm);
	    }
	      
}
