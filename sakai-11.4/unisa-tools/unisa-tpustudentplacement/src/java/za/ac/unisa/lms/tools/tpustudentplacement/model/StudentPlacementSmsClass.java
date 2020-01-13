package za.ac.unisa.lms.tools.tpustudentplacement.model;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessages;

import za.ac.unisa.lms.tools.tpustudentplacement.utils.InfoMessagesUtil;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.OperListener;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl.StuPlacementReader;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl.StudentPlacementLogUI;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl.StudentPlacementUI;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.CommunicationUI;
import Srsms01h.Abean.Srsms01sSendSingleSms;

public class StudentPlacementSmsClass {
	

	private static final  String smsNotSendTechnialError ="SMS was not send due to technical errors :ExitStateType<3 in proxy Srsms01sSendSingleSms";
	private static final  String smsNotSendTechnialErrorExitstate3="SMS was not send due to technical errors :ExitStateType<3 in proxy Srsms01sSendSingleSms";
	private static final  String smsAddedSussesfully="SMS was successfully added to the queue.";
	private static final  String msgToSchool="This is to inform you a placement email has been send to your school from Unisa Teaching Practice Unit.";
	private static final  String msgToStudent=". Your placement for teaching practice finalized. Confirmation letter emailed to you.";
	
	         StudentPlacementUI spUI;
	         public StudentPlacementSmsClass(){
	        	         spUI=new StudentPlacementUI();
	         }
	         public boolean sendSmS(StudentPlacementForm studentPlacementForm,ActionMessages messages,
	                          ActionMessages infoMessages,HttpServletRequest request){
                                    Srsms01sSendSingleSms op = new Srsms01sSendSingleSms();
                                    OperListener opl = new OperListener();
                                    String message=smsMessage(studentPlacementForm);
                                    String recipient=getRecipent(studentPlacementForm);
                                    String errorMsg=setSmsDataToProxyObject(op,opl,message,recipient,studentPlacementForm);
                                    InfoMessagesUtil infoMessagesUtil=new InfoMessagesUtil();
                                    if(!errorMsg.trim().equals("")){
                                    	  infoMessagesUtil.addMessages(messages, errorMsg);
                                    	  return false;
                                    }
                                    try{
                                         op.execute();
                                    }catch(Exception ex){
                                    	infoMessagesUtil.addMessages(messages, "SMS not sent , error executing the proxy");
                                    }
                                    boolean successful=checkSmsStatus(op,opl,messages,infoMessages,request);
                                    if(successful){
                                    	addSmsLog(studentPlacementForm);
                                    }
                                    return successful;
             }
	         private String smsMessage(StudentPlacementForm studentPlacementForm){
	    	                        String message="";
		                            if (studentPlacementForm.getCommunicationTo().equalsIgnoreCase("Student")){
                                              message = "Dear " + studentPlacementForm.getStudent().getPrintName().trim() + msgToStudent;
		                            }else{
                                              message = msgToSchool;
                                    }
		                       return message;
	         }
	         private String getRecipent(StudentPlacementForm studentPlacementForm){
	    	               if (studentPlacementForm.getCommunicationTo().equalsIgnoreCase("Student")){
                                    return "S";
                           }else {
                        	        return "P";
                           }
             }
	         private String setSmsDataToProxyObject(Srsms01sSendSingleSms op,OperListener opl,String message,
	    		                 String recipient,StudentPlacementForm studentPlacementForm){
	        	                     try{
	    	                             op.clear();
	                                     op.setInCsfClientServerCommunicationsAction("TP");
                                         op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
                                         op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
                                         op.setInSmsRequestReasonGc27(15);
                                         op.setInSmsQueueReferenceNr(studentPlacementForm.getStudent().getNumber());
                                         op.setInSmsQueueMessage(message);
                                         op.setInNovellCodeWsStaffEmailAddress(studentPlacementForm.getUserId());
                                         op.setInWsSmsToSmsTo(recipient);
                                         op.setInSmsQueueCellNr(studentPlacementForm.getCommunicationCellNumber());
                                         return "";
	        	                     }catch(Exception ex){
	        	                    	 return  "SMAS not sent ,Error adding data to proxy";
	        	                     }
	        }
	        private boolean checkSmsStatus(Srsms01sSendSingleSms op,OperListener opl,ActionMessages messages,
                              ActionMessages infoMessages,HttpServletRequest request){
                                     InfoMessagesUtil infoMessagesUtil=new InfoMessagesUtil();
                                     if (opl.getException() != null){
                                          //throw opl.getException();
                                          infoMessagesUtil.addMessages(messages,smsNotSendTechnialError);
                                          return false;
                                     }
                                     if (op.getExitStateType() < 3){
                                              //throw new Exception(op.getExitStateMsg());
                                              infoMessagesUtil.addMessages(messages,smsNotSendTechnialErrorExitstate3);
                                              return false;
                                    
	                                }
                                    if (op.getOutCsfStringsString500() != "" &&
                                             !op.getOutCsfStringsString500().equalsIgnoreCase("SMS was successfully added to the queue.")){
                                                infoMessagesUtil.addMessages(infoMessages,smsAddedSussesfully);
                                                return true;
                                    }else if(op.getOutCsfStringsString500().equalsIgnoreCase("SMS was successfully added to the queue.")) {
                                                 infoMessagesUtil.addMessages(infoMessages,smsAddedSussesfully);			
                                                 return true;
                                    }	
                          return true;
          }
	        public  boolean  sendSmsNotifyingOfEmail(StudentPlacementForm studentPlacementForm,         
			                  HttpServletRequest request,ActionMessages infoMessages,ActionMessages messages){
	        	                       boolean sendSuccessfuly=false;
	        	                       if (studentPlacementForm.getCommunicationTo().equalsIgnoreCase("Student")){
		                                   if (studentPlacementForm.getCommunicationCellNumber()!=null &&
		    		                             !studentPlacementForm.getCommunicationCellNumber().trim().equalsIgnoreCase("")){
		    	                                    sendSuccessfuly=sendSmS(studentPlacementForm,messages,infoMessages,request);
		                                  }
	        	                       }
		                               return sendSuccessfuly;
	       	}
	        public void addSmsLog(StudentPlacementForm studentPlacementForm){
	    	                 	CommunicationUI communicationUI=new CommunicationUI();
	                            StudentPlacementListRecord placementListRec=communicationUI.getPlacementForCorresp(studentPlacementForm);
	                            try{
	           	                        StudentPlacement  sp=new StudentPlacement();
	           	                        sp=spUI.getStudentPlacement(studentPlacementForm,placementListRec);
	          		                    StudentPlacementLogUI logAdder=new StudentPlacementLogUI();
	    		                        logAdder.setLogOnSmsToStu(studentPlacementForm);
	        	                }catch(Exception ex){
	        		
	        	               }
           }
}
