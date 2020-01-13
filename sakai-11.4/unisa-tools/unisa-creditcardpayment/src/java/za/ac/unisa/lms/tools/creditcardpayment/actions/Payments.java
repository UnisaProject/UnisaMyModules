package za.ac.unisa.lms.tools.creditcardpayment.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionMapping;

import za.ac.unisa.lms.tools.creditcardpayment.forms.CreditCardPaymentForm;
import za.ac.unisa.lms.tools.creditcardpayment.utils.CreditcardPaymentValidator;

public class Payments{
	
	public  Payments( CreditcardPaymentValidator  creditcardPaymentValidator){
		this.creditcardPaymentValidator=creditcardPaymentValidator;
		
	}
	CreditcardPaymentValidator  creditcardPaymentValidator;
    public String validateTpPaymentInfo(CreditCardPaymentForm creditForm,ActionMapping mapping,HttpServletRequest request, HttpServletResponse response ) throws Exception{
		
		Double totalamount = new Double(0);
		 // student nr
		String stuNumErrMsg=creditcardPaymentValidator.checkStudentNr(creditForm.getStudent().getStudentNumber());
		if(!stuNumErrMsg.equals("")){
			return stuNumErrMsg;
		}
		// email addr
		String emailErrorMsg=creditcardPaymentValidator.validateEmail(creditForm);
		if(!emailErrorMsg.equals("")){
			return emailErrorMsg;
		}
		// study fee amount
		if("".equals(creditForm.getStudyFeeAmountInput().trim())){
			return "Study fee amount you wish to pay is invalid.";
		}
		// credit card nr
		String creditcardNumErrorMsg=creditcardPaymentValidator.validateCredicardNum(creditForm);
		if(!creditcardNumErrorMsg.equals("")){
			return creditcardNumErrorMsg;
		}
		
		//  card holders name
		String creditcardHolderNameErrorMsg=creditcardPaymentValidator.validateCreditcardHolderName(creditForm);
		if(!creditcardHolderNameErrorMsg.equals("")){
			return creditcardHolderNameErrorMsg;
		}
		// cvv number
		String cvvNumErrorMsg= creditcardPaymentValidator.validateCvvNum(creditForm);
		if(!cvvNumErrorMsg.equals("")){
			return cvvNumErrorMsg;
		}// total cc amount
		if("".equals(creditForm.getCcTotalAmountInput().trim())){
			return "Invalid total amount being paid.";
		}
		
		
		String err = creditcardPaymentValidator.validateMoney(creditForm.getStudyFeeAmountInput(),0);
		if (!"".equals(err)){
			return "Study fee amount you wish to pay"+err;
		}else{
			// convert input study fee to double
			creditForm.setStudyFeeAmount(Double.parseDouble(creditForm.getStudyFeeAmountInput()));
		}
		String libFeeErr = creditcardPaymentValidator.validateMoney(creditForm.getLibraryFineFeeAmountInput(),0);
		if (!"".equals(libFeeErr)){
			return "Library fine amount you wish to pay "+libFeeErr;
		}else{
			// convert input  double
			  creditForm.setLibraryFineFeeAmount(Double.parseDouble(creditForm.getLibraryFineFeeAmountInput()));
		}
		//Study fee amount larger than minimum required for registration
		/*if (creditForm.getMinimumStudyFee()>0){
			if (creditForm.getStudyFeeAmount()<creditForm.getMinimumStudyFee()){
				NumberFormat formatter = new DecimalFormat("#0.00");
				return "Study fee amount you wish to pay can not be less than R" + formatter.format(creditForm.getMinimumStudyFee());
			}
		}**/
			
		err = creditcardPaymentValidator.validateMoney(creditForm.getCcTotalAmountInput(),1);
		if (!"".equals(err)){
			return "Total amount being paid"+err;
		}else{
			// convert input study fee to double
			creditForm.setCcTotalAmount(Double.parseDouble(creditForm.getCcTotalAmountInput()));
		}
//		 student indicated that he/she wants to cancel the library fee
		if("Y".equalsIgnoreCase(creditForm.getCancelSmartCard())){
			creditForm.setLibraryFeeForStudent(Double.parseDouble("0"));
		}else{
			creditForm.setLibraryFeeForStudent(creditForm.getLibraryFeeAmount());
		}
		// Check that fee totals match up
		totalamount = creditForm.getLibraryFeeForStudent() + creditForm.getMatricFeeForStudent() + creditForm.getStudyFeeAmount()+creditForm.getLibraryFineFeeAmount();
		if (totalamount != creditForm.getCcTotalAmount()){
			if( creditForm.getLibraryFeeForStudent() >0 && creditForm.getMatricFeeForStudent()>0 && creditForm.getStudyFeeAmount()>0 &&+creditForm.getLibraryFineFeeAmount()>0){
				return "Total amount being paid doesn't match total of library access, matriculation board , study fees  and library fine.";
			}else if( creditForm.getLibraryFeeForStudent() >0 && creditForm.getMatricFeeForStudent()>0 && creditForm.getStudyFeeAmount()>0 ){
					return "Total amount being paid doesn't match total of library access, matriculation board and study fees.";
			}else  if( creditForm.getLibraryFeeForStudent() >0 && creditForm.getMatricFeeForStudent()>0  &&+creditForm.getLibraryFineFeeAmount()>0){
						return "Total amount being paid doesn't match total of library access, matriculation board   and library fine.";
			}else if( creditForm.getLibraryFeeForStudent() >0  && creditForm.getStudyFeeAmount()>0 &&+creditForm.getLibraryFineFeeAmount()>0){
							return "Total amount being paid doesn't match total of library access, study fees  and library fine.";
			}else if( creditForm.getMatricFeeForStudent()>0 && creditForm.getStudyFeeAmount()>0 &&+creditForm.getLibraryFineFeeAmount()>0){
								return "Total amount being paid doesn't match total of matriculation board , study fees  and library fine.";
			}else if(creditForm.getLibraryFeeForStudent()>0 && creditForm.getStudyFeeAmount()>0){
				                return "Total amount being paid doesn't match total of library access  and study fees .";
			}else if(creditForm.getMatricFeeForStudent()>0 && creditForm.getStudyFeeAmount()>0){
				                return "Total amount being paid doesn't match total of matriculation board   and study fees .";
			}else if(creditForm.getLibraryFeeForStudent()>0 && creditForm.getStudyFeeAmount()>0){
				                return "Total amount being paid doesn't match total of library access  and study fees .";
			}else if(creditForm.getLibraryFineFeeAmount()>0 && creditForm.getStudyFeeAmount()>0){
				                return "Total amount being paid doesn't match total of library fine   and study fees .";
			}else if(creditForm.getLibraryFineFeeAmount()>0 &&+creditForm.getMatricFeeForStudent()>0){
					            return "Total amount being paid doesn't match total of matriculation  and library fine .";
		    }else if(creditForm.getLibraryFeeForStudent()>0 && creditForm.getMatricFeeForStudent()>0){
						        return "Total amount being paid doesn't match total of library access  and matriculation board  fees .";
			}else if(creditForm.getMatricFeeForStudent()>0 && creditForm.getStudyFeeAmount()>0){
				                return "Total amount being paid doesn't match total of matriculation board and study fees .";
			}else if(creditForm.getMatricFeeForStudent()>0 && creditForm.getLibraryFeeForStudent()>0){
					            return "Total amount being paid doesn't match total of matriculation board and library access .";
			}else if(creditForm.getStudyFeeAmount()>0){
				                return "Total amount being paid doesn't match total of study fees .";
			}else if(creditForm.getLibraryFineFeeAmount()>0){
				                return "Total amount being paid doesn't match total of Library fine .";
			}else if(creditForm.getMatricFeeForStudent()>0){
				                return "Total amount being paid doesn't match total of matriculation board fee.";
			}else if(creditForm.getLibraryFeeForStudent()>0){
				                return "Total amount being paid doesn't match total of library access .";
			}
		}
	    return "";
	} 

