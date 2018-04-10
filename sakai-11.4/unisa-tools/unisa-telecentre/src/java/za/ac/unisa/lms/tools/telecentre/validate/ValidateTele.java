package za.ac.unisa.lms.tools.telecentre.validate;

public class ValidateTele {

	public static boolean containsDigit(String s) {
	    boolean containsDigit = false;

	    if (s != null && !s.isEmpty()) {
	        for (char c : s.toCharArray()) {
	            if (containsDigit = Character.isDigit(c)) {
	                break;
	            }
	        }
	    }

	    return containsDigit;
	}
	public static boolean isEmpty(String s) {
	   try
	   {
	    if (s != null && !s.isEmpty()) {
	     
	    	
	    	
	    	return false;     	
	         
	         
	    }
	    else{ return true; }
	   }
	   catch(Exception ex)
	   {
		   return false;
		   
	   }
	   

	}
	
	public static boolean checkifSevenEighDigit(String s)
	{
		 if(s.length() >=7  || s.length() <=8)
		 {
			 return true;
			 
		 }
		 else
		 {
			 
			 return false;
		 }
		
		
	}
	public static boolean validateInteger(String s)
	{
		 if(!isEmpty(s))
		 {
			 
			 if(containsDigit(s) && isNumeric(s))
			 {
				if(checkifSevenEighDigit(s))
				{
				       
					return true;
				 	
				}
				else
				{
					
					return false;
					
				}//end of seven or eight
			 }
			 else
			 {
				 return false;
				 
			 }//end of digit
			 
		 }
		 else
		 {
			 
			 return false;
		 }//end empty
		 		
		
	}
	public static boolean isNumeric(String str)
	{
	    return str.matches("[+-]?\\d*(\\.\\d+)?");
	}
	
}
