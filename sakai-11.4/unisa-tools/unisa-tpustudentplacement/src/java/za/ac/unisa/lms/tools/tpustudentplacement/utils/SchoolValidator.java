package za.ac.unisa.lms.tools.tpustudentplacement.utils;

import org.apache.struts.action.ActionMessages;

public class SchoolValidator  extends IndexArrayValidator{
	
	private void validateSelectedIndexForEmptiness(String[] indexArr,ActionMessages messages){
		               String   errorMsg="Please select a school";
                       validateSelectedIndexForEmptiness(indexArr,messages,errorMsg);
    }
	private  void validateSelectedIndexForTotSecetions(String[] indexArr,ActionMessages messages){ 
		            String  errorMsg="Please select only one school";
		            validateSelectedIndexForTotSecetions(indexArr,messages,errorMsg);
    }
	public void validateSelection(String[] indexArr,ActionMessages messages){
		              validateSelectedIndexForEmptiness(indexArr,messages);
		              validateSelectedIndexForTotSecetions(indexArr,messages);
    }

}
