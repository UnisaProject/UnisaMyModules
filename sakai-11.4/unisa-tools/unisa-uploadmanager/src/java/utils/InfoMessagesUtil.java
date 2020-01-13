package utils;

import java.util.Iterator;
import java.util.List;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class InfoMessagesUtil {
	
	       public static void addMessages(ActionMessages messages,String message){
		              if((message!=null)&&
		            		  (!message.equals("")))
		               {
		            	  if (message.split(":").length > 1) {
                            messages.add( ActionMessages.GLOBAL_MESSAGE,
                                 new ActionMessage(message.split(":")[1],message.split(":")[0]));
		            	  } else {
		            		    messages.add( ActionMessages.GLOBAL_MESSAGE,
		                                 new ActionMessage("errors.message",message));
		            	  }
		              }
           } 
	       public static  ActionMessages addMessages(String message){
		                          ActionMessages messages=new ActionMessages(); 
                                  messages.add( ActionMessages.GLOBAL_MESSAGE,
                                       new ActionMessage("errors.message",message));
                              return  messages;
           }
	       public static  ActionMessages addMessageList(List messageList){
                                ActionMessages messages=new ActionMessages(); 
                                 Iterator i=messageList.iterator();
                                 while(i.hasNext()){
                                	 String message=i.next().toString();
                                     messages.add( ActionMessages.GLOBAL_MESSAGE,
                                     new ActionMessage("errors.message",message));
                                 }
                         return  messages;
            }
	       public static  void addMessageList(List messageList,ActionMessages messages){
                               Iterator i=messageList.iterator();
                               while(i.hasNext()){
          	                         String message=i.next().toString();
                                     messages.add( ActionMessages.GLOBAL_MESSAGE,
                                           new ActionMessage("errors.message",message));
                               }
            }
	       public static String fileCopyErrorMsg(){
	    	                     return "There was a problem writing/getting files form the file server\n+" +
	    	    		                " either  the file is corrupted or the application does not have permision to upload files\n"+
	   	                                " please contact the people responsible for the study material's fileserver";
	       }
}
