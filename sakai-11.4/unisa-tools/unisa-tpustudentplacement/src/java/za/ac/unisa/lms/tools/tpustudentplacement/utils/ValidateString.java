package za.ac.unisa.lms.tools.tpustudentplacement.utils;

import org.apache.struts.action.ActionMessages;

public class ValidateString {
	
	public boolean validateStr(String field){
		                boolean valid=true;
                        if((field==null)||
      		                   (field.trim().equals(""))){
                       	           valid=false;
                        }
                        return valid;
	}
	public void validateStr(String   str,ActionMessages messages,String msg){ 
		               boolean valid=validateStr(str);
		               if(!valid){
		                     InfoMessagesUtil infoMessagesUtil =new InfoMessagesUtil ();
		                     infoMessagesUtil.addMessages(messages, msg);
		               }
   }  

}
