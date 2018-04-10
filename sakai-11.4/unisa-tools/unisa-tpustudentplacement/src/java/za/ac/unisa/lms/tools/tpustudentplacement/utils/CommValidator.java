package za.ac.unisa.lms.tools.tpustudentplacement.utils;

import org.apache.struts.action.ActionMessages;

import za.ac.unisa.utils.CellPhoneVerification;

public class CommValidator {
	 private static final String errormsg="Invalid Cell phone number. Cell phone number "+
             "must start with a + and must be at least 12 characters long.";	

public void validateCellNr(String cellNr,String countryCode,ActionMessages messages){
InfoMessagesUtil  infoMessagesUtil =new InfoMessagesUtil();
String message="Cell number is required";
if(cellNr==null ||
cellNr.trim().equalsIgnoreCase("")){
  infoMessagesUtil.addMessages(messages,message);
}else{//Test for valid cell number
 checkValidCellNumber(cellNr, countryCode,messages,infoMessagesUtil);
}
}
public void checkValidSAcellNumber(String cellNr,ActionMessages messages,InfoMessagesUtil  infoMessagesUtil){
           CellPhoneVerification cellVerification = new CellPhoneVerification();
           String message="";
           if(!cellVerification.isSaCellNumber(cellNr)){
	                 message="SA cell phone number must start with +27";
	         }else if (!cellVerification.validSaCellNumber(cellNr)){
				         message= "Invalid SA cell phone number, wrong number range.";
			 }
           infoMessagesUtil.addMessages(messages,message);
} 

public void checkValidCellNumber(String cellNr,String countryCode, ActionMessages messages,InfoMessagesUtil  infoMessagesUtil){
             cellphoneValidator cellVerifier = new cellphoneValidator();
             String message="";
	              if(!cellVerifier.validCellNumber(cellNr)){
	    	             message=errormsg;
	    	             infoMessagesUtil.addMessages(messages,message);
		          }else  if (countryCode.trim().equalsIgnoreCase("1015")){ 
	    	                  checkValidSAcellNumber(cellNr, messages,infoMessagesUtil);
	              }

} 
public void validateEmailAddressr(String emailAddress,ActionMessages messages){
InfoMessagesUtil  infoMessagesUtil =new InfoMessagesUtil();
String message="Invalid email address.";
EmailValidater emailValidator = new EmailValidater();
if (emailAddress.trim()!=null && 
        !emailAddress.trim().equalsIgnoreCase("")){
	 if (!emailValidator.validate(emailAddress.trim())){
	          infoMessagesUtil.addMessages(messages,message);
	 }
}
}

}
