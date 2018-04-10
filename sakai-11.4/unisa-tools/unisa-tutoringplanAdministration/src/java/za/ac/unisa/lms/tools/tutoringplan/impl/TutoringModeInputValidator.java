package za.ac.unisa.lms.tools.tutoringplan.impl;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import za.ac.unisa.lms.tools.tutoringplan.dao.TutoringModeDAO;
import za.ac.unisa.lms.tools.tutoringplan.forms.TutoringMode;
import za.ac.unisa.lms.tools.tutoringplan.forms.TutoringPlanDetails;
public class TutoringModeInputValidator {
	
	public List validateInput (TutoringMode  tutoringMode)throws Exception{
        List validationErrsList=new ArrayList();
        if (tutoringMode.getTutorMode()==null || 
      		 tutoringMode.getTutorMode().trim().equalsIgnoreCase("")){
      	     validationErrsList.add("Please select a tutoring mode.");
        }
        validateStudentTutorRatio(tutoringMode,validationErrsList);
        validateGroups(tutoringMode,validationErrsList);
        validateTutorRelatedData(tutoringMode,validationErrsList);
      return validationErrsList;
}
	TutoringModeDAO dao;
	public  TutoringModeInputValidator(TutoringModeDAO dao){
	              this.dao=dao;
	}
  private   void validateStudentTutorRatio(TutoringMode  tutoringMode,List  validationErrsList){
         if (tutoringMode.getTutorStuRatio()==null ||
               tutoringMode.getTutorStuRatio().trim().equalsIgnoreCase("")){
                       validationErrsList.add("Tutor_student ratio is required.");
         } else {
                  if (!TutoringplanUtils.isInteger(tutoringMode.getTutorStuRatio())){
                         validationErrsList.add("Tutor_student ratio must be numeric");
                  }
         }
 }

 private   void validateGroups(TutoringMode  tutoringMode,List  validationErrsList){
	                   if (tutoringMode.getGroupChoice()==null || 
      		               tutoringMode.getGroupChoice().trim().equalsIgnoreCase("")){
                             validationErrsList.add("Please select a grouping method.");
                    }
       
                  if (tutoringMode.getMaxGroupsPerTutor()==null || 
                        tutoringMode.getMaxGroupsPerTutor().trim().equalsIgnoreCase("")){
                        validationErrsList.add("Maximum number of groups per tutor is required.");
                  } else {
                           if (!TutoringplanUtils.isInteger(tutoringMode.getMaxGroupsPerTutor())){
	                           validationErrsList.add("Maximum number of groups per tutor must be numeric");
                          }
                }
                  if (tutoringMode.getAllocationCriteria()==null ||
      	        		tutoringMode.getAllocationCriteria().trim().equalsIgnoreCase("")){
      	        	   validationErrsList.add("Please select the group allocation criteria.");
      	        }
      	       
}
 private void validateTutorRelatedData(TutoringMode  tutoringMode,List  validationErrsList)throws Exception{
	   if (tutoringMode.getIncludeLectAsTutors()==null ||
    		 tutoringMode.getIncludeLectAsTutors().trim().equalsIgnoreCase("")){
              validationErrsList.add("Please indicate whether lecturing staff should be included as tutors.");
       }else{
    	        if(tutoringMode.getIncludeLectAsTutors().trim().equalsIgnoreCase("N")){
    	        	short acadYear=TutoringplanUtils.getAcademicYear();
    	        	short semester=TutoringplanUtils.getSemester();
    	        	String  studyUnit=TutoringplanUtils.getStudyUnit();
    		        if(dao.isLecAssignedAsTutor(acadYear, semester, studyUnit,tutoringMode.getTutorMode())){
    		        	validationErrsList.add("A lecturer has already been assigned as a tutor , you can't set  value of 'Include lecturing staff as tutors?' to No");
    		        } 
    		         
    	        }
       }
       if (tutoringMode.getTutorContact()==null ||
	         tutoringMode.getTutorContact().trim().equalsIgnoreCase("")){
    	    validationErrsList.add("Please indicate the tutor contact detials to be supplied to the students.");
      }
 }
	public void validateNewMode(List tutoringModeList,TutoringMode newTutoringMode,
			                    List validationErrList){
		if (tutoringModeList.size()>0){
			boolean groupMethodNotManual = false;
			for(int i=0; i < tutoringModeList.size(); i++){
				TutoringMode tutorMode = new TutoringMode();
				tutorMode = (TutoringMode)tutoringModeList.get(i);
				if (newTutoringMode.getTutorMode().equalsIgnoreCase(tutorMode.getTutorMode())){
					validationErrList.add("Tutoring mode already exists.");
					i =tutoringModeList.size();
				}
				if (!tutorMode.getGroupChoice().equalsIgnoreCase("MANUAL")){
					groupMethodNotManual=true;
				}
			}
			if (groupMethodNotManual){
				validationErrList.add("Only MANUAL grouping is allowed, if more than "+
			                           "one tutoring mode per module. First set the grouping method "+
						           "of all existing tutoring modes to 'MANUAL' before adding a new one.");
			}
			if (!groupMethodNotManual && !newTutoringMode.getGroupChoice().equalsIgnoreCase("MANUAL")){
				validationErrList.add("Only MANUAL grouping is allowed, if more than one tutoring mode per module.");
			}
		}
	}
	
	
	public void validateTutorMode(TutoringMode tutoringMode,List validationErrList){
		                         if ((Integer.parseInt(tutoringMode.getTutorStuRatio()) < 
					                   Integer.parseInt(tutoringMode.getMaxGroupsPerTutor()))){
				                       validationErrList.add("The maximum number of groups per tutor should not exceed the number of students"+
					                   " per group (tutor-student ratio)");
			                    }
		         if (tutoringMode.getTutorMode().equalsIgnoreCase("FACE")){
			              if (!tutoringMode.getGroupChoice().equalsIgnoreCase("MANUAL")){
				                  validationErrList.add("Only MANUAL grouping is allowed for face-to-face tutoring mode");
			             }
		        }			
	}
	
	
	public void validateEditedMode(List tutoringModeList,TutoringMode editedTutoringMode,
			      List validationErrList){
	                  if (tutoringModeList.size()>1){
			                if (!editedTutoringMode.getGroupChoice().equalsIgnoreCase("MANUAL")){
			            	       validationErrList.add("Only MANUAL grouping is allowed, if more than "+
			                                       "one tutoring mode per module.");
			               }
		             }
	}	
	public String    validateSelectedIndex(String[]  indexToRemoveList ){
	                        if(indexToRemoveList==null ||
	                		       indexToRemoveList.length == 0) {
                                             return "At least one tutoring mode must be selected to be removed";
	                        }
	                        return "";
    }

}
