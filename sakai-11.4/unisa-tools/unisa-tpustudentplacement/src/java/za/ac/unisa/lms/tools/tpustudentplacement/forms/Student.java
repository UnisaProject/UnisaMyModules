package za.ac.unisa.lms.tools.tpustudentplacement.forms;

import java.util.ArrayList;
import java.util.List;

import za.ac.unisa.lms.tools.tpustudentplacement.dao.ContactDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.StudentDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.StudentImp;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.StringsUtil;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.StudentValidator;

public class Student {
	
	private String name;
	private String printName;
	private Integer number;
	private Qualification qual;
	private Address postalAddress;
	private Address physicalAddress;
	private Contact contactInfo;
	private List listPracticalModules;
	private String practicalModules;
	
	StudentImp  studentImp;
	StudentImplHelper studentImplHelper;
	public Student(){
		        studentImp=new StudentImp();
		        studentImplHelper=new  StudentImplHelper();
	}
	public String getStudentEmail(String stuNum) throws Exception{
		return  studentImp.getStudentEmail(stuNum);
	}
	public  List getStudentModuleList(int studentNr, Short acadYear, Short semester)throws Exception{
		return  studentImp.getStudentModuleList(studentNr, acadYear, semester);
	
	}
	public  String getStudentCell(String studentNr) throws Exception{
		return  studentImp.getStudentCell(studentNr);
	}
	public  String getCountryCode(int studentNr) throws Exception{
		 return  studentImp.getCountryCode(studentNr);
	}
	public void getStudent(int studentNr) throws Exception {
		         studentImp.getStudent(this,studentNr);
	}
	public  String  getStudentData(Student student,short acadYear,short semester)throws Exception{
                  		  //if empty string is returned then the data is valid
                          return studentImplHelper.getStudentData(student,acadYear,semester);
    }
	 public Qualification getStudentQual(int studentNr, Short acadYear) throws Exception {
		 return studentImp.getStudentQualification(studentNr, acadYear);
	 }
	public String getPracticalModules() {
		return practicalModules;
	}
	public String getPrintName() {
		return printName;
	}
	public void setPrintName(String printName) {
		this.printName = printName;
	}
	public void setPracticalModules(String practicalModules) {
		this.practicalModules = practicalModules;
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
	public Qualification getQual() {
		return qual;
	}
	public void setQual(Qualification qual) {
		this.qual = qual;
	}
	public Address getPostalAddress() {
		return postalAddress;
	}
	public void setPostalAddress(Address postalAddress) {
		this.postalAddress = postalAddress;
	}
	public Address getPhysicalAddress() {
		return physicalAddress;
	}
	public void setPhysicalAddress(Address physicalAddress) {
		this.physicalAddress = physicalAddress;
	}
	public Contact getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(Contact contactInfo) {
		this.contactInfo = contactInfo;
	}
	public List getListPracticalModules() {
		return listPracticalModules;
	}
	public void setListPracticalModules(List listPracticalModules) {
		this.listPracticalModules = listPracticalModules;
	}	
	         public String getStudentContactNumber(int studentNumber) throws Exception{
		            return studentImp.getStudentContactNumber(studentNumber);
	         }
	         
             public  Address getStuPostalAddress(int stuNum)throws Exception{
      	                        return studentImp.getStudPostalAddress(stuNum);
             }
             public  Address getStuPhysicalAddress(int stuNum)throws Exception{
            	               return studentImp.getStuPhysicalAddress(stuNum);
             } 
             public Contact getContactDetails(int stuNum)throws Exception{
    	                       return  studentImp.getContactDetails(stuNum);
             }
             public Qualification  getStudentQualification(int stuNum,short year) throws Exception{
	                            return studentImp.getStudentQualification(stuNum,year);
             }
             public List getStudentPracticalModuleList(int stuNum,short academicYear,short semester) throws Exception{
                            return studentImp.getStudentPracticalModuleList(stuNum, academicYear, semester);
             }
             public void setStudentData(Student student, int stuNum,short acadYear,short semester)throws Exception{
            	                studentImp.setStudentData(student,stuNum,acadYear,semester);
            	                  
             }
             
	      
}
