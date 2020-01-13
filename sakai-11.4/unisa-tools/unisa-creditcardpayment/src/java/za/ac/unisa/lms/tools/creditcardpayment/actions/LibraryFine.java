package za.ac.unisa.lms.tools.creditcardpayment.actions;

import za.ac.unisa.lms.tools.creditcardpayment.forms.CreditCardPaymentForm;
import za.ac.unisa.lms.tools.creditcardpayment.utils.CreditcardPaymentValidator;

public class LibraryFine extends Fee{
	public  LibraryFine(CreditcardPaymentValidator creditcardPaymentValidator) {
		            super(creditcardPaymentValidator,"Library fine amount you wish to pay ");
	}
	public String setLibraryFineFeeToFormBean(CreditCardPaymentForm creditForm){
		          try{
		                String libraryAmountEntered=creditForm.getLibraryFineFeeAmountInput();
		                setAmountEntered(libraryAmountEntered);
		                creditForm.setLibraryFeeAmount(getAmountToBePaid());
		                return "";
	             }catch(InvalidAmountException ime){
		                 return ime.getErr();
	            }
	}

}
