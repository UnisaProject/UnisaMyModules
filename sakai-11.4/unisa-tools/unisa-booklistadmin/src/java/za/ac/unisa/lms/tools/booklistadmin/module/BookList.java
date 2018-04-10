package za.ac.unisa.lms.tools.booklistadmin.module;
import  za.ac.unisa.lms.tools.booklistadmin.module.AuditTrailModule;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookListHelperClasses.BooklistImpl;
import za.ac.unisa.lms.tools.booklistadmin.module.view.BookListView;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMessages;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import za.ac.unisa.lms.tools.booklistadmin.dao.BookListDAO;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
public class BookList extends BooklistImpl{
	
	    BookListDAO bookListDAO;
	    
	    public  BookList(){
	    	       super(new BookListDAO(),new AuditTrail(),new Course());
	               bookListDAO =new BookListDAO ();
	    }
		public List getBookList (String subject, String acadyear, String typeOfbook) throws Exception {
			           return  bookListDAO.getBookList(subject, acadyear, typeOfbook);
         }
		public List getBookList(String code, String acadyear, String typeOfbook,String status) throws Exception {
			                  return  bookListDAO.getBookList(code, acadyear, typeOfbook, status);
		}
		public String getStatus(String subject,String acadyear, String typeofBooklist) throws Exception {
			return  bookListDAO.getStatus(subject, acadyear, typeofBooklist);
	    }
        public void updateCourseBookLink(int status, String course,int  academicYear,String bookListType){
		                 bookListDAO.updateCourseBookLink(status, course, academicYear, bookListType);		       
	    }
	    public void insertCourseBookLink(int  academicYear, String courseId,String courseNotes,int status,String bookListType){
		                 bookListDAO.insertCourseBookLink(academicYear, courseId, courseNotes, status, bookListType);
	    }
	    public String viewCourseList(College college,BooklistAdminForm booklistAdminForm,HttpServletRequest request,
	                     ActionMessages messages,String nextPage )throws Exception{
                         BookListView bookList=new BookListView();
                     return bookList.viewCourseList(college,booklistAdminForm, request, messages, nextPage);
        }
	    public String confirmBookList(BooklistAdminForm booklistAdminForm,ActionMessages messages){
		                BookListView bookList=new BookListView();
		                return bookList.confirmBookList(booklistAdminForm, messages);
       }
	   public String getListUpdaterDisplayName(AuditTrailModule lastUpdateInfo,UserDirectoryService dirService) throws Exception{
		   String lastUpdaterName="";
		   try{
                   String userId =lastUpdateInfo.getNetworkId();
                   User lastUpdatedUserDetails = dirService.getUserByEid(userId);
                   lastUpdaterName = lastUpdatedUserDetails.getDisplayName();
		   }catch(Exception ex){}
           return lastUpdaterName;
      }
   
}
