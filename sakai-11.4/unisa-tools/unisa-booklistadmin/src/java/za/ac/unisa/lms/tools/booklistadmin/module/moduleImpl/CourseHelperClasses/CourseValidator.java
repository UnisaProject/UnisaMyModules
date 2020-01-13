package za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.CourseHelperClasses;

import org.apache.struts.action.ActionMessages;
import za.ac.unisa.lms.tools.booklistadmin.dao.CourseDAO;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.InfoMessagesUtil;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.Utilities;

public class CourseValidator {

	
	InfoMessagesUtil infoMessagesUtil;
	CourseDAO courseDAO;
	public CourseValidator(CourseDAO courseDAO) {
                  this.courseDAO=courseDAO;
	}
    public void validateCourse(String courseId,ActionMessages messages)throws Exception{
    	           String  errorMsg="";
                   if ((courseId==null)||
                		   (courseId.equalsIgnoreCase(""))){
                	  errorMsg="Please enter search criteria and click on Search again";
			       }else if (courseId.length()<7){
			    	   errorMsg= "Module Code must be minimum of 7 characters";
			       }else {
			    	   String courseValid =courseDAO.courseValid(courseId);
						if (courseValid.equalsIgnoreCase("")){
			                  errorMsg="Please enter valid course code";
						}
			       }
                   InfoMessagesUtil.addMessages(messages, errorMsg);
    }
    public void  validateCourseNotes(BooklistAdminForm booklistAdminForm,ActionMessages messages){
                      String courseNote =Utilities.ltrim(booklistAdminForm.getCourseNote());
                      String  errorMsg="";
                      if((booklistAdminForm.getTxtTitle()==null)||(booklistAdminForm.getTxtTitle().trim()).length() == 0){
                             errorMsg="Title cannot be empty, please enter the Title.";
                      }else if (courseNote.length() == 0 || courseNote==null){
      	                      errorMsg="You cannot save empty course notes, Please enter course notes.";
      	              }else if (courseNote.length() > 1000){
   		                     errorMsg="Course notes should not be more than 1000 characters, Please enter course notes.";
   	                  }else{
   		                     errorMsg="";
   	                  }
                      InfoMessagesUtil.addMessages(messages, errorMsg);
  }

}
