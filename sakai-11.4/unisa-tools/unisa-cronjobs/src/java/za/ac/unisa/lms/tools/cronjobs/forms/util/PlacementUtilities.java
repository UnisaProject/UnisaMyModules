package za.ac.unisa.lms.tools.cronjobs.forms.util;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;

public class PlacementUtilities {
	 public boolean isValidDate(String inDate, String format) {

		    if (inDate == null)
		      return false;

		    //set the format to use as a constructor argument
		    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		    
		    if (inDate.trim().length() != dateFormat.toPattern().length())
		      return false;

		    dateFormat.setLenient(false);
		    
		    try {
		      //parse the inDate parameter
		      dateFormat.parse(inDate.trim());
		    }
		    catch (ParseException pe) {
		      return false;
		    }
		    inDate.indexOf("/");
		    if (inDate.indexOf("/")!=4 || inDate.length()!=10){
		    	return false;
		    }
		    inDate = inDate.substring(inDate.indexOf("/")+1);
		    if (inDate.indexOf("/")!=2 || inDate.length()!=5){
		    	return false;
		    }
		    return true;
	}
	 
	 public Integer getDefaultAcadYear() {
			return Calendar.getInstance().get(Calendar.YEAR);
		} 
	 
	 public boolean isInteger(String stringValue) {
			try
			{
				Integer i = Integer.parseInt(stringValue);
				return true;
			}	
			catch(NumberFormatException e)
			{}
			return false;
		}
	 public boolean isStringEmpty(String str){
        
         if(str==null ||str.equals("")){
      	      return true;
         }else{
        	    return false;
         }
	 }
	 private static final String saCode="1015";
	 public static String getSaCode(){
		 return saCode;
	 }


}