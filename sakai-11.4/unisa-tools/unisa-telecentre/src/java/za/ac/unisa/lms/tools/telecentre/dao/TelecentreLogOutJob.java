package za.ac.unisa.lms.tools.telecentre.dao;
import java.util.ArrayList;
import java.util.Iterator;
import za.ac.unisa.lms.tools.telecentre.forms.dateUtil;
public class TelecentreLogOutJob {
	
		//Sifiso Changes:Changed line below:2016/07/05:Changed 'used_hrs' variable from int to double
	    //private void updateHours(int studentNum,int used_hrs,String tele_id,String date,String endTime){
		private void updateHours(int studentNum,double used_hrs,String tele_id,String date,String endTime){
             //int available_hrs,allocated_hrs;	//Sifiso Changes:Changed:2016/06/30-Changed 'available_hrs' from int to double
	    	 double available_hrs;  	//Sifiso Changes
	    	 int allocated_hrs;			//Sifiso Changes:Added:2016/06/30:As above in line 8
	    	 String timeUnit="Hours";	//Sifiso Changes:2016/08/26:Added 'timeUnit' variable to add as parameter in updateTotalHrs method
             dateUtil du=new dateUtil();
             TelecentreDAO dao=new TelecentreDAO();
             int month=du.getMonthInt();
             int year=du.getYearInt();
             if(du.getDayOfMonthInt()==1){
            	 if(month==1){
            		 month=12;
            		 year--;
            	 }else{
            		 month--;
            	 }
             }
             available_hrs=dao.getStudentAvailableHours(""+studentNum,month,year);
             int updateHrs=1;
             if(available_hrs>=2){
        	      updateHrs=2;
             }
             if((used_hrs==0)&&(available_hrs>0)){
        	   if(available_hrs>=updateHrs){
                   dao.updateStuAvailHours(studentNum,updateHrs);
                   dao.updateTeleAvailHours(tele_id,updateHrs);
        	   }
        	   //Sifiso Changes:2016/08/26:Added 'timeUnit' parameter to resolve SR: SR8071
               //dao.updateTotalHrs(tele_id,""+studentNum,""+updateHrs);
        	   dao.updateTotalHrs(tele_id,""+studentNum,""+updateHrs,timeUnit);
               String subject="End of Telecentre session";
   	               String  body=dao.getBodyOfClosingEmail(""+studentNum,date,endTime);
   	               try{
   	                   String emailAddress = dao.getEmail(tele_id);
   	                   dao.sendEmail(subject,body,emailAddress);
   	               }catch(Exception ex){}
            }
        }
   	    private ArrayList<StudentTelecentre> getCurrentVisits(){
		             TelecentreDAO dao=new TelecentreDAO();
		             return dao.getCurrentVisits();
   	    }
   	    private boolean compareTime(String givenTime){// to check latest timestamp if it is less than curr system time
		       dateUtil du=new dateUtil();
		       if(du.givenTimeGreaterSystime(givenTime)){
		    	 return true;
		       }else{
		    	 return false;
		       }
	    }
	    public void checkVisitsStatus(){
		              dateUtil du=new dateUtil();
		              ArrayList currVisits=getCurrentVisits();
		              Iterator iterator=currVisits.iterator();
		              while(iterator.hasNext()){
		            	  StudentTelecentre  stuTel=(StudentTelecentre)iterator.next();
		            	  if(!compareTime(stuTel.getEndTime())){
		            		  updateHours(stuTel.getStudentNumber(),stuTel.getUsedHours(),""+stuTel.getTeleId(),du.dateOnly(),stuTel.getEndTime());
		            	  }
		              }
	    }
	    public void checkVisitsStatusForPrev(){
		              TelecentreDAO dao=new TelecentreDAO();
		              dateUtil du=new dateUtil();
                      ArrayList currVisits=dao.getPrevDayVisits();
                      Iterator iterator=currVisits.iterator();
                      while(iterator.hasNext()){
         	              StudentTelecentre  stuTel=(StudentTelecentre)iterator.next();
         	              updateHours(stuTel.getStudentNumber(),0,""+stuTel.getTeleId(),du.getPrevDate(),stuTel.getEndTime());
                      }
       }
}
