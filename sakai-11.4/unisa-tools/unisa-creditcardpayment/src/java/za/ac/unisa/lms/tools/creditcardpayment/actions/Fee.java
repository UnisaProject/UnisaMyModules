package za.ac.unisa.lms.tools.creditcardpayment.actions;
import za.ac.unisa.lms.tools.creditcardpayment.utils.CreditcardPaymentValidator;

public abstract class Fee extends Amount {
	private String validateInputErrMsg;
	public Fee(CreditcardPaymentValidator creditcardPaymentValidator,String  validateInputErrMsg) {
		        this.creditcardPaymentValidator=creditcardPaymentValidator;
		        this. validateInputErrMsg= validateInputErrMsg;
	}
	CreditcardPaymentValidator creditcardPaymentValidator;
	public String validateInputAmount(String amountEntered){
                     String errMsg="";//creditcardPaymentValidator.validateMoney(amountEntered);
                     if(!errMsg.isEmpty()){
                            	 errMsg= validateInputErrMsg+errMsg;
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
                   this.amountEntered = amountEntered;
    }
	 public  double getAmountToBePaid(){
         return convertInputToDouble(amountEntered);
     }
	
}
