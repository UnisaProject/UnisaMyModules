package za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookListHelperClasses;
import java.util.List;
import org.sakaiproject.user.api.UserDirectoryService;
import za.ac.unisa.lms.tools.booklistadmin.dao.BookListDAO;
import  za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import za.ac.unisa.lms.tools.booklistadmin.module.AuditTrail;
import za.ac.unisa.lms.tools.booklistadmin.module.AuditTrailModule;
import za.ac.unisa.lms.tools.booklistadmin.module.Course;
public class BookListGetterUtil {
	
	AuditTrail auditTrail;
	BookListDAO bookListDAO;
	Course course;
	public BookListGetterUtil(AuditTrail auditTrail, BookListDAO bookListDAO,Course course) {
               this.bookListDAO=bookListDAO;
               this.auditTrail=auditTrail;
               this.bookListDAO= bookListDAO;
                this.course=course;
	}
	public String  getStatus( BooklistAdminForm  booklistAdminForm,String courseId,String year,
			               String typeOfBookList)throws Exception{
		
	                String status=bookListDAO.getStatus(courseId, year, typeOfBookList);
	               if(status.equals("3")){
		                 booklistAdminForm.setStatus("No Books prescribed for this course");		
		            }else if(status.equals("5")){
		                   booklistAdminForm.setStatus("Book list authorized by Dean");			
	                }else if(status.equals("4")){
		                      booklistAdminForm.setStatus("Booklist authorized by School Director");
	                } else if(status.equals("2")){
		                 booklistAdminForm.setStatus("Booklist authorized by COD");
	                } else if(status.equals("1")){
		                       booklistAdminForm.setStatus("Booklist submitted but not authorized");
	                }else if(status.equals("0")){
		                      booklistAdminForm.setStatus("Booklist started but not submitted");
	                }else if(status.equals("-1")){
		                   booklistAdminForm.setStatus("Book list with no activity");
	                }else if(status.equals("6")){
		                    booklistAdminForm.setStatus("Book list published by administrator");
	                }else if(status.equals("7")){
		                     booklistAdminForm.setStatus("Booklist open for editing by administrator");			
	                }
	               return status;
	}
	public List getBookList(BooklistAdminForm booklistAdminForm)throws Exception{
                        String year=booklistAdminForm.getYear().trim();
                        String bookListType=booklistAdminForm.getTypeOfBookList().trim();
                        String  courseCode=booklistAdminForm.getCourseId().trim().trim();
                        List list =bookListDAO.getBookList(courseCode,year,bookListType);
                 return list;
    }
	public List getBookCopyList (String subject, String acadyear,String typeOfbooklist) throws Exception {
		return  bookListDAO.getBookCopyList(subject, acadyear, typeOfbooklist);
	}
	public List getBookCopyList (BooklistAdminForm booklistAdminForm) throws Exception {
                     String year=booklistAdminForm.getYear().trim();
                     String bookListType=booklistAdminForm.getTypeOfBookList().trim();
                     String  courseCode=booklistAdminForm.getCourseId().trim();
                  return  bookListDAO.getBookCopyList( courseCode, year, bookListType);
   }
	public void setBookListDataToBean(BooklistAdminForm booklistAdminForm,UserDirectoryService userDirService)throws Exception{
			  String status=booklistAdminForm.getStatusOption();
            booklistAdminForm.getCourseId();
            List list=getBookList(booklistAdminForm);
            booklistAdminForm.setBooklist(list);
            AuditTrailModule auditTrailModule=auditTrail.getLastUpdated(booklistAdminForm);
            booklistAdminForm.setLastUpdated(auditTrailModule);
            String courseNote=course.getCourseNote(booklistAdminForm);
            booklistAdminForm.setCourseNote(courseNote);
            String lastUpdateName=auditTrail.getNameOfLastUpdater(auditTrailModule, userDirService);
            if(!lastUpdateName.equals("")){
          	  booklistAdminForm.setDisplayAuditTrailName(lastUpdateName);
            }
	}
	

}
