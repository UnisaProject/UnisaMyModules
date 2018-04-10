package za.ac.unisa.lms.tools.creditcardpayment.actions;
import za.ac.unisa.lms.tools.creditcardpayment.forms.CreditCardPaymentForm;
public class CalculatedTotalNonTpValidator {
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
          				          errorMsg=check3GCanBePaid(creditForm);
          				          if(!errorMsg.isEmpty()){
       				        	       return errorMsg;
       				              }
          			}
          			return "You have no liability to the university";
          		}
          		return "";
          		}
          	private String checkLibraryFeeCanBePaid(CreditCardPaymentForm creditForm){
          		           if ((creditForm.isCanChooseLibraryCard()) && (creditForm.isCanChooseMatric())&& (creditForm.getLibraryFineFee()>0)&&
          		        		   (creditForm.getBalanceAmount()>0)&&creditForm.isCanChooseThreeGDataBundle()){
          		                 return  "No library access, matriculation ,study fees , 3G Fee orlibrary fine indicated ";
          	               } 
          		           if ((creditForm.isCanChooseLibraryCard()) && (creditForm.getLibraryFineFee()>0)&& (creditForm.getBalanceAmount()>0)
          		        		   &&creditForm.isCanChooseThreeGDataBundle()){
          		                 return  "No library access ,study fees ,3G fee or library fine indicated ";
          	               }
          		           if ((creditForm.isCanChooseLibraryCard()) && (creditForm.isCanChooseMatric())&& (creditForm.getBalanceAmount()>0)
          		        		   &&creditForm.isCanChooseThreeGDataBundle()){
          		                 return  "No library access, matriculation ,3G Fee  or study fees  indicated ";
          	               } 
          		           if ((creditForm.isCanChooseLibraryCard()) && (creditForm.isCanChooseMatric())&& (creditForm.getLibraryFineFee()>0)&&creditForm.isCanChooseThreeGDataBundle()){
          		                 return  "No library access, matriculation,3G fee  or library fine indicated ";
          	               } 
          		         if ((creditForm.isCanChooseLibraryCard()) && (creditForm.isCanChooseMatric())&& (creditForm.getLibraryFineFee()>0)&&(creditForm.getBalanceAmount()>0)){
      		                 return  "No library access, matriculation,study fees  or library fine indicated ";
      	                  } 
      		             if ((creditForm.isCanChooseLibraryCard()) && (creditForm.getBalanceAmount()>0)&&creditForm.isCanChooseThreeGDataBundle()){
          		                 return  "No library access,3G fee or study fees  indicated ";
          	               } 
          		           if ((creditForm.isCanChooseLibraryCard()) && (creditForm.getLibraryFineFee()>0) &&creditForm.isCanChooseThreeGDataBundle()){
          		                 return  "No library access,3G fee  or library fine indicated ";
          	               } 
          		           if ((creditForm.isCanChooseLibraryCard()) && (creditForm.isCanChooseMatric())&&creditForm.isCanChooseThreeGDataBundle()){
          		                 return  "No library access,3G fee or  matriculation  indicated ";
          	               } 
          		         if ((creditForm.isCanChooseLibraryCard()) &&(creditForm.getBalanceAmount()>0)&&creditForm.isCanChooseThreeGDataBundle()){
      		                 return  "No library access,3G fee or  matriculation  indicated ";
      	                  } 
          		        if ((creditForm.isCanChooseLibraryCard()) &&(creditForm.getBalanceAmount()>0)&& (creditForm.isCanChooseMatric())){
    		                 return  "No library access,study fees or  matriculation  indicated ";
    	                  } 
          		         if ((creditForm.isCanChooseLibraryCard()) &&(creditForm.isCanChooseMatric())&& (creditForm.isCanChooseMatric())){
		                     return  "No library access,matriculation or library fine indicated ";
	                     } 
		                 if ((creditForm.isCanChooseLibraryCard()) &&(creditForm.getBalanceAmount()>0)&&(creditForm.getLibraryFineFee()>0)){
          		                 return  "No library access ,study fees, or library fine  indicated ";
          	               } 
		                 if ((creditForm.isCanChooseLibraryCard()) &&(creditForm.isCanChooseMatric())&&(creditForm.getLibraryFineFee()>0)){
      		                 return  "No library access ,matriculation or library fine  indicated ";
      	               }
		                 if ((creditForm.isCanChooseLibraryCard()) &&(creditForm.isCanChooseMatric())){
      		                 return  "No library access or matriculation fee  indicated ";
      	               }
		                 if ((creditForm.isCanChooseLibraryCard()) &&(creditForm.getLibraryFineFee()>0)){
      		                 return  "No library access or library fine  indicated ";
      	               }
		                 if ((creditForm.isCanChooseLibraryCard()) &&(creditForm.getBalanceAmount()>0)){
      		                 return  "No library access or study fees";
      	               } 
		                 if ((creditForm.isCanChooseLibraryCard()) &&creditForm.isCanChooseThreeGDataBundle()){
      		                 return  "No library access or 3G fee  indicated ";
      	               } 
		                 if ((creditForm.isCanChooseLibraryCard())){
      		                 return  "No library access  indicated ";
      	               } 
          		         return "";
          	}
              private String checkMatricFeeCanBePaid(CreditCardPaymentForm creditForm){
              	 if ((creditForm.isCanChooseMatric())&& (creditForm.getLibraryFineFee()>0)&& (creditForm.getBalanceAmount()>0)&&creditForm.isCanChooseThreeGDataBundle()){
                           return  "No  matriculation ,study fees,3G fee or library fine indicated ";
                 } 
              	 if ((creditForm.isCanChooseMatric())&& (creditForm.getLibraryFineFee()>0)&& (creditForm.getBalanceAmount()>0)){
                     return  "No  matriculation ,study fees or library fine indicated ";
                 } 
              	 if ((creditForm.isCanChooseMatric())&& (creditForm.getLibraryFineFee()>0)&&creditForm.isCanChooseThreeGDataBundle()){
                     return  "No  matriculation ,3G fee or library fine indicated ";
                  } 
              	 if ((creditForm.isCanChooseMatric())&& (creditForm.getBalanceAmount()>0)&&creditForm.isCanChooseThreeGDataBundle()){
                     return  "No  matriculation ,study fees or 3G fee indicated ";
                  } 
          
                 if ((creditForm.isCanChooseMatric()) && (creditForm.getLibraryFineFee()>0)&&creditForm.isCanChooseThreeGDataBundle()){
                       return  "No  matriculation,3G fee  or library fine indicated ";
                 }
                if ( (creditForm.isCanChooseMatric())&& (creditForm.getBalanceAmount()>0)&&creditForm.isCanChooseThreeGDataBundle()){
                       return  "No  matriculation,3G fee  or study fees ";
                 } 
                if ((creditForm.isCanChooseMatric())&& (creditForm.getLibraryFineFee()>0)){
                    return  "No  matriculation  or library fine indicated ";
                } 
                if ((creditForm.isCanChooseMatric())&& (creditForm.getBalanceAmount()>0)){
                    return  "No  matriculation or study fees  indicated ";
                } 
                if ((creditForm.isCanChooseMatric())&&creditForm.isCanChooseThreeGDataBundle()){
                    return  "No  matriculation  or 3G fee  indicated ";
                 } 
                 
                if ((creditForm.isCanChooseMatric())){
                    return  "No  matriculation  fee indicated ";
                  } 
                return "";
              }
              private String checkLibraryFineCanBePaid(CreditCardPaymentForm creditForm){
              	  if ((creditForm.getLibraryFineFee()>0)&& (creditForm.getBalanceAmount()>0)&&creditForm.isCanChooseThreeGDataBundle()){
                       return  "No study fees,3G fee or library fine indicated ";
                   } 
              	  if ((creditForm.getLibraryFineFee()>0)&&creditForm.isCanChooseThreeGDataBundle()){
                          return  "No  library fine or 3G fee indicated ";
                  } 
              	if ((creditForm.getLibraryFineFee()>0)&& (creditForm.getBalanceAmount()>0)){
                    return  "No study fees or library fine indicated ";
                } 
              	if ((creditForm.getLibraryFineFee()>0)){
                    return  "library fine indicated ";
                } 
               return "";
             }
              private String checkStudyFeeCanBePaid(CreditCardPaymentForm creditForm){
           	   if ( (creditForm.getBalanceAmount()>0)&&creditForm.isCanChooseThreeGDataBundle()){
                      return  "No study fees or 3G fee  indicated ";
                  }
           	if ( (creditForm.getBalanceAmount()>0)){
                return  "No study fees  indicated ";
            }
           	   return "";
           	}
              private String check3GCanBePaid(CreditCardPaymentForm creditForm){
              	   if ( (creditForm.getBalanceAmount()>0)&&creditForm.isCanChooseThreeGDataBundle()){
                         return  "No study fees or 3G fee  indicated ";
                     }
              	if ( (creditForm.getBalanceAmount()>0)){
                   return  "No study fees  indicated ";
               }
              	   return "";
              	}
 }