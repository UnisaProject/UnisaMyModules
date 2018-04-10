package za.ac.unisa.lms.tools.booklistadmin.module.view;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses.BookGetterUtil;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import za.ac.unisa.lms.tools.booklistadmin.module.Book;

public class AddBookScreen {

	BookGetterUtil bookDataGetter;
	public AddBookScreen(BookGetterUtil bookDataGetter) {
		      this.bookDataGetter=bookDataGetter;
	}
	private  String setDataForEditBookScreen(BooklistAdminForm booklistAdminForm){
		                     bookDataGetter.getBookData(booklistAdminForm);
		                     if(booklistAdminForm.getTypeOfBookList().equalsIgnoreCase("E")){
		                    		 String ereserveType=booklistAdminForm.geteReserveType().trim();
          				                  booklistAdminForm.setCourseLang(" ");
          				                  return getAppropriateEreserveBookInputScreen(ereserveType,booklistAdminForm);
                             }else{
                                       booklistAdminForm.setCourseLang(booklistAdminForm.getBookDetails().getCourseLang());
                                      return "newbookadd";
                             }
   }
	
	private String getAppropriateEreserveBookInputScreen(String ereserverType,BooklistAdminForm booklistAdminForm){
		       String nextPage="";
		if(ereserverType.equalsIgnoreCase("Journal")){
			booklistAdminForm.seteReserveType("Journal");
			nextPage= "addjournal";
		}else if(ereserverType.equalsIgnoreCase("LawReport")){
			booklistAdminForm.seteReserveType("LawReport");
			nextPage= "addlawreport";
		}else{
			booklistAdminForm.seteReserveType("BookChapter");
				nextPage= "addbookchapter";
		}
		return nextPage;
	}
	public String setDataForAddBookScreen(BooklistAdminForm booklistAdminForm){
		                
		                   if(booklistAdminForm.getSearchOption().equalsIgnoreCase("edit")){
			                      return setDataForEditBookScreen(booklistAdminForm);
		                   }else if(booklistAdminForm.getSearchOption().equals("searchform")){
		                	   return setDataForBookSearchScreen(booklistAdminForm);
		                   }else{
		                	   return refereshAddNewBookScreen(booklistAdminForm);
		                   }
    }
	private String  refereshAddNewBookScreen(BooklistAdminForm booklistAdminForm){
			                 booklistAdminForm.setTxtTitle("");
				             booklistAdminForm.setTxtAuthor("");
				             booklistAdminForm.setTxtBookNote("");
				             booklistAdminForm.setCourseLang("E");			
				             booklistAdminForm.setTxtEdition("");
				             booklistAdminForm.setTxtISBN("0000000000000");
				             booklistAdminForm.setTxtISBN1("0000000000000");
				             booklistAdminForm.setTxtISBN2("0000000000000");
				             booklistAdminForm.setTxtISBN3("0000000000000");
				             booklistAdminForm.setTxtYear("");
				             booklistAdminForm.setTxtPublisher("");
				             booklistAdminForm.setPublishStatus(0);
				             booklistAdminForm.setTxtOtherAuthor("");
				             booklistAdminForm.setAvailableAsEbook("unknown");
				             booklistAdminForm.setEbook_pages("");
				             booklistAdminForm.setEbookVolume("");
				             booklistAdminForm.setNoteToLibrary("");
				             booklistAdminForm.setUrl("");
				             booklistAdminForm.setMasterCopy("N");
				             booklistAdminForm.setMasterCopyFormat("Unknown");
				             if(booklistAdminForm.getTypeOfBookList().equalsIgnoreCase("E")){
	                    		     String ereserveType=booklistAdminForm.geteReserveType().trim();
      				                 return       getAppropriateEreserveBookInputScreen(ereserveType,booklistAdminForm);
				             }else{
				                     return "newbookadd";
				             }
	}
	private String setDataForBookSearchScreen(BooklistAdminForm booklistAdminForm){
		              
		                    	      booklistAdminForm.setTxtAuthor("");
		                              booklistAdminForm.setTxtTitle("");
		                              return "searchform";
                      
     }

	
}
