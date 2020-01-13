package za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.CourseHelperClasses;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import  za.ac.unisa.lms.tools.booklistadmin.module.Course;
import  za.ac.unisa.lms.tools.booklistadmin.module.Book;
import  za.ac.unisa.lms.tools.booklistadmin.module.College;
import  za.ac.unisa.lms.tools.booklistadmin.module.BookList;
import  za.ac.unisa.lms.tools.booklistadmin.module.AuditTrailModule;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.InfoMessagesUtil;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses.BookGetterUtil;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses.BookSearchUtils;

import org.apache.struts.action.ActionMessages;
import org.sakaiproject.user.api.UserDirectoryService;
import java.util.List;
public class CourseSearcher {

	
	Course course;
	BookList bookList;
	College college;
	InfoMessagesUtil  infoMessagesUtil;
	BookSearchUtils bookSearchUtils;
	public CourseSearcher( BookGetterUtil  bookGetterUtil,Course course,BookList bookList,College college) {
		            this.course=course;
		            this.bookList= bookList;
		             this.college=college;
		    infoMessagesUtil =new InfoMessagesUtil();
		    bookSearchUtils=new BookSearchUtils(bookGetterUtil,bookList);
	}
	public  String  searchBookByCourseCode(UserDirectoryService uDirService,BooklistAdminForm booklistAdminForm,
			                ActionMessages messages) throws Exception{
	                    	  
		                         String nextPage= "searchcourse";
		                         try{
		                                String courseId=booklistAdminForm.getCourseId().trim().toUpperCase();
		                                course.validateCourse(courseId, messages);
	                    	            if(messages.isEmpty()){
	                    	                     nextPage=getListData(booklistAdminForm, uDirService,messages,courseId);
	                    	            }
			                     }catch (Exception e) {
			                    	 e.printStackTrace();
            			                                 bookSearchUtils.handleException(messages,e);
            			                                 nextPage= "adminmenu";
                                 }
		                         return nextPage;
	}
	private String getListData(BooklistAdminForm booklistAdminForm,UserDirectoryService uDirService,
			                  ActionMessages messages,String courseId)throws Exception{
		                            
		                             String year=booklistAdminForm.getYear();
                                     String bookListType=booklistAdminForm.getTypeOfBookList();   
		                             String nextPage= "displaybooklist";
		                             String status=bookList.getStatus(courseId.trim(),year.trim(), bookListType.trim());
	                                 if(!status.equals("3")){
	    	                                setBookListInfoToFormBean(booklistAdminForm,courseId,year,bookListType );
                                            List list=booklistAdminForm.getBooklist();
                                            AuditTrailModule lastUpdateInfo=booklistAdminForm.getLastUpdated();
                                            if(list.size()==0){
                                            }else{
    	                                           setUpdaterInfo(lastUpdateInfo,booklistAdminForm,uDirService);
                                            }
                                      }else{
                                        	  InfoMessagesUtil.addMessages(messages," No books declared for this course");
                                      }
	                                 return nextPage;
   }
	private void  setUpdaterInfo(AuditTrailModule updateInfo,BooklistAdminForm form,UserDirectoryService uDirService)throws Exception{
		                String updaterName="";
		                try{
			                 updaterName=bookList.getListUpdaterDisplayName(updateInfo,uDirService);
		                }catch(Exception  e){}
		                     if ( updateInfo != null) {
		                    	       form.setDisplayAuditTrailName(updaterName);
                              }
   }
	private void setBookListInfoToFormBean(BooklistAdminForm booklistAdminForm,String courseId, 
			          String year,String bookListType )throws Exception{
		                   
		                   List list=bookSearchUtils.getList(booklistAdminForm);
                           AuditTrailModule lastUpdateInfo=bookList.getLastUpdated(courseId,year,bookListType);
                           booklistAdminForm.setBooklist(list);
                           booklistAdminForm.setLastUpdated(lastUpdateInfo);
                           String courseNotes=course.getCourseNote(courseId,year,bookListType);
                           booklistAdminForm.setCourseNote(courseNotes);
                           booklistAdminForm.setCollege(college.collegeName(courseId));
       
	}
	
}
