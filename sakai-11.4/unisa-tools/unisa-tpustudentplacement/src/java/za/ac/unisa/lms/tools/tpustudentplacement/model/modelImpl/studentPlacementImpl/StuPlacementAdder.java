package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl;

import org.apache.struts.action.ActionMessages;

import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Supervisor;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.SupervisorUI;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.InfoMessagesUtil;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.SupervisorValidator;
public class StuPlacementAdder {
	
	
	      StuPlacementReader stuPlacementReader;
	      StuPlacementRemover stuPlacementRemover;
	      public StuPlacementAdder(){
	    	         stuPlacementRemover=new StuPlacementRemover();
	    	         stuPlacementReader=new StuPlacementReader();
	      }
		  public ActionMessages  addStudentPlacement(StudentPlacementForm studentPlacementForm,ActionMessages messages)throws Exception{
	                                    //saves placement if record doesn't already exist
			                                   SupervisorValidator supValid=new SupervisorValidator();
			                                   StudentPlacement placement=studentPlacementForm.getStudentPlacement();
			                                   SupervisorUI supervisorUI=new SupervisorUI();
			                                   Supervisor supervisor=supervisorUI.getSupervisor(placement.getSupervisorCode());
			                                   if((!supervisor.getSurname().contains("Dummy"))||
			                                		   (!supervisor.getSurname().contains("DUMMY"))){
       	                                                    if(!supValid.isSupValid(placement.getSupervisorCode())) {
       		                                                    InfoMessagesUtil infoMessagesUtil=new InfoMessagesUtil();
	        	                                                 infoMessagesUtil.addMessages(messages,"The supervisor you selected has reached the maximum allowed students, choose another supervisor.");
       	                                                    }
			                                   }
       	                                        if (!messages.isEmpty()){
       	                                        	  return messages;
       	                                        }
        	                                    checkModuleExists(studentPlacementForm,messages);
    	                                        if (messages.isEmpty()){
                                        	                 insertPlacement(studentPlacementForm); 
                                                }
                                      return  messages;
          }
          public void setPlacementLogForAddition(StudentPlacementForm studentPlacementForm,ActionMessages messages){
        	                     StudentPlacementLogUI logAdder=new StudentPlacementLogUI();
                                  try{
                                	    logAdder.setLogOnNewPlacement(studentPlacementForm);
                                  }catch(Exception ex){
                                	  handleLogError(studentPlacementForm,messages);
                                  }
          }
          private void handleLogError(StudentPlacementForm studentPlacementForm,ActionMessages messages){
        	                 try{
        	                       InfoMessagesUtil infoMessagesUtil=new InfoMessagesUtil(); 
                                   String message="The placement was not saved, due to errors saving the placement log,Please add the placement again";
                                   infoMessagesUtil.addMessages(messages,message);
                                   StudentPlacement  placement=studentPlacementForm.getStudentPlacement();
                                   short acadYear=Short.parseShort(studentPlacementForm.getAcadYear());
                                   short semester=Short.parseShort(studentPlacementForm.getSemester());
                                   int studentNr=Integer.parseInt(placement.getStuNum());
                                   int schoolCode=placement.getSchoolCode();
                                   String module=placement.getModule();
                                   stuPlacementRemover.removeStudentPlacement(acadYear, semester, studentNr, 
                   		            module,schoolCode);
                             }catch(Exception err){}
         }
         public void insertPlacement(StudentPlacementForm studentPlacementForm)throws Exception{
        	              StudentPlacement placement=studentPlacementForm.getStudentPlacement();
        	               placement.insertStudentPlacement(Short.parseShort(studentPlacementForm.getAcadYear()),
        	            		   Short.parseShort(studentPlacementForm.getSemester()),
        	            		   Integer.parseInt(studentPlacementForm.getStudentNr()), placement);
         }
         public ActionMessages checkModuleExists(StudentPlacementForm studentPlacementForm,ActionMessages messages)throws Exception{
                                  StuPlacementReader stuPlacementReader=new StuPlacementReader();
                                  StudentPlacement placement= stuPlacementReader.getStoredStuPlacement(studentPlacementForm);
                                  if (placement.getModule()!=null){
                                         String message="This module has already been placed at this school";
                                         InfoMessagesUtil infoMessagesUtil=new InfoMessagesUtil();
                                         infoMessagesUtil.addMessages(messages, message);
                                  }
                                  return messages;
         }	    
         public ActionMessages addPlacement(StudentPlacementForm studentPlacementForm){
        	                         ActionMessages messages =new ActionMessages();
        	                         try{
                                              checkModuleExists(studentPlacementForm,messages);
       	                                      if (messages.isEmpty()){
                                           	        insertPlacement(studentPlacementForm); 
                                                    if (messages.isEmpty()){
                                                          setPlacementLogForAddition(studentPlacementForm, messages);
                                                    }
       	                                      }
                                      }catch(Exception err){}
                                     return messages;
        }
}
