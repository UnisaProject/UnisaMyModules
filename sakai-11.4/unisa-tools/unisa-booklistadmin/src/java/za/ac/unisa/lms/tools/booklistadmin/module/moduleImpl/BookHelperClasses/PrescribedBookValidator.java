package za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses;
import org.apache.struts.action.ActionMessages;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.InfoMessagesUtil;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.Utilities;

public class PrescribedBookValidator {
	
	public String validateData(ActionMessages messages,BooklistAdminForm booklistAdminForm){
		            String  errorMsg="";
		         if((booklistAdminForm.getTxtTitle().trim()).length() == 0){
		                 errorMsg="Title cannot be empty, please enter the Title.";
		                 InfoMessagesUtil.addMessages(messages, errorMsg);
		                 return "";
		
	} else if (booklistAdminForm.getTxtAuthor().trim().length() == 0){
		           errorMsg="Author cannot be empty, please enter the Author.";
                  InfoMessagesUtil.addMessages(messages, errorMsg);
                  return "";
	} else if (booklistAdminForm.getTxtEdition().trim().length() == 0){
		errorMsg= "Edition cannot be empty, please enter the Edition";
		InfoMessagesUtil.addMessages(messages, errorMsg);
		return "";				
	} else if (booklistAdminForm.getTxtYear().length() == 0){
		errorMsg= "Year cannot be empty, please enter the Year";
		InfoMessagesUtil.addMessages(messages, errorMsg);
		return "";
	} else if (!Utilities.isInteger(booklistAdminForm.getTxtYear())){
		errorMsg= "Year must be a number.";
		InfoMessagesUtil.addMessages(messages, errorMsg);
		return "";
	} else if (booklistAdminForm.getTxtYear().length() < 4){
		errorMsg= "Year cannot be less than four characters, please enter the Year";
		InfoMessagesUtil.addMessages(messages, errorMsg);
		return "";
	}
	 else if (booklistAdminForm.getTxtBookNote().length()>300){
		 errorMsg= "Book note should not exceed 300 characters, including spaces. Please shorten your text.";
			InfoMessagesUtil.addMessages(messages, errorMsg);
			return "";	
	 } else if (booklistAdminForm.getTxtPublisher().trim().length() == 0){
		 errorMsg="Publisher cannot be empty, please enter the Publisher";
			InfoMessagesUtil.addMessages(messages, errorMsg);
			return "";	
	} else if (booklistAdminForm.getTxtISBN().length() == 0){
		errorMsg="ISBN cannot be empty, please enter an ISBN number or fill field with 13 zeros.";
		InfoMessagesUtil.addMessages(messages, errorMsg);
		return "";
   } else if (booklistAdminForm.getTxtISBN().length() < 10){
	   errorMsg="ISBN cannot be less 10 characters, please enter valid ISBN number or fill field with 13 zeros.";
		InfoMessagesUtil.addMessages(messages, errorMsg);
		return "";
   }else if (booklistAdminForm.getPublishStatus().intValue() == 0){
	   errorMsg= "The Book is Published option is set to No.  Only published books may be prescribed. Refer to Section 4.7 of the relevant Policy.";
		InfoMessagesUtil.addMessages(messages, errorMsg);
		return "";			
	 }else {
		 return "";
	 }
  }
}
