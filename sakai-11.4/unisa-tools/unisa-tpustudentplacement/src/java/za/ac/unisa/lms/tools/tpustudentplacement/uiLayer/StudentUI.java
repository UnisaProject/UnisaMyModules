package za.ac.unisa.lms.tools.tpustudentplacement.uiLayer;

import java.util.ArrayList;
import java.util.List;

import za.ac.unisa.lms.tools.tpustudentplacement.dao.StudentPlacementDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Address;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Contact;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Qualification;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Student;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentImplHelper;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.StudentImp;
public class StudentUI extends StudentImp{
	
		   public void setStudentCellNrToFormBean(StudentPlacementForm studentPlacementForm)throws Exception{
			                 String studentNr=studentPlacementForm.getStudentNr();          
			                 studentPlacementForm.setCommunicationCellNumber(getStudentCell(studentNr));
                          
		   }
		   public void  setStudentCommunicationDetToFormBean(StudentPlacementForm studentPlacementForm) throws Exception{
                            String email=studentImp.getStudentEmail(""+studentPlacementForm.getStudent().getNumber());
	                        studentPlacementForm.setCommunicationEmailAddress(email);
	                        ContactUI contactUI=new ContactUI();
		                    Contact contact = contactUI.getContactDetails(studentPlacementForm.getStudent().getNumber(), 1);
	                        studentPlacementForm.setCommunicationCellNumber(contact.getCellNumber());
           }
		   StudentImp  studentImp;
			StudentImplHelper studentImplHelper;
			public StudentUI(){
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
			public void getStudent(Student student, int studentNr) throws Exception {
				         studentImp.getStudent(student,studentNr);
			}
			public  String  getStudentData(Student student,short acadYear,short semester)throws Exception{
		                  		  //if empty string is returned then the data is valid
		                          return studentImplHelper.getStudentData(student,acadYear,semester);
		    }
			public String getStudentContactNumber(int studentNumber) throws Exception{
				            return studentImp.getStudentContactNumber(studentNumber);
			}
			public Contact getContactDetails(int stuNum)throws Exception{
		    	                       return  studentImp.getContactDetails(stuNum);
		    }
			  public Contact getContactDetails(String stuNum)throws Exception {
		    	            int studentNr=Integer.parseInt(stuNum);
		    	            ContactUI contactUI=new ContactUI(); 
		    	          return contactUI.getContactDetails(studentNr, 1);
               
		    }
	        
	        public void  setStudentEmailToFormBean(StudentPlacementForm studentPlacementForm) throws Exception{
	        	               String email=studentImp.getStudentEmail(""+studentPlacementForm.getStudent().getNumber());
	            	           studentPlacementForm.setCommunicationEmailAddress(email);
	         }
	         public void  setStudent(StudentPlacementForm studentPlacementForm)throws Exception {
			           int stuNum=Integer.parseInt(studentPlacementForm.getStudentNr());
	    	           short semester=Short.parseShort(studentPlacementForm.getSemester());
	    	           short academicYear=Short.parseShort(studentPlacementForm.getAcadYear());
	    	           Student student=getStudent(stuNum);
	    	           List moduleList=student.getStudentPracticalModuleList(stuNum, academicYear, semester);
	    	           student.setListPracticalModules(moduleList);
	    	           StringsUtil stringsUtil=new StringsUtil();
                       String moduleString=stringsUtil.getStringFromList(moduleList);
                       student.setPracticalModules(moduleString);
                       Qualification qual=getStudentQualification(stuNum,academicYear);
                       Address postalAddress=student.getStuPostalAddress(stuNum);
                       Address physicalAddress=student.getStuPhysicalAddress(stuNum);
                       setStuAddresses(student,postalAddress,physicalAddress);
                       Contact contactDetails=student.getContactDetails(stuNum);
                       setStuContactDetails(student,contactDetails);
                       student.setQual(qual);
	    	           if(student.getNumber()!=null){
	    	                          setStudentPlacementList(studentPlacementForm);
	    	           }else{
	       	        	      student.setNumber(-1);   
	       	           }
	    	           studentPlacementForm.setStudent(student);
		}
		private void  setStudentPlacementList(StudentPlacementForm studentPlacementForm) throws Exception{
			int stuNum=Integer.parseInt(studentPlacementForm.getStudentNr());
	    	short semester=Short.parseShort(studentPlacementForm.getSemester());
	    	short academicYear=Short.parseShort(studentPlacementForm.getAcadYear());
	 		   		        StudentPlacementDAO dao = new StudentPlacementDAO();
	                        List listStudentPlacement = new ArrayList<StudentPlacementListRecord>();
	                        listStudentPlacement = dao.getStudentPlacementList(academicYear,semester,stuNum);
	                        studentPlacementForm.setListStudentPlacement(listStudentPlacement);
	    }

}
