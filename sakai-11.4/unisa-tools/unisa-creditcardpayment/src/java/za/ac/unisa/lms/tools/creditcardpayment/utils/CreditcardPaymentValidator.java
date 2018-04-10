package za.ac.unisa.lms.tools.creditcardpayment.utils;

import java.math.BigDecimal;

import za.ac.unisa.lms.tools.creditcardpayment.forms.CreditCardPaymentForm;

public class CreditcardPaymentValidator {
	
	/**
	 * 
	 * Validate student number
	 * 
	 */	
	public String checkStudentNr(String studentNr){
		
		
		if (studentNr == null || "".equalsIgnoreCase(studentNr.trim())){
			return  "Enter a valid student or application reference number.";			
		}

		if (studentNr.trim().length() < 7 ){
			return "Student or application reference number must be at least 7 characters.";
		}
		if (studentNr.trim().length() >8 ){
			return "Student or application reference number must not be more than 8 characters.";
		}
		/* stud number must be numeric */
		try {
			Long.parseLong(studentNr);
		} catch (NumberFormatException e) {
				return "Student or application reference number must be numeric.";
		}
		return "";
	}
	public String validateCvvNum(CreditCardPaymentForm creditForm){
        if(creditForm.getCvvNo()== null || "".equals(creditForm.getCvvNo().trim())){
	             return "CVV number cannot be empty.";
        }else{
	              creditForm.setCardHolder(creditForm.getCardHolder().trim());
        }
        if(! isNumeric(creditForm.getCvvNo())){
	             return "CVV number must be numeric.";
        }
        if(creditForm.getCvvNo().length() >3){
	             return "CVV number must have 3  characters ";
        }
        if(creditForm.getCvvNo().length() <3){
	            return "CVV number must have 3  characters ";
        }
        return "";
    }
	public String	validateCredicardNum(CreditCardPaymentForm creditForm){
                      if (creditForm.getCardNumber()== null || "".equals(creditForm.getCardNumber().trim())){
           	               return "Credit card number cannot be empty.";
                      }else{
                            creditForm.setCardNumber(creditForm.getCardNumber().trim());
                      }
                      if(! isNumeric(creditForm.getCardNumber())){
                          return "Credit card number must be numeric.";
                      }else if(creditForm.getCardNumber().trim().length() < 16){
                          return "Credit card number must consist of 16 digits.";
                      }else if(creditForm.getCardNumber().trim().length() > 16){
                          return "Credit card number must consist of 16 digits only.";
                      }
                    return "";
   }
	public String validateCreditcardHolderName(CreditCardPaymentForm creditForm){
		      if (creditForm.getCardHolder()== null || "".equals(creditForm.getCardHolder().trim())){
		        	return "Card holders name cannot be empty.";
		      }else{
			        creditForm.setCardHolder(creditForm.getCardHolder().trim());
		      }
		      return "";
	}
	public String validateEmail(CreditCardPaymentForm creditForm){
                 	String emailAdrress=creditForm.getStudent().getEmailAddress();
	               if( emailAdrress== null || "".equals(emailAdrress.trim())){
		                 return  "E-mail address cannot be empty.";
	               }
	               EmailValidator  emValidator=new EmailValidator();
	               boolean valid = emValidator.validate(emailAdrress);
	               if(!valid){			
		               return "E-mail address invalid.";
	               }else{
		               creditForm.getStudent().setEmailAddress(emailAdrress);
	               }
	               return "";
	}
	private boolean isNumeric(String testString){

		boolean result = true;

		try{
			Long.parseLong(testString);
		} catch (NumberFormatException e) {
			result = false;
		}

		return result;

	}
	
	public String validateMoney(String testString, int testForZero){

		// testForZero = 1: input can not be zero
		BigDecimal money;
		
		// must be A NUMBER
		try{
			money = new BigDecimal(testString);
		} catch (NumberFormatException e) {
			return " is invalid.";
		}
		if(testForZero==1){
			// can not be zero
			if (money.compareTo(new BigDecimal("0"))==0){
				return " can not be zero.";
			}
		}
		// can not be negative
		int pos = testString.indexOf("-");
		if (pos !=-1){
			return " is invalid.";
		}
		// can not be more than 2 digits after decimal point and 7 before
		pos = testString.indexOf(".");
		if(pos >= 0){
			String remainder = testString.substring(pos+1);
			if (remainder.length()>2){
				return " is invalid.";
			}
			String number = testString.substring(0,pos-1);
			if (number.length()>7){
				return " is too large.";
			}
			
		}else{
			if (testString.length()>7){
				return " is too large.";
			}
		}
		
		return "";

	}
public String validateTotals(String testString){
	             return validateMoney(testString,1);
	}

}
