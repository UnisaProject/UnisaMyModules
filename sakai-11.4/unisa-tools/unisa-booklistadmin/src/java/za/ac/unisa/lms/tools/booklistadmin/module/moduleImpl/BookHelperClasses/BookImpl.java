package za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses;
import org.apache.struts.action.ActionMessages;
import org.sakaiproject.user.api.UserDirectoryService;
import za.ac.unisa.lms.tools.booklistadmin.dao.BookDAO;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import za.ac.unisa.lms.tools.booklistadmin.module.AuditTrail;
import za.ac.unisa.lms.tools.booklistadmin.module.BookList;
import za.ac.unisa.lms.tools.booklistadmin.module.BookModule;
import za.ac.unisa.lms.tools.booklistadmin.module.College;
import za.ac.unisa.lms.tools.booklistadmin.module.Course;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookListHelperClasses.BookListGetterUtil;
public class BookImpl extends BookGetterUtil{
	
	BookDAO bookDAO;
	AuditTrail auditTrail;
	BookModule bookModule;
	BookSetterUtil bookDataSetter;
	BookListGetterUtil bookListDataGetter;
	BookGetterUtil bookDataGetter;
	BookFinderUpUtil bookSearcher;
	College college;
	BookCopyUtil bookCopier;
	BookDeleteUtil bookDeleteUtil;
	public  BookImpl(BookDAO bookDAO,AuditTrail auditTrail,Course course,BookList bookList,College college){
		                  super(new BookDAO());
		                  bookDataSetter=new  BookSetterUtil();
		                  this.bookDAO=bookDAO;
		                  bookDataGetter=new BookGetterUtil(bookDAO);
		                  this.auditTrail=auditTrail;
		                  this.college=college;
		                  bookSearcher=new BookFinderUpUtil(bookDataGetter,bookList,course,college);
		                  bookCopier=new BookCopyUtil(course, auditTrail);
		                  bookDeleteUtil=new BookDeleteUtil(bookDAO,auditTrail);
	}
	public String  saveBookUpdateData(BooklistAdminForm booklistAdminForm,BookModule bookModule,String bookType) throws Exception{
                     int booknr = bookDAO.updateBook(bookModule);
	                 String auditTrailMsgForAdminUpdate="Admin Updated "+bookType+" booknr ";
	                 String auditTrailMsgForNonAdminUpdate="Updated "+bookType+" booknr " +bookModule.getBookId()+" Add booknr "+booknr;
	          return auditTrail.updateAuditTrail(booklistAdminForm,auditTrailMsgForAdminUpdate,auditTrailMsgForNonAdminUpdate);
   }
	public void  saveBook(BooklistAdminForm booklistAdminForm,BookModule bookModule) throws Exception{
		               String bookType=booklistAdminForm.getTypeOfBookList().trim();
		               if(bookType.equalsIgnoreCase("E")){
		            	   bookType= bookModule.geteReserveType();
		               }
                      bookModule.setBookId(bookDAO.insertBook(bookModule,bookType).toString());
                      String auditTrailMsgForAdminUpdate= "Added booknr " +bookModule.getBookId();
                      auditTrail.updateAuditTrail(booklistAdminForm,auditTrailMsgForAdminUpdate) ;
    }
	public  String  searchBookByPublisher(BooklistAdminForm booklistAdminForm,ActionMessages messages) {
                          return bookSearcher.searchBookByPublisher(booklistAdminForm, messages);
    }
    public  String  searchBookByCourseCode(UserDirectoryService uDirService,BooklistAdminForm booklistAdminForm,
                           ActionMessages messages) throws Exception{
                 return bookSearcher.searchBookByCourseCode(uDirService, booklistAdminForm, messages);
    }
    public  String  searchBookNoOtpion(BooklistAdminForm booklistAdminForm,ActionMessages messages) {
                          return bookSearcher.searchBookNoOtpion(booklistAdminForm, messages);
    }
     public String copyBook(BooklistAdminForm booklistAdminForm,ActionMessages messages)throws Exception {
    	           return bookCopier.copyBook(booklistAdminForm, messages);
    }
    public String copyExistingBook(BooklistAdminForm booklistAdminForm)throws Exception {
    	               return bookCopier.copyExistingBook(booklistAdminForm);
   }
    public void setBookData(BooklistAdminForm booklistAdminForm,BookModule bookModule){
    	             bookDataSetter.setBookData(booklistAdminForm, bookModule);
	}
	public void setPrescribedBookData(BooklistAdminForm booklistAdminForm,BookModule bookModule){
		            bookDataSetter.setPrescribedBookData(booklistAdminForm, bookModule);
	}
	public void setRecommendedBookData(BooklistAdminForm booklistAdminForm,BookModule bookModule){
		            bookDataSetter.setRecommendedBookData(booklistAdminForm, bookModule);
    }
	
	public void setEreserveBookData(BooklistAdminForm booklistAdminForm,BookModule bookModule){
	             	bookDataSetter.setEreserveBookData(booklistAdminForm, bookModule) ; 
    }
	public void setEreserveBookData(BooklistAdminForm booklistAdminForm,BookModule bookModule,String bookType){
		            bookDataSetter.setEreserveBookData(booklistAdminForm, bookModule, bookType);
   }
	public void deleteBook(BooklistAdminForm booklistAdminForm,ActionMessages messages)throws Exception{
		              bookDeleteUtil.deleteBook(booklistAdminForm, messages);
	}
}
