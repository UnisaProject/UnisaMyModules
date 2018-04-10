package za.ac.unisa.lms.tools.tpustudentplacement.utils;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import java.util.List;
import java.util.Iterator;

public class InfoMessagesUtil {
	
	       public void addMessages(ActionMessages messages,String message){
		              if((message!=null)&&
		            		  (!message.equals("")))
		               {
                            messages.add( ActionMessages.GLOBAL_MESSAGE,
                                 new ActionMessage("message.generalmessage",message));
		              }
           } 
	       public ActionMessages addMessages(String message){
		                          ActionMessages messages=new ActionMessages(); 
                                  messages.add( ActionMessages.GLOBAL_MESSAGE,
                                       new ActionMessage("message.generalmessage",message));
                              return  messages;
           }
	       public ActionMessages addMessageList(List messageList){
                                ActionMessages messages=new ActionMessages(); 
                                 Iterator i=messageList.iterator();
                                 while(i.hasNext()){
                                	 String message=i.next().toString();
                                     messages.add( ActionMessages.GLOBAL_MESSAGE,
                                     new ActionMessage("message.generalmessage",message));
                                 }
                         return  messages;
            }
	        public void addMessageList(List messageList,ActionMessages messages){
                               Iterator i=messageList.iterator();
                               while(i.hasNext()){
          	                         String message=i.next().toString();
                                     messages.add( ActionMessages.GLOBAL_MESSAGE,
                                           new ActionMessage("message.generalmessage",message));
                               }
            }
}
