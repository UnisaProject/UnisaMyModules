package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl;

import za.ac.unisa.lms.tools.tpustudentplacement.utils.CommunicationDataValidator;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.schoolImpl.SchoolUI;

import org.apache.struts.action.ActionMessages;

public class LetterUI {
	       
	            public  void getLetter(StudentPlacementForm studentPlacementForm,ActionMessages messages)throws Exception{
	                              CommunicationDataValidator cv=new CommunicationDataValidator();
                                  String communicationTo=studentPlacementForm.getCommunicationTo();
                                  cv.validateCommunicationTo(communicationTo,messages);
                                  studentPlacementForm.setCurrentPage("inputCorrespondence");
                                  if (messages.isEmpty()) {
           	                      	    SchoolUI schoolUI=new SchoolUI();
                                        schoolUI.setSchoolToFormBean(studentPlacementForm);
                                        if (communicationTo.equalsIgnoreCase("Student")){
                                                 studentPlacementForm.setCurrentPage("studentLetter");
                                        }else {
                                                 studentPlacementForm.setCurrentPage("schoolLetter");
                                        }		
                                  }
	            }

}
