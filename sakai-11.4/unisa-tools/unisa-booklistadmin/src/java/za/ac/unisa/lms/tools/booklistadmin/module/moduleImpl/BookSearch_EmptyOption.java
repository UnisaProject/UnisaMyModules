package za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.apache.struts.action.ActionMessages;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import za.ac.unisa.lms.tools.booklistadmin.module.BookModule;
import za.ac.unisa.lms.tools.booklistadmin.module.BookList;
import za.ac.unisa.lms.tools.booklistadmin.module.Book;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses.BookGetterUtil;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses.BookSearchUtils;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses.BookValidator;

public class BookSearch_EmptyOption {

	
	BookList bookList;
	BookGetterUtil bookDataGetter;
	BookSearchUtils bookSearchUtils;
	public BookSearch_EmptyOption(BookGetterUtil bookDataGetter,BookList bookList) {
		        this.bookList=bookList;
		        bookSearchUtils=new BookSearchUtils(bookDataGetter,bookList);
    }
	public  String  searchBookNoOtpion(BooklistAdminForm booklistAdminForm,ActionMessages messages) {
		                BookValidator bookValidator=new BookValidator();
                        bookValidator.validateInputForSearchNoOption(booklistAdminForm, messages);
                        String nextPage="searchform";
                         if(messages.isEmpty()){
     	                        try {
     	                        	   nextPage="bookfind";
     	                        	   List nonLinkedBooks=getNonLinkedBooks(booklistAdminForm);
	                                   booklistAdminForm.setBooklist(nonLinkedBooks);
	                                   BookSearchUtils.handleEmptyList(booklistAdminForm, messages);
     	                        } catch (Exception e) {
     	                        	          bookSearchUtils.handleException(messages, e);
     	                        }
                         }
                         return nextPage;
	}
    private boolean isBookLinked(boolean linked,List linkedBooks,BookModule bookModule){
    	                 Iterator linkedBooksIterator= linkedBooks.iterator();
	                     while(linkedBooksIterator.hasNext() && (!linked)){
                                 BookModule bDetails = (BookModule) linkedBooksIterator.next();
		                         if (bDetails.getBookId().equalsIgnoreCase(bookModule.getBookId())){
			                               linked = true;	
			                     } 
                          }
	                     return linked;
    }
    private List getNonLinkedBooks(BooklistAdminForm booklistAdminForm) throws Exception{
    	                 Vector<BookModule> tempList = new Vector<BookModule>();
                         Iterator searchedBooks =bookSearchUtils.getList(booklistAdminForm).iterator();
                         List linkedBooks= bookList.getBookList(booklistAdminForm);
                         boolean linked = false;
                         while (searchedBooks.hasNext()){
     	                         linked = false;
                                 BookModule bookModule = (BookModule) searchedBooks.next();
                                 linked=isBookLinked(linked,linkedBooks,bookModule);
                                 if (!linked){					
                                       tempList.addElement(bookModule);
                                }
                        }
                        return tempList;
    }
      
}
