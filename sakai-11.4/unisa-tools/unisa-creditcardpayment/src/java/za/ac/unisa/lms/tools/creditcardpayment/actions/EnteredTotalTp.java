package za.ac.unisa.lms.tools.creditcardpayment.actions;

import za.ac.unisa.lms.tools.creditcardpayment.forms.CreditCardPaymentForm;
import za.ac.unisa.lms.tools.creditcardpayment.utils.CreditcardPaymentValidator;

public class EnteredTotalTp extends Fee{
	           TotalAmountValidatorTp totalAmountVerifier;
	           public  EnteredTotalTp(CreditcardPaymentValidator creditcardPaymentValidator) {
                           super(creditcardPaymentValidator,"Total amount being paid. ");
                           totalAmountVerifier=new TotalAmountValidatorTp();
               }
	           public void setAmountEntered(String amountEntered) throws InvalidAmountException {
		                       String errMsg=validateInputAmount(amountEntered);
		                       if(!errMsg.isEmpty()){
		            	            throw new InvalidAmountException(errMsg);
		                       }
		                       errMsg=creditcardPaymentValidator.validateTotals(amountEntered);
		                       if(!errMsg.isEmpty()){
		            	             throw new InvalidAmountException(errMsg);
		                       }
		                       errMsg=creditcardPaymentValidator.validateTotals(amountEntered);
		                       if(!errMsg.isEmpty()){
		            	             throw new InvalidAmountException(errMsg);
		                       }
		                       this.amountEntered = amountEntered;
	           }
               public String setTotalAmountTpToFormBean(CreditCardPaymentForm creditForm){
                                     try{
                                             String totalFeeEntered=creditForm.getCcTotalAmountInput();
                                             setAmountEntered(totalFeeEntered);
                                             creditForm.setCcTotalAmount(getAmountToBePaid());
                                             return "";
                                       }catch(InvalidAmountException ime){
                                               return ime.getErr();
                                       }
              }
            
}