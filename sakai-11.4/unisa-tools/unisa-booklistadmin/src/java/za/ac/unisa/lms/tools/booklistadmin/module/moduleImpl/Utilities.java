package za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl;

import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;

public class Utilities {
	public static int toInt(String intStr){
	             return Integer.parseInt(intStr);
     }
	public static void trimEnteredData(String bookType,BooklistAdminForm booklistAdminForm){
                     booklistAdminForm.setTxtTitle(rtrim(ltrim(booklistAdminForm.getTxtTitle())));
                     booklistAdminForm.setTxtAuthor(rtrim(ltrim(booklistAdminForm.getTxtAuthor())));
 		             if(bookType.equals("BookChapter")){
                             booklistAdminForm.setTxtEdition(rtrim(ltrim(booklistAdminForm.getTxtEdition())));
                     }
                     if(!bookType.equals("LawReport")){
                          booklistAdminForm.setTxtYear(rtrim(ltrim(booklistAdminForm.getTxtYear())));
                     }
                     if(!bookType.equals("LawReport")){
                          booklistAdminForm.setTxtPublisher(rtrim(ltrim(booklistAdminForm.getTxtPublisher())));
                     }
                     if(!bookType.equals("notEreserves")){
                         booklistAdminForm.setEbook_pages(rtrim(ltrim(booklistAdminForm.getEbook_pages())));
                     }
    }
	public static boolean isInteger(String i)
	{
		try{
			Integer.parseInt(i);
			return true;
		}catch(NumberFormatException nfe)
		{
			return false;
		}
	}
	/* remove leading whitespace */
    public static String ltrim(String source) {
        return source.replaceAll("^\\s+", "");
    }

    /* remove trailing whitespace */
    public static String rtrim(String source) {	
        return source.replaceAll("\\s+$", "");
    }
}
