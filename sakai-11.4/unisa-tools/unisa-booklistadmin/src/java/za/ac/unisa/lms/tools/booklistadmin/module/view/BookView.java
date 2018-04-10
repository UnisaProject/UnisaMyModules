package za.ac.unisa.lms.tools.booklistadmin.module.view;
import java.util.Iterator;
import org.apache.struts.action.ActionMessages;

import za.ac.unisa.lms.tools.booklistadmin.dao.BookDAO;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.InfoMessagesUtil;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses.BookGetterUtil;
import za.ac.unisa.lms.tools.booklistadmin.module.BookModule;

public class BookView {
	
	BookGetterUtil bookDataGetter;
	BookDAO bookDAO;
	public  BookView(BookDAO bookDAO){
		       bookDataGetter=new BookGetterUtil(bookDAO);
		       addBookScreen=new AddBookScreen(bookDataGetter);
	}
	AddBookScreen addBookScreen;
	public String confirmBookDeletion(BooklistAdminForm booklistAdminForm,ActionMessages messages){
	                  boolean deleteStatus = false;
	                  String errorMsg="";
	                  Iterator i = booklistAdminForm.getBooklist().iterator();
	                  while (i.hasNext()){
		                     BookModule bookDetails = (BookModule)i.next();
		                     if (bookDetails.isRemove()){
			                       deleteStatus = true;		
			                       break;
		                     }
	                  }	
	                  if (deleteStatus){
			    	            errorMsg= "Are you sure you want to delete the following Book List information?";
			    	            InfoMessagesUtil.addMessages(messages,errorMsg);
                                return "bookremove";
		               } else {
		         		         errorMsg= "Please tick from check box and click remove.";
		         		        InfoMessagesUtil.addMessages(messages,errorMsg);
                                 return "editModule";
		               }
	}
	public String setDataForAddBookScreen(BooklistAdminForm booklistAdminForm){
        		       return addBookScreen.setDataForAddBookScreen(booklistAdminForm);
    }
	public  String createSearchBookScreen(BooklistAdminForm booklistAdminForm ){
		               SearchBookScreen searchBookScreen=new SearchBookScreen();
		               return searchBookScreen.createSearchBookScreen(booklistAdminForm);
    }
	
	 
}
