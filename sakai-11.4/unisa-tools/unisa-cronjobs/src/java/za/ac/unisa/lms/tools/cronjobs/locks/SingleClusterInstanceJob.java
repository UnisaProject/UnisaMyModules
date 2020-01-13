package za.ac.unisa.lms.tools.cronjobs.locks;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

public abstract class SingleClusterInstanceJob implements InterruptableJob {
	
	private LockManager lockManager;
	private Log log;
	private Date startTime;
	private Date endTime;

	public Date getEndTime() {
		return endTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public abstract void executeLocked(JobExecutionContext arg0) throws JobExecutionException;
	
	public void updateLock() throws JobExecutionException {
		try {
			lockManager.update();
			log.debug("Lock updated: "+lockManager.toString());
		} catch (Exception e) {
			log.error("Lock update failed: "+e);
			throw new JobExecutionException(e);
		}
	}
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log = LogFactory.getLog(this.getClass());
		lockManager = new LockManager(this.getClass().getName());
		startTime = new Date();
		
		try {
			lockManager.lock();
		} catch (AlreadyLockedException ale) {
			log.info("A lock already exists. Not running.");
			return;
		} catch (LockTimeoutException lte) {
			log.info("Lock timeout");
			return;
		} catch (Exception e) {
			log.error("Unknown exception: "+e+" "+e.getMessage());
			throw new JobExecutionException(e);
		}

		try {
			log.info(this.getClass().getName()+" starting");
			executeLocked(arg0);
			log.info(this.getClass().getName()+" completed without exception");
		} catch (JobExecutionException jee) {
			log.error(jee);
			throw jee;
		} catch (Exception e) {
			log.error("Unhandled exception: "+e);
			throw new JobExecutionException(e);
		} finally {
			try {
				endTime = new Date();
				if (lockManager.isLocked()) {
					lockManager.unlock();
				}
			} catch (Exception e) {
				throw new JobExecutionException(e);
			}
		}

	}

	public void interrupt() throws UnableToInterruptJobException {
		try {
			if (lockManager.isLocked()) {
				lockManager.unlock();
			}
		} catch (Exception e) {
			log.error("Could not unlock: "+lockManager);
		}
	}
	
}
