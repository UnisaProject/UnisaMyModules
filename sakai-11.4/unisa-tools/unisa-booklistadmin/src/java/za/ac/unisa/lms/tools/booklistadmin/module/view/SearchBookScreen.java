package za.ac.unisa.lms.tools.booklistadmin.module.view;

import java.util.Calendar;

import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;

public class SearchBookScreen {

	public SearchBookScreen() {
		// TODO Auto-generated constructor stub
	}
	public  String createSearchBookScreen(BooklistAdminForm booklistAdminForm ){
	             	if(booklistAdminForm.getSearchOption().equals("searchCourse")){
	             	        searchByCourseScreen(booklistAdminForm);
		                    return "searchcourse";
	                }else if(booklistAdminForm.getSearchOption().equals("publisher")){
	                	 return searchByPublisherScreen(booklistAdminForm);
	                }else{
	                	return "searchform";
	                }
	}
	private  String searchByPublisherScreen(BooklistAdminForm booklistAdminForm){
		               booklistAdminForm.setTxtAuthor("");
		               booklistAdminForm.setTxtTitle("");
		               booklistAdminForm.setPublisherName("");
		               booklistAdminForm.setSearchOption("publisher");
		               return "searchform";
		                   
	}
	private  void searchByCourseScreen(BooklistAdminForm booklistAdminForm){
                      String enteredYear=booklistAdminForm.getYear();
                      Calendar calendar = Calendar.getInstance();
	                  Integer nextYear = new Integer(calendar.get(Calendar.YEAR)+1);
	                  Integer thisYear = new Integer(calendar.get(Calendar.YEAR));
	                  Integer lastYear= new Integer(calendar.get(Calendar.YEAR)-1);
	                  Integer year= getYear(enteredYear,nextYear,thisYear,lastYear);
	                  setTheBookListYears(booklistAdminForm,year,nextYear,thisYear,lastYear);
	                  booklistAdminForm.setCourseId("");
   }
	private Integer getYear(String enteredYear,Integer nextYear,Integer thisYear,Integer lastYear){
		     		      Integer year;				  
		      		      if (enteredYear != null){
		    	                 year=getEnteredYear(enteredYear, nextYear,thisYear,lastYear);
			              } else {
				                    year = nextYear;				  
			              }	
		      		      return year;
    }
	private  Integer getEnteredYear(String enteredYear,Integer nextYear,Integer thisYear,Integer lastYear){
		                 Integer year;
		                 if(enteredYear.equalsIgnoreCase(nextYear.toString())){
                                   year = nextYear;
                         } else if (enteredYear.equalsIgnoreCase(lastYear.toString())){
                                     year = lastYear;
                         } else {
                                    year = thisYear;
                         }
		                 return year; 
	}
	private void setTheBookListYears(BooklistAdminForm booklistAdminForm,Integer year,Integer nextYear,
			        Integer thisYear,Integer lastYear){
	                      booklistAdminForm.setYear(year.toString());
                          booklistAdminForm.setLastYear(lastYear.toString());
                          booklistAdminForm.setCurrentYear(thisYear.toString());
                          booklistAdminForm.setNextYear(nextYear.toString());
                          
	}
}
