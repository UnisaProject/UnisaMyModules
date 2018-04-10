package za.ac.unisa.lms.tools.tpustudentplacement.forms;

import za.ac.unisa.lms.tools.tpustudentplacement.forms.Student;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Qualification;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.StudentValidator;
import java.util.List;
import java.util.ArrayList;

public class StudentImplHelper {
	   
	
	  public  String  getStudentData(Student student,short acadYear,short semester)throws Exception{
             		  //this methods assumes that student.getNumber() gives a valid student number 
                            StudentValidator studValidator=new StudentValidator ();  
                            String validatorRes="";
                            int stuNum=student.getNumber();
                            student.getStudent(stuNum);
                            Qualification qual=student.getStudentQual(stuNum, acadYear);
                            validatorRes=studValidator.validateQualification(qual);
                            List moduleList=new ArrayList();
                            if(validatorRes.equals("")){
                                     moduleList=student.getStudentPracticalModuleList(stuNum,acadYear,semester);
                        	         validatorRes=studValidator.validatePracticalModuleList(moduleList);
                            }
                        	if(validatorRes.equals("")){
                        		     student.setStudentData(student,stuNum,acadYear,semester);
                        	}
                       return validatorRes;
         }

}