 public String validatenNonTpPaymentInfo(CreditCardPaymentForm creditForm,ActionMapping mapping,HttpServletRequest request, HttpServletResponse response ) throws Exception{
		
		Double totalamount = new Double(0);
		 // student nr
		String stuNumErrMsg=creditcardPaymentValidator.checkStudentNr(creditForm.getStudent().getStudentNumber());
		if(!stuNumErrMsg.equals("")){
			return stuNumErrMsg;
		}
		// email addr
		String emailErrorMsg=creditcardPaymentValidator.validateEmail(creditForm);
		if(!emailErrorMsg.equals("")){
			return emailErrorMsg;
		}
		// Add library fee to big total if checked
    	Double totalAmount = new Double(0);
		if ("on".equalsIgnoreCase(creditForm.getPayLibraryFee())){
			totalAmount = totalAmount + creditForm.getLibraryFee();
		}
		// Add MR fee to big total if checked
		if ("on".equalsIgnoreCase(creditForm.getPayMatricFirstAppFee())){
			totalAmount = totalAmount + creditForm.getMatricFirstAppFee();
		}
		// Add 3GDataBundle fee to big total if checked
		if ("on".equalsIgnoreCase(creditForm.getPayThreeGDataBundleFee())){
			totalAmount = totalAmount + creditForm.getThreeGDataBundleFee();
		}
		// study fee amount
		if("".equals(creditForm.getStudyFeeAmountInput().trim())){
			return "Study fee amount you wish to pay is invalid.";
		}
		String err = creditcardPaymentValidator.validateMoney(creditForm.getStudyFeeAmountInput(),0);
		if (!"".equals(err)){
			return "Study fee amount you wish to pay"+err;
		}else{
			// convert input study fee to double
			creditForm.setStudyFeeAmount(Double.parseDouble(creditForm.getStudyFeeAmountInput()));
		}
		// credit card nr
		String creditcardNumErrorMsg=creditcardPaymentValidator.validateCredicardNum(creditForm);
		if(!creditcardNumErrorMsg.equals("")){
			return creditcardNumErrorMsg;
		}
		
		//  card holders name
		String creditcardHolderNameErrorMsg=creditcardPaymentValidator.validateCreditcardHolderName(creditForm);
		if(!creditcardHolderNameErrorMsg.equals("")){
			return creditcardHolderNameErrorMsg;
		}
		// cvv number
		String cvvNumErrorMsg= creditcardPaymentValidator.validateCvvNum(creditForm);
		if(!cvvNumErrorMsg.equals("")){
			return cvvNumErrorMsg;
		}// total cc amount
		if("".equals(creditForm.getCcTotalAmountInput().trim())){
			return "Invalid total amount being paid.";
		}
			String libFeeErr = creditcardPaymentValidator.validateMoney(creditForm.getLibraryFineFeeAmountInput(),0);
		if (!"".equals(libFeeErr)){
			return "Library fine amount you wish to pay "+libFeeErr;
		}else{
			// convert input  double
			  creditForm.setLibraryFineFeeAmount(Double.parseDouble(creditForm.getLibraryFineFeeAmountInput()));
		}
				
		err = creditcardPaymentValidator.validateMoney(creditForm.getCcTotalAmountInput(),1);
		if (!"".equals(err)){
			return "Total amount being paid"+err;
		}else{
			// convert input study fee to double
			creditForm.setCcTotalAmount(Double.parseDouble(creditForm.getCcTotalAmountInput()));
		}
 	// Check that fee totals match up
		totalamount+=(creditForm.getStudyFeeAmount()+creditForm.getLibraryFineFeeAmount());
		if (totalamount != creditForm.getCcTotalAmount()){
			TotalAmountValidatorNonTp totalAmountValidatorNonTp=new TotalAmountValidatorNonTp();
			String errTot=totalAmountValidatorNonTp.verifyEnteredvsCalculatedTotals(totalAmount, creditForm);
			if(!errTot.isEmpty()){
				//return errTot;
			}
		}
		return "";
	}
}
