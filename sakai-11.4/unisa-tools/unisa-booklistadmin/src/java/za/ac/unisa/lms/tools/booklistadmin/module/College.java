package za.ac.unisa.lms.tools.booklistadmin.module;

import java.util.ArrayList;
import za.ac.unisa.lms.tools.booklistadmin.dao.CollegeDAO;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;

public class College {
	
	
	public ArrayList getCollegeList() throws Exception {
		      CollegeDAO dao=new CollegeDAO();
	          return dao.getCollegeList();
	}
	public String collegeName(String subject) throws Exception {
		            CollegeDAO dao=new CollegeDAO();
		           return dao.collegeName(subject);
	}
	public void setCollege(BooklistAdminForm booklistAdminForm){
	                 try{
		                        String college=booklistAdminForm.getColleg();
		                        String[] collegewithcode= college.split("-");
		                        String collegeCode=collegewithcode[0];
		                        String collegeName=collegewithcode[1];
		                        booklistAdminForm.setCollegeCode(collegeCode);
		                        booklistAdminForm.setCollege(collegeName);
		              }catch(Exception ex){
		            	          //booklistAdminForm.setCancelOption("TOCOURSEVIEW");
		              }
   }
	

}
