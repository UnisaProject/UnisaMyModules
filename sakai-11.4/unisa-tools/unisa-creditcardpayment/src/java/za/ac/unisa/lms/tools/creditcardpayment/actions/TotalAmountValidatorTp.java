package za.ac.unisa.lms.tools.creditcardpayment.actions;

import za.ac.unisa.lms.tools.creditcardpayment.forms.CreditCardPaymentForm;

public class TotalAmountValidatorTp {

	CalculatedTotalTpValidator    calculatedTotalTpValidator;
	public  TotalAmountValidatorTp(){
		           calculatedTotalTpValidator=new CalculatedTotalTpValidator();
	}
	
	
	
      private String MatricBoardBeingPaid(CreditCardPaymentForm creditForm){
			             if(creditForm.getMatricFeeForStudent()>0){
	                                 return " matriculation ";
	                     }else{
			            	   return "";
			             }
	    }
       private String checkLibraryCardBeingPaid(CreditCardPaymentForm creditForm){
	    	            if(creditForm.getLibraryFeeForStudent()>0){
	                                return " library access ";
	                      }else{
	   	                        return "";
	                    }
	   }
	   private String checkStudyFeesBeingPaid(CreditCardPaymentForm creditForm){
		                   if(creditForm.getStudyFeeAmount()>0){
	                              return " study fees ";
	                       }else{
	                              return "";
	                       }
	   }
	   
	   private String checkLibraryFineBeingPaid(CreditCardPaymentForm creditForm){
	       if(creditForm.getLibraryFineFeeAmount()>0){
	              return " library fine ";
	       }else{
	              return "";
	       }
	   }
	   public String verifyEnteredvsCalculatedTotals(double totalAmount,CreditCardPaymentForm creditForm){
		   
		      if(totalAmount==0){
		    	  return "You have not selected an item  to pay";
		      }
		      String  errorString= "The value of 'Total amount being paid' doesn't match total of ";
		      if((totalAmount!=creditForm.getCcTotalAmount())){
		       int intitalLenth=errorString.length();
		        String libtempStr=checkLibraryCardBeingPaid(creditForm);
			    errorString+=libtempStr;
			   String matricBoardTempStr=MatricBoardBeingPaid(creditForm);
			    if(!matricBoardTempStr.trim().equals("")){
			    	        if(intitalLenth<errorString.length()){
			    	           	matricBoardTempStr=" plus "+matricBoardTempStr;
				             }
			    }
			    errorString+=matricBoardTempStr;
			   String studFeetempStr=checkStudyFeesBeingPaid(creditForm);
			    if(!studFeetempStr.trim().equals("")){
			    	    if(intitalLenth<errorString.length()){
			    		       studFeetempStr=" plus "+studFeetempStr;
			    	     }
			    }
			    errorString+=studFeetempStr;
			    String libfinetempStr=checkLibraryFineBeingPaid(creditForm);
			    if(!libfinetempStr.trim().equals("")){
			    	      if(intitalLenth<errorString.length()){
			    		     libfinetempStr=" plus "+libfinetempStr;
			    	      }
			    }
			    errorString+=libfinetempStr;
		      }else{
		    	  return "";
		      }
		   return errorString;
	   }
	   
	   public String validateCalculatedTotal(double totalAmount,CreditCardPaymentForm creditForm){
           return  calculatedTotalTpValidator.validateCalculatedTotal(totalAmount, creditForm);
}


}