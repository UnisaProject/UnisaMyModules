package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionMessages;

import za.ac.unisa.lms.tools.tpustudentplacement.dao.databaseUtils;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Student;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import  za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.DateUtil;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.StuEditWinValidator;;
public class StuPlacementEditWinBuilder {
	
	 public void buildEditWin(StudentPlacementForm studentPlacementForm,ActionMessages messages ){ 
		               StuEditWinValidator stuEditWinValidator=new StuEditWinValidator();
		               String[] indexArr=studentPlacementForm.getIndexNrPlacementSelected();
		               stuEditWinValidator.validateSelectedPlacement(messages, indexArr);
		               if(!messages.isEmpty()){
		            	   return;
		               }
		               StudentPlacementListRecordImpl spImpl=new  StudentPlacementListRecordImpl();
		               StudentPlacementListRecord studentPlacementListRecord =spImpl.getStuPlacementListRecord(studentPlacementForm);
		       		   StuPlacementReader stuPlacementReader=new StuPlacementReader();
		       		   StudentPlacement studentPlacement =stuPlacementReader.getStudentPlacement(studentPlacementForm, studentPlacementListRecord);
		       		   studentPlacementForm.setStudentPlacement(studentPlacement);
		       		   String module=studentPlacement.getModule();
		       		   if((studentPlacement==null)||(module==null)||(module.equals(""))){
		       				  return;
		          	   }
		       		   setModulesForPrevYear(studentPlacementForm,module);
		       		   studentPlacementForm.setSupervisorCode(studentPlacement.getSupervisorCode());
		       		   studentPlacementForm.setStudentPlacement(studentPlacement);
		       		   studentPlacementForm.setStudentPlacementAction("edit");	
		       		   studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
		       		   studentPlacementForm.setCurrentPage("editStudentPlacement");
		       		   StudentPlacementImage imageBuilder=new StudentPlacementImage(studentPlacement);
		       		   studentPlacementForm.setPlacementImage(imageBuilder.getPlacementImage());
		       		            
	 }
	 private void setModulesForPrevYear(StudentPlacementForm studentPlacementForm,String module){
		                DateUtil   dateUtil=new DateUtil(); 
                        if(Integer.parseInt(studentPlacementForm.getAcadYear())<dateUtil.yearInt()){
 		                       Student  student=studentPlacementForm.getStudent();
 		                       List listPracticalModules=new ArrayList();
 		                       listPracticalModules.add(module);
                               student.setListPracticalModules(listPracticalModules);
                       }              
 	}

}
