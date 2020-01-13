package za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses;

import java.util.List;
import org.apache.struts.action.ActionMessages;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import za.ac.unisa.lms.tools.booklistadmin.module.BookList;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.InfoMessagesUtil;

public class BookSearchUtils {

	BookGetterUtil bookDataGetter;
	BookList bookList;
	public BookSearchUtils(BookGetterUtil bookDataGetter,BookList bookList) {
		      this.bookDataGetter=bookDataGetter;
		      this.bookList=bookList;
	}
	public void handleException(ActionMessages messages,Exception e){
                     InfoMessagesUtil.addMessages(messages,"There seems to be an error with the database, please inform the myUnisa Team");
                     e.printStackTrace();
    }
	public List getList(BooklistAdminForm booklistAdminForm)throws Exception{
		             List list =new java.util.ArrayList();
		              String  academicYear=booklistAdminForm.getYear();
                     String courseId=booklistAdminForm.getCourseId();
                     String typeOfbooklist=booklistAdminForm.getTypeOfBookList();
                     if(booklistAdminForm.getSearchOption().equals("searchCourse")){
        	                      list=bookList.getBookList(courseId,academicYear,typeOfbooklist);
                      }else{
                    	          list=bookDataGetter.getSearchedBooks(booklistAdminForm);
                      }
               		  return list;
	 }
	public static void handleEmptyList(BooklistAdminForm booklistAdminForm,ActionMessages messages){
        if (booklistAdminForm.getBooklist().size()==0){
                    String errorMsg="No book entries found that match your search criteria";
	                 InfoMessagesUtil.addMessages(messages, errorMsg);
	    }
    }
}
