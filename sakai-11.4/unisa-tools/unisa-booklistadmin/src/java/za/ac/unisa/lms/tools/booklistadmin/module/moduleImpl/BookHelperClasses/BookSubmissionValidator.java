package za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses;

import org.apache.struts.action.ActionMessages;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.InfoMessagesUtil;
public class BookSubmissionValidator {
	
	public void confirmSubmissioinDateRemoval(ActionMessages messages,BooklistAdminForm  booklistAdminForm){
		          String confirmationMessages="Are you sure you want to delete the  following academic year(s)";
		          addMessage(messages, confirmationMessages);
		      	  
	}
	public void noSelection(ActionMessages messages){
       
                           String noSelectionMessage="No submission year have been marked for removal. Please click the"+
                                    "check box next to the year you want to remove";
                                    addMessage(messages, noSelectionMessage);
    }
	private void addMessage(ActionMessages messages,String message){
		                    InfoMessagesUtil.addMessages(messages, message);
		
	}
}
