package za.ac.unisa.lms.tools.tpustudentplacement.utils;

import java.util.ArrayList;
import java.util.List;

public class EmailDataValidator extends CommunicationDataValidator{
	
	
    public List  validateEmailData(String emailAddress,String cellNumber,String communicationTo){
	                              //error messages will be returned in a list
    	                        List errorMsgList=new ArrayList();
    	                        validateCommunicationTo(communicationTo,errorMsgList);
    	                        if(errorMsgList.size()>0){
    	                        	return errorMsgList;
    	                        }
    	                        validateEmailAddress(emailAddress,errorMsgList);
    	                        if(errorMsgList.size()>0){
    	                        	return errorMsgList;
    	                        }
    	                        if(communicationTo.equalsIgnoreCase("Student")){
                                     validateCellNumber(cellNumber,errorMsgList);
    	                        }
                                return errorMsgList;
    }     
  
 
    public void validateEmailAddress(String emailAddress,List errorMsgList){
                     if (emailAddress==null ||
                    		 emailAddress.trim().equalsIgnoreCase("")){
 	                                 errorMsgList.add("Email address is required.");
                      }else{
                    	      EmailValidater emailValidater = new EmailValidater();
                  		      if (!emailValidater.validate(emailAddress)){
                  		    	        errorMsgList.add("Invalid email address.");
                  		     }
                      }
    }
  
	
 
}
