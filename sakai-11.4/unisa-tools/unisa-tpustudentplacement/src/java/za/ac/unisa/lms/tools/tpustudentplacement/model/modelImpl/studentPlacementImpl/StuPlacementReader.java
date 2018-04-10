package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.StudentUI;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.InfoMessagesUtil;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.PlacementValidator;

import java.util.Set;
import java.util.HashSet;
public class StuPlacementReader {
	
	        public StudentPlacement  getStuExistingPlacement(StudentPlacement newPlacement,short acadYear,
	        		                      short semester,int stuNum )throws Exception{
	                                            StudentPlacement existingPlacement = new StudentPlacement();
	                                            existingPlacement = existingPlacement.getStudentPlacement(acadYear,
                                                                           semester,stuNum,newPlacement.getModule(),
                                                                           newPlacement.getSchoolCode());
                                      return existingPlacement ;
	        }
	        public StudentPlacement getStoredStuPlacement(StudentPlacementForm studentPlacementForm)throws Exception {
                                         StudentPlacementUI placementUI = new StudentPlacementUI();
                                         return placementUI.getStudentPlacement(Short.parseShort(studentPlacementForm.getAcadYear()),
                                                                   Short.parseShort(studentPlacementForm.getSemester()),
                                                                   Integer.parseInt(studentPlacementForm.getStudentNr()),
                                                                   studentPlacementForm.getStudentPlacement().getModule(),
                                                                   studentPlacementForm.getStudentPlacement().getSchoolCode());
            }
	        public StudentPlacement getStudentPlacement(StudentPlacementForm studentPlacementForm,
	                 StudentPlacementListRecord placementListRec){
                         StudentPlacement  sp=new StudentPlacement();
                         try{
                               short acadYear=Short.parseShort(studentPlacementForm.getAcadYear());
                               short semester=Short.parseShort(studentPlacementForm.getSemester());
                               int  studentNr=Integer.parseInt(studentPlacementForm.getStudentNr());
                               String module =placementListRec.getModule();
                               int schoolCode=placementListRec.getSchoolCode();
                                  sp=sp.getStudentPlacement(acadYear,semester,studentNr,module,schoolCode);
                                  return sp;
                      }catch(Exception ex){
                   	     return sp;  
   	            	}
           }
	        public String   listStudentPlacement(ActionMapping mapping, ActionForm form,
				                      HttpServletRequest request, HttpServletResponse response,ActionMessages messages){
	        	                     	
	        	                         StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
			                         try{
			                          
			                                PlacementValidator placementValidator =new PlacementValidator();
			                                String year=studentPlacementForm.getAcadYear();
			                                String stuNum=studentPlacementForm.getStudentNr();
			                                placementValidator.validateAcademicYear(year, messages);
			                                placementValidator.validateStuNum(stuNum, messages);
			                                studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
			                                if (messages.isEmpty()) {
			                    	              StudentUI studentUI=new StudentUI(); 
			    		                          studentUI.setStudent(studentPlacementForm);
			                    	              placementValidator.validateStuNumRegistered(""+studentPlacementForm.getStudent().getNumber(),
			                    	        		 messages);
			                                }
			                                if (messages.isEmpty()) {
   			                                         studentPlacementForm.setCurrentPage("listStudentPlacement");
			                                }else{
			                     	                studentPlacementForm.setCurrentPage("inputStudentPlacement");
			                                } 
			                                return studentPlacementForm.getCurrentPage();
			                            }catch(Exception ex){
			                    	                  InfoMessagesUtil infoMessagesUtil=new InfoMessagesUtil();
  	    	                    	                  String message="There was an error listing  a placements: eror is +ex.message";
  	    	                    	                  infoMessagesUtil.addMessages(messages, message);
  	    	                    	                  studentPlacementForm.setCurrentPage("inputStudentPlacement");
  	    	                    	                return studentPlacementForm.getCurrentPage();
			                           }
		  }
	      public Set getSchoolCodesInPlacementList(List listPlacements){
	    	                 Set  schoolCodesList=new HashSet();
	    	                  for(int x=0;x<listPlacements.size();x++){
           	    	                	StudentPlacementListRecord  splr=(StudentPlacementListRecord)listPlacements.get(x);
           	    	                	schoolCodesList.add(""+splr.getSchoolCode());
	    	                 }
	    	                  return schoolCodesList;
	    	  
	      }
}
