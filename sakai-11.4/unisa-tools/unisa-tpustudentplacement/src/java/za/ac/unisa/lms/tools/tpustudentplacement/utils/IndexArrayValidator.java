package za.ac.unisa.lms.tools.tpustudentplacement.utils;

import org.apache.struts.action.ActionMessages;
public class IndexArrayValidator {
	
		
		InfoMessagesUtil infoMessagesUtil;
		public IndexArrayValidator(){
			            infoMessagesUtil=new  InfoMessagesUtil();
		}
		private String  validateSelectedIndexForEmptiness(String[] indexArr,String errorMsg){
	                      String msg="";
	                      if (indexArr==null ||
	    		                   indexArr.length==0){
	                                     msg= errorMsg;
	                      }
	                      return msg;
	    }
		private String  validateSelectedIndexForTotSelections(String[] indexArr,String errorMsg){
	                        String msg="";
	                        if (indexArr==null ||indexArr.length>1){
	                                msg= errorMsg;
	                        }
	                        return msg;
	    }
		protected void validateSelectedIndexForEmptiness(String[] indexArr,ActionMessages messages,String   errorMsg){ 
                             String msg=validateSelectedIndexForEmptiness(indexArr,errorMsg);
                             infoMessagesUtil.addMessages(messages,msg);
        }
		protected  void validateSelectedIndexForTotSecetions(String[] indexArr,ActionMessages messages,String  errorMsg){ 
                              String msg=validateSelectedIndexForTotSelections(indexArr,errorMsg);
                              infoMessagesUtil.addMessages(messages,msg);
		}
}
