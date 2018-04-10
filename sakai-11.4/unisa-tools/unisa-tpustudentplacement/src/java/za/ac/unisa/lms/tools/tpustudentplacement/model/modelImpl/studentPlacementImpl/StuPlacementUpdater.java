package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl;

import java.util.List;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.DateUtil;

import za.ac.unisa.lms.tools.tpustudentplacement.forms.PlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.SupervisorUI;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.InfoMessagesUtil;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.SupervisorValidator;

import org.apache.struts.action.ActionMessages;

public class StuPlacementUpdater {
	
	           public void updatePlacement(StudentPlacementForm studentPlacementForm,ActionMessages messages)throws Exception{
	                          //get original module and school
	        	                 boolean canSave=checkIfPreliminaryPlacementAlreadyEdited(studentPlacementForm,messages);
	        	                 if(canSave){
	        	                        StuPlacementReader  stuPlacementReader=new StuPlacementReader();
	        	                        StudentPlacement storedPlacement=stuPlacementReader.getStoredStuPlacement(studentPlacementForm);
	        	                        StudentPlacement newPlacement=studentPlacementForm.getStudentPlacement();
	        	                        if (studentPlacementForm.getStudentPlacementAction().equalsIgnoreCase("Edit")){
                                	           editPlacement(studentPlacementForm,storedPlacement,newPlacement,messages); 
                                        }else{
                                        	SupervisorValidator supValid=new SupervisorValidator();
                                        	   if(!supValid.isSupValid(newPlacement.getSupervisorCode())) {
                                        		      InfoMessagesUtil infoMessagesUtil=new InfoMessagesUtil();
 	                            	        	      infoMessagesUtil.addMessages(messages,"The supervisor you selected has reached the maximum allowed students, choose another supervisor.");
 	                            	        	 }else{
                                	                   editPrelimPlacement(studentPlacementForm,storedPlacement,newPlacement,messages);
                                               }
                                        }
	        	                 }
               }
	           public void editPlacement(StudentPlacementForm studentPlacementForm,StudentPlacement storedPlacement,
	        		                     StudentPlacement newPlacement,ActionMessages messages)throws Exception{
	        	                           StudentPlacementListRecord originalPlacement=getOriginalPlacement(studentPlacementForm);
	        	                           boolean keysChangedAndmoduleExist=checkSchoolAndModule(studentPlacementForm,originalPlacement,storedPlacement,newPlacement,messages);
	        	                           if(keysChangedAndmoduleExist){
	        	                        	   return;
	        	                           }
                                           updatePlacement(studentPlacementForm,originalPlacement,newPlacement,messages);
                                           if(messages.isEmpty()){
                                                 setPlacementLogForEdit(studentPlacementForm,originalPlacement,messages);
                                           }
	           }
	           public void editPrelimPlacement(StudentPlacementForm studentPlacementForm,StudentPlacement storedPlacement,
	                             StudentPlacement newPlacement,ActionMessages messages)throws Exception{
 	                                      StudentPlacementListRecord originalPlacement =new StudentPlacementListRecord(); 
 	                                      PlacementListRecord originalPrelimPlacement=studentPlacementForm.getOriginalPrelimPlacement();
                                          originalPlacement.setModule(originalPrelimPlacement.getModule());
                                          originalPlacement.setSchoolCode(originalPrelimPlacement.getSchoolCode());
                                          //if keys change determine if another record with same keys already exists
                                          boolean keysChangedAndmoduleExist=checkSchoolAndModule(studentPlacementForm,originalPlacement,
                                        		  storedPlacement,newPlacement,messages);
                                          InfoMessagesUtil infoMessagesUtil=new InfoMessagesUtil();
                                          if(keysChangedAndmoduleExist){
                    	                            return;
                                          }
                                          updatePlacement(studentPlacementForm,originalPlacement,newPlacement,messages);
                                          if(messages.isEmpty()){
                                                 setPlacementLogForEdit(studentPlacementForm,originalPlacement,messages);
                                                 if(messages.isEmpty()){
                                                        infoMessagesUtil.addMessages(messages,"Changes have been saved");
                                                        removeFromPrelimPlacementList(studentPlacementForm);
                                                 }
                                          }
                                          studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
                                          studentPlacementForm.setCanSaveEdits(0);
               }
	           private boolean checkIfPreliminaryPlacementAlreadyEdited(StudentPlacementForm studentPlacementForm,ActionMessages messages){
	        	                boolean canSave=true;
	                            if (!studentPlacementForm.getStudentPlacementAction().equalsIgnoreCase("Edit")){
	                            	        if(studentPlacementForm.getCanSaveEdits()==0){
	                            	        	 InfoMessagesUtil infoMessagesUtil=new InfoMessagesUtil();
	                            	        	  infoMessagesUtil.addMessages(messages,"Press the Next button to select the next Preliminary Placement.");
	                            	        	  canSave=false;
	                            	        }
	                            }
	                            return canSave;
               }
	           private boolean checkSchoolAndModule(StudentPlacementForm studentPlacementForm,
	                                               StudentPlacementListRecord originalPlacement,
	                                               StudentPlacement storedPlacement,StudentPlacement newPlacement,
	                                                ActionMessages messages){
                   	        	                            //if keys change determine if another record with same keys already exists\
	                                           	boolean moduleExist=false;
                                              if (!checkSchoolAndModule(originalPlacement,newPlacement)){//keys changed
                                                    if  (!messages.isEmpty()){
                                                    	   studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
       	                                                   moduleExist=true;
                                                     }
                                              }                 
                                              return moduleExist;
                   
	           }
	           private void removeFromPrelimPlacementList(StudentPlacementForm studentPlacementForm){
	                           int pos=studentPlacementForm.getPosOfCurrPrelimPlacement();
	                           List placementList=studentPlacementForm.getListPlacement();
	                           if(studentPlacementForm.getSupervisorCode()!=283){
	                                  placementList.remove(pos);
	                                  studentPlacementForm.setPosOfCurrPrelimPlacement(--pos);
	                           }
	           }
               private void updatePlacement(StudentPlacementForm studentPlacementForm,
		                        StudentPlacementListRecord originalPlacement,
		                        StudentPlacement placement,ActionMessages messages ) {
            	                       try{
	                                         StudentPlacementUI placementUI = new StudentPlacementUI();
	                                         placementUI.updateStudentPlacement(Short.parseShort(studentPlacementForm.getAcadYear()),
			                                                   Short.parseShort(studentPlacementForm.getSemester()),
			                                                   Integer.parseInt(studentPlacementForm.getStudentNr()),
			                                                   originalPlacement.getModule(),
			                                                   originalPlacement.getSchoolCode(),
			                                                   placement,studentPlacementForm.getSupervisorCode());
            	                       }catch(Exception ex){
            	                    	        InfoMessagesUtil infoMessagesUtil=new InfoMessagesUtil();
                         	        	        infoMessagesUtil.addMessages(messages,"Changes were not saved, there was an error  :"+ex.getMessage());
            	                      }
	                          
               }
	           private boolean checkSchoolAndModule(StudentPlacementListRecord originalPlacement,StudentPlacement placement){
	        	                    try{
	        	                    	if(originalPlacement==null){
	        	                    		   return false;
	        	                    	}
	        	                    	if(placement.getModule().trim().equalsIgnoreCase(originalPlacement.getModule().trim())
                  		                        &&placement.getSchoolCode().compareTo(originalPlacement.getSchoolCode())==0){
                 	                              return  true;
                                          }else{
                 	                              return false;
                                         }
	        	                    }catch(Exception ex){
	        	                    	      return false;
	        	                    }
               }
	           private StudentPlacementListRecord getOriginalPlacement(StudentPlacementForm studentPlacementForm){
                                                      StudentPlacementListRecord originalPlacement=new StudentPlacementListRecord(); 
                                                      for (int i=0; i <studentPlacementForm.getIndexNrPlacementSelected().length; i++) {
                                                              String array[] = studentPlacementForm.getIndexNrPlacementSelected();
                                                              originalPlacement= (StudentPlacementListRecord)
                                                              studentPlacementForm.getListStudentPlacement().get(Integer.parseInt(array[i]));
                                                      }
                                                    return originalPlacement;
               }
               private void setPlacementLogForEdit(StudentPlacementForm studentPlacementForm,
	        		           StudentPlacementListRecord originalPlacement,ActionMessages messages) {
	        	                      StudentPlacementLogUI logAdder=new StudentPlacementLogUI();
	        	                      try{
                                	           logAdder.setLogOnUpdatePlacement(studentPlacementForm);
                                      }catch(Exception ex){
                                    	             InfoMessagesUtil infoMessagesUtil=new InfoMessagesUtil();
                                    	             StudentPlacementUI spUI=new StudentPlacementUI();
    	        	                                 StudentPlacement placement=spUI.getPlacementFromPlacementListRecord(originalPlacement);
                                                     updatePlacement(studentPlacementForm,originalPlacement,placement,messages);
                                                     String errorMsg="Changes have not been saved.There was an error writing a log for update of the placement, error:"+ex.getMessage();
                                                     infoMessagesUtil.addMessages(messages,errorMsg);
                                      }
              }          
	           

}
