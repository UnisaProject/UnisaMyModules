package za.ac.unisa.lms.tools.cronjobs.locks;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.springframework.dao.DataIntegrityViolationException;

import za.ac.unisa.lms.tools.cronjobs.locks.dao.CronJobLock;
import za.ac.unisa.lms.tools.cronjobs.locks.dao.CronJobLockDAO;
import za.ac.unisa.utils.AdminEmail;

public class LockManager {

	private String jobId;
	private Log log;
	private CronJobLockDAO dao;
	private CronJobLock cachedCronJobLock;
	private String serverId;

	public LockManager(String jobId) {
		this.jobId = jobId;
		log = LogFactory.getLog(this.getClass());
		serverId = ServerConfigurationService.getServerId();
		dao = new CronJobLockDAO();
	}

	public boolean isLocked() {
		if (dao.getCronJobLockById(jobId) != null) {
			return true;
		}
		return false;
	}

	public void lock() throws AlreadyLockedException, LockTimeoutException {
		CronJobLock cjl = dao.getCronJobLockById(jobId);

		if (cjl != null) {
			long seconds = ((new Date().getTime()) - cjl.getModifiedOn().getTime()) / 1000;

			log.debug("Lock "+cjl.toString()+" is "+seconds+" seconds old.");

			if (seconds > 30 * 60) {
				String message = "Modification time on lock "+jobId+" is older " +
				"than 30 minutes. Removing lock and allowing a lock to succeed. Lock Info: "+cjl.toString();
				log.error(message);
				AdminEmail.sendAdminEmail("Lock timeout", message);
				dao.deleteCronJobLock(cjl);
				log.debug("Lock deleted due to timeout: "+toString());
				throw new LockTimeoutException(message);
			} else
			throw new AlreadyLockedException("Lock already exists: "+cjl.toString());
		}
		cjl = new CronJobLock(jobId);

		try {
			dao.insertCronJobLock(cjl);
		} catch (DataIntegrityViolationException dive) {
			log.warn("Lock failed. Seems like we just weren't quick enough.");
			cjl = dao.getCronJobLockById(jobId);
			if (cjl != null) {
				throw new AlreadyLockedException("Lock already exists: "+cjl);
			} else {
				throw new AlreadyLockedException("Lock insert failed, but lock query did not return a lock.", dive);
			}
		}
		cachedCronJobLock = cjl;
		log.debug("Lock created: "+toString());
	}

	public void unlock() throws NotLockedException {
		CronJobLock cjl = dao.getCronJobLockById(jobId);
		if (cjl == null) {
			throw new NotLockedException("Lock doesn't exist: "+jobId);
		}
		dao.deleteCronJobLock(cjl);
		log.debug("Lock deleted: "+toString());
		cachedCronJobLock = null;
	}

	public void update() throws NotLockedException, IllegalStateException {
		CronJobLock cjl = dao.getCronJobLockById(jobId);
		if (cjl == null) {
			cachedCronJobLock = null;
			throw new NotLockedException("Lock doesn't exist: "+jobId);
		}
		if (!cjl.getServerId().equals(serverId)) {
			cachedCronJobLock = null;
			throw new IllegalStateException("Lock to be updated not owned by me ("+serverId+"). We are running concurrently... Lock: "+cjl);
		}
		cjl.setModifiedOn(new Timestamp(new Date().getTime()));
		dao.updateCronJobLock(cjl);
		cachedCronJobLock = cjl;
		log.debug("Lock updated: "+toString());
	}

	public String toString() {
		String jobDesc = jobId;
		if (cachedCronJobLock != null) { jobDesc = cachedCronJobLock.toString(); };
		return "LockManager instance "+super.toString()+" for job: "+jobDesc;
	}
}
