package za.ac.unisa.lms.tools.tpustudentplacement.utils;
import org.apache.struts.action.ActionMessages;
public class SupervisorSelectionValidator {
	
			    private static final String noSelectionMessage="Please select a supervisor";
			    private static final String tooManySelectionsMessage="Please select only one supervisor";
			    private static final String messageForAddView= "Please select a supervisor to view or edit";
				private static final String secMessageForAddView= "Please select only one supervisor to view or edit";
	    		
                InfoMessagesUtil infoMessagesUtil;
	            public SupervisorSelectionValidator(){
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
	           public void validateIndexArrForSupervSelection(String[] indexArr,ActionMessages messages){ 
	        	                   String msg=validateSelectedIndexForEmptiness(indexArr,noSelectionMessage);
	        	                   infoMessagesUtil.addMessages(messages,msg );
		                           if(messages.isEmpty()){
		                        	   msg=validateSelectedIndexForTotSeletions(indexArr,tooManySelectionsMessage);
		                        	   infoMessagesUtil.addMessages(messages,msg);
		                           }
		                          
               }
	           public void validateIndexArrForSupervAddOrView(String[] indexArr,ActionMessages messages){ 
	        	                   String msg=validateSelectedIndexForEmptiness(indexArr,messageForAddView);
                                   infoMessagesUtil.addMessages(messages,msg );
                                   if(messages.isEmpty()){
                	                      msg=validateSelectedIndexForTotSeletions(indexArr,secMessageForAddView);
                	                      infoMessagesUtil.addMessages(messages,msg);
                                   }
              }
}
//