package za.ac.unisa.lms.tools.tpustudentplacement.uiLayer;

import java.util.Iterator;
import java.util.List;

public class StringsUtil {
	       
	        public String getStringFromList(List list){
	    	              if(list==null){
	    	            	     return "";
	    	              }else{
	    	            	    return  getStrFromList(list);
	    	              } 
	        }
	    	private  String getStrFromList(List list){
	    	                   Iterator i=list.iterator();
	    	                    int x=0; 
	    	                   String listString="";
	    	                   while(i.hasNext()){
	    	        	            if (x>0){
	    	        		            listString+=",";
	    	        	            }
	                                listString=i.next().toString();
	    	                   }
	                     return listString;
	    	}
	    	public  String getStrFromList(List list,String separatorChar){
                Iterator i=list.iterator();
                 int x=0; 
                String listString="";
                while(i.hasNext()){
     	            if (x>0){
     		            listString+=",";
     	            }
                     listString=i.next().toString();
                }
          return listString;
}
}
