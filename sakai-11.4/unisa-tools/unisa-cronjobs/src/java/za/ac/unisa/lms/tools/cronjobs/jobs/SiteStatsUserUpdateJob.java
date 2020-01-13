package za.ac.unisa.lms.tools.cronjobs.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import za.ac.unisa.lms.tools.cronjobs.dao.SatbookDAO;
import za.ac.unisa.lms.tools.cronjobs.dao.SiteStatsDao;
import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;

public class SiteStatsUserUpdateJob extends SingleClusterInstanceJob {
	public void executeLocked(JobExecutionContext context)
			throws JobExecutionException {		
		SiteStatsDao siteStatsDao = new SiteStatsDao();

		// update the eid with user id in sst_evnts(sitestats db)
		try {
		 siteStatsDao.updateUserIdEvents();
		 System.out.println("Cronjob: SiteStatsUserUpdateJob updateUserIdEvents");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		// update the eid with user id in sst_resources(sitestats db)
		try {
			siteStatsDao.updateUserIdResources();
			System.out.println("Cronjob: SiteStatsUserUpdateJob updateUserIdResources");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
