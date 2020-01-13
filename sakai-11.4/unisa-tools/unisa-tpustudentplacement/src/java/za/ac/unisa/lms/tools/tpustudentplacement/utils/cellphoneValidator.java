package za.ac.unisa.lms.tools.tpustudentplacement.utils;

public class cellphoneValidator {

	public boolean validCellNumber(String cellNumber){
		              if((cellNumber==null)||(cellNumber.trim().equals(""))){
		            	  return false;
		              }
		              if(cellNumber.trim().length()>=12){
		                  if(cellNumber.charAt(0)=='+'){
		                   	    return checkNumeric(cellNumber,1);
		                  }else{
		                	    return false;
		                  }
		              }else{
		            	  return false;
		              }
	}
	private boolean  checkNumeric(String numStr,int startpos){
		               boolean strOfNumerics=true;
		             for(int x=startpos;x<numStr.length();x++){
		                 if(!checkNumericChar(numStr.charAt(x))){
		                	 strOfNumerics=false;
		                	 break;
		                 }
		             }
		             return strOfNumerics;
	}
	public boolean checkNumericChar(char chr){
		             if(Character.isDigit(chr)){
		            	 return true;
		             }else{
		            	 return false;
		             }
	}
}
