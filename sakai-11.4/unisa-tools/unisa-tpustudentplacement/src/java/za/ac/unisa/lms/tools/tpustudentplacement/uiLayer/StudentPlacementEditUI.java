package za.ac.unisa.lms.tools.tpustudentplacement.uiLayer;
import org.apache.struts.action.ActionMessages;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.StuPlacementActionValidator;
	public class StudentPlacementEditUI{
		
		  public void editplacement(StudentPlacementForm studentPlacementForm,ActionMessages messages )throws Exception{
			                   StuPlacementActionValidator spsv=new StuPlacementActionValidator();
			                   String[] arrayStr = studentPlacementForm.getIndexNrPlacementSelected();
			                   spsv.validateSelectedIndexForEditPlacement(arrayStr,messages); 
			                   if(messages.isEmpty()){
			                	          StudentPlacementListRecord studentPlacementListRecord= getPlacementListRecord(arrayStr,studentPlacementForm);
			                	          StudentPlacement studentPlacement=getPlacaement(studentPlacementForm,studentPlacementListRecord);
		                       	          studentPlacementForm.setStudentPlacement(studentPlacement);
                                 	      studentPlacementForm.setSupervisorCode(studentPlacement.getSupervisorCode());
		                                  studentPlacementForm.setStudentPlacementAction("edit");	
		                                  studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
		                                  studentPlacementForm.setCurrentPage("editStudentPlacement");
	                            }

	       }
           private StudentPlacement getPlacaement(StudentPlacementForm studentPlacementForm,
		                       StudentPlacementListRecord studentPlacementListRecord)throws Exception{
	                                 StudentPlacement studentPlacement=new StudentPlacement();
                                 return    studentPlacement.getStudentPlacement(Short.parseShort(studentPlacementForm.getAcadYear()),
          			                                Short.parseShort(studentPlacementForm.getSemester()),
        			                                Integer.parseInt(studentPlacementForm.getStudentNr()),
        			                                studentPlacementListRecord.getModule(),
        			                                studentPlacementListRecord.getSchoolCode());
            }
           
           private StudentPlacementListRecord getPlacementListRecord(String[] arrayStr,
            		                StudentPlacementForm studentPlacementForm)throws Exception{
	                                StudentPlacementListRecord studentPlacementListRecord = new StudentPlacementListRecord();
                                    for (int i=0; i <arrayStr.length; i++) {
                                              studentPlacementListRecord = (StudentPlacementListRecord)
                                        		        studentPlacementForm.getListStudentPlacement().get(Integer.parseInt(arrayStr[i]));
                                     }
                                    return studentPlacementListRecord;
             }
}
