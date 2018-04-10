package za.ac.unisa.lms.tools.booklistadmin.module;
import java.util.List;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses.BookGetterUtil;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses.BookImpl;
import za.ac.unisa.lms.tools.booklistadmin.module.view.BookView;
import za.ac.unisa.lms.tools.booklistadmin.module.Course;
import za.ac.unisa.lms.tools.booklistadmin.module.BookList;
import za.ac.unisa.lms.tools.booklistadmin.dao.BookDAO;
import org.apache.struts.action.ActionMessages;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;

public class Book extends BookImpl{
	BookView bookView;
	PrescribedBook prescribedBook;
	EreserveBook eReserveBook;
	RecommendedBook recommendedBook;
	BookDAO bookDAO;
	AuditTrail auditTrail;
	BookModule bookModule;
	BookGetterUtil bookGetterUtil;
	College college;
	public  Book(){
		        super(new BookDAO(),new AuditTrail(),new Course(),
		        		new BookList(),new College());
		         bookDAO=new BookDAO();
		         bookView=new  BookView(bookDAO);
		         auditTrail=new AuditTrail();
		         BookImpl bookImpl=new BookImpl(new BookDAO(),new AuditTrail(),new Course(),
		          new BookList(),new College());
		         eReserveBook=new EreserveBook(bookImpl);
                 prescribedBook=new PrescribedBook(bookImpl);
                 recommendedBook=new RecommendedBook(bookImpl);
	}
      public String confirmBookDeletion(BooklistAdminForm booklistAdminForm,ActionMessages messages){
		         return bookView.confirmBookDeletion(booklistAdminForm, messages);
      }
	  public String setDataForAddBookScreen(BooklistAdminForm booklistAdminForm){
		        return bookView.setDataForAddBookScreen(booklistAdminForm);
      }
	  public  String createSearchBookScreen(BooklistAdminForm booklistAdminForm ){
                   return bookView.createSearchBookScreen(booklistAdminForm);
     }
     public BookModule getBookData(String bookId){
        return bookDAO.getBook(bookId);
     }
     public void saveBook(BooklistAdminForm booklistAdminForm,String bookType) throws Exception{
		           if(booklistAdminForm.getTypeOfBookList().equals("P")){
		        	       prescribedBook.savePrescribedBook(booklistAdminForm);
                   }else if(booklistAdminForm.getTypeOfBookList().equals("R")){
                	   recommendedBook.saveRecommendedBook(booklistAdminForm);
                   }else if(booklistAdminForm.getTypeOfBookList().equals("E")){
                	           eReserveBook.saveEreserveBook(booklistAdminForm);
		           }			
	}
     public String updateBook(BooklistAdminForm booklistAdminForm) throws Exception{
                   if(booklistAdminForm.getTypeOfBookList().equals("P")){
                	   return prescribedBook.updatePrescribedBook(booklistAdminForm);
                   }else if(booklistAdminForm.getTypeOfBookList().equals("R")){
                	   return recommendedBook.updateRecommendedBook(booklistAdminForm);
                   }else if(booklistAdminForm.getTypeOfBookList().equals("E")){
                	   return eReserveBook.updateEreserveBook(booklistAdminForm);
                   }
                   return "addNewBook";
   }
   public String validateBookData(BooklistAdminForm booklistAdminForm,ActionMessages messages) throws Exception{
                     if(booklistAdminForm.getTypeOfBookList().equals("P")){
                    	     return   prescribedBook.validatePrescribedBookData(messages,booklistAdminForm);
                     }else if(booklistAdminForm.getTypeOfBookList().equals("R")){
                    	       return   recommendedBook.validateRecommendedBookData(messages, booklistAdminForm);
                     }else if(booklistAdminForm.getTypeOfBookList().equals("E")){
                    	     return    eReserveBook.validateEreserveBookData(messages, booklistAdminForm);
                     }else{
                    	 return "addNewBook";
                     }
   }
  }
