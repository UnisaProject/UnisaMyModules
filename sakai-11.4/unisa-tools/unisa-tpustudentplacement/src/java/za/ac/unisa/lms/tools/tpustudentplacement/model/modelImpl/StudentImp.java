package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl;

import za.ac.unisa.lms.tools.tpustudentplacement.dao.ContactDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.StudentDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.databaseUtils;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Address;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Contact;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Qualification;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Student;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.StringsUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class StudentImp {
	
	          StudentDAO dao;
	           public StudentImp(){
		                 dao=new StudentDAO(); 
	           }
	           public String getStudentEmail(String stuNum) throws Exception{
		                        return  dao.getStudentEmail(stuNum);
	           }
	           public  String getStudentCell(String studentNr) throws Exception{
		                   return  dao.getStudentCell(studentNr);
	
	           }
	           public List getStudentModuleList(int studentNr, Short acadYear, Short semester)throws Exception{
		                      return dao.getStudentModuleList(studentNr, acadYear, semester);
	           }
	           public void  getStudent(Student student,int studentNr) throws Exception {
		                       dao.getStudent(student,studentNr);
	           }
	           public String getCountryCode(int studentNr) throws Exception {
	        	             return dao.getCountryCode(studentNr);
               }
	           public Student getStudent(int stuNum)throws Exception{
                               return dao.getStudent(stuNum);
               }
	           public String getStudentContactNumber(int studentNumber) throws Exception{
		                        return dao.getStudentContactNumber(studentNumber);
	           }
	           public  Address getStudPostalAddress(int stuNum)throws Exception{
      	                             ContactDAO contactDAO=new ContactDAO();
                                   return contactDAO.getAddress(stuNum,1,1);
               }
               public  Address getStuPhysicalAddress(int stuNum)throws Exception{
                                    ContactDAO contactDAO=new ContactDAO();
                                  return contactDAO.getAddress(stuNum,1,3);
              } 
              public Contact  getContactDetails(int stuNum)throws Exception{
	                                 ContactDAO contactDAO=new ContactDAO();
	                              return contactDAO.getContactDetails(stuNum,1);
	                            
              }
              public Qualification  getStudentQualification(int stuNum,short year) throws Exception{
    	                        Qualification qual = new Qualification();
    	                        qual = dao.getStudentQual(stuNum,year);
    	                     return qual;
              }
              public List getStudentPracticalModuleList(int stuNum,short academicYear,short semester) throws Exception{
		                               return dao.getStudentModuleList(stuNum, academicYear,semester);
              }
              public void setStuQualificationData(Student student,Qualification qual,List moduleList){
	                            student.setQual(qual);	
                                student.setListPracticalModules(moduleList);
                                StringsUtil stringsUtil=new StringsUtil();
                                String moduleString=stringsUtil.getStringFromList(moduleList);
                                student.setPracticalModules(moduleString);
              }
              public void setStuAddresses(Student student,Address postalAddress, Address physicalAddress){
                                student.setPostalAddress(postalAddress);
                                student.setPhysicalAddress(physicalAddress);
              }
              public void setStuContactDetails(Student student,Contact contact){
	                             student.setContactInfo(contact); 
              }
              public void setStudentData(Student student, int stuNum,short acadYear,short semester)throws Exception{
            	                 Qualification qual=getStudentQualification(stuNum,acadYear);
	                             List moduleList=getStudentPracticalModuleList(stuNum,acadYear,semester);
	                             setStuQualificationData(student,qual,moduleList);
	                             Address postalAddress=student.getStuPostalAddress(stuNum);
	                             Address physicalAddress=student.getStuPhysicalAddress(stuNum);
	                             setStuAddresses(student,postalAddress,physicalAddress);
	                             Contact contactDetails=student.getContactDetails(stuNum);
	                             setStuContactDetails(student,contactDetails);
	         }

    

}
