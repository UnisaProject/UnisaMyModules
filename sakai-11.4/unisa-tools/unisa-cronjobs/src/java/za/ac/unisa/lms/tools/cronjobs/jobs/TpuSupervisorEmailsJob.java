package za.ac.unisa.lms.tools.cronjobs.jobs;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;
import za.ac.unisa.lms.tools.cronjobs.forms.model.SupervisorEmail;
public class TpuSupervisorEmailsJob extends SingleClusterInstanceJob implements StatefulJob, OutputGeneratingJob {
	
	 public void executeLocked(JobExecutionContext context) throws JobExecutionException{
		    try{
		    	    SupervisorEmail sec=new SupervisorEmail(); 
		    	    sec.sendEmailToSupervisors();
			    	
		    }catch(Exception e){
			//TODO Auto-generated catch block
			e.printStackTrace();
		   }
	  }
	  public String getOutput(){
			  return null;
	  }
}
