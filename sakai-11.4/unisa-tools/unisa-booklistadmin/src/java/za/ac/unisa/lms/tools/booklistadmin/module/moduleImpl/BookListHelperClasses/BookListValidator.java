package za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookListHelperClasses;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.InfoMessagesUtil;

import org.apache.struts.action.ActionMessages;
import java.util.List;

public class BookListValidator {

	BookListGetterUtil bookListDataGetter;
	public BookListValidator(BookListGetterUtil bookListDataGetter) {
		       this.bookListDataGetter=bookListDataGetter;
	}
	
	public boolean isBookListEmpty(BooklistAdminForm booklistAdminForm,ActionMessages messages) throws Exception{
	 	                     boolean empty=false;
		                     List list= getBookList(booklistAdminForm);
		                     if(booklistAdminForm.getTypeOfBookList().equals("P")){
		                    	        checkPBookList(list.size(),messages);
		                     }else if(booklistAdminForm.getTypeOfBookList().equals("R")){
		                    	         checkRBookList(list.size(),messages);
		                     }else{
		                    	         checkEBookList(list.size(),messages);
		                     }
		                     if(list.size()==0){
            	        	         empty=true;
            	             }
		                     return empty;
	 }
	  private List getBookList(BooklistAdminForm booklistAdminForm ) throws Exception{
					        return  bookListDataGetter.getBookList(booklistAdminForm);
	  }
	  private void checkEBookList(int listSize,ActionMessages messages){  
		                addMsgsForBean(listSize," eReserve  Books ","E" ,messages);
	  } 
	  private void checkRBookList(int listSize,ActionMessages messages){  
		                addMsgsForBean(listSize," Recommended  Books ","E" ,messages);
      }
	  private void checkPBookList(int listSize,ActionMessages messages){  
		                 addMsgsForBean(listSize,"Prescribed Books","P" ,messages);
	  }
      private void  addMsgsForBean(int listSize,String bookType,String bookListType ,ActionMessages messages){  
    	                String errorMsg="";
                        if(listSize== 0){
        	                    errorMsg=getMsgWhenListEmpty(bookType);
		                }else {
		                	errorMsg=getMsgWhenListNotEmpty(bookListType);        
					     }
		                InfoMessagesUtil.addMessages(messages,errorMsg);
      }
      private String getMsgWhenListEmpty(String bookType){
    	                   return  "Are you sure there are no "+bookType+" for this course?";
      }
      private String getMsgWhenListNotEmpty(String bookListType){
    	                 String errorMsg= "Book items have been added for this course. Please remove all book items "+
        		                          "from this view before declaring no prescribed books";
    	                 if(bookListType.equalsIgnoreCase("E")){
    	                	         errorMsg= "Items have been added for this course. Please remove all book items "+
   		                                       "from this view before declaring no prescribed books";
    	                 }
    	                 return errorMsg;  
      }
	  
}
