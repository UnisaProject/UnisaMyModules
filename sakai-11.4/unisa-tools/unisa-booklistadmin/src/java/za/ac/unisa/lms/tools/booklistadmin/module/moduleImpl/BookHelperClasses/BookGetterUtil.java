package za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses;

import java.util.List;

import za.ac.unisa.lms.tools.booklistadmin.dao.BookDAO;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import za.ac.unisa.lms.tools.booklistadmin.module.BookModule;
public class BookGetterUtil {

	
	BookDAO bookDAO;
	public BookGetterUtil(BookDAO bookDAO) {
		    this.bookDAO=bookDAO;
	}
	public void getBookData(BooklistAdminForm booklistAdminForm){
		             BookModule bookModule=bookDAO.getBook(booklistAdminForm.getBookId());
			         booklistAdminForm.setBookDetails(bookModule);
		    		 booklistAdminForm.seteReserveType(bookModule.geteReserveType());
		             booklistAdminForm.setTxtTitle(bookModule.getTxtTitle());
		             booklistAdminForm.setTxtAuthor(bookModule.getTxtAuthor());
		             booklistAdminForm.setTxtBookNote(bookModule.getTxtBookNote());				
		             booklistAdminForm.setCourseLang(bookModule.getCourseLang());
		             booklistAdminForm.setTxtEdition(bookModule.getTxtEdition());
		             booklistAdminForm.setEbook_pages(bookModule.getEbook_pages());
		             booklistAdminForm.setEbookVolume(bookModule.getEbookVolume());
		             booklistAdminForm.setUrl(bookModule.getUrl());
		             booklistAdminForm.setMasterCopy(bookModule.getMasterCopy());
		             booklistAdminForm.setMasterCopyFormat(bookModule.getMasterCopyFormat());
		             booklistAdminForm.setAvailableAsEbook(bookModule.getAvailableAsEbook());
		             booklistAdminForm.setTxtOtherAuthor(bookModule.getOtherAuthor());
		             booklistAdminForm.setTxtISBN(bookModule.getTxtISBN());
		             booklistAdminForm.setTxtISBN1(bookModule.getTxtISBN1());
		             booklistAdminForm.setTxtISBN2(bookModule.getTxtISBN2());
		             booklistAdminForm.setTxtISBN3(bookModule.getTxtISBN3());
		             booklistAdminForm.setTxtYear(bookModule.getTxtYear());
		             booklistAdminForm.setTxtPublisher(bookModule.getTxtPublisher());
		             booklistAdminForm.setPublishStatus(bookModule.getPublishStatus());
		             booklistAdminForm.setNoteToLibrary(bookModule.getNoteToLibrary());
	}
	public List getSearchedBooks(BooklistAdminForm booklistAdminForm)throws Exception{
                       String publisherName=booklistAdminForm.getPublisherName();
                       if(publisherName==null){
                    	   publisherName="";
                       }
                       publisherName=publisherName.trim();
                       String bookName=booklistAdminForm.getTxtTitle().trim();
                       String authorName=booklistAdminForm.getTxtAuthor().trim();
                       int year=new Integer(booklistAdminForm.getYear().trim());
                       String bookListType=booklistAdminForm.getTypeOfBookList().trim();
                       String ereserveType="";
                       if(bookListType.equals("E")){
                    	   ereserveType=booklistAdminForm.geteReserveType().trim();
                       }
                       List list =bookDAO.getSearchbook(bookName,authorName,publisherName,year,bookListType,ereserveType);
                  return list;
   }
	
	public void deleteBook(BookModule bookDetails,String course, String acadyear) throws Exception{
	       bookDAO.deleteBook(bookDetails, course.trim(), acadyear.trim());
}
public BookModule getBook (String bookId) {
	return  bookDAO.getBook(bookId);
}
public Integer insertBook(BookModule bookDetails, String bookType)throws Exception{	
	return  bookDAO.insertBook(bookDetails, bookType);
}
public Integer updateBook(BookModule bookDetails) throws Exception{
	return  bookDAO.updateBook(bookDetails);
}		
public Integer updatebookforcourses(BookModule bookDetails) throws Exception{
	return  bookDAO.updatebookforcourses(bookDetails);
}
public String getBooknr () throws Exception {
	
	return  bookDAO.getBooknr();
}
public BookModule getBookData(String bookId){
    return bookDAO.getBook(bookId);
}

}
