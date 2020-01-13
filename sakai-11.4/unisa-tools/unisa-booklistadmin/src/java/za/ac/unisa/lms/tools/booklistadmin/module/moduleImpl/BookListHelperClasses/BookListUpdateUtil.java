package za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookListHelperClasses;
import org.apache.struts.action.ActionMessages;
import za.ac.unisa.lms.tools.booklistadmin.module.AuditTrail;
import za.ac.unisa.lms.tools.booklistadmin.module.AuditTrailModule;
import za.ac.unisa.lms.tools.booklistadmin.module.Course;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.InfoMessagesUtil;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import za.ac.unisa.lms.tools.booklistadmin.dao.BookListDAO;

public class BookListUpdateUtil extends BookListStatusUtil{

	AuditTrail auditTrail;
	BookListDAO bookListDAO;
	Course course;
	BookListGetterUtil bookListDataGetter;
	public BookListUpdateUtil(AuditTrail auditTrail, BookListDAO bookListDAO) {
		                          super(bookListDAO);
		                          this.auditTrail=auditTrail;
		                          this.bookListDAO= bookListDAO;
	}
	public void setBookStatusToNoBooks(BooklistAdminForm booklistAdminForm,ActionMessages   messages)throws Exception{
		                updateBookListStatus(booklistAdminForm);
		                updateAuditTrail(booklistAdminForm);
		                addMsgsForUser(booklistAdminForm,messages);
    }
	
	private void updateAuditTrail(BooklistAdminForm booklistAdminForm){
		            if(booklistAdminForm.getTypeOfBookList().equals("E")){
	       	              auditTrail.updateAuditTrail(booklistAdminForm, "No eReserves prescribed for this course");
	               }else{
	       	              auditTrail.updateAuditTrail(booklistAdminForm, "No books prescribed for this course");
	              }
	}
	public AuditTrailModule getLastUpdated(String courseId,String year,String typeofbooklist)throws Exception {
        return auditTrail.getLastUpdated(courseId, year, typeofbooklist);
    }
	private void addMsgsForUser(BooklistAdminForm booklistAdminForm,ActionMessages   messages){
		              String errorMsg="";
		              if(booklistAdminForm.getTypeOfBookList().equals("E")){
		            	      errorMsg="Successful declaration of no eReserves. No authorization required.";
			   		  }else{
			   			      errorMsg="Successful declaration of no books. No authorization required.";
		           	}
		            InfoMessagesUtil.addMessages(messages,errorMsg);
	}
	public void updateBookListStatus(String bookListStatus,BooklistAdminForm booklistAdminForm,String networkID)throws Exception{
		               String year=booklistAdminForm.getYear().trim();
	                   String bookListType=booklistAdminForm.getTypeOfBookList().trim();
	                   String  courseId=booklistAdminForm.getCourseId().trim();
                       bookListDAO.updateBookListStatus(bookListStatus, courseId, year, networkID, bookListType);
	}
	public void updateBookListStatus(String confirm,String course, String acadyear, String networkID, String typeOfbooklst)throws Exception{
            bookListDAO.updateBookListStatus(confirm, course, acadyear, networkID, typeOfbooklst);
   }
	public void  adminUpdateList(BooklistAdminForm booklistAdminForm,ActionMessages   messages)throws Exception{
		           bookListDAO.updateBookListStatus("6", booklistAdminForm.getCourseId(), booklistAdminForm.getYear(),
	               booklistAdminForm.getNetworkId(),booklistAdminForm.getTypeOfBookList());
                   auditTrail.updateAuditTrail(booklistAdminForm, "Admin expedited booklist");
                   InfoMessagesUtil.addMessages(messages,"Book List have been successfully verified");
	}
   
}
