package za.ac.unisa.lms.tools.assmarkerreallocation.actions;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import za.ac.unisa.lms.tools.assmarkerreallocation.forms.MarkerDetailRecord;
public class Utilities {
	public static boolean isInteger(String stringValue) {
		try
		{
			Integer i = Integer.parseInt(stringValue);
			return true;
		}	
		catch(NumberFormatException e)
		{}
		return false;
	}
	        public static void addErrorMessage(ActionMessages messages,String errMsg){
                                   messages.add(ActionMessages.GLOBAL_MESSAGE,
	                                  new ActionMessage("message.generalmessage", errMsg));
	        }
            public static void addErrorMessages(ActionMessages messages,List errMsgList){
                        Iterator iter=errMsgList.iterator();
                        while(iter.hasNext()){
                                   messages.add(ActionMessages.GLOBAL_MESSAGE,
                                    new ActionMessage("message.generalmessage",iter.next().toString()));
                        }    
            }
            public static String replaceNull(Object object){
                        String stringValue="";
                        if (object==null){			
                        }else{
                               stringValue=object.toString();
                        }			
                     return stringValue;
           }
            public static void  sortList(List listMarkerDetail){
         	   Collections.sort(listMarkerDetail, new Comparator() {
                   	public int compare (Object o1, Object o2){
         				MarkerDetailRecord m1 = (MarkerDetailRecord) o1;
         				MarkerDetailRecord m2 = (MarkerDetailRecord) o2;
         				if (Integer.parseInt(m1.getMarker().getMarkPercentage()) < Integer.parseInt(m2.getMarker().getMarkPercentage())){
         					return 1;
         				} else if (Integer.parseInt(m1.getMarker().getMarkPercentage()) == Integer.parseInt(m2.getMarker().getMarkPercentage())){
         					return 0;
         				}else {
         					return -1;
         				}
         			}
         		});
         		
         		Collections.sort(listMarkerDetail, new Comparator() {
         		
         		public int compare (Object o1, Object o2){
         			MarkerDetailRecord m1 = (MarkerDetailRecord) o1;
         			MarkerDetailRecord m2 = (MarkerDetailRecord) o2;
         			if (Integer.parseInt(m1.getMarker().getMarkPercentage()) == Integer.parseInt(m2.getMarker().getMarkPercentage())){
         				return m1.getMarker().getPerson().getNameReverse().compareToIgnoreCase(m2.getMarker().getPerson().getNameReverse());
         			}else{
         				return -1;
         			}
         			
         		}
         	});
          }
          
			
}
