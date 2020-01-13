package za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses;
import org.apache.struts.action.ActionMessages;
import org.sakaiproject.user.api.UserDirectoryService;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import za.ac.unisa.lms.tools.booklistadmin.module.Book;
import za.ac.unisa.lms.tools.booklistadmin.module.BookList;
import za.ac.unisa.lms.tools.booklistadmin.module.College;
import za.ac.unisa.lms.tools.booklistadmin.module.Course;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookSearch_EmptyOption;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookSearcher_Publisher;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.CourseHelperClasses.CourseSearcher;

public class BookFinderUpUtil {
	
	Course course;
	BookList bookList;
	BookSearch_EmptyOption bookSearch_EmptyOption;
	CourseSearcher bookSearcher_CourseCode;
	BookSearcher_Publisher bookSearcher_Publisher;
	College college;
	BookGetterUtil bookDataGetter;
	public BookFinderUpUtil(BookGetterUtil bookDataGetter,BookList  bookList,Course course,College college){
		                  this.course=course;
		                  this.bookList= bookList;
		                  this.college=college;
		                  this.bookDataGetter=bookDataGetter;
		                  bookSearch_EmptyOption=new BookSearch_EmptyOption(bookDataGetter,bookList);
		                  bookSearcher_CourseCode=new CourseSearcher(bookDataGetter,course,bookList,college);
		                  bookSearcher_Publisher=new BookSearcher_Publisher(bookDataGetter,bookList);
		
	}
	public  String  searchBookByPublisher(BooklistAdminForm booklistAdminForm,ActionMessages messages) {
		               return bookSearcher_Publisher.searchBookByPublisher(booklistAdminForm, messages);
    }
    public  String  searchBookByCourseCode(UserDirectoryService uDirService,BooklistAdminForm booklistAdminForm,
			                ActionMessages messages) throws Exception{
		              return bookSearcher_CourseCode.searchBookByCourseCode(uDirService, booklistAdminForm, messages);
	}
	public  String  searchBookNoOtpion(BooklistAdminForm booklistAdminForm,ActionMessages messages) {
		                 return bookSearch_EmptyOption.searchBookNoOtpion(booklistAdminForm, messages);
    }
}
