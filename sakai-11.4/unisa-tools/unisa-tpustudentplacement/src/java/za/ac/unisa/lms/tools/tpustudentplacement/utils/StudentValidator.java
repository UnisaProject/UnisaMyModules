package za.ac.unisa.lms.tools.tpustudentplacement.utils;

import java.util.List;
import org.apache.struts.action.ActionMessages;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Qualification;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Student;

public class StudentValidator {
	
	
	         InfoMessagesUtil infMsgUtil;
	         public StudentValidator(){
	        	         infMsgUtil=new InfoMessagesUtil();
	         }
            public String validateStudentNum(String stuNum){
                              if((stuNum==null)||
                            		  (stuNum.trim().equals(""))){
                  	                          return "Student number does not exists.";
                              }else{
                            	     PlacementUtilities plu=new PlacementUtilities();
                            	     if(plu.isInteger(stuNum)){
                            	    	     return "";
                            	     }else{
                            	    	     return "Student number must be an integer.";
                            	     }
                         	         
                             }
             }

            public String validateQualification( Qualification qual){
                                 if (qual.getCode()==null){
	         					        return "Student is not registered for this academic year.";
                                 }else{
                            	        return "";
                                }
            }
            public String validatePracticalModuleList(List moduleList){
                             if (moduleList.size()==0){
                                    return 	"Student is not registered for any practical module for this academic year and semester.";
                             }else{
                     	            return "";
                             }
           }
           public void validateStudentNum(String stuNum,ActionMessages messages){
            	                String msg=validateStudentNum(stuNum);
            	                if(!msg.trim().equals("")){
            	                	infMsgUtil.addMessages(messages,msg);
            	                }
            }
          	public void validateQualification( Qualification qual,ActionMessages messages){
 	                         String msg=validateQualification(qual);
 	                         if(!msg.trim().equals("")){
 	                        	    infMsgUtil.addMessages(messages,msg);
 	                         }
            }
          	public void validatePracticalModuleList(List moduleList,ActionMessages messages){
          		               String msg=validatePracticalModuleList(moduleList);
          	             	   if(!msg.trim().equals("")){
                	                  infMsgUtil.addMessages(messages,msg);
                               }
            }
          	public void validateStudentData(Student student,ActionMessages messages){ 
	                        validateQualification(student.getQual(),messages);
	                        if(!messages.isEmpty()){
                                   return;
                            }
	                        validatePracticalModuleList(student.getListPracticalModules(),messages);
	                        if(!messages.isEmpty()){
                                    return;
                            }
           }
          	
}
