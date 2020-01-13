package za.ac.unisa.lms.tools.cronjobs.forms.model;

import za.ac.unisa.lms.tools.cronjobs.tpuModel.tpuModelImpl.StudentImp;

public class Student {
	
	private String name;
	private String printName;
	private Integer number;
	
	StudentImp  studentImp;
	public Student(){
		     studentImp=new StudentImp();
	}
	
	 public String getStudentName(int studentNr) throws Exception {
		  return  studentImp.getStudentName(studentNr);
	  }
	public String getPrintName() {
		return printName;
	}
	public void setPrintName(String printName) {
		this.printName = printName;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	public String getStudentContactNumber(int studentNumber) throws Exception{
		            return studentImp.getStudentContactNumber(studentNumber);
	}
	public void getStudentData(int studentNr) throws Exception {
        studentImp.getStudent(this,studentNr);
   }

}
