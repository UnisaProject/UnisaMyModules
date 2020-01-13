package za.ac.unisa.lms.tools.cronjobs.jobs;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import za.ac.unisa.lms.tools.cronjobs.dao.SatbookDAO;
import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;



public class VCbookingJob extends SingleClusterInstanceJob implements StatefulJob, OutputGeneratingJob {
	
	public static long runcount = 1L;
	private ByteArrayOutputStream outputStream;
	private PrintWriter output;
	
	
	public void executeLocked(JobExecutionContext context) throws JobExecutionException{
		SatbookDAO db1 = new SatbookDAO();
		
		//Send e-mail to users who placed the bookings
		
		try{
			db1.selectVCBookingListForUsers();
		}catch(Exception e){
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Send e-mail to venue facilitators who placed the bookings
		try{
			db1.selectVenueBookingList();
		}catch(Exception e){
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getOutput(){
		if (output != null){
			output.flush();
			return outputStream.toString();
		}
		return null;
	}
}
