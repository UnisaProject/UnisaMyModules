package za.ac.unisa.lms.tools.creditcardpayment.actions;
import za.ac.unisa.lms.tools.creditcardpayment.forms.CreditCardPaymentForm;

public class TotalAmountValidatorNonTp {
	
	
	CalculatedTotalNonTpValidator    calculatedTotalNonTpValidator;
	public  TotalAmountValidatorNonTp(){
		           calculatedTotalNonTpValidator=new CalculatedTotalNonTpValidator();
	}
	
		private String MatricBoardBeingPaid(CreditCardPaymentForm creditForm){
		             if(creditForm.isCanChooseMatric()){
                            if("on".equalsIgnoreCase(creditForm.getPayMatricFirstAppFee())){
       	                         return " matriculation ";
                             }else{
       	                            return "";
                             }
		             }else{
		            	 return "";
		             }
   }

	private String check3GBeingPaid(CreditCardPaymentForm creditForm){
		        if(creditForm.isCanChooseThreeGDataBundle()){
	                  if("on".equalsIgnoreCase(creditForm.getPayThreeGDataBundleFee())){
	            	         return " 3G Data bundle ";
	                  }else{
	            	          return "";
	                  }
		         }else{
       	                  return "";
                 }
    }
    private String checkLibraryCardBeingPaid(CreditCardPaymentForm creditForm){
    	            if(creditForm.isCanChooseLibraryCard()){
                           if("on".equalsIgnoreCase(creditForm.getPayLibraryFee())){
	                           return " library access ";
                           }else{
	                              return "";
                           }
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
			      String  errorString= "The value of 'Total amount being paid' doesn't match total of";
	       int intitalLenth=errorString.length();
	        String threegtempStr=check3GBeingPaid(creditForm);
		    errorString+=threegtempStr;
		    String libtempStr=checkLibraryCardBeingPaid(creditForm);
		    if(!libtempStr.trim().equals("")){
		    	         if(intitalLenth<errorString.length()){
		    	                  libtempStr=" plus "+libtempStr;
		    	         }
		    }
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
	   return errorString;
   }
   public String validateCalculatedTotal(double totalAmount,CreditCardPaymentForm creditForm){
	                return  calculatedTotalNonTpValidator.validateCalculatedTotal(totalAmount, creditForm);
   }
 	

}
