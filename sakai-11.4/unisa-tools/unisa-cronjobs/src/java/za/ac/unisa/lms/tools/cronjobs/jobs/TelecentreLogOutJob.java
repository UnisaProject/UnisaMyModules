package za.ac.unisa.lms.tools.cronjobs.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import za.ac.unisa.lms.tools.cronjobs.dao.SatbookDAO;
import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;

import za.ac.unisa.lms.tools.cronjobs.dao.TelecentreDAO;
import za.ac.unisa.lms.tools.cronjobs.dao.dateUtil;
import za.ac.unisa.lms.tools.cronjobs.dao.StudentTelecentre;

import java.util.ArrayList;
import java.util.Iterator;

public class TelecentreLogOutJob extends SingleClusterInstanceJob implements StatefulJob, OutputGeneratingJob {
	
	//Sifiso Changes:Changed line below:2016/07/05:Changed 'used_hrs' variable from int to double
	//private void updateHours(int studentNum,int used_hrs,String tele_id,String date,String endTime){
	private void updateHours(int studentNum,double used_hrs,String tele_id,String date,String endTime){
        //int available_hrs,allocated_hrs;	//Sifiso Changes:Changed:2016/06/30-Changed 'available_hrs' variable from int to double
		double available_hrs;   //Sifiso Changes
   	 	int allocated_hrs;		//Sifiso Changes:Added:2016/06/30:As above in line 22
        dateUtil du=new dateUtil();
        TelecentreDAO dao=new TelecentreDAO();
        available_hrs=dao.getStudentAvailableHours(""+studentNum,du.getMonthInt(),du.getYearInt());
        int updateHrs=1;
        if(available_hrs>=2){
        	updateHrs=2;
        }
        if(used_hrs==0){
        	 if(available_hrs>=updateHrs){
                dao.updateStuAvailHours(studentNum,updateHrs);
                dao.updateTeleAvailHours(tele_id,updateHrs);
        	 }
             dao.updateTotalHrs(tele_id,""+studentNum,""+updateHrs);
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
		            	  if(!compareTime(stuTel.getEndTime())){	//Sifiso Comments:2016/08/24:If Current Time is Greater than End TimeStamp
		            		  updateHours(stuTel.getStudentNumber(),stuTel.getUsedHours(),""+stuTel.getTeleId(),du.dateOnly(),stuTel.getEndTime());
		            	  }
		              }
	   }
	   public void checkVisitsStatusForPrev(){
		              TelecentreDAO dao=new TelecentreDAO();
		              dateUtil du=new dateUtil();
                      ArrayList currVisits=dao.getLastDayVisits();
                      Iterator iterator=currVisits.iterator();
                      while(iterator.hasNext()){
         	              StudentTelecentre  stuTel=(StudentTelecentre)iterator.next();
         	             updateHours(stuTel.getStudentNumber(),stuTel.getUsedHours(),""+stuTel.getTeleId(),du.getPrevDate(),stuTel.getEndTime());
                      }
       }
	   
	   public void executeLocked(JobExecutionContext context) throws JobExecutionException{
		    try{
		    	checkVisitsStatus();
		    	checkVisitsStatusForPrev();
			    	
			    
		    }catch(Exception e){
			//TODO Auto-generated catch block
			e.printStackTrace();
		   }
	  }
	  public String getOutput(){
			return null;
	}
	private void updateAvailableHoursPerHour(StudentTelecentre stuTel){
		//Sifiso Changes:Changed line below:2016/07/05-Initialiaze 'stuAvailHours' varaible as double
        //int totProjHours=0,totUsedHours=0,stuAvailHours=0;
		double stuAvailHours=0.0;					//Sifiso Changes:Initialize the 'stuAvailHours' variable as double
		int totProjHours=0,totUsedHours=0;			//Sifiso Changes:Initialize the rest of the variable as int
        dateUtil du=new dateUtil();
        totProjHours=du.calcHours(stuTel.getEndTime(),stuTel.getStartTime());
        totUsedHours=du.calcHours(stuTel.getStartTime());
        String tele_id=""+stuTel.getTeleId();
        TelecentreDAO dao=new TelecentreDAO();
        int studentNum=stuTel.getStudentNumber();
        stuAvailHours=dao.getStudentAvailableHours(""+studentNum,du.getMonthInt(),du.getYearInt());
        if(totUsedHours>(totProjHours-stuAvailHours)){
      	       dao.updateStuAvailHours(studentNum,1);
                 dao.updateTeleAvailHours(""+stuTel.getTeleId(),1);
        }else if(totUsedHours>totProjHours){
      	         //updateTotalHrs(studentNum,tele_id,totProjHours);
        }
   }
	
}
