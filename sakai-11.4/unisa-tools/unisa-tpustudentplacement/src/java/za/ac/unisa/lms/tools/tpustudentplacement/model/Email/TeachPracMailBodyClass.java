package za.ac.unisa.lms.tools.tpustudentplacement.model.Email;
import za.ac.unisa.lms.domain.general.Person;
import java.util.List;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.School;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Student;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.model.Email.*;
public class TeachPracMailBodyClass{
	
	         public  String  getSchoolMailData(Person user,School school,Student student,List pList){
		    	                    SchoolEmailData schEmailData=new  SchoolEmailData();
			       	                return schEmailData.getMailBody(user,school,student,pList);
             }
			 public  String  getStudentMailData(Student student,List pList,int schoolCode){
                                  StudentEmailData studentEmailData=new  StudentEmailData();
		                           return studentEmailData.getMailBody(student.getPrintName(),pList,schoolCode);
		     }
			 public String getMailBody(StudentPlacementForm studentPlacementForm){
	                              if(studentPlacementForm.getCommunicationTo().equalsIgnoreCase("Student")){
	            	                        return  getStudentMailData(studentPlacementForm.getStudent(),
	            	                        		   studentPlacementForm.getListStudentPlacement(),studentPlacementForm.getCommunicationSchool());
	                              }else{
	                            	         return  getSchoolMailData(studentPlacementForm.getUser(),studentPlacementForm.getSchool(),
    	            	        		             studentPlacementForm.getStudent(), studentPlacementForm.getListStudentPlacement());
	                               }
            }
}
