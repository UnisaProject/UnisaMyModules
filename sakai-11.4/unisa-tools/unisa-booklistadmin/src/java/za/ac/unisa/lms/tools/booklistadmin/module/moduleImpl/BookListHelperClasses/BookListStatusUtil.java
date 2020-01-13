package za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookListHelperClasses;

import za.ac.unisa.lms.tools.booklistadmin.dao.BookListDAO;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;

public class BookListStatusUtil {

	
	BookListDAO bookListDAO;
	public BookListStatusUtil(BookListDAO bookListDAO) {
		          this.bookListDAO=bookListDAO;            
	}

	public String getStatus(String subject,String acadyear, String typeofBooklist) throws Exception {
               return bookListDAO.getStatus(subject, acadyear, typeofBooklist);
    }
    public void updateBookListStatus(String confirm,String course, String acadyear, String networkID, String typeOfbooklst)throws Exception{
    	              bookListDAO.updateBookListStatus(confirm, course, acadyear, networkID, typeOfbooklst);
    }
     public void updateCourseBookLink(int status, String course,int  academicYear,String bookListType){
    	               bookListDAO.updateCourseBookLink(status, course, academicYear, bookListType);
    }
     public void updateBookListStatus(BooklistAdminForm booklistAdminForm)throws Exception{
         String courseId=booklistAdminForm.getCourseId().trim();
         String year=booklistAdminForm.getYear().trim();
         String networkId=booklistAdminForm.getNetworkId().trim();
         String bookListType=booklistAdminForm.getTypeOfBookList().trim();
         bookListDAO.updateBookListStatus("3",courseId , year ,networkId,bookListType);
         booklistAdminForm.setStatus("No Books prescribed for this course");
  }
   
}
