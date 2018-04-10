package za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses;

import java.util.Iterator;

import org.apache.struts.action.ActionMessages;
import za.ac.unisa.lms.tools.booklistadmin.module.BookModule;
import za.ac.unisa.lms.tools.booklistadmin.module.Course;
import za.ac.unisa.lms.tools.booklistadmin.module.AuditTrail;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.InfoMessagesUtil;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;

public class BookCopyUtil {

	
	Course course;
	AuditTrail auditTrail;
	public BookCopyUtil(Course course,AuditTrail auditTrail) {
		  this.course=course;
		  this.auditTrail=auditTrail;
	}
	
	public String copyExistingBook(BooklistAdminForm booklistAdminForm)throws Exception {
		      String nextPage="bookfind";
		      
    		Iterator i = booklistAdminForm.getBooklist().iterator();
			while(i.hasNext()){
				 BookModule bookModule = (BookModule) i.next();
				 if(bookModule.isRemove()){
					 bookModule.setTypeOfBookList(booklistAdminForm.getTypeOfBookList());
					 booklistAdminForm.setStatus("Booklist open for editing by administrator");	
					 course.linkBookToCourse(bookModule,booklistAdminForm.getCourseId(),booklistAdminForm.getYear());
					 auditTrail.updateAuditTrail(booklistAdminForm, "Admin Linked booknr " +booklistAdminForm.getCopyBook());
					  nextPage="editmodule";
				 }
			}
		      
		      
		     /* if((booklistAdminForm.getCopyBook()==null)||(!booklistAdminForm.getCopyBook().equalsIgnoreCase(""))){
		      }else{
		                            BookModule bookModule = new BookModule();
			                		bookModule.setTypeOfBookList(booklistAdminForm.getTypeOfBookList());
			                        bookModule.setBookId(booklistAdminForm.getCopyBook());
			                        booklistAdminForm.setStatus("Booklist open for editing by administrator");	
			                        course.linkBookToCourse(bookModule,booklistAdminForm.getCourseId(),booklistAdminForm.getYear());
			                        auditTrail.updateAuditTrail(booklistAdminForm, "Admin Linked booknr " +booklistAdminForm.getCopyBook());
			                        nextPage="editmodule";
		     }*/
		       return nextPage; 
	}
	public String copyBook(BooklistAdminForm booklistAdminForm,ActionMessages messages)throws Exception {
		
		                   String nextPage="";
		                   if (booklistAdminForm.getCopyExistingBookOption().equalsIgnoreCase("COPYEXISTINGBOOKS")){
		                	   nextPage=handleExistingBook(booklistAdminForm, messages);
		                   }
		                   boolean copyStatus = false;
		                   copyStatus=linkBook(booklistAdminForm,copyStatus); 
		                   nextPage=handleFalseCopyStatus(copyStatus, messages);
	                       addErrorMessagesForBean(messages,"You have successfully copied the book(s)");
	                       return nextPage;
	}
	private String handleFalseCopyStatus(boolean copyStatus,ActionMessages messages)throws Exception {
    	                String nextPage="editmodule";
	                    if (!copyStatus){
	                      	      addErrorMessagesForBean(messages,"Please tick check box and click copy.");
	                      	      nextPage="bookReuse";
	                    }
	                    return nextPage;
	}
	private boolean validateBookSelection(String copyBookIndicator){
		                  boolean booToCopySelectected=true;
	                      if (copyBookIndicator == null){
      	                       booToCopySelectected=false;
	                      }
	                      return booToCopySelectected;
    }
	private void addErrorMessagesForBean(ActionMessages messages,String  errorMsg)throws Exception {
		           InfoMessagesUtil.addMessages(messages, errorMsg);
	}
	private String handleExistingBook(BooklistAdminForm booklistAdminForm,ActionMessages messages)throws Exception {
	                   	          String nextPage="";
	                              boolean isbookSelected=validateBookSelection(booklistAdminForm.getCopyBook());
	                              if(isbookSelected){  
		                                 nextPage =copyExistingBook(booklistAdminForm);
	                              } else {
      	                                     addErrorMessagesForBean(messages,"Select the book or click cancel button");
   	                                         nextPage="bookfind";
	                              }
	                       return nextPage;
   }
	private boolean linkBook(BooklistAdminForm booklistAdminForm,boolean copyStatus)throws Exception {
		
		
		 BookModule bookModule = new BookModule();
		bookModule.setTypeOfBookList(booklistAdminForm.getTypeOfBookList());
		bookModule.setBookId(booklistAdminForm.getCopyBook());
		booklistAdminForm.setStatus("Booklist open for editing by administrator");
		  copyStatus =  true;
		 course.linkBookToCourse(bookModule,booklistAdminForm.getCourseId(),booklistAdminForm.getYear());
		 auditTrail.updateAuditTrail(booklistAdminForm, "Admin Linked booknr "+bookModule.getBookId());
		 return copyStatus;
	}
	
}


