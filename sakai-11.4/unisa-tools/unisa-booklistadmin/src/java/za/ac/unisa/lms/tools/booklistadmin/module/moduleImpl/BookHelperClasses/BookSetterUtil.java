package za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses;

import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import za.ac.unisa.lms.tools.booklistadmin.module.BookModule;

public class BookSetterUtil {

	public BookSetterUtil() {
		// TODO Auto-generated constructor stub
	}
	public void setBookData(BooklistAdminForm booklistAdminForm,BookModule bookModule){
		bookModule.setBookId(booklistAdminForm.getBookId());
		bookModule.setTypeOfBookList(booklistAdminForm.getTypeOfBookList());
		bookModule.setTxtTitle(booklistAdminForm.getTxtTitle());
		bookModule.setTxtAuthor(booklistAdminForm.getTxtAuthor());
		bookModule.setTxtBookNote(booklistAdminForm.getTxtBookNote());
		bookModule.setTxtEdition(booklistAdminForm.getTxtEdition());
		bookModule.setTxtISBN(booklistAdminForm.getTxtISBN());
		bookModule.setTxtISBN1("");
		bookModule.setTxtISBN2("");
		bookModule.setTxtISBN3("");
		bookModule.setTxtPublisher(booklistAdminForm.getTxtPublisher());
		bookModule.setTxtYear(booklistAdminForm.getTxtYear());
		bookModule.setPublishStatus(booklistAdminForm.getPublishStatus());
		bookModule.setCourse(booklistAdminForm.getCourseId());
		bookModule.setCourseLang(booklistAdminForm.getCourseLang());
		bookModule.setAcadYear(new Integer(booklistAdminForm.getYear()));
		bookModule.setOtherAuthor(booklistAdminForm.getTxtOtherAuthor());
		bookModule.setNoteToLibrary("");
		bookModule.setAvailableAsEbook("");
		bookModule.setEbook_pages("");
		bookModule.setEbookVolume("");
		bookModule.setUrl("");
		bookModule.setMasterCopy("");
		bookModule.setMasterCopyFormat("");
		booklistAdminForm.setBookDetails(bookModule);
	}
	public void setPrescribedBookData(BooklistAdminForm booklistAdminForm,BookModule bookModule){
		               setBookData(booklistAdminForm,bookModule);
	}
	public void setRecommendedBookData(BooklistAdminForm booklistAdminForm,BookModule bookModule){
                      setBookData(booklistAdminForm,bookModule);
                      setRBookSpecificData(booklistAdminForm,bookModule);
    }
	private void setRBookSpecificData(BooklistAdminForm booklistAdminForm,BookModule bookModule){
	                  bookModule.setNoteToLibrary(booklistAdminForm.getNoteToLibrary());
  	                  bookModule.setAvailableAsEbook(booklistAdminForm.getAvailableAsEbook());
  	                  bookModule.setOtherAuthor(booklistAdminForm.getTxtOtherAuthor());
  	                  bookModule.setTxtISBN1(booklistAdminForm.getTxtISBN1());
	                  bookModule.setTxtISBN2(booklistAdminForm.getTxtISBN2());
	                  bookModule.setTxtISBN3(booklistAdminForm.getTxtISBN3());
	}
	private void setEBookSpecificData(BooklistAdminForm booklistAdminForm,BookModule bookModule){
	                    bookModule.setPublishStatus(3);
	                   	bookModule.setEbook_pages(booklistAdminForm.getEbook_pages());
	                	bookModule.setEbookVolume(booklistAdminForm.getEbookVolume());
	                	bookModule.setUrl(booklistAdminForm.getUrl());
	                	bookModule.setMasterCopy(booklistAdminForm.getMasterCopy());
	                	bookModule.setMasterCopyFormat(booklistAdminForm.getMasterCopyFormat());
	                	bookModule.seteReserveType(booklistAdminForm.geteReserveType());
	}
	public void setEreserveBookData(BooklistAdminForm booklistAdminForm,BookModule bookModule){
		        setBookData(booklistAdminForm, bookModule);
		        setEBookSpecificData(booklistAdminForm, bookModule);
    }
	public void setEreserveBookData(BooklistAdminForm booklistAdminForm,BookModule bookModule,String bookType){
                     setBookData(booklistAdminForm, bookModule);
                     setEBookSpecificData(booklistAdminForm, bookModule);
                     if(bookType.equalsIgnoreCase("Book Chapter")){
		             		bookModule.setTxtEdition(booklistAdminForm.getTxtEdition());
		             }else{
			                  bookModule.setTxtEdition("");
		             }
   }
	 
}  
