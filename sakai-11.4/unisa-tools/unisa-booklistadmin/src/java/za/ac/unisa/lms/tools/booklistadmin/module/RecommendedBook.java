package za.ac.unisa.lms.tools.booklistadmin.module;

import org.apache.struts.action.ActionMessages;

import za.ac.unisa.lms.tools.booklistadmin.dao.BookDAO;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses.BookImpl;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses.BookSetterUtil;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses.RecommendedBookValidator;

public class RecommendedBook extends BookSetterUtil{
	
	
	BookDAO bookDAO;
	AuditTrail auditTrail;
	BookImpl bookImpl;
	public RecommendedBook(BookImpl bookImpl) {
		           this.bookImpl=bookImpl;
	}
	    public String updateRecommendedBook(BooklistAdminForm booklistAdminForm)  throws Exception{
		              BookModule bookModule =new BookModule();
		              bookImpl.setRecommendedBookData(booklistAdminForm,bookModule);
	                  return saveUpdateData(booklistAdminForm,bookDAO,bookModule,auditTrail);
	    }
		private String  saveUpdateData(BooklistAdminForm booklistAdminForm,BookDAO dao,BookModule bookModule,AuditTrail auditTrail) throws Exception{
			              return  bookImpl.saveBookUpdateData( booklistAdminForm,bookModule,"R") ;
        }
		public void saveRecommendedBook(BooklistAdminForm booklistAdminForm)  throws Exception{
                            BookModule bookModule=new BookModule();
                            bookImpl.setRecommendedBookData(booklistAdminForm, bookModule);
                            bookImpl.saveBook(booklistAdminForm,bookModule);
        }

		public String validateRecommendedBookData(ActionMessages messages,BooklistAdminForm booklistAdminForm){
			                RecommendedBookValidator bookValidator=new RecommendedBookValidator();
			                return  bookValidator.validateData(messages, booklistAdminForm);
	    }
		
        
}
