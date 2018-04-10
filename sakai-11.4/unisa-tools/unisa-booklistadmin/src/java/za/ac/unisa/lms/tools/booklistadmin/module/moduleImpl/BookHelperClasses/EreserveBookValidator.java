package za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses;

import org.apache.struts.action.ActionMessages;

import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.InfoMessagesUtil;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.Utilities;

public class EreserveBookValidator {
	
	public String validateData(ActionMessages messages,BooklistAdminForm booklistAdminForm){

		   String  errorMsg="";
		
		if(!booklistAdminForm.geteReserveType().equalsIgnoreCase("BookChapter") && booklistAdminForm.getTxtTitle().length() == 0)
		{
		    errorMsg="Title cannot be empty, please enter the Title.";
			InfoMessagesUtil.addMessages(messages, errorMsg);return errorMsg;
			
		}
		else if(!booklistAdminForm.geteReserveType().equalsIgnoreCase("LawReport") && !booklistAdminForm.geteReserveType().equalsIgnoreCase("BookChapter") && booklistAdminForm.getTxtAuthor().length() == 0)
		{
		       errorMsg="Author cannot be empty, please enter the Author.";
				InfoMessagesUtil.addMessages(messages, errorMsg);return errorMsg;
				

		}
		else if(booklistAdminForm.geteReserveType().equalsIgnoreCase("LawReport") && booklistAdminForm.getEbookVolume().length() == 0)
		{
			  errorMsg="Case number cannot be empty, please enter the case number.";
			InfoMessagesUtil.addMessages(messages, errorMsg);return errorMsg;
			
		} 
		else if(!booklistAdminForm.geteReserveType().equalsIgnoreCase("LawReport") && booklistAdminForm.getEbook_pages().length() == 0)
		{
			 errorMsg="Page numbers cannot be empty, please enter the page numbers.";
			InfoMessagesUtil.addMessages(messages, errorMsg);return errorMsg;
			
		} 
		else if(!booklistAdminForm.geteReserveType().equalsIgnoreCase("LawReport") && booklistAdminForm.getTxtYear().length() == 0 )
		{
		
				 errorMsg="Year cannot be empty, please enter the Year";
				InfoMessagesUtil.addMessages(messages, errorMsg);return errorMsg;
				
		
		} 
		else if(!booklistAdminForm.geteReserveType().equalsIgnoreCase("LawReport") && !Utilities.isInteger(booklistAdminForm.getTxtYear()))
		{
		
				 errorMsg="Year must be a number.";
				InfoMessagesUtil.addMessages(messages, errorMsg);return errorMsg;
				
		} 
		else if(!booklistAdminForm.geteReserveType().equalsIgnoreCase("LawReport") && booklistAdminForm.getTxtYear().length() < 4)
		{				
		
				 errorMsg="Year cannot be less than four characters, please enter the Year";
				InfoMessagesUtil.addMessages(messages, errorMsg);return errorMsg;
				
		 
		}
		else if(!booklistAdminForm.geteReserveType().equalsIgnoreCase("LawReport") && booklistAdminForm.getTxtPublisher().length() == 0)
		{				
	
			 errorMsg="Publication cannot be empty, please enter the Publication details";
			InfoMessagesUtil.addMessages(messages, errorMsg);return errorMsg;
			
		  
		}
		else if (booklistAdminForm.getNoteToLibrary().length() > 255)
		{
			 errorMsg="Note to the library can not exceed 255 characters. Please shorten your note";
			InfoMessagesUtil.addMessages(messages, errorMsg);return errorMsg;
			
		}
		
		
		/*
		       String  errorMsg="";
		       if(booklistAdminForm.geteReserveType()==null){
		    	   errorMsg="Please select the type of eReserve  item.";
	               InfoMessagesUtil.addMessages(messages, errorMsg);
		       }else    if((booklistAdminForm.getTxtTitle().trim(.length() == 0){
                errorMsg="Title cannot be empty, please enter the Title.";
               InfoMessagesUtil.addMessages(messages, errorMsg);
                return errorMsg;

	    }else if(!booklistAdminForm.geteReserveType().equalsIgnoreCase("LawReport") && booklistAdminForm.getTxtAuthor().length() == 0){
	 
	      	errorMsg=" Author cannot be empty, please enter the Author.";
			InfoMessagesUtil.addMessages(messages, errorMsg);
			return errorMsg;

	   }else if(booklistAdminForm.getEbook_pages().length() == 0)
	  {
		   errorMsg="Page numbers cannot be empty, please enter the page numbers.";
		   InfoMessagesUtil.addMessages(messages, errorMsg);
		  return errorMsg;
	   } else if(!booklistAdminForm.geteReserveType().equalsIgnoreCase("LawReport") && booklistAdminForm.getTxtYear().length() == 0 )
	   {
	
		errorMsg=" Year cannot be empty, please enter the Year";
			InfoMessagesUtil.addMessages(messages, errorMsg);
			return errorMsg;
	
	   } 
	else if(!booklistAdminForm.geteReserveType().equalsIgnoreCase("LawReport") && !Utilities.isInteger(booklistAdminForm.getTxtYear()
	{
	
		errorMsg="Year must be a number.";
			InfoMessagesUtil.addMessages(messages, errorMsg);
			return errorMsg;
	
	} 
	else if(!booklistAdminForm.geteReserveType().equalsIgnoreCase("LawReport") && booklistAdminForm.getTxtYear().length() < 4)
	{				
	
		errorMsg="Year cannot be less than four characters, please enter the Year";
			InfoMessagesUtil.addMessages(messages, errorMsg);
			return errorMsg;
	 
	}
	else if(!booklistAdminForm.geteReserveType().equalsIgnoreCase("LawReport") && booklistAdminForm.getTxtPublisher().length() == 0)
	{				

		errorMsg=" Publication cannot be empty, please enter the Publication details";
		InfoMessagesUtil.addMessages(messages, errorMsg);
		return errorMsg;
	}
	else if (booklistAdminForm.getNoteToLibrary().length() > 255)
	{
		errorMsg="Note to the library can not exceed 255 characters. Please shorten your note";
		InfoMessagesUtil.addMessages(messages, errorMsg);
		return errorMsg;
	}
        return errorMsg;
  }*/
		 return errorMsg;
}
}
