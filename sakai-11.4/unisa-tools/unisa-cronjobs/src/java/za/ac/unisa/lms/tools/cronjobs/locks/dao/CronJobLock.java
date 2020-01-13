package za.ac.unisa.lms.tools.cronjobs.locks.dao;

import java.sql.Timestamp;
import java.util.Date;

import org.sakaiproject.component.cover.ServerConfigurationService;

public class CronJobLock {
	private String jobId;

	private Timestamp lockTime;

	private Timestamp modifiedOn;

	private String serverId;

	public CronJobLock() {
		lockTime = new Timestamp(new Date().getTime());
		modifiedOn = new Timestamp(new Date().getTime());
		serverId = ServerConfigurationService.getServerId();
	}

	public CronJobLock(String jobId) {
		lockTime = new Timestamp(new Date().getTime());
		modifiedOn = new Timestamp(new Date().getTime());
		serverId = ServerConfigurationService.getServerId();
		this.jobId = jobId;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public Timestamp getLockTime() {
		return lockTime;
	}

	public void setLockTime(Timestamp lockTime) {
		this.lockTime = lockTime;
	}

	public Timestamp getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Timestamp modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String toString() {
		return "JobID: " + jobId + " LockTime: " + lockTime + " ModifiedOn: "
				+ modifiedOn + " ServerId: " + serverId;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

}
