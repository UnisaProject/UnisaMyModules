package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessages;

import za.ac.unisa.lms.tools.tpustudentplacement.dao.SupervisorDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Supervisor;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.SupervisorListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.SelectionValidator;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.SupervisorCommunicValidator;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.SupervisorDataValidator;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.SupervisorSelectionValidator;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.SupervisorValidator;

public class SupervisorUI  extends  SupervisorImpl{
	
	SupervisorValidator supervisorValidator;
	public SupervisorUI(){
		       supervisorValidator=new SupervisorValidator(this);
	}
	public String  validateSupervForPlacement(HttpServletRequest request,
                         StudentPlacementForm studentPlacementForm,
                         ActionMessages messages,
                         SupervisorListRecord supervisor)throws Exception{
		                             return supervisorValidator.validateSupervForPlacement(request, 
		                            		 studentPlacementForm, messages, supervisor);
		
	}
	public String setSupForAddOrViewPlacement(StudentPlacementForm studentPlacementForm,
                               SupervisorListRecord supervisor,String fromPage,String returnStr)throws Exception{
                                   String nextPage=returnStr;
                                   if ((fromPage.equalsIgnoreCase("inputPlacement") ||
             		                     fromPage.equalsIgnoreCase("listPlacement"))||
             		                    (fromPage.equalsIgnoreCase("listPrelimPlacement"))
                                           &&(returnStr.trim().equals(""))) {
                                               studentPlacementForm.setPlacementFilterSupervisor(supervisor.getCode());
                                               studentPlacementForm.setPlacementFilterSupervisorDesc(supervisor.getName());
                                               nextPage=fromPage;
                                   }
                              return nextPage;
    }
	public String setSupForEditPlacement(StudentPlacementForm studentPlacementForm,
                    SupervisorListRecord supervisor,String fromPage,String returnStr)throws Exception{
                    String nextPage=returnStr;
                    if ((fromPage.equalsIgnoreCase("editStudentPlacement"))||
				           (fromPage.equalsIgnoreCase("editPrelimPlacement"))
				                 &&(returnStr.trim().equals(""))) {
                    	              studentPlacementForm.getStudentPlacement().setSupervisorCode(supervisor.getCode());
                                      studentPlacementForm.getStudentPlacement().setSupervisorName(supervisor.getName());
                                      nextPage=fromPage;
                    }
                    return nextPage;
   }
   public SupervisorListRecord getSelectedSupervisor(StudentPlacementForm studentPlacementForm){
		                             SupervisorListRecord supervisor = new SupervisorListRecord();
		                             for (int i=0; i <studentPlacementForm.getIndexNrSelected().length; i++) {			
			                                  String array[] = studentPlacementForm.getIndexNrSelected();
			                                  int index=Integer.parseInt(array[i]);
			                                  supervisor = (SupervisorListRecord)studentPlacementForm.getListSupervisor().get(index);			
			                                  i=studentPlacementForm.getIndexNrSelected().length;
		                            }
		                            return supervisor;
	}
   public void validateIndexArrForSupervSelection(String[] indexArr,ActionMessages messages){
	                  SelectionValidator selectionValidator=new SelectionValidator();
	                  String noSelectionMessage="Please select a supervisor";
	                  String tooManySelectionsMessage="Please select only one supervisor";
	                  selectionValidator.validateIndexArrForSelection(indexArr, messages,noSelectionMessage,tooManySelectionsMessage);
    }
	public void validateIndexArrForSupervAddOrView(String[] indexArr,ActionMessages messages){ 
		               SelectionValidator selectionValidator=new SelectionValidator();
		               String messageForAddView= "Please select a supervisor to view or edit";
			           String secMessageForAddView= "Please select only one supervisor to view or edit";
   					   selectionValidator.validateIndexArrForAddOrView(indexArr, messages,messageForAddView,secMessageForAddView);
    }
	public void setSelectedSupervisor(StudentPlacementForm studentPlacementForm) throws Exception{
                      Supervisor supervisor = new Supervisor();
                      SupervisorListRecord supervisorListRecord=getSelectedSupervisor(studentPlacementForm);
                      SupervisorDAO dao = new SupervisorDAO();
                      int supervisorCode = supervisorListRecord.getCode();
                      supervisor = dao.getSupervisor(supervisorCode);
                      supervisor.setStudentsAllowed(supervisorListRecord.getStudentsAllowed());
                      supervisor.setStudentsAllocated(supervisorListRecord.getStudentsAllocated());
                      supervisor.setEmailAddress(supervisorListRecord.getEmailAddress());
                      supervisor.setCode(supervisorCode);
                      studentPlacementForm.setSupervisorCode(supervisorCode);
                      supervisor.setName(supervisorListRecord.getName());
                      studentPlacementForm.setSupervisor(supervisor);
                      studentPlacementForm.setSupervisorFullName(supervisorListRecord.getName());
   }
   public void validateSupervData(Supervisor supervisor,ActionMessages messages){
		            supervisorValidator.validateSupervData(supervisor, messages);
   }
   
}
