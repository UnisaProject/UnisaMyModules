package za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses;
import org.apache.struts.action.ActionMessages;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.InfoMessagesUtil;
public class BookValidator {

	public BookValidator() {
		// TODO Auto-generated constructor stub
	}
    public String validateInputForSearchByPubisher(BooklistAdminForm booklistAdminForm,ActionMessages messages){
		             String errorMsg= "";
		             String nextPage="searchform";
		             if(booklistAdminForm.getTypeOfBookList().equals("E")){
		            	
		            		if((booklistAdminForm.geteReserveType()==null)||
		            				 (booklistAdminForm.geteReserveType().equals("")))
		            				 {
    			                      errorMsg="Please select the type of eReserve you would like to search for.";
    			                      InfoMessagesUtil.addMessages(messages,errorMsg);
    			      	            return nextPage;
		            		}
		             }
		             
		             if (booklistAdminForm.getTxtTitle().equals("")  && 
                                booklistAdminForm.getTxtAuthor().equals("")&&
                                booklistAdminForm.getPublisherName().equals("")){
                                errorMsg= "Please enter Title or/and Author or/and Publisher.";
		             }else{
		            	       if (!booklistAdminForm.getTxtTitle().equalsIgnoreCase("") || 
		            		        !booklistAdminForm.getTxtAuthor().equalsIgnoreCase("") || 
			        		          !booklistAdminForm.getPublisherName().equalsIgnoreCase("")){
			                                 if (booklistAdminForm.getTxtTitle().length() < 3 && 
			                                		 booklistAdminForm.getTxtAuthor().length() < 3 &&
			                                		  booklistAdminForm.getPublisherName().length()<3){
			                                	         errorMsg="Please enter valid data of at least three characters in either "+
			                                	 	       "Title or Author to continue your search for book information.";
				                              } 
		            	       }
		           }
    	            InfoMessagesUtil.addMessages(messages,errorMsg);
    	            return nextPage;
    }
    public String validateInputForSearchNoOption(BooklistAdminForm booklistAdminForm,ActionMessages messages){
    	               String errorMsg= "";
                       String nextPage="searchform";
                       	   if ((booklistAdminForm.getTypeOfBookList().equals("E"))&&
          		            		  ((booklistAdminForm.geteReserveType()==null)||
          		            				 (booklistAdminForm.geteReserveType().equals(""))))
          		            		   {
              			                      errorMsg="Please select the type of eReserve you would like to search for.";
          		            				         				
                                       }else if  (booklistAdminForm.getTxtTitle().equalsIgnoreCase("") 
    	            		                           && booklistAdminForm.getTxtAuthor().equalsIgnoreCase("")){
    	            	                                errorMsg= "Please enter Title or/and Author.";
		             	                } else if ((!booklistAdminForm.getTxtTitle().equalsIgnoreCase("") ||
		             			                   !booklistAdminForm.getTxtAuthor().equalsIgnoreCase(""))){
		                                   if (booklistAdminForm.getTxtTitle().length() < 3 && 
		                                		   booklistAdminForm.getTxtAuthor().length() < 3){
		                                	            errorMsg="Please enter valid data of at least three characters in either "+
		                                	 	        "Title or Author to continue your search for book information.";
		                                   } 
		                                  
				        }
                       
		    	        InfoMessagesUtil.addMessages(messages,errorMsg);
		    	        return nextPage;
  }	
}
