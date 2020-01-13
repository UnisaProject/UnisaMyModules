package za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.CourseHelperClasses;

import org.apache.struts.action.ActionMessages;
import za.ac.unisa.lms.tools.booklistadmin.dao.CourseDAO;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import za.ac.unisa.lms.tools.booklistadmin.module.AuditTrail;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.InfoMessagesUtil;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.Utilities;

public class CourseImpl {

	CourseDAO courseDAO;
	AuditTrail auditTrail;
	public  CourseImpl(CourseDAO courseDAO,AuditTrail auditTrail){
		       this.courseDAO=courseDAO;
		       this.auditTrail=auditTrail;
	}
	
    public void validateCourse(String courseId,ActionMessages messages)throws Exception {
    	            CourseValidator  courseValidator=new  CourseValidator(courseDAO);
    	            courseValidator.validateCourse(courseId, messages);
    }
    public void  validateCourseNotes(BooklistAdminForm booklistAdminForm,ActionMessages messages){
    	                  String courseNote =Utilities.ltrim(booklistAdminForm.getCourseNote());
        		          String  errorMsg="";
        		          if ( courseNote==null ||courseNote.length() == 0){
		                	         errorMsg="You cannot save empty course notes, Please enter course notes.";
		                	         
			         	  }else if (courseNote.length() > 1000){
			         		          errorMsg="Course notes should not be more than 1000 characters, Please enter course notes.";
			         	  }
        		          InfoMessagesUtil.addMessages(messages, errorMsg);
    }
    public void saveCourseNote(String status,BooklistAdminForm booklistAdminForm,ActionMessages messages) throws Exception{
    	                String studyUnit = booklistAdminForm.getCourseId().trim();
  		                String acadYear = booklistAdminForm.getYear().trim();
  		                String courseNote =booklistAdminForm.getCourseNote().trim();	
  		                String typeOfBookList=booklistAdminForm.getTypeOfBookList().trim();
    	                courseDAO.saveCourseNote(studyUnit, acadYear, courseNote ,status,typeOfBookList);
     	                 auditTrail.updateAuditTrail(booklistAdminForm, "Admin updated course note ");
   }
   public String getCourseNote (String subject,String acadyear, String typeofBooklist) throws Exception {
    	return courseDAO.getCourseNote(subject, acadyear, typeofBooklist);
   }
  public String getCourseNote (BooklistAdminForm booklistAdminForm) throws Exception {
    	                    String courseId = booklistAdminForm.getCourseId().trim();
                            String year = booklistAdminForm.getYear().trim();
                             String typeOfBookList=booklistAdminForm.getTypeOfBookList().trim();
                             return courseDAO.getCourseNote(courseId, year, typeOfBookList);
	}
  public void declareNoBooks (BooklistAdminForm booklistAdminForm) throws Exception {
      String courseId = booklistAdminForm.getCourseId().trim();
      String year = booklistAdminForm.getYear().trim();
       String typeOfBookList=booklistAdminForm.getTypeOfBookList().trim();
      courseDAO.declareNoBooks(Integer.parseInt(year),courseId,typeOfBookList);
}
  
	
}
