package za.ac.unisa.lms.tools.tpustudentplacement.utils;

import java.util.List;

import org.apache.struts.action.ActionMessages;

import za.ac.unisa.lms.tools.tpustudentplacement.forms.Student;
import za.ac.unisa.utils.CellPhoneVerification;

public class CommunicationDataValidator {
	
	 public void validateCommunicationTo(String communicationTo,List errorMsgList){
                   if (communicationTo==null ||
       		             communicationTo.trim().equalsIgnoreCase("")){
       	                     errorMsgList.add("Recipient is required.");
                   }
     }
	 public String validateCommunicationTo(String communicationTo,String errorMsg){
                     if (communicationTo==null ||
		                    communicationTo.trim().equalsIgnoreCase("")){
                    	        return errorMsg;
                     }else{
                   	       return "";
                     }
     }
	 public String validateCommunicationTo(String communicationTo){
                      if (communicationTo==null ||
                           communicationTo.trim().equalsIgnoreCase("")){
                    	      return "Recipient is required.";
                      }else{
                    	      return "";
                      }
     }
	 public void validateCellNumber(String cellNumber,List errorMsgList){
                      if (cellNumber==null || 
                    		  cellNumber.trim().equalsIgnoreCase("")){
                	               errorMsgList.add("Cell Phone Number is required.");
                      }else{
                                //Test for valid cell number
                                CellPhoneVerification cellVerification = new CellPhoneVerification();
                                if (!cellVerification.isCellNumber(cellNumber)){
               	                        errorMsgList.add("Invalid Cell Phone Number.");
                                }
                      }
    }        
	 public void validateCommunicationTo(String  communicationTo,ActionMessages messages,String msg){
                              msg =validateCommunicationTo(communicationTo,msg);
                              InfoMessagesUtil infMsgUtil=new InfoMessagesUtil(); 
                              if(!msg.trim().equals("")){
         	                         infMsgUtil.addMessages(messages,msg);
                              }
    }
	 public void validateCommunicationTo(String  communicationTo,ActionMessages messages){
                        String  msg =validateCommunicationTo(communicationTo);
                         InfoMessagesUtil infMsgUtil=new InfoMessagesUtil(); 
                         if(!msg.trim().equals("")){
                                infMsgUtil.addMessages(messages,msg);
                         }
    }
}
