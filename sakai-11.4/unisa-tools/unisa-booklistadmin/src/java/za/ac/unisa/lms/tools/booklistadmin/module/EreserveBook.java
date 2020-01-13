package za.ac.unisa.lms.tools.booklistadmin.module;

import org.apache.struts.action.ActionMessages;

import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses.BookImpl;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses.EreserveBookValidator;

public class EreserveBook {
  
	BookImpl bookImpl;
	public EreserveBook(BookImpl bookImpl) {
		           this.bookImpl=bookImpl;
	}
	public String updateEreserveBook(BooklistAdminForm booklistAdminForm)  throws Exception{
        BookModule bookModule=new BookModule();
        bookImpl.setEreserveBookData(booklistAdminForm,bookModule);
        return saveUpdateData(booklistAdminForm,bookModule);
   }
   private String  saveUpdateData(BooklistAdminForm booklistAdminForm,BookModule bookModule) throws Exception{
            return bookImpl.saveBookUpdateData(booklistAdminForm, bookModule, "E");
   }
   public void saveEreserveBook(BooklistAdminForm booklistAdminForm)  throws Exception{
        BookModule bookModule=new BookModule();
        bookImpl.setEreserveBookData(booklistAdminForm,bookModule);
        bookImpl.saveBook(booklistAdminForm,bookModule);
   }
   public String validateEreserveBookData(ActionMessages messages, BooklistAdminForm booklistAdminForm){
                  EreserveBookValidator ereserveBookValidator=new EreserveBookValidator();
                     return ereserveBookValidator.validateData(messages, booklistAdminForm);
   }
}
