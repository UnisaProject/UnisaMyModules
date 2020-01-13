package za.ac.unisa.lms.tools.booklistadmin.module;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.Utilities;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.CourseHelperClasses.CourseImpl;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;

import java.sql.Types;
import java.util.ArrayList;
import za.ac.unisa.lms.tools.booklistadmin.dao.CourseDAO;
import za.ac.unisa.lms.tools.booklistadmin.dao.DatabaseUtil;

import org.apache.struts.action.ActionMessages;
import org.springframework.jdbc.core.PreparedStatementCreator;
public class Course extends CourseImpl{
	
	
	
	CourseDAO courseDAO;
	CourseImpl courseImpl;
	AuditTrail auditTrail;
	public  Course(){
		    super(new CourseDAO(),new AuditTrail());
		 courseDAO=new CourseDAO();
		 auditTrail=new  AuditTrail();
	}
	
	public void updateCourseBookLink(int status, String course,int  academicYear,String bookListType){
    	courseDAO.updateCourseBookLink(status, course, academicYear, bookListType);
	}
	public void insertCourseBookLink(int  academicYear, String courseId,String courseNotes,int status,String bookListType){
		courseDAO.insertCourseBookLink(academicYear, courseId, courseNotes, status, bookListType);
	}
	public void linkBookToCourse(BookModule bookDetails, String courseId, String acadYear)throws Exception{
		  courseDAO.linkBookToCourse(bookDetails, courseId, acadYear);
   }
	public ArrayList getCourseListpercollege(String code,String year,String status,String typeofbooklist ) throws Exception {
        return courseDAO.getCourseListpercollege(code, year, status, typeofbooklist);
}
public String getCourselistCount(String code,String year,String status,String typeofbooklist ) throws Exception {
return courseDAO.getCourselistCount(code, year, typeofbooklist);
}    
public ArrayList getNoActionCourseList(String code,String year,String typeofbooklist) throws Exception {
return courseDAO.getNoActionCourseList(code, year, typeofbooklist);
}
public String getCourselistCount(String code,String year,String typeofbooklist) throws Exception {

return courseDAO.getCourselistCount(code, year, typeofbooklist);
}
public String courseValid(String subject) throws Exception {
return courseDAO.courseValid(subject);
}
public void saveCourseNote (String studyUnit,String acadYear,String courseNote,String status, String typeofBooklist) throws Exception {
     courseDAO.saveCourseNote(studyUnit, acadYear, courseNote, status, typeofBooklist);
}
}
