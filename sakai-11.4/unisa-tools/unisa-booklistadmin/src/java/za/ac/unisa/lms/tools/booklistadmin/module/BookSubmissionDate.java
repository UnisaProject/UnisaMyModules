package za.ac.unisa.lms.tools.booklistadmin.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionMessages;

import za.ac.unisa.lms.tools.booklistadmin.dao.BookSubmissionDateDAO;
import za.ac.unisa.lms.tools.booklistadmin.dao.DateUtil;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.SubmissionDateImpl;
import za.ac.unisa.lms.tools.booklistadmin.module.view.BookSubmissionDateUI;
public class BookSubmissionDate {

	SubmissionDateImpl bookSubmissionDateImpl;
	BookSubmissionDateDAO dao;
	DateUtil dateutility;
	BookSubmissionDateUI bookSubmissionDateUI;
	public  BookSubmissionDate() {
		        dateutility=new DateUtil();
		        dao=new BookSubmissionDateDAO();
		        bookSubmissionDateImpl=new SubmissionDateImpl(dao,dateutility);
		        bookSubmissionDateUI=new BookSubmissionDateUI(dao,dateutility);
	}
	
	public void removeSubmissionDate(BooklistAdminForm booklistAdminForm,HttpServletRequest request){
    	              bookSubmissionDateImpl.removeSubmissionDate(booklistAdminForm, request);          
    }
    public void  createDateManagementScrn(BooklistAdminForm booklistAdminForm,HttpServletRequest request){
    	               bookSubmissionDateUI.createDateManagementScrn(booklistAdminForm, request);
    }
    public  String   confirmDateRemovaL(BooklistAdminForm booklistAdminForm,HttpServletRequest request,ActionMessages  messages){
    	                 return bookSubmissionDateImpl.confirmDateRemovaL(booklistAdminForm, request, messages);
    	
   }
    public void saveBookSubmissionDate(BooklistAdminForm booklistAdminForm,String  networkId,HttpSession session,int academicYear){
    	bookSubmissionDateImpl.saveBookSubmissionDate(booklistAdminForm, networkId, session, academicYear);
    }
}
