package za.ac.unisa.lms.tools.creditcardpayment.actions;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import za.ac.unisa.lms.tools.creditcardpayment.forms.CreditCardPaymentForm;
import za.ac.unisa.lms.tools.creditcardpayment.utils.CreditcardPaymentValidator;

public class StudyFee extends Fee{

	private double minimumExpected;
	private final String errMsg="Study fee amount you wish to pay can not be less than R" ;
	public StudyFee(CreditcardPaymentValidator creditcardPaymentValidator) {
		            super(creditcardPaymentValidator,"Study fee amount you wish to pay ");
	}
	public String isGreaterThanMinimumRequired(double minimumExpected, double amountToBePaid){
		              String errMsg="";
		              if (minimumExpected>0){
			               if (amountToBePaid<minimumExpected){
				                 NumberFormat formatter = new DecimalFormat("#0.00");
				                 errMsg= errMsg+ formatter.format(minimumExpected);
			                }    
		               }
		          return errMsg;
	}
	protected double convertInputToDouble(String studyFeeAmountInput){
        return Double.parseDouble(studyFeeAmountInput);
    }
   
	public void setAmountEntered(String amountEntered) throws InvalidAmountException {
		             String errMsg=validateInputAmount(amountEntered);
		             if(!errMsg.isEmpty()){
		            	   throw new InvalidAmountException(errMsg);
		             }
		             double amountToBePaid=convertInputToDouble(amountEntered);
		             errMsg=isGreaterThanMinimumRequired(minimumExpected,amountToBePaid);
		             if(!errMsg.isEmpty()){
		            	   throw new InvalidAmountException(errMsg);
		             }
		             this.amountEntered = amountEntered;
	}
	public double getMinimumExpected() {
		return minimumExpected;
	}
	public void setMinimumExpected(double minimumExpected) {
		this.minimumExpected = minimumExpected;
	}
	public String setStudyFeeToFromBean(CreditCardPaymentForm creditForm){
		          try{
		                String studyFeeAmountEntered=creditForm.getStudyFeeAmountInput();
		                setAmountEntered(studyFeeAmountEntered);
		                setMinimumExpected(creditForm.getMinimumStudyFee());
		                creditForm.setStudyFeeAmount(getAmountToBePaid());
		                return "";
	             }catch(InvalidAmountException ime){
		                 return ime.getErr();
	            }
	}

}
