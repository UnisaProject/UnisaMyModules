package za.ac.unisa.lms.tools.tpustudentplacement.utils;

import org.apache.struts.action.ActionMessages;

public class SelectionValidator {

     InfoMessagesUtil infoMessagesUtil;
    public SelectionValidator(){
                  infoMessagesUtil=new  InfoMessagesUtil();
    }

   public String  validateSelectedIndexForEmptiness(String[] indexArr,String message) {
                        String msg="";
                        if (indexArr==null ||
                                 indexArr.length==0){
                                 msg= message;
                        }
                        return msg;
   }
   public String  validateSelectedIndexForTotSeletions(String[] indexArr,String message) {
                       String msg="";
                       if (indexArr.length>1){
                              msg= message;
                       }
                       return msg;
   }
   public void validateIndexArrForSelection(String[] indexArr,ActionMessages messages,String noSelectionMessage,String tooManySelectionsMessage){ 
	                   String msg=validateSelectedIndexForEmptiness(indexArr,noSelectionMessage);
	                   infoMessagesUtil.addMessages(messages,msg );
                       if(messages.isEmpty()){
                    	   msg=validateSelectedIndexForTotSeletions(indexArr,tooManySelectionsMessage);
                    	   infoMessagesUtil.addMessages(messages,msg);
                       }
                      
   }
   public void validateIndexArrForAddOrView(String[] indexArr,ActionMessages messages,String messageForAddView,String secMessageForAddView){ 
	                   String msg=validateSelectedIndexForEmptiness(indexArr,messageForAddView);
                       infoMessagesUtil.addMessages(messages,msg );
                       if(messages.isEmpty()){
    	                      msg=validateSelectedIndexForTotSeletions(indexArr,secMessageForAddView);
    	                      infoMessagesUtil.addMessages(messages,msg);
                       }
  }

}
