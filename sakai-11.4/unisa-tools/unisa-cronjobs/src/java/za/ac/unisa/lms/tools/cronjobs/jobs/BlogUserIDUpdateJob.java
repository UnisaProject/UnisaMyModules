package za.ac.unisa.lms.tools.cronjobs.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import za.ac.unisa.lms.tools.cronjobs.dao.BlogUserTitleUpdateDao;
import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;

public class BlogUserIDUpdateJob extends SingleClusterInstanceJob {
	public void executeLocked(JobExecutionContext context)
			throws JobExecutionException {		
		BlogUserTitleUpdateDao blogTitleDao = new BlogUserTitleUpdateDao();

		// update the title with user id in blogwow_blog table
		try {
			blogTitleDao.blogUpdate();
		 System.out.println("Cronjob: BlogUserIDUpdateJob blogUpdate");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
}

