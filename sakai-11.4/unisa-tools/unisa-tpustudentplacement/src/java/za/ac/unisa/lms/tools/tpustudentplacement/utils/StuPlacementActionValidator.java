package za.ac.unisa.lms.tools.tpustudentplacement.utils;

import java.util.ArrayList;
import java.util.List;
import org.apache.struts.action.ActionMessages;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Student;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;

public class StuPlacementActionValidator{
	             
	             
	               InfoMessagesUtil imsgsUtil;
	               SmsDataValidator smsDataValidator ; 
	               EmailDataValidator emailDataValidator;
	               StudentPlacementValidator stuPlacValidator;
	                 public  StuPlacementActionValidator(){
	            	          imsgsUtil=new InfoMessagesUtil();
	            	           smsDataValidator=new SmsDataValidator();
	            	           emailDataValidator=new EmailDataValidator();
	            	           stuPlacValidator=new StudentPlacementValidator();
	                }
	                public void  validateIndexForRemovingPlacement(String[]  indexNrPlacementSelected,ActionMessages  messages){
	                	                          List  msgsList=new ArrayList();
	                	                          if (indexNrPlacementSelected==null ||
	     	                      		                indexNrPlacementSelected.length==0){
	     	                        	                    msgsList.add("Please select a placement to remove");
	     	                	                 }
	     	                                     if (indexNrPlacementSelected!=null &&
	     	                                    		 indexNrPlacementSelected.length>1){
	     	                						         msgsList.add("Please select only one placement to remove");
	     	                                     }
                                                  InfoMessagesUtil infoMessagesUtil  =new InfoMessagesUtil ();
                                                  infoMessagesUtil.addMessageList(msgsList,messages);
	                }
	                public void validateEvalMark(String   evaluationMark,ActionMessages messages){ 
                                      String msg=stuPlacValidator.validateEvaluationMark(evaluationMark);
                                      imsgsUtil.addMessages(messages,msg) ;             
                    }
	                public ActionMessages validateStuPlacement(StudentPlacement stuPlacement,int academicYear){
	                                         List messageList=stuPlacement.validateStuPlacement(stuPlacement,academicYear);
	                                         return  imsgsUtil.addMessageList(messageList);
	                }
	                public ActionMessages  validateSmsCommunicData(Student student,String cellNumber,String  communicationTo){
	                	                      List errorMsgList = smsDataValidator.validateSmsData(student,cellNumber, 
                                        		                              communicationTo);
	                	                      return imsgsUtil.addMessageList(errorMsgList);
                    }
	                public ActionMessages  validatEmailCommunicData(String emailAddress,String cellNumber,String communicationTo){
	                                           List errorMsgList = emailDataValidator.validateEmailData(emailAddress,cellNumber, 
                  		                                                  communicationTo);
	                                            return imsgsUtil.addMessageList(errorMsgList);
                    }
	               	public void validateSelectedIndexForEditPlacement(String[]  indexNrPlacementSelected,ActionMessages messages){
	                	    List  msgsList=new ArrayList();

	                         if (indexNrPlacementSelected==null ||
	                	                indexNrPlacementSelected.length==0){
	                                    msgsList.add("Please select a placement to view or edit");
	                         }
	                         if (indexNrPlacementSelected!=null &&
	                        		 indexNrPlacementSelected.length>1){
	                			         msgsList.add("Please select only one placement to view or edit");
	                         }
	                                         InfoMessagesUtil infoMessagesUtil  =new InfoMessagesUtil ();
	                                         infoMessagesUtil.addMessageList(msgsList, messages);
	                   }

}
