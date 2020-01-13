package za.ac.unisa.lms.tools.booklistadmin.module;

import org.apache.struts.action.ActionMessages;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses.BookImpl;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses.BookSetterUtil;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses.PrescribedBookValidator;
import za.ac.unisa.lms.tools.booklistadmin.dao.BookDAO;

public class PrescribedBook extends BookSetterUtil{
	
	
	BookDAO bookDAO;
	AuditTrail auditTrail;
	BookImpl bookImpl;
	public PrescribedBook(BookImpl bookImpl) {
		           this.bookImpl=bookImpl;
	}
	public String updatePrescribedBook(BooklistAdminForm booklistAdminForm)  throws Exception{
		              BookModule bookModule=new BookModule();
		              bookImpl.setPrescribedBookData(booklistAdminForm,bookModule);
		              return saveUpdateData(booklistAdminForm,bookModule);
	}
	private String  saveUpdateData(BooklistAdminForm booklistAdminForm,BookModule bookModule) throws Exception{
	   	               return bookImpl.saveBookUpdateData(booklistAdminForm, bookModule, "P");
    }
	public void savePrescribedBook(BooklistAdminForm booklistAdminForm)  throws Exception{
                      BookModule bookModule=new BookModule();
                      bookImpl.setPrescribedBookData(booklistAdminForm,bookModule);
                      bookImpl.saveBook(booklistAdminForm,bookModule);
    }
	public String validatePrescribedBookData(ActionMessages messages, BooklistAdminForm booklistAdminForm){
		          PrescribedBookValidator prescribedBookValidator=new PrescribedBookValidator();
		          return prescribedBookValidator.validateData(messages, booklistAdminForm);
	}
    

}
