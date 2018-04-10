package za.ac.unisa.lms.tools.telecentre.forms;

import za.ac.unisa.lms.tools.telecentre.forms.dateUtil;
import  za.ac.unisa.lms.tools.telecentre.dao.TelecentreDAO;
import  za.ac.unisa.lms.tools.telecentre.dao.StudentTelecentre;

import  java.util.ArrayList;
import  java.util.Iterator;

public class TelecentreLogInTracker  {
		    public boolean checkVisitsStatus(String stuNum){
		              ArrayList currVisits=getCurrentVisitsForStu(stuNum);
		              Iterator iterator=currVisits.iterator();
		              boolean found=false;
		              while(iterator.hasNext()){
		            	 
		                  StudentTelecentre  stuTel=(StudentTelecentre)iterator.next();
		                  if(stuTel.getUsedHours()==0){
		                	  found = true;
		                	  break;
		                  }
		             }
		              return found;
       }
	   public ArrayList<StudentTelecentre> getCurrentVisitsForStu(String stuNum){
                TelecentreDAO dao=new TelecentreDAO();
                return dao.getCurrentVisitsForStu(stuNum);
       }
}
