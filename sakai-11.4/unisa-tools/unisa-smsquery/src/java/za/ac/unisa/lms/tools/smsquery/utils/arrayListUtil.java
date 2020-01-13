package za.ac.unisa.lms.tools.smsquery.utils;

import java.util.ArrayList;
import java.util.ListIterator;

public class arrayListUtil {
	public int  getNextItems(ArrayList list,ArrayList subList,int limit,int startPos){
	         //it adds elements of list from startPos into the empty sublist  and it returns the position 
     		//in list of the the  element immediately after the last element of sublist
		        ListIterator li=list.listIterator(startPos);
		        int totItems=0;
                while((li.hasNext())&&(totItems<limit)){
          	         subList.add(li.next());
          	         startPos=li.nextIndex();
          	         totItems++;
                } 
                return  startPos;
    }   
	public int  getPrevItems(ArrayList list,ArrayList subList,int limit,int startPos){
	      //it adds elements of list from startPos into the empty sublist  and it returns the position in list of the first element of sublist
		        if(startPos>list.size()){
		        	startPos=list.size();
		        }
		        ListIterator li=list.listIterator(startPos);
		        int totItems=0;
              while((li.hasPrevious())&&(totItems<limit)){
            	   startPos=li.previousIndex();
        	       subList.add(li.previous());
        	       totItems++;;
              } 
              return  startPos;
  }

}
