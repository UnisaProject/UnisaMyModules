package za.ac.unisa.lms.tools.tpustudentplacement.uiLayer;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMessages;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl.StudentPlacementUI;

public class CorrespScreenBuilder {
	
	CorrFromEditDummyScrBuilder corrFromEditDummyScrBuilder;
	CorrspFromLogScrBuilder corrspFromLogScrBuilder;
	public  CorrespScreenBuilder(){
		         corrFromEditDummyScrBuilder=new  CorrFromEditDummyScrBuilder();
		         corrspFromLogScrBuilder=new CorrspFromLogScrBuilder();
    }
	public void buildCommunicationScreen(HttpServletRequest request,StudentPlacementForm studentPlacementForm,
			              StudentPlacementUI studentPlacementUI,CommunicationUI communicationUI,ActionMessages messages)throws Exception{
		                         if (studentPlacementForm.getCurrentPage().equalsIgnoreCase("editPrelimPlacement")){
                                	 corrFromEditDummyScrBuilder.setCorrespDetForEditDummyPlacement(request,studentPlacementForm,messages);
                                	 studentPlacementForm.setPreviousPage("editPrelimPlacement");
                                 }else if(studentPlacementForm.getLogButtonTracker().equals("FromPlacementLogMaintenanceLink")||
    	    	                            studentPlacementForm.getLogButtonTracker().equals("FromPlacementLogBtn")){
                    	                    corrspFromLogScrBuilder.setCorrespondeceDetailsFromLog(request,studentPlacementForm,messages);
                                  }else{
                                	        setCommunicationList(studentPlacementForm,studentPlacementUI,communicationUI);
                                	        resetCommunicationVariables(studentPlacementForm);
                                	        studentPlacementForm.setPreviousPage("listStudentPlacement");
                                  }
                                  studentPlacementForm.setCurrentPage("inputCorrespondence");
	}
	
	private void setCommunicationList(StudentPlacementForm studentPlacementForm,
			            StudentPlacementUI studentPlacementUI,CommunicationUI communicationUI){
		                       studentPlacementUI.setStuPlacementList(studentPlacementForm);
                               List list=communicationUI.getListOfSchoolsInStuPlacementList(studentPlacementForm);
                               studentPlacementForm.setCommunicationList(list);
	}
	private void resetCommunicationVariables(StudentPlacementForm studentPlacementForm){
                    studentPlacementForm.setCommunicationTo("");
                    studentPlacementForm.setCommunicationEmailAddress("");
                    studentPlacementForm.setCommunicationCellNumber("");
  	}

}
