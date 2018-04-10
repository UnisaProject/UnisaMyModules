package za.ac.unisa.lms.tools.creditcardpayment.actions;
import za.ac.unisa.lms.tools.creditcardpayment.forms.CreditCardPaymentForm;
import za.ac.unisa.lms.tools.creditcardpayment.utils.CreditcardPaymentValidator;

public class CreditCard {
/*	
	public CreditCard(CreditcardPaymentValidator creditcardPaymentValidator){
		this.creditcardPaymentValidator=creditcardPaymentValidator;
	}
	CreditcardPaymentValidator creditcardPaymentValidator;
	public String validateCreditCardDetails(String creditCardNumber,String creditCardHolderName,String cvvNum){
		                 String errMsg=validateCredicardNum(creditCardNumber);
		                 if(!errMsg.isEmpty())
		            	      return  errMsg;
		                 errMsg=validateCreditcardHolderName( creditCardHolderName);
		                 if(!errMsg.isEmpty())
		            	      return  errMsg;
		                 errMsg=validateCvvNum(cvvNum);
		                return  errMsg;
		}
	public String validateCvvNum(String cvvNumber){
		              String errMsg= isCvvNumEmpty(cvvNumber);
                      if(!errMsg.isEmpty())
   	                      return  errMsg;
                      errMsg=isCvvNumNumeric(cvvNumber);
                      if(!errMsg.isEmpty())
   	                     return  errMsg;
                       errMsg= isCvvNumLenght3Digits(cvvNumber);
                    return  errMsg;
    }
	public String	validateCredicardNum(String creditCardNumber){
		                 String errMsg=isCreditCardNumEmpty(creditCardNumber);
		                 if(!errMsg.isEmpty())
		            	      return  errMsg;
		                 errMsg=isCreditCardNumNumeric(creditCardNumber);
		                 if(!errMsg.isEmpty())
		            	      return  errMsg;
		                 errMsg=isCardNumLenght16Digits(creditCardNumber);
		                return  errMsg;
    }
	private String  isCreditCardNumEmpty(String creditCardNumber){
		                  String errMsg="";
                          if (creditCardNumber== null || creditCardNumber.trim().isEmpty()){
                                errMsg= "Credit card number cannot be empty.";
                          }
		               return errMsg;
     }
	private String  isCreditCardNumNumeric(String creditCardNumber){
                       String errMsg="";
		               if(!creditcardPaymentValidator.isNumeric(creditCardNumber.trim())){
		            	    errMsg= "Credit card number must be numeric.";
		               }
                       return errMsg;
   }
	private String  isCardNumLenght16Digits(String creditCardNumber){
                         String errMsg="";
                         if(creditCardNumber.trim().length() != 16){
                               errMsg="Credit card number must have 16  characters.";
                        }
                       return errMsg;
  }
	private String  isCvvNumEmpty(String cvvNumber){
                          String errMsg="";
                          if (cvvNumber== null || cvvNumber.trim().isEmpty()){
                               errMsg="CVV number cannot be empty.";
                          }
                        return errMsg;
     }
    private String  isCvvNumNumeric(String cvvNumber){
                      String errMsg="";
                      if(!creditcardPaymentValidator.isNumeric(cvvNumber.trim())){
  	                        errMsg= "CVV number must be numeric.";
                      }
                      return errMsg;
    }
    private String  isCvvNumLenght3Digits(String cvvNumber){
                        String errMsg="";
                        if(cvvNumber.trim().length() != 3){
                              errMsg="CVV number must have 3  characters.";
                        }
                         return errMsg;
   }
  public String validateCreditcardHolderName(String creditCardHolderName){
		             if (creditCardHolderName== null || "".equals(creditCardHolderName.trim())){
		        	            return "Card holders name cannot be empty.";
		             } 
		             return "";
	}
  public String validateCreditCardDetails(CreditCardPaymentForm creditCardPaymentForm ){
	                String creditCardNumber=creditCardPaymentForm.getCardNumber();
	                String creditCardHolderName=creditCardPaymentForm.getCardHolder();
	                String cvvNum=creditCardPaymentForm.getCvvNo();
	               return  validateCreditCardDetails(creditCardNumber,creditCardHolderName,cvvNum);
 }
 */
}
