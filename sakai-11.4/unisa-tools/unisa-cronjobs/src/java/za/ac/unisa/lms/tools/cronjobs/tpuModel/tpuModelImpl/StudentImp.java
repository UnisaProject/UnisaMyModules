package za.ac.unisa.lms.tools.cronjobs.tpuModel.tpuModelImpl;

import za.ac.unisa.lms.tools.cronjobs.dao.tpuDAO.StudentDAO;
import za.ac.unisa.lms.tools.cronjobs.forms.model.Student;
public class StudentImp {
	
	  StudentDAO dao;
	  public StudentImp(){
		       dao=new StudentDAO(); 
	  }
	  public String getStudentName(int studentNr) throws Exception {
		  return  dao.getStudent(studentNr).getName();
	  }
	  public String getStudentContactNumber(int studentNumber) throws Exception{
		return dao.getStudentContactNumber(studentNumber);
	  }
	  public void getStudent(Student student,int studentNr) throws Exception {
		           dao.getStudent(student,studentNr);
      }
}
