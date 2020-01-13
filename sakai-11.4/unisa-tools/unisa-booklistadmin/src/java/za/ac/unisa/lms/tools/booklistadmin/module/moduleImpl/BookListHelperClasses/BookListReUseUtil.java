package za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookListHelperClasses;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.InfoMessagesUtil;
import za.ac.unisa.lms.tools.booklistadmin.dao.BookListDAO;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.struts.action.ActionMessages;

public class BookListReUseUtil {

	BookListDAO bookListDAO;
	public BookListReUseUtil(BookListDAO bookListDAO) {
		       this.bookListDAO= bookListDAO;
	}
	public String reUseBookList(BooklistAdminForm booklistAdminForm,ActionMessages messages)throws Exception{
                          String nextPage="";
		                  GregorianCalendar calCurrent = new GregorianCalendar();
		        	      String  year=booklistAdminForm.getYear();
	        	          String bookListType=booklistAdminForm.getTypeOfBookList();
	        	          String courseId=booklistAdminForm.getCourseId();
	        	          List  list =bookListDAO.getBookCopyList(courseId,year,bookListType);
	        	         
		        	      if (list.size() == 0){
		        	    	      //year=""+(calCurrent.get(Calendar.YEAR)+1);
		        	    	  
		        	    	  year=Integer.toString(Integer.parseInt(year)+1);
		        	    	  //year=Integer.toString(booklistAdminForm.getYear());
		        	    	      list=bookListDAO.getBookCopyList(courseId,year,bookListType);
		        	      }
		        	      booklistAdminForm.setBooklist(list);
		        	      setFormBeanMsg(messages,list.size());
		        	      setBookListStatus(booklistAdminForm,list.size());
		        	      nextPage=getNextPage(list.size());
		        	      return nextPage;
	}
		
			
			private void setFormBeanMsg(ActionMessages messages,int listSize ){
				              if (listSize == 0){
				            	     String errorMsg="You cannot reuse, no books to copy from previous year.";
				            	     InfoMessagesUtil.addMessages(messages, errorMsg);
				              }
				             
			}
			private String getNextPage(int listSize ){
				              String nextPage="bookreuse";
	                          if (listSize == 0){
	            	                  nextPage="displaybooklist";
	                          }
	                          return nextPage;
			}
			private void setBookListStatus(BooklistAdminForm booklistAdminForm,int listSize ){
				                if (listSize == 0){
				                       String status="Booklist open for editing by administrator";
				                       booklistAdminForm.setStatus(status);
				               }
			}

}
