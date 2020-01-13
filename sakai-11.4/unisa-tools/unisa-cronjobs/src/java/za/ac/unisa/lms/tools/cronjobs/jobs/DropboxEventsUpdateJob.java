package za.ac.unisa.lms.tools.cronjobs.jobs;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import za.ac.unisa.lms.tools.cronjobs.dao.DropboxEventsUpdateDAO;
import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;

public class DropboxEventsUpdateJob extends SingleClusterInstanceJob implements
StatefulJob, InterruptableJob, OutputGeneratingJob {
	
	
	public void updateDropboxEvents() throws Exception{
		

		System.out.println("Cronjob: DropboxEventsUpdateJob");
		DropboxEventsUpdateDAO sakai_db = new DropboxEventsUpdateDAO();
         
		String contentnew = "content.new";
		String contentdelete = "content.delete";
		/** =========== select bookings between from and to dates */
		boolean eventsExists = sakai_db.checkDropboxJob();
		if (!eventsExists) {
			try {
				sakai_db.updateDropboxEvents(contentnew);
				sakai_db.updateDropboxEvents(contentdelete);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public String getOutput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void executeLocked(JobExecutionContext arg0)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		try {		
			updateDropboxEvents();		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
