package za.ac.unisa.lms.tools.booklistadmin.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionMessages;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import za.ac.unisa.lms.tools.booklistadmin.dao.BookReleaseDateDAO;
import za.ac.unisa.lms.tools.booklistadmin.dao.DateUtil;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookReleaseDateImpl;
import za.ac.unisa.lms.tools.booklistadmin.module.view.BookReleaseDateUI;

public class BookReleaseDate {
			
	BookReleaseDateImpl bookReleaseDateImpl;
	BookReleaseDateDAO bookReleaseDateDAO;
	BookReleaseDateUI bookReleaseDateUI;
	DateUtil dateutility;
		   public  BookReleaseDate() {
			         dateutility=new DateUtil();
			         bookReleaseDateDAO=new BookReleaseDateDAO();
			         bookReleaseDateImpl=new BookReleaseDateImpl(bookReleaseDateDAO,dateutility);
			         bookReleaseDateUI=new  BookReleaseDateUI(bookReleaseDateDAO,dateutility);
			        
		   }
		public void removeReleaseDate(BooklistAdminForm booklistAdminForm,HttpServletRequest request){
			           bookReleaseDateImpl.removeReleaseDate(booklistAdminForm, request);
		}
		public void  createReleaseDateManagementScrn(BooklistAdminForm booklistAdminForm,HttpServletRequest request){
			             bookReleaseDateUI.createReleaseDateManagementScrn(booklistAdminForm, request);
	    }
		 public  String   confirmDateRemovaL(BooklistAdminForm booklistAdminForm,HttpServletRequest request,ActionMessages  messages){
			                 return  bookReleaseDateImpl.confirmDateRemovaL(booklistAdminForm, request, messages);
		 }

       public void saveBookReleaseDate(BooklistAdminForm booklistAdminForm,HttpSession session,int academicYear){
	            bookReleaseDateImpl.saveBookReleaseDate(booklistAdminForm, session, academicYear);
      }
	             
}