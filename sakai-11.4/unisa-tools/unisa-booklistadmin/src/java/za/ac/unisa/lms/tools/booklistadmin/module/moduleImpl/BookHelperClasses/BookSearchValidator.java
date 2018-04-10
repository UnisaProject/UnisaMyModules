package za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses;

public class BookSearchValidator {

	public BookSearchValidator() {
		// TODO Auto-generated constructor stub
	}
	
	public   static String validateForNullnes(String str){
		if (str==null){
			    return "";
		}else{
			return str.trim();
		}
	}
}
	
