package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionMessages;

import za.ac.unisa.lms.tools.tpustudentplacement.dao.StudentPlacementDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.PlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.StudentPlacementEditUI;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.PlacementUtilities;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.InfoMessagesUtil;

public class StudentPlacementUI  extends StudentPlacementImpl{
	
	      public void   createAddPlacementScreen(StudentPlacementForm studentPlacementForm){
		                 StudentPlacement placement = new StudentPlacement();			
			            //initialise values
			            studentPlacementForm.setStudentPlacement(placement);
			          studentPlacementForm.setStudentPlacementAction("add");	
			          studentPlacementForm.setSchoolCalledFrom("editStudentPlacement");
			          studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
			          studentPlacementForm.setCurrentPage("editStudentPlacement");
	       }
	        public void editplacement(StudentPlacementForm studentPlacementForm,ActionMessages messages )throws Exception{
	    	                StudentPlacementEditUI speui=new StudentPlacementEditUI();
	    	                speui.editplacement(studentPlacementForm, messages);
	        }
	        public ActionMessages  saveStuPlacement(StudentPlacementForm studentPlacementForm)throws Exception{
                                       StuPlacementCRUDHelperClass stuPlacementCRUDHelperClass=new StuPlacementCRUDHelperClass();
                                       if(studentPlacementForm.getStudentPlacementAction().equals("Add")){
                                             if( checkForDuplicatePlacement(studentPlacementForm)){
                                            	 InfoMessagesUtil msgUtil=new InfoMessagesUtil();
                                            	 ActionMessages  messages=new ActionMessages();
                                            	 String message="The placement already exists";
                                            	 msgUtil.addMessages(messages, message);
                                            	 return messages;
                                             }
                                        }
                                       return stuPlacementCRUDHelperClass.saveStudentPlacement(studentPlacementForm);
	        }
	        public void setStuPlacementList(StudentPlacementForm studentPlacementForm){
	                                        try{
	                                             StudentPlacementDAO dao = new StudentPlacementDAO();
                                                 List studentPlacementList = dao.getStudentPlacementList(Short.parseShort(studentPlacementForm.getAcadYear()),
				                                              Short.parseShort(studentPlacementForm.getSemester()),Integer.parseInt(studentPlacementForm.getStudentNr().trim()));
                                                         studentPlacementForm.setListStudentPlacement(studentPlacementList);
	                                       }catch(Exception ex){}
            }
	        private boolean checkForDuplicatePlacement(StudentPlacementForm studentPlacementForm)throws Exception {  
	        	                 return isPlacementExisting(Short.parseShort(studentPlacementForm.getAcadYear())
	        	                		 ,Short.parseShort(studentPlacementForm.getSemester()), 
	        	                		 Integer.parseInt(studentPlacementForm.getStudentNr()), studentPlacementForm.getStudentPlacement());
	        }
	        public void setPlacementList(StudentPlacementForm studentPlacementForm,Short province) throws Exception {     
                                StudentPlacement stuPlacement = new StudentPlacement();
                                List list = new ArrayList<PlacementListRecord>();
                                list = stuPlacement.getPlacementList(Short.parseShort(studentPlacementForm.getAcadYear()), 
                                  Short.parseShort(studentPlacementForm.getSemester()), 
                                  province, 
                                  studentPlacementForm.getPlacementFilterDistrict(), 
                                  studentPlacementForm.getPlacementFilterSupervisor(), 
                                  studentPlacementForm.getPlacementFilterSchool(), 
                                  studentPlacementForm.getPlacementFilterModule(), 
                                  studentPlacementForm.getPlacementSortOn(),
                                  studentPlacementForm.getPlacementFilterCountry());
                                  studentPlacementForm.setListPlacement(list);
          }
	      public void initForIntCountry(StudentPlacementForm studentPlacementForm){
		                    if(!studentPlacementForm.getPlacementFilterCountry().equals(PlacementUtilities.getSaCode())){
     	                         studentPlacementForm.setSchoolFilterProvince(Short.parseShort("0"));
     	                         studentPlacementForm.setSchoolFilterCountry(studentPlacementForm.getPlacementFilterCountry());
     	                         studentPlacementForm.setPlacementFilterDistrict(Short.parseShort("0"));
     	                         studentPlacementForm.setPlacementFilterDistrictValue(null);
     	                         studentPlacementForm.setPlacementFilterProvince(Short.parseShort("0"));
     	                         studentPlacementForm.setSchoolCalledFrom("inputPlacement");
                            }
	    }
}
