package za.ac.unisa.lms.tools.creditcardpayment.actions;

 
import za.ac.unisa.lms.tools.creditcardpayment.forms.CreditCardPaymentForm;
import za.ac.unisa.lms.tools.creditcardpayment.utils.CreditcardPaymentValidator;

public class MatricFee extends Fee{

	public  MatricFee(CreditcardPaymentValidator creditcardPaymentValidator) {
                  super(creditcardPaymentValidator,"Matriculation Exception Fee amount you wish to pay ");
    }
}
