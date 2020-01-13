package za.ac.unisa.lms.tools.tpustudentplacement.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.struts.action.ActionMessages;
 
public class EmailValidater{
 
	  private Pattern pattern;
	  private Matcher matcher;
 
	  private static final String EMAIL_PATTERN = 
                   "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
 
	  public EmailValidater(){
		  pattern = Pattern.compile(EMAIL_PATTERN);
	  }
 
	  /**
	   * Validate hex with regular expression
	   * @param hex hex for validation
	   * @return true valid hex, false invalid hex
	   */
	  public boolean validate(final String hex){
 
		  matcher = pattern.matcher(hex);
		  return matcher.matches();
 
	  }
	  public void validateEmailAddressr(String emailAddress,ActionMessages messages){
          InfoMessagesUtil  infoMessagesUtil =new InfoMessagesUtil();
          String message="Invalid email address.";
         if (emailAddress.trim()!=null && 
                   !emailAddress.trim().equalsIgnoreCase("")){
         	 if (!validate(emailAddress.trim())){
         	          infoMessagesUtil.addMessages(messages,message);
         	 }
          }
    }

}
