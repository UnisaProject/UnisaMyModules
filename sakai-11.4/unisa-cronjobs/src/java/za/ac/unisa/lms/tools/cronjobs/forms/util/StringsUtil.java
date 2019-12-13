package za.ac.unisa.lms.tools.cronjobs.forms.util;

import java.util.Iterator;
import java.util.Set;
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
	                                x++;
	    	                   }
	                     return listString;
	    	}
	    	public  String getStrFromList(List list,String delimStr){
                Iterator i=list.iterator();
                 int x=0; 
                String listString="";
                while(i.hasNext()){
     	              if (x>0){
     	            	  listString+=delimStr;
     	              }
                      listString+=i.next().toString();
                      x++;
                }
                return listString;
	     }
         private  String getStrFromListWithTrailingDelimStr(List list,String delimStr){
                    Iterator i=list.iterator();
                     int x=0; 
                    String listString="";
                    while(i.hasNext()){
         	               listString+=delimStr;
         	               listString+=i.next().toString();
                          x++;
                    }
               
                return listString;
         }
         private  String getStrFromListWithTrailingDelimStr(Set list,String delimStr){
                            Iterator i=list.iterator();
                            int x=0; 
                            String listString="";
                            while(i.hasNext()){
  	                              listString+=delimStr;
  	                              listString+=i.next().toString();
                                  x++;
                           }
                           return listString;
         }
         public  String getStrFromListWithTrailDelimStr(List list,String delimStr){
        	                if(list==null){
        	                      return "";
                            }else{
        	                       return getStrFromListWithTrailingDelimStr(list,delimStr);
                            } 
        }
        public  String getStrFromListWithTrailDelimStr(Set list,String delimStr){
                            if(list==null){
                                 return "";
                            }else{
                                   return getStrFromListWithTrailingDelimStr(list,delimStr);
                           } 
         }
         public  String getStrFromListWithDelim(List list,String delimStr){
        	              if(list==null){
        	                    return "";
                           }else{
        	                    return  getStrFromList(list,delimStr);
                          } 
	    }
}
