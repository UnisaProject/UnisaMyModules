package za.ac.unisa.lms.tools.tpustudentplacement.utils;
import java.util.List;
import java.util.ArrayList;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Student;

import za.ac.unisa.utils.CellPhoneVerification;

public class SmsDataValidator  extends CommunicationDataValidator{
	
	
	        public List  validateSmsData(Student student,String cellNumber,
			                     String communicationTo){
			                              //error messages will be returned in a list
	        	                        List errorMsgList=new ArrayList();
		                                validateCommunicationTo(communicationTo,errorMsgList);
		                                if(errorMsgList.size()>0){
		                                	return errorMsgList;
		                                }
		                                if(invalidCommunicationToSchoolBySms(communicationTo,errorMsgList)){
		                                	return errorMsgList;
		                                }
		                                validateCellNumber(cellNumber,errorMsgList);
		                                if(errorMsgList.size()>0){
		                                	return errorMsgList;
		                                }
		                                compareEnteredCellToSystemCell(student,communicationTo,cellNumber,errorMsgList);
		                                return errorMsgList;
		    }     
		  
		 
		  public boolean invalidCommunicationToSchoolBySms(String communicationTo,List errorMsgList){
                           if (communicationTo.equalsIgnoreCase("School")){
                	               errorMsgList.add("Option to send SMS to a school is not available.");
                	               return true;
                           }else{
                        	   return false;
                           }
           }
		  public void compareEnteredCellToSystemCell(Student  student,String  communicationTo,String cellNumber,List errorMsgList){
	               if (communicationTo.equalsIgnoreCase("Student")){
			           if (student.getContactInfo().getCellNumber()==null){
	  		                errorMsgList.add(
					               "Correct student cell phone number on Student system.  SMS may only be send to Student's cell phone number on the system.");
				       }else {
					            if (!cellNumber.equalsIgnoreCase(student.getContactInfo().getCellNumber())){
					               	 errorMsgList.add(
									"Correct student cell phone number on Student system.  SMS may only be send to Student's cell phone number on the system.");
						        }
					   }
				  }
		  }
}
