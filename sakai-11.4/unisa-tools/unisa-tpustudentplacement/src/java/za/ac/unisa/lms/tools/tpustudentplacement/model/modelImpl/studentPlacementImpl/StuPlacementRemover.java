package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import za.ac.unisa.lms.tools.tpustudentplacement.dao.StudentPlacementDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl.StudentPlacementLogUI;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl.StudentPlacementUI;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.StudentPlacementListRecordUI;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.StuPlacementActionValidator;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.InfoMessagesUtil;

public class StuPlacementRemover {
	    
	         StudentPlacementDAO dao;
	         StuPlacementReader stuPlacementReader;
	         public StuPlacementRemover(){
                       dao=new  StudentPlacementDAO();
                       stuPlacementReader=new StuPlacementReader();
             }
	  	     public void removeStudentPlacement(Short acadYear,Short semester,Integer studentNr,
                               String module,int schoolCode) throws Exception {
                               dao.removeStudentPlacement(acadYear, semester, studentNr,module,schoolCode);
             }
	  	     private void removeStudentPlacement(StudentPlacementListRecord placementListRecord,
	  			                StudentPlacementForm studentPlacementForm,
	  			                ActionMessages messages){
	  	    	                    try{
	  		                                int schoolCode=placementListRecord.getSchoolCode();
	  		                                String module=placementListRecord.getModule();
	  		                                short semester=Short.parseShort(studentPlacementForm.getSemester());
	  		                                short acadYear=Short.parseShort(studentPlacementForm.getAcadYear());
	  		                                int studentNr=Integer.parseInt(studentPlacementForm.getStudentNr());
	  		                                removeStudentPlacement(acadYear, semester, studentNr,module,schoolCode);
	  	    	                    }catch(Exception ex){
	  	    	                    	InfoMessagesUtil infoMessagesUtil=new InfoMessagesUtil();
	  	    	                    	String message="There was an error removing a placement: eror is +ex.message";
	  	    	                    	infoMessagesUtil.addMessages(messages, message);
                    	  	    	 }
             }
	  	     public String removeStupdentPlacement(ActionMapping mapping,
	                                   HttpServletRequest request,
	                                   HttpServletResponse response,
	                                   StudentPlacementForm studentPlacementForm,
	                                   ActionMessages messages) throws Exception{
	  		                                  
	  	    	                          StuPlacementActionValidator validator=new StuPlacementActionValidator();
                                          String []  indexArr=studentPlacementForm.getIndexNrPlacementSelected();
                                           validator.validateIndexForRemovingPlacement(indexArr, messages);
                                          StudentPlacementListRecordUI stuPlacListRecUI=new StudentPlacementListRecordUI();
                                          StudentPlacementListRecord placementListRecord =
                                        		  stuPlacListRecUI.getSelectedPlacemenRecord(studentPlacementForm);
                                          String nextPage="listStudentPlacement";
                                          validateEvalMarkForRemovePlacement(studentPlacementForm,placementListRecord,messages);
                                          if (messages.isEmpty()) {
              	                                    removeStudentPlacement(placementListRecord,studentPlacementForm,messages);
                                                    studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
                                                    studentPlacementForm.setCommunicationSchool(placementListRecord.getSchoolCode());
                                                    nextPage=stuPlacementReader.listStudentPlacement(mapping, studentPlacementForm,    
                                                    		 request, response,messages);
                                         }
                                         if (messages.isEmpty()) {
                                        	       setLog(studentPlacementForm,placementListRecord);
                                         }
                                 return nextPage;
                }
	  	        private void validateEvalMarkForRemovePlacement(StudentPlacementForm studentPlacementForm,
                                 StudentPlacementListRecord placementListRecord,ActionMessages messages){
                                 if (messages.isEmpty()) {
                    	                StuPlacementActionValidator validator=new StuPlacementActionValidator();
                                        String evalMark=placementListRecord.getEvaluationMark();
                                        validator.validateEvalMark(evalMark, messages);
                                 }
               }
	  	        private void setLog(StudentPlacementForm studentPlacementForm,
	  	        		            StudentPlacementListRecord placementListRecord){
	  	        	                      try{
	  	        	                            StudentPlacementUI spUI=new StudentPlacementUI();
	  	        	                            StudentPlacement sp=spUI.getStudentPlacement(studentPlacementForm,placementListRecord);
	  	        	                            StudentPlacementLogUI  splUI=new StudentPlacementLogUI();
	  	        	                            splUI.setLogOnDeletePlacement(studentPlacementForm);
	  	        	                      }catch(Exception ex){}
	  	        }
	  	   	  	     
}
