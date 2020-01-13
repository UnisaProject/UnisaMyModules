package za.ac.unisa.lms.tools.tpustudentplacement.utils;
import org.apache.struts.action.ActionMessages;

public class PlacementValidator {
	PlacementUtilities util=new PlacementUtilities();
	InfoMessagesUtil infoMessagesUtil=new InfoMessagesUtil();
	
	public void validateAcademicYear(String year,ActionMessages messages){
		                     if (year==null || year.trim().equalsIgnoreCase("")){
		                    	 infoMessagesUtil.addMessages(messages,"Academic year is required.");
					         }else{
	                                   if (!util.isInteger(year.trim())){
	                                	   infoMessagesUtil.addMessages(messages,"Academic year must be numeric");
	                                   }
	                         }
     }
	public void validateStuNum(String stuNum,ActionMessages messages){
                    if (stuNum==null || stuNum.trim().equalsIgnoreCase("")){
                        	infoMessagesUtil.addMessages(messages,"Student number is required.");
                    }else{
	                        if (!util.isInteger(stuNum.trim())){
	                        	infoMessagesUtil.addMessages(messages,"Student number must be numeric");
	                        }
	                }
     }
     public void validateStuNumRegistered(String stuNum,ActionMessages messages){
                        if ((stuNum=="-1" )|| stuNum.trim().equalsIgnoreCase("-1")){
                            	infoMessagesUtil.addMessages(messages,"Student number is not  valid");
                        }
    }
}
