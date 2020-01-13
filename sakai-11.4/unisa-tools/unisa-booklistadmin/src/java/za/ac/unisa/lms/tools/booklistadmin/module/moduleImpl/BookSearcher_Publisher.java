package za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl;
import java.util.Iterator;
import java.util.Vector;
import java.util.List;
import org.apache.struts.action.ActionMessages;
import za.ac.unisa.lms.tools.booklistadmin.module.BookModule;
import za.ac.unisa.lms.tools.booklistadmin.module.BookList;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses.BookGetterUtil;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses.BookSearchUtils;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses.BookValidator;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;

public class BookSearcher_Publisher {

	BookSearchUtils bookSearchUtils;
	public BookSearcher_Publisher(BookGetterUtil bookDataGetter,BookList  bookList) {
		                       bookSearchUtils=new BookSearchUtils(bookDataGetter,bookList);
	}
	public  String  searchBookByPublisher(BooklistAdminForm booklistAdminForm,ActionMessages messages) {
		                   BookValidator bookValidator=new BookValidator();
		                   bookValidator.validateInputForSearchByPubisher(booklistAdminForm, messages);
		                   String nextPage="searchform";
		                   if(messages.isEmpty()){
		                	       try{
		                        	    setBookListInFormBean(booklistAdminForm);
		                        	    BookSearchUtils.handleEmptyList(booklistAdminForm, messages);
		                        	    nextPage=getNextPage(booklistAdminForm.getBooklist());
		                        }catch (Exception e) {
		                   			           bookSearchUtils.handleException(messages,e);
		                   			           nextPage= "searchform";
	                               }
		                  }
		                  return  nextPage;
	}
	private void setBookListInFormBean(BooklistAdminForm booklistAdminForm)throws Exception{
	                   Vector<BookModule> tempList = new Vector<BookModule>();
	                   List list =bookSearchUtils.getList(booklistAdminForm);
                       Iterator searchedBooks =list.iterator();
  	                   while (searchedBooks.hasNext()){
            	                 BookModule bookDetails = (BookModule) searchedBooks.next();
                                 tempList.addElement(bookDetails);
                       }
                       booklistAdminForm.setBooklist(tempList);
    }
	public String getNextPage(List list){
		                 String nextPage="bookfind";
                         if ((list==null)||(list.size()==0)){
                        	     nextPage="searchform";
	                     }
                         return nextPage;
	}
}