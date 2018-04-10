package za.ac.unisa.lms.tools.creditcardpayment.actions;
import za.ac.unisa.lms.tools.creditcardpayment.forms.CreditCardPaymentForm;
public class CalculatedTotalTpValidator {
	           
	  public String validateCalculatedTotal(double totalAmount,CreditCardPaymentForm creditForm){
          		String errorMsg="";
          		if (totalAmount == 0) {
          			if((creditForm.getMatricFeeForStudent()==0)&&(creditForm.getLibraryFeeForStudent()==0)&&
          					 (creditForm.getStudyFeeAmount()==0)&&(creditForm.getLibraryFineFeeAmount()==0)){
          				           errorMsg=checkLibraryFeeCanBePaid(creditForm);
          				           if(!errorMsg.isEmpty()){
          				        	   return errorMsg;
          				           }
          				           errorMsg=checkMatricFeeCanBePaid(creditForm);
          				           if(!errorMsg.isEmpty()){
          				        	   return errorMsg;
          				           }
          				           errorMsg=checkLibraryFineCanBePaid(creditForm);
          				           if(!errorMsg.isEmpty()){
          				        	   return errorMsg;
          				           }
          				           errorMsg=checkStudyFeeCanBePaid(creditForm);
          				           if(!errorMsg.isEmpty()){
          				        	   return errorMsg;
          				           }
          			}
          			return "You have no liability to the university";
          		}
          		return "Total amount to be paid cannot be zero";
          		}
          	private String checkLibraryFeeCanBePaid(CreditCardPaymentForm creditForm){
          		           if ((creditForm.getLibraryFeeAmount()>0) && (creditForm.getMatricFee()>0)&& (creditForm.getLibraryFineFee()>0)&& (creditForm.getBalanceAmount()>0)){
          		                 return  "No library access, matriculation ,study fees or library fine indicated ";
          	               } 
          		           if ((creditForm.getLibraryFeeAmount()>0) && (creditForm.getLibraryFineFee()>0)&& (creditForm.getBalanceAmount()>0)){
          		                 return  "No library access ,study fees or library fine indicated ";
          	               }
          		           if ((creditForm.getLibraryFeeAmount()>0) && (creditForm.getMatricFee()>0)&& (creditForm.getBalanceAmount()>0)){
          		                 return  "No library access, matriculation  or study fees  indicated ";
          	               } 
          		           if ((creditForm.getLibraryFeeAmount()>0) && (creditForm.getMatricFee()>0)&& (creditForm.getLibraryFineFee()>0)){
          		                 return  "No library access, matriculation  or library fine indicated ";
          	               } 
          		           if ((creditForm.getLibraryFeeAmount()>0) && (creditForm.getBalanceAmount()>0)){
          		                 return  "No library access or study fees  indicated ";
          	               } 
          		           if ((creditForm.getLibraryFeeAmount()>0) && (creditForm.getLibraryFineFee()>0) ){
          		                 return  "No library access  or library fine indicated ";
          	               } 
          		           if ((creditForm.getLibraryFeeAmount()>0) && (creditForm.getMatricFee()>0)){
          		                 return  "No library access or  matriculation  indicated ";
          	               } 
          		           if ((creditForm.getLibraryFeeAmount()>0)){
          		                 return  "No library access  indicated ";
          	               } 
          		         return "";
          	}
              private String checkMatricFeeCanBePaid(CreditCardPaymentForm creditForm){
              	 if ((creditForm.getMatricFee()>0)&& (creditForm.getLibraryFineFee()>0)&& (creditForm.getBalanceAmount()>0)){
                           return  "No  matriculation ,study fees or library fine indicated ";
                 } 
                 if ((creditForm.getMatricFee()>0) && (creditForm.getLibraryFineFee()>0)){
                       return  "No  matriculation  or library fine indicated ";
                 }
                if ( (creditForm.getMatricFee()>0)&& (creditForm.getBalanceAmount()>0)){
                       return  "No  matriculation  or study fees ";
                 } 
                if ((creditForm.getMatricFee()>0)){
                    return  "No  matriculation  fee indicated ";
                  } 
                return "";
              }
              private String checkLibraryFineCanBePaid(CreditCardPaymentForm creditForm){
              	  if ((creditForm.getLibraryFineFee()>0)&& (creditForm.getBalanceAmount()>0)){
                       return  "No study fees or library fine indicated ";
                   } 
              	  if ((creditForm.getLibraryFineFee()>0)){
                          return  "No  library fine indicated ";
                  } 
               return "";
             }
              private String checkStudyFeeCanBePaid(CreditCardPaymentForm creditForm){
           	   if ( creditForm.getBalanceAmount()>0){
                      return  "No study fees  indicated ";
                  }
           	   return "";
           	}
           	
}