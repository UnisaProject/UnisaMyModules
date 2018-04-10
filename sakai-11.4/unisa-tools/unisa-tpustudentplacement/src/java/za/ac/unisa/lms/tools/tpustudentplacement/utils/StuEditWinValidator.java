package za.ac.unisa.lms.tools.tpustudentplacement.utils;

import org.apache.struts.action.ActionMessages;

public class StuEditWinValidator {
	 
	public void validateSelectedPlacement(ActionMessages messages, String[] indexArr){
                   IndexArrayValidator indexArrayValidator=new IndexArrayValidator();
                   String errorMsg="Please select a placement to view or edit";
                   indexArrayValidator.validateSelectedIndexForEmptiness(indexArr, messages, errorMsg);
                   if( !messages.isEmpty()){
       	                 return;
                   }
                   errorMsg="Please select only one placement to view or edit";
                   indexArrayValidator.validateSelectedIndexForTotSecetions(indexArr, messages, errorMsg);
      }

}
