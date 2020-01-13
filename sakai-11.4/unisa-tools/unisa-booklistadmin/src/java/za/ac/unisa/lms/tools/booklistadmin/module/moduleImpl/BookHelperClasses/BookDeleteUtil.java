package za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses;

import java.util.Iterator;
import org.apache.struts.action.ActionMessages;
import za.ac.unisa.lms.tools.booklistadmin.module.BookModule;
import za.ac.unisa.lms.tools.booklistadmin.module.AuditTrail;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.InfoMessagesUtil;
import za.ac.unisa.lms.tools.booklistadmin.dao.BookDAO;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;

public class BookDeleteUtil {

	BookDAO bookDAO;
	AuditTrail auditTrail;
	public BookDeleteUtil(BookDAO bookDAO,AuditTrail auditTrail) {
		this.bookDAO=bookDAO;
		this.auditTrail=auditTrail;
	}
	
	public void deleteBook(BooklistAdminForm booklistAdminForm,ActionMessages messages)throws Exception{
		              Iterator i = booklistAdminForm.getBooklist().iterator();
	                  while (i.hasNext()){
		                      BookModule bookModule = (BookModule) i.next();
		                      bookModule.setTypeOfBookList(booklistAdminForm.getTypeOfBookList());
		                      if (bookModule.isRemove()){	
		        	                bookDAO.deleteBook(bookModule, booklistAdminForm.getCourseId(),booklistAdminForm.getYear());				
			                        auditTrail.updateAuditTrail(booklistAdminForm, "Admin deleted booknr " +bookModule.getBookId());
		                      }
	                   }
	                   booklistAdminForm.setStatus("Booklist open for editing by administrator");
	                    InfoMessagesUtil.addMessages(messages, "You have successfully deleted the book(s)");
	}

}
