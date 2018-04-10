package za.ac.unisa.lms.tools.tpustudentplacement.utils;

import java.util.List;
import java.util.ArrayList;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;

public class StudentPlacementValidator {
	
	
	  public List   validateStudentPlacement(StudentPlacement studentPlacement,int academicYear){
		  //validation returns a list of error messages in a list, the list will be empty when the placement has valid data
		                            List  errorMsgList=new ArrayList(); 
	                                validateSchool(studentPlacement,errorMsgList);
	                                validateSupervisorCode(studentPlacement,errorMsgList);
	                                validateModule(studentPlacement,errorMsgList);
	                                validateStartDate(studentPlacement,academicYear,errorMsgList);
	                                validateEndDate(studentPlacement,academicYear,errorMsgList);
	                                validateNumberOfWeeks(studentPlacement,errorMsgList);
	                                return errorMsgList;
	    }     
	  
	  public void validateSchool(StudentPlacement studentPlacement,List errorMsgList){
		                  if (studentPlacement.getSchoolCode()==null ||
                                 studentPlacement .getSchoolCode()==0){
		                	     errorMsgList.add("School is required.");
                          }
	  }
	  public void validateSupervisorCode(StudentPlacement studentPlacement,List errorMsgList){
                         if (studentPlacement.getSupervisorCode()==null || 
                                   studentPlacement.getSupervisorCode()==0){
                                   errorMsgList.add("Supervisor is required.");
                        }
	  }
	  public void validateStartDate(StudentPlacement studentPlacement,int academicYear,List errorMsgList){
		                 String startDate=studentPlacement.getStartDate();
		                 if(isTextInputNotEmpty(startDate)){
                 	               validateDate(startDate,academicYear,errorMsgList);
                         }else{
                        	    errorMsgList.add("Start date is required.");
                         } 
	  }
	  public void validateEndDate(StudentPlacement studentPlacement,int academicYear,List errorMsgList){
                          String endDate=studentPlacement.getEndDate().trim();;
                          if(isTextInputNotEmpty(endDate)){
                        	        validateDate(endDate,academicYear,errorMsgList);
                          }else{
                            	    errorMsgList.add("End date is required."); 
                          }
      }
	  private void validateDate(String dateStr,int  academicYear,List errorMsgList){
                         DateUtil dateUtil=new DateUtil(); 
                         if(!dateUtil.validateDateStrGivenYear(dateStr,'/',academicYear)){
           	                   String msg="An invalid date was entered,  Please enter a date of the form YYYY/MM/DD)";
           	                   errorMsgList.add(msg);
                         }
      }
	   public void validateModule(StudentPlacement studentPlacement,List errorMsgList){
		                     String module=studentPlacement.getModule();
                             if(!isTextInputNotEmpty(module)){
                            	 errorMsgList.add("A Module is required"); 
                              }
	   }
	  private boolean isTextInputNotEmpty(String inputStr){
		                  if(inputStr==null || 
		        		        inputStr.trim().equalsIgnoreCase("")){
		        	               return false;
		                  }else{
		        	               return true;
		                 }
	  }
	 public void validateNumberOfWeeks(StudentPlacement studentPlacement,List errorMsgList){
		                   PlacementUtilities util=new PlacementUtilities();
                           if (studentPlacement.getNumberOfWeeks()==null ||
                                  studentPlacement.getNumberOfWeeks().trim().equalsIgnoreCase("")){
                        	          errorMsgList.add("Number of weeks is required.");
                           }else{
                                   if (!util.isInteger(studentPlacement.getNumberOfWeeks().trim())){
                                	     errorMsgList.add("Number of weeks must be numeric");
                                    }
                          }
	  }
	  public String validateEvaluationMark(String   evaluationMark){ 
                       String msg="Cannot remove a placement where a student has already been evaluated.";
                       if ((evaluationMark==null)|| 
                    		(evaluationMark.trim().equalsIgnoreCase("")) ||
  		                      (evaluationMark.trim().equalsIgnoreCase("0"))){
                    	           msg="";
                       }
                       return msg;                   
      }
}
